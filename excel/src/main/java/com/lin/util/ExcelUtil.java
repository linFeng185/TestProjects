package com.lin.util;

import com.lin.annotation.Excel;
import com.lin.enums.ExcelType;
import com.lin.enums.FileName;
import org.apache.poi.ss.usermodel.*;
import org.apache.poi.ss.util.CellRangeAddress;
import org.apache.poi.xssf.streaming.SXSSFWorkbook;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.lang.reflect.Field;
import java.math.BigDecimal;
import java.net.URLEncoder;
import java.text.DecimalFormat;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.*;

/**
 * excel工具类
 * @Author: lin
 * @Date: 2021/1/29 17:15
 */
public class ExcelUtil <T>{

    /**
     * sheet的最大行数
     */
    private final static int SHEET_MAX_NUM=65536;

    /**
     * 数据导入/导出时开始的行数
     */
    private final static int DATA_BEGIN_INDEX=2;

    /**
     * 默认的工作表下标
     */
    private final static int DEFAULT_SHEET_INDEX=0;

    /**
     * 字段描述行下标
     */
    private final static int FIELD_ROW_INDEX =1;

    /**
     * 实体类型
     */
    private Class<T> clazz;

    /**
     * 工作蒲对象
     */
    private Workbook wb;

    /**
     * 工作表对象
     */
    private Sheet sheet;

    /**
     * 样式
     */
    private Map<String, CellStyle> style;

    /**
     * 标题合并单元格的数量
     */
    private int titleMergeCellNum;

    /**
     * excel中不显示的字段
     */
    private Map<String,String> judgeStrMap=new HashMap<>();

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

    /**
     * 导出excel
     * @param dataList
     * @param fileEnum
     * @throws IllegalAccessException
     */
    public void export(List<T> dataList, FileName fileEnum) throws IllegalAccessException {
        export(ServletUtil.getRequest(),ServletUtil.getResponse(),dataList,fileEnum);
    }

