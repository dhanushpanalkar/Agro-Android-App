package com.example.agro;

import static android.content.ContentValues.TAG;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.cardview.widget.CardView;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.TextView;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

public class MainScreen extends AppCompatActivity {

    private CardView humicard;
    private CardView tempcard;
    private CardView turbcard;
    private CardView soilcard;
    private CardView watercard;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_screen);

        FirebaseDatabase database = FirebaseDatabase.getInstance();
        DatabaseReference Humidity = database.getReference("Humidity");
        DatabaseReference Temperature = database.getReference("Temperature");
        DatabaseReference Soil = database.getReference("Soil");
        DatabaseReference Turbidity = database.getReference("turbidity");
        DatabaseReference water = database.getReference("waterlevel");

        humicard = findViewById(R.id.humidcard);
        tempcard = findViewById(R.id.temp);
        turbcard = findViewById(R.id.turbiditycard);
        soilcard = findViewById(R.id.cardViewsoil);
        watercard = findViewById(R.id.cardViewwater);

        // Set click listeners for each card

        humicard.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(MainScreen.this, humid.class);
                startActivity(intent);
            }
        });

        tempcard.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(MainScreen.this, tempview.class);
                startActivity(intent);
            }
        });

        turbcard.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(MainScreen.this, turbidityview.class);
                startActivity(intent);
            }
        });

        soilcard.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(MainScreen.this, soilview.class);
                startActivity(intent);
            }
        });

        watercard.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(MainScreen.this, waterlevelview.class);
                startActivity(intent);
            }
        });

        // ValueEventListener for Humidity
        Humidity.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                displayData(dataSnapshot, R.id.Humidity);
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {
                Log.w(TAG, "Failed to read Humidity value.", error.toException());
            }
        });

        // ValueEventListener for Temperature
        Temperature.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                displayData(dataSnapshot, R.id.Temperature);
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {
                Log.w(TAG, "Failed to read Temperature value.", error.toException());
            }
        });

        // ValueEventListener for Soil
        Soil.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                displayData(dataSnapshot, R.id.Soil);
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {
                Log.w(TAG, "Failed to read Soil value.", error.toException());
            }
        });

        // ValueEventListener for Turbidity
        Turbidity.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                displayData(dataSnapshot, R.id.Turbidity);
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {
                Log.w(TAG, "Failed to read Turbidity value.", error.toException());
            }
        });

        // ValueEventListener for Water Level
        water.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                displayData(dataSnapshot, R.id.watertxt);
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {
                Log.w(TAG, "Failed to read Water Level value.", error.toException());
            }
        });
    }

    // Method to display data or "Null" if data is not available
    private void displayData(DataSnapshot dataSnapshot, int textViewId) {
        Double data = dataSnapshot.getValue(Double.class);
        TextView textView = findViewById(textViewId);
        if (data != null) {
            textView.setText(String.valueOf(data));
        } else {
            textView.setText("Null");
        }
    }
}
