package com.example.luckynumber;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import java.util.Random;

public class SecondActivity extends AppCompatActivity {

    TextView welcomeTxt, luckyNumberTxt, userTxt;
    Button share_btn;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_second);
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });

        welcomeTxt = findViewById(R.id.textView2);
        luckyNumberTxt = findViewById(R.id.lucky_number_txt);
        share_btn = findViewById(R.id.share_btn);
        userTxt = findViewById(R.id.textView3);

        // Receiving the data from Main Activity
        Intent i = getIntent();
        String userName = i.getStringExtra("name");

        // Generate and display username
        String displayedUsername = generateUsername(userName);
        userTxt.setText(displayedUsername + "'s");


        // Generating Random Numbers
        int random_num = generateRandomNumber();
        luckyNumberTxt.setText(""+random_num);


        // Share Button Trigger to across platforms
        share_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                shareData(userName, random_num);
            }
        });
    }

    // Generating Random Numbers
    public int generateRandomNumber() {
        Random random = new Random();
        int upper_limit = 1000;

        int randomNumberGenerated = random.nextInt(upper_limit);
        return randomNumberGenerated;
    }

    // Generating and capitalizing username based on passed userName
    public String generateUsername(String userName) {
        if (userName == null || userName.isEmpty()) {
            return "User: Unknown";
        }

        // Split the name into words
        String[] words = userName.split("\\s+");
        StringBuilder capitalizedUsername = new StringBuilder();

        for (String word : words) {
            if (!word.isEmpty()) {
                capitalizedUsername.append(Character.toUpperCase(word.charAt(0)))
                        .append(word.substring(1).toLowerCase())
                        .append(" ");
            }
        }

        // Trim the trailing space
        return capitalizedUsername.toString().trim();
    }

    // Sharing Data to various application platforms
    public void shareData(String username, int randomNum) {
        // Implicit Intent
        Intent i = new Intent(Intent.ACTION_SEND);
        i.setType("text/plain");

        // Additional Info
        i.putExtra(Intent.EXTRA_SUBJECT, username + " got lucky today");
        i.putExtra(Intent.EXTRA_TEXT, "His lucky number is: " + randomNum);

        startActivity(Intent.createChooser(i, "Choosing a Platform"));

    }
}