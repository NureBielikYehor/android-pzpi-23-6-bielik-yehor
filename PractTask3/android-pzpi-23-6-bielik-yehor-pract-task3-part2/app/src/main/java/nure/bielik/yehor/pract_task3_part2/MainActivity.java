package nure.bielik.yehor.pract_task3_part2;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.Looper;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
    }

    @Override
    protected void onStart() {
        super.onStart();

        Handler handler = new Handler(Looper.getMainLooper());

        Button startHandlerButton = findViewById(R.id.startHandlerButton);
        Button secondActivityButton = findViewById(R.id.secondActivityButton);
        Button thirdActivityButton = findViewById(R.id.thirdActivityButton);

        startHandlerButton.setOnClickListener(v -> handler.postDelayed(() -> {
            TextView textView = findViewById(R.id.handlerMessageTextView);
            textView.setText("Handler executed after delay");
        }, 2000));

        secondActivityButton.setOnClickListener(v -> {
            Intent intent = new Intent(MainActivity.this, SecondActivity.class);
            startActivity(intent);
        });

        thirdActivityButton.setOnClickListener(v -> {
            Intent intent = new Intent(MainActivity.this, ThirdActivity.class);
            startActivity(intent);
        });
    }
}