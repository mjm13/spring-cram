package com.meijm.basis.controller;

import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestPart;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.IOException;

@Slf4j
@RestController
@RequestMapping("/upload")
public class UploadController {
    @PostMapping(value = "/file")
    public void uploadFile(@RequestPart(value = "file", required = false) MultipartFile file) throws IOException {
        File temp = new File(file.getOriginalFilename());
        file.transferTo(temp);
        log.info("path:{}",temp.getAbsolutePath());
    }
}
