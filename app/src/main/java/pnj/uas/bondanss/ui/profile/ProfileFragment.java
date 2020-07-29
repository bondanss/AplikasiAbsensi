package pnj.uas.bondanss.ui.profile;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import pnj.uas.bondanss.MainActivity;
import pnj.uas.bondanss.R;

public class ProfileFragment extends Fragment {

    SharedPreferences sharedPreferences;

    EditText edtEmail, edtNim, edtNama, edtKelas;
    Button actionLogout;

    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {
        View root = inflater.inflate(R.layout.fragment_profile, container, false);
        return root;
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        sharedPreferences = getActivity().getSharedPreferences("user", Context.MODE_PRIVATE);

        edtEmail = view.findViewById(R.id.edtEmail);
        edtNim = view.findViewById(R.id.edtNim);
        edtNama = view.findViewById(R.id.edtNama);
        edtKelas = view.findViewById(R.id.edtKelas);
        actionLogout = view.findViewById(R.id.actionLogout);

        edtEmail.setText(sharedPreferences.getString("email", ""));
        edtNim.setText(sharedPreferences.getString("nim", ""));
        edtNama.setText(sharedPreferences.getString("nama", ""));
        edtKelas.setText(sharedPreferences.getString("kelas", ""));

        actionLogout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                sharedPreferences.edit().clear().commit();
                Intent intent = new Intent(getActivity(), MainActivity.class);
                startActivity(intent);
                getActivity().finish();
            }
        });

    }
}
