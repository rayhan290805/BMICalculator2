package com.example.bmicalculator;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;
import androidx.appcompat.app.AppCompatActivity;

public class MainActivity extends AppCompatActivity {

    private EditText editTextWeight, editTextHeight;
    private Button buttonCalculate, buttonReset;
    private TextView textViewResult, textViewCategory;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        // Initialize views
        editTextWeight = findViewById(R.id.editTextWeight);
        editTextHeight = findViewById(R.id.editTextHeight);
        buttonCalculate = findViewById(R.id.buttonCalculate);
        buttonReset = findViewById(R.id.buttonReset);
        textViewResult = findViewById(R.id.textViewResult);
        textViewCategory = findViewById(R.id.textViewCategory);

        // Set click listeners
        buttonCalculate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                calculateBMI();
            }
        });

        buttonReset.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                resetFields();
            }
        });
    }

    private void calculateBMI() {
        String weightStr = editTextWeight.getText().toString().trim();
        String heightStr = editTextHeight.getText().toString().trim();

        // Validate input
        if (weightStr.isEmpty() || heightStr.isEmpty()) {
            Toast.makeText(this, "Please enter both weight and height", Toast.LENGTH_SHORT).show();
            return;
        }

        try {
            double weight = Double.parseDouble(weightStr);
            double height = Double.parseDouble(heightStr);

            // Validate values
            if (weight <= 0 || height <= 0) {
                Toast.makeText(this, "Please enter valid positive numbers", Toast.LENGTH_SHORT).show();
                return;
            }

            // Convert height from centimeters to meters
            double heightInMeters = height / 100.0;

            // Calculate BMI
            double bmi = weight / (heightInMeters * heightInMeters);

            // Display BMI result
            textViewResult.setText(String.format("BMI: %.1f", bmi));
            textViewResult.setVisibility(View.VISIBLE);

            // Determine and display BMI category
            String category = getBMICategory(bmi);
            textViewCategory.setText("Category: " + category);
            textViewCategory.setVisibility(View.VISIBLE);

            // Set category color
            setCategoryColor(category);

        } catch (NumberFormatException e) {
            Toast.makeText(this, "Please enter valid numbers", Toast.LENGTH_SHORT).show();
        }
    }

    private String getBMICategory(double bmi) {
        if (bmi < 18.5) {
            return "Underweight";
        } else if (bmi >= 18.5 && bmi <= 24.9) {
            return "Normal";
        } else if (bmi >= 25.0 && bmi <= 29.9) {
            return "Overweight";
        } else {
            return "Obese";
        }
    }

    private void setCategoryColor(String category) {
        int color;
        switch (category) {
            case "Underweight":
                color = 0xFF2196F3; // Blue
                break;
            case "Normal":
                color = 0xFF4CAF50; // Green
                break;
            case "Overweight":
                color = 0xFFFF9800; // Orange
                break;
            case "Obese":
                color = 0xFFF44336; // Red
                break;
            default:
                color = 0xFF000000; // Black
                break;
        }
        textViewCategory.setTextColor(color);
    }

    private void resetFields() {
        editTextWeight.setText("");
        editTextHeight.setText("");
        textViewResult.setVisibility(View.GONE);
        textViewCategory.setVisibility(View.GONE);
        editTextWeight.requestFocus();
    }
}