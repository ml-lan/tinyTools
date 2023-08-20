package com.tiny.tool.ctrl;

import java.net.URL;
import java.util.ResourceBundle;
import javafx.fxml.Initializable;
import javafx.scene.control.Label;
import javafx.scene.layout.Pane;

public class HomeCtrl implements Initializable {

    public Pane homePane;
    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        homePane.getChildren().add(new Label("主页"));
    }
}
