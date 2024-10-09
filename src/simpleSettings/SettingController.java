package simpleSettings;

import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.net.URL;
import java.sql.SQLException;
import java.util.List;
import java.util.ResourceBundle;
import java.util.logging.Level;
import java.util.logging.Logger;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.RadioButton;
import javafx.scene.control.ToggleGroup;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.Label;
import javax.swing.JOptionPane;
import simpletranslations.TranslateButtons;
import simpletranslations.TranslateController;
import simpletranslations.TranslateGeneral;
import simpleusers.RunSimpleUsers;
import space.dynomake.libretranslate.Language;

public class SettingController implements Initializable {

    private Scene scene;
    private SettingsDAO settingsDAO;
    private RunSimpleUsers runSimpleUsers;
    private boolean isDarkMode = false; // Keep track of theme mode

    @FXML
    private Button lightBtn;

    @FXML
    private Button darkBtn;

    @FXML
    private RadioButton btnImp;

    @FXML
    private RadioButton btnMetric;

    @FXML
    private ToggleGroup unit;

    @FXML
    ComboBox<String> comboLang;

    @FXML
    private Button savebtn;


    SettingsDAO settingsdao = new SettingsDAO();

    public SettingController() {
        this.scene = null;
    }

    public void setScene(Scene scene) {
        this.scene = scene;
    }

    @Override
    public void initialize(URL url, ResourceBundle rb) {
        ObservableList<String> languageList = FXCollections.observableArrayList("English", "Spanish", "French", "Japanese", " Arabic", "Chinese");
        comboLang.setItems(languageList);

        settingsDAO = new SettingsDAO();
        refreshList();
    }

    @FXML
    public String getUnit(ActionEvent event) {
        if (btnImp != null && btnImp.isSelected()) {
            return "Imperial";
        } else if (btnMetric != null && btnMetric.isSelected()) {
            return "Metric";
        }
        return "No unit selected";
    }

