package com.example.coronavirus19status_app;


import android.app.Activity;
import android.os.Bundle;
import android.content.Intent;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;

import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.google.android.material.navigation.NavigationBarView;


public class MainPage extends Activity {

    private long backKeyPressedTime = 0;
    private Toast toast;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.corona_main);

        TextView time = (TextView) findViewById(R.id.time);
        time.setText(((MainActivity) MainActivity.mContext).getDateText());

        TextView all_info = (TextView) findViewById(R.id.corona_state);
        all_info.setText(((MainActivity)MainActivity.mContext).getTotal());

        Button city_button = (Button) findViewById(R.id.city_button);
        city_button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(getApplicationContext(), city.class);
                startActivity(intent);
            }
        });

        Button vaccine = (Button) findViewById(R.id.vaccine_button);
        vaccine.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(getApplicationContext(), vaccine.class);
                startActivity(intent);
            }
        });

        Button corona_state = (Button) findViewById(R.id.corona_state_button);
        corona_state.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(getApplicationContext(), corona_state.class);
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
                        return true;
                    case R.id.third_Tab:
                        intent = new Intent(getApplicationContext(), city.class);
                        startActivity(intent);
                        return true;
                    case R.id.fourth_Tab:
                        intent = new Intent(getApplicationContext(), vaccine.class);
                        startActivity(intent);
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
    @Override
    public void onBackPressed() {
        if (System.currentTimeMillis() > backKeyPressedTime + 2500) {
            backKeyPressedTime = System.currentTimeMillis();
            toast = Toast.makeText(this, "뒤로 가기 버튼을 한 번 더 누르시면 종료됩니다.", Toast.LENGTH_LONG);
            toast.show();
            return;
        }
        if (System.currentTimeMillis() <= backKeyPressedTime + 2500) {
            finish();
            toast.cancel();
            toast = Toast.makeText(this,"이용해 주셔서 감사합니다.",Toast.LENGTH_LONG);
            toast.show();
            moveTaskToBack(true);
            finishAndRemoveTask();
            System.exit(0);
        }
    }
}