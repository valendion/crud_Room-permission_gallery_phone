package com.example.kontak_app;

import android.Manifest;
import android.app.Activity;
import android.app.AlertDialog;
import android.arch.persistence.room.Room;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.drawable.Drawable;
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
import java.util.ArrayList;

public class kontak_detail_activity extends AppCompatActivity {

    private Toolbar toolbar;
    private Appdatabase db;
    private TextInputLayout et_namadpn,et_namablakang,et_nohp,et_alamat;

    private ImageView iv_gambar;
    private Button btn_gambar;
    Integer SELECT_FILE = 0;
    Uri selectImage;
    Kontak kontak;
    private ArrayList<Kontak> daftarkontak;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_kontak_detail);
        db = Room.databaseBuilder(getApplicationContext(),
                Appdatabase.class, "kontakdb").build();
        daftarkontak = new ArrayList<>();
        toolbar = findViewById(R.id.tb_toolbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setTitle("Detail Data");
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);



        et_namadpn = findViewById(R.id.text_input_namadpn);
        et_namablakang = findViewById(R.id.text_input_namablakang);
        et_alamat = findViewById(R.id.text_input_alamat);
        et_nohp = findViewById(R.id.text_input_nohp);
        iv_gambar = findViewById(R.id.iv_user);
        btn_gambar = findViewById(R.id.btn_gambar);

        et_namadpn.setEnabled(false);
        et_namablakang.setEnabled(false);
        et_alamat.setEnabled(false);
        et_nohp.setEnabled(false);
        btn_gambar.setEnabled(false);

        kontak = (Kontak) getIntent().getSerializableExtra("data");
        if (kontak != null) {
            selectImage = Uri.parse(kontak.getGambarKontak());
            et_namadpn.getEditText().setText(kontak.getNamadpnKontak());
            et_namablakang.getEditText().setText(kontak.getNamablakangKontak());
            et_nohp.getEditText().setText(kontak.getNohpKontak());
            et_alamat.getEditText().setText(kontak.getAlamatKontak());
            iv_gambar.setImageURI(selectImage);

            toolbar.setOnMenuItemClickListener(new Toolbar.OnMenuItemClickListener() {
                @Override
                public boolean onMenuItemClick(MenuItem menuItem) {
                    int id = menuItem.getItemId();
                    if (id == R.id.ic_edit) {
                        Drawable drawable = getResources().getDrawable(R.drawable.save);
                        menuItem.setIcon(R.drawable.save);
                        et_namadpn.setEnabled(true);
                        et_namablakang.setEnabled(true);
                        et_alamat.setEnabled(true);
                        et_nohp.setEnabled(true);
                        btn_gambar.setEnabled(true);

                        btn_gambar.setOnClickListener(new View.OnClickListener() {
                            @Override
                            public void onClick(View v) {
                                Log.i("dion","terklik ji");
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

                        toolbar.setOnMenuItemClickListener(new Toolbar.OnMenuItemClickListener() {
                            @Override
                            public boolean onMenuItemClick(MenuItem menuItem) {
                                int id = menuItem.getItemId();
                                if (id == R.id.ic_edit) {
                                    if (et_namadpn.getEditText().getText().toString().isEmpty()) {
                                        et_namadpn.setError("Nama depan anda masih kosong");
                                    } else if (et_namablakang.getEditText().getText().toString().isEmpty()) {
                                        et_namablakang.setError("Nama belakang anda masih kosong");
                                    } else if (et_nohp.getEditText().getText().toString().isEmpty()) {
                                        et_nohp.setError("Nomor anda masih kosong");
                                    } else if (et_alamat.getEditText().getText().toString().isEmpty()) {
                                        et_alamat.setError("Alamat anda masih kosong");
                                    } else {
                                        kontak.setNamadpnKontak(et_namadpn.getEditText().getText().toString());
                                        kontak.setNamablakangKontak(et_namablakang.getEditText().getText().toString());
                                        kontak.setNohpKontak(et_nohp.getEditText().getText().toString());
                                        kontak.setAlamatKontak(et_alamat.getEditText().getText().toString());
                                        kontak.setGambarKontak(selectImage.toString());
                                        updateKontak(kontak);

                                        Intent intent = new Intent(kontak_detail_activity.this, daftar_teman_activity.class);
                                        startActivity(intent);


                                    }


                                }else if (id == R.id.ic_delete) {
                                    if (et_namadpn.getEditText().getText().toString().isEmpty()) {
                                        et_namadpn.setError("Nama depan anda masih kosong");
                                    } else if (et_namablakang.getEditText().getText().toString().isEmpty()) {
                                        et_namablakang.setError("Nama belakang anda masih kosong");
                                    } else if (et_nohp.getEditText().getText().toString().isEmpty()) {
                                        et_nohp.setError("Nomor anda masih kosong");
                                    } else if (et_alamat.getEditText().getText().toString().isEmpty()) {
                                        et_alamat.setError("Alamat anda masih kosong");
                                    } else {
                                        AlertDialog.Builder builder = new AlertDialog.Builder(kontak_detail_activity.this);
                                        builder.setTitle("PERHATIAN !!!");
                                        builder.setMessage("Apakah data ini ingin dihapus ?");
                                        builder.setCancelable(false);
                                        builder.setPositiveButton("NO", new DialogInterface.OnClickListener() {
                                            @Override
                                            public void onClick(DialogInterface dialog, int which) {
                                                dialog.cancel();
                                            }
                                        });

                                        builder.setNegativeButton("YES", new DialogInterface.OnClickListener() {
                                            @Override
                                            public void onClick(DialogInterface dialog, int which) {
                                                delete();

                                                Intent intent = new Intent(kontak_detail_activity.this, daftar_teman_activity.class);
                                                startActivity(intent);

                                            }
                                        });
                                        builder.create().show();


                                    }
                                }

                                return false;

                            }
                        });


                    } else if (id == R.id.ic_delete) {
                        if (et_namadpn.getEditText().getText().toString().isEmpty()) {
                            et_namadpn.setError("Nama depan anda masih kosong");
                        } else if (et_namablakang.getEditText().getText().toString().isEmpty()) {
                            et_namablakang.setError("Nama belakang anda masih kosong");
                        } else if (et_nohp.getEditText().getText().toString().isEmpty()) {
                            et_nohp.setError("Nomor anda masih kosong");
                        } else if (et_alamat.getEditText().getText().toString().isEmpty()) {
                            et_alamat.setError("Alamat anda masih kosong");
                        } else {
                            AlertDialog.Builder builder = new AlertDialog.Builder(kontak_detail_activity.this);
                            builder.setTitle("PERHATIAN !!!");
                            builder.setMessage("Apakah data ini ingin dihapus ?");
                            builder.setCancelable(false);
                            builder.setPositiveButton("NO", new DialogInterface.OnClickListener() {
                                @Override
                                public void onClick(DialogInterface dialog, int which) {
                                    dialog.cancel();
                                }
                            });

                            builder.setNegativeButton("YES", new DialogInterface.OnClickListener() {
                                @Override
                                public void onClick(DialogInterface dialog, int which) {
                                    delete();

                                    Intent intent = new Intent(kontak_detail_activity.this, daftar_teman_activity.class);
                                    startActivity(intent);

                                }
                            });
                            builder.create().show();


                        }
                    }

                    return false;
                }
            });


        }
    }
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_item_detail, menu);
        return true;
    }

    private void SelectImage() {
        Intent intent = new Intent(Intent.ACTION_PICK, MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
        intent.setType("image/*");
        startActivityForResult(intent.createChooser(intent, "Select FIle"), SELECT_FILE);
    }

    public static Intent getActIntent(Activity activity) {
        return new Intent(activity, kontak_detail_activity.class);
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
                Toast.makeText(kontak_detail_activity.this, "status row " + status, Toast.LENGTH_SHORT).show();
            }
        }.execute();
    }

    public void delete(){
        AsyncTask.execute(new Runnable() {
            @Override
            public void run() {
                AsyncTask.execute(new Runnable() {
                    @Override
                    public void run() {
                        db.kontakDAO().deleteKontak(kontak);
                        daftarkontak.remove(kontak);
                        Intent intent = new Intent(kontak_detail_activity.this, daftar_teman_activity.class);
                        startActivity(intent);
                        runOnUiThread(new Runnable() {
                            @Override
                            public void run() {

                            }
                        });
                    }
                });
            }
        });
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




            } else {
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
        String string_namadpn = et_namadpn.getEditText().getText().toString();
        String string_namablk = et_namablakang.getEditText().getText().toString();
        String string_nohp = et_nohp.getEditText().getText().toString();
        String string_alamat = et_alamat.getEditText().getText().toString();

        if (string_namadpn.equals(kontak.getNamadpnKontak()) && string_namablk.equals(kontak.getNamablakangKontak()) && string_nohp.equals(kontak.getNohpKontak()) && string_alamat.equals(kontak.getAlamatKontak())){
            Intent intent = new Intent(kontak_detail_activity.this, daftar_teman_activity.class);
            startActivity(intent);

        }else if (et_namadpn.getEditText().getText().toString().isEmpty()) {
            et_namadpn.setError("Nama depan anda masih kosong");
        } else if (et_namablakang.getEditText().getText().toString().isEmpty()) {
            et_namablakang.setError("Nama belakang anda masih kosong");
        } else if (et_nohp.getEditText().getText().toString().isEmpty()) {
            et_nohp.setError("Nomor anda masih kosong");
        } else if (et_alamat.getEditText().getText().toString().isEmpty()) {
            et_alamat.setError("Alamat anda masih kosong");
        }else {

            AlertDialog.Builder builder = new AlertDialog.Builder(kontak_detail_activity.this);
            builder.setTitle("PERHATIAN !!!");
            builder.setMessage("Apakah data ini ingin disimpan ?");
            builder.setCancelable(false);
            builder.setPositiveButton("NO", new DialogInterface.OnClickListener() {
                @Override
                public void onClick(DialogInterface dialog, int which) {
                    dialog.cancel();
                }
            });

            builder.setNegativeButton("YES", new DialogInterface.OnClickListener() {
                @Override
                public void onClick(DialogInterface dialog, int which) {
                    kontak.setNamadpnKontak(et_namadpn.getEditText().getText().toString());
                    kontak.setNamablakangKontak(et_namablakang.getEditText().getText().toString());
                    kontak.setNohpKontak(et_nohp.getEditText().getText().toString());
                    kontak.setAlamatKontak(et_alamat.getEditText().getText().toString());
                    kontak.setGambarKontak(selectImage.toString());
                    updateKontak(kontak);

                    Intent intent = new Intent(kontak_detail_activity.this, daftar_teman_activity.class);
                    startActivity(intent);

                }
            });
            builder.create().show();




        }


    }
}
