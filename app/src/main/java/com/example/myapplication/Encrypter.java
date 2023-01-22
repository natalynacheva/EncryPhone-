package com.example.myapplication;

public class Encrypter {
    MasterKey masterKey = new MasterKey.Builder(context)
            .setKeyScheme(MasterKey.KeyScheme.AES256_GCM)
            .build();

    File file = new File(context.getFilesDir(), "secret_data");
    EncryptedFile encryptedFile = EncryptedFile.Builder(
            context,
            file,
            masterKey,
            EncryptedFile.FileEncryptionScheme.AES256_GCM_HKDF_4KB
    ).build();
}
