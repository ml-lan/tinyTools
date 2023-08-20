package com.tiny.tool.ctrl;

import atlantafx.base.controls.Card;
import atlantafx.base.theme.Styles;
import java.net.URL;
import java.util.ResourceBundle;
import javafx.fxml.Initializable;
import javafx.scene.control.Label;
import javafx.scene.layout.Pane;

public class AboutCtrl implements Initializable {

    public Pane aboutPane;

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        String javaVersion = System.getProperty("java.version");
        String javafxVersion = System.getProperty("javafx.version");
        String text = "A Tiny Tool Build By JavaFX " + javafxVersion + ""
            + "running on Java " + javaVersion + ".";
        var card = new Card();
        card.getStyleClass().add(Styles.ELEVATED_2);
        card.setMinWidth(250);
        card.setMaxWidth(250);
        card.setBody(new Label(text));
        card.setMinHeight(100);
        card.setMinWidth(450);
        aboutPane.getChildren().add(card);
    }
}
