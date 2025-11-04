package com.meijm.toolbox.watermark;

import java.nio.charset.StandardCharsets;
import java.util.ArrayList;
import java.util.List;
import java.util.zip.CRC32;

/**
 * 基于零宽字符的隐形文本水印工具
 * 采用中间分散插入 + 压缩优化策略
 */
public class InvisibleTextWatermark {
    // 零宽字符映射：00, 01, 10, 11
    private static final char[] ZERO_WIDTH_CHARS = {
            '\u200B',  // 00 - 零宽空格
            '\u200C',  // 01 - 零宽非连接符
            '\u200D',  // 10 - 零宽连接符
            '\uFEFF'   // 11 - 零宽不换行空格
    };

    private static final int BITS_PER_CHAR = 2;
    private static final int MAX_WATERMARK_LENGTH = 1000;

    /**
     * 嵌入水印到字符串中（分散插入，极致压缩）
     * @param carrier 载体字符串
     * @param watermark 水印字符串
     * @return 嵌入水印后的字符串
     */
    public static String embed(String carrier, String watermark) {
        if (carrier == null || watermark == null || watermark.isEmpty()) {
            throw new IllegalArgumentException("载体和水印不能为空");
        }

        byte[] wmBytes = watermark.getBytes(StandardCharsets.UTF_8);
        if (wmBytes.length > MAX_WATERMARK_LENGTH) {
            throw new IllegalArgumentException("水印过长");
        }

        // 查找所有可插入位置（字符之间都可插入）
        List<Integer> insertPositions = findAllInsertPositions(carrier);

        // 使用变长编码 + CRC8校验
        byte[] compressed = compressWatermark(wmBytes);
        char[] encoded = encodeToZeroWidthArray(compressed);

        // 分散插入到文本中
        return distributeInsert(carrier, encoded, insertPositions);
    }

    /**
     * 从字符串提取水印
     */
    public static String extract(String watermarked) {
        if (watermarked == null || watermarked.isEmpty()) {
            return null;
        }

        // 提取所有零宽字符并解码
        int[] bits = decodeFromZeroWidth(watermarked);
        if (bits.length < 16) {  // 至少需要长度字段(7位) + 校验(8位) + 1字节数据
            return null;
        }

        try {
            // 解析变长长度（前7位）
            int wmLength = bitsToInt(bits, 0, 7);
            if (wmLength <= 0 || wmLength > 127) {
                return null;
            }

            // 提取CRC校验码（8位）
            int crc8 = bitsToInt(bits, 7, 8);

            // 验证数据完整性
            int requiredBits = 15 + wmLength * 8;
            if (bits.length < requiredBits) {
                return null;
            }

            // 提取水印字节
            byte[] wmBytes = bitsToBytes(bits, 15, wmLength);

            // 校验CRC
            if (calculateCRC8(wmBytes) != crc8) {
                return null;
            }

            return new String(wmBytes, StandardCharsets.UTF_8);
        } catch (Exception e) {
            return null;
        }
    }

    /**
     * 查找所有可插入位置
     * 策略：字符之间都可插入，包括空白字符
     */
    private static List<Integer> findAllInsertPositions(String text) {
        List<Integer> positions = new ArrayList<>();
        int len = text.length();

        if (len == 0) {
            return positions;
        }

        // 为每个字符间隙创建插入点
        // 对于单字符，在字符后插入；对于多字符，在所有间隙插入
        for (int i = 1; i <= len; i++) {
            positions.add(i);
        }

        return positions;
    }

    /**
     * 分散插入零宽字符
     * 策略：尽量均匀分布，优先使用文本中间位置
     */
    private static String distributeInsert(String carrier, char[] encoded, List<Integer> positions) {
        int posCount = positions.size();
        int encLen = encoded.length;

        if (posCount == 0) {
            // 空文本，直接返回编码
            return new String(encoded);
        }

        StringBuilder sb = new StringBuilder(carrier.length() + encLen);
        int encIdx = 0;
        int posIdx = 0;

        for (int i = 0; i < carrier.length(); i++) {
            sb.append(carrier.charAt(i));

            // 检查是否在插入位置
            if (posIdx < posCount && positions.get(posIdx) == i + 1) {
                // 计算本位置插入数量（均匀分布）
                if (encIdx < encLen) {
                    int remaining = encLen - encIdx;
                    int remainingPos = posCount - posIdx;
                    int insertCount = (remaining + remainingPos - 1) / remainingPos;  // 向上取整

                    // 插入零宽字符
                    for (int j = 0; j < insertCount && encIdx < encLen; j++) {
                        sb.append(encoded[encIdx++]);
                    }
                }
                posIdx++;
            }
        }

        // 处理剩余（理论上不会发生）
        while (encIdx < encLen) {
            sb.append(encoded[encIdx++]);
        }

        return sb.toString();
    }

