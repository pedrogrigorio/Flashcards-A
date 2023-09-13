package com.example.flashcards_app;

import androidx.appcompat.app.AppCompatActivity;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.viewpager2.widget.ViewPager2;

import android.content.Intent;
import android.os.Bundle;
import android.view.Gravity;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.PopupWindow;
import android.widget.Space;
import android.widget.TextView;

import com.example.flashcards_app.databinding.ActivityMainBinding;
import com.google.android.material.tabs.TabLayout;
import com.google.android.material.tabs.TabLayoutMediator;

public class MainActivity extends AppCompatActivity {

    ConstraintLayout parent;
    private ActivityMainBinding binding;
    private Button createDeck;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        parent = findViewById(R.id.parent_layout);
        binding = ActivityMainBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        createDeck = findViewById(R.id.btn_create_deck);
//        createDeck.setOnClickListener(v -> {
//            showCreateDeckPopupWindow();
//        });


//        Button editDeck = findViewById(R.id.btn_edit_deck);
//        editDeck.setOnClickListener(v -> {
//            showEditDeckPopupWindow();
//        });

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

    public void accessProfile(View v) {
        Intent in = new Intent(MainActivity.this, ProfileActivity.class);
        startActivity(in);
    }

    public void accessNotifications(View v) {
        Intent in = new Intent(MainActivity.this, NotificationActivity.class);
        startActivity(in);
    }

    public void showCreateDeckPopupWindow() {
//        System.out.println(binding.viewPager.getCurrentItem());
        View view = View.inflate(this, R.layout.create_deck_popup, null);
        ImageView close = view.findViewById(R.id.create_deck_close_popup);
        TextView cancel = view.findViewById(R.id.cancel_create_deck);

        int width = ViewGroup.LayoutParams.MATCH_PARENT;
        int height = LinearLayout.LayoutParams.MATCH_PARENT;

        PopupWindow popupWindow = new PopupWindow(view, width, height, false);

        popupWindow.showAtLocation(parent, Gravity.CENTER, 0, 0);

        close.setOnClickListener(v -> {
            popupWindow.dismiss();
        });

        cancel.setOnClickListener(v -> {
            popupWindow.dismiss();
        });
    }

    public Button getCreateDeckButton() {
        return createDeck;
    }
    public void showEditDeckPopupWindow() {
        View view = View.inflate(this, R.layout.edit_deck_popup, null);
        ImageView close = view.findViewById(R.id.edit_deck_close_popup);
        TextView cancel = view.findViewById(R.id.cancel_edit_deck);

        int width = ViewGroup.LayoutParams.MATCH_PARENT;
        int height = LinearLayout.LayoutParams.MATCH_PARENT;

        PopupWindow popupWindow = new PopupWindow(view, width, height, false);

        popupWindow.showAtLocation(parent, Gravity.CENTER, 0, 0);

        close.setOnClickListener(v -> {
            popupWindow.dismiss();
        });

        cancel.setOnClickListener(v -> {
            popupWindow.dismiss();
        });
    }
}