package com.lin.enums;

/**
 * @Author: lin
 * @Date: 2021/1/29 17:33
 */
public enum  ExcelType {

    /**
     * 类型：0导出，1导入，2导入导出
     */
    IS_EXPORT(0)
    ,IS_IMPORT(1)
    ,ALL(2);

    private final int val;

    ExcelType(int val){
        this.val=val;
    }

    public int getVal() {
        return val;
    }
}
