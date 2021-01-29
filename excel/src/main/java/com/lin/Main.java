package com.lin;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

/**
 * @Author: linFeng
 * @Date: 2021/1/29 17:06
 * @Copyright: www.zektech.cn
 */
@RestController
public class Main {

    /**
     * 导出
     * @return
     */
    @GetMapping
    public String export(){
        return "SUCCESS";
    }

    /**
     * 导入数据
     * @param file 文件
     * @return
     */
    @PostMapping
    public String importData(MultipartFile file){
        return "SUCCESS";
    }
}
