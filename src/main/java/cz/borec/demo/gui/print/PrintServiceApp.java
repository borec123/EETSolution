package cz.borec.demo.gui.print;

/*
 * JasperReports - Free Java Reporting Library.
 * Copyright (C) 2001 - 2016 TIBCO Software Inc. All rights reserved.
 * http://www.jaspersoft.com
 *
 * Unless you have purchased a commercial license agreement from Jaspersoft,
 * the following license terms apply:
 *
 * This program is part of JasperReports.
 *
 * JasperReports is free software: you can redistribute it and/or modify
 * it under the terms of the GNU Lesser General Public License as published by
 * the Free Software Foundation, either version 3 of the License, or
 * (at your option) any later version.
 *
 * JasperReports is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE. See the
 * GNU Lesser General Public License for more details.
 *
 * You should have received a copy of the GNU Lesser General Public License
 * along with JasperReports. If not, see <http://www.gnu.org/licenses/>.
 */

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;

import javax.print.attribute.HashPrintRequestAttributeSet;
import javax.print.attribute.HashPrintServiceAttributeSet;
import javax.print.attribute.PrintRequestAttributeSet;
import javax.print.attribute.PrintServiceAttributeSet;
import javax.print.attribute.standard.MediaSizeName;

import cz.borec.demo.AppProperties;
import cz.borec.demo.Constants;
import cz.borec.demo.PrinterWidth;
import cz.borec.demo.gui.controls.AppPropertiesProxy;
import net.sf.jasperreports.engine.JRException;
import net.sf.jasperreports.engine.JRImageRenderer;
import net.sf.jasperreports.engine.JRPrintElement;
import net.sf.jasperreports.engine.JRPrintImage;
import net.sf.jasperreports.engine.JRPrintLine;
import net.sf.jasperreports.engine.JRPrintPage;
import net.sf.jasperreports.engine.JRPrintText;
import net.sf.jasperreports.engine.JRStyleContainer;
import net.sf.jasperreports.engine.JasperPrint;
import net.sf.jasperreports.engine.JasperPrintManager;
import net.sf.jasperreports.engine.base.JRBasePrintImage;
import net.sf.jasperreports.engine.base.JRBasePrintLine;
import net.sf.jasperreports.engine.base.JRBasePrintPage;
import net.sf.jasperreports.engine.base.JRBasePrintText;
import net.sf.jasperreports.engine.design.JRDesignStyle;
import net.sf.jasperreports.engine.export.JRPrintServiceExporter;
import net.sf.jasperreports.engine.type.HorizontalTextAlignEnum;
import net.sf.jasperreports.engine.type.ScaleImageEnum;
import net.sf.jasperreports.engine.util.AbstractSampleApp;
import net.sf.jasperreports.engine.util.JRLoader;
import net.sf.jasperreports.engine.util.JRSaver;
import net.sf.jasperreports.export.SimpleExporterInput;
import net.sf.jasperreports.export.SimplePrintServiceExporterConfiguration;

/**
 * @author Teodor Danciu (teodord@users.sourceforge.net)
 */
public class PrintServiceApp extends AbstractSampleApp {

	/**
	 * @throws JRException 
	 *
	 */
	public static void main(String[] args) throws JRException {
		main(new PrintServiceApp(), args);
	}

	private ArrayList<String[]> lines;
	private int width = 135;
	private int height = 300;
	private ArrayList<String> header;
	private ArrayList<String> footer;
	private JRDesignStyle normalStyle;
	private byte[] a;
	private int x;
	private float fontsize;
	private JRDesignStyle boldStyle;

	/**
	 *
	 */
	public void test() throws JRException {
	}

	/**
	 *
	 */
	public void fill() throws JRException {
		long start = System.currentTimeMillis();
		JasperPrint jasperPrint = getJasperPrint();
		JasperPrintManager.printPages(jasperPrint, 0, jasperPrint.getPages()
				.size() - 1, Boolean.valueOf(AppPropertiesProxy.get(Constants.CONFIG_PRINTING_DIALOG)));
		System.err.println("Printing time : "
				+ (System.currentTimeMillis() - start));
		/*
		 * JRSaver.saveObject(jasperPrint, "PrintServiceReport.jrprint");
		 * System.err.println("Filling time : " + (System.currentTimeMillis() -
		 * start));
		 */}

