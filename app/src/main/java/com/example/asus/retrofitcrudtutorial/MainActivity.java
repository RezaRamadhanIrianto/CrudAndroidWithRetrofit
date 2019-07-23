package com.example.asus.retrofitcrudtutorial;

import android.app.ProgressDialog;
import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.annotation.RestrictTo;
import android.support.design.widget.BottomNavigationView;
import android.support.v4.app.Fragment;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.example.asus.retrofitcrudtutorial.api.ApiRequestBiodata;
import com.example.asus.retrofitcrudtutorial.api.Retroserver;
import com.example.asus.retrofitcrudtutorial.model.ResponseModel;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class MainActivity extends AppCompatActivity{

    EditText nama, usia, domisili;
    Button btn_save, btn_tampil_data, btn_update, btn_hapus;
    ProgressDialog pd;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        nama = findViewById(R.id.nama);
        usia = findViewById(R.id.usia);
        domisili = findViewById(R.id.domisili);
        btn_save= findViewById(R.id.btn_save);
        btn_tampil_data = findViewById(R.id.btn_pindah);
        btn_update = findViewById(R.id.btn_update);
        btn_hapus = findViewById(R.id.btn_hapus);

        Intent data = getIntent();
        final String idData = data.getStringExtra("id");
        if(idData != null){
            btn_save.setVisibility(View.GONE);
            btn_tampil_data.setVisibility(View.GONE);
            btn_update.setVisibility(View.VISIBLE);
            btn_hapus.setVisibility(View.VISIBLE);

            nama.setText(data.getStringExtra("nama"));
            usia.setText(data.getStringExtra("usia"));
            domisili.setText(data.getStringExtra("domisili"));
        }


        pd = new ProgressDialog(this);

        btn_tampil_data.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent goData = new Intent(MainActivity.this, TampilData.class);
                startActivity(goData);
            }
        });

        btn_hapus.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                pd.setMessage("Deleting ...");
                pd.setCancelable(false);
                pd.show();

                ApiRequestBiodata api = Retroserver.getClient().create(ApiRequestBiodata.class);
                Call<ResponseModel> del = api.hapusData(idData);
                del.enqueue(new Callback<ResponseModel>() {
                    @Override
                    public void onResponse(Call<ResponseModel> call, Response<ResponseModel> response) {
                        Log.d("RETRO", "onResponse: ");
                        Intent getTampil = new Intent(MainActivity.this, TampilData.class);
                        startActivity(getTampil);
                        Toast.makeText(MainActivity.this, response.body().getPesan(), Toast.LENGTH_SHORT).show();
                        pd.hide();
                    }

                    @Override
                    public void onFailure(Call<ResponseModel> call, Throwable t) {
                        Log.d("RETRO", "onFailure: " + t);
                        pd.hide();
                    }
                });

            }
        });

        btn_update.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                pd.setMessage("updating ...");
                pd.setCancelable(false);
                pd.show();

                ApiRequestBiodata api = Retroserver.getClient().create(ApiRequestBiodata.class);
                Call<ResponseModel> update = api.updateData(idData, nama.getText().toString(), usia.getText().toString(), domisili.getText().toString());
                update.enqueue(new Callback<ResponseModel>() {
                    @Override
                    public void onResponse(Call<ResponseModel> call, Response<ResponseModel> response) {
                        Log.d("RETRO", "onResponse: ");
                        Toast.makeText(MainActivity.this, response.body().getPesan(), Toast.LENGTH_SHORT).show();
                        pd.hide();

                        btn_save.setVisibility(View.VISIBLE);
                        btn_tampil_data.setVisibility(View.VISIBLE);
                        btn_update.setVisibility(View.GONE);
                        btn_hapus.setVisibility(View.GONE);

                        nama.setText("");
                        usia.setText("");
                        domisili.setText("");
                    }

                    @Override
                    public void onFailure(Call<ResponseModel> call, Throwable t) {
                        pd.hide();
                        Log.d("RETRO", "onFailure: " + t);
                    }
                });

            }
        });

        btn_save.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                pd.setMessage("send data ...");
                pd.setCancelable(false);
                pd.show();

                String sNama = nama.getText().toString();
                String sUsia = usia.getText().toString();
                String sDomisili = domisili.getText().toString();
                ApiRequestBiodata api = Retroserver.getClient().create(ApiRequestBiodata.class);

                Call<ResponseModel> sendBio = api.sendBiodata(sNama, sUsia, sDomisili);
                sendBio.enqueue(new Callback<ResponseModel>() {
                    @Override
                    public void onResponse(Call<ResponseModel> call, Response<ResponseModel> response) {
                        pd.hide();
                        Log.d("Retro", "response : " + response.body().toString());
                        String kode = response.body().getKode();

                        if(kode.equals("1")){
                            Toast.makeText(MainActivity.this, "Data berhasil disimpan", Toast.LENGTH_SHORT).show();
                        }else{
                            Toast.makeText(MainActivity.this, "Data gagal disimpan", Toast.LENGTH_SHORT).show();
                        }
                    }

                    @Override
                    public void onFailure(Call<ResponseModel> call, Throwable t) {
                        Log.d("Retro", "onFailure: " + t);
                    }
                });
            }
        });

    }
}
