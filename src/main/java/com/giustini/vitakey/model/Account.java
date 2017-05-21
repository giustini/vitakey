package com.giustini.vitakey.model;

import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;

public class Account {

    private final StringProperty name;
    private final StringProperty url;
    private final StringProperty login;
    private final StringProperty passwd;

    public Account() {
        this(null, null, null, null);
    }

    public Account(String name, String url, String login, String passwd) {
        this.name = new SimpleStringProperty(name);
        this.url = new SimpleStringProperty(url);
        this.login = new SimpleStringProperty(login);
        this.passwd = new SimpleStringProperty(passwd);
    }

    public String getName() {
        return name.get();
    }

    public void setName(String name) {
        this.name.set(name);
    }

    public StringProperty nameProperty() {
        return name;
    }

    public String getURL() {
        return url.get();
    }

    public void setURL(String url) {
        this.url.set(url);
    }

    public StringProperty urlProperty() {
        return url;
    }

    public String getLogin() {
        return login.get();
    }

    public void setLogin(String login) {
        this.login.set(login);
    }

    public StringProperty urlLogin() {
        return login;
    }

    public String getPasswd() {
        return passwd.get();
    }

    public void setPasswd(String passwd) {
        this.passwd.set(passwd);
    }

    public StringProperty passwdProperty() {
        return passwd;
    }

}
