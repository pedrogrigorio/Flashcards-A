package com.example.flashcards_app.activities;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.LinearSnapHelper;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Build;
import android.os.Bundle;

import android.view.View;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.Toast;

import com.example.flashcards_app.R;
import com.example.flashcards_app.adapters.ReviewAdapter;
import com.example.flashcards_app.util.DifficultLevel;
import com.example.flashcards_app.viewmodel.ViewModelLogic.Review.AudioCard;
import com.example.flashcards_app.viewmodel.ViewModelLogic.Review.ProgressBarCards;
import com.example.flashcards_app.models.Review;
import com.example.flashcards_app.viewmodel.ReviewViewModel;

import java.util.List;


public class ReviewActivity extends AppCompatActivity {

    private ReviewViewModel reviewViewModel;
    private RecyclerView recyclerView;
    private ReviewAdapter reviewAdapter;
    private LinearSnapHelper linearSnapHelper;
    private ProgressBarCards progressBarCards;
    private Button microphoneButton;
    private static Button easyButton;
    private static Button goodButton;
    private static Button hardButton;
    private Button audioButton;
    private AudioCard audioCard;
    private LinearLayoutManager layoutManager;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_review);

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.KITKAT) {
            getWindow().setFlags(WindowManager.LayoutParams.FLAG_LAYOUT_NO_LIMITS, WindowManager.LayoutParams.FLAG_LAYOUT_NO_LIMITS);
        }

        Intent intent = getIntent();
        if (intent.hasExtra("deckId")) {
            int deckId = intent.getIntExtra("deckId", -1);
            Toast.makeText(this, "ID: " + deckId, Toast.LENGTH_SHORT).show();
        } else {
            Toast.makeText(this, "Erro ao carregar o deck", Toast.LENGTH_SHORT).show();
        }


        startUpRecycleViewMVVM();
        startUpScreenElements();

        recyclerView.addOnScrollListener(new RecyclerView.OnScrollListener() {
            @Override
            public void onScrolled(@NonNull RecyclerView recyclerView, int dx, int dy) {
                recyclerView.setScrollingTouchSlop(dx);

                updateProgressBar();
                ReviewAdapter.ReviewHolder firstVisibleViewHolder = (ReviewAdapter.ReviewHolder) recyclerView.findViewHolderForAdapterPosition(getCurrentRecycleObjectOnScreen());
                setVisibilityDifficultButtons(firstVisibleViewHolder == null || reviewViewModel.hasBeenReviewed(getCurrentRecycleObjectOnScreen()));

            }
        });



        this.audioButton.setOnClickListener(v -> speakAudio());

        easyButton.setOnClickListener(v -> setEasyButton());
        goodButton.setOnClickListener(v -> setGoodButton());
        hardButton.setOnClickListener(v -> setHardButton());

    }

    private void setEasyButton() {
        changeDataToReviewedCards(DifficultLevel.EASY.getValue());
        this.reviewViewModel.loadUiCards(getCurrentRecycleObjectOnScreen(), this);
        nextSmoothScrollToPosition(getCurrentRecycleObjectOnScreen()+1);
    }
    private void setGoodButton() {
        changeDataToReviewedCards(DifficultLevel.GOOD.getValue());
        this.reviewViewModel.loadUiCards(getCurrentRecycleObjectOnScreen(),this);
        nextSmoothScrollToPosition(getCurrentRecycleObjectOnScreen()+1);
    }
    private void setHardButton() {
        changeDataToReviewedCards(DifficultLevel.HARD.getValue());
        this.reviewViewModel.loadUiCards(getCurrentRecycleObjectOnScreen(),this);
        nextSmoothScrollToPosition(getCurrentRecycleObjectOnScreen()+1);

    }

    private void changeDataToReviewedCards(int levelStamp) {
        ReviewAdapter.ReviewHolder firstVisibleViewHolder = (ReviewAdapter.ReviewHolder) recyclerView.findViewHolderForAdapterPosition(getCurrentRecycleObjectOnScreen());
        if (firstVisibleViewHolder != null) {
                this.reviewViewModel.setReviewedCard(getCurrentRecycleObjectOnScreen(),levelStamp);

        }
    }

    private void speakAudio() {
        ReviewAdapter.ReviewHolder firstVisibleViewHolder = (ReviewAdapter.ReviewHolder) this.recyclerView.findViewHolderForAdapterPosition(getCurrentRecycleObjectOnScreen());
        if (firstVisibleViewHolder != null) {
            this.audioCard.speak(firstVisibleViewHolder.getTextCard());
        }
    }

    private int getCurrentRecycleObjectOnScreen() {
        return this.layoutManager.findFirstVisibleItemPosition();
    }

    private void nextSmoothScrollToPosition(int currentRecycleObject) {
        if (currentRecycleObject < this.reviewAdapter.getItemCount()) {
            recyclerView.smoothScrollToPosition(currentRecycleObject);
        }
    }

    private void startUpScreenElements() {
        this.microphoneButton  = findViewById(R.id.microphone_button);
        easyButton        = findViewById(R.id.easy_button);
        goodButton        = findViewById(R.id.good_button);
        hardButton        = findViewById(R.id.hard_button);
        this.audioButton  = findViewById(R.id.audio_button);
        this.audioCard = new AudioCard(getApplicationContext());
        startUpProgressBar(this.reviewViewModel.getLoadCardsSize());
    }

    private void startUpRecycleViewMVVM() {

        // Recycle View config
        this.linearSnapHelper = new LinearSnapHelper();
        this.reviewAdapter = new ReviewAdapter();
        this.recyclerView = findViewById(R.id.testReviewScreen);
        configRecyclerView();
        this.layoutManager = (LinearLayoutManager) recyclerView.getLayoutManager();

        // View Model Config
        this.reviewViewModel = new ViewModelProvider(this).get(ReviewViewModel.class);
        configReviewViewModel();
        this.reviewViewModel.loadUiCards(0, this);

    }

    private void configRecyclerView() {
        this.recyclerView.setLayoutManager(new LinearLayoutManager(getApplicationContext(), LinearLayoutManager.HORIZONTAL, false));
        this.recyclerView.setHasFixedSize(true);
        recyclerView.setAdapter(this.reviewAdapter);
        linearSnapHelper.attachToRecyclerView(this.recyclerView);
    }



    public void configReviewViewModel() {
        reviewViewModel.getReviewData().observe(this, new Observer<List<Review>>() {
            @Override
            public void onChanged(List<Review> reviews) {
                reviewAdapter.setReviews(reviews);
            }
        });
    }

    @Override
    protected void onDestroy() {
        if (this.audioCard != null) {
            this.audioCard.shutDown();
        }
        super.onDestroy();
    }

    private void startUpProgressBar(int getItemCount) {
        this.progressBarCards  = new ProgressBarCards(findViewById(R.id.progressText), findViewById(R.id.progressBar));
        this.progressBarCards.setAmount(getItemCount);
    }

    private void updateProgressBar() {
        this.progressBarCards.setCurrent(getCurrentRecycleObjectOnScreen()+1);
    }


    public static  void setVisibilityDifficultButtons(boolean setVisibility) {
        int visibility = setVisibility ? View.VISIBLE : View.INVISIBLE;
        goodButton.setVisibility(visibility);
        hardButton.setVisibility(visibility);
        easyButton.setVisibility(visibility);
    }





}