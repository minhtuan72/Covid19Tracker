package com.example.appcovid;

import android.content.Context;
import android.os.Bundle;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.Spinner;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.DividerItemDecoration;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.appcovid.adapter.NoteAdapter;
import com.example.appcovid.ui.notifications.Note;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.List;


public class AddActivity extends AppCompatActivity implements View.OnClickListener  {
    EditText tieu_de, noi_dung, ghi_chu;
    Context context = AddActivity.this;
    private Button btAdd,btCancel;
//    private RecyclerView recyclerView;
//    private NoteAdapter noteAdapter;
//    private List<Note> noteList;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add);
        initView();
        //getListNote();
        btCancel.setOnClickListener(this);
        btAdd.setOnClickListener(this);

        tieu_de = findViewById(R.id.tvTieude);
        noi_dung = findViewById(R.id.tvNoidung);
        ghi_chu = findViewById(R.id.tvLuuy);

//        recyclerView = findViewById(R.id.rcView);
////        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(this);
////        recyclerView.setLayoutManager(linearLayoutManager);
//
//        noteList = new ArrayList<>();
//        noteAdapter = new NoteAdapter();
//        recyclerView.setAdapter(noteAdapter);



    }


    private void initView() {

        btAdd=findViewById(R.id.btAdd);
        btCancel=findViewById(R.id.btCancel);


    }

    @Override
    public void onClick(View view) {

        if(view==btCancel){
            finish();
        }
        if(view==btAdd){
            String tieude = tieu_de.getText().toString().trim();
            String noidung = noi_dung.getText().toString().trim();
            String ghichu = ghi_chu.getText().toString().trim();

            Note note = new Note(tieude, noidung, ghichu);

            onClickAdd(note);

        }
        }

    private void onClickAdd(Note note) {
        FirebaseDatabase database = FirebaseDatabase.getInstance();
        DatabaseReference myRef = database.getReference("list_notes" );

        myRef.child(note.getTieude()).setValue(note);
        myRef.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                Toast.makeText(context, "Thêm thành công", Toast.LENGTH_SHORT).show();
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {
                Toast.makeText(context, "Thêm Thất Bại", Toast.LENGTH_SHORT).show();
            }
        });
    }

//    private void getListNote(){
//        FirebaseDatabase database = FirebaseDatabase.getInstance();
//        DatabaseReference myRef = database.getReference("list_notes" );
//
//        myRef.addValueEventListener(new ValueEventListener() {
//            @Override
//            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
//               for (DataSnapshot snapshot : dataSnapshot.getChildren()){
//                   Note note = snapshot.getValue(Note.class);
//                   noteList.add(note);
//               }
//               noteAdapter.notifyDataSetChanged();
//            }
//
//            @Override
//            public void onCancelled(@NonNull DatabaseError databaseError) {
//                Toast.makeText(context, "Thất Bại", Toast.LENGTH_SHORT).show();
//            }
//        });
   // }
}