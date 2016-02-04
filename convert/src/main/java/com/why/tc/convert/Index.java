package com.why.tc.convert;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.List;

import org.apache.commons.cli.CommandLine;
import org.apache.commons.cli.CommandLineParser;
import org.apache.commons.cli.HelpFormatter;
import org.apache.commons.cli.Options;
import org.apache.commons.cli.ParseException;
import org.apache.commons.cli.PosixParser;
import org.apache.poi.xssf.usermodel.XSSFRow;
import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;

import com.why.tc.pack.Pack;

public class Index {

	public static void main(String[] args) throws IOException {

		Options opt = new Options();
		opt.addOption("f", "from", true, "origin of execl");

		opt.addOption("t", "to", true, "xml file");

		opt.addOption("s", "sheet", true, "execl sheet name");

		opt.addOption("v", "v", true, "version of testlink 1.8 or 1.9");

		opt.addOption("h", "help", false, "print help for the command.");

		String formatstr = "command [-f/--from][-t/--to][-s/--sheet][-v/--version][-h/--help]";

		HelpFormatter formatter = new HelpFormatter();
		CommandLineParser parser = new PosixParser();
		CommandLine cl = null;
		try {

			cl = parser.parse(opt, args);
		} catch (ParseException e) {
			formatter.printHelp(formatstr, opt);
		}

		if (cl.hasOption("h")) {
			formatter.printHelp(formatstr, opt);
			return;
		}

		String execl_name;
		if (cl.hasOption("f")) {
			execl_name = cl.getOptionValue("f");
		} else {
			formatter.printHelp(formatstr, opt);
			return;
		}

		String xml_name;
		if (cl.hasOption("t")) {
			xml_name = cl.getOptionValue("t");
		} else {
			formatter.printHelp(formatstr, opt);
			return;
		}

		String sheet_name;
		if (cl.hasOption("s")) {
			sheet_name = cl.getOptionValue("s");
		} else {
			formatter.printHelp(formatstr, opt);
			return;
		}

		String version;
		if (cl.hasOption("v")) {
			version = cl.getOptionValue("v");
		} else {
			formatter.printHelp(formatstr, opt);
			return;
		}

		String path = System.getProperty("user.dir") + "/";

		File origin = new File(path + execl_name);
		InputStream is = new FileInputStream(origin);
		XSSFWorkbook wb = new XSSFWorkbook(is);

		XSSFSheet st = wb.getSheet(sheet_name);
		int rowNum = st.getLastRowNum();

		List<TestCase> all = new ArrayList<TestCase>();

		// start from second row
		for (int rowIndex = 1; rowIndex <= rowNum; rowIndex++) {
			XSSFRow row = st.getRow(rowIndex);

			if (row.getLastCellNum() < 6) {
				continue;
			}

			TestCase tc = new TestCase();
			tc.setName(row.getCell(0).getStringCellValue());
			tc.setSummary(row.getCell(1).getStringCellValue());
			tc.setPrecondition(row.getCell(2).getStringCellValue());
			tc.setType(row.getCell(3).getStringCellValue());
			tc.setImportance(row.getCell(4).getStringCellValue());
			tc.setSteps(row.getCell(5).getStringCellValue());
			tc.setExpect(row.getCell(6).getStringCellValue());
			all.add(tc);
		}

		wb.close();

		Pack pc = new Pack();

		String content = null;

		if (version.equals("1.8")) {
			content = pc.pack18(all);
		} else {
			content = pc.pack19(all);
		}

		if (content == null) {
			return;
		}

		FileOutputStream out = new FileOutputStream(new File(path + xml_name));
		out.write(content.getBytes("utf-8"));
		out.close();
	}
}
