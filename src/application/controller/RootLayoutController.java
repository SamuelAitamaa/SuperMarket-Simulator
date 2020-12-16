package application.controller;

import java.io.File;
import java.io.FileOutputStream;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import javax.swing.ImageIcon;

import org.shaded.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.shaded.apache.poi.ss.usermodel.Cell;
import org.shaded.apache.poi.ss.usermodel.Row;
import org.shaded.apache.poi.ss.usermodel.Sheet;
import org.shaded.apache.poi.ss.usermodel.Workbook;

import application.model.Tulokset;
import application.view.GuiIf;
import application.view.Main;
import javafx.fxml.FXML;
import javafx.geometry.Orientation;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.SplitPane;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.stage.FileChooser;
import javafx.stage.Stage;

/**
 * Kontrolleri RootLayoutille.
 * 
 * @author Samuel Aitamaa
 */
public class RootLayoutController {
	
	public RootLayoutController() {
		Image carticon = new Image(getClass().getResource("img/carticon.png").toExternalForm());
        this.naytto.getIcons().add(carticon);
	}
	
	GuiIf gui;
	Kontrolleri ctrl = new Kontrolleri(gui);

    // Reference to the main application
    private Main mainApp;
	private Tulokset tulokset = new Tulokset();
	@FXML
	TableView table = new TableView();
	Stage naytto = new Stage();
	FileChooser fileChooser = new FileChooser();
	File saveFile;
	File file;
	Workbook workbook;
	
	String filePath;

    public void setMainApp(Main mainApp) {
        this.mainApp = mainApp;
        
    }
    
    public void setController(Kontrolleri kontrolleri) {
		this.ctrl = kontrolleri;
	}

  
    /**
     * Toiminnalisuus "About" -napille, tarjoaa yleistä tietoa sovelluksesta
     */
    @FXML
    private void showAbout() {
    	
    	
    	Alert alert = new Alert(AlertType.INFORMATION);
    	Stage stage = (Stage) alert.getDialogPane().getScene().getWindow();
    	Image carticon = new Image(getClass().getResource("img/carticon.png").toExternalForm());
        stage.getIcons().add(carticon);
    	alert.setTitle("SuperMarket");
    	alert.setHeaderText("SuperMarket Simulator on kolmivaihesimuloinnilla ja MVC-arkkitehtuurilla toteutettu sovellus,\njoka tarkastelee kauppajonojen käyttäytymistä halutuilla parametreilla."
    			+ "\n\nSovellus on tehty Metropolian opiskelijatyönä."
    			+ "\n\n- Samuel Aitamaa");
    	alert.setContentText("Kaikki oikeudet pidätetään.");
    	

    	alert.showAndWait();
    }
    
