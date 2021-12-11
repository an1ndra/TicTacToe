package com.codeavenge.tictactoe;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;

import com.codeavenge.tictactoe.databinding.ActivityGameDisplayBinding;

public class GameDisplay extends AppCompatActivity {


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        ActivityGameDisplayBinding binding = ActivityGameDisplayBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        binding.playAgain.setVisibility(View.GONE);
        binding.home.setVisibility(View.GONE);
        String[] playerNames=getIntent().getStringArrayExtra("PLAYER_NAME");
        if (playerNames!=null){
            binding.playerDisplay.setText(playerNames[0]+"'s Turn");
        }
        binding.ticTacToeBoard.setUpGame(binding.playAgain,binding.home,binding.playerDisplay,playerNames);

        binding.home.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(GameDisplay.this,MainActivity.class);
                startActivity(intent);
            }
        });

        binding.playAgain.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                binding.ticTacToeBoard.resetGame();
                binding.ticTacToeBoard.invalidate();
            }
        });
    }
}