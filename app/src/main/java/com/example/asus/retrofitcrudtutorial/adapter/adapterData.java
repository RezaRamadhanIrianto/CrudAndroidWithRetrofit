package com.example.asus.retrofitcrudtutorial.adapter;

import android.content.Context;
import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.text.Layout;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.example.asus.retrofitcrudtutorial.MainActivity;
import com.example.asus.retrofitcrudtutorial.R;
import com.example.asus.retrofitcrudtutorial.model.DataModel;

import org.w3c.dom.Text;

import java.util.List;

public class adapterData extends RecyclerView.Adapter<adapterData.HolderData> {

    private List<DataModel> mList;
    private Context ctx;

    public adapterData(Context ctx, List<DataModel> mList){
        this.ctx = ctx;
        this.mList = mList;
    }

    @NonNull
    @Override
    public HolderData onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View layout = LayoutInflater.from(parent.getContext()).inflate(R.layout.layout_list,parent, false);
        HolderData holder = new HolderData(layout);
        return holder;
    }

    @Override
    public void onBindViewHolder(@NonNull HolderData holder, int position) {
        DataModel dm = mList.get(position);
        holder.nama.setText(dm.getNama());
        holder.usia.setText(dm.getUsia());
        holder.domisili.setText(dm.getDomisili());
        holder.dm = dm;

    }

    @Override
    public int getItemCount() {
        return mList.size();
    }

    class HolderData extends RecyclerView.ViewHolder{
        TextView nama, domisili, usia;
        DataModel dm;
        public HolderData(View v){

            super(v);
            nama = (TextView) v.findViewById(R.id.tvNama);
            usia = (TextView) v.findViewById(R.id.tvUsia);
            domisili = (TextView) v.findViewById(R.id.tvDomisili);

            v.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    Intent goInput = new Intent(ctx, MainActivity.class);
                    goInput.putExtra("id", dm.getId());
                    goInput.putExtra("nama", dm.getNama());
                    goInput.putExtra("usia", dm.getUsia());
                    goInput.putExtra("domisili", dm.getDomisili());
                    ctx.startActivity(goInput);
                }
            });

        }

    }

}
