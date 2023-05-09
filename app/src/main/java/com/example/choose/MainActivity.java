package com.example.choose;

import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        final WheelView wheelView = (WheelView) findViewById(R.id.wheelView);
        final List<String> lists = new ArrayList<>();
        for (int i = 0; i < 20; i++) {
            lists.add("test:" + i);
        }
        wheelView.lists(lists).fontSize(45).showCount(9).selectTip("SELECTED").select(0).listener(new WheelView.OnWheelViewItemSelectListener() {
            @Override
            public void onItemSelect(int index) {
                Log.d("cc", "current select:" + wheelView.getSelectItem() + " index :" + index + ",result=" + lists.get(index));
            }
        }).build();

        // START BUTTON
        Button startButton = findViewById(R.id.start);
        startButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Toast.makeText(getApplicationContext(), "CALLING...", Toast.LENGTH_SHORT).show();
                wheelView.showSpecificalItemSlide(new Random().nextInt(20));
            }
        });
    }
}