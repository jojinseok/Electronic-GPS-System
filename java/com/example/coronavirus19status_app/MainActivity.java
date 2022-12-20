package com.example.coronavirus19status_app;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.os.Bundle;
import android.util.Log;
import android.widget.Button;
import android.content.Intent;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.net.URLEncoder;
import java.text.SimpleDateFormat;
import java.util.Date;


public class MainActivity extends AppCompatActivity {

    long mNow;
    Date mDate;
    SimpleDateFormat mFormat = new SimpleDateFormat("yyyyMMdd");
    final int CONN_TIME = 5000;

    private FirebaseAuth mFireBaseAuth; // FireBase 인증
    private DatabaseReference mDataBaseRef; // 실시간 데이터베이스
    private EditText mEdtID, mEdtPw; // 회원가입 입력필드

    String dateText;
    String daily_confirmed_API;
    String Accumulated_confirmed_API;
    String daily_dead_API;
    String Accumulated_dead_API;
    String daily_exam_API;
    String Accumulated_exam_API;
    String daily_first_complete_API;
    String Accumulated_first_complete_API;
    String daily_second_complete_API;
    String Accumulated_second_complete_API;
    String daily_third_complete_API;
    String Accumulated_third_complete_API;
    String City_API;

    String Gangwon = "";
    String Chungcheongbuk = "";
    String Daejeon = "";
    String Daegu = "";
    String Gyeongsangbuk = "";
    String Ulsan = "";
    String Gyeongsangnam = "";
    String Gyeonggi = "";
    String Jeju = "";
    String Seoul = "";
    String Busan = "";
    String Gwangju = "";
    String Jeollanam = "";
    String Incheon = "";
    String Sejong = "";
    String Chungcheongnam = "";
    String Jeollabuk = "";
    String Total = "";

    String daily_accumulated_isolation_string;
    String accumulated_isolation_string;

    int daily_accumulated_isolation;
    int accumulated_isolation_today;

