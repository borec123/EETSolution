package cz.borec.demo.gui;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Iterator;
import java.util.List;
import java.util.ListIterator;

import cz.borec.demo.core.dto.OrderDTO;
import cz.borec.demo.gui.controls.AlertHelper;
import cz.borec.demo.gui.controls.BlueText;
import cz.borec.demo.gui.controls.LiveButton;
import cz.borec.demo.gui.controls.SubCategoryButton;
import cz.borec.demo.gui.utils.GridPaneFiller;
import cz.borec.demo.util.StringUtils;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.geometry.Insets;
import javafx.scene.Node;
import javafx.scene.control.Button;
import javafx.scene.control.ScrollPane;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Pane;

public class OrderQueuePane extends AbstractPaneBase {

	private GridPane g;
	private HBox topButtons;

	public OrderQueuePane(Controller controller) {
		super(controller);
	}

	protected void createLeftButtons(HBox hbox) {
		Button buttonMenu = createMenuButton();
		hbox.getChildren().add(buttonMenu);
	}
	
	protected void createButtons(HBox hbox) {
		LiveButton buttonAdd = new LiveButton("Nov\u00E1 objedn\u00E1vka");
		buttonAdd.setOnAction(new EventHandler<ActionEvent>() {

			@Override
			public void handle(ActionEvent arg0) {
				controller.newOrder();
			}

		});

		hbox.getChildren().add(buttonAdd);
	}	
	
	@Override
	protected void fillHorizontalBox(HBox hbox) {
/*		Button buttonMenu = createMenuButton();
		hbox.getChildren().add(buttonMenu);
		BlueText arrow = new BlueText("\u2192");
		hbox.getChildren().add(arrow);*/
		BlueText label = new BlueText("Fronta objedn\u00E1vek");
		hbox.getChildren().add(label);
	}

	@Override
	protected void fillVBox(GridPane g) {
		// TODO Auto-generated method stub

	}

	protected Node createMainContent() {
		BorderPane b = new BorderPane();
		topButtons = new HBox();
		topButtons.setPadding(new Insets(5, 5, 5, 5));
		topButtons.setSpacing(5);
		topButtons.getChildren().add(new javafx.scene.control.Label("kokot"));

		
		g = new GridPane();
		g.setPadding(new Insets(5, 5, 5, 5));
		g.setHgap(7);
		g.setVgap(7);

		ScrollPane scrollPane = new ScrollPane();
		scrollPane.setContent(g);

		b.setTop(topButtons);
		b.setCenter(scrollPane);

		// b.add(new LiveButton("Radegast 12\u00B0\n 0,5l"), 0, 0);

		return b;
	}
	
	
	@Override
	public Pane addGridPane() {

		BorderPane borderPane = new BorderPane();
		/*
		 * HBox hbox = new HBox(); hbox.setPadding(new Insets(8, 10, 5, 0));
		 * hbox.setSpacing(10); label_order = new BlueText(LABEL_STR, 15);
		 * hbox.getChildren().add(label_order);
		 * hbox.setAlignment(Pos.CENTER_LEFT); borderPane.setTop(hbox);
		 */
		borderPane.setCenter(createMainPane());
		return borderPane;
		
	}

	public void reload() {
		List<OrderDTO> orders = controller.getModel().getOrderHistoryOfTable(null);
		List<LiveButton> buttonList = new ArrayList<LiveButton>();
		for (OrderDTO orderDTO : orders) {
			LiveButton b = new SubCategoryButton(StringUtils.splitIntoLines("\u010D." + orderDTO.getId().toString() + '\n' + orderDTO.getDate().toLocaleString()));
			// b.setPrefSize(BUTTON_SIZE, BUTTON_SIZE);
			b.setOnAction(new EventHandler<ActionEvent>() {

				@Override
				public void handle(ActionEvent arg0) {
					controller.tablePane(orderDTO);
				}
			});
			buttonList.add(b);
		}
		
		double g_Width = 0.0;
		if(g.getParent() == null) {
			g_Width = 500.0;
		}
		else {
			Node hovno = (Node) g.getParent().getParent().getParent();
			g_Width = ((ScrollPane) hovno).getWidth();
			GridPaneFiller.fillButtons(g, buttonList, g_Width);
		}
	}
}
