package com.example.bmicalc;

import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.text.Html;
import android.text.method.ScrollingMovementMethod;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

import com.example.bmicalc.DTO.QuizDTO;
import com.example.bmicalc.DTO.RecipeDTO;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;

public class QuizActivity extends AppCompatActivity {

    int currentQuestionNumber = 0;
    ArrayList<QuizDTO> quizDTOS = new ArrayList<>();
    TextView questionTextView;
    Button aAnswer;
    Button bAnswer;
    Button cAnswer;
    Button dAnswer;
    Button[] answerButtons = {aAnswer, bAnswer, cAnswer, dAnswer};

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_quiz);

        questionTextView = findViewById(R.id.form_quiz_question);
        aAnswer = findViewById(R.id.aResponse);
        bAnswer = findViewById(R.id.bResponse);
        cAnswer = findViewById(R.id.cResponse);
        dAnswer = findViewById(R.id.dResponse);
        try {
            InputStream inputStream = getAssets().open("questions.json");
            int size = inputStream.available();
            byte[] buffer = new byte[size];
            inputStream.read(buffer);


            JSONArray jsonArray = new JSONArray(new String(buffer));
            for (int i = 0; i < jsonArray.length(); i++) {

                JSONObject jsonObject = jsonArray.getJSONObject(i);

                JSONArray answerJsonObject = jsonObject.getJSONArray("answers");
                ArrayList<String> answerList = new ArrayList<>();

                for (int y = 0; y < answerJsonObject.length(); y++) {
                    String answer = answerJsonObject.getString(y);
                    answerList.add(answer);

                }

                QuizDTO quizDTO = new QuizDTO(
                        jsonObject.getString("question"),
                        answerList,
                        jsonObject.getString("correctAnswer")
                );

                quizDTOS.add(quizDTO);
            }

        } catch (IOException | JSONException e) {
            e.printStackTrace();
        }

    }


    public void onButtonClick(View v) {

        Intent myIntent = new Intent(getBaseContext(), MainActivity.class);
        startActivity(myIntent);
    }

    public void start(View v) {

        QuizDTO quizDTO = quizDTOS.get(currentQuestionNumber);
        questionTextView.setText(quizDTO.getQuestion());
        for (int i = 0; i < 4 ; i++){
            answerButtons[i].setText(quizDTO.getAnswers().get(i));
            answerButtons[i].setVisibility(View.VISIBLE);
        }
        aAnswer.setText(quizDTO.getAnswers().get(0));
        bAnswer.setText(quizDTO.getAnswers().get(1));
        cAnswer.setText(quizDTO.getAnswers().get(2));
        dAnswer.setText(quizDTO.getAnswers().get(3));
        currentQuestionNumber++;

    }
}