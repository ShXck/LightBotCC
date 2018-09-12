package org.tec.comp;
import org.tec.comp.game.Subscriber;
import org.tec.comp.interpreter.*;

import javax.swing.*;

public class Main {

    public static void main(String[] args) {
        /*try {
            LangParser.parse("testcode.txt");
        } catch (ParseException e) {
            e.printStackTrace();
        }*/



        SwingUtilities.invokeLater(new IDE_Window());
    }
}
