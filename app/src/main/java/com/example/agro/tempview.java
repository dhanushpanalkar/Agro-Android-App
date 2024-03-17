package com.example.agro;

import static android.content.ContentValues.TAG;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.constraintlayout.widget.ConstraintLayout;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ProgressBar;
import android.widget.TextView;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

public class tempview extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_tempview);

        FirebaseDatabase database = FirebaseDatabase.getInstance();
        DatabaseReference temper = database.getReference("Temperature");

        temper.addValueEventListener(new ValueEventListener() {
            @SuppressLint("SetTextI18n")
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                Double temperature = dataSnapshot.getValue(Double.class);
                TextView temptextview = findViewById(R.id.tempview);
                TextView tempstrview = findViewById(R.id.tempstr);
                String tempstr = temperature.toString();
                temptextview.setText(tempstr);
                ProgressBar progressBarhumid = findViewById(R.id.progress_bartemp);
                progressBarhumid.setProgress(temperature.intValue());

                if (temperature > 30) {
                    tempstrview.setText("High temperature 🔥 " + temperature + "°C");
                } else if (temperature < 10) {
                    tempstrview.setText("Low temperature ❄  " + temperature + "°C");
                } else {
                    tempstrview.setText("Normal temperature 😊 " + temperature + "°C");
                }

            }
            public void onCancelled(@NonNull DatabaseError error) {
                Log.w(TAG, "Failed to read value.", error.toException());
            }
        });

        ConstraintLayout layout = findViewById(R.id.templyt);
        layout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(tempview.this, MainScreen.class);
                startActivity(intent);
            }
        });
    }
}







