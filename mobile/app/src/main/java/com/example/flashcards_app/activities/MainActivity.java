package com.example.flashcards_app.activities;

import android.content.Intent;
import android.os.Bundle;
import android.view.Gravity;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.PopupWindow;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.viewpager2.widget.ViewPager2;

import com.example.flashcards_app.databinding.ActivityMainBinding;
import com.example.flashcards_app.fragments.DecksFragment;
import com.example.flashcards_app.fragments.FriendsFragment;
import com.example.flashcards_app.R;
import com.example.flashcards_app.adapters.ViewPagerAdapter;
import com.google.android.material.tabs.TabLayoutMediator;

public class MainActivity extends AppCompatActivity {

    ConstraintLayout rootLayout;
    private ActivityMainBinding binding;
    private Button createDeck;
    private Button addFriends;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        rootLayout = findViewById(R.id.root_layout);
        binding = ActivityMainBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        createDeck = findViewById(R.id.btn_create_deck);
        addFriends = findViewById(R.id.btn_add_friends);

        configTabLayout();
    }

    private void configTabLayout() {
        ViewPagerAdapter adapter = new ViewPagerAdapter(this);
        binding.viewPager.setAdapter(adapter);

        adapter.addFragment(new DecksFragment(), "Baralhos");
        adapter.addFragment(new FriendsFragment(), "Amigos");

        binding.viewPager.setOffscreenPageLimit(adapter.getItemCount());
        binding.viewPager.registerOnPageChangeCallback(new ViewPager2.OnPageChangeCallback() {
            @Override
            public void onPageSelected(int position) {
                if (position == 0) {
                    binding.btnAddFriends.setVisibility(View.INVISIBLE);
                    binding.btnCreateDeck.setVisibility(View.VISIBLE);
                } else if (position == 1) {
                    binding.btnCreateDeck.setVisibility(View.INVISIBLE);
                    binding.btnAddFriends.setVisibility(View.VISIBLE);
                }
            }
        });

        TabLayoutMediator mediator = new TabLayoutMediator(binding.tabLayout, binding.viewPager, (tab, position) -> {

            tab.setText(adapter.getTitle(position));
        });

        mediator.attach();
    }

    /* Change page methods */

    public void accessProfile(View v) {
        Intent in = new Intent(MainActivity.this, ProfileActivity.class);
        startActivity(in);
    }

    public void accessNotifications(View v) {
        Intent in = new Intent(MainActivity.this, NotificationActivity.class);
        startActivity(in);
    }

    /* Get view elements */
    public Button getCreateDeckButton() {
        return createDeck;
    }

    public Button getAddFriendsButton() {
        return addFriends;
    }

}