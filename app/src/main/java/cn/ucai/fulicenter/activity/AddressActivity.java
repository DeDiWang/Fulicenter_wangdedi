package cn.ucai.fulicenter.activity;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.TextView;

import java.lang.reflect.Array;
import java.util.Arrays;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import cn.ucai.fulicenter.R;
import cn.ucai.fulicenter.utils.L;
import cn.ucai.fulicenter.utils.MFGT;

public class AddressActivity extends AppCompatActivity {
    private final String TAG = AddressActivity.class.getSimpleName();
    @BindView(R.id.etName)
    EditText etName;
    @BindView(R.id.etNumber)
    EditText etNumber;
    @BindView(R.id.etCity)
    Spinner etCity;
    @BindView(R.id.etAddress)
    EditText etAddress;
    @BindView(R.id.tvSum)
    TextView tvSum;

    String[] arrCartId;
    double orderPrice;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_address);
        ButterKnife.bind(this);
        String cartIds = getIntent().getStringExtra("cartIds");
        arrCartId = cartIds.substring(0,cartIds.length()).split(",");
        L.e(TAG+" arrCartId=="+Arrays.toString(arrCartId));
        orderPrice = getIntent().getDoubleExtra("orderPrice", 0);
        initView();
    }

    private void initView() {
        tvSum.setText("￥"+String.valueOf(orderPrice));
    }

    @OnClick({R.id.ivBack, R.id.btnBuy})
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.ivBack:
                MFGT.finish(this);
                break;
            case R.id.btnBuy:

                break;
        }
    }
}
