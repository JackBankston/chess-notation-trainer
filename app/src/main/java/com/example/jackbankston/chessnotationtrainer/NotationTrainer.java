package com.example.jackbankston.chessnotationtrainer;

import android.app.ActionBar;
import android.support.v7.app.ActionBarActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.MotionEvent;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;


public class NotationTrainer extends ActionBarActivity {
    final int[] dimensions = new int[2];
    final int[] upperLeft = new int[2];
    Board board;
    String currentGoal;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_notation_trainer);

        //Let's add a back button to the action bar
        android.support.v7.app.ActionBar actionBar = getSupportActionBar();
        if (actionBar != null) {
            actionBar.setDisplayHomeAsUpEnabled(true);
            actionBar.setHomeButtonEnabled(true);
        }

        board = new Board();
        TextView topTextView = (TextView) findViewById(R.id.topTextView);
        currentGoal = board.randomPosition();
        topTextView.setText(currentGoal);
        
        coordinateGame();
    }


    public void coordinateGame(){
        //Why is this final? I don't know. It only works when it's final.
        final ImageView chessBoard = (ImageView) findViewById(R.id.chessBoard);
        final TextView topTextView = (TextView) findViewById(R.id.topTextView);

        chessBoard.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View v, MotionEvent event) {
                if (event.getActionMasked() == MotionEvent.ACTION_DOWN) { //To make sure this will only happen on a down click.
                    //The big idea:
                    //  - Get the position the user just clicked
                    //  - If it matches the currentGoal
                    //          - Change the currentGoal
                    //  - If not
                    //          - Don't change the currentGoal
                    //coordView.setText(event.getX() + ", " + event.getY()); //Just for testing purposes.
                    int heightOfEachSquare = dimensions[0] / 8;
                    int widthOfEachSquare = dimensions[1] / 8;
                    int heightSquare = ((int) event.getY()) / heightOfEachSquare;
                    int widthSquare = ((int) event.getX()) / widthOfEachSquare;
                    //topTextView.setText(board.getPosition(widthSquare, heightSquare));
                    if (board.getPosition(widthSquare, heightSquare).equals(currentGoal)){//It matches the current goal
                        currentGoal = board.randomPosition();
                        topTextView.setText(currentGoal);
                    }
                    else {
                        //Do nothing, for now.
                    }
                    //topTextView.setText(board.randomPosition());
                }
                return true;
            }
        }); //End of OnTouchListener
    }

    @Override
    public void onWindowFocusChanged(boolean hasFocus){
        ImageView chessBoard = (ImageView) findViewById(R.id.chessBoard);
        dimensions[0] = chessBoard.getMeasuredHeight();
        dimensions[1] = chessBoard.getMeasuredWidth();
        chessBoard.getLocationOnScreen(upperLeft);
    }

    //Listener for click on the board.
    public void boardClicked(View v){
        ImageView chessBoard = (ImageView) findViewById(R.id.chessBoard);

    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_notation_trainer, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        switch (item.getItemId()) {
            case android.R.id.home:
                // app icon in action bar clicked; goto parent activity.
                this.finish();
                return true;
            case R.id.action_settings:
                return true;
            default:
                return super.onOptionsItemSelected(item);
        }
    }
}
