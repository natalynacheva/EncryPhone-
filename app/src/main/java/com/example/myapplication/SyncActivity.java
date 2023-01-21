package com.example.myapplication;

import android.content.Intent;
import android.database.Cursor;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.util.Log;
import android.widget.Button;

import androidx.appcompat.app.AppCompatActivity;

import java.io.IOException;
public class SyncActivity extends AppCompatActivity {
    private static final int FILE_CODE = 1;
    Button browseButton, nextButton;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.syncing_data);

        browseButton = findViewById(R.id.browse_files);
        nextButton = findViewById(R.id.proceed_button);


        browseButton.setOnClickListener(arg0 -> {

            Intent i = new Intent(
                    Intent.ACTION_PICK,
                    MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
            Log.i("click", "button is clicked.");
            //nextButton.setOnClickListener(view -> syncData());
            startActivityForResult(i, FILE_CODE);
        });
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        Uri uri;
            if (data != null) {
                // select image and log file path
                uri = data.getData();
                String[] filePathColumn = {MediaStore.Images.Media.DATA};
                // Perform operations on the document using its URI.
                Cursor cursor = getContentResolver().query(uri,
                        filePathColumn, null, null, null);
                cursor.moveToFirst();
                int columnIndex = cursor.getColumnIndex(filePathColumn[0]);
                String picturePath = cursor.getString(columnIndex);
                cursor.close();
                Log.i("files selected", "files successfully saved.");
                //saveFileToDrive(uri);
                try {
                    String id = UploadBasic.uploadBasic();
                    Log.i("ID collected", "id:" + id);
                } catch (IOException e) {
                    Log.i("error files selected", "files unsuccessfully saved.");
                    throw new RuntimeException(e);
                }
            }
            }
        }
