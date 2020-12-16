package application.view;
	

import java.io.IOException;

import javax.swing.ImageIcon;

import com.gembox.spreadsheet.SpreadsheetInfo;
import application.controller.*;
import application.model.Trace;
import application.model.Trace.Level;
import javafx.application.Application;
import javafx.application.Platform;
import javafx.event.EventHandler;
import javafx.fxml.FXMLLoader;
import javafx.stage.Stage;
import javafx.stage.WindowEvent;
import javafx.scene.*;
import javafx.scene.control.*;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.*;



/**
 * Ohjelman pääluokka, käynnistyy ohjelman käynnistyessä
 * @author Samuel
 *
 */
public class Main extends Application implements GuiIf{

	//Kontrollerin esittely (tarvitaan käyttöliittymässä)
	private Kontrolleri kontrolleri;

	// Käyttöliittymäkomponentit:
	/**
	 * Tekstikenttä, josta saamme simulointiajan
	 */
	private TextField simulationTime;
	/**
	 * Tekstikenttä, josta saamme viiveen
	 */
	private TextField delayTime;
	/**
	 * Tekstikenttä, josta saamme kassojen määrän
	 */
	private TextField kassojenMäärä;
	/**
	 * Juurinäkymä
	 */
	private BorderPane rootLayout;
	/**
	 * Päänäyttämö
	 */
	public Stage primaryStage;
	/**
	 * Näyttö, joka vastaa visualisoinnin toteuttamisesta
	 */
	private Visualisointi naytto;


	/**
	 * Asettaa kontrollerin ja TraceLevelin Infoksi
	 */
	@Override
	public void init(){		
		Trace.setTraceLevel(Level.INFO);
		kontrolleri = new Kontrolleri(this);
	}

	/**
	 * Näyttää päänäyttämön ja siihen kuuluvat layoutit (root, launcher, simulation)
	 */
	@Override
	public void start(Stage primaryStage) {
		// Käyttöliittymän rakentaminen
		try {
			
			primaryStage.setOnCloseRequest(new EventHandler<WindowEvent>() {
			    @Override
			    public void handle(WindowEvent t) {
			        Platform.exit();
			        System.exit(0);
			    }
			});					
			this.primaryStage = primaryStage;
	        this.primaryStage.setTitle("SuperMarket");
	        
	        // Set the application icon.
	        Image carticon = new Image(getClass().getResource("img/carticon.png").toExternalForm());
	        this.primaryStage.getIcons().add(carticon);
	        	        
	        initRootLayout();
	        showLauncher();
	        showSimulationView();
		} catch(Exception e) {
			e.printStackTrace();
		}
	}
	
	/**
	 * Asettaa Launcherin (UI) juurinäkymään.
	 */
	public void showLauncher() {
    	try {
            // Load person overview.
            FXMLLoader loader = new FXMLLoader();
            loader.setLocation(Main.class.getResource("Launcher.fxml"));
            AnchorPane launcher = (AnchorPane) loader.load();
            
            // Set person overview into the center of root layout.
           
            rootLayout.setCenter(launcher);
           
            // Give the controller access to the main app.
            LauncherController controller = loader.getController();
            controller.setController(kontrolleri);
            controller.setMainApp(this);

        } catch (IOException e) {
            e.printStackTrace();
        }
    }
	
	
	
	/**
	 * Asettaa simulointinäkymän juurinäkymään
	 */
	public void showSimulationView() {
		
		try {
            // Load person overview.
            FXMLLoader loader = new FXMLLoader();
            loader.setLocation(Main.class.getResource("SimulationView.fxml"));
            AnchorPane launcher = (AnchorPane) loader.load();
            naytto = new Visualisointi(868, 565);
            launcher.getChildren().add(naytto);
            		                
            rootLayout.setRight(launcher);
            
            // Set person overview into the center of root layout.

            // Give the controller access to the main app.
            
            SimulationController controller = loader.getController();
            controller.setController(kontrolleri);
            controller.setMainApp(this);
        } catch (IOException e) {
            e.printStackTrace();
        }
	}
	
	/**
	 * Lataa juurinäkymän ja asettaa sen päänäyttämöksi
	 */
	public void initRootLayout() {
        try {
            // Load root layout from fxml file.
            FXMLLoader loader = new FXMLLoader();
            loader.setLocation(Main.class.getResource("RootLayout.fxml"));
            rootLayout = (BorderPane) loader.load();
            RootLayoutController controller = loader.getController();
            
            // Show the scene containing the root layout.
            Scene scene = new Scene(rootLayout);
            primaryStage.setScene(scene);
            primaryStage.show();
            controller.setController(kontrolleri);
            controller.setMainApp(this);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }


	// Käyttöliittymän rajapintametodit (kutsutaan kontrollerista)

	/**
	 * Metodi, jolla haemme kassojen määrän tekstikentästä
	 */
	@Override
	public int getKassojenmaara(){
		return Integer.parseInt(kassojenMäärä.getText());
	}
	
	/**
	 * Metodi, jolla haemme simulointiajan tekstikentästä
	 */
	@Override
	public double getAika(){
		return Double.parseDouble(simulationTime.getText());
	}

	/**
	 * Metodi, jolla haemme viiveen tekstikentästä
	 */
	@Override
	public long getViive(){
		return Long.parseLong(delayTime.getText());
	}


	/**
	 * Metodi, jolla saamme visualisoinninsta vastaavan olion
	 */
	@Override
	public Visualisointi getVisualisointi() {
		 return naytto;
	}
	
	public Main getMainApp() {
		return this;
	}
	
	// JavaFX-sovelluksen (käyttöliittymän) käynnistäminen

	/**
	 * Main funktio, joka käynnistää ohjelman
	 * @param args
	 */
	public static void main(String[] args) {
		SpreadsheetInfo.setLicense("FREE-LIMITED-KEY");
		launch(args);
	}
	
}
