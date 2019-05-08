package com.haier.datamart.airflowsupport.remote;

import org.apache.commons.net.ftp.FTPClient;
import org.apache.commons.net.ftp.FTPReply;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.PropertySource;
import org.springframework.stereotype.Component;

import java.io.File;
import java.io.IOException;
import java.net.MalformedURLException;

/**
 * liuzhilong
 */
//@Component
//@ConfigurationProperties(prefix = "airflowsupport.remote.AirFlowFTPRemoteFileService")
public class AirFlowFTPRemoteFileService implements RemoteFileService {
    private String ftpIp;
    private int ftpPort;
    private String username;
    private String password;
    private String dirPath;


    private FTPClient ftpClientHolder;
    @Override
    public void saveFile(String targetFileName,File e) {

    }

    @Override
    public File getFile(String fileName) {
        return null;
    }

    /**
     * 初始化ftp服务器
     */
    public FTPClient initFtpClient() throws IOException {
        FTPClient ftpClient = new FTPClient();
        ftpClient.setControlEncoding("utf-8");
        System.out.println("connecting...ftp服务器:"+this.ftpIp+":"+this.ftpPort);
        ftpClient.connect(ftpIp, ftpPort); //连接ftp服务器
        ftpClient.login(username, password); //登录ftp服务器
        int replyCode = ftpClient.getReplyCode(); //是否成功登录服务器
        if(!FTPReply.isPositiveCompletion(replyCode)){
            System.out.println("connect failed...ftp服务器:"+this.ftpIp+":"+this.ftpPort);
        }
        System.out.println("connect successfu...ftp服务器:"+this.ftpIp+":"+this.ftpPort);
        return ftpClient;
    }


}
