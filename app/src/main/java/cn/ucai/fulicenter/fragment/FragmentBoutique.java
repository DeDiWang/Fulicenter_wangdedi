package cn.ucai.fulicenter.fragment;

import android.content.Context;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.widget.SwipeRefreshLayout;
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
import cn.ucai.fulicenter.adapter.BoutiqueAdapter;
import cn.ucai.fulicenter.bean.BoutiqueBean;
import cn.ucai.fulicenter.net.NetDao;
import cn.ucai.fulicenter.utils.OkHttpUtils;
import cn.ucai.fulicenter.view.SpaceItemDecoration;

/**
 * A simple {@link Fragment} subclass.
 */
public class FragmentBoutique extends Fragment {

    @BindView(R.id.tvRefreshHint)
    TextView tvRefreshHint;
    @BindView(R.id.rvBoutique)
    RecyclerView rvBoutique;
    @BindView(R.id.srl)
    SwipeRefreshLayout srl;

    ArrayList<BoutiqueBean> boutiqueList;
    BoutiqueAdapter mAdapter;
    LinearLayoutManager layoutManager;

    public FragmentBoutique() {

    }
    Context context;
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View layout = inflater.inflate(R.layout.fragment_boutique, container, false);
        ButterKnife.bind(this, layout);
        context=getContext();
        initView();
        downloadBoutiques();
        return layout;
    }

    private void downloadBoutiques() {
        final OkHttpUtils<BoutiqueBean[]> utils = new OkHttpUtils<>(context);
        NetDao.downloadBoutique(context, new OkHttpUtils.OnCompleteListener<BoutiqueBean[]>() {
            @Override
            public void onSuccess(BoutiqueBean[] result) {
                srl.setRefreshing(false);
                if(result!=null){
                    ArrayList<BoutiqueBean> boutiqueList = utils.array2List(result);
                    mAdapter.initBoutiqueList(boutiqueList);
                }
            }

            @Override
            public void onError(String error) {

            }
        });
    }

    private void initView() {
        boutiqueList=new ArrayList<>();
        mAdapter=new BoutiqueAdapter(context,boutiqueList);
        rvBoutique.setAdapter(mAdapter);
        layoutManager=new LinearLayoutManager(context);
        rvBoutique.setLayoutManager(layoutManager);

        rvBoutique.setHasFixedSize(true);
        rvBoutique.addItemDecoration(new SpaceItemDecoration(10));
    }

}
