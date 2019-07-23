package com.example.asus.retrofitcrudtutorial;

import android.app.ProgressDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.telecom.Call;
import android.util.Log;

import com.example.asus.retrofitcrudtutorial.adapter.adapterData;
import com.example.asus.retrofitcrudtutorial.api.ApiRequestBiodata;
import com.example.asus.retrofitcrudtutorial.api.Retroserver;
import com.example.asus.retrofitcrudtutorial.model.DataModel;
import com.example.asus.retrofitcrudtutorial.model.ResponseModel;

import java.util.ArrayList;
import java.util.List;

import retrofit2.Callback;
import retrofit2.Response;

public class TampilData extends AppCompatActivity {
    private RecyclerView mRecycler;
    private RecyclerView.Adapter mAdapter;
    private RecyclerView.LayoutManager mManager;
    private List<DataModel> mItems = new ArrayList<>();
    ProgressDialog pd;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_tampil_data);

        pd = new ProgressDialog(this);
        pd.setMessage("Loading....");
        pd.setCancelable(false);
        pd.show();
        mRecycler = (RecyclerView) findViewById(R.id.recyclerTemp);
        mManager = new LinearLayoutManager(this,LinearLayoutManager.VERTICAL, false);
        mRecycler.setLayoutManager(mManager);

        ApiRequestBiodata api = Retroserver.getClient().create(ApiRequestBiodata.class);
        retrofit2.Call<ResponseModel> getData = api.getBiodata();
        getData.enqueue(new Callback<ResponseModel>() {
            @Override
            public void onResponse(retrofit2.Call<ResponseModel> call, Response<ResponseModel> response) {
                pd.hide();
                Log.d("RETRO", "onResponse: " + response.body().getKode());
                mItems = response.body().getResult();
                mAdapter = new adapterData(TampilData.this, mItems);
                mRecycler.setAdapter(mAdapter);
                mAdapter.notifyDataSetChanged();
            }

            @Override
            public void onFailure(retrofit2.Call<ResponseModel> call, Throwable t) {
                Log.d("RETRO", "onFailure: " + t);

            }
        });

    }
}
