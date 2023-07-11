package com.meijm.dynamicdb.service;

import cn.hutool.core.io.FileUtil;
import com.google.common.collect.ImmutableList;
import com.meijm.dynamicdb.entity.BookInfo;
import com.meijm.dynamicdb.mapper.BookInfoMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

@Service
public class BookInfoService {
    public static List<String> suffixs = ImmutableList.of("txt","mobi","azw3","pdf","epub","azw","chm","prc");
    @Autowired
    private BookInfoMapper bookInfoMapper;

    public void init(){
        File dir = new File("F:\\同步文件夹\\学习资料");
        FileUtil.walkFiles(dir,file -> {
            String suffix = FileUtil.getSuffix(file);
            if (suffixs.contains(suffix)) {
                BookInfo bookInfo = new BookInfo();
                bookInfo.setPath(file.getPath());
                bookInfo.setName(file.getName());
                bookInfo.setSize(FileUtil.size(file)/1024);
                bookInfo.setSuffix(suffix);
                bookInfoMapper.insert(bookInfo);
            }
        });
    }

    public void init1(){
        File dir = new File("F:\\同步文件夹\\学习资料");
        saveFiles(dir.listFiles());
    }

    public void saveFiles(File[] files ){
        List<BookInfo> bookInfoList = new ArrayList<>();
        for (File file : files) {
            String suffix = FileUtil.getSuffix(file);
            if (suffixs.contains(suffix)) {
                BookInfo bookInfo = new BookInfo();
                bookInfo.setPath(file.getPath());
                bookInfo.setName(file.getName());
                bookInfo.setSize(FileUtil.size(file)/1024);
                bookInfo.setSuffix(suffix);
                bookInfoList.add(bookInfo);
            }
            if (file.isDirectory()) {
                saveFiles(file.listFiles());
            }
        }
    }
}
