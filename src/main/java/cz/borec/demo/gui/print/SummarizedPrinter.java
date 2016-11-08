package cz.borec.demo.gui.print;

import javafx.geometry.Pos;
import javafx.scene.Node;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import cz.borec.demo.AppProperties;
import cz.borec.demo.core.dto.OrderDTO;
import cz.borec.demo.core.dto.OrderItemDTO;
import cz.borec.demo.core.dto.SummarizedOrderDTO;
import cz.borec.demo.gui.controls.BlueText;
import cz.borec.demo.gui.controls.PrintLabel;

public class SummarizedPrinter extends Printer {

	private static SummarizedPrinter instance = new SummarizedPrinter();

	public static void print(SummarizedOrderDTO orderDTO) {
		instance.printInternal(orderDTO);
	}
	
	@Override
	protected VBox getHeader( OrderDTO orderDTO) {
		VBox vbox = new VBox();
		vbox.setAlignment(Pos.CENTER);
		vbox.getChildren().add(new PrintLabel("Provozovna: " + AppProperties.getProperties().getRestaurantName()));
		vbox.getChildren().add(new PrintLabel("Provozovatel: " + AppProperties.getProperties().getCustomerName()));
		vbox.getChildren().add(new PrintLabel(AppProperties.getProperties().getCustomerStreet()));
		vbox.getChildren().add(new PrintLabel(AppProperties.getProperties().getCustomerAddress()));
		vbox.getChildren().add(new PrintLabel("I\u010CO: " + AppProperties.getProperties().getCustomerICO()));
		vbox.getChildren()
		.add(new PrintLabel("Datum od: "
				+ orderDTO.getDate().toLocaleString()));
		vbox.getChildren()
		.add(new PrintLabel("Datum do: "
				+ ((SummarizedOrderDTO)orderDTO).getDateTo().toLocaleString()));

		return vbox;
	}

	@Override
	protected String getItemValue(OrderItemDTO orderItemDTO) {
		
		return orderItemDTO.getPrice().toString();

	}

	@Override
	protected Node getImageBox() {

		return new HBox();
	}

	@Override
	protected Node getFooter() {

		return new HBox();
	}

}
