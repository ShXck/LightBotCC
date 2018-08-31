package org.tec.comp;

import org.tec.comp.interpreter.Lexer_Handler;

import javax.swing.*;
import java.io.IOException;

public class Main {

    public static void main(String[] args) {
        Lexer_Handler lh = new Lexer_Handler();
        //lh.generate();
        try {
            lh.test_lexer("mycode.txt");
        } catch (IOException e) {
            e.printStackTrace();
        }
        SwingUtilities.invokeLater(new IDE_Window());
    }
}
