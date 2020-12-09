package home.scene.OS;

import javafx.application.Platform;
import javafx.beans.value.ChangeListener;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.collections.transformation.FilteredList;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.control.TableCell;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.control.TextFormatter;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.input.KeyEvent;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.AnchorPane;
import javafx.util.Callback;

import java.io.IOException;
import java.net.URL;
import java.text.DecimalFormat;
import java.text.NumberFormat;
import java.util.ResourceBundle;

import com.jfoenix.controls.JFXButton;
import com.jfoenix.controls.JFXComboBox;
import com.jfoenix.controls.JFXTextField;
import com.jfoenix.controls.JFXToggleButton;

import home.resource.classComponent.AutoCompleteComboBoxListener;
import home.resource.classComponent.MyNumberStringConverter;
import home.resource.classComponent.Util;
import home.resource.classForDB.Address;
import home.resource.classForDB.Atm;
import home.scene.bottomBar.ctrlBottomBar;

public class ctrlOS implements Initializable {

    @FXML
    private AnchorPane osPane;

    @FXML
    private TableView<Atm> atmTable;

    @FXML
    private TableColumn<Atm, String> codeCol;

    @FXML
    private TableColumn<Atm, String> nameCol;

    @FXML
    private TableColumn<Atm, Address> addressCol;

    @FXML
    private TableColumn<Atm, Long> moneyCol;

    @FXML
    private TableColumn<Atm, Boolean> statusCol;

    @FXML
    private AnchorPane bottomBar;

    @FXML
    private JFXTextField textCode;

    @FXML
    private JFXTextField textName;

    @FXML
    private JFXTextField textMoneyStorage;

    @FXML
    private JFXTextField textAddress;

    @FXML
    private JFXComboBox<String> comboBoxVillage;

    @FXML
    private JFXComboBox<String> comboBoxDistrict;

    @FXML
    private JFXComboBox<String> comboBoxProvince;

    @FXML
    private JFXToggleButton toggleActive;

    // Get all buttons from ctrlNavBar
    ObservableList<JFXButton> bottomBarButtons;

    // text formatter for currency
    TextFormatter<Number> textFormatterCurrency;

    private final ObservableList<Atm> data = FXCollections.observableArrayList(
            new Atm("ab6632", "Atm so 3", new Address("26", "Dong hoa", "Di an", "Binh duong"), 2200000L, true),
            new Atm("ab6633", "Atm so 4", new Address("26", "Dong hoa", "Di an", "Binh duong"), 2200000L, true),
            new Atm("ab6634", "Atm so 5", new Address("26", "Dong hoa", "Di an", "Binh duong"), 2200000L, true),
            new Atm("ab6635", "Atm so 6", new Address("26", "Dong hoa", "Di an", "Binh duong"), 2200000L, true),
            new Atm("ab6636", "Atm so 7", new Address("26", "Dong hoa", "Di an", "Binh duong"), 2200000L, true));

