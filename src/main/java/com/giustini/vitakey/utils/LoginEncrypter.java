package com.giustini.vitakey.utils;

import com.giustini.vitakey.model.Login;

public class LoginEncrypter {

    private Encrypter encrypter;

    public LoginEncrypter(String passPhrase) {
        this.encrypter = new Encrypter(passPhrase);
    }

    public Login encryptLogin(Login decryptedLogin) {

        Login encryptedLogin = new Login();

        encryptedLogin.setLogin(this.encrypter.encrypt(decryptedLogin.getLogin()));
        encryptedLogin.setPasswd(this.encrypter.encrypt(decryptedLogin.getPasswd()));

        return encryptedLogin;
    }

    public Login decryptLogin(Login encryptedLogin) {

        Login decryptedLogin = new Login();

        decryptedLogin.setLogin(this.encrypter.decrypt(encryptedLogin.getLogin()));
        decryptedLogin.setPasswd(this.encrypter.decrypt(encryptedLogin.getPasswd()));

        return decryptedLogin;
    }
}
