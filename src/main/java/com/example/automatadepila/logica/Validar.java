package com.example.automatadepila.logica;

import javafx.scene.control.Label;
import javafx.scene.control.TextArea;

import java.util.HashMap;
import java.util.Stack;
import java.util.regex.Pattern;

public class Validar {
    private TextArea resultado;
    private Label estatus;
    public Validar(TextArea resultado, Label estatus){
        this.resultado = resultado;
        this.estatus = estatus;
    }
    private static final HashMap<String, String> productions = new HashMap<>();
    static {
        productions.put("SC", "PRC CLM | PRC CL");
        productions.put("CLM", "PRM COC");
        productions.put("CL", "L+ COC");
        productions.put("COC", "CORI CONC");
        productions.put("CONC", "CONTENIDOC+ CORC");
        productions.put("CONTENIDOC", "L+ M | PRF FCM | PRF FC");
        productions.put("FC", "FN COF");
        productions.put("FN", "L+ PAIF");
        productions.put("PAIF", "PARI PACF");
        productions.put("PACF", "PARAMS PARC");
        productions.put("PARAMS", "L+ PF| L+");
        productions.put("PF", "C PARAMS");
        productions.put("FCM", "PRM FMP");
        productions.put("FMP", "PARI SP");
        productions.put("SP", "PARC COF");
        productions.put("COF", "CORI CONF");
        productions.put("CONF", "CONTENIDOF+ CORC");
        productions.put("CONTENIDOF", "L+ M | PRP PI | PRI GV | PRSQ ISQ | CONW COF | DCON CONW | FCO COF | SS EISCS | SSS CAS");
        productions.put("SSS", "PRS L+");
        productions.put("CAS", "CORI CASS");
        productions.put("CASS", "CASES CORC");
        productions.put("CASES", "SCS* DEC");
        productions.put("DEC", "DE DECO");
        productions.put("DE", "default");
        productions.put("DECO", "CORI DECON");
        productions.put("DECON", "EX CORC");
        productions.put("EX", "exit");
        productions.put("SCS", "SCS1 SCS2");
        productions.put("SCS1", "PRSC SE");
        productions.put("PRSC", "case");
        productions.put("SE", "==");
        productions.put("SCS2", "DAS COF | D+ COF");
        productions.put("DAS", "COMILLA ST");
        productions.put("EISCS", "EISC* ESC?");
        productions.put("EISC", "PRELSIF CSC");
        productions.put("ESC", "PRELSE COF");
        productions.put("SS", "PRIF CSC");
        productions.put("CSC", "CON COF");
        productions.put("FCO", "PRFO FCF");
        productions.put("CFR", "PRR FCOF");
        productions.put("FCOF", "PARI FCONF");
        productions.put("FCF", "L+ CFR");
        productions.put("FCONF", "PALABRAS PARC");
        productions.put("DCON", "PRD COF");
        productions.put("CONW", "PRW CON");
        productions.put("CON", "PARI COND");
        productions.put("COND", "L+ PARC | LE DAT");
        productions.put("DAT", "V PARC");
        productions.put("LE", "L+ EE");
        productions.put("ISQ", "PARI O");
        productions.put("O", "PALABRAS PARC");
        productions.put("GV", "SMM L+");
        productions.put("PI", "PARI PFP");
        productions.put("PFP", "DAP+ PARC");
        productions.put("DAP", "L+ C? | ST C?");
        productions.put("M", "VA* X");
        productions.put("VA", "C L+");
        productions.put("X", "E S");
        productions.put("S", "V K*");
        productions.put("K", "C V");
        productions.put("V", "COMILLAS ST | D+ N? | true | false");
        productions.put("NU", "D+ N?");
        productions.put("N", "P D+");
        productions.put("ST", "PALABRAS+ COMILLAS");
        productions.put("PALABRAS", "[a-zA-Z]+ | D+ N?");
        productions.put("COMILLAS", "\\\"");
        productions.put("D", "[0-9]");
        productions.put("P", "\\.");
        productions.put("EE", "== | != | >= | <=");
        productions.put("E", "=");
        productions.put("C", ",");
        productions.put("PARI", "\\(");
        productions.put("PARC", "\\)");
        productions.put("CORI", "\\{");
        productions.put("CORC", "\\}");
        productions.put("L", "[a-zA-Z]");
        productions.put("PRF", "func");
        productions.put("PRC", "class");
        productions.put("PRM", "main");
        productions.put("SMM", ">>");
        productions.put("PRI", "in");
        productions.put("PRP", "println | print");
        productions.put("PRSQ", "sqrt");
        productions.put("PRW", "while");
        productions.put("PRD", "do");
        productions.put("PRFO", "for");
        productions.put("PRR", "range");
        productions.put("PRIF", "if");
        productions.put("PRELSE", "else");
        productions.put("PRELSIF", "elsif");
        productions.put("PRS", "switch");
    }
    private String respuesta = "";

    public static String getProduction(String symbol) {
        return productions.get(symbol);
    }

    public static boolean isTerminal(String symbol) {
        return !productions.containsKey(symbol);
    }

    private String pda(String string){
        Stack<String> stack = new Stack<>();

        int nPalabras = 0;
        int ncontenidosC = 0;
        int nDats = 0;

        stack.clear();

        stack.push("$");
        stack.push("SC");

        int i = 0;

        String X;

        string = string.replaceAll("\\." , " . ")
        .replaceAll("\"", " \" ")
        .replaceAll(",", " , ")
        .replaceAll("\\{", " { ")
        .replaceAll("\\}", " } ")
        .replaceAll("\\(", " ( ")
        .replaceAll("\\)", " ) ")
        .replaceAll(">>", " >> ");

        String [] words = string.split("\\s+");
        respuesta += "Pila inicial: " + stack + "\n";
        resultado.setText(respuesta);

        while (true) {
            try{
                X = stack.peek();
            
            if (X.equals("$")) {
                return ("La cadena es aceptada");
            }
            
            if (isTerminal(X)) {
                if (X != null && (Pattern.matches(X, words[i]) || X.matches(words[i]))) {
                    stack.pop();
                    respuesta += stack + "\n";
                    resultado.setText(respuesta);
                    i++;
                }else if(X.equals("C?")){
                    stack.pop();
                    if (Pattern.matches("\\,", words[i])){
                        stack.push("C");
                    } else {
                        respuesta += stack + "\n";
                        resultado.setText(respuesta);
                        return ("Cadena no valida");
                    }
                    respuesta += stack + "\n";
                    resultado.setText(respuesta);
                }else if(X.equals("SCS*")){
                    if(words[i].equals("case")){
                        stack.push("SCS2");
                        stack.push("SCS1");
                    } else{
                        stack.pop();
                    }
                    respuesta += stack + "\n";
                    resultado.setText(respuesta);
                }else if(X.equals("DAP+")){
                    if(Character.isLetter(words[i].charAt(0))){
                        
                        if (words[i+1].matches(getProduction("C"))){
                            stack.push("C?");
                        } else {
                            stack.pop();
                        }
                        stack.push("L+");
                        respuesta += stack + "\n";
                        resultado.setText(respuesta);
                    }else if(words[i].matches(getProduction("COMILLAS"))){
                        stack.push("ST");
                        stack.push("COMILLAS");
                        respuesta += stack + "\n";
                        resultado.setText(respuesta);
                    }else{
                        if(nDats == 0){
                            respuesta += stack + "\n";
                            resultado.setText(respuesta);
                            return ("Cadena no valida");
                        }
                        stack.pop();
                        respuesta += stack + "\n";
                        resultado.setText(respuesta);
                    }
                }else if(X.equals("CONTENIDOF+")){
                    if(words[i].matches("switch")){
                        stack.push("CAS");
                        stack.push("SSS");
                        respuesta += stack + "\n";
                        resultado.setText(respuesta);
                    }else if(words[i].matches("if")){
                        stack.push("EISCS");
                        stack.push("SS");
                        respuesta += stack + "\n";
                        resultado.setText(respuesta);
                    }else if(words[i].matches("for")){
                        stack.push("COF");
                        stack.push("FCO");
                        respuesta += stack + "\n";
                        resultado.setText(respuesta);
                    }else if(words[i].matches("while")){
                        stack.push("COF");
                        stack.push("CONW");
                        respuesta += stack + "\n";
                        resultado.setText(respuesta);
                    }else if (words[i].matches("do")){
                        stack.push("CONW");
                        stack.push("DCON");
                        respuesta += stack + "\n";
                        resultado.setText(respuesta);
                    }else if(words[i].matches("for")){
                        stack.push("COF");
                        stack.push("FCO");
                        respuesta += stack + "\n";
                        resultado.setText(respuesta);
                    }else if (words[i].matches("println")){
                        stack.push("PI");
                        stack.push("println");
                        respuesta += stack + "\n";
                        resultado.setText(respuesta);
                    }else if (words[i].matches("print")){
                        stack.push("PI");
                        stack.push("print");
                        respuesta += stack + "\n";
                        resultado.setText(respuesta);
                    }else if(words[i].matches("in")){
                        stack.push("GV");
                        stack.push("PRI");
                        respuesta += stack + "\n";
                        resultado.setText(respuesta);

                    }else if(words[i].matches("sqrt")){
                        stack.push("ISQ");
                        stack.push("PRSQ");
                        respuesta += stack + "\n";
                        resultado.setText(respuesta);

                    }else if (Character.isLetter(words[i].charAt(0))){
                        stack.push("M");
                        stack.push("L+");
                        respuesta += stack + "\n";
                        resultado.setText(respuesta);
                    }else{
                        respuesta += stack + "\n";
                        resultado.setText(respuesta);
                        stack.pop();
                    }
                }else if(X.equals("L+")){
                    String cadena = words[i];
                    for (int j = 0; j < cadena.length(); j++) {
                        String productions = getProduction("L");
                        String[] symbols = productions.split("\\s+");
                        stack.push(symbols[0]);
                        char letra = cadena.charAt(j);
                        respuesta += stack + "\n";
                        resultado.setText(respuesta);
                        String letraStr = Character.toString(letra);
                        if(!letraStr.matches(symbols[0])){
                            return ("Cadena no valida");
                        } else{
                            stack.pop();
                            respuesta += stack + "\n";
                            resultado.setText(respuesta);
                        }
                    }
                    stack.pop();
                    i++;
                    respuesta += stack + "\n";
                    resultado.setText(respuesta);
                }else if(X.equals("VA*")){
                    String productions = getProduction("C");
                    String[] production = productions.split("\\|");
                    String[] symbols = production[0].split("\\s+");
                    if (Pattern.matches(words[i], symbols[0])){
                        stack.push("L+");
                        stack.push("C");
                    } else {
                        stack.pop();
                    }
                }else if(X.equals("K*")){
                    if (Pattern.matches(words[i], getProduction("C"))){
                        stack.push("V");
                        stack.push("C");
                    } else {
                        stack.pop();
                    }
                }else if(X.equals("PALABRAS+")){
                    String productions = getProduction("PALABRAS");
                    String[] production = productions.split("\\|");
                    String[] symbols0 = production[0].split("\\s+");
                    if (nPalabras == 0){
                        if (words[i].matches(symbols0[0])){
                            stack.push("L+");
                        } else if (Character.isDigit(words[i].charAt(0))){
                            if(words[i+1].matches(getProduction("P"))){
                                stack.push("N");
                            }
                            stack.push("D+");
                        } else {
                            respuesta += stack + "\n";
                            resultado.setText(respuesta);
                            return ("Cadena no valida");
                        }
                        nPalabras++;
                    } else {
                        if (words[i].matches(symbols0[0])){
                            stack.push("L+");
                        } else if (Character.isDigit(words[i].charAt(0))){
                            stack.push("NU");
                        } else {
                            nPalabras = 0;
                            stack.pop();
                        }
                    }
                }else if(X.equals("D+")){
                    String cadena = words[i];
                    for (int j = 0; j < cadena.length(); j++) {
                        String productions = getProduction("D");
                        String[] symbols = productions.split("\\s+");
                        stack.push(symbols[0]);
                        char letra = cadena.charAt(j);
                        respuesta += stack + "\n";
                        resultado.setText(respuesta);
                        String letraStr = Character.toString(letra);
                        if(!letraStr.matches(symbols[0])){
                            respuesta += stack + "\n";
                            resultado.setText(respuesta);
                            return ("Cadena no valida");
                        } else{
                            stack.pop();
                            respuesta += stack + "\n";
                            resultado.setText(respuesta);
                        }
                    }
                    stack.pop();
                    i++;
                    respuesta += stack + "\n";
                    resultado.setText(respuesta);
                }else if(X.equals("N?")){
                    stack.pop();
                    String productions = getProduction("N");
                    String[] production = productions.split("\\|");
                    String[] symbols = production[0].split("\\s+");
                    if (Pattern.matches(getProduction(symbols[0]), words[i])){
                        stack.push("D+");
                        stack.push("P");
                    } else {
                        stack.pop();
                    }
                    respuesta += stack + "\n";
                    resultado.setText(respuesta);
                }else if(X.equals("EISC*")){
                    if(words[i].equals("elsif")){
                        stack.push("CSC");
                        stack.push("PRELSIF");
                    } else{
                        stack.pop();
                    }
                    respuesta += stack + "\n";
                    resultado.setText(respuesta);
                }else if(X.equals("ESC?")){
                    stack.pop();
                    if(words[i].equals("else")){
                        stack.push("COF");
                        stack.push("PRELSE");
                    }
                }else if(X.equals("CONTENIDOC+")){
                    if (words[i].matches("func")){
                        if (words[i+1].matches("main")){
                            stack.push("FCM");
                            stack.push("PRF");
                        }else{
                            stack.push("FC");
                            stack.push("PRF");
                        }
                        ncontenidosC++;
                        respuesta += stack + "\n";
                        resultado.setText(respuesta);
                    }else if (Character.isLetter(words[i].charAt(0))){
                        stack.push("M");
                        stack.push("L+");
                        respuesta += stack + "\n";
                        resultado.setText(respuesta);
                        ncontenidosC++;
                    }else {
                        if(ncontenidosC == 0){
                            stack.pop();
                            respuesta += stack + "\n";
                            resultado.setText(respuesta);
                            return ("Cadena no valida");
                        }
                        ncontenidosC = 0;
                        stack.pop();
                        respuesta += stack + "\n";
                        resultado.setText(respuesta);
                    }
                }else {
                    respuesta += stack + "\n";
                    resultado.setText(respuesta);
                    return ("Cadena no valida");
                }
            } else { // X is not terminal
                if (X.equals("SC")){
                    if (words[i+1].equals("main")){
                        stack.pop();
                        stack.push("CLM");
                        stack.push("PRC");
                        respuesta += stack + "\n";
                        resultado.setText(respuesta);
                    } else {
                        stack.pop();
                        stack.push("CL");
                        stack.push("PRC");
                        respuesta += stack + "\n";
                        resultado.setText(respuesta);
                    }
                }else if(X.equals("SCS2")){
                    if(words[i].matches(getProduction("COMILLAS"))){
                        stack.pop();
                        stack.push("COF");
                        stack.push("DAS");
                    }else if(Character.isDigit(words[i].charAt(0))){
                        stack.pop();
                        stack.push("COF");
                        stack.push("D+");
                    } else {
                        respuesta += stack + "\n";
                        resultado.setText(respuesta);
                        return ("Cadena no valida");
                    }
                    respuesta += stack + "\n";
                    resultado.setText(respuesta);
                }else if(X.equals("EE")){
                    stack.pop();
                    if(words[i].equals("==")){
                        stack.push("==");
                        respuesta += stack + "\n";
                        resultado.setText(respuesta);
                    } else if(words[i].equals("!=")){
                        stack.push("!=");
                        respuesta += stack + "\n";
                        resultado.setText(respuesta);
                    } else if(words[i].equals(">=")){
                        stack.push(">=");
                        respuesta += stack + "\n";
                        resultado.setText(respuesta);
                    } else if(words[i].equals("<=")){
                        stack.push("<=");
                        respuesta += stack + "\n";
                        resultado.setText(respuesta);
                    } else {
                        respuesta += stack + "\n";
                        resultado.setText(respuesta);
                        return ("Cadena no valida");
                    }
                }else if (X.equals("COND")){
                    stack.pop();
                    if(words[i+1].matches("==") || words[i+1].matches("!=") || words[i+1].matches(">=") || words[i+1].matches("<=")){
                        stack.push("DAT");
                        stack.push("LE");
                        respuesta += stack + "\n";
                        resultado.setText(respuesta);
                    } else if (words[i+1].matches(getProduction("PARC"))){
                        stack.push("PARC");
                        stack.push("L+");
                    }
                }else if(X.equals("PARAMS")){
                    if (Character.isLetter(words[i].charAt(0))){
                        stack.pop();
                        respuesta += stack + "\n";
                        resultado.setText(respuesta);
                        stack.push("L+");
                        respuesta += stack + "\n";
                        resultado.setText(respuesta);
                        stack.pop();
                        respuesta += stack + "\n";
                        resultado.setText(respuesta);
                        i++;
                        if (words[i].equals(getProduction("C"))){
                            stack.push("PARAMS");
                            respuesta += stack + "\n";
                            resultado.setText(respuesta);
                            i++;
                        }
                    } else if (!Character.isLetter(words[i].charAt(0))){
                        stack.pop();
                        respuesta += stack + "\n";
                        resultado.setText(respuesta);
                    } else {
                        respuesta += stack + "\n";
                        resultado.setText(respuesta);
                        return ("Cadena no valida");
                    }
                }else if(X.equals("PALABRAS")){
                    stack.pop();
                    String productions = getProduction("PALABRAS");
                    String[] production = productions.split("\\|");
                    String[] symbols0 = production[0].split("\\s+");
                    if (words[i].matches(symbols0[0])){
                        stack.push("L+");
                    } else if (Character.isDigit(words[i].charAt(0))){
                        if(words[i+1].matches(getProduction("P"))){
                            stack.push("N");
                        }
                        stack.push("D+");
                    } else {
                        respuesta += stack + "\n";
                        resultado.setText(respuesta);
                        return ("Cadena no valida");
                    }
                }else if (X.equals("V")){
                    if(words[i].matches(getProduction("COMILLAS"))){
                        stack.pop();
                        stack.push("ST");
                        stack.push("COMILLAS");
                        respuesta += stack + "\n";
                        resultado.setText(respuesta);
                    }else if(words[i].equals("true")){
                        stack.pop();
                        stack.push("true");
                        respuesta += stack + "\n";
                        resultado.setText(respuesta);
                        stack.pop();
                        respuesta += stack + "\n";
                        resultado.setText(respuesta);
                        i++;
                    }else if(words[i].equals("false")){
                        stack.pop();
                        stack.push("false");
                        respuesta += stack + "\n";
                        resultado.setText(respuesta);
                        stack.pop();
                        respuesta += stack + "\n";
                        resultado.setText(respuesta);
                        i++;
                    }else if (Character.isDigit(words[i].charAt(0))){
                        stack.pop();
                        if(words[i+1].matches(getProduction("P"))){
                            stack.push("N");
                        }
                        stack.push("D+");
                    }else {
                        respuesta += stack + "\n";
                        resultado.setText(respuesta);
                        return ("Cadena no valida");
                    }
                }else if (getProduction(X) != null) {
                    stack.pop();
                    String productions = getProduction(X);
                    String[] production = productions.split("\\|");
                    String[] symbols = production[0].split("\\s+");
                    for (int j = symbols.length - 1; j >= 0; j--) {
                        stack.push(symbols[j]);
                    }
                    respuesta += stack + "\n";
                    resultado.setText(respuesta);
                } else {
                    respuesta += stack + "\n";
                    resultado.setText(respuesta);
                    return ("Error");
                }
            }
            }catch (Exception e) {
                return ("Cadena no valida");
            }
        }
    }

    public void validar(String texto) {
        resultado.setText(respuesta);
        estatus.setText(pda(texto));
    }
}
