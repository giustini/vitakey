package com.giustini.vitakey.ui;

import javafx.fxml.FXML;
import org.controlsfx.dialog.Dialogs;

public class LoginLayoutController {

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
