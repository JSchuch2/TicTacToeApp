package com.example.tictactoeapp;

import androidx.appcompat.app.AppCompatActivity;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.Arrays;

public class MainActivity extends AppCompatActivity
{
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
    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        Arrays.fill(comparer, null);


        //watches and changes the text that tells the player whose turn it is
        final TextView playersTurn = findViewById(R.id.playersturnTextView);

        final Bitmap backgroundStart = Bitmap.createBitmap(115, 115, Bitmap.Config.ARGB_8888);
        final Canvas backgroundCanvas = new Canvas(backgroundStart);
        backgroundCanvas.drawColor(Color.DKGRAY);
        //~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~Initializes the grayStart for all Squares~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~

        /*final Bitmap grayStart = Bitmap.createBitmap(115, 115, Bitmap.Config.ARGB_8888);
        final Canvas baseCanvas = new Canvas(grayStart);*/
        baseCanvas.drawColor(Color.GRAY);
        //~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~


        //~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~Initializes the bitmap and canvas for when an X is drawn~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~
        /*final Bitmap xStart = Bitmap.createBitmap(115, 115, Bitmap.Config.ARGB_8888);
        final Canvas xCanvas = new Canvas(xStart);*/
        //x and o draw color
        Paint black = new Paint(Color.BLACK); //paintbrush color
        //background color
        xCanvas.drawColor(Color.GRAY);
        //Size of Drawing
        black.setTextSize(215);
        //puts the x
        xCanvas.drawText("x", 0, 115, black); //onClick of image box draw an x on it

        //~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~

        //~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~Initializes the bitmap and canvas for when an O is drawn~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~


        //background color
        oCanvas.drawColor(Color.GRAY);
        //puts the o by drawing text, it just looks better than actually using drawCircle
        oCanvas.drawText("o", 0, 115, black); //onClick of image box draw an x on it