    /**
     * 导出excel
     * @param request
     * @param response
     * @param dataList
     * @param fileEnum
     * @throws IllegalAccessException
     */
    public void export(HttpServletRequest request,HttpServletResponse response, List<T> dataList,FileName fileEnum) throws IllegalAccessException {
        String sheetName=fileEnum.getSheetName();
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
            Row titleRow=createRow(0);
            titleRow.setHeight((short)(20*20));
            Cell titleCell=titleRow.createCell(0);
            titleCell.setCellValue(fileEnum.getTitleValue());
            titleCell.setCellStyle(style.get("title"));
            //创建字段行
            Row fieldRow=createRow(1);
            //单元格下标
            int listSize= fieldAndExcelList.size();
            CellStyle fieldStyle=style.get("field");
            for (int i2=0;i2<listSize;i2++){
                Excel excelObj=(Excel) fieldAndExcelList.get(i2)[1];
                if(judgeStrMap.get(excelObj.judgeStr())!=null){
                    continue;
                }
                Cell fieldCell=createCell(fieldRow,i2,fieldStyle);
                fieldCell.setCellValue(excelObj.name());
                //设置列宽
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
            response.setHeader("Content-Disposition", "attachment;filename=" + URLEncoder.encode(fileEnum.getFileName()+".xlsx", "utf-8"));
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
     * @param file 输入流
     * @return 结果集
     */
    public List<T>importExcel(MultipartFile file){
        try {
            return importExcel(file.getInputStream(), DATA_BEGIN_INDEX,DEFAULT_SHEET_INDEX);
        } catch (IOException e) {
            e.printStackTrace();
        }
        return null;
    }

    /**
     * 导入excel
     * @param inputStream 输入流
     * @param rowBeginIndex 读取数据的开始下标
     * @return 结果集
     */
    public List<T>importExcel(InputStream inputStream,int rowBeginIndex,int sheetIndex){

        List<T> res=new ArrayList<>();
        try {
            wb=WorkbookFactory.create(inputStream);
            sheet=wb.getSheetAt(sheetIndex);
            int rowNum=sheet.getPhysicalNumberOfRows();
            if(rowNum<=rowBeginIndex){
                throw new NullPointerException("数据不能为空");
            }
            //获取字段标题的一行数据
            Row headRow=sheet.getRow(FIELD_ROW_INDEX);
            int headCellNum=headRow.getPhysicalNumberOfCells();
            Map<String,Integer> headMap=new HashMap<>(headCellNum);
            //循环单元格，并取出字段名
            for (int i=0;i<headCellNum;i++){
                Cell headCell=headRow.getCell(i);
                headMap.put(String.valueOf(headCell.getStringCellValue()),i);
            }
            Field [] fields=clazz.getDeclaredFields();
            Map<Integer,Field>fieldMap=new HashMap<>(headCellNum);
            for (Field field:fields) {
                Excel excel=field.getAnnotation(Excel.class);
                if(excel.excelType()==ExcelType.IS_EXPORT){
                    continue;
                }
                //设置私有属性可以访问
                field.setAccessible(true);
                if (!headMap.containsKey(excel.name())){
                    continue;
                }
                fieldMap.put(headMap.get(excel.name()),field);
            }
            for (int i=rowBeginIndex;i<rowNum;i++){
                Row row=sheet.getRow(i);
                T t=clazz.newInstance();
                for (Map.Entry<Integer,Field>entry:fieldMap.entrySet()){
                    Cell cell=row.getCell(entry.getKey());
                    setEntityFieldValue(t,cell,entry.getValue());
                }
                res.add(t);
            }
        } catch (IOException  | InstantiationException | IllegalAccessException e) {
            e.printStackTrace();
        }
        return res;
    }

    /**
     * 导入数据到excel中
     * @param dataList
     * @param beginIndex
     * @throws IllegalAccessException
     */
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
            Row row=createRow(rowIndex);
            for (int i2=0;i2<fieldListLength;i2++){
                Field field=(Field) fieldAndExcelList.get(i2)[0];
                field.setAccessible(true);
                Object object=field.get(dataObj);
                Cell cell=createCell(row,i2,dataStyle);
                setCellValue(cell,object,(Excel) fieldAndExcelList.get(i2)[1]);
            }
        }
    }

