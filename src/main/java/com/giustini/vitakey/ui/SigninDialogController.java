package com.giustini.vitakey.ui;

import com.giustini.vitakey.model.Login;
import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.scene.paint.Color;
import javafx.stage.Stage;

public class SigninDialogController {

    @FXML
    private TextField loginField;
    @FXML
    private PasswordField passwdField;
    @FXML
    private PasswordField repasswdField;

    @FXML
    private Label errorLabel;

    private Stage dialogStage;
    private boolean okClicked = false;

    @FXML
    private void initialize() {
    }

    public void setDialogStage(Stage dialogStage) {
        this.dialogStage = dialogStage;
    }

    public boolean isOkClicked() {
        return okClicked;
    }

    public Login getLogin() {
        if (okClicked) {
            return new Login(loginField.getText(), passwdField.getText());
        } else return null;
    }

    @FXML
    private void handleOk() {

        if (!validateFields()) {
            errorLabel.setTextFill(Color.web("#FF0000"));
            errorLabel.setText("Rellena los campos vacíos");
        } else {
            if (validatePasswd(passwdField.getText(), repasswdField.getText())) {
                okClicked = true;
                dialogStage.close();
            } else {
                errorLabel.setTextFill(Color.web("#FF0000"));
                errorLabel.setText("Las contraseñas\nno coinciden");
            }
        }
    }

    private boolean validateFields() {
        return !(loginField.getText() == null || loginField.getText().length() == 0 ||
                passwdField.getText() == null || passwdField.getText().length() == 0 ||
                repasswdField.getText() == null || repasswdField.getText().length() == 0);
    }

    private boolean validatePasswd(String passwdInput, String repasswdInput) {
        return passwdInput.equals(repasswdInput);
    }

}
