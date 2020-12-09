package home;

import javafx.collections.ObservableList;
import javafx.event.Event;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.VBox;

import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;

import com.jfoenix.controls.JFXButton;
import com.jfoenix.controls.JFXDrawer;
import com.jfoenix.controls.JFXHamburger;

import home.scene.OS.ctrlOS;
import home.scene.cropImage.ctrlCropImage;
import home.scene.lobby.ctrlLobby;
import home.scene.navBar.ctrlNavBar;

public class ctrlHome implements Initializable {

    @FXML
    private BorderPane root;

    @FXML
    private AnchorPane centerPane;

    @FXML
    private BorderPane currentPane;

    @FXML
    private JFXHamburger hamburger;

    @FXML
    private JFXDrawer navBarPane;

    // Declaring ctrlNavBar globally
    ctrlNavBar insCtrlNav = new ctrlNavBar();

    // Get all buttons from ctrlNavBar
    ObservableList<JFXButton> navBarButtons;

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        initDrawer();
        ctrlNavBar.isAvtUpdatedProperty().addListener((observable, oldValue, newValue) -> {
            ctrlLobby insCtrlLobby = new ctrlLobby();
            loadFXML(getClass().getResource("/scene/lobby.fxml"), insCtrlLobby);
            ObservableList<JFXButton> lobbyButtons = insCtrlLobby.fetchAllButtons();
            sideSceneFromLobby(lobbyButtons);
            ctrlNavBar.isAvtUpdatedProperty().set(false);
        });
        sideSceneFromNavBar();
    }

    private void sideSceneFromNavBar() {
        for (JFXButton button : navBarButtons) {
            switch (button.getId()) {
                case "changeAvtBtn":
                    button.setOnAction(actionEvent -> {
                        loadFXML(getClass().getResource("scene/cropImage/cropImage.fxml"), new ctrlCropImage());
                    });
                    break;
                case "changeInfoBtn":
                    button.setOnAction(actionEvent -> {
                        // load scene for change info
                    });
                    break;
                case "seeMoreBtn":
                    button.setOnAction(actionEvent -> {
                        // load scene for see more
                    });
                    break;
                case "homeBtn":
                    button.setOnAction(actionEvent -> {
                        ctrlLobby insCtrlLobby = new ctrlLobby();
                        loadFXML(getClass().getResource("scene/lobby/lobby.fxml"), insCtrlLobby);
                        ObservableList<JFXButton> lobbyButtons = insCtrlLobby.fetchAllButtons();
                        sideSceneFromLobby(lobbyButtons);
                    });
                    break;
                case "settingBtn":
                    button.setOnAction(actionEvent -> {
                        // load scene for setting
                    });
                    break;
                case "signOutBtn":
                    button.setOnAction(actionEvent -> {
                        // action for sign out
                    });
                    break;
            }
        }
    }

    private void sideSceneFromLobby(ObservableList<JFXButton> lobbyButtons) {
        for (JFXButton button : lobbyButtons) {
            switch (button.getId()) {
                case "navAtmBtn":
                    button.setOnAction(actionEvent -> {
                        loadFXML(getClass().getResource("scene/OS/os.fxml"), new ctrlOS());
                    });
                    break;
                case "navDataBaseBtn":
                    button.setOnAction(actionEvent -> {
                        // loadFXML(getClass().getResource(""), new ;
                    });
                    break;
                case "navSupplierBtn":
                    button.setOnAction(actionEvent -> {
                        // loadFXML(getClass().getResource(""), new ;
                    });
                    break;
                case "navReportProblemBtn":
                    button.setOnAction(actionEvent -> {
                        // loadFXML(getClass().getResource(""), new ;
                    });
                    break;
                case "navAccountBtn":
                    button.setOnAction(actionEvent -> {
                        // loadFXML(getClass().getResource(""), new ;
                    });
                    break;
                case "navMessageBtn":
                    button.setOnAction(actionEvent -> {
                        // loadFXML(getClass().getResource(""), new ;
                    });
                    break;
                case "navPeriodicReportBtn":
                    button.setOnAction(actionEvent -> {
                        // loadFXML(getClass().getResource(""), new ;
                    });
                    break;
                case "navUnusualReportBtn":
                    button.setOnAction(actionEvent -> {
                        // loadFXML(getClass().getResource(""), new ;
                    });
                    break;
            }
        }
    }

    private void loadFXML(URL url, Object ctrl) {
        try {
            FXMLLoader loader = new FXMLLoader(url);
            loader.setController(ctrl);
            currentPane.setCenter(loader.load());
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private void initDrawer() {
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("scene/navBar/navBar.fxml"));
            loader.setController(insCtrlNav);
            VBox navBarBox = loader.load();
            navBarPane.setSidePane(navBarBox);
            navBarButtons = insCtrlNav.fetchAllButtons();
            navBarPane.setMinWidth(0);
        } catch (IOException e) {
            e.printStackTrace();
        }

        hamburger.addEventHandler(MouseEvent.MOUSE_CLICKED, (Event event) -> {
            if (navBarPane.isClosed())
                navBarPane.open();
        });
    }

    @FXML
    void handleExitDrawer(MouseEvent event) {
        if (navBarPane.isOpened()) {
            navBarPane.close();
        }
    }

}