	/**
	 *
	 */
	/*
	 * public void print() throws JRException { long start =
	 * System.currentTimeMillis(); PrintRequestAttributeSet
	 * printRequestAttributeSet = new HashPrintRequestAttributeSet();
	 * printRequestAttributeSet.add(MediaSizeName.ISO_A8);
	 * 
	 * PrintServiceAttributeSet printServiceAttributeSet = new
	 * HashPrintServiceAttributeSet(); // printServiceAttributeSet.add(new //
	 * PrinterName("Epson Stylus 820 ESC/P 2", null)); //
	 * printServiceAttributeSet.add(new // PrinterName("hp LaserJet 1320 PCL 6",
	 * null)); // printServiceAttributeSet.add(new PrinterName("PDFCreator",
	 * null));
	 * 
	 * JRPrintServiceExporter exporter = new JRPrintServiceExporter();
	 * 
	 * exporter.setExporterInput(new SimpleExporterInput(
	 * "PrintServiceReport.jrprint")); SimplePrintServiceExporterConfiguration
	 * configuration = new SimplePrintServiceExporterConfiguration();
	 * configuration.setPrintRequestAttributeSet(printRequestAttributeSet);
	 * configuration.setPrintServiceAttributeSet(printServiceAttributeSet);
	 * configuration.setDisplayPageDialog(new Boolean(false));
	 * configuration.setDisplayPrintDialog(new Boolean(false));
	 * exporter.setConfiguration(configuration); exporter.exportReport();
	 * 
	 * System.err.println("Printing time : " + (System.currentTimeMillis() -
	 * start)); }
	 */

