package cz.borec.demo.gui.print;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.Locale;

import cz.borec.demo.AppProperties;
import cz.borec.demo.Constants;
import cz.borec.demo.core.dto.OrderDTO;
import cz.borec.demo.core.dto.OrderItemDTO;
import cz.borec.demo.core.dto.SummarizedOrderDTO;
import cz.borec.demo.gui.controls.AppPropertiesProxy;
import cz.borec.demo.gui.controls.PrintLabel;

public class BillBuilder {


	private static DateFormat formatData = new SimpleDateFormat("d.MM.yyyy H:mm");
    
	public static ArrayList<String> getHeader(OrderDTO o) {
		ArrayList<String> lines = new ArrayList<String>();
		String s;
		s = AppPropertiesProxy.get(Constants.CONFIG_RESTAURANT_NAME);
		lines.add(s);
		s = AppPropertiesProxy.get(Constants.CONFIG_CUSTOMER_NAME);
		lines.add(s);
		s = AppPropertiesProxy.get(Constants.CONFIG_CUSTOMER_STREET);
		lines.add(s);
		s = AppPropertiesProxy.get(Constants.CONFIG_CUSTOMER_ADDRESS);
		lines.add(s);
		s = "I\u010CO: " + AppProperties.getProperties().getCustomerICO();
		lines.add(s);
		s = "Datum: " + formatData.format(!o.isSummarized() ? o.getDate() : new Date());
		lines.add(s);
		if (!o.isSummarized()) {
			s = "M\u00EDsto: "
					+ (o.getTableDTO() != null ? o.getTableDTO().getName()
							+ (o.getFullName() == null ? "" : " (" + o.getFullName() + ")")
							: o
							.getFullName());
			lines.add(s);
		}
		s = "line";
		lines.add(s);
		if (!o.isSummarized()) {
			s = "\u00DA\u010CET \u010D.: " + o.getId();
			lines.add(s);
			s = "FIK: " + (o.getFIK() == null ? "Neodesl\u00E1no" : o.getFIK());
			lines.add(s);
		} else {
			s = "P\u0158EHLED TR\u017DEB";
			lines.add(s);
			s = "Datum od: " + formatData.format(o.getDate());
			lines.add(s);
			s = "Datum do: "
					+ formatData.format(((SummarizedOrderDTO) o).getDateTo());
			lines.add(s);
		}

		s = "\n";
		lines.add(s);
				s = "line";
		lines.add(s);

		s = "Po\u010Det (DPH)      Cena \tCelkem";
		lines.add(s);
		s = "line";
		lines.add(s);
		lines.add(s);

		return lines;

	}

	public static ArrayList<String[]> getLines(OrderDTO o) {
		ArrayList<String[]> lines = new ArrayList<String[]>();
		for (OrderItemDTO orderItemDTO : o.getItems()) {
			String[] s = new String[4];
			s[0] = orderItemDTO.getProductName();
			s[1] = orderItemDTO.getAmount() + " ks  (" + orderItemDTO.getProduct().getCategory().getVat()/* + " - " + orderItemDTO.getVatValue() */ + ")";
			s[2] = !o.isSummarized() ? orderItemDTO.getPrice().toString() : orderItemDTO.getProduct().getPrice().toString();
			s[3] = !o.isSummarized() ? orderItemDTO.getPriceTotal().toString() : orderItemDTO.getPrice().toString();
			lines.add(s);
		}

		return lines;
	}

	public static ArrayList<String> getFooter(OrderDTO o) {
		ArrayList<String> lines = new ArrayList<String>();
		String s;
		s = "line";
		lines.add(s);

		if (o.getDiscount().doubleValue() > 0) {
			s = "Sleva:     \t\t -" + o.getDiscount().toString();
			lines.add(s);
		}
		s = "DPH (k\u010D): \t\t" + o.getVatAmount();
		lines.add(s);

		s = "CELKEM (k\u010D): \t" + o.getSumFormattedAfterDiscount();
		lines.add(s);

		s = "\n";
		lines.add(s);

		s = "\n";
		lines.add(s);

		if (!o.isSummarized()) {
			s = "D\u011Bkujeme za n\u00E1v\u0161t\u011Bvu.";
			lines.add(s);
			s = "\n";
			lines.add(s);
		}

		s = ".";
		lines.add(s);

		for (String string : lines) {
			System.out.println(string);
		}

		return lines;
	}

}
