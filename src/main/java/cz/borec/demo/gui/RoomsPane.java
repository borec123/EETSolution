package cz.borec.demo.gui;

import java.util.List;

import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Node;
import javafx.scene.control.Button;
import javafx.scene.control.Tooltip;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Pane;
import javafx.scene.layout.VBox;
import javafx.scene.text.Text;
import cz.borec.demo.core.dto.OrderDTO;
import cz.borec.demo.core.dto.RoomDTO;
import cz.borec.demo.core.dto.TableDTO;
import cz.borec.demo.core.dto.TableType;
import cz.borec.demo.gui.controls.AlertHelper;
import cz.borec.demo.gui.controls.BlueText;
import cz.borec.demo.gui.controls.LiveButton;
import cz.borec.demo.gui.controls.SubCategoryButton;
import cz.borec.demo.gui.controls.TableButton;
import cz.borec.demo.gui.controls.TableNamePane;
import javafx.scene.layout.BorderPane;

public class RoomsPane extends AbstractPaneBase {

	public RoomsPane(Controller controller, List<RoomDTO> list) {
		super(controller);
		rooms = list;
	}

	private List<RoomDTO> rooms;
	private RoomDTO selectedRoom;
	protected TableDTO tableDTO;
	protected boolean editMode;

	@Override
	protected void fillHorizontalBox(HBox hbox) {
		Button buttonMenu = createMenuButton();
		hbox.getChildren().add(buttonMenu);
		BlueText arrow = new BlueText("\u2192");
		hbox.getChildren().add(arrow);
		BlueText label = new BlueText("M\u00EDstnosti");
		hbox.getChildren().add(label);
	}

	@Override
	protected void fillVBox(javafx.scene.layout.GridPane vbox) {

		if (rooms != null) {
			SubCategoryButton options[] = createHyperlinks(rooms);
			fillWithHyperlinks(options);
		}
	}

	@Override
	public Pane addGridPane() {
		if (selectedRoom != null) {
			BorderPane borderPane = new BorderPane();
			Pane pane = new Pane();
			// pane.setPrefSize(200, 200);
			reloadPane(pane);

			HBox hbox = new HBox();
			hbox.setPadding(new Insets(8, 10, 8, 10));
			hbox.setSpacing(10);
			hbox.setAlignment(Pos.CENTER_RIGHT);

			createButtons(hbox);

			BorderPane buttonsPane = new BorderPane();
			buttonsPane.setCenter(hbox);

			borderPane.setBottom(buttonsPane);
			borderPane.setCenter(pane);

			return borderPane;
		}
		return null;
	}

	private double X;
	private double Y;

	private void reloadPane(Pane pane) {
		this.rooms = controller.getModel().getAllRooms();
		for (RoomDTO roomDTO : rooms) {
			if (roomDTO.getId().equals(selectedRoom.getId())) {
				selectedRoom = roomDTO;
			}
		}
		for (TableDTO tableDTO : selectedRoom.getTables()) {
			LiveButton button = createTableButton(tableDTO);
			// button.setId("button1");

			pane.getChildren().add(button);
			if (tableDTO.getOrderDTO() != null) {
				if (tableDTO.getOrderDTO().getItems().size() > 0) {
					String preffix = tableDTO.getOrderDTO().getFullName().equals(tableDTO.getName()) ? ""
							: tableDTO.getOrderDTO().getFullName() + ": ";
					Text t = new Text(preffix + tableDTO.getOrderDTO().getSum().toString());
					t.relocate(tableDTO.X, tableDTO.Y + tableDTO.height);
					pane.getChildren().add(t);
				}
			}
		}
	}

