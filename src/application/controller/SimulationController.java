package application.controller;

import application.view.Main;
import javafx.fxml.FXML;
import javafx.scene.Scene;
import javafx.scene.canvas.Canvas;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.control.Label;
import javafx.scene.layout.Pane;
import javafx.scene.paint.Color;
import javafx.scene.shape.Circle;
import javafx.stage.Stage;

public class SimulationController extends Canvas {
	private Stage dialogStage;
	Main mainApp;
	@FXML
	Canvas canvas;
	private GraphicsContext gc;
	
	@FXML
	private Circle k1;
	@FXML
	private Circle k2;
	@FXML
	private Circle k3;
	@FXML
	private Circle k4;
	@FXML
	private Circle k5;
	@FXML
	private Circle k6;
	
	@FXML
	private Circle ik1;
	@FXML
	private Circle ik2;
	@FXML
	private Circle ik3;
	@FXML
	private Circle ik4;
	@FXML
	private Circle ik5;
	@FXML
	private Circle ik6;
	
	
	Kontrolleri kontrolleri;
	
	
	public SimulationController() {
	
	}
	
	public void setController(Kontrolleri kontrolleri) {
		this.kontrolleri = kontrolleri;
	}
	
	public void setDialogStage(Stage dialogStage) {
        this.dialogStage = dialogStage;
    }
	
	@FXML
    private void initialize() {
		
    }
	
	private void initUI() {

        Pane root = new Pane();

        Canvas canvas = new Canvas(300, 300);
        gc = canvas.getGraphicsContext2D();
        //drawLines(gc);

        root.getChildren().add(canvas);

        Scene scene = new Scene(root, 300, 250, Color.WHITESMOKE);

        dialogStage.setTitle("Lines");
        dialogStage.setScene(scene);
        dialogStage.show();
    }

	public void setMainApp(Main mainApp) {
		this.mainApp = mainApp;
		
	}
	
}
