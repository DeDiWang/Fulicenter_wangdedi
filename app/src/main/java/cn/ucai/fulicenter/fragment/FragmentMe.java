package cn.ucai.fulicenter.fragment;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import cn.ucai.fulicenter.FuLiCenterApplication;
import cn.ucai.fulicenter.R;
import cn.ucai.fulicenter.activity.SettingActivity;
import cn.ucai.fulicenter.bean.UserAvatar;
import cn.ucai.fulicenter.utils.ImageLoader;
import cn.ucai.fulicenter.utils.L;
import cn.ucai.fulicenter.utils.MFGT;

/**
 * A simple {@link Fragment} subclass.
 */
public class FragmentMe extends Fragment {
    private static final String TAG = FragmentMe.class.getSimpleName();
    @BindView(R.id.ivUserThumb)
    ImageView ivUserThumb;
    @BindView(R.id.tvNick)
    TextView tvNick;
    @BindView(R.id.goodsCount)
    TextView goodsCount;
    @BindView(R.id.shopsCount)
    TextView shopsCount;
    @BindView(R.id.footCount)
    TextView footCount;

    public FragmentMe() {
        // Required empty public constructor
    }

    Context mContext;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_me, container, false);
        ButterKnife.bind(this, view);
        mContext = getActivity();
        initData();
        return view;
    }

    private void initData() {
        UserAvatar user = FuLiCenterApplication.getUser();
        L.e(TAG + " user==" + user);
        if (user != null) {
            tvNick.setText(user.getMuserNick());
            ImageLoader.setAvatar(ImageLoader.getAvatarUrl(user), mContext, ivUserThumb);
        } else {
            MFGT.gotoLoginActivity((Activity) mContext);
        }
    }

    @OnClick({R.id.tvSetting, R.id.ivMessage, R.id.collectGoods,
            R.id.collectShops, R.id.myFooter, R.id.ivBarcode, R.id.btnShowMyGoods,
            R.id.ivObligation, R.id.ivWaitDeliver, R.id.ivWaitReceipt,
            R.id.ivWaitEvaluate, R.id.ivAfterSafe, R.id.tvMyCardBag,
            R.id.lifeCard, R.id.shopCard, R.id.vipCard})
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.tvSetting:
                startActivity(new Intent(mContext, SettingActivity.class));
                break;
            case R.id.ivMessage:
                break;
            case R.id.collectGoods:
                break;
            case R.id.collectShops:
                break;
            case R.id.myFooter:
                break;
            case R.id.ivBarcode:
                break;
            case R.id.btnShowMyGoods:
                break;
            case R.id.ivObligation:
                break;
            case R.id.ivWaitDeliver:
                break;
            case R.id.ivWaitReceipt:
                break;
            case R.id.ivWaitEvaluate:
                break;
            case R.id.ivAfterSafe:
                break;
            case R.id.tvMyCardBag:
                break;
            case R.id.lifeCard:
                break;
            case R.id.shopCard:
                break;
            case R.id.vipCard:
                break;
        }
    }

    @Override
    public void onResume() {
        super.onResume();
        UserAvatar user = FuLiCenterApplication.getUser();
        if (user!= null) {
            tvNick.setText(user.getMuserNick());

            ImageLoader.setAvatar(ImageLoader.getAvatarUrl(user), mContext, ivUserThumb);
        }
    }
}