	private LiveButton createTableButton(TableDTO tableDTO) {
		TableButton button = new TableButton(tableDTO.width == 0 ? "" : tableDTO.getName());
		button.setPrefSize(tableDTO.width, tableDTO.height);
		button.relocate(tableDTO.X, tableDTO.Y);
		button.setUserData(tableDTO);
		if (tableDTO.width == 0) {
			button.setTooltip(new Tooltip(tableDTO.getName()));
		}
		button.setOnAction(new EventHandler<ActionEvent>() {

			@Override
			public void handle(ActionEvent arg0) {
				if (!editMode) {
					handleTableclick(arg0);
				}
			}

		});

		if (editMode) {
			button.setOnMousePressed(new EventHandler<javafx.scene.input.MouseEvent>() {

				@Override
				public void handle(javafx.scene.input.MouseEvent event) {
					X = event.getSceneX();
					Y = event.getSceneY();
					System.out.println("setOnMouseDragEntered");
				}
			});
			button.setOnMouseDragged(new EventHandler<javafx.scene.input.MouseEvent>() {
				@Override
				public void handle(javafx.scene.input.MouseEvent event) {
					moveTable(event.getSource(), event.getSceneX() - X, event.getSceneY() - Y);

					System.out.println("setOnMouseDragged");
				}
			});
			button.setOnMouseClicked(new EventHandler<javafx.scene.input.MouseEvent>() {
				@Override
				public void handle(javafx.scene.input.MouseEvent event) {
					if (event.getClickCount() == 2) {
						TableNamePane discountPane = new TableNamePane();
						LiveButton button = (LiveButton) event.getSource();
						TableDTO table = (TableDTO) (button).getUserData();
						discountPane.setName(table.getName());
						discountPane.setType(table.getType());
						AlertHelper.showTableNameDialog(discountPane, "St\u016Fl");
						if (discountPane.isOK()) {
							table.setName(discountPane.getName());
							button.setText(discountPane.getName());
							table.setType(discountPane.getType());
							button.setPrefSize(table.width, table.height);
						}

					}
				}
			});
		}
		return button;
	}

	protected void moveTable(Object source, double d, double e) {
		BorderPane pane = (BorderPane) getCenter();
		if (pane != null) {
			Pane pane2 = (Pane) pane.getCenter();
			LiveButton table = (LiveButton) source;
			table.relocate(d, e);
			TableDTO tDTO = (TableDTO) table.getUserData();
			tDTO.X = (int) d;
			tDTO.Y = (int) e;
		}

	}

	protected void handleTableclick(ActionEvent arg0) {
		tableDTO = (TableDTO) ((LiveButton) arg0.getSource()).getUserData();
		// System.out.println("Table: " + tableDTO);
		controller.tablePane(tableDTO);
	}

	/*
	 * public void setRooms(List<RoomDTO> allRooms) { rooms = allRooms;
	 * reloadVBox(); }
	 */
	private void reloadVBox() {

		javafx.scene.layout.GridPane vbox = getVBox();
		vbox.getChildren().clear();
		fillVBox(vbox);
	}

	protected void handleHyperlinkAction(ActionEvent arg0) {
		selectedRoom = (RoomDTO) ((SubCategoryButton) arg0.getSource()).getUserData();
		refresh();
	}

	public void refresh() {
		reloadVBox();
		BorderPane pane = (BorderPane) getCenter();
		if (pane != null) {
			Pane pane2 = (Pane) pane.getCenter();
			pane2.getChildren().clear();
			reloadPane(pane2);
		} else {
			setCenter(addGridPane());
		}
	}


	protected void createButtons(HBox hbox) {
		final LiveButton buttonAdd = new LiveButton("Editovat");
		final LiveButton buttonPlus = new LiveButton("P\u0159idat");
		buttonPlus.setOnAction(new EventHandler<ActionEvent>() {

			@Override
			public void handle(ActionEvent arg0) {
				TableDTO tableDTo = new TableDTO(0, 0, 70, 70);
				tableDTo.setName("Novy stul");
				tableDTo.setRoomDTO(selectedRoom);
				// selectedRoom.getTables().add(tableDTo);
				// createTableButton(tableDTo );
				refresh();
			}
		});
		buttonAdd.setOnAction(new EventHandler<ActionEvent>() {

			@Override
			public void handle(ActionEvent arg0) {
				editMode = !editMode;
				buttonPlus.setVisible(editMode);
				buttonAdd.setText(editMode ? "Konec editace" : "Editovat");
				if (!editMode) {
					BorderPane pane = (BorderPane) getCenter();
					if (pane != null) {
						Pane pane2 = (Pane) pane.getCenter();
						ObservableList<Node> ch = pane2.getChildren();
						for (Node node : ch) {
							if (node instanceof LiveButton) {
								LiveButton table = (LiveButton) node;
								TableDTO tDTO = (TableDTO) table.getUserData();
								if (tDTO.getId() == null) {
									Long id = controller.getModel().createTable(tDTO);
									tDTO.setId(id);
								} else {
									controller.getModel().saveTable(tDTO);
								}
							}
						}
					}
				}
				refresh();
			}
		});

		hbox.getChildren().add(buttonPlus);
		buttonPlus.setVisible(false);
		hbox.getChildren().add(buttonAdd);

	}

}