    public void initTables() {
    	TableColumn<String, Tulokset> column0 = new TableColumn<>("ID");
        TableColumn<String, Tulokset> column1 = new TableColumn<>("Simulointiaika");
        TableColumn<String, Tulokset> column2 = new TableColumn<>("Asiakkaita");
        TableColumn<String, Tulokset> column3 = new TableColumn<>("Palveltuja");
        TableColumn<String, Tulokset> column4 = new TableColumn<>("Keskim. jonotus");
        TableColumn<String, Tulokset> column5 = new TableColumn<>("Keskim. palvelu");
        TableColumn<String, Tulokset> column6 = new TableColumn<>("Kassoja");
        TableColumn<String, Tulokset> column7 = new TableColumn<>("Itsepalvelukassat");
        TableColumn<String, Tulokset> column8 = new TableColumn<>("Henkilökunta");
        TableColumn<String, Tulokset> column9 = new TableColumn<>("Työtunnit");
        TableColumn<String, Tulokset> column10 = new TableColumn<>("Työn teho");
        
        column0.setCellValueFactory(new PropertyValueFactory<>("index"));
        column1.setCellValueFactory(new PropertyValueFactory<>("simulointiaika"));
        column2.setCellValueFactory(new PropertyValueFactory<>("asiakkaidenKokonaismaara"));
        column3.setCellValueFactory(new PropertyValueFactory<>("palvellutAsiakkaat"));
        column4.setCellValueFactory(new PropertyValueFactory<>("jonotuskeskiarvo"));
        column5.setCellValueFactory(new PropertyValueFactory<>("palvelukeskiarvo"));
        column6.setCellValueFactory(new PropertyValueFactory<>("kassojenMaara"));
        column7.setCellValueFactory(new PropertyValueFactory<>("itsepalvelukassat"));
        column8.setCellValueFactory(new PropertyValueFactory<>("henkilokunta"));
        column9.setCellValueFactory(new PropertyValueFactory<>("tyotunnit"));
        column10.setCellValueFactory(new PropertyValueFactory<>("tyonTeho"));
        
        column0.prefWidthProperty().bind(table.widthProperty().multiply(0.08));
        column1.prefWidthProperty().bind(table.widthProperty().multiply(0.09));
        column2.prefWidthProperty().bind(table.widthProperty().multiply(0.08));
        column3.prefWidthProperty().bind(table.widthProperty().multiply(0.08));
        column4.prefWidthProperty().bind(table.widthProperty().multiply(0.1));
        column5.prefWidthProperty().bind(table.widthProperty().multiply(0.1));
        column6.prefWidthProperty().bind(table.widthProperty().multiply(0.08));
        column7.prefWidthProperty().bind(table.widthProperty().multiply(0.11));
        column8.prefWidthProperty().bind(table.widthProperty().multiply(0.09));
        column9.prefWidthProperty().bind(table.widthProperty().multiply(0.08));
        column10.prefWidthProperty().bind(table.widthProperty().multiply(0.08));
        
        
        for(int i=0; i < resultsLength(); i++) {
        	table.getItems().add(ctrl.tuoTulokset()[i]);
        }

        table.getColumns().addAll(column0, column1, column2, column3, column4, column5, column6, column7, column8, column9, column10);
        SplitPane root = new SplitPane();
        root.setOrientation(Orientation.VERTICAL);
        root.getItems().addAll(table);
        Scene scene = new Scene(root);
        naytto.setScene(scene);
    }
    
    public void showResults() {
		initTables();
		naytto.setTitle("Tulokset");
		naytto.setMinWidth(1000);
		naytto.setMaxWidth(1000);
		naytto.setMaxHeight(500);
		
		
		naytto.show();
    }
    
    /* Unused code for saving results to a .txt file. I'll leave it here for now! */
    
    
    /*public void saveAs() {
    	showResults();
    	// Create the file
    	
    	try {
    		//File file = new File();
    		//FileChooser fileChooser = new FileChooser();
    		saveFile = fileChooser.showSaveDialog(table.getScene().getWindow());
    		file = new File(saveFile.getAbsolutePath());
    		this.filePath = saveFile.getAbsolutePath();
    		
            
    	      //File myObj = new File("C:\\Users\\Samuel\\Desktop\\filename.txt");
    	      if (file.createNewFile()) {
    	        System.out.println("File created: " + file.getName());
    	      } else {
    	        System.out.println("File already exists.");
    	      }
    	    } catch (IOException e) {
    	      System.out.println("An error occurred.");
    	      e.printStackTrace();
    	    }
    	
    	 save();
     }
    
    public void save() {
    	if (this.filePath != null) {
    	try {
     	      FileWriter myWriter = new FileWriter(this.filePath);
     	      for (int i=0; i < resultsLength(); i++) {
     	    	  myWriter.write(ctrl.tuoTulokset()[i].toString() + "\n");
     	      }
     	      myWriter.close();
     	      System.out.println("Successfully wrote to the file.");
     	    } catch (IOException e) {
     	      System.out.println("An error occurred.");
     	      e.printStackTrace();
     	    }
    	} else {
    		saveAs();
    	}
    }*/
    
    
    public void writeToFile() {
    	 try {
    	      FileWriter myWriter = new FileWriter(saveFile.getAbsolutePath());
    	      for (int i=0; i < resultsLength(); i++) {
    	    	  myWriter.write(ctrl.tuoTulokset()[i].toString() + "\n");
    	      }
    	      myWriter.close();
    	      System.out.println("Successfully wrote to the file.");
    	    } catch (IOException e) {
    	      System.out.println("An error occurred.");
    	      e.printStackTrace();
    	    }
    }
   
    public int resultsLength() {
    	int index = 0;
    	for (int i=0; i < ctrl.tuoTulokset().length; i++) {
    		if (ctrl.tuoTulokset()[i] != null) {
    			index = i + 1;
    		}
    	}
    	return index;
    }
   
