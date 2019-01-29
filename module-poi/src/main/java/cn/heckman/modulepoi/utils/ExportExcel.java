package cn.heckman.modulepoi.utils;

import org.apache.commons.lang.StringUtils;
import org.apache.poi.hssf.usermodel.*;

import java.lang.reflect.Field;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.text.SimpleDateFormat;
import java.util.*;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * 导出文件
 * Created by zhangxb on 2016/10/8.
 */
public class ExportExcel {
    /**
     * 这是一个通用的方法，利用了JAVA的反射机制，可以将放置在JAVA集合中并且符号一定条件的数据以EXCEL 的形式输出到指定IO设备上
     *
     * @param title   表格标题名
     * @param headers 表格属性列名数组
     * @param values  为空时安装bean对象字段顺序取值
     * @param dataset 需要显示的数据集合,集合中一定要放置符合javabean风格的类的对象。此方法支持的
     *                javabean属性的数据类型有基本数据类型及String,Date,byte[](图片数据)
     *                //     * @param out 与输出设备关联的流对象，可以将EXCEL文档导出到本地文件或者网络中
     * @param pattern 如果有时间数据，设定输出格式。默认为"yyy-MM-dd HH:mm:ss"
     */
    public static <T> HSSFWorkbook exportExcel(String title, String one, String[] headers, String[] values, Collection<T> dataset, String pattern, String money) throws NoSuchMethodException, InvocationTargetException, IllegalAccessException {
        if (pattern == null) {
            pattern = "yyy-MM-dd HH:mm:ss";
        }
        // 声明一个工作薄
        HSSFWorkbook workbook = new HSSFWorkbook();
        // 生成一个表格
        HSSFSheet sheet = workbook.createSheet(title);
        // 设置表格默认列宽度为15个字节
        sheet.setDefaultColumnWidth((short) 20);

        //生成字体
        HSSFFont font = workbook.createFont();
        //设置颜色
//        font.setColor(HSSFFont.COLOR_RED);
        //设置加粗
        font.setBoldweight(HSSFFont.BOLDWEIGHT_BOLD);
        // 生成一个样式
        HSSFCellStyle style = workbook.createCellStyle();
        //将字体添加到样式中
        style.setFont(font);
        int index = 0;
        if (StringUtils.isNotEmpty(one)) {
            HSSFRow row = sheet.createRow(index);
            HSSFCell cell = row.createCell(0);
            cell.setCellStyle(style);
            HSSFRichTextString text = new HSSFRichTextString(one);
            cell.setCellValue(text);
            index++;
        }
        // 产生表格标题行
        HSSFRow row = sheet.createRow(index);
        for (short i = 0; i < headers.length; i++) {
            HSSFCell cell = row.createCell(i);
            cell.setCellStyle(style);
            HSSFRichTextString text = new HSSFRichTextString(headers[i]);
            cell.setCellValue(text);
        }

        // 遍历集合数据，产生数据行
        Iterator<T> it = dataset.iterator();

        while (it.hasNext()) {
            index++;
            row = sheet.createRow(index);
            T t = (T) it.next();
            // 利用反射，根据javabean属性的先后顺序，动态调用getXxx()方法得到属性值
            String[] objs = null;
            if (values != null && values.length > 0) {
                objs = values;
            } else {
                Field[] fields = t.getClass().getDeclaredFields();
                objs = new String[fields.length];
                for (int i = 0; i < fields.length; i++) {
                    objs[i] = fields[i].getName();
                }
            }
            for (short i = 0; i < objs.length; i++) {
                HSSFCell cell = row.createCell(i);
                cell.setCellType(HSSFCell.CELL_TYPE_STRING);
//                cell.setCellStyle(style2);
                String fieldName = objs[i];
                String textValue = null;
                if (fieldName.indexOf(":") != -1) {
                    String[] s1 = fieldName.split(":");
                    if (s1[1].indexOf("=") != -1) {
                        textValue = getKeyValue(s1[1], "=", getValue(pattern, t, s1[0]));
                    } else if (s1[1].indexOf("-") != -1) {
                        textValue = getValue(pattern, t, getKeyValue(s1[1], "-", getValue(pattern, t, s1[0])));
                    }
                } else {
                    textValue = getValue(pattern, t, fieldName);
                }
                // 如果不是图片数据，就利用正则表达式判断textValue是否全部由数字组成
                if (textValue != null) {
//                    Pattern p = Pattern.compile("^[-+]?\\d+(.\\d+)?$");
//                    Matcher matcher = p.matcher(textValue.trim());
//                    matcher.matches();
                    if (StringUtils.isNotEmpty(money) && ("," + money + ",").indexOf("," + fieldName + ",") != -1) {
                        cell.setCellValue(Double.parseDouble(textValue));
                    } else {
                        cell.setCellValue(textValue);
                    }
                }
            }
        }
        return workbook;
//        workbook.write(out);
    }

    private static String getKeyValue(String str, String type, String key) {
        Map<String, String> map = new HashMap();
        String[] s2 = str.split(";");
        for (int i = 0; i < s2.length; i++) {
            String[] temp = s2[i].split(type);
            map.put(temp[0], temp[1]);
        }
        return map.get(key);
    }

    private static <T> String getValue(String pattern, T t, String fieldName) throws NoSuchMethodException, IllegalAccessException, InvocationTargetException {
        String getMethodName = "get" + fieldName.substring(0, 1).toUpperCase() + fieldName.substring(1);
        Class tCls = t.getClass();
        Method getMethod = tCls.getMethod(getMethodName, new Class[]{});
        Object value = getMethod.invoke(t, new Object[]{});

        // 判断值的类型后进行强制类型转换
        String textValue = null;
        if (value instanceof Date) {
            Date date = (Date) value;
            SimpleDateFormat sdf = new SimpleDateFormat(pattern);
            textValue = sdf.format(date);
        } else {
            if (StringUtils.isNotEmpty((String) value)) {
//其它数据类型都当作字符串简单处理
                textValue = value.toString();
            } else {
                textValue = "";
            }

        }
        return textValue;
    }

    public static void main(String[] args) {
        Pattern p = Pattern.compile("^[-+]?\\d+(.\\d+)?$");
        String test = "1000.0";
        Matcher matcher = p.matcher(test);
        if (matcher.matches()) {
            System.out.println(Double.parseDouble(test));
        } else {
            System.out.println(false);
        }
        String money = "cost";
        String fieldName = "cost";
        if (StringUtils.isNotEmpty(money) && ("," + money + ",").indexOf("," + fieldName + ",") != -1) {
            System.out.println(true);
        } else {
            System.out.println(false);
        }
    }
}
