package com.lin.util;

import com.lin.annotation.Excel;
import org.apache.poi.ss.usermodel.*;
import org.apache.poi.ss.util.CellRangeAddress;
import org.apache.poi.xssf.streaming.SXSSFCell;
import org.apache.poi.xssf.streaming.SXSSFRow;
import org.apache.poi.xssf.streaming.SXSSFSheet;
import org.apache.poi.xssf.streaming.SXSSFWorkbook;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.lang.reflect.Field;
import java.net.URLEncoder;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * excel工具类
 * @Author: lin
 * @Date: 2021/1/29 17:15
 */
public class ExcelUtil <T>{

    /**
     * 导入时从该值开始读取数据
     */
    private static final int BEGIN_INDEX =1;

    /**
     * 实体类型
     */
    private Class<T> clazz;

    /**
     * 工作蒲对象
     */
    private SXSSFWorkbook wb;

    /**
     * 工作表对象
     */
    private SXSSFSheet sheet;

    /**
     * 样式
     */
    private Map<String, CellStyle> style;

    /**
     * 标题合并单元格的数量
     */
    private int titleMergeCellNum;

    /**
     * sheet的最大行数
     */
    private static final int SHEET_MAX_NUM=65536;

    /**
     * excel中不显示的字段
     */
    private Map<String,String> judgeStrMap=new HashMap<>();

    /**
     * 数据导入/导出时开始的行数
     */
    private final static int DATA_BEGIN_INDEX=2;

    /**
     * 存储字段和注解的集合
     */
    private List<Object[]> fieldAndExcelList;

    /**
     * 最大列高
     */
    private int height;


    public ExcelUtil(Class<T> clazz){
        this.clazz=clazz;
    }

    public void export(List<T> dataList,String fileName,String title) throws IllegalAccessException {
        export(ServletUtil.getRequest(),ServletUtil.getResponse(),dataList,fileName,"data",title);
    }

