package com.giustini.vitakey.ui;

import javafx.fxml.FXML;
import javafx.stage.Stage;

public class ConfirmDeleteDialogController {

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

    @FXML
    private void handleOk() {
        okClicked = true;
        dialogStage.close();
    }

    @FXML
    private void handleCancel() {
        dialogStage.close();
    }
}
