package com.tiny.tool.ctrl;

import com.tiny.tool.entity.BrowseLog;
import com.tiny.tool.util.PageUtil;
import java.io.File;
import java.io.IOException;
import java.net.URL;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.ResourceBundle;
import java.util.stream.Collectors;
import javafx.beans.property.SimpleStringProperty;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.Initializable;
import javafx.geometry.Insets;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.Pagination;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.FlowPane;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.Pane;
import javafx.scene.layout.StackPane;
import javafx.stage.FileChooser;
import javafx.stage.Window;
import org.apache.commons.io.FileUtils;
import org.apache.commons.io.LineIterator;

/**
 * 主界面控制器
 */
public class MainCtrl implements Initializable {

    // 主容器
    public Pane rootPane;
    // 表格
    public TableView<BrowseLog> tableView;
    public TextField ipText, dateText;
    /**
     * 日志数据
     */
    public ArrayList<BrowseLog> logList = new ArrayList<>(100000);
    public ArrayList<BrowseLog> searchList = new ArrayList<>(1000);
    public static final int pageSize = 1000;


    public void initialize(URL location, ResourceBundle resources) {
        System.out.println("initialize: " + location.getPath());
        BorderPane pane = new BorderPane();
        GridPane gridPane = new GridPane();
        Button btnChooseFile = new Button();
        gridPane.setPadding(new Insets(11, 12, 13, 14));
        gridPane.setHgap(5);//设置控件之间的垂直间隔距离
        gridPane.setVgap(5);//设置控件之间的水平间隔距离
        btnChooseFile.setText("选择日志文件");
        btnChooseFile.setOnAction(this::onBtnChooseFileClick);
        Button btnAbout = new Button();
        btnAbout.setText("关于");
        btnAbout.setOnAction(this::onBtnAboutClick);
        gridPane.add(btnAbout, 0, 0);
        gridPane.add(btnChooseFile, 1, 0);
        Label ipLabel = new Label("IP:");
        ipText = new TextField();
        gridPane.add(ipLabel, 0, 1);
        gridPane.add(ipText, 1, 1);
        Label dateLabel = new Label("时间:");
        dateText = new TextField();
        gridPane.add(dateLabel, 2, 1);
        gridPane.add(dateText, 3, 1);
        Button btnSearch = new Button();
        btnSearch.setText("搜索");
        btnSearch.setOnAction(this::onBtnSearch);
        Button btnReset = new Button();
        btnReset.setText("重置");
        btnReset.setOnAction(this::onBtnReset);
        gridPane.add(btnSearch, 4, 1);
        gridPane.add(btnReset, 5, 1);
        pane.setTop(gridPane); //放置在上面
        FlowPane flowPane = new FlowPane();
        flowPane.setPadding(new Insets(11, 12, 13, 14));
        flowPane.setHgap(5);//设置控件之间的垂直间隔距离
        flowPane.setVgap(5);//设置控件之间的水平间隔距离
        tableView = new TableView<>();
        TableColumn<BrowseLog, String> noColumn = new TableColumn<>("序号");
        noColumn.setMinWidth(60);
        noColumn.setCellValueFactory(data -> new SimpleStringProperty(data.getValue().getNo()));
        tableView.getColumns().add(noColumn);
        //IP
        TableColumn<BrowseLog, String> ipColumn = new TableColumn<>("IP");
        ipColumn.setMinWidth(100);
        ipColumn.setCellValueFactory(data -> new SimpleStringProperty(data.getValue().getRemoteAddr()));
        tableView.getColumns().add(ipColumn);
        //时间
        TableColumn<BrowseLog, String> timeColumn = new TableColumn<>("时间");
        timeColumn.setMinWidth(180);
        timeColumn.setCellValueFactory(data -> new SimpleStringProperty(data.getValue().getTimeLocal()));
        tableView.getColumns().add(timeColumn);
        //方式
        TableColumn<BrowseLog, String> actColumn = new TableColumn<>("方式");
        actColumn.setMinWidth(40);
        actColumn.setCellValueFactory(data -> new SimpleStringProperty(data.getValue().getAction()));
        tableView.getColumns().add(actColumn);
        //地址
        TableColumn<BrowseLog, String> addressColumn = new TableColumn<>("地址");
        addressColumn.setMinWidth(100);
        addressColumn.setCellValueFactory(data -> new SimpleStringProperty(data.getValue().getRequest()));
        tableView.getColumns().add(addressColumn);
        //状态
        TableColumn<BrowseLog, String> statusColumn = new TableColumn<>("状态");
        statusColumn.setMinWidth(80);
        statusColumn.setCellValueFactory(data -> new SimpleStringProperty(data.getValue().getStatus()));
        tableView.getColumns().add(statusColumn);
        //大小
        TableColumn<BrowseLog, String> sizeColumn = new TableColumn<>("大小");
        sizeColumn.setMinWidth(60);
        sizeColumn.setCellValueFactory(data -> new SimpleStringProperty(data.getValue().getBodyBytesSent()));
        tableView.getColumns().add(sizeColumn);
        //来源
        TableColumn<BrowseLog, String> originColumn = new TableColumn<>("来源");
        originColumn.setMinWidth(120);
        originColumn.setCellValueFactory(data -> new SimpleStringProperty(data.getValue().getHttpReferer()));
        tableView.getColumns().add(originColumn);
        //信息
        TableColumn<BrowseLog, String> infoColumn = new TableColumn<>("信息");
        infoColumn.setMinWidth(140);
        infoColumn.setCellValueFactory(data -> new SimpleStringProperty(data.getValue().getHttpXForwardedFor()));
        tableView.getColumns().add(infoColumn);
        var pg = new Pagination(1000, 0);
        pg.setMaxPageIndicatorCount(5);
        pg.setPageFactory(pageNum -> {
            if (!searchList.isEmpty()) {
                PageUtil<BrowseLog> pager = new PageUtil<>(searchList, pageSize);
                ObservableList<BrowseLog> obList = FXCollections.observableArrayList(pager.page(pageNum + 1));
                tableView.setItems(obList);
            }
            if (searchList.isEmpty() && !logList.isEmpty()) {
                PageUtil<BrowseLog> pager = new PageUtil<>(logList, pageSize);
                ObservableList<BrowseLog> obList = FXCollections.observableArrayList(pager.page(pageNum + 1));
                tableView.setItems(obList);
            }
            return new StackPane();
        });
        flowPane.getChildren().addAll(tableView,pg);
        pane.setCenter(flowPane);
        rootPane.getChildren().add(pane);
    }

    /**
     * 重置按钮事件
     * @param actionEvent
     */
    public void onBtnReset(ActionEvent actionEvent) {
        logList.clear();
        searchList.clear();
        ipText.setText("");
        dateText.setText("");
        tableView.getItems().clear();
        tableView.refresh();
    }
    /**
     * 搜索按钮事件
     */
    public void onBtnSearch(ActionEvent actionEvent) {
        if (ipText.getText().equals("") && dateText.getText().equals("")) {
            return;
        }
        if (ipText.getText() != null && !ipText.getText().equals("")) {
            searchList = logList.stream().filter(e -> e.getRemoteAddr()!=null && e.getRemoteAddr().contains(ipText.getText()))
                .collect(Collectors.toCollection(ArrayList::new));
        }
        if (dateText.getText() != null && !dateText.getText().equals("")) {
            searchList = logList.stream().filter(e -> e.getTimeLocal()!=null && e.getTimeLocal().contains(dateText.getText()))
                .collect(Collectors.toCollection(ArrayList::new));
        }
        ObservableList<BrowseLog> obList = FXCollections.observableArrayList(searchList).sorted(Comparator.comparing(
            BrowseLog::getTimeLocal));
        tableView.setItems(obList);
    }

    /**
     * 弹出框按钮单击事件
     * @param actionEvent
     */
    public void onBtnAboutClick(ActionEvent actionEvent) {
        String javaVersion = System.getProperty("java.version");
        String javafxVersion = System.getProperty("javafx.version");
        Alert alert = new Alert(Alert.AlertType.INFORMATION);
        alert.setHeaderText(null);
        alert.setContentText("A Tiny Tool Build By JavaFX " + javafxVersion + ", running on Java " + javaVersion + ".");
        alert.show();
    }

    /**
     * 选择文件按钮单机事件
     * @param actionEvent
     */
    public void onBtnChooseFileClick(ActionEvent actionEvent)  {
        Window window = rootPane.getScene().getWindow();
        FileChooser fileChooser = new FileChooser();
        // 文件类型过滤器
        FileChooser.ExtensionFilter filter = new FileChooser.ExtensionFilter("Log files (*.log, *.txt)", "*.log", "*.txt");
        fileChooser.getExtensionFilters().add(filter);
        File file = fileChooser.showOpenDialog(window);
        if (file != null) {
            logList.clear();
            try (LineIterator it = FileUtils.lineIterator(file, "UTF-8")) {
                int i = 0;
                while (it.hasNext()) {
                    i++;
                    logList.add(new BrowseLog(String.valueOf(i), it.nextLine()));
                }
            } catch (IOException e) {
                System.out.println(e.getMessage());
            }
            // 把清单对象转换为JavaFX控件能够识别的数据对象
            PageUtil<BrowseLog> pager = new PageUtil<>(logList, pageSize);
            ObservableList<BrowseLog> obList = FXCollections.observableArrayList(pager.page(1));
            tableView.setItems(obList);
        }
    }

}
