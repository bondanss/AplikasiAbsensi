package pnj.uas.bondanss.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import pnj.uas.bondanss.R;
import pnj.uas.bondanss.obj.AbsensiObj;

public class AdapterAbsensi extends ArrayAdapter<AbsensiObj> {
    Context context;
    int resource;


    public AdapterAbsensi(@androidx.annotation.NonNull Context context, int resource) {
        super(context, resource);
        this.context = context;
        this.resource = resource;
    }

    @NonNull
    @Override
    public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {
        Holder holder = null;
        if (convertView == null) {
            holder = new Holder();
            convertView = LayoutInflater.from(context).inflate(resource, parent, false);

            holder.avatar = convertView.findViewById(R.id.avatar);
            holder.txtEmail = convertView.findViewById(R.id.txtEmail);

            convertView.setTag(holder);
        }else {

            holder=(Holder) convertView.getTag();
        }

        holder.txtEmail.setText(getItem(position).getEmail());
        holder.txtTanggal.setText(getItem(position).getTanggal());
        holder.txtWaktu.setText(getItem(position).getWaktu());
        holder.txtLokasi.setText(getItem(position).getLokasi());
        holder.txtLat.setText(getItem(position).getLat());
        holder.txtLng.setText(getItem(position).getLng());
        return convertView;
    }

    public void setNotifyOnChange() {
    }

    class Holder {
        ImageView avatar;
        TextView txtEmail,txtTanggal, txtWaktu, txtLokasi, txtLat, txtLng, txtGambar;
    }
}
