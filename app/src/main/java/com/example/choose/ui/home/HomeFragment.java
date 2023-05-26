package com.example.choose.ui.home;

import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;

import com.example.choose.R;
import com.example.choose.ReadWriteUtils;
import com.example.choose.WheelView;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

public class HomeFragment extends Fragment {
    private String currentChosenFileName = "cue1";

    private int itemsCnt = 20;
    private final List<String> lists = new ArrayList<>();

    private List<Double> weightLists = new ArrayList<>();

    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {

        View view = inflater.inflate(R.layout.fragment_home, container, false);
        WheelView wheelView = view.findViewById(R.id.wheelView);

        String str = ReadWriteUtils.load(getActivity(), currentChosenFileName);
        if (TextUtils.isEmpty(str)) {
            for (int i = 0; i < itemsCnt; i++) {
                lists.add("No cue:" + i);
            }
        } else {
            String[] lines = str.split("\r\n|\r|\n");
            itemsCnt = lines.length;
            if (itemsCnt == 0) {
                for (int i = 0; i < 20; i++) {
                    lists.add("No cue:" + i);
                }
            } else if (itemsCnt < 11) {
                // extend to enough items, need at least 11 items
                for (int i = 0; i < 11 /itemsCnt + 1; i++) {
                    lists.addAll(Arrays.asList(lines));
                }
            } else {
                lists.addAll(Arrays.asList(lines));
            }
        }
        transformWeight();

        wheelView.lists(lists).fontSize(45).showCount(9).selectTip("SELECTED").select(0).listener(new WheelView.OnWheelViewItemSelectListener() {
            @Override
            public void onItemSelect(int index) {
                Log.d("cc", "current select:" + wheelView.getSelectItem() + " index :" + index + ",result=" + lists.get(index));
            }
        }).build();

        // START BUTTON
        Button startButton = view.findViewById(R.id.start);
        startButton.setOnClickListener(view1 -> {
            Toast.makeText(view1.getContext(), "CALLING...", Toast.LENGTH_SHORT).show();
            wheelView.showSpecificalItemSlide(getLocationByRandom());  // new Random().nextInt(lists.size())
        });

        // radio button
        RadioGroup radgroup = view.findViewById(R.id.radioGroupH);
        //第一种获得单选按钮值的方法
        //为radioGroup设置一个监听器:setOnCheckedChanged()
        radgroup.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(RadioGroup group, int checkedId) {
                RadioButton radbtn = view.findViewById(checkedId);
                currentChosenFileName = radbtn.getText().toString();
                String str = ReadWriteUtils.load(getActivity(), currentChosenFileName);
                lists.clear();
                if (TextUtils.isEmpty(str)) {
                    for (int i = 0; i < 20; i++) {
                        lists.add("No cue:" + i);
                    }
                } else {
                    String[] lines = str.split("\r\n|\r|\n");
                    itemsCnt = lines.length;
                    if (itemsCnt == 0) {
                        for (int i = 0; i < 20; i++) {
                            lists.add("No cue:" + i);
                        }
                    } else if (itemsCnt < 11) {
                        // extend to enough items, need at least 11 items
                        for (int i = 0; i < 11 / itemsCnt + 1; i++) {
                            lists.addAll(Arrays.asList(lines));
                        }
                    } else {
                        lists.addAll(Arrays.asList(lines));
                    }
                }
                transformWeight();
                wheelView.lists(lists).fontSize(45).showCount(9).selectTip("SELECTED").select(0).listener(new WheelView.OnWheelViewItemSelectListener() {
                    @Override
                    public void onItemSelect(int index) {
                        Log.d("cc", "current select:" + wheelView.getSelectItem() + " index :" + index + ",result=" + lists.get(index));
                    }
                }).build();
                wheelView.refresh();

            }
        });

        return view;
    }

    private int getLocationByRandom() {
        Double aRandomDouble = Math.random();
        for (int i = 0; i < weightLists.size(); i++) {
            if (weightLists.get(i) >= aRandomDouble) {
                return i;
            }
        }
        return 0;
    }

    private void transformWeight() {
        weightLists.clear();
        Double lastNum;
        for (String aItem : lists) {
            try {
                String[] lines = aItem.split(",|，");
                lastNum = Double.valueOf(lines[lines.length - 1]);
            } catch (Exception e) {
                lastNum = 1.0d;
            }
            if (weightLists.size() == 0) {
                weightLists.add(lastNum);
            } else {
                weightLists.add(lastNum + weightLists.get(weightLists.size() - 1));
            }
        }

        // normalized
        Double sum = weightLists.get(weightLists.size() - 1);
        weightLists = weightLists.stream().map(i -> i / sum).collect(Collectors.toList());
        Log.d("weightLists", weightLists.stream().map(i -> String.valueOf(i.toString())).collect(Collectors.joining(",")));
    }

}