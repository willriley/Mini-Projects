package com.google.engedu.ghost;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.KeyEvent;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;
import java.io.IOException;
import java.io.InputStream;
import java.util.Random;

public class GhostActivity extends AppCompatActivity {
    private static final int MIN_WORD_LENGTH = 3;
    private static final String COMPUTER_TURN = "Computer's turn";
    private static final String USER_TURN = "Your turn";
    private GhostDictionary dictionary;
    private boolean yourTurn = false;
    private boolean gameOver = false;
    private Random random = new Random();

    private String currentWord = "";
    private TextView wordBox;
    private TextView status;
    private TextView userScoreBox;
    private TextView cpuScoreBox;
    private int userScore = 0;
    private int cpuScore = 0;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_ghost);
        try {
            InputStream inputStream = getAssets().open("words.txt");
            dictionary = new FastDictionary(inputStream);
        } catch (IOException e) {
            Toast toast = Toast.makeText(this, "Could not load dictionary", Toast.LENGTH_LONG);
            toast.show();
        }

        wordBox = (TextView)findViewById(R.id.wordBox);
        status = (TextView)findViewById(R.id.status);
        userScoreBox = (TextView)findViewById(R.id.userScoreBox);
        cpuScoreBox = (TextView)findViewById(R.id.cpuScoreBox);

        if (savedInstanceState != null) {
            currentWord = savedInstanceState.getString("fragment");
            status.setText(savedInstanceState.getString("status"));
            userScore = savedInstanceState.getInt("user score");
            cpuScore = savedInstanceState.getInt("cpu score");
            updateScores();
        } else { onStart(null); }
    }

    @Override
    public void onSaveInstanceState(Bundle savedInstanceState) {
        // Saves the fragment, status msg, and scores upon suspension
        savedInstanceState.putString("fragment", currentWord);
        savedInstanceState.putString("status", (String)status.getText());
        savedInstanceState.putInt("user score", userScore);
        savedInstanceState.putInt("cpu score", cpuScore);

        super.onSaveInstanceState(savedInstanceState);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_ghost, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();
        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }
        return super.onOptionsItemSelected(item);
    }

    // Called upon booting + whenever reset is pressed
    public boolean onStart(View view) {
        gameOver = false;
        yourTurn = random.nextBoolean();
        currentWord = "";
        wordBox.setText("");
        updateScores();
        if (yourTurn) {
            status.setText(USER_TURN);
        } else {
            status.setText(COMPUTER_TURN);
            computerTurn();
        }
        return true;
    }

    @Override
    public boolean onKeyUp(int keyCode, KeyEvent event) {
        if (gameOver) { return false; } // user can't type after game's over

        if(keyCode >= 29 && keyCode <= 54) { // if key typed is a letter
            // concatenate that letter to currentWord
            currentWord += (char)event.getUnicodeChar();
            wordBox.setText(currentWord);
            computerTurn();
        }
        return super.onKeyUp(keyCode, event);
    }

    /*
     *  General purpose method that's called both by the user and the CPU.
     *  Challenges are best reserved for when you think the other player's
     *  formed a word that is either 1) valid + at least 4 letters or 2) impossible
     *  to form other words from.
     */
    public void challenge(View v) {
        if (yourTurn) { // if the user pressed it
            if (validWord() || dictionary.getAnyWordStartingWith(currentWord) == null)
                gameOver(true);
            else
                gameOver(false);
        } else {
            // the CPU is infallible when challenging
            gameOver(false);
        }
    }

    private void computerTurn() {
        yourTurn = false;
        String possibleWord = dictionary.getAnyWordStartingWith(currentWord);

        if (validWord()) { // if you've already completed a valid word, CPU wins
            challenge(null);
        } else {
            if (possibleWord != null) { // if you can make a word
                // add the next char in word after the existing prefix
                currentWord += possibleWord.charAt(currentWord.length());
                wordBox.setText(currentWord);
                status.setText(USER_TURN);
                yourTurn = true;
            } else { challenge(null); } // if you can't make a word, CPU wins
        }
    }

    private boolean validWord() {
        return currentWord.length() >= MIN_WORD_LENGTH && dictionary.isWord(currentWord);
    }

    private void gameOver(boolean userWon) {
        gameOver = true;
        if (userWon) {
            status.setText("You won!");
            userScore++;
        } else {
            status.setText("CPU won");
            cpuScore++;
        }
        updateScores();
    }

    private void updateScores() {
        userScoreBox.setText("You – " + String.valueOf(userScore));
        cpuScoreBox.setText("CPU – " + String.valueOf(cpuScore));
    }

}
