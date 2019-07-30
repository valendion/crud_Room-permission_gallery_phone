package com.example.kontak_app;

import android.app.AlertDialog;
import android.arch.persistence.room.Room;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Build;
import android.support.annotation.RequiresApi;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.support.v7.widget.SearchView;
import android.view.Menu;
import android.view.MenuItem;
import android.view.inputmethod.EditorInfo;
//import android.widget.SearchView;
import android.widget.Toast;
import android.app.Activity;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class daftar_teman_activity extends AppCompatActivity implements SearchView.OnQueryTextListener{
    private Toolbar toolbar;
    private Appdatabase db;
    private RecyclerView recyclerView;
//    private RecyclerView.Adapter adapter;
    private RecyclerView.LayoutManager layoutManager;
    private ArrayList<Kontak> daftarKontak;
    Adapter adapter;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_daftar_teman);
        toolbar = findViewById(R.id.tb_toolbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setTitle("Daftar Teman");
        daftarKontak = new ArrayList<>();

        db = Room.databaseBuilder(getApplicationContext(),
                Appdatabase.class, "kontakdb").allowMainThreadQueries().build();

        toolbar.setOnMenuItemClickListener(new Toolbar.OnMenuItemClickListener() {
            @Override
            public boolean onMenuItemClick(MenuItem menuItem) {
                int id = menuItem.getItemId();
                if (id == R.id.ic_add) {
                    Intent intent = new Intent(daftar_teman_activity.this, tambah_data_activity.class);
                    startActivity(intent);
                }


                return true;
            }
        });


        recyclerView = findViewById(R.id.rv_main);
        recyclerView.setHasFixedSize(true);
        layoutManager = new LinearLayoutManager(this);
        recyclerView.setLayoutManager(layoutManager);

        daftarKontak.addAll(Arrays.asList(db.kontakDAO().selectAllKontaks()));

        adapter = new Adapter(daftarKontak, this);
        recyclerView.setAdapter(adapter);

    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {

        getMenuInflater().inflate(R.menu.menu_item, menu);
        MenuItem menuItem = menu.findItem(R.id.ic_search);
//        SearchView searchView = (SearchView) menuItem.getActionView();
        SearchView searchView = (SearchView) menuItem.getActionView();
//        searchView.setOnQueryTextListener((android.support.v7.widget.SearchView.OnQueryTextListener) this);

        searchView.setOnQueryTextListener(this);
        return true;
    }


    @Override
    public boolean onQueryTextSubmit(String query) {
        return false;
    }

    @Override
    public boolean onQueryTextChange(String newText) {

       newText = newText.toLowerCase();
        ArrayList<Kontak> newlist = new ArrayList<>();

        for ( Kontak name : daftarKontak){
            String namadepan = name.getNamadpnKontak().toLowerCase();
            String namabelakang = name.getNamablakangKontak().toLowerCase();

//            if (containsWord(namadepan +" " + namabelakang,newText.split("\\s"))){
//                newlist.add(name);
//            }



            if (namadepan.contains(newText)||namabelakang.contains(newText)){
                newlist.add(name);
            }



        }




        adapter.updatelist(newlist);
        return true;
    }

    private boolean containsWord(String source,String... words){
        boolean isContains = false;
        for (String word : words){
            if(source.contains(word)){
                isContains = true;
            }
        }
        return isContains;
    }

    @Override
    public void onBackPressed() {
        AlertDialog.Builder builder = new AlertDialog.Builder(daftar_teman_activity.this);
        builder.setTitle("PERHATIAN !!!");
        builder.setMessage("Apakah anda ingin keluar ?");
        builder.setCancelable(false);
        builder.setPositiveButton("NO", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                dialog.cancel();
            }
        });

        builder.setNegativeButton("YES", new DialogInterface.OnClickListener() {
            @RequiresApi(api = Build.VERSION_CODES.LOLLIPOP)
            @Override
            public void onClick(DialogInterface dialog, int which) {
                /*finish();
                Intent intent = new Intent(Intent.ACTION_MAIN);
                intent.addCategory(Intent.CATEGORY_HOME);
                startActivity(intent);
*/
                finishAffinity();
                System.exit(0);

            }
        });
        builder.create().show();

    }


}
