package com.QrAttendence.student;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.MenuItem;
import android.widget.Toast;

import com.QrAttendence.R;
import com.google.android.material.bottomnavigation.BottomNavigationView;

public class StudentActivity extends AppCompatActivity {
    BottomNavigationView bottomNavigationView;

    // student dashboard activity

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_student_dashboard);

        bottomNavigationView = findViewById(R.id.bottom_navigation);
        StudentDetailsFragment studentDetailsFragment = new StudentDetailsFragment();
        QrScanFragment generateQrFragment = new QrScanFragment();

        bottomNavigationView.setOnNavigationItemSelectedListener(new BottomNavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem item) {
                switch (item.getItemId()) {

                    case R.id.scan:
//
                        StudentActivity.this.getSupportFragmentManager().beginTransaction().replace(R.id.container, generateQrFragment).commit();
                        return true;

                    case R.id.profile:

                          StudentActivity.this.getSupportFragmentManager().beginTransaction().replace(R.id.container, studentDetailsFragment).commit();
                        return true;

                }
                return false;
            }
        });

        bottomNavigationView.setSelectedItemId(R.id.profile);

    }
}