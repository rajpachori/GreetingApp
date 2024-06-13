package com.example.greetingsapp;
import android.app.AlertDialog;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.Drawable;
import android.net.Uri;
import android.os.Bundle;
import androidx.appcompat.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;

import androidx.core.content.FileProvider;
import androidx.navigation.NavController;
import androidx.navigation.Navigation;
import androidx.navigation.ui.AppBarConfiguration;
import androidx.navigation.ui.NavigationUI;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;

public class GreetingAnniversary extends AppCompatActivity {
    private AppBarConfiguration appBarConfiguration;
    private ImageView selectedImage = null;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_greeting_anniversary);

        setupImageView(R.id.image1);
        setupImageView(R.id.image2);
        setupImageView(R.id.image3);
        setupImageView(R.id.image4);
        setupImageView(R.id.image5);
        setupImageView(R.id.image6);
        setupImageView(R.id.image7);
        setupImageView(R.id.image8);
        setupImageView(R.id.image9);
        setupImageView(R.id.image10);

        Button shareButton = findViewById(R.id.share_button);
        shareButton.setOnClickListener(view -> {
            if (selectedImage != null) {
                shareImage();
            }
            else {
                AlertDialog.Builder builder = new AlertDialog.Builder(GreetingAnniversary.this);
                builder.setMessage("First tap and select the greeting then share.");
                builder.setTitle("Alert!");
                builder.setCancelable(true);
                AlertDialog alertDialog = builder.create();
                alertDialog.show();
            }
        });
        Button backButton = findViewById(R.id.back_button);
        backButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(GreetingAnniversary.this, MainActivity.class);
                startActivity(intent);
            }
        });
    }
    private void setupImageView(int imageViewId) {
        ImageView imageView = findViewById(imageViewId);
        imageView.setOnClickListener(view -> {
            if (selectedImage != null) {
                selectedImage.clearColorFilter();
            }
            selectedImage = imageView;
            imageView.setColorFilter(0xAAFF0000); // Red tint
        });
    }
    private void shareImage() {
        try {
            if (selectedImage != null) {
                // Get the selected image resource
                int selectedImageResource = 0;
                Drawable drawable = selectedImage.getDrawable();
                if (drawable instanceof BitmapDrawable) {
                    BitmapDrawable bitmapDrawable = (BitmapDrawable) drawable;
                    Bitmap bitmap = bitmapDrawable.getBitmap();

                    // Decode the selected image using BitmapFactory
                    ByteArrayOutputStream byteArrayOutputStream = new ByteArrayOutputStream();
                    bitmap.compress(Bitmap.CompressFormat.JPEG, 100, byteArrayOutputStream);
                    byte[] byteArray = byteArrayOutputStream.toByteArray();

                    // Create a Uri from the byte array
                    Uri imageUri = Uri.parse("content://media/external/images/media");
                    // Now, we need to write the decoded bitmap to a file and get its URI
                    imageUri = writeToTempImageAndGetPathUri(byteArray);

                    // Create a share intent
                    Intent shareIntent = new Intent(Intent.ACTION_SEND);
                    shareIntent.setType("image/*");
                    shareIntent.putExtra(Intent.EXTRA_STREAM, imageUri);

                    EditText etUserMsg = findViewById(R.id.editText);

                    String txtUserMsg;
                    String txtCompleteMsg;
                    txtUserMsg = etUserMsg.getText().toString();
                    //txtWebsite = txtMsg + System.getProperty("line.separator") + System.getProperty("line.separator") + "Visit https://happy2u.in for lovely greetings.";
                    txtCompleteMsg = txtUserMsg + System.getProperty("line.separator") + System.getProperty("line.separator") + getString(R.string.msg_website);
                    shareIntent.putExtra(Intent.EXTRA_TEXT, txtCompleteMsg);

                    startActivity(Intent.createChooser(shareIntent, "Share Image"));
                }
            }
        }
        catch (Exception e) {
            //TextView text1 = findViewById(R.id.txtView);
            //text1.setText(e.getMessage());
            e.printStackTrace();
        }
    }
    private Uri writeToTempImageAndGetPathUri(byte[] imageData) {
        // Write the byte array to a temporary file and return its URI
        File tempDir = getCacheDir();
        File tempFile = new File(tempDir, "temp_image.jpg");
        try {
            FileOutputStream fos = new FileOutputStream(tempFile);
            fos.write(imageData);
            fos.flush();
            fos.close();
            // Get the FileProvider URI for the temporary file

            return FileProvider.getUriForFile(this, BuildConfig.APPLICATION_ID + ".provider", tempFile);
        } catch (IOException e) {
            e.printStackTrace();
            return null;
        }
    }
    @Override
    public boolean onSupportNavigateUp() {
        NavController navController = Navigation.findNavController(this, R.id.nav_host_fragment_content_greeting_anniversary);
        return NavigationUI.navigateUp(navController, appBarConfiguration)
                || super.onSupportNavigateUp();
    }
}