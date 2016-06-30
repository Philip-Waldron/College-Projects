package com.lsa.lol.summonerapp;

import android.preference.PreferenceManager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.Toast;

public class UserAddActivity extends AppCompatActivity {


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_user_add);

        final EditText input = (EditText)findViewById(R.id.editText);
        final Spinner dropdown = (Spinner)findViewById(R.id.spinner);
        String[] regions = new String[]{"EUW", "NA", "EUNE"};
        ArrayAdapter<String> adapter = new ArrayAdapter<>(this, android.R.layout.simple_spinner_dropdown_item, regions);
        dropdown.setAdapter(adapter);

        Button submit= (Button)findViewById(R.id.button);
        submit.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                String summoner = input.getText().toString();
                String region = dropdown.getSelectedItem().toString();
                if (region.matches(""))
                    region = "EUW";
                if (summoner.matches("")) {
                    Toast.makeText(UserAddActivity.this, "Error: Please enter a summoner name", Toast.LENGTH_LONG).show();
                } else {
                    PreferenceManager.getDefaultSharedPreferences(UserAddActivity.this).edit().putString("summoner", summoner).commit();
                    PreferenceManager.getDefaultSharedPreferences(UserAddActivity.this).edit().putString("region", region).commit();

                    Toast.makeText(UserAddActivity.this, "Data Submitted", Toast.LENGTH_LONG).show();
                }

            }
        });
    }
}
