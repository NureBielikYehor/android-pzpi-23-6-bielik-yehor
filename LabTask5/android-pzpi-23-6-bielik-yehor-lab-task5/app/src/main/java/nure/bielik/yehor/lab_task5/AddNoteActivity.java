package nure.bielik.yehor.lab_task5;

import android.app.DatePickerDialog;
import android.app.TimePickerDialog;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.view.View;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.TimePicker;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

public class AddNoteActivity extends AppCompatActivity {

    private static final int PICK_IMAGE_REQUEST = 1;
    private NotesImportance noteImportance = NotesImportance.High;
    private boolean isDateOfAppointmentModified = false;
    private ImageView importanceImage;
    private Uri imageUri;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        if (NotesManager.isDarkThemeEnabled()) {
            setTheme(R.style.Theme_Lab5_Dark);
        } else {
            setTheme(R.style.Theme_Lab5_Light);
        }

        setContentView(R.layout.activity_add_note);
        ImageView imageView = findViewById(R.id.imageView);
        imageView.setOnClickListener(view -> openImageChooser());
        ImageButton checkButton = findViewById(R.id.checkButton);
        ImageButton arrow = findViewById(R.id.arrow);
        ImageButton time = findViewById(R.id.time);
        TextView date = findViewById(R.id.date);
        EditText editTextName = findViewById(R.id.name);
        importanceImage = findViewById(R.id.importanceImage);
        ImageView importanceImage = findViewById(R.id.importanceImage);
        EditText editTextDescription = findViewById(R.id.description);
        int noteId = getIntent().getIntExtra("noteId", 0);
        checkButton.setOnClickListener(view -> {
            if (editTextName.getText().toString().isBlank() && editTextDescription.getText().toString().isBlank()) {
                AlertDialog alertDialog = new AlertDialog.Builder(this).setMessage(R.string.error_note_empty).setPositiveButton(R.string.ok, null).create();
                alertDialog.show();
                return;
            }

            if (date.getText().toString().isEmpty() || !date.getText().toString().contains(" ")) {
                AlertDialog alertDialog = new AlertDialog.Builder(this).setMessage(R.string.error_appointment_date_time_not_selected).setPositiveButton(R.string.ok, null).create();
                alertDialog.show();
                return;
            }
            SimpleDateFormat simpleDateFormat = new SimpleDateFormat("dd.MM.yyyy HH:mm");
            try {
                Date appointmentDate = simpleDateFormat.parse(date.getText().toString());
                if (new Date().after(appointmentDate) && isDateOfAppointmentModified) {
                    AlertDialog alertDialog = new AlertDialog.Builder(this).setMessage(R.string.error_invalid_appointment_date).setPositiveButton(R.string.ok, null).create();
                    alertDialog.show();
                    return;
                }

            if (noteId == 0) {
                Note note = new Note(editTextName.getText().toString(), editTextDescription.getText().toString(), new Date(), appointmentDate, noteImportance, imageUri == null ? null : imageUri.toString());
                NotesManager.addNote(note);
            } else {
                Note note = new Note(editTextName.getText().toString(), editTextDescription.getText().toString(), new Date(), noteId, appointmentDate, noteImportance, imageUri == null ? null : imageUri.toString());
                NotesManager.editNote(note);
            }
            onBackPressed();
        } catch (ParseException e) {
            throw new RuntimeException(e);
        }
        });

        View.OnClickListener setDateClickListener = view -> {
            Calendar calendar = Calendar.getInstance();
            int day = calendar.get(Calendar.DAY_OF_MONTH);
            int month = calendar.get(Calendar.MONTH);
            int year = calendar.get(Calendar.YEAR);
            int hour = calendar.get(Calendar.HOUR_OF_DAY);
            int minute = calendar.get(Calendar.MINUTE);
            TimePickerDialog timePickerDialog = new TimePickerDialog(AddNoteActivity.this, new TimePickerDialog.OnTimeSetListener() {
                @Override
                public void onTimeSet(TimePicker timePicker, int hour, int minute) {
                    date.setText(date.getText() + " " + addZero(hour) + ":" + addZero(minute));
                }
            }, hour, minute, true);
            DatePickerDialog datePickerDialog = new DatePickerDialog(AddNoteActivity.this, new DatePickerDialog.OnDateSetListener() {
                @Override
                public void onDateSet(DatePicker datePicker, int year, int month, int day) {
                    time.setVisibility(View.GONE);
                    date.setText(addZero(day) + "." + (addZero(month + 1) + "." + year));
                    timePickerDialog.show();
                }
            }, year, month, day);
            datePickerDialog.getDatePicker().setMinDate(System.currentTimeMillis());
            datePickerDialog.show();
            isDateOfAppointmentModified = true;
        };

        time.setOnClickListener(setDateClickListener);
        date.setOnClickListener(setDateClickListener);

        arrow.setOnClickListener(view -> {
            onBackPressed();
        });
        if (noteId != 0) {
            SimpleDateFormat simpleDateFormat = new SimpleDateFormat("dd.MM.yyyy HH:mm");
            Note note = NotesManager.getNote(noteId);
            editTextName.setText(note.getName());
            editTextDescription.setText(note.getDescription());
            noteImportance = note.getImportance();
            updateImportanceImage();
            date.setText(simpleDateFormat.format(note.getDateOfAppointment()));
            time.setVisibility(View.GONE);
            if (note.getImageUri() != null) {
                imageUri = Uri.parse(note.getImageUri());
                imageView.setImageURI(imageUri);
            }
        }

        importanceImage.setOnClickListener(view -> {
            if(noteImportance.equals(NotesImportance.High)) {
                noteImportance = NotesImportance.Low;
            } else if(noteImportance.equals(NotesImportance.Low)) {
                noteImportance = NotesImportance.Medium;
            } else {
                noteImportance = NotesImportance.High;
            }
            updateImportanceImage();
        });
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if (requestCode == PICK_IMAGE_REQUEST && resultCode == RESULT_OK && data != null) {
            imageUri = saveImageToInternalStorage(data.getData());
            if (imageUri != null) {
                ImageView imageView = findViewById(R.id.imageView);
                imageView.setImageURI(imageUri);

                updateNoteImage(imageUri);
            }
        }
    }

    private Uri saveImageToInternalStorage(Uri imageUri) {
        try {
            InputStream inputStream = getContentResolver().openInputStream(imageUri);
            if (inputStream == null) return null;

            String fileName = "saved_image_" + System.currentTimeMillis() + ".jpg";
            File file = new File(getFilesDir(), fileName);

            FileOutputStream outputStream = new FileOutputStream(file);
            byte[] buffer = new byte[1024];
            int length;
            while ((length = inputStream.read(buffer)) > 0) {
                outputStream.write(buffer, 0, length);
            }

            outputStream.close();
            inputStream.close();
            return Uri.fromFile(file);

        } catch (IOException e) {
            e.printStackTrace();
        }
        return null;
    }


    private void updateNoteImage(Uri selectedImageUri) {
        int noteId = getIntent().getIntExtra("noteId", 0);
        if (noteId != 0) {
            Note note = NotesManager.getNote(noteId);
            note.setImageUri(selectedImageUri.toString());
            NotesManager.editNote(note);
        }
    }

    private void openImageChooser() {
        Intent intent = new Intent(Intent.ACTION_PICK, MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
        intent.setType("image/*");
        startActivityForResult(intent, PICK_IMAGE_REQUEST);
    }

    private String addZero(int value) {
        if (value < 10) {
            return "0" + value;
        } else {
            return Integer.toString(value);
        }
    }

    private void updateImportanceImage() {
        if(noteImportance.equals(NotesImportance.High)) {
            importanceImage.setImageResource(R.drawable.star_high);
        } else if(noteImportance.equals(NotesImportance.Low)) {
            importanceImage.setImageResource(R.drawable.star_low);
        } else {
            importanceImage.setImageResource(R.drawable.star_medium);
        }
    }
}