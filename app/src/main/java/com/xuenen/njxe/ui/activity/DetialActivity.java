package com.xuenen.njxe.ui.activity;

import android.os.Bundle;
import android.view.View;
import android.widget.TextView;

import com.google.gson.Gson;
import com.xuenen.njxe.R;
import com.xuenen.njxe.bean.Check;
import com.xuenen.njxe.common.BaseActivity;
import com.xuenen.njxe.constant.C;
import com.xuenen.njxe.utils.LogUtils;
import com.xuenen.njxe.utils.OkHttpUtil;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.Response;

public class DetialActivity extends BaseActivity {

    private TextView cyrxm;
    private TextView lxdh;
    private TextView flag;
    private TextView zjlx;
    private TextView zjhm;
    private TextView bmdw;
    private TextView dalymd;
    private TextView djsj;
    private TextView cxnr;
    private TextView jydh;
    private TextView bz;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detial);

        cyrxm = (TextView) findViewById(R.id.tv_cyrxm);
        lxdh = (TextView) findViewById(R.id.tv_lxdh);
        flag = (TextView) findViewById(R.id.tv_flag);
        zjlx = (TextView) findViewById(R.id.tv_zjlx);
        zjhm = (TextView) findViewById(R.id.tv_zjhm);
        bmdw = (TextView) findViewById(R.id.tv_bmdw);
        dalymd = (TextView) findViewById(R.id.tv_dalymd);
        djsj = (TextView) findViewById(R.id.tv_djsj);
        cxnr = (TextView) findViewById(R.id.tv_cxnr);
        jydh = (TextView) findViewById(R.id.tv_jydh);
        bz = (TextView) findViewById(R.id.tv_bz);

        String s = getIntent().getStringExtra("check");
        System.out.println("SS    " + s);
//        Check check = new Check();
        try {
            JSONObject jsonObject1 = new JSONObject(s);
//            check.setCyrxm(jsonObject1.getString("cyrxm"));
//            check.setDalymd(jsonObject1.getString("dalymd"));
//            check.setDjsj(jsonObject1.getString("djsj"));
//            check.setId(jsonObject1.getInt("id"));
//            check.setJyfs(jsonObject1.getInt("jyfs"));
            Map<String, String> map = new HashMap<>();
            map.put("id", jsonObject1.getInt("id") + "");
            System.out.println("ID " + jsonObject1.getInt("id"));
            OkHttpUtil.doPost(C.URL.findOneCheckin, map, new Callback() {
                @Override
                public void onFailure(Call call, IOException e) {

                }

                @Override
                public void onResponse(Call call, Response response) throws IOException {
                    try {

                        String data = (new JSONObject(response.body().string())).getString("data");
                        System.out.println("SSssss" + data);
                        final JSONObject jsonObject = new JSONObject(data);
                        System.out.println(jsonObject);


                        runOnUiThread(new Runnable() {
                            @Override
                            public void run() {
                                try {
                                    cyrxm.setText(jsonObject.getString("cyrxm"));
                                    lxdh.setText(jsonObject.getString("lxdh"));
                                    flag.setText(jsonObject.getString("flag"));
                                    zjlx.setText(jsonObject.getString("zjlx"));
                                    zjhm.setText(jsonObject.getString("zjhm"));
                                    bmdw.setText(jsonObject.getString("bmdw"));
                                    dalymd.setText(jsonObject.getString("dalymd"));
                                    djsj.setText(jsonObject.getString("djsj"));
                                    cxnr.setText(jsonObject.getString("cxnr"));
                                    jydh.setText(jsonObject.getString("jydh"));
                                    bz.setText(jsonObject.getString("bz"));
                                } catch (JSONException e) {
                                    e.printStackTrace();
                                }
                            }
                        });
                    } catch (JSONException e) {
                        e.printStackTrace();
                    }
                    ;
                }
            });

        } catch (JSONException e) {
            e.printStackTrace();
        }
    }
}
