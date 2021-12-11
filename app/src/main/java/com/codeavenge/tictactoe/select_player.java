package com.codeavenge.tictactoe;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;

import com.codeavenge.tictactoe.databinding.ActivitySelectPlayerBinding;

public class select_player extends AppCompatActivity {
    ActivitySelectPlayerBinding binding;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivitySelectPlayerBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        binding.submit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
            String player1Name=binding.player1.getText().toString();
            String player2Name=binding.player2.getText().toString();

            Intent intent = new Intent(select_player.this,GameDisplay.class);
            //Take data from edit text and pass it to GameDisplay activity
            intent.putExtra("PLAYER_NAME",new String[] {player1Name,player2Name});
            startActivity(intent);

            }
        });

    }
}