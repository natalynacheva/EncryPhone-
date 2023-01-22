package com.example.myapplication;


import android.os.Build;
import android.util.Log;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.security.InvalidKeyException;
import java.security.NoSuchAlgorithmException;
import java.util.Base64;

import javax.crypto.Cipher;
import javax.crypto.CipherOutputStream;
import javax.crypto.KeyGenerator;
import javax.crypto.NoSuchPaddingException;
import javax.crypto.SecretKey;

public class Encrypter {
    static SecretKey encrypt(String PathWithoutExtension) throws IOException, NoSuchAlgorithmException, NoSuchPaddingException, InvalidKeyException {

        SecretKey secretKey = generateAESKey();

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            assert secretKey != null;
            Log.d("Encryption", "AES key : " + Base64.getEncoder().encodeToString(secretKey.getEncoded()));
        }

        Cipher cipher = Cipher.getInstance("AES/CBC/PKCS5Padding");
            cipher.init(Cipher.ENCRYPT_MODE, secretKey);

            // Input file
            File input = new File(PathWithoutExtension + ".jpeg");
            FileInputStream fileInputStream = new FileInputStream(input);

            // Output file
            File output = new File(PathWithoutExtension + ".enc");
            FileOutputStream fileOutputStream = new FileOutputStream(output);

            CipherOutputStream cipherOutputStream = new CipherOutputStream(fileOutputStream, cipher);

            // Writes the ciphertext in the output
            int b;
            byte[] d = new byte[8];
            while ((b = fileInputStream.read(d)) != -1) {
                cipherOutputStream.write(d, 0, b);
            }

            // Flushes and closes streams
            cipherOutputStream.flush();
            cipherOutputStream.close();
            fileInputStream.close();

            return secretKey;
        }

    private static SecretKey generateAESKey() {

        // Initialisation of the keyGenerator to generate a 128bit key
        try {
            KeyGenerator keyGenerator = KeyGenerator.getInstance("AES");
            keyGenerator.init(128);
            return (keyGenerator.generateKey());
        } catch (NoSuchAlgorithmException e) {
           // Toast.makeText(AppContext.getAppContext(), "AES key could not be generated", Toast.LENGTH_SHORT).show();
        }
        return null;
    }

}