	/**
	 *
	 */
	private JasperPrint getJasperPrint() throws JRException {
		// JasperPrint
		JasperPrint jasperPrint = new JasperPrint();
		jasperPrint.setName("NoReport");
		jasperPrint.setPageWidth(this.width);
		jasperPrint.setPageHeight(this.height);

		// Fonts
		normalStyle = new JRDesignStyle();
		normalStyle.setName("Sans_Normal");
		normalStyle.setDefault(true);
		normalStyle.setFontName("Arial");
		normalStyle.setFontSize(new Float(6f));
		normalStyle.setPdfFontName("Helvetica");
		normalStyle.setPdfEncoding("UTF-8");
		normalStyle.setPdfEmbedded(false);
		jasperPrint.addStyle(normalStyle);

		boldStyle = new JRDesignStyle();
		boldStyle.setName("Sans_Bold");
		boldStyle.setFontName("Arial");
		boldStyle.setFontSize(new Float(8f));
		boldStyle.setBold(true);
		boldStyle.setPdfFontName("Helvetica-Bold");
		boldStyle.setPdfEncoding("UTF-8");
		boldStyle.setPdfEmbedded(false);
		jasperPrint.addStyle(boldStyle);

		JRDesignStyle italicStyle = new JRDesignStyle();
		italicStyle.setName("Sans_Italic");
		italicStyle.setFontName("Arial");
		italicStyle.setFontSize(new Float(8f));
		italicStyle.setItalic(true);
		italicStyle.setPdfFontName("Helvetica-Oblique");
		italicStyle.setPdfEncoding("UTF-8");
		italicStyle.setPdfEmbedded(false);
		jasperPrint.addStyle(italicStyle);

		JRPrintPage page = new JRBasePrintPage();
		JRPrintPage firstPage = page;
		//jasperPrint.addPage(firstPage);

		int y = 0;
		if (AppProperties.getProperties().isPrintLogo()) {
			JRPrintImage image = new JRBasePrintImage(
					jasperPrint.getDefaultStyleProvider());
			image.setX((this.width - x) / 2 - 30 + x);
			image.setY(y);
			image.setWidth(60);
			int imageHeight = 60;
			image.setHeight(imageHeight);
			y += imageHeight;
			image.setScaleImage(ScaleImageEnum.CLIP);
			image.setRenderable(JRImageRenderer.getInstance(a));
			page.addElement(image);
		}


		int i = 0;
		for (String line1 : header) {
			if ("line".equals(line1)) {
				JRPrintLine line = new JRBasePrintLine(
						jasperPrint.getDefaultStyleProvider());
				line.setX(x);
				line.setY(y);
				line.setWidth(this.width);
				line.setHeight(0);
				y += 3;
				page.addElement(line);
			} else {
				JRPrintText text = new JRBasePrintText(
						jasperPrint.getDefaultStyleProvider());
				text.setX(x);
				text.setY(y);
				text.setWidth(this.width - 10);
				text.setHeight(15);
				text.setTextHeight(text.getHeight());
				text.setHorizontalTextAlign((i++ < 7) ? HorizontalTextAlignEnum.CENTER
						: HorizontalTextAlignEnum.LEFT);
				text.setLineSpacingFactor(1.3133681f);
				text.setLeadingOffset(-4.955078f);
				text.setStyle(normalStyle);
				text.setFontSize(new Float(fontsize));
				text.setText("\n".equals(line1) ? " " : line1);
				page.addElement(text);
				y += text.getFontSize() + 3;
			}
			if (y > this.height - 10) {
				//if (firstPage != page) {
					jasperPrint.addPage(page);
					page = new JRBasePrintPage();
					y = 0;
				//}
			}
		}
/*		if (firstPage != page) {
			jasperPrint.addPage(page);
		}
*/
		for (String[] line1 : lines) {

			JRPrintText text = new JRBasePrintText(
					jasperPrint.getDefaultStyleProvider());
			text.setX(this.x);
			text.setY(y);
			text.setWidth(this.width - this.x - 10);
			text.setHeight(15);
			text.setTextHeight(text.getHeight());
			text.setHorizontalTextAlign((i++ < 7) ? HorizontalTextAlignEnum.CENTER
					: HorizontalTextAlignEnum.LEFT);
			text.setLineSpacingFactor(1.3133681f);
			text.setLeadingOffset(-4.955078f);
			text.setStyle(normalStyle);
			text.setFontSize(new Float(fontsize));
			text.setText(line1[0]);
			page.addElement(text);
			y += text.getFontSize() + 3;

			text = new JRBasePrintText(jasperPrint.getDefaultStyleProvider());
			text.setX(0);
			text.setY(y);
			text.setWidth(this.width / 10 * 3);
			text.setHeight(15);
			text.setTextHeight(text.getHeight());
			text.setHorizontalTextAlign(HorizontalTextAlignEnum.RIGHT);
			text.setLineSpacingFactor(1.3133681f);
			text.setLeadingOffset(-4.955078f);
			text.setStyle(normalStyle);
			text.setFontSize(new Float(fontsize));
			text.setText(line1[1]);
			page.addElement(text);
			// y += text.getFontSize() + 3;

			text = new JRBasePrintText(jasperPrint.getDefaultStyleProvider());
			text.setX((this.width - this.x) / 10 * 3);
			text.setY(y);
			text.setWidth((this.width - this.x) / 10 * 3);
			text.setHeight(15);
			text.setTextHeight(text.getHeight());
			text.setHorizontalTextAlign(HorizontalTextAlignEnum.RIGHT);
			text.setLineSpacingFactor(1.3133681f);
			text.setLeadingOffset(-4.955078f);
			text.setStyle(normalStyle);
			text.setFontSize(new Float(fontsize));
			text.setText(line1[2]);
			page.addElement(text);
			// y += text.getFontSize() + 3;

			text = new JRBasePrintText(jasperPrint.getDefaultStyleProvider());
			text.setX((this.width - this.x) / 10 * 6);
			text.setY(y);
			text.setWidth((this.width - this.x) / 10 * 4);
			text.setHeight(15);
			text.setTextHeight(text.getHeight());
			text.setHorizontalTextAlign(HorizontalTextAlignEnum.RIGHT);
			text.setLineSpacingFactor(1.3133681f);
			text.setLeadingOffset(-4.955078f);
			text.setStyle(normalStyle);
			text.setFontSize(new Float(fontsize));
			text.setText(line1[3]);
			page.addElement(text);
			y += text.getFontSize() + 3;

			if (y > this.height - 10) {
				//if (firstPage != page) {
					firstPage = page;
					jasperPrint.addPage(page);
					page = new JRBasePrintPage();
					y = 0;
				//}
			}

		}
		/*
		 * if (firstPage != page) { jasperPrint.addPage(page); }
		 */
		for (String line1 : footer) {
			if ("line".equals(line1)) {
				JRPrintLine line = new JRBasePrintLine(
						jasperPrint.getDefaultStyleProvider());
				line.setX(x);
				line.setY(y);
				line.setWidth(this.width);
				line.setHeight(0);
				y += 3;
				page.addElement(line);
			} else {
				JRPrintText text = new JRBasePrintText(
						jasperPrint.getDefaultStyleProvider());
				text.setX(x);
				text.setY(y);
				text.setWidth(this.width - x - 10);
				text.setHeight(15);
				text.setTextHeight(text.getHeight());
				boolean celkem = line1.startsWith("CELKEM");
				text.setHorizontalTextAlign((celkem  || line1.startsWith("Sleva") || line1.startsWith("DPH")) ? HorizontalTextAlignEnum.RIGHT
						: HorizontalTextAlignEnum.LEFT);
				text.setLineSpacingFactor(1.3133681f);
				text.setLeadingOffset(-4.955078f);
				text.setStyle(celkem ? boldStyle : normalStyle);
				text.setFontSize(new Float(fontsize + (celkem ? 2 : 0)));
				text.setText("\n".equals(line1) ? " " : line1);
				page.addElement(text);
				y += text.getFontSize() + 3;
			}
			if (y > this.height - 10) {
				//if (firstPage != page) {
					firstPage = page;
					jasperPrint.addPage(page);
					page = new JRBasePrintPage();
					y = 0;
				//}
			}
		}
		//if (firstPage != page) {
			jasperPrint.addPage(page);
		//}

		/*
		 * text = new JRBasePrintText(jasperPrint.getDefaultStyleProvider());
		 * text.setX(210); text.setY(85); text.setWidth(325);
		 * text.setHeight(15); text.setTextHeight(text.getHeight());
		 * text.setHorizontalTextAlign(HorizontalTextAlignEnum.RIGHT);
		 * text.setLineSpacingFactor(1.329241f);
		 * text.setLeadingOffset(-4.076172f); text.setStyle(italicStyle);
		 * text.setFontSize(new Float(12f)); text.setText((new
		 * SimpleDateFormat("EEE, MMM d, yyyy")).format(new Date()));
		 * page.addElement(text);
		 * 
		 * text = new JRBasePrintText(jasperPrint.getDefaultStyleProvider());
		 * text.setX(40); text.setY(150); text.setWidth(515);
		 * text.setHeight(200); text.setTextHeight(text.getHeight());
		 * text.setHorizontalTextAlign(HorizontalTextAlignEnum.JUSTIFIED);
		 * text.setLineSpacingFactor(1.329241f);
		 * text.setLeadingOffset(-4.076172f); text.setStyle(normalStyle);
		 * text.setFontSize(new Float(14f)); text.setText(
		 * "JasperReports is a powerful report-generating tool that has the ability to deliver rich content onto the screen, to the printer or into PDF, HTML, XLS, CSV or XML files.\n\n"
		 * +
		 * "It is entirely written in Java and can be used in a variety of Java enabled applications, including J2EE or Web applications, to generate dynamic content.\n\n"
		 * +
		 * "Its main purpose is to help creating page oriented, ready to print documents in a simple and flexible manner."
		 * ); page.addElement(text);
		 */

		return jasperPrint;
	}

