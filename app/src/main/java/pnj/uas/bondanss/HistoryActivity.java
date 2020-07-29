package pnj.uas.bondanss;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.database.Cursor;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.firebase.ui.auth.data.model.User;
import com.firebase.ui.database.FirebaseRecyclerAdapter;
import com.firebase.ui.database.FirebaseRecyclerOptions;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.ChildEventListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.HashMap;

import pnj.uas.bondanss.adapter.AdapterAbsensi;
import pnj.uas.bondanss.adapter.AdapterPegawai;
import pnj.uas.bondanss.db.DatabasePegawai;
import pnj.uas.bondanss.model.ModelPegawai;
import pnj.uas.bondanss.obj.AbsensiObj;
import pnj.uas.bondanss.ui.adapter.ViewHolder;

public class HistoryActivity extends AppCompatActivity {

    //Deklarasi Variable
    private ImageView avatar;
    private TextView txtEmail, txtWaktu, txtTanggal, txtLokasi, txtLat, txtLng;
    private FirebaseAuth auth;
    private String GetUserID;

    LinearLayoutManager mLinearLayoutManager;
    RecyclerView mRecycleView;
    FirebaseDatabase mFirebaseDatabase;
    DatabaseReference mDatabaseReference;

    FirebaseRecyclerAdapter<AbsensiObj, ViewHolder> firebaseRecyclerAdapter;
    FirebaseRecyclerOptions<AbsensiObj> options;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_history);
        getSupportActionBar().setTitle("Data Absensi");

        txtEmail = findViewById(R.id.txtEmail);
        auth = FirebaseAuth.getInstance();

        //Mendapatkan User ID dari akun yang terautentikasi
        FirebaseUser user = auth.getCurrentUser();
        GetUserID = user.getUid();

        mLinearLayoutManager = new LinearLayoutManager(this);
        mLinearLayoutManager.setReverseLayout(true);
        mLinearLayoutManager.setStackFromEnd(true);

        mRecycleView = findViewById(R.id.RVV_recipe);

        mFirebaseDatabase = mFirebaseDatabase.getInstance();
        mDatabaseReference = mFirebaseDatabase.getReference().child("absensi");

        showData();


    }

    private void showData(){

        options = new FirebaseRecyclerOptions.Builder<AbsensiObj>().setQuery(mDatabaseReference, AbsensiObj.class).build();

        firebaseRecyclerAdapter = new FirebaseRecyclerAdapter<AbsensiObj, ViewHolder>(options) {
            @Override
            protected void onBindViewHolder(@NonNull ViewHolder holder, int position, @NonNull AbsensiObj model) {

                holder.setDetails(getApplicationContext(), model.getEmail(), model.getTanggal(), model.getWaktu(), model.getLokasi(), model.getLat(), model.getLng(), model.getStatus(), model.getGambar());

            }

            @NonNull
            @Override
            public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {

                View itemView = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_list_history, parent, false );

                ViewHolder viewHolder = new ViewHolder(itemView);

                viewHolder.setOnClickListener(new ViewHolder.ClickListener() {
                    @Override
                    public void onItemClick(View view, int position) {
                        Toast.makeText(HistoryActivity.this, "Helo", Toast.LENGTH_SHORT).show();
                    }

                    @Override
                    public void onItemLongClick(View view, int position) {
                        Toast.makeText(HistoryActivity.this, "Hello juga", Toast.LENGTH_SHORT).show();

                    }
                });

                return viewHolder;
            }
        };

        mRecycleView.setLayoutManager(mLinearLayoutManager);
        firebaseRecyclerAdapter.startListening();
        mRecycleView.setAdapter(firebaseRecyclerAdapter);
    }

    protected void onStart()
    {
        super.onStart();

        if (firebaseRecyclerAdapter != null)
        {
            firebaseRecyclerAdapter.startListening();
        }
    }

//    void buatData() {
//        ArrayList<AbsensiObj> data = new ArrayList<>();
//
//        database = new DatabasePegawai(this).getWritableDatabase();
//        Cursor cursor = database.rawQuery("SELECT * FROM tb_pegawai", null);
//        if (cursor.moveToFirst()){
//            do {
//                AbsensiObj listAbsensi = new AbsensiObj();
//                listAbsensi.setEmail(""+cursor.getInt(0));
//                listAbsensi.setTanggal(cursor.getString(1));
//                listAbsensi.setWaktu(cursor.getString(2));
//                listAbsensi.setLokasi(cursor.getString(3));
//                listAbsensi.setLat(cursor.getString(4));
//                listAbsensi.setLng(cursor.getString(5));
//                data.add(listAbsensi);
//            }
//            while (cursor.moveToNext());
//
//        }
//        database.close();
//        adapterAbsensi.addAll(data);
//        adapterAbsensi.setNotifyOnChange();
//    }

    }
