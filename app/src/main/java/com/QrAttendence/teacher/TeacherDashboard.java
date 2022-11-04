package com.QrAttendence.teacher;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.MenuItem;

import com.QrAttendence.R;
import com.QrAttendence.student.StudentActivity;

import com.google.android.material.bottomnavigation.BottomNavigationView;

public class TeacherDashboard extends AppCompatActivity {
    Intent i;
    String Qid;
    BottomNavigationView bottomNavigationView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_teacher_dashboard);
        i = getIntent();
        Qid = i.getStringExtra("Qid");
        bottomNavigationView = findViewById(R.id.bottom_navigation);

        QRFragment qrFragment = new QRFragment();
        RecordsFragment recordsFragment = new RecordsFragment();
        TeacherProfileFragment teacherProfileFragment = new TeacherProfileFragment();


        bottomNavigationView.setOnNavigationItemSelectedListener(new BottomNavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem item) {
                switch (item.getItemId()) {

                    case R.id.qr:
                        Bundle b = new Bundle();
                        b.putString("Qid", Qid);
                        qrFragment.setArguments(b);
                        TeacherDashboard.this.getSupportFragmentManager().beginTransaction().replace(R.id.container, qrFragment).commit();

                        return true;

                    case R.id.records:
                        TeacherDashboard.this.getSupportFragmentManager().beginTransaction().replace(R.id.container, recordsFragment).commit();
                        return true;

                    case R.id.profile:
                        TeacherDashboard.this.getSupportFragmentManager().beginTransaction().replace(R.id.container, teacherProfileFragment).commit();
                        return true;

                }
                return false;
            }
        });
        bottomNavigationView.setSelectedItemId(R.id.profile);

    }

}