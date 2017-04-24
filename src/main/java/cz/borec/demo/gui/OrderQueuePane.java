package cz.borec.demo.gui;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Iterator;
import java.util.List;
import java.util.ListIterator;

import cz.borec.demo.Constants;
import cz.borec.demo.core.dto.OrderDTO;
import cz.borec.demo.core.entity.OrderState;
import cz.borec.demo.gui.controls.AlertHelper;
import cz.borec.demo.gui.controls.BlueText;
import cz.borec.demo.gui.controls.LiveButton;
import cz.borec.demo.gui.controls.ProductButton;
import cz.borec.demo.gui.controls.SubCategoryButton;
import cz.borec.demo.gui.controls.SubSubCategoryButton;
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
import javafx.scene.paint.Color;
import javafx.scene.layout.Background;
import javafx.scene.layout.BackgroundFill;
import javafx.scene.layout.CornerRadii;

public class OrderQueuePane extends AbstractPaneBase {

	private GridPane g;
	private HBox topButtons;
	private OrderState mode;

	public OrderQueuePane(Controller controller) {
		super(controller);
	}

	protected void createLeftButtons(HBox hbox) {
		addButtons(hbox);
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
		Button buttonMenu = createMenuButton();
		hbox.getChildren().add(buttonMenu);
		BlueText arrow = new BlueText("\u2192");
		hbox.getChildren().add(arrow);
		BlueText label = new BlueText("Objedn\u00E1vky");
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
		//topButtons.getChildren().add(new javafx.scene.control.Label("kokot"));

		
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
	
	
	private void addButtons(HBox hbox) {
		LiveButton buttonAll = new LiveButton("V\u0161echny");
		buttonAll.setOnAction(new EventHandler<ActionEvent>() {
			@Override
			public void handle(ActionEvent arg0) {
				controller.orderQueuePane(null);
			}
		});
		hbox.getChildren().add(buttonAll);
		LiveButton buttonPreparing = new LiveButton(Constants.LABEL_PREPARING);
		buttonPreparing.setOnAction(new EventHandler<ActionEvent>() {
			@Override
			public void handle(ActionEvent arg0) {
				controller.orderQueuePane(OrderState.PREPARING);
			}
		});
		hbox.getChildren().add(buttonPreparing);
		LiveButton buttonShift = new LiveButton(Constants.LABEL_SHIFT);
		buttonShift.setOnAction(new EventHandler<ActionEvent>() {
			@Override
			public void handle(ActionEvent arg0) {
				controller.orderQueuePane(OrderState.SHIFT);
			}
		});
		hbox.getChildren().add(buttonShift);
		LiveButton buttonHandOver = new LiveButton(Constants.LABEL_HAND_OVER);
		buttonHandOver.setOnAction(new EventHandler<ActionEvent>() {
			@Override
			public void handle(ActionEvent arg0) {
				controller.orderQueuePane(OrderState.HAND_OVER);
			}
		});
		hbox.getChildren().add(buttonHandOver);
		LiveButton buttonStorno = new LiveButton(Constants.LABEL_STORNO);
		buttonStorno.setOnAction(new EventHandler<ActionEvent>() {
			@Override
			public void handle(ActionEvent arg0) {
				controller.orderQueuePane(OrderState.STORNO);
			}
		});
		hbox.getChildren().add(buttonStorno);
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
		List<OrderDTO> orders = controller.getModel().getOrderHistoryOfTable(null, this.mode);
		List<LiveButton> buttonList = new ArrayList<LiveButton>();
		for (OrderDTO orderDTO : orders) {
			LiveButton b = null;
			String label = "\u010D." + orderDTO.getId().toString() + '\n' + orderDTO.getDate().toLocaleString() + '\n' + orderDTO.getSumFormattedAfterDiscount() + "k\u010D";
			switch(orderDTO.getState()) {
			case PREPARING:
				b = new SubCategoryButton(StringUtils.splitIntoLines(label ));
				break;
			case SHIFT:
				b = new ProductButton(StringUtils.splitIntoLines(label));
				break;
			case HAND_OVER:
				b = new SubSubCategoryButton(StringUtils.splitIntoLines(label));
				break;
			case STORNO:
				b = new SubCategoryButton(StringUtils.splitIntoLines(label));
				b.setBackground(new Background(new BackgroundFill(
			Color.LIGHTSKYBLUE   /*Color.web("#00B3C7") */,
			new CornerRadii(5.0), Insets.EMPTY)));
				break;
			}
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

	public void setMode(OrderState i) {
		this.mode = i;
		
	}

	public OrderState getMode() {
		return mode;
	}
}
