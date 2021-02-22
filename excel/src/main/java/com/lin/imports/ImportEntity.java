package com.lin.imports;

import com.lin.annotation.Excel;
import com.lin.util.DateUtil;

import java.time.LocalDate;
import java.time.LocalDateTime;

/**
 * @Author: lin
 * @Date: 2021/2/5 9:51
 */
public class ImportEntity {
    /**
     * 名称
     */
    @Excel(name = "名字")
    private String name;

    /**
     * 年龄
     */
    @Excel(name = "年龄")
    private Integer age;

    /**
     * 性别
     */
    @Excel(name = "性别",valueConvert = "0:女,1:男,2:未知")
    private Integer sex;

    /**
     * 体重
     */
    @Excel(name = "体重")
    private Double bodyWeight;

    /**
     * 生日
     */
    @Excel(name = "生日",dateFormat = "yyyy-MM-dd")
    private LocalDate birthday;

    /**
     * 时间
     */
    @Excel(name = "时间",isOptional = true,judgeStr = "isTime",dateTimeFormat = DateUtil.YYYY_MM_DD_HH_MM)
    private LocalDateTime time;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Integer getAge() {
        return age;
    }

    public void setAge(Integer age) {
        this.age = age;
    }

    public Double getBodyWeight() {
        return bodyWeight;
    }

    public void setBodyWeight(Double bodyWeight) {
        this.bodyWeight = bodyWeight;
    }

    public LocalDate getBirthday() {
        return birthday;
    }

    public void setBirthday(LocalDate birthday) {
        this.birthday = birthday;
    }

    public LocalDateTime getTime() {
        return time;
    }

    public void setTime(LocalDateTime time) {
        this.time = time;
    }

    public Integer getSex() {
        return sex;
    }

    public void setSex(Integer sex) {
        this.sex = sex;
    }
}
