package com.example.coronavirus19status_app;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;

import androidx.annotation.NonNull;

import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.google.android.material.navigation.NavigationBarView;

public class city extends Activity {
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.city);
        String pas = "";
        int creatStart;
        int creatEnd;

        Button Gangwon = (Button) findViewById(R.id.Gangwon_button);
        pas = ((MainActivity) MainActivity.mContext).getGangwon();
        creatStart = pas.indexOf("일일 확진자 수 = ");
        creatEnd = pas.indexOf("\n");
        Gangwon.setText("강원도\n" + pas.substring(creatStart, creatEnd).replace("일일 확진자 수 = ", ""));

        Button Chungcheongbuk = (Button) findViewById(R.id.Chungcheongbuk_button);
        pas = ((MainActivity) MainActivity.mContext).getChungcheongbuk();
        creatStart = pas.indexOf("일일 확진자 수 = ");
        creatEnd = pas.indexOf("\n");
        Chungcheongbuk.setText("충청북도\n" + pas.substring(creatStart, creatEnd).replace("일일 확진자 수 = ", ""));

        Button Daejeon = (Button) findViewById(R.id.Daejeon_button);
        pas = ((MainActivity) MainActivity.mContext).getDaejeon();
        creatStart = pas.indexOf("일일 확진자 수 = ");
        creatEnd = pas.indexOf("\n");
        Daejeon.setText("대전\n" + pas.substring(creatStart, creatEnd).replace("일일 확진자 수 = ", ""));

        Button Daegu = (Button) findViewById(R.id.Daegu_button);
        pas = ((MainActivity) MainActivity.mContext).getDaegu();
        creatStart = pas.indexOf("일일 확진자 수 = ");
        creatEnd = pas.indexOf("\n");
        Daegu.setText("대구\n" + pas.substring(creatStart, creatEnd).replace("일일 확진자 수 = ", ""));

        Button Gyeongsangbuk = (Button) findViewById(R.id.Gyeongsangbuk_button);
        pas = ((MainActivity) MainActivity.mContext).getGyeongsangbuk();
        creatStart = pas.indexOf("일일 확진자 수 = ");
        creatEnd = pas.indexOf("\n");
        Gyeongsangbuk.setText("경상북도\n" + pas.substring(creatStart, creatEnd).replace("일일 확진자 수 = ", ""));

        Button Ulsan = (Button) findViewById(R.id.Ulsan_button);
        pas = ((MainActivity) MainActivity.mContext).getUlsan();
        creatStart = pas.indexOf("일일 확진자 수 = ");
        creatEnd = pas.indexOf("\n");
        Ulsan.setText("울산\n" + pas.substring(creatStart, creatEnd).replace("일일 확진자 수 = ", ""));

        Button Gyeongsangnam = (Button) findViewById(R.id.Gyeongsangnam_button);
        pas = ((MainActivity) MainActivity.mContext).getGyeongsangnam();
        creatStart = pas.indexOf("일일 확진자 수 = ");
        creatEnd = pas.indexOf("\n");
        Gyeongsangnam.setText("경상남도\n" + pas.substring(creatStart, creatEnd).replace("일일 확진자 수 = ", ""));

        Button Gyeonggi = (Button) findViewById(R.id.Gyeonggi_button);
        pas = ((MainActivity) MainActivity.mContext).getGyeonggi();
        creatStart = pas.indexOf("일일 확진자 수 = ");
        creatEnd = pas.indexOf("\n");
        Gyeonggi.setText("경기도\n" + pas.substring(creatStart, creatEnd).replace("일일 확진자 수 = ", ""));

        Button Jeju = (Button) findViewById(R.id.Jeju_button);
        pas = ((MainActivity) MainActivity.mContext).getJeju();
        creatStart = pas.indexOf("일일 확진자 수 = ");
        creatEnd = pas.indexOf("\n");
        Jeju.setText("제주도\n" + pas.substring(creatStart, creatEnd).replace("일일 확진자 수 = ", ""));

        Button Seoul = (Button) findViewById(R.id.Seoul_button);
        pas = ((MainActivity) MainActivity.mContext).getSeoul();
        creatStart = pas.indexOf("일일 확진자 수 = ");
        creatEnd = pas.indexOf("\n");
        Seoul.setText("서울\n" + pas.substring(creatStart, creatEnd).replace("일일 확진자 수 = ", ""));

        Button Busan = (Button) findViewById(R.id.Busan_button);
        pas = ((MainActivity) MainActivity.mContext).getBusan();
        creatStart = pas.indexOf("일일 확진자 수 = ");
        creatEnd = pas.indexOf("\n");
        Busan.setText("부산\n" + pas.substring(creatStart, creatEnd).replace("일일 확진자 수 = ", ""));

        Button Gwangju = (Button) findViewById(R.id.Gwangju_button);
        pas = ((MainActivity) MainActivity.mContext).getGwangju();
        creatStart = pas.indexOf("일일 확진자 수 = ");
        creatEnd = pas.indexOf("\n");
        Gwangju.setText("광주\n" + pas.substring(creatStart, creatEnd).replace("일일 확진자 수 = ", ""));

        Button Jeollanam = (Button) findViewById(R.id.Jeollanam_button);
        pas = ((MainActivity) MainActivity.mContext).getJeollanam();
        creatStart = pas.indexOf("일일 확진자 수 = ");
        creatEnd = pas.indexOf("\n");
        Jeollanam.setText("전라남도\n" + pas.substring(creatStart, creatEnd).replace("일일 확진자 수 = ", ""));

        Button Incheon = (Button) findViewById(R.id.Incheon_button);
        pas = ((MainActivity) MainActivity.mContext).getIncheon();
        creatStart = pas.indexOf("일일 확진자 수 = ");
        creatEnd = pas.indexOf("\n");
        Incheon.setText("인천\n" + pas.substring(creatStart, creatEnd).replace("일일 확진자 수 = ", ""));

        Button Sejong = (Button) findViewById(R.id.Sejong_button);
        pas = ((MainActivity) MainActivity.mContext).getSejong();
        creatStart = pas.indexOf("일일 확진자 수 = ");
        creatEnd = pas.indexOf("\n");
        Sejong.setText("세종\n" + pas.substring(creatStart, creatEnd).replace("일일 확진자 수 = ", ""));

        Button Chungcheongnam = (Button) findViewById(R.id.Chungcheongnam_button);
        pas = ((MainActivity) MainActivity.mContext).getChungcheongnam();
        creatStart = pas.indexOf("일일 확진자 수 = ");
        creatEnd = pas.indexOf("\n");
        Chungcheongnam.setText("충청남도\n" + pas.substring(creatStart, creatEnd).replace("일일 확진자 수 = ", ""));

        Button Jeollabuk = (Button) findViewById(R.id.Jeollabuk_button);
        pas = ((MainActivity) MainActivity.mContext).getJeollabuk();
        creatStart = pas.indexOf("일일 확진자 수 = ");
        creatEnd = pas.indexOf("\n");
        Jeollabuk.setText("전라북도\n" + pas.substring(creatStart, creatEnd).replace("일일 확진자 수 = ", ""));


        Button seoul_button = (Button) findViewById(R.id.Seoul_button);
        seoul_button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(getApplicationContext(), Seoul.class);
                startActivity(intent);
            }
        });
        Button busan_button = (Button) findViewById(R.id.Busan_button);
        busan_button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(getApplicationContext(), Busan.class);
                startActivity(intent);
            }
        });
        Button ulsan_button = (Button) findViewById(R.id.Ulsan_button);
        ulsan_button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(getApplicationContext(), Ulsan.class);
                startActivity(intent);
            }
        });
        Button chungcheongbuk_button = (Button) findViewById(R.id.Chungcheongbuk_button);
        chungcheongbuk_button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(getApplicationContext(), ChungCheongBuk.class);
                startActivity(intent);
            }
        });
        Button chungcheongnam_button = (Button) findViewById(R.id.Chungcheongnam_button);
        chungcheongnam_button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(getApplicationContext(), Chungcheongnam.class);
                startActivity(intent);
            }
        });
        Button daegu_button = (Button) findViewById(R.id.Daegu_button);
        daegu_button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(getApplicationContext(), Daegu.class);
                startActivity(intent);
            }
        });
        Button daejeon_button = (Button) findViewById(R.id.Daejeon_button);
        daejeon_button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(getApplicationContext(), Daejeon.class);
                startActivity(intent);
            }
        });
        Button gangwon_button = (Button) findViewById(R.id.Gangwon_button);
        gangwon_button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(getApplicationContext(), Gangwon.class);
                startActivity(intent);
            }
        });
        Button gwangju_button = (Button) findViewById(R.id.Gwangju_button);
        gwangju_button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(getApplicationContext(), Gwangju.class);
                startActivity(intent);
            }
        });
        Button gyeonggi_button = (Button) findViewById(R.id.Gyeonggi_button);
        gyeonggi_button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(getApplicationContext(), Gyeonggi.class);
                startActivity(intent);
            }
        });
        Button gyeongsangnam_button = (Button) findViewById(R.id.Gyeongsangnam_button);
        gyeongsangnam_button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(getApplicationContext(), Gyeongsangnam.class);
                startActivity(intent);
            }
        });
        Button gyeongsangbuk_button = (Button) findViewById(R.id.Gyeongsangbuk_button);
        gyeongsangbuk_button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(getApplicationContext(), Gyeongsangbuk.class);
                startActivity(intent);
            }
        });
        Button incheon_button = (Button) findViewById(R.id.Incheon_button);
        incheon_button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(getApplicationContext(), Incheon.class);
                startActivity(intent);
            }
        });
        Button jeju_button = (Button) findViewById(R.id.Jeju_button);
        jeju_button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(getApplicationContext(), Jeju.class);
                startActivity(intent);
            }
        });
        Button jeollabuk_button = (Button) findViewById(R.id.Jeollabuk_button);
        jeollabuk_button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(getApplicationContext(), Jeollabuk.class);
                startActivity(intent);
            }
        });
        Button jeollanam_button = (Button) findViewById(R.id.Jeollanam_button);
        jeollanam_button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(getApplicationContext(), Jeollanam.class);
                startActivity(intent);
            }
        });
        Button sejong_button = (Button) findViewById(R.id.Sejong_button);
        sejong_button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(getApplicationContext(), Sejong.class);
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
}
