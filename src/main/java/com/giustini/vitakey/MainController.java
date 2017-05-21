package com.giustini.vitakey;

import com.giustini.vitakey.model.Account;
import com.giustini.vitakey.model.AccountListWrapper;
import com.giustini.vitakey.model.Login;
import com.giustini.vitakey.ui.*;
import com.giustini.vitakey.utils.AccountDataEncrypter;
import com.giustini.vitakey.utils.AccountsDataFileUtil;
import com.giustini.vitakey.utils.LoginReader;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.image.Image;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.BorderPane;
import javafx.stage.Modality;
import javafx.stage.Stage;
import org.controlsfx.dialog.Dialogs;

import javax.xml.bind.JAXBContext;
import javax.xml.bind.Marshaller;
import javax.xml.bind.Unmarshaller;
import java.io.File;
import java.io.IOException;

public class MainController {

    private Stage primaryStage;
    private BorderPane rootLayout;
    private BorderPane loginLayout;

    private String passPhrase;

    private static final String FXML_FILES_PATH = "ui/fxml/";

    public ObservableList<Account> accountData = FXCollections.observableArrayList();

    public MainController() {
        // Sample accounts
        accountData.add(new Account("Facebook", "https://www.facebook.com", "loginf", "passwordf"));
        accountData.add(new Account("Gmail", "https://www.gmail.com", "loging", "passwordg"));
        accountData.add(new Account("Dropbox", "https://www.dropbox.com/es", "logind", "passwordd"));
    }

    public ObservableList<Account> getAccountData() {
        return accountData;
    }

    public Stage getPrimaryStage() {
        return primaryStage;
    }

    public void setPassPhrase(String passPhrase) {
        this.passPhrase = passPhrase;
    }

    public void init(Stage primaryStage) {
        this.primaryStage = primaryStage;
        this.primaryStage.setTitle("Vitakey");
        this.primaryStage.getIcons().add(new Image("/images/lock3_icon_32.png"));
        this.primaryStage.setResizable(false);

        if (isFirstAccess()) {
            Login login = showSigninDialog();
            if (login != null) {
                this.passPhrase = login.getPasswd();
                LoginReader.writeLogin(login, this.passPhrase);
            } else {
                System.exit(0);
            }

            // If it is the first access, log in automatically.
            initAccountLayoutOverview();

        } else {
            initLoginLayoutOverview();
        }
    }

    public void initAccountLayoutOverview() {
        initRootLayout();
        showAccountOverview();
    }

    public void initLoginLayoutOverview() {
        initLoginLayout();
        showLoginOverview();
    }

    private void initRootLayout() {

        try {
            FXMLLoader loader = new FXMLLoader();
            loader.setLocation(MainController.class.getResource(FXML_FILES_PATH + "RootLayout.fxml"));
            rootLayout = (BorderPane) loader.load();

            Scene scene = new Scene(rootLayout);
            primaryStage.close();
            primaryStage.setResizable(true);
            primaryStage.setScene(scene);
            primaryStage.centerOnScreen();
            primaryStage.show();
            RootLayoutController controller = loader.getController();
            controller.setMainController(this);

            primaryStage.show();
        } catch (IOException e) {
            e.printStackTrace();
        }

        // Try to load last opened account file.
        File file = AccountsDataFileUtil.getFile();
        if (file != null) {
            loadAccountDataFromFile(file);
        }
    }

