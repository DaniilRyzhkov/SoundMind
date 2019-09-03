package com.qualitasvita.soundmind.adapters;

import android.app.Activity;
import android.content.Context;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.qualitasvita.soundmind.CompletedNoteActivity;
import com.qualitasvita.soundmind.MainActivity;
import com.qualitasvita.soundmind.Note;
import com.qualitasvita.soundmind.R;

import java.util.ArrayList;
import java.util.List;

import static com.qualitasvita.soundmind.MainActivity.EXTRA_NOTE_POSITION;
import static com.qualitasvita.soundmind.MainActivity.REQUEST_CODE_COMPLETED_NOTE;

public class MainAdapter extends RecyclerView.Adapter<MainAdapter.ViewHolder> {

    private List<Note> notes = new ArrayList<>();

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull final ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_of_list_main, parent, false);
        final ViewHolder viewHolder = new ViewHolder(view);
        viewHolder.rootLayout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                final int position = viewHolder.getAdapterPosition();
                Intent intent = new Intent(parent.getContext(), CompletedNoteActivity.class);
                Note note = notes.get(position);
                intent.putExtra(Note.class.getSimpleName(), note);
                intent.putExtra(EXTRA_NOTE_POSITION, position);
                ((Activity) parent.getContext()).startActivityForResult(intent, REQUEST_CODE_COMPLETED_NOTE);
            }
        });
        return viewHolder;
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        Note note = notes.get(position);
        holder.noteDate.setText(note.getDate());
        holder.noteTitle.setText(note.getSituationText());
    }

    @Override
    public int getItemCount() {
        return notes.size();
    }

    public void setList(List<Note> list) {
        notes = list;
        notifyDataSetChanged();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {

        private TextView noteDate;
        private TextView noteTitle;
        private LinearLayout rootLayout;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            this.noteDate = itemView.findViewById(R.id.item_date);
            this.noteTitle = itemView.findViewById(R.id.item_title);
            this.rootLayout = itemView.findViewById(R.id.item_root_layout);
        }
    }

}
