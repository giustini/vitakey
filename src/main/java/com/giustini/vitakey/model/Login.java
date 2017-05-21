package com.giustini.vitakey.model;

import javax.xml.bind.annotation.XmlRootElement;

@XmlRootElement
public class Login {

    private String login;
    private String passwd;

    public Login() {
        this(null, null);
    }

    public Login(String login, String passwd) {
        this.login = login;
        this.passwd = passwd;
    }

    public String getLogin() {
        return this.login;
    }

    public void setLogin(String login) {
        this.login = login;
    }

    public String getPasswd() {
        return this.passwd;
    }

    public void setPasswd(String passwd) {
        this.passwd = passwd;
    }

}