    private void showAccountOverview() {
        try {
            FXMLLoader loader = new FXMLLoader();
            loader.setLocation(MainController.class.getResource(FXML_FILES_PATH + "AccountOverview.fxml"));
            AnchorPane accountOverview = (AnchorPane) loader.load();

            rootLayout.setCenter(accountOverview);

            AccountOverviewController controller = loader.getController();
            controller.setMainController(this);

        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private void initLoginLayout() {

        try {
            FXMLLoader loader = new FXMLLoader();
            loader.setLocation(MainController.class.getResource(FXML_FILES_PATH + "LoginLayout.fxml"));
            loginLayout = (BorderPane) loader.load();

            Scene scene = new Scene(loginLayout);
            primaryStage.setScene(scene);

            primaryStage.show();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private void showLoginOverview() {
        try {
            FXMLLoader loader = new FXMLLoader();
            loader.setLocation(MainController.class.getResource(FXML_FILES_PATH + "LoginOverview.fxml"));
            AnchorPane loginOverview = (AnchorPane) loader.load();

            loginLayout.setCenter(loginOverview);

            LoginOverviewController controller = loader.getController();
            controller.setMainController(this);

        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public boolean showAccountEditDialog(Account account) {
        try {
            FXMLLoader loader = new FXMLLoader();
            loader.setLocation(MainController.class.getResource(FXML_FILES_PATH + "AccountEditDialog.fxml"));
            AnchorPane page = (AnchorPane) loader.load();

            Stage dialogStage = new Stage();
            dialogStage.setTitle("Información de la cuenta");
            dialogStage.initModality(Modality.WINDOW_MODAL);
            dialogStage.initOwner(primaryStage);
            dialogStage.getIcons().add(new Image("/images/edit_32.png"));
            dialogStage.setResizable(false);
            Scene scene = new Scene(page);
            dialogStage.setScene(scene);

            AccountEditDialogController controller = loader.getController();
            controller.setDialogStage(dialogStage);
            controller.setAccount(account);

            dialogStage.showAndWait();

            return controller.isOkClicked();
        } catch (IOException e) {
            e.printStackTrace();
            return false;
        }
    }

    public boolean showConfirmDeleteDialog() {
        try {
            FXMLLoader loader = new FXMLLoader();
            loader.setLocation(MainController.class.getResource(FXML_FILES_PATH + "ConfirmDeleteDialog.fxml"));
            AnchorPane page = (AnchorPane) loader.load();

            Stage dialogStage = new Stage();
            dialogStage.setTitle("Eliminar cuenta");
            dialogStage.initModality(Modality.WINDOW_MODAL);
            dialogStage.initOwner(primaryStage);
            dialogStage.getIcons().add(new Image("/images/delete_32.png"));
            dialogStage.setResizable(false);
            Scene scene = new Scene(page);
            dialogStage.setScene(scene);

            ConfirmDeleteDialogController controller = loader.getController();
            controller.setDialogStage(dialogStage);

            dialogStage.showAndWait();

            return controller.isOkClicked();
        } catch (IOException e) {
            e.printStackTrace();
            return false;
        }
    }

    public Login showSigninDialog() {
        try {
            FXMLLoader loader = new FXMLLoader();
            loader.setLocation(MainController.class.getResource(FXML_FILES_PATH + "SigninDialog.fxml"));
            AnchorPane page = (AnchorPane) loader.load();

            Stage dialogStage = new Stage();
            dialogStage.setTitle("Registro");
            dialogStage.initModality(Modality.WINDOW_MODAL);
            dialogStage.initOwner(primaryStage);
            dialogStage.getIcons().add(new Image("/images/signin_32.png"));
            dialogStage.setResizable(false);
            Scene scene = new Scene(page);
            dialogStage.setScene(scene);

            SigninDialogController controller = loader.getController();
            controller.setDialogStage(dialogStage);

            dialogStage.showAndWait();

            return controller.getLogin();
        } catch (IOException e) {
            e.printStackTrace();
            return null;
        }
    }

    public void loadAccountDataFromFile(File file) {
        try {
            JAXBContext context = JAXBContext
                    .newInstance(AccountListWrapper.class);
            Unmarshaller um = context.createUnmarshaller();

            // Reading XML from the file and unmarshalling.
            AccountListWrapper wrapper = (AccountListWrapper) um.unmarshal(file);

            // Decrypting our account data
            String passPhrase = LoginReader.readLogin(this.passPhrase).getPasswd();
            AccountDataEncrypter encrypter = new AccountDataEncrypter(passPhrase);
            wrapper.setAccounts(encrypter.decryptLoginPasswd(wrapper.getAccounts()));

            accountData.clear();
            accountData.addAll(wrapper.getAccounts());

            // Save the file path to the registry.
            primaryStage.setTitle(AccountsDataFileUtil.setFile(file));

        } catch (Exception e) {
            Dialogs.create()
                    .title("Error")
                    .masthead("No se puede leer el archivo:\n" + file.getPath())
                    .showException(e);
        }
    }

    public void saveAccountDataToFile(File file) {
        try {
            JAXBContext context = JAXBContext
                    .newInstance(AccountListWrapper.class);
            Marshaller m = context.createMarshaller();
            m.setProperty(Marshaller.JAXB_FORMATTED_OUTPUT, true);

            // Encrypting our account data
            String passPhrase = LoginReader.readLogin(this.passPhrase).getPasswd();
            AccountDataEncrypter encrypter = new AccountDataEncrypter(passPhrase);
            encrypter.encryptLoginPasswd(accountData);

            // Wrapping our encrypted account data.
            AccountListWrapper wrapper = new AccountListWrapper();
            wrapper.setAccounts(accountData);

            // Marshalling and saving XML to the file.
            m.marshal(wrapper, file);
            encrypter.decryptLoginPasswd(accountData);

            // Save the file path to the registry.
            primaryStage.setTitle(AccountsDataFileUtil.setFile(file));

        } catch (Exception e) { // catches ANY exception
            Dialogs.create().title("Error")
                    .masthead("No se puede escribir en el archivo:\n" + file.getPath())
                    .showException(e);
        }
    }

    private boolean isFirstAccess() {
        return LoginReader.isFirstAccess();
    }

}
