package com.example.brainteaser;

import androidx.appcompat.app.AppCompatActivity;

import android.database.SQLException;
import android.os.Bundle;
import android.widget.ListView;
import android.widget.Toast;

import java.util.ArrayList;

public class Main4Activity extends AppCompatActivity {

    ListView lvHigh, lvAverage;
    ArrayList<Ranks> list;
    ArrayList<Average> list1;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main4);

        lvHigh = findViewById(R.id.lvHigh);
        lvAverage = findViewById(R.id.lvAverage);

        list = new ArrayList<Ranks>();
        list1 = new ArrayList<Average>();

        try {
            GameDB gameDB = new GameDB(getApplicationContext());
            gameDB.open();
            list = gameDB.getAllHighest();
            gameDB.close();

            RanksAdapter adapter = new RanksAdapter(getApplicationContext(), list);
            lvHigh.setAdapter(adapter);
        }
        catch (SQLException e)
        {
            Toast.makeText(this, e.getMessage(), Toast.LENGTH_SHORT).show();
        }

        try {
            GameDB gameDB1 = new GameDB(getApplicationContext());
            gameDB1.open();
            list1 = gameDB1.getAllAverage();
            gameDB1.close();

            AverageAdapter adapter = new AverageAdapter(getApplicationContext(), list1);
            lvAverage.setAdapter(adapter);
        }
        catch (SQLException e)
        {
            Toast.makeText(this, e.getMessage(), Toast.LENGTH_SHORT).show();
        }

    }
}
