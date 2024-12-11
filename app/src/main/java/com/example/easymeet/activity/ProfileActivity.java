package com.example.easymeet.activity;

import android.content.Intent;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.text.InputType;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.example.easymeet.R;
import com.example.easymeet.model.ProfileData;
import com.example.easymeet.repository.ProfileDataRepository;
import com.example.easymeet.utility.SessionManager;

import java.io.File;
import java.io.FileOutputStream;

public class ProfileActivity extends AppCompatActivity {

    private boolean isEditing = false; // Track editing mode
    private EditText usernameEdit, descriptionEdit;
    private Button editProfileButton;
    private ImageView profileImage;

    private Uri selectedImageUri;

    int userId;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_profile);
        //ProfileData profileData1 = getIntent().getParcelableExtra("profileData");
        //Log.d("nje username:",profileData1.getUsername());

        // Initialize views
        usernameEdit = findViewById(R.id.usernameEdit);
        descriptionEdit = findViewById(R.id.descriptionEdit);
        editProfileButton = findViewById(R.id.editProfileButton);
        profileImage = findViewById(R.id.profileImage);

        // Assuming SessionManager.getUserId() correctly fetches the user ID
        Intent intent = getIntent();
        userId = Integer.parseInt(intent.getStringExtra("id"));
        //userId = SessionManager.getUserId(ProfileActivity.this);
        //Log.d("id-ja", String.valueOf(userId));

        ProfileData profileData = ProfileDataRepository.getProfileDataByUserId(ProfileActivity.this, userId);
        //Log.d("emri", profileData.getUsername());
        if (profileData == null) {
            Log.e("ProfileActivity", "No profile data found for userId: " + userId);
        } else {
            usernameEdit.setText(profileData.getUsername());
            descriptionEdit.setText(profileData.getDescription());
            if (profileData.getProfilePic() != null && !profileData.getProfilePic().isEmpty()) {
                profileImage.setImageURI(Uri.fromFile(new File(profileData.getProfilePic())));
            } else {
                profileImage.setImageResource(R.drawable.avatar); // Set a default image
            }
        }
        if (userId == SessionManager.getUserId(ProfileActivity.this)){
        editProfileButton.setOnClickListener(v -> toggleEditMode());}
        else {
            editProfileButton.setVisibility(View.INVISIBLE);
        }
    }

    private void toggleEditMode() {
        if (isEditing) {
            setEditingEnabled(false);
            editProfileButton.setText("Edit Profile");
            isEditing = false;
            saveProfileChanges();
        } else {
            setEditingEnabled(true);
            editProfileButton.setText("Save Changes");
            isEditing = true;
            profileImage.setOnClickListener(v -> openImagePicker());

        }
    }

    private void setEditingEnabled(boolean enabled) {
        usernameEdit.setFocusable(enabled);
        usernameEdit.setFocusableInTouchMode(enabled);
        usernameEdit.setCursorVisible(enabled);

        descriptionEdit.setFocusable(enabled);
        descriptionEdit.setFocusableInTouchMode(enabled);
        descriptionEdit.setCursorVisible(enabled);
        profileImage.setClickable(enabled);

        if (enabled) {
            usernameEdit.setInputType(InputType.TYPE_CLASS_TEXT);
            descriptionEdit.setInputType(InputType.TYPE_CLASS_TEXT | InputType.TYPE_TEXT_FLAG_MULTI_LINE);
        }
    }

    private void openImagePicker() {
        // Example code for opening an image picker
        Intent intent = new Intent(Intent.ACTION_PICK);
        intent.setType("image/*");
        startActivityForResult(intent, 100); // Use 100 as a request code for the image picker
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if (requestCode == 100 && resultCode == RESULT_OK && data != null) {
            selectedImageUri = data.getData(); // Store the selected image URI
            profileImage.setImageURI(selectedImageUri); // Update the UI

            // Save the image to internal storage and get the path
            String savedImagePath = saveImageToInternalStorage(selectedImageUri);
            if (savedImagePath != null) {
                selectedImageUri = Uri.fromFile(new File(savedImagePath)); // Update URI with saved path
            }
        }
    }


    private void saveProfileChanges() {
        String username = usernameEdit.getText().toString();
        String description = descriptionEdit.getText().toString();
        String profilePic = null; // Path to the saved profile picture

        if (selectedImageUri != null) {
            // Save the selected image to internal storage
            profilePic = saveImageToInternalStorage(selectedImageUri);
            if (profilePic != null) {
                Log.d("ProfileActivity", "Image saved at: " + profilePic);
            } else {
                Toast.makeText(this, "Failed to save the profile picture.", Toast.LENGTH_SHORT).show();
            }
        } else {
            // Retain existing profile picture if no new image is selected
            ProfileData currentProfile = ProfileDataRepository.getProfileDataByUserId(this, userId);
            profilePic = currentProfile != null ? currentProfile.getProfilePic() : "";
        }

        ProfileData profileData = new ProfileData(userId, username, profilePic, description);
        boolean isSaved = ProfileDataRepository.saveProfileData(ProfileActivity.this, profileData);

        if (isSaved) {
            Toast.makeText(this, "Profile updated successfully!", Toast.LENGTH_SHORT).show();
        } else {
            Toast.makeText(this, "Failed to update profile.", Toast.LENGTH_SHORT).show();
        }


        profileImage.setOnClickListener(null);
        Intent intent = new Intent(ProfileActivity.this, HomeActivity.class);
        startActivity(intent);
    }


    private String saveImageToInternalStorage(Uri imageUri) {
        try {
            Bitmap bitmap = MediaStore.Images.Media.getBitmap(this.getContentResolver(), imageUri);

            File directory = new File(getFilesDir(), "Images");
            if (!directory.exists()) {
                directory.mkdirs(); // Create the directory if it doesn't exist
            }

            File file = new File(directory, "profile_image_" + userId + ".png");
            FileOutputStream fos = new FileOutputStream(file);
            bitmap.compress(Bitmap.CompressFormat.PNG, 100, fos);
            fos.close();

            Log.d("ProfileActivity", "Image saved at: " + file.getAbsolutePath());
            return file.getAbsolutePath();
        } catch (Exception e) {
            e.printStackTrace();
            Log.e("ProfileActivity", "Failed to save image: " + e.getMessage());
            return null;
        }
    }


    // Assuming you have a way to get the user ID (either from SharedPreferences or passed intent)

}