	public PrintServiceApp()  {
		super();
		if (AppProperties.getProperties().isPrintLogo()) {
			try {
				a = JRLoader.loadBytesFromResource("images/printlogo.png");
			} catch (JRException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
		PrinterWidth printerWidth = AppProperties.getProperties().getPrinterWidth();
		switch (printerWidth) {
		case mm58:
			 width = 135;
			height = 300;
			x = 0;
			 this.fontsize = 7f;
			break;

		case mm76:
			width = 200;
			 height = 400;
			 x = 20;
			 this.fontsize = 9f;
			break;

		case mm80:
			width = 220;
			 height = 400;
			break;

		default:
			break;
		}
	}
	

	/*
	 * private JRPrintElement createJRPrintElement(String line1, Integer y,
	 * Integer i, JasperPrint jasperPrint) {
	 * 
	 * if ("line".equals(line1)) { JRPrintLine line = new JRBasePrintLine(
	 * jasperPrint.getDefaultStyleProvider()); line.setX(0); line.setY(y);
	 * line.setWidth(this.width); line.setHeight(0); y += 3; return line; } else
	 * { JRPrintText text = new JRBasePrintText(
	 * jasperPrint.getDefaultStyleProvider()); text.setX(0); text.setY(y);
	 * text.setWidth(this.width - 10); text.setHeight(15);
	 * text.setTextHeight(text.getHeight()); text.setHorizontalTextAlign((i++ <
	 * 6 || i++ == lines.size() - 2) ? HorizontalTextAlignEnum.CENTER :
	 * HorizontalTextAlignEnum.LEFT); text.setLineSpacingFactor(1.3133681f);
	 * text.setLeadingOffset(-4.955078f); text.setStyle(normalStyle);
	 * text.setFontSize(new Float(7f)); text.setText("\n".equals(line1) ? " " :
	 * line1); y += text.getFontSize() + 3; return text; }
	 * 
	 * }
	 */
	public void setLines(ArrayList<String[]> lines) {
		this.lines = lines;

	}

	public ArrayList<String[]> getLines() {
		return lines;
	}

	public void setHeader(ArrayList<String> header) {
		this.header = header;

	}

	public void setFooter(ArrayList<String> footer) {
		this.footer = footer;

	}

}
