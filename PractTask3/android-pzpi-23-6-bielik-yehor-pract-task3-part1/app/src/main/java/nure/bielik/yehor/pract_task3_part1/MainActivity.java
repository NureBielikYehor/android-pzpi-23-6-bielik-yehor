package nure.bielik.yehor.pract_task3_part1;

import android.app.AlertDialog;
import android.app.DatePickerDialog;
import android.content.DialogInterface;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.Button;
import android.widget.DatePicker;

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
        Button showDialogButton = findViewById(R.id.showDialogButton);
        Button showDatePickerButton = findViewById(R.id.showDatePickerButton);
        Button showCustomDialogButton = findViewById(R.id.showCustomDialogButton);
        showDialogButton.setOnClickListener(v -> new AlertDialog.Builder(MainActivity.this)
                .setTitle("Діалог")
                .setMessage("Це приклад AlertDialog.")
                .setPositiveButton("OK", (dialog, which) -> {
                })
                .setNegativeButton("Cancel", (dialog, which) -> {
                })
                .show());

        showDatePickerButton.setOnClickListener(v -> {
            DatePickerDialog datePickerDialog = new DatePickerDialog(MainActivity.this,
                    (view, year, month, dayOfMonth) -> {
                    }, 2023, 8, 1);
            datePickerDialog.show();
        });

        showCustomDialogButton.setOnClickListener(v -> {
            LayoutInflater inflater = getLayoutInflater();
            View dialogView = inflater.inflate(R.layout.custom_dialog, null);

            AlertDialog.Builder builder = new AlertDialog.Builder(MainActivity.this);
            builder.setView(dialogView)
                    .setPositiveButton("OK", (dialog, id) -> {
                    })
                    .setNegativeButton("Cancel", (dialog, id) -> {
                    });
            builder.create().show();
        });
    }
}