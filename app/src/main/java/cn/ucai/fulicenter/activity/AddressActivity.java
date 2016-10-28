package cn.ucai.fulicenter.activity;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.text.TextUtils;
import android.view.View;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import java.lang.reflect.Array;
import java.util.Arrays;
import java.util.concurrent.TimeUnit;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import cn.ucai.fulicenter.R;
import cn.ucai.fulicenter.utils.CommonUtils;
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
                String userName = etName.getText().toString();
                if(TextUtils.isEmpty(userName)){
                    etName.setError("用户名不能为空");
                    etName.requestFocus();
                    return;
                }
                String number = etNumber.getText().toString();
                if(TextUtils.isEmpty(number)){
                    etNumber.setText("手机号不能为空");
                    etNumber.requestFocus();
                    return;
                }
                if(!number.matches("[\\d]{11}")){
                    etNumber.setError("手机号格式不正确");
                    etNumber.requestFocus();
                    return;
                }
                String address = etAddress.getText().toString();
                if(TextUtils.isEmpty(address)){
                    etAddress.setError("地址不能为空");
                    etAddress.requestFocus();
                    return;
                }
                CommonUtils.showLongToast(orderPrice+"元");
                break;
        }
    }

}
