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
import pnj.uas.bondanss.model.ModelPegawai;

public class AdapterPegawai extends ArrayAdapter<ModelPegawai> {
    Context context;
    int resource;


    public AdapterPegawai(@androidx.annotation.NonNull Context context, int resource) {
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
            holder.txtNip = convertView.findViewById(R.id.txtNip);
            holder.txtNama = convertView.findViewById(R.id.txtNama);

            convertView.setTag(holder);
        }else {

            holder=(Holder) convertView.getTag();
        }

        holder.txtNip.setText(getItem(position).getNip());
        holder.txtNama.setText(getItem(position).getNama());

        return convertView;

    }

    public void setNotifyOnChange() {
    }

    class Holder {
        ImageView avatar;
        TextView txtNip,txtNama;
    }
}
