package com.example.spotifywrapped.ui.settings;

import android.os.Bundle;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.PopupWindow;
import android.widget.RelativeLayout;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;

import com.example.spotifywrapped.R;
import com.example.spotifywrapped.databinding.FragmentSettingsBinding;

public class SettingsFragment extends Fragment {

    PopupWindow updateLoginPopup;
    EditText editTextUsername;
    EditText editTextPassword;
    Button buttonUpdate;

    PopupWindow deleteAccountPopup;
    Button buttonDeleteAccount;

    private FragmentSettingsBinding binding;

    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {
        SettingsViewModel settingsViewModel =
                new ViewModelProvider(this).get(SettingsViewModel.class);

        binding = FragmentSettingsBinding.inflate(inflater, container, false);
        View root = binding.getRoot();

        // Find the entry view for updating login info
        RelativeLayout updateLoginEntry = root.findViewById(R.id.entryUpdateLogin);

        // Set OnClickListener to open the update login info popup
        updateLoginEntry.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                showUpdateLoginPopup();
            }
        });

        // Find the entry view for deleting account
        RelativeLayout deleteAccountEntry = root.findViewById(R.id.entryDeleteAccount);

        // Set OnClickListener to open the delete account popup
        deleteAccountEntry.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                showDeleteAccountPopup();
            }
        });

        return root;
    }

    private void showUpdateLoginPopup() {
        // Inflate the update login info popup layout
        View popupView = getLayoutInflater().inflate(R.layout.update_login_info_popup, null);

        // Initialize UI elements from the popup layout
        editTextUsername = popupView.findViewById(R.id.editTextUsername);
        editTextPassword = popupView.findViewById(R.id.editTextPassword);
        buttonUpdate = popupView.findViewById(R.id.buttonUpdate);

        // Create the update login info popup window
        updateLoginPopup = new PopupWindow(popupView, ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT, true);

        // Set background to dismiss the popup when clicked outside of it
        updateLoginPopup.setBackgroundDrawable(getResources().getDrawable(android.R.color.transparent));

        // Set animation style for the popup
        updateLoginPopup.setAnimationStyle(android.R.style.Animation_Dialog);

        // Show the popup window at the center of the screen
        updateLoginPopup.showAtLocation(popupView, Gravity.CENTER, 0, 0);

        // Set OnClickListener for the update button
        buttonUpdate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // Handle update button click here
                // Example: String newUsername = editTextUsername.getText().toString();
                // Example: String newPassword = editTextPassword.getText().toString();
                // Example: updateLoginInfo(newUsername, newPassword);

                // Dismiss the update login info popup window after updating
                updateLoginPopup.dismiss();
            }
        });
    }

    private void showDeleteAccountPopup() {
        // Inflate the delete account popup layout
        View popupView = getLayoutInflater().inflate(R.layout.delete_account_popup, null);

        // Initialize views from the popup layout
        buttonDeleteAccount = popupView.findViewById(R.id.buttonDelete);

        // Create the delete account popup window
        deleteAccountPopup = new PopupWindow(popupView, ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT, true);

        // Set background to dismiss the popup when clicked outside of it
        deleteAccountPopup.setBackgroundDrawable(getResources().getDrawable(android.R.color.transparent));

        // Set animation style for the popup
        deleteAccountPopup.setAnimationStyle(android.R.style.Animation_Dialog);

        // Show the popup window at the center of the screen
        deleteAccountPopup.showAtLocation(popupView, Gravity.CENTER, 0, 0);

        // Set OnClickListener for the delete button
        buttonDeleteAccount.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // Handle delete button click here
                // Example: deleteAccount();

                // Dismiss the delete account popup window after deleting
                deleteAccountPopup.dismiss();
            }
        });
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        binding = null;
    }
}
