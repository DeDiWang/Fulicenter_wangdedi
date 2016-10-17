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
        initData();
        return view;
    }

    int pageId = 1;

    private void initData() {
        netDao.downloadGoodsList(mContext, pageId, new OkHttpUtils.OnCompleteListener<NewGoodsBean[]>() {

            @Override
            public void onSuccess(NewGoodsBean[] result) {
                if(result!=null && result.length>0){
                    ArrayList<NewGoodsBean> list = ConvertUtils.array2List(result);
                    mAdapter.initData(list);
                }
            }

            @Override
            public void onError(String error) {
                L.e("error"+error);
            }
        });
    }

    private void initView() {
        mSrl.setColorSchemeColors(
                getResources().getColor(R.color.google_blue),
                getResources().getColor(R.color.google_green),
                getResources().getColor(R.color.google_red),
                getResources().getColor(R.color.google_yellow)
        );
        gridManager = new GridLayoutManager(mContext, I.COLUM_NUM);
        gridManager.setOrientation(LinearLayoutManager.VERTICAL);
        mRvGoodsList.setLayoutManager(gridManager);

        mRvGoodsList.setAdapter(mAdapter);
    }
}
