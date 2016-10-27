package cn.ucai.fulicenter.fragment;


import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
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
import cn.ucai.fulicenter.FuLiCenterApplication;
import cn.ucai.fulicenter.I;
import cn.ucai.fulicenter.R;
import cn.ucai.fulicenter.adapter.CartAdapter;
import cn.ucai.fulicenter.bean.CartBean;
import cn.ucai.fulicenter.bean.GoodsDetailsBean;
import cn.ucai.fulicenter.bean.UserAvatar;
import cn.ucai.fulicenter.net.NetDao;
import cn.ucai.fulicenter.utils.ConvertUtils;
import cn.ucai.fulicenter.utils.L;
import cn.ucai.fulicenter.utils.OkHttpUtils;
import cn.ucai.fulicenter.view.SpaceItemDecoration;

/**
 * A simple {@link Fragment} subclass.
 */
public class FragmentCart extends Fragment {

    @BindView(R.id.tvRefreshHint)
    TextView tvRefreshHint;
    @BindView(R.id.rvCartGoods)
    RecyclerView rvCartGoods;
    @BindView(R.id.srl)
    SwipeRefreshLayout srl;
    @BindView(R.id.tvSum)
    TextView tvSum;
    @BindView(R.id.tvSave)
    TextView tvSave;

    Context mContext;
    ArrayList<CartBean> cartList;
    CartAdapter mAdapter;
    LinearLayoutManager layoutManager;
    public FragmentCart() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_cart, container, false);
        ButterKnife.bind(this, view);
        mContext=getContext();
        initView();
        initData();
        setListener();
        return view;
    }
    MyBroadcast myBroadcast;
    private void setListener() {
        srl.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                srl.setRefreshing(true);
                tvRefreshHint.setVisibility(View.VISIBLE);
                initData();
            }
        });
        //注册广播接收者
        myBroadcast = new MyBroadcast();
        IntentFilter filter = new IntentFilter(I.BROADCAST_UPDATE_CART);
        mContext.registerReceiver(myBroadcast,filter);
    }
    //定义广播者类
    class MyBroadcast extends BroadcastReceiver{
        @Override
        public void onReceive(Context context, Intent intent) {
            sumPrice();
        }
    }
    private void initData() {
        UserAvatar user = FuLiCenterApplication.getUser();
        if(user!=null){
            NetDao.findCarts(mContext, user.getMuserName(), new OkHttpUtils.OnCompleteListener<CartBean[]>() {
                @Override
                public void onSuccess(CartBean[] result) {
                    srl.setRefreshing(false);
                    tvRefreshHint.setVisibility(View.GONE);
                    ArrayList<CartBean> list = ConvertUtils.array2List(result);
                    mAdapter.initData(list);

                    sumPrice();
                }

                @Override
                public void onError(String error) {
                    L.e("error===="+error);
                }
            });
        }
    }

    private void initView() {
        srl.setColorSchemeColors(
                getResources().getColor(R.color.blue),
                getResources().getColor(R.color.green),
                getResources().getColor(R.color.red),
                getResources().getColor(R.color.google_yellow)
        );
        cartList=new ArrayList<>();
        mAdapter=new CartAdapter(mContext,cartList);
        rvCartGoods.setAdapter(mAdapter);
        layoutManager=new LinearLayoutManager(mContext);
        rvCartGoods.setLayoutManager(layoutManager);
        rvCartGoods.setHasFixedSize(true);
        rvCartGoods.addItemDecoration(new SpaceItemDecoration(20));
    }

    @Override
    public void onResume() {
        super.onResume();
        initData();
    }

    //计算购物车商品总价
    private void sumPrice(){
        double sum = 0;
        double rank = 0;
        if(cartList!=null && cartList.size()>0){
            for(CartBean c : cartList){
                GoodsDetailsBean goods = c.getGoods();
                if(c.isChecked()){
                    sum += c.getCount() * getPrice(goods.getCurrencyPrice());
                    rank += c.getCount() * getPrice(goods.getRankPrice());
                }
            }
            tvSum.setText("总计：￥"+ sum);
            tvSave.setText("节省：￥"+(sum - rank));
        }else{
            tvSum.setText("总计：￥0");
            tvSave.setText("节省：￥0");
        }
    }
    private int getPrice(String str){
        String s = str.substring(str.indexOf("￥") + 1);
        int i = Integer.parseInt(s);
        return i;
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        mContext.unregisterReceiver(myBroadcast);
    }
}
