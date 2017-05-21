package com.giustini.vitakey.ui;

import com.giustini.vitakey.MainController;
import com.giustini.vitakey.model.Account;
import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import org.controlsfx.dialog.Dialogs;

public class AccountOverviewController {

    @FXML
    private TableView<Account> accountTable;
    @FXML
    private TableColumn<Account, String> accountNameColumn;

    @FXML
    private Label nameLabel;
    @FXML
    private Label urlLabel;
    @FXML
    private Label loginLabel;
    @FXML
    private Label passwdLabel;

    private MainController mainController;

    public AccountOverviewController() {
    }

    @FXML
    private void initialize() {
        accountNameColumn.setCellValueFactory(CellData -> CellData.getValue().nameProperty());

        showAccountDetails(null);

        accountTable.getSelectionModel().selectedItemProperty().addListener(
                (observable, oldValue, newValue) -> showAccountDetails(newValue));
    }

    public void setMainController(MainController mainController) {
        this.mainController = mainController;

        accountTable.setItems(mainController.getAccountData());
    }

    @FXML
    private void handleShowPasswd() {
        Account selectedAccount = accountTable.getSelectionModel().getSelectedItem();
        passwdLabel.setText(selectedAccount.getPasswd());
    }

    @FXML
    private void handleNewAccount() {
        Account tempAccount = new Account();
        boolean okClicked = mainController.showAccountEditDialog(tempAccount);
        if (okClicked) {
            mainController.getAccountData().add(tempAccount);
        }
    }

    @FXML
    private void handleEditAccount() {
        Account selectedAccount = accountTable.getSelectionModel().getSelectedItem();
        if (selectedAccount != null) {
            boolean okClicked = mainController.showAccountEditDialog(selectedAccount);
            if (okClicked) {
                showAccountDetails(selectedAccount);
            }

        } else {
            // Nothing selected.
            Dialogs.create()
                    .title("Sin selección")
                    .masthead("Ninguna cuenta seleccionada")
                    .message("Por favor, selecciona la cuenta que quieras editar.")
                    .showWarning();
        }
    }

    private void showAccountDetails(Account account) {
        if (account != null) {
            nameLabel.setText(account.getName());
            urlLabel.setText(account.getURL());
            loginLabel.setText(account.getLogin());
            passwdLabel.setText("************");
        } else {
            nameLabel.setText("");
            urlLabel.setText("");
            loginLabel.setText("");
            passwdLabel.setText("");
        }
    }

    @FXML
    private void handleDeleteAccount() {
        int selectedIndex = accountTable.getSelectionModel().getSelectedIndex();
        if (selectedIndex >= 0) {
            boolean okClicked = mainController.showConfirmDeleteDialog();
            if (okClicked) {
                accountTable.getItems().remove(selectedIndex);
            }
        } else {
            Dialogs.create()
                    .title("Sin selección")
                    .masthead("Ninguna cuenta seleccionada")
                    .message("Por favor, selecciona la cuenta que quieras eliminar.")
                    .showWarning();
        }
    }

}
