package com.example.tictactoeapp;

import androidx.appcompat.app.AppCompatActivity;

import android.annotation.SuppressLint;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.res.Resources;
import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.Arrays;

public class MainActivity extends AppCompatActivity
{
    //One giant bug right now with the AI is it will select an option after the player has already won. which causes the next game to mess up as that next click registers for said game
    //an easy solution is to only allow that to occur if game logic doesnt see a win state

    final Bitmap grayStart = Bitmap.createBitmap(115, 115, Bitmap.Config.ARGB_8888);
    final Canvas baseCanvas = new Canvas(grayStart);

    //Bitmap Array that tracks if a player has already used a certain area
    final Bitmap[] comparer = new Bitmap[9];

    final Bitmap xStart = Bitmap.createBitmap(115, 115, Bitmap.Config.ARGB_8888);
    final Canvas xCanvas = new Canvas(xStart);

    final Bitmap oStart = Bitmap.createBitmap(115, 115, Bitmap.Config.ARGB_8888);
    final Canvas oCanvas = new Canvas(oStart);

    int counterHolder;

    //Easy boolean way to tell whose turn we are on. If it is true it is player 1, if it is false it is player 2
    boolean currentPlayer = true;
    boolean gameInProgress = false;

    //this value is true as either we need it to be true when the player first clicks his space so the ai can start, or if we call ai to start first it can
    boolean aiPlays = true;

    boolean isLocalGame = true;

    //if true the player will be x for every next auto game unless they reselect
    //if false the player will be o for every next auto game unless they reselect
    boolean permaIsPlayerX = true;

    int GlobalIndex = 0;

    //need these int so the AI can make informed decisions
    // 0 | Means that it is an empty space
    // 1 | Means that the Player picked this space
    // 2 | Means that the AI Picked this space
    int b1 = 0;
    int b2 = 0;
    int b3 = 0;
    int b4 = 0;
    int b5 = 0;
    int b6 = 0;
    int b7 = 0;
    int b8 = 0;
    int b9 = 0;
    //these all need to be set back to zero for the AI to win


    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        Arrays.fill(comparer, null);

        //watches and changes the text that tells the player whose turn it is
        final TextView gameTitle = findViewById(R.id.tictacTitle);
        final Bitmap backgroundStart = Bitmap.createBitmap(115, 115, Bitmap.Config.ARGB_8888);
        final Canvas backgroundCanvas = new Canvas(backgroundStart);
        backgroundCanvas.drawColor(Color.DKGRAY);

        //~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~Initializes the grayStart for all Squares~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~
        baseCanvas.drawColor(Color.GRAY);
        //~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~

        //~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~Initializes the bitmap and canvas for when an X is drawn~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~
        //x and o draw color
        Paint black = new Paint(Color.BLACK); //paintbrush color
        //background color
        xCanvas.drawColor(Color.GRAY);
        //Size of Drawing
        black.setTextSize(215);
        //puts the x
        xCanvas.drawText("x", 5, 115, black); //onClick of image box draw an x on it
        //~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~

        //~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~Initializes the bitmap and canvas for when an O is drawn~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~
        //background color
        oCanvas.drawColor(Color.GRAY);

        //puts the o by drawing text, it just looks better than actually using drawCircle
        oCanvas.drawText("o", -5, 115, black); //onClick of image box draw an x on it
        //~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~

        //~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~Initialize all Blocks with ImageViews~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~
        final ImageView backgroundBlock = findViewById(R.id.backgroundOfBoard);
        final ImageView blockOne = findViewById(R.id.firstBlock);
        final ImageView blockTwo = findViewById(R.id.secondBlock);
        final ImageView blockThree = findViewById(R.id.thirdBlock);
        final ImageView blockFour = findViewById(R.id.fourthBlock);
        final ImageView blockFive = findViewById(R.id.fifthBlock);
        final ImageView blockSix = findViewById(R.id.sixthBlock);
        final ImageView blockSeven = findViewById(R.id.seventhBlock);
        final ImageView blockEight = findViewById(R.id.eightBlock);
        final ImageView blockNine = findViewById(R.id.ninthBlock);
        final TextView playersTurn = findViewById(R.id.playersturnTextView);

        final Button forceEnd = findViewById(R.id.forceEndGameButton);
        final Button newArtificialIntelligenceGameButton = findViewById(R.id.AIButton);
        final Button newLocalGameButton = findViewById(R.id.LocalButton);
        //~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~

        //~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~AI Game Listeners~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~
        newArtificialIntelligenceGameButton.setOnClickListener(new View.OnClickListener()
        {
            Resources res = getResources();

            @Override
            public void onClick(View v)
            {
                isLocalGame = false;
                forceEnd.setOnClickListener(new View.OnClickListener()
                {
                    @SuppressLint("SetTextI18n")
                    @Override
                    public void onClick(View v)
                    {
                        new AlertDialog.Builder(MainActivity.this).setTitle("Tic-Tac-Toe")
                                .setMessage("Game Force Stopped | Player Reset to X")
                                .setCancelable(false)
                                .setPositiveButton("Play Again!", new DialogInterface.OnClickListener() {
                                    @Override
                                    public void onClick(DialogInterface dialog, int which) {
                                        Resources res = getResources();
                                        blockOne.setImageBitmap(grayStart);
                                        blockTwo.setImageBitmap(grayStart);
                                        blockThree.setImageBitmap(grayStart);
                                        blockFour.setImageBitmap(grayStart);
                                        blockFive.setImageBitmap(grayStart);
                                        blockSix.setImageBitmap(grayStart);
                                        blockSeven.setImageBitmap(grayStart);
                                        blockEight.setImageBitmap(grayStart);
                                        blockNine.setImageBitmap(grayStart);
                                        playersTurn.setText(res.getString(R.string.playerXTurnNotice));
                                        gameInProgress = false;


                                        //on win states we need to set all the global block ints to 0
                                        b1 = 0;
                                        b2 = 0;
                                        b3 = 0;
                                        b4 = 0;
                                        b5 = 0;
                                        b6 = 0;
                                        b7 = 0;
                                        b8 = 0;
                                        b9 = 0;
                                        GlobalIndex = 0;
                                        aiPlays = true;
                                        permaIsPlayerX = true;
                                        currentPlayer = true;
                                    }
                                }).show();

                        Arrays.fill(comparer, null);
                        counterHolder = 0;



                    }
                });

                if(gameInProgress) // if game is currently in progress
                {
                    new AlertDialog.Builder(MainActivity.this).setTitle("Hold on!")
                            .setMessage("Before you start a new AI match, be sure to finish your current match!")
                            .setCancelable(false)
                            .setPositiveButton("Okay", new DialogInterface.OnClickListener() {
                                @Override
                                public void onClick(DialogInterface dialog, int which) {
                                    //do nothing, this is just for information
                                }
                            }).show();
                }
                else
                {
                    new AlertDialog.Builder(MainActivity.this).setTitle("New AI Game")
                            .setMessage("Select your Role")
                            .setCancelable(false)
                            .setNegativeButton("I want to play as X", new DialogInterface.OnClickListener() {
                                @Override
                                public void onClick(DialogInterface dialog, int which)
                                {
                                    playersTurn.setText(res.getString(R.string.playerXTurnNotice));
                                    currentPlayer = true;
                                    permaIsPlayerX = true;
                                }
                            })
                            .setPositiveButton("I want to play as O", new DialogInterface.OnClickListener() {
                                @Override
                                public void onClick(DialogInterface dialog, int which)
                                {
                                    //AI PLAYS AS X so it goes first then once every time player goes
                                    playersTurn.setText(res.getString(R.string.playerOTurnNotice));
                                    currentPlayer = true;
                                    permaIsPlayerX = false;
                                    if(!isLocalGame)
                                {
                                    ExecuteAIHandler();
                                }
                                }
                            }).show();


                    gameTitle.setText(res.getString(R.string.AI_Game_Title));

                    //~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~Set all images to initial grayed out bitmap~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~
                    backgroundBlock.setImageBitmap(backgroundStart);
                    blockOne.setImageBitmap(grayStart);
                    blockTwo.setImageBitmap(grayStart);
                    blockThree.setImageBitmap(grayStart);
                    blockFour.setImageBitmap(grayStart);
                    blockFive.setImageBitmap(grayStart);
                    blockSix.setImageBitmap(grayStart);
                    blockSeven.setImageBitmap(grayStart);
                    blockEight.setImageBitmap(grayStart);
                    blockNine.setImageBitmap(grayStart);
                    //~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~
                    //~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~OnClick Listeners~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~
                    blockOne.setOnClickListener(new View.OnClickListener()
                    {
                        @Override
                        public void onClick(View v)
                        {

                            //Checks to see if something has already been drawn in this area
                            if((comparer[0] != xStart) && (comparer[0] != oStart))
                            {
                                gameInProgress = true;
                                if (currentPlayer) //If Player One
                                {
                                    //Switch Player
                                    currentPlayer = false;
                                    //Draw Move
                                    blockOne.setImageBitmap(xStart);
                                    comparer[0] = xStart;

                                }
                                else //If Player Two
                                {
                                    //Switch Player
                                    currentPlayer = true;
                                    //Draw Move
                                    blockOne.setImageBitmap(oStart);
                                    comparer[0] = oStart;

                                }
                                if(!aiPlays)
                                {
                                    b1 = 2; // make block the AI's
                                }
                                else
                                {
                                    b1 = 1; //make block the persons
                                }
                                gameLogic();

                            }

                        }
                    });

                    blockTwo.setOnClickListener(new View.OnClickListener()
                    {

                        @SuppressLint("SetTextI18n")
                        @Override
                        public void onClick(View v)
                        {
                            if((comparer[1] != xStart) && (comparer[1] != oStart))
                            {
                                gameInProgress = true;
                                if (currentPlayer) //If Player One
                                {
                                    //Switch Player
                                    currentPlayer = false;
                                    //Draw Move
                                    blockTwo.setImageBitmap(xStart);
                                    comparer[1] = xStart;

                                }
                                else //If Player Two
                                {
                                    //Switch Player
                                    currentPlayer = true;
                                    //Draw Move
                                    blockTwo.setImageBitmap(oStart);
                                    comparer[1] = oStart;

                                }
                                if(!aiPlays)
                                {
                                    b2 = 2; // make block the AI's
                                }
                                else
                                {
                                    b2 = 1; //make block the persons
                                }
                                gameLogic();
                            }
                        }
                    });

                    blockThree.setOnClickListener(new View.OnClickListener()
                    {

                        @Override
                        public void onClick(View v)
                        {

                            if((comparer[2] != xStart) && (comparer[2] != oStart)) {
                                gameInProgress = true;
                                if (currentPlayer) //If Player One
                                {
                                    //Switch Player
                                    currentPlayer = false;
                                    //Draw Move
                                    blockThree.setImageBitmap(xStart);
                                    comparer[2] = xStart;

                                } else //If Player Two
                                {
                                    //Switch Player
                                    currentPlayer = true;
                                    //Draw Move
                                    blockThree.setImageBitmap(oStart);
                                    comparer[2] = oStart;

                                }
                                if(!aiPlays)
                                {
                                    b3 = 2; // make block the AI's
                                }
                                else
                                {
                                    b3 = 1; //make block the persons
                                }
                                gameLogic();
                            }
                        }
                    });

                    blockFour.setOnClickListener(new View.OnClickListener()
                    {

                        @Override
                        public void onClick(View v)
                        {
                            if((comparer[3] != xStart) && (comparer[3] != oStart)) {
                                gameInProgress = true;
                                if (currentPlayer) //If Player One
                                {
                                    //Switch Player
                                    currentPlayer = false;
                                    //Draw Move
                                    blockFour.setImageBitmap(xStart);
                                    comparer[3] = xStart;

                                } else //If Player Two
                                {
                                    //Switch Player
                                    currentPlayer = true;
                                    //Draw Move
                                    blockFour.setImageBitmap(oStart);
                                    comparer[3] = oStart;

                                }
                                if(!aiPlays)
                                {
                                    b4 = 2; // make block the AI's
                                }
                                else
                                {
                                    b4 = 1; //make block the persons
                                }
                                gameLogic();

                            }
                        }
                    });

                    blockFive.setOnClickListener(new View.OnClickListener()
                    {

                        @Override
                        public void onClick(View v)
                        {
                            if((comparer[4] != xStart) && (comparer[4] != oStart)) {
                                gameInProgress = true;
                                if (currentPlayer) //If Player One
                                {

                                    //Switch Player
                                    currentPlayer = false;
                                    //Draw Move
                                    blockFive.setImageBitmap(xStart);
                                    comparer[4] = xStart;

                                } else //If Player Two
                                {
                                    //Switch Player
                                    currentPlayer = true;
                                    //Draw Move
                                    blockFive.setImageBitmap(oStart);
                                    comparer[4] = oStart;

                                }
                                if(!aiPlays)
                                {
                                    b5 = 2; // make block the AI's
                                }
                                else
                                {
                                    b5 = 1; //make block the persons
                                }
                                gameLogic();

                            }
                        }
                    });

                    blockSix.setOnClickListener(new View.OnClickListener()
                    {

                        @Override
                        public void onClick(View v)
                        {
                            if((comparer[5] != xStart) && (comparer[5] != oStart)) {
                                gameInProgress = true;
                                if (currentPlayer) //If Player One
                                {
                                    //Switch Player
                                    currentPlayer = false;
                                    //Draw Move
                                    blockSix.setImageBitmap(xStart);
                                    comparer[5] = xStart;

                                } else //If Player Two
                                {
                                    //Switch Player
                                    currentPlayer = true;
                                    //Draw Move
                                    blockSix.setImageBitmap(oStart);
                                    comparer[5] = oStart;

                                }
                                if(!aiPlays)
                                {
                                    b6 = 2; // make block the AI's
                                }
                                else
                                {
                                    b6 = 1; //make block the persons
                                }
                                gameLogic();
                            }
                        }
                    });

                    blockSeven.setOnClickListener(new View.OnClickListener()
                    {

                        @Override
                        public void onClick(View v)
                        {
                            if((comparer[6] != xStart) && (comparer[6] != oStart)) {
                                gameInProgress = true;
                                if (currentPlayer) //If Player One
                                {
                                    //Switch Player
                                    currentPlayer = false;
                                    //Draw Move
                                    blockSeven.setImageBitmap(xStart);
                                    comparer[6] = xStart;

                                } else //If Player Two
                                {
                                    //Switch Player
                                    currentPlayer = true;
                                    //Draw Move
                                    blockSeven.setImageBitmap(oStart);
                                    comparer[6] = oStart;

                                }
                                if(!aiPlays)
                                {
                                    b7 = 2; // make block the AI's
                                }
                                else
                                {
                                    b7 = 1; //make block the persons
                                }
                                gameLogic();

                            }
                        }
                    });

                    blockEight.setOnClickListener(new View.OnClickListener()
                    {

                        @Override
                        public void onClick(View v)
                        {
                            if((comparer[7] != xStart) && (comparer[7] != oStart)) {
                                gameInProgress = true;
                                if (currentPlayer) //If Player One
                                {
                                    //Switch Player
                                    currentPlayer = false;
                                    //Draw Move
                                    blockEight.setImageBitmap(xStart);
                                    comparer[7] = xStart;

                                } else //If Player Two
                                {
                                    //Switch Player
                                    currentPlayer = true;
                                    //Draw Move
                                    blockEight.setImageBitmap(oStart);
                                    comparer[7] = oStart;

                                }
                                if(!aiPlays)
                                {
                                    b8 = 2; // make block the AI's
                                }
                                else
                                {
                                    b8 = 1; //make block the persons
                                }
                                gameLogic();

                            }
                        }
                    });

                    blockNine.setOnClickListener(new View.OnClickListener()
                    {

                        @Override
                        public void onClick(View v)
                        {
                            if((comparer[8] != xStart) && (comparer[8] != oStart)) {
                                gameInProgress = true;
                                if (currentPlayer) //If Player One
                                {
                                    //Switch Player
                                    currentPlayer = false;
                                    //Draw Move
                                    blockNine.setImageBitmap(xStart);

                                    comparer[8] = xStart;
                                } else //If Player Two
                                {
                                    //Switch Player
                                    currentPlayer = true;
                                    //Draw Move
                                    blockNine.setImageBitmap(oStart);

                                    comparer[8] = oStart;
                                }
                                if(!aiPlays)
                                {
                                    b9 = 2; // make block the AI's
                                }
                                else
                                {
                                    b9 = 1; //make block the persons
                                }
                                gameLogic();

                            }
                        }
                    });
                }}});
        //~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~END AI Game Listeners~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~

