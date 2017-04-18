package com.example.mytaobao.fragment;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import com.daimajia.slider.library.Animations.DescriptionAnimation;
import com.daimajia.slider.library.SliderLayout;
import com.daimajia.slider.library.SliderTypes.BaseSliderView;
import com.daimajia.slider.library.SliderTypes.TextSliderView;
import com.example.mytaobao.R;
import com.example.mytaobao.adapter.CardViewtemDecortion;
import com.example.mytaobao.adapter.HomeCatgoryAdapter;
import com.example.mytaobao.bean.Banner;
import com.example.mytaobao.bean.Campaign;
import com.example.mytaobao.bean.HomeCampaign;
import com.example.mytaobao.http.BaseCallback;
import com.example.mytaobao.http.Contants;
import com.example.mytaobao.http.OkHttpHelper;
import com.example.mytaobao.http.SpotsCallBack;
import com.example.mytaobao.widget.MyApplication;
import com.google.gson.Gson;

import java.util.List;

import okhttp3.Call;
import okhttp3.Request;
import okhttp3.Response;


public class HomeFragment extends Fragment {
    private SliderLayout mSliderLayout;
    private Gson mGson = new Gson();

    private List<Banner> mBanner;


    private OkHttpHelper httpHelper = OkHttpHelper.getInstance();
    private RecyclerView mRecyclerView;
    private HomeCatgoryAdapter mAdatper;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
       View view =  inflater.inflate(R.layout.fragment_home, container, false);
        mSliderLayout = (SliderLayout) view.findViewById(R.id.slider);

        requestImages();
        initRecyclerView(view);
        return view;

    }

    private void initRecyclerView(View view) {
        mRecyclerView = (RecyclerView) view.findViewById(R.id.recyclerview);
        httpHelper.get(Contants.API.CAMPAIGN_HOME, new BaseCallback<List<HomeCampaign>> (){
            @Override
            public void onBeforeRequest(Request request) {

            }

            @Override
            public void onFailure(Call call, Exception e) {

            }

            @Override
            public void onResponse(Response response) {

            }

            @Override
            public void onSuccess(Response response, List<HomeCampaign> homeCampaigns) {
                initData(homeCampaigns);
            }


            @Override
            public void onError(Response response, int code, Exception e) {

            }
        });

    }

    private void initData(List<HomeCampaign> homeCampaigns) {

        mAdatper = new HomeCatgoryAdapter(homeCampaigns,getActivity());
        mAdatper.setOnCampaignClickListener(new HomeCatgoryAdapter.OnCampaignClickListener() {
            @Override
            public void onClick(View view, Campaign campaign) {
                Toast.makeText(MyApplication.getContext(),campaign.getTitle(),Toast.LENGTH_LONG).show();
            }
        });
        mRecyclerView.setAdapter(mAdatper);

        mRecyclerView.addItemDecoration(new CardViewtemDecortion());

        mRecyclerView.setLayoutManager(new LinearLayoutManager(this.getActivity()));
    }


    private void requestImages() {
        String url = "http://112.124.22.238:8081/course_api/banner/query?type=1";

        //获取到的数据存入bean中即Banner中，然后再传入OKHttpHelper中的callbackSuccess方法里，再传入baseCallback
        //中的onsuccess中，即SpotsCallBack的onSuccess中。这就是整个数据的传递过程。
        httpHelper.get(url, new SpotsCallBack<List<Banner>>(getActivity()) {
            @Override
            public void onFailure(Call call, Exception e) {

            }

            @Override
            public void onSuccess(Response response, List<Banner> banners) {
                mBanner = banners;
                initSlider();
            }

            private void initSlider() {

                if(mBanner !=null){

                    for (Banner banner : mBanner){


                        TextSliderView textSliderView = new TextSliderView(getActivity());
                        textSliderView.image(banner.getImgUrl());
                        textSliderView.description(banner.getName());
                        textSliderView.setScaleType(BaseSliderView.ScaleType.Fit);
                        mSliderLayout.addSlider(textSliderView);

                    }
                }



                mSliderLayout.setPresetIndicator(SliderLayout.PresetIndicators.Center_Bottom);

                mSliderLayout.setCustomAnimation(new DescriptionAnimation());
                mSliderLayout.setPresetTransformer(SliderLayout.Transformer.RotateUp);
                mSliderLayout.setDuration(3000);
            }


            @Override
            public void onError(Response response, int code, Exception e) {

            }
        });
    }
    @Override
    public void onDestroy() {
        super.onDestroy();

        mSliderLayout.stopAutoCycle();
    }
}
