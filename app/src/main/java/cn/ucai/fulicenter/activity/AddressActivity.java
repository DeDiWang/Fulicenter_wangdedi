package cn.ucai.fulicenter.activity;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.text.TextUtils;
import android.view.View;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.TextView;

import com.pingplusplus.android.PingppLog;
import com.pingplusplus.libone.PaymentHandler;
import com.pingplusplus.libone.PingppOne;
import org.json.JSONException;
import org.json.JSONObject;
import java.text.SimpleDateFormat;
import java.util.Arrays;
import java.util.Date;
import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import cn.ucai.fulicenter.R;
import cn.ucai.fulicenter.bean.MessageBean;
import cn.ucai.fulicenter.net.NetDao;
import cn.ucai.fulicenter.utils.CommonUtils;
import cn.ucai.fulicenter.utils.L;
import cn.ucai.fulicenter.utils.MFGT;
import cn.ucai.fulicenter.utils.OkHttpUtils;

public class AddressActivity extends AppCompatActivity implements PaymentHandler{
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
    private static String URL = "http://218.244.151.190/demo/charge";
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_address);
        ButterKnife.bind(this);
        String cartIds = getIntent().getStringExtra("cartIds");
        arrCartId = cartIds.substring(0,cartIds.length()).split(",");
        L.e("arrCartId============"+Arrays.toString(arrCartId));
        orderPrice = getIntent().getDoubleExtra("orderPrice", 0);
        initView();

        // 设置要使用的支付方式
        PingppOne.enableChannels(new String[]{"wx", "alipay", "upacp", "cnp", "bfb"});
        //提交数据的格式，默认格式为json
        //PingppOne.CONTENT_TYPE = "application/x-www-form-urlencoded";
        PingppOne.CONTENT_TYPE = "application/json";
        //是否开启日志
        PingppLog.DEBUG = true;
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
                gotoBuy();
                break;
        }
    }

    private void gotoBuy() {
        // 产生个订单号
        String orderNo = new SimpleDateFormat("yyyyMMddhhmmss")
                .format(new Date());

        // 构建账单json对象
        JSONObject bill = new JSONObject();

        try {
            bill.put("order_no", orderNo);
            bill.put("amount", orderPrice*100);
        } catch (JSONException e) {
            e.printStackTrace();
        }
        //壹收款: 创建支付通道的对话框
        PingppOne.showPaymentChannels(getSupportFragmentManager(), bill.toString(), URL, this);
    }

    @Override
    public void handlePaymentResult(Intent data) {
        int resultCode = data.getExtras().getInt("code");
        switch (resultCode){
            case 1:
                paySuccess();
                CommonUtils.showShortToast(getResources().getString(R.string.pingpp_title_activity_pay_sucessed));
                finish();
                break;
            case -1:
                CommonUtils.showShortToast(getResources().getString(R.string.pingpp_pay_failed));
                finish();
                break;
        }
    }

    private void paySuccess() {
        for(String id:arrCartId){
            NetDao.deleteCart(this, Integer.parseInt(id), new OkHttpUtils.OnCompleteListener<MessageBean>() {
                @Override
                public void onSuccess(MessageBean result) {
                    L.e("result====="+result);
                }

                @Override
                public void onError(String error) {

                }
            });
        }
    }
}
