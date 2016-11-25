package cz.borec.demo.gui;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Node;
import javafx.scene.control.Button;
import javafx.scene.control.CheckBox;
import javafx.scene.control.ComboBox;
import javafx.scene.control.Label;
import javafx.scene.control.Labeled;
import javafx.scene.control.TextField;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Pane;
import javafx.stage.FileChooser;
import javafx.util.Callback;

import org.springframework.util.StringUtils;

import cz.borec.demo.Constants;
import cz.borec.demo.CustomProperties;
import cz.borec.demo.Mode;
import cz.borec.demo.PrinterWidth;
import cz.borec.demo.core.entity.UnitEntity;
import cz.borec.demo.gui.controls.AlertHelper;
import cz.borec.demo.gui.controls.AppPropertiesProxy;
import cz.borec.demo.gui.controls.BlueText;
import cz.borec.demo.gui.controls.LiveButton;

public class SettingsPane extends AbstractPaneBase {

	private static final String LABEL_CRITICAL_AMOUNT = "Kritick\u00E9 mno\u017Estv\u00ED (%s):";
	private TextField textFieldName;
	// private TextField textFieldPrice;
	private ComboBox comboBoxUnits;
	private TextField textFieldResataurant;
	private CheckBox checkBoxPrintingDialog;
	private CheckBox checkBoxShowDPH;
	private TextField textFieldICO;
	private TextField textFieldCustomerStreet;
	private TextField textFieldCustomerAddress;
	private TextField textFieldCustomerName;
	protected File f;
	private ComboBox comboBoxMode;
	private ComboBox comboBoxSize;
	private ComboBox comboBoxFIMode;
	private ComboBox comboBoxSkin;


	public SettingsPane(Controller controller) {
		super(controller);
	}

	@Override
	protected void fillHorizontalBox(HBox hbox) {
		Button buttonMenu = createMenuButton();
		hbox.getChildren().add(buttonMenu);
		BlueText arrow = new BlueText("\u2192");
		hbox.getChildren().add(arrow);
		arrow = new BlueText("Nastaven\u00ED");
		hbox.getChildren().add(arrow);
	}

	@Override
	protected void fillVBox(GridPane vbox) {
		// TODO Auto-generated method stub

	}

	@Override
	public Pane addGridPane() {
		BorderPane borderPane = new BorderPane();

		borderPane.setCenter(createMainPane());

		HBox hbox = new HBox();
		hbox.setPadding(new Insets(8, 10, 8, 10));
		hbox.setSpacing(10);
		hbox.setAlignment(Pos.CENTER_RIGHT);
		borderPane.setBottom(hbox);

		return borderPane;
	}

	protected void createButtons(HBox hbox) {
		LiveButton buttonDone = new LiveButton("OK");
		buttonDone.setOnAction(new EventHandler<ActionEvent>() {


			@Override
			public void handle(ActionEvent arg0) {

				if (StringUtils.hasText(textFieldName.getText())) {
					saveConfig(Constants.CONFIG_CERTIFICATE_PATH, textFieldName.getText());
				}
				saveConfig(Constants.CONFIG_RESTAURANT_NAME, textFieldResataurant.getText());
				saveConfig(Constants.CONFIG_CUSTOMER_NAME, textFieldCustomerName.getText());
				saveConfig(Constants.CONFIG_CUSTOMER_STREET, textFieldCustomerStreet.getText());
				saveConfig(Constants.CONFIG_CUSTOMER_ADDRESS, textFieldCustomerAddress.getText());
				saveConfig(Constants.CONFIG_CUSTOMER_ICO, textFieldICO.getText());
				saveConfig(Constants.CONFIG_PRINTING_DIALOG, String.valueOf(checkBoxPrintingDialog.isSelected()));
				saveConfig(Constants.CONFIG_COUNT_DPH, String.valueOf(checkBoxShowDPH.isSelected()));
				saveConfig(Constants.CONFIG_PRINTING_WIDTH, ((PrinterWidth)comboBoxUnits.getValue()).name());
				saveConfig(Constants.CONFIG_DISPLAY_SIZE, comboBoxSize.getValue().toString());
				saveConfig(Constants.CONFIG_SKIN, comboBoxSkin.getValue().toString());
				saveConfig(Constants.CONFIG_FI_MODE, "Testovac\u00ED".equals(comboBoxFIMode.getValue().toString()) ? "true" : "false");

				Mode a = Mode.valueOf(comboBoxMode.getValue().toString());
				switch(a) {
				case Standalone:
					saveConfig(Constants.CONFIG_IS_MULTINODED, "false");
					saveConfig(Constants.CONFIG_IS_SERVER, "true");
					break;
				case Server:
					saveConfig(Constants.CONFIG_IS_MULTINODED, "true");
					saveConfig(Constants.CONFIG_IS_SERVER, "true");
					break;
				case Klient:
					saveConfig(Constants.CONFIG_IS_MULTINODED, "true");
					saveConfig(Constants.CONFIG_IS_SERVER, "false");
					break;
				}
				
				try {
					CustomProperties.getProperties().storePropertiesToFile();
				} catch (Exception e) {
					e.printStackTrace();
					AlertHelper.showInfoDialog("Chyba.",
							"Zm\u011Bny konfigurace neulozeny.");
				}
				
				//controller.getModel().loadProperties();
				AlertHelper.showInfoDialog("Ulo\u017Eeno.",
						"N\u011Bkter\u00E9 zm\u011Bny se projev\u00ED po restartu aplikace.");

				// controller.productSearchPane(ProductSearchMode.PRODUCTS);
			}

			private void saveConfig(String configCertificatePath, String text) {
				/*ConfigEntity configCertificate = new ConfigEntity();
				configCertificate.setId(configCertificatePath);
				configCertificate.setValue(text);

				controller.getModel().saveOrUpdateConfig(configCertificate);*/
				
				CustomProperties.getProperties().setProperty(configCertificatePath, text);
				
			}
		});

		LiveButton buttonAdd = new LiveButton("Zru\u0161it");
		buttonAdd.setOnAction(new EventHandler<ActionEvent>() {

			@Override
			public void handle(ActionEvent arg0) {

				controller.mainMenu();
			}
		});
		hbox.getChildren().add(buttonDone);
		hbox.getChildren().add(buttonAdd);

	}

