package com.ftp.ftpupload;

import com.ftp.ftpupload.upload.ftp.property.FtpClientProperties;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.context.properties.EnableConfigurationProperties;

@SpringBootApplication
@EnableConfigurationProperties(FtpClientProperties.class)
public class FtpUploadApplication {

    public static void main(String[] args) {
        SpringApplication.run(FtpUploadApplication.class, args);
    }
}