    /**
     * 压缩水印数据：变长编码 + CRC8校验
     * 格式：[7位长度][8位CRC8][数据内容]
     */
    private static byte[] compressWatermark(byte[] data) {
        int len = data.length;
        if (len > 127) {
            throw new IllegalArgumentException("水印过长（最大127字节）");
        }

        // 计算CRC8校验码
        int crc8 = calculateCRC8(data);

        // 构建压缩数据：15位头部 + 数据
        int totalBits = 15 + len * 8;
        int totalBytes = (totalBits + 7) / 8;
        byte[] compressed = new byte[totalBytes];

        // 写入长度（7位）
        writeBits(compressed, 0, len, 7);

        // 写入CRC8（8位）
        writeBits(compressed, 7, crc8, 8);

        // 写入数据内容
        for (int i = 0; i < len; i++) {
            writeBits(compressed, 15 + i * 8, data[i] & 0xFF, 8);
        }

        return compressed;
    }

    /**
     * 计算CRC8校验码（简化版）
     */
    private static int calculateCRC8(byte[] data) {
        int crc = 0;
        for (byte b : data) {
            crc ^= (b & 0xFF);
            for (int i = 0; i < 8; i++) {
                if ((crc & 0x80) != 0) {
                    crc = (crc << 1) ^ 0x07;  // CRC-8多项式
                } else {
                    crc <<= 1;
                }
            }
        }
        return crc & 0xFF;
    }

    /**
     * 写入指定位数的数据到字节数组
     */
    private static void writeBits(byte[] bytes, int bitOffset, int value, int bitCount) {
        for (int i = 0; i < bitCount; i++) {
            int bitPos = bitOffset + i;
            int byteIdx = bitPos / 8;
            int bitIdx = 7 - (bitPos % 8);

            if (((value >> (bitCount - 1 - i)) & 1) == 1) {
                bytes[byteIdx] |= (1 << bitIdx);
            }
        }
    }

    /**
     * 将字节数组编码为零宽字符数组
     */
    private static char[] encodeToZeroWidthArray(byte[] data) {
        char[] result = new char[data.length * 4];
        int idx = 0;

        for (byte b : data) {
            for (int shift = 6; shift >= 0; shift -= 2) {
                int code = (b >> shift) & 0b11;
                result[idx++] = ZERO_WIDTH_CHARS[code];
            }
        }

        return result;
    }

    /**
     * 从字符串中提取零宽字符并解码为位数组
     */
    private static int[] decodeFromZeroWidth(String text) {
        // 统计零宽字符数量
        int count = 0;
        for (int i = 0; i < text.length(); i++) {
            if (isZeroWidthChar(text.charAt(i))) {
                count++;
            }
        }

        if (count == 0) {
            return new int[0];
        }

        // 转换为位数组
        int[] bits = new int[count * BITS_PER_CHAR];
        int idx = 0;

        for (int i = 0; i < text.length(); i++) {
            char c = text.charAt(i);
            int code = getZeroWidthCode(c);
            if (code >= 0) {
                bits[idx++] = (code >> 1) & 1;
                bits[idx++] = code & 1;
            }
        }

        return bits;
    }

    private static boolean isZeroWidthChar(char c) {
        return c == ZERO_WIDTH_CHARS[0] || c == ZERO_WIDTH_CHARS[1]
                || c == ZERO_WIDTH_CHARS[2] || c == ZERO_WIDTH_CHARS[3];
    }

    private static int getZeroWidthCode(char c) {
        for (int i = 0; i < ZERO_WIDTH_CHARS.length; i++) {
            if (c == ZERO_WIDTH_CHARS[i]) {
                return i;
            }
        }
        return -1;
    }

    private static int bitsToInt(int[] bits, int offset, int length) {
        int value = 0;
        for (int i = 0; i < length; i++) {
            value = (value << 1) | bits[offset + i];
        }
        return value;
    }