    public static Context mContext;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.login_page);

        mContext = this;

        mFireBaseAuth = FirebaseAuth.getInstance();
        mDataBaseRef = FirebaseDatabase.getInstance().getReference("coronaStatusApp");

        EditText Editid = (EditText) findViewById(R.id.enterId);
        EditText EditPW = (EditText) findViewById(R.id.enterPass);

        Button loginBt1 = (Button) findViewById(R.id.loginButton);
        loginBt1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                // 로그인 요청
                String strID = Editid.getText().toString();
                String strPwd = EditPW.getText().toString();

                if(!strID.equals("") && !strPwd.equals("")) {
                    mFireBaseAuth.signInWithEmailAndPassword(strID, strPwd).addOnCompleteListener(MainActivity.this, new OnCompleteListener<AuthResult>() {
                        @Override
                        public void onComplete(@NonNull Task<AuthResult> task) {
                            if (task.isSuccessful()) {
                                Intent intent = new Intent(MainActivity.this, MainPage.class);
                                startActivity(intent);
                                Log.d("dsdsd", strID);
                            } else {
                                Toast.makeText(MainActivity.this, "로그인 실패..!", Toast.LENGTH_SHORT).show();
                            }
                        }
                    });

                    try {
                        new Thread() {
                            public void run() {
                                try {
                                    String coronadata = getHTML(); //데이터 불러오기
                                    String coronadata2 = getHTML1();
                                    String vaccine_api = Vaccine_api();
                                    String city_api = City_api();
                                    String city_api1 = City_api1();


                                    //날짜
                                    int createStart = coronadata.indexOf("<createDt>");
                                    int createEnd = coronadata.indexOf("</createDt>");
                                    String createDt = coronadata.substring(createStart, createEnd);
                                    String createDtString = createDt.replace("<createDt>", "기준 날짜 = ");
                                    dateText = createDtString;

                                    int Accumulated_confirmedStart = coronadata.indexOf("<decideCnt>");
                                    int Accumulated_confirmedEnd = coronadata.indexOf("</decideCnt>");
                                    String Accumulated_confirmedCnt = coronadata.substring(Accumulated_confirmedStart, Accumulated_confirmedEnd);
                                    String Accumulated_confirmedCnt1 = Accumulated_confirmedCnt.replace("<decideCnt>", "");
                                    Accumulated_confirmed_API = Accumulated_confirmedCnt1;


                                    int daily_confirmedStart = coronadata2.indexOf("<decideCnt>");
                                    int daily_confirmedEnd = coronadata2.indexOf("</decideCnt>");
                                    String daily_confirmedCnt = coronadata2.substring(daily_confirmedStart, daily_confirmedEnd);
                                    String daily_confirmed_Cnt = daily_confirmedCnt.replace("<decideCnt>", "");
                                    String Accumulated_confirm_Cnt = Accumulated_confirmedCnt.replace("<decideCnt>", "");
                                    try {
                                        int daily_confirmed_cnt = Integer.parseInt(daily_confirmed_Cnt);
                                        int accumulated_confirmed_cnt = Integer.parseInt(Accumulated_confirm_Cnt);
                                        int daily_confirmed_cnt1 = accumulated_confirmed_cnt - daily_confirmed_cnt;
                                        String daily_confirmedCnt1 = Integer.toString(daily_confirmed_cnt1);
                                        daily_confirmed_API = daily_confirmedCnt1;

                                    } catch (Exception e) {
                                        e.printStackTrace();
                                    }

                                    int Accumulated_deathStart = coronadata.indexOf("<deathCnt>");
                                    int Accumulated_deathEnd = coronadata.indexOf("</deathCnt>");
                                    String Accumulated_deathCnt = coronadata.substring(Accumulated_deathStart, Accumulated_deathEnd);
                                    String Accumulated_deathCnt1 = Accumulated_deathCnt.replace("<deathCnt>", "");
                                    Accumulated_dead_API = Accumulated_deathCnt1;


                                    int daily_deathStart = coronadata2.indexOf("<deathCnt>");
                                    int daily_deathEnd = coronadata2.indexOf("</deathCnt>");
                                    String daily_deathCnt = coronadata2.substring(daily_deathStart, daily_deathEnd);
                                    String daily_death_Cnt = daily_deathCnt.replace("<deathCnt>", "");
                                    String Accumulated_death_Cnt = Accumulated_deathCnt.replace("<deathCnt>", "");
                                    try {
                                        int daily_death_cnt = Integer.parseInt(daily_death_Cnt);
                                        int accumulated_death_cnt = Integer.parseInt(Accumulated_death_Cnt);
                                        int daily_death_cnt1 = accumulated_death_cnt - daily_death_cnt;
                                        String daily_deathCnt1 = Integer.toString(daily_death_cnt1);
                                        daily_dead_API = daily_deathCnt1;

                                    } catch (Exception e) {
                                        e.printStackTrace();
                                    }


                                    int Accumulated_examStart = coronadata.indexOf("<accExamCnt>");
                                    int Accumulated_examEnd = coronadata.indexOf("</accExamCnt>");
                                    String Accumulated_examCnt = coronadata.substring(Accumulated_examStart, Accumulated_examEnd);
                                    String Accumulated_examCnt1 = Accumulated_examCnt.replace("<accExamCnt>", "");
                                    Accumulated_exam_API = Accumulated_examCnt1;


                                    int daily_examStart = coronadata2.indexOf("<accExamCnt>");
                                    int daily_examEnd = coronadata2.indexOf("</accExamCnt>");
                                    String daily_examCnt = coronadata2.substring(daily_examStart, daily_examEnd);
                                    String daily_exam_Cnt = daily_examCnt.replace("<accExamCnt>", "");
                                    String Accumulated_exam_Cnt = Accumulated_examCnt.replace("<accExamCnt>", "");
                                    try {
                                        int daily_exam_cnt = Integer.parseInt(daily_exam_Cnt);
                                        int accumulated_exam_cnt = Integer.parseInt(Accumulated_exam_Cnt);
                                        int daily_exam_cnt1 = accumulated_exam_cnt - daily_exam_cnt;
                                        String daily_examCnt1 = Integer.toString(daily_exam_cnt1);
                                        daily_exam_API = daily_examCnt1;

                                    } catch (Exception e) {
                                        e.printStackTrace();
                                    }

                                    int daily_firstCntStart = vaccine_api.indexOf("<firstCnt>");
                                    int daily_firstCntEnd = vaccine_api.indexOf("</firstCnt>");
                                    String daily_firstCnt = vaccine_api.substring(daily_firstCntStart, daily_firstCntEnd);
                                    String daily_firstCnt1 = daily_firstCnt.replace("<firstCnt>", "일일 1차 접종자 수 = ");
                                    daily_first_complete_API = daily_firstCnt1;

                                    int daily_secondCntStart = vaccine_api.indexOf("<secondCnt>");
                                    int daily_secondCntEnd = vaccine_api.indexOf("</secondCnt>");
                                    String daily_secondCnt = vaccine_api.substring(daily_secondCntStart, daily_secondCntEnd);
                                    String daily_secondCnt1 = daily_secondCnt.replace("<secondCnt>", "일일 2차 접종자 수 = ");
                                    daily_second_complete_API = daily_secondCnt1;

                                    int daily_thirdCntStart = vaccine_api.indexOf("<thirdCnt>");
                                    int daily_thirdCntEnd = vaccine_api.indexOf("</thirdCnt>");
                                    String daily_thirdCnt = vaccine_api.substring(daily_thirdCntStart, daily_thirdCntEnd);
                                    String daily_thirdCnt1 = daily_thirdCnt.replace("<thirdCnt>", "일일 3차 접종자 수 = ");
                                    daily_third_complete_API = daily_thirdCnt1;

                                    int rev = vaccine_api.indexOf("(A)+(B)");
                                    StringBuffer sb = new StringBuffer();
                                    sb.append(vaccine_api);
                                    String rev_vaccine_api = sb.delete(0, rev).toString();
                                    int accumulated_firstCntStart = rev_vaccine_api.indexOf("<firstCnt>");
                                    int accumulated_firstCntEnd = rev_vaccine_api.indexOf("</firstCnt>");
                                    String accumulated_firstCnt = rev_vaccine_api.substring(accumulated_firstCntStart, accumulated_firstCntEnd);
                                    String accumulated_firstCnt1 = accumulated_firstCnt.replace("<firstCnt>", "누적 1차 접종자 수 = ");
                                    Accumulated_first_complete_API = accumulated_firstCnt1;

                                    int accumulated_secondCntStart = rev_vaccine_api.indexOf("<secondCnt>");
                                    int accumulated_secondCntEnd = rev_vaccine_api.indexOf("</secondCnt>");
                                    String accumulated_secondCnt = rev_vaccine_api.substring(accumulated_secondCntStart, accumulated_secondCntEnd);
                                    String accumulated_secondCnt1 = accumulated_secondCnt.replace("<secondCnt>", "누적 2차 접종자 수 = ");
                                    Accumulated_second_complete_API = accumulated_secondCnt1;


                                    int accumulated_thirdCntStart = rev_vaccine_api.indexOf("<thirdCnt>");
                                    int accumulated_thirdCntEnd = rev_vaccine_api.indexOf("</thirdCnt>");
                                    String accumulated_thirdCnt = rev_vaccine_api.substring(accumulated_thirdCntStart, accumulated_thirdCntEnd);
                                    String accumulated_thirdCnt1 = accumulated_thirdCnt.replace("<thirdCnt>", "누적 3차 접종자 수 = ");
                                    Accumulated_third_complete_API = accumulated_thirdCnt1;

                                    int Start = city_api.indexOf("<defCnt>");
                                    int End = city_api.indexOf("</defCnt>");
                                    String sestring = city_api.substring(Start, End);
                                    String sestring1 = sestring.replace("defCnt", "확진자 수 = ");
                                    City_API = sestring1;

                                    ////////////////////////////////////////////////////////////////////////////////////

                                    city_api = City_api();
                                    sb = new StringBuffer();
                                    sb.append(city_api);
                                    int i = 0;
                                    String item[] = new String[20];
                                    int city_Start;
                                    int city_End;
                                    String city_data;
                                    String city_data_string;

                                    for (i = 0; i < 19; i++) {

                                        city_Start = sb.indexOf("<item>");
                                        city_End = sb.indexOf("</item>");
                                        item[i] = sb.substring(city_Start, city_End);
                                        sb.delete(city_Start, city_End + 2);
                                        if (item[i].contains("Gyeonggi")) {

                                            city_Start = item[i].indexOf("<incDec>");
                                            city_End = item[i].indexOf("</incDec>");
                                            city_data = item[i].substring(city_Start, city_End);
                                            city_data_string = city_data.replace("<incDec>", "일일 확진자 수 = ");
                                            Gyeonggi = Gyeonggi + city_data_string;

                                            city_Start = item[i].indexOf("<overFlowCnt>");
                                            city_End = item[i].indexOf("</overFlowCnt>");
                                            city_data = item[i].substring(city_Start, city_End);
                                            city_data_string = city_data.replace("<overFlowCnt>", "일일 해외 유입자 수 = ");
                                            Gyeonggi = Gyeonggi + "\n" + city_data_string;

                                            city_Start = item[i].indexOf("<qurRate>");
                                            city_End = item[i].indexOf("</qurRate>");
                                            city_data = item[i].substring(city_Start, city_End);
                                            city_data_string = city_data.replace("<qurRate>", "10만명 당 확진자 수 = ");
                                            Gyeonggi = Gyeonggi + "\n" + city_data_string;

                                            city_Start = item[i].indexOf("<defCnt>");
                                            city_End = item[i].indexOf("</defCnt>");
                                            city_data = item[i].substring(city_Start, city_End);
                                            city_data_string = city_data.replace("<defCnt>", "누적 확진자 수 = ");
                                            Gyeonggi = Gyeonggi + "\n" + city_data_string;

                                            city_Start = item[i].indexOf("<isolClearCnt>");
                                            city_End = item[i].indexOf("</isolClearCnt>");
                                            city_data = item[i].substring(city_Start, city_End);
                                            city_data_string = city_data.replace("<isolClearCnt>", "누적 완치자 수 = ");
                                            Gyeonggi = Gyeonggi + "\n" + city_data_string;

                                            city_Start = item[i].indexOf("<deathCnt>");
                                            city_End = item[i].indexOf("</deathCnt>");
                                            city_data = item[i].substring(city_Start, city_End);
                                            city_data_string = city_data.replace("<deathCnt>", "누적 사망자 수 = ");
                                            Gyeonggi = Gyeonggi + "\n" + city_data_string;

                                        } else if (item[i].contains("Sejong")) {
                                            city_Start = item[i].indexOf("<incDec>");
                                            city_End = item[i].indexOf("</incDec>");
                                            city_data = item[i].substring(city_Start, city_End);
                                            city_data_string = city_data.replace("<incDec>", "일일 확진자 수 = ");
                                            Sejong = city_data_string;

                                            city_Start = item[i].indexOf("<overFlowCnt>");
                                            city_End = item[i].indexOf("</overFlowCnt>");
                                            city_data = item[i].substring(city_Start, city_End);
                                            city_data_string = city_data.replace("<overFlowCnt>", "일일 해외 유입자 수 = ");
                                            Sejong = Sejong + "\n" + city_data_string;

                                            city_Start = item[i].indexOf("<qurRate>");
                                            city_End = item[i].indexOf("</qurRate>");
                                            city_data = item[i].substring(city_Start, city_End);
                                            city_data_string = city_data.replace("<qurRate>", "10만명 당 확진자 수 = ");
                                            Sejong = Sejong + "\n" + city_data_string;

                                            city_Start = item[i].indexOf("<defCnt>");
                                            city_End = item[i].indexOf("</defCnt>");
                                            city_data = item[i].substring(city_Start, city_End);
                                            city_data_string = city_data.replace("<defCnt>", "누적 확진자 수 = ");
                                            Sejong = Sejong + "\n" + city_data_string;

                                            city_Start = item[i].indexOf("<isolClearCnt>");
                                            city_End = item[i].indexOf("</isolClearCnt>");
                                            city_data = item[i].substring(city_Start, city_End);
                                            city_data_string = city_data.replace("<isolClearCnt>", "누적 완치자 수 = ");
                                            Sejong = Sejong + "\n" + city_data_string;

                                            city_Start = item[i].indexOf("<deathCnt>");
                                            city_End = item[i].indexOf("</deathCnt>");
                                            city_data = item[i].substring(city_Start, city_End);
                                            city_data_string = city_data.replace("<deathCnt>", "누적 사망자 수 = ");
                                            Sejong = Sejong + "\n" + city_data_string;

                                        } else if (item[i].contains("Jeju")) {
                                            city_Start = item[i].indexOf("<incDec>");
                                            city_End = item[i].indexOf("</incDec>");
                                            city_data = item[i].substring(city_Start, city_End);
                                            city_data_string = city_data.replace("<incDec>", "일일 확진자 수 = ");
                                            Jeju = city_data_string;

                                            city_Start = item[i].indexOf("<overFlowCnt>");
                                            city_End = item[i].indexOf("</overFlowCnt>");
                                            city_data = item[i].substring(city_Start, city_End);
                                            city_data_string = city_data.replace("<overFlowCnt>", "일일 해외 유입자 수 = ");
                                            Jeju = Jeju + "\n" + city_data_string;

                                            city_Start = item[i].indexOf("<qurRate>");
                                            city_End = item[i].indexOf("</qurRate>");
                                            city_data = item[i].substring(city_Start, city_End);
                                            city_data_string = city_data.replace("<qurRate>", "10만명 당 확진자 수 = ");
                                            Jeju = Jeju + "\n" + city_data_string;

                                            city_Start = item[i].indexOf("<defCnt>");
                                            city_End = item[i].indexOf("</defCnt>");
                                            city_data = item[i].substring(city_Start, city_End);
                                            city_data_string = city_data.replace("<defCnt>", "누적 확진자 수 = ");
                                            Jeju = Jeju + "\n" + city_data_string;

                                            city_Start = item[i].indexOf("<isolClearCnt>");
                                            city_End = item[i].indexOf("</isolClearCnt>");
                                            city_data = item[i].substring(city_Start, city_End);
                                            city_data_string = city_data.replace("<isolClearCnt>", "누적 완치자 수 = ");
                                            Jeju = Jeju + "\n" + city_data_string;

                                            city_Start = item[i].indexOf("<deathCnt>");
                                            city_End = item[i].indexOf("</deathCnt>");
                                            city_data = item[i].substring(city_Start, city_End);
                                            city_data_string = city_data.replace("<deathCnt>", "누적 사망자 수 = ");
                                            Jeju = Jeju + "\n" + city_data_string;

                                        } else if (item[i].contains("Gyeongsangnam")) {
                                            city_Start = item[i].indexOf("<incDec>");
                                            city_End = item[i].indexOf("</incDec>");
                                            city_data = item[i].substring(city_Start, city_End);
                                            city_data_string = city_data.replace("<incDec>", "일일 확진자 수 = ");
                                            Gyeongsangnam = city_data_string;

                                            city_Start = item[i].indexOf("<overFlowCnt>");
                                            city_End = item[i].indexOf("</overFlowCnt>");
                                            city_data = item[i].substring(city_Start, city_End);
                                            city_data_string = city_data.replace("<overFlowCnt>", "일일 해외 유입자 수 = ");
                                            Gyeongsangnam = Gyeongsangnam + "\n" + city_data_string;

                                            city_Start = item[i].indexOf("<qurRate>");
                                            city_End = item[i].indexOf("</qurRate>");
                                            city_data = item[i].substring(city_Start, city_End);
                                            city_data_string = city_data.replace("<qurRate>", "10만명 당 확진자 수 = ");
                                            Gyeongsangnam = Gyeongsangnam + "\n" + city_data_string;

                                            city_Start = item[i].indexOf("<defCnt>");
                                            city_End = item[i].indexOf("</defCnt>");
                                            city_data = item[i].substring(city_Start, city_End);
                                            city_data_string = city_data.replace("<defCnt>", "누적 확진자 수 = ");
                                            Gyeongsangnam = Gyeongsangnam + "\n" + city_data_string;

                                            city_Start = item[i].indexOf("<isolClearCnt>");
                                            city_End = item[i].indexOf("</isolClearCnt>");
                                            city_data = item[i].substring(city_Start, city_End);
                                            city_data_string = city_data.replace("<isolClearCnt>", "누적 완치자 수 = ");
                                            Gyeongsangnam = Gyeongsangnam + "\n" + city_data_string;

                                            city_Start = item[i].indexOf("<deathCnt>");
                                            city_End = item[i].indexOf("</deathCnt>");
                                            city_data = item[i].substring(city_Start, city_End);
                                            city_data_string = city_data.replace("<deathCnt>", "누적 사망자 수 = ");
                                            Gyeongsangnam = Gyeongsangnam + "\n" + city_data_string;

                                        } else if (item[i].contains("Gyeongsangbuk")) {
                                            city_Start = item[i].indexOf("<incDec>");
                                            city_End = item[i].indexOf("</incDec>");
                                            city_data = item[i].substring(city_Start, city_End);
                                            city_data_string = city_data.replace("<incDec>", "일일 확진자 수 = ");
                                            Gyeongsangbuk = city_data_string;

                                            city_Start = item[i].indexOf("<overFlowCnt>");
                                            city_End = item[i].indexOf("</overFlowCnt>");
                                            city_data = item[i].substring(city_Start, city_End);
                                            city_data_string = city_data.replace("<overFlowCnt>", "일일 해외 유입자 수 = ");
                                            Gyeongsangbuk = Gyeongsangbuk + "\n" + city_data_string;

                                            city_Start = item[i].indexOf("<qurRate>");
                                            city_End = item[i].indexOf("</qurRate>");
                                            city_data = item[i].substring(city_Start, city_End);
                                            city_data_string = city_data.replace("<qurRate>", "10만명 당 확진자 수 = ");
                                            Gyeongsangbuk = Gyeongsangbuk + "\n" + city_data_string;

                                            city_Start = item[i].indexOf("<defCnt>");
                                            city_End = item[i].indexOf("</defCnt>");
                                            city_data = item[i].substring(city_Start, city_End);
                                            city_data_string = city_data.replace("<defCnt>", "누적 확진자 수 = ");
                                            Gyeongsangbuk = Gyeongsangbuk + "\n" + city_data_string;

                                            city_Start = item[i].indexOf("<isolClearCnt>");
                                            city_End = item[i].indexOf("</isolClearCnt>");
                                            city_data = item[i].substring(city_Start, city_End);
                                            city_data_string = city_data.replace("<isolClearCnt>", "누적 완치자 수 = ");
                                            Gyeongsangbuk = Gyeongsangbuk + "\n" + city_data_string;

                                            city_Start = item[i].indexOf("<deathCnt>");
                                            city_End = item[i].indexOf("</deathCnt>");
                                            city_data = item[i].substring(city_Start, city_End);
                                            city_data_string = city_data.replace("<deathCnt>", "누적 사망자 수 = ");
                                            Gyeongsangbuk = Gyeongsangbuk + "\n" + city_data_string;

                                        } else if (item[i].contains("Jeollanam")) {
                                            city_Start = item[i].indexOf("<incDec>");
                                            city_End = item[i].indexOf("</incDec>");
                                            city_data = item[i].substring(city_Start, city_End);
                                            city_data_string = city_data.replace("<incDec>", "일일 확진자 수 = ");
                                            Jeollanam = city_data_string;

                                            city_Start = item[i].indexOf("<overFlowCnt>");
                                            city_End = item[i].indexOf("</overFlowCnt>");
                                            city_data = item[i].substring(city_Start, city_End);
                                            city_data_string = city_data.replace("<overFlowCnt>", "일일 해외 유입자 수 = ");
                                            Jeollanam = Jeollanam + "\n" + city_data_string;

                                            city_Start = item[i].indexOf("<qurRate>");
                                            city_End = item[i].indexOf("</qurRate>");
                                            city_data = item[i].substring(city_Start, city_End);
                                            city_data_string = city_data.replace("<qurRate>", "10만명 당 확진자 수 = ");
                                            Jeollanam = Jeollanam + "\n" + city_data_string;

                                            city_Start = item[i].indexOf("<defCnt>");
                                            city_End = item[i].indexOf("</defCnt>");
                                            city_data = item[i].substring(city_Start, city_End);
                                            city_data_string = city_data.replace("<defCnt>", "누적 확진자 수 = ");
                                            Jeollanam = Jeollanam + "\n" + city_data_string;

                                            city_Start = item[i].indexOf("<isolClearCnt>");
                                            city_End = item[i].indexOf("</isolClearCnt>");
                                            city_data = item[i].substring(city_Start, city_End);
                                            city_data_string = city_data.replace("<isolClearCnt>", "누적 완치자 수 = ");
                                            Jeollanam = Jeollanam + "\n" + city_data_string;

                                            city_Start = item[i].indexOf("<deathCnt>");
                                            city_End = item[i].indexOf("</deathCnt>");
                                            city_data = item[i].substring(city_Start, city_End);
                                            city_data_string = city_data.replace("<deathCnt>", "누적 사망자 수 = ");
                                            Jeollanam = Jeollanam + "\n" + city_data_string;

                                        } else if (item[i].contains("Jeollabuk")) {
                                            city_Start = item[i].indexOf("<incDec>");
                                            city_End = item[i].indexOf("</incDec>");
                                            city_data = item[i].substring(city_Start, city_End);
                                            city_data_string = city_data.replace("<incDec>", "일일 확진자 수 = ");
                                            Jeollabuk = city_data_string;

                                            city_Start = item[i].indexOf("<overFlowCnt>");
                                            city_End = item[i].indexOf("</overFlowCnt>");
                                            city_data = item[i].substring(city_Start, city_End);
                                            city_data_string = city_data.replace("<overFlowCnt>", "일일 해외 유입자 수 = ");
                                            Jeollabuk = Jeollabuk + "\n" + city_data_string;

                                            city_Start = item[i].indexOf("<qurRate>");
                                            city_End = item[i].indexOf("</qurRate>");
                                            city_data = item[i].substring(city_Start, city_End);
                                            city_data_string = city_data.replace("<qurRate>", "10만명 당 확진자 수 = ");
                                            Jeollabuk = Jeollabuk + "\n" + city_data_string;

                                            city_Start = item[i].indexOf("<defCnt>");
                                            city_End = item[i].indexOf("</defCnt>");
                                            city_data = item[i].substring(city_Start, city_End);
                                            city_data_string = city_data.replace("<defCnt>", "누적 확진자 수 = ");
                                            Jeollabuk = Jeollabuk + "\n" + city_data_string;

                                            city_Start = item[i].indexOf("<isolClearCnt>");
                                            city_End = item[i].indexOf("</isolClearCnt>");
                                            city_data = item[i].substring(city_Start, city_End);
                                            city_data_string = city_data.replace("<isolClearCnt>", "누적 완치자 수 = ");
                                            Jeollabuk = Jeollabuk + "\n" + city_data_string;

                                            city_Start = item[i].indexOf("<deathCnt>");
                                            city_End = item[i].indexOf("</deathCnt>");
                                            city_data = item[i].substring(city_Start, city_End);
                                            city_data_string = city_data.replace("<deathCnt>", "누적 사망자 수 = ");
                                            Jeollabuk = Jeollabuk + "\n" + city_data_string;

                                        } else if (item[i].contains("Chungcheongnam")) {
                                            city_Start = item[i].indexOf("<incDec>");
                                            city_End = item[i].indexOf("</incDec>");
                                            city_data = item[i].substring(city_Start, city_End);
                                            city_data_string = city_data.replace("<incDec>", "일일 확진자 수 = ");
                                            Chungcheongnam = city_data_string;

                                            city_Start = item[i].indexOf("<overFlowCnt>");
                                            city_End = item[i].indexOf("</overFlowCnt>");
                                            city_data = item[i].substring(city_Start, city_End);
                                            city_data_string = city_data.replace("<overFlowCnt>", "일일 해외 유입자 수 = ");
                                            Chungcheongnam = Chungcheongnam + "\n" + city_data_string;

                                            city_Start = item[i].indexOf("<qurRate>");
                                            city_End = item[i].indexOf("</qurRate>");
                                            city_data = item[i].substring(city_Start, city_End);
                                            city_data_string = city_data.replace("<qurRate>", "10만명 당 확진자 수 = ");
                                            Chungcheongnam = Chungcheongnam + "\n" + city_data_string;

                                            city_Start = item[i].indexOf("<defCnt>");
                                            city_End = item[i].indexOf("</defCnt>");
                                            city_data = item[i].substring(city_Start, city_End);
                                            city_data_string = city_data.replace("<defCnt>", "누적 확진자 수 = ");
                                            Chungcheongnam = Chungcheongnam + "\n" + city_data_string;

                                            city_Start = item[i].indexOf("<isolClearCnt>");
                                            city_End = item[i].indexOf("</isolClearCnt>");
                                            city_data = item[i].substring(city_Start, city_End);
                                            city_data_string = city_data.replace("<isolClearCnt>", "누적 완치자 수 = ");
                                            Chungcheongnam = Chungcheongnam + "\n" + city_data_string;

                                            city_Start = item[i].indexOf("<deathCnt>");
                                            city_End = item[i].indexOf("</deathCnt>");
                                            city_data = item[i].substring(city_Start, city_End);
                                            city_data_string = city_data.replace("<deathCnt>", "누적 사망자 수 = ");
                                            Chungcheongnam = Chungcheongnam + "\n" + city_data_string;

                                        } else if (item[i].contains("Chungcheongbuk")) {
                                            city_Start = item[i].indexOf("<incDec>");
                                            city_End = item[i].indexOf("</incDec>");
                                            city_data = item[i].substring(city_Start, city_End);
                                            city_data_string = city_data.replace("<incDec>", "일일 확진자 수 = ");
                                            Chungcheongbuk = city_data_string;

                                            city_Start = item[i].indexOf("<overFlowCnt>");
                                            city_End = item[i].indexOf("</overFlowCnt>");
                                            city_data = item[i].substring(city_Start, city_End);
                                            city_data_string = city_data.replace("<overFlowCnt>", "일일 해외 유입자 수 = ");
                                            Chungcheongbuk = Chungcheongbuk + "\n" + city_data_string;

                                            city_Start = item[i].indexOf("<qurRate>");
                                            city_End = item[i].indexOf("</qurRate>");
                                            city_data = item[i].substring(city_Start, city_End);
                                            city_data_string = city_data.replace("<qurRate>", "10만명 당 확진자 수 = ");
                                            Chungcheongbuk = Chungcheongbuk + "\n" + city_data_string;

                                            city_Start = item[i].indexOf("<defCnt>");
                                            city_End = item[i].indexOf("</defCnt>");
                                            city_data = item[i].substring(city_Start, city_End);
                                            city_data_string = city_data.replace("<defCnt>", "누적 확진자 수 = ");
                                            Chungcheongbuk = Chungcheongbuk + "\n" + city_data_string;

                                            city_Start = item[i].indexOf("<isolClearCnt>");
                                            city_End = item[i].indexOf("</isolClearCnt>");
                                            city_data = item[i].substring(city_Start, city_End);
                                            city_data_string = city_data.replace("<isolClearCnt>", "누적 완치자 수 = ");
                                            Chungcheongbuk = Chungcheongbuk + "\n" + city_data_string;

                                            city_Start = item[i].indexOf("<deathCnt>");
                                            city_End = item[i].indexOf("</deathCnt>");
                                            city_data = item[i].substring(city_Start, city_End);
                                            city_data_string = city_data.replace("<deathCnt>", "누적 사망자 수 = ");
                                            Chungcheongbuk = Chungcheongbuk + "\n" + city_data_string;

                                        } else if (item[i].contains("Gangwon")) {
                                            city_Start = item[i].indexOf("<incDec>");
                                            city_End = item[i].indexOf("</incDec>");
                                            city_data = item[i].substring(city_Start, city_End);
                                            city_data_string = city_data.replace("<incDec>", "일일 확진자 수 = ");
                                            Gangwon = city_data_string;

                                            city_Start = item[i].indexOf("<overFlowCnt>");
                                            city_End = item[i].indexOf("</overFlowCnt>");
                                            city_data = item[i].substring(city_Start, city_End);
                                            city_data_string = city_data.replace("<overFlowCnt>", "일일 해외 유입자 수 = ");
                                            Gangwon = Gangwon + "\n" + city_data_string;

                                            city_Start = item[i].indexOf("<qurRate>");
                                            city_End = item[i].indexOf("</qurRate>");
                                            city_data = item[i].substring(city_Start, city_End);
                                            city_data_string = city_data.replace("<qurRate>", "10만명 당 확진자 수 = ");
                                            Gangwon = Gangwon + "\n" + city_data_string;

                                            city_Start = item[i].indexOf("<defCnt>");
                                            city_End = item[i].indexOf("</defCnt>");
                                            city_data = item[i].substring(city_Start, city_End);
                                            city_data_string = city_data.replace("<defCnt>", "누적 확진자 수 = ");
                                            Gangwon = Gangwon + "\n" + city_data_string;

                                            city_Start = item[i].indexOf("<isolClearCnt>");
                                            city_End = item[i].indexOf("</isolClearCnt>");
                                            city_data = item[i].substring(city_Start, city_End);
                                            city_data_string = city_data.replace("<isolClearCnt>", "누적 완치자 수 = ");
                                            Gangwon = Gangwon + "\n" + city_data_string;

                                            city_Start = item[i].indexOf("<deathCnt>");
                                            city_End = item[i].indexOf("</deathCnt>");
                                            city_data = item[i].substring(city_Start, city_End);
                                            city_data_string = city_data.replace("<deathCnt>", "누적 사망자 수 = ");
                                            Gangwon = Gangwon + "\n" + city_data_string;

                                        } else if (item[i].contains("Ulsan")) {
                                            city_Start = item[i].indexOf("<incDec>");
                                            city_End = item[i].indexOf("</incDec>");
                                            city_data = item[i].substring(city_Start, city_End);
                                            city_data_string = city_data.replace("<incDec>", "일일 확진자 수 = ");
                                            Ulsan = city_data_string;

                                            city_Start = item[i].indexOf("<overFlowCnt>");
                                            city_End = item[i].indexOf("</overFlowCnt>");
                                            city_data = item[i].substring(city_Start, city_End);
                                            city_data_string = city_data.replace("<overFlowCnt>", "일일 해외 유입자 수 = ");
                                            Ulsan = Ulsan + "\n" + city_data_string;

                                            city_Start = item[i].indexOf("<qurRate>");
                                            city_End = item[i].indexOf("</qurRate>");
                                            city_data = item[i].substring(city_Start, city_End);
                                            city_data_string = city_data.replace("<qurRate>", "10만명 당 확진자 수 = ");
                                            Ulsan = Ulsan + "\n" + city_data_string;

                                            city_Start = item[i].indexOf("<defCnt>");
                                            city_End = item[i].indexOf("</defCnt>");
                                            city_data = item[i].substring(city_Start, city_End);
                                            city_data_string = city_data.replace("<defCnt>", "누적 확진자 수 = ");
                                            Ulsan = Ulsan + "\n" + city_data_string;

                                            city_Start = item[i].indexOf("<isolClearCnt>");
                                            city_End = item[i].indexOf("</isolClearCnt>");
                                            city_data = item[i].substring(city_Start, city_End);
                                            city_data_string = city_data.replace("<isolClearCnt>", "누적 완치자 수 = ");
                                            Ulsan = Ulsan + "\n" + city_data_string;

                                            city_Start = item[i].indexOf("<deathCnt>");
                                            city_End = item[i].indexOf("</deathCnt>");
                                            city_data = item[i].substring(city_Start, city_End);
                                            city_data_string = city_data.replace("<deathCnt>", "누적 사망자 수 = ");
                                            Ulsan = Ulsan + "\n" + city_data_string;

                                        } else if (item[i].contains("Daejeon")) {
                                            city_Start = item[i].indexOf("<incDec>");
                                            city_End = item[i].indexOf("</incDec>");
                                            city_data = item[i].substring(city_Start, city_End);
                                            city_data_string = city_data.replace("<incDec>", "일일 확진자 수 = ");
                                            Daejeon = city_data_string;

                                            city_Start = item[i].indexOf("<overFlowCnt>");
                                            city_End = item[i].indexOf("</overFlowCnt>");
                                            city_data = item[i].substring(city_Start, city_End);
                                            city_data_string = city_data.replace("<overFlowCnt>", "일일 해외 유입자 수 = ");
                                            Daejeon = Daejeon + "\n" + city_data_string;

                                            city_Start = item[i].indexOf("<qurRate>");
                                            city_End = item[i].indexOf("</qurRate>");
                                            city_data = item[i].substring(city_Start, city_End);
                                            city_data_string = city_data.replace("<qurRate>", "10만명 당 확진자 수 = ");
                                            Daejeon = Daejeon + "\n" + city_data_string;

                                            city_Start = item[i].indexOf("<defCnt>");
                                            city_End = item[i].indexOf("</defCnt>");
                                            city_data = item[i].substring(city_Start, city_End);
                                            city_data_string = city_data.replace("<defCnt>", "누적 확진자 수 = ");
                                            Daejeon = Daejeon + "\n" + city_data_string;

                                            city_Start = item[i].indexOf("<isolClearCnt>");
                                            city_End = item[i].indexOf("</isolClearCnt>");
                                            city_data = item[i].substring(city_Start, city_End);
                                            city_data_string = city_data.replace("<isolClearCnt>", "누적 완치자 수 = ");
                                            Daejeon = Daejeon + "\n" + city_data_string;

                                            city_Start = item[i].indexOf("<deathCnt>");
                                            city_End = item[i].indexOf("</deathCnt>");
                                            city_data = item[i].substring(city_Start, city_End);
                                            city_data_string = city_data.replace("<deathCnt>", "누적 사망자 수 = ");
                                            Daejeon = Daejeon + "\n" + city_data_string;

                                        } else if (item[i].contains("Gwangju")) {
                                            city_Start = item[i].indexOf("<incDec>");
                                            city_End = item[i].indexOf("</incDec>");
                                            city_data = item[i].substring(city_Start, city_End);
                                            city_data_string = city_data.replace("<incDec>", "일일 확진자 수 = ");
                                            Gwangju = city_data_string;

                                            city_Start = item[i].indexOf("<overFlowCnt>");
                                            city_End = item[i].indexOf("</overFlowCnt>");
                                            city_data = item[i].substring(city_Start, city_End);
                                            city_data_string = city_data.replace("<overFlowCnt>", "일일 해외 유입자 수 = ");
                                            Gwangju = Gwangju + "\n" + city_data_string;

                                            city_Start = item[i].indexOf("<qurRate>");
                                            city_End = item[i].indexOf("</qurRate>");
                                            city_data = item[i].substring(city_Start, city_End);
                                            city_data_string = city_data.replace("<qurRate>", "10만명 당 확진자 수 = ");
                                            Gwangju = Gwangju + "\n" + city_data_string;

                                            city_Start = item[i].indexOf("<defCnt>");
                                            city_End = item[i].indexOf("</defCnt>");
                                            city_data = item[i].substring(city_Start, city_End);
                                            city_data_string = city_data.replace("<defCnt>", "누적 확진자 수 = ");
                                            Gwangju = Gwangju + "\n" + city_data_string;

                                            city_Start = item[i].indexOf("<isolClearCnt>");
                                            city_End = item[i].indexOf("</isolClearCnt>");
                                            city_data = item[i].substring(city_Start, city_End);
                                            city_data_string = city_data.replace("<isolClearCnt>", "누적 완치자 수 = ");
                                            Gwangju = Gwangju + "\n" + city_data_string;

                                            city_Start = item[i].indexOf("<deathCnt>");
                                            city_End = item[i].indexOf("</deathCnt>");
                                            city_data = item[i].substring(city_Start, city_End);
                                            city_data_string = city_data.replace("<deathCnt>", "누적 사망자 수 = ");
                                            Gwangju = Gwangju + "\n" + city_data_string;

                                        } else if (item[i].contains("Incheon")) {
                                            city_Start = item[i].indexOf("<incDec>");
                                            city_End = item[i].indexOf("</incDec>");
                                            city_data = item[i].substring(city_Start, city_End);
                                            city_data_string = city_data.replace("<incDec>", "일일 확진자 수 = ");
                                            Incheon = city_data_string;

                                            city_Start = item[i].indexOf("<overFlowCnt>");
                                            city_End = item[i].indexOf("</overFlowCnt>");
                                            city_data = item[i].substring(city_Start, city_End);
                                            city_data_string = city_data.replace("<overFlowCnt>", "일일 해외 유입자 수 = ");
                                            Incheon = Incheon + "\n" + city_data_string;

                                            city_Start = item[i].indexOf("<qurRate>");
                                            city_End = item[i].indexOf("</qurRate>");
                                            city_data = item[i].substring(city_Start, city_End);
                                            city_data_string = city_data.replace("<qurRate>", "10만명 당 확진자 수 = ");
                                            Incheon = Incheon + "\n" + city_data_string;

                                            city_Start = item[i].indexOf("<defCnt>");
                                            city_End = item[i].indexOf("</defCnt>");
                                            city_data = item[i].substring(city_Start, city_End);
                                            city_data_string = city_data.replace("<defCnt>", "누적 확진자 수 = ");
                                            Incheon = Incheon + "\n" + city_data_string;

                                            city_Start = item[i].indexOf("<isolClearCnt>");
                                            city_End = item[i].indexOf("</isolClearCnt>");
                                            city_data = item[i].substring(city_Start, city_End);
                                            city_data_string = city_data.replace("<isolClearCnt>", "누적 완치자 수 = ");
                                            Incheon = Incheon + "\n" + city_data_string;

                                            city_Start = item[i].indexOf("<deathCnt>");
                                            city_End = item[i].indexOf("</deathCnt>");
                                            city_data = item[i].substring(city_Start, city_End);
                                            city_data_string = city_data.replace("<deathCnt>", "누적 사망자 수 = ");
                                            Incheon = Incheon + "\n" + city_data_string;

                                        } else if (item[i].contains("Daegu")) {
                                            city_Start = item[i].indexOf("<incDec>");
                                            city_End = item[i].indexOf("</incDec>");
                                            city_data = item[i].substring(city_Start, city_End);
                                            city_data_string = city_data.replace("<incDec>", "일일 확진자 수 = ");
                                            Daegu = city_data_string;

                                            city_Start = item[i].indexOf("<overFlowCnt>");
                                            city_End = item[i].indexOf("</overFlowCnt>");
                                            city_data = item[i].substring(city_Start, city_End);
                                            city_data_string = city_data.replace("<overFlowCnt>", "일일 해외 유입자 수 = ");
                                            Daegu = Daegu + "\n" + city_data_string;

                                            city_Start = item[i].indexOf("<qurRate>");
                                            city_End = item[i].indexOf("</qurRate>");
                                            city_data = item[i].substring(city_Start, city_End);
                                            city_data_string = city_data.replace("<qurRate>", "10만명 당 확진자 수 = ");
                                            Daegu = Daegu + "\n" + city_data_string;

                                            city_Start = item[i].indexOf("<defCnt>");
                                            city_End = item[i].indexOf("</defCnt>");
                                            city_data = item[i].substring(city_Start, city_End);
                                            city_data_string = city_data.replace("<defCnt>", "누적 확진자 수 = ");
                                            Daegu = Daegu + "\n" + city_data_string;

                                            city_Start = item[i].indexOf("<isolClearCnt>");
                                            city_End = item[i].indexOf("</isolClearCnt>");
                                            city_data = item[i].substring(city_Start, city_End);
                                            city_data_string = city_data.replace("<isolClearCnt>", "누적 완치자 수 = ");
                                            Daegu = Daegu + "\n" + city_data_string;

                                            city_Start = item[i].indexOf("<deathCnt>");
                                            city_End = item[i].indexOf("</deathCnt>");
                                            city_data = item[i].substring(city_Start, city_End);
                                            city_data_string = city_data.replace("<deathCnt>", "누적 사망자 수 = ");
                                            Daegu = Daegu + "\n" + city_data_string;

                                        } else if (item[i].contains("Busan")) {
                                            city_Start = item[i].indexOf("<incDec>");
                                            city_End = item[i].indexOf("</incDec>");
                                            city_data = item[i].substring(city_Start, city_End);
                                            city_data_string = city_data.replace("<incDec>", "일일 확진자 수 = ");
                                            Busan = city_data_string;

                                            city_Start = item[i].indexOf("<overFlowCnt>");
                                            city_End = item[i].indexOf("</overFlowCnt>");
                                            city_data = item[i].substring(city_Start, city_End);
                                            city_data_string = city_data.replace("<overFlowCnt>", "일일 해외 유입자 수 = ");
                                            Busan = Busan + "\n" + city_data_string;

                                            city_Start = item[i].indexOf("<qurRate>");
                                            city_End = item[i].indexOf("</qurRate>");
                                            city_data = item[i].substring(city_Start, city_End);
                                            city_data_string = city_data.replace("<qurRate>", "10만명 당 확진자 수 = ");
                                            Busan = Busan + "\n" + city_data_string;

                                            city_Start = item[i].indexOf("<defCnt>");
                                            city_End = item[i].indexOf("</defCnt>");
                                            city_data = item[i].substring(city_Start, city_End);
                                            city_data_string = city_data.replace("<defCnt>", "누적 확진자 수 = ");
                                            Busan = Busan + "\n" + city_data_string;

                                            city_Start = item[i].indexOf("<isolClearCnt>");
                                            city_End = item[i].indexOf("</isolClearCnt>");
                                            city_data = item[i].substring(city_Start, city_End);
                                            city_data_string = city_data.replace("<isolClearCnt>", "누적 완치자 수 = ");
                                            Busan = Busan + "\n" + city_data_string;

                                            city_Start = item[i].indexOf("<deathCnt>");
                                            city_End = item[i].indexOf("</deathCnt>");
                                            city_data = item[i].substring(city_Start, city_End);
                                            city_data_string = city_data.replace("<deathCnt>", "누적 사망자 수 = ");
                                            Busan = Busan + "\n" + city_data_string;

                                        } else if (item[i].contains("Seoul")) {
                                            city_Start = item[i].indexOf("<incDec>");
                                            city_End = item[i].indexOf("</incDec>");
                                            city_data = item[i].substring(city_Start, city_End);
                                            city_data_string = city_data.replace("<incDec>", "일일 확진자 수 = ");
                                            Seoul = city_data_string;

                                            city_Start = item[i].indexOf("<overFlowCnt>");
                                            city_End = item[i].indexOf("</overFlowCnt>");
                                            city_data = item[i].substring(city_Start, city_End);
                                            city_data_string = city_data.replace("<overFlowCnt>", "일일 해외 유입자 수 = ");
                                            Seoul = Seoul + "\n" + city_data_string;

                                            city_Start = item[i].indexOf("<qurRate>");
                                            city_End = item[i].indexOf("</qurRate>");
                                            city_data = item[i].substring(city_Start, city_End);
                                            city_data_string = city_data.replace("<qurRate>", "10만명 당 확진자 수 = ");
                                            Seoul = Seoul + "\n" + city_data_string;

                                            city_Start = item[i].indexOf("<defCnt>");
                                            city_End = item[i].indexOf("</defCnt>");
                                            city_data = item[i].substring(city_Start, city_End);
                                            city_data_string = city_data.replace("<defCnt>", "누적 확진자 수 = ");
                                            Seoul = Seoul + "\n" + city_data_string;

                                            city_Start = item[i].indexOf("<isolClearCnt>");
                                            city_End = item[i].indexOf("</isolClearCnt>");
                                            city_data = item[i].substring(city_Start, city_End);
                                            city_data_string = city_data.replace("<isolClearCnt>", "누적 완치자 수 = ");
                                            Seoul = Seoul + "\n" + city_data_string;

                                            city_Start = item[i].indexOf("<deathCnt>");
                                            city_End = item[i].indexOf("</deathCnt>");
                                            city_data = item[i].substring(city_Start, city_End);
                                            city_data_string = city_data.replace("<deathCnt>", "누적 사망자 수 = ");
                                            Seoul = Seoul + "\n" + city_data_string;

                                        } else if (item[i].contains("Total")) {

                                            city_Start = item[i].indexOf("<incDec>");
                                            city_End = item[i].indexOf("</incDec>");
                                            city_data = item[i].substring(city_Start, city_End);
                                            city_data_string = city_data.replace("<incDec>", "일일 확진자 수 = ");
                                            Total = city_data_string;

                                            city_Start = item[i].indexOf("<overFlowCnt>");
                                            city_End = item[i].indexOf("</overFlowCnt>");
                                            city_data = item[i].substring(city_Start, city_End);
                                            city_data_string = city_data.replace("<overFlowCnt>", "일일 해외 유입자 수 = ");
                                            Total = Total + "\n" + city_data_string;

                                            city_Start = item[i].indexOf("<qurRate>");
                                            city_End = item[i].indexOf("</qurRate>");
                                            city_data = item[i].substring(city_Start, city_End);
                                            city_data_string = city_data.replace("<qurRate>", "10만명 당 확진자 수 = ");
                                            Total = Total + "\n" + city_data_string;

                                            city_Start = item[i].indexOf("<defCnt>");
                                            city_End = item[i].indexOf("</defCnt>");
                                            city_data = item[i].substring(city_Start, city_End);
                                            city_data_string = city_data.replace("<defCnt>", "누적 확진자 수 = ");
                                            Total = Total + "\n" + city_data_string;

                                            city_Start = item[i].indexOf("<isolClearCnt>");
                                            city_End = item[i].indexOf("</isolClearCnt>");
                                            city_data = item[i].substring(city_Start, city_End);
                                            city_data_string = city_data.replace("<isolClearCnt>", "누적 완치자 수 = ");
                                            Total = Total + "\n" + city_data_string;

                                            try {
                                                city_data_string = city_data.replace("<isolClearCnt>", "");
                                                accumulated_isolation_today = Integer.parseInt(city_data_string);
                                                int accumulated_isolation_before = city_api1.indexOf("Total");
                                                StringBuffer sb1 = new StringBuffer();
                                                sb1.append(city_api1);
                                                sb1.delete(0,accumulated_isolation_before);
                                                city_Start = sb1.indexOf("<isolClearCnt>");
                                                city_End = sb1.indexOf("</isolClearCnt>");
                                                city_data = sb1.substring(city_Start, city_End);
                                                city_data_string = city_data.replace("<isolClearCnt>", "");
                                                int accumulated_isolation = Integer.parseInt(city_data_string);

                                                daily_accumulated_isolation = accumulated_isolation_today - accumulated_isolation;

                                                daily_accumulated_isolation_string = Integer.toString(daily_accumulated_isolation);
                                                accumulated_isolation_string = Integer.toString(accumulated_isolation_today);

                                            } catch (Exception e) {
                                                e.printStackTrace();
                                            }

                                            city_Start = item[i].indexOf("<deathCnt>");
                                            city_End = item[i].indexOf("</deathCnt>");
                                            city_data = item[i].substring(city_Start, city_End);
                                            city_data_string = city_data.replace("<deathCnt>", "누적 사망자 수 = ");
                                            Total = Total + "\n" + city_data_string;

                                        }
                                    }
                                } catch (Exception e) {
                                    e.printStackTrace();
                                }

                            }
                        }.start();
                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                } else {
                    Toast.makeText(MainActivity.this, "아이디 혹은 비밀번호를 입력해주세요", Toast.LENGTH_SHORT).show();
                }

            }
        });


        Button loginBt2 = (Button) findViewById(R.id.registerButton);
        loginBt2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(MainActivity.this, register.class);
                startActivity(intent);
            }
        });

        Button loginBt3 = (Button) findViewById(R.id.find_Id_PassButton);
        loginBt3.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(getApplicationContext(), Search_ID_PW.class);
                startActivity(intent);
            }
        });
    }
    private String getTime() {
        mNow = System.currentTimeMillis();
        mDate = new Date(mNow);
        return mFormat.format(mDate);
    }

    private String getHTML() {
        String coronaHtml = "";
        HttpURLConnection con = null;
        InputStreamReader isr = null;
        BufferedReader br = null;

        try{
            StringBuilder urlBuilder = new StringBuilder("http://openapi.data.go.kr/openapi/service/rest/Covid19/getCovid19InfStateJson"); /*URL*/
            urlBuilder.append("?" + URLEncoder.encode("serviceKey","UTF-8") + "=BGYZ3GEcNWvV9fWuAwFKo9dMtPwYVng3hWqlYLq9dHcHp5DGZAXeoBQJhD5nRR%2B0XZW8EqPjwkxzm5ph6ZIwqA%3D%3D"); /*Service Key*/
            urlBuilder.append("&" + URLEncoder.encode("ServiceKey","UTF-8") + "=" + URLEncoder.encode("BGYZ3GEcNWvV9fWuAwFKo9dMtPwYVng3hWqlYLq9dHcHp5DGZAXeoBQJhD5nRR+0XZW8EqPjwkxzm5ph6ZIwqA==", "UTF-8")); /*공공데이터포털에서 받은 인증키*/
            urlBuilder.append("&" + URLEncoder.encode("pageNo","UTF-8") + "=" + URLEncoder.encode("1", "UTF-8")); /*페이지번호*/
            urlBuilder.append("&" + URLEncoder.encode("numOfRows","UTF-8") + "=" + URLEncoder.encode("10", "UTF-8")); /*한 페이지 결과 수*/
            urlBuilder.append("&" + URLEncoder.encode("startCreateDt","UTF-8") + "=" + URLEncoder.encode(getTime(), "UTF-8")); /*검색할 생성일 범위의 시작*/
            urlBuilder.append("&" + URLEncoder.encode("endCreateDt","UTF-8") + "=" + URLEncoder.encode(getTime(), "UTF-8")); /*검색할 생성일 범위의 종료*/
            URL url = new URL(urlBuilder.toString());
            con = (HttpURLConnection) url.openConnection();
            con.setConnectTimeout(CONN_TIME);
            con.setReadTimeout(CONN_TIME);

            isr = new InputStreamReader(con.getInputStream());
            br = new BufferedReader(isr);

            String str = null;
            while ((str = br.readLine()) != null) {
                coronaHtml += str + "\n";
            }

        }catch(Exception e){
            e.printStackTrace();
        }finally{
            if(con != null){
                try{con.disconnect();}catch(Exception e){}
            }

            if(isr != null){
                try{isr.close();}catch(Exception e){}
            }

            if(br != null){
                try{br.close();}catch(Exception e){}
            }
        }
        return coronaHtml;
    }

    private String getHTML1() {
        String coronaHtml = "";
        HttpURLConnection con = null;
        InputStreamReader isr = null;
        BufferedReader br = null;
        int time=Integer.parseInt(getTime());
        String time1=Integer.toString(time-1);
        try{
            StringBuilder urlBuilder = new StringBuilder("http://openapi.data.go.kr/openapi/service/rest/Covid19/getCovid19InfStateJson"); /*URL*/
            urlBuilder.append("?" + URLEncoder.encode("serviceKey","UTF-8") + "=BGYZ3GEcNWvV9fWuAwFKo9dMtPwYVng3hWqlYLq9dHcHp5DGZAXeoBQJhD5nRR%2B0XZW8EqPjwkxzm5ph6ZIwqA%3D%3D"); /*Service Key*/
            urlBuilder.append("&" + URLEncoder.encode("ServiceKey","UTF-8") + "=" + URLEncoder.encode("BGYZ3GEcNWvV9fWuAwFKo9dMtPwYVng3hWqlYLq9dHcHp5DGZAXeoBQJhD5nRR+0XZW8EqPjwkxzm5ph6ZIwqA==", "UTF-8")); /*공공데이터포털에서 받은 인증키*/
            urlBuilder.append("&" + URLEncoder.encode("pageNo","UTF-8") + "=" + URLEncoder.encode("1", "UTF-8")); /*페이지번호*/
            urlBuilder.append("&" + URLEncoder.encode("numOfRows","UTF-8") + "=" + URLEncoder.encode("10", "UTF-8")); /*한 페이지 결과 수*/
            urlBuilder.append("&" + URLEncoder.encode("startCreateDt","UTF-8") + "=" + URLEncoder.encode(time1, "UTF-8")); /*검색할 생성일 범위의 시작*/
            urlBuilder.append("&" + URLEncoder.encode("endCreateDt","UTF-8") + "=" + URLEncoder.encode(time1, "UTF-8")); /*검색할 생성일 범위의 종료*/
            URL url = new URL(urlBuilder.toString());
            con = (HttpURLConnection) url.openConnection();
            con.setConnectTimeout(CONN_TIME);
            con.setReadTimeout(CONN_TIME);

            isr = new InputStreamReader(con.getInputStream());
            br = new BufferedReader(isr);

            String str = null;
            while ((str = br.readLine()) != null) {
                coronaHtml += str + "\n";
            }

        }catch(Exception e){
            e.printStackTrace();
        }finally{
            if(con != null){
                try{con.disconnect();}catch(Exception e){}
            }

            if(isr != null){
                try{isr.close();}catch(Exception e){}
            }

            if(br != null){
                try{br.close();}catch(Exception e){}
            }
        }
        return coronaHtml;
    }

    private String City_api() {
        String coronaHtml = "";
        HttpURLConnection con = null;
        InputStreamReader isr = null;
        BufferedReader br = null;
        int time=Integer.parseInt(getTime());
        try{
            StringBuilder urlBuilder = new StringBuilder("http://openapi.data.go.kr/openapi/service/rest/Covid19/getCovid19SidoInfStateJson"); /*URL*/
            urlBuilder.append("?" + URLEncoder.encode("serviceKey","UTF-8") + "=BGYZ3GEcNWvV9fWuAwFKo9dMtPwYVng3hWqlYLq9dHcHp5DGZAXeoBQJhD5nRR%2B0XZW8EqPjwkxzm5ph6ZIwqA%3D%3D"); /*Service Key*/
            urlBuilder.append("&" + URLEncoder.encode("pageNo","UTF-8") + "=" + URLEncoder.encode("1", "UTF-8")); /*페이지번호*/
            urlBuilder.append("&" + URLEncoder.encode("numOfRows","UTF-8") + "=" + URLEncoder.encode("10", "UTF-8")); /*한 페이지 결과 수*/
            urlBuilder.append("&" + URLEncoder.encode("startCreateDt","UTF-8") + "=" + URLEncoder.encode(getTime(), "UTF-8")); /*검색할 생성일 범위의 시작*/
            urlBuilder.append("&" + URLEncoder.encode("endCreateDt","UTF-8") + "=" + URLEncoder.encode(getTime(), "UTF-8")); /*검색할 생성일 범위의 종료*/
            URL url = new URL(urlBuilder.toString());
            con = (HttpURLConnection) url.openConnection();
            con.setConnectTimeout(CONN_TIME);
            con.setReadTimeout(CONN_TIME);

            isr = new InputStreamReader(con.getInputStream());
            br = new BufferedReader(isr);

            String str = null;
            while ((str = br.readLine()) != null) {
                coronaHtml += str + "\n";
            }

        }catch(Exception e){
            e.printStackTrace();
        }finally{
            if(con != null){
                try{con.disconnect();}catch(Exception e){}
            }

            if(isr != null){
                try{isr.close();}catch(Exception e){}
            }

            if(br != null){
                try{br.close();}catch(Exception e){}
            }
        }
        return coronaHtml;
    }

    private String City_api1() {
        String coronaHtml = "";
        HttpURLConnection con = null;
        InputStreamReader isr = null;
        BufferedReader br = null;
        int time=Integer.parseInt(getTime());
        String time1=Integer.toString(time-1);
        try{
            StringBuilder urlBuilder = new StringBuilder("http://openapi.data.go.kr/openapi/service/rest/Covid19/getCovid19SidoInfStateJson"); /*URL*/
            urlBuilder.append("?" + URLEncoder.encode("serviceKey","UTF-8") + "=BGYZ3GEcNWvV9fWuAwFKo9dMtPwYVng3hWqlYLq9dHcHp5DGZAXeoBQJhD5nRR%2B0XZW8EqPjwkxzm5ph6ZIwqA%3D%3D"); /*Service Key*/
            urlBuilder.append("&" + URLEncoder.encode("pageNo","UTF-8") + "=" + URLEncoder.encode("1", "UTF-8")); /*페이지번호*/
            urlBuilder.append("&" + URLEncoder.encode("numOfRows","UTF-8") + "=" + URLEncoder.encode("10", "UTF-8")); /*한 페이지 결과 수*/
            urlBuilder.append("&" + URLEncoder.encode("startCreateDt","UTF-8") + "=" + URLEncoder.encode(time1, "UTF-8")); /*검색할 생성일 범위의 시작*/
            urlBuilder.append("&" + URLEncoder.encode("endCreateDt","UTF-8") + "=" + URLEncoder.encode(time1, "UTF-8")); /*검색할 생성일 범위의 종료*/
            URL url = new URL(urlBuilder.toString());
            con = (HttpURLConnection) url.openConnection();
            con.setConnectTimeout(CONN_TIME);
            con.setReadTimeout(CONN_TIME);

            isr = new InputStreamReader(con.getInputStream());
            br = new BufferedReader(isr);

            String str = null;
            while ((str = br.readLine()) != null) {
                coronaHtml += str + "\n";
            }

        }catch(Exception e){
            e.printStackTrace();
        }finally{
            if(con != null){
                try{con.disconnect();}catch(Exception e){}
            }

            if(isr != null){
                try{isr.close();}catch(Exception e){}
            }

            if(br != null){
                try{br.close();}catch(Exception e){}
            }
        }
        return coronaHtml;
    }

    private String Vaccine_api() {
        String coronaHtml = "";
        HttpURLConnection con = null;
        InputStreamReader isr = null;
        BufferedReader br = null;

        try{
            StringBuilder urlBuilder = new StringBuilder("https://nip.kdca.go.kr/irgd/cov19stats.do?list=all");
            URL url = new URL(urlBuilder.toString());
            con = (HttpURLConnection) url.openConnection();
            con.setConnectTimeout(CONN_TIME);
            con.setReadTimeout(CONN_TIME);

            isr = new InputStreamReader(con.getInputStream());
            br = new BufferedReader(isr);

            String str = null;
            while ((str = br.readLine()) != null) {
                coronaHtml += str + "\n";
            }

        }catch(Exception e){
            e.printStackTrace();
        }finally{
            if(con != null){
                try{con.disconnect();}catch(Exception e){}
            }

            if(isr != null){
                try{isr.close();}catch(Exception e){}
            }

            if(br != null){
                try{br.close();}catch(Exception e){}
            }
        }
        return coronaHtml;
    }

    /*getter ------ setter*/
    public String getDateText() {
        return dateText;
    }

    public void setDateText(String dateText) {
        this.dateText = dateText;
    }

    public String getDaily_confirmed_API() {
        return daily_confirmed_API;
    }

    public void setDaily_confirmed_API(String daily_confirmed_API) {
        this.daily_confirmed_API = daily_confirmed_API;
    }

    public String getAccumulated_confirmed_API() {
        return Accumulated_confirmed_API;
    }

    public void setAccumulated_confirmed_API(String accumulated_confirmed_API) {
        Accumulated_confirmed_API = accumulated_confirmed_API;
    }

    public String getDaily_dead_API() {
        return daily_dead_API;
    }

    public void setDaily_dead_API(String daily_dead_API) {
        this.daily_dead_API = daily_dead_API;
    }

    public String getAccumulated_dead_API() {
        return Accumulated_dead_API;
    }

    public void setAccumulated_dead_API(String accumulated_dead_API) {
        Accumulated_dead_API = accumulated_dead_API;
    }

    public String getDaily_exam_API() {
        return daily_exam_API;
    }

    public void setDaily_exam_API(String daily_exam_API) {
        this.daily_exam_API = daily_exam_API;
    }

    public String getAccumulated_exam_API() {
        return Accumulated_exam_API;
    }

    public void setAccumulated_exam_API(String accumulated_exam_API) {
        Accumulated_exam_API = accumulated_exam_API;
    }

    public String getDaily_first_complete_API() {
        return daily_first_complete_API;
    }

    public void setDaily_first_complete_API(String daily_first_complete_API) {
        this.daily_first_complete_API = daily_first_complete_API;
    }

    public String getAccumulated_first_complete_API() {
        return Accumulated_first_complete_API;
    }

    public void setAccumulated_first_complete_API(String accumulated_first_complete_API) {
        Accumulated_first_complete_API = accumulated_first_complete_API;
    }

    public String getDaily_second_complete_API() {
        return daily_second_complete_API;
    }

    public void setDaily_second_complete_API(String daily_second_complete_API) {
        this.daily_second_complete_API = daily_second_complete_API;
    }

    public String getAccumulated_second_complete_API() {
        return Accumulated_second_complete_API;
    }

    public void setAccumulated_second_complete_API(String accumulated_second_complete_API) {
        Accumulated_second_complete_API = accumulated_second_complete_API;
    }

    public String getDaily_third_complete_API() {
        return daily_third_complete_API;
    }

    public void setDaily_third_complete_API(String daily_third_complete_API) {
        this.daily_third_complete_API = daily_third_complete_API;
    }

    public String getAccumulated_third_complete_API() {
        return Accumulated_third_complete_API;
    }

    public void setAccumulated_third_complete_API(String accumulated_third_complete_API) {
        Accumulated_third_complete_API = accumulated_third_complete_API;
    }

    public String getCity_API() {
        return City_API;
    }

    public void setCity_API(String city_API) {
        City_API = city_API;
    }

    public String getGangwon() {
        return Gangwon;
    }

    public void setGangwon(String gangwon) {
        Gangwon = gangwon;
    }

    public String getChungcheongbuk() {
        return Chungcheongbuk;
    }

    public void setChungcheongbuk(String chungcheongbuk) {
        Chungcheongbuk = chungcheongbuk;
    }

    public String getDaejeon() {
        return Daejeon;
    }

    public void setDaejeon(String daejeon) {
        Daejeon = daejeon;
    }

    public String getDaegu() {
        return Daegu;
    }

    public void setDaegu(String daegu) {
        Daegu = daegu;
    }

    public String getGyeongsangbuk() {
        return Gyeongsangbuk;
    }

    public void setGyeongsangbuk(String gyeongsangbuk) {
        Gyeongsangbuk = gyeongsangbuk;
    }

    public String getUlsan() {
        return Ulsan;
    }

    public void setUlsan(String ulsan) {
        Ulsan = ulsan;
    }

    public String getGyeongsangnam() {
        return Gyeongsangnam;
    }

    public void setGyeongsangnam(String gyeongsangnam) {
        Gyeongsangnam = gyeongsangnam;
    }

    public String getGyeonggi() {
        return Gyeonggi;
    }

    public void setGyeonggi(String gyeonggi) {
        Gyeonggi = gyeonggi;
    }

    public String getJeju() {
        return Jeju;
    }

    public void setJeju(String jeju) {
        Jeju = jeju;
    }

    public String getSeoul() {
        return Seoul;
    }

    public void setSeoul(String seoul) {
        Seoul = seoul;
    }

    public String getBusan() {
        return Busan;
    }

    public void setBusan(String busan) {
        Busan = busan;
    }

    public String getGwangju() {
        return Gwangju;
    }

    public void setGwangju(String gwangju) {
        Gwangju = gwangju;
    }

    public String getJeollanam() {
        return Jeollanam;
    }

    public void setJeollanam(String jeollanam) {
        Jeollanam = jeollanam;
    }

    public String getIncheon() {
        return Incheon;
    }

    public void setIncheon(String incheon) {
        Incheon = incheon;
    }

    public String getSejong() {
        return Sejong;
    }

    public void setSejong(String sejong) {
        Sejong = sejong;
    }

    public String getChungcheongnam() {
        return Chungcheongnam;
    }

    public void setChungcheongnam(String chungcheongnam) {
        Chungcheongnam = chungcheongnam;
    }

    public String getJeollabuk() {
        return Jeollabuk;
    }

    public void setJeollabuk(String jeollabuk) {
        Jeollabuk = jeollabuk;
    }

    public String getDaily_accumulated_isolation_string() {
        return daily_accumulated_isolation_string;
    }

    public void setDaily_accumulated_isolation_string(String daily_accumulated_isolation_string) {
        this.daily_accumulated_isolation_string = daily_accumulated_isolation_string;
    }

    public String getAccumulated_isolation_string() {
        return accumulated_isolation_string;
    }

    public void setAccumulated_isolation_string(String accumulated_isolation_string) {
        this.accumulated_isolation_string = accumulated_isolation_string;
    }
    public String getTotal() {
        return Total;
    }

    public void setTotal(String total) {
        Total = total;
    }
}