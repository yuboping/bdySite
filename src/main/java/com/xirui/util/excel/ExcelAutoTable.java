/**
 * 
 */
package com.xirui.util.excel;

import java.io.FileOutputStream;
import java.io.IOException;
import java.text.NumberFormat;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.poi.hssf.usermodel.HSSFCell;
import org.apache.poi.hssf.usermodel.HSSFCellStyle;
import org.apache.poi.hssf.usermodel.HSSFRichTextString;
import org.apache.poi.hssf.usermodel.HSSFRow;
import org.apache.poi.hssf.usermodel.HSSFSheet;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.ss.util.CellRangeAddress;

/**
 * <p>Title:ExcelAutoTable</p>
 * <p>Description:</p>
 * <p>Company:yuboping</p>
 * @author yuboping
 * @date 2016年7月6日下午3:16:53
 */
public class ExcelAutoTable {
	
	public static void main(String[] args) {
		List<Map<String,Object>> map = new ArrayList<Map<String,Object>>();
		
		 NumberFormat numberFormat = NumberFormat.getInstance();  
		  
	        // 设置精确到小数点后2位  
	  
	        numberFormat.setMaximumFractionDigits(2);  
	  
	        String result = numberFormat.format((float) 2 / (float) 6 * 100);
	        System.out.println(result);
	}
	
