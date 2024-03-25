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

    PopupWindow popupWindow;
    EditText editTextUsername;
    EditText editTextPassword;
    Button buttonUpdate;

    private FragmentSettingsBinding binding;

    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {
        SettingsViewModel settingsViewModel =
                new ViewModelProvider(this).get(SettingsViewModel.class);

        binding = FragmentSettingsBinding.inflate(inflater, container, false);
        View root = binding.getRoot();

        // Find the entry view for updating login info
        RelativeLayout updateLoginEntry = root.findViewById(R.id.entryUpdateLogin);

        // Set OnClickListener to open the pop-up
        updateLoginEntry.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                showUpdateLoginPopup();
            }
        });

        return root;
    }

    private void showUpdateLoginPopup() {
        // Inflate the pop-up layout
        View popupView = getLayoutInflater().inflate(R.layout.update_login_info_popup, null);

        // Initialize UI elements from the pop-up layout
        editTextUsername = popupView.findViewById(R.id.editTextUsername);
        editTextPassword = popupView.findViewById(R.id.editTextPassword);
        buttonUpdate = popupView.findViewById(R.id.buttonUpdate);

        // Create the pop-up window
        popupWindow = new PopupWindow(popupView, ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT, true);

        // Set background to dismiss the pop-up when clicked outside of it
        popupWindow.setBackgroundDrawable(getResources().getDrawable(android.R.color.transparent));

        // Set animation style for the pop-up
        popupWindow.setAnimationStyle(android.R.style.Animation_Dialog);

        // Show the pop-up window at the center of the screen
        popupWindow.showAtLocation(popupView, Gravity.CENTER, 0, 0);

        // Set onClickListener for the update button
        buttonUpdate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // Handle update button click here
            }
        });
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        binding = null;
    }
}