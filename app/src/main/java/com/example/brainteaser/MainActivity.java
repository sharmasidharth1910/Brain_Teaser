package com.example.brainteaser;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.NotificationCompat;

import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

public class MainActivity extends AppCompatActivity {

    ImageView ivInfo;
    EditText etUsername, etPassword;
    Button btnLogin;
    TextView tvNext, tvNext2;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        ivInfo = findViewById(R.id.ivInfo);
        etUsername = findViewById(R.id.etUsername);
        etPassword = findViewById(R.id.etPassword);
        btnLogin = findViewById(R.id.btnLogin);
        tvNext = findViewById(R.id.tvNext);
        tvNext2 = findViewById(R.id.tvNext2);

        ivInfo.setOnLongClickListener(new View.OnLongClickListener() {
            @Override
            public boolean onLongClick(View v) {
                Toast.makeText(MainActivity.this, "Application designed and implemented by Sidharth Sharma", Toast.LENGTH_SHORT).show();
                return false;
            }
        });

        tvNext.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(MainActivity.this, Main2Activity.class);
                startActivity(intent);
                etUsername.setText("");
                etPassword.setText("");
            }
        });

        tvNext2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                GameDB gameDB = new GameDB(getApplicationContext());
                gameDB.open();
                int flag;
                flag = gameDB.checkUser("Guest", "0000");
                gameDB.close();
                if (flag !=0)
                {
                    Intent intent = new Intent(MainActivity.this, com.example.brainteaser.Main3Activity.class);
                    intent.putExtra("username", "Guest");
                    intent.putExtra("password", "0000");
                    startActivity(intent);
                    MainActivity.this.finish();
                }
                else
                {
                    gameDB.open();
                    gameDB.createEntry("Guest", "0000");
                    gameDB.close();
                    Intent intent = new Intent(MainActivity.this, com.example.brainteaser.Main3Activity.class);
                    intent.putExtra("username", "Guest");
                    intent.putExtra("password", "0000");
                    startActivity(intent);
                    MainActivity.this.finish();
                }

            }
        });

        btnLogin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(etUsername.getText().toString().trim().isEmpty())
                {
                    etUsername.setError("Required!");
                }
                if (etPassword.getText().toString().trim().isEmpty())
                {
                    etPassword.setError("Required");
                }
                if(!etPassword.getText().toString().trim().isEmpty() && !etUsername.getText().toString().trim().isEmpty())
                {
                    String user = etUsername.getText().toString().trim();
                    String pass = etPassword.getText().toString().trim();

                    GameDB gameDB = new GameDB(getApplicationContext());
                    gameDB.open();
                    int flag;
                    flag = gameDB.checkUser(user, pass);
                    gameDB.close();
                    if (flag !=0)
                    {
                        Intent intent = new Intent(MainActivity.this, com.example.brainteaser.Main3Activity.class);
                        intent.putExtra("username", user);
                        intent.putExtra("password", pass);

//                        String message = "Welcome " + user + " to the game !!";
//
//                        NotificationCompat.Builder builder = new NotificationCompat.Builder(
//                                getApplicationContext())
//                                .setSmallIcon(R.drawable.logopng)
//                                .setContentTitle("New Notification")
//                                .setContentText(message)
//                                .setAutoCancel(true);
//
//                        Intent i = new Intent(MainActivity.this, Main3Activity.class);
//                        i.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
//                        i.putExtra("username", user);
//                        i.putExtra("password", pass);
//
//                        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
//                            NotificationChannel channel = new NotificationChannel("0", "Notification Channel", NotificationManager.IMPORTANCE_DEFAULT);
//                            channel.setDescription("Normal Channel");
//                            PendingIntent pendingIntent = PendingIntent.getActivity(MainActivity.this, 0, i, PendingIntent.FLAG_UPDATE_CURRENT);
//                            builder.setContentIntent(pendingIntent);
//
//                            NotificationManager notificationManager = (NotificationManager)getSystemService(Context.NOTIFICATION_SERVICE);
//                            notificationManager.createNotificationChannel(channel);
//                            notificationManager.notify(0, builder.build());
//                        }
                        startActivity(intent);
                        MainActivity.this.finish();
                    }
                    else
                    {
                        Toast.makeText(MainActivity.this, "Username or password incorrect \n or does not exist", Toast.LENGTH_SHORT).show();
                    }
                }
            }
        });

    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.main1, menu);
        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {

        switch(item.getItemId())
        {
            case R.id.off:
                AlertDialog.Builder dialog = new AlertDialog.Builder(MainActivity.this);
                dialog.setTitle("Important")
                        .setMessage("Do you really want to exit ?")
                        .setCancelable(false)
                        .setPositiveButton("OK", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {
                                MainActivity.this.finish();
                            }
                        })
                        .setNegativeButton("CANCEL", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {

                            }
                        });
                dialog.show();
                break;
        }

        return super.onOptionsItemSelected(item);
    }
}
