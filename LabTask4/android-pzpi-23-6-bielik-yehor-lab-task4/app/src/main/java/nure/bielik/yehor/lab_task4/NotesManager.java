package nure.bielik.yehor.lab_task4;

import java.util.ArrayList;
import java.util.List;

public class NotesManager {
    private static List<Note> notes = new ArrayList<>();

    public static void addNote(Note note) {
        notes.add(note);
    }

    public static void editNote(Note note) {
        Note oldNote = getNote(note.getId());
        int oldNoteIndex = notes.indexOf(oldNote);
        oldNote.setName(note.getName());
        oldNote.setDescription(note.getDescription());
        oldNote.setDateOfAppointment(note.getDateOfAppointment());
        oldNote.setImageUri(note.getImageUri());
        oldNote.setImportance(note.getImportance());
        notes.set(oldNoteIndex, oldNote);
    }

    public static  List<Note> searchNotes(String name) {
        List<Note> results = new ArrayList<>();
        for (Note note : notes) {
            if (note.getName().toLowerCase().contains(name.toLowerCase()) || note.getName().toLowerCase().contains(name.toLowerCase())) {
                results.add(note);
            }
        }
        return results;
    }

    public static List<Note> getNotes() {
        return notes;
    }

    public static void deleteNote(Note note) {
        notes.remove(note);
    }

    public static Note getNote(int id) {
        for (int i = 0; i < notes.size(); i++) {
            if (notes.get(i).getId() == id) {
                return notes.get(i);
            }
        }
        return null;
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
}
