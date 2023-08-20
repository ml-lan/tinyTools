package com.tiny.tool.ctrl;

import atlantafx.base.controls.Card;
import atlantafx.base.theme.Styles;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import com.tiny.tool.entity.Cmd;
import com.tiny.tool.util.JsonUtil;
import java.io.File;
import java.io.IOException;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.ResourceBundle;
import javafx.event.ActionEvent;
import javafx.fxml.Initializable;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Node;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.layout.FlowPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Pane;
import javafx.scene.text.Text;
import javafx.scene.text.TextFlow;

public class HomeCtrl implements Initializable {

    public Pane homePane;
    public static final String fileName = "cmd.json";
    public List<Cmd> cmdList = new ArrayList<>();

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        String tempFilePath = System.getProperty("user.dir") + File.separator + "conf" + File.separator + fileName;
        String str = JsonUtil.readJsonFile(tempFilePath);
        if (str == null || "".equals(str)) {
            return;
        }
        Gson gson = new Gson();
        cmdList = gson.fromJson(str, new TypeToken<List<Cmd>>(){}.getType());
        FlowPane pane = new FlowPane();
        pane.setMinWidth(900);
        pane.setPadding(new Insets(11, 12, 13, 14));
        pane.setHgap(20);//设置控件之间的垂直间隔距离
        pane.setVgap(20);//设置控件之间的水平间隔距离
        for (Cmd cmd : cmdList) {
            var card = new Card();
            card.setMinWidth(100);
            card.setMaxWidth(300);
            var title = new Label(cmd.getName());
            title.getStyleClass().add(Styles.TITLE_4);
            card.setHeader(title);
            var text = new TextFlow(new Text(
                cmd.getDescribe()
            ));
            text.setMaxWidth(300);
            card.setBody(text);
            var startBtn = new Button("启动");
            var stopBtn = new Button("停止");
            startBtn.setId(cmd.getId());
            startBtn.setOnAction(this::onBtnStart);
            var footer = new HBox(20,
                startBtn,
                stopBtn
            );
            footer.setAlignment(Pos.CENTER_LEFT);
            card.setFooter(footer);
            pane.getChildren().add(card);
        }
        homePane.getChildren().add(pane);
    }


    /**
     * 启动脚本事件
     * @param actionEvent
     */
    public void onBtnStart(ActionEvent actionEvent) {
        String id = ((Node) actionEvent.getSource()).getId();
        Optional<Cmd> cmd = cmdList.stream().filter(e->e.getId().equals(id)).findFirst();
        //执行脚本
        cmd.ifPresent(value -> runBat(cmd.get().getPath(), cmd.get().getName()));
    }


    /**
     * 启动脚本
     * @param path
     * @param batName
     */
    public void runBat(String path, String batName) {
        String cmd = "cmd /c start /d " + path +" "+ batName;
        try {
            Process ps = Runtime.getRuntime().exec(cmd);
            ps.waitFor();
        } catch (IOException | InterruptedException ioe) {
            ioe.printStackTrace();
        }
    }
}
