package com.tiny.tool.ctrl;

import java.net.URL;
import java.util.ResourceBundle;
import javafx.fxml.Initializable;
import javafx.scene.control.Tab;
import javafx.scene.control.TabPane;
import javafx.scene.control.TabPane.TabClosingPolicy;
import javafx.scene.layout.Pane;

/**
 * 主界面控制器
 */
public class MainCtrl implements Initializable {

    // 主容器
    public Pane rootPane;
    //
    public Tab homeTab, logTab, aboutTab;
    public TabPane mainTabPane;
    public Pane homeTabPage, logTabPage, aboutTabPage;

    public void initialize(URL location, ResourceBundle resources) {
        System.out.println("initialize: " + location.getPath());
        mainTabPane.setTabClosingPolicy(TabClosingPolicy.UNAVAILABLE);
        mainTabPane.setMinWidth(990);
        logTabPage.setMinHeight(600);
    }
}
