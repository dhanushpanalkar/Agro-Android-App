package com.example.agro;

import static android.content.ContentValues.TAG;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.constraintlayout.widget.ConstraintLayout;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.PorterDuff;
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

public class soilview extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_soilview);

        FirebaseDatabase database = FirebaseDatabase.getInstance();
        DatabaseReference Soil = database.getReference("Soil");
        Soil.addValueEventListener(new ValueEventListener() {
            @SuppressLint("SetTextI18n")
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                Double soil = dataSnapshot.getValue(Double.class);
                TextView soiltextview = findViewById(R.id.soilview);
                TextView soilstr = findViewById(R.id.soilstr);
                String soilstrng = soil.toString();
                soiltextview.setText(soilstrng);
                ProgressBar progressBarsoil = findViewById(R.id.progress_barsoil);
                progressBarsoil.setProgress(soil.intValue());

                if (soil > 3000) {
                    soilstr.setText("Soil is Very dry");
                    progressBarsoil.getProgressDrawable().setColorFilter(Color.RED, PorterDuff.Mode.SRC_IN);
                } else if (soil >= 2000 && soil < 3000) {
                    soilstr.setText("Soil is Slush");
                    progressBarsoil.getProgressDrawable().setColorFilter(Color.YELLOW, PorterDuff.Mode.SRC_IN);
                } else if (soil >= 1000 && soil < 2000) {
                    soilstr.setText("Soil is Moist");
                    progressBarsoil.getProgressDrawable().setColorFilter(Color.GREEN, PorterDuff.Mode.SRC_IN);
                } else {
                    soilstr.setText("Soil is Wet");
                    progressBarsoil.getProgressDrawable().setColorFilter(Color.GREEN, PorterDuff.Mode.SRC_IN);
                }
            }
            public void onCancelled(DatabaseError error) {
                // Handle the error
                Log.w(TAG, "Failed to read value.", error.toException());
            }
        });

        ConstraintLayout layoutsoil = findViewById(R.id.soilcontraintlyt);
        layoutsoil.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(soilview.this, MainScreen.class);
                startActivity(intent);
            }
        });
    }
}