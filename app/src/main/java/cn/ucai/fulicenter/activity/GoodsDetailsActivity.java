package cn.ucai.fulicenter.activity;

import android.content.Context;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.webkit.WebView;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import cn.ucai.fulicenter.I;
import cn.ucai.fulicenter.R;
import cn.ucai.fulicenter.bean.AlbumsBean;
import cn.ucai.fulicenter.bean.GoodsDetailsBean;
import cn.ucai.fulicenter.net.NetDao;
import cn.ucai.fulicenter.utils.OkHttpUtils;
import cn.ucai.fulicenter.view.FlowIndicator;
import cn.ucai.fulicenter.view.SlideAutoLoopView;

public class GoodsDetailsActivity extends AppCompatActivity {

    @BindView(R.id.ivBack) ImageView ivBack;
    @BindView(R.id.tvTitle) TextView tvTitle;
    @BindView(R.id.ivShare) ImageView ivShare;
    @BindView(R.id.ivCollect) ImageView ivCollect;
    @BindView(R.id.ivCart) ImageView ivCart;
    @BindView(R.id.tvCartCount) TextView tvCartCount;
    @BindView(R.id.tvGoodEnglishName) TextView tvGoodEnglishName;
    @BindView(R.id.tvGoodName) TextView tvGoodName;
    @BindView(R.id.tvGoodShopPrice) TextView tvGoodShopPrice;
    @BindView(R.id.tvGoodCurrentPrice) TextView tvGoodCurrentPrice;
    @BindView(R.id.slideAuto) SlideAutoLoopView slideAuto;
    @BindView(R.id.flowIndicator) FlowIndicator flowIndicator;
    @BindView(R.id.wvGoodBrief) WebView wvGoodBrief;
    int goodsId;
    Context context;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_goods_details);
        ButterKnife.bind(this);
        context=GoodsDetailsActivity.this;
        initView();
        initData();
    }

    private void initView() {

    }
    GoodsDetailsBean mGoodsDetail;
    private void initData() {
        goodsId = getIntent().getIntExtra(I.GoodsDetails.KEY_GOODS_ID, 0);
        if(goodsId>0){
            NetDao.downloadGoodDetails(GoodsDetailsActivity.this, goodsId, new OkHttpUtils.OnCompleteListener<GoodsDetailsBean>() {
                @Override
                public void onSuccess(GoodsDetailsBean result) {
                    if(result!=null){
                        mGoodsDetail=result;
                        //在相应控件上显示商品详情
                        showGoodsDetails();
                    }
                }
                @Override
                public void onError(String error) {
                    finish();
                    Toast.makeText(GoodsDetailsActivity.this, "下载商品详情失败", Toast.LENGTH_SHORT).show();
                }
            });
        }else{
            finish();
            Toast.makeText(GoodsDetailsActivity.this, "下载商品详情失败", Toast.LENGTH_SHORT).show();
        }
    }

    private void showGoodsDetails() {
        tvGoodEnglishName.setText(mGoodsDetail.getGoodsEnglishName());
        tvGoodName.setText(mGoodsDetail.getGoodsName());
        tvGoodShopPrice.setText(mGoodsDetail.getShopPrice());
        tvGoodCurrentPrice.setText(mGoodsDetail.getCurrencyPrice());
        //设置滚动循环播放商品图片
        slideAuto.startPlayLoop(flowIndicator,getAlbumImgUrl(),getAlbumSize());
        wvGoodBrief.loadDataWithBaseURL(null,mGoodsDetail.getGoodsBrief(),I.TEXT_HTML,"utf-8",null);
    }
    //得到商品图片个数
    private int getAlbumSize() {
        if(mGoodsDetail.getProperties()!=null && mGoodsDetail.getProperties().size()>0){
            return mGoodsDetail.getProperties().get(0).getAlbums().size();
        }
        return 0;
    }
    //得到商品图片集
    private String[] getAlbumImgUrl() {
        String[] albumImgUrls=null;
        if(mGoodsDetail.getProperties()!=null && mGoodsDetail.getProperties().size()>0){
            List<AlbumsBean> albumsBeanList = mGoodsDetail.getProperties().get(0).getAlbums();
            albumImgUrls=new String[albumsBeanList.size()];
            for(int i=0;i<albumsBeanList.size();i++){
                albumImgUrls[i]=albumsBeanList.get(i).getImgUrl();
            }
        }
        return albumImgUrls;
    }
}
