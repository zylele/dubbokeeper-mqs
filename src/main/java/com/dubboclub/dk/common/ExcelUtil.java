package com.dubboclub.dk.common;

import java.io.ByteArrayInputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import java.util.regex.PatternSyntaxException;

import org.apache.poi.hssf.usermodel.HSSFCellStyle;
import org.apache.poi.hssf.usermodel.HSSFFont;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.hssf.util.HSSFColor;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.CellStyle;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.ss.util.CellRangeAddress;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.dubboclub.dk.remote.common.BizException;
import com.dubboclub.dk.remote.common.ErrCode;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.ObjectMapper;

/**
 * 描 述：Excel文件操作工具类，包括读、写、合并等功能 创建时间：2017-3-27
 *
 * @author liux
 */
public class ExcelUtil {

	private static final Logger LOGGER = LoggerFactory.getLogger(ExcelUtil.class);

	// %%%%%%%%-------常量部分 开始----------%%%%%%%%%
	/**
	 * 默认的开始读取的行位置为第一行（索引值为0）
	 */
	private final static int READ_START_POS = 0;

	/**
	 * 默认结束读取的行位置为最后一行（索引值=0，用负数来表示倒数第n行）
	 */
	private final static int READ_END_POS = 0;

	/**
	 * 默认Excel内容的开始比较列位置为第一列（索引值为0）
	 */
	private final static int COMPARE_POS = 0;

	/**
	 * 默认多文件合并的时需要做内容比较（相同的内容不重复出现）
	 */
	private final static boolean NEED_COMPARE = true;

	/**
	 * 默认多文件合并的新文件遇到名称重复时，进行覆盖
	 */
	private final static boolean NEED_OVERWRITE = true;

	/**
	 * 默认只操作一个sheet
	 */
	private final static boolean ONLY_ONE_SHEET = true;

	/**
	 * 默认读取第一个sheet中（只有当ONLY_ONE_SHEET = true时有效）
	 */
	private final static int SELECTED_SHEET = 0;

	/**
	 * 默认从第一个sheet开始读取（索引值为0）
	 */
	private final static int READ_START_SHEET = 0;

	/**
	 * 默认在最后一个sheet结束读取（索引值=0，用负数来表示倒数第n行）
	 */
	private final static int READ_END_SHEET = 0;

	/**
	 * 默认打印各种信息
	 */
	private final static boolean PRINT_MSG = true;

	// %%%%%%%%-------常量部分 结束----------%%%%%%%%%

	// %%%%%%%%-------字段部分 开始----------%%%%%%%%%
	/**
	 * Excel文件路径
	 */
	private String excelPath = "data.xlsx";

	/**
	 * 设定开始读取的位置，默认为0
	 */
	private int startReadPos = READ_START_POS;

	/**
	 * 设定结束读取的位置，默认为0，用负数来表示倒数第n行
	 */
	private int endReadPos = READ_END_POS;

	/**
	 * 设定开始比较的列位置，默认为0
	 */
	private int comparePos = COMPARE_POS;

	/**
	 * 设定汇总的文件是否需要替换，默认为true
	 */
	private boolean isOverWrite = NEED_OVERWRITE;

	/**
	 * 设定是否需要比较，默认为true(仅当不覆写目标内容是有效，即isOverWrite=false时有效)
	 */
	private boolean isNeedCompare = NEED_COMPARE;

	/**
	 * 设定是否只操作第一个sheet
	 */
	private boolean onlyReadOneSheet = ONLY_ONE_SHEET;

	/**
	 * 设定操作的sheet在索引值
	 */
	private int selectedSheetIdx = SELECTED_SHEET;

	/**
	 * 设定操作的sheet的名称
	 */
	private String selectedSheetName = "";

	/**
	 * 设定开始读取的sheet，默认为0
	 */
	private int startSheetIdx = READ_START_SHEET;

	/**
	 * 设定结束读取的sheet，默认为0，用负数来表示倒数第n行
	 */
	private int endSheetIdx = READ_END_SHEET;

	/**
	 * 设定是否打印消息
	 */
	private boolean printMsg = PRINT_MSG;
	private String sheetName = "sheet1";

	// %%%%%%%%-------字段部分 结束----------%%%%%%%%%

	public String getSheetName() {
		return sheetName;
	}

	public void setSheetName(String sheetName) {
		this.sheetName = sheetName;
	}

	public ExcelUtil() {
	}

	public ExcelUtil(String excelPath) {
		this.excelPath = excelPath;
	}

	/**
	 * 还原设定（其实是重新new一个新的对象并返回）
	 *
	 * @return
	 */
	public ExcelUtil restoreSettings() {
		ExcelUtil instance = new ExcelUtil(this.excelPath);
		return instance;
	}

	/**
	 * 自动根据文件扩展名，调用对应的读取方法
	 *
	 * @throws IOException
	 * @Title: writeExcel
	 * @Date : 2016-7-27 下午01:50:38
	 */
	public List<Row> readExcel() throws IOException {
		return readExcel(this.excelPath);
	}

	/**
	 * 自动根据文件扩展名，调用对应的读取方法
	 *
	 * @param xlsPath
	 * @throws IOException
	 * @Title: writeExcel
	 * @Date : 2016-7-27 下午01:50:38
	 */
	public List<Row> readExcel(String xlsPath) throws IOException {

		// 扩展名为空时，
		if (xlsPath.equals("")) {
			throw new IOException("文件路径不能为空！");
		} else {
			File file = new File(xlsPath);
			if (!file.exists()) {
				throw new IOException("文件不存在！");
			}
		}

		// 获取扩展名
		String ext = xlsPath.substring(xlsPath.lastIndexOf(".") + 1);

		try {

			if ("xls".equals(ext)) { // 使用xls方式读取
				return readExcel_xls(xlsPath);
			} else if ("xlsx".equals(ext)) { // 使用xlsx方式读取
				return readExcel_xlsx(xlsPath);
			} else { // 依次尝试xls、xlsx方式读取
				out("您要操作的文件没有扩展名，正在尝试以xls方式读取...");
				try {
					return readExcel_xls(xlsPath);
				} catch (IOException e1) {
					out("尝试以xls方式读取，结果失败！，正在尝试以xlsx方式读取...");
					try {
						return readExcel_xlsx(xlsPath);
					} catch (IOException e2) {
						out("尝试以xls方式读取，结果失败！\n请您确保您的文件是Excel文件，并且无损，然后再试。");
						throw e2;
					}
				}
			}
		} catch (IOException e) {
			throw e;
		}
	}

	/**
	 * 自动根据文件扩展名，调用对应的写入方法
	 *
	 * @param rowList
	 * @throws IOException
	 * @Title: writeExcel
	 * @Date : 2016-7-27 下午01:50:38
	 */
	public void writeExcel(List<Map<String, Object>> rowList) throws IOException {
		writeExcel(rowList, excelPath);
	}

	/**
	 * 自动根据文件扩展名，调用对应的写入方法
	 *
	 * @param rowList
	 * @param xlsPath
	 * @throws IOException
	 * @Title: writeExcel
	 * @Date : 2016-7-27 下午01:50:38
	 */
	public void writeExcel(List<Map<String, Object>> rowList, String xlsPath) throws IOException {

		// 扩展名为空时，
		if (xlsPath.equals("")) {
			throw new IOException("文件路径不能为空！");
		}

		// 获取扩展名
		String ext = xlsPath.substring(xlsPath.lastIndexOf(".") + 1);

		try {

			if ("xls".equals(ext)) { // 使用xls方式写入
				writeExcel_xls(rowList, xlsPath);
			} else if ("xlsx".equals(ext)) { // 使用xlsx方式写入
				writeExcel_xlsx(rowList, xlsPath);
			} else { // 依次尝试xls、xlsx方式写入
				out("您要操作的文件没有扩展名，正在尝试以xls方式写入...");
				try {
					writeExcel_xls(rowList, xlsPath);
				} catch (IOException e1) {
					out("尝试以xls方式写入，结果失败！，正在尝试以xlsx方式读取...");
					try {
						writeExcel_xlsx(rowList, xlsPath);
					} catch (IOException e2) {
						out("尝试以xls方式写入，结果失败！\n请您确保您的文件是Excel文件，并且无损，然后再试。");
						throw e2;
					}
				}
			}
		} catch (IOException e) {
			throw e;
		}
	}

	/**
	 * 修改Excel（97-03版，xls格式）
	 *
	 * @param rowList
	 * @param dist_xlsPath
	 * @throws IOException
	 * @Title: writeExcel_xls
	 * @Date : 2016-7-27 下午01:50:38
	 */
	public void writeExcel_xls(List<Map<String, Object>> rowList, String dist_xlsPath) throws IOException {
		writeExcel_xls(rowList, excelPath, dist_xlsPath);
	}

