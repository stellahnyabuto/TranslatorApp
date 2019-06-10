package com.example.translatorapp;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;

import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import java.io.BufferedReader;
import java.io.FileNotFoundException;

import java.io.IOException;

import java.io.InputStreamReader;
import java.util.ArrayList;


public class MainActivity extends AppCompatActivity {
    private RecyclerView recyclerView;
    private MyAdapter mAdapter;
    private RecyclerView.LayoutManager layoutManager;

    private Button btn_translate;
    private Button btn_exit;
    private EditText editText;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        recyclerView = findViewById(R.id.result);
        recyclerView.setHasFixedSize(true);
        layoutManager = new LinearLayoutManager(this);
        recyclerView.setLayoutManager(layoutManager);

        btn_translate = findViewById(R.id.btn_translate);
        btn_exit = findViewById(R.id.btn_exit);
        editText = findViewById(R.id.editText);

        mAdapter = new MyAdapter();
        recyclerView.setAdapter(mAdapter);


        btn_translate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mAdapter.setDataSet(
                        translations(editText.getText().toString()));
                mAdapter.notifyDataSetChanged();

            }
        });

        btn_exit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });

    }

    String[] translations(String word){
        ArrayList<String> swa_words = new ArrayList<>();
        try (
                BufferedReader br = new BufferedReader(
                        new InputStreamReader(getAssets().open("data.csv")))){
            String line;
            while ((line = br.readLine()) != null) {
                String[] values = line.split(",");
                if (values[1].equals(word)){
                    swa_words.add(values[0]);
                }

            }

            //ArrayList to String array conversion
            String[] arr = new String[swa_words.size()];
            for (int i = 0; i < swa_words.size(); i++) {
                // Assign each value to String array
                arr[i] = swa_words.get(i);
            }

            return arr;

        }
        catch (FileNotFoundException e) {
            e.printStackTrace();
        }
        catch (IOException e) {
            e.printStackTrace();
        }

        return new String[]{"Empty"};

    }
}
