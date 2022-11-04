package com.QrAttendence.teacher;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import android.widget.Toast;

import com.QrAttendence.R;
import com.google.android.material.button.MaterialButton;
import com.google.android.material.textfield.TextInputEditText;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.Collections;
import java.util.HashMap;
import java.util.Map;

public class teacherRegistrationActivity extends AppCompatActivity {
    TextInputEditText name, id, email, clas, phone_no, password;
    MaterialButton submit;
    FirebaseDatabase database;
    DatabaseReference myRef;
    DatabaseReference teachers, teacher;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_teacher_registration);
        name = findViewById(R.id.name);
        id = findViewById(R.id.id);
        email = findViewById(R.id.email);
        clas = findViewById(R.id.clas);
        phone_no = findViewById(R.id.phone_no);
        submit = findViewById(R.id.submitBt);
        password = findViewById(R.id.password);

        submit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String str_name, str_email, str_clas, str_phone_no, str_id, str_pass;

                str_name = name.getText().toString().trim();
                str_id = id.getText().toString().trim();
                str_email = email.getText().toString().trim();
                str_clas = clas.getText().toString().trim();
                str_phone_no = phone_no.getText().toString().trim();
                str_pass = password.getText().toString().trim();

                // storing data in firebase
                database = FirebaseDatabase.getInstance();
                myRef = database.getReference("/QRAttendance");
                teachers = myRef.child("teachers");
                teacherStore(str_name, str_email, str_clas, str_phone_no, str_id, str_pass);


            }
        });

    }

    public void teacherStore(String str_name, String str_email, String str_clas, String str_phone_no, String str_id, String str_pass) {

        Map<String, TeacherModel> teacherModelMap = new HashMap<String, TeacherModel>();
        teacherModelMap.put(str_id, new TeacherModel(str_name, str_email, str_clas, str_phone_no, str_id, str_pass));
        teachers.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                // teachers.setValue(teacherModelMap);
                teachers.updateChildren(Collections.unmodifiableMap(teacherModelMap));
                Toast.makeText(teacherRegistrationActivity.this, "Teacher Registration Successful", Toast.LENGTH_SHORT).show();


                SharedPreferences sharedPreferences = getSharedPreferences("teacherDetails", MODE_PRIVATE);
                SharedPreferences.Editor myEdit = sharedPreferences.edit();
                myEdit.putString("id", str_id);
                myEdit.apply();
                myEdit.commit();


                Intent i = new Intent(teacherRegistrationActivity.this, Teacher_Profile.class);
                startActivity(i);

            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {
                Toast.makeText(teacherRegistrationActivity.this, "Teacher Registration Failed..try again..", Toast.LENGTH_SHORT).show();
            }
        });

    }
}