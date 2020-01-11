package com.yfc.my.shop.web.admin.web.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpServletRequest;
import java.io.File;
import java.io.IOException;
import java.util.*;

/**
 * 文件上传控制器
 * <p>title:UploadController</p>
 * <p>description:</p>
 *
 *@author yfc
 *@version 1.0.0
 *@date 2020/1/1 22:09
*/
@Controller
public class UploadController {
    public static final String UPLOAD_PATH="/static/upload/";
    /**
     * 文件上传
     * @dropFile dropzone上传的文件
     * @editorFile wangEditro上传的文件
     * @return
     */
    @ResponseBody
    @RequestMapping(value = "upload",method = RequestMethod.POST)
    public Map<String,Object> upload(MultipartFile dropFile,MultipartFile[] editorFiles, HttpServletRequest request)  {
        Map<String,Object> result = new HashMap<>();
        //如果是dropzone上传
        if(dropFile!=null){
            result.put("fileName",write(dropFile,request));
        }
        //wangEditor上传的文件
       if(editorFiles!=null&&editorFiles.length>0) {
           List<String> fileNames = new ArrayList<>();
           for (MultipartFile editorFile : editorFiles) {
               fileNames.add(write(editorFile,request));
           }
            result.put("errno",0);
            result.put("data",fileNames);
        }

        return result;
    }
    private String write(MultipartFile myFile,HttpServletRequest request){
        //获取文件名
        String fileName = myFile.getOriginalFilename();
        String fileSuffix = fileName.substring(fileName.lastIndexOf("."));

        //文件存放路径
        String filPath=request.getSession().getServletContext().getRealPath(UPLOAD_PATH);
        File file = new File(filPath);
        //如果文件不存在，则创建新文件夹
        if(!file.exists()){
            file.mkdir();
        }
        //将文件写入文件夹中
        file=new File(filPath, UUID.randomUUID()+fileSuffix);
        try {
            myFile.transferTo(file);
        } catch (IOException e) {
            e.printStackTrace();
        }
        /**
         * 返回文件完整路径
         * Scheme :服务器提供的协议如http/https
         * ServerName:服务器的名字，如localhost/域名/ip
         * ServerPort：服务器端口
         */
        String serverPath=request.getScheme()+"://"+request.getServerName()+":"+request.getServerPort();
        return  serverPath+UPLOAD_PATH+file.getName();
    }
}
