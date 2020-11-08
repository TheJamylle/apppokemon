package com.example.apppokemon.ui.pokemon;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

public class PokemonViewModel extends ViewModel {

    private MutableLiveData<String> mText;

    public PokemonViewModel() {
        mText = new MutableLiveData<>();
    }

    public LiveData<String> getText() {
        return mText;
    }
}