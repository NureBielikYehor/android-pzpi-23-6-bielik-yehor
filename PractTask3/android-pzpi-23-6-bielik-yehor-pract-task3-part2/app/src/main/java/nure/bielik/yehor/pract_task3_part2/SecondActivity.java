package nure.bielik.yehor.pract_task3_part2;

import android.os.Bundle;
import android.os.Handler;
import android.os.Looper;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

public class SecondActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_second);
    }

    @Override
    protected void onStart() {
        super.onStart();
        Handler handler = new Handler(Looper.getMainLooper());

        new Thread(() -> {
            try {
                Thread.sleep(3000);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            handler.post(() -> {
                TextView textView = findViewById(R.id.handlerMessageTextView);
                textView.setText("Updated from background thread");
            });
        }).start();
    }
}