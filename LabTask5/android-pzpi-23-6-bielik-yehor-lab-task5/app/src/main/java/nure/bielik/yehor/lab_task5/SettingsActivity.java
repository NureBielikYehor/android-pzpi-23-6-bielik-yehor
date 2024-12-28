package nure.bielik.yehor.lab_task5;

import android.content.Intent;
import android.os.Bundle;
import android.widget.Button;
import android.widget.SeekBar;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.SwitchCompat;

public class SettingsActivity extends AppCompatActivity {
    private SeekBar fontSizeSeekBar;
    private Button saveButton;
    private SwitchCompat themeSwitch;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        if (NotesManager.isDarkThemeEnabled()) {
            setTheme(R.style.Theme_Lab5_Dark);
        } else {
            setTheme(R.style.Theme_Lab5_Light);
        }

        setContentView(R.layout.activity_settings);
        themeSwitch = findViewById(R.id.theme_switch);
        themeSwitch.setChecked(NotesManager.isDarkThemeEnabled());

        themeSwitch.setOnCheckedChangeListener((buttonView, isChecked) -> {
            NotesManager.setDarkThemeEnabled(isChecked);
            recreate();
        });

        fontSizeSeekBar = findViewById(R.id.font_size_seekbar);
        saveButton = findViewById(R.id.save_button);

        float currentFontSize = NotesManager.getFontSize();
        fontSizeSeekBar.setProgress((int) currentFontSize);

        fontSizeSeekBar.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener() {
            @Override
            public void onProgressChanged(SeekBar seekBar, int progress, boolean fromUser) {}

            @Override
            public void onStartTrackingTouch(SeekBar seekBar) {}

            @Override
            public void onStopTrackingTouch(SeekBar seekBar) {}
        });

        saveButton.setOnClickListener(v -> {
            float newFontSize = fontSizeSeekBar.getProgress();
            NotesManager.setFontSize(newFontSize);
            Intent intent = new Intent(SettingsActivity.this, MainActivity.class);
            startActivity(intent);
            finish();
        });
    }
}
