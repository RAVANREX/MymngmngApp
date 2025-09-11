package com.example.mnymng.viewmodel;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

public class EmojiViewModel extends ViewModel {
    private final MutableLiveData<String> selectedEmoji = new MutableLiveData<>();

    public void selectEmoji(String emoji) {
        selectedEmoji.setValue(emoji);
    }

    public LiveData<String> getSelectedEmoji() {
        return selectedEmoji;
    }
}
