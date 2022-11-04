package com.QrAttendence.teacher;

import androidx.annotation.NonNull;
import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AppCompatActivity;

import android.app.DatePickerDialog;
import android.app.TimePickerDialog;
import android.content.Intent;
import android.graphics.Bitmap;
import android.os.Build;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TimePicker;
import android.widget.Toast;

import com.QrAttendence.R;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.google.zxing.BarcodeFormat;
import com.google.zxing.MultiFormatWriter;
import com.google.zxing.WriterException;
import com.google.zxing.common.BitMatrix;
import com.journeyapps.barcodescanner.BarcodeEncoder;


import java.util.Calendar;
import java.util.Collections;
import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

public class Teacher_Profile extends AppCompatActivity {
    EditText date, time, endTime, code, subject;
    Button dateBtn, TimeBtn, endTimeBtn, generateBtn;
    DatePickerDialog dp;
    ImageView qr;
    FirebaseDatabase database;
    DatabaseReference myRef;
    DatabaseReference lectures;

    //Create new Lecture Activity


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_teacher_profile);
        date = findViewById(R.id.date);
        time = findViewById(R.id.time);
        dateBtn = findViewById(R.id.dateBtn);
        TimeBtn = findViewById(R.id.timeBtn);
        endTimeBtn = findViewById(R.id.endTimeBtn);
        endTime = findViewById(R.id.endTime);
        generateBtn = findViewById(R.id.generateqr);
        qr = findViewById(R.id.qr);

        code = findViewById(R.id.code);

        subject = findViewById(R.id.subject);

        dateBtn.setOnClickListener(new View.OnClickListener() {
            @RequiresApi(api = Build.VERSION_CODES.N)
            @Override
            public void onClick(View v) {
                Calendar c = Calendar.getInstance();
                int day = c.get(Calendar.DAY_OF_MONTH);
                int month = c.get(Calendar.MONTH);
                int year = c.get(Calendar.YEAR);
                dp = new DatePickerDialog(Teacher_Profile.this);
                dp.show();
                dp.setOnDateSetListener(new DatePickerDialog.OnDateSetListener() {
                    @Override
                    public void onDateSet(DatePicker view, int year, int month, int dayOfMonth) {
                        int m = month + 1;
                        date.setText(dayOfMonth + "/" + m + "/" + year);
                    }
                });
            }
        });

        TimeBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Calendar d = Calendar.getInstance();
                int hour = d.get(Calendar.HOUR_OF_DAY);
                int minute = d.get(Calendar.MINUTE);
                TimePickerDialog timepicker;

                timepicker = new TimePickerDialog(Teacher_Profile.this, new TimePickerDialog.OnTimeSetListener() {
                    @Override
                    public void onTimeSet(TimePicker view, int hourOfDay, int minute) {
                        time.setText(hourOfDay + ":" + minute);
                    }

                }, hour, minute, true);
                timepicker.show();
            }
        });

        endTimeBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Calendar d = Calendar.getInstance();
                int hour = d.get(Calendar.HOUR_OF_DAY);
                int minute = d.get(Calendar.MINUTE);
                TimePickerDialog timepicker;

                timepicker = new TimePickerDialog(Teacher_Profile.this, new TimePickerDialog.OnTimeSetListener() {
                    @Override
                    public void onTimeSet(TimePicker view, int hourOfDay, int minute) {
                        endTime.setText(hourOfDay + ":" + minute);
                    }

                }, hour, minute, true);
                timepicker.show();
            }
        });

        generateBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String s_subject, s_date, s_time, s_end_time, s_code;


                s_subject = subject.getText().toString();
                s_date = date.getText().toString();
                s_time = time.getText().toString();
                s_end_time = endTime.getText().toString();
                s_code = code.getText().toString();
                UUID uuid = UUID.randomUUID();
                String uID = String.valueOf(uuid);

                // storing data in firebase
                database = FirebaseDatabase.getInstance();
                myRef = database.getReference("/QRAttendance");
                lectures = myRef.child("lectures");

                storeLecture(s_subject, s_date, s_time, s_end_time, s_code, uID);

                // store all data in database
                // and print qr in next in activity
            }
        });
    }


    public void storeLecture(String s_subject, String s_date, String s_time, String s_end_time, String s_code, String id) {

        Map<String, LectureModel> lectureModelMap = new HashMap<String, LectureModel>();

        lectureModelMap.put(id, new LectureModel(s_subject, s_date, s_time, s_end_time, s_code, id));

        lectures.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                lectures.updateChildren(Collections.unmodifiableMap(lectureModelMap));
                Toast.makeText(Teacher_Profile.this, "Lecture scheduled..", Toast.LENGTH_SHORT).show();
                Intent i = new Intent(Teacher_Profile.this, TeacherDashboard.class);
                i.putExtra("Qid", id);
                startActivity(i);
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {
                Toast.makeText(Teacher_Profile.this, "Lecture schedule failed..", Toast.LENGTH_SHORT).show();
            }
        });

    }


}