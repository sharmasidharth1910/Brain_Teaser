package com.example.brainteaser;

import androidx.appcompat.app.AppCompatActivity;

import android.database.SQLException;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

public class Main2Activity extends AppCompatActivity {

    EditText etUser, etPass, etRepass;
    Button btnSignup;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main2);

        etUser = findViewById(R.id.etUser);
        etRepass = findViewById(R.id.etRepass);
        etPass = findViewById(R.id.etPass);
        btnSignup = findViewById(R.id.btnSignup);

        btnSignup.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                if (etUser.getText().toString().trim().isEmpty())
                {
                    etUser.setError("Required");
                }
                if (etPass.getText().toString().trim().isEmpty())
                {
                    etPass.setError("Required");
                }
                if (etRepass.getText().toString().trim().isEmpty())
                {
                    etRepass.setError("Required");
                }
                if(!etRepass.getText().toString().equals(etPass.getText().toString()))
                {
                    etRepass.setError("Should be same as above");
                }
                if (!etUser.getText().toString().trim().isEmpty() && !etPass.getText().toString().trim().isEmpty() && etRepass.getText().toString().equals(etPass.getText().toString()))
                {
                    String username = etUser.getText().toString().trim();
                    String password = etPass.getText().toString().trim();

                    try {
                        GameDB gameDB = new GameDB(getApplicationContext());
                        gameDB.open();
                        int flag;
                        flag = gameDB.checkUser(username,password);
                        if (flag != 0) {
                            Toast.makeText(Main2Activity.this, "User already exists", Toast.LENGTH_SHORT).show();
                            gameDB.close();
                        } else {
                            gameDB.createEntry(username, password);
                            gameDB.close();
                            Toast.makeText(Main2Activity.this, "Successfully registered the user !", Toast.LENGTH_SHORT).show();
                            etUser.setText("");
                            etPass.setText("");
                            etRepass.setText("");
                            Main2Activity.this.finish();
                        }
                    }
                    catch(SQLException e)
                        {
                            Toast.makeText(Main2Activity.this, e.getMessage(), Toast.LENGTH_SHORT).show();
                        }
                }

            }
        });

    }
}
