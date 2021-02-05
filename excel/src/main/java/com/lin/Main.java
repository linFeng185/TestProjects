package com.lin;

import com.lin.export.Entity;
import com.lin.util.ExcelUtil;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

/**
 * @Author: lin
 * @Date: 2021/1/29 17:06
 */
@RestController
public class Main {

    /**
     * 导出
     * @return
     */
    @GetMapping
    public String export() throws IllegalAccessException {
        ExcelUtil<Entity> util=new ExcelUtil<>(Entity.class);
        List<Entity> data=new ArrayList<>();
        for (int i=0;i<1000000;i++){
            Entity entity=new Entity();
            entity.setAge(i);
            entity.setBirthday(LocalDate.now());
            entity.setBodyWeight((double) i);
            entity.setName("名称"+i);
            entity.setSex(i%2);
            entity.setTime(LocalDateTime.now());
            data.add(entity);
        }
        System.out.println(LocalDateTime.now());
        util.export(data,"111.xlsx","标题");
        System.out.println(LocalDateTime.now());
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
