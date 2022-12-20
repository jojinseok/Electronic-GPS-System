package com.example.coronavirus19status_app;

import android.app.Activity;
import android.app.AlertDialog;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

public class Search_ID_PW extends Activity {
    FirebaseAuth firebaseAuth;

    private FirebaseDatabase firebaseDatabase = FirebaseDatabase.getInstance();
    private DatabaseReference databaseReference = firebaseDatabase.getReference();

    String getTest = "";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.search_id_pw);
        firebaseAuth = FirebaseAuth.getInstance();

        EditText find_id_name_text = (EditText) findViewById(R.id.find_ID_Name_Text);
        EditText find_pass_name_text = (EditText) findViewById(R.id.find_Pass_Name_Text);
        EditText find_pass_id_text = (EditText) findViewById(R.id.find_Pass_ID_Text);

        Button find_id_button = (Button) findViewById(R.id.find_ID_Button);
        find_id_button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                FirebaseDatabase.getInstance().getReference("UserAccount")
                        .addValueEventListener(new ValueEventListener() {
                    @Override
                    public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                        String find_id_name = find_id_name_text.getText().toString().trim();
                        int count = 0;
                        for (DataSnapshot snapshot : dataSnapshot.getChildren()) {
                            Log.d("1",snapshot.getValue().toString());
                            String parsing = snapshot.getValue().toString();
                            int creatStart;
                            int creatEnd;
                            creatStart = parsing.indexOf("name=");
                            creatEnd = parsing.indexOf(", e");
                            String compare = parsing.substring(creatStart, creatEnd).replace("name=","");
                            if(compare.equals(find_id_name)) {
                                creatStart = parsing.indexOf("emailID=");
                                creatEnd = parsing.indexOf("}");
                                String id = parsing.substring(creatStart, creatEnd).replace("emailID=", "");
                                AlertDialog.Builder builder = new AlertDialog.Builder(Search_ID_PW.this);
                                builder.setTitle("아이디"); //AlertDialog의 제목 부분
                                builder.setMessage(id); //AlertDialog의 내용 부분
                                builder.setPositiveButton("확인",null);
                                builder.create().show(); //보이기
                                count = 1;
                                break;
                            }
                        }
                        if (count == 0){
                            Toast.makeText(Search_ID_PW.this, "일치하는 아이디가 없습니다", Toast.LENGTH_SHORT).show();
                        }
                    }

                    @Override
                    public void onCancelled(@NonNull DatabaseError error) {

                    }
                });
            }
        });

        Button find_pass_button = (Button) findViewById(R.id.find_Pass_Button);
        find_pass_button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                FirebaseDatabase.getInstance().getReference("UserAccount").addValueEventListener(new ValueEventListener() {
                    @Override
                    public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                        String find_pass_name = find_pass_name_text.getText().toString().trim();
                        String find_pass_id = find_pass_id_text.getText().toString().trim();
                        int count = 0;
                        for (DataSnapshot snapshot : dataSnapshot.getChildren()) {
                            Log.d("1", snapshot.getValue().toString());
                            String parsing = snapshot.getValue().toString();
                            int creatStart;
                            int creatEnd;
                            creatStart = parsing.indexOf("name=");
                            creatEnd = parsing.indexOf(", e");
                            String name_compare = parsing.substring(creatStart, creatEnd).replace("name=", "");

                            creatStart = parsing.indexOf("emailID=");
                            creatEnd = parsing.indexOf("}");
                            String id_compare = parsing.substring(creatStart, creatEnd).replace("emailID=", "");

                            if (name_compare.equals(find_pass_name) && id_compare.equals(find_pass_id)) {
                                creatStart = parsing.indexOf("password=");
                                creatEnd = parsing.indexOf(", i");
                                String password = parsing.substring(creatStart, creatEnd).replace("password=", "");
                                AlertDialog.Builder builder = new AlertDialog.Builder(Search_ID_PW.this);
                                builder.setTitle("비밀번호"); //AlertDialog의 제목 부분
                                builder.setMessage(password); //AlertDialog의 내용 부분
                                builder.setPositiveButton("확인",null);
                                builder.create().show(); //보이기
                                count = 1;
                                break;
                            }
                        }
                        if (count == 0){
                            Toast.makeText(Search_ID_PW.this, "이름 혹은 아이디가 잘못되었습니다", Toast.LENGTH_SHORT).show();
                        }
                    }

                    @Override
                    public void onCancelled(@NonNull DatabaseError error) {

                    }
                });
            }
        });

    }
}