package cn.ucai.fulicenter.activity;

import android.app.Activity;
import android.content.Context;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.webkit.WebView;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import cn.ucai.fulicenter.FuLiCenterApplication;
import cn.ucai.fulicenter.I;
import cn.ucai.fulicenter.R;
import cn.ucai.fulicenter.bean.AlbumsBean;
import cn.ucai.fulicenter.bean.GoodsDetailsBean;
import cn.ucai.fulicenter.bean.MessageBean;
import cn.ucai.fulicenter.bean.UserAvatar;
import cn.ucai.fulicenter.net.NetDao;
import cn.ucai.fulicenter.utils.CommonUtils;
import cn.ucai.fulicenter.utils.MFGT;
import cn.ucai.fulicenter.utils.OkHttpUtils;
import cn.ucai.fulicenter.view.FlowIndicator;
import cn.ucai.fulicenter.view.SlideAutoLoopView;

public class GoodsDetailsActivity extends AppCompatActivity {

    @BindView(R.id.ivBack)
    ImageView ivBack;
    @BindView(R.id.tvTitle)
    TextView tvTitle;
    @BindView(R.id.ivShare)
    ImageView ivShare;
    @BindView(R.id.ivCollect)
    ImageView ivCollect;
    @BindView(R.id.ivCart)
    ImageView ivCart;
    @BindView(R.id.tvCartCount)
    TextView tvCartCount;
    @BindView(R.id.tvGoodEnglishName)
    TextView tvGoodEnglishName;
    @BindView(R.id.tvGoodName)
    TextView tvGoodName;
    @BindView(R.id.tvGoodShopPrice)
    TextView tvGoodShopPrice;
    @BindView(R.id.tvGoodCurrentPrice)
    TextView tvGoodCurrentPrice;
    @BindView(R.id.slideAuto)
    SlideAutoLoopView slideAuto;
    @BindView(R.id.flowIndicator)
    FlowIndicator flowIndicator;
    @BindView(R.id.wvGoodBrief)
    WebView wvGoodBrief;
    int goodsId;
    Context context;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_goods_details);
        ButterKnife.bind(this);
        context = GoodsDetailsActivity.this;
        initView();
        initData();
    }

    private void initView() {

    }

    GoodsDetailsBean mGoodsDetail;

    private void initData() {
        goodsId = getIntent().getIntExtra(I.GoodsDetails.KEY_GOODS_ID, 0);
        if (goodsId > 0) {
            NetDao.downloadGoodDetails(GoodsDetailsActivity.this, goodsId, new OkHttpUtils.OnCompleteListener<GoodsDetailsBean>() {
                @Override
                public void onSuccess(GoodsDetailsBean result) {
                    if (result != null) {
                        mGoodsDetail = result;
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
        } else {
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
        slideAuto.startPlayLoop(flowIndicator, getAlbumImgUrl(), getAlbumSize());
        wvGoodBrief.loadDataWithBaseURL(null, mGoodsDetail.getGoodsBrief(), I.TEXT_HTML, "utf-8", null);
    }

    //得到商品图片个数
    private int getAlbumSize() {
        if (mGoodsDetail.getProperties() != null && mGoodsDetail.getProperties().size() > 0) {
            return mGoodsDetail.getProperties().get(0).getAlbums().size();
        }
        return 0;
    }

    //得到商品图片集
    private String[] getAlbumImgUrl() {
        String[] albumImgUrls = null;
        if (mGoodsDetail.getProperties() != null && mGoodsDetail.getProperties().size() > 0) {
            List<AlbumsBean> albumsBeanList = mGoodsDetail.getProperties().get(0).getAlbums();
            albumImgUrls = new String[albumsBeanList.size()];
            for (int i = 0; i < albumsBeanList.size(); i++) {
                albumImgUrls[i] = albumsBeanList.get(i).getImgUrl();
            }
        }
        return albumImgUrls;
    }

    @OnClick(R.id.ivBack)
    public void oBackClick() {
        MFGT.finish(this);
    }

    @Override
    public void onBackPressed() {
        MFGT.finish(this);
    }

    @Override
    protected void onResume() {
        super.onResume();
        isCollected();
    }

    //判断该商品是否被收藏
    boolean isCollected = false;

    private void isCollected() {
        UserAvatar user = FuLiCenterApplication.getUser();
        if (user != null) {
            NetDao.isCollect(context, goodsId, user.getMuserName(), new OkHttpUtils.OnCompleteListener<MessageBean>() {
                @Override
                public void onSuccess(MessageBean result) {
                    if (result.isSuccess() && result != null) {
                        isCollected = true;
                    } else {
                        isCollected = false;
                    }
                    updateGoodCollectStatus();
                }

                @Override
                public void onError(String error) {
                    isCollected = false;
                    updateGoodCollectStatus();
                }
            });
        } else {
            updateGoodCollectStatus();
        }
    }

    //
    private void updateGoodCollectStatus() {
        if (isCollected) {
            ivCollect.setImageResource(R.mipmap.bg_collect_out);
        } else {
            ivCollect.setImageResource(R.mipmap.bg_collect_in);
        }
    }

    @OnClick({R.id.ivShare, R.id.ivCollect})
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.ivShare:
                break;
            case R.id.ivCollect:
                final UserAvatar user = FuLiCenterApplication.getUser();
                if(user==null){
                    MFGT.gotoLoginActivity((Activity) context);
                }else{
                    if(isCollected){
                        NetDao.deleteCollectGood(context, goodsId, user.getMuserName(), new OkHttpUtils.OnCompleteListener<MessageBean>() {
                            @Override
                            public void onSuccess(MessageBean result) {
                                CommonUtils.showShortToast(result.getMsg());
                                isCollected=!isCollected;
                                updateGoodCollectStatus();
                            }

                            @Override
                            public void onError(String error) {

                            }
                        });
                    }else{
                        NetDao.addCollectGood(context, goodsId, user.getMuserName(), new OkHttpUtils.OnCompleteListener<MessageBean>() {
                            @Override
                            public void onSuccess(MessageBean result) {
                                CommonUtils.showShortToast(result.getMsg());
                                isCollected=!isCollected;
                                updateGoodCollectStatus();
                            }

                            @Override
                            public void onError(String error) {

                            }
                        });
                    }
                }
                break;
        }
    }
}
