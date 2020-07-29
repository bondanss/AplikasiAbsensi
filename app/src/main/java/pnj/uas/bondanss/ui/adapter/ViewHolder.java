package pnj.uas.bondanss.ui.adapter;

import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.net.Uri;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.recyclerview.widget.RecyclerView;

import com.squareup.picasso.Picasso;

import java.text.NumberFormat;
import java.util.Locale;

import pnj.uas.bondanss.R;

public class ViewHolder extends RecyclerView.ViewHolder {

    View mview;

    public ViewHolder(@NonNull View itemView){

        super(itemView);

        mview = itemView;

        itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                mClickListener.onItemClick(view, getAdapterPosition());

            }
        });

        itemView.setOnLongClickListener(new View.OnLongClickListener() {
            @Override
            public boolean onLongClick(View view) {

                mClickListener.onItemLongClick(view,getAdapterPosition());
                return false;
            }
        });

    }

    public void setDetails(Context ctx, String email, String tanggal, String waktu, String lokasi, final String lat, final String lng, String status, String gambar){

        TextView mName = mview.findViewById(R.id.TVWaktu);
        TextView mCategory = mview.findViewById(R.id.TVLokasi);
        TextView mDiff = mview.findViewById(R.id.TVbutton);
        ImageView mImageView = mview.findViewById(R.id.IVfotoabsen);

        mName.setText(tanggal+" ("+ waktu + ")");
        mCategory.setText(lokasi);
        Picasso.get().load(gambar).into(mImageView);

        mDiff.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

//                 Masukan Class yang di coment tapi musti di send context nya as argument
                        Uri gmmIntentUri = Uri.parse("google.navigation:q=" + lat + "," + lng);
                        Intent mapIntent = new Intent(Intent.ACTION_VIEW, gmmIntentUri);
                        mapIntent.setPackage("com.google.android.apps.maps");

            }
        });

    }

    public void gotoDetail(Context ctx, String category, String difficulty, String directions, String image, String ingredients,  String name, String recipe_id, String servings, String time)
    {

    }

//    private void ShowNavigation(){
//        final AlertDialog.Builder builder = new AlertDialog.Builder();
//        builder.setMessage("Buka Google Maps?")
//                .setCancelable(true)
//                .setPositiveButton("Iya", new DialogInterface.OnClickListener() {
//                    public void onClick(@SuppressWarnings("unused") final DialogInterface dialog, @SuppressWarnings("unused") final int id) {
//
//                        Uri gmmIntentUri = Uri.parse("google.navigation:q=" + latid + "," + longid);
//                        Intent mapIntent = new Intent(Intent.ACTION_VIEW, gmmIntentUri);
//                        mapIntent.setPackage("com.google.android.apps.maps");
//
//                        try{
//                            if (mapIntent.resolveActivity(LihatLokasi.this.getPackageManager()) != null) {
//                                startActivity(mapIntent);
//                            }
//                        }catch (NullPointerException e){
////                                Log.e(TAG, "onClick: NullPointerException: Couldn't open map." + e.getMessage() );
//                            Toast.makeText(LihatLokasi.this, "Couldn't open map", Toast.LENGTH_SHORT).show();
//                        }
//
//                    }
//                })
//                .setNegativeButton("Tidak", new DialogInterface.OnClickListener() {
//                    public void onClick(final DialogInterface dialog, @SuppressWarnings("unused") final int id) {
//                        dialog.cancel();
//                    }
//                });
//        final AlertDialog alert = builder.create();
//        alert.show();
//    }

    private ViewHolder.ClickListener mClickListener;

    public interface ClickListener{

        void onItemClick(View view, int position);
        void onItemLongClick(View view, int position);
    }

    public void setOnClickListener(ViewHolder.ClickListener clickListener)
    {
        mClickListener = clickListener;
    }




}

