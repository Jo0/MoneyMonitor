package Reporting.Models;

import java.io.ByteArrayInputStream;
import java.io.InputStream;
import java.nio.charset.Charset;
import java.util.ArrayList;

import Core.Models.Category;
import Core.Services.ReportBuilder;

public class HtmlDocument {
	public HtmlDocument(String reportTitle, String tableTitle, HtmlTable table, HtmlTable summaryTable, ReportBuilder.SortingType sortType)
	{
		
		title = reportTitle;
		tableTitleText = tableTitle;
		htmlTable = table;
		_summaryTable = summaryTable;
	}
	
	public ArrayList<Category> _categories;
	public String title = "MoneyMonitor Report";
	public String tableTitleText = "There is no information in this report.";
	public String footerText = "This is a report generated by the Money Monitor project";
	public HtmlTable htmlTable;
	public HtmlTable _summaryTable;
	
	public String GetHtml () throws Exception
	{
		if(htmlTable == null)
		{
			throw new Exception("No table defined for html document.");
		}
		
		return "<link href=\"bootstrap.min.css\" rel=\"stylesheet\" type=\"text/css\" />"
				+ "<link href=\"style.css\" rel=\"stylesheet\" type=\"text/css\" />"
				+ "<header class=\"page-header\">" + title + "</header>"
				+ "<html>"
				+ "<head>"
				+ "<title>" + title + "</title>"
				+ "</head>"
				+ "<body>"
				+ "<div class=\"container\">"
				+ "<div class=\"row\">"
				+ "<div class=\"col-xs-3 mmTableHolder\">"
				+ "<h1>Summary</h1>"
				+ "<table class=\"table table-bordered mmTable\">"
				+ GetTableHtml(_summaryTable)
				+ "</table>"
				+ "</div>"
				+ "<div class=\"col-xs-1\"></div>"
				+ "<div class=\"col-xs-8 mmTableHolder\">"
				+ "<h1>" + tableTitleText + "</h1>"
				+ "<table class=\"table table-striped table-bordered mmTable\">"
				+ GetTableHtml(htmlTable)
				+ "</table>"
				+ "</div>"
				+ "</div>"
				+ "</div>"
				+ "</body>"
				+ "</html>"
				+ "<footer>" + footerText + "</footer>";
	}
	
	
	private String GetTableHtml(HtmlTable table)
	{
		String tableHtml = "<thead><tr>";
		for(int x = 0; x < table.Columns.size(); x++)
		{
			HtmlColumn col = table.Columns.get(x);
			tableHtml += "<th>" + col.columnName + "</th>";
			
		}
		tableHtml += "</tr></thead>";
		tableHtml += "<tbody>";
		for(int x = 0; x < table.Rows.size(); x++)
		{
			HtmlRow row = table.Rows.get(x);
			tableHtml += "<tr>";
			for(int y = 0; y < row.Columns.size(); y++)
			{
				String columnValue = row.Values[y];
				tableHtml += "<td>" + columnValue + "</td>";
				
			}
			tableHtml += "</tr>";
		}
		tableHtml += "</tbody>";
		return tableHtml;
	}
	
	public InputStream GetHtmlStream() throws Exception
	{
		return new ByteArrayInputStream( GetHtml().getBytes( Charset.defaultCharset() ) );
	}
}
