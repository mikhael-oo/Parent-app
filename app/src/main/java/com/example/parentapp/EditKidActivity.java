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

import com.example.parentapp.models.Coin;
import com.example.parentapp.models.Kid;
import com.example.parentapp.models.KidManager;
import com.example.parentapp.models.TaskManager;

import java.io.FileNotFoundException;
import java.io.InputStream;

/**
Edits the child's name and supports deletion of the child as well, it takes a bundle of the
position of the child and sets it to the array position of the kidmanager and allows access to the
child's name
 */

public class EditKidActivity extends AppCompatActivity {

    private Coin coin = Coin.getCoinInstance();
    private KidManager manager;
    private TaskManager taskManager = TaskManager.getInstance();
    private EditText editInputName;
    private ImageView editKidImage;
    private String kidName;
    private Bitmap kidImageSelected;
    private int position;
    private Kid editedKid;


    public static Intent makeIntent(Context context) {
        return new Intent(context, EditKidActivity.class);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_kid_edit);
        ActionBar ab = getSupportActionBar();
        assert ab != null;
        ab.setDisplayHomeAsUpEnabled(true);
        Toolbar toolbar = findViewById(R.id.toolbar);

        manager = KidManager.getInstance();

        Intent intent = getIntent();
        Bundle b = intent.getExtras();
        position = b.getInt("Kid Index");
        editedKid = manager.returnKids().get(position);

        getSupportActionBar().setTitle("Edit " + editedKid.getName() + "'s name!");

        editInputName = (EditText) findViewById(R.id.editNameInput);
        editKidImage = findViewById(R.id.editChildImage);
        editKidImage.setImageBitmap(editedKid.getImage());
        editInputName.setText(editedKid.getName());
        kidImageSelected = editedKid.getImage();

        editKidImage.setOnClickListener(view -> {
            selectImage(EditKidActivity.this);
        });

    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_delete_kid,menu);
        getMenuInflater().inflate(R.menu.menu_save_kid,menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        switch(item.getItemId()) {
            case R.id.action_save_kid:

                kidName = (editInputName.getText().toString());

                coin.editHistory(editedKid.getName(), kidName);
                taskManager.editTasks(editedKid.getName(), kidName);
                editedKid.setName(kidName);


                editedKid.setImage(kidImageSelected);
                editKidImage.setImageBitmap(editedKid.getImage());

                Toast.makeText(this, "Your kid has been edited", Toast.LENGTH_SHORT).show();
                SharedPrefsConfig.setSavedKidsSharedPrefs(this, manager);
                finish();
                return true;

            case android.R.id.home:
                Toast.makeText(this, "Make sure you saved your edit!", Toast.LENGTH_SHORT).show();
                finish();
                return true;

            case R.id.action_delete_kid:
                Toast.makeText(this, "Deleting your " + editedKid.getName() + "!! BYE BYE ", Toast.LENGTH_SHORT).show();
                finish();
                coin.deleteFromHistory(editedKid.getName());
                taskManager.deleteFromTasks(editedKid.getName());
                manager.removeKid(position);
                SharedPrefsConfig.setSavedKidsSharedPrefs(this, manager);
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
                        editKidImage.setImageBitmap(kidImageSelected);
                    }

                    break;
                case 1:
                    if (resultCode == RESULT_OK) {
                        try {
                            final Uri imageUri = data.getData();
                            final InputStream imageStream = getContentResolver().openInputStream(imageUri);
                            kidImageSelected = BitmapFactory.decodeStream(imageStream);
                            editKidImage.setImageBitmap(kidImageSelected);
                        } catch (FileNotFoundException e) {
                            e.printStackTrace();
                            Toast.makeText(EditKidActivity.this, "Something went wrong", Toast.LENGTH_LONG).show();
                        }

                    }else {
                        Toast.makeText(EditKidActivity.this, "You haven't picked Image",Toast.LENGTH_LONG).show();
                    }

                    break;
            }
        }
    }
}


