package com.example.hangman;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;

public class SecondActivity extends AppCompatActivity {
    Integer num_guesses = 0;
    String word_to_guess; // The actual word we want to guess
    String covered_word; // Looks like -> "______"
    String guessed_letters = ""; // Keep track of which letters the player has guessed
    TextView validate_guess; // Displays feedback on player's guess
    TextView coveredWordText; // Displays the covered word player is trying to guess at the top
    TextView showGuessedLetters; // Letters player has guessed already
    TextView show_guesses; // Displays number of bad guesses
    ImageView hangmanImg; // The main image

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_second);

        // Get the string from previous screen
        Bundle bundle = getIntent().getExtras();
        String transferred_text = bundle.getString("stuff");

        // Draw the hangman picture
        hangmanImg = (ImageView) findViewById(R.id.imageView);
        hangmanImg.setImageResource(R.drawable.hangman0);

        coveredWordText = findViewById(R.id.coveredWordText);
        // Build the "_____" based on the transferred text
        covered_word = "";
        for (int i = 0; i < transferred_text.length(); i++) {
            covered_word += "_"; // Add a blank for each letter in the word
        }
        coveredWordText.setText(covered_word); // Display the covered word
        word_to_guess = transferred_text;

        // Show Guessed Letters
        showGuessedLetters = findViewById(R.id.guessedLetters);
        showGuessedLetters.setText("Guessed Letters: ");

        // Get show_guesses view
        show_guesses = findViewById(R.id.num_guesses);

        configureBackButton();

        configureSubmitButton();
    }

    private void configureBackButton() {
        // Ends second screen activity and goes back to first screen
        Button backButton = (Button) findViewById(R.id.backButton);
        backButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
            }
        });
    }

    private void configureSubmitButton() {
        // Submit button increments the guess counter and checks
        // if the user guess is equal to the word
        Button submitButton = (Button) findViewById(R.id.submitButton);
        submitButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                // Get the player guess
                EditText input_guess = findViewById(R.id.inputGuess);
                String player_guess = input_guess.getText().toString();

                // Set up input validator view
                validate_guess = findViewById(R.id.validate_guess);

                // Check if the input is one letter
                if (checkForOneLetter(player_guess)) {
                    validate_guess.setText("Valid Guess");
                    // Check if the player has guessed the letter before
                    if (letterIsGuessed(player_guess) == false) {
                        guessed_letters += player_guess; // Add letter to list if not guessed before
                    } else {
                        return;
                    }
                    // Check if the letter is in the word
                    uncoverWord(player_guess);

                } else {
                    validate_guess.setText("Too many characters");
                }

                showGuessedLetters.setText("Guessed Letters: " + guessed_letters);

            }
        });
    }

    private boolean checkForOneLetter(String player_guess) {
        // Check if the player entered one letter
        if (player_guess.length() == 1) { return true; } else { return false; }
    }
    private void uncoverWord(String player_guess) {
        // Replace the "_"'s in the word with corresponding letters
        String oldCoveredWord = covered_word;
        char player_guessChar = player_guess.charAt(0);
        for (int i = 0; i < word_to_guess.length(); i++) {
            int compare = Character.compare(player_guessChar, word_to_guess.charAt(i));
            if (compare == 0) {
                char[] c = covered_word.toCharArray();
                c[i] = player_guess.charAt(0);
                covered_word = String.valueOf(c);
                coveredWordText.setText(covered_word); // Display the newly uncovered word
            }
        }
        // Give feedback on whether the word has changed
        if (covered_word.equals(oldCoveredWord)) {
            validate_guess.setText("Bad Guess");
            // Increment the total number of bad player guesses
            num_guesses++;
            updateImage();
            show_guesses.setText("Bad Guesses: " + num_guesses.toString()); // update guess count view
            if (num_guesses >= 6) {
                endGame("You Lose :(");
            }
        } else if (covered_word.equals(word_to_guess)) {
            // Change to win screen
            endGame("You Guessed the Word!");
        } else {
            validate_guess.setText("Correct Guess");
        }
    }


    public void endGame(String winStatus) {
        // Transition to the final screen
        Intent i = new Intent(SecondActivity.this, WinActivity.class);
        Bundle bundle = new Bundle();
        bundle.putString("winStatus", winStatus);
        bundle.putString("numGuesses", "Bad Guesses: " + num_guesses.toString());
        i.putExtras(bundle);
        startActivity(i);
        // Since the player has won, finish activity for this screen. Otherwise the back
        // button on win screen would come back here
        finish();
    }

    public boolean letterIsGuessed(String player_guess) {
        // Check if the letter has been guessed already
        for (int i = 0; i < guessed_letters.length(); i++) {
            int compare = Character.compare(player_guess.charAt(0), guessed_letters.charAt(i));
            // If they match, give feedback, add to amount of times letter is guessed
            if (compare == 0) {
                validate_guess.setText("You've already guessed that letter");
                return true;
            }
        }
        return false;
    }

    public void updateImage() {
        // Show image based on how the player is doing
        if (num_guesses == 0) {
            hangmanImg.setImageResource(R.drawable.hangman0);
        } else if (num_guesses == 1) {
            hangmanImg.setImageResource(R.drawable.hangman1);
        } else if (num_guesses == 2) {
            hangmanImg.setImageResource(R.drawable.hangman2);
        } else if (num_guesses == 3) {
            hangmanImg.setImageResource(R.drawable.hangman3);
        } else if (num_guesses == 4) {
            hangmanImg.setImageResource(R.drawable.hangman4);
        } else if (num_guesses == 5) {
            hangmanImg.setImageResource(R.drawable.hangman5);
        } else if (num_guesses == 6) {
            hangmanImg.setImageResource(R.drawable.hangman6);
        }
    }
}