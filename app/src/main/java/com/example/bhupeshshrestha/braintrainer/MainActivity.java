package com.example.bhupeshshrestha.braintrainer;

import android.os.Bundle;
import android.os.CountDownTimer;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.Button;
import android.widget.RelativeLayout;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.Random;

public class MainActivity extends AppCompatActivity {

    Button startBtn;
    Button button0;
    Button button1;
    Button button2;
    Button button3;
    Button playAgainbtn;

    TextView sumTextView;
    TextView resultTextView;
    TextView pointsTextView;
    TextView timerTextView;

    RelativeLayout secondLayout;

    ArrayList<Integer> answers = new ArrayList<Integer>();
    int locationOfCorrectAnswer;
    int score = 0;
    int questions = 0;
    boolean gameActive = true;


    public void start(View view){
        startBtn.setVisibility(View.INVISIBLE);
        secondLayout.setVisibility(View.VISIBLE);
        playAgainbtn = (Button) findViewById(R.id.playAgainBtn);
        playAgain(playAgainbtn);
    }

    public void playAgain(View view){
        gameActive = true;
        score = 0;
        questions = 0;
        pointsTextView.setText("0/0");
        timerTextView.setText("30s");
        resultTextView.setText("");
        playAgainbtn.setVisibility(View.INVISIBLE);

        new CountDownTimer(30100, 1000) {
            @Override
            public void onTick(long millisUntilFinished) {
                timerTextView.setText(String.valueOf( millisUntilFinished / 1000)+ "s");
            }

            @Override
            public void onFinish() {
                timerTextView.setText("0s");
                resultTextView.setText("Game Over! Your Score is: " + score + "/" + questions);
                playAgainbtn.setVisibility(View.VISIBLE);
                sumTextView.setText("");
                gameActive = false;
            }
        }.start();
    }

    public void generateQuestions(){
        Random rand = new Random();
        int firstNumber = rand.nextInt(26);
        int secondNumber = rand.nextInt(36);
        sumTextView.setText(Integer.toString(firstNumber) + " + " + Integer.toString(secondNumber));

        locationOfCorrectAnswer = rand.nextInt(4);
        int inCorrectAnswer;

        for (int i=0; i<4; i++){
            if(i == locationOfCorrectAnswer){
                answers.add(firstNumber+secondNumber);
            }else {
                inCorrectAnswer = rand.nextInt(75);
                while (inCorrectAnswer == firstNumber + secondNumber){
                    inCorrectAnswer = rand.nextInt(75);
                }
                answers.add(inCorrectAnswer);
            }
        }
        button0 = (Button) findViewById(R.id.button0);
        button1 = (Button) findViewById(R.id.button1);
        button2 = (Button) findViewById(R.id.button2);
        button3 = (Button) findViewById(R.id.button3);

        button0.setText(Integer.toString(answers.get(0)));
        button1.setText(Integer.toString(answers.get(1)));
        button2.setText(Integer.toString(answers.get(2)));
        button3.setText(Integer.toString(answers.get(3)));
    }

    public void chooseAnswer(View view){
        if(gameActive){
            if((view.getTag().toString()).equals(Integer.toString(locationOfCorrectAnswer))){
                score++;
                resultTextView.setText("Correct!");
            }else{
                resultTextView.setText("Incorrect!");
            }
            questions++;
            pointsTextView.setText(Integer.toString(score) + "/" + Integer.toString(questions));
            answers.clear();
            generateQuestions();
        }
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        secondLayout = (RelativeLayout) findViewById(R.id.secondLayout);

        startBtn = (Button) findViewById(R.id.startBtn);
        sumTextView = (TextView) findViewById(R.id.sumTextView);
        resultTextView = (TextView) findViewById(R.id.resultTextView);
        timerTextView = (TextView) findViewById(R.id.timerTextView);
        pointsTextView = (TextView) findViewById(R.id.pointsTextView);
        generateQuestions();
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_main, menu);
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
}
