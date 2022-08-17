package com.example.appcovid.adapter;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.RelativeLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.appcovid.R;
import com.example.appcovid.ui.notifications.Note;

import java.util.ArrayList;
import java.util.List;

public class NoteAdapter extends RecyclerView.Adapter<NoteAdapter.NoteViewHolder> {

    private List<Note> noteList;

    public NoteAdapter() {
        this.noteList = new ArrayList<>();
    }

    @NonNull
    @Override
    public NoteViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item, parent,false);
        return new NoteViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull NoteViewHolder holder, int position) {
        Note note = noteList.get(position);

        holder.tvtieude.setText(note.getTieude());
        holder.tvnoidung.setText(note.getNoidung());
        holder.tvghichu.setText(note.getGhichu());

    }

    @Override
    public int getItemCount() {
        if(noteList!=null){
            return noteList.size();
        }
        else {
            return 0;
        }
    }

    public class NoteViewHolder extends RecyclerView.ViewHolder{
        RelativeLayout relativeLayout;
        TextView tvtieude;
        TextView tvnoidung;
        TextView tvghichu;
        public NoteViewHolder(@NonNull View itemView) {
            super(itemView);
            relativeLayout= itemView.findViewById(R.id.cvitem);
            tvtieude = itemView.findViewById(R.id.tvTieudeitem);
            tvnoidung = itemView.findViewById(R.id.tvNoidungitem);
            tvghichu = itemView.findViewById(R.id.tvGhichuitem);
        }
    }
}
