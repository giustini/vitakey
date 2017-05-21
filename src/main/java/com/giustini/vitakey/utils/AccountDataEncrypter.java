package com.giustini.vitakey.utils;

import com.giustini.vitakey.model.Account;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

import java.util.ArrayList;
import java.util.List;

public class AccountDataEncrypter {

    private Encrypter encrypter;

    public AccountDataEncrypter(String passPhrase) {
        this.encrypter = new Encrypter(passPhrase);
    }

    public ObservableList<Account> encryptLoginPasswd(ObservableList<Account> decryptedAccounts) {

        ObservableList<Account> encryptedAccounts = FXCollections.observableArrayList();
        encryptedAccounts.addAll(decryptedAccounts);

        for (Account a : encryptedAccounts) {
            a.setLogin(this.encrypter.encrypt(a.getLogin()));
            a.setPasswd(this.encrypter.encrypt(a.getPasswd()));
        }

        return encryptedAccounts;
    }

    public List<Account> decryptLoginPasswd(List<Account> encryptedAccounts) {

        List<Account> decryptedAccounts = new ArrayList<Account>();
        decryptedAccounts.addAll(encryptedAccounts);

        for (Account a : decryptedAccounts) {
            a.setLogin(this.encrypter.decrypt(a.getLogin()));
            a.setPasswd(this.encrypter.decrypt(a.getPasswd()));
        }

        return decryptedAccounts;
    }
}
