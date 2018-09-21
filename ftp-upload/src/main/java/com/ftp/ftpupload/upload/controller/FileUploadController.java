package com.ftp.ftpupload.upload.controller;


import com.ftp.ftpupload.upload.ftp.util.FtpUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.IOException;
import java.time.LocalDate;
import java.util.UUID;

@RestController
@RequestMapping("/file")
public class FileUploadController {

    @Value("${nginx.path}")
    private String nginxPath;

    @Autowired
    private FtpUtils ftpUtils;

    @PostMapping("/upload")
    public String fileUpload(@RequestParam("file") MultipartFile file) throws IOException {
        LocalDate nowDate = LocalDate.now();
        String originalFileName = file.getOriginalFilename();
        String fileSuffix = originalFileName.substring(originalFileName.lastIndexOf(".") + 1);
        String fileName = UUID.randomUUID().toString().replaceAll("-", "") + "." + fileSuffix;
        String path = File.separator + fileSuffix.toLowerCase()
                + File.separator + nowDate.getYear()
                + File.separator + nowDate.getMonthValue()
                + File.separator + nowDate.getDayOfMonth();
        if (ftpUtils.uploadFile(path, fileName, file.getInputStream())) {
            return (nginxPath + path + File.separator + fileName).replaceAll("\\\\", "/");
        }
        return "failed";
    }
}
