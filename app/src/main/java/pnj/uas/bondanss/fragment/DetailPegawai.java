package pnj.uas.bondanss.fragment;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import pnj.uas.bondanss.R;

public class DetailPegawai extends Fragment {

    TextView txtNip, txtNama, txtEmail, txtAlamat, txtTelepon;
    Button actionUpdate, actionDelete;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.activity_detail_pegawai, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        txtNip          = view.findViewById(R.id.txtNip);
        txtNama         = view.findViewById(R.id.txtNama);
        txtEmail       = view.findViewById(R.id.txtEmail);
        txtAlamat       = view.findViewById(R.id.txtAlamat);
        txtTelepon        = view.findViewById(R.id.txtTelepon);

        actionUpdate = view.findViewById(R.id.actionUpdate);
        actionDelete = view.findViewById(R.id.actionDelete);

        Bundle data = getArguments();
        txtNip.setText(data.getString("nip",""));
        txtNama.setText(data.getString("nama",""));
        txtEmail.setText(data.getString("email",""));
        txtAlamat.setText(data.getString("alamat",""));
        txtTelepon.setText(data.getString("telepon",""));

    }
}
