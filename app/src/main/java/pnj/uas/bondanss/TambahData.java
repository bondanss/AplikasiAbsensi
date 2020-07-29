package pnj.uas.bondanss;

import androidx.appcompat.app.AppCompatActivity;

import android.content.ContentValues;
import android.content.Intent;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import pnj.uas.bondanss.db.DatabasePegawai;

public class TambahData extends AppCompatActivity implements View.OnClickListener {

    EditText edtNip, edtNama, edtEmail, edtAlamat, edtTelepon;
    Button actionSimpan;
    SQLiteDatabase database;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_tambah_data);
        edtNip          = findViewById(R.id.edtNip);
        edtNama         = findViewById(R.id.edtNama);
        edtEmail       = findViewById(R.id.edtEmail);
        edtAlamat       = findViewById(R.id.edtAlamat);
        edtTelepon        = findViewById(R.id.edtTelepon);
        actionSimpan = findViewById(R.id.actionSimpan);

        actionSimpan.setOnClickListener(TambahData.this);
    }

    @Override
    public void onClick(View v) {
        ContentValues contentValues = new ContentValues();
        contentValues.put("Nip", Integer.parseInt(edtNip.getText().toString()));
        contentValues.put("Nama", edtNama.getText().toString());
        contentValues.put("Email", edtEmail.getText().toString());
        contentValues.put("Alamat", edtAlamat.getText().toString());
        contentValues.put("Telepon", edtTelepon.getText().toString());

        database = new DatabasePegawai(this).getWritableDatabase();

        long insert = database.insert("tb_pegawai", null, contentValues);

        Intent intent = new Intent(this, TambahData.class);

        if(insert!=-1) {
            Toast.makeText(this, "Simpan Berhasil", Toast.LENGTH_SHORT).show();
        }
        else {
            Toast.makeText(this, "Simpan Gagal", Toast.LENGTH_SHORT).show();
        }
        finish();
    }
}
