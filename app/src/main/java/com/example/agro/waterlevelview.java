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

public class waterlevelview extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_waterlevelview);
        FirebaseDatabase database = FirebaseDatabase.getInstance();
        DatabaseReference watervw = database.getReference("waterlevel");

        watervw.addValueEventListener(new ValueEventListener() {
            @SuppressLint("SetTextI18n")
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                Double waterlvl = dataSnapshot.getValue(Double.class);
                TextView lvltextview = findViewById(R.id.waterview);
                TextView lvlstr = findViewById(R.id.waterstr);
                assert waterlvl != null;
                String lvl = waterlvl.toString();
                lvltextview.setText(lvl);
                ProgressBar progressBarwater = findViewById(R.id.vprogressbar);
                progressBarwater.setProgress(waterlvl.intValue());

                String waterLevelString;
                if ( waterlvl == 0) {
                    waterLevelString = "Water level is empty.";
                } else if ( waterlvl < 25) {
                    waterLevelString = "Water level is low.";
                } else if ( waterlvl < 50) {
                    waterLevelString = "Water level is half full.";
                } else if ( waterlvl < 75) {
                    waterLevelString = "Water level is almost full.";
                } else {
                    waterLevelString = "Water level is full.";
                }

                lvlstr.setText( waterlvl + "%" + " " + waterLevelString);
            }

            public void onCancelled(@NonNull DatabaseError error) {
                Log.w(TAG, "Failed to read value.", error.toException());
            }
        });

        ConstraintLayout layout = findViewById(R.id.waterviewlyt);
        layout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(waterlevelview.this, MainScreen.class);
                startActivity(intent);
            }
        });
    }
}