package com.haier.datamart.airflowsupport.remote;

/**
 * Created by long on 2018/11/12.
 */

import java.io.File;

/**
 * 用于airflow远程文件操作的
 */
public interface RemoteFileService {
    /**
     * 用于保存一条file信息
     * @param e
     */
    public void saveFile(String targetfileName,File e);

    /**
     * 获得一条文件信息
     * @param fileName
     * @return
     */
    public File getFile(String fileName);
}
