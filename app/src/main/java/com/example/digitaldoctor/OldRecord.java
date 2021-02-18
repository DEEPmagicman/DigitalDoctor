package com.example.digitaldoctor;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.Window;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TableLayout;

import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

public class OldRecord extends AppCompatActivity {
    FirebaseDatabase database = FirebaseDatabase.getInstance();
    DatabaseReference myRef = database.getReference("record");
    Button oldPdfbutton;
    EditText oldPdfEditTest;
    TableLayout tableLayout;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        requestWindowFeature(Window.FEATURE_NO_TITLE); //will hide the title
        getSupportActionBar().hide(); // hide the title bar
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_old_record);
        oldPdfbutton = findViewById(R.id.oldPrintBtn);
        oldPdfEditTest = findViewById(R.id.oldPrintEditText);

    }
}