    @FXML
    public void refreshList() {
        List<Settings> settings = settingsdao.getAllSettings();
        ObservableList<String> settingsStrings = FXCollections.observableArrayList();
        for (Settings setting : settings) {
            settingsStrings.add(setting.toString());
        }
    }

public void handleSaveButton(ActionEvent event) {
    String selectedLanguage = comboLang.getValue();
    String unit = getUnit(event);
     TranslateButtons translateButtons = new TranslateButtons();
   
     

    if (selectedLanguage != null) {
        // Save the selected settings
        Settings settings = new Settings(isDarkMode, selectedLanguage, unit, 1);
        settingsDAO.insertSettings(settings);
        System.out.println("Settings saved.");

        // Show a popup message that the settings have been saved
        PopUpMessage popUpMessage = new PopUpMessage();
        String title = "Saved Settings";
        String header = "Your selected settings have been saved!";
        String content = "Language: " + selectedLanguage + "\n"
                + "Theme: " + isDarkMode + "\n"
                + "Unit: " + unit;
        popUpMessage.showInformationDialog(title, header, content);

        // Refresh the settings list
        refreshList();

        // Instantiate TranslateGeneral
        TranslateGeneral translateGeneral = new TranslateGeneral();
        Language targetLanguage = Language.ENGLISH; // Default to English for translation base
        switch (selectedLanguage) {
            case "Spanish":
                targetLanguage = Language.SPANISH;
                break;
            case "French":
                targetLanguage = Language.FRENCH;
                break;
            case "Japanese":
                targetLanguage = Language.JAPANESE;
                break;
            case "Arabic":
                targetLanguage = Language.ARABIC;
                break;
            case "Chinese":
                targetLanguage = Language.CHINESE;
                break;
        }

//        // Translate label text
//        String translatedLabelText = translateGeneral.translate(targetLanguage, labelToTranslate.getText());
//        labelToTranslate.setText(translatedLabelText); // Update the label text

        // Translate button texts
        savebtn.setText(translateGeneral.translate(targetLanguage, savebtn.getText()));
        lightBtn.setText(translateGeneral.translate(targetLanguage, lightBtn.getText()));
        darkBtn.setText(translateGeneral.translate(targetLanguage, darkBtn.getText()));
        
      
    
    // Example of translating button texts after saving settings
    if ("Spanish".equals(selectedLanguage)) {
        savebtn.setText(translateButtons.transBtnToSpanish(savebtn));
        lightBtn.setText(translateButtons.transBtnToSpanish(lightBtn));
        darkBtn.setText(translateButtons.transBtnToSpanish(darkBtn));
    } else if ("French".equals(selectedLanguage)) {
        savebtn.setText(translateButtons.transBtnToFrench(savebtn));
        lightBtn.setText(translateButtons.transBtnToFrench(lightBtn));
        darkBtn.setText(translateButtons.transBtnToFrench(darkBtn));
    } 
    } else {
        System.out.println("Please select a language.");
    }
    //------------------------------------------------------------------------
 
    
    // Example of translating button texts after saving settings
    if ("french".equals(selectedLanguage)) {
        savebtn.setText(translateButtons.transBtnToSpanish(savebtn));
        lightBtn.setText(translateButtons.transBtnToSpanish(lightBtn));
        darkBtn.setText(translateButtons.transBtnToSpanish(darkBtn));
    } else if ("French".equals(selectedLanguage)) {
        savebtn.setText(translateButtons.transBtnToFrench(savebtn));
        lightBtn.setText(translateButtons.transBtnToFrench(lightBtn));
        darkBtn.setText(translateButtons.transBtnToFrench(darkBtn));
    } 

    if(isDarkMode = true){
    
        try {
            System.out.println("dark mode");
            switchSceneDark();
        } catch (IOException ex) {
            Logger.getLogger(SettingController.class.getName()).log(Level.SEVERE, null, ex);
        }
    } else if (isDarkMode = false){
    
         try {
            System.out.println("dark mode");
            switchSceneLight();
        } catch (IOException ex) {
            Logger.getLogger(SettingController.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
    //----------------------------------------------------------------------------
    
    
}

@FXML
    public void switchScene() throws IOException {
        RunSimpleUsers rs = new RunSimpleUsers();
        rs.switchScene("/home/HomePage.fxml");
    }
    
    public void switchSceneLight() throws IOException {
        RunSimpleUsers rs = new RunSimpleUsers();
        rs.switchScene("/simpleSettings/Settings.fxml");
    }
    
@FXML
    public void switchSceneDark() throws IOException {
        RunSimpleUsers rs = new RunSimpleUsers();
        rs.switchScene("/simpleSettings/SettingsDark.fxml");
    }
    
@FXML
    public void switchSceneDarkHome() throws IOException {
        RunSimpleUsers rs = new RunSimpleUsers();
        rs.switchScene("/home/HomePageDark.fxml");
    }
    
    @FXML
    public void displayAllSettings(List<Settings> allSettings) {
        Alert alert = new Alert(AlertType.INFORMATION);
        alert.setTitle("Saved Settings");

        if (!allSettings.isEmpty()) {
            Settings latestSettings = allSettings.get(allSettings.size() - 1);
            String displayText = "User ID: " + latestSettings.getUserId() + "\n"
                    + "Language: " + latestSettings.getLanguage() + "\n"
                    + "Theme: " + latestSettings.isTheme() + "\n"
                    + "Unit: " + latestSettings.getUnit();

            alert.setHeaderText(null);
            alert.setContentText(displayText);
        } else {
            alert.setHeaderText(null);
            alert.setContentText("No settings available.");
        }

        alert.showAndWait();
    }

    @FXML
    public void handleLightButtonAction() {
        if (scene != null) {
            scene.getStylesheets().clear();
            scene.getStylesheets().add(getClass().getResource("/simpleSettings/light.css").toExternalForm());
            isDarkMode = false; // Set the mode flag to false
        }
    }

    @FXML
    public void handleDarkButtonAction() {
        if (scene != null) {
            scene.getStylesheets().clear();
            scene.getStylesheets().add(getClass().getResource("/simpleSettings/dark.css").toExternalForm());
            isDarkMode = true; // Set the mode flag to true
        }
    }

    public void insert() throws FileNotFoundException, IOException {
        FileReader fr = new FileReader("user.txt");
        BufferedReader br = new BufferedReader(fr);
        int userId = Integer.parseInt(br.readLine());

        String language = comboLang.getSelectionModel().getSelectedItem();

        String selectedMeasure = "";
        if (unit.getSelectedToggle() != null) {
            selectedMeasure = ((RadioButton) unit.getSelectedToggle()).getText();
        }

        Settings settings = new Settings(isDarkMode, language, selectedMeasure, userId);
        settingsdao.insertSettings(settings);
    }
//
//    @FXML
//    public void translateLabelAction() {
//        TranslateController translateController = new TranslateController(comboLang);
//        String translatedText = translateController.translateLabel(labelToTranslate);
//        labelToTranslate.setText(translatedText);
//    }
}