        //~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~Local Game Listeners~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~
        newLocalGameButton.setOnClickListener(new View.OnClickListener()
        {
            Resources res = getResources();

            @Override
            public void onClick(View v)
            {
                forceEnd.setOnClickListener(new View.OnClickListener()
                {
                    @SuppressLint("SetTextI18n")
                    @Override
                    public void onClick(View v)
                    {
                        new AlertDialog.Builder(MainActivity.this).setTitle("Tic-Tac-Toe")
                                .setMessage("Game Force Stopped | Player Reset to X")
                                .setCancelable(false)
                                .setPositiveButton("Play Again!", new DialogInterface.OnClickListener() {
                                    @Override
                                    public void onClick(DialogInterface dialog, int which) {
                                        isLocalGame = true;
                                        gameTitle.setText(res.getString(R.string.Local_Game_Title));
                                        playersTurn.setText(res.getString(R.string.playerXTurnNotice));
                                        Resources res = getResources();
                                        blockOne.setImageBitmap(grayStart);
                                        blockTwo.setImageBitmap(grayStart);
                                        blockThree.setImageBitmap(grayStart);
                                        blockFour.setImageBitmap(grayStart);
                                        blockFive.setImageBitmap(grayStart);
                                        blockSix.setImageBitmap(grayStart);
                                        blockSeven.setImageBitmap(grayStart);
                                        blockEight.setImageBitmap(grayStart);
                                        blockNine.setImageBitmap(grayStart);
                                        gameInProgress = false;


                                        //on win states we need to set all the global block ints to 0
                                        b1 = 0;
                                        b2 = 0;
                                        b3 = 0;
                                        b4 = 0;
                                        b5 = 0;
                                        b6 = 0;
                                        b7 = 0;
                                        b8 = 0;
                                        b9 = 0;
                                        GlobalIndex = 0;
                                        aiPlays = true;
                                        permaIsPlayerX = true;
                                        currentPlayer = true;
                                    }
                                }).show();

                        Arrays.fill(comparer, null);
                        counterHolder = 0;



                    }
                });
                if(gameInProgress) // if game is currently in progress
                {
                    new AlertDialog.Builder(MainActivity.this).setTitle("Hold on!")
                            .setMessage("Before you start a new local match, be sure to finish your current match!")
                            .setCancelable(false)
                            .setPositiveButton("Okay", new DialogInterface.OnClickListener() {
                                @Override
                                public void onClick(DialogInterface dialog, int which) {
                                    //do nothing, this is just for information
                                }
                            }).show();
                }
                else
                {
                    isLocalGame = true;
                    permaIsPlayerX = true;
                    //currentPlayer = true;

                    gameTitle.setText(res.getString(R.string.Local_Game_Title));
                    //~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~Set all images to initial grayed out bitmap~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~
                    backgroundBlock.setImageBitmap(backgroundStart);
                    blockOne.setImageBitmap(grayStart);
                    blockTwo.setImageBitmap(grayStart);
                    blockThree.setImageBitmap(grayStart);
                    blockFour.setImageBitmap(grayStart);
                    blockFive.setImageBitmap(grayStart);
                    blockSix.setImageBitmap(grayStart);
                    blockSeven.setImageBitmap(grayStart);
                    blockEight.setImageBitmap(grayStart);
                    blockNine.setImageBitmap(grayStart);
                    //~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~

                    //~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~OnClick Listeners~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~
                    blockOne.setOnClickListener(new View.OnClickListener()
                    {
                        @Override
                        public void onClick(View v)
                        {
                            //Checks to see if something has already been drawn in this area
                            if((comparer[0] != xStart) && (comparer[0] != oStart))
                            {
                                gameInProgress = true;
                                if (currentPlayer) //If Player One
                                {
                                    //Switch Player
                                    currentPlayer = false;
                                    //Draw Move
                                    blockOne.setImageBitmap(xStart);
                                    comparer[0] = xStart;

                                }
                                else //If Player Two
                                {
                                    //Switch Player
                                    currentPlayer = true;
                                    //Draw Move
                                    blockOne.setImageBitmap(oStart);
                                    comparer[0] = oStart;

                                }
                                gameLogic();
                            }

                        }
                    });

                    blockTwo.setOnClickListener(new View.OnClickListener()
                    {

                        @SuppressLint("SetTextI18n")
                        @Override
                        public void onClick(View v)
                        {
                            if((comparer[1] != xStart) && (comparer[1] != oStart))
                            {
                                gameInProgress = true;
                                if (currentPlayer) //If Player One
                                {
                                    //Switch Player
                                    currentPlayer = false;
                                    //Draw Move
                                    blockTwo.setImageBitmap(xStart);
                                    comparer[1] = xStart;

                                }
                                else //If Player Two
                                {
                                    //Switch Player
                                    currentPlayer = true;
                                    //Draw Move
                                    blockTwo.setImageBitmap(oStart);
                                    comparer[1] = oStart;

                                }
                                gameLogic();
                            }
                        }
                    });

                    blockThree.setOnClickListener(new View.OnClickListener()
                    {

                        @Override
                        public void onClick(View v)
                        {

                            if((comparer[2] != xStart) && (comparer[2] != oStart)) {
                                gameInProgress = true;
                                if (currentPlayer) //If Player One
                                {
                                    //Switch Player
                                    currentPlayer = false;
                                    //Draw Move
                                    blockThree.setImageBitmap(xStart);
                                    comparer[2] = xStart;

                                } else //If Player Two
                                {
                                    //Switch Player
                                    currentPlayer = true;
                                    //Draw Move
                                    blockThree.setImageBitmap(oStart);
                                    comparer[2] = oStart;

                                }
                                gameLogic();
                            }
                        }
                    });

                    blockFour.setOnClickListener(new View.OnClickListener()
                    {

                        @Override
                        public void onClick(View v)
                        {
                            if((comparer[3] != xStart) && (comparer[3] != oStart)) {
                                gameInProgress = true;
                                if (currentPlayer) //If Player One
                                {
                                    //Switch Player
                                    currentPlayer = false;
                                    //Draw Move
                                    blockFour.setImageBitmap(xStart);
                                    comparer[3] = xStart;

                                } else //If Player Two
                                {
                                    //Switch Player
                                    currentPlayer = true;
                                    //Draw Move
                                    blockFour.setImageBitmap(oStart);
                                    comparer[3] = oStart;

                                }
                                gameLogic();
                            }
                        }
                    });

                    blockFive.setOnClickListener(new View.OnClickListener()
                    {

                        @Override
                        public void onClick(View v)
                        {
                            if((comparer[4] != xStart) && (comparer[4] != oStart)) {
                                gameInProgress = true;
                                if (currentPlayer) //If Player One
                                {

                                    //Switch Player
                                    currentPlayer = false;
                                    //Draw Move
                                    blockFive.setImageBitmap(xStart);
                                    comparer[4] = xStart;

                                } else //If Player Two
                                {
                                    //Switch Player
                                    currentPlayer = true;
                                    //Draw Move
                                    blockFive.setImageBitmap(oStart);
                                    comparer[4] = oStart;

                                }
                                gameLogic();
                            }
                        }
                    });

                    blockSix.setOnClickListener(new View.OnClickListener()
                    {

                        @Override
                        public void onClick(View v)
                        {
                            if((comparer[5] != xStart) && (comparer[5] != oStart)) {
                                gameInProgress = true;
                                if (currentPlayer) //If Player One
                                {
                                    //Switch Player
                                    currentPlayer = false;
                                    //Draw Move
                                    blockSix.setImageBitmap(xStart);
                                    comparer[5] = xStart;

                                } else //If Player Two
                                {
                                    //Switch Player
                                    currentPlayer = true;
                                    //Draw Move
                                    blockSix.setImageBitmap(oStart);
                                    comparer[5] = oStart;

                                }
                                gameLogic();
                            }
                        }
                    });

                    blockSeven.setOnClickListener(new View.OnClickListener()
                    {

                        @Override
                        public void onClick(View v)
                        {
                            if((comparer[6] != xStart) && (comparer[6] != oStart)) {
                                gameInProgress = true;
                                if (currentPlayer) //If Player One
                                {
                                    //Switch Player
                                    currentPlayer = false;
                                    //Draw Move
                                    blockSeven.setImageBitmap(xStart);
                                    comparer[6] = xStart;

                                } else //If Player Two
                                {
                                    //Switch Player
                                    currentPlayer = true;
                                    //Draw Move
                                    blockSeven.setImageBitmap(oStart);
                                    comparer[6] = oStart;

                                }
                                gameLogic();
                            }
                        }
                    });

                    blockEight.setOnClickListener(new View.OnClickListener()
                    {

                        @Override
                        public void onClick(View v)
                        {
                            if((comparer[7] != xStart) && (comparer[7] != oStart)) {
                                gameInProgress = true;
                                if (currentPlayer) //If Player One
                                {
                                    //Switch Player
                                    currentPlayer = false;
                                    //Draw Move
                                    blockEight.setImageBitmap(xStart);
                                    comparer[7] = xStart;

                                } else //If Player Two
                                {
                                    //Switch Player
                                    currentPlayer = true;
                                    //Draw Move
                                    blockEight.setImageBitmap(oStart);
                                    comparer[7] = oStart;

                                }
                                gameLogic();
                            }
                        }
                    });

                    blockNine.setOnClickListener(new View.OnClickListener()
                    {

                        @Override
                        public void onClick(View v)
                        {
                            if((comparer[8] != xStart) && (comparer[8] != oStart)) {
                                gameInProgress = true;
                                if (currentPlayer) //If Player One
                                {
                                    //Switch Player
                                    currentPlayer = false;
                                    //Draw Move
                                    blockNine.setImageBitmap(xStart);

                                    comparer[8] = xStart;
                                } else //If Player Two
                                {
                                    //Switch Player
                                    currentPlayer = true;
                                    //Draw Move
                                    blockNine.setImageBitmap(oStart);

                                    comparer[8] = oStart;
                                }
                                gameLogic();
                            }
                        }
                    });
                }}}); //end of Local Game listener

    }
    //~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~End of On Create~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~

    public void gameLogic()
    {
        final ImageView blockOne = findViewById(R.id.firstBlock);
        final ImageView blockTwo = findViewById(R.id.secondBlock);
        final ImageView blockThree = findViewById(R.id.thirdBlock);
        final ImageView blockFour = findViewById(R.id.fourthBlock);
        final ImageView blockFive = findViewById(R.id.fifthBlock);
        final ImageView blockSix = findViewById(R.id.sixthBlock);
        final ImageView blockSeven = findViewById(R.id.seventhBlock);
        final ImageView blockEight = findViewById(R.id.eightBlock);
        final ImageView blockNine = findViewById(R.id.ninthBlock);
        final TextView playersTurn = findViewById(R.id.playersturnTextView);

        //Tests after every click if a tie has occurred
        if (comparer[counterHolder] != null && counterHolder == 8)
        {
            if (comparer[0] == xStart && comparer[1] == xStart && comparer[2] == xStart)
            {
                //Player x wins
                new AlertDialog.Builder(this).setTitle("Tic-Tac-Toe")
                        .setMessage("Player X won!")
                        .setCancelable(false)
                        .setPositiveButton("Play Again!", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {
                                Resources res = getResources();
                                blockOne.setImageBitmap(grayStart);
                                blockTwo.setImageBitmap(grayStart);
                                blockThree.setImageBitmap(grayStart);
                                blockFour.setImageBitmap(grayStart);
                                blockFive.setImageBitmap(grayStart);
                                blockSix.setImageBitmap(grayStart);
                                blockSeven.setImageBitmap(grayStart);
                                blockEight.setImageBitmap(grayStart);
                                blockNine.setImageBitmap(grayStart);
                                gameInProgress = false;


                                //on win states we need to set all the global block ints to 0
                                b1 = 0;
                                b2 = 0;
                                b3 = 0;
                                b4 = 0;
                                b5 = 0;
                                b6 = 0;
                                b7 = 0;
                                b8 = 0;
                                b9 = 0;
                            GlobalIndex = 0;
                            aiPlays = true;
                            if (permaIsPlayerX)
                            {
                                currentPlayer = true;
                            }
                            else
                            {
                                currentPlayer = true;
                                if(!isLocalGame)
                                {
                                    ExecuteAIHandler();
                                }
                                else
                                {
                                    currentPlayer = false;
                                }

                            }
                            }
                        }).show();

                Arrays.fill(comparer, null);
                counterHolder = 0;
            }
            else if (comparer[0] == oStart && comparer[1] == oStart && comparer[2] == oStart)
            {
                //Player o wins
                new AlertDialog.Builder(this).setTitle("Tic-Tac-Toe")
                        .setMessage("Player O won!")
                        .setCancelable(false)
                        .setPositiveButton("Play Again!", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {
                                Resources res = getResources();
                                blockOne.setImageBitmap(grayStart);
                                blockTwo.setImageBitmap(grayStart);
                                blockThree.setImageBitmap(grayStart);
                                blockFour.setImageBitmap(grayStart);
                                blockFive.setImageBitmap(grayStart);
                                blockSix.setImageBitmap(grayStart);
                                blockSeven.setImageBitmap(grayStart);
                                blockEight.setImageBitmap(grayStart);
                                blockNine.setImageBitmap(grayStart);
                                gameInProgress = false;


                                //on win states we need to set all the global block ints to 0
                                b1 = 0;
                                b2 = 0;
                                b3 = 0;
                                b4 = 0;
                                b5 = 0;
                                b6 = 0;
                                b7 = 0;
                                b8 = 0;
                                b9 = 0;
                            GlobalIndex = 0;
                            aiPlays = true;
                            if (permaIsPlayerX)
                            {
                                currentPlayer = true;
                            }
                            else
                            {
                                currentPlayer = true;
                                if(!isLocalGame)
                                {
                                    ExecuteAIHandler();
                                }
                                else
                                {
                                    currentPlayer = false;
                                }
                            }
                            }
                        }).show();

                Arrays.fill(comparer, null);
                counterHolder = 0;
            }
            else if (comparer[3] == xStart && comparer[4] == xStart && comparer[5] == xStart)
            {
                //Player x wins
                new AlertDialog.Builder(this).setTitle("Tic-Tac-Toe")
                        .setMessage("Player X won!")
                        .setCancelable(false)
                        .setPositiveButton("Play Again!", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {
                                Resources res = getResources();
                                blockOne.setImageBitmap(grayStart);
                                blockTwo.setImageBitmap(grayStart);
                                blockThree.setImageBitmap(grayStart);
                                blockFour.setImageBitmap(grayStart);
                                blockFive.setImageBitmap(grayStart);
                                blockSix.setImageBitmap(grayStart);
                                blockSeven.setImageBitmap(grayStart);
                                blockEight.setImageBitmap(grayStart);
                                blockNine.setImageBitmap(grayStart);
                                gameInProgress = false;


                                //on win states we need to set all the global block ints to 0
                                b1 = 0;
                                b2 = 0;
                                b3 = 0;
                                b4 = 0;
                                b5 = 0;
                                b6 = 0;
                                b7 = 0;
                                b8 = 0;
                                b9 = 0;
                            GlobalIndex = 0;
                            aiPlays = true;
                            if (permaIsPlayerX)
                            {
                                currentPlayer = true;
                            }
                            else
                            {
                                currentPlayer = true;
                                if(!isLocalGame)
                                {
                                    ExecuteAIHandler();
                                }
                                else
                                {
                                    currentPlayer = false;
                                }
                            }
                            }
                        }).show();

                Arrays.fill(comparer, null);
                counterHolder = 0;
            }
            else if (comparer[3] == oStart && comparer[4] == oStart && comparer[5] == oStart)
            {
                //Player o wins
                new AlertDialog.Builder(this).setTitle("Tic-Tac-Toe")
                        .setMessage("Player O won!")
                        .setCancelable(false)
                        .setPositiveButton("Play Again!", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {
                                Resources res = getResources();
                                blockOne.setImageBitmap(grayStart);
                                blockTwo.setImageBitmap(grayStart);
                                blockThree.setImageBitmap(grayStart);
                                blockFour.setImageBitmap(grayStart);
                                blockFive.setImageBitmap(grayStart);
                                blockSix.setImageBitmap(grayStart);
                                blockSeven.setImageBitmap(grayStart);
                                blockEight.setImageBitmap(grayStart);
                                blockNine.setImageBitmap(grayStart);
                                gameInProgress = false;


                                //on win states we need to set all the global block ints to 0
                                b1 = 0;
                                b2 = 0;
                                b3 = 0;
                                b4 = 0;
                                b5 = 0;
                                b6 = 0;
                                b7 = 0;
                                b8 = 0;
                                b9 = 0;
                            GlobalIndex = 0;
                            aiPlays = true;
                            if (permaIsPlayerX)
                            {
                                currentPlayer = true;
                            }
                            else
                            {
                                currentPlayer = true;
                                if(!isLocalGame)
                                {
                                    ExecuteAIHandler();
                                }
                                else
                                {
                                    currentPlayer = false;
                                }
                            }
                            }
                        }).show();

                Arrays.fill(comparer, null);
                counterHolder = 0;
            }
            else if (comparer[6] == xStart && comparer[7] == xStart && comparer[8] == xStart)
            {
                //Player x wins
                new AlertDialog.Builder(this).setTitle("Tic-Tac-Toe")
                        .setMessage("Player X won!")
                        .setCancelable(false)
                        .setPositiveButton("Play Again!", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {
                                Resources res = getResources();
                                blockOne.setImageBitmap(grayStart);
                                blockTwo.setImageBitmap(grayStart);
                                blockThree.setImageBitmap(grayStart);
                                blockFour.setImageBitmap(grayStart);
                                blockFive.setImageBitmap(grayStart);
                                blockSix.setImageBitmap(grayStart);
                                blockSeven.setImageBitmap(grayStart);
                                blockEight.setImageBitmap(grayStart);
                                blockNine.setImageBitmap(grayStart);
                                gameInProgress = false;


                                //on win states we need to set all the global block ints to 0
                                b1 = 0;
                                b2 = 0;
                                b3 = 0;
                                b4 = 0;
                                b5 = 0;
                                b6 = 0;
                                b7 = 0;
                                b8 = 0;
                                b9 = 0;
                            GlobalIndex = 0;
                            aiPlays = true;
                            if (permaIsPlayerX)
                            {
                                currentPlayer = true;
                            }
                            else
                            {
                                currentPlayer = true;
                                if(!isLocalGame)
                                {
                                    ExecuteAIHandler();
                                }
                                else
                                {
                                    currentPlayer = false;
                                }
                            }
                            }
                        }).show();

                Arrays.fill(comparer, null);
                counterHolder = 0;
            }
            else if (comparer[6] == oStart && comparer[7] == oStart && comparer[8] == oStart)
            {
                //Player o wins
                new AlertDialog.Builder(this).setTitle("Tic-Tac-Toe")
                        .setMessage("Player O won!")
                        .setCancelable(false)
                        .setPositiveButton("Play Again!", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {
                                Resources res = getResources();
                                blockOne.setImageBitmap(grayStart);
                                blockTwo.setImageBitmap(grayStart);
                                blockThree.setImageBitmap(grayStart);
                                blockFour.setImageBitmap(grayStart);
                                blockFive.setImageBitmap(grayStart);
                                blockSix.setImageBitmap(grayStart);
                                blockSeven.setImageBitmap(grayStart);
                                blockEight.setImageBitmap(grayStart);
                                blockNine.setImageBitmap(grayStart);
                                gameInProgress = false;


                                //on win states we need to set all the global block ints to 0
                                b1 = 0;
                                b2 = 0;
                                b3 = 0;
                                b4 = 0;
                                b5 = 0;
                                b6 = 0;
                                b7 = 0;
                                b8 = 0;
                                b9 = 0;
                            GlobalIndex = 0;
                            aiPlays = true;
                            if (permaIsPlayerX)
                            {
                                currentPlayer = true;
                            }
                            else
                            {
                                currentPlayer = true;
                                if(!isLocalGame)
                                {
                                    ExecuteAIHandler();
                                }
                                else
                                {
                                    currentPlayer = false;
                                }
                            }
                            }
                        }).show();

                Arrays.fill(comparer, null);
                counterHolder = 0;
            }
            else if (comparer[0] == xStart && comparer[3] == xStart && comparer[6] == xStart)
            {
                //Player x wins
                new AlertDialog.Builder(this).setTitle("Tic-Tac-Toe")
                        .setMessage("Player X won!")
                        .setCancelable(false)
                        .setPositiveButton("Play Again!", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {
                                Resources res = getResources();
                                blockOne.setImageBitmap(grayStart);
                                blockTwo.setImageBitmap(grayStart);
                                blockThree.setImageBitmap(grayStart);
                                blockFour.setImageBitmap(grayStart);
                                blockFive.setImageBitmap(grayStart);
                                blockSix.setImageBitmap(grayStart);
                                blockSeven.setImageBitmap(grayStart);
                                blockEight.setImageBitmap(grayStart);
                                blockNine.setImageBitmap(grayStart);
                                gameInProgress = false;


                                //on win states we need to set all the global block ints to 0
                                b1 = 0;
                                b2 = 0;
                                b3 = 0;
                                b4 = 0;
                                b5 = 0;
                                b6 = 0;
                                b7 = 0;
                                b8 = 0;
                                b9 = 0;
                            GlobalIndex = 0;
                            aiPlays = true;
                            if (permaIsPlayerX)
                            {
                                currentPlayer = true;
                            }
                            else
                            {
                                currentPlayer = true;
                                if(!isLocalGame)
                                {
                                    ExecuteAIHandler();
                                }
                                else
                                {
                                    currentPlayer = false;
                                }
                            }
                            }
                        }).show();

                Arrays.fill(comparer, null);
                counterHolder = 0;
            }
            else if (comparer[0] == oStart && comparer[3] == oStart && comparer[6] == oStart)
            {
                //Player o wins
                new AlertDialog.Builder(this).setTitle("Tic-Tac-Toe")
                        .setMessage("Player O won!")
                        .setCancelable(false)
                        .setPositiveButton("Play Again!", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {
                                Resources res = getResources();
                                blockOne.setImageBitmap(grayStart);
                                blockTwo.setImageBitmap(grayStart);
                                blockThree.setImageBitmap(grayStart);
                                blockFour.setImageBitmap(grayStart);
                                blockFive.setImageBitmap(grayStart);
                                blockSix.setImageBitmap(grayStart);
                                blockSeven.setImageBitmap(grayStart);
                                blockEight.setImageBitmap(grayStart);
                                blockNine.setImageBitmap(grayStart);
                                gameInProgress = false;


                                //on win states we need to set all the global block ints to 0
                                b1 = 0;
                                b2 = 0;
                                b3 = 0;
                                b4 = 0;
                                b5 = 0;
                                b6 = 0;
                                b7 = 0;
                                b8 = 0;
                                b9 = 0;
                            GlobalIndex = 0;
                            aiPlays = true;
                            if (permaIsPlayerX)
                            {
                                currentPlayer = true;
                            }
                            else
                            {
                                currentPlayer = true;
                                if(!isLocalGame)
                                {
                                    ExecuteAIHandler();
                                }
                                else
                                {
                                    currentPlayer = false;
                                }
                            }
                            }
                        }).show();

                Arrays.fill(comparer, null);
                counterHolder = 0;
            }
            else if (comparer[1] == xStart && comparer[4] == xStart && comparer[7] == xStart)
            {
                //Player x wins
                new AlertDialog.Builder(this).setTitle("Tic-Tac-Toe")
                        .setMessage("Player X won!")
                        .setCancelable(false)
                        .setPositiveButton("Play Again!", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {
                                Resources res = getResources();
                                blockOne.setImageBitmap(grayStart);
                                blockTwo.setImageBitmap(grayStart);
                                blockThree.setImageBitmap(grayStart);
                                blockFour.setImageBitmap(grayStart);
                                blockFive.setImageBitmap(grayStart);
                                blockSix.setImageBitmap(grayStart);
                                blockSeven.setImageBitmap(grayStart);
                                blockEight.setImageBitmap(grayStart);
                                blockNine.setImageBitmap(grayStart);
                                gameInProgress = false;


                                //on win states we need to set all the global block ints to 0
                                b1 = 0;
                                b2 = 0;
                                b3 = 0;
                                b4 = 0;
                                b5 = 0;
                                b6 = 0;
                                b7 = 0;
                                b8 = 0;
                                b9 = 0;
                            GlobalIndex = 0;
                            aiPlays = true;
                            if (permaIsPlayerX)
                            {
                                currentPlayer = true;
                            }
                            else
                            {
                                currentPlayer = true;
                                if(!isLocalGame)
                                {
                                    ExecuteAIHandler();
                                }
                                else
                                {
                                    currentPlayer = false;
                                }
                            }
                            }
                        }).show();

                Arrays.fill(comparer, null);
                counterHolder = 0;
            }
            else if (comparer[1] == oStart && comparer[4] == oStart && comparer[7] == oStart)
            {
                //Player o wins
                new AlertDialog.Builder(this).setTitle("Tic-Tac-Toe")
                        .setMessage("Player O won!")
                        .setCancelable(false)
                        .setPositiveButton("Play Again!", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {
                                Resources res = getResources();
                                blockOne.setImageBitmap(grayStart);
                                blockTwo.setImageBitmap(grayStart);
                                blockThree.setImageBitmap(grayStart);
                                blockFour.setImageBitmap(grayStart);
                                blockFive.setImageBitmap(grayStart);
                                blockSix.setImageBitmap(grayStart);
                                blockSeven.setImageBitmap(grayStart);
                                blockEight.setImageBitmap(grayStart);
                                blockNine.setImageBitmap(grayStart);
                                gameInProgress = false;


                                //on win states we need to set all the global block ints to 0
                                b1 = 0;
                                b2 = 0;
                                b3 = 0;
                                b4 = 0;
                                b5 = 0;
                                b6 = 0;
                                b7 = 0;
                                b8 = 0;
                                b9 = 0;
                            GlobalIndex = 0;
                            aiPlays = true;
                            if (permaIsPlayerX)
                            {
                                currentPlayer = true;
                            }
                            else
                            {
                                currentPlayer = true;
                                if(!isLocalGame)
                                {
                                    ExecuteAIHandler();
                                }
                                else
                                {
                                    currentPlayer = false;
                                }
                            }
                            }
                        }).show();

                Arrays.fill(comparer, null);
                counterHolder = 0;
            }
            else if (comparer[2] == xStart && comparer[5] == xStart && comparer[8] == xStart)
            {
                //Player x wins
                new AlertDialog.Builder(this).setTitle("Tic-Tac-Toe")
                        .setMessage("Player X won!")
                        .setCancelable(false)
                        .setPositiveButton("Play Again!", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {
                                Resources res = getResources();
                                blockOne.setImageBitmap(grayStart);
                                blockTwo.setImageBitmap(grayStart);
                                blockThree.setImageBitmap(grayStart);
                                blockFour.setImageBitmap(grayStart);
                                blockFive.setImageBitmap(grayStart);
                                blockSix.setImageBitmap(grayStart);
                                blockSeven.setImageBitmap(grayStart);
                                blockEight.setImageBitmap(grayStart);
                                blockNine.setImageBitmap(grayStart);
                                gameInProgress = false;


                                //on win states we need to set all the global block ints to 0
                                b1 = 0;
                                b2 = 0;
                                b3 = 0;
                                b4 = 0;
                                b5 = 0;
                                b6 = 0;
                                b7 = 0;
                                b8 = 0;
                                b9 = 0;
                            GlobalIndex = 0;
                            aiPlays = true;
                            if (permaIsPlayerX)
                            {
                                currentPlayer = true;
                            }
                            else
                            {
                                currentPlayer = true;
                                if(!isLocalGame)
                                {
                                    ExecuteAIHandler();
                                }
                                else
                                {
                                    currentPlayer = false;
                                }
                            }
                            }
                        }).show();
                Arrays.fill(comparer, null);
                counterHolder = 0;
            }
            else if (comparer[2] == oStart && comparer[5] == oStart && comparer[8] == oStart)
            {
                //Player o wins
                new AlertDialog.Builder(this).setTitle("Tic-Tac-Toe")
                        .setMessage("Player O won!")
                        .setCancelable(false)
                        .setPositiveButton("Play Again!", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {
                                Resources res = getResources();
                                blockOne.setImageBitmap(grayStart);
                                blockTwo.setImageBitmap(grayStart);
                                blockThree.setImageBitmap(grayStart);
                                blockFour.setImageBitmap(grayStart);
                                blockFive.setImageBitmap(grayStart);
                                blockSix.setImageBitmap(grayStart);
                                blockSeven.setImageBitmap(grayStart);
                                blockEight.setImageBitmap(grayStart);
                                blockNine.setImageBitmap(grayStart);
                                gameInProgress = false;


                                //on win states we need to set all the global block ints to 0
                                b1 = 0;
                                b2 = 0;
                                b3 = 0;
                                b4 = 0;
                                b5 = 0;
                                b6 = 0;
                                b7 = 0;
                                b8 = 0;
                                b9 = 0;
                                GlobalIndex = 0;
                                aiPlays = true;
                            if (permaIsPlayerX)
                            {
                                currentPlayer = true;
                            }
                            else
                            {
                                currentPlayer = true;
                                if(!isLocalGame)
                                {
                                    ExecuteAIHandler();
                                }
                                else
                                {
                                    currentPlayer = false;
                                }
                            }
                            }
                        }).show();
                Arrays.fill(comparer, null);
                counterHolder = 0;
            }
            else if (comparer[0] == xStart && comparer[4] == xStart && comparer[8] == xStart)
            {
                //Player x wins
                new AlertDialog.Builder(this).setTitle("Tic-Tac-Toe")
                        .setMessage("Player X won!")
                        .setCancelable(false)
                        .setPositiveButton("Play Again!", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {
                                Resources res = getResources();
                                blockOne.setImageBitmap(grayStart);
                                blockTwo.setImageBitmap(grayStart);
                                blockThree.setImageBitmap(grayStart);
                                blockFour.setImageBitmap(grayStart);
                                blockFive.setImageBitmap(grayStart);
                                blockSix.setImageBitmap(grayStart);
                                blockSeven.setImageBitmap(grayStart);
                                blockEight.setImageBitmap(grayStart);
                                blockNine.setImageBitmap(grayStart);
                                gameInProgress = false;


                                //on win states we need to set all the global block ints to 0
                                b1 = 0;
                                b2 = 0;
                                b3 = 0;
                                b4 = 0;
                                b5 = 0;
                                b6 = 0;
                                b7 = 0;
                                b8 = 0;
                                b9 = 0;
                                GlobalIndex = 0;
                                aiPlays = true;
                            if (permaIsPlayerX)
                            {
                                currentPlayer = true;
                            }
                            else
                            {
                                currentPlayer = true;
                                if(!isLocalGame)
                                {
                                    ExecuteAIHandler();
                                }
                                else
                                {
                                    currentPlayer = false;
                                }
                            }
                            }
                        }).show();

                Arrays.fill(comparer, null);
                counterHolder = 0;
            }
            else if (comparer[0] == oStart && comparer[4] == oStart && comparer[8] == oStart)
            {
                //Player o wins
                new AlertDialog.Builder(this).setTitle("Tic-Tac-Toe")
                        .setMessage("Player O won!")
                        .setCancelable(false)
                        .setPositiveButton("Play Again!", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {
                                Resources res = getResources();
                                blockOne.setImageBitmap(grayStart);
                                blockTwo.setImageBitmap(grayStart);
                                blockThree.setImageBitmap(grayStart);
                                blockFour.setImageBitmap(grayStart);
                                blockFive.setImageBitmap(grayStart);
                                blockSix.setImageBitmap(grayStart);
                                blockSeven.setImageBitmap(grayStart);
                                blockEight.setImageBitmap(grayStart);
                                blockNine.setImageBitmap(grayStart);
                                gameInProgress = false;


                                //on win states we need to set all the global block ints to 0
                                b1 = 0;
                                b2 = 0;
                                b3 = 0;
                                b4 = 0;
                                b5 = 0;
                                b6 = 0;
                                b7 = 0;
                                b8 = 0;
                                b9 = 0;
                            GlobalIndex = 0;
                            aiPlays = true;
                            if (permaIsPlayerX)
                            {
                                currentPlayer = true;
                            }
                            else
                            {
                                currentPlayer = true;
                                if(!isLocalGame)
                                {
                                    ExecuteAIHandler();
                                }
                                else
                                {
                                    currentPlayer = false;
                                }
                            }
                            }
                        }).show();

                Arrays.fill(comparer, null);
                counterHolder = 0;
            }
            else if (comparer[2] == xStart && comparer[4] == xStart && comparer[6] == xStart)
            {
                //Player x wins
                new AlertDialog.Builder(this).setTitle("Tic-Tac-Toe")
                        .setMessage("Player X won!")
                        .setCancelable(false)
                        .setPositiveButton("Play Again!", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {
                                Resources res = getResources();
                                blockOne.setImageBitmap(grayStart);
                                blockTwo.setImageBitmap(grayStart);
                                blockThree.setImageBitmap(grayStart);
                                blockFour.setImageBitmap(grayStart);
                                blockFive.setImageBitmap(grayStart);
                                blockSix.setImageBitmap(grayStart);
                                blockSeven.setImageBitmap(grayStart);
                                blockEight.setImageBitmap(grayStart);
                                blockNine.setImageBitmap(grayStart);
                                gameInProgress = false;


                                //on win states we need to set all the global block ints to 0
                                b1 = 0;
                                b2 = 0;
                                b3 = 0;
                                b4 = 0;
                                b5 = 0;
                                b6 = 0;
                                b7 = 0;
                                b8 = 0;
                                b9 = 0;
                            GlobalIndex = 0;
                            aiPlays = true;
                            if (permaIsPlayerX)
                            {
                                currentPlayer = true;
                            }
                            else
                            {
                                currentPlayer = true;
                                if(!isLocalGame)
                                {
                                    ExecuteAIHandler();
                                }
                                else
                                {
                                    currentPlayer = false;
                                }
                            }
                            }
                        }).show();

                Arrays.fill(comparer, null);
                counterHolder = 0;
            }
            else if (comparer[2] == oStart && comparer[4] == oStart && comparer[6] == oStart)
            {
                //Player o wins
                new AlertDialog.Builder(this).setTitle("Tic-Tac-Toe")
                        .setMessage("Player O won!")
                        .setCancelable(false)
                        .setPositiveButton("Play Again!", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {
                                Resources res = getResources();
                                blockOne.setImageBitmap(grayStart);
                                blockTwo.setImageBitmap(grayStart);
                                blockThree.setImageBitmap(grayStart);
                                blockFour.setImageBitmap(grayStart);
                                blockFive.setImageBitmap(grayStart);
                                blockSix.setImageBitmap(grayStart);
                                blockSeven.setImageBitmap(grayStart);
                                blockEight.setImageBitmap(grayStart);
                                blockNine.setImageBitmap(grayStart);
                                gameInProgress = false;


                                //on win states we need to set all the global block ints to 0
                                b1 = 0;
                                b2 = 0;
                                b3 = 0;
                                b4 = 0;
                                b5 = 0;
                                b6 = 0;
                                b7 = 0;
                                b8 = 0;
                                b9 = 0;
                            GlobalIndex = 0;
                            aiPlays = true;
                            if (permaIsPlayerX)
                            {
                                currentPlayer = true;
                            }
                            else
                            {
                                currentPlayer = true;
                                if(!isLocalGame)
                                {
                                    ExecuteAIHandler();
                                }
                                else
                                {
                                    currentPlayer = false;
                                }
                            }
                            }
                        }).show();

                Arrays.fill(comparer, null);
                counterHolder = 0;
            }
            else
            {
                new AlertDialog.Builder(this).setTitle("Tic-Tac-Toe")
                        .setMessage("Wow a Tie! No one wins!")
                        .setCancelable(false)
                        .setPositiveButton("Play Again!", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {
                                Resources res = getResources();
                                blockOne.setImageBitmap(grayStart);
                                blockTwo.setImageBitmap(grayStart);
                                blockThree.setImageBitmap(grayStart);
                                blockFour.setImageBitmap(grayStart);
                                blockFive.setImageBitmap(grayStart);
                                blockSix.setImageBitmap(grayStart);
                                blockSeven.setImageBitmap(grayStart);
                                blockEight.setImageBitmap(grayStart);
                                blockNine.setImageBitmap(grayStart);
                                gameInProgress = false;


                                //on win states we need to set all the global block ints to 0
                                b1 = 0;
                                b2 = 0;
                                b3 = 0;
                                b4 = 0;
                                b5 = 0;
                                b6 = 0;
                                b7 = 0;
                                b8 = 0;
                                b9 = 0;
                            GlobalIndex = 0;
                            aiPlays = true;
                            if (permaIsPlayerX)
                            {
                                currentPlayer = true;
                            }
                            else
                            {
                                currentPlayer = true;
                                if(!isLocalGame)
                                {
                                    ExecuteAIHandler();
                                }
                                else
                                {
                                    currentPlayer = false;
                                }
                            }
                            }
                        }).show();

                Arrays.fill(comparer, null);
                counterHolder = 0;
            }
        }
        else
        {
            counterHolder++;
        }
        if (comparer[0] == xStart && comparer[1] == xStart && comparer[2] == xStart)
        {
            //Player x wins
            new AlertDialog.Builder(this).setTitle("Tic-Tac-Toe")
                    .setMessage("Player X won!")
                    .setCancelable(false)
                    .setPositiveButton("Play Again!", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int which) {
                            Resources res = getResources();
                            blockOne.setImageBitmap(grayStart);
                            blockTwo.setImageBitmap(grayStart);
                            blockThree.setImageBitmap(grayStart);
                            blockFour.setImageBitmap(grayStart);
                            blockFive.setImageBitmap(grayStart);
                            blockSix.setImageBitmap(grayStart);
                            blockSeven.setImageBitmap(grayStart);
                            blockEight.setImageBitmap(grayStart);
                            blockNine.setImageBitmap(grayStart);
                            gameInProgress = false;


                            //on win states we need to set all the global block ints to 0
                            b1 = 0;
                            b2 = 0;
                            b3 = 0;
                            b4 = 0;
                            b5 = 0;
                            b6 = 0;
                            b7 = 0;
                            b8 = 0;
                            b9 = 0;
                            GlobalIndex = 0;
                            aiPlays = true;
                            if (permaIsPlayerX)
                            {
                                currentPlayer = true;
                            }
                            else
                            {
                                currentPlayer = true;
                                if(!isLocalGame)
                                {
                                    ExecuteAIHandler();
                                }
                                else
                                {
                                    currentPlayer = false;
                                }
                            }
                        }
                    }).show();

            Arrays.fill(comparer, null);
            counterHolder = 0;
        }
        else if (comparer[0] == oStart && comparer[1] == oStart && comparer[2] == oStart)
        {
            //Player o wins
            new AlertDialog.Builder(this).setTitle("Tic-Tac-Toe")
                    .setMessage("Player O won!")
                    .setCancelable(false)
                    .setPositiveButton("Play Again!", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int which) {
                            Resources res = getResources();
                            blockOne.setImageBitmap(grayStart);
                            blockTwo.setImageBitmap(grayStart);
                            blockThree.setImageBitmap(grayStart);
                            blockFour.setImageBitmap(grayStart);
                            blockFive.setImageBitmap(grayStart);
                            blockSix.setImageBitmap(grayStart);
                            blockSeven.setImageBitmap(grayStart);
                            blockEight.setImageBitmap(grayStart);
                            blockNine.setImageBitmap(grayStart);
                            gameInProgress = false;


                            //on win states we need to set all the global block ints to 0
                            b1 = 0;
                            b2 = 0;
                            b3 = 0;
                            b4 = 0;
                            b5 = 0;
                            b6 = 0;
                            b7 = 0;
                            b8 = 0;
                            b9 = 0;
                            GlobalIndex = 0;
                            aiPlays = true;
                            if (permaIsPlayerX)
                            {
                                currentPlayer = true;
                            }
                            else
                            {
                                currentPlayer = true;
                                if(!isLocalGame)
                                {
                                    ExecuteAIHandler();
                                }
                                else
                                {
                                    currentPlayer = false;
                                }
                            }
                        }
                    }).show();

            Arrays.fill(comparer, null);
            counterHolder = 0;
        }
        else if (comparer[3] == xStart && comparer[4] == xStart && comparer[5] == xStart)
        {
            //Player x wins
            new AlertDialog.Builder(this).setTitle("Tic-Tac-Toe")
                    .setMessage("Player X won!")
                    .setCancelable(false)
                    .setPositiveButton("Play Again!", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int which) {
                            Resources res = getResources();
                            blockOne.setImageBitmap(grayStart);
                            blockTwo.setImageBitmap(grayStart);
                            blockThree.setImageBitmap(grayStart);
                            blockFour.setImageBitmap(grayStart);
                            blockFive.setImageBitmap(grayStart);
                            blockSix.setImageBitmap(grayStart);
                            blockSeven.setImageBitmap(grayStart);
                            blockEight.setImageBitmap(grayStart);
                            blockNine.setImageBitmap(grayStart);
                            gameInProgress = false;


                            //on win states we need to set all the global block ints to 0
                            b1 = 0;
                            b2 = 0;
                            b3 = 0;
                            b4 = 0;
                            b5 = 0;
                            b6 = 0;
                            b7 = 0;
                            b8 = 0;
                            b9 = 0;
                            GlobalIndex = 0;
                            aiPlays = true;
                            if (permaIsPlayerX)
                            {
                                currentPlayer = true;
                            }
                            else
                            {
                                currentPlayer = true;
                                if(!isLocalGame)
                                {
                                    ExecuteAIHandler();
                                }
                                else
                                {
                                    currentPlayer = false;
                                }
                            }
                        }
                    }).show();

            Arrays.fill(comparer, null);
            counterHolder = 0;
        }
        else if (comparer[3] == oStart && comparer[4] == oStart && comparer[5] == oStart)
        {
            //Player o wins
            new AlertDialog.Builder(this).setTitle("Tic-Tac-Toe")
                    .setMessage("Player O won!")
                    .setCancelable(false)
                    .setPositiveButton("Play Again!", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int which) {
                            Resources res = getResources();
                            blockOne.setImageBitmap(grayStart);
                            blockTwo.setImageBitmap(grayStart);
                            blockThree.setImageBitmap(grayStart);
                            blockFour.setImageBitmap(grayStart);
                            blockFive.setImageBitmap(grayStart);
                            blockSix.setImageBitmap(grayStart);
                            blockSeven.setImageBitmap(grayStart);
                            blockEight.setImageBitmap(grayStart);
                            blockNine.setImageBitmap(grayStart);
                            gameInProgress = false;


                            //on win states we need to set all the global block ints to 0
                            b1 = 0;
                            b2 = 0;
                            b3 = 0;
                            b4 = 0;
                            b5 = 0;
                            b6 = 0;
                            b7 = 0;
                            b8 = 0;
                            b9 = 0;
                            GlobalIndex = 0;
                            aiPlays = true;
                            if (permaIsPlayerX)
                            {
                                currentPlayer = true;
                            }
                            else
                            {
                                currentPlayer = true;
                                if(!isLocalGame)
                                {
                                    ExecuteAIHandler();
                                }
                                else
                                {
                                    currentPlayer = false;
                                }
                            }
                        }
                    }).show();

            Arrays.fill(comparer, null);
            counterHolder = 0;
        }
        else if (comparer[6] == xStart && comparer[7] == xStart && comparer[8] == xStart)
        {
            //Player x wins
            new AlertDialog.Builder(this).setTitle("Tic-Tac-Toe")
                    .setMessage("Player X won!")
                    .setCancelable(false)
                    .setPositiveButton("Play Again!", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int which) {
                            Resources res = getResources();
                            blockOne.setImageBitmap(grayStart);
                            blockTwo.setImageBitmap(grayStart);
                            blockThree.setImageBitmap(grayStart);
                            blockFour.setImageBitmap(grayStart);
                            blockFive.setImageBitmap(grayStart);
                            blockSix.setImageBitmap(grayStart);
                            blockSeven.setImageBitmap(grayStart);
                            blockEight.setImageBitmap(grayStart);
                            blockNine.setImageBitmap(grayStart);
                            gameInProgress = false;


                            //on win states we need to set all the global block ints to 0
                            b1 = 0;
                            b2 = 0;
                            b3 = 0;
                            b4 = 0;
                            b5 = 0;
                            b6 = 0;
                            b7 = 0;
                            b8 = 0;
                            b9 = 0;
                            GlobalIndex = 0;
                            aiPlays = true;
                            if (permaIsPlayerX)
                            {
                                currentPlayer = true;
                            }
                            else
                            {
                                currentPlayer = true;
                                if(!isLocalGame)
                                {
                                    ExecuteAIHandler();
                                }
                                else
                                {
                                    currentPlayer = false;
                                }
                            }
                        }
                    }).show();

            Arrays.fill(comparer, null);
            counterHolder = 0;
        }
        else if (comparer[6] == oStart && comparer[7] == oStart && comparer[8] == oStart)
        {
            //Player o wins
            new AlertDialog.Builder(this).setTitle("Tic-Tac-Toe")
                    .setMessage("Player O won!")
                    .setCancelable(false)
                    .setPositiveButton("Play Again!", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int which) {
                            Resources res = getResources();
                            blockOne.setImageBitmap(grayStart);
                            blockTwo.setImageBitmap(grayStart);
                            blockThree.setImageBitmap(grayStart);
                            blockFour.setImageBitmap(grayStart);
                            blockFive.setImageBitmap(grayStart);
                            blockSix.setImageBitmap(grayStart);
                            blockSeven.setImageBitmap(grayStart);
                            blockEight.setImageBitmap(grayStart);
                            blockNine.setImageBitmap(grayStart);
                            gameInProgress = false;


                            //on win states we need to set all the global block ints to 0
                            b1 = 0;
                            b2 = 0;
                            b3 = 0;
                            b4 = 0;
                            b5 = 0;
                            b6 = 0;
                            b7 = 0;
                            b8 = 0;
                            b9 = 0;
                            GlobalIndex = 0;
                            aiPlays = true;
                            if (permaIsPlayerX)
                            {
                                currentPlayer = true;
                            }
                            else
                            {
                                currentPlayer = true;
                                if(!isLocalGame)
                                {
                                    ExecuteAIHandler();
                                }
                                else
                                {
                                    currentPlayer = false;
                                }
                            }
                        }
                    }).show();

            Arrays.fill(comparer, null);
            counterHolder = 0;
        }
        else if (comparer[0] == xStart && comparer[3] == xStart && comparer[6] == xStart)
        {
            //Player x wins
            new AlertDialog.Builder(this).setTitle("Tic-Tac-Toe")
                    .setMessage("Player X won!")
                    .setCancelable(false)
                    .setPositiveButton("Play Again!", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int which) {
                            Resources res = getResources();
                            blockOne.setImageBitmap(grayStart);
                            blockTwo.setImageBitmap(grayStart);
                            blockThree.setImageBitmap(grayStart);
                            blockFour.setImageBitmap(grayStart);
                            blockFive.setImageBitmap(grayStart);
                            blockSix.setImageBitmap(grayStart);
                            blockSeven.setImageBitmap(grayStart);
                            blockEight.setImageBitmap(grayStart);
                            blockNine.setImageBitmap(grayStart);
                            gameInProgress = false;


                            //on win states we need to set all the global block ints to 0
                            b1 = 0;
                            b2 = 0;
                            b3 = 0;
                            b4 = 0;
                            b5 = 0;
                            b6 = 0;
                            b7 = 0;
                            b8 = 0;
                            b9 = 0;
                            GlobalIndex = 0;
                            aiPlays = true;
                            if (permaIsPlayerX)
                            {
                                currentPlayer = true;
                            }
                            else
                            {
                                currentPlayer = true;
                                if(!isLocalGame)
                                {
                                    ExecuteAIHandler();
                                }
                                else
                                {
                                    currentPlayer = false;
                                }
                            }
                        }
                    }).show();

            Arrays.fill(comparer, null);
            counterHolder = 0;
        }
        else if (comparer[0] == oStart && comparer[3] == oStart && comparer[6] == oStart)
        {
            //Player o wins
            new AlertDialog.Builder(this).setTitle("Tic-Tac-Toe")
                    .setMessage("Player O won!")
                    .setCancelable(false)
                    .setPositiveButton("Play Again!", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int which) {
                            Resources res = getResources();
                            blockOne.setImageBitmap(grayStart);
                            blockTwo.setImageBitmap(grayStart);
                            blockThree.setImageBitmap(grayStart);
                            blockFour.setImageBitmap(grayStart);
                            blockFive.setImageBitmap(grayStart);
                            blockSix.setImageBitmap(grayStart);
                            blockSeven.setImageBitmap(grayStart);
                            blockEight.setImageBitmap(grayStart);
                            blockNine.setImageBitmap(grayStart);
                            gameInProgress = false;


                            //on win states we need to set all the global block ints to 0
                            b1 = 0;
                            b2 = 0;
                            b3 = 0;
                            b4 = 0;
                            b5 = 0;
                            b6 = 0;
                            b7 = 0;
                            b8 = 0;
                            b9 = 0;
                            GlobalIndex = 0;
                            aiPlays = true;
                            if (permaIsPlayerX)
                            {
                                currentPlayer = true;
                            }
                            else
                            {
                                currentPlayer = true;
                                if(!isLocalGame)
                                {
                                    ExecuteAIHandler();
                                }
                                else
                                {
                                    currentPlayer = false;
                                }
                            }
                        }
                    }).show();

            Arrays.fill(comparer, null);
            counterHolder = 0;
        }
        else if (comparer[1] == xStart && comparer[4] == xStart && comparer[7] == xStart)
        {
            //Player x wins
            new AlertDialog.Builder(this).setTitle("Tic-Tac-Toe")
                    .setMessage("Player X won!")
                    .setCancelable(false)
                    .setPositiveButton("Play Again!", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int which) {
                            Resources res = getResources();
                            blockOne.setImageBitmap(grayStart);
                            blockTwo.setImageBitmap(grayStart);
                            blockThree.setImageBitmap(grayStart);
                            blockFour.setImageBitmap(grayStart);
                            blockFive.setImageBitmap(grayStart);
                            blockSix.setImageBitmap(grayStart);
                            blockSeven.setImageBitmap(grayStart);
                            blockEight.setImageBitmap(grayStart);
                            blockNine.setImageBitmap(grayStart);
                            gameInProgress = false;


                            //on win states we need to set all the global block ints to 0
                            b1 = 0;
                            b2 = 0;
                            b3 = 0;
                            b4 = 0;
                            b5 = 0;
                            b6 = 0;
                            b7 = 0;
                            b8 = 0;
                            b9 = 0;
                            GlobalIndex = 0;
                            aiPlays = true;
                            if (permaIsPlayerX)
                            {
                                currentPlayer = true;
                            }
                            else
                            {
                                currentPlayer = true;
                                if(!isLocalGame)
                                {
                                    ExecuteAIHandler();
                                }
                                else
                                {
                                    currentPlayer = false;
                                }
                            }
                        }
                    }).show();

            Arrays.fill(comparer, null);
            counterHolder = 0;
        }
        else if (comparer[1] == oStart && comparer[4] == oStart && comparer[7] == oStart)
        {
            //Player o wins
            new AlertDialog.Builder(this).setTitle("Tic-Tac-Toe")
                    .setMessage("Player O won!")
                    .setCancelable(false)
                    .setPositiveButton("Play Again!", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int which) {
                            Resources res = getResources();
                            blockOne.setImageBitmap(grayStart);
                            blockTwo.setImageBitmap(grayStart);
                            blockThree.setImageBitmap(grayStart);
                            blockFour.setImageBitmap(grayStart);
                            blockFive.setImageBitmap(grayStart);
                            blockSix.setImageBitmap(grayStart);
                            blockSeven.setImageBitmap(grayStart);
                            blockEight.setImageBitmap(grayStart);
                            blockNine.setImageBitmap(grayStart);
                            gameInProgress = false;


                            //on win states we need to set all the global block ints to 0
                            b1 = 0;
                            b2 = 0;
                            b3 = 0;
                            b4 = 0;
                            b5 = 0;
                            b6 = 0;
                            b7 = 0;
                            b8 = 0;
                            b9 = 0;
                            GlobalIndex = 0;
                            aiPlays = true;
                            if (permaIsPlayerX)
                            {
                                currentPlayer = true;
                            }
                            else
                            {
                                currentPlayer = true;
                                if(!isLocalGame)
                                {
                                    ExecuteAIHandler();
                                }
                                else
                                {
                                    currentPlayer = false;
                                }
                            }
                        }
                    }).show();

            Arrays.fill(comparer, null);
            counterHolder = 0;
        }
        else if (comparer[2] == xStart && comparer[5] == xStart && comparer[8] == xStart)
        {
            //Player x wins
            new AlertDialog.Builder(this).setTitle("Tic-Tac-Toe")
                    .setMessage("Player X won!")
                    .setCancelable(false)
                    .setPositiveButton("Play Again!", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int which) {
                            Resources res = getResources();
                            blockOne.setImageBitmap(grayStart);
                            blockTwo.setImageBitmap(grayStart);
                            blockThree.setImageBitmap(grayStart);
                            blockFour.setImageBitmap(grayStart);
                            blockFive.setImageBitmap(grayStart);
                            blockSix.setImageBitmap(grayStart);
                            blockSeven.setImageBitmap(grayStart);
                            blockEight.setImageBitmap(grayStart);
                            blockNine.setImageBitmap(grayStart);
                            gameInProgress = false;


                            //on win states we need to set all the global block ints to 0
                            b1 = 0;
                            b2 = 0;
                            b3 = 0;
                            b4 = 0;
                            b5 = 0;
                            b6 = 0;
                            b7 = 0;
                            b8 = 0;
                            b9 = 0;
                            GlobalIndex = 0;
                            aiPlays = true;
                            if (permaIsPlayerX)
                            {
                                currentPlayer = true;
                            }
                            else
                            {
                                currentPlayer = true;
                                if(!isLocalGame)
                                {
                                    ExecuteAIHandler();
                                }
                                else
                                {
                                    currentPlayer = false;
                                }
                            }
                        }
                    }).show();
            Arrays.fill(comparer, null);
            counterHolder = 0;
        }
        else if (comparer[2] == oStart && comparer[5] == oStart && comparer[8] == oStart)
        {
            //Player o wins
            new AlertDialog.Builder(this).setTitle("Tic-Tac-Toe")
                    .setMessage("Player O won!")
                    .setCancelable(false)
                    .setPositiveButton("Play Again!", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int which) {
                            Resources res = getResources();
                            blockOne.setImageBitmap(grayStart);
                            blockTwo.setImageBitmap(grayStart);
                            blockThree.setImageBitmap(grayStart);
                            blockFour.setImageBitmap(grayStart);
                            blockFive.setImageBitmap(grayStart);
                            blockSix.setImageBitmap(grayStart);
                            blockSeven.setImageBitmap(grayStart);
                            blockEight.setImageBitmap(grayStart);
                            blockNine.setImageBitmap(grayStart);
                            gameInProgress = false;


                            //on win states we need to set all the global block ints to 0
                            b1 = 0;
                            b2 = 0;
                            b3 = 0;
                            b4 = 0;
                            b5 = 0;
                            b6 = 0;
                            b7 = 0;
                            b8 = 0;
                            b9 = 0;
                            GlobalIndex = 0;
                            aiPlays = true;
                            if (permaIsPlayerX)
                            {
                                currentPlayer = true;
                            }
                            else
                            {
                                currentPlayer = true;
                                if(!isLocalGame)
                                {
                                    ExecuteAIHandler();
                                }
                                else
                                {
                                    currentPlayer = false;
                                }
                            }
                        }
                    }).show();
            Arrays.fill(comparer, null);
            counterHolder = 0;
        }
        else if (comparer[0] == xStart && comparer[4] == xStart && comparer[8] == xStart)
        {
            //Player x wins
            new AlertDialog.Builder(this).setTitle("Tic-Tac-Toe")
                    .setMessage("Player X won!")
                    .setCancelable(false)
                    .setPositiveButton("Play Again!", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int which) {
                            Resources res = getResources();
                            blockOne.setImageBitmap(grayStart);
                            blockTwo.setImageBitmap(grayStart);
                            blockThree.setImageBitmap(grayStart);
                            blockFour.setImageBitmap(grayStart);
                            blockFive.setImageBitmap(grayStart);
                            blockSix.setImageBitmap(grayStart);
                            blockSeven.setImageBitmap(grayStart);
                            blockEight.setImageBitmap(grayStart);
                            blockNine.setImageBitmap(grayStart);
                            gameInProgress = false;


                            //on win states we need to set all the global block ints to 0
                            b1 = 0;
                            b2 = 0;
                            b3 = 0;
                            b4 = 0;
                            b5 = 0;
                            b6 = 0;
                            b7 = 0;
                            b8 = 0;
                            b9 = 0;
                            GlobalIndex = 0;
                            aiPlays = true;
                            if (permaIsPlayerX)
                            {
                                currentPlayer = true;
                            }
                            else
                            {
                                currentPlayer = true;
                                if(!isLocalGame)
                                {
                                    ExecuteAIHandler();
                                }
                                else
                                {
                                    currentPlayer = false;
                                }
                            }
                        }
                    }).show();

            Arrays.fill(comparer, null);
            counterHolder = 0;
        }
        else if (comparer[0] == oStart && comparer[4] == oStart && comparer[8] == oStart)
        {
            //Player o wins
            new AlertDialog.Builder(this).setTitle("Tic-Tac-Toe")
                    .setMessage("Player O won!")
                    .setCancelable(false)
                    .setPositiveButton("Play Again!", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int which) {
                            Resources res = getResources();
                            blockOne.setImageBitmap(grayStart);
                            blockTwo.setImageBitmap(grayStart);
                            blockThree.setImageBitmap(grayStart);
                            blockFour.setImageBitmap(grayStart);
                            blockFive.setImageBitmap(grayStart);
                            blockSix.setImageBitmap(grayStart);
                            blockSeven.setImageBitmap(grayStart);
                            blockEight.setImageBitmap(grayStart);
                            blockNine.setImageBitmap(grayStart);
                            gameInProgress = false;


                            //on win states we need to set all the global block ints to 0
                            b1 = 0;
                            b2 = 0;
                            b3 = 0;
                            b4 = 0;
                            b5 = 0;
                            b6 = 0;
                            b7 = 0;
                            b8 = 0;
                            b9 = 0;
                            GlobalIndex = 0;
                            aiPlays = true;
                            if (permaIsPlayerX)
                            {
                                currentPlayer = true;
                            }
                            else
                            {
                                currentPlayer = true;
                                if(!isLocalGame)
                                {
                                    ExecuteAIHandler();
                                }
                                else
                                {
                                    currentPlayer = false;
                                }
                            }
                        }
                    }).show();

            Arrays.fill(comparer, null);
            counterHolder = 0;
        }
        else if (comparer[2] == xStart && comparer[4] == xStart && comparer[6] == xStart)
        {
            //Player x wins
            new AlertDialog.Builder(this).setTitle("Tic-Tac-Toe")
                    .setMessage("Player X won!")
                    .setCancelable(false)
                    .setPositiveButton("Play Again!", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int which) {
                            Resources res = getResources();
                            blockOne.setImageBitmap(grayStart);
                            blockTwo.setImageBitmap(grayStart);
                            blockThree.setImageBitmap(grayStart);
                            blockFour.setImageBitmap(grayStart);
                            blockFive.setImageBitmap(grayStart);
                            blockSix.setImageBitmap(grayStart);
                            blockSeven.setImageBitmap(grayStart);
                            blockEight.setImageBitmap(grayStart);
                            blockNine.setImageBitmap(grayStart);
                            gameInProgress = false;


                            //on win states we need to set all the global block ints to 0
                            b1 = 0;
                            b2 = 0;
                            b3 = 0;
                            b4 = 0;
                            b5 = 0;
                            b6 = 0;
                            b7 = 0;
                            b8 = 0;
                            b9 = 0;
                            GlobalIndex = 0;
                            aiPlays = true;
                            if (permaIsPlayerX)
                            {
                                currentPlayer = true;
                            }
                            else
                            {
                                currentPlayer = true;
                                if(!isLocalGame)
                                {
                                    ExecuteAIHandler();
                                }
                                else
                                {
                                    currentPlayer = false;
                                }
                            }
                        }
                    }).show();

            Arrays.fill(comparer, null);
            counterHolder = 0;
        }
        else if (comparer[2] == oStart && comparer[4] == oStart && comparer[6] == oStart)
        {
            //Player o wins
            new AlertDialog.Builder(this).setTitle("Tic-Tac-Toe")
                    .setMessage("Player O won!")
                    .setCancelable(false)
                    .setPositiveButton("Play Again!", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int which) {
                            Resources res = getResources();
                            blockOne.setImageBitmap(grayStart);
                            blockTwo.setImageBitmap(grayStart);
                            blockThree.setImageBitmap(grayStart);
                            blockFour.setImageBitmap(grayStart);
                            blockFive.setImageBitmap(grayStart);
                            blockSix.setImageBitmap(grayStart);
                            blockSeven.setImageBitmap(grayStart);
                            blockEight.setImageBitmap(grayStart);
                            blockNine.setImageBitmap(grayStart);
                            gameInProgress = false;



                            //on win states we need to set all the global block ints to 0
                            b1 = 0;
                            b2 = 0;
                            b3 = 0;
                            b4 = 0;
                            b5 = 0;
                            b6 = 0;
                            b7 = 0;
                            b8 = 0;
                            b9 = 0;
                            GlobalIndex = 0;
                            aiPlays = true;
                            if (permaIsPlayerX)
                            {
                                currentPlayer = true;
                            }
                            else
                            {
                                currentPlayer = true;
                                if(!isLocalGame)
                                {
                                    ExecuteAIHandler();
                                }
                                else
                                {
                                    currentPlayer = false;
                                }
                            }
                        }
                    }).show();

            Arrays.fill(comparer, null);
            counterHolder = 0;
        }
        else
        {
            //on non win states we need to call aihandler
            if(!isLocalGame)
                                {
                                    ExecuteAIHandler();
                                }
        }
        }
    //~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~END Game Logic~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~

    public void ExecuteAIHandler()
    {

        Log.i("AIGAMEHANDLER", "///////////////////////////////////////////////////////////////////////////////////////////////////");
        Log.i("AIGAMEHANDLER", "Made It To AI Game Handler");




        if(aiPlays == true)
        {
            Log.i("AIGAMEHANDLER", "Player's Last Turn: ");
            Log.i("AIGAMEHANDLER Value of B1: ", String.valueOf(b1));
            Log.i("AIGAMEHANDLER Value of B2: ", String.valueOf(b2));
            Log.i("AIGAMEHANDLER Value of B3: ", String.valueOf(b3));
            Log.i("AIGAMEHANDLER Value of B4: ", String.valueOf(b4));
            Log.i("AIGAMEHANDLER Value of B5: ", String.valueOf(b5));
            Log.i("AIGAMEHANDLER Value of B6: ", String.valueOf(b6));
            Log.i("AIGAMEHANDLER Value of B7: ", String.valueOf(b7));
            Log.i("AIGAMEHANDLER Value of B8: ", String.valueOf(b8));
            Log.i("AIGAMEHANDLER Value of B9: ", String.valueOf(b9));
            //playerLockedOut = false;
            final ImageView blockOne = findViewById(R.id.firstBlock);
            final ImageView blockTwo = findViewById(R.id.secondBlock);
            final ImageView blockThree = findViewById(R.id.thirdBlock);
            final ImageView blockFour = findViewById(R.id.fourthBlock);
            final ImageView blockFive = findViewById(R.id.fifthBlock);
            final ImageView blockSix = findViewById(R.id.sixthBlock);
            final ImageView blockSeven = findViewById(R.id.seventhBlock);
            final ImageView blockEight = findViewById(R.id.eightBlock);
            final ImageView blockNine = findViewById(R.id.ninthBlock);

            //This ensures now that anytime a player makes a move, the Ai will immediately make its move
            aiPlays = !aiPlays;

            //Now we simply need to figure out which area to click for the AI
            //we will simply use the global integers to know what areas are open, which areas are the player, which areas are the AI.
            //The Simple Tac Algorithm is meant to replicate how a novice would play the game
            //1. click to Win if ai has two in a row
            //2. click to Block if player has two in a row
            //3. if it is not the first or second turn click one of the areas next to your other spots, up, down, left, right, or diagonals
            //4. if there is not a spot you can place next to (surrounded) place it in an empty corner
            //5. if there is not a empty corner place it on an empty side.
            
            int twoHolder = 2;
            int oneHolder = 1;

            //Start of WinStates
            //top row
            if (b1 == twoHolder && b2 == twoHolder && b3 == 0)
            {
               blockThree.performClick();
            }
            else if (b3 == twoHolder && b2 == twoHolder && b1 == 0)
            {
                blockOne.performClick();
            }
            else if (b1 == twoHolder && b3 == twoHolder && b2 == 0)
            {
                blockTwo.performClick();
            }
            //mid row
            else if (b4 == twoHolder && b5 == twoHolder && b6 == 0)
            {
                blockSix.performClick();
            }
            else if (b6 == twoHolder && b5 == twoHolder && b4 == 0)
            {
                blockFour.performClick();
            }
            else if (b6 == twoHolder && b4 == twoHolder && b5 == 0)
            {
                blockFive.performClick();
            }
            //bottom row
            else if (b7 == twoHolder && b8 == twoHolder && b9 == 0)
            {
                blockNine.performClick();
            }
            else if (b9 == twoHolder && b8 == twoHolder && b7 == 0)
            {
                blockSeven.performClick();
            }
            else if (b9 == twoHolder && b7 == twoHolder && b8 == 0)
            {
                blockEight.performClick();
            }
            //left col
            else if (b1 == twoHolder && b4 == twoHolder && b7 == 0)
            {
                blockSeven.performClick();
            }
            else if (b7 == twoHolder && b4 == twoHolder && b1 == 0)
            {
                blockOne.performClick();
            }
            else if (b7 == twoHolder && b1 == twoHolder && b4 == 0)
            {
                blockFour.performClick();
            }
            //mid col
            else if (b2 == twoHolder && b5 == twoHolder && b8 == 0)
            {
                blockEight.performClick();
            }
            else if (b8 == twoHolder && b5 == twoHolder && b2 == 0)
            {
                blockTwo.performClick();
            }
            else if (b8 == twoHolder && b2 == twoHolder && b5 == 0)
            {
                blockFive.performClick();
            }
            // right col
            else if (b3 == twoHolder && b6 == twoHolder && b9 == 0)
            {
                blockNine.performClick();
            }
            else if (b9 == twoHolder && b6 == twoHolder && b3 == 0)
            {
                blockThree.performClick();
            }
            else if (b9 == twoHolder && b3 == twoHolder && b6 == 0)
            {
                blockSix.performClick();
            }
            //diag 1
            else if (b3 == twoHolder && b5 == twoHolder && b7 == 0)
            {
                blockSeven.performClick();
            }
            else if (b7 == twoHolder && b5 == twoHolder && b3 == 0)
            {
                blockThree.performClick();
            }
            else if (b7 == twoHolder && b3 == twoHolder && b5 == 0)
            {
                blockFive.performClick();
            }
            //diag 2
            else if (b1 == twoHolder && b5 == twoHolder && b9 == 0)
            {
                blockNine.performClick();
            }
            else if (b1 == twoHolder && b9 == twoHolder && b3 == 0)
            {
                blockThree.performClick();
            }
            else if (b5 == twoHolder && b9 == twoHolder && b6 == 0)
            {
                blockSix.performClick();
            }
            //END OF WIN STATES
            //START OF BLOCK STATES
            //TOP ROW
            else if (b1 == oneHolder && b2 == oneHolder && b3 == 0)
            {
                blockThree.performClick();
            }
            else if (b3 == oneHolder && b2 == oneHolder && b1 == 0)
            {
                blockOne.performClick();
            }
            else if (b1 == oneHolder && b3 == oneHolder && b2 == 0)
            {
                blockTwo.performClick();
            }
            //MID ROW
            else if (b4 == oneHolder && b5 == oneHolder && b6 == 0)
            {
                blockSix.performClick();
            }
            else if (b6 == oneHolder && b5 == oneHolder && b4 == 0)
            {
                blockFour.performClick();
            }
            else if (b6 == oneHolder && b4 == oneHolder && b5 == 0)
            {
                blockFive.performClick();
            }
            //BOTTOM ROW
            else if (b7 == oneHolder && b8 == oneHolder && b9 == 0)
            {
                blockNine.performClick();
            }
            else if (b9 == oneHolder && b8 == oneHolder && b7 == 0)
            {
                blockSeven.performClick();
            }
            else if (b9 == oneHolder && b7 == oneHolder && b8 == 0)
            {
                blockEight.performClick();
            }
            //LEFT COL
            else if (b1 == oneHolder && b4 == oneHolder && b7 == 0)
            {
                blockSeven.performClick();
            }
            else if (b7 == oneHolder && b4 == oneHolder && b1 == 0)
            {
                blockOne.performClick();
            }
            else if (b7 == oneHolder && b1 == oneHolder && b4 == 0)
            {
                blockFour.performClick();
            }
            //MID COL
            else if (b2 == oneHolder && b5 == oneHolder && b8 == 0)
            {
                blockEight.performClick();
            }
            else if (b8 == oneHolder && b5 == oneHolder && b2 == 0)
            {
                blockTwo.performClick();
            }
            else if (b8 == oneHolder && b2 == oneHolder && b5 == 0)
            {
                blockFive.performClick();
            }
            //RIGHT COL
            else if (b3 == oneHolder && b6 == oneHolder && b9 == 0)
            {
                blockNine.performClick();
            }
            else if (b9 == oneHolder && b6 == oneHolder && b3 == 0)
            {
                blockThree.performClick();
            }
            else if (b9 == oneHolder && b3 == oneHolder && b6 == 0)
            {
                blockSix.performClick();
            }
            //DIAG 1
            else if (b3 == oneHolder && b5 == oneHolder && b7 == 0)
            {
                blockSeven.performClick();
            }
            else if (b7 == oneHolder && b5 == oneHolder && b3 == 0)
            {
                blockThree.performClick();
            }
            else if (b7 == oneHolder && b3 == oneHolder && b5 == 0)
            {
                blockFive.performClick();
            }
            //DIAG 2
            else if (b1 == oneHolder && b5 == oneHolder && b9 == 0)
            {
                blockNine.performClick();
            }
            else if (b1 == oneHolder && b9 == oneHolder && b3 == 0)
            {
                blockThree.performClick();
            }
            else if (b5 == oneHolder && b9 == oneHolder && b6 == 0)
            {
                blockSix.performClick();
            }
            //PLACE IN MIDDLE IF THERE ARE NOT TWO IN A ROW
            else if(b5 == 0)
            {
                blockFive.performClick();
            }
            // if cant win or block or place in the middle: place in corners
            else if (b1 == 0 || b3 == 0 || b7 == 0 || b9 == 0)
            {
                if(b1 == 0)
                {
                    blockOne.performClick();
                }
                else if(b3 == 0)
                {
                    blockThree.performClick();
                }
                else if(b7 == 0)
                {
                    blockSeven.performClick();
                }
                else if(b9 == 0)
                {
                    blockNine.performClick();
                }
            }
            // if cant win, block, place in the middle place, or place in corners: place in empty side
            else if (b2 == 0 || b4 == 0 || b6 == 0 || b8 == 0)
            {
                if(b2 == 0)
                {
                    blockTwo.performClick();
                }
                else if(b4 == 0)
                {
                    blockFour.performClick();
                }
                else if(b6 == 0)
                {
                    blockSix.performClick();
                }
                else if(b8 == 0)
                {
                    blockEight.performClick();
                }
            }   //if none of these actions can occur then that means we will have tied by that point as there is no middle, corners, or sides

        }
        else
        {


            Log.i("AIGAMEHANDLER", "AI's Last Turn: ");
            Log.i("AIGAMEHANDLER Value of B1: ", String.valueOf(b1));
            Log.i("AIGAMEHANDLER Value of B2: ", String.valueOf(b2));
            Log.i("AIGAMEHANDLER Value of B3: ", String.valueOf(b3));
            Log.i("AIGAMEHANDLER Value of B4: ", String.valueOf(b4));
            Log.i("AIGAMEHANDLER Value of B5: ", String.valueOf(b5));
            Log.i("AIGAMEHANDLER Value of B6: ", String.valueOf(b6));
            Log.i("AIGAMEHANDLER Value of B7: ", String.valueOf(b7));
            Log.i("AIGAMEHANDLER Value of B8: ", String.valueOf(b8));
            Log.i("AIGAMEHANDLER Value of B9: ", String.valueOf(b9));

            //This ensures now that anytime a player makes a move, the Ai will immediately make its move
            aiPlays = !aiPlays;
        }
    }
    } // end of class