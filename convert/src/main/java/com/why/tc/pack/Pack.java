package com.why.tc.pack;

import java.util.List;

import com.why.tc.convert.TestCase;

public class Pack {
	public String pack18(List<TestCase> all) {
		StringBuilder content = new StringBuilder(1024);
		content.append("<?xml version=\"1.0\" encoding=\"UTF-8\"?>")
				.append("\r\n").append("<testcases>").append("\r\n");
		for (int i = 0, size = all.size(); i < size; i++) {
			TestCase tc = all.get(i);
			content.append("<testcase internalid=\"666\" name=\"")
					.append(tc.getName()).append("\">").append("\r\n\t")
					.append("<summary><![CDATA[").append(tc.getSummary())
					.append("]]></summary>").append("\r\n\t")
					.append("<steps><![CDATA[").append(tc.getSteps())
					.append("]]></steps>").append("\r\n\t")
					.append("<expectedresults><![CDATA[")
					.append(tc.getExpect()).append("]]></expectedresults>")
					.append("\r\n").append("</testcase>").append("\r\n");
		}
		content.append("</testcases>");
		return content.toString();
	}

	public String pack19(List<TestCase> all) {
		StringBuilder content = new StringBuilder(1024);
		content.append("<?xml version=\"1.0\" encoding=\"UTF-8\"?>")
				.append("\r\n").append("<testcases>").append("\r\n");
		for (int i = 0, size = all.size(); i < size; i++) {
			TestCase tc = all.get(i);
			content.append("<testcase internalid=\"666\" name=\"")
					.append(tc.getName()).append("\">").append("\r\n\t")
					.append("<version><![CDATA[1]]></version>")
					.append("\r\n\t").append("<summary><![CDATA[<p>")
					.append(tc.getSummary()).append("</p>]]></summary>")
					.append("\r\n\t").append("<preconditions><![CDATA[<p>")
					.append(tc.getPrecondition())
					.append("<p>]]></preconditions>").append("\r\n\t");

			if (tc.getType().equals("自动化")) {
				content.append("<execution_type><![CDATA[2]]></execution_type>")
						.append("\r\n\t");
			} else {
				content.append("<execution_type><![CDATA[1]]></execution_type>")
						.append("\r\n\t");
			}

			if (tc.getImportance().equals("高")) {
				content.append("<importance><![CDATA[3]]></importance>")
						.append("\r\n\t");
			} else if (tc.getImportance().equals("低")) {
				content.append("<importance><![CDATA[1]]></importance>")
						.append("\r\n\t");
			} else {
				content.append("<importance><![CDATA[2]]></importance>")
						.append("\r\n\t");
			}

			content.append("<steps>").append("\r\n\t\t").append("<step>")
					.append("\r\n\t\t\t")
					.append("<step_number><![CDATA[\"1\"]]></step_number>")
					.append("\r\n\t\t\t").append("<actions><![CDATA[<p>")
					.append(tc.getSteps()).append("</p>]]></actions>")
					.append("\r\n\t\t\t")
					.append("<expectedresults><![CDATA[<p>")
					.append(tc.getExpect()).append("</p>]]></expectedresults>")
					.append("\r\n\t\t").append("</step>").append("\r\n\t")
					.append("</steps>").append("\r\n").append("</testcase>")
					.append("\r\n");

		}
		content.append("</testcases>");
		return content.toString();
	}
}
