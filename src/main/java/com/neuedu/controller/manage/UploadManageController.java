package com.neuedu.controller.manage;

import com.neuedu.common.ServerReponse;
import com.neuedu.service.IProductService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

@Controller
@RequestMapping(value = "/manage/product/")
public class UploadManageController {

    @Autowired
    IProductService iProductService;

    @RequestMapping(value = "upload",method = RequestMethod.GET)
    public String upload1(){
        return "upload";//返回的是逻辑视图    路径：前缀+逻辑视图+后缀
    }

    @RequestMapping(value = "upload",method = RequestMethod.POST)
    @ResponseBody
    public ServerReponse upload2(@RequestParam(value = "upload_file",required = false) MultipartFile file){
        String path = "D:\\20181203郭扬老师讲课\\upload";
        return iProductService.upload(file,path);
    }

}
