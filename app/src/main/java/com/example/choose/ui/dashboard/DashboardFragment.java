package com.example.choose.ui.dashboard;

import android.os.Bundle;
import android.text.Editable;
import android.text.TextUtils;
import android.text.TextWatcher;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.RadioGroup;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;

import com.example.choose.R;
import com.example.choose.ReadWriteUtils;

public class DashboardFragment extends Fragment {
    private String currentChosenFileName = "cue1";


    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_dashboard, container, false);
        EditText editText = view.findViewById(R.id.e_t);
        String str = ReadWriteUtils.load(getActivity(), currentChosenFileName);
        if (!TextUtils.isEmpty(str)) {
            editText.setText(str);
        }

        editText.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void afterTextChanged(Editable editable) {

            }
        });

        // save button
        Button button = view.findViewById(R.id.save);
        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                ReadWriteUtils.saveFile(editText.getText().toString(), getActivity(), getContext(), currentChosenFileName);
            }
        });

        // radio button
        RadioGroup radgroup = view.findViewById(R.id.radioGroup);
        //第一种获得单选按钮值的方法
        //为radioGroup设置一个监听器:setOnCheckedChanged()
        radgroup.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(RadioGroup group, int checkedId) {
                RadioButton radbtn = view.findViewById(checkedId);
                currentChosenFileName = radbtn.getText().toString();
                String str = ReadWriteUtils.load(getActivity(), currentChosenFileName);
                editText.setText(str);

            }
        });

        return view;
    }
}