package com.example.mytaobao.fragment;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.cjj.MaterialRefreshLayout;
import com.cjj.MaterialRefreshListener;

import com.example.mytaobao.adapter.DividerItemDecoration;
import com.example.mytaobao.adapter.HotWaresAdapter;
import com.example.mytaobao.bean.Page;
import com.example.mytaobao.bean.Wares;
import com.example.mytaobao.http.Contants;
import com.example.mytaobao.http.OkHttpHelper;
import com.example.mytaobao.http.SpotsCallBack;
import com.example.mytaobao.widget.MyApplication;

import java.util.List;

import okhttp3.Call;
import okhttp3.Response;

import com.example.mytaobao.R;

public class HotFragment extends Fragment {
    private OkHttpHelper httpHelper = OkHttpHelper.getInstance();
    private int currPage = 1;
    private int totalPage = 1;
    private int pageSize = 10;
    private static final int STATE_NORMAL = 0;
    private static final int STATE_REFREH = 1;
    private static final int STATE_MORE = 2;

    private int state = STATE_NORMAL;

    private List<Wares> datas;

    private HotWaresAdapter mAdatper;
    private MaterialRefreshLayout mRefreshLaout;
    private RecyclerView mRecyclerView;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_hot, container, false);

        mRefreshLaout = (MaterialRefreshLayout) view.findViewById(R.id.refresh_view);
        mRecyclerView = (RecyclerView) view.findViewById(R.id.recyclerview_hot);
        initRefreshLayout();
        getData();
        return view;


    }

    private void getData() {
        String url = Contants.API.WARES_HOT + "?curPage=" + currPage + "&pageSize=" + pageSize;
        httpHelper.get(url, new SpotsCallBack<Page<Wares>>(getContext()) {

            @Override
            public void onSuccess(Response response, Page<Wares>  waresPage) {

                datas = waresPage.getList();
                currPage = waresPage.getCurrentPage();
                totalPage = waresPage.getTotalPage();

                showData();

            }


            @Override
            public void onError(Response response, int code, Exception e) {

            }
        });
    }

    private void showData() {
        switch (state) {

            case STATE_NORMAL:
                mAdatper = new HotWaresAdapter(datas);

                mRecyclerView.setAdapter(mAdatper);

                mRecyclerView.setLayoutManager(new LinearLayoutManager(getContext()));
                mRecyclerView.setItemAnimator(new DefaultItemAnimator());
                mRecyclerView.addItemDecoration(new DividerItemDecoration(getContext(), DividerItemDecoration.VERTICAL_LIST));

                break;

            case STATE_REFREH:
                mAdatper.clearData();
                mAdatper.addData(datas);

                mRecyclerView.scrollToPosition(0);
                mRefreshLaout.finishRefresh();
                break;

            case STATE_MORE:
                mAdatper.addData(mAdatper.getDatas().size(), datas);
                mRecyclerView.scrollToPosition(mAdatper.getDatas().size());
                mRefreshLaout.finishRefreshLoadMore();
                break;

        }
    }


    private void initRefreshLayout() {
        mRefreshLaout.setLoadMore(true);
        mRefreshLaout.setMaterialRefreshListener(new MaterialRefreshListener() {
            @Override
            public void onRefresh(MaterialRefreshLayout materialRefreshLayout) {

                refreshData();
            }

            @Override
            public void onRefreshLoadMore(MaterialRefreshLayout materialRefreshLayout) {
                if (currPage <= totalPage)
                    loadMoreData();
                else {
                    mRefreshLaout.finishRefreshLoadMore();
                }
            }
        });

    }

    private void loadMoreData() {
        currPage = ++currPage;
        state = STATE_MORE;

        getData();

    }

    private void refreshData() {


        currPage = 1;

        state = STATE_REFREH;
        getData();

    }

}
