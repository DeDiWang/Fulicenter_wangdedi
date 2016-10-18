package cn.ucai.fulicenter.adapter;

import android.content.Context;
import android.content.Intent;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.ArrayList;

import butterknife.BindView;
import butterknife.ButterKnife;
import cn.ucai.fulicenter.I;
import cn.ucai.fulicenter.R;
import cn.ucai.fulicenter.activity.GoodsDetailsActivity;
import cn.ucai.fulicenter.bean.NewGoodsBean;
import cn.ucai.fulicenter.utils.ImageLoader;

/**
 * Created by 11039 on 2016/10/17.
 */
public class GoodAdapter extends RecyclerView.Adapter {
    Context context;
    ArrayList<NewGoodsBean> goodsList;

    //设置商品列表项单击事件的监听对象
    View.OnClickListener mItemOnClickListener;

    public GoodAdapter(final Context context, ArrayList<NewGoodsBean> goodsList) {
        this.context = context;
        this.goodsList = goodsList;
        //只需设置一个监听对象
        mItemOnClickListener=new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                //拿到该项的商品id
                int goodsId = (int) view.getTag();
                //跳转到商品详情的Activity
                context.startActivity(new Intent(context, GoodsDetailsActivity.class)
                .putExtra(I.GoodsDetails.KEY_GOODS_ID,goodsId));
            }
        };
    }

    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        RecyclerView.ViewHolder holder;
        LayoutInflater inflater=LayoutInflater.from(context);
        if (viewType == I.TYPE_FOOTER) {
            holder = new FooterViewHolder(inflater.inflate(R.layout.item_footer, null));
        } else {
            holder = new GoodViewHolder(inflater.inflate( R.layout.item_goods, null));
        }
        return holder;
    }

    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder holder, int position) {
        if(getItemViewType(position)==I.TYPE_FOOTER){
            FooterViewHolder footerViewHolder= (FooterViewHolder) holder;
            footerViewHolder.tvFooter.setText(footer);
            return;
        }
        GoodViewHolder goodViewHolder= (GoodViewHolder) holder;
        NewGoodsBean good = goodsList.get(position);
        //下载商品图片
        ImageLoader.downloadImg(context,goodViewHolder.ivGoodAvatar,good.getGoodsThumb());
        goodViewHolder.tvGoodName.setText(good.getGoodsName());
        goodViewHolder.tvGoodPrice.setText(good.getCurrencyPrice());

        //给单击事件监听对象传递数据
        goodViewHolder.layoutGoods.setTag(good.getGoodsId());
    }

    @Override
    public int getItemViewType(int position) {
        if (position == getItemCount() - 1) {
            return I.TYPE_FOOTER;
        } else {
            return I.TYPE_ITEM;
        }
    }

    @Override
    public int getItemCount() {
        return goodsList != null ? goodsList.size() + 1 : 1;
    }

    public void initData(ArrayList<NewGoodsBean> mGoodsList) {
        if(goodsList!=null){
            goodsList.clear();
        }
        goodsList.addAll(mGoodsList);
        notifyDataSetChanged();
    }

    private String footer;
    public void setFooter(String footer) {
        this.footer=footer;
        notifyDataSetChanged();
    }
    private boolean isMore;
    public void setMore(boolean isMore) {
        this.isMore=isMore;
    }
    public boolean isMore(){
        return isMore;
    }

    public void addData(ArrayList<NewGoodsBean> list) {
        this.goodsList.addAll(list);
        notifyDataSetChanged();
    }
    int newState;
    public void setScrollState(int newState) {
        this.newState=newState;
        notifyDataSetChanged();
    }

    class FooterViewHolder extends RecyclerView.ViewHolder {
        @BindView(R.id.tvFooter)
        TextView tvFooter;

        FooterViewHolder(View view) {
            super(view);
            ButterKnife.bind(this, view);
        }
    }

    class GoodViewHolder extends RecyclerView.ViewHolder{
        @BindView(R.id.ivGoodAvatar)
        ImageView ivGoodAvatar;
        @BindView(R.id.tvGoodName)
        TextView tvGoodName;
        @BindView(R.id.tvGoodPrice)
        TextView tvGoodPrice;

        View layoutGoods;
        GoodViewHolder(View view) {
            super(view);
            layoutGoods = view.findViewById(R.id.layout_goods);
            ButterKnife.bind(this, view);
            //设置列表项的监听
            layoutGoods.setOnClickListener(mItemOnClickListener);
        }
    }
}
