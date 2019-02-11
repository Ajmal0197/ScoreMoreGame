package com.example.scoremoregame;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

public class GameOverActivity extends AppCompatActivity
{
    private Button startGameAgain;
    private TextView scoreDisplay;
    private String score;

    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_game_over);

        startGameAgain = findViewById(R.id.play_again_btn);
        scoreDisplay= findViewById(R.id.display_score);

        score = getIntent().getExtras().get("score").toString();

        startGameAgain.setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View v)
            {
                startActivity(new Intent(getApplicationContext(), MainActivity.class));
            }
        });

        scoreDisplay.setText("Score = " + score);
    }
}
