package nure.bielik.yehor.lab_task4;

import android.app.SearchManager;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.PopupMenu;
import android.widget.SearchView;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity {
    private Adapter adapter = new Adapter();
    private NotesImportance currentImportanceFilter = NotesImportance.High;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        RecyclerView recyclerView = findViewById(R.id.recyclerview);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        recyclerView.setAdapter(adapter);
        registerForContextMenu(recyclerView);
        adapter.setNoteClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                editNote(adapter.getSelectedNote().getId());
            }
        });
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.main_activity_menu, menu);
        SearchManager searchManager = (SearchManager) getSystemService(Context.SEARCH_SERVICE);
        SearchView searchView = (SearchView) menu.findItem(R.id.search).getActionView();
        searchView.setSearchableInfo(searchManager.getSearchableInfo(getComponentName()));
        searchView.setOnSearchClickListener(view -> menu.findItem(R.id.add).setVisible(false));
        searchView.setOnCloseListener(() -> {
            menu.findItem(R.id.add).setVisible(true);
            update();
            return false;
        });
        searchView.setMaxWidth(Integer.MAX_VALUE);
        searchView.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String s) {
                return true;
            }

            @Override
            public boolean onQueryTextChange(String s) {
                List<Note> searchedNotes = NotesManager.searchNotes(s);
                List<Note> filteredNotes = NotesManager.filterNotesByImportance(currentImportanceFilter);
                List<Note> result = intersect(searchedNotes, filteredNotes);
                adapter.setNotes(result);
                return false;
            }
        });
        return super.onCreateOptionsMenu(menu);
    }

    private List<Note> intersect(List<Note> list1, List<Note> list2) {
        List<Note> result = new ArrayList<>(list1);
        result.retainAll(list2);
        return result;
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        if (item.getItemId() == R.id.add) {
            Intent intent = new Intent(this, AddNoteActivity.class);
            startActivity(intent);
        } else if (item.getItemId() == R.id.filter) {
            showFilterPopup(findViewById(R.id.filter));
        }
        return super.onOptionsItemSelected(item);
    }

    @Override
    protected void onStart() {
        super.onStart();
        update();
    }

    @Override
    public boolean onContextItemSelected(@NonNull MenuItem item) {
        Note note = adapter.getSelectedNote();
        if(item.getItemId() == R.id.edit) {
            editNote(note.getId());
        } else if (item.getItemId() == R.id.delete) {
            AlertDialog alertDialog = new AlertDialog.Builder(this).setMessage(R.string.confirmation_of_deletion).setPositiveButton(R.string.yes, (dialogInterface, i) -> {
                NotesManager.deleteNote(note);
                update();
            }).setNegativeButton(R.string.no, null).create();
            alertDialog.show();
        }
        return true;
    }

    private void editNote(int noteId) {
        Intent intent = new Intent(this, AddNoteActivity.class);
        intent.putExtra("noteId", noteId);
        startActivity(intent);
    }

    private void update() {
        adapter.setNotes(NotesManager.getNotes());
    }

    private void showFilterPopup(View anchor) {
        PopupMenu popupMenu = new PopupMenu(this, anchor);
        popupMenu.getMenu().add(Menu.NONE, 0, 0, R.string.high)
                .setIcon(R.drawable.star_low);
        popupMenu.getMenu().add(Menu.NONE, 1, 1, R.string.medium)
                .setIcon(R.drawable.star_medium);
        popupMenu.getMenu().add(Menu.NONE, 2, 2, R.string.low)
                .setIcon(R.drawable.star_high);
        popupMenu.getMenu().add(Menu.NONE, 3, 3, R.string.clear_filters);
        popupMenu.setOnMenuItemClickListener(item -> {
            if (item.getItemId() == 3) {
                currentImportanceFilter = null;
                List<Note> allNotes = NotesManager.getNotes();
                adapter.setNotes(allNotes);
            } else {
                NotesImportance importance = NotesImportance.values()[item.getItemId()];
                currentImportanceFilter = importance;
                List<Note> filteredNotes = NotesManager.filterNotesByImportance(importance);
                adapter.setNotes(filteredNotes);
            }
            return true;
        });
        popupMenu.show();
    }

}