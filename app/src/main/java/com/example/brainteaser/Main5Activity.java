package com.example.brainteaser;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.Toast;

public class Main5Activity extends AppCompatActivity {

    ImageView ivCall, ivMessage, ivMail, ivFace, ivInsta, ivGit;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main5);

        ivCall = findViewById(R.id.ivCall);
        ivMessage = findViewById(R.id.ivMessage);
        ivMail = findViewById(R.id.ivMail);
        ivFace = findViewById(R.id.ivFace);
        ivInsta = findViewById(R.id.ivInsta);
        ivGit = findViewById(R.id.ivGit);

        ivCall.setOnLongClickListener(new View.OnLongClickListener() {
            @Override
            public boolean onLongClick(View v) {

                Toast.makeText(Main5Activity.this, "Click to call !", Toast.LENGTH_SHORT).show();

                return false;
            }
        });

        ivMail.setOnLongClickListener(new View.OnLongClickListener() {
            @Override
            public boolean onLongClick(View v) {

                Toast.makeText(Main5Activity.this, "Click to mail !", Toast.LENGTH_SHORT).show();

                return false;
            }
        });

        ivMessage.setOnLongClickListener(new View.OnLongClickListener() {
            @Override
            public boolean onLongClick(View v) {

                Toast.makeText(Main5Activity.this, "Click to message !", Toast.LENGTH_SHORT).show();

                return false;
            }
        });

        ivFace.setOnLongClickListener(new View.OnLongClickListener() {
            @Override
            public boolean onLongClick(View v) {

                Toast.makeText(Main5Activity.this, "Click to open Facebook !", Toast.LENGTH_SHORT).show();

                return true;
            }
        });

        ivGit.setOnLongClickListener(new View.OnLongClickListener() {
            @Override
            public boolean onLongClick(View v) {

                Toast.makeText(Main5Activity.this, "Click to open Github !", Toast.LENGTH_SHORT).show();

                return true;
            }
        });

        ivInsta.setOnLongClickListener(new View.OnLongClickListener() {
            @Override
            public boolean onLongClick(View v) {

                Toast.makeText(Main5Activity.this, "Click to open Instagram !", Toast.LENGTH_SHORT).show();

                return true;
            }
        });

        ivCall.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(Intent.ACTION_DIAL, Uri.parse("tel:0000000000"));
                startActivity(intent);
            }
        });

        ivMessage.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(Intent.ACTION_SEND);
                intent.putExtra(Intent.EXTRA_TEXT, "Hello");
                intent.setType("text/plain");
                Intent i = Intent.createChooser(intent, "Message through");
                startActivity(i);
            }
        });

        ivMail.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(Intent.ACTION_SEND);
                intent.setType("text/html");
                intent.putExtra(Intent.EXTRA_EMAIL, "emailaddress@emailaddress.com");
                intent.putExtra(Intent.EXTRA_SUBJECT, "Subject");
                intent.putExtra(Intent.EXTRA_TEXT, "I'm email body.");

                startActivity(Intent.createChooser(intent, "Send Email"));
            }
        });

        ivFace.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(Intent.ACTION_VIEW, Uri.parse("https://www.facebook.com/"));
                startActivity(intent);
            }
        });

        ivInsta.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(Intent.ACTION_VIEW, Uri.parse("https://www.instagram.com/"));
                startActivity(intent);
            }
        });

        ivGit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(Intent.ACTION_VIEW, Uri.parse("https://github.com/sharmasidharth1910"));
                startActivity(intent);
            }
        });
    }
}
