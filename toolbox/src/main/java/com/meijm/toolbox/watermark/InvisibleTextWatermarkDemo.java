package com.meijm.toolbox.watermark;

import java.nio.ByteBuffer;
import java.nio.ByteOrder;
import java.nio.charset.StandardCharsets;

public class InvisibleTextWatermarkDemo {
    private static final char ZWSP = '\u200B';  // 00, 零宽空格
    private static final char ZWNJ = '\u200C';  // 01, 零宽非连接符
    private static final char ZWJ = '\u200D';   // 10, 零宽连接符
    private static final char FEFF = '\uFEFF';  // 11, 零宽不换行空格

    /**
     * 嵌入水印到字符串（末尾隐形零宽序列，2位/字符，中文兼容）
     * @param carrier 载体字符串
     * @param watermark 水印字符串
     * @return 嵌入水印后的字符串
     */
    public static String embedWatermark(String carrier, String watermark) {
        // 水印准备：长度 + 内容
        byte[] watermarkBytes = watermark.getBytes(StandardCharsets.UTF_8);
        int wmLength = watermarkBytes.length;
        byte[] data = new byte[4 + wmLength];
        ByteBuffer.wrap(data).order(ByteOrder.BIG_ENDIAN).putInt(wmLength);
        System.arraycopy(watermarkBytes, 0, data, 4, wmLength);

        // 转为位流（boolean[]，true=1，高位先）
        boolean[] bits = new boolean[data.length * 8];
        for (int i = 0; i < data.length; i++) {
            for (int j = 7; j >= 0; j--) {
                bits[i * 8 + (7 - j)] = ((data[i] >> j) & 0x01) == 1;
            }
        }
        int totalBits = bits.length;

        // 计算所需零宽字符数（2位/1char）
        int numInsertsNeeded = (totalBits + 1) / 2;

        // 嵌入：完整载体 + 末尾零宽序列
        StringBuilder sb = new StringBuilder(carrier);
        int bitIndex = 0;
        for (int i = 0; i < numInsertsNeeded; i++) {
            if (bitIndex >= totalBits) break;
            boolean bit1 = bits[bitIndex++];
            boolean bit2 = bitIndex < totalBits ? bits[bitIndex++] : false;  // 垫0
            int code = (bit1 ? 2 : 0) | (bit2 ? 1 : 0);  // bit1<<1 | bit2
            char toAppend;
            switch (code) {
                case 0: toAppend = ZWSP; break;
                case 1: toAppend = ZWNJ; break;
                case 2: toAppend = ZWJ; break;
                case 3: toAppend = FEFF; break;
                default: toAppend = ZWSP;
            }
            sb.append(toAppend);
        }
        return sb.toString();
    }

    /**
     * 从字符串提取水印
     * @param watermarked 水印字符串
     * @return 提取的水印字符串
     */
    public static String extractWatermark(String watermarked) {
        // 收集零宽字符位（2位/字符）
        StringBuilder bitBuilder = new StringBuilder();
        for (int i = 0; i < watermarked.length(); i++) {
            char c = watermarked.charAt(i);
            if (c == ZWSP) {
                bitBuilder.append("00");
            } else if (c == ZWNJ) {
                bitBuilder.append("01");
            } else if (c == ZWJ) {
                bitBuilder.append("10");
            } else if (c == FEFF) {
                bitBuilder.append("11");
            }
        }
        String bitStr = bitBuilder.toString();
        int totalBits = bitStr.length();
        if (totalBits < 32) {
            return "提取失败：零宽字符不足";
        }

        // 前32位转为长度（大端序）
        int length = 0;
        for (int i = 0; i < 32; i++) {
            if (bitStr.charAt(i) == '1') {
                length |= (1 << (31 - i));
            }
        }
        int wmByteLength = length;
        int requiredBits = 32 + wmByteLength * 8;
        if (totalBits < requiredBits || wmByteLength <= 0 || wmByteLength > 1000) {
            return "提取失败：无效长度或数据不足";
        }

        // 提取水印字节
        byte[] data = new byte[wmByteLength];
        for (int byteIdx = 0; byteIdx < wmByteLength; byteIdx++) {
            int startBit = 32 + byteIdx * 8;
            for (int j = 7; j >= 0; j--) {
                int bitPos = startBit + (7 - j);
                if (bitPos < totalBits && bitStr.charAt(bitPos) == '1') {
                    data[byteIdx] |= (1 << j);
                }
            }
        }
        return new String(data, StandardCharsets.UTF_8);
    }

    public static void main(String[] args) {
        String carrier = "节標：这是一个测试文本，用于数字水印演示。";  // 中文短载体
        String watermark = "user-应用1";

        System.out.println("原始数据: " + carrier);
        String watermarked = embedWatermark(carrier, watermark);
        System.out.println("嵌入后字符串: " + watermarked);  // 主体相同，末尾隐形
        System.out.println("视觉比较：原长=" + carrier.length() + ", 新长=" + watermarked.length() + " (增加末尾零宽序列)");

        String extracted = extractWatermark(watermarked);
        System.out.println("提取的水印: " + extracted);
    }
}