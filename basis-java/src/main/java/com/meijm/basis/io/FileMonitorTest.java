package com.meijm.basis.io;

import org.apache.commons.io.FileUtils;

import java.io.File;

public class FileMonitorTest {
    public static void main(String[] args) {
//        do {
//            oldLength = new File(path).length();
//            log.info("File Uploading >>" + path + ": " + oldLength);
//            Thread.sleep(3000);
//            newLength = new File(oldPath).length();
//            log.info("File Uploading >>" + path + ": " + newLength);
//        } while (oldLength != newLength);
        File file = new File("D:\\Project\\Self\\spring-cram\\basis-java\\src\\main\\java\\com\\meijm\\basis\\io\\FileMonitorTest.java");
        System.out.println(file.length());
    }
}