	/**
	 * 修改Excel（97-03版，xls格式）
	 *
	 * @param rowList
	 * @param src_xlsPath
	 * @param dist_xlsPath
	 * @throws IOException
	 * @Title: writeExcel_xls
	 * @Date : 2016-7-27 下午01:50:38
	 */
	public void writeExcel_xls(List<Map<String, Object>> rowList, String src_xlsPath, String dist_xlsPath)
			throws IOException {

		// 判断文件路径是否为空
		if (dist_xlsPath == null || dist_xlsPath.equals("")) {
			out("文件路径不能为空");
			throw new IOException("文件路径不能为空");
		}
		// 判断文件路径是否为空
		if (src_xlsPath == null || src_xlsPath.equals("")) {
			out("文件路径不能为空");
			throw new IOException("文件路径不能为空");
		}

		// 判断列表是否有数据，如果没有数据，则返回
		if (rowList == null || rowList.size() == 0) {
			out("文档为空");
			return;
		}

		try {
			HSSFWorkbook wb = null;

			// 判断文件是否存在
			File file = new File(dist_xlsPath);
			if (file.exists()) {
				// 如果复写，则删除后
				if (isOverWrite) {
					if (!file.delete()) {
						throw new IllegalStateException("Remove directory failed");
					}
					// 如果文件不存在，则创建一个新的Excel
					wb = new HSSFWorkbook();
					wb.createSheet(sheetName);
					// wb = new HSSFWorkbook(new FileInputStream(src_xlsPath));
				} else {
					// 如果文件存在，则读取Excel
					wb = new HSSFWorkbook(new FileInputStream(file));
					if(wb.getSheet(sheetName)==null){
						wb.createSheet(sheetName);
					}
				}
			} else {
				// 如果文件不存在，则创建一个新的Excel
				wb = new HSSFWorkbook();
				wb.createSheet(sheetName);
				// wb = new HSSFWorkbook(new FileInputStream(src_xlsPath));
			}

			// 将rowlist的内容写到Excel中
			writeExcel(wb, rowList, dist_xlsPath);

		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	/**
	 * 修改Excel（97-03版，xls格式）
	 *
	 * @param rowList
	 * @param dist_xlsPath
	 * @throws IOException
	 * @Title: writeExcel_xls
	 * @Date : 2016-7-27 下午01:50:38
	 */
	public void writeExcel_xlsx(List<Map<String, Object>> rowList, String dist_xlsPath) throws IOException {
		writeExcel_xls(rowList, excelPath, dist_xlsPath);
	}

	/**
	 * 修改Excel（2007版，xlsx格式）
	 *
	 * @param rowList
	 * @throws IOException
	 * @Title: writeExcel_xlsx
	 * @Date : 2016-7-27 下午01:50:38
	 */
	public void writeExcel_xlsx(List<Map<String, Object>> rowList, String src_xlsPath, String dist_xlsPath)
			throws IOException {

		// 判断文件路径是否为空
		if (dist_xlsPath == null || dist_xlsPath.equals("")) {
			out("文件路径不能为空");
			throw new IOException("文件路径不能为空");
		}
		// 判断文件路径是否为空
		if (src_xlsPath == null || src_xlsPath.equals("")) {
			out("文件路径不能为空");
			throw new IOException("文件路径不能为空");
		}

		// 判断列表是否有数据，如果没有数据，则返回
		if (rowList == null || rowList.size() == 0) {
			out("文档为空");
			return;
		}

		try {
			// 读取文档
			XSSFWorkbook wb = null;

			// 判断文件是否存在
			File file = new File(dist_xlsPath);
			if (file.exists()) {
				// 如果复写，则删除后
				if (isOverWrite) {
					if (!file.delete()) {
						throw new IllegalStateException("Remove directory failed");
					}
					// 如果文件不存在，则创建一个新的Excel
					// wb = new XSSFWorkbook();
					// wb.createSheet("Sheet1");
					wb = new XSSFWorkbook(new FileInputStream(src_xlsPath));
				} else {
					// 如果文件存在，则读取Excel
					wb = new XSSFWorkbook(new FileInputStream(file));
				}
			} else {
				// 如果文件不存在，则创建一个新的Excel
				// wb = new XSSFWorkbook();
				// wb.createSheet("Sheet1");
				wb = new XSSFWorkbook(new FileInputStream(src_xlsPath));
			}
			// 将rowlist的内容添加到Excel中
			writeExcel(wb, rowList, dist_xlsPath);

		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	/**
	 * //读取Excel 2007版，xlsx格式
	 *
	 * @return
	 * @throws IOException
	 * @Title: readExcel_xlsx
	 * @Date : 2016-7-27 上午11:43:11
	 */
	public List<Row> readExcel_xlsx() throws IOException {
		return readExcel_xlsx(excelPath);
	}

	/**
	 * //读取Excel 2007版，xlsx格式
	 *
	 * @return
	 * @throws Exception
	 * @Title: readExcel_xlsx
	 * @Date : 2016-7-27 上午11:43:11
	 */
	public List<Row> readExcel_xlsx(String xlsPath) throws IOException {
		// 判断文件是否存在
		File file = new File(xlsPath);
		if (!file.exists()) {
			throw new IOException("文件名为" + file.getName() + "Excel文件不存在！");
		}

		XSSFWorkbook wb = null;
		List<Row> rowList = new ArrayList<Row>();
		try {
			FileInputStream fis = new FileInputStream(file);
			// 去读Excel
			wb = new XSSFWorkbook(fis);

			// 读取Excel 2007版，xlsx格式
			rowList = readExcel(wb);

		} catch (IOException e) {
			e.printStackTrace();
		}
		return rowList;
	}

	/**
	 * //读取Excel 2007版，xlsx格式
	 *
	 * @return
	 * @throws Exception
	 * @Title: readExcel_xlsx
	 * @Date : 2016-7-27 上午11:43:11
	 */
	public List<Row> readExcel_xlsx(InputStream xlsxInputStream) throws IOException {

		XSSFWorkbook wb = null;
		List<Row> rowList = new ArrayList<Row>();
		try {

			// 去读Excel
			wb = new XSSFWorkbook(xlsxInputStream);

			// 读取Excel 2007版，xlsx格式
			rowList = readExcel(wb);

		} catch (IOException e) {
			e.printStackTrace();
			throw e;
		}
		return rowList;
	}

	/**
	 * //读取Excel 2007版，xlsx格式
	 *
	 * @return
	 * @throws Exception
	 * @Title: readExcel_xlsx
	 * @Date : 2016-7-27 上午11:43:11
	 */
	public List<Row> readExcel_xlsx(byte[] xlsxBytes) throws IOException {

		XSSFWorkbook wb = null;
		List<Row> rowList = new ArrayList<Row>();
		ByteArrayInputStream byteArrayInputStream = null;
		try {

			byteArrayInputStream = new ByteArrayInputStream(xlsxBytes);
			// 去读Excel
			wb = new XSSFWorkbook(byteArrayInputStream);

			// 读取Excel 2007版，xlsx格式
			rowList = readExcel(wb);

		} catch (IOException e) {
			e.printStackTrace();
			throw e;
		} finally {
			if (byteArrayInputStream != null) {
				try {
					byteArrayInputStream.close();
				} catch (Exception e2) {
				}
			}
		}
		return rowList;
	}

	/***
	 * 读取Excel(97-03版，xls格式)
	 *
	 * @throws IOException
	 *
	 * @Title: readExcel
	 * @Date : 2016-7-27 上午09:53:21
	 */
	public List<Row> readExcel_xls() throws IOException {
		return readExcel_xls(excelPath);
	}

	/***
	 * 读取Excel(97-03版，xls格式)
	 *
	 * @throws Exception
	 *
	 * @Title: readExcel
	 * @Date : 2016-7-27 上午09:53:21
	 */
	public List<Row> readExcel_xls(String xlsPath) throws IOException {

		// 判断文件是否存在
		File file = new File(xlsPath);
		if (!file.exists()) {
			throw new IOException("文件名为" + file.getName() + "Excel文件不存在！");
		}

		HSSFWorkbook wb = null;// 用于Workbook级的操作，创建、删除Excel
		List<Row> rowList = new ArrayList<Row>();

		try {
			// 读取Excel
			wb = new HSSFWorkbook(new FileInputStream(file));

			// 读取Excel 97-03版，xls格式
			rowList = readExcel(wb);

		} catch (IOException e) {
			e.printStackTrace();
		}
		return rowList;
	}

	/***
	 * 读取Excel(97-03版，xls格式)
	 *
	 * @throws Exception
	 *
	 * @Title: readExcel
	 * @Date : 2016-7-27 上午09:53:21
	 */
	public List<Row> readExcel_xls(InputStream xlsInputStream) throws IOException {
		HSSFWorkbook wb = null;// 用于Workbook级的操作，创建、删除Excel
		List<Row> rowList = new ArrayList<Row>();

		try {
			// 读取Excel
			wb = new HSSFWorkbook(xlsInputStream);

			// 读取Excel 97-03版，xls格式
			rowList = readExcel(wb);

		} catch (IOException e) {
			e.printStackTrace();
			throw e;
		}
		return rowList;
	}

	/***
	 * 读取Excel(97-03版，xls格式)
	 *
	 * @throws Exception
	 *
	 * @Title: readExcel
	 * @Date : 2016-7-27 上午09:53:21
	 */
	public List<Row> readExcel_xls(byte[] xlsBytes) throws IOException {
		HSSFWorkbook wb = null;// 用于Workbook级的操作，创建、删除Excel
		List<Row> rowList = new ArrayList<Row>();
		ByteArrayInputStream byteArrayInputStream = null;
		try {
			byteArrayInputStream = new ByteArrayInputStream(xlsBytes);

			// 读取Excel
			wb = new HSSFWorkbook(byteArrayInputStream);

			// 读取Excel 97-03版，xls格式
			rowList = readExcel(wb);

		} catch (IOException e) {
			e.printStackTrace();
			throw e;
		} finally {
			if (byteArrayInputStream != null) {
				try {
					byteArrayInputStream.close();
				} catch (Exception e2) {
				}
			}
		}
		return rowList;
	}

	/***
	 * 读取单元格的值
	 *
	 * @Title: getCellValue
	 * @Date : 2016-7-27 上午10:52:07
	 * @param cell
	 * @return
	 */
	public static String getCellValue(Cell cell) {
		Object result = "";
		if (cell != null) {
			switch (cell.getCellType()) {
			case Cell.CELL_TYPE_STRING:
				result = cell.getStringCellValue();
				break;
			case Cell.CELL_TYPE_NUMERIC:
				result = cell.getNumericCellValue();
				break;
			case Cell.CELL_TYPE_BOOLEAN:
				result = cell.getBooleanCellValue();
				break;
			case Cell.CELL_TYPE_FORMULA:
				result = cell.getCellFormula();
				break;
			case Cell.CELL_TYPE_ERROR:
				result = cell.getErrorCellValue();
				break;
			case Cell.CELL_TYPE_BLANK:
				break;
			default:
				break;
			}
		}
		return result.toString();
	}

	/**
	 * 通用读取Excel
	 *
	 * @param wb
	 * @return
	 * @Title: readExcel
	 * @Date : 2016-7-27 上午11:26:53
	 */
	private List<Row> readExcel(Workbook wb) {
		List<Row> rowList = new ArrayList<Row>();

		int sheetCount = 1;// 需要操作的sheet数量

		Sheet sheet = null;
		if (onlyReadOneSheet) { // 只操作一个sheet
			// 获取设定操作的sheet(如果设定了名称，按名称查，否则按索引值查)
			sheet = selectedSheetName.equals("") ? wb.getSheetAt(selectedSheetIdx) : wb.getSheet(selectedSheetName);
		} else { // 操作多个sheet
			sheetCount = wb.getNumberOfSheets();// 获取可以操作的总数量
		}
		// 获取sheet数目
		for (int t = startSheetIdx; t < sheetCount + endSheetIdx; t++) {
			// 获取设定操作的sheet
			if (!onlyReadOneSheet) {
				sheet = wb.getSheetAt(t);
			}

			// 获取最后行号
			int lastRowNum = sheet.getLastRowNum();

			if (lastRowNum > 0) { // 如果>0，表示有数据
				// LOGGER.debug("开始读取第{}个名为【{}】的内容：", t, sheet.getSheetName());
			}
			Row row = null;
			int cellnum = 1;
			// 循环读取
			for (int i = startReadPos; i <= lastRowNum + endReadPos; i++) {
				row = sheet.getRow(i);
				// LOGGER.debug("第{}行内容为{},共{}列!", i, row.toString(),
				// row.getLastCellNum());
				if (row != null && getCellValue(row.getCell(0)) != "") {
					// 获取每一单元格的值
					// LOGGER.debug("第{}行内容为{},共{}列!", i, row.toString(),
					// row.getLastCellNum());
					cellnum = 0;
					for (int j = 0; j < row.getLastCellNum(); j++) {
						String value = getCellValue(row.getCell(j));
						if ("".equals(value)) {
							cellnum++;
						}
					}
					if (cellnum != row.getLastCellNum()) {
						rowList.add(row);
					}
				}
			}
		}
		// LOGGER.debug("共多少行{}", rowList.size());
		return rowList;
	}

	/**
	 * 修改Excel，并另存为
	 *
	 * @param wb
	 * @param rowList
	 * @param xlsPath
	 * @Title: WriteExcel
	 * @Date : 2016-7-27 下午01:33:59
	 */
	private void writeExcel(Workbook wb, List<Map<String, Object>> rowList, String xlsPath) {

		if (wb == null) {
			out("操作文档不能为空！");
			return;
		}

		// xiugai  huwei 20180912
		//Sheet sheet = wb.getSheetAt(0);// 修改第一个sheet中的值
		Sheet sheet = wb.getSheet(sheetName);//修改为取对应名字的sheet

		// 如果每次重写，那么则从开始读取的位置写，否则果获取源文件最新的行。
		// int t = 0;//记录最新添加的行数
		int lastRowNum = isOverWrite ? startReadPos : sheet.getLastRowNum() + 1;
		out("要添加的数据总条数为：" + rowList.size());
		String[] headers = null;
		for (int t = 0; t < rowList.size(); t++) {
			Map<String, Object> rowMap = rowList.get(t);
			if (rowMap == null || rowMap.isEmpty())
				continue;
			if (t == 0) {
				headers = new String[rowMap.size()];
				Set<String> keys = rowMap.keySet();
				int k = 0;
				for (String key : keys) {
					headers[k++] = key;
				}
			}
			// 判断是否已经存在该数据
			// int pos = findInExcel(sheet, row);
			//
			// Row r = null;// 如果数据行已经存在，则获取后重写，否则自动创建新行。
			// if (pos >= 0) {
			// sheet.removeRow(sheet.getRow(pos));
			// r = sheet.createRow(pos);
			// } else {
			// r = sheet.createRow(lastRowNum + t++);
			// }
			Row r = sheet.createRow(lastRowNum + t);

			// 用于设定单元格样式
			// CellStyle newstyle = wb.createCellStyle();
			CellStyle newstyle = getCellStyle(wb);
			// 循环为新行创建单元格
			for (int i = 0; i < headers.length; i++) {
				Cell cell = r.createCell(i);// 获取数据类型
				/*
				 * 如果map的value值为null，则会报空指针异常 huwei
				 * cell.setCellValue(rowMap.get(headers[i]).toString());//
				 * 复制单元格的值到新的单元格
				 */

				if (rowMap.get(headers[i]) == null) {
					cell.setCellValue("");
				} else {
					cell.setCellValue(rowMap.get(headers[i]).toString());
				}

				// cell.setCellStyle(row.getCell(i).getCellStyle());//出错
				// if (row.getCell(i) == null) continue;
				// copyCellStyle(row.getCell(i).getCellStyle(), newstyle); //
				// 获取原来的单元格样式
				if (t == 0) {
					cell.setCellStyle(getHeaderStyle(wb));// 设置样式
				} else {
					cell.setCellStyle(newstyle);// 设置样式
				}
				// sheet.autoSizeColumn(i);//自动跳转列宽度
			}
		}
		// out("其中检测到重复条数为:" + (rowList.size() - t) + " ，追加条数为："+t);

		// 统一设定合并单元格
		setMergedRegion(sheet);
		File file = new File(xlsPath);
		File dir = new File(file.getParent());
		if (!dir.exists()) {
			if (!dir.mkdirs()) {
				throw new IllegalStateException("Create directory failed");
			}
		}
		try {
			// 重新将数据写入Excel中
			FileOutputStream outputStream = new FileOutputStream(file);
			wb.write(outputStream);
			outputStream.flush();
			outputStream.close();
		} catch (Exception e) {
			out("写入Excel时发生错误！ ");
			e.printStackTrace();
		}
	}
	

	private CellStyle getCellStyle(Workbook workbook) {
		HSSFCellStyle cell_Style = (HSSFCellStyle) workbook.createCellStyle();// 设置字体样式
		cell_Style.setAlignment(HSSFCellStyle.ALIGN_CENTER);
		cell_Style.setVerticalAlignment(HSSFCellStyle.VERTICAL_CENTER);// 垂直对齐居中
		cell_Style.setWrapText(true); // 设置为自动换行
		HSSFFont cell_Font = (HSSFFont) workbook.createFont();
		cell_Font.setFontName("宋体");
		cell_Font.setFontHeightInPoints((short) 8);
		cell_Style.setFont(cell_Font);
		cell_Style.setBorderBottom(HSSFCellStyle.BORDER_THIN); // 下边框
		cell_Style.setBorderLeft(HSSFCellStyle.BORDER_THIN);// 左边框
		cell_Style.setBorderTop(HSSFCellStyle.BORDER_THIN);// 上边框
		cell_Style.setBorderRight(HSSFCellStyle.BORDER_THIN);// 右边框
		return cell_Style;
	}

	private CellStyle getHeaderStyle(Workbook workbook) {
		HSSFCellStyle cellstyle = (HSSFCellStyle) workbook.createCellStyle();// 设置表头样式
		cellstyle.setAlignment(HSSFCellStyle.ALIGN_CENTER);// 设置居中
		cellstyle.setFillForegroundColor(HSSFColor.GREY_25_PERCENT.index);
		cellstyle.setFillPattern(HSSFCellStyle.SOLID_FOREGROUND);
		HSSFCellStyle headerStyle = (HSSFCellStyle) workbook.createCellStyle();// 创建标题样式
		headerStyle.setVerticalAlignment(HSSFCellStyle.VERTICAL_CENTER); // 设置垂直居中
		headerStyle.setAlignment(HSSFCellStyle.ALIGN_CENTER); // 设置水平居中
		HSSFFont headerFont = (HSSFFont) workbook.createFont(); // 创建字体样式
		headerFont.setBoldweight(HSSFFont.BOLDWEIGHT_BOLD); // 字体加粗
		headerFont.setFontName("Times New Roman"); // 设置字体类型
		headerFont.setFontHeightInPoints((short) 8); // 设置字体大小
		headerStyle.setFont(headerFont); // 为标题样式设置字体样式
		return cellstyle;
	}

	/**
	 * 查找某行数据是否在Excel表中存在，返回行数。
	 *
	 * @param sheet
	 * @param row
	 * @return
	 * @Title: findInExcel
	 * @Date : 2016-7-27 下午02:23:12
	 */
	@SuppressWarnings("unused")
	private int findInExcel(Sheet sheet, Row row) {
		int pos = -1;

		try {
			// 如果覆写目标文件，或者不需要比较，则直接返回
			if (isOverWrite || !isNeedCompare) {
				return pos;
			}
			for (int i = startReadPos; i <= sheet.getLastRowNum() + endReadPos; i++) {
				Row r = sheet.getRow(i);
				if (r != null && row != null) {
					String v1 = getCellValue(r.getCell(comparePos));
					String v2 = getCellValue(row.getCell(comparePos));
					if (v1.equals(v2)) {
						pos = i;
						break;
					}
				}
			}
		} catch (Exception e) {
			throw new IllegalStateException("Get cell info failed " + e.getMessage(), e);
		}
		return pos;
	}

	/**
	 * 复制一个单元格样式到目的单元格样式
	 *
	 * @param fromStyle
	 * @param toStyle
	 */
	public static void copyCellStyle(CellStyle fromStyle, CellStyle toStyle) {
		toStyle.setAlignment(fromStyle.getAlignment());
		// 边框和边框颜色
		toStyle.setBorderBottom(fromStyle.getBorderBottom());
		toStyle.setBorderLeft(fromStyle.getBorderLeft());
		toStyle.setBorderRight(fromStyle.getBorderRight());
		toStyle.setBorderTop(fromStyle.getBorderTop());
		toStyle.setTopBorderColor(fromStyle.getTopBorderColor());
		toStyle.setBottomBorderColor(fromStyle.getBottomBorderColor());
		toStyle.setRightBorderColor(fromStyle.getRightBorderColor());
		toStyle.setLeftBorderColor(fromStyle.getLeftBorderColor());

		// 背景和前景
		toStyle.setFillBackgroundColor(fromStyle.getFillBackgroundColor());
		toStyle.setFillForegroundColor(fromStyle.getFillForegroundColor());

		// 数据格式
		toStyle.setDataFormat(fromStyle.getDataFormat());
		toStyle.setFillPattern(fromStyle.getFillPattern());
		// toStyle.setFont(fromStyle.getFont(null));
		toStyle.setHidden(fromStyle.getHidden());
		toStyle.setIndention(fromStyle.getIndention());// 首行缩进
		toStyle.setLocked(fromStyle.getLocked());
		toStyle.setRotation(fromStyle.getRotation());// 旋转
		toStyle.setVerticalAlignment(fromStyle.getVerticalAlignment());
		toStyle.setWrapText(fromStyle.getWrapText());

	}

	/**
	 * 获取合并单元格的值
	 *
	 * @param sheet
	 * @return
	 */
	public void setMergedRegion(Sheet sheet) {
		int sheetMergeCount = sheet.getNumMergedRegions();

		for (int i = 0; i < sheetMergeCount; i++) {
			// 获取合并单元格位置
			CellRangeAddress ca = sheet.getMergedRegion(i);
			int firstRow = ca.getFirstRow();
			if (startReadPos - 1 > firstRow) {// 如果第一个合并单元格格式在正式数据的上面，则跳过。
				continue;
			}
			int lastRow = ca.getLastRow();
			int mergeRows = lastRow - firstRow;// 合并的行数
			int firstColumn = ca.getFirstColumn();
			int lastColumn = ca.getLastColumn();
			// 根据合并的单元格位置和大小，调整所有的数据行格式，
			for (int j = lastRow + 1; j <= sheet.getLastRowNum(); j++) {
				// 设定合并单元格
				sheet.addMergedRegion(new CellRangeAddress(j, j + mergeRows, firstColumn, lastColumn));
				j = j + mergeRows;// 跳过已合并的行
			}

		}
	}

	/**
	 * 打印消息，
	 *
	 * @param msg
	 *            消息内容
	 */
	private void out(String msg) {
		if (printMsg) {
			out(msg, true);
		}
	}

	/**
	 * 打印消息，
	 *
	 * @param msg
	 *            消息内容
	 * @param tr
	 *            换行
	 */
	private void out(String msg, boolean tr) {
		if (printMsg) {
			System.out.print(msg + (tr ? "\n" : ""));
		}
	}

	public String getExcelPath() {
		return this.excelPath;
	}

	public void setExcelPath(String excelPath) {
		this.excelPath = excelPath;
	}

	public boolean isNeedCompare() {
		return isNeedCompare;
	}

	public void setNeedCompare(boolean isNeedCompare) {
		this.isNeedCompare = isNeedCompare;
	}

	public int getComparePos() {
		return comparePos;
	}

	public void setComparePos(int comparePos) {
		this.comparePos = comparePos;
	}

	public int getStartReadPos() {
		return startReadPos;
	}

	public void setStartReadPos(int startReadPos) {
		this.startReadPos = startReadPos;
	}

	public int getEndReadPos() {
		return endReadPos;
	}

	public void setEndReadPos(int endReadPos) {
		this.endReadPos = endReadPos;
	}

	public boolean isOverWrite() {
		return isOverWrite;
	}

	public void setOverWrite(boolean isOverWrite) {
		this.isOverWrite = isOverWrite;
	}

	public boolean isOnlyReadOneSheet() {
		return onlyReadOneSheet;
	}

	public void setOnlyReadOneSheet(boolean onlyReadOneSheet) {
		this.onlyReadOneSheet = onlyReadOneSheet;
	}

	public int getSelectedSheetIdx() {
		return selectedSheetIdx;
	}

	public void setSelectedSheetIdx(int selectedSheetIdx) {
		this.selectedSheetIdx = selectedSheetIdx;
	}

	public String getSelectedSheetName() {
		return selectedSheetName;
	}

	public void setSelectedSheetName(String selectedSheetName) {
		this.selectedSheetName = selectedSheetName;
	}

	public int getStartSheetIdx() {
		return startSheetIdx;
	}

	public void setStartSheetIdx(int startSheetIdx) {
		this.startSheetIdx = startSheetIdx;
	}

	public int getEndSheetIdx() {
		return endSheetIdx;
	}

	public void setEndSheetIdx(int endSheetIdx) {
		this.endSheetIdx = endSheetIdx;
	}

	public boolean isPrintMsg() {
		return printMsg;
	}

	public void setPrintMsg(boolean printMsg) {
		this.printMsg = printMsg;
	}

	/*public static void main(String[] args) {
		ExcelUtil eu = new ExcelUtil();
		eu.setExcelPath("excelFile/testdata.xlsx");
		List<Map<String, Object>> list = new ArrayList<>();
		for (int i = 0; i < 5; i++) {
			Map<String, Object> map = new HashMap<String, Object>();
			map.put("col1", "gg" + i);
			map.put("col2", "gg" + i);
			map.put("col3", "gg" + i);
			list.add(map);
		}

		try {
			eu.writeExcel(list);
		} catch (IOException e) {
			e.printStackTrace();
		}
		// 从第一行开始读取
		// eu.setStartReadPos(1);
		//
		// String src_xlspath = "C:\\Users\\Administrator\\Desktop\\数据统计.xlsx";
		// //String dist_xlsPath = "D:\\2.xls";
		// List<Row> rowList = null;
		// try {
		// rowList = eu.readExcel(src_xlspath);
		// //eu.writeExcel_xls(rowList, src_xlspath, dist_xlsPath);
		// } catch (IOException e) {
		// e.printStackTrace();
		// }
		// System.out.println("开始时间 | 截止时间 | 账号名称 | 计划名称 | 展现数 ");
		// for(int i=1;i<rowList.size();i++){
		// Row obj=rowList.get(i);
		// System.out.println(""+getCellValue(obj.getCell(0))+"|"+getCellValue(obj.getCell(1))+"|"+getCellValue(obj.getCell(2))+"|"+getCellValue(obj.getCell(3))+"|"+getCellValue(obj.getCell(4)));
		// }

	}*/

	
	private static ObjectMapper objectMapper = new ObjectMapper();
/*	@Autowired
	private DataQueryDao dataQueryDao;*/

	
	//以票联某报文为例，解析以excel导出
	public static void test(String[] args) 
	{
		objectMapper.setSerializationInclusion(JsonInclude.Include.NON_NULL);
		// 使用BigDecimal取代浮点类型，以免精度丢失
		objectMapper.configure(
				DeserializationFeature.USE_BIG_DECIMAL_FOR_FLOATS, true);
		objectMapper.configure(
				DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES, false);  
		ExcelUtil excel = new ExcelUtil();		
		Map<String,String> requestMap = new HashMap<String, String>();
		Map<String, Object> bizContent = new HashMap<String, Object>();
		List<Map<String,Object>> contentList = new ArrayList<>();
		List<Map<String,Object>> certInfoArray = new ArrayList<>();
		List<Map<String,Object>> handlerInfoArray = new ArrayList<>();
		List<Map<String,Object>> contactInfoArray = new ArrayList<>();
		List<Map<String,Object>> vedioInfoArray = new ArrayList<>();
		List<List<Map<String,Object>>> sheetList = new ArrayList<>();
		List<Map<String, Object>> listAll = new ArrayList<Map<String, Object>>();
		List<Map<String, Object>> listSort = new ArrayList<Map<String, Object>>();	//按数据库表本身查询的字段顺序排序后的datalist
		List<Map<String, Object>> list = new ArrayList<>();
		List<Map<String, Object>> tableFileds = null;	//查询相关表字段
		Map<String, Object> index = new HashMap<>();
		Map<String, Object> indexTmp = null;
		String filepath = "D:\\IT\\data.xls";
		String jsonStr = "{ "
    +" \"third_trade_no\": \"42EB0757-6E9D-4827-BEC4-A2C865EAAF19-1532749576\","
    +" \"sign_type\": \"RSA2\","
    +" \"biz_content\": \"{\\\"ch_given_name\\\":\\\"djjd\\\",\\\"client_short\\\":\\\"dgh\\\",\\\"inland_offshore\\\":\\\"O\\\",\\\"cus_type\\\":\\\"300\\\",\\\"option\\\":\\\"01\\\",\\\"crt_user\\\":\\\"这\\\",\\\"is_tech\\\":\\\"12\\\",\\\"country_risk\\\":\\\"ATA\\\",\\\"end_rec_time\\\":\\\"20180728\\\",\\\"bus_scope\\\":\\\"78\\\",\\\"ent_org_type\\\":\\\"1\\\",\\\"crt_date\\\":\\\"20180728\\\",\\\"tax_payer_reasont\\\":\\\"1\\\",\\\"reg_amt\\\":\\\"7788\\\",\\\"pla_reg\\\":\\\"145\\\",\\\"en_live_address\\\":\\\"chhj\\\",\\\"reg_cur_no\\\":\\\"HKD\\\",\\\"is_need_ibt_sur\\\":\\\"1\\\",\\\"cert_info_array\\\":[{\\\"lic_no\\\":\\\"384833\\\",\\\"lic_type\\\":\\\"16\\\",\\\"area_code\\\":\\\"321283\\\",\\\"ent_end_date\\\":\\\"20190728\\\",\\\"pref_flag\\\":\\\"Y\\\",\\\"iss_authority\\\":\\\"你也\\\",\\\"iss_state\\\":\\\"32\\\",\\\"inspect_date\\\":\\\"20180528\\\",\\\"iss_country\\\":\\\"ARM\\\",\\\"iss_city\\\":\\\"321200\\\",\\\"ent_beg_date\\\":\\\"20170728\\\"}],\\\"is_eco_area\\\":\\\"31\\\",\\\"client_indicator\\\":\\\"V\\\",\\\"bill_sts\\\":\\\"BS00\\\",\\\"bill_no\\\":\\\"54566\\\",\\\"en_city\\\":\\\"437844\\\",\\\"corp_size\\\":\\\"5\\\",\\\"real_amt\\\":\\\"5566\\\",\\\"reg_street_addr\\\":\\\"大喊大叫的\\\",\\\"tax_payer_reason_text\\\":\\\"734747247\\\",\\\"category_type\\\":\\\"308\\\",\\\"client_status\\\":\\\"NTC\\\",\\\"reg_nation_addr\\\":\\\"ARM\\\",\\\"chnl_type\\\":\\\"PCNPS\\\",\\\"real_cur_no\\\":\\\"次\\\",\\\"ent_bld_date\\\":\\\"20160728\\\",\\\"handler_info_array\\\":[{\\\"cert_type\\\":\\\"16\\\",\\\"cert_end_date\\\":\\\"20180728\\\",\\\"cert_no\\\":\\\"46667\\\",\\\"hd_name\\\":\\\"778\\\",\\\"rel_rel\\\":\\\"RR01\\\",\\\"cert_beg_date\\\":\\\"20180728\\\"},{\\\"hd_name\\\":\\\"567\\\",\\\"holder_type\\\":\\\"A1\\\",\\\"resident_flag\\\":\\\"3\\\",\\\"holding_scale\\\":\\\"37383\\\",\\\"rel_rel\\\":\\\"RR06\\\",\\\"cert_end_date\\\":\\\"20190728\\\",\\\"invest_date\\\":\\\"20180728\\\",\\\"cert_type\\\":\\\"16\\\",\\\"nation_code\\\":\\\"ARG\\\",\\\"cert_no\\\":\\\"567\\\"},{\\\"address\\\":\\\"北京师范大学珠海\\\",\\\"hd_name\\\":\\\"345\\\",\\\"rel_rel\\\":\\\"RR04\\\",\\\"cert_end_date\\\":\\\"20180728\\\",\\\"cert_type\\\":\\\"2\\\",\\\"nation_code\\\":\\\"ARE\\\",\\\"cert_no\\\":\\\"5677\\\"}],\\\"bus_type\\\":\\\"100173\\\",\\\"contact_info_array\\\":[{\\\"nation\\\":\\\"ARG\\\",\\\"address\\\":\\\"7656\\\",\\\"pref_flag\\\":\\\"Y\\\",\\\"area_code\\\":\\\"230828\\\",\\\"post_code\\\":\\\"567\\\",\\\"iss_state\\\":\\\"23\\\",\\\"iss_city\\\":\\\"230800\\\",\\\"contact_type\\\":\\\"11\\\",\\\"contact_tel\\\":\\\"566\\\"}],\\\"is_inner_cus\\\":\\\"0\\\",\\\"tax_ibt_nation\\\":\\\"ARM\\\",\\\"no_need_reason\\\":\\\"\\\",\\\"vedio_info_array\\\":[{\\\"vi_name\\\":\\\"1532747308\\\",\\\"cert_type\\\":\\\"1_1\\\",\\\"cif_act\\\":\\\"01\\\",\\\"vi_no\\\":\\\"\\\",\\\"vi_type\\\":\\\"01\\\",\\\"vi_path\\\":\\\"\\/s\\/app\\/ubfs\\/20210722\\/a7166df1-6d92-4551-9437-127da7490018.jpg\\\"},{\\\"vi_name\\\":\\\"1532748130\\\",\\\"cert_type\\\":\\\"1_2\\\",\\\"cif_act\\\":\\\"01\\\",\\\"vi_no\\\":\\\"\\\",\\\"vi_type\\\":\\\"01\\\",\\\"vi_path\\\":\\\"\\/s\\/app\\/ubfs\\/20210722\\/927f3d9e-0d29-4925-bfa3-3dd229829de1.jpg\\\"},{\\\"vi_name\\\":\\\"1532748142\\\",\\\"cert_type\\\":\\\"1_3\\\",\\\"cif_act\\\":\\\"01\\\",\\\"vi_no\\\":\\\"\\\",\\\"vi_type\\\":\\\"01\\\",\\\"vi_path\\\":\\\"\\/s\\/app\\/ubfs\\/20210722\\/4dc0a06c-745f-4f0b-a992-4ffbacfcd4cf.jpg\\\"},{\\\"vi_name\\\":\\\"1532748153\\\",\\\"cert_type\\\":\\\"1_4\\\",\\\"cif_act\\\":\\\"01\\\",\\\"vi_no\\\":\\\"\\\",\\\"vi_type\\\":\\\"01\\\",\\\"vi_path\\\":\\\"\\/s\\/app\\/ubfs\\/20210722\\/635d3301-84cc-4672-8ac7-5f3fbd6cb44e.jpg\\\"},{\\\"vi_name\\\":\\\"1532748172\\\",\\\"cert_type\\\":\\\"1_5\\\",\\\"cif_act\\\":\\\"01\\\",\\\"vi_no\\\":\\\"\\\",\\\"vi_type\\\":\\\"01\\\",\\\"vi_path\\\":\\\"\\/s\\/app\\/ubfs\\/20210722\\/92f06a38-1835-45f5-9966-1a300b21140d.jpg\\\"},{\\\"vi_name\\\":\\\"1532748181\\\",\\\"cert_type\\\":\\\"1_6\\\",\\\"cif_act\\\":\\\"01\\\",\\\"vi_no\\\":\\\"\\\",\\\"vi_type\\\":\\\"01\\\",\\\"vi_path\\\":\\\"\\/s\\/app\\/ubfs\\/20210722\\/8e42bf0b-ec8b-470d-94f5-da2162cc4f4a.jpg\\\"},{\\\"vi_name\\\":\\\"1532748120\\\",\\\"cert_type\\\":\\\"3_1\\\",\\\"cif_act\\\":\\\"01\\\",\\\"vi_no\\\":\\\"\\\",\\\"vi_type\\\":\\\"01\\\",\\\"vi_path\\\":\\\"\\/s\\/app\\/ubfs\\/20210722\\/c373ebab-6649-4d76-a799-a537bb73cd32.jpg\\\"},{\\\"vi_name\\\":\\\"1532748193\\\",\\\"cert_type\\\":\\\"4_1\\\",\\\"cif_act\\\":\\\"01\\\",\\\"vi_no\\\":\\\"\\\",\\\"vi_type\\\":\\\"01\\\",\\\"vi_path\\\":\\\"\\/s\\/app\\/ubfs\\/20210722\\/934a3a76-2a3d-46ab-aff4-bf86c74f7490.jpg\\\"},{\\\"vi_name\\\":\\\"1532748201\\\",\\\"cert_type\\\":\\\"4_2\\\",\\\"cif_act\\\":\\\"01\\\",\\\"vi_no\\\":\\\"\\\",\\\"vi_type\\\":\\\"01\\\",\\\"vi_path\\\":\\\"\\/s\\/app\\/ubfs\\/20210722\\/93a07826-8cff-4385-88d3-12f45575096e.jpg\\\"},{\\\"vi_name\\\":\\\"1532748214\\\",\\\"cert_type\\\":\\\"5_1\\\",\\\"cif_act\\\":\\\"01\\\",\\\"vi_no\\\":\\\"\\\",\\\"vi_type\\\":\\\"01\\\",\\\"vi_path\\\":\\\"\\/s\\/app\\/ubfs\\/20210722\\/14d2c963-8451-493a-934d-ae5681a92431.jpg\\\"},{\\\"vi_name\\\":\\\"1532748229\\\",\\\"cert_type\\\":\\\"5_2\\\",\\\"cif_act\\\":\\\"01\\\",\\\"vi_no\\\":\\\"\\\",\\\"vi_type\\\":\\\"01\\\",\\\"vi_path\\\":\\\"\\/s\\/app\\/ubfs\\/20210722\\/7e0335d7-69ac-4ec5-9681-657542bbe730.jpg\\\"},{\\\"vi_name\\\":\\\"1532748254\\\",\\\"cert_type\\\":\\\"6_1\\\",\\\"cif_act\\\":\\\"01\\\",\\\"vi_no\\\":\\\"\\\",\\\"vi_type\\\":\\\"01\\\",\\\"vi_path\\\":\\\"\\/s\\/app\\/ubfs\\/20210722\\/5a45aa5e-6608-466c-ab74-aaac5a91710c.jpg\\\"},{\\\"vi_name\\\":\\\"1532748263\\\",\\\"cert_type\\\":\\\"6_2\\\",\\\"cif_act\\\":\\\"01\\\",\\\"vi_no\\\":\\\"\\\",\\\"vi_type\\\":\\\"01\\\",\\\"vi_path\\\":\\\"\\/s\\/app\\/ubfs\\/20210722\\/3c74e964-0095-4653-bade-dfa7af13f733.jpg\\\"},{\\\"vi_name\\\":\\\"1532748271\\\",\\\"cert_type\\\":\\\"6_3\\\",\\\"cif_act\\\":\\\"01\\\",\\\"vi_no\\\":\\\"\\\",\\\"vi_type\\\":\\\"01\\\",\\\"vi_path\\\":\\\"\\/s\\/app\\/ubfs\\/20210722\\/132d4445-5d27-4a29-b0cf-132575598bad.jpg\\\"},{\\\"vi_name\\\":\\\"1532748285\\\",\\\"cert_type\\\":\\\"13_1\\\",\\\"cif_act\\\":\\\"01\\\",\\\"vi_no\\\":\\\"\\\",\\\"vi_type\\\":\\\"01\\\",\\\"vi_path\\\":\\\"\\/s\\/app\\/ubfs\\/20210722\\/3cfe7fcc-5b11-417f-ab1b-20948ae37491.jpg\\\"},{\\\"vi_name\\\":\\\"1532748305\\\",\\\"cert_type\\\":\\\"15_1\\\",\\\"cif_act\\\":\\\"01\\\",\\\"vi_no\\\":\\\"\\\",\\\"vi_type\\\":\\\"01\\\",\\\"vi_path\\\":\\\"\\/s\\/app\\/ubfs\\/20210722\\/ea056122-bae3-4d4d-91c2-70025fa137ef.jpg\\\"},{\\\"vi_name\\\":\\\"1532748343\\\",\\\"cert_type\\\":\\\"15_2\\\",\\\"cif_act\\\":\\\"01\\\",\\\"vi_no\\\":\\\"\\\",\\\"vi_type\\\":\\\"01\\\",\\\"vi_path\\\":\\\"\\/s\\/app\\/ubfs\\/20210722\\/9b826edb-c161-4d08-be02-29c276c6ec11.jpg\\\"},{\\\"vi_name\\\":\\\"1532748417\\\",\\\"cert_type\\\":\\\"17_1\\\",\\\"cif_act\\\":\\\"01\\\",\\\"vi_no\\\":\\\"\\\",\\\"vi_type\\\":\\\"01\\\",\\\"vi_path\\\":\\\"\\/s\\/app\\/ubfs\\/20210722\\/3ba6cf3f-1b82-4ce7-8169-ad8709a167e0.jpg\\\"},{\\\"vi_name\\\":\\\"1532748430\\\",\\\"cert_type\\\":\\\"17_2\\\",\\\"cif_act\\\":\\\"01\\\",\\\"vi_no\\\":\\\"\\\",\\\"vi_type\\\":\\\"01\\\",\\\"vi_path\\\":\\\"\\/s\\/app\\/ubfs\\/20210722\\/0abac1de-2843-48d7-a199-22421d4df30d.jpg\\\"},{\\\"vi_name\\\":\\\"1532748460\\\",\\\"cert_type\\\":\\\"18_1\\\",\\\"cif_act\\\":\\\"01\\\",\\\"vi_no\\\":\\\"\\\",\\\"vi_type\\\":\\\"01\\\",\\\"vi_path\\\":\\\"\\/s\\/app\\/ubfs\\/20210722\\/b9a81451-63cb-4957-a9bc-c3c819f8910a.jpg\\\"},{\\\"vi_name\\\":\\\"1532748471\\\",\\\"cert_type\\\":\\\"18_2\\\",\\\"cif_act\\\":\\\"01\\\",\\\"vi_no\\\":\\\"\\\",\\\"vi_type\\\":\\\"01\\\",\\\"vi_path\\\":\\\"\\/s\\/app\\/ubfs\\/20210722\\/fe980cff-50b6-4f14-9eae-5c291f21cc0d.jpg\\\"},{\\\"vi_name\\\":\\\"1532748482\\\",\\\"cert_type\\\":\\\"19_1\\\",\\\"cif_act\\\":\\\"01\\\",\\\"vi_no\\\":\\\"\\\",\\\"vi_type\\\":\\\"01\\\",\\\"vi_path\\\":\\\"\\/s\\/app\\/ubfs\\/20210722\\/23e86e1b-4161-41fd-b0f8-25546ef6de87.jpg\\\"},{\\\"vi_name\\\":\\\"1532748492\\\",\\\"cert_type\\\":\\\"19_2\\\",\\\"cif_act\\\":\\\"01\\\",\\\"vi_no\\\":\\\"\\\",\\\"vi_type\\\":\\\"01\\\",\\\"vi_path\\\":\\\"\\/s\\/app\\/ubfs\\/20210722\\/2c74e80c-e5b3-443a-917f-d6261a873bcc.jpg\\\"},{\\\"vi_name\\\":\\\"1532748502\\\",\\\"cert_type\\\":\\\"20_1\\\",\\\"cif_act\\\":\\\"01\\\",\\\"vi_no\\\":\\\"\\\",\\\"vi_type\\\":\\\"01\\\",\\\"vi_path\\\":\\\"\\/s\\/app\\/ubfs\\/20210722\\/f3e8a7ce-9cb5-425b-b80c-94bdbe60399b.jpg\\\"},{\\\"vi_name\\\":\\\"1532748511\\\",\\\"cert_type\\\":\\\"20_2\\\",\\\"cif_act\\\":\\\"01\\\",\\\"vi_no\\\":\\\"\\\",\\\"vi_type\\\":\\\"01\\\",\\\"vi_path\\\":\\\"\\/s\\/app\\/ubfs\\/20210722\\/3a509ca1-fea3-4a6f-abec-1f98116360f4.jpg\\\"},{\\\"vi_name\\\":\\\"1532748523\\\",\\\"cert_type\\\":\\\"21_1\\\",\\\"cif_act\\\":\\\"01\\\",\\\"vi_no\\\":\\\"\\\",\\\"vi_type\\\":\\\"01\\\",\\\"vi_path\\\":\\\"\\/s\\/app\\/ubfs\\/20210722\\/9b3921cf-ce6e-4f26-8a29-3d6a6e5fedd8.jpg\\\"},{\\\"vi_name\\\":\\\"1532748541\\\",\\\"cert_type\\\":\\\"21_2\\\",\\\"cif_act\\\":\\\"01\\\",\\\"vi_no\\\":\\\"\\\",\\\"vi_type\\\":\\\"01\\\",\\\"vi_path\\\":\\\"\\/s\\/app\\/ubfs\\/20210722\\/173326fb-6457-403a-b675-0b5799a37d9b.jpg\\\"},{\\\"vi_name\\\":\\\"1532748550\\\",\\\"cert_type\\\":\\\"21_3\\\",\\\"cif_act\\\":\\\"01\\\",\\\"vi_no\\\":\\\"\\\",\\\"vi_type\\\":\\\"01\\\",\\\"vi_path\\\":\\\"\\/s\\/app\\/ubfs\\/20210722\\/c154fb08-f602-4566-991e-2e9d5bf605df.jpg\\\"}],\\\"end_rec_user\\\":\\\"36373\\\",\\\"pub_comp_flag\\\":\\\"41\\\",\\\"ind_type2\\\":\\\"J69\\\",\\\"tax_payer_reg_num\\\":\\\"5569\\\",\\\"ind_type3\\\":\\\"J694\\\",\\\"ind_type4\\\":\\\"J6940\\\",\\\"eco_type\\\":\\\"142\\\",\\\"fx_register_id\\\":\\\"你们\\\",\\\"ibt_type\\\":\\\"2\\\",\\\"is_high_tech\\\":\\\"21\\\",\\\"fx_iss_place\\\":\\\"ehdh\\\",\\\"cus_mngr\\\":\\\"zhaobo\\\",\\\"ind_type\\\":\\\"J\\\",\\\"busilicence_status\\\":\\\"4\\\"}\","
    +"\"partner_id\": \"UBFS\","
    +"\"charset\": \"utf-8\","
    +"\"trade_code\": \"insertOpenCifAccInfo\","
    +"\"version\": \"1.0\","
    +"\"sign\": \"lgJnXkS/fGWq6JvCfGePDhJe+Rxru+w+O0cj953AjRhp/d114nV802ZsbE6neDDBo16E5bo2wsy1NxdaxtRZsdeBVzUfMstdtYwtRDVM2M5/gazTCftmEAEfMdqxby9sDhParEAVkAJJXscx4rB2D9jcJFb+oeJQ7T4L6vrG2Jo=\","
    +"\"timestamp\": \"2018-07-28 11:46:16\","
    +"\"content_type\": \"json\"}";
		try {
			requestMap = objectMapper.readValue(jsonStr, Map.class);
			LOGGER.info(">>>>>>序列化后报文{}", requestMap);
			bizContent = objectMapper.readValue(requestMap.get("biz_content"), Map.class);
			LOGGER.info(">>>>>>序列化后biz_content报文{}", bizContent);
		} catch (IOException e) {
			LOGGER.error("序列化异常，报文格式错误", e);
		}

		certInfoArray = (List<Map<String, Object>>) bizContent.remove("cert_info_array");
		handlerInfoArray = (List<Map<String, Object>>) bizContent.remove("handler_info_array");
		contactInfoArray = (List<Map<String, Object>>) bizContent.remove("contact_info_array");
		vedioInfoArray = (List<Map<String, Object>>) bizContent.remove("vedio_info_array");
		contentList.add(bizContent);
		sheetList.add(contentList);
		sheetList.add(certInfoArray);
		sheetList.add(handlerInfoArray);
		sheetList.add(contactInfoArray);
		sheetList.add(vedioInfoArray);
		for(int i = 0 ;i<sheetList.size();i++) {
			if(i!=0){
				excel.isOverWrite=false;
			}
			excel.sheetName="sheet"+i;
			listAll.clear();
			list.clear();
			listSort.clear();
			index.clear();
			list = sheetList.get(i);
			
			//map转linkmap
			/*String selectFiledSql = "select COLUMNNAME from xxx  where prdt_no='xxxx' order by num";
			tableFileds = dataQueryDao.selectData(selectFiledSql);
			List<String> filedList = new ArrayList<>();//execl头;
			for (Map<String, Object> filed : tableFileds) {
				filedList.add((String) filed.get("COLUMNNAME"));//execl头按顺序添加
			}
			for (Map<String, Object> dataMap : list) {
				LinkedHashMap<String, Object> filedSort = new LinkedHashMap<String, Object>();
				for(String filed : filedList) {
					if(dataMap.containsKey(filed)) {
						filedSort.put(filed, dataMap.get(filed));
					}
				}
				
				listSort.add(filedSort);
			}*/
			//最前面添加字段名 ，表头
			indexTmp = list.get(0);
			Iterator<String> iterator = indexTmp.keySet().iterator();
			while (iterator.hasNext()) {
				String key = iterator.next();
				index.put(key, key);//
				//index.put(key, "备注");//修改表头key为备注,map.get(key)
			}
			listAll.add(index);//
			listAll.addAll(list);
			//listAll.addAll(listSort);
			try{
				LOGGER.info("开始写execl文件");
				excel.writeExcel(listAll, filepath);
				LOGGER.info("execl文件写入成功");
			} catch (IOException e) {
				LOGGER.error(ErrCode.TC019.desc() + "：写入execl表格失败");
				throw new BizException(ErrCode.TC019.code(), ErrCode.TC019.desc());
			} catch (BizException e) {
				LOGGER.error(e.getErrMsg());
				throw new BizException(e.getErrCode(), e.getErrMsg());
			}
		}
		
		/*try{
			//list = dataQueryDao.selectData("");//获取
			
			对查询的数据,按照数据库本身的查询顺序 进行排序

			String selectFiledSql = "查询excel头顺序的表";
			tableFileds = dataQueryDao.selectData(selectFiledSql);
			List<String> filedList = new ArrayList<>();//execl头按顺序排列
			for (Map<String, Object> filed : tableFileds) {
				filedList.add((String) filed.get("COLUMNNAME"));
			}
			for (Map<String, Object> dataMap : list) {
			LinkedHashMap<String, Object> filedSort = new LinkedHashMap<String, Object>();
			for(String filed : filedList) {
				if(dataMap.containsKey(filed)) {
					filedSort.put(filed, dataMap.get(filed));
				}
			}
			
			listSort.add(filedSort);
		}

		 最前面添加字段名 
		indexTmp = listSort.get(0);
		Iterator<String> iterator = indexTmp.keySet().iterator();
		while (iterator.hasNext()) {
			String key = iterator.next();
			index.put(key, key);
		}
		listAll.add(index);//
		listAll.addAll(listSort);

		log.info("开始写execl文件");
		excel.writeExcel(listAll, filepath);
		log.info("execl文件写入成功");
	} catch (IOException e) {
		log.error(ErrCode.TC019.desc() + "：写入execl表格失败");
		throw new BizException(ErrCode.TC019.code(), ErrCode.TC019.desc());
	} catch (BizException e) {
		log.error(e.getErrMsg());
		throw new BizException(e.getErrCode(), e.getErrMsg());
	}
		*/
	}
	
	
	public static void main(String[] args) 
	{
		objectMapper.setSerializationInclusion(JsonInclude.Include.NON_NULL);
		// 使用BigDecimal取代浮点类型，以免精度丢失
		objectMapper.configure(
				DeserializationFeature.USE_BIG_DECIMAL_FOR_FLOATS, true);
		objectMapper.configure(
				DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES, false);  
		ExcelUtil excel = new ExcelUtil();		
		Map<String,String> requestDef = new HashMap<String, String>();
		Map<String,String> DefMap = new HashMap<String, String>();
		Map<String,String> requestValue = new HashMap<String, String>();
		Map<String, Object> bizContent = new HashMap<String, Object>();
		LinkedHashMap<String, Object> linkMap = new LinkedHashMap<String, Object>();
		List<Map<String,Object>> contentList = new ArrayList<>();
		List<Map<String,Object>> certInfoArray = new ArrayList<>();
		List<Map<String,Object>> handlerInfoArray = new ArrayList<>();
		List<Map<String,Object>> contactInfoArray = new ArrayList<>();
		List<Map<String,Object>> vedioInfoArray = new ArrayList<>();
		List<List<Map<String,Object>>> sheetList = new ArrayList<>();
		List<Map<String, Object>> listAll = new ArrayList<Map<String, Object>>();
		List<Map<String, Object>> listSort = new ArrayList<Map<String, Object>>();	//按数据库表本身查询的字段顺序排序后的datalist
		List<Map<String, Object>> list = new ArrayList<>();
		List<Map<String, Object>> tableFileds = null;	//查询相关表字段
		Map<String, Object> index = new LinkedHashMap<>();
		Map<String, Object> indexTmp = null;
		String filepath = "D:\\IT\\data.xls";
		String appValue = "{\"shop_info\":\"{\\\"business_license_url\\\": \\\"4fd\\\",\\\"business_address\\\": \\\"beijin-shangdi-yqh\\\",\\\"business_license_number\\\": \\\"510902199511188694\\\",\\\"business_company_name\\\":\\\"4fd\\\",\\\"company_name\\\":\\\"4fd\\\",\\\"business_license_number_expire_date\\\": \\\"21300630\\\",\\\"business_address_is_me\\\": \\\"0\\\",\\\"business_address_e\\\": \\\"\\\"}\",\"base_info\": \"{\\\"bind_card_openbank_code\\\":\\\"ICBC\\\",\\\"bank_card_no\\\": \\\"6227000330022426955\\\",\\\"phone_no\\\":\\\"18335834703\\\",\\\"idno_url_f\\\": \\\"https://www-test.4paradigm.com/image/about-jingyan.png?v=429bd49d63\\\",\\\"address\\\": \\\"beij\\\",\\\"name\\\": \\\"崔彩虹180\\\",\\\"idno\\\":\\\"510902199511188694\\\",\\\"idno_url_o\\\":\\\"https://www-test.4paradigm.com/image/about-business.jpg?v=9f405afbb3\\\"}\"}";
		String appDef="{\"ApplyFieldDef\":\"{\\\"shop_info\\\": \\\"Map\\\", \\\"base_info\\\": \\\"Map\\\"}\"}";
		String yuDef="shop_info:Map,base_info:Map";
		String yuName[];
		String yuType[];
		try {
			requestValue = objectMapper.readValue(appValue, Map.class);
			LOGGER.info("appvalue序列化后报文{}", requestValue);
			requestDef = objectMapper.readValue(appDef, Map.class);
			LOGGER.info("appdef序列化后报文{}", requestDef);
			DefMap = objectMapper.readValue(requestDef.get("ApplyFieldDef"), Map.class);
		} catch (IOException e) {
			LOGGER.error("序列化异常，报文格式错误", e);
		}
		/*
		 * 过滤特殊字符、空格、转义字符和报文中的ApplyFieldDef字段
		 * */
		String Temp=appDef;
		String appDefTmp=FilterStr(Temp);
		appDefTmp = appDefTmp.replace("ApplyFieldDef:", "");

		LOGGER.info(appDefTmp);
		/*对比报文与预设格式 顺序字段是否完全一致*/
		if(appDefTmp.compareTo(yuDef)!=0){
			throw new BizException("","传入类型与预设类型不符");
		}
		String[] yuDefstr  = yuDef.split(",");
		
		/*	按照类型组装excel，
		 *  多个map合并为一个sheet页，剩下每个list一个sheet页，表现形式为同一个list
		 *  sheet页目前是按map组合为第一页，剩下list按顺序依次创建
		 *  同一个sheet页中的多条记录按表中顺序依次插入；
		 * */
		for (int i = 0; i < yuDefstr.length; i++) {
			String defType = DefMap.get(yuDefstr[i].split(":")[0]);
			Object valueStr = requestValue.get(yuDefstr[i]);
			/*if("Map".equals(defType)){
				requestValue = objectMapper.readValue((String)valueStr, Map.class);
				
				//map转linkmap
				//String selectFiledSql = "select COLUMNNAME from xxx  where prdt_no='xxxx' order by num";
				tableFileds = dataQueryDao.selectData(selectFiledSql);
				List<String> filedList = new ArrayList<>();//execl头;
				for (Map<String, Object> filed : tableFileds) {
					filedList.add((String) filed.get("COLUMNNAME"));//execl头按顺序添加
				}
				LinkedHashMap<String, Object> filedSort = new LinkedHashMap<String, Object>();
				for(String filed : filedList) {
					if(requestValue.containsKey(filed)) {
						filedSort.put(filed, requestValue.get(filed));
					}else{
						//看需求是报错还是空串还是跳过该列
						filedSort.put(filed, "");
					}
				}
				linkMap.putAll(filedSort);
			}else if("List".equals(defType)){
				//map转linkmap
				//String selectFiledSql = "select COLUMNNAME from xxx  where prdt_no='xxxx' order by num";
				tableFileds = dataQueryDao.selectData(selectFiledSql);
				List<String> filedList = new ArrayList<>();//execl头;
				for (Map<String, Object> filed : tableFileds) {
					filedList.add((String) filed.get("COLUMNNAME"));//execl头按顺序添加
				}
				LinkedHashMap<String, Object> filedSort = new LinkedHashMap<String, Object>();
				for (Map<String, Object> dataMap : list) {
					for(String filed : filedList) {
						if(requestValue.containsKey(filed)) {
							filedSort.put(filed, requestValue.get(filed));
						}else{
						//看需求是报错还是空串还是跳过该列
							filedSort.put(filed, "");
						}
					}
				}
				sheetList.add(filedSort);
			}*/
			
			
		}

		contentList.add(linkMap);
		sheetList.add(0,contentList);
		for(int i = 0 ;i<sheetList.size();i++) {
			if(i!=0){
				excel.isOverWrite=false;//根据具体交易确定是否需要覆盖文件，建议在第一次任务时候覆盖
			}
			excel.sheetName="sheet"+i;
			listAll.clear();
			list.clear();
			listSort.clear();
			index.clear();
			list = sheetList.get(i);
			
			//最前面添加字段名 ，表头
			indexTmp = list.get(0);
			Iterator<String> iterator = indexTmp.keySet().iterator();
			while (iterator.hasNext()) {
				String key = iterator.next();
				index.put(key, key);//
				//index.put(key, "备注");//修改表头key为备注,map.get(key),map可以查表获取
			}
			listAll.add(index);//
			listAll.addAll(list);
			//listAll.addAll(listSort);
			try{
				LOGGER.info("开始写execl文件");
				excel.writeExcel(listAll, filepath);
				LOGGER.info("execl文件写入成功");
			} catch (IOException e) {
				LOGGER.error(ErrCode.TC019.desc() + "：写入execl表格失败");
				throw new BizException(ErrCode.TC019.code(), ErrCode.TC019.desc());
			} catch (BizException e) {
				LOGGER.error(e.getErrMsg());
				throw new BizException(e.getErrCode(), e.getErrMsg());
			}
		}
		

	}

	public static String FilterStr(String str) throws PatternSyntaxException
	{
		/**
		 * 特殊字符,不包括：,
		 */
		String regEx="[`~!@#$%^&*()+=|{}';'\\[\\].<>/?~！@#￥%……&*（）——+|{}【】‘；：”“’。，、？\" \\\\]";
		
 
		Pattern pat = Pattern.compile(regEx);     
        Matcher mat = pat.matcher(str);
        String res = mat.replaceAll("").trim();

        return res;  
	}

}
