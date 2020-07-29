package pnj.uas.bondanss.db;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import androidx.annotation.Nullable;

public class DatabasePegawai extends SQLiteOpenHelper {
    public static  String _NAMA_DATABASE = "db_pegawai";
    public  static int _VERSION = 1;
    public static String tb_pegawai =
            "CREATE TABLE tb_pegawai (nip INTEGER PRIMARY KEY, nama TEXT, email TEXT, alamat TEXT, telepon TEXT)";

    public DatabasePegawai(@Nullable Context context) {
        super(context, _NAMA_DATABASE, null, _VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL(tb_pegawai);

    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        if (oldVersion != newVersion) {
            db.execSQL("DROP TABLE tb_pegawai");
            db.execSQL(tb_pegawai);
        }
    }
}
