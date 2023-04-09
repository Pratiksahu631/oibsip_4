package com.example.quizapp;

import androidx.appcompat.app.AppCompatActivity;

import android.annotation.SuppressLint;
//import android.app.AlertDialog;
import android.graphics.Color;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.Button;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.google.android.material.bottomsheet.BottomSheetDialog;

import java.util.Objects;


public class MainActivity extends AppCompatActivity implements View.OnClickListener {

    TextView question;
    TextView question_attempted;
    Button button1, button2, button3, button4, button5;

    int Score;
    int totalQ = QuestionAnswer.questions.length;
    int currentQueIndex = 0;
    int attempted = 0;
    String SelectedAns = "";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        question = findViewById(R.id.question);
        question_attempted = findViewById(R.id.question_attempt);
        button1 = findViewById(R.id.button1);
        button2 = findViewById(R.id.button2);
        button3 = findViewById(R.id.button3);
        button4 = findViewById(R.id.button4);
        button5 = findViewById(R.id.button5);

        button1.setOnClickListener(this);
        button2.setOnClickListener(this);
        button3.setOnClickListener(this);
        button4.setOnClickListener(this);
        button5.setOnClickListener(this);

        loadNewQuestion();
    }
    @SuppressLint("SetTextI18n")
    private void loadNewQuestion(){
        question_attempted.setText("Question - "+attempted+"/"+totalQ);
        if (currentQueIndex == (totalQ-1)){
            button5.setText("SUBMIT");
        }
//        if(currentQueIndex<(totalQ-1)){
//            button5.setText("NEXT");
//        }
        if (currentQueIndex>(totalQ-1)){
            ShowBottomSheet();
            return;
        }
        question.setText(QuestionAnswer.questions[currentQueIndex]);
        button1.setText(QuestionAnswer.choices[currentQueIndex][0]);
        button2.setText(QuestionAnswer.choices[currentQueIndex][1]);
        button3.setText(QuestionAnswer.choices[currentQueIndex][2]);
        button4.setText(QuestionAnswer.choices[currentQueIndex][3]);

    }

//    private void finishQuiz(){
//        new AlertDialog.Builder(this)
//                .setMessage("Score : "+Score+"/"+totalQ)
//                .setPositiveButton("Restart", (dialogInterface,i)->restartQuiz())
//                .setCancelable(false)
//                .show();
//    }

    @SuppressLint("SetTextI18n")
    private void restartQuiz(){
        button5.setText("NEXT");
        Score = 0;
        currentQueIndex = 0;
        attempted = 0;
        loadNewQuestion();
    }

    @SuppressLint("SetTextI18n")
    private void ShowBottomSheet(){
        BottomSheetDialog bottomSheetDialog = new BottomSheetDialog(MainActivity.this);
        View bottomSheetView = LayoutInflater.from(getApplicationContext()).inflate(R.layout.activity_bottom_sheet, findViewById(R.id.ll_bottomSheet),false);
        TextView s = bottomSheetView.findViewById(R.id.id_score);
        Button restart = bottomSheetView.findViewById(R.id.restart_btn);

        restart.setOnClickListener(v -> {
            restartQuiz();
            bottomSheetDialog.dismiss();
        });
        s.setText("Score : "+Score+"/"+totalQ);

        bottomSheetDialog.setCancelable(false);
        bottomSheetDialog.setContentView(bottomSheetView);
        bottomSheetDialog.show();
    }

    @Override
    public void onClick(View view){

        button1.setBackgroundColor(Color.parseColor("#FF6200EE"));
        button2.setBackgroundColor(Color.parseColor("#FF6200EE"));
        button3.setBackgroundColor(Color.parseColor("#FF6200EE"));
        button4.setBackgroundColor(Color.parseColor("#FF6200EE"));
        Button clickedBtn = (Button) view;

        if (clickedBtn.getId() == R.id.button5){
            if (Objects.equals(SelectedAns, QuestionAnswer.Answers[currentQueIndex])){
                Score++;
            }
            currentQueIndex++;

            loadNewQuestion();
        }
        else{
            SelectedAns = clickedBtn.getText().toString();
            clickedBtn.setBackgroundColor(Color.GREEN);
            attempted++;
        }
    }

}