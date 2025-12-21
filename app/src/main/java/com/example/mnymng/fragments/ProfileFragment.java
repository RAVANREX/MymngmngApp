package com.example.mnymng.fragments;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Toast;

import androidx.activity.result.ActivityResultLauncher;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatDelegate;
import androidx.fragment.app.Fragment;

import com.bumptech.glide.Glide;
import com.example.mnymng.R;
import com.example.mnymng.databinding.FragmentProfileBinding;
import com.firebase.ui.auth.AuthUI;
import com.firebase.ui.auth.FirebaseAuthUIActivityResultContract;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

import java.util.Collections;

public class ProfileFragment extends Fragment {

    // Constants for SharedPreferences
    private static final String PREFS_NAME = "prefs";
    private static final String KEY_DARK_MODE = "dark_mode";
    private static final String KEY_CURRENCY = "currency";
    private static final String DEFAULT_CURRENCY = "USD";

    private FragmentProfileBinding binding;
    private SharedPreferences sharedPreferences;

    private final ActivityResultLauncher<Intent> signInLauncher = registerForActivityResult(
            new FirebaseAuthUIActivityResultContract(),
            (result) -> {
                if (result.getResultCode() == getActivity().RESULT_OK) {
                    // Successfully signed in
                    FirebaseUser user = FirebaseAuth.getInstance().getCurrentUser();
                    updateUI(user);
                    Toast.makeText(getContext(), "Signed in successfully.", Toast.LENGTH_SHORT).show();
                } else {
                    // Sign in failed. If response is null the user canceled the
                    // sign-in flow using the back button. Otherwise check
                    // result.getError().getErrorCode() and handle the error.
                    Toast.makeText(getContext(), "Sign-in failed.", Toast.LENGTH_SHORT).show();
                }
            });

    public ProfileFragment() {
        // Required empty public constructor
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {
        binding = FragmentProfileBinding.inflate(inflater, container, false);

        sharedPreferences = requireActivity().getSharedPreferences(PREFS_NAME, Context.MODE_PRIVATE);

        initViews();
        initListeners();
        checkCurrentUser();

        return binding.getRoot();
    }

    private void initViews() {
        setupCurrencySpinner();
        loadPreferences();
    }

    private void setupCurrencySpinner() {
        ArrayAdapter<CharSequence> adapter = ArrayAdapter.createFromResource(requireContext(),
                R.array.currencies, android.R.layout.simple_spinner_item);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        binding.spinnerCurrency.setAdapter(adapter);
    }

    private void loadPreferences() {
        // Set saved preferences
        binding.switchTheme.setChecked(sharedPreferences.getBoolean(KEY_DARK_MODE, false));
        String savedCurrency = sharedPreferences.getString(KEY_CURRENCY, DEFAULT_CURRENCY);
        ArrayAdapter<CharSequence> adapter = (ArrayAdapter<CharSequence>) binding.spinnerCurrency.getAdapter();
        if (adapter != null) {
            for (int i = 0; i < adapter.getCount(); i++) {
                if (adapter.getItem(i).toString().equals(savedCurrency)) {
                    binding.spinnerCurrency.setSelection(i);
                    break;
                }
            }
        }
    }

    private void checkCurrentUser() {
        FirebaseUser currentUser = FirebaseAuth.getInstance().getCurrentUser();
        updateUI(currentUser);
    }

    private void initListeners() {
        binding.signInButton.setOnClickListener(v -> startSignIn());
        binding.signOutButton.setOnClickListener(v -> signOut());

        binding.switchTheme.setOnCheckedChangeListener((buttonView, isChecked) -> {
            sharedPreferences.edit().putBoolean(KEY_DARK_MODE, isChecked).apply();
            if (isChecked) {
                AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_YES);
            } else {
                AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_NO);
            }
        });

        binding.spinnerCurrency.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                String currency = parent.getItemAtPosition(position).toString();
                sharedPreferences.edit().putString(KEY_CURRENCY, currency).apply();
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {
                // Do nothing
            }
        });
    }

    private void startSignIn() {
        // Create and launch sign-in intent
        Intent signInIntent = AuthUI.getInstance()
                .createSignInIntentBuilder()
                .setAvailableProviders(Collections.singletonList(
                        new AuthUI.IdpConfig.GoogleBuilder().build()))
                .build();

        signInLauncher.launch(signInIntent);
    }

    private void signOut() {
        AuthUI.getInstance()
                .signOut(requireContext())
                .addOnCompleteListener(task -> {
                    updateUI(null);
                    Toast.makeText(getContext(), "Signed out successfully.", Toast.LENGTH_SHORT).show();
                });
    }

    private void updateUI(@Nullable FirebaseUser user) {
        if (user != null) {
            // Signed in
            binding.signInButton.setVisibility(View.GONE);
            binding.llSignedIn.setVisibility(View.VISIBLE);

            binding.profileName.setText(user.getDisplayName());
            binding.profileEmail.setText(user.getEmail());

            if (user.getPhotoUrl() != null) {
                Glide.with(this)
                        .load(user.getPhotoUrl())
                        .placeholder(R.drawable.ic_person)
                        .error(R.drawable.ic_person)
                        .into(binding.profileImage);
            } else {
                binding.profileImage.setImageResource(R.drawable.ic_person);
            }

        } else {
            // Signed out
            binding.signInButton.setVisibility(View.VISIBLE);
            binding.llSignedIn.setVisibility(View.GONE);

            binding.profileName.setText("");
            binding.profileEmail.setText("");
            binding.profileImage.setImageResource(R.drawable.ic_person);
        }
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        binding = null;
    }
}
