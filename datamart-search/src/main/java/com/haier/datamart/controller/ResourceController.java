package com.haier.datamart.controller;

import java.util.ArrayList;
import java.util.List;

import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import com.haier.datamart.base.PublicResult;
import com.haier.datamart.base.PublicResultConstant;
import com.haier.datamart.utils.ComUtil;
import com.haier.datamart.utils.FileUtil;

/**
 * @author zuoqb
 * @since on 2018/5/11.
 */
@RestController
@RequestMapping("/api/resource")
public class ResourceController {

    @PostMapping
    public PublicResult uploadResource(@RequestParam("files")MultipartFile[] multipartFiles) throws Exception {
        List<String> filePaths = new ArrayList<>();
        if(!ComUtil.isEmpty(multipartFiles) && multipartFiles.length != 0) {
            for (MultipartFile multipartFile : multipartFiles) {
                int fileType =  FileUtil.getFileType(multipartFile.getOriginalFilename());
                filePaths.add(
                        FileUtil.saveFile(multipartFile.getInputStream(), fileType, multipartFile.getOriginalFilename(), null)
                );
            }
        }
        return new PublicResult<List>(PublicResultConstant.SUCCESS, filePaths);
    }

    @DeleteMapping
    public PublicResult deleteResource(@RequestParam("filePaths") List<String> filePaths){
        if(!ComUtil.isEmpty(filePaths) && filePaths.size() !=0){
            for (String item: filePaths) {
                if(!FileUtil.deleteUploadedFile(item)){
                    return new PublicResult<String>(PublicResultConstant.ERROR, null);
                }
            }
        }
        return new PublicResult<String>(PublicResultConstant.SUCCESS, null);
    }

}
