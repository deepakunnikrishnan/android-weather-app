package com.androidnerds.newsapp;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.widget.TextView;

import com.androidnerds.cardcarousel.CarouselView;

import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        List<Card> cardList = new ArrayList<>();
        cardList.add(new Card("1"));
        cardList.add(new Card("2"));
        cardList.add(new Card("3"));
        cardList.add(new Card("4"));
        cardList.add(new Card("5"));
        cardList.add(new Card("6"));
        cardList.add(new Card("7"));
        cardList.add(new Card("8"));
        CarouseViewAdapter adapter = new CarouseViewAdapter();

        TextView textView = findViewById(R.id.textView);
        CarouselView carouselView = findViewById(R.id.carouselView);
        carouselView.setItemSelectedListener(position -> textView.setText("Position: "+(position+1)));

        carouselView.setAdapter(adapter);
        adapter.submitList(cardList);
    }
}