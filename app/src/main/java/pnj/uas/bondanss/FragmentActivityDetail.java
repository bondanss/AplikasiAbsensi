package pnj.uas.bondanss;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.FragmentTransaction;

import android.os.Bundle;

import pnj.uas.bondanss.fragment.DetailPegawai;

public class FragmentActivityDetail extends AppCompatActivity {
    DetailPegawai detailPegawai;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_fragment_detail);

        Bundle extras = getIntent().getExtras();
        detailPegawai = new DetailPegawai();

        if (extras!=null) {
            String nip = extras.getString("nip","");
            String nama = extras.getString("nama","");
            String email = extras.getString("email","");
            String alamat = extras.getString("alamat","");
            String telepon = extras.getString("telepon","");

            Bundle data = new Bundle();
            data.putString("nip",nip);
            data.putString("nama",nama);
            data.putString("email",email);
            data.putString("alamat",alamat);
            data.putString("telepon",telepon);
            detailPegawai.setArguments(data);
        }
        FragmentTransaction ft = getSupportFragmentManager().beginTransaction();
        ft.replace(R.id.containerLayout , detailPegawai);
        ft.commit();
    }
}
