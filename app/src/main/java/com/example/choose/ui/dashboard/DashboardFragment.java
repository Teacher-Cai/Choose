package com.example.choose.ui.dashboard;

import android.content.Context;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextUtils;
import android.text.TextWatcher;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;

import com.example.choose.R;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.net.URLDecoder;
import java.net.URLEncoder;

public class DashboardFragment extends Fragment {


    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_dashboard, container, false);
        EditText editText = view.findViewById(R.id.e_t);
        String str = load();
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

        Button button = view.findViewById(R.id.save);
        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                saveFile(editText.getText().toString());
            }
        });

        return view;
    }

    /***
     * 从文件中加载
     * @return
     */
    public String load() {
        FileInputStream fis = null;
        BufferedReader reader = null;
        StringBuilder content = new StringBuilder();
        try {
            fis = getActivity().openFileInput("items.txt");
            reader = new BufferedReader(new InputStreamReader(fis));
            String str;

            while ((str = reader.readLine()) != null) {
                content.append(str);
            }
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            if (reader != null) {
                try {
                    reader.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
            if (fis != null) {
                try {
                    fis.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }
        return URLDecoder.decode(content.toString());
    }

    /***
     * 保存到文件
     * @param str
     */
    public void saveFile(String str) {
        FileOutputStream fos = null;
        BufferedWriter writer = null;

        try {
            fos = getActivity().openFileOutput("items.txt", Context.MODE_PRIVATE);
            writer = new BufferedWriter(new OutputStreamWriter(fos));
            try {
                str = URLEncoder.encode(str);
                writer.write(str);
                Toast.makeText(getContext(), "保存成功", Toast.LENGTH_SHORT).show();
            } catch (IOException e) {
                e.printStackTrace();
            }
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } finally {
            if (writer != null) {
                try {
                    writer.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
            if (fos != null) {
                try {
                    fos.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }
    }
}