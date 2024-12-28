package nure.bielik.yehor.lab_task5;

import android.net.Uri;
import android.view.LayoutInflater;
import android.view.MenuInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public class Adapter extends RecyclerView.Adapter<Adapter.ViewHolder> {
    private List<Note> notes = new ArrayList<>();
    private Note selectedNote;
    private View.OnClickListener noteClickListener;

    public void setNoteClickListener(View.OnClickListener noteClickListener) {
        this.noteClickListener = noteClickListener;
    }

    public Note getSelectedNote() {
        return selectedNote;
    }


    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.note_item, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        Note item = notes.get(position);
        holder.bind(item);
        holder.itemView.setOnLongClickListener(view -> {
            selectedNote = item;
            return false;
        });
        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                selectedNote = item;
                noteClickListener.onClick(view);
            }
        });
    }

    @Override
    public int getItemCount() {
        return notes.size();
    }

    public static class ViewHolder extends RecyclerView.ViewHolder {

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            itemView.setOnCreateContextMenuListener((contextMenu, view, contextMenuInfo) -> {
                MenuInflater menuInflater = new MenuInflater(view.getContext());
                menuInflater.inflate(R.menu.context_menu, contextMenu);
            });
        }



        public void bind(Note note) {
            TextView name = itemView.findViewById(R.id.name);
            TextView description = itemView.findViewById(R.id.description);
            TextView dateOfCreation = itemView.findViewById(R.id.dateOfCreation);
            ImageView noteImportanceImage = itemView.findViewById(R.id.noteImportanceImage);
            ImageView imageView = itemView.findViewById(R.id.imageView);

            name.setText(note.getName());
            description.setText(note.getDescription());
            updateImportanceImage(note, noteImportanceImage);
            if (note.getImageUri() != null) {
                imageView.setImageURI(Uri.parse(note.getImageUri()));
            }

            if (note.getDescription().isBlank()) {
                description.setText(R.string.no_text);
            } else if (note.getName().isBlank()) {
                name.setText(note.getDescription());
                description.setVisibility(View.GONE);
            }
            String pattern = "d MMMM yyyy HH:mm";
            if (isSameYear(new Date(), note.getDateOfCreation())) {
                pattern = "d MMMM HH:mm";
            }

            dateOfCreation.setText(new SimpleDateFormat(pattern).format(note.getDateOfCreation()));
        }

        private boolean isSameYear(Date date1, Date date2) {
            SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy");
            return simpleDateFormat.format(date1).equals(simpleDateFormat.format(date2));
        }
    }

    public void setNotes(List<Note> notes) {
        this.notes.clear();
        this.notes.addAll(notes);
        notifyDataSetChanged();
    }


    private static void updateImportanceImage(Note note, ImageView noteImportanceImage) {
        if(note.getImportance().equals(NotesImportance.High)) {
            noteImportanceImage.setImageResource(R.drawable.star_high);
        } else if(note.getImportance().equals(NotesImportance.Low)) {
            noteImportanceImage.setImageResource(R.drawable.star_low);
        } else {
            noteImportanceImage.setImageResource(R.drawable.star_medium);
        }
    }
}

