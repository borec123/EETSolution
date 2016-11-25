package cz.borec.demo.gui;

import java.net.URL;
import java.util.List;

import org.springframework.transaction.CannotCreateTransactionException;

import cz.borec.demo.AppProperties;
import cz.borec.demo.Constants;
import cz.borec.demo.gui.controls.AlertHelper;
import cz.borec.demo.gui.controls.AppPropertiesProxy;
import cz.borec.demo.gui.controls.BlueText;
import cz.borec.demo.gui.controls.Colors;
import cz.borec.demo.gui.controls.LiveButton;
import cz.borec.demo.util.DatabaseConnectionTester;
import cz.borec.demo.util.H2DatabaseStarter;
import javafx.application.Application;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Node;
import javafx.scene.Scene;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.effect.DropShadow;
import javafx.scene.layout.Background;
import javafx.scene.layout.BackgroundFill;
import javafx.scene.layout.CornerRadii;
import javafx.scene.layout.VBox;
import javafx.scene.layout.BorderPane;
import javafx.scene.paint.Color;
import javafx.stage.Stage;
import javafx.stage.WindowEvent;

public class Main extends Application {

	private static final String CAPTION = "Java EET Solution";
	LiveButton btn = new LiveButton();
	private Scene scene;
	private Controller controller;

	@Override
	public void start(Stage primaryStage) {
		try {
			
/*			new Thread(new Runnable() {
				@Override
				public void run() {
					 final Stage dialog = new Stage();
		             dialog.initModality(javafx.stage.Modality.APPLICATION_MODAL);
		             dialog.initOwner(primaryStage);
		             VBox dialogVbox = new VBox(20);
		             dialogVbox.getChildren().add(new javafx.scene.text.Text("Hospoda startuje ..."));
		             Scene dialogScene = new Scene(dialogVbox, 300, 200);
		             dialog.setScene(dialogScene);
		             dialog.show();
		             try {
						Thread.sleep(2000);
					} catch (InterruptedException e) {
						e.printStackTrace();
					}
		             dialog.close();
			}
			}).start();
*/			
			
			btn.setText("Zku\u0161ebn\u00ED verze");
			btn.setTextFill(Color.WHITE);

			final DropShadow shadow = new DropShadow();
			btn.setOnAction(new EventHandler<ActionEvent>() {

				@Override
				public void handle(ActionEvent event) {
					System.out.println("Hello World!");

					// btn.setEffect(shadow);
					// btn.setEffect(null);

					/*
					 * Alert alert = new Alert(AlertType.INFORMATION);
					 * alert.setTitle("Information Dialog");
					 * alert.setHeaderText("Look, an Information Dialog");
					 * alert.setContentText("I have a great message for you!");
					 * 
					 * alert.showAndWait();
					 */
					// print();

					controller.mainMenu();
					// scene.setRoot(productSearchPane );
				}
			});

			BorderPane root = new BorderPane();
			VBox hbox3 = new VBox();
			hbox3.setAlignment(Pos.BOTTOM_CENTER);

			/*
			 * URL url =
			 * getClass().getClassLoader().getResource("images/light2.jpg");
			 * String image = url.toString();
			 * 
			 * root.setStyle("-fx-background-image: url('" + image + "'); " +
			 * "  -fx-background-position: center center; " +
			 * " -fx-background-repeat: stretch;");
			 */
			Color c;
			switch (Integer.parseInt(AppPropertiesProxy.get(Constants.CONFIG_SKIN))) {
			case 2:
				c = Color.web("#222222");
				root.setStyle(" -fx-background-color:  linear-gradient(to bottom right, transparent, " + 
						   " #202020 25%, #888888 50%); ");
				break;
			case 3:
				c =  Color.web("#00B3C7");
				root.setBackground(new Background(new BackgroundFill(c,
						new CornerRadii(5.0), Insets.EMPTY)));
				break;

			default:
/*				c = Color.LIGHTBLUE;
				root.setBackground(new Background(new BackgroundFill(c,
						new CornerRadii(5.0), Insets.EMPTY)));
*/				root.setStyle(" -fx-background-color:  linear-gradient(to bottom right, transparent, " + 
		   " #4286f4 25%, #cfe0fc 50%); ");
				break;
			}

			BlueText everywhere = new BlueText(CAPTION, 24);
			// everywhere.getFont()
			hbox3.getChildren().add(new BlueText(""));
			hbox3.getChildren().add(new BlueText(""));
			hbox3.getChildren().add(new BlueText(""));
			hbox3.getChildren().add(new BlueText(""));
			hbox3.getChildren().add(new BlueText(""));
			hbox3.getChildren().add(everywhere);
			// hbox3.getChildren().add(btn);
			root.setTop(hbox3);
			root.setCenter(btn);

			hbox3 = new VBox();
			hbox3.setAlignment(Pos.CENTER);
			everywhere = new BlueText("", 24);
			// everywhere.getFont()
			hbox3.getChildren().add(new BlueText(""));
			hbox3.getChildren().add(new BlueText(""));
			hbox3.getChildren().add(new BlueText(""));
			hbox3.getChildren().add(new BlueText(""));
			hbox3.getChildren().add(new BlueText(""));
			hbox3.getChildren().add(everywhere);

			root.setBottom(hbox3);

			scene = new Scene(root, 1000, 500);
			controller = new Controller(scene, primaryStage);

			scene.getStylesheets()
					.add(AppProperties.getProperties().getStyle());

			String mode;
			if (!Boolean.parseBoolean(AppPropertiesProxy.get(Constants.CONFIG_IS_MULTINODED))) {
				mode = "Standalone";
			} else if (Boolean.parseBoolean(AppPropertiesProxy.get(Constants.CONFIG_IS_SERVER))) {
				mode = "Server";
			} else {
				mode = "Klient";
			}
			primaryStage.setTitle(CAPTION + " - zku\u0161ebn\u00ED verze"
					+ String.format(" (m\u00F3d %s)", mode));
			primaryStage.setScene(scene);

			primaryStage.setOnCloseRequest(new EventHandler<WindowEvent>() {
				public void handle(WindowEvent we) {
					System.exit(0);
				}
			});

			primaryStage.show();
		} catch (CannotCreateTransactionException ex) {
			ex.printStackTrace();
			AlertHelper.showDialog("Chyba !",
					"Datab\u00E1zov\u00E1 komponenta neb\u011B\u017E\u00ED !",
					AlertType.ERROR);
		} catch (RuntimeException ex) {
			ex.printStackTrace();
			AlertHelper.showDialog("Chyba !", "", AlertType.ERROR);

		} catch (Exception ex) {
			ex.printStackTrace();
			AlertHelper.showDialog("Chyba !", ex.toString(), AlertType.ERROR);
		}
	}