    public void export(HttpServletRequest request,HttpServletResponse response, List<T> dataList, String fileName, String sheetName,String title) throws IllegalAccessException {
        init(request,sheetName);
        //数据导入开始下标
        int rowBeginIndex=0;
        double sheetNum=Math.ceil(dataList.size()/SHEET_MAX_NUM);
        for (int i=0;i<=sheetNum;i++){
            if(i>0){
                sheet=wb.createSheet(sheetName+i);
                rowBeginIndex+=SHEET_MAX_NUM;
            }
            //创建标题
            if(titleMergeCellNum>0){
                sheet.addMergedRegion(new CellRangeAddress(0,0,0, titleMergeCellNum));
            }
            SXSSFRow titleRow=createRow(0);
            titleRow.setHeight((short)(20*20));
            SXSSFCell titleCell=titleRow.createCell(0);
            titleCell.setCellValue(title);
            titleCell.setCellStyle(style.get("title"));
            //创建字段行
            SXSSFRow fieldRow=createRow(1);
            //单元格下标
            int listSize= fieldAndExcelList.size();
            CellStyle fieldStyle=style.get("field");
            for (int i2=0;i2<listSize;i2++){
                Excel excelObj=(Excel) fieldAndExcelList.get(i2)[1];
                if(judgeStrMap.get(excelObj.judgeStr())!=null){
                    continue;
                }
                createCell(fieldRow,i2,excelObj.name(),excelObj,fieldStyle);
                sheet.setColumnWidth(i2,excelObj.width()*256);
            }
            importData(dataList,rowBeginIndex);
        }
        ByteArrayOutputStream os = new ByteArrayOutputStream();
        OutputStream out=null;
        try {
            wb.write(os);
            byte[] bytes = os.toByteArray();
            response.addHeader("Content-Type", "application/vnd.openxmlformats-officedocument.spreadsheetml.sheet");
            response.setHeader("Content-Disposition", "attachment;filename=" + URLEncoder.encode(fileName, "utf-8"));
            out= response.getOutputStream();
            out.write(bytes);
        } catch (IOException e) {
            e.printStackTrace();
        }finally {
            try {
                wb.close();
                if(out!=null){
                    out.close();
                }
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }

    /**
     * 导入excel
     * @param inputStream 输入流
     * @return 结果集
     */
    public List<T>importExcel(InputStream inputStream){
        return importExcel(inputStream, BEGIN_INDEX);
    }

    /**
     * 导入excel
     * @param inputStream 输入流
     * @param beginIndex 读取数据的开始下标
     * @return 结果集
     */
    public List<T>importExcel(InputStream inputStream,int beginIndex){
        List<T> s=new ArrayList<>();
        return s;
    }

    private void importData(List<T> dataList,int beginIndex) throws IllegalAccessException {
        int dataListSize=dataList.size();
        //获取数据单元格样式
        CellStyle dataStyle=style.get("data");
        //数据行开始下标
        int rowIndex=DATA_BEGIN_INDEX;
        //字段集合长度（用来创建列）
        int fieldListLength= fieldAndExcelList.size();
        for (int i=beginIndex;i<dataListSize && i<beginIndex+SHEET_MAX_NUM;i++,rowIndex++){
            T dataObj=dataList.get(i);
            SXSSFRow row=createRow(rowIndex);
            for (int i2=0;i2<fieldListLength;i2++){
                Field field=(Field) fieldAndExcelList.get(i2)[0];
                field.setAccessible(true);
                Object object=field.get(dataObj);
                createCell(row,i2,object,(Excel) fieldAndExcelList.get(i2)[1],dataStyle);
            }
        }
    }

    private void init(HttpServletRequest request,String sheetName){
        createWorkbook();
        createSheet(sheetName);
        createStyle();
        Field [] fields=clazz.getDeclaredFields();
        //合并单元格时，由于是从0开始合并的，需要判断出第一个不用进行加减操作
        boolean isTitleMergeCellNum=true;
        fieldAndExcelList =new ArrayList<>();
        for (Field field : fields) {
            Excel excel = field.getAnnotation(Excel.class);
            if (excel == null) {
                continue;
            }
            height=excel.height()>height?excel.height():height;
            //判断是否为可选字段
            if (!excel.isOptional()) {
                fieldAndExcelList.add(new Object[]{field, excel});
                if (isTitleMergeCellNum) {
                    isTitleMergeCellNum = false;
                    continue;
                }
                titleMergeCellNum += 1;
                continue;
            }
            String judgesStrValue = request.getParameter(excel.judgeStr());
            if (judgesStrValue != null) {
                titleMergeCellNum -= 1;
                //如果-1后没有可显示的字段时，计数归0，判断条件重置
                if (titleMergeCellNum <= 0) {
                    titleMergeCellNum = 0;
                    isTitleMergeCellNum = true;
                }
                judgeStrMap.put(excel.judgeStr(), judgesStrValue);
                continue;
            }
            fieldAndExcelList.add(new Object[]{field, excel});
            titleMergeCellNum += 1;
        }
    }

    private void createWorkbook(){
        wb = new SXSSFWorkbook(300);
    }

    private void createSheet(String sheetName){
        sheet=wb.createSheet(sheetName);
    }

    private SXSSFRow createRow(int index){
        return sheet.createRow(index);
    }

    private void createCell(SXSSFRow row, int index,Object value,Excel excel,CellStyle cellStyle){
        row.setHeight((short) (height*20));
        SXSSFCell cell= row.createCell(index);
        typeConvert(cell,value,excel);
        cell.setCellStyle(cellStyle);
        //CellUtil.setCellStyleProperties(cell,null);
    }

    private void typeConvert(SXSSFCell cell,Object value,Excel excel){
        if(value instanceof String){
            cell.setCellValue((String) value);
        }
        if(value instanceof LocalDate){
            cell.setCellValue(((LocalDate)value).toString());
        }
        if(value instanceof LocalDateTime){
            cell.setCellValue(((LocalDateTime)value).toString());
        }
        if(value instanceof Double){
            cell.setCellValue((Double)value);
        }
        if(value instanceof Long){
            cell.setCellValue((Long)value);
        }
        if(value instanceof Integer){
            cell.setCellValue((Integer)value);
        }
        if(value instanceof Short){
            cell.setCellValue((Short)value);
        }
        if(value instanceof Float){
            cell.setCellValue((Float)value);
        }
        if(value instanceof Boolean){
            cell.setCellValue((Boolean)value);
        }
    }

    /**
     * 创建样式，具体方法参考文档：http://poi.apache.org/apidocs/dev/org/apache/poi/hssf/usermodel/HSSFCellStyle.html
     */
    private void createStyle(){
        style=new HashMap<>(3);
        CellStyle title=wb.createCellStyle();
        //设置填充色
        //设置边框样式
        setBorderStyle(title);
        //设置字体样式
        Font titleFont=wb.createFont();
        titleFont.setFontHeight((short)(16*20));
        titleFont.setFontName("Arial");
        title.setFont(titleFont);
        //设置对齐方式
        setBackgroundColorAndAlignment(title);
        //标题
        style.put("title",title);
        CellStyle field=wb.createCellStyle();
        setBorderStyle(field);
        //设置字体样式
        Font fieldFont=wb.createFont();
        fieldFont.setFontHeight((short)(12*20));
        fieldFont.setFontName("Arial");
        fieldFont.setBold(false);
        field.setFont(fieldFont);
        //设置对齐方式
        setBackgroundColorAndAlignment(field);
        //字段标题
        style.put("field",field);
        CellStyle data=wb.createCellStyle();
        setBorderStyle(data);
        DataFormat dataFormat =wb.createDataFormat();
        //默认为文本类型
        data.setDataFormat(dataFormat.getFormat("@"));
        //数据
        style.put("data",data);
    }

    /**
     * 设置边框样式
     * @param cellStyle
     */
    private void setBorderStyle(CellStyle cellStyle){
        cellStyle.setBorderBottom(BorderStyle.THIN);
        cellStyle.setBorderLeft(BorderStyle.THIN);
        cellStyle.setBorderRight(BorderStyle.THIN);
        cellStyle.setBorderTop(BorderStyle.THIN);
    }

    /**
     * 设置背景颜色和对齐方式
     * @param cellStyle
     */
    private void setBackgroundColorAndAlignment(CellStyle cellStyle){
        cellStyle.setAlignment(HorizontalAlignment.CENTER);
        cellStyle.setVerticalAlignment(VerticalAlignment.CENTER);
        cellStyle.setFillForegroundColor(IndexedColors.LIGHT_TURQUOISE.getIndex());
        cellStyle.setFillPattern(FillPatternType.SOLID_FOREGROUND);
    }
}
