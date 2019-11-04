package com.example.brainteaser;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.NotificationCompat;

import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.media.MediaPlayer;
import android.os.Bundle;
import android.os.CountDownTimer;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Random;
import static android.view.View.GONE;

public class Main3Activity extends AppCompatActivity {

    Button btnGo, btnAgain;
    TextView tvScore, tvTimer, tvSum, tvResult;
    Button button0, button1, button2, button3;
    int location;
    int numberOfQuestions = 0;
    int score = 0;
    boolean click = true;
    String username;
    String password;
    TextView tvName;

    ArrayList<Integer> answers = new ArrayList<Integer>();

    public void answer(View view)
    {
        final MediaPlayer mediaPlayer = MediaPlayer.create(getApplicationContext(),R.raw.clicksound);
            if (mediaPlayer.isPlaying())
            {
                mediaPlayer.stop();
                mediaPlayer.start();
            }
            else
            {
                mediaPlayer.start();
            }

        if (click == true) {
            if (Integer.toString(location).equals(view.getTag().toString())) {
                tvResult.setText("Correct :)");
                score++;
            } else {
                tvResult.setText("Wrong :(");
            }

            numberOfQuestions++;
            tvScore.setText(Integer.toString(score) + " / " + Integer.toString(numberOfQuestions));

            newQuestion();
        }
    }

    public void newQuestion() {
        Random random = new Random();

        int a = random.nextInt(101);
        int b = random.nextInt(101);

        tvSum.setText(Integer.toString(a) + " + " + Integer.toString(b));

        location = random.nextInt(4);

        answers.clear();

        for (int i = 0; i < 4; i++) {
            if (i == location)
                answers.add(a + b);
            else {
                int wrongAnswer = random.nextInt(202);

                while (wrongAnswer == a + b) {
                    wrongAnswer = random.nextInt(202);
                }

                answers.add(wrongAnswer);
            }
        }

        button0.setText(Integer.toString(answers.get(0)));
        button1.setText(Integer.toString(answers.get(1)));
        button2.setText(Integer.toString(answers.get(2)));
        button3.setText(Integer.toString(answers.get(3)));
    }

    public void playAgain(View view) {
        score = 0;
        numberOfQuestions = 0;
        tvTimer.setText("30s");
        tvScore.setText("0 / 0");
        btnAgain.setVisibility(GONE);
        tvResult.setText("");
        click = true;

        new CountDownTimer(30100, 1000) {
            @Override
            public void onTick(long l) {

                tvTimer.setText(String.valueOf(l / 1000) + "s");

            }

            @Override
            public void onFinish() {

                tvTimer.setText("0s");
                tvResult.setText("Done!");
                btnAgain.setVisibility(View.VISIBLE);
                click = false;

                try {
                    GameDB gameDB = new GameDB(getApplicationContext());
                    gameDB.open();
                    gameDB.updateUser(username, password, score);
                    gameDB.close();
                }
                catch (Exception e)
                {
                    Toast.makeText(Main3Activity.this, "Unable to upload the result : " + e.getMessage(), Toast.LENGTH_SHORT).show();
                }

            }
        }.start();
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main3);

        btnGo = findViewById(R.id.btnGo);
        btnAgain = findViewById(R.id.btnAgain);
        tvSum = findViewById(R.id.tvSum);
        tvResult = findViewById(R.id.tvResult);
        tvScore = findViewById(R.id.tvScore);
        tvTimer = findViewById(R.id.tvTimer);
        tvName = findViewById(R.id.tvName);

        Intent intent = getIntent();
        username = intent.getStringExtra("username");
        password = intent.getStringExtra("password");
        tvName.setText("Welcome " + username.toUpperCase());

        button0 = findViewById(R.id.button0);
        button1 = findViewById(R.id.button1);
        button2 = findViewById(R.id.button2);
        button3 = findViewById(R.id.button3);

        btnGo.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                btnGo.setVisibility(GONE);
                tvTimer.setVisibility(View.VISIBLE);
                tvSum.setVisibility(View.VISIBLE);
                tvScore.setVisibility(View.VISIBLE);
                button0.setVisibility(View.VISIBLE);
                button1.setVisibility(View.VISIBLE);
                button2.setVisibility(View.VISIBLE);
                button3.setVisibility(View.VISIBLE);
                btnAgain.setVisibility(View.VISIBLE);
                tvResult.setVisibility(View.VISIBLE);
                tvName.setVisibility(GONE);

                tvResult.setText("");

                newQuestion();

                playAgain(findViewById(R.id.btnAgain));
            }
        });

    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.main, menu);
        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {

        switch (item.getItemId())
        {
            case R.id.exit:
                AlertDialog.Builder dialog = new AlertDialog.Builder(Main3Activity.this);
                dialog.setTitle("Important")
                        .setMessage("Do you really want to exit ?")
                        .setCancelable(false)
                        .setPositiveButton("OK", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {
                                Main3Activity.this.finish();
                            }
                        })
                        .setNegativeButton("CANCEL", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {

                            }
                        });
                dialog.show();
                break;

            case R.id.logout:
                AlertDialog.Builder dialog1 = new AlertDialog.Builder(Main3Activity.this);
                dialog1.setTitle("Important")
                        .setMessage("Do you want to logout ?")
                        .setCancelable(false)
                        .setPositiveButton("OK", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {
                                Intent intent = new Intent(Main3Activity.this, MainActivity.class);
                                startActivity(intent);
                                Main3Activity.this.finish();
                            }
                        })
                        .setNegativeButton("CANCEL", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {

                            }
                        });
                dialog1.show();
                break;

            case R.id.moreinfo:
                AlertDialog.Builder builder = new AlertDialog.Builder(Main3Activity.this);
                builder.setTitle("Information")
                        .setMessage("This game is just to test the decision making skills using simple maths.\n " +
                                "You can the play the game and compare your results with others at the same time. \n " +
                                "Enjoy the Game !!")
                        .setPositiveButton("OK", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {

                            }
                        });
                builder.show();
                break;

            case R.id.leaderboard:
                Intent intent = new Intent(Main3Activity.this, Main4Activity.class);
                startActivity(intent);
                break;

            case R.id.contactUs:
                Intent intent1 = new Intent(Main3Activity.this,Main5Activity.class);
                startActivity(intent1);
                break;

            case R.id.highest:
                try {
                    GameDB gameDB = new GameDB(getApplicationContext());
                    gameDB.open();
                    int high = gameDB.highest(username, password);
                    gameDB.close();
                    AlertDialog.Builder builder1 = new AlertDialog.Builder(Main3Activity.this);
                    builder1.setTitle("Your highest score")
                            .setMessage("   " + high)
                            .setPositiveButton("OK", new DialogInterface.OnClickListener() {
                                @Override
                                public void onClick(DialogInterface dialog, int which) {

                                }
                            });
                    builder1.show();
                }
                catch (Exception e)
                {
                    Toast.makeText(this, "Unable to find your highest score !", Toast.LENGTH_SHORT).show();
                }
                break;

        }

        return super.onOptionsItemSelected(item);
    }
}
