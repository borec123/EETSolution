package cz.borec.demo.gui.controls;

import java.util.Optional;

import org.apache.commons.lang.StringUtils;

import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.ButtonType;
import javafx.scene.layout.GridPane;
import javafx.stage.Modality;
import javafx.stage.Stage;

public class AlertHelper {

	private static Scene amountScene;

	public static void showInfoDialog(String msg, String msg2) {
		Alert alert = new Alert(AlertType.INFORMATION);
		alert.setTitle("Chybov\u00E1 zpr\u00E1va");
		alert.setHeaderText(msg);
		if (!StringUtils.isEmpty(msg2)) {
			alert.setContentText(msg2);
		}
		alert.showAndWait();

	}

	public static boolean showConfirmDialog(String msg, String msg2) {
		Alert alert = new Alert(AlertType.CONFIRMATION);
		alert.setTitle("Potvrzen\u00ED");
		alert.setHeaderText(msg);
		if (!StringUtils.isEmpty(msg2)) {
			alert.setContentText(msg2);
		}
		Optional<ButtonType> r = alert.showAndWait();
		if (r.isPresent()) {
			if(r.get() == ButtonType.OK) {
				return true;
			}
		}

		return false;
	}

	public static void showDialog(String msg, String msg2, AlertType alertType) {
		Alert alert = new Alert(alertType);
		alert.setTitle("Chybov\u00E1 zpr\u00E1va");
		alert.setHeaderText(msg);
		if (!StringUtils.isEmpty(msg2)) {
			alert.setContentText(msg2);
		}
		alert.showAndWait();

	}

	public static void showModalDialog(GridPane root, String title) {
		Scene scene = new Scene(root);
		scene.getStylesheets().add("css/style.css");
		Stage stage = new Stage();
		stage.setTitle(title);
		stage.setScene(scene);
		stage.initModality(Modality.APPLICATION_MODAL);
		stage.showAndWait();
	}	
	
	public static void showAmountDialog(GridPane root, String title) {
		if(amountScene == null) {
			amountScene = new Scene(root);
		}
		else {
			amountScene.setRoot(root);
		}
		amountScene.getStylesheets().add("css/style.css");
		Stage amountStage = new Stage();
		amountStage.setTitle(title);
		amountStage.setScene(amountScene);
		amountStage.initModality(Modality.APPLICATION_MODAL);
		amountStage.showAndWait();
	}

	public static void showDiscountDialog(DiscountPane discountPane,
			String title) {
		if(amountScene == null) {
			amountScene = new Scene(discountPane);
		}
		else {
			amountScene.setRoot(discountPane);
		}
		amountScene.getStylesheets().add("css/style.css");
		Stage amountStage = new Stage();
		amountStage.setTitle(title);
		amountStage.setScene(amountScene);
		amountStage.initModality(Modality.APPLICATION_MODAL);
		amountStage.showAndWait();
	}

	public static void showTableNameDialog(TableNamePane discountPane,
			String title) {
		if(amountScene == null) {
			amountScene = new Scene(discountPane);
		}
		else {
			amountScene.setRoot(discountPane);
		}
		amountScene.getStylesheets().add("css/style.css");
		Stage amountStage = new Stage();
		amountStage.setTitle(title);
		amountStage.setScene(amountScene);
		amountStage.initModality(Modality.APPLICATION_MODAL);
		amountStage.showAndWait();
	}

}
