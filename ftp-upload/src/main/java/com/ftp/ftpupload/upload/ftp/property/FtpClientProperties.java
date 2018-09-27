package com.ftp.ftpupload.upload.ftp.property;

import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Configuration;
import org.springframework.stereotype.Component;

@Data
@Component
@Configuration
@ConfigurationProperties(prefix = "ftp")
public class FtpClientProperties {

    private String ip;

    private Integer port;

    private String userName;

    private String password;
}
