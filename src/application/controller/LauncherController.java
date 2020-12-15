package application.controller;

import java.io.IOException;
import java.text.DecimalFormat;
import java.text.NumberFormat;

import application.model.Kello;
import application.model.Moottori;
import application.view.GuiIf;
import application.view.Main;
import javafx.fxml.FXML;
import javafx.scene.canvas.Canvas;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.RadioButton;
import javafx.scene.control.Spinner;
import javafx.scene.control.SpinnerValueFactory.IntegerSpinnerValueFactory;
import javafx.scene.control.TextField;
import javafx.scene.control.ToggleGroup;
import javafx.scene.image.ImageView;

public class LauncherController implements LauncherControllerIf {
	
	Main mainApp;
	@FXML
	private Label timeLabel;
	@FXML
	private TextField simulationTime;
	@FXML
	private TextField delayTime;
	@FXML
	private TextField kassojenMäärä;
	@FXML
	private RadioButton ikYes;
	@FXML
	private RadioButton ikNo;
	@FXML
	ToggleGroup toggleGroup = new ToggleGroup();
	@FXML
	private TextField saapumisKAField;
	@FXML
	private TextField hajontaField;
	@FXML
	private Canvas canvas;
	@FXML
	private Button startButton;
	@FXML
	private Button rewindForwardButton;
	@FXML
	private Button rewindBackwardButton;
	@FXML
	private Spinner<Integer> kassaSpinner;
	private IntegerSpinnerValueFactory valueFactory = new IntegerSpinnerValueFactory(1, 6, 1, 1); // (int min, int max, int initialValue, int amountToStepBy)
	
	
	
	
	private GuiIf gui;
	Kello kello = Kello.getInstance();
	NumberFormat formatter = new DecimalFormat("#0.0");  
	SimulationController ctrl = new SimulationController();
	Moottori motor;
	private Kontrolleri kontrolleri;
	private int saapumisKA;
	private int hajonta;
	private boolean running = false;
	
	ImageView rewindforwardimage = new ImageView(getClass().getResource("img/rewindforward.png").toExternalForm());
	
	ImageView rewindbackwardimage = new ImageView(getClass().getResource("img/rewindbackward.png").toExternalForm());
	
	ImageView stopimage = new ImageView(getClass().getResource("img/stop.png").toExternalForm());
	 
	
	public LauncherController() {}
	
	/**
	 * Metodi, jolla asetetaan Kontrolleri-olio
	 * @param kontrolleri luokan Kontrolleri olio
	 */
	public void setController(Kontrolleri kontrolleri) {
		this.kontrolleri = kontrolleri;
	}
	
	/**
	 * Metodi, jolla asetetaan viittaus GuiIf rajapintaan
	 * @param gui viittaus GuiIf rajapintaan
	 */
	public void setGui(GuiIf gui) {
		this.gui = gui;
	}
	
	/**
	 * Metodi, jolla voidaan vastaanottaa GuiIf rajapinnan ilmentymä
	 * @return rajapinnan ilmentymä
	 */
	public GuiIf getGui() {
		return this.gui;
	}

	/**
	 * Alustusmetodi, lisätään RadioButtonin ToggleGrouppiin
	 */
	@FXML
	private void initialize() {
		ikYes.setToggleGroup(toggleGroup);
		ikNo.setToggleGroup(toggleGroup);
		kassaSpinner.setValueFactory(valueFactory);
		//img = image("src/img/play");
		rewindForwardButton.setGraphic(rewindforwardimage);
		rewindforwardimage.setFitHeight(30);
		rewindforwardimage.setFitWidth(30);
	    
	    rewindBackwardButton.setGraphic(rewindbackwardimage);
	    rewindbackwardimage.setFitHeight(30);
	    rewindbackwardimage.setFitWidth(30);
	}
	
	/**
	 * Asetetaan kellonaika sille tarkoitetulle paikalle
	 */
	public void setKello() {
		timeLabel.setText(formatter.format(Kello.getInstance().getAika()) + " s");
	}
	

	/**
	 * Metodi, jolla vastaanotetaan Main-olio
	 * @param mainApp Mainin olio
	 */
	public void setMainApp(Main mainApp) {
        this.mainApp = mainApp;
    }
	
	/**
	 * Metodi, jolla saamme vastauksen ToggleGroupista, onko itsepalvelukassat valittu vai ei
	 *
	 */
	public boolean getItsepalvelukassat() {
		if (toggleGroup.getSelectedToggle() == ikYes) {
			return true;
		} else {
			return false;
		}
	}
	
	/**
	 * Kassojen määrä tekstikentän tulos
	 */
	public int KassojenMaara() {
		int kassat;
		if(getItsepalvelukassat()) {
		kassat = kassaSpinner.getValue() + 6;
		} else {
			kassat = Integer.parseInt(kassojenMäärä.getText());
		}
	
		return kassat;
	}
	
	public int getSaapumisKA() {
		return Integer.parseInt(saapumisKAField.getText());
	}
	
	public int gethajonta() {
		return Integer.parseInt(hajontaField.getText());
	}
	
	public void setSaapumisKAJaHajonta() {
		saapumisKA = getSaapumisKA();
		hajonta = gethajonta();
	}
	
	/**
	 * hidasta() ja nopeuta() metodit lisäävät moottorin sleep aikaa, tehden simulaattorin visuaalisesta toiminnasta halutunlaista.
	 */
	public void hidasta() { // hidastetaan moottorisäiettä
		motor.setViive((long)(motor.getViive()*1.10));
	}

	public void nopeuta() { // nopeutetaan moottorisäiettä
		motor.setViive((long)(motor.getViive()*0.9));
	}
	
	
	public void showResults() {
		RootLayoutController rlctrl = new RootLayoutController();
		rlctrl.setController(kontrolleri);
        rlctrl.setMainApp(mainApp);
		rlctrl.showResults();
	}
	
	public void saveResults() {
		RootLayoutController rlctrl = new RootLayoutController();
		rlctrl.setController(kontrolleri);
        rlctrl.setMainApp(mainApp);
        try {
			rlctrl.writeExcel();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	
	public void totalCustomers() {
		SimulationController sctrl = new SimulationController();
		
	}
	

	/**
	 * Metodi, joka käsittelee "Käynnistä" -napin toiminnot.
	 */
	@Override
	public void handleStart() {	
		running = !running;
		if (running) {
			startButton.setText("");
			startButton.setGraphic(stopimage);
			stopimage.setFitHeight(30);
		    stopimage.setFitWidth(30);
		    kontrolleri.setTimeLabel(timeLabel);
			kello = Kello.getInstance();
			kello.setAika(0);
			setSaapumisKAJaHajonta();
			motor = new Moottori(kontrolleri, saapumisKA, hajonta);
			motor.setItsepalvelukassat(getItsepalvelukassat());
			motor.setSimulointiaika(Integer.parseInt(simulationTime.getText()));
			motor.getKassojenmaara(kassaSpinner.getValue());
			motor.setViive(Integer.parseInt(delayTime.getText()));
			motor.avaaKassat(kassaSpinner.getValue(), getItsepalvelukassat());
			((Thread)motor).start();
			mainApp.showSimulationView();
			setKello();
		} else {
			startButton.setText("KÄYNNISTÄ");
			startButton.setGraphic(null);
			motor.setViive(0);
		}
		
	}
	
}
