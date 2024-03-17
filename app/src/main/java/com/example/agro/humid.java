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

public class humid extends AppCompatActivity {
    private int progresshumid = 0;
    private ProgressBar progressBarhumid;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_humid);
        FirebaseDatabase database = FirebaseDatabase.getInstance();
        DatabaseReference humidity = database.getReference("Humidity");

        humidity.addValueEventListener(new ValueEventListener() {
            @SuppressLint("SetTextI18n")
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                Double humidity = dataSnapshot.getValue(Double.class);
                TextView humidtextview = findViewById(R.id.Humidityview);
                TextView humidstr = findViewById(R.id.humidstr);
                assert humidity != null;
                String humid = humidity.toString();
                humidtextview.setText(humid);
                ProgressBar progressBarhumid = findViewById(R.id.progress_barhumid);
                progressBarhumid.setProgress(humidity.intValue());

                if (humidity < 30) {
                    humidstr.setText("☀");
                } else if (humidity >= 30 && humidity < 60) {
                    humidstr.setText("🌤️");
                } else {
                    humidstr.setText("☁");
                }

            }
            public void onCancelled(@NonNull DatabaseError error) {
                Log.w(TAG, "Failed to read value.", error.toException());
            }
        });

        ConstraintLayout layout = findViewById(R.id.humidcontraintlyt);
        layout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(humid.this, MainScreen.class);
                startActivity(intent);
            }
        });
    }


}