    private static byte[] bitsToBytes(int[] bits, int offset, int byteCount) {
        byte[] bytes = new byte[byteCount];
        for (int i = 0; i < byteCount; i++) {
            int b = 0;
            for (int j = 0; j < 8; j++) {
                b = (b << 1) | bits[offset + i * 8 + j];
            }
            bytes[i] = (byte) b;
        }
        return bytes;
    }

    public static void main(String[] args) {
        // 测试用例1：正常文本
        String carrier1 = "人工智能技术正在快速发展，深度学习算法已经应用到各个领域。";
        String watermark = "user-001";

        System.out.println("=== 测试1：正常文本 ===");
        System.out.println("原始: " + carrier1);
        String wm1 = embed(carrier1, watermark);
        System.out.println("嵌入: " + wm1);
        System.out.println("长度: " + carrier1.length() + " → " + wm1.length()
                + " (增加 " + (wm1.length() - carrier1.length()) + ")");
        String ext1 = extract(wm1);
        System.out.println("提取: " + ext1);
        System.out.println("验证: " + (watermark.equals(ext1) ? "✓" : "✗"));
        System.out.println();

        // 测试用例2：包含空格的文本
        String carrier2 = "Hello World! This is a test.";
        System.out.println("=== 测试2：英文带空格 ===");
        System.out.println("原始: " + carrier2);
        String wm2 = embed(carrier2, watermark);
        System.out.println("嵌入: " + wm2);
        System.out.println("长度: " + carrier2.length() + " → " + wm2.length()
                + " (增加 " + (wm2.length() - carrier2.length()) + ")");
        String ext2 = extract(wm2);
        System.out.println("提取: " + ext2);
        System.out.println("验证: " + (watermark.equals(ext2) ? "✓" : "✗"));
        System.out.println();

        // 测试用例3：短文本
        String carrier3 = "短文本";
        System.out.println("=== 测试3：短文本 ===");
        System.out.println("原始: " + carrier3);
        String wm3 = embed(carrier3, watermark);
        System.out.println("嵌入: " + wm3);
        System.out.println("长度: " + carrier3.length() + " → " + wm3.length()
                + " (增加 " + (wm3.length() - carrier3.length()) + ")");
        String ext3 = extract(wm3);
        System.out.println("提取: " + ext3);
        System.out.println("验证: " + (watermark.equals(ext3) ? "✓" : "✗"));
        System.out.println();

        // 测试用例4：单字符
        String carrier4 = "A";
        System.out.println("=== 测试4：单字符 ===");
        System.out.println("原始: " + carrier4);
        String wm4 = embed(carrier4, watermark);
        System.out.println("嵌入: " + wm4);
        System.out.println("长度: " + carrier4.length() + " → " + wm4.length()
                + " (增加 " + (wm4.length() - carrier4.length()) + ")");
        String ext4 = extract(wm4);
        System.out.println("提取: " + ext4);
        System.out.println("验证: " + (watermark.equals(ext4) ? "✓" : "✗"));
        System.out.println();

        // 测试用例5：纯空格
        String carrier5 = "   ";
        System.out.println("=== 测试5：纯空格 ===");
        System.out.println("原始: '" + carrier5 + "'");
        String wm5 = embed(carrier5, watermark);
        System.out.println("嵌入: '" + wm5 + "'");
        System.out.println("长度: " + carrier5.length() + " → " + wm5.length()
                + " (增加 " + (wm5.length() - carrier5.length()) + ")");
        String ext5 = extract(wm5);
        System.out.println("提取: " + ext5);
        System.out.println("验证: " + (watermark.equals(ext5) ? "✓" : "✗"));
        System.out.println();

        // 压缩效果对比
        System.out.println("=== 压缩效果分析 ===");
        System.out.println("水印 'user-001' 字节数: " + watermark.getBytes(StandardCharsets.UTF_8).length);
        System.out.println("传统方案(4字节长度): " + (4 + 8) * 4 + " 个零宽字符");
        System.out.println("优化方案(变长编码): " + (wm1.length() - carrier1.length()) + " 个零宽字符");
        System.out.println("压缩率: " + String.format("%.1f%%", (1 - (wm1.length() - carrier1.length()) / 48.0) * 100));
    }
}