	public static void getData(String path,List<Map<String,Object>> clickSum,List<Map<String,Object>> buySum,
			List<Map<String,Object>> userSum,List<Map<String,Object>> dealerSum,List<Map<String,Object>> ranking,
			List<Map<String,Object>> sevenMap,List<Map<String,Object>> allMap) {
		HSSFWorkbook wb = new HSSFWorkbook();
		HSSFCellStyle style = wb.createCellStyle(); // 样式对象      
		style.setVerticalAlignment(HSSFCellStyle.VERTICAL_CENTER);// 垂直      
	    style.setAlignment(HSSFCellStyle.ALIGN_CENTER);// 水平 
		
		// 判断有几个sheet 
		List<String> sheet = sheet();
		
		for (int i = 0 ; i < sheet.size(); i++) {
			String name = sheet.get(i);
			if (i == 0) {
				sheet1(wb,style,name,clickSum);
			}
			if (i == 1) {
				String[] headers = {"时间段","账户险","儿童疫苗险","君安行","安居综合"};
				sheet2(wb,style,name,buySum,headers);
			}
			if (i== 2) {
				sheet3(wb,style,name,userSum);
			}
			if (i== 3) {
				String[] headers = {"排名","经销商名称","销售总笔数","销售总金额","账户险笔数","儿童疫苗险笔数","君安行笔数","安居综合笔数"};
				sheet4(wb,style,name,dealerSum,headers);
			}
			if (i== 4) {
				String[] headers = {"排名","当天","近7日汇总","全部"};
				sheet5(wb,style,name,ranking,headers,sevenMap,allMap);
			}
		}
		
		FileOutputStream os = null;
		    
		try {
			
			os = new FileOutputStream(path);
			wb.write(os);  
			
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			try {
				os.close();
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
		
		
	}
	
	public static void sheet1(HSSFWorkbook wb,HSSFCellStyle style,String name,List<Map<String,Object>> result) {
		HSSFSheet sheet = wb.createSheet(name);
		HSSFRow row = sheet.createRow(0);  
	    row.setHeight((short) 500);// 设定行的高度  
	    
	    // 创建一个Excel的单元格  
	    HSSFCell cell = row.createCell(0);
	    // 合并单元格(startRow，endRow，startColumn，endColumn)  
	    sheet.addMergedRegion(new CellRangeAddress(0, 1, 0, 0));
	    cell.setCellStyle(style);
	    cell.setCellValue("时间段");
	    HSSFCell cell1 = row.createCell(1);
	    cell1.setCellStyle(style);
	    cell1.setCellValue("首页");
	    
	    HSSFCell cell2 = row.createCell(2);
	    //合并单元格(startRow，endRow，startColumn，endColumn)  
	    sheet.addMergedRegion(new CellRangeAddress(0, 0, 2, 4));
	    cell2.setCellStyle(style);
	    cell2.setCellValue("账户险");
	    
	    HSSFCell cell3 = row.createCell(5);
	    sheet.addMergedRegion(new CellRangeAddress(0, 0, 5, 5+2));
	    cell3.setCellStyle(style);
	    cell3.setCellValue("儿童疫苗险");
	    
	    HSSFCell cell4 = row.createCell(8);
	    sheet.addMergedRegion(new CellRangeAddress(0, 0, 8, 8+2));
	    cell4.setCellStyle(style);
	    cell4.setCellValue("君安行");
	    
	    HSSFCell cell5 = row.createCell(11);
	    sheet.addMergedRegion(new CellRangeAddress(0, 0, 11, 11 + 2));
	    cell5.setCellStyle(style);
	    cell5.setCellValue("安居综合");
	    
	    
	    HSSFRow row1 = sheet.createRow(1);  // 第二行
	    row1.setHeight((short) 500);// 设定行的高度  
	    HSSFCell cd = row1.createCell(1);
	    cd.setCellStyle(style);
	    cd.setCellValue("首页");
	    
	    HSSFCell cd1 = row1.createCell(2);
	    cd1.setCellStyle(style);
	    cd1.setCellValue("详情页");
	    HSSFCell cd2 = row1.createCell(3);
	    cd2.setCellStyle(style);
	    cd2.setCellValue("购买页");
	    HSSFCell cd3 = row1.createCell(4);
	    cd3.setCellStyle(style);
	    cd3.setCellValue("查看购买转化率");
	    
	    HSSFCell cd4 = row1.createCell(5);
	    cd4.setCellStyle(style);
	    cd4.setCellValue("详情页");
	    HSSFCell cd5 = row1.createCell(6);
	    cd5.setCellStyle(style);
	    cd5.setCellValue("购买页");
	    HSSFCell cd6 = row1.createCell(7);
	    cd6.setCellStyle(style);
	    cd6.setCellValue("查看购买转化率");
	    
	    HSSFCell cd7 = row1.createCell(8);
	    cd7.setCellStyle(style);
	    cd7.setCellValue("详情页");
	    HSSFCell cd8 = row1.createCell(9);
	    cd8.setCellStyle(style);
	    cd8.setCellValue("购买页");
	    HSSFCell cd9 = row1.createCell(10);
	    cd9.setCellStyle(style);
	    cd9.setCellValue("查看购买转化率");
	    
	    HSSFCell cd10 = row1.createCell(11);
	    cd10.setCellStyle(style);
	    cd10.setCellValue("详情页");
	    HSSFCell cd11 = row1.createCell(12);
	    cd11.setCellStyle(style);
	    cd11.setCellValue("购买页");
	    HSSFCell cd12 = row1.createCell(13);
	    cd12.setCellStyle(style);
	    cd12.setCellValue("查看购买转化率");
	    if (null != result) { 
 			int index = 2;
 			for (Map<String, Object> map : result) {
 				row = sheet.createRow(index);
 				int i = 0;
 				for (Map.Entry<String, Object> entry : map.entrySet()) {
 					HSSFCell celle = row.createCell(i);
 					celle.setCellValue(entry.getValue().toString());
 					i++;
 				}
 				index++;
 			}
 		}
	}
	
	
	public static void sheet3(HSSFWorkbook wb,HSSFCellStyle style,String name,List<Map<String,Object>> result) {
		HSSFSheet sheet = wb.createSheet(name);
		HSSFRow row = sheet.createRow(0);  
	    row.setHeight((short) 500);// 设定行的高度  
	    
	    // 创建一个Excel的单元格  
	    HSSFCell cell = row.createCell(0);
	    // 合并单元格(startRow，endRow，startColumn，endColumn)  
	    sheet.addMergedRegion(new CellRangeAddress(0, 1, 0, 0));
	    cell.setCellStyle(style);
	    cell.setCellValue("时间段");
	    HSSFCell cell1 = row.createCell(1);
	    sheet.addMergedRegion(new CellRangeAddress(0, 1, 1, 1));
	    cell1.setCellStyle(style);
	    cell1.setCellValue("首页");
	    
	    HSSFCell cell2 = row.createCell(2);
	    //合并单元格(startRow，endRow，startColumn，endColumn)  
	    sheet.addMergedRegion(new CellRangeAddress(0, 0, 2, 3));
	    cell2.setCellStyle(style);
	    cell2.setCellValue("账户险");
	    
	    HSSFCell cell3 = row.createCell(4);
	    sheet.addMergedRegion(new CellRangeAddress(0, 0, 4, 4+1));
	    cell3.setCellStyle(style);
	    cell3.setCellValue("儿童疫苗险");
	    
	    HSSFCell cell4 = row.createCell(6);
	    sheet.addMergedRegion(new CellRangeAddress(0, 0, 6, 6+1));
	    cell4.setCellStyle(style);
	    cell4.setCellValue("君安行");
	    
	    HSSFCell cell5 = row.createCell(8);
	    sheet.addMergedRegion(new CellRangeAddress(0, 0, 8, 8 + 1));
	    cell5.setCellStyle(style);
	    cell5.setCellValue("安居综合");
	    
	    
	    HSSFRow row1 = sheet.createRow(1);  // 第二行
	    row1.setHeight((short) 500);// 设定行的高度  
	  /*  HSSFCell cd = row1.createCell(1);
	    cd.setCellStyle(style);
	    cd.setCellValue("首页");*/
	    HSSFCell cd1 = row1.createCell(2);
	    cd1.setCellStyle(style);
	    cd1.setCellValue("详情页");
	    HSSFCell cd2 = row1.createCell(3);
	    cd2.setCellStyle(style);
	    cd2.setCellValue("购买页");
	    HSSFCell cd4 = row1.createCell(4);
	    cd4.setCellStyle(style);
	    cd4.setCellValue("详情页");
	    HSSFCell cd5 = row1.createCell(5);
	    cd5.setCellStyle(style);
	    cd5.setCellValue("购买页");
	    HSSFCell cd7 = row1.createCell(6);
	    cd7.setCellStyle(style);
	    cd7.setCellValue("详情页");
	    HSSFCell cd8 = row1.createCell(7);
	    cd8.setCellStyle(style);
	    cd8.setCellValue("购买页");
	    HSSFCell cd10 = row1.createCell(8);
	    cd10.setCellStyle(style);
	    cd10.setCellValue("详情页");
	    HSSFCell cd11 = row1.createCell(9);
	    cd11.setCellStyle(style);
	    cd11.setCellValue("购买页");
	    // 遍历集合数据，产生数据行
	 	if (null != result) { 
	 		int index = 2;
 			for (Map<String, Object> map : result) {
 				row = sheet.createRow(index);
 				int i = 0;
 				for (Map.Entry<String, Object> entry : map.entrySet()) {
 					HSSFCell celle = row.createCell(i);
 					celle.setCellValue(entry.getValue().toString());
 					i++;
 				}
 				index++;
 			}
 		}
	}
	
	public static void sheet2(HSSFWorkbook wb,HSSFCellStyle style,String name,List<Map<String,Object>> result,String[] headers) {
		HSSFSheet sheet = wb.createSheet(name);
		HSSFRow row = sheet.createRow(0);  
	    row.setHeight((short) 500);// 设定行的高度  
		for (int i = 0; i < headers.length; i++) {
			HSSFCell cell = row.createCell(i);
			cell.setCellStyle(style);
			HSSFRichTextString text = new HSSFRichTextString(headers[i]);
			cell.setCellValue(text.toString());
		}
		// 遍历集合数据，产生数据行
		if (null != result) { 
			int index = 1;
 			for (Map<String, Object> map : result) {
 				row = sheet.createRow(index);
 				int i = 0;
 				for (Map.Entry<String, Object> entry : map.entrySet()) {
 					HSSFCell celle = row.createCell(i);
 					celle.setCellValue(entry.getValue().toString());
 					i++;
 				}
 				index++;
 			}
		}
	}
	
	public static void sheet4(HSSFWorkbook wb,HSSFCellStyle style,String name,List<Map<String,Object>> result,String[] headers) {
		HSSFSheet sheet = wb.createSheet(name);
		HSSFRow row = sheet.createRow(0);  
	    row.setHeight((short) 500);// 设定行的高度  
		for (int i = 0; i < headers.length; i++) {
			HSSFCell cell = row.createCell(i);
			cell.setCellStyle(style);
			HSSFRichTextString text = new HSSFRichTextString(headers[i]);
			cell.setCellValue(text.toString());
		}
		// 遍历集合数据，产生数据行
		if (null != result) { 
			int index = 1;
 			for (Map<String, Object> map : result) {
 				row = sheet.createRow(index);
 				int i = 0;
 				HSSFCell celle1 = row.createCell(i);
 				celle1.setCellValue(index);
 				for (Map.Entry<String, Object> entry : map.entrySet()) {
 					HSSFCell celle = row.createCell(i+1);
 					celle.setCellValue(entry.getValue().toString());
 					i++;
 				}
 				index++;
 			}
		}
	}
	
	public static void sheet5(HSSFWorkbook wb,HSSFCellStyle style,String name,List<Map<String,Object>> yestMap,
			String[] headers,List<Map<String,Object>> sevenMap,List<Map<String,Object>> allMap) {
		HSSFSheet sheet = wb.createSheet(name);
		HSSFRow row = sheet.createRow(0);  
	    row.setHeight((short) 500);// 设定行的高度  
		for (int i = 0; i < headers.length; i++) {
			HSSFCell cell = row.createCell(i);
			cell.setCellStyle(style);
			HSSFRichTextString text = new HSSFRichTextString(headers[i]);
			cell.setCellValue(text.toString());
		}
		// 遍历集合数据，产生数据行
			
		int index = 1;
//		row = sheet.createRow(index);
//		// 遍历集合数据，产生数据行
//		if (null != yestMap && yestMap.size() > 0) { 
// 			for (Map<String, Object> map : yestMap) {
// 				for (Map.Entry<String, Object> entry : map.entrySet()) {
// 					HSSFCell celle = row.createCell(1);
// 					celle.setCellValue(entry.getValue().toString());
// 				}
// 				index++;
// 			}
//		}
//		
//		// 遍历集合数据，产生数据行
//		if (null != sevenMap) { 
//			for (Map<String, Object> map : sevenMap) {
// 			//	row = sheet.createRow(index);
// 				for (Map.Entry<String, Object> entry : map.entrySet()) {
// 					HSSFCell celle = row.createCell(2);
// 					celle.setCellValue(entry.getValue().toString());
// 				}
// 			//	index++;
// 			}
//		}
		// 遍历集合数据，产生数据行 
		if (null != allMap) { 
 			for (int i = 0;i<allMap.size(); i++) {
 				Map<String,Object> all = allMap.get(i);
 				Map<String,Object> yest = null;
 				Map<String,Object> seven = null;
 				if (i <= yestMap.size() - 1) {
 					yest = yestMap.get(i);
 				}
 				if (i <= sevenMap.size() - 1 ) {
 					seven = sevenMap.get(i);
 				}
 				
 				row = sheet.createRow(index);
 				HSSFCell celle1 = row.createCell(0);
 				celle1.setCellValue(index);
 				
 				if (null != yest && !yest.isEmpty()) {
 					for (Map.Entry<String, Object> entry : yest.entrySet()) {
 	 					HSSFCell celle = row.createCell(1);
 	 					celle.setCellValue(entry.getValue().toString());
 	 				}
 				}
 				
 				if (null != seven && !seven.isEmpty()) {
 	 				for (Map.Entry<String, Object> entry : seven.entrySet()) {
 	 					HSSFCell celle = row.createCell(2);
 	 					celle.setCellValue(entry.getValue().toString());
 	 				}
 				}
 				
 				for (Map.Entry<String, Object> entry : all.entrySet()) {
 					HSSFCell celle = row.createCell(3);
 					celle.setCellValue(entry.getValue().toString());
 				}
 				
 				index++;
 			}
		}
		
	}
	
	public static List<String> sheet() {
		List<String> sheet = new ArrayList<String>();
		sheet.add("点击数统计");
		sheet.add("购买情况统计");
		sheet.add("点击进入的用户数统计");
		sheet.add("当日销售笔数前10经销商销售统计");
		sheet.add("重点经销商前10（按笔数排名）");
		return sheet;
	}
	
	public static void header() {
		List<Map<String,String>> row = new ArrayList<Map<String,String>>();
		Map<String,String> mc = new HashMap<String,String>();
		mc.put("1", "详情页");
		mc.put("2", "购买页");
		mc.put("3", "查看购买转化率");
		row.add(mc);
		
		Map<String,String> md = new HashMap<String,String>();
		md.put("pd1", "账户险");
		md.put("pd2", "儿童疫苗险");
		md.put("pd3", "君安行");
		md.put("pd4", "安居综合");
		row.add(md);
	}
}
