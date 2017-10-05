package cz.borec.demo.gui;

import java.net.URL;
import java.util.ArrayList;
import java.util.List;

import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Node;
import javafx.scene.control.Button;
import javafx.scene.control.TableView;
import javafx.scene.layout.Background;
import javafx.scene.layout.BackgroundFill;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.CornerRadii;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Pane;
import javafx.scene.layout.Priority;
import javafx.scene.layout.StackPane;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import javafx.scene.paint.CycleMethod;
import javafx.scene.paint.LinearGradient;
import javafx.scene.paint.Stop;
import javafx.scene.shape.Rectangle;
import javafx.scene.text.Font;
import javafx.scene.text.FontPosture;
import javafx.scene.text.FontWeight;
import javafx.scene.text.Text;
import cz.borec.demo.AppProperties;
import cz.borec.demo.Constants;
import cz.borec.demo.core.dto.BaseDTO;
import cz.borec.demo.core.dto.OrderItemDTO;
import cz.borec.demo.gui.controls.AppPropertiesProxy;
import cz.borec.demo.gui.controls.Colors;
import cz.borec.demo.gui.controls.LiveButton;
import cz.borec.demo.gui.controls.Settings;
import cz.borec.demo.gui.controls.SubCategoryButton;

public abstract class AbstractPaneBase2 extends BorderPane {

	Controller controller;

	public AbstractPaneBase2(Controller controller) {
		this.controller = controller;
		construct();
	}

	public void construct() {

		HBox hbox = addHorizontalBox();
		setTop(hbox);
		setLeft(addVerticalBox());
		addStackPane(hbox);
		setCenter(addGridPane());
		// setBackground(Settings.getBackground());
		// String image =
		// getClass().getResource("light_texture1.jpg").toExternalForm();

		int look = Integer.parseInt(AppPropertiesProxy.get(Constants.CONFIG_SKIN));
		if (look == 2) {
/*			setBackground(new Background(new BackgroundFill(
					 Color.web("#222222") ,
					new CornerRadii(5.0), Insets.EMPTY)));
*/			setStyle(" -fx-background-color:  linear-gradient(to bottom right, transparent, " + 
   " #888888 25%, #202020 50%); ");
		} else if (look == 3) {
			setBackground(new Background(new BackgroundFill(
					/*Color.LIGHTSKYBLUE */  Color.web("#00B3C7") ,
					new CornerRadii(5.0), Insets.EMPTY)));
		} else {
/*			URL url = getClass().getClassLoader().getResource(
					"images/light2.jpg");
			String image = url.toString();
			setStyle("-fx-background-image: url('" + image + "'); "
					+ "  -fx-background-position: center center; "
					+ " -fx-background-repeat: stretch;");
*/	
			setStyle(" -fx-background-color:  linear-gradient(to bottom right, transparent, " + 
   " #4286f4 25%, #cfe0fc 50%); ");
			
		}

	}

	public HBox addHorizontalBox() {
		HBox hbox = new HBox();
		hbox.setPadding(new Insets(15, 12, 15, 12));
		hbox.setSpacing(10);
		hbox.setAlignment(Pos.CENTER_LEFT);
		// hbox.setStyle("-fx-background-color: #336699;");

		fillHorizontalBox(hbox);

		/*
		 * Button buttonCurrent = new Button("Current");
		 * buttonCurrent.setPrefSize(100, 20);
		 * 
		 * Button buttonProjected = new Button("Projected");
		 * buttonProjected.setPrefSize(100, 20);
		 * hbox.getChildren().addAll(buttonCurrent, buttonProjected);
		 */
		return hbox;
	}

	protected abstract void fillHorizontalBox(HBox hbox);

	public VBox addVerticalBox() {
		VBox vbox = new VBox();
		vbox.setPadding(new Insets(10));
		vbox.setSpacing(18);

		fillVBox(vbox);

		return vbox;
	}

	protected VBox getVBox() {

		return (VBox) getLeft();
	}

	protected abstract void fillVBox(VBox vbox);

