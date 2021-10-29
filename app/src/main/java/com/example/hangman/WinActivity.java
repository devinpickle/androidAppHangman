package com.example.hangman;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

public class WinActivity extends AppCompatActivity {

    TextView winText; // Displays the win/lose message
    TextView guessCount; // Displays the total number of bad guesses
    ImageView winImg; // Displays status of the hangman

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_win);

        // Set up the text views
        winText = findViewById(R.id.winText);
        guessCount = findViewById(R.id.guessCount);

        // Unpack the bundle
        Bundle bundle = getIntent().getExtras();
        String winStatus = bundle.getString("winStatus");
        String numGuesses = bundle.getString("numGuesses");
        winText.setText(winStatus);
        guessCount.setText(numGuesses);

        // Display Win/lose img
        winImg = (ImageView) findViewById(R.id.winImage);
        if (winStatus.equals("You Guessed the Word!")) {
            winImg.setImageResource(R.drawable.hangman0);
        } else {
            winImg.setImageResource(R.drawable.hangman6);
        }

        configureBackButton();
    }
    private void configureBackButton() {
        // Ends win screen activity and returns to first screen
        Button backButton = (Button) findViewById(R.id.buttonPlayAgain);
        backButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
            }
        });
    }
}