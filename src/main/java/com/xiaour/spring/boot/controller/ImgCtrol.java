package com.xiaour.spring.boot.controller;

import java.io.File;
import java.util.HashMap;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import com.xiaour.spring.boot.config.BaseController;
import com.xiaour.spring.boot.config.ResultModel;

import io.swagger.annotations.Api;


@RestController
@RequestMapping(value="/img")
@Api(description="图片")
public class ImgCtrol extends BaseController{
	
	private String nginx = "/usr/local/zfb/page/dist/build";
	
	private String upload = "/upload";
	
	@PostMapping("/upload")
    public ResultModel uplaod(HttpServletRequest req, @RequestParam("file") MultipartFile file) {
		String destFileName = "";
		String re = "";
        try {
            //1.根据时间戳创建新的文件名，这样即便是第二次上传相同名称的文件，也不会把第一次的文件覆盖了
            String fileName = System.currentTimeMillis() + "_" +file.getOriginalFilename();
            //2.通过req.getServletContext().getRealPath("") 获取当前项目的真实路径，然后拼接前面的文件名
             destFileName = nginx + upload + File.separator + fileName;
            //3.第一次运行的时候，这个文件所在的目录往往是不存在的，这里需要创建一下目录（创建到了webapp下uploaded文件夹下）
             re = upload + File.separator + fileName;
             File destFile = new File(destFileName);
            file.transferTo(destFile);
        }  catch (Exception e) {
            e.printStackTrace();
            return error(e.getMessage());
        }
        Map<String,String> resp = new HashMap<String,String>();
        resp.put("url", re);
        return ok(resp);
    }
}
