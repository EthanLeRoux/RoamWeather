/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package simpletranslations;

import javafx.scene.control.ComboBox;
import javafx.scene.control.Label;
import space.dynomake.libretranslate.Language;
import space.dynomake.libretranslate.Translator;

/**
 *
 * @author ethan
 */
public class TranslateController {
     private ComboBox<String> comboLang;

    public TranslateController(ComboBox<String> comboLang) {
        this.comboLang = comboLang;
    }

    // Method to translate Label text based on the selected language
    public String translateLabel(Label lbl) {
        String selectedLanguage = comboLang.getValue();
        String text = lbl.getText();
        String translatedText = null;

        switch (selectedLanguage) {
            case "Spanish":
                translatedText = Translator.translate(Language.ENGLISH, Language.SPANISH, text);
                break;
            case "French":
                translatedText = Translator.translate(Language.ENGLISH, Language.FRENCH, text);
                break;
            case "Japanese":
                translatedText = Translator.translate(Language.ENGLISH, Language.JAPANESE, text);
                break;
            case "Arabic":
                translatedText = Translator.translate(Language.ENGLISH, Language.ARABIC, text);
                break;
            case "Chinese":
                translatedText = Translator.translate(Language.ENGLISH, Language.CHINESE, text);
                break;
            default:
                translatedText = text; // Default to English if no language is selected
        }

        return translatedText;
    }
}
