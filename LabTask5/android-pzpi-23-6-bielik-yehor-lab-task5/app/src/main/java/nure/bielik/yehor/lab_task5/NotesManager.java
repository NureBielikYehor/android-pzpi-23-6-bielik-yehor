package nure.bielik.yehor.lab_task5;

import android.content.Context;
import android.content.SharedPreferences;

import java.util.ArrayList;
import java.util.List;

public class NotesManager {
    private static List<Note> notes = new ArrayList<>();
    private static DBHelper dbHelper;
    private static SharedPreferences sharedPreferences;

    public static void initialize(Context context) {
        dbHelper = new DBHelper(context);
        sharedPreferences = context.getSharedPreferences("settings", Context.MODE_PRIVATE);
    }

    public static void addNote(Note note) {
        dbHelper.addNote(note);
    }

    public static float getFontSize() {
        return sharedPreferences.getFloat("fontSize", 14f);
    }

    public static void setFontSize(float fontSize) {
        SharedPreferences.Editor editor = sharedPreferences.edit();
        editor.putFloat("fontSize", fontSize);
        editor.apply();
    }

    public static void editNote(Note note) {
        Note oldNote = getNote(note.getId());
        if (oldNote != null) {
            dbHelper.updateNote(note);
        }
    }

    public static List<Note> searchNotes(String name) {
        List<Note> results = new ArrayList<>();
        for (Note note : notes) {
            if (note.getName().toLowerCase().contains(name.toLowerCase()) || note.getName().toLowerCase().contains(name.toLowerCase())) {
                results.add(note);
            }
        }
        return results;
    }

    public static List<Note> getNotes() {
        return dbHelper.getAllNotes();
    }

    public static void deleteNote(Note note) {
        dbHelper.deleteNote(note.getId());
    }

    public static Note getNote(int id) {
        return dbHelper.getNoteById(id);
    }

    public static List<Note> filterNotesByImportance(NotesImportance importance) {
        List<Note> results = new ArrayList<>();
        for (Note note : notes) {
            if (note.getImportance() == importance) {
                results.add(note);
            }
        }
        return results;
    }

    public static boolean isDarkThemeEnabled() {
        return sharedPreferences.getBoolean("darkTheme", false);
    }

    public static void setDarkThemeEnabled(boolean enabled) {
        SharedPreferences.Editor editor = sharedPreferences.edit();
        editor.putBoolean("darkTheme", enabled);
        editor.apply();
    }
}