    private List<Tulokset> getResultList() {
    	
    	List<Tulokset> resultList = new ArrayList<Tulokset>();
    	
        for (int i=0; i < resultsLength(); i++) {
        		resultList.add(ctrl.tuoTulokset()[i]);
        	}        	
        return resultList;
    }
    
    
    public void writeExcel() throws IOException {
    	showResults();
    	FileChooser fileChooser = new FileChooser();
    	fileChooser.getExtensionFilters().addAll(
                new FileChooser.ExtensionFilter("XLS files (*.xls)", "*.xls")
        );
		saveFile = fileChooser.showSaveDialog(table.getScene().getWindow());
		
        workbook = new HSSFWorkbook();
        Sheet sheet = workbook.createSheet();
        
       
     
        List<Tulokset> tuloslista = getResultList();
        String excelFilePath = saveFile.getAbsolutePath();
        int rowCount = 0;
     
        Row headerRow = sheet.createRow(++rowCount);
        
        
        for (Tulokset tulos : tuloslista) {
            Row row = sheet.createRow(++rowCount);
            writeTable(tulos, row, headerRow);
        }
     
        try (FileOutputStream outputStream = new FileOutputStream(excelFilePath)) {
            workbook.write(outputStream);
        } catch(Error e) {
        	System.out.println(e);
        }
    }
    
    private void writeTable(Tulokset tulos, Row row, Row headerRow) {
    	
    	Cell idCell = headerRow.createCell(1);
        idCell.setCellValue("ID");
        
        Cell timeCell = headerRow.createCell(2);
        timeCell.setCellValue("Simulointiaika (s)");
        
        Cell customerCell = headerRow.createCell(3);
        customerCell.setCellValue("Asiakkaita");
        
        Cell servedCell = headerRow.createCell(4);
        servedCell.setCellValue("Palveltuja");
        
        Cell queueCell = headerRow.createCell(5);
        queueCell.setCellValue("Keskim. jonotus (s)");
        
        Cell servingCell = headerRow.createCell(6);
        servingCell.setCellValue("Keskim. palvelu (s)");
        
        Cell kassaCell = headerRow.createCell(7);
        kassaCell.setCellValue("Kassoja");
        
        Cell ikCell = headerRow.createCell(8);
        ikCell.setCellValue("Itsepalvelukassat");
        
        Cell henkilokuntaCell = headerRow.createCell(9);
        henkilokuntaCell.setCellValue("Henkilökunta");
        
        Cell tyotunnitCell = headerRow.createCell(10);
        tyotunnitCell.setCellValue("Työtunnit (h)");
        
        Cell tyontehoCell = headerRow.createCell(11);
        tyontehoCell.setCellValue("Työn teho (Palveltuja asiakkaita per työtunti)");
        
        
    	
        Cell cell = row.createCell(1);
        cell.setCellValue(tulos.getIndex());
        
        cell = row.createCell(2);
        cell.setCellValue(tulos.getSimulointiaika());
     
        cell = row.createCell(3);
        cell.setCellValue(tulos.getAsiakkaidenKokonaismaara());
     
        cell = row.createCell(4);
        cell.setCellValue(tulos.getPalvellutAsiakkaat());
        
        cell = row.createCell(5);
        cell.setCellValue(tulos.getJonotuskeskiarvo());
        
        cell = row.createCell(6);
        cell.setCellValue(tulos.getPalvelukeskiarvo());
        
        cell = row.createCell(7);
        cell.setCellValue(tulos.getKassojenMaara());
        
        cell = row.createCell(8);
        cell.setCellValue(tulos.getItsepalvelukassat());
        
        cell = row.createCell(9);
        cell.setCellValue(tulos.getHenkilokunta());
        
        cell = row.createCell(10);
        cell.setCellValue(tulos.getTyotunnit());
        
        cell = row.createCell(11);
        cell.setCellValue(tulos.getTyonTeho());
        
        for(int colNum = 0; colNum < row.getLastCellNum(); colNum++) {
            workbook.getSheetAt(0).autoSizeColumn(colNum);
        }
    }
	

    /**
     * Closes the application.
     */
    @FXML
    private void handleExit() {
        System.exit(0);
    }
}