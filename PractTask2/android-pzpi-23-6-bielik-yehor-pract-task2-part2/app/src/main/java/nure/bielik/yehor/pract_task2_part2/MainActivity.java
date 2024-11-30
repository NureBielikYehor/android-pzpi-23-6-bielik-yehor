package nure.bielik.yehor.pract_task2_part2;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

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
        Button calculatorButton = findViewById(R.id.calculatorButton);
        calculatorButton.setOnClickListener(view -> {
            Intent intent = new Intent(this, CalculatorActivity.class);
            startActivity(intent);
        });
    }
}