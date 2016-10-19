package cn.ucai.fulicenter.adapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import java.util.ArrayList;

import butterknife.BindView;
import butterknife.ButterKnife;
import cn.ucai.fulicenter.I;
import cn.ucai.fulicenter.R;
import cn.ucai.fulicenter.bean.BoutiqueBean;
import cn.ucai.fulicenter.utils.ImageLoader;

/**
 * Created by 11039 on 2016/10/18.
 */
public class BoutiqueAdapter extends RecyclerView.Adapter {
    Context context;
    ArrayList<BoutiqueBean> mBoutiqueList;

    public BoutiqueAdapter(Context context, ArrayList<BoutiqueBean> boutiqueList) {
        this.context = context;
        this.mBoutiqueList = boutiqueList;
    }

    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        RecyclerView.ViewHolder holder;
        LayoutInflater inflater = LayoutInflater.from(context);
        if (viewType == I.TYPE_FOOTER) {
            holder = new FooterViewHolder(inflater.inflate(R.layout.item_footer, null));
        } else {
            holder = new BoutiqueViewHolder(inflater.inflate(R.layout.item_boutique, null));
        }
        return holder;
    }
    private String footer;
    public void setFooter(String footer){
        this.footer=footer;
        notifyDataSetChanged();
    }
    private boolean isMore;
    public void setMore(boolean isMore){
        this.isMore=isMore;
    }
    public boolean isMore(){
        return isMore;
    }
    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder holder, int position) {
        if(getItemViewType(position)==I.TYPE_FOOTER){
            FooterViewHolder footerViewHolder= (FooterViewHolder) holder;
            footerViewHolder.tvFooter.setText(footer);
            return;
        }
        BoutiqueBean boutiqueBeen = mBoutiqueList.get(position);
        BoutiqueViewHolder boutiqueViewHolder= (BoutiqueViewHolder) holder;
        boutiqueViewHolder.tvBoutiqueTitle.setText(boutiqueBeen.getTitle());
        boutiqueViewHolder.tvBoutiqueName.setText(boutiqueBeen.getName());
        boutiqueViewHolder.tvBoutiqueDes.setText(boutiqueBeen.getDescription());
        //下载精品图片并设置
        ImageLoader.downloadImg(context,boutiqueViewHolder.ivBoutique,boutiqueBeen.getImageurl());
    }

    @Override
    public int getItemCount() {
        return mBoutiqueList != null ? mBoutiqueList.size() + 1 : 1;
    }

    @Override
    public int getItemViewType(int position) {
        if (position == getItemCount() - 1) {
            return I.TYPE_FOOTER;
        } else {
            return I.TYPE_ITEM;
        }
    }

    public void initBoutiqueList(ArrayList<BoutiqueBean> boutiqueList) {
        if(mBoutiqueList!=null){
            mBoutiqueList.clear();
        }
        mBoutiqueList.addAll(boutiqueList);
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

    static class BoutiqueViewHolder extends RecyclerView.ViewHolder{
        @BindView(R.id.ivBoutique)
        ImageView ivBoutique;
        @BindView(R.id.tvBoutiqueTitle)
        TextView tvBoutiqueTitle;
        @BindView(R.id.tvBoutiqueName)
        TextView tvBoutiqueName;
        @BindView(R.id.tvBoutiqueDes)
        TextView tvBoutiqueDes;
        @BindView(R.id.layout_boutique)
        LinearLayout layoutBoutique;

        BoutiqueViewHolder(View view) {
            super(view);
            ButterKnife.bind(this, view);
        }
    }
}
