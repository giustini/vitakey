package com.giustini.vitakey.utils;

import org.junit.Test;

import javax.crypto.KeyGenerator;
import javax.crypto.SecretKey;
import java.security.NoSuchAlgorithmException;

import static org.junit.Assert.assertTrue;

public class StringEncrypterTests {

    @Test
    public void desEncryptDecryptTest() throws NoSuchAlgorithmException {

        String stringToEncrypt = "password";

        SecretKey desKey = KeyGenerator.getInstance("DES").generateKey();
        Encrypter desEncrypter = new Encrypter(desKey, desKey.getAlgorithm());

        String encrypted = desEncrypter.encrypt(stringToEncrypt);

        String decrypted = desEncrypter.decrypt(encrypted);

        assertTrue(stringToEncrypt.equals(decrypted));
    }

    @Test
    public void passPhraseEncryptDecryptTest() {

        String stringToEncrypt = "password";
        String passPhrase = "passPhrase";

        Encrypter passPhraseEncrypter = new Encrypter(passPhrase);

        String encrypted = passPhraseEncrypter.encrypt(stringToEncrypt);

        String decrypted = passPhraseEncrypter.decrypt(encrypted);

        assertTrue(stringToEncrypt.equals(decrypted));
    }
}
