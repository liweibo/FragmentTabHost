package com.example.mytaobao.adapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.mytaobao.R;
import com.example.mytaobao.bean.Campaign;
import com.example.mytaobao.bean.HomeCampaign;
import com.example.mytaobao.pictureLoad.ImageLoadProxy;

import java.util.List;

/**
 * Created by lwb on 2017/4/17.
 */

public class HomeCatgoryAdapter extends RecyclerView.Adapter<HomeCatgoryAdapter.ViewHolder> {

    private static int VIEW_TYPE_L = 0;
    private static int VIEW_TYPE_R = 1;

    private LayoutInflater mInflater;


    private List<HomeCampaign> mDatas;

    private Context mContext;


    private OnCampaignClickListener mListener;

    public void setOnCampaignClickListener(OnCampaignClickListener listener) {
        mListener = listener;
    }

    public interface OnCampaignClickListener {
        void onClick(View view, Campaign campaign);
    }


    public HomeCatgoryAdapter(List<HomeCampaign> datas, Context context) {
        mDatas = datas;
        this.mContext = context;
    }


    @Override
    public ViewHolder onCreateViewHolder(ViewGroup viewGroup, int type) {

        mInflater = LayoutInflater.from(viewGroup.getContext());
        if (type == VIEW_TYPE_R) {

            return new ViewHolder(mInflater.inflate(R.layout.template_home_cardview2, viewGroup, false));
        }

        return new ViewHolder(mInflater.inflate(R.layout.template_home_cardview, viewGroup, false));
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, int position) {

        HomeCampaign homeCampaign = mDatas.get(position);
        holder.textTitle.setText(homeCampaign.getTitle());
        ImageLoadProxy.getInstance().displayImage(homeCampaign.getCpOne().getImgUrl(),holder.imageViewBig);
        ImageLoadProxy.getInstance().displayImage(homeCampaign.getCpTwo().getImgUrl(),holder.imageViewSmallTop);
        ImageLoadProxy.getInstance().displayImage(homeCampaign.getCpThree().getImgUrl(),holder.imageViewSmallBottom);

    }


    @Override
    public int getItemCount() {
        return mDatas.size();
    }

    @Override
    public int getItemViewType(int position) {
        if(position % 2==0){
            return  VIEW_TYPE_R;
        }
        else return VIEW_TYPE_L;
    }

    public class ViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {
        TextView textTitle;
        ImageView imageViewBig;
        ImageView imageViewSmallTop;
        ImageView imageViewSmallBottom;

        public ViewHolder(View itemView) {
            super(itemView);


            textTitle = (TextView) itemView.findViewById(R.id.text_title);
            imageViewBig = (ImageView) itemView.findViewById(R.id.imgview_big);
            imageViewSmallTop = (ImageView) itemView.findViewById(R.id.imgview_small_top);
            imageViewSmallBottom = (ImageView) itemView.findViewById(R.id.imgview_small_bottom);


            imageViewBig.setOnClickListener(this);
            imageViewSmallTop.setOnClickListener(this);
            imageViewSmallBottom.setOnClickListener(this);


        }

        @Override
        public void onClick(View v) {

            HomeCampaign homeCampaign = mDatas.get(getLayoutPosition());
            if (mListener != null) {

                switch (v.getId()) {

                    case R.id.imgview_big:
                        mListener.onClick(v, homeCampaign.getCpOne());
                        break;

                    case R.id.imgview_small_top:
                        mListener.onClick(v, homeCampaign.getCpTwo());
                        break;

                    case R.id.imgview_small_bottom:
                        mListener.onClick(v, homeCampaign.getCpThree());
                        break;


                }
            }
        }
    }
}
