package com.example.agro;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;
import androidx.appcompat.app.AppCompatActivity;

import com.example.agro.DatabaseHelper;

public class RegisterActivity extends AppCompatActivity {

    private EditText editTextName;
    private EditText editTextVillageName;
    private EditText editTextMobileNumber;
    private EditText editTextPassword;
    private EditText editTextEmail;
    private DatabaseHelper dbHelper;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.register_activity);

        dbHelper = new DatabaseHelper(this);

        editTextName = findViewById(R.id.editTextName);
        editTextEmail = findViewById(R.id.editTextEmail);
        editTextVillageName = findViewById(R.id.editTextVillageName);
        editTextMobileNumber = findViewById(R.id.editTextMobileNumber);
        editTextPassword = findViewById(R.id.editTextPassword);

        Button buttonRegister = findViewById(R.id.buttonRegister);
        buttonRegister.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                registerUser();
            }
        });

        Button buttonBack = findViewById(R.id.buttonBack);
        buttonBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(RegisterActivity.this, MainActivity.class);
                startActivity(intent);
                finish();
            }
        });
    }

    private void registerUser() {
        String name = editTextName.getText().toString().trim();
        String email = editTextEmail.getText().toString().trim();
        String villageName = editTextVillageName.getText().toString().trim();
        String mobileNumber = editTextMobileNumber.getText().toString().trim();
        String password = editTextPassword.getText().toString().trim();
        if (name.isEmpty() || email.isEmpty() || villageName.isEmpty() || mobileNumber.isEmpty() || password.isEmpty()) {
            Toast.makeText(RegisterActivity.this, "Please fill in all fields", Toast.LENGTH_SHORT).show();
            return;
        }

        boolean isInserted = dbHelper.addUser(email, name, villageName, mobileNumber, password);

        if (isInserted) {
            Toast.makeText(RegisterActivity.this, "Registration successful", Toast.LENGTH_SHORT).show();
            clearFields();
        } else {
            Toast.makeText(RegisterActivity.this, "Registration failed", Toast.LENGTH_SHORT).show();
        }
    }

    private void clearFields() {
        editTextName.setText("");
        editTextVillageName.setText("");
        editTextMobileNumber.setText("");
        editTextPassword.setText("");
        editTextEmail.setText("");
    }
}
