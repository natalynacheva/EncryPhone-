package com.example.myapplication;

import android.app.Activity;
import android.content.Intent;
import android.content.IntentSender;
import android.database.Cursor;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.util.Log;
import android.view.View;
import android.widget.Button;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import com.google.android.gms.auth.api.signin.GoogleSignIn;
import com.google.android.gms.drive.CreateFileActivityOptions;
import com.google.android.gms.drive.Drive;
import com.google.android.gms.drive.DriveClient;
import com.google.android.gms.drive.DriveContents;
import com.google.android.gms.drive.DriveResourceClient;
import com.google.android.gms.drive.MetadataChangeSet;
import com.google.android.gms.tasks.Continuation;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.Task;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.OutputStream;

public class SyncActivity extends AppCompatActivity {
    private static final int FILE_CODE = 1;
    Button browseButton, nextButton;
    private DriveResourceClient mDriveResourceClient;
    private DriveClient mDriveClient;

    private static final int REQUEST_CODE_CREATOR = 3;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.syncing_data);

        browseButton = findViewById(R.id.browse_files);
        nextButton = findViewById(R.id.proceed_button);


        browseButton.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View arg0) {

                Intent i = new Intent(
                        Intent.ACTION_PICK,
                        android.provider.MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
                //nextButton.setOnClickListener(view -> syncData());
                startActivityForResult(i, FILE_CODE);
            }
        });
    }

            @Override
            public void onActivityResult(int requestCode, int resultCode, Intent data) {
                super.onActivityResult(requestCode, resultCode, data);
                mDriveClient = Drive.getDriveClient(this, GoogleSignIn.getLastSignedInAccount(this));
                // Build a drive resource client.
                mDriveResourceClient =
                        Drive.getDriveResourceClient(this, GoogleSignIn.getLastSignedInAccount(this));

                if (requestCode == FILE_CODE && resultCode == Activity.RESULT_OK) {
                    //List<Uri> files = Utils.getSelectedFilesFromResult(data);
                    //for (Uri uri : files) {
                    //  File file = Utils.getFileForUri(uri);
                    // Do something with the result...
                    // Use the last signed in account here since it already have a Drive scope.
                    Log.i("files selected", "files successfully saved.");
                    //saveFileToDrive(file);
                    Uri uri = null;
                    if (data != null) {
                        uri = data.getData();
                        String[] filePathColumn = { MediaStore.Images.Media.DATA };
                        // Perform operations on the document using its URI.
                        Cursor cursor = getContentResolver().query(uri,
                                filePathColumn, null, null, null);
                        cursor.moveToFirst();
                        int columnIndex = cursor.getColumnIndex(filePathColumn[0]);
                        String picturePath = cursor.getString(columnIndex);
                        cursor.close();
                        saveFileToDrive(uri);
                    }

                }
            }

            /**
             * Create a new file and save it to Drive.
             */
            private void saveFileToDrive(Uri file) {
                // Start by creating a new contents, and setting a callback.
                Log.i("creating contents", "Creating new contents.");
                mDriveResourceClient
                        .createContents()
                        .continueWithTask(
                                new Continuation<DriveContents, Task<Void>>() {
                                    @Override
                                    public Task<Void> then(@NonNull Task<DriveContents> task) throws Exception {
                                        return createFileIntentSender(task.getResult(), file);
                                    }
                                })
                        .addOnFailureListener(
                                new OnFailureListener() {
                                    @Override
                                    public void onFailure(@NonNull Exception e) {
                                        Log.w("error", "Failed to create new contents.", e);
                                    }
                                });
            }

            /**
             * Creates an {@link IntentSender} to start a dialog activity with configured {@link
             * CreateFileActivityOptions} for user to create a new photo in Drive.
             */
            private Task<Void> createFileIntentSender(DriveContents driveContents, Uri file) {
                Log.i("client created", "New contents created.");
                // Get an output stream for the contents.
                OutputStream outputStream = driveContents.getOutputStream();
                // Write the bitmap data from it.
                ByteArrayOutputStream bitmapStream = new ByteArrayOutputStream();
                // file.compress(Bitmap.CompressFormat.PNG, 100, bitmapStream);
                try {
                    outputStream.write(bitmapStream.toByteArray());
                } catch (IOException e) {
                    Log.w("error", "Unable to write file contents.", e);
                }

                // Create the initial metadata - MIME type and title.
                // Note that the user will be able to change the title later.
                MetadataChangeSet metadataChangeSet =
                        new MetadataChangeSet.Builder()
                                .setMimeType("image/jpeg")
                                .setTitle("Android Photo.png")
                                .build();
                // Set up options to configure and display the create file activity.
                CreateFileActivityOptions createFileActivityOptions =
                        new CreateFileActivityOptions.Builder()
                                .setInitialMetadata(metadataChangeSet)
                                .setInitialDriveContents(driveContents)
                                .build();

                return mDriveClient
                        .newCreateFileActivityIntentSender(createFileActivityOptions)
                        .continueWith(
                                new Continuation<IntentSender, Void>() {
                                    @Override
                                    public Void then(@NonNull Task<IntentSender> task) throws Exception {
                                        startIntentSenderForResult(task.getResult(), REQUEST_CODE_CREATOR, null, 0, 0, 0);
                                        return null;
                                    }
                                });
            }
        }