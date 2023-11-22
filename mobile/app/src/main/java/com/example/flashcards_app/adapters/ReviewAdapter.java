package com.example.flashcards_app.adapters;

import android.animation.AnimatorInflater;
import android.animation.AnimatorSet;
import android.annotation.SuppressLint;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.example.flashcards_app.R;
import com.example.flashcards_app.activities.ReviewActivity;
import com.example.flashcards_app.dialogs.DeleteCardDialog;
import com.example.flashcards_app.dialogs.EditCardDialog;
import com.example.flashcards_app.models.Animator;
import com.example.flashcards_app.models.Review;
import com.example.flashcards_app.util.ViewModelAdapterMethods;
import com.example.flashcards_app.viewmodel.ViewModelLogic.Review.AudioCard;


import androidx.annotation.NonNull;
import androidx.fragment.app.FragmentActivity;
import androidx.fragment.app.FragmentManager;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;
import java.util.List;

public class ReviewAdapter extends RecyclerView.Adapter<ReviewAdapter.ReviewHolder> {

    private List<Review> reviews = new ArrayList<>();

    private OnDeleteCardButtonListener onDeleteCardButtonListener;
    private OnEditCardButtonListener onEditCardButtonListener;


    @NonNull
    @Override
    public ReviewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_new_card_review, parent, false);
        return new ReviewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(@NonNull ReviewHolder holder, int position) {
        Review currentReview = this.reviews.get(position);
        holder.frontTextCard.setText(currentReview.getFrontText());
        holder.backTextCard.setText(currentReview.getBackText());


        holder.frontCard.setOnClickListener(v -> {
            holder.animator.makeAnimationRight();
            ReviewActivity.setVisibilityDifficultButtons(true);
            holder.setAnimatorState(false);
        });
        holder.backCard.setOnClickListener(v -> {
            holder.animator.makeAnimationLeft();
            holder.setAnimatorState(true);
        });

        holder.audioButton.setOnClickListener(v -> {
            holder.speakAudio();
        });

        holder.editButton.setOnClickListener(v -> {
            onEditCardButtonListener.editCard();
        });

        holder.deleteButton.setOnClickListener(v -> {
            onDeleteCardButtonListener.deleteCard(position);
        });

    }
    @Override
    public void onViewRecycled(@NonNull ReviewHolder holder) {
        super.onViewRecycled(holder);
        holder.resetAnimatorState();
    }

    @Override
    public int getItemCount() {
        return reviews.size();
    }

    @SuppressLint("NotifyDataSetChanged")
    public void setReviews(List<Review> newReviews) {
        reviews = newReviews;
        notifyDataSetChanged();

    }

    public interface OnDeleteCardButtonListener {

        void deleteCard(int position);
    }

    public void setOnDeleteCardButtonListener(OnDeleteCardButtonListener onDeleteCardButtonListener) {
        this.onDeleteCardButtonListener = onDeleteCardButtonListener;
    }

    public interface OnEditCardButtonListener {
        void editCard();
    }

    public void setOnEditCardButtonListener(OnEditCardButtonListener onEditCardButtonListener) {
        this.onEditCardButtonListener = onEditCardButtonListener;
    }


    public static class ReviewHolder extends RecyclerView.ViewHolder {
        private View frontCard;
        private View backCard;
        private TextView frontTextCard;
        private TextView backTextCard;
        private Animator animator;
        private boolean isFront = true;
        private AudioCard audioCard;
        private Button deleteButton;
        private Button audioButton;
        private Button editButton;


        public ReviewHolder(@NonNull View itemView) {
            super(itemView);

            frontTextCard = itemView.findViewById(R.id.frontCardReviewText);
            backTextCard  = itemView.findViewById(R.id.backCardReviewText);
            frontCard     = itemView.findViewById(R.id.frontCardView);
            backCard      = itemView.findViewById(R.id.backCardView);
            audioButton   = itemView.findViewById(R.id.audio_button);
            editButton    = itemView.findViewById(R.id.edit_button);
            deleteButton  = itemView.findViewById(R.id.delete_button);

             this.animator = new Animator(itemView.getContext(),
                    (AnimatorSet) AnimatorInflater.loadAnimator(itemView.getContext(), R.animator.front_animator_anticlockwise),
                    (AnimatorSet) AnimatorInflater.loadAnimator(itemView.getContext(), R.animator.back_animator_anticlockwise),
                    (AnimatorSet) AnimatorInflater.loadAnimator(itemView.getContext(), R.animator.front_animator_clockwise),
                    (AnimatorSet) AnimatorInflater.loadAnimator(itemView.getContext(), R.animator.back_animator_clockwise),
                    this.frontCard, this.backCard
            );

            audioCard = new AudioCard(itemView.getContext());

        }

        public String getFrontTextCard() {
            return (String) this.frontTextCard.getText();
        }

        public void resetAnimatorState() {
            if (!this.isFront) {
                this.animator.makeAnimationLeft();
                this.isFront = true;
            }
        }

        public boolean getIsTrue() {
            return isFront;
        }
        public void setAnimatorState(boolean isFront) {
            this.isFront = isFront;
        }

        private void speakAudio() {
            this.audioCard.speak(getFrontTextCard());
        }

        private void cleanAudio() {
            if (audioCard != null) {
                audioCard.shutDown();
            }

        }
    }

}