	/*
	 * protected void print() { Node node = new Label("Fantomas");
	 * javafx.print.PrinterJob job = javafx.print.PrinterJob.createPrinterJob();
	 * if (job != null) { boolean success = job.printPage(node); if (success) {
	 * job.endJob(); } }
	 * 
	 * }
	 */

	public static void main(String[] args) {
		
/*		List<String> l = javafx.scene.text.Font.getFamilies();
		l.forEach(k -> System.out.println(k));*/

		boolean b = DatabaseConnectionTester.testConnection();
		if (Boolean.parseBoolean(AppPropertiesProxy.get(Constants.CONFIG_IS_MULTINODED))
				&& Boolean.parseBoolean(AppPropertiesProxy.get(Constants.CONFIG_IS_SERVER))) {
			if (!b) {
				try {
					int a = H2DatabaseStarter.startDatabase();
					System.out.print("Starting database.");
					for (int i = 0; i < 3; i++) {
						Thread.sleep(1000);
						System.out.print(".");
					}
					System.out.println();
					b = DatabaseConnectionTester.testConnection();
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		}
		/*
		 * if (!b) { AlertHelper.showDialog("Chyba !",
		 * "Datab\u00E1zov\u00E1 komponenta neb\u011B\u017E\u00ED !",
		 * AlertType.ERROR); } else {
		 */launch(args);
		/* } */

		System.out.println("Konec");
	}
}