	public void addStackPane(HBox hb) {
		StackPane stack = new StackPane();
		Rectangle helpIcon = new Rectangle(30.0, 25.0);
		helpIcon.setFill(new LinearGradient(0, 0, 0, 1, true,
				CycleMethod.NO_CYCLE, new Stop[] {
						new Stop(0, Color.web("#4977A3")),
						new Stop(0.5, Color.web("#B0C6DA")),
						new Stop(1, Color.web("#9CB6CF")), }));
		helpIcon.setStroke(Color.web("#D0E6FA"));
		helpIcon.setArcHeight(3.5);
		helpIcon.setArcWidth(3.5);

		Text helpText = new Text("?");
		helpText.setFont(Font.font("Verdana", FontWeight.BOLD, 18));
		helpText.setFill(Color.WHITE);
		helpText.setStroke(Color.web("#7080A0"));

		stack.getChildren().addAll(helpIcon, helpText);
		stack.setAlignment(Pos.CENTER_RIGHT); // Right-justify nodes in stack
		StackPane.setMargin(helpText, new Insets(0, 10, 0, 0)); // Center "?"

		hb.getChildren().add(stack); // Add to HBox from Example 1-2
		HBox.setHgrow(stack, Priority.ALWAYS); // Give stack any extra space
	}

	public abstract Pane addGridPane();

	protected Button createMenuButton() {
		LiveButton buttonMenu = new LiveButton("Menu");
		buttonMenu.setPrefWidth(100);
		//buttonMenu.setPrefSize(100, (AppPropertiesProxy.getDisplaySize() == 2) ? 50 : 20);
		buttonMenu.setFont(Font.font("Verdana", FontPosture.ITALIC, 14));
		buttonMenu.setOnAction(new EventHandler<ActionEvent>() {

			@Override
			public void handle(ActionEvent arg0) {
				controller.mainMenu();

			}

		});
		return buttonMenu;
	}

	protected void fillWithHyperlinks(SubCategoryButton[] options) {
		VBox vbox = getVBox();
		for (int i = 0; i < options.length; i++) {
			VBox.setMargin(options[i], new Insets(0, 0, 0, 8));
			vbox.getChildren().add(options[i]);
		}

	}

	protected SubCategoryButton[] createHyperlinks(
			List<? extends BaseDTO<Long>> dtos) {

		List<SubCategoryButton> links = new ArrayList<SubCategoryButton>();
		for (BaseDTO<Long> roomDTO : dtos) {
			SubCategoryButton pub = new SubCategoryButton(roomDTO.toString());
			pub.setUserData(roomDTO);
			pub.setOnAction(new EventHandler<ActionEvent>() {

				@Override
				public void handle(ActionEvent arg0) {
					handleHyperlinkAction(arg0);
					System.out.println(((SubCategoryButton) arg0.getSource())
							.getText());

				}

			});
			links.add(pub);
		}

		SubCategoryButton options[] = new SubCategoryButton[links.size()];
		options = links.toArray(options);
		return options;
	}


	protected void handleHyperlinkAction(ActionEvent arg0) {

	}

	public Pane createButtonPane() {
		BorderPane buttonsPane = new BorderPane();

		HBox leftbox = new HBox();
		leftbox.setPadding(new Insets(8, 10, 8, 10));
		leftbox.setSpacing(10);
		leftbox.setAlignment(Pos.CENTER_RIGHT);

		createLeftButtons(leftbox);

		buttonsPane.setLeft(leftbox);

		HBox hbox = new HBox();
		hbox.setPadding(new Insets(8, 10, 8, 10));
		hbox.setSpacing(10);
		hbox.setAlignment(Pos.CENTER_RIGHT);

		createButtons(hbox);

		buttonsPane.setCenter(hbox);

		return buttonsPane;
	}

	protected void createLeftButtons(HBox leftbox) {

	}

	protected Node createMainContent() {

		BorderPane pane = new BorderPane();
		return pane;
	}

	protected void createButtons(HBox hbox) {

	}

}
