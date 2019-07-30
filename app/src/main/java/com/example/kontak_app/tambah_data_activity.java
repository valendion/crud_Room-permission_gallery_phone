package com.example.kontak_app;

import android.Manifest;
import android.app.Activity;
import android.arch.persistence.room.Room;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.AsyncTask;
import android.os.Build;
import android.os.Environment;
import android.provider.MediaStore;
import android.support.annotation.Nullable;
import android.support.design.widget.TextInputLayout;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Toast;

import java.io.File;


public class tambah_data_activity extends AppCompatActivity {
    private Toolbar toolbar;
    private Appdatabase db;
    private TextInputLayout et_namadpn,et_namablakang,et_nohp,et_alamat;
    private ImageView iv_gambar;
    private Button btn_simpan,btn_kembali,btn_gambar;
    Integer SELECT_FILE = 0;
    Uri selectImage;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_tambah_data);


        db = Room.databaseBuilder(getApplicationContext(),
                Appdatabase.class, "kontakdb").build();
        toolbar = findViewById(R.id.tb_toolbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setTitle("Tambah Data");
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);




        et_namadpn = findViewById(R.id.text_input_namadpn);
        et_namablakang = findViewById(R.id.text_input_namablakang);
        et_alamat = findViewById(R.id.text_input_alamat);
        et_nohp = findViewById(R.id.text_input_nohp);
        iv_gambar = findViewById(R.id.iv_user);
        btn_kembali = findViewById(R.id.btn_kembali);
        btn_simpan = findViewById(R.id.btn_simpan);
        btn_gambar = findViewById(R.id.btn_gambar);

//        btn_simpan.setEnabled(false);
       final Kontak kontak = (Kontak) getIntent().getSerializableExtra("data");
        if (kontak != null){
            btn_simpan.setEnabled(true);
            et_namadpn.getEditText().setText(kontak.getNamadpnKontak());
            et_namablakang.getEditText().setText(kontak.getNamablakangKontak());
            et_nohp.getEditText().setText(kontak.getNohpKontak());
            et_alamat.getEditText().setText(kontak.getAlamatKontak());
            iv_gambar.setImageURI(selectImage);


            btn_simpan.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    kontak.setNamadpnKontak(et_namadpn.getEditText().getText().toString());
                    kontak.setNamablakangKontak(et_namablakang.getEditText().getText().toString());
                    kontak.setNohpKontak(et_nohp.getEditText().getText().toString());
                    kontak.setAlamatKontak(et_alamat.getEditText().getText().toString());
                    kontak.setGambarKontak(selectImage.toString());
                    updateKontak(kontak);

                }
            });

        }else {

            if (iv_gambar != null) {
//                btn_simpan.setEnabled(true);
                btn_simpan.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {


                        if (et_namadpn.getEditText().getText().toString().isEmpty()) {
                            et_namadpn.setError("Nama depan anda masih kosong");
                        } else if (et_namablakang.getEditText().getText().toString().isEmpty()) {
                            et_namablakang.setError("Nama belakang anda masih kosong");
                        } else if (et_nohp.getEditText().getText().toString().isEmpty()) {
                            et_nohp.setError("Nomor ho anda masih kosong");
                        } else if (et_alamat.getEditText().getText().toString().isEmpty()) {
                            et_alamat.setError("Alamat anda masih kosong");
                        } else if (iv_gambar == null) {
                            Toast.makeText(tambah_data_activity.this, "Input gambar anda", Toast.LENGTH_SHORT).show();
                        } else {
                            if (selectImage != null && !selectImage.equals(Uri.EMPTY)){
                                Kontak kontak = new Kontak();
                                kontak.setNamadpnKontak(et_namadpn.getEditText().getText().toString());
                                kontak.setNamablakangKontak(et_namablakang.getEditText().getText().toString());
                                kontak.setNohpKontak(et_nohp.getEditText().getText().toString());
                                kontak.setAlamatKontak(et_alamat.getEditText().getText().toString());
                                kontak.setGambarKontak(selectImage.toString());

                                insertData(kontak);

                                Intent intent = new Intent(tambah_data_activity.this, daftar_teman_activity.class);
                                startActivity(intent);

                            }else {

                                Toast.makeText(tambah_data_activity.this, "tidak ada gambar", Toast.LENGTH_SHORT).show();
                            }



                        }
                    }
                });

            }



        }






        btn_kembali.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(tambah_data_activity.this, daftar_teman_activity.class);
                startActivity(intent);
            }
        });


        btn_gambar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
//                Log.i("dion","terklik ji");
                if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
                    if (checkSelfPermission(Manifest.permission.READ_EXTERNAL_STORAGE)
                            == PackageManager.PERMISSION_DENIED) {
                        //permision not granted, request it
                        String[] permissions = {Manifest.permission.READ_EXTERNAL_STORAGE};
                        //SHOW POP FOR REALTIME PERMISSION
                        requestPermissions(permissions, SELECT_FILE);


                    }else{
                        SelectImage();
                    }
                }







            }
        });



    }



    private void updateKontak(final Kontak kontak) {
        new AsyncTask<Void, Void, Long>() {
            @Override
            protected Long doInBackground(Void... voids) {
                long status = db.kontakDAO().updateKontak(kontak);
                return status;
            }

            @Override
            protected void onPostExecute(Long status) {
                Toast.makeText(tambah_data_activity.this, "status row " + status, Toast.LENGTH_SHORT).show();
            }
        }.execute();
    }

    private void insertData(final Kontak kontak) {
        new AsyncTask<Void, Void, Long>() {


            @Override
            protected Long doInBackground(Void... voids) {
                long status = db.kontakDAO().insertKkontak(kontak);
                return status;
            }

            @Override
            protected void onPostExecute(Long status) {
                super.onPostExecute(status);
                Toast.makeText(tambah_data_activity.this, "berhasil " + status, Toast.LENGTH_SHORT).show();
            }
        }.execute();


    }

    private void SelectImage() {
        Intent intent = new Intent(Intent.ACTION_PICK, MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
        intent.setType("image/*");
        startActivityForResult(intent.createChooser(intent, "Select FIle"), SELECT_FILE);

    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if (resultCode == Activity.RESULT_OK) {

         if (requestCode == SELECT_FILE) {
                getGalleryPath();
                Log.i("FileUri",data.getDataString());
                selectImage = data.getData();

                iv_gambar.setImageURI(selectImage);




            }else {
             Toast.makeText(this, "Akses di tolak", Toast.LENGTH_SHORT).show();
         }

        }

    }

    String getGalleryPath() {
        File folder = Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_DCIM);

        if (!folder.exists()) {
            folder.mkdir();
        }

        return folder.getAbsolutePath();
    }

    @Override
    public void onBackPressed() {
        Intent intent = new Intent(tambah_data_activity.this,daftar_teman_activity.class);
        startActivity(intent);
    }
}
