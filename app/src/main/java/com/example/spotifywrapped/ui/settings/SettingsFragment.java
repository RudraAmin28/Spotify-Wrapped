package com.example.spotifywrapped.ui.settings;

import android.content.Intent;
import android.content.pm.PackageManager;
import android.net.Uri;
import android.os.Bundle;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.CompoundButton;
import android.widget.EditText;
import android.widget.PopupWindow;
import android.widget.RelativeLayout;
import android.widget.Switch;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatDelegate;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;
import androidx.navigation.Navigation;
import androidx.navigation.fragment.NavHostFragment;

import com.example.spotifywrapped.MainActivity;
import com.example.spotifywrapped.R;
import com.example.spotifywrapped.databinding.FragmentSettingsBinding;
import com.example.spotifywrapped.ui.wrapped.WrappedFragment;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

public class SettingsFragment extends Fragment {

    private PopupWindow updateLoginPopup;
    private EditText editTextUsername;
    private EditText editTextPassword;
    private Button buttonUpdate;

    private PopupWindow deleteAccountPopup;
    private Button buttonDeleteAccount;
    private Switch darkModeSwitch;

    private PopupWindow signOutPopup;
    private Button buttonSignOutConfirm;
    private static boolean isDarkMode;

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

        // Find the entry view for signing out
        RelativeLayout signOutEntry = root.findViewById(R.id.entrySignOut);

        // Set OnClickListener to open the sign out popup
        signOutEntry.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                showSignOutPopup();
            }
        });

        FloatingActionButton homeFab = root.findViewById(R.id.home_fab);
        homeFab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // Navigate to SettingsFragment
                NavHostFragment.findNavController(SettingsFragment.this)
                        .navigate(R.id.action_settingsFragment_to_wrappedFragment);
            }
        });

        // Find the dark mode switch
        darkModeSwitch = root.findViewById(R.id.darkModeSwitch);
        darkModeSwitch.setChecked(isDarkMode);
        // Set listener to toggle dark mode
        darkModeSwitch.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                // Apply dark mode theme when switch is checked, otherwise apply light mode theme
                if (isChecked) {
                    AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_YES);
                    isDarkMode = true;
                } else {
                    AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_NO);
                    isDarkMode = false;
                }
            }
        });

        return root;
    }

    private void showUpdateLoginPopup() {
        // Inflate the update login info popup layout
        View popupView = getLayoutInflater().inflate(R.layout.update_login_info_popup, null);

        // Initialize UI elements from the popup layout
        editTextUsername = popupView.findViewById(R.id.editTextEmail);
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
                FirebaseUser user = FirebaseAuth.getInstance().getCurrentUser();
                String newEmail = editTextUsername.getText().toString();
                String newPassword = editTextPassword.getText().toString();
                MainActivity.updateEmail(user, newEmail);
                MainActivity.updatePassword(user, newPassword);

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
                FirebaseUser user = FirebaseAuth.getInstance().getCurrentUser();
                if (user != null) {
                    MainActivity.deleteUser();
                    FirebaseAuth.getInstance().signOut();
                    Navigation.findNavController(requireView()).navigate(R.id.nav_login);
                }

                // Dismiss the delete account popup window after deleting
                deleteAccountPopup.dismiss();
            }
        });
    }

    private void showSignOutPopup() {
        // Inflate the sign out popup layout
        View popupView = getLayoutInflater().inflate(R.layout.signout_popup, null);

        // Initialize views from the popup layout
        buttonSignOutConfirm = popupView.findViewById(R.id.buttonSignOutConfirm);
        Button buttonCancel = popupView.findViewById(R.id.buttonCancel);

        // Create the sign out popup window
        signOutPopup = new PopupWindow(popupView, ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT, true);

        // Set background to dismiss the popup when clicked outside of it
        signOutPopup.setBackgroundDrawable(getResources().getDrawable(android.R.color.transparent));

        // Set animation style for the popup
        signOutPopup.setAnimationStyle(android.R.style.Animation_Dialog);

        // Show the popup window at the center of the screen
        signOutPopup.showAtLocation(popupView, Gravity.CENTER, 0, 0);

        // Set OnClickListener for the sign out confirm button
        buttonSignOutConfirm.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // Handle sign out button click here
//                spotifySignOut(() -> {
//                    FirebaseAuth.getInstance().signOut();
//                    Navigation.findNavController(requireView()).navigate(R.id.nav_login);
//                });

                FirebaseAuth.getInstance().signOut();
                Navigation.findNavController(requireView()).navigate(R.id.nav_login);
                // Dismiss the sign out popup window after signing out
                signOutPopup.dismiss();
            }
        });

        // Set OnClickListener for the cancel button
        buttonCancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // Dismiss the sign out popup window if canceled
                signOutPopup.dismiss();
            }
        });
    }

    public void spotifySignOut(final Runnable callback) {
        String url = "https://accounts.spotify.com";
        Intent intent = new Intent(Intent.ACTION_VIEW, Uri.parse(url));
        PackageManager packageManager = requireActivity().getPackageManager();
        if (intent.resolveActivity(packageManager) != null) {
            // Start the activity if there's an app available
            startActivity(intent);
            callback.run();
        } else {
            // Handle the case where no app can handle the intent
        }
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        binding = null;
    }
}
