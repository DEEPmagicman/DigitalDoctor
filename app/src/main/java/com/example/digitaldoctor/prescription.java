package com.example.digitaldoctor;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.annotation.SuppressLint;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.DashPathEffect;
import android.graphics.Paint;
import android.graphics.pdf.PdfDocument;
import android.os.Bundle;
import android.view.View;
import android.view.Window;
import android.widget.Button;
import android.widget.EditText;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Date;

public class prescription extends AppCompatActivity {
    // Write a message to the database
    FirebaseDatabase database = FirebaseDatabase.getInstance();
    DatabaseReference myRef = database.getReference("record");
    DataObj dataObj = new DataObj();
    Button btnloginbutton;
    EditText discription,ill,p1,p2,p3,p4;
    long dpriscriptionNo = 0;
    @SuppressLint("SimpleDateFormat")
    SimpleDateFormat datePatternformat = new SimpleDateFormat("dd-mm-yyyy hh:mm a");


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        requestWindowFeature(Window.FEATURE_NO_TITLE); //will hide the title
        getSupportActionBar().hide(); // hide the title bar
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_prescription);
        callFindViewById();
        callOnclickListener();

        myRef.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                dpriscriptionNo = snapshot.getChildrenCount();
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });
    }

    private void callOnclickListener() {
        btnloginbutton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dataObj.prescriptionNo = dpriscriptionNo + 1;
                dataObj.ill = String.valueOf(ill.getText());
                dataObj.p1 = String.valueOf(p1.getText());
                dataObj.p2 = String.valueOf(p2.getText());
                dataObj.p3 = String.valueOf(p3.getText());
                dataObj.p4 = String.valueOf(p4.getText());
                dataObj.discription = String.valueOf(discription.getText());
                dataObj.date = new Date().getTime();

                myRef.child(String.valueOf(dpriscriptionNo+1)).setValue(dataObj);

                try {
                    printPdf();
                } catch (IOException e) {
                    e.printStackTrace();
                }
                //9822204910 9767177341 hp service
            }
        });
        
    }

    private void printPdf() throws IOException {
        PdfDocument myPdfDocument = new PdfDocument();
        Paint paint = new Paint();
        Paint forLinePaint = new Paint();
        PdfDocument.PageInfo myPageInfo = new PdfDocument.PageInfo.Builder(250,350,1).create();
        PdfDocument.Page myPage = myPdfDocument.startPage(myPageInfo);
        Canvas canvas = myPage.getCanvas();

        paint.setTextSize(15.5f);
        paint.setColor(Color.rgb(0,50,250));

        canvas.drawText("Digital Doctor", 20, 20,paint);
        paint.setTextSize(8.5f);
        canvas.drawText("Address : 101, Sai Residence",20,40,paint);
        canvas.drawText("Surat , 395002",20,55,paint);
        forLinePaint.setStyle(Paint.Style.STROKE);
        forLinePaint.setPathEffect(new DashPathEffect(new float[]{5,5},0));
        forLinePaint.setStrokeWidth(2);
        canvas.drawLine(20,65,230,65,forLinePaint);

        canvas.drawText("Patient Name : ",20,80,paint);
        canvas.drawLine(20,90,250,90,forLinePaint);

        canvas.drawText("By Dr. Nirmal Mehta",20,105,paint);

        canvas.drawText("Illness "+ill.getText(),20,125,paint);
        canvas.drawText("Prescription: ",20,145,paint);
        canvas.drawText(" "+p1.getText(),100,145,paint);
        canvas.drawText(" "+p2.getText(),100,155,paint);
        canvas.drawText(" "+p3.getText(),100,165,paint);
        canvas.drawText(" "+p4.getText(),100,175,paint);
        canvas.drawText("Discription: ",20,195,paint);
        canvas.drawText(" "+discription.getText(),20,205,paint);

        canvas.drawLine(20,210,230,210,forLinePaint);

        canvas.drawText("Date: "+datePatternformat.format(new Date().getTime()),20,260,paint);
        canvas.drawText(String.valueOf(dpriscriptionNo+1),20,275,paint);
        canvas.drawText("Payment Method: Cash",20,290,paint);

        paint.setTextAlign(Paint.Align.CENTER);
        paint.setTextSize(12f);
        canvas.drawText("Get Well Soon!",canvas.getWidth()/2,320,paint);

        myPdfDocument.finishPage(myPage);
        File file = new File(this.getExternalFilesDir("/"),"Prescription.pdf");

        try {
            myPdfDocument.writeTo(new FileOutputStream(file));
        } catch (IOException e) {
            e.printStackTrace();
        }

        myPdfDocument.close();

    }

    private void callFindViewById() {
        btnloginbutton = findViewById(R.id.loginbutton);
        p1 = findViewById(R.id.prescription_1);
        p2 = findViewById(R.id.prescription_2);
        p3 = findViewById(R.id.prescription_3);
        p4 = findViewById(R.id.prescription_4);
        ill = findViewById(R.id.name);
        discription = findViewById(R.id.details);
    }
}