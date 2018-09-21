package com.ftp.ftpupload.upload.ftp.util;


import com.ftp.ftpupload.upload.ftp.property.FtpClientProperties;
import org.apache.commons.net.ftp.FTP;
import org.apache.commons.net.ftp.FTPClient;
import org.apache.commons.net.ftp.FTPClientConfig;
import org.apache.commons.net.ftp.FTPFile;
import org.apache.commons.net.ftp.FTPReply;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.util.Arrays;

@Component
public class FtpUtils {
    @Autowired
    private FtpClientProperties ftpClientProperties;

    /**
     * 判断是否有重名文件
     *
     * @param fileName
     * @param fs
     * @return
     */
    public static boolean isFileExist(String fileName, FTPFile[] fs) {
        //将FTPFile数组转成流,任意文件名称和fileName相同,返回true.表示有重名文件.
        return Arrays.stream(fs).anyMatch(ftpFile -> ftpFile.getName().equals(fileName));
    }

    /**
     * 根据重名判断的结果 生成新的文件的名称
     *
     * @param fileName
     * @param fs
     * @return
     */
    public static String changeName(String fileName, FTPFile[] fs) {
        int n = 0;
        while (isFileExist(fileName, fs)) {
            n++;
            String a = "[" + n + "]";
            //最后一出现小数点的位置
            int b = fileName.lastIndexOf(".");
            //最后一次"["出现的位置
            int c = fileName.lastIndexOf("[");
            if (c < 0) {
                c = b;
            }
            //文件的名字
            StringBuilder name = new StringBuilder(fileName.substring(0, c));
            //后缀的名称
            StringBuilder suffix = new StringBuilder(fileName.substring(b + 1));
            fileName = name.append(a) + "." + suffix;
        }
        return fileName;
    }

    /**
     * @param args
     * @throws FileNotFoundException 测试程序
     */
    public static void main(String[] args) throws FileNotFoundException {

        String path = "服务器地址";
        File f1 = new File("D:\\a.txt");
        String filename = f1.getName();
        System.out.println(filename);
        FtpUtils a = new FtpUtils();
        InputStream input = new FileInputStream(f1);
        FTPClient ftp = a.getConnectionFTP();
        a.uploadFile(ftp, path, filename, input);
        a.closeFTP(ftp);
    }

    /**
     * 获得连接FTP方式
     *
     * @param hostname FTP服务器地址
     * @param port     FTP服务器端口
     * @param username FTP登录用户名
     * @param password FTP登录密码
     * @return FTPClient
     */
    public FTPClient getConnectionFTP() {
        //创建FTPClient对象
        FTPClient ftp = new FTPClient();
        ftp.setConnectTimeout(10000);
        try {
            //连接FTP服务器
            ftp.connect(ftpClientProperties.getIp(), ftpClientProperties.getPort());
            //下面三行代码必须要，而且不能改变编码格式，否则不能正确下载中文文件
            ftp.setControlEncoding("GBK");
            FTPClientConfig conf = new FTPClientConfig(FTPClientConfig.SYST_NT);
            conf.setServerLanguageCode("zh");
            //登录ftp
            ftp.login(ftpClientProperties.getUserName(), ftpClientProperties.getPassword());
            if (!FTPReply.isPositiveCompletion(ftp.getReplyCode())) {
                ftp.disconnect();
                System.out.println("连接服务器失败");
            }
            System.out.println("登陆服务器成功");
        } catch (IOException e) {
            e.printStackTrace();
        }
        return ftp;
    }

