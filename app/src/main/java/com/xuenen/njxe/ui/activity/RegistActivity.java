package com.xuenen.njxe.ui.activity;

import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.Toast;

import com.xuenen.njxe.R;
import com.xuenen.njxe.bean.User;
import com.xuenen.njxe.common.BaseActivity;
import com.xuenen.njxe.constant.C;
import com.xuenen.njxe.utils.LogUtils;
import com.xuenen.njxe.utils.OkHttpUtil;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.Response;

public class RegistActivity extends BaseActivity {
    private EditText etAccount;
    private EditText etUserName;
    private EditText etPassword;
    private EditText etAgainpassword;
    private Spinner spSection;
    private EditText etPhone;
    private EditText etEmail;
    private Button regist;
    private Button cacle;
    private User user;
    private Map<String, String> map = new HashMap<>();
    private List<String> keys, values;
    private String s;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_regist);
        getSupportActionBar().setTitle("档案利用预约系统注册");

        etAccount = (EditText) findViewById(R.id.et_account);
        etUserName = (EditText) findViewById(R.id.et_username);
        etPassword = (EditText) findViewById(R.id.et_password);
        etAgainpassword = (EditText) findViewById(R.id.et_againpassword);
        spSection = (Spinner) findViewById(R.id.sp_section);
        etPhone = (EditText) findViewById(R.id.et_phone);
        etEmail = (EditText) findViewById(R.id.et_email);
        regist = (Button) findViewById(R.id.btn_regidt);
        cacle = (Button) findViewById(R.id.btn_cacle);
        user = new User();

        OkHttpUtil.doGet("http://192.168.3.30:8080/jyyy/mobile/findDicByDmdw.do", new Callback() {
            @Override
            public void onFailure(Call call, IOException e) {
                runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        Toast.makeText(RegistActivity.this, "部门数据异常", Toast.LENGTH_LONG).show();
                    }
                });
            }

            @Override
            public void onResponse(Call call, Response response) throws IOException {
                try {
                    final JSONObject jsonObject = new JSONObject(response.body().string());
                    LogUtils.w(jsonObject.toString());
                    runOnUiThread(new Runnable() {
                        @Override
                        public void run() {
                            try {
                                s = jsonObject.getString("data");
                                JSONArray js = new JSONArray(s);
                                keys = new ArrayList<>();
                                values = new ArrayList<>();
                                for (int i = 0; i < js.length(); i++) {
                                    String sec = js.getString(i);
                                    System.out.println(sec);
                                    String key = new JSONObject(sec).getString("KEY");
                                    String vavle = new JSONObject(sec).getString("VALUE");
                                    keys.add(key);
                                    values.add(vavle);
                                }
                                runOnUiThread(new Runnable() {
                                    @Override
                                    public void run() {
                                        ArrayAdapter<String> adapter = new ArrayAdapter<String>(RegistActivity.this,
                                                android.R.layout.simple_spinner_item, values);
                                        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
                                        spSection.setAdapter(adapter);
                                        spSection.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
                                            @Override
                                            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                                                map.put("orgid", parent.getAdapter().getItem(position).toString());
                                                user.setOrgid(parent.getAdapter().getItem(position).toString());
                                                System.out.println(values.get(position));
//                                                Toast.makeText(RegistActivity.this, values.get(position), Toast.LENGTH_SHORT).show();
                                                // TODO: 2016/12/15 0015 设置为对应的数据字典编号
                                            }

                                            @Override
                                            public void onNothingSelected(AdapterView<?> parent) {

                                            }
                                        });
                                    }
                                });
                            } catch (JSONException e) {
                                e.printStackTrace();
                            }
                        }
                    });
                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }
        });


        regist.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (!TextUtils.isEmpty(etAccount.getText().toString())) {
                    user.setAccount(etAccount.getText().toString());
                    map.put("account", etAccount.getText().toString());
                    System.out.println(etAccount.getText().toString());
                } else {
                    Toast.makeText(RegistActivity.this, "账号不能为空", Toast.LENGTH_SHORT).show();
                    return;
                }
                if (!TextUtils.isEmpty(etUserName.getText().toString())) {
                    user.setUsername(etUserName.getText().toString());
                    map.put("username", etUserName.getText().toString());
                } else {
                    Toast.makeText(RegistActivity.this, "用户名不能为空", Toast.LENGTH_SHORT).show();
                    return;
                }

                if (etPassword.getText().toString().equals(etAgainpassword.getText().toString()) && (!TextUtils.isEmpty(etPassword.getText().toString()) || !TextUtils.isEmpty(etAgainpassword.getText().toString()))) {
                    user.setPassword(etPassword.getText().toString());
                    map.put("password", etPassword.getText().toString());
                } else {
                    Toast.makeText(RegistActivity.this, "您两次输入的密码不一样,请重新输入", Toast.LENGTH_SHORT).show();
                    return;
                }

                String phone = etPhone.getText().toString();
                if (!TextUtils.isEmpty(phone)) {
                    Pattern p = Pattern.compile("^((13[0-9])|(15[^4,\\D])|(18[0,5-9]))\\d{8}$");
                    Matcher m = p.matcher(phone);
                    if (m.matches()) {
                        user.setPhone(phone);
                        map.put("phone", phone);
                    } else {
                        Toast.makeText(RegistActivity.this, "电话格式不正确", Toast.LENGTH_SHORT).show();
                        return;
                    }
                }else {
                    Toast.makeText(RegistActivity.this, "联系电话不能为空", Toast.LENGTH_SHORT).show();
                }
                String email = etEmail.getText().toString();
                if (!TextUtils.isEmpty(email)) {
                    Pattern p = Pattern.compile("^([a-z0-9A-Z]+[-|_|\\.]?)+[a-z0-9A-Z]@([a-z0-9A-Z]+(-[a-z0-9A-Z]+)?\\.)+[a-zA-Z]{2,}$");
                    Matcher m = p.matcher(email);
                    if (m.matches()) {
                        user.setEmail(email);
                        map.put("email", email);
                    } else {
                        Toast.makeText(RegistActivity.this, "邮箱不能为空", Toast.LENGTH_SHORT).show();
                        return;
                    }

                }else {
                    Toast.makeText(RegistActivity.this, "邮箱格式不正确", Toast.LENGTH_SHORT).show();
                }
                OkHttpUtil.doPost(C.URL.addURL, map, new Callback() {
                    @Override
                    public void onFailure(Call call, IOException e) {

                    }

                    @Override
                    public void onResponse(Call call, Response response) throws IOException {

                        try {
                            final JSONObject jsonObject = new JSONObject(response.body().string());
                            System.out.println(jsonObject.toString());
                            if ("0".equals(jsonObject.getString("requestcode"))) {
                                runOnUiThread(new Runnable() {
                                    @Override
                                    public void run() {

                                        System.out.println(user.toString());
                                        Toast.makeText(RegistActivity.this, "注册成功", Toast.LENGTH_SHORT).show();
                                        startActivity(new Intent(RegistActivity.this, LoginActivity.class));
                                    }
                                });
                            } else {
                                runOnUiThread(new Runnable() {
                                    @Override
                                    public void run() {
                                        try {
                                            Toast.makeText(RegistActivity.this, jsonObject.getString("message"), Toast.LENGTH_SHORT).show();
                                        } catch (JSONException e) {
                                            e.printStackTrace();
                                        }
                                    }
                                });
                            }
                        } catch (JSONException e) {
                            e.printStackTrace();
                        }
                    }
                });
//                System.out.println(user.toString());
//                Toast.makeText(RegistActivity.this, "注册成功", Toast.LENGTH_SHORT).show();
//                startActivity(new Intent(RegistActivity.this, LoginActivity.class));
            }
        });
        cacle.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(RegistActivity.this, LoginActivity.class));
            }
        });
    }

}
