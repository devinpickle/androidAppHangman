package com.example.hangman;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        configureNextButton();

        configureToastButton();
    }

    private void configureNextButton() {
        Button nextButton = (Button) findViewById(R.id.nextButton);
        nextButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                EditText inputText = findViewById(R.id.inputText);
                String typed_word = inputText.getText().toString();
                Intent i = new Intent(MainActivity.this, SecondActivity.class);
                Bundle bundle = new Bundle();
                bundle.putString("stuff", typed_word);
                i.putExtras(bundle);
                startActivity(i);
            }
        });

    }

    private void configureToastButton() {
        Button toastButton = (Button) findViewById(R.id.toastButton);
        toastButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                EditText inputText = findViewById(R.id.inputText);
                String typed_word = inputText.getText().toString();
                Toast.makeText(MainActivity.this, typed_word, Toast.LENGTH_SHORT).show();
            }
        });

    }
}