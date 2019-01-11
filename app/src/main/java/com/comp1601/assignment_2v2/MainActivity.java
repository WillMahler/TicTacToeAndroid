package com.comp1601.assignment_2v2;

import android.graphics.Color;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;


public class MainActivity extends AppCompatActivity {

    private final String TAG = "TicTacToe_MainActivity";
    private Button mRow1Col1Button, mRow1Col2Button, mRow1Col3Button;
    private Button mRow2Col1Button, mRow2Col2Button, mRow2Col3Button;
    private Button mRow3Col1Button, mRow3Col2Button, mRow3Col3Button;
    private Button mResetButton;
    private Button[][] mButtonList;
    TicTacToeGame game = new TicTacToeGame();
    private TextView mWins;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        setupButtons();
        setupTextViews();
        setupButtonListeners();

        mButtonList = new Button[][]{{mRow1Col1Button, mRow1Col2Button, mRow1Col3Button},
                {mRow2Col1Button, mRow2Col2Button, mRow2Col3Button},
                {mRow3Col1Button, mRow3Col2Button, mRow3Col3Button}};

        game.newGame();

        mWins.setText(game.getHumanWins() + " : " + game.getComputerWins());
        
    }
    

    public void setupTextViews(){
        mWins = (TextView)findViewById(R.id.wins_textbox);
    }

    public void setupButtons(){

        mRow1Col1Button = (Button) findViewById(R.id.row1col1_button);
        mRow1Col2Button = (Button) findViewById(R.id.row1col2_button);
        mRow1Col3Button = (Button) findViewById(R.id.row1col3_button);
        mRow2Col1Button = (Button) findViewById(R.id.row2col1_button);
        mRow2Col2Button = (Button) findViewById(R.id.row2col2_button);
        mRow2Col3Button = (Button) findViewById(R.id.row2col3_button);
        mRow3Col1Button = (Button) findViewById(R.id.row3col1_button);
        mRow3Col2Button = (Button) findViewById(R.id.row3col2_button);
        mRow3Col3Button = (Button) findViewById(R.id.row3col3_button);
        mResetButton = (Button) findViewById(R.id.resetgame_button);

    }

    public void setupButtonListeners(){

        mRow1Col1Button.setOnClickListener(v -> {
            if (!game.isThereAWin()){
                game.makeAMove(game.getCurrentPlayer(), 0, 0);
                generalButtonFunctions();
            }
        });

        mRow1Col2Button.setOnClickListener(v -> {
            if (!game.isThereAWin()){
                game.makeAMove(game.getCurrentPlayer(), 0, 1);
                generalButtonFunctions();
            }
        });

        mRow1Col3Button.setOnClickListener(v -> {
            if (!game.isThereAWin()){
                game.makeAMove(game.getCurrentPlayer(), 0, 2);
                generalButtonFunctions();
            }
        });

        mRow2Col1Button.setOnClickListener(v -> {
            if (!game.isThereAWin()){
                game.makeAMove(game.getCurrentPlayer(), 1, 0);
                generalButtonFunctions();
            }
        });

        mRow2Col2Button.setOnClickListener(v -> {
            if (!game.isThereAWin()){
                game.makeAMove(game.getCurrentPlayer(), 1, 1);
                generalButtonFunctions();
            }
        });

        mRow2Col3Button.setOnClickListener(v -> {
            if (!game.isThereAWin()){
                game.makeAMove(game.getCurrentPlayer(), 1, 2);
                generalButtonFunctions();
            }
        });

        mRow3Col1Button.setOnClickListener(v -> {
            if (!game.isThereAWin()){
                game.makeAMove(game.getCurrentPlayer(), 2, 0);
                generalButtonFunctions();
            }
        });

        mRow3Col2Button.setOnClickListener(v -> {
            if (!game.isThereAWin()){
                game.makeAMove(game.getCurrentPlayer(), 2, 1);
                generalButtonFunctions();
            }
        });

        mRow3Col3Button.setOnClickListener(v -> {
            if (!game.isThereAWin()){
                game.makeAMove(game.getCurrentPlayer(), 2, 2);
                generalButtonFunctions();
            }
        });

        mResetButton.setOnClickListener(v -> {
            game.newGame();
            fillGameboard(game.getGameboard());
            unhighlightWinner();
        });
        
    }

    public void generalButtonFunctions(){

        fillGameboard(game.getGameboard());
        printLogcatGameboard();

        if (game.isThereAWin()) {
            highlightWinner();
            // Adjust scoreboard
            mWins.setText(game.getHumanWins() + " : " + game.getComputerWins());
            
            // Display the appropriate win toast
            if (game.didOWin()){
                Toast.makeText(MainActivity.this,
                        R.string.toast_winner_is_o,
                        Toast.LENGTH_LONG).show();
            }
            else if (game.didXWin()){
                Toast.makeText(MainActivity.this,
                        R.string.toast_winner_is_x_,
                        Toast.LENGTH_LONG).show();

            }
        }
        
        // Display appropriate toast if game ended in a tie
        if (game.isItATie()){
            Toast.makeText(MainActivity.this,
                    R.string.toast_tie_game,
                    Toast.LENGTH_LONG).show();
        }

    }

    public void highlightWinner(){

        int highlight;

        // Highlight appropriate column if a column won
        if (game.didColumnWin()){
            highlight = game.getWhichColumnWon();
            for (int i = 0; i<3; i++){
                mButtonList[i][highlight].setBackgroundColor(Color.CYAN);
            }
        }

        // Highlight appropriate row if a row won
        if (game.didRowWin()){
            highlight = game.getWhichRowWon();
            for (int i = 0; i<3; i++){
                mButtonList[highlight][i].setBackgroundColor(Color.CYAN);
            }
        }

        // Highlight appropriate diagonal if a diagonal won
        if (game.didDiagonalWin()){
            // Highlighting for the diagonal that starts at top left
            if (game.getWhichDiagonalWon() == 0){
                for (int i=0; i<3; i++){
                    mButtonList[i][i].setBackgroundColor(Color.CYAN);
                }
            }
            // Highlighting for the diagonal that starts at top right
            if (game.getWhichDiagonalWon() == 1){
                int colCounter = 2;
                for (int i=0; i<3; i++){
                    mButtonList[i][colCounter].setBackgroundColor(Color.CYAN);
                    colCounter--;
                }
            }
        }
    }
    
    public void fillGameboard(String[][] gameboard){

        for (int i=0; i<3; i++){
            for (int j = 0; j<3; j++){
                if (gameboard[i][j].equals("X")){
                    mButtonList[i][j].setText("X");
                }

                if (gameboard[i][j].equals("O")){
                    mButtonList[i][j].setText("O");
                }

                if (gameboard[i][j].equals("_")){
                    mButtonList[i][j].setText(" ");
                }
            }
        }
    }

    public void unhighlightWinner(){

        for (int i=0; i<3; i++){
            for (int j = 0; j<3; j++){
                mButtonList[i][j].setBackgroundResource(android.R.drawable.btn_default);
            }
        }

    }

    public void printLogcatGameboard(){

        String[][] g = game.getGameboard();
        String logcatBoard = "";

        for (int i = 0; i<3; i++){

            for (int j = 0; j<3; j++){
                if (g[i][j].equals("_")){
                    logcatBoard += " ";
                }
                else
                    logcatBoard += g[i][j];
                if (j<2)
                    logcatBoard += "|";
            }
            logcatBoard += "\n";
        }

        Log.i(TAG, logcatBoard);

        }

    // SCREEN ROTATION
    // Started to try to complete this requirement, but lacked time
    @Override
    public void onSaveInstanceState(Bundle savedInstanceState){

        super.onSaveInstanceState(savedInstanceState);
    }    
}
