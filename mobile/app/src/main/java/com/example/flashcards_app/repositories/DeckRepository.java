package com.example.flashcards_app.repositories;


import androidx.lifecycle.MutableLiveData;

import com.example.flashcards_app.api.DeckService;
import com.example.flashcards_app.dto.CreateCardDTO;
import com.example.flashcards_app.dto.DeleteDeckDTO;
import com.example.flashcards_app.dto.UpdateDeckDTO;
import com.example.flashcards_app.models.Deck;
import com.example.flashcards_app.models.Review;
import com.example.flashcards_app.network.RetrofitClient;
import com.example.flashcards_app.util.AppPreferences;

import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class DeckRepository {
    private DeckService deckService;

    public DeckRepository() {
        deckService = RetrofitClient.getRetrofitInstance().create(DeckService.class);
    }

    public MutableLiveData<List<Deck>> getAllDecks() {
        MutableLiveData<List<Deck>> decksLiveData = new MutableLiveData<>();

        Call<List<Deck>> call = deckService.getAllDecks();
        call.enqueue(new Callback<List<Deck>>() {
            @Override
            public void onResponse(Call<List<Deck>> call, Response<List<Deck>> response) {
                if (response.isSuccessful() && response.body() != null) {
                    System.out.println("PRINT: requisition OK");
                    decksLiveData.setValue(response.body());
                } else {
                    // error treatment
                    System.out.println("PRINT: getAllDecks error");
                }
            }

            @Override
            public void onFailure(Call<List<Deck>> call, Throwable t) {
                System.out.println("PRINT: requisition fail " + t.getMessage());
                t.printStackTrace();
            }
        });

        return decksLiveData;
    }

    public MutableLiveData<List<Deck>> updateDeck(Deck deck) {
        MutableLiveData<List<Deck>> decksLiveData = new MutableLiveData<>();
        UpdateDeckDTO updateDeckDTO = new UpdateDeckDTO(deck.getId(),
                deck.getTitle(),
                deck.getImgSrc(),
                deck.getNewCardsNumber(),
                deck.getLearnCardsNumber(),
                deck.getReviewCardsNumber());

        Call<List<Deck>> call = deckService.updateDeck(updateDeckDTO);

        call.enqueue(new Callback<List<Deck>>() {
            @Override
            public void onResponse(Call<List<Deck>> call, Response<List<Deck>> response) {
                if(response.isSuccessful() && response.body() !=null) {
                    System.out.println("Deu certo para rodar o update");
                    decksLiveData.setValue(response.body());
                }
            }

            @Override
            public void onFailure(Call<List<Deck>> call, Throwable t) {
                // error treatment
                System.out.println("PRINT: update deck error");

                // Some day I will back here
            }
        });

        return decksLiveData;

    }

    public MutableLiveData<List<Deck>> addNewDeck() {
        MutableLiveData<List<Deck>> decksLiveData = new MutableLiveData<>();

        Call<List<Deck>> call = deckService.createDeck();

        call.enqueue(new Callback<List<Deck>>() {
            @Override
            public void onResponse(Call<List<Deck>> call, Response<List<Deck>> response) {
                if(response.isSuccessful() && response.body() !=null) {
                    decksLiveData.setValue(response.body());
                }
            }

            @Override
            public void onFailure(Call<List<Deck>> call, Throwable t) {
                // error treatment
                System.out.println("PRINT: add new deck error");

                // Some day I will back here
            }
        });

        return decksLiveData;
    }

    public MutableLiveData<List<Deck>> deleteDeck(Deck deck) {
        MutableLiveData<List<Deck>> decksLiveData = new MutableLiveData<>();
        DeleteDeckDTO deleteDeckDTO = new DeleteDeckDTO(deck.getId());

        Call<List<Deck>> call = deckService.deleteDeck(deck.getId());


        call.enqueue(new Callback<List<Deck>>() {
            @Override
            public void onResponse(Call<List<Deck>> call, Response<List<Deck>> response) {
                if(response.isSuccessful() && response.body() != null) {
                    decksLiveData.setValue(response.body());
                }
            }

            @Override
            public void onFailure(Call<List<Deck>> call, Throwable t) {
                // error treatment
                System.out.println("PRINT: delete decks error");

                // Some day I will back here
            }
        });


        return decksLiveData;

    }



}
