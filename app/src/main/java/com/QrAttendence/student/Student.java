package com.QrAttendence.student;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.QrAttendence.R;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

public class Student extends AppCompatActivity {
    Button registration, login;
    EditText email, pass;
    FirebaseDatabase database;
    DatabaseReference myRef, students;
    DataSnapshot dataSnapshot;

    //Login Student

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_student);

        email = findViewById(R.id.email);
        pass = findViewById(R.id.pass);

        registration = findViewById(R.id.registartion);
        login = findViewById(R.id.login);

        login.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                String spass, semail;
                spass = pass.getText().toString();
                semail = email.getText().toString();
                loginUser(semail, spass);
            }
        });

        registration.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent(Student.this, studentRegistration.class);
                startActivity(i);
            }
        });
    }

    public void loginUser(String semail, String spass) {

        database = FirebaseDatabase.getInstance();
        myRef = database.getReference("/QRAttendance");
        students = myRef.child("students");


        students.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                Object id = snapshot.getValue();

                Toast.makeText(Student.this, "" + snapshot.getChildrenCount(), Toast.LENGTH_SHORT).show();

//                for (DataSnapshot postSnapshot : dataSnapshot.getChildren()) {
//                    UserDetails userDetails = postSnapshot.getValue(UserDetails.class);
//                    userDetailsArray.add(0, userDetails);
//                }

                Intent i = new Intent(Student.this, StudentActivity.class);
                startActivity(i);

                Toast.makeText(Student.this, "ID: " + id, Toast.LENGTH_SHORT).show();

            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {
                Toast.makeText(Student.this, "", Toast.LENGTH_SHORT).show();
            }
        });


    }

}