package com.pantos27.tictactoe;

import android.content.DialogInterface;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import java.util.ArrayList;

public class MainActivity extends AppCompatActivity{


    private static final String TAG = "MainActivity";
    Button block1, block2, block3, block4, block5, block6, block7, block8, block9, restart;
    TextView result;
    boolean fresh = true;
    boolean oTurn = true;
    boolean ended = false;
    ArrayList<Button> buttons = new ArrayList<>(9);


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);


        block1 = (Button) findViewById(R.id.bt_block1);
        block2 = (Button) findViewById(R.id.bt_block2);
        block3 = (Button) findViewById(R.id.bt_block3);
        block4 = (Button) findViewById(R.id.bt_block4);
        block5 = (Button) findViewById(R.id.bt_block5);
        block6 = (Button) findViewById(R.id.bt_block6);
        block7 = (Button) findViewById(R.id.bt_block7);
        block8 = (Button) findViewById(R.id.bt_block8);
        block9 = (Button) findViewById(R.id.bt_block9);
        buttons.add(block1);
        buttons.add(block2);
        buttons.add(block3);
        buttons.add(block4);
        buttons.add(block5);
        buttons.add(block6);
        buttons.add(block7);
        buttons.add(block8);
        buttons.add(block9);
        for (Button button : buttons) {
            button.setOnClickListener(onSquareClicked);
        }
        result = (TextView) findViewById(R.id.tv_show_result);
        restart = (Button) findViewById(R.id.bt_restart_game);


        



         //Restarts the game
        restart.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (ended || fresh){
                    newGame();

                }else{
                    AlertDialog.Builder builder = new AlertDialog.Builder(MainActivity.this);

                    builder.setTitle(R.string.app_name).setMessage(R.string.restart_message)
                            .setPositiveButton("OK", new DialogInterface.OnClickListener() {
                                @Override
                                public void onClick(DialogInterface dialogInterface, int i) {
                                    newGame();
                                }
                            }).setNegativeButton("CANCEL", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialogInterface, int i) {

                        }
                    }).show();
                }

            }
        });

    }
    void newGame(){
        fresh = true;
        oTurn = true;
        ended = false;
        for (Button button : buttons) {
            button.setText("");
        }
        result.setText("");
        restart.setText(R.string.restart_button_text_initially);
    }

    View.OnClickListener onSquareClicked = new View.OnClickListener() {
        @Override
        public void onClick(View view) {
            Log.d(TAG, "onClick: "+oTurn);
            Button button = (Button)view;
            if (ended || button.getText().length()!=0) return;

            fresh = false;
            restart.setText(R.string.restart_button_text_in_middle_of_game);
            button.setText(oTurn? R.string.player_1_move:R.string.player_2_move);
            checkBoard();
            oTurn = !oTurn;
        }
    };

    private void checkBoard() {
        if (checkSequence(block1,block2,block3) || checkSequence(block4,block5,block6) || checkSequence(block7,block8,block9)
                || checkSequence(block1,block4,block7) || checkSequence(block2,block5,block8) || checkSequence(block3,block6,block9)
                || checkSequence(block1,block5,block9) || checkSequence(block7,block5,block3)){
            //We have a winner
            ended = true;
            result.setText(oTurn? R.string.player_1_wins:R.string.player_2_wins);
            restart.setText(R.string.restart_button_text_initially);


        }else if(allFull()){
            //It's a tie
            result.setText(R.string.draw);
            ended = true;
            restart.setText(R.string.restart_button_text_initially);
        }

    }

    private boolean checkSequence(Button btn1,Button btn2,Button btn3){
        return btn1.getText().length()>0 && btn1.getText()==btn2.getText() && btn2.getText()==btn3.getText();
    }

    private boolean allFull(){
        for (Button button : buttons) {
            if (button.getText().length()==0) return false;
        }
        return true;
    }
}
