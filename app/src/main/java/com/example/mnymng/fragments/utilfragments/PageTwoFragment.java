package com.example.mnymng.fragments.utilfragments;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;

import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText; // Import EditText
import android.widget.TextView;

import com.example.mnymng.DB.models.Category; // Import Category
import com.example.mnymng.R;
import com.example.mnymng.viewmodel.EmojiViewModel;

import java.io.Serializable; // Import Serializable

public class PageTwoFragment extends Fragment {

    private EmojiViewModel emojiViewModel;
    private TextView emojiTextView;
    private EditText categoryNameEditText; // Assuming an EditText for category name
    private EditText descriptionEditText; // Assuming an EditText for description
    private Category categoryToEdit;

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        // Initialize ViewModel, scoped to the parent Fragment (e.g., PopupViewFragment)
        emojiViewModel = new ViewModelProvider(requireParentFragment()).get(EmojiViewModel.class);

        // Retrieve category if passed for editing
        if (getArguments() != null && getArguments().containsKey("categoryDataToEdit")) {
            Serializable serializableCategory = getArguments().getSerializable("categoryDataToEdit");
            if (serializableCategory instanceof Category) {
                categoryToEdit = (Category) serializableCategory;
            }
        }
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.cata_form, container, false);
        emojiTextView = view.findViewById(R.id.item_emoji);
        // Assuming you have an EditText with this ID in cata_form.xml
        categoryNameEditText = view.findViewById(R.id.nameEditText);
        descriptionEditText = view.findViewById(R.id.descEditText);
        return view;
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        if (categoryToEdit != null) {
            // Edit mode: Populate fields from categoryToEdit
            Log.d("PageTwoFragment", "Editing category: " + categoryToEdit.getCata_name());
            if (categoryNameEditText != null) {
                categoryNameEditText.setText(categoryToEdit.getCata_name());
            }
            if (emojiTextView != null) {
                emojiTextView.setText(categoryToEdit.getCata_icon()); // Assuming cata_icon stores the emoji string
            }
            if (descriptionEditText != null) {
                descriptionEditText.setText(categoryToEdit.getCata_desc());
            }

            // TODO: Populate other fields like color picker if they exist
        } else {
            // Add mode: Observe ViewModel for emoji changes (presumably from PageOneFragment)
            // emojiViewModel should now always be non-null here if this branch is taken.
            emojiViewModel.getSelectedEmoji().observe(getViewLifecycleOwner(), emoji -> {
                // Update the UI
                if (emojiTextView != null) {
                    emojiTextView.setText(emoji);
                }
            });
        }
    }
    
    // TODO: Add public methods to get the entered data for saving
      public String getCategoryName() { return categoryNameEditText.getText().toString(); }
      public String getSelectedEmoji() { return emojiTextView.getText().toString(); }
      public String getdescription() { return descriptionEditText.getText().toString(); }
}
