package nure.bielik.yehor.pract_task2_part1;

import android.graphics.Color;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

public class MainActivity extends AppCompatActivity {

    TextView twitterTextView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
    }

    @Override
    protected void onStart() {
        super.onStart();
        twitterTextView = findViewById(R.id.twitterTextView);
    }

    public void onButtonClick(View view) {
        twitterTextView.setText("rettiwT");
    }

    public void onButtonClick2(View view) {
        Toast.makeText(this, "Повідомлення успішно виведено", Toast.LENGTH_SHORT).show();
    }
}