    @Override
    public void initialize(URL location, ResourceBundle resources) {

        ctrlBottomBar insCtrlBottomBar = new ctrlBottomBar();
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("../bottomBar/bottomBar.fxml"));
            loader.setController(insCtrlBottomBar);
            bottomBar.getChildren().add(loader.load());
        } catch (IOException e) {
            e.printStackTrace();
        }
        bottomBarButtons = insCtrlBottomBar.fetchAllButtons();

        // set text for title
        insCtrlBottomBar.fetchLabelTitle().get(0).setText("Operating system");

        // set handler click for buttons in bottom bar
        setHandleForClickButtonInBottomBar();

        // setting textField moneyStorage to currencyFormat
        setTextFieldCurrencyFormat(textMoneyStorage);

        // setting filter and auto complete of comboBox
        new AutoCompleteComboBoxListener<>(comboBoxVillage);
        new AutoCompleteComboBoxListener<>(comboBoxDistrict);
        new AutoCompleteComboBoxListener<>(comboBoxProvince);

        // init
        setValueFromTableToTextField();
        initTable();
        bindDataFromTable();

    }

    private void setTextFieldCurrencyFormat(JFXTextField textField) {

        // Setting up the textField with a Formatter
        NumberFormat nFormat = NumberFormat.getInstance();

        // Define the integer and fractional digits
        nFormat.setMinimumIntegerDigits(1);
        nFormat.setMaximumFractionDigits(2);

        // setting up the TextFormatter with the NumberFormat and a Filter to limit the
        // inputChars
        textFormatterCurrency = new TextFormatter<>(new MyNumberStringConverter(nFormat), 0l, Util.createFilter());

        textField.setTextFormatter(textFormatterCurrency);
    }

    // * Initialize table view

    public void initTable() {

        // CODE COL SETTING
        codeCol.setPrefWidth(100);
        codeCol.setCellValueFactory(new PropertyValueFactory<>("code"));

        // NAME COL SETTING
        nameCol.setPrefWidth(150);
        nameCol.setCellValueFactory(new PropertyValueFactory<>("name"));

        // MONEY COL SETTING
        moneyCol.setPrefWidth(150);
        moneyCol.setCellValueFactory(new PropertyValueFactory<>("moneyStorage"));

        // ---- setting the money storage column
        NumberFormat currencyFormat = NumberFormat.getCurrencyInstance();
        moneyCol.setCellFactory(ms -> new TableCell<Atm, Long>() {

            @Override
            protected void updateItem(Long price, boolean empty) {
                super.updateItem(price, empty);
                if (empty) {
                    setText(null);
                } else {
                    setText(currencyFormat.format(price));
                }
            }
        });

        // ADDRESS COL SETTING
        addressCol.setCellValueFactory(new PropertyValueFactory<Atm, Address>("address"));

        // ---- setting the cell factory for the address column
        Callback<TableColumn<Atm, Address>, TableCell<Atm, Address>> cellAddressFactory = param -> {
            final TableCell<Atm, Address> addressCell = new TableCell<Atm, Address>() {
                @Override
                public void updateItem(Address item, boolean empty) {
                    super.updateItem(item, empty);
                    if (empty) {
                        setGraphic(null);
                        setText(null);
                    } else {
                        setGraphic(null);
                        setText(String.format("%s - %s - %s - %s", item.getCompleteStreet(), item.getVillage(),
                                item.getDistrict(), item.getProvince()));
                    }
                }
            };
            return addressCell;
        };
        addressCol.setCellFactory(cellAddressFactory);

        // STATUS COL SETTING
        statusCol.setCellValueFactory(new PropertyValueFactory<>("status"));

        // ---- setting the cell factory for the status column
        Callback<TableColumn<Atm, Boolean>, TableCell<Atm, Boolean>> cellStatusFactory = param -> {
            final TableCell<Atm, Boolean> cell = new TableCell<Atm, Boolean>() {
                final JFXButton btn = new JFXButton("Active");

                {
                    btn.setOnAction(event -> {
                        // todo: handle when click - change status atm

                        // autoselect row when click button
                        atmTable.getSelectionModel().select(getIndex());
                    });
                }

                @Override
                public void updateItem(Boolean item, boolean empty) {
                    super.updateItem(item, empty);
                    if (empty) {
                        setGraphic(null);
                        setText(null);
                    } else {
                        setGraphic(btn);
                        setText(null);
                    }
                }
            };
            return cell;
        };

        statusCol.setCellFactory(cellStatusFactory);

        atmTable.setItems(data);
        atmTable.getSelectionModel().select(data.get(0));

    }

    // * set handler for bottom bar

    private void setHandleForClickButtonInBottomBar() {
        for (JFXButton button : bottomBarButtons) {
            switch (button.getId()) {
                case "addBtn":
                    button.setOnAction(actionEvent -> {
                        Atm newAtm = new Atm();
                        newAtm.setCode(textCode.getText());
                        newAtm.setName(textName.getText());
                        newAtm.setMoneyStorage(Long.parseLong(
                                textMoneyStorage.getTextFormatter().valueProperty().getValue().toString()) / 100);
                        newAtm.setAddress(new Address(textAddress.getText(), comboBoxVillage.getValue(),
                                comboBoxDistrict.getValue(), comboBoxProvince.getValue()));
                        newAtm.setStatus(toggleActive.isSelected());
                        data.add(newAtm);
                    });
                    break;
                case "editBtn":
                    button.setOnAction(actionEvent -> {
                        Atm newAtm = new Atm();
                        newAtm.setCode(textCode.getText());
                        newAtm.setName(textName.getText());
                        newAtm.setMoneyStorage(Long.parseLong(
                                textMoneyStorage.getTextFormatter().valueProperty().getValue().toString()) / 100);
                        newAtm.setAddress(new Address(textAddress.getText(), comboBoxVillage.getValue(),
                                comboBoxDistrict.getValue(), comboBoxProvince.getValue()));
                        newAtm.setStatus(toggleActive.isSelected());
                        data.get(atmTable.getSelectionModel().getSelectedIndex());
                    });
                    break;
                case "deleteBtn":
                    button.setOnAction(actionEvent -> {
                        // load scene for see more
                    });
            }
        }
    }

    public void bindDataFromTable() {
        comboBoxVillage.getItems().addAll("Dong hoa", "Item 2", "Item 3");
        comboBoxDistrict.getItems().addAll("Di an", "Item 2", "Item 3");
        comboBoxProvince.getItems().addAll("Binh duong", "Item 2", "Item 3");
    }

    // * setting for binding value when selected row

    public void setValueFromTableToTextField() {
        atmTable.getSelectionModel().selectedItemProperty().addListener((obs, oldSelection, newSelection) -> {
            if (newSelection != null) {
                Atm atm = atmTable.getItems().get(atmTable.getSelectionModel().getSelectedIndex());
                textCode.setText(atm.getCode());
                textName.setText(atm.getName());
                textMoneyStorage.setText(atm.getMoneyStorage().toString());
                textAddress.setText(atm.getAddress().getCompleteStreet());
                comboBoxVillage.getSelectionModel().select((atm.getAddress().getVillage().toString()));
                comboBoxDistrict.getSelectionModel().select((atm.getAddress().getDistrict().toString()));
                comboBoxProvince.getSelectionModel().select((atm.getAddress().getProvince().toString()));
                toggleActive.setSelected(atm.getStatus());
            }
        });
    }

    @FXML
    void checkValueMoneyText(KeyEvent event) {

        // ---- force the textField money storage to be numeric only
        if (!textMoneyStorage.getText().matches("\\d*")) {
            textMoneyStorage.setText(textMoneyStorage.getText().replaceAll("[^\\d]", ""));
        }

    }
}