package pnj.uas.bondanss;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;

import java.util.ArrayList;

import pnj.uas.bondanss.adapter.AdapterPegawai;
import pnj.uas.bondanss.db.DatabasePegawai;
import pnj.uas.bondanss.model.ModelPegawai;

public class DataPegawai extends AppCompatActivity {

    ListView listView;
    AdapterPegawai adapterPegawai;
    SQLiteDatabase database;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_data_pegawai);
        listView = findViewById(R.id.listview);
        adapterPegawai = new AdapterPegawai(this, R.layout.item_list_view);
        listView.setAdapter(adapterPegawai);

        listView.setOnItemClickListener(new AdapterView.OnItemClickListener(){
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long L) {
                ModelPegawai data = (ModelPegawai) adapterView.getAdapter().getItem(i);
                Intent intent = new Intent(DataPegawai.this, FragmentActivityDetail.class);
                intent.putExtra("nip", data.getNip());
                intent.putExtra("nama",data.getNama());
                intent.putExtra("email",data.getEmail());
                intent.putExtra("alamat",data.getAlamat());
                intent.putExtra("telepon",data.getTelepon());
                startActivity(intent);

            }
        });

        buatData();
    }

    void buatData() {
        ArrayList<ModelPegawai> data = new ArrayList<>();

        database = new DatabasePegawai(this).getWritableDatabase();
        Cursor cursor = database.rawQuery("SELECT * FROM tb_pegawai", null);
        if (cursor.moveToFirst()){
            do {
                ModelPegawai itemPegawai = new ModelPegawai();
                itemPegawai.setNip(""+cursor.getInt(0));
                itemPegawai.setNama(cursor.getString(1));
                itemPegawai.setEmail(cursor.getString(2));
                itemPegawai.setAlamat(cursor.getString(3));
                itemPegawai.setTelepon(cursor.getString(4));
                data.add(itemPegawai);
            }
            while (cursor.moveToNext());

        }
        database.close();
        adapterPegawai.addAll(data);
        adapterPegawai.setNotifyOnChange();
    }
}
