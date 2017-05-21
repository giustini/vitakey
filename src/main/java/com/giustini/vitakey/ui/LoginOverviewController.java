package com.giustini.vitakey.ui;

import com.giustini.vitakey.MainController;
import com.giustini.vitakey.model.Login;
import com.giustini.vitakey.utils.LoginReader;
import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.scene.paint.Color;

public class LoginOverviewController {

    @FXML
    private TextField loginField;
    @FXML
    private PasswordField passwdField;

    @FXML
    private Label errorLabel;

    private MainController mainController;

    public void setMainController(MainController mainController) {
        this.mainController = mainController;
    }

    @FXML
    public void handleOK() {
        if (validate(loginField.getText(), passwdField.getText())) {
            mainController.setPassPhrase(passwdField.getText());
            mainController.initAccountLayoutOverview();
        } else {
            errorLabel.setTextFill(Color.web("#FF0000"));
            errorLabel.setText("Contraseña\nincorrecta");
        }
    }

    private boolean validate(String loginInput, String passwdInput) {
        Login login = LoginReader.readLogin(passwdInput);
        return loginInput.equals(login.getLogin()) && passwdInput.equals(login.getPasswd());
    }

}