	protected Node createMainContent() {

		GridPane grid = new GridPane();
		grid.setHgap(20);
		grid.setVgap(20);
		grid.setPadding(new Insets(30, 0, 0, 70));

		javafx.scene.control.Label label1 = new Label("Certifik\u00E1t:");
		textFieldName = new TextField();
		textFieldName.setEditable(false);
		textFieldName.setPrefWidth(300);
		grid.add(label1, 0, 0);

		grid.add(textFieldName, 1, 0);

		LiveButton button = new LiveButton("Zm\u011Bnit");

		grid.add(button, 1, 1);

		button.setOnAction(new EventHandler<ActionEvent>() {

			@Override
			public void handle(ActionEvent arg0) {
				javafx.stage.FileChooser fileChooser = new javafx.stage.FileChooser();
				fileChooser.setTitle("Certifik\u00E1t");
				if (f != null) {
					fileChooser.setInitialDirectory(f.getParentFile());
					;
				}
				FileChooser.ExtensionFilter extFilter = new FileChooser.ExtensionFilter(
						"Soubory certifik\u00E1t\u016F (*.p12)", "*.p12");
				fileChooser.getExtensionFilters().add(extFilter);
				/*
				 * ExtensionFilter filter = new ExtensionFilter("Shut up !",
				 * Arrays.asList(new String[] { "*.p12" }));
				 * fileChooser.setSelectedExtensionFilter(filter );
				 */
				f = fileChooser.showOpenDialog(controller.getPrimaryStage());
				if (f != null) {
					textFieldName.setText(f.toString());
				}
			}
		});

		/*
		 * label1 = new Label("Cena (kc):"); textFieldPrice = new TextField ();
		 * grid.add(label1, 0, 1); grid.add(textFieldPrice, 1, 1);
		 */

		label1 = new Label("N\u00E1zev provozovny:");
		textFieldResataurant = new TextField();
		grid.add(label1, 0, 2);
		grid.add(textFieldResataurant, 1, 2);

		
		label1 = new Label("\u0160\u00ED\u0159ka tisk\u00E1rny:");
		ObservableList<PrinterWidth> unitList = FXCollections
				.observableArrayList(getEmptyUnitList());

		comboBoxUnits = new ComboBox(unitList);
		grid.add(label1, 0, 3);
		grid.add(comboBoxUnits, 1, 3);

		comboBoxUnits.setOnAction(new EventHandler<ActionEvent>() {

			@Override
			public void handle(ActionEvent event) {

			}
		});

		label1 = new Label("M\u00F3d:");
		ObservableList<Mode> modeList = FXCollections
				.observableArrayList(getModeList());

		comboBoxMode = new ComboBox(modeList);
		grid.add(label1, 0, 4);
		grid.add(comboBoxMode, 1, 4);

		comboBoxMode.setOnAction(new EventHandler<ActionEvent>() {

			@Override
			public void handle(ActionEvent event) {

			}
		});

		label1 = new Label("I\u010CO:");
		textFieldICO = new TextField();
		grid.add(label1, 2, 0);
		grid.add(textFieldICO, 3, 0);
		
		label1 = new Label("Jm\u00E9no podnikatele (n\u00E1zev):");
		textFieldCustomerName = new TextField();
		grid.add(label1, 2, 1);
		grid.add(textFieldCustomerName, 3, 1);
		
		label1 = new Label("Adresa (Ulice a \u010Dp.):");
		textFieldCustomerStreet = new TextField();
		grid.add(label1, 2, 2);
		grid.add(textFieldCustomerStreet, 3, 2);
		
		label1 = new Label("PS\u010C a M\u011Bsto:");
		textFieldCustomerAddress = new TextField();
		grid.add(label1, 2, 3);
		grid.add(textFieldCustomerAddress, 3, 3);
		
		label1 = new Label("Velikost zobrazen\u00ED:");
		ObservableList<Integer> list11 = FXCollections
				.observableArrayList(Arrays.asList(new Integer[] {1, 2, 3, 4}));
		comboBoxSize = new ComboBox(list11);
		grid.add(label1, 2, 4);
		grid.add(comboBoxSize, 3, 4);

		label1 = new Label("Vzhled:");
		ObservableList<Integer> skins = FXCollections
				.observableArrayList(Arrays.asList(new Integer[] {1, 2, 3}));
		comboBoxSkin = new ComboBox(skins);
		grid.add(label1, 2, 6);
		grid.add(comboBoxSkin, 3, 6);

		label1 = new Label("Provoz:");
		ObservableList<String> listFiMode = FXCollections
				.observableArrayList(Arrays.asList(new String[] {"Testovac\u00ED", "Ostr\u00FD"}));
		comboBoxFIMode = new ComboBox(listFiMode);
		grid.add(label1, 2, 5);
		grid.add(comboBoxFIMode, 3, 5);

		
		checkBoxPrintingDialog = new CheckBox();
		label1 = new Label("Zobrazit dialog p\u0159ed tiskem:");
		grid.add(label1, 0, 5);
		grid.add(checkBoxPrintingDialog, 1, 5);
		
		checkBoxShowDPH = new CheckBox();
		label1 = new Label("DPH:");
		grid.add(label1, 0, 6);
		grid.add(checkBoxShowDPH, 1, 6);
		return grid;
	}

	private List<Mode> getModeList() {
		
		return Arrays.asList(Mode.values());
	}

	private List<PrinterWidth> getEmptyUnitList() {
		return Arrays.asList(PrinterWidth.values());
	}
	
	public void loadProperties() {
		textFieldName.setText(AppPropertiesProxy.get(Constants.CONFIG_CERTIFICATE_PATH));
		textFieldResataurant.setText(AppPropertiesProxy.get(Constants.CONFIG_RESTAURANT_NAME));
		textFieldICO.setText(AppPropertiesProxy.get(Constants.CONFIG_CUSTOMER_ICO));
		textFieldCustomerName.setText(AppPropertiesProxy.get(Constants.CONFIG_CUSTOMER_NAME));
		textFieldCustomerStreet.setText(AppPropertiesProxy.get(Constants.CONFIG_CUSTOMER_STREET));
		textFieldCustomerAddress.setText(AppPropertiesProxy.get(Constants.CONFIG_CUSTOMER_ADDRESS));
		
		comboBoxSize.setValue(Integer.parseInt(AppPropertiesProxy.get(Constants.CONFIG_DISPLAY_SIZE)));
		
		comboBoxFIMode.setValue(Boolean.parseBoolean(AppPropertiesProxy.get(Constants.CONFIG_FI_MODE)) ? "Testovac\u00ED": "Ostr\u00FD");
		
		comboBoxSkin.setValue(Integer.parseInt(AppPropertiesProxy.get(Constants.CONFIG_SKIN)));
		
		comboBoxUnits.setValue(PrinterWidth.valueOf(
				AppPropertiesProxy.get(Constants.CONFIG_PRINTING_WIDTH)));
		
		boolean multinoded = Boolean.parseBoolean((String) AppPropertiesProxy.get(Constants.CONFIG_IS_MULTINODED));
		boolean server = Boolean.parseBoolean((String) AppPropertiesProxy.get(Constants.CONFIG_IS_SERVER));
		
		if(!multinoded) {
			comboBoxMode.setValue(Mode.Standalone);
		}
		else {
			if(server) {
				comboBoxMode.setValue(Mode.Server);
			}
			else {
				comboBoxMode.setValue(Mode.Klient);
			}
		}
		
		boolean b = Boolean.parseBoolean(AppPropertiesProxy.get(Constants.CONFIG_PRINTING_DIALOG));
		checkBoxPrintingDialog.setSelected(b );
		boolean c = Boolean.parseBoolean(AppPropertiesProxy.get(Constants.CONFIG_COUNT_DPH));
		checkBoxShowDPH.setSelected(c );
	}

}
