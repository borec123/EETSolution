package cz.borec.demo.gui;

import java.util.Arrays;
import java.util.Collections;
import java.util.List;

import org.springframework.util.StringUtils;

import cz.borec.demo.Constants;
import cz.borec.demo.DPH;
import cz.borec.demo.core.dto.CategoryDTO;
import cz.borec.demo.core.entity.SalesProductEntity;
import cz.borec.demo.core.entity.UnitEntity;
import cz.borec.demo.gui.controls.AlertHelper;
import cz.borec.demo.gui.controls.AppPropertiesProxy;
import cz.borec.demo.gui.controls.BlueText;
import cz.borec.demo.gui.controls.LiveButton;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
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
import javafx.scene.control.CheckBox;
import javafx.scene.control.ComboBox;
import javafx.scene.control.Label;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import javafx.scene.control.TreeItem;
import javafx.scene.control.TreeView;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;

public class CategoryPane extends AbstractPaneBase {

	private TextField textFieldName;
	private TextField textFieldParent;
	private ComboBox comboBoxDPH;
	private TreeView<CategoryDTO> tree;
	private LiveButton buttonEdit;
	private boolean editMode;
	private LiveButton buttonDelete;
	private LiveButton buttonSave;
	private LiveButton buttonAdd;
	private CategoryDTO parentCategory;
	private TreeItem<CategoryDTO> selectedItem;
	private TreeItem<CategoryDTO> rootItem;
	private boolean countDPH;

	public CategoryPane(Controller controller, List<CategoryDTO> list) {
		super(controller);
		categories = list;
		reloadVBox();
	}

	private void reloadVBox() {

		GridPane vbox = getVBox();
		vbox.getChildren().clear();
		fillVBox(vbox);
	}

	@Override
	protected void fillHorizontalBox(HBox hbox) {
		Button buttonMenu = createMenuButton();
		hbox.getChildren().add(buttonMenu);
		BlueText arrow = new BlueText("\u2192");
		hbox.getChildren().add(arrow);
		BlueText label = new BlueText("Kategorie");
		hbox.getChildren().add(label);
	}

	public void refresh() {

		categories = controller.getModel().getAllCategories(true);
		CategoryDTO root = new CategoryDTO();
		root.setName("Kategorie");
		rootItem = new TreeItem<CategoryDTO>(root);
		rootItem.setExpanded(true);

		if (categories != null) {
			loadChildren(rootItem, categories);
		}
		tree.setRoot(rootItem);
	}

	@Override
	protected void fillVBox(GridPane g) {
		CategoryDTO root = new CategoryDTO();
		root.setName("Kategorie");
		rootItem = new TreeItem<CategoryDTO>(root);
		rootItem.setExpanded(true);

		if (categories != null) {
			loadChildren(rootItem, categories);
		}
		tree = new TreeView<CategoryDTO>(rootItem);
		tree.getSelectionModel().selectedItemProperty().addListener(new ChangeListener() {

			@Override
			public void changed(ObservableValue observable, Object oldValue, Object newValue) {
				setEditMode(false);
				selectedItem = (TreeItem<CategoryDTO>) newValue;
				if (selectedItem != null && selectedItem.getParent() != null) {
					updateUI(selectedItem.getValue(), selectedItem.getParent().getValue(),
							selectedItem.getParent() == tree.getRoot());
				}

				// do what ever you want
			}

		});
		g.add(tree, 0, 0);
	}

	private void updateUI(CategoryDTO c, CategoryDTO parent, boolean isRoot) {
		category = c;
		textFieldName.setText(c.getName());
		comboBoxDPH.setValue(c.getVat());
		if (!isRoot) {
			textFieldParent.setText(parent.getName());
			parentCategory = parent;
		} else {
			textFieldParent.setText("");
			parentCategory = null;
		}
	}

	private void loadChildren(TreeItem<CategoryDTO> rootItem, List<CategoryDTO> categories) {
		for (CategoryDTO categoryDTO : categories) {
			TreeItem<CategoryDTO> item = new TreeItem<CategoryDTO>(categoryDTO);
			rootItem.getChildren().add(item);
			if (categoryDTO.getChildCategories().size() > 0) {
				loadChildren(item, categoryDTO.getChildCategories());
			}
		}
	}

