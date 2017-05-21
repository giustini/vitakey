package com.giustini.vitakey.ui;

import com.giustini.vitakey.MainController;
import com.giustini.vitakey.utils.AccountsDataFileUtil;
import javafx.fxml.FXML;
import javafx.stage.FileChooser;
import org.controlsfx.dialog.Dialogs;

import java.io.File;

public class RootLayoutController {

    private MainController mainController;

    public void setMainController(MainController mainController) {
        this.mainController = mainController;
    }

    @FXML
    private void handleNew() {
        mainController.getAccountData().clear();
        mainController.getPrimaryStage().setTitle(AccountsDataFileUtil.setFile(null));
    }

    @FXML
    private void handleOpen() {
        FileChooser fileChooser = new FileChooser();

        FileChooser.ExtensionFilter extFilter = new FileChooser.ExtensionFilter(
                "XML files (*.xml)", "*.xml");
        fileChooser.getExtensionFilters().add(extFilter);

        File file = fileChooser.showOpenDialog(mainController.getPrimaryStage());

        if (file != null) {
            mainController.loadAccountDataFromFile(file);
        }
    }

    @FXML
    private void handleSave() {
        File accountFile = AccountsDataFileUtil.getFile();
        if (accountFile != null) {
            mainController.saveAccountDataToFile(accountFile);
        } else {
            handleSaveAs();
        }
    }

    @FXML
    private void handleSaveAs() {
        FileChooser fileChooser = new FileChooser();

        FileChooser.ExtensionFilter extFilter =
                new FileChooser.ExtensionFilter("XML files (*.xml)", "*.xml");
        fileChooser.getExtensionFilters().add(extFilter);

        File file = fileChooser.showSaveDialog(mainController.getPrimaryStage());

        if (file != null) {
            // Make sure it has the correct extension
            if (!file.getPath().endsWith(".xml")) {
                file = new File(file.getPath() + ".xml");
            }
            mainController.saveAccountDataToFile(file);
        }
    }

    @FXML
    private void handleAbout() {
        Dialogs.create()
                .title("Vitakey")
                .masthead("Acerca de...")
                .message("Vitakey, versión 1.0 beta\n"
                        + "Copyright \u00a9 2017 Giustini Apps. Todos los derechos reservados"
                        + "\nAutor: Andrés Giustini (http://github.com/giustini)")
                .showInformation();
    }

    @FXML
    private void handleExit() {
        System.exit(0);
    }
}