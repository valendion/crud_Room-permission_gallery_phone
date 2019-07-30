package com.example.kontak_app;

import android.Manifest;
import android.app.Activity;
import android.arch.persistence.room.Room;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.net.Uri;
import android.support.annotation.NonNull;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import android.support.v7.widget.RecyclerView;
import android.text.TextUtils;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Filter;
import android.widget.Filterable;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.SearchView;
import android.widget.TextView;
import android.widget.Toast;

import com.bumptech.glide.Glide;

import java.util.ArrayList;
import java.util.List;

public class Adapter extends RecyclerView.Adapter<Adapter.ViewHolder> implements ActivityCompat.OnRequestPermissionsResultCallback {

    ArrayList<Kontak> daftar_kontak;
    ArrayList<Kontak> daftar_kontak_Full;
    Context context;
    Appdatabase db;
    Integer REQUEST_CALL = 0;
    TextView noHp;


    public Adapter(ArrayList<Kontak> daftar_kontak, Context context) {
        this.daftar_kontak = daftar_kontak;
        this.context = context;


//        daftar_kontak_Full = new ArrayList<>(daftar_kontak);
//        tv_noHp = daftar_kontak.get(i).nohpKontak;
        db = Room.databaseBuilder(context.getApplicationContext(),
                Appdatabase.class, "kontakdb").allowMainThreadQueries().build();
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {

        View view = LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.content_view, viewGroup, false);
        // mereset ukuran view, margin, padding, dan parameter layout lainnya

        ViewHolder viewHolder = new ViewHolder(view);

        return viewHolder;

    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder viewHolder, final int i) {
        String namadpn = daftar_kontak.get(i).getNamadpnKontak();
        String namablakangKontak = daftar_kontak.get(i).getNamablakangKontak();
       final String noHp = daftar_kontak.get(i).getNohpKontak();
        String alamat = daftar_kontak.get(i).getAlamatKontak();




        if (!TextUtils.isEmpty(daftar_kontak.get(i).getGambarKontak())){
            Log.d("FileUri",daftar_kontak.get(i).getGambarKontak());
            Uri uri = Uri.parse(daftar_kontak.get(i).getGambarKontak());
//            viewHolder.imageView.setImageURI(uri);
            Glide.with(context)
                    .load(daftar_kontak.get(i).getGambarKontak())
                    .override(400,100)
                    .error(R.drawable.user)
                    .into(viewHolder.gambarlist);

        }

        viewHolder.ll_detail.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Kontak kontak = db.kontakDAO().selectKontakDetail(daftar_kontak.get(i).getKontakid());
                context.startActivity(kontak_detail_activity.getActIntent((Activity) context).putExtra("data", kontak));

            }
        });

        viewHolder.gambartelpon.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                makephone(i);
            }
        });

        viewHolder.tv_nama.setText(namadpn+" "+namablakangKontak);
        viewHolder.tv_noHp.setText(noHp);



    }


    @Override
    public int getItemCount() {
        return  daftar_kontak.size();
    }


    class ViewHolder extends RecyclerView.ViewHolder{
       public LinearLayout ll_detail;
       public TextView tv_namadpn,tv_namablakang,tv_noHp,tv_nama;
       public ImageView gambarlist,gambartelpon;

        ViewHolder(View view){
            super(view);

            ll_detail = view.findViewById(R.id.ll_detail);
            tv_namadpn = view.findViewById(R.id.text_input_namadpn);
            tv_namablakang = view.findViewById(R.id.text_input_namablakang);
            gambarlist = view.findViewById(R.id.iv_gambarnya);
            tv_noHp = view.findViewById(R.id.tv_no_hp);
            tv_nama = view.findViewById(R.id.tv_nama);
            gambartelpon = view.findViewById(R.id.iv_telepon);


        }


    }


    @Override
    public void onRequestPermissionsResult(int i, @NonNull String[] strings, @NonNull int[] ints) {
        if (i == REQUEST_CALL){
            if (ints.length > 0 && ints[0] == PackageManager.PERMISSION_GRANTED){
                makephone(i);
            }else {
                Toast.makeText(context, "Permission ditolak", Toast.LENGTH_SHORT).show();
            }
        }
    }

    private void makephone(int i) {
        String noHp = daftar_kontak.get(i).getNohpKontak();
        if (noHp.length()>0){
            if (ContextCompat.checkSelfPermission(context, Manifest.permission.CALL_PHONE)!= PackageManager.PERMISSION_GRANTED){
                ActivityCompat.requestPermissions((Activity) context, new String[]{Manifest.permission.CALL_PHONE}, REQUEST_CALL);
            }else {
                String dial = "tel:"+noHp;
                context.startActivity(new Intent(Intent.ACTION_CALL, Uri.parse(dial)));
            }


        }else{
            Toast.makeText(context, "Masukkan nomor anda", Toast.LENGTH_SHORT).show();

        }
    }


    public  void updatelist(ArrayList<Kontak> newlist){
        daftar_kontak = new ArrayList<>();
        daftar_kontak.addAll(newlist);
        notifyDataSetChanged();
    }
}