	protected void createButtons(HBox hbox) {
		LiveButton buttonDone = new LiveButton("Zp\u011Bt");
		buttonDone.setOnAction(new EventHandler<ActionEvent>() {

			@Override
			public void handle(ActionEvent arg0) {
				if (editMode) {
					setEditMode(false);
					if (selectedItem != null && selectedItem.getParent() != null) {
						updateUI(selectedItem.getValue(), selectedItem.getParent().getValue(),
								selectedItem.getParent() == tree.getRoot());
					}
				} else {
					controller.mainMenu();
				}
			}
		});

		buttonAdd = new LiveButton("P\u0159idat podkategorii");
		buttonAdd.setOnAction(new EventHandler<ActionEvent>() {

			@Override
			public void handle(ActionEvent arg0) {

				category = new CategoryDTO();
				// parentCategory.getChildCategories().add(category);
				updateUI(category, selectedItem.getValue(), selectedItem == tree.getRoot());
				setEditMode(true);
			}
		});
		hbox.getChildren().add(buttonAdd);

		buttonEdit = new LiveButton("Editovat");
		buttonEdit.setOnAction(new EventHandler<ActionEvent>() {

			@Override
			public void handle(ActionEvent arg0) {

				if (category == null) {
					AlertHelper.showInfoDialog("Nen\u00ED zvolena kategorie.", "Zvolte kategorii !");
				} else {
					setEditMode(true);
				}
			}
		});
		hbox.getChildren().add(buttonEdit);

		buttonDelete = new LiveButton("Smazat");
		buttonDelete.setOnAction(new EventHandler<ActionEvent>() {

			@Override
			public void handle(ActionEvent arg0) {
				CategoryDTO cat = selectedItem.getValue();
				boolean b = AlertHelper.showConfirmDialog("Opravdu smazat kategorii ?", cat.getName());
				if (b) {
					// o.setDeleted(true);
					try {
						controller.getModel().deleteCategory(cat);
						controller.resetCategories();
						refresh();
					} catch (org.springframework.dao.DataIntegrityViolationException ex) {
						AlertHelper.showDialog("Kategorii nelze smazat.",
								"Z d\u016Fvodu existence pod\u0159\u00EDzen\u00FDch z\u00E1znam\u016F (produkt\u016F nebo podkategori\u00ED).",
								javafx.scene.control.Alert.AlertType.ERROR);
					}
				}
			}
		});
		hbox.getChildren().add(buttonDelete);

		buttonSave = new LiveButton("Ulo\u017Eit");
		buttonSave.setOnAction(new EventHandler<ActionEvent>() {

			@Override
			public void handle(ActionEvent arg0) {
				if(StringUtils.isEmpty(textFieldName.getText())) {
					AlertHelper.showInfoDialog("Vypl\u0148te n\u00E1zev !", "");
					return;
				}
				category.setName(textFieldName.getText());
				DPH dph = (DPH) comboBoxDPH.getValue();
				if(StringUtils.isEmpty(dph)) {
					AlertHelper.showInfoDialog("Vypl\u0148te DPH !", "");
					return;
				}
				category.setVat(dph );
				if (category.getId() == null) {
					category.setParentCategory(parentCategory);
					category.setId(controller.getModel().createCategory(category));
					/*
					 * parentCategory.getChildCategories().add(category);
					 * controller.getModel().updateCategory(parentCategory);
					 */
				} else {
					category.setParentCategory(parentCategory);
					controller.getModel().updateCategory(category);
				}
				controller.resetCategories();
				refresh();
			}
		});
		hbox.getChildren().add(buttonSave);

		hbox.getChildren().add(buttonDone);
	}

	protected void setEditMode(boolean b) {
		this.editMode = b;
		this.tree.setEditable(!b);
		buttonEdit.setVisible(!b);
		buttonDelete.setVisible(!b);
		buttonAdd.setVisible(!b);
		buttonSave.setVisible(b);
		textFieldName.setEditable(b);
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
		setEditMode(false);
		return borderPane;
	}

	protected Node createMainContent() {

		GridPane grid = new GridPane();
		grid.setHgap(20);
		grid.setVgap(20);
		grid.setPadding(new Insets(30, 0, 0, 70));

		javafx.scene.control.Label label1 = new Label("N\u00E1zev kategorie:");
		textFieldName = new TextField();
		grid.add(label1, 0, 0);
		grid.add(textFieldName, 1, 0);

		label1 = new Label("Nad\u0159azen\u00E1 kategorie:");
		textFieldParent = new TextField();
		textFieldParent.setEditable(false);
		grid.add(label1, 0, 1);
		grid.add(textFieldParent, 1, 1);

		countDPH = Boolean.parseBoolean(AppPropertiesProxy.get(Constants.CONFIG_COUNT_DPH));
		if (countDPH) {
			label1 = new Label("Sazba DPH:");
			ObservableList<DPH> unitList = FXCollections.observableArrayList(getDPHList());
			comboBoxDPH = new ComboBox(unitList);
			grid.add(label1, 0, 3);
			grid.add(comboBoxDPH, 1, 3);
		}

		/*
		 * comboBoxDPH.setOnAction(new EventHandler<ActionEvent>() {
		 * 
		 * @Override public void handle(ActionEvent event) {
		 * labelCriticalAmount.setText(String.format(LABEL_CRITICAL_AMOUNT,
		 * comboBoxUnits.getValue()));
		 * 
		 * } });
		 */

		return grid;
	}

	private List<DPH> getDPHList() {
		return Arrays.asList(DPH.values());
	}
}
