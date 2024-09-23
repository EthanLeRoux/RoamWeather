/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package simpletranslations;

import javafx.scene.control.Button;
import space.dynomake.libretranslate.Language;
import space.dynomake.libretranslate.Translator;

/**
 *
 * @author ethan
 */

public class TranslateButtons {
    public String transBtnToSpanish(Button btn){
        String text = btn.getText();
        String transText = Translator.translate(Language.ENGLISH, Language.DUTCH, text);
        return transText;
    }
    
    public String transBtnToFrench(Button btn){
        String text = btn.getText();
        String transText = Translator.translate(Language.ENGLISH, Language.FRENCH, text);
        return transText;
    }
    
    public String transBtnToJapanese(Button btn) {
        String text = btn.getText();
        String transText = Translator.translate(Language.ENGLISH, Language.JAPANESE, text);
        return transText;
    }

    public String transBtnToArabic(Button btn) {
        String text = btn.getText();
        String transText = Translator.translate(Language.ENGLISH, Language.ARABIC, text);
        return transText;
    }

    public String transBtnToChinese(Button btn) {
        String text = btn.getText();
        String transText = Translator.translate(Language.ENGLISH, Language.CHINESE, text);
        return transText;
    }
}