    /**
     * 关闭连接FTP方式
     *
     * @param ftp FTPClient对象
     * @return boolean
     */
    public boolean closeFTP(FTPClient ftp) {
        if (ftp.isConnected()) {
            try {
                ftp.disconnect();
                System.out.println("ftp已经关闭");
                return true;
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
        return false;
    }

    public boolean uploadFile(String path, String fileName, InputStream inputStream) {
        FTPClient ftpClient = getConnectionFTP();
        return uploadFile(ftpClient, path, fileName, inputStream);
    }

    /**
     * 上传文件FTP方式
     *
     * @param ftp         FTPClient对象
     * @param path        FTP服务器上传地址
     * @param filename    本地文件路径
     * @param inputStream 输入流
     * @return boolean
     */
    public boolean uploadFile(FTPClient ftp, String path, String fileName, InputStream inputStream) {
        boolean success = false;
        try {
            //转移到指定FTP服务器目录
            changeDirectory(ftp, path);
            //得到目录的相应文件列表
            FTPFile[] fs = ftp.listFiles();
            fileName = FtpUtils.changeName(fileName, fs);
//            fileName = new String(fileName.getBytes("GBK"), StandardCharsets.ISO_8859_1);
//            path = new String(path.getBytes("GBK"), StandardCharsets.ISO_8859_1);
//            //转到指定上传目录
//            ftp.changeWorkingDirectory(path);
            //将上传文件存储到指定目录
            ftp.setFileType(FTP.BINARY_FILE_TYPE);
            //如果缺省该句 传输txt正常 但图片和其他格式的文件传输出现乱码
            ftp.storeFile(fileName, inputStream);
            //关闭输入流
            inputStream.close();
            //退出ftp
            ftp.logout();
            //表示上传成功
            success = true;
            System.out.println("上传成功。。。。。。");
        } catch (Exception e) {
            e.printStackTrace();
        }
        return success;
    }

    private void changeDirectory(FTPClient ftp, String path) {
        String[] pathArr = "\\".equals(File.separator) ? path.split("\\\\") : path.split(File.separator);
        Arrays.asList(pathArr).forEach(s -> {
            try {
                if (!ftp.changeWorkingDirectory(s)){
                    createFtpDirectory(ftp,s);
                    ftp.changeWorkingDirectory(s);
                }
            } catch (IOException e) {
                e.printStackTrace();
            }
        });
    }

    private void createFtpDirectory(FTPClient ftp, String path){
        try {
            ftp.makeDirectory(path);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    /**
     * 删除文件FTP方式
     *
     * @param ftp      FTPClient对象
     * @param path     FTP服务器上传地址
     * @param filename FTP服务器上要删除的文件名
     * @return
     */
    public boolean deleteFile(FTPClient ftp, String path, String fileName) {
        boolean success = false;
        try {
            ftp.changeWorkingDirectory(path);//转移到指定FTP服务器目录
            fileName = new String(fileName.getBytes("GBK"), "ISO8859-1");
            path = new String(path.getBytes("GBK"), "ISO8859-1");
            ftp.deleteFile(fileName);
            ftp.logout();
            success = true;
        } catch (Exception e) {
            e.printStackTrace();
        }
        return success;
    }

    /**
     * 上传文件FTP方式
     *
     * @param ftp       FTPClient对象
     * @param path      FTP服务器上传地址
     * @param fileName  本地文件路径
     * @param localPath 本里存储路径
     * @return boolean
     */
    public boolean downFile(FTPClient ftp, String path, String fileName, String localPath) {
        boolean success = false;
        try {
            ftp.changeWorkingDirectory(path);//转移到FTP服务器目录
            FTPFile[] fs = ftp.listFiles(); //得到目录的相应文件列表
            for (FTPFile ff : fs) {
                if (ff.getName().equals(fileName)) {
                    File localFile = new File(localPath + "\\" + ff.getName());
                    OutputStream outputStream = new FileOutputStream(localFile);
                    //将文件保存到输出流outputStream中
                    ftp.retrieveFile(new String(ff.getName().getBytes("GBK"), "ISO8859-1"), outputStream);
                    outputStream.flush();
                    outputStream.close();
                    System.out.println("下载成功");
                }
            }
            ftp.logout();
            success = true;
        } catch (Exception e) {
            e.printStackTrace();
        }
        return success;
    }

}
