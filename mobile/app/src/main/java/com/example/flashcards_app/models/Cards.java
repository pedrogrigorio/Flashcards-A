package com.example.flashcards_app.models;


import android.animation.AnimatorSet;
import android.content.Context;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import com.example.flashcards_app.activities.ReviewActivity;

import java.util.Vector;


public class Cards {

    private TextView frontCardText;
    private TextView backCardText;
    private AudioCard audioCard;
    private Button easyButton;
    private Button goodButton;
    private Button hardButton;
    private Button audioButton;
    private  LoadDataCards loadDataCards;
    private Animator animator;



    public Cards(Context context,
                 TextView frontCardText,
                 TextView backCardText,
                 Button easyButton,
                 Button goodButton,
                 Button hardButton,
                 Button audioButton) {

        this.frontCardText     = frontCardText;
        this.easyButton        = easyButton;
        this.goodButton        = goodButton;
        this.hardButton        = hardButton;
        this.audioButton       = audioButton;
        this.backCardText      = backCardText;
        this.audioCard         =  new AudioCard(context);
        this.loadDataCards      = new LoadDataCards();
        this.loadDataCards.LoadCards();
        this.updateCard(1);
    }


    public int getCardAmount() { return this.loadDataCards.getAmount(); }
    public boolean easyButtonCommand(int index) {
        this.showControlDifficultButton(false);
        return this.updateCard(index);
    }

    public boolean goodButtonCommand(int index) {
        this.showControlDifficultButton(false);
        return this.updateCard(index);
    }

    public boolean hardButtonCommand(int index) {
        this.showControlDifficultButton(false);
        return this.updateCard(index);
    }

    public void showControlDifficultButton(boolean show) {
        if (show) {
            easyButton.setVisibility(Button.VISIBLE);
            goodButton.setVisibility(Button.VISIBLE);
            hardButton.setVisibility(Button.VISIBLE);
            audioButton.setEnabled(false);
        }else {
            easyButton.setVisibility(Button.INVISIBLE);
            goodButton.setVisibility(Button.INVISIBLE);
            hardButton.setVisibility(Button.INVISIBLE);
            audioButton.setEnabled(false);
        }
    }

    public void audioSpeak() {
        this.audioCard.speak(this.frontCardText.getText().toString());
    }

    private boolean updateCard(int request) {
        String[] data = this.loadDataCards.getDataCard(request);
        if (data == null) return false;
        else {
            this.setFrontCardText(data[0]);
            this.setBackCardText(data[1]);
        }

        return true;
    }
    private void setFrontCardText(String frontCardText) { this.frontCardText.setText(frontCardText); }
    private void setBackCardText(String backCardText) {this.backCardText.setText(backCardText); }

}
