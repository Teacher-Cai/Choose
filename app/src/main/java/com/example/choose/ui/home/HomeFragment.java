package com.example.choose.ui.home;

import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;

import com.example.choose.R;
import com.example.choose.WheelView;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

public class HomeFragment extends Fragment {

    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {
        HomeViewModel homeViewModel =
                new ViewModelProvider(this).get(HomeViewModel.class);

        View view = inflater.inflate(R.layout.fragment_home, container, false);
        final WheelView wheelView = (WheelView) view.findViewById(R.id.wheelView);
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
        Button startButton = view.findViewById(R.id.start);
        startButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Toast.makeText(view.getContext(), "CALLING...", Toast.LENGTH_SHORT).show();
                wheelView.showSpecificalItemSlide(new Random().nextInt(20));
            }
        });

        return view;
    }

}