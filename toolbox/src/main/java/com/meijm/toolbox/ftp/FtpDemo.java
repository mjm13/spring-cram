package com.meijm.toolbox.ftp;

import cn.hutool.core.io.FileUtil;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.net.ftp.FTPClient;
import org.apache.commons.net.ftp.FTPFile;
import org.apache.commons.net.ftp.FTPReply;

import java.io.IOException;
@Slf4j
public class FtpDemo {
    public static void main(String[] args) {
        FTPClient ftpClient = new FTPClient();
        try {
            log.info("开始连接");
            ftpClient.connect("192.168.3.27", 21);
// 匿名登陆
            log.info("匿名登陆");
            ftpClient.login("anonymous", "222");

// 检查登陆是否成功
            int replyCode = ftpClient.getReplyCode();
            if (!FTPReply.isPositiveCompletion(replyCode)) {
                ftpClient.disconnect();
                throw new IOException("FTP server refused connection.");
            }
            log.info("开始上传文件");
            ftpClient.appendFile("text1.txt", FileUtil.getInputStream("D:/info.log"));
// 列出目录内容
            log.info("列出服务器上文件");
            for (FTPFile ftpFile : ftpClient.listFiles()) {
                log.info(ftpFile.getName());
            }

// 下载和上传文件
// ...

// 登出并断开连接
            ftpClient.logout();
            ftpClient.disconnect();
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }
}
