package nure.bielik.yehor.pract_task4;

import android.annotation.SuppressLint;
import android.content.ContentValues;
import android.content.Context;
import android.content.SharedPreferences;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.widget.Button;
import android.widget.EditText;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity {

    private SharedPreferences sharedPreferences;
    private DBHelper dbHelper;
    private EditText nameEditText;
    private EditText ageEditText;
    private RecyclerView list;
    private List<User> users;
    private MyAdapter adapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
    }

    @Override
    protected void onStart() {
        super.onStart();

        initializeUIComponents();
        initializeSharedPreferences();
        setupRecyclerView();
    }

    private void initializeUIComponents() {
        nameEditText = findViewById(R.id.name);
        ageEditText = findViewById(R.id.age);
        list = findViewById(R.id.list);

        Button saveToSharedPreferences = findViewById(R.id.saveToSharedPreferences);
        Button saveToDataBase = findViewById(R.id.saveToDataBase);
        Button saveToTextFile = findViewById(R.id.saveToTextFile);
        Button readFromTextFile = findViewById(R.id.readFromTextFile);

        saveToSharedPreferences.setOnClickListener(view -> saveToSharedPreferences());
        saveToDataBase.setOnClickListener(view -> saveToDatabase());
        saveToTextFile.setOnClickListener(view -> saveToTextFile());
        readFromTextFile.setOnClickListener(view -> readFromTextFile());
    }

    private void initializeSharedPreferences() {
        sharedPreferences = getSharedPreferences("MyPrefs", MODE_PRIVATE);
        loadFromSharedPreferences();
    }

    private void setupRecyclerView() {
        list.setLayoutManager(new LinearLayoutManager(this));
        dbHelper = new DBHelper(this);
        users = loadUsersFromDatabase();
        adapter = new MyAdapter(users);
        list.setAdapter(adapter);
    }

    private void saveToTextFile() {
        try (FileOutputStream fos = openFileOutput("myfile.txt", Context.MODE_PRIVATE)) {
            fos.write(nameEditText.getText().toString().getBytes());
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    private void readFromTextFile() {
        try (FileInputStream fis = openFileInput("myfile.txt")) {
            StringBuilder name = new StringBuilder();
            int c;
            while ((c = fis.read()) != -1) {
                name.append((char) c);
            }
            nameEditText.setText(name.toString());
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private void saveToSharedPreferences() {
        SharedPreferences.Editor editor = sharedPreferences.edit();
        editor.putString("Name", nameEditText.getText().toString());
        editor.putInt("Age", ageEditText.getText().toString().isEmpty() ? 0 : Integer.parseInt(ageEditText.getText().toString()));
        editor.apply();
    }

    private void loadFromSharedPreferences() {
        String name = sharedPreferences.getString("Name", "");
        int age = sharedPreferences.getInt("Age", -1);
        nameEditText.setText(name);
        if (age >= 0) {
            ageEditText.setText(Integer.toString(age));
        }
    }

    private void saveToDatabase() {
        SQLiteDatabase db = dbHelper.getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put("name", nameEditText.getText().toString());
        values.put("age", ageEditText.getText().toString().isEmpty() ? 0 : Integer.parseInt(ageEditText.getText().toString()));
        db.insert("users", null, values);

        users.add(new User(nameEditText.getText().toString(), Integer.parseInt(ageEditText.getText().toString())));
        adapter.updateData(users);
    }

    @SuppressLint("Range")
    private List<User> loadUsersFromDatabase() {
        List<User> users = new ArrayList<>();
        SQLiteDatabase db = dbHelper.getReadableDatabase();
        try (Cursor cursor = db.query("users", null, null, null, null, null, null)) {
            while (cursor.moveToNext()) {
                String name = cursor.getString(cursor.getColumnIndex("name"));
                int age = cursor.getInt(cursor.getColumnIndex("age"));
                users.add(new User(name, age));
            }
        }
        return users;
    }
}
