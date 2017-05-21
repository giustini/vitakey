package com.giustini.vitakey.utils;

import com.giustini.vitakey.MainController;

import java.io.File;
import java.util.prefs.Preferences;

public class AccountsDataFileUtil {

    public static File getFile() {
        Preferences prefs = Preferences.userNodeForPackage(MainController.class);
        String filePath = prefs.get("filePath", null);
        if (filePath != null) {
            return new File(filePath);
        } else {
            return null;
        }
    }

    public static String setFile(File file) {
        Preferences prefs = Preferences.userNodeForPackage(MainController.class);

        String title = "Vitakey";

        if (file != null) {
            prefs.put("filePath", file.getPath());

            title += " - " + file.getName();
        } else {
            prefs.remove("filePath");
        }

        return title;
    }
}
