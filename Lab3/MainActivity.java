package com.example.myapplication;

import android.annotation.SuppressLint;
import android.graphics.Color;
import android.os.Bundle;
import android.os.CountDownTimer;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import androidx.activity.ComponentActivity;


import java.util.Random;

public class MainActivity extends ComponentActivity {

    private String[] colors = {"Червоний", "Зелений", "Синій", "Жовтий", "Білий", "Чорний"};

    private int[] colorCodes = {Color.RED, Color.GREEN, Color.BLUE, Color.YELLOW, Color.WHITE, Color.BLACK};

    private Random random = new Random();

    private TextView questionTextView;
    private TextView colorTextView;
    private TextView timerTextView;
    private TextView scoreTextView;
    private Button yesButton;
    private Button noButton;

    private int colorIndex;
    private int wordIndex;
    private int score;
    private boolean isPlaying;
    private CountDownTimer timer;

    @SuppressLint("MissingInflatedId")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        questionTextView = findViewById(R.id.questionTextView);
        colorTextView = findViewById(R.id.colorTextView);
        timerTextView = findViewById(R.id.timerTextView);
        scoreTextView = findViewById(R.id.scoreTextView);
        yesButton = findViewById(R.id.yesButton);
        noButton = findViewById(R.id.noButton);

        timer = new CountDownTimer(180000, 1000) {
            @Override
            public void onTick(long millisUntilFinished) {
                timerTextView.setText("Час: " + millisUntilFinished / 1000 + " с");
            }

            @Override
            public void onFinish() {
                endGame();
            }
        };

        yesButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (isPlaying) {
                    checkAnswer(true);
                }
            }
        });

        noButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (isPlaying) {
                    checkAnswer(false);
                }
            }
        });

        startGame();
    }

    private void startGame() {
        score = 0;
        scoreTextView.setText("Рахунок: " + score);
        timer.start();
        isPlaying = true;
        generateQuestion();
    }

    private void endGame() {
        timer.cancel();
        isPlaying = false;
        Toast.makeText(this, "Гра закінчена. Ви набрали " + score + " балів.", Toast.LENGTH_LONG).show();
    }

    private void generateQuestion() {
        colorIndex = random.nextInt(colors.length);
        wordIndex = random.nextInt(colors.length);
        colorTextView.setText(colors[wordIndex]);
        colorTextView.setTextColor(colorCodes[colorIndex]);
    }

    private void checkAnswer(boolean answer) {
        boolean isMatch = colorIndex == wordIndex;
        if (answer == isMatch) {
            score++;
            Toast.makeText(this, "Вірно!", Toast.LENGTH_SHORT).show();
        } else {
            Toast.makeText(this, "Невірно!", Toast.LENGTH_SHORT).show();
        }
        scoreTextView.setText("Рахунок: " + score);
        generateQuestion();
    }
}
