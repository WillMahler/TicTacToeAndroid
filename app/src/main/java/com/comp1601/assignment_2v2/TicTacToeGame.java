package com.comp1601.assignment_2v2;

import java.lang.Math;

public class TicTacToeGame {

    private int mGameCounter = -1;
    private String mCurrentMove;
    private String mCurrentPlayer;
    private int mNumberOfMovesMade;
    private String mStartingPlayer;
    private String[][] mGameboard = new String[3][3];
    private int mWhichRowWon, mWhichColumnWon, mWhichDiagonalWon;
    private Boolean mDiagonalWin, mRowWin, mColumnWin, mXWon, mOWon, mTieGame;
    private int mHumanWins = 0;
    private int mComputerWins = 0;

    // Get methods
    public int getWhichRowWon()         {return mWhichRowWon;}
    public int getWhichColumnWon()      {return mWhichColumnWon;}
    public int getWhichDiagonalWon()    {return mWhichDiagonalWon;}
    public String getCurrentPlayer()    {return mCurrentPlayer;}
    public Boolean didDiagonalWin()     {return mDiagonalWin;}
    public Boolean didRowWin()          {return mRowWin;}
    public Boolean didColumnWin()       {return mColumnWin;}
    public Boolean didOWin()            {return mOWon;}
    public Boolean didXWin()            {return mXWon;}
    public Boolean isItATie()           {return mTieGame;}
    public Boolean boardFull()          {return (mNumberOfMovesMade == 9);}
    public String[][] getGameboard()    {return mGameboard;}
    public int getHumanWins()           {return mHumanWins;}
    public int getComputerWins()        {return mComputerWins;}
    
    public String getFirstPlayer(int numberOfGamesPlayed){
        
        if ((numberOfGamesPlayed == 0) || (numberOfGamesPlayed % 2 == 0))
            mStartingPlayer = "Human";
        else
            mStartingPlayer = "App";

        return mStartingPlayer;
    }

    public void newGame(){
        
        resetWinMarkers();
        resetGameboard();
        mGameCounter++;
        mCurrentPlayer = getFirstPlayer(mGameCounter);
        if (mCurrentPlayer.equals("App")){
            AppMove();
        }
    }

    public void resetGameboard(){

        mNumberOfMovesMade = 0;
        mCurrentMove = "X";

        // Repopulate mGameboard with "_" to remove X and Os
        for (int i=0; i<3; i++){
            for (int j = 0; j<3; j++){
                mGameboard[i][j] = "_";
                }
            }
        }

    public void updateScore() {

        if (isThereAWin()) {
            if (mCurrentPlayer.equals("App")) {
                mHumanWins++;
            }
            else {
                // Workaround band-aid solution so that mComputerWins isn't incremented
                // twice with how the other methods affect each other
                if ((mHumanWins + mComputerWins) < (mGameCounter + 1)) {
                    mComputerWins++;
                }
            }
        }
    }
    
    private void resetWinMarkers(){
        mWhichRowWon = -1;
        mWhichColumnWon = -1;
        mWhichDiagonalWon = -1;
        mDiagonalWin = false;
        mRowWin = false;
        mColumnWin = false;
        mXWon = false;
        mOWon = false;
        mTieGame = false;
    }
    
    public Boolean makeAMove(String player, int r, int c) {

        Boolean validMove = false;

        // Checks to see if the spot being played on has an empty space
        if (mGameboard[r][c].equals("_")) {
            mGameboard[r][c] = mCurrentMove;
            ++mNumberOfMovesMade;
            validMove = true;

            // Switch the current move
            if (mCurrentMove.equals("X")) {
                mCurrentMove = "O";
            }
            else
                mCurrentMove = "X";

            // Switch the current player
            if (player.equals("Human")) {
                mCurrentPlayer = "App";
                // Checks to see if the app should make a move or not
                if ((mNumberOfMovesMade <9) && !isThereAWin())
                    AppMove();
            }
            else{
                mCurrentPlayer = "Human";
            }
        }

        if (isThereAWin())
            updateScore();

        return validMove;
    }

    public Boolean isThereAWin(){

        Boolean win = false;

        // Avoids checking for a win if not enough moves have
        // been made for a win to even have occurred yet
        if (mNumberOfMovesMade >= 4){

            // Check for horizontal win
            for (int i=0; i<3; i++){
                int xCounter = 0;
                int oCounter = 0;
                for (int j = 0; j<3; j++){
                    if (mGameboard[i][j].equals("O")){
                        oCounter++;
                    }
                    if (mGameboard[i][j].equals("X")){
                        xCounter++;
                    }
                    if ((oCounter == 3) || (xCounter == 3)){
                        mRowWin = true;
                        mWhichRowWon = i;
                        win = true;
                        if (oCounter == 3)
                            mOWon = true;
                        if (xCounter ==3)
                            mXWon = true;
                    }
                }
            }

            // Check for vertical win
            for (int i=0; i<3; i++){
                int xCounter = 0;
                int oCounter = 0;
                for (int j = 0; j<3; j++){
                    if (mGameboard[j][i].equals("O")){
                        oCounter++;
                    }
                    if (mGameboard[j][i].equals("X")){
                        xCounter++;
                    }
                    if ((oCounter == 3) || (xCounter == 3)){
                        mColumnWin = true;
                        mWhichColumnWon = i;
                        win = true;
                        if (oCounter == 3)
                            mOWon = true;
                        if (xCounter ==3)
                            mXWon = true;
                    }
                }
            }

            // Check for diagonal from top left win
            int xCounter = 0;
            int oCounter = 0;
            for (int i=0; i<3; i++){
                if (mGameboard[i][i].equals("O")){
                    oCounter++;
                }
                if (mGameboard[i][i].equals("X")){
                    xCounter++;
                }
                if ((oCounter == 3) || (xCounter == 3)){
                    mDiagonalWin = true;
                    mWhichDiagonalWon = 0;
                    win = true;
                    if (oCounter == 3)
                        mOWon = true;
                    if (xCounter ==3)
                        mXWon = true;
                }
            }

            // Check for diagonal from top right win
            xCounter = 0;
            oCounter = 0;
            int rowCounter = 0;
            for (int i=2; i>-1; i--){
                if (mGameboard[rowCounter][i].equals("O")) {
                    oCounter++;
                }
                if (mGameboard[rowCounter][i].equals("X")) {
                    xCounter++;
                }
                if ((oCounter == 3) || (xCounter == 3)) {
                    mDiagonalWin = true;
                    mWhichDiagonalWon = 1;
                    win = true;
                    if (oCounter == 3)
                        mOWon = true;
                    if (xCounter ==3)
                        mXWon = true;
                }
                rowCounter++;
                }
        }

        // Check for a tie game
        if ((boardFull()) && (!win))
            mTieGame = true;

        return win;
    }

    // Used this just for testing the class on its own, not used in Main Activity
    public void printGameboard(){
        for (int i = 0; i<3; i++){
            for (int j = 0; j<3; j++){
                if (mGameboard[i][j].equals("_")){
                    System.out.print("_");
                }
                else
                    System.out.print(mGameboard[i][j]);

                if (j<2)
                    System.out.print("|");
            }
            System.out.println();
        }
    }

    public void AppMove() {

        // Computer player just randomly picks positions on the board
        // until it finds a valid move to make
        Boolean MoveMade = makeAMove("App", (int) (Math.random() * 3), (int) (Math.random() * 3));
        while (!(MoveMade)) {
            MoveMade = makeAMove("App", (int) (Math.random() * 3), (int) (Math.random() * 3));
        }
    }


}