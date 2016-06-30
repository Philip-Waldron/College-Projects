package com.lsa.lol.summonerapp;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

public class MainActivity extends Activity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
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
                if(region.matches(""))
                    region = "EUW";
                if(summoner.matches(""))
                {
                    Toast.makeText(MainActivity.this, "Error: Please enter a summoner name", Toast.LENGTH_LONG).show();
                }
                else
                {
                    Intent intent = new Intent(MainActivity.this, Main2Activity.class);
                    intent.putExtra("SUMMONER", summoner);
                    intent.putExtra("REGION", region);
                    startActivity(intent);
                }

            }
        });

        TextView getUpdates = (TextView)findViewById(R.id.getUpdates);
        getUpdates.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {

                Intent getUpdateIntent = new Intent(MainActivity.this, UserAddActivity.class);
                startActivity(getUpdateIntent);

            }
        });

    }

}
