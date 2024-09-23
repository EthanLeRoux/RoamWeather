/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package simpletranslations;

import javafx.scene.control.Label;
import space.dynomake.libretranslate.Language;
import space.dynomake.libretranslate.Translator;

/**
 *
 * @author ethan
 */

public class TranslateLabels {
    // Method to translate Label text to Spanish
    public String transLblToSpanish(Label lbl) {
        String text = lbl.getText();
        String transText = Translator.translate(Language.ENGLISH, Language.SPANISH, text);
        return transText;
    }

    // Method to translate Label text to French
    public String transLblToFrench(Label lbl) {
        String text = lbl.getText();
        String transText = Translator.translate(Language.ENGLISH, Language.FRENCH, text);
        return transText;
    }

    // Method to translate Label text to Japanese
    public String transLblToJapanese(Label lbl) {
        String text = lbl.getText();
        String transText = Translator.translate(Language.ENGLISH, Language.JAPANESE, text);
        return transText;
    }

    // Method to translate Label text to Arabic
    public String transLblToArabic(Label lbl) {
        String text = lbl.getText();
        String transText = Translator.translate(Language.ENGLISH, Language.ARABIC, text);
        return transText;
    }

    // Method to translate Label text to Chinese
    public String transLblToChinese(Label lbl) {
        String text = lbl.getText();
        String transText = Translator.translate(Language.ENGLISH, Language.CHINESE, text);
        return transText;
    }
    
}

