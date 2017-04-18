package com.example.mytaobao.adapter;

import android.net.Uri;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.mytaobao.R;
import com.example.mytaobao.bean.Page;
import com.example.mytaobao.bean.Wares;
import com.example.mytaobao.pictureLoad.ImageLoadProxy;
import com.facebook.drawee.view.SimpleDraweeView;

import java.util.List;

/**
 * Created by lwb on 2017/4/18.
 */

public class HotWaresAdapter extends RecyclerView.Adapter<HotWaresAdapter.HotWaresViewHolder> {
    public List<Wares> mDatas;
    private LayoutInflater mInflater;

    public HotWaresAdapter(List<Wares> data) {
        mDatas = data;
    }

    @Override
    public HotWaresViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        mInflater = LayoutInflater.from(parent.getContext());
        View view = mInflater.inflate(R.layout.template_hot_wares, null);
        return new HotWaresViewHolder(view);
    }

    @Override
    public void onBindViewHolder(HotWaresViewHolder holder, int position) {
        Wares wares = mDatas.get(position);
        //holder.draweeView.setImageURI(Uri.parse(wares.getImgUrl()));
        ImageLoadProxy.getInstance().displayImage(wares.getImgUrl(),holder.draweeView);
        holder.textTitle.setText(wares.getName());
        holder.textPrice.setText("￥"+wares.getPrice());
    }


    @Override
    public int getItemCount() {
        if(mDatas!=null && mDatas.size()>0)
            return mDatas.size();

        return 0;
    }



    public List<Wares> getDatas(){

        return  mDatas;
    }
    public void clearData(){

        mDatas.clear();
        notifyItemRangeRemoved(0,mDatas.size());
    }

    public void addData(List<Wares> datas){

        addData(0,datas);
    }

    public void addData(int position,List<Wares> datas){

        if(datas !=null && datas.size()>0) {

            mDatas.addAll(datas);
            notifyItemRangeChanged(position, mDatas.size());
        }

    }

    public class HotWaresViewHolder extends RecyclerView.ViewHolder {
        ImageView draweeView;
        TextView textTitle;
        TextView textPrice;

        public HotWaresViewHolder(View itemView) {
            super(itemView);

            draweeView = (ImageView) itemView.findViewById(R.id.drawee_view);
            textTitle = (TextView) itemView.findViewById(R.id.text_title);
            textPrice = (TextView) itemView.findViewById(R.id.text_price);

        }


    }
}
