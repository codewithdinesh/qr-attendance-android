package com.QrAttendence;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import androidx.appcompat.app.AppCompatActivity;

import com.example.attendanceqr.teacher.TeacherDashboard;
import com.example.attendanceqr.teacher.teacherRegistrationActivity;

public class Admin extends AppCompatActivity {
    Button login, registration;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_admin);
        registration = findViewById(R.id.registration);
        login = findViewById(R.id.login);

        login.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent(Admin.this, TeacherDashboard.class);
                startActivity(i);
            }
        });

        registration.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent(Admin.this, teacherRegistrationActivity.class);
                startActivity(i);
            }
        });
    }

    public void Tlogin(View view) {


    }
}