package com.example.automatadepila.controllers;

import com.example.automatadepila.logica.Validar;
import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.scene.control.TextArea;

public class AutomataPilaController {
    Validar validar;

    @FXML
    private TextArea resultado;

    @FXML
    private TextArea entrada;
    @FXML
    private Label estatus;

    public void validar(){
        validar = new Validar(resultado, estatus);
        String texto = entrada.getText();
        validar.validar(texto.toString());
    }

    public void limpiar(){
        resultado.setText("");
        entrada.setText("");
        estatus.setText("");
    }
}
