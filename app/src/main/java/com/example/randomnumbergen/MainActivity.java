package com.example.randomnumbergen;

//package com.example.randomnumbergenerator;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.material.button.MaterialButton;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

public class MainActivity extends AppCompatActivity {

    private EditText fromInput, toInput;
    private TextView resultTextView;
    private MaterialButton findBtn;
    private RecyclerView historyRecyclerView;
    private HistoryAdapter historyAdapter;
    private List<Integer> generatedNumbers = new ArrayList<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        // Initialize UI components
        fromInput = findViewById(R.id.fromInput);
        toInput = findViewById(R.id.toInput);
        resultTextView = findViewById(R.id.resultTextView);
        findBtn = findViewById(R.id.findBtn);
        historyRecyclerView = findViewById(R.id.historyRecyclerView);

        // Set up RecyclerView
        historyAdapter = new HistoryAdapter(generatedNumbers);
        historyRecyclerView.setLayoutManager(new LinearLayoutManager(this));
        historyRecyclerView.setAdapter(historyAdapter);

        // Set click listener for the generate button
        findBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                generateRandomNumber();
            }
        });
    }

    private void generateRandomNumber() {
        try {
            // Get input values
            String fromStr = fromInput.getText().toString().trim();
            String toStr = toInput.getText().toString().trim();

            // Validate inputs
            if (fromStr.isEmpty() || toStr.isEmpty()) {
                Toast.makeText(this, "Please enter both values", Toast.LENGTH_SHORT).show();
                return;
            }

            // Parse input values
            int fromValue = Integer.parseInt(fromStr);
            int toValue = Integer.parseInt(toStr);

            // Validate range
            if (fromValue >= toValue) {
                Toast.makeText(this, "'From' value must be less than 'To' value", Toast.LENGTH_SHORT).show();
                return;
            }

            // Generate random number
            Random random = new Random();
            int randomNumber = random.nextInt((toValue - fromValue) + 1) + fromValue;

            // Display result with animation
            resultTextView.animate()
                    .alpha(0f)
                    .setDuration(150)
                    .withEndAction(new Runnable() {
                        @Override
                        public void run() {
                            resultTextView.setText(String.valueOf(randomNumber));
                            resultTextView.animate()
                                    .alpha(1f)
                                    .setDuration(150)
                                    .start();
                        }
                    }).start();

            // Add to history
            generatedNumbers.add(0, randomNumber);
            historyAdapter.notifyDataSetChanged();

        } catch (NumberFormatException e) {
            Toast.makeText(this, "Please enter valid numbers", Toast.LENGTH_SHORT).show();
        }
    }
}