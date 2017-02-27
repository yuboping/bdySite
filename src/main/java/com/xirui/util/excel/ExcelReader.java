package com.xirui.util.excel;

import java.io.IOException;
import java.io.InputStream;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.poi.hssf.usermodel.HSSFCell;
import org.apache.poi.hssf.usermodel.HSSFDateUtil;
import org.apache.poi.hssf.usermodel.HSSFRow;
import org.apache.poi.hssf.usermodel.HSSFSheet;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.poifs.filesystem.POIFSFileSystem;

public class ExcelReader {

	private static POIFSFileSystem fs;
	private static HSSFWorkbook wb;
	private static HSSFSheet sheet;
	private static HSSFRow row;

	/**
	 * 
	 * <p>
	 * Description:读取Excel数据内容
	 * </p>
	 * 
	 * @author yuboping@new4g.cn
	 * @date 2016年5月12日上午11:03:34
	 * @param is
	 *            :InputStream
	 * @param s
	 *            :String[] 对应的key值(顺序排列)
	 * @return
	 */
	public static List<Map<String, Object>> readExcelContent(InputStream is,
			String[] s) {
		List<Map<String, Object>> content = new ArrayList<Map<String, Object>>();
		String str = "";
		try {
			fs = new POIFSFileSystem(is);
			wb = new HSSFWorkbook(fs);
		} catch (IOException e) {
			e.printStackTrace();
		}
		sheet = wb.getSheetAt(0);
		row = sheet.getRow(0);
		// 得到总行数
		int rowNum = sheet.getLastRowNum();
		// 标题总列数
		int colNum = row.getPhysicalNumberOfCells();
		String[] title = new String[colNum];
		for (int i = 0; i < colNum; i++) {
			title[i] = getCellFormatValue(row.getCell((short) i));
		}
		// 正文内容应该从第二行开始,第一行为表头的标题
		for (int i = 1; i <= rowNum; i++) {
			Map<String, Object> map = new HashMap<String, Object>();
			row = sheet.getRow(i);
			int j = 0;
			while (j < colNum) {
				String cell = getCellFormatValue(row.getCell((short) j)).trim();
//				System.out.println(s[j] + " :" + cell);
				map.put(s[j], cell);
				if (cell == "") {
					j++;
					continue;
				}
				str += cell + " ";
				j++;
			}
			// System.out.println("------");
			content.add(map);
			str.trim();
			str = "";
		}
		return content;
	}

	/**
	 * 读取Excel表格表头的内容
	 * 
	 * @param InputStream
	 * @return String 表头内容的数组
	 */
	public static String[] readExcelTitle(InputStream is) {
		try {
			fs = new POIFSFileSystem(is);
			wb = new HSSFWorkbook(fs);
		} catch (IOException e) {
			e.printStackTrace();
		}
		sheet = wb.getSheetAt(0);
		row = sheet.getRow(0);
		// 标题总列数
		int colNum = row.getPhysicalNumberOfCells();
		String[] title = new String[colNum];
		for (int i = 0; i < colNum; i++) {
			title[i] = getCellFormatValue(row.getCell((short) i));
		}
		return title;
	}

	/**
	 * 获取单元格数据内容为字符串类型的数据
	 * 
	 * @param cell
	 *            Excel单元格
	 * @return String 单元格数据内容
	 */
	/*
	 * private String getStringCellValue(HSSFCell cell) { String strCell = "";
	 * switch (cell.getCellType()) { case HSSFCell.CELL_TYPE_STRING: strCell =
	 * cell.getStringCellValue(); break; case HSSFCell.CELL_TYPE_NUMERIC:
	 * strCell = String.valueOf(cell.getNumericCellValue()); break; case
	 * HSSFCell.CELL_TYPE_BOOLEAN: strCell =
	 * String.valueOf(cell.getBooleanCellValue()); break; case
	 * HSSFCell.CELL_TYPE_BLANK: strCell = ""; break; default: strCell = "";
	 * break; } if (strCell.equals("") || strCell == null) { return ""; } if
	 * (cell == null) { return ""; } return strCell; }
	 */

	/**
	 * 获取单元格数据内容为日期类型的数据
	 * 
	 * @param cell
	 *            Excel单元格
	 * @return String 单元格数据内容
	 */
	// private String getDateCellValue(HSSFCell cell) {
	// String result = "";
	// try {
	// int cellType = cell.getCellType();
	// if (cellType == HSSFCell.CELL_TYPE_NUMERIC) {
	// Date date = cell.getDateCellValue();
	// result = (date.getYear() + 1900) + "-" + (date.getMonth() + 1)
	// + "-" + date.getDate();
	// } else if (cellType == HSSFCell.CELL_TYPE_STRING) {
	// String date = getStringCellValue(cell);
	// result = date.replaceAll("[年月]", "-").replace("日", "").trim();
	// } else if (cellType == HSSFCell.CELL_TYPE_BLANK) {
	// result = "";
	// }
	// } catch (Exception e) {
	// //System.out.println("日期格式不正确!");
	// e.printStackTrace();
	// }
	// return result;
	// }

	/**
	 * 根据HSSFCell类型设置数据
	 * 
	 * @param cell
	 * @return
	 */
	private static String getCellFormatValue(HSSFCell cell) {
		String cellvalue = "";
		if (cell != null) {
			// 判断当前Cell的Type
			switch (cell.getCellType()) {
			// 如果当前Cell的Type为NUMERIC
			case HSSFCell.CELL_TYPE_NUMERIC: {
				cellvalue = String.valueOf(cell.getNumericCellValue());
				break;
			}
			case HSSFCell.CELL_TYPE_FORMULA: {
				// 判断当前的cell是否为Date
				if (HSSFDateUtil.isCellDateFormatted(cell)) {
					// 如果是Date类型则，转化为Data格式

					// 方法1：这样子的data格式是带时分秒的：2011-10-12 0:00:00
					// cellvalue = cell.getDateCellValue().toLocaleString();

					// 方法2：这样子的data格式是不带带时分秒的：2011-10-12
					Date date = cell.getDateCellValue();
					SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
					cellvalue = sdf.format(date);

				}
				// 如果是纯数字
				else {
					// 取得当前Cell的数值
					cellvalue = String.valueOf(cell.getNumericCellValue());
				}
				break;
			}
			// 如果当前Cell的Type为STRIN
			case HSSFCell.CELL_TYPE_STRING:
				// 取得当前的Cell字符串
				cellvalue = cell.getRichStringCellValue().getString();
				break;
			// 默认的Cell值
			default:
				cellvalue = " ";
			}
		} else {
			cellvalue = "";
		}
		return cellvalue;

	}
}
