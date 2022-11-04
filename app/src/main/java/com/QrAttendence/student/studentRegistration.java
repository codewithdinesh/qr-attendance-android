package com.QrAttendence.student;

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

//import org.json.JSONObject;

import java.util.Collections;
import java.util.HashMap;
import java.util.Map;

public class studentRegistration extends AppCompatActivity {
    TextInputEditText name, email, clas, roll_no, phone_no, enroll_no, password;
    MaterialButton submitBtn;
    FirebaseDatabase database;
    DatabaseReference myRef;
    DatabaseReference students, student;

    // student registration activity

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_student_registration);
        name = findViewById(R.id.name);
        email = findViewById(R.id.email);
        clas = findViewById(R.id.clas);
        roll_no = findViewById(R.id.roll_no);
        phone_no = findViewById(R.id.phone_no);
        enroll_no = findViewById(R.id.en);
        submitBtn = findViewById(R.id.submitBtn);
        password = findViewById(R.id.password);


        submitBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String str_name, str_email, str_class, str_roll_no, str_phone_nom, str_enroll_no, str_pass;

                str_name = name.getText().toString().trim();
                str_email = email.getText().toString().trim();
                str_class = clas.getText().toString().trim();
                str_roll_no = roll_no.getText().toString().trim();
                str_phone_nom = phone_no.getText().toString().trim();
                str_enroll_no = enroll_no.getText().toString().trim();
                str_pass = password.getText().toString().trim();

                // storing data in firebase
                database = FirebaseDatabase.getInstance();


                myRef = database.getReference("/QRAttendance");
                students = myRef.child("students");
                studentDataStore(str_email, str_name, str_class, str_roll_no, str_phone_nom, str_enroll_no, str_pass);
                // myRef.setValue(student);

                Intent i = new Intent(studentRegistration.this, StudentActivity.class);
                startActivity(i);

                // Intent i=new Intent(studentRegistration.this,);
                // startActivity(i);

            }
        });

    }

    public void studentDataStore(String str_email, String str_name, String str_class, String str_roll_no, String str_phone_nom, String str_enroll_no, String str_pass) {


        Map<String, StudentModel> studentModelMap = new HashMap<>();

        studentModelMap.put(str_roll_no, new StudentModel(str_name, str_class, str_email, str_phone_nom, str_enroll_no, str_pass));

        students.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                students.updateChildren(Collections.unmodifiableMap(studentModelMap));
                //students.setValue(studentModelMap);
                Toast.makeText(studentRegistration.this, "Registration successful", Toast.LENGTH_SHORT).show();

                SharedPreferences sharedPreferences = getSharedPreferences("userDetails", MODE_PRIVATE);
                SharedPreferences.Editor myEdit = sharedPreferences.edit();
                myEdit.putString("authID", str_enroll_no);
                myEdit.apply();
                myEdit.commit();

            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {
                Toast.makeText(studentRegistration.this, "Registration Failed", Toast.LENGTH_SHORT).show();
            }
        });

    }
}