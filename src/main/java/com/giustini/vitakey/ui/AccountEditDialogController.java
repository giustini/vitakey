package com.giustini.vitakey.ui;

import com.giustini.vitakey.model.Account;
import javafx.fxml.FXML;
import javafx.scene.control.TextField;
import javafx.stage.Stage;
import org.controlsfx.dialog.Dialogs;

public class AccountEditDialogController {

    @FXML
    private TextField nameField;
    @FXML
    private TextField urlField;
    @FXML
    private TextField loginField;
    @FXML
    private TextField passwdField;

    private Stage dialogStage;
    private Account account;
    private boolean okClicked = false;

    @FXML
    private void initialize() {
    }

    public void setDialogStage(Stage dialogStage) {
        this.dialogStage = dialogStage;
    }

    public void setAccount(Account account) {
        this.account = account;

        nameField.setText(account.getName());
        urlField.setText(account.getURL());
        loginField.setText(account.getLogin());
        passwdField.setText(account.getPasswd());
    }

    public boolean isOkClicked() {
        return okClicked;
    }

    @FXML
    private void handleOk() {
        if (isInputValid()) {
            account.setName(nameField.getText());
            account.setURL(urlField.getText());
            account.setLogin(loginField.getText());
            account.setPasswd(passwdField.getText());

            okClicked = true;
            dialogStage.close();
        }
    }

    @FXML
    private void handleCancel() {
        dialogStage.close();
    }

    private boolean isInputValid() {
        String errorMessage = "";

        if (nameField.getText() == null || nameField.getText().length() == 0) {
            errorMessage += "· Nombre del sitio\n";
        }

        if (urlField.getText() == null || urlField.getText().length() == 0) {
            errorMessage += "· URL del sitio\n";
        }

        if (loginField.getText() == null || loginField.getText().length() == 0) {
            errorMessage += "· Nombre de usuario\n";
        }

        if (passwdField.getText() == null || passwdField.getText().length() == 0) {
            errorMessage += "· Contraseña\n";
        }

        if (errorMessage.length() == 0) {
            return true;
        } else {
            Dialogs.create()
                    .title("Campos inválidos")
                    .masthead("Por favor, corrige los siguientes campos\ninválidos:")
                    .message(errorMessage)
                    .showError();
            return false;
        }
    }
}
