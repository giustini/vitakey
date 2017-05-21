package com.giustini.vitakey.utils;

import com.giustini.vitakey.model.Login;

import javax.xml.bind.JAXBContext;
import javax.xml.bind.JAXBException;
import javax.xml.bind.Marshaller;
import javax.xml.bind.Unmarshaller;
import java.io.File;

public class LoginReader {

    // DEPLOYMENT login file path
    //private static File loginFile = new File("xml/login.xml");

    // DEVELOPMENT login file path
    private static File loginFile = new File("src/main/resources/xml/login.xml");

    public static boolean isFirstAccess() {

        Login login = null;

        try {

            JAXBContext context = JAXBContext.newInstance(Login.class);
            Unmarshaller um = context.createUnmarshaller();
            login = (Login) um.unmarshal(loginFile);

        } catch (JAXBException e) {
            e.printStackTrace();
        }

        return login == null || login.getLogin() == null || login.getPasswd() == null;
    }

    public static Login readLogin(String passPhrase) {

        LoginEncrypter encrypter = new LoginEncrypter(passPhrase);
        Login login = null;

        try {

            JAXBContext context = JAXBContext.newInstance(Login.class);
            Unmarshaller um = context.createUnmarshaller();
            login = encrypter.decryptLogin((Login) um.unmarshal(loginFile));

        } catch (JAXBException e) {
            e.printStackTrace();
        }

        return login;
    }

    public static void writeLogin(Login login, String passPhrase) {

        LoginEncrypter encrypter = new LoginEncrypter(passPhrase);

        try {

            JAXBContext context = JAXBContext.newInstance(Login.class);
            Marshaller m = context.createMarshaller();
            m.setProperty(Marshaller.JAXB_FORMATTED_OUTPUT, true);
            m.marshal(encrypter.encryptLogin(login), loginFile);

        } catch (JAXBException e) {
            e.printStackTrace();
        }
    }

}