        //black.setStyle(Paint.Style.STROKE);
        //oCanvas.drawCircle(57,57,30, black);

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
        //~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~



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
                    if (currentPlayer == true) //If Player One
                    {
                        //Switch Player
                        currentPlayer = false;
                        //Draw Move
                        blockOne.setImageBitmap(xStart);
                        comparer[0] = xStart;
                        playersTurn.setText("Player o's Turn!");
                        gameLogic();
                    }
                    else //If Player Two
                    {
                        //Switch Player
                        currentPlayer = true;
                        //Draw Move
                        blockOne.setImageBitmap(oStart);
                        comparer[0] = oStart;
                        playersTurn.setText("Player x's Turn!");
                        gameLogic();
                    }
                }

            }
        });

        blockTwo.setOnClickListener(new View.OnClickListener()
        {

            @Override
            public void onClick(View v)
            {
                if((comparer[1] != xStart) && (comparer[1] != oStart))
                {
                    if (currentPlayer == true) //If Player One
                    {
                        //Switch Player
                        currentPlayer = false;
                        //Draw Move
                        blockTwo.setImageBitmap(xStart);
                        comparer[1] = xStart;
                        playersTurn.setText("Player o's Turn!");
                        gameLogic();
                    }
                    else //If Player Two
                    {
                        //Switch Player
                        currentPlayer = true;
                        //Draw Move
                        blockTwo.setImageBitmap(oStart);
                        comparer[1] = oStart;
                        playersTurn.setText("Player x's Turn!");
                        gameLogic();
                    }
                }
            }
        });

        blockThree.setOnClickListener(new View.OnClickListener()
        {

            @Override
            public void onClick(View v)
            {

                if((comparer[2] != xStart) && (comparer[2] != oStart)) {
                    if (currentPlayer == true) //If Player One
                    {
                        //Switch Player
                        currentPlayer = false;
                        //Draw Move
                        blockThree.setImageBitmap(xStart);
                        comparer[2] = xStart;
                        playersTurn.setText("Player o's Turn!");
                        gameLogic();
                    } else //If Player Two
                    {
                        //Switch Player
                        currentPlayer = true;
                        //Draw Move
                        blockThree.setImageBitmap(oStart);
                        comparer[2] = oStart;
                        playersTurn.setText("Player x's Turn!");
                        gameLogic();
                    }
                }
            }
        });

        blockFour.setOnClickListener(new View.OnClickListener()
        {

            @Override
            public void onClick(View v)
            {
                if((comparer[3] != xStart) && (comparer[3] != oStart)) {
                    if (currentPlayer == true) //If Player One
                    {
                        //Switch Player
                        currentPlayer = false;
                        //Draw Move
                        blockFour.setImageBitmap(xStart);
                        comparer[3] = xStart;
                        playersTurn.setText("Player o's Turn!");
                        gameLogic();
                    } else //If Player Two
                    {
                        //Switch Player
                        currentPlayer = true;
                        //Draw Move
                        blockFour.setImageBitmap(oStart);
                        comparer[3] = oStart;
                        playersTurn.setText("Player x's Turn!");
                        gameLogic();
                    }
                }
            }
        });

        blockFive.setOnClickListener(new View.OnClickListener()
        {

            @Override
            public void onClick(View v)
            {
                if((comparer[4] != xStart) && (comparer[4] != oStart)) {
                    if (currentPlayer == true) //If Player One
                    {

                        //Switch Player
                        currentPlayer = false;
                        //Draw Move
                        blockFive.setImageBitmap(xStart);
                        comparer[4] = xStart;
                        playersTurn.setText("Player o's Turn!");
                        gameLogic();
                    } else //If Player Two
                    {
                        //Switch Player
                        currentPlayer = true;
                        //Draw Move
                        blockFive.setImageBitmap(oStart);
                        comparer[4] = oStart;
                        playersTurn.setText("Player x's Turn!");
                        gameLogic();
                    }
                }
            }
        });

        blockSix.setOnClickListener(new View.OnClickListener()
        {

            @Override
            public void onClick(View v)
            {
                if((comparer[5] != xStart) && (comparer[5] != oStart)) {
                    if (currentPlayer == true) //If Player One
                    {
                        //Switch Player
                        currentPlayer = false;
                        //Draw Move
                        blockSix.setImageBitmap(xStart);
                        comparer[5] = xStart;
                        playersTurn.setText("Player o's Turn!");
                        gameLogic();
                    } else //If Player Two
                    {
                        //Switch Player
                        currentPlayer = true;
                        //Draw Move
                        blockSix.setImageBitmap(oStart);
                        comparer[5] = oStart;
                        playersTurn.setText("Player x's Turn!");
                        gameLogic();
                    }
                }
            }
        });

        blockSeven.setOnClickListener(new View.OnClickListener()
        {

            @Override
            public void onClick(View v)
            {
                if((comparer[6] != xStart) && (comparer[6] != oStart)) {
                    if (currentPlayer == true) //If Player One
                    {
                        //Switch Player
                        currentPlayer = false;
                        //Draw Move
                        blockSeven.setImageBitmap(xStart);
                        comparer[6] = xStart;
                        playersTurn.setText("Player o's Turn!");
                        gameLogic();
                    } else //If Player Two
                    {
                        //Switch Player
                        currentPlayer = true;
                        //Draw Move
                        blockSeven.setImageBitmap(oStart);
                        comparer[6] = oStart;
                        playersTurn.setText("Player x's Turn!");
                        gameLogic();
                    }
                }
            }
        });

        blockEight.setOnClickListener(new View.OnClickListener()
        {

            @Override
            public void onClick(View v)
            {
                if((comparer[7] != xStart) && (comparer[7] != oStart)) {
                    if (currentPlayer == true) //If Player One
                    {
                        //Switch Player
                        currentPlayer = false;
                        //Draw Move
                        blockEight.setImageBitmap(xStart);
                        comparer[7] = xStart;
                        playersTurn.setText("Player o's Turn!");
                        gameLogic();
                    } else //If Player Two
                    {
                        //Switch Player
                        currentPlayer = true;
                        //Draw Move
                        blockEight.setImageBitmap(oStart);
                        comparer[7] = oStart;
                        playersTurn.setText("Player x's Turn!");
                        gameLogic();
                    }
                }
            }
        });

        blockNine.setOnClickListener(new View.OnClickListener()
        {

            @Override
            public void onClick(View v)
            {
                if((comparer[8] != xStart) && (comparer[8] != oStart)) {
                    if (currentPlayer == true) //If Player One
                    {
                        //Switch Player
                        currentPlayer = false;
                        //Draw Move
                        blockNine.setImageBitmap(xStart);
                        playersTurn.setText("Player o's Turn!");
                        comparer[8] = xStart;
                        gameLogic();
                    } else //If Player Two
                    {
                        //Switch Player
                        currentPlayer = true;
                        //Draw Move
                        blockNine.setImageBitmap(oStart);
                        playersTurn.setText("Player o's Turn!");
                        comparer[8] = oStart;
                        gameLogic();
                    }
                }
            }
        });
        //~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~



    }

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

        //Tests after every click if a tie has occurred
        if (comparer[counterHolder] != null && counterHolder == 8)
        {
            new AlertDialog.Builder(this).setTitle("Tic-Tac-Toe")
                    .setMessage("Wow a Tie! No one wins!")
                    .setCancelable(false)
                    .setPositiveButton("Play Again!", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int which) {
                            blockOne.setImageBitmap(grayStart);
                            blockTwo.setImageBitmap(grayStart);
                            blockThree.setImageBitmap(grayStart);
                            blockFour.setImageBitmap(grayStart);
                            blockFive.setImageBitmap(grayStart);
                            blockSix.setImageBitmap(grayStart);
                            blockSeven.setImageBitmap(grayStart);
                            blockEight.setImageBitmap(grayStart);
                            blockNine.setImageBitmap(grayStart);
                        }
                    }).show();

            Arrays.fill(comparer, null);
            counterHolder = 0;
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
                            blockOne.setImageBitmap(grayStart);
                            blockTwo.setImageBitmap(grayStart);
                            blockThree.setImageBitmap(grayStart);
                            blockFour.setImageBitmap(grayStart);
                            blockFive.setImageBitmap(grayStart);
                            blockSix.setImageBitmap(grayStart);
                            blockSeven.setImageBitmap(grayStart);
                            blockEight.setImageBitmap(grayStart);
                            blockNine.setImageBitmap(grayStart);
                        }
                    }).show();

            Arrays.fill(comparer, null);
            counterHolder = 0;
        }
        if (comparer[0] == oStart && comparer[1] == oStart && comparer[2] == oStart)
        {
            //Player o wins
            new AlertDialog.Builder(this).setTitle("Tic-Tac-Toe")
                    .setMessage("Player O won!")
                    .setCancelable(false)
                    .setPositiveButton("Play Again!", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int which) {
                            blockOne.setImageBitmap(grayStart);
                            blockTwo.setImageBitmap(grayStart);
                            blockThree.setImageBitmap(grayStart);
                            blockFour.setImageBitmap(grayStart);
                            blockFive.setImageBitmap(grayStart);
                            blockSix.setImageBitmap(grayStart);
                            blockSeven.setImageBitmap(grayStart);
                            blockEight.setImageBitmap(grayStart);
                            blockNine.setImageBitmap(grayStart);
                        }
                    }).show();

            Arrays.fill(comparer, null);
            counterHolder = 0;
        }
        if (comparer[3] == xStart && comparer[4] == xStart && comparer[5] == xStart)
        {
            //Player x wins
            new AlertDialog.Builder(this).setTitle("Tic-Tac-Toe")
                    .setMessage("Player X won!")
                    .setCancelable(false)
                    .setPositiveButton("Play Again!", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int which) {
                            blockOne.setImageBitmap(grayStart);
                            blockTwo.setImageBitmap(grayStart);
                            blockThree.setImageBitmap(grayStart);
                            blockFour.setImageBitmap(grayStart);
                            blockFive.setImageBitmap(grayStart);
                            blockSix.setImageBitmap(grayStart);
                            blockSeven.setImageBitmap(grayStart);
                            blockEight.setImageBitmap(grayStart);
                            blockNine.setImageBitmap(grayStart);
                        }
                    }).show();

            Arrays.fill(comparer, null);
            counterHolder = 0;
        }
        if (comparer[3] == oStart && comparer[4] == oStart && comparer[5] == oStart)
        {
            //Player o wins
            new AlertDialog.Builder(this).setTitle("Tic-Tac-Toe")
                    .setMessage("Player O won!")
                    .setCancelable(false)
                    .setPositiveButton("Play Again!", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int which) {
                            blockOne.setImageBitmap(grayStart);
                            blockTwo.setImageBitmap(grayStart);
                            blockThree.setImageBitmap(grayStart);
                            blockFour.setImageBitmap(grayStart);
                            blockFive.setImageBitmap(grayStart);
                            blockSix.setImageBitmap(grayStart);
                            blockSeven.setImageBitmap(grayStart);
                            blockEight.setImageBitmap(grayStart);
                            blockNine.setImageBitmap(grayStart);
                        }
                    }).show();

            Arrays.fill(comparer, null);
            counterHolder = 0;
        }
        if (comparer[6] == xStart && comparer[7] == xStart && comparer[8] == xStart)
        {
            //Player x wins
            new AlertDialog.Builder(this).setTitle("Tic-Tac-Toe")
                    .setMessage("Player X won!")
                    .setCancelable(false)
                    .setPositiveButton("Play Again!", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int which) {
                            blockOne.setImageBitmap(grayStart);
                            blockTwo.setImageBitmap(grayStart);
                            blockThree.setImageBitmap(grayStart);
                            blockFour.setImageBitmap(grayStart);
                            blockFive.setImageBitmap(grayStart);
                            blockSix.setImageBitmap(grayStart);
                            blockSeven.setImageBitmap(grayStart);
                            blockEight.setImageBitmap(grayStart);
                            blockNine.setImageBitmap(grayStart);
                        }
                    }).show();

            Arrays.fill(comparer, null);
            counterHolder = 0;
        }
        if (comparer[6] == oStart && comparer[7] == oStart && comparer[8] == oStart)
        {
            //Player o wins
            new AlertDialog.Builder(this).setTitle("Tic-Tac-Toe")
                    .setMessage("Player O won!")
                    .setCancelable(false)
                    .setPositiveButton("Play Again!", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int which) {
                            blockOne.setImageBitmap(grayStart);
                            blockTwo.setImageBitmap(grayStart);
                            blockThree.setImageBitmap(grayStart);
                            blockFour.setImageBitmap(grayStart);
                            blockFive.setImageBitmap(grayStart);
                            blockSix.setImageBitmap(grayStart);
                            blockSeven.setImageBitmap(grayStart);
                            blockEight.setImageBitmap(grayStart);
                            blockNine.setImageBitmap(grayStart);
                        }
                    }).show();

            Arrays.fill(comparer, null);
            counterHolder = 0;
        }
        if (comparer[0] == xStart && comparer[3] == xStart && comparer[6] == xStart)
        {
            //Player x wins
            new AlertDialog.Builder(this).setTitle("Tic-Tac-Toe")
                    .setMessage("Player X won!")
                    .setCancelable(false)
                    .setPositiveButton("Play Again!", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int which) {
                            blockOne.setImageBitmap(grayStart);
                            blockTwo.setImageBitmap(grayStart);
                            blockThree.setImageBitmap(grayStart);
                            blockFour.setImageBitmap(grayStart);
                            blockFive.setImageBitmap(grayStart);
                            blockSix.setImageBitmap(grayStart);
                            blockSeven.setImageBitmap(grayStart);
                            blockEight.setImageBitmap(grayStart);
                            blockNine.setImageBitmap(grayStart);
                        }
                    }).show();

            Arrays.fill(comparer, null);
            counterHolder = 0;
        }
        if (comparer[0] == oStart && comparer[3] == oStart && comparer[6] == oStart)
        {
            //Player o wins
            new AlertDialog.Builder(this).setTitle("Tic-Tac-Toe")
                    .setMessage("Player O won!")
                    .setCancelable(false)
                    .setPositiveButton("Play Again!", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int which) {
                            blockOne.setImageBitmap(grayStart);
                            blockTwo.setImageBitmap(grayStart);
                            blockThree.setImageBitmap(grayStart);
                            blockFour.setImageBitmap(grayStart);
                            blockFive.setImageBitmap(grayStart);
                            blockSix.setImageBitmap(grayStart);
                            blockSeven.setImageBitmap(grayStart);
                            blockEight.setImageBitmap(grayStart);
                            blockNine.setImageBitmap(grayStart);
                        }
                    }).show();

            Arrays.fill(comparer, null);
            counterHolder = 0;
        }
        if (comparer[1] == xStart && comparer[4] == xStart && comparer[7] == xStart)
        {
            //Player x wins
            new AlertDialog.Builder(this).setTitle("Tic-Tac-Toe")
                    .setMessage("Player X won!")
                    .setCancelable(false)
                    .setPositiveButton("Play Again!", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int which) {
                            blockOne.setImageBitmap(grayStart);
                            blockTwo.setImageBitmap(grayStart);
                            blockThree.setImageBitmap(grayStart);
                            blockFour.setImageBitmap(grayStart);
                            blockFive.setImageBitmap(grayStart);
                            blockSix.setImageBitmap(grayStart);
                            blockSeven.setImageBitmap(grayStart);
                            blockEight.setImageBitmap(grayStart);
                            blockNine.setImageBitmap(grayStart);
                        }
                    }).show();

            Arrays.fill(comparer, null);
            counterHolder = 0;
        }
        if (comparer[1] == oStart && comparer[4] == oStart && comparer[7] == oStart)
        {
            //Player o wins
            new AlertDialog.Builder(this).setTitle("Tic-Tac-Toe")
                    .setMessage("Player O won!")
                    .setCancelable(false)
                    .setPositiveButton("Play Again!", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int which) {
                            blockOne.setImageBitmap(grayStart);
                            blockTwo.setImageBitmap(grayStart);
                            blockThree.setImageBitmap(grayStart);
                            blockFour.setImageBitmap(grayStart);
                            blockFive.setImageBitmap(grayStart);
                            blockSix.setImageBitmap(grayStart);
                            blockSeven.setImageBitmap(grayStart);
                            blockEight.setImageBitmap(grayStart);
                            blockNine.setImageBitmap(grayStart);
                        }
                    }).show();

            Arrays.fill(comparer, null);
            counterHolder = 0;
        }
        if (comparer[2] == xStart && comparer[5] == xStart && comparer[8] == xStart)
        {
            //Player x wins
            new AlertDialog.Builder(this).setTitle("Tic-Tac-Toe")
                    .setMessage("Player X won!")
                    .setCancelable(false)
                    .setPositiveButton("Play Again!", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int which) {
                            blockOne.setImageBitmap(grayStart);
                            blockTwo.setImageBitmap(grayStart);
                            blockThree.setImageBitmap(grayStart);
                            blockFour.setImageBitmap(grayStart);
                            blockFive.setImageBitmap(grayStart);
                            blockSix.setImageBitmap(grayStart);
                            blockSeven.setImageBitmap(grayStart);
                            blockEight.setImageBitmap(grayStart);
                            blockNine.setImageBitmap(grayStart);
                        }
                    }).show();
            Arrays.fill(comparer, null);
            counterHolder = 0;
        }
        if (comparer[2] == oStart && comparer[5] == oStart && comparer[8] == oStart)
        {
            //Player o wins
            new AlertDialog.Builder(this).setTitle("Tic-Tac-Toe")
                    .setMessage("Player O won!")
                    .setCancelable(false)
                    .setPositiveButton("Play Again!", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int which) {
                            blockOne.setImageBitmap(grayStart);
                            blockTwo.setImageBitmap(grayStart);
                            blockThree.setImageBitmap(grayStart);
                            blockFour.setImageBitmap(grayStart);
                            blockFive.setImageBitmap(grayStart);
                            blockSix.setImageBitmap(grayStart);
                            blockSeven.setImageBitmap(grayStart);
                            blockEight.setImageBitmap(grayStart);
                            blockNine.setImageBitmap(grayStart);
                        }
                    }).show();
            Arrays.fill(comparer, null);
            counterHolder = 0;
        }
        if (comparer[0] == xStart && comparer[4] == xStart && comparer[8] == xStart)
        {
            //Player x wins
            new AlertDialog.Builder(this).setTitle("Tic-Tac-Toe")
                    .setMessage("Player X won!")
                    .setCancelable(false)
                    .setPositiveButton("Play Again!", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int which) {
                            blockOne.setImageBitmap(grayStart);
                            blockTwo.setImageBitmap(grayStart);
                            blockThree.setImageBitmap(grayStart);
                            blockFour.setImageBitmap(grayStart);
                            blockFive.setImageBitmap(grayStart);
                            blockSix.setImageBitmap(grayStart);
                            blockSeven.setImageBitmap(grayStart);
                            blockEight.setImageBitmap(grayStart);
                            blockNine.setImageBitmap(grayStart);
                        }
                    }).show();

            Arrays.fill(comparer, null);
            counterHolder = 0;
        }
        if (comparer[0] == oStart && comparer[4] == oStart && comparer[8] == oStart)
        {
            //Player o wins
            new AlertDialog.Builder(this).setTitle("Tic-Tac-Toe")
                    .setMessage("Player O won!")
                    .setCancelable(false)
                    .setPositiveButton("Play Again!", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int which) {
                            blockOne.setImageBitmap(grayStart);
                            blockTwo.setImageBitmap(grayStart);
                            blockThree.setImageBitmap(grayStart);
                            blockFour.setImageBitmap(grayStart);
                            blockFive.setImageBitmap(grayStart);
                            blockSix.setImageBitmap(grayStart);
                            blockSeven.setImageBitmap(grayStart);
                            blockEight.setImageBitmap(grayStart);
                            blockNine.setImageBitmap(grayStart);
                        }
                    }).show();

            Arrays.fill(comparer, null);
            counterHolder = 0;
        }
        if (comparer[2] == xStart && comparer[4] == xStart && comparer[6] == xStart)
        {
            //Player x wins
            new AlertDialog.Builder(this).setTitle("Tic-Tac-Toe")
                    .setMessage("Player X won!")
                    .setCancelable(false)
                    .setPositiveButton("Play Again!", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int which) {
                            blockOne.setImageBitmap(grayStart);
                            blockTwo.setImageBitmap(grayStart);
                            blockThree.setImageBitmap(grayStart);
                            blockFour.setImageBitmap(grayStart);
                            blockFive.setImageBitmap(grayStart);
                            blockSix.setImageBitmap(grayStart);
                            blockSeven.setImageBitmap(grayStart);
                            blockEight.setImageBitmap(grayStart);
                            blockNine.setImageBitmap(grayStart);
                        }
                    }).show();

            Arrays.fill(comparer, null);
            counterHolder = 0;
        }
        if (comparer[2] == oStart && comparer[4] == oStart && comparer[6] == oStart)
        {
            //Player o wins
            new AlertDialog.Builder(this).setTitle("Tic-Tac-Toe")
                    .setMessage("Player O won!")
                    .setCancelable(false)
                    .setPositiveButton("Play Again!", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int which) {
                            blockOne.setImageBitmap(grayStart);
                            blockTwo.setImageBitmap(grayStart);
                            blockThree.setImageBitmap(grayStart);
                            blockFour.setImageBitmap(grayStart);
                            blockFive.setImageBitmap(grayStart);
                            blockSix.setImageBitmap(grayStart);
                            blockSeven.setImageBitmap(grayStart);
                            blockEight.setImageBitmap(grayStart);
                            blockNine.setImageBitmap(grayStart);
                        }
                    }).show();

            Arrays.fill(comparer, null);
            counterHolder = 0;
        }
        }
    }
