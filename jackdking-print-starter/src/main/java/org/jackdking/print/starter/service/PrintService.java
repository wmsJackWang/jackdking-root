package org.jackdking.print.starter.service;

import org.jackdking.print.starter.properties.PrintProperties;

public class PrintService {
	
	private PrintProperties printProperties;
	
	public void setPrintServiceName(PrintProperties printProperties) {
		this.printProperties = printProperties;
	}
	
	
	public PrintProperties getPrintServiceName() {
		return printProperties;
	}
	

}
