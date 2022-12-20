package com.example.coronavirus19status_app;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import androidx.annotation.NonNull;

import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.google.android.material.navigation.NavigationBarView;


public class vaccine extends Activity {
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.vaccine);

        TextView daily_first_complete_API = (TextView) findViewById(R.id.daily_first_complete_API);
        daily_first_complete_API.setText(((MainActivity)MainActivity.mContext).getDaily_first_complete_API());

        TextView daily_second_complete_API = (TextView) findViewById(R.id.daily_second_complete_API);
        daily_second_complete_API.setText(((MainActivity)MainActivity.mContext).getDaily_second_complete_API());

        TextView daily_third_complete_API = (TextView) findViewById(R.id.daily_third_complete_API);
        daily_third_complete_API.setText(((MainActivity)MainActivity.mContext).getDaily_third_complete_API());

        TextView Accumulated_first_complete_API = (TextView) findViewById(R.id.Accumulated_first_complete_API);
        Accumulated_first_complete_API.setText(((MainActivity)MainActivity.mContext).getAccumulated_first_complete_API());

        TextView Accumulated_second_complete_API = (TextView) findViewById(R.id.Accumulated_second_complete_API);
        Accumulated_second_complete_API.setText(((MainActivity)MainActivity.mContext).getAccumulated_second_complete_API());

        TextView Accumulated_third_complete_API = (TextView) findViewById(R.id.Accumulated_third_complete_API);
        Accumulated_third_complete_API.setText(((MainActivity)MainActivity.mContext).getAccumulated_third_complete_API());


        Button vaccine_type= (Button)findViewById(R.id.Vaccine_type_button);
        vaccine_type.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(getApplicationContext(), vaccine_type.class);
                startActivity(intent);
            }
        });
        Button vaccine_side_effect = (Button) findViewById(R.id.Vaccine_side_effect);
        vaccine_side_effect.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(getApplicationContext(), vaccine_side_effect.class);
                startActivity(intent);
            }
        });
        BottomNavigationView bottom_menu = findViewById(R.id.bottom_menu);
        bottom_menu.setOnItemSelectedListener(new NavigationBarView.OnItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem item) {
                Intent intent;
                switch (item.getItemId()) {
                    case R.id.first_Tab:
                        onBackPressed();
                        return true;
                    case R.id.second_Tab:
                        intent = new Intent(getApplicationContext(), MainPage.class);
                        startActivity(intent);
                        return true;
                    case R.id.third_Tab:
                        intent = new Intent(getApplicationContext(), city.class);
                        startActivity(intent);
                        return true;
                    case R.id.fourth_Tab:
                        return true;
                    case R.id.fifth_Tab:
                        intent = new Intent(getApplicationContext(), corona_state.class);
                        startActivity(intent);
                        return true;
                }
                return false;
            }
        });

    }
}