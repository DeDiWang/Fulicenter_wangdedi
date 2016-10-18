package cn.ucai.fulicenter.fragment;


import android.content.Context;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import java.util.ArrayList;

import butterknife.BindView;
import butterknife.ButterKnife;
import cn.ucai.fulicenter.I;
import cn.ucai.fulicenter.R;
import cn.ucai.fulicenter.adapter.GoodAdapter;
import cn.ucai.fulicenter.bean.NewGoodsBean;
import cn.ucai.fulicenter.net.NetDao;
import cn.ucai.fulicenter.utils.ConvertUtils;
import cn.ucai.fulicenter.utils.ImageLoader;
import cn.ucai.fulicenter.utils.L;
import cn.ucai.fulicenter.utils.OkHttpUtils;

/**
 * A simple {@link Fragment} subclass.
 */
public class FragmentNewGoods extends Fragment {

    @BindView(R.id.tvRefreshHint)
    TextView mTvRefreshHint;
    @BindView(R.id.rvGoodsList)
    RecyclerView mRvGoodsList;
    @BindView(R.id.srl)
    SwipeRefreshLayout mSrl;

    public FragmentNewGoods() {
        // Required empty public constructor
    }

    View view;
    Context mContext;
    NetDao netDao;
    ArrayList<NewGoodsBean> mGoodsList;
    GoodAdapter mAdapter;
    GridLayoutManager gridManager;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        view = inflater.inflate(R.layout.fragment_new_goods, container, false);
        ButterKnife.bind(this, view);
        mContext = getActivity();
        netDao = new NetDao();
        mGoodsList = new ArrayList<>();
        mAdapter = new GoodAdapter(mContext, mGoodsList);

        initView();
        downloadNewGoods(mPageId,I.ACTION_DOWNLOAD);
        setListener();
        return view;
    }

    private void setListener() {
        setPullDownListener();
        setPUllUpListener();
    }
    int lastPosition;
    //上拉加载
    private void setPUllUpListener() {
        mRvGoodsList.setOnScrollListener(new RecyclerView.OnScrollListener() {
            @Override
            public void onScrollStateChanged(RecyclerView recyclerView, int newState) {
                super.onScrollStateChanged(recyclerView, newState);
                lastPosition = gridManager.findLastVisibleItemPosition();
                mAdapter.setScrollState(newState);
                L.e("lastPosition="+lastPosition+",count="+mAdapter.getItemCount()+",isMore="+mAdapter.isMore());
                if(newState==RecyclerView.SCROLL_STATE_IDLE &&
                        lastPosition>=mAdapter.getItemCount()-1 &&
                        mAdapter.isMore()){
                    mPageId++;
                    downloadNewGoods(mPageId,I.ACTION_PULL_UP);
                }
            }

            @Override
            public void onScrolled(RecyclerView recyclerView, int dx, int dy) {
                super.onScrolled(recyclerView, dx, dy);
            }
        });
    }

    //下拉刷新
    private void setPullDownListener() {
        mSrl.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                mSrl.setRefreshing(true);
                mSrl.setEnabled(true);
                mTvRefreshHint.setVisibility(View.VISIBLE);
                mPageId=1;
                downloadNewGoods(mPageId,I.ACTION_PULL_DOWN);
            }
        });
    }

    int mPageId = 1;

    private void downloadNewGoods(int pageId, final int action) {
        netDao.downloadGoodsList(mContext, pageId, new OkHttpUtils.OnCompleteListener<NewGoodsBean[]>() {

            @Override
            public void onSuccess(NewGoodsBean[] result) {
                mSrl.setRefreshing(false);
                mTvRefreshHint.setVisibility(View.GONE);
                mAdapter.setFooter("加载更多...");
                mAdapter.setMore(true);
                if(result!=null){
                    ArrayList<NewGoodsBean> list = ConvertUtils.array2List(result);
                    if(action==I.ACTION_PULL_DOWN || action==I.ACTION_DOWNLOAD){
                        mAdapter.initData(list);
                    }else{
                        mAdapter.addData(list);
                    }
                    if(list.size()<I.PAGE_SIZE_DEFAULT){
                        mAdapter.setMore(false);
                        mAdapter.setFooter("不能加载更多...");
                    }
                }else{
                    mAdapter.setMore(false);
                    mAdapter.setFooter("不能加载更多...");
                }
            }

            @Override
            public void onError(String error) {
                L.e("error"+error);
            }
        });
    }

    private void initView() {
        //下拉刷新圈圈的颜色
        mSrl.setColorSchemeColors(
                getResources().getColor(R.color.google_blue),
                getResources().getColor(R.color.google_green),
                getResources().getColor(R.color.google_red),
                getResources().getColor(R.color.google_yellow)
        );
        gridManager = new GridLayoutManager(mContext, I.COLUM_NUM);
        gridManager.setOrientation(LinearLayoutManager.VERTICAL);
        mRvGoodsList.setLayoutManager(gridManager);
        mRvGoodsList.setHasFixedSize(true);

        mRvGoodsList.setAdapter(mAdapter);
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        OkHttpUtils.release();
        ImageLoader.release();
    }
}
