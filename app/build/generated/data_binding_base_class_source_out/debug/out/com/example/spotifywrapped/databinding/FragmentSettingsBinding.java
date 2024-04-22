// Generated by view binder compiler. Do not edit!
package com.example.spotifywrapped.databinding;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.RelativeLayout;
import android.widget.Switch;
import android.widget.TextView;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.viewbinding.ViewBinding;
import androidx.viewbinding.ViewBindings;
import com.example.spotifywrapped.R;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import java.lang.NullPointerException;
import java.lang.Override;
import java.lang.String;

public final class FragmentSettingsBinding implements ViewBinding {
  @NonNull
  private final RelativeLayout rootView;

  @NonNull
  public final TextView DeleteAccountText;

  @NonNull
  public final TextView SignOutText;

  @NonNull
  public final Switch darkModeSwitch;

  @NonNull
  public final RelativeLayout entryDeleteAccount;

  @NonNull
  public final RelativeLayout entrySignOut;

  @NonNull
  public final RelativeLayout entryToggleDarkMode;

  @NonNull
  public final RelativeLayout entryUpdateLogin;

  @NonNull
  public final FloatingActionButton homeFab;

  @NonNull
  public final TextView toggleDarkModeText;

  @NonNull
  public final TextView updateLoginText;

  private FragmentSettingsBinding(@NonNull RelativeLayout rootView,
      @NonNull TextView DeleteAccountText, @NonNull TextView SignOutText,
      @NonNull Switch darkModeSwitch, @NonNull RelativeLayout entryDeleteAccount,
      @NonNull RelativeLayout entrySignOut, @NonNull RelativeLayout entryToggleDarkMode,
      @NonNull RelativeLayout entryUpdateLogin, @NonNull FloatingActionButton homeFab,
      @NonNull TextView toggleDarkModeText, @NonNull TextView updateLoginText) {
    this.rootView = rootView;
    this.DeleteAccountText = DeleteAccountText;
    this.SignOutText = SignOutText;
    this.darkModeSwitch = darkModeSwitch;
    this.entryDeleteAccount = entryDeleteAccount;
    this.entrySignOut = entrySignOut;
    this.entryToggleDarkMode = entryToggleDarkMode;
    this.entryUpdateLogin = entryUpdateLogin;
    this.homeFab = homeFab;
    this.toggleDarkModeText = toggleDarkModeText;
    this.updateLoginText = updateLoginText;
  }

  @Override
  @NonNull
  public RelativeLayout getRoot() {
    return rootView;
  }

  @NonNull
  public static FragmentSettingsBinding inflate(@NonNull LayoutInflater inflater) {
    return inflate(inflater, null, false);
  }

  @NonNull
  public static FragmentSettingsBinding inflate(@NonNull LayoutInflater inflater,
      @Nullable ViewGroup parent, boolean attachToParent) {
    View root = inflater.inflate(R.layout.fragment_settings, parent, false);
    if (attachToParent) {
      parent.addView(root);
    }
    return bind(root);
  }

  @NonNull
  public static FragmentSettingsBinding bind(@NonNull View rootView) {
    // The body of this method is generated in a way you would not otherwise write.
    // This is done to optimize the compiled bytecode for size and performance.
    int id;
    missingId: {
      id = R.id.DeleteAccountText;
      TextView DeleteAccountText = ViewBindings.findChildViewById(rootView, id);
      if (DeleteAccountText == null) {
        break missingId;
      }

      id = R.id.SignOutText;
      TextView SignOutText = ViewBindings.findChildViewById(rootView, id);
      if (SignOutText == null) {
        break missingId;
      }

      id = R.id.darkModeSwitch;
      Switch darkModeSwitch = ViewBindings.findChildViewById(rootView, id);
      if (darkModeSwitch == null) {
        break missingId;
      }

      id = R.id.entryDeleteAccount;
      RelativeLayout entryDeleteAccount = ViewBindings.findChildViewById(rootView, id);
      if (entryDeleteAccount == null) {
        break missingId;
      }

      id = R.id.entrySignOut;
      RelativeLayout entrySignOut = ViewBindings.findChildViewById(rootView, id);
      if (entrySignOut == null) {
        break missingId;
      }

      id = R.id.entryToggleDarkMode;
      RelativeLayout entryToggleDarkMode = ViewBindings.findChildViewById(rootView, id);
      if (entryToggleDarkMode == null) {
        break missingId;
      }

      id = R.id.entryUpdateLogin;
      RelativeLayout entryUpdateLogin = ViewBindings.findChildViewById(rootView, id);
      if (entryUpdateLogin == null) {
        break missingId;
      }

      id = R.id.home_fab;
      FloatingActionButton homeFab = ViewBindings.findChildViewById(rootView, id);
      if (homeFab == null) {
        break missingId;
      }

      id = R.id.toggleDarkModeText;
      TextView toggleDarkModeText = ViewBindings.findChildViewById(rootView, id);
      if (toggleDarkModeText == null) {
        break missingId;
      }

      id = R.id.updateLoginText;
      TextView updateLoginText = ViewBindings.findChildViewById(rootView, id);
      if (updateLoginText == null) {
        break missingId;
      }

      return new FragmentSettingsBinding((RelativeLayout) rootView, DeleteAccountText, SignOutText,
          darkModeSwitch, entryDeleteAccount, entrySignOut, entryToggleDarkMode, entryUpdateLogin,
          homeFab, toggleDarkModeText, updateLoginText);
    }
    String missingId = rootView.getResources().getResourceName(id);
    throw new NullPointerException("Missing required view with ID: ".concat(missingId));
  }
}