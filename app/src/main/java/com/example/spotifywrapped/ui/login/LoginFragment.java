package com.example.spotifywrapped.ui.login;

import static androidx.constraintlayout.helper.widget.MotionEffect.TAG;

import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;
import androidx.navigation.Navigation;

import com.example.spotifywrapped.MainActivity;
import com.example.spotifywrapped.R;
import com.example.spotifywrapped.SpotifyAuthCallback;
import com.example.spotifywrapped.authentication.EmailPasswordActivity;
import com.example.spotifywrapped.databinding.FragmentLoginBinding;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

public class LoginFragment extends Fragment {

    private FragmentLoginBinding binding;
    private FirebaseAuth mAuth;
    private EmailPasswordActivity emailPasswordActivity;
    private SpotifyAuthCallback spotifyAuthCallback;
    public static TextView errorMessage;

//    public LoginFragment(SpotifyAuthCallback spotifyAuthCallback) {
//        this.spotifyAuthCallback = spotifyAuthCallback;
//    }

    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        LoginViewModel loginViewModel = new ViewModelProvider(this).get(LoginViewModel.class);

        mAuth = FirebaseAuth.getInstance();
        emailPasswordActivity = new EmailPasswordActivity();
//        MainActivity mainActivity = new MainActivity();
//        mainActivity.setLoginFragment(this);
//        setAuthCallback(mainActivity);
//        System.out.println(spotifyAuthCallback + " 2");
        System.out.println(this + " 1");

        binding = FragmentLoginBinding.inflate(inflater, container, false);
        View root = binding.getRoot();

        errorMessage = root.findViewById(R.id.textViewEmailPasswordError);

        final EditText editTextEmail = binding.editTextEmail;
        final EditText editTextPassword = binding.editTextPassword;
        Button buttonSignUp = binding.buttonSignUp;
        Button buttonLogin = binding.buttonLogin;

        buttonSignUp.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String email = editTextEmail.getText().toString().trim();
                String password = editTextPassword.getText().toString().trim();

                TextView errorMessage = root.findViewById(R.id.textViewEmailPasswordError);
                if (!email.isEmpty() && !password.isEmpty()) {
                    if (email.contains("@") && email.contains(".")) {
                        errorMessage.setText("");
                        emailPasswordActivity.createAccount(mAuth, email, password);
                        Navigation.findNavController(v).navigate(R.id.action_loginFragment_to_wrappedFragment);
//                        spotifyAuthCallback.onAuthSuccess();
                    } else {
                        errorMessage.setText("Invalid email address");
                    }
                } else {
                    errorMessage.setText("Cannot enter a blank email or password!");
                }
            }
        });

        buttonLogin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String email = editTextEmail.getText().toString().trim();
                String password = editTextPassword.getText().toString().trim();

//                TextView errorMessage = root.findViewById(R.id.textViewEmailPasswordError);

                emailPasswordActivity.signIn(mAuth, email, password, () -> {
                    Navigation.findNavController(v).navigate(R.id.action_loginFragment_to_wrappedFragment);
//                    System.out.println(spotifyAuthCallback);
//                    spotifyAuthCallback.onAuthSuccess();
                });

//                mAuth.signInWithEmailAndPassword(email, password)
//                        .addOnCompleteListener(getActivity(), new OnCompleteListener<AuthResult>() {
//                            @Override
//                            public void onComplete(@NonNull Task<AuthResult> task) {
//                                if (task.isSuccessful()) {
//                                    // Sign in success, update UI with the signed-in user's information
//                                    Log.d(TAG, "signInWithEmail:success");
//                                    FirebaseUser user = mAuth.getCurrentUser();
//                                } else {
//                                    // If sign in fails, display a message to the user.
//                                    Log.w(TAG, "signInWithEmail:failure", task.getException());
//                                    errorMessage.setText("Invalid login");
//                                }
//                            }
//                        });
            }
        });

        return root;
    }

    public void setAuthCallback(SpotifyAuthCallback spotifyAuthCallback) {
        this.spotifyAuthCallback = spotifyAuthCallback;
//        System.out.println(this.spotifyAuthCallback + " 3");
    }

    public LoginFragment getThis() {
        return this;
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        binding = null;
    }
}