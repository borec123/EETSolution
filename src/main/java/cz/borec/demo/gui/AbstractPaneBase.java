package cz.borec.demo.gui;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import cz.borec.demo.Constants;
import cz.borec.demo.core.dto.BaseDTO;
import cz.borec.demo.core.dto.CategoryDTO;
import cz.borec.demo.gui.controls.AppPropertiesProxy;
import cz.borec.demo.gui.controls.BlueText;
import cz.borec.demo.gui.controls.ButtonSizeUtils;
import cz.borec.demo.gui.controls.CategoryButton;
import cz.borec.demo.gui.controls.LiveButton;
import cz.borec.demo.gui.controls.SubCategoryButton;
import cz.borec.demo.gui.controls.SubSubCategoryButton;
import cz.borec.demo.util.StringUtils;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Node;
import javafx.scene.control.Button;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Pane;
import javafx.scene.layout.Priority;
import javafx.scene.layout.StackPane;
import javafx.scene.paint.Color;
import javafx.scene.paint.CycleMethod;
import javafx.scene.paint.LinearGradient;
import javafx.scene.paint.Stop;
import javafx.scene.shape.Rectangle;
import javafx.scene.text.Font;
import javafx.scene.text.FontPosture;
import javafx.scene.text.FontWeight;
import javafx.scene.text.Text;

public abstract class AbstractPaneBase extends BorderPane {

	Controller controller;
	protected List<CategoryDTO> categories;
	protected CategoryDTO category;
	protected BlueText categoryText;
	private List<CategoryDTO> selectedSubCategories;
	private Map<Long, SubCategoryButton[]> subCategories = new HashMap<Long, SubCategoryButton[]>();

	public AbstractPaneBase(Controller controller) {
		this.controller = controller;
		construct();
	}
	
	public void resetCategories() {
		//TODO: implement.
 	}

	public void construct() {

		HBox hbox = addHorizontalBox();
		setTop(hbox);
		
/*		 ScrollPane scrollPane = new ScrollPane();
		 scrollPane.setContent(addVerticalBox());
*/		
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
					new CornerRadii(5.0), Insets.EMPTY)));*/
			
			setStyle(" -fx-background-color: #222222, linear-gradient(to bottom right, transparent, " + 
   " #202020 25%, #888888 50%); ");

		} else if (look == 3) {
			/*			setBackground(new Background(new BackgroundFill(
			Color.LIGHTSKYBLUE   Color.web("#00B3C7") ,
			new CornerRadii(5.0), Insets.EMPTY)));*/
	setStyle(" -fx-background-color: #00b3c7, linear-gradient(to bottom right, transparent, " + 
" #00b3c7 25%, #b1f5f6 50%); ");

		} else {
			/*URL url = getClass().getClassLoader().getResource(
					"images/light2.jpg");
			String image = url.toString();
			setStyle("-fx-background-image: url('" + image + "'); "
					+ "  -fx-background-position: center center; "
					+ " -fx-background-repeat: stretch;");*/
			setStyle(" -fx-background-color:  linear-gradient(to bottom right, transparent, " + 
   " #4286f4 25%, #cfe0fc 50%); ");
/*			setStyle(" -fx-background-color: #fe91fc, linear-gradient(to bottom right, transparent, " + 
   " #4286f4 25%, #cfe0fc 50%); ");
*/		}

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

	public GridPane addVerticalBox() {
		/*
		 * VBox vbox = new VBox(); vbox.setPadding(new Insets(10));
		 * vbox.setSpacing(18);
		 */

		GridPane g = new GridPane();
		g.setHgap(7);
		g.setVgap(7);
		g.setPadding(new Insets(20, 20, 5, 10));

		fillVBox(g);

		return g;
	}

	protected GridPane getVBox() {

		return (GridPane) getLeft();
		// return (GridPane) ((ScrollPane)getLeft()).getContent();
	}

	protected abstract void fillVBox(GridPane g);

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
		//buttonMenu.setPrefSize(100, (AppPropertiesProxy.getDisplaySize() == 2) ? 45 : 20);
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
		GridPane vbox = getVBox();
		vbox.getChildren().clear();
		double hhheight = getHeight();
		double g_Height = vbox.getHeight() > 0 ? vbox.getHeight(): 300.0; // getPrefHeight();
		double height = 0.0;
		int ii = 0;
		int jj = 0;
		for (int i = 0; i < options.length; i++) {
			vbox.add(options[i], ii, jj++);
			// VBox.setMargin(options[i], new Insets(0, 10, 0, 6));
			height += options[i].getPrefHeight() + vbox.getVgap();
			if (height > g_Height - options[i].getPrefHeight()) {
				height = 0.0;
				jj = 0;
				ii++;
			} 
			//else {
			//}
		}

	}

	protected void handleHyperlinkAction(ActionEvent arg0) {
		CategoryDTO cat = (CategoryDTO) ((SubCategoryButton) arg0.getSource())
				.getUserData();
		if (cat.isRoot()) {
			SubCategoryButton[] options = subCategories.get(cat.getId());
			selectedSubCategories = cat.getChildCategories();
			if (options == null) {
				List<CategoryDTO> all = new ArrayList<CategoryDTO>();
				all.addAll(categories);
				all.addAll(selectedSubCategories);
				options = createHyperlinks(all);
				this.subCategories.put(cat.getId(), options);
			}
			fillWithHyperlinks(options);
		} else {
			if (cat.getChildCategories().size() > 0) {
				List<CategoryDTO> all = new ArrayList<CategoryDTO>();
				all.addAll(categories);
				all.addAll(selectedSubCategories);
				int i = all.indexOf(cat);
				for (CategoryDTO categoryDTO : cat.getChildCategories()) {
					categoryDTO.setSecondLevel(true);
					all.add(++i, categoryDTO);
				}
				SubCategoryButton[] options = createHyperlinks(all);
				fillWithHyperlinks(options);
			}
			category = cat;
			categoryText.setText(category.toString());
			refresh();
		}
	}

	protected void refresh() {

	}

	SubCategoryButton[] createHyperlinks(List<? extends BaseDTO<Long>> dtos) {

		List<SubCategoryButton> links = new ArrayList<SubCategoryButton>();
		for (BaseDTO<Long> roomDTO : dtos) {
			SubCategoryButton pub;
			if (roomDTO instanceof CategoryDTO
					&& ((CategoryDTO) roomDTO).isRoot()) {
				pub = new CategoryButton(roomDTO.toString());
			} else {
				if (roomDTO instanceof CategoryDTO
						&& ((CategoryDTO) roomDTO).isSecondLevel()) {
					pub = new SubSubCategoryButton(
							StringUtils.splitIntoLines2(roomDTO.toString()));
				} else {
					pub = new SubCategoryButton(
							StringUtils.splitIntoLines2(roomDTO.toString()));
				}
			}
			pub.setUserData(roomDTO);
			pub.setPrefSize(ButtonSizeUtils.getRoomButtonWidth(),
					ButtonSizeUtils.getRoomButtonHeight());// Width(135);

			pub.setOnAction(new EventHandler<ActionEvent>() {

				@Override
				public void handle(ActionEvent arg0) {
					handleHyperlinkAction(arg0);
				}

			});
			links.add(pub);
		}

		SubCategoryButton options[] = new SubCategoryButton[links.size()];
		options = links.toArray(options);
		return options;
	}

	protected LiveButton createButtonRooms() {
		LiveButton buttonRooms = new LiveButton();
		buttonRooms.setText("Objedn\u00E1vky");
		//buttonRooms.setPrefSize(100, (AppPropertiesProxy.getDisplaySize() == 2) ? 45 : 20);
		buttonRooms.setOnAction(new EventHandler<ActionEvent>() {
			@Override
			public void handle(ActionEvent arg0) {
				controller.orderQueuePane();
			}
		});
		return buttonRooms;
	}

	public Pane createMainPane() {
		BorderPane pane = new BorderPane();
		BorderPane main = new BorderPane();
		main.setCenter(createMainContent());
		BorderPane panel = new BorderPane();
		panel.setPrefWidth(50);
		main.setRight(panel);
		pane.setCenter(main);

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

		pane.setBottom(buttonsPane);
		return pane;
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
