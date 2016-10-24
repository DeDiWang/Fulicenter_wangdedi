package cn.ucai.fulicenter.fragment;

import android.app.Activity;
import android.content.Context;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import butterknife.BindView;
import butterknife.ButterKnife;
import cn.ucai.fulicenter.Dao.SharedPreferencesUtils;
import cn.ucai.fulicenter.FuLiCenterApplication;
import cn.ucai.fulicenter.R;
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
        L.e(TAG+" user=="+user);
        if(user!=null){
            tvNick.setText(user.getMuserNick());
            ImageLoader.setAvatar(user,mContext,ivUserThumb);
        }else{
            MFGT.gotoLoginActivity((Activity) mContext);
        }
    }
}
