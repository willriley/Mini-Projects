package com.example.android.scarnesdice;

import android.os.Handler;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

// TODO: add delay, allow game to end

public class MainActivity extends AppCompatActivity {

    private TextView yourScore, cpuScore, turnScore;
    private ImageView dice;
    private int[] images = {R.drawable.dice1, R.drawable.dice2, R.drawable.dice3,
        R.drawable.dice4, R.drawable.dice5, R.drawable.dice6};

    private int totalUserScore = 0;
    private int totalCompScore = 0;
    private int userTurnScore = 0;
    private boolean yourTurn = true;
    private int currentUserScore = 0;
    private int currentCompScore = 0;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        yourScore = (TextView)findViewById(R.id.yourScore);
        cpuScore = (TextView)findViewById(R.id.cpuScore);
        turnScore = (TextView)findViewById(R.id.turnScore);
        dice = (ImageView)findViewById(R.id.diceImg);
    }

    /*
     * General purpose method that's called whenever
     * it's the CPU's turn or the user presses it.
     * It generates a random die number, updates the dice image
     * to reflect that number, and updates the scores.
     */
    public void roll(View view) {
        int rand = random(); // rolls dice
        dice.setImageResource(images[rand-1]); // resets image
        update(rand);
    }

    /*
     * General purpose method that's called whenever
     * the CPU's turn ends or the user presses it.
     * It changes turns and updates scores.
     */
    public void hold(View view) {
        if (yourTurn) {
            totalUserScore += currentUserScore;
            cpuTurn();
        } else {
            if (currentCompScore >= 20) {
                totalCompScore += currentCompScore;
            }
            currentCompScore = 0;
            yourTurn = true;
        }
        updateLabels();
    }

    // Called when user clicks reset button or when the game's over
    public void reset(View view) {
        totalUserScore = totalCompScore = userTurnScore = currentUserScore = currentCompScore = 0;
        yourTurn = true;
        updateLabels();
    }

    private void cpuTurn() {
        currentUserScore = 0;
        yourTurn = false;

        Button roll = (Button)findViewById(R.id.rollButton);
        Button hold = (Button)findViewById(R.id.holdButton);
        roll.setEnabled(false); // disables roll & hold buttons
        hold.setEnabled(false);
        roll(null); // rolls once

        final Handler h = new Handler();
        h.postDelayed(new Runnable() {
            @Override
            public void run() {
                if (currentCompScore < 20 && !yourTurn) {
                    roll(null);
                    h.postDelayed(this,500);
                } else if (currentCompScore >= 20) {
                    hold(null);
                    h.removeCallbacks(this);
                }
            }
        }, 500);

        roll.setEnabled(true);
        hold.setEnabled(true);
    }

    // The logic behind everything.
    private void update(int roll) {
        switch (roll) {
            case 1:
                if (yourTurn) {
                    userTurnScore = 0;
                    cpuTurn();
                } else { hold(null); }
                break;
            default:
                if (yourTurn) {
                    currentUserScore += roll;
                    userTurnScore++;
                } else { currentCompScore += roll; }
        }

        updateLabels();
        if (haveWinner()) { gameOver(); }
    }

    /*
     * Called whenever any player's score eclipses 100.
     * It displays who won, temporarily disables the buttons,
     * and resets after 2000ms.
     */
    public void gameOver() {
        TextView endMessage = (TextView)findViewById(R.id.endMessage);
        if (totalCompScore + currentCompScore >= 100) {
            endMessage.setText("You Lost");
        } else { endMessage.setText("You won!"); }

        final Button roll = (Button)findViewById(R.id.rollButton);
        final Button hold = (Button)findViewById(R.id.holdButton);
        final Button reset = (Button)findViewById(R.id.resetButton);
        final Handler h = new Handler();
        h.postDelayed(new Runnable() {
            @Override
            public void run() {
                roll.setEnabled(false);
                hold.setEnabled(false);
                reset.setEnabled(false);
                reset(null);
            }
        }, 2000);

        roll.setEnabled(true);
        hold.setEnabled(true);
        reset.setEnabled(true);
    }

    private boolean haveWinner() {
        int cpu = totalCompScore + currentCompScore;
        int user = totalUserScore + currentUserScore;
        return user >= 100 || cpu >=100;
    }

    private void updateLabels() {
        yourScore.setText(String.format("Your score: %s", String.valueOf(totalUserScore + currentUserScore)));
        cpuScore.setText(String.format("Computer score: %s", String.valueOf(totalCompScore + currentCompScore)));
        turnScore.setText(String.format("Your turn score: %s", String.valueOf(userTurnScore)));
    }

    private int random() {
        return (int)(Math.random()*6)+1;
    }
}
