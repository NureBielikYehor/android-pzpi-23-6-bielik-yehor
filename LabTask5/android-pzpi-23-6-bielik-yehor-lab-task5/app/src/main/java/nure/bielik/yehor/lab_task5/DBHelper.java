package nure.bielik.yehor.lab_task5;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public class DBHelper extends SQLiteOpenHelper {

    private static final String DATABASE_NAME = "notes.db";
    private static final int DATABASE_VERSION = 2;
    private static final String TABLE_NOTES = "notes";
    private static final String COLUMN_ID = "id";
    private static final String COLUMN_NAME = "name";
    private static final String COLUMN_DESCRIPTION = "description";
    private static final String COLUMN_DATE_CREATION = "date_creation";
    private static final String COLUMN_DATE_APPOINTMENT = "date_appointment";
    private static final String COLUMN_IMPORTANCE = "importance";
    private static final String COLUMN_IMAGE_URI = "image_uri";

    public DBHelper(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        String createNotesTableQuery = "CREATE TABLE " + TABLE_NOTES + " (" +
                COLUMN_ID + " INTEGER PRIMARY KEY AUTOINCREMENT, " +
                COLUMN_NAME + " TEXT, " +
                COLUMN_DESCRIPTION + " TEXT, " +
                COLUMN_DATE_CREATION + " INTEGER, " +
                COLUMN_DATE_APPOINTMENT + " INTEGER, " +
                COLUMN_IMPORTANCE + " TEXT, " +
                COLUMN_IMAGE_URI + " TEXT)";
        db.execSQL(createNotesTableQuery);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {

    }

    public void addNote(Note note) {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put(COLUMN_NAME, note.getName());
        values.put(COLUMN_DESCRIPTION, note.getDescription());
        values.put(COLUMN_DATE_CREATION, note.getDateOfCreation().getTime());
        values.put(COLUMN_DATE_APPOINTMENT, note.getDateOfAppointment().getTime());
        values.put(COLUMN_IMPORTANCE, note.getImportance().name());
        values.put(COLUMN_IMAGE_URI, note.getImageUri());

        db.insert(TABLE_NOTES, null, values);
        db.close();
    }

    public void updateNote(Note note) {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put(COLUMN_NAME, note.getName());
        values.put(COLUMN_DESCRIPTION, note.getDescription());
        values.put(COLUMN_DATE_CREATION, note.getDateOfCreation().getTime());
        values.put(COLUMN_DATE_APPOINTMENT, note.getDateOfAppointment().getTime());
        values.put(COLUMN_IMPORTANCE, note.getImportance().name());
        values.put(COLUMN_IMAGE_URI, note.getImageUri());

        db.update(TABLE_NOTES, values, COLUMN_ID + " = ?", new String[]{String.valueOf(note.getId())});
        db.close();
    }

    public void deleteNote(int id) {
        SQLiteDatabase db = this.getWritableDatabase();
        db.delete(TABLE_NOTES, COLUMN_ID + " = ?", new String[]{String.valueOf(id)});
        db.close();
    }

    public List<Note> getAllNotes() {
        List<Note> notes = new ArrayList<>();
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor cursor = db.query(TABLE_NOTES, null, null, null, null, null, null);

        if (cursor.moveToFirst()) {
            do {
                Note note = new Note(
                        cursor.getString(cursor.getColumnIndexOrThrow(COLUMN_NAME)),
                        cursor.getString(cursor.getColumnIndexOrThrow(COLUMN_DESCRIPTION)),
                        new Date(cursor.getLong(cursor.getColumnIndexOrThrow(COLUMN_DATE_CREATION))),
                        cursor.getInt(cursor.getColumnIndexOrThrow(COLUMN_ID)),
                        new Date(cursor.getLong(cursor.getColumnIndexOrThrow(COLUMN_DATE_APPOINTMENT))),
                        NotesImportance.valueOf(cursor.getString(cursor.getColumnIndexOrThrow(COLUMN_IMPORTANCE))),
                        cursor.getString(cursor.getColumnIndexOrThrow(COLUMN_IMAGE_URI))
                );
                notes.add(note);
            } while (cursor.moveToNext());
        }
        cursor.close();
        db.close();
        return notes;
    }

    public Note getNoteById(int id) {
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor cursor = db.query(TABLE_NOTES, null, COLUMN_ID + " = ?", new String[]{String.valueOf(id)}, null, null, null);

        if (cursor != null && cursor.moveToFirst()) {
            Note note = new Note(
                    cursor.getString(cursor.getColumnIndexOrThrow(COLUMN_NAME)),
                    cursor.getString(cursor.getColumnIndexOrThrow(COLUMN_DESCRIPTION)),
                    new Date(cursor.getLong(cursor.getColumnIndexOrThrow(COLUMN_DATE_CREATION))),
                    cursor.getInt(cursor.getColumnIndexOrThrow(COLUMN_ID)),
                    new Date(cursor.getLong(cursor.getColumnIndexOrThrow(COLUMN_DATE_APPOINTMENT))),
                    NotesImportance.valueOf(cursor.getString(cursor.getColumnIndexOrThrow(COLUMN_IMPORTANCE))),
                    cursor.getString(cursor.getColumnIndexOrThrow(COLUMN_IMAGE_URI))
            );
            cursor.close();
            db.close();
            return note;
        }
        return null;
    }
}
