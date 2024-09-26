/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package simpletranslations;

import javafx.scene.control.Button;
import javafx.scene.control.Label;
import space.dynomake.libretranslate.Language;
import space.dynomake.libretranslate.Translator;

/**
 *
 * @author ethan
 */
public class TranslateGeneral {
    public String transLblToSpanish(String text) {
        String transText = Translator.translate(Language.ENGLISH, Language.SPANISH, text);
        return transText;
    }
    public String transLblToArabic(String text) {
    String transText = Translator.translate(Language.ENGLISH, Language.ARABIC, text);
    return transText;
}

public String transLblToFrench(String text) {
    String transText = Translator.translate(Language.ENGLISH, Language.FRENCH, text);
    return transText;
}

public String transLblToChinese(String text) {
    String transText = Translator.translate(Language.ENGLISH, Language.CHINESE, text);
    return transText;
}

public String transLblToJapanese(String text) {
    String transText = Translator.translate(Language.ENGLISH, Language.JAPANESE, text);
    return transText;
}
    public String translate(Language to, String text) {
        // Implementation of translation
        String transText = Translator.translate(Language.ENGLISH, to, text);
        return transText;
    }
    }

