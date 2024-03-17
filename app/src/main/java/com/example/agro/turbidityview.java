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

public class turbidityview extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_turbidityview);
        FirebaseDatabase database = FirebaseDatabase.getInstance();
        DatabaseReference Turbidity = database.getReference("turbidity");

        Turbidity.addValueEventListener(new ValueEventListener() {
            @SuppressLint("SetTextI18n")
            @Override

            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                Double turbidity = dataSnapshot.getValue(Double.class);
                assert turbidity != null;
                String turbiditystr = turbidity.toString();
                TextView turbiditytextview = findViewById(R.id.turbidityview);
                TextView turbstr = findViewById(R.id.turbstr);
                ProgressBar progressBartur = findViewById(R.id.progress_barturb);
                progressBartur.setProgress(turbidity.intValue());
                turbiditytextview.setText(turbiditystr);
                if (turbidity >= 3000) {
                    if (turbidity >= 4050) {
                        // water is pure
                        turbstr.setText("Water is Pure💧💎");
                        progressBartur.getProgressDrawable().setColorFilter(Color.GREEN, PorterDuff.Mode.SRC_IN);
                    } else {
                        // water is slightly impure
                        turbstr.setText("Water is slightly impure💧🧪");
                        progressBartur.getProgressDrawable().setColorFilter(Color.YELLOW, PorterDuff.Mode.SRC_IN);
                    }
                } else {
                    // water is highly impure
                    turbstr.setText("Water is highly impure💧🚱");
                    progressBartur.getProgressDrawable().setColorFilter(Color.RED, PorterDuff.Mode.SRC_IN);
                }
            }

            public void onCancelled(DatabaseError error) {
                Log.w(TAG, "Failed to read value.", error.toException());
            }
        });

        ConstraintLayout layoutturb = findViewById(R.id.turblyt);
        layoutturb.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(turbidityview.this, MainScreen.class);
                startActivity(intent);
            }
        });


    }
}
