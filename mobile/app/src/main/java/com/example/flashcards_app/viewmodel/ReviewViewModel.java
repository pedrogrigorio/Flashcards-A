package com.example.flashcards_app.viewmodel;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import com.example.flashcards_app.models.Review;

import java.util.ArrayList;
import java.util.List;

public class ReviewViewModel extends ViewModel {
    private MutableLiveData<List<Review>> reviewLiveData = new MutableLiveData<>();
    private List<Review> reviewTemp = new ArrayList<>();

    public ReviewViewModel() {
        loadReviewCardsData();
    }

    private void loadReviewCardsData() {
        reviewTemp.add(new Review("Good Nigth", "Boa Noite", 1));
        reviewTemp.add(new Review("Have a nice day", "Tenha um bom dia", 2));
        reviewTemp.add(new Review("So far, so good", "Até agora, tudo bem", 1));
        reviewTemp.add(new Review("I'm lost", "Eu estou perdido", 1));


    }

    public void loadUiCards(int indexUiCard) {
        indexUiCard = Math.min(indexUiCard, reviewTemp.size()) + 1;
        this.reviewLiveData.setValue(reviewTemp.subList(0,indexUiCard));


    }

    public LiveData<List<Review>> getReviewData() {
        return this.reviewLiveData;

    }

}
