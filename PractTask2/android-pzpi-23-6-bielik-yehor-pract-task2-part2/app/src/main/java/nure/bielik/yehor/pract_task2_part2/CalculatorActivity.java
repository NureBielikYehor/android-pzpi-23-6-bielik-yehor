package nure.bielik.yehor.pract_task2_part2;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

public class CalculatorActivity extends AppCompatActivity {

    TextView calculation;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_calculator);
    }

    @Override
    protected void onStart() {
        super.onStart();
        calculation = findViewById(R.id.calculation);

    }

    public void onDigitClick(View view) {
        Button button = (Button) view;
        calculation.setText(button.getText());
    }
}