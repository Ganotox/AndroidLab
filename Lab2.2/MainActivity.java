package com.example.myapplication;

import android.os.Bundle;
import android.os.CountDownTimer;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import androidx.activity.ComponentActivity;

import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import java.util.Random;

public class MainActivity extends ComponentActivity {

    private String[] breeds = {"Лабрадор-ретрівер", "Німецька вівчарка", "Бігль", "Пудель", "Хаскі", "Бульдог", "Чихуахуа", "Ротвейлер", "Далматинець", "Ши-тцу"};

    private Random random = new Random();

    private TextView questionTextView;
    private TextView arrayTextView;
    private TextView breedTextView;
    private TextView timerTextView;
    private TextView scoreTextView;
    private Button yesButton;
    private Button noButton;

    private List<String> array;
    private String breed;
    private int score;
    private boolean isPlaying;
    private CountDownTimer timer;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        questionTextView = findViewById(R.id.questionTextView);
        arrayTextView = findViewById(R.id.arrayTextView);
        breedTextView = findViewById(R.id.breedTextView);
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
        array = Arrays.asList(breeds).subList(random.nextInt(breeds.length - 4), random.nextInt(breeds.length - 4) + 5);
        Collections.shuffle(array);
        arrayTextView.setText("Масив: " + array.toString());
        breed = breeds[random.nextInt(breeds.length)];
        breedTextView.setText("Порода: " + breed);
    }

    private void checkAnswer(boolean answer) {
        boolean isMatch = array.contains(breed);
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
