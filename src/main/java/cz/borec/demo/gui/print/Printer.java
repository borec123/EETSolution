package cz.borec.demo.gui.print;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Date;

import net.sf.jasperreports.engine.JRException;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.print.PageLayout;
import javafx.print.PageOrientation;
import javafx.print.Paper;
import javafx.scene.Node;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.Label;
import javafx.scene.control.Labeled;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.Background;
import javafx.scene.layout.BackgroundFill;
import javafx.scene.layout.CornerRadii;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import javafx.scene.transform.Scale;
import javafx.stage.Modality;
import javafx.stage.Stage;
import javafx.geometry.VPos;
import javafx.scene.control.Separator;
import cz.borec.demo.AppProperties;
import cz.borec.demo.core.dto.OrderDTO;
import cz.borec.demo.core.dto.OrderItemDTO;
import cz.borec.demo.gui.controls.AlertHelper;
import cz.borec.demo.gui.controls.BlueText;
import cz.borec.demo.gui.controls.PrintLabel;

public class Printer {
	private static Printer instance = new Printer();
	
	public static void print(OrderDTO orderDTO) {
		instance.printInternal(orderDTO);
	}
	
	protected void printInternal(OrderDTO orderDTO) {

		printJasper(orderDTO);
		

	}

	PrintServiceApp printService = new PrintServiceApp();
	
	private void printJasper(OrderDTO orderDTO) {
		ArrayList<String> header = BillBuilder.getHeader(orderDTO);
		ArrayList<String[]> lines = BillBuilder.getLines(orderDTO);
		ArrayList<String> footer = BillBuilder.getFooter(orderDTO);
		printService.setHeader(header);
		printService.setLines(lines);
		printService.setFooter(footer);
		try {
			printService.fill();
			//printService.print();
		} catch (JRException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
	private GridPane createPane(OrderDTO orderDTO) {
		
		
		Collection<OrderItemDTO> items = orderDTO.getItems();
		GridPane root = new GridPane();
		root.setHgap(0);
		root.setVgap(2);
		root.setPadding(new Insets(2, 2, 2, 2));

		root.setBackground(new Background(new BackgroundFill(Color.WHITE,
				new CornerRadii(5.0), Insets.EMPTY)));
		root.setAlignment(Pos.TOP_CENTER);
		// root.setGridLinesVisible(true);

/*		HBox hbox = new HBox();
		hbox.setAlignment(Pos.CENTER);
		Node node = getInitialHeader(orderDTO);
		hbox.getChildren().add(node);
		root.add(hbox, 0, 0, 3, 3);
*/

		if(!orderDTO.isSummarized()) {
		PrintLabel label = new PrintLabel( 9, true);
		label.setText("\u00DA\u010CTENKA \u010D.");
		root.add(label, 0, 0);
		root.add(new PrintLabel(""), 1, 0);
		PrintLabel lab3 = new PrintLabel(9, true);
		lab3.setText(orderDTO.getId().toString());
		HBox hbox3 = new HBox();
		hbox3.setAlignment(Pos.CENTER_RIGHT);
		hbox3.getChildren().add(lab3);

		root.add(hbox3, 2, 0);
		
		
		root.add(getHorizontalLine(), 0, 1, 3, 1);
		
		root.add(new PrintLabel("FIK: " + (orderDTO.getFIK() == null ? "Neodesl\u00E1no" : orderDTO.getFIK())), 0, 2, 3, 1);
/*		root.add(new PrintLabel(""), 1, 2);
		lab3 = new PrintLabel(c);
		 hbox3 = new HBox();
		hbox3.setAlignment(Pos.CENTER_RIGHT);
		hbox3.getChildren().add(lab3);
		root.add(hbox3, 2, 2);
*/		}
		else {
			PrintLabel label = new PrintLabel( 9, true);
			label.setText("P\u0158EHLED TR\u017DEB");
			root.add(label, 0, 0);
			root.add(new PrintLabel(""), 1, 0);
			root.add(new PrintLabel(""), 2, 0);

			root.add(getHorizontalLine(), 0, 1, 3, 1);
			
			root.add(new PrintLabel(""), 0, 2);
			root.add(new PrintLabel(""), 1, 2);
			root.add(new PrintLabel(""), 2, 2);
		}


		if(AppProperties.getProperties().isPrintLogo()) {
			root.add(getImageBox(), 0, 3, 3, 3);
		}

		HBox hbox = new HBox();
		hbox.setAlignment(Pos.CENTER);
		VBox vbox = getHeader(orderDTO);
		hbox.getChildren().add(vbox);
		root.add(hbox, 0, 6, 3, 3);

		int index = 9;
		root.add(getHorizontalLine(), 0, index++, 3, 1);

		PrintLabel l1 = new PrintLabel("Po\u010Det");
		l1.setPrefWidth(30);
		root.add(l1, 0, index);
		PrintLabel l2 = new PrintLabel("Cena");
		l2.setAlignment(Pos.CENTER);
		l2.setPrefWidth(30);
		root.add(l2, 1, index);
		PrintLabel l3 = new PrintLabel("Celkem");
		l3.setPrefWidth(30);
		root.add(l3, 2, index++);

		root.add(getHorizontalLine(), 0, index++, 3, 1);

		for (OrderItemDTO orderItemDTO : items) {
			root.add(new PrintLabel(orderItemDTO.getProductName()), 0, index,
					3, 1);
			PrintLabel lab2 = new PrintLabel(orderItemDTO.getAmount()
					.toString() + " ks");
			HBox hboxl = new HBox();
			hboxl.setAlignment(Pos.CENTER_RIGHT);
			hboxl.getChildren().add(lab2);
			root.add(hboxl, 0, index + 1);
			hboxl = new HBox();
			lab2 = new PrintLabel(orderItemDTO.getProduct().getPrice()
					.toString());
			hboxl.setAlignment(Pos.CENTER_RIGHT);
			hboxl.getChildren().add(lab2);
			root.add(hboxl, 1, index + 1);
			hboxl = new HBox();
			lab2 = new PrintLabel(getItemValue(orderItemDTO));
			hboxl.setAlignment(Pos.CENTER_RIGHT);
			hboxl.getChildren().add(lab2);
			root.add(hboxl, 2, index + 1);
			index += 2;
		}

		root.add(getHorizontalLine(), 0, index++, 3, 1);

		//---
		if(orderDTO.getDiscount().doubleValue() > 0) {
		root.add(new PrintLabel("Sleva"), 0, index);

		root.add(new PrintLabel(""), 1, index);

		PrintLabel lab2 = new PrintLabel("-" + orderDTO.getDiscount().toString());
		hbox = new HBox();
		hbox.setAlignment(Pos.CENTER_RIGHT);
		hbox.getChildren().add(lab2);

		root.add(hbox, 2, index++);
		}
		//---
		root.add(new PrintLabel("DPH 21%"), 0, index);

		root.add(new PrintLabel(""), 1, index);

		PrintLabel lab2 = new PrintLabel(orderDTO.getVatAmount().toString());
		hbox = new HBox();
		hbox.setAlignment(Pos.CENTER_RIGHT);
		hbox.getChildren().add(lab2);

		root.add(hbox, 2, index++);
		//---

		PrintLabel total = new PrintLabel( 9, true);
		
		total.setText("CELKEM (k\u010D)");
		
		
		root.add(total, 0, index);

		root.add(new PrintLabel(""), 1, index);

		lab2 = new PrintLabel(9, true);
		lab2.setText(orderDTO.getSumFormattedAfterDiscount());
		hbox = new HBox();
		hbox.setAlignment(Pos.CENTER_RIGHT);
		hbox.getChildren().add(lab2);

		root.add(hbox, 2, index++);

		root.add(getHorizontalLine(), 0, index++, 3, 1);

		// root.add(hbox, 0, 0, 3, 3);

/*		root.add(new PrintLabel("FIK: "), 0, index);

		root.add(new PrintLabel(""), 1, index);
		lab2 = new PrintLabel(orderDTO.getFIK() == null ? "Neodesl\u00E1no" : orderDTO.getFIK());
		hbox = new HBox();
		hbox.setAlignment(Pos.CENTER_RIGHT);
		hbox.getChildren().add(lab2);
		root.add(hbox, 2, index++);
*/
		root.add(getFooter(), 0, index, 3, 1);

		return root;
	}

/*	private Node getInitialHeader(OrderDTO orderDTO) {
		GridPane root = new GridPane();
		root.setHgap(0);
		root.setVgap(2);
		root.setPadding(new Insets(2, 2, 2, 2));
		PrintLabel p = new PrintLabel("\u010C\u00CDSLO \u00DA\u010CTENKY: ", true);
		PrintLabel p2 = new PrintLabel(orderDTO.getId().toString(), true);
		root.add(p, 0, 0);
		root.add(p2, 1, 0);
		return root;
	}
*/
	protected String getItemValue(OrderItemDTO orderItemDTO) {
		
		return orderItemDTO.getPriceTotal().toString();

	}

	protected VBox getHeader(OrderDTO orderDTO) {
		VBox vbox = new VBox();
		vbox.setAlignment(Pos.CENTER);
		vbox.getChildren().add(new PrintLabel("Provozovna: " + AppProperties.getProperties().getRestaurantName()));
		vbox.getChildren().add(new PrintLabel("Provozovatel: " + AppProperties.getProperties().getCustomerName()));
		vbox.getChildren().add(new PrintLabel(AppProperties.getProperties().getCustomerStreet()));
		vbox.getChildren().add(new PrintLabel(AppProperties.getProperties().getCustomerAddress()));
		vbox.getChildren().add(new PrintLabel("I\u010CO: " + AppProperties.getProperties().getCustomerICO()));
		vbox.getChildren()
		.add(new PrintLabel("datum: "
				+ orderDTO.getDate().toLocaleString()));
		String s = null;
		if(orderDTO.getTableDTO() == null) {
			s = orderDTO.getFullName() ;
		}
		else {
			s = orderDTO.getTableDTO().getName();
		}
		
		vbox.getChildren()
				.add(new PrintLabel("m\u00EDsto: "
						+ s));
		return vbox;
	}

	private HBox imageBox = null;

	protected Node getImageBox() {
		if (imageBox == null) {
			Image image = new Image("images/printlogo.png");
			ImageView pic = new ImageView();
			pic.setFitWidth(100);
			pic.setFitHeight(100);
			pic.setImage(image);

			imageBox = new HBox();
			imageBox.setAlignment(Pos.CENTER);
			imageBox.getChildren().add(pic);
		}
		return imageBox;
	}

	private static Node getHorizontalLine() {
		
	    final Separator sepHor = new Separator();
	    sepHor.setValignment(VPos.CENTER);
	        
	    return sepHor;

/*		return new PrintLabel(
				"-------------------------------------------------------------------");
*/	}

	private HBox footer = null;

	protected Node getFooter() {
		if (footer == null) {
			BlueText t = new BlueText(
					"D\u011Bkujeme za n\u00E1v\u0161t\u011Bvu", 8);

			VBox vbox = new VBox();
			vbox.setAlignment(Pos.CENTER);
			vbox.getChildren().add(new PrintLabel("Otev\u00EDrac\u00ED doba:"));
			vbox.getChildren().add(new PrintLabel(" "));
			vbox.getChildren().add(t);

			footer = new HBox();
			footer.setAlignment(Pos.CENTER);
			footer.getChildren().add(vbox);

		}
		return footer;
	}

	/**
	 * Scales the node based on the standard letter, portrait paper to be
	 * printed.
	 * 
	 * @param node
	 *            The scene node to be printed.
	 */
	/*
	 * public static void print(final Node node) { javafx.print.Printer printer
	 * = javafx.print.Printer.getDefaultPrinter(); PageLayout pageLayout =
	 * printer.createPageLayout(Paper.NA_LETTER, PageOrientation.PORTRAIT,
	 * javafx.print.Printer.MarginType.DEFAULT); double scaleX =
	 * pageLayout.getPrintableWidth() / node.getBoundsInParent().getWidth();
	 * double scaleY = pageLayout.getPrintableHeight() /
	 * node.getBoundsInParent().getHeight(); // node.getTransforms().add(new
	 * Scale(0.7, 1));
	 * 
	 * executePrint(node);
	 * 
	 * }
	 */

	private void executePrint(Node node) {
		
		javafx.print.Printer printer = javafx.print.Printer.getDefaultPrinter();
		System.out.println("WIDTH: " + printer.getDefaultPageLayout().getPrintableWidth());
		System.out.println("HEIGHT: " + printer.getDefaultPageLayout().getPrintableHeight());
		
		
		
		// Paper.JAPANESE_POSTCARD = 100mm => pageLayout.getPrintableWidth() * 0.76
        //PageLayout pageLayout = printer.createPageLayout(Paper.A4, PageOrientation.PORTRAIT, javafx.print.Printer.MarginType.HARDWARE_MINIMUM);
        PageLayout pageLayout = printer.createPageLayout(Paper.JAPANESE_POSTCARD, PageOrientation.PORTRAIT, 0, 0, 10, 10);
	        double width = node.getBoundsInParent().getWidth();
	        double height = node.getBoundsInParent().getHeight();
	        double scaleX = (pageLayout.getPrintableWidth()) / width;
/*	        double scaleX = (pageLayout.getPrintableWidth() - pageLayout.getLeftMargin() - pageLayout.getRightMargin()) / width;
	        double scaleY = (pageLayout.getPrintableHeight() - pageLayout.getTopMargin() - pageLayout.getBottomMargin()) / height;
*/	        node.getTransforms().add(new Scale(scaleX, 1));

	        javafx.print.PrinterJob job = javafx.print.PrinterJob
				.createPrinterJob();
		if (job != null) {
			if (job.showPrintDialog(null)) {
				boolean success = job.printPage(pageLayout, node);
				if (success) {
					job.endJob();
				}
			}
		}
		
		
		
		
		
		
		
		
/*		javafx.print.Printer printer = javafx.print.Printer.getDefaultPrinter();
		// Paper.JAPANESE_POSTCARD = 100mm => pageLayout.getPrintableWidth() * 0.76
        //PageLayout pageLayout = printer.createPageLayout(Paper.A4, PageOrientation.PORTRAIT, javafx.print.Printer.MarginType.HARDWARE_MINIMUM);
        PageLayout pageLayout = printer.createPageLayout(Paper.A4, PageOrientation.PORTRAIT, 0, 0, 10, 10);
	        double width = node.getBoundsInParent().getWidth();
	        double height = node.getBoundsInParent().getHeight();
	        double scaleX = (pageLayout.getPrintableWidth()) / width;
	        double scaleX = (pageLayout.getPrintableWidth() - pageLayout.getLeftMargin() - pageLayout.getRightMargin()) / width;
	        double scaleY = (pageLayout.getPrintableHeight() - pageLayout.getTopMargin() - pageLayout.getBottomMargin()) / height;
	        node.getTransforms().add(new Scale(scaleX, 1));

	        javafx.print.PrinterJob job = javafx.print.PrinterJob
				.createPrinterJob();
		if (job != null) {
			if (job.showPrintDialog(null)) {
				boolean success = job.printPage(pageLayout, node);
				if (success) {
					job.endJob();
				}
			}
		}
*/
	}
}
