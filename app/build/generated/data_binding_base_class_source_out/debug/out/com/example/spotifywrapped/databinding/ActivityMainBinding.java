// Generated by view binder compiler. Do not edit!
package com.example.spotifywrapped.databinding;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.drawerlayout.widget.DrawerLayout;
import androidx.viewbinding.ViewBinding;
import androidx.viewbinding.ViewBindings;
import com.example.spotifywrapped.R;
import java.lang.NullPointerException;
import java.lang.Override;
import java.lang.String;

public final class ActivityMainBinding implements ViewBinding {
  @NonNull
  private final DrawerLayout rootView;

  @NonNull
  public final AppBarMainBinding appBarMain;

  @NonNull
  public final DrawerLayout drawerLayout;

  private ActivityMainBinding(@NonNull DrawerLayout rootView, @NonNull AppBarMainBinding appBarMain,
      @NonNull DrawerLayout drawerLayout) {
    this.rootView = rootView;
    this.appBarMain = appBarMain;
    this.drawerLayout = drawerLayout;
  }

  @Override
  @NonNull
  public DrawerLayout getRoot() {
    return rootView;
  }

  @NonNull
  public static ActivityMainBinding inflate(@NonNull LayoutInflater inflater) {
    return inflate(inflater, null, false);
  }

  @NonNull
  public static ActivityMainBinding inflate(@NonNull LayoutInflater inflater,
      @Nullable ViewGroup parent, boolean attachToParent) {
    View root = inflater.inflate(R.layout.activity_main, parent, false);
    if (attachToParent) {
      parent.addView(root);
    }
    return bind(root);
  }

  @NonNull
  public static ActivityMainBinding bind(@NonNull View rootView) {
    // The body of this method is generated in a way you would not otherwise write.
    // This is done to optimize the compiled bytecode for size and performance.
    int id;
    missingId: {
      id = R.id.app_bar_main;
      View appBarMain = ViewBindings.findChildViewById(rootView, id);
      if (appBarMain == null) {
        break missingId;
      }
      AppBarMainBinding binding_appBarMain = AppBarMainBinding.bind(appBarMain);

      DrawerLayout drawerLayout = (DrawerLayout) rootView;

      return new ActivityMainBinding((DrawerLayout) rootView, binding_appBarMain, drawerLayout);
    }
    String missingId = rootView.getResources().getResourceName(id);
    throw new NullPointerException("Missing required view with ID: ".concat(missingId));
  }
}
