package com.QrAttendence;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;

import com.example.attendanceqr.R;
import com.example.attendanceqr.student.Student;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
    }

    public void LoginTea(View view) {
        Intent i=new Intent(getApplicationContext(),Admin.class);
        startActivity(i);
    }

    public void LoginStud(View view) {
        Intent intent=new Intent(getApplicationContext(), Student.class);
        startActivity(intent);
    }
}