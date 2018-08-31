package org.tec.comp.interpreter;

import java.io.*;
import java.util.LinkedList;

import static org.tec.comp.interpreter.Token.*;

public class Lexer_Handler {

    private LinkedList<Pair<String, String>> token_list;
    private final String FLEX_FILE_PATH = "C:/Users/dell-pc/Desktop/LightBotCC/IDE/LightBotIDE/src/org/tec/comp/interpreter/lang.flex"; //TODO: CAMBIARLO.


    public Lexer_Handler() {
        token_list = new LinkedList<>();
    }

    public void generate() {
        File flex_file = new File(FLEX_FILE_PATH);
        jflex.Main.generate(flex_file);
    }

    public void test_lexer(String file) throws IOException {
        File code_file = new File (file);
        Reader reader = new BufferedReader(new FileReader(code_file));
        Lexer lexer = new Lexer(reader);

        String parse_result = "";

        boolean is_eof = false;

        while(!is_eof) {
            try{
                Token token = lexer.yylex();

                switch (token) {
                    case ID:
                        token_list.add(new Pair<>(lexer.lexeme, "ID"));
                        break;
                    case KEYWORD:
                        token_list.add(new Pair<>(lexer.lexeme, "KEYWORD"));
                        break;
                    case ASSIGN:
                        token_list.add(new Pair<>("=", "ASSIGN"));
                        break;
                    case NUMBER:
                        token_list.add(new Pair<>(lexer.lexeme, "NUMBER"));
                        break;
                    default:
                        token_list.add(new Pair<>(lexer.lexeme, "UNKNOWN"));
                        break;
                }
            } catch (NullPointerException npe) {
                parse_result += "EOF" + "\n";
                is_eof = true;
            }
        }

        for(Pair p : token_list) {
            parse_result += p + "\n";
        }
        System.out.println(parse_result);
    }

}