    /**
     * 初始化
     * @param request
     * @param sheetName
     */
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
            if (excel == null||excel.excelType()== ExcelType.IS_IMPORT) {
                continue;
            }
            //获取单元格最大高度
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
                judgeStrMap.put(excel.judgeStr(), judgesStrValue);
                continue;
            }
            fieldAndExcelList.add(new Object[]{field, excel});
            titleMergeCellNum += 1;
        }
    }

    /**
     * 创建工作蒲
     */
    private void createWorkbook(){
        wb = new SXSSFWorkbook(300);
    }

    /**
     * 创建工作表
     * @param sheetName
     */
    private void createSheet(String sheetName){
        sheet=wb.createSheet(sheetName);
    }

    /**
     * 创建行
     * @param index
     * @return
     */
    private Row createRow(int index){
        return sheet.createRow(index);
    }

    /**
     * 创建单元格
     * @param row
     * @param index
     * @param cellStyle
     * @return
     */
    private Cell createCell(Row row, int index,CellStyle cellStyle){
        row.setHeight((short) (height*20));
        Cell cell= row.createCell(index);
        cell.setCellStyle(cellStyle);
        return cell;
    }

    /**
     * 设置单元格内容
     * @param cell
     * @param value
     * @param excel
     */
    private void setCellValue(Cell cell,Object value,Excel excel){
        if(value==null){
            cell.setCellValue(excel.defaultValue());
            return;
        }
        if("".equals(excel.valueConvert())){
            typeConvert(cell,value,excel);
            return;
        }
        cell.setCellValue((String) valueConvert(value,excel,true));
    }

    /**
     * 简单的类型转换
     * @param cell
     * @param value
     * @param excel
     */
    private void typeConvert(Cell cell,Object value,Excel excel){
        if(value instanceof String){
            cell.setCellValue((String) value);
        }else if(value instanceof LocalDate){
            if ("".equals(excel.dateFormat())){
                cell.setCellValue(DateUtil.localDateToStr((LocalDate)value));
                return;
            }
            cell.setCellValue(DateUtil.localDateToStr((LocalDate)value,excel.dateFormat()));
        }else if(value instanceof LocalDateTime){
            if ("".equals(excel.dateFormat())){
                cell.setCellValue(DateUtil.localDateTimeToStr((LocalDateTime)value));
                return;
            }
            cell.setCellValue(DateUtil.localDateTimeToStr((LocalDateTime)value,excel.dateTimeFormat()));
        }else if(value instanceof Double){
            cell.setCellValue((Double)value);
        }else if(value instanceof Long){
            cell.setCellValue((Long)value);
        }else if(value instanceof Integer){
            cell.setCellValue((Integer)value);
        }else if(value instanceof Short){
            cell.setCellValue((Short)value);
        }else if(value instanceof Float){
            cell.setCellValue((Float)value);
        }else if(value instanceof Boolean){
            cell.setCellValue((Boolean)value);
        }
    }

    /**
     * 值转换
     * @param fieldValue 值
     * @param excel
     * @param isExport 是否导出
     * @return
     */
    private Object valueConvert(Object fieldValue,Excel excel,boolean isExport){
        String value=excel.valueConvert();
        String [] values=value.split(",");
        String separate=":";
        for (String str:values){
            String [] valArr=str.split(separate);
            //如果是导出就返回:右边的值（例如男），否则返回左边的（例如1）
            if(isExport){
                if(valArr[0].equals(String.valueOf(fieldValue))){
                    return valArr[1];
                }
                continue;
            }
            if(valArr[1].equals(fieldValue)){
                return valArr[0];
            }
        }
        return "";
    }

    /**
     * 给实体字段赋值
     * @param t
     * @param cell
     * @param field
     */
    private void setEntityFieldValue(T t, Cell cell, Field field) {
        Object value=getCellValue(cell,field);
        ReflectUtils.invokeSetter(t,field.getName(),value);
    }

    /**
     * 获取单元格值
     * @param cell
     * @param field
     * @return
     */
    private Object getCellValue(Cell cell, Field field){
        Class<?> fieldType=field.getType();
        Object value;
        CellType cellType=cell.getCellType();
        if(cellType==CellType.NUMERIC){
            if(Double.class==fieldType||Float.class==fieldType||BigDecimal.class==fieldType){
                if(Double.class==fieldType){
                    value=cell.getNumericCellValue();
                }else if(Float.class==fieldType){
                    value=Float.valueOf(String.valueOf(cell.getNumericCellValue()));
                }else{
                    value=new BigDecimal(String.valueOf(cell.getNumericCellValue()));
                }
            }else{
                value=cell.getNumericCellValue();
                String val=new DecimalFormat("0").format(value);
                if(Integer.class==fieldType){
                    value=Integer.valueOf(val);
                }else if(Long.class==fieldType){
                    value=Long.valueOf(val);
                }else if(Short.class==fieldType){
                    value=Short.valueOf(val);
                }
            }
        }else if(cellType==CellType.STRING){
            if(String.class==fieldType){
                value=cell.getStringCellValue();
            }else if(LocalDate.class==fieldType){
                Excel excel=field.getAnnotation(Excel.class);
                value=DateUtil.strToLocalDate(cell.getStringCellValue(),excel.dateFormat());
            }else if(LocalDateTime.class==fieldType){
                Excel excel=field.getAnnotation(Excel.class);
                value=DateUtil.strToLocalDateTime(cell.getStringCellValue(),excel.dateTimeFormat());
            }else {
                Excel excel=field.getAnnotation(Excel.class);
                String val=cell.getStringCellValue();
                if("".equals(excel.valueConvert())){
                    value=val;
                }else {
                    value=valueConvert(val,excel,false);
                }
            }
        }else {
            value=null;
        }
        return value;
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
