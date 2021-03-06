package com.example.parentapp;

import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import com.example.parentapp.models.Kid;
import com.example.parentapp.models.KidManager;

/**
adds child to the the list when asked for its name
adds to the array list
comment
 */
public class AddKidActivity extends AppCompatActivity {

    KidManager manager;
    EditText inputKidName;
    String kidName;
    ImageView kidImage;
    Bitmap kidImageSelected;


    public static Intent makeIntent(Context context) {
        return new Intent(context, AddKidActivity.class);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_kid_add);
        ActionBar ab = getSupportActionBar();
        ab.setDisplayHomeAsUpEnabled(true);
        setTitle(getString(R.string.add_kid_title));




        manager = KidManager.getInstance();
        Intent intent = getIntent();
        Bundle b = intent.getExtras();

        Toolbar toolbar = findViewById(R.id.toolbarAdd);

        inputKidName = (EditText) findViewById(R.id.nameInput);
        kidImage = findViewById(R.id.kidImage);

        kidImage.setOnClickListener(view -> {
            selectImage(AddKidActivity.this);
        });
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_save_kid,menu);
        return true;
    }


    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        switch(item.getItemId()) {
            case R.id.action_save_kid:


                kidName = (inputKidName.getText().toString());


                Kid newKid = new Kid(kidName, kidImageSelected);
                manager.addKid(newKid);
                SharedPrefsConfig.setSavedKidsSharedPrefs(this, manager);
                Toast.makeText(this, newKid.getName() +" has been added", Toast.LENGTH_SHORT).show();

                finish();
                return true;

            case android.R.id.home:
                Toast.makeText(this, "Make sure you saved your kid!", Toast.LENGTH_SHORT).show();
                finish();
                return true;

            default:
                return super.onOptionsItemSelected(item);
        }
    }


    private void selectImage(Context context) {
        final CharSequence[] options = { "Take Photo", "Choose from Gallery","Cancel" };

        AlertDialog.Builder builder = new AlertDialog.Builder(context);
        builder.setTitle("Choose your profile picture");

        builder.setItems(options, new DialogInterface.OnClickListener() {

            @Override
            public void onClick(DialogInterface dialog, int item) {

                if (options[item].equals("Take Photo")) {
                    Intent takePicture = new Intent(android.provider.MediaStore.ACTION_IMAGE_CAPTURE);
                    startActivityForResult(takePicture, 0);

                } else if (options[item].equals("Choose from Gallery")) {
                    Intent pickPhoto = new Intent(Intent.ACTION_PICK, android.provider.MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
                    startActivityForResult(pickPhoto , 1);

                } else if (options[item].equals("Cancel")) {
                    dialog.dismiss();
                }
            }
        });
        builder.show();
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (resultCode != RESULT_CANCELED) {
            switch (requestCode) {
                case 0:
                    if (resultCode == RESULT_OK && data != null) {
                        kidImageSelected = (Bitmap) data.getExtras().get("data");
                        kidImage.setImageBitmap(kidImageSelected);
                        SharedPrefsConfig.setKidPictureSharedPref(this, manager);
                    }

                    break;
                case 1:
                    if (resultCode == RESULT_OK && data != null) {
                        Uri selectedImage = data.getData();
                        String[] filePathColumn = {MediaStore.Images.Media.DATA};
                        if (selectedImage != null) {
                            Cursor cursor = getContentResolver().query(selectedImage,
                                    filePathColumn, null, null, null);
                            if (cursor != null) {
                                cursor.moveToFirst();

                                int columnIndex = cursor.getColumnIndex(filePathColumn[0]);
                                String picturePath = cursor.getString(columnIndex);
                                kidImageSelected = BitmapFactory.decodeFile(picturePath);
                                kidImage.setImageBitmap(kidImageSelected);
                                SharedPrefsConfig.setKidPictureSharedPref(this, manager);
                                cursor.close();
                            }
                        }

                    }
                    break;
            }
        }
    }

}
