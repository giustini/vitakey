package com.giustini.vitakey.utils;

import org.apache.commons.codec.binary.Base64;

import javax.crypto.*;
import javax.crypto.spec.PBEKeySpec;
import javax.crypto.spec.PBEParameterSpec;
import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.security.InvalidAlgorithmParameterException;
import java.security.InvalidKeyException;
import java.security.NoSuchAlgorithmException;
import java.security.spec.AlgorithmParameterSpec;
import java.security.spec.InvalidKeySpecException;
import java.security.spec.KeySpec;

class Encrypter {

    private Cipher ecipher;
    private Cipher dcipher;

    Encrypter(SecretKey key, String algorithm) {

        try {

            this.ecipher = Cipher.getInstance(algorithm);
            this.dcipher = Cipher.getInstance(algorithm);

            this.ecipher.init(Cipher.ENCRYPT_MODE, key);
            this.dcipher.init(Cipher.DECRYPT_MODE, key);

        } catch (NoSuchPaddingException | NoSuchAlgorithmException | InvalidKeyException e) {
        }
    }

    Encrypter(String passPhrase) {

        try {

            byte[] salt = {
                    (byte) 0xA9, (byte) 0x9B, (byte) 0xC8, (byte) 0x32,
                    (byte) 0x56, (byte) 0x34, (byte) 0xE3, (byte) 0x03
            };

            int iterationCount = 19;

            KeySpec keySpec = new PBEKeySpec(passPhrase.toCharArray(), salt, iterationCount);
            SecretKey key = SecretKeyFactory.getInstance("PBEWithMD5AndDES").generateSecret(keySpec);

            this.ecipher = Cipher.getInstance(key.getAlgorithm());
            this.dcipher = Cipher.getInstance(key.getAlgorithm());

            AlgorithmParameterSpec paramSpec = new PBEParameterSpec(salt, iterationCount);

            this.ecipher.init(Cipher.ENCRYPT_MODE, key, paramSpec);
            this.dcipher.init(Cipher.DECRYPT_MODE, key, paramSpec);

        } catch (InvalidAlgorithmParameterException | InvalidKeySpecException |
                NoSuchPaddingException | NoSuchAlgorithmException | InvalidKeyException e) {
        }
    }

    String encrypt(String str) {

        String encoded = null;

        try {

            byte[] utf8 = str.getBytes("UTF8");
            byte[] enc = ecipher.doFinal(utf8);

            encoded = Base64.encodeBase64String(enc);

        } catch (BadPaddingException | IllegalBlockSizeException | UnsupportedEncodingException e) {
        }

        return encoded;
    }

    String decrypt(String str) {

        String decoded = null;

        try {

            byte[] dec = Base64.decodeBase64(str);
            byte[] utf8 = dcipher.doFinal(dec);

            decoded = new String(utf8, "UTF8");

        } catch (BadPaddingException | IllegalBlockSizeException | IOException e) {
        }

        return decoded;
    }

}