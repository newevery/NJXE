package com.xuenen.njxe.ui.activity;

import android.content.Context;
import android.os.Bundle;
import android.support.v7.widget.CardView;
import android.text.TextUtils;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.alibaba.fastjson.JSON;
import com.xuenen.njxe.R;
import com.xuenen.njxe.bean.Checkin;
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

public class BorrowActivity extends BaseActivity implements View.OnClickListener {


    private Spinner spArchiveType;//档案类型
    private Spinner spBorrowType;//借阅方式
    private Spinner spSectionType;//部门单位
    private Spinner spCardType;//证件类型
    private Spinner spDaLyMd;//
    private View frLayout;//
    private EditText etName;
    private EditText etPhone;
    private EditText etCardNum;
    private EditText etDayNum;
    private String name;
    private String phone;
    private String cardNum;
    private String dayNum;
    private LinearLayout lv1, lv2, lv3, lv4;
    private TextView tv1, tv2, tv3, tv4;
    private EditText et1, et2, et3, et4;
    private String s1, s2, s3, s4;
    private Button btnSummit;
    private JSONObject json = new JSONObject();
    private List<String> keys, values;
    private String sec;
    private int p;
    private int userId;
    private Checkin checkin = new Checkin();
    private Map<String, String> map = new HashMap<String, String>();
    private String userName;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_borrow);
        Init();

        SetData();
        btnSummit.setOnClickListener(this);
    }

    private void SetData() {
        OkHttpUtil.doGet(C.URL.finDicByDmdwURL, new Callback() {
            @Override
            public void onFailure(Call call, IOException e) {
                runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        Toast.makeText(BorrowActivity.this, "部门数据异常", Toast.LENGTH_LONG).show();
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
                                String s = jsonObject.getString("data");
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
                                        ArrayAdapter<String> adapter = new ArrayAdapter<String>(BorrowActivity.this,
                                                android.R.layout.simple_spinner_item, values);
                                        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
                                        spSectionType.setAdapter(adapter);
                                        spSectionType.setSelection(0);
                                        spSectionType.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
                                            @Override
                                            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                                                sec = parent.getAdapter().getItem(position).toString();
                                                System.out.println(values.get(position));
                                                p = position;
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
        spCardType.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                String[] cgeZj = getResources().getStringArray(R.array.cgeZj);
                String cardType = cgeZj[position];
                checkin.setZjlx(cardType);
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });
        spDaLyMd.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                String[] dalymd = getResources().getStringArray(R.array.dalLyMd);
                String lymd = dalymd[position];
                checkin.setDalymd(lymd);
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });
        spBorrowType.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                String[] borrowType = getResources().getStringArray(R.array.daJYFS);
                String jyfs = borrowType[position];
                checkin.setJyfs(Integer.valueOf(position + 1));
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });
        spArchiveType.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                et1.setText("");
                et2.setText("");
                et3.setText("");
                et4.setText("");
                switch (position) {
                    case 0://文件查阅
                        lv1.setVisibility(View.VISIBLE);
                        tv1.setText("查询内容  ：");
                        lv2.setVisibility(View.GONE);
                        lv3.setVisibility(View.GONE);
                        lv4.setVisibility(View.GONE);
                        break;
                    case 1://录取名册
                        lv1.setVisibility(View.VISIBLE);
                        lv2.setVisibility(View.VISIBLE);
                        lv3.setVisibility(View.VISIBLE);
                        lv4.setVisibility(View.VISIBLE);
                        tv1.setText("生源省份");
                        tv2.setText("专业   ：");
                        tv3.setText("录取年份  ：");
                        break;
                    case 2://成绩查询
                        lv1.setVisibility(View.VISIBLE);
                        lv2.setVisibility(View.VISIBLE);
                        lv3.setVisibility(View.VISIBLE);
                        lv4.setVisibility(View.VISIBLE);
                        tv1.setText("专业 :");
                        tv2.setText("录取年份  ：");
                        tv3.setText("学院 :");
                        tv4.setText("毕业年份  ：");
                        break;
                    case 3://基建档案
                        lv1.setVisibility(View.VISIBLE);
                        lv2.setVisibility(View.GONE);
                        lv3.setVisibility(View.GONE);
                        lv4.setVisibility(View.GONE);
                        tv1.setText("项目信息 :");
                        break;
                    case 4://科研档案
                        lv1.setVisibility(View.VISIBLE);
                        lv2.setVisibility(View.GONE);
                        lv3.setVisibility(View.GONE);
                        lv4.setVisibility(View.GONE);
                        tv1.setText("项目信息 :");
                        break;
                    case 5://声响档案
                        lv1.setVisibility(View.VISIBLE);
                        lv2.setVisibility(View.VISIBLE);
                        lv3.setVisibility(View.VISIBLE);
                        lv4.setVisibility(View.VISIBLE);
                        tv1.setText("时间  : ");
                        tv2.setText("地点  : ");
                        tv3.setText("人物  : ");
                        tv4.setText("事件  : ");
                        break;
                    case 6://毕业名册
                        lv1.setVisibility(View.VISIBLE);
                        lv2.setVisibility(View.VISIBLE);
                        lv3.setVisibility(View.GONE);
                        lv4.setVisibility(View.GONE);
                        tv1.setText("专业 :");
                        tv2.setText("专业年份 :");
                        break;
                    case 7://其他档案
                        lv1.setVisibility(View.VISIBLE);
                        lv2.setVisibility(View.GONE);
                        lv3.setVisibility(View.GONE);
                        lv4.setVisibility(View.GONE);
                        tv1.setText("主題/关键词 :");
                        break;
                    case 8://财会内容
                        lv1.setVisibility(View.VISIBLE);
                        lv2.setVisibility(View.VISIBLE);
                        lv3.setVisibility(View.GONE);
                        lv4.setVisibility(View.GONE);
                        tv1.setText("财会凭证号   :");
                        tv2.setText("财会档案内容  :");
                        break;
                }
                String[] daLx = getResources().getStringArray(R.array.daLx);
                String dalx = daLx[position];
                checkin.setCaseType(Integer.valueOf(position + 1));
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });
    }

    private void Init() {
        getSupportActionBar().setTitle("借阅");


        frLayout = findViewById(R.id.fr_layout);
//        cvBottom = (CardView) findViewById(R.id.cv_fragmentbottom);
//        cvArchiveType = (CardView) frLayout.findViewById(R.id.cv_archiveType);
//        tvDoType = (TextView) findViewById(R.id.tv_doType);
//        tvCardNum = (TextView) frLayout.findViewById(R.id.tv_cardNum);
//        tvPeopleType = (TextView) frLayout.findViewById(R.id.tv_peopleType);
//        cvBusinissType = (CardView) frLayout.findViewById(R.id.cv_businissType);
//        tvBusinessType = (TextView) frLayout.findViewById(R.id.tv_businessType);
//        tvToaim = (TextView) frLayout.findViewById(R.id.tv_toaim);
//        tvDaynum = (TextView) frLayout.findViewById(R.id.tv_daynum);
//        cvPeopleType = (CardView) frLayout.findViewById(R.id.cv_peopleType);
        userName = getSharedPreferences("userInfo", Context.MODE_WORLD_READABLE).getString("USERNAME", "");
        userId = getSharedPreferences("userInfo", Context.MODE_WORLD_READABLE).getInt("USERID", 0);
        spArchiveType = (Spinner) frLayout.findViewById(R.id.sp_archiveType);//档案类型
        spBorrowType = (Spinner) frLayout.findViewById(R.id.sp_borrowType);//借阅方式
        etName = (EditText) frLayout.findViewById(R.id.et_name);//姓名
        etName.setText(userName);
        etPhone = (EditText) frLayout.findViewById(R.id.et_phone);//电话
        etCardNum = (EditText) frLayout.findViewById(R.id.et_cardnum);//证件号码

        spSectionType = (Spinner) frLayout.findViewById(R.id.sp_sectionType);//部门单位
        spCardType = (Spinner) frLayout.findViewById(R.id.sp_cardType);//证件类型
        spDaLyMd = (Spinner) frLayout.findViewById(R.id.sp_daLyMd);//档案利用目的
        lv1 = (LinearLayout) findViewById(R.id.lv1);
        lv2 = (LinearLayout) findViewById(R.id.lv2);
        lv3 = (LinearLayout) findViewById(R.id.lv3);
        lv4 = (LinearLayout) findViewById(R.id.lv4);
        tv1 = (TextView) findViewById(R.id.tv_1);
        tv2 = (TextView) findViewById(R.id.tv_2);
        tv3 = (TextView) findViewById(R.id.tv_3);
        tv4 = (TextView) findViewById(R.id.tv_4);
        et1 = (EditText) findViewById(R.id.et_1);
        et2 = (EditText) findViewById(R.id.et_2);
        et3 = (EditText) findViewById(R.id.et_3);
        et4 = (EditText) findViewById(R.id.et_4);
        btnSummit = (Button) findViewById(R.id.btn_summit);
    }

    @Override
    public void onClick(View v) {
        etDayNum = (EditText) frLayout.findViewById(R.id.et_daynum);//借阅天数
        name = etName.getText().toString();
        phone = etPhone.getText().toString();
        cardNum = etCardNum.getText().toString();
        dayNum = etDayNum.getText().toString();
        if (TextUtils.isEmpty(name)) {
            Toast.makeText(BorrowActivity.this, "名字不能为空", Toast.LENGTH_SHORT).show();
            return;
        } else {
            checkin.setCyrxm(name);
        }
        if (TextUtils.isEmpty(phone)) {
            Toast.makeText(BorrowActivity.this, "电话不能为空", Toast.LENGTH_SHORT).show();
            return;
        } else {
            Pattern p = Pattern.compile("^((13[0-9])|(15[^4,\\D])|(18[0,5-9]))\\d{8}$");
            Matcher m = p.matcher(phone);
            if (m.matches()) {
                checkin.setLxdh(phone);
            } else {
                Toast.makeText(BorrowActivity.this, "电话格式不正确", Toast.LENGTH_SHORT).show();
                return;
            }
        }

        s1 = et1.getText().toString();
        s2 = et2.getText().toString();
        s3 = et3.getText().toString();
        s4 = et4.getText().toString();
        switch (p) {
            case 0:
                if (TextUtils.isEmpty(s1)) {
                    Toast.makeText(BorrowActivity.this, "请填写完整信息", Toast.LENGTH_SHORT).show();
                    return;
                } else {
                    checkin.setCxnr(s1);
                }
                break;
            case 1:
                if (TextUtils.isEmpty(s1) || TextUtils.isEmpty(s2) || TextUtils.isEmpty(s3)) {
                    Toast.makeText(BorrowActivity.this, "请填写完整信息", Toast.LENGTH_SHORT).show();
                    return;
                } else {
                    checkin.setProvince(s1);
                    checkin.setSpecialty(s2);
                    checkin.setStartYear(s3);
                }
                break;
            case 2:
                if (TextUtils.isEmpty(s1) || TextUtils.isEmpty(s2) || TextUtils.isEmpty(s3) || TextUtils.isEmpty(s4)) {
                    Toast.makeText(BorrowActivity.this, "请填写完整信息", Toast.LENGTH_SHORT).show();
                    return;
                } else {
                    checkin.setSpecialty(s1);
                    checkin.setStartYear(s2);
                    checkin.setCollege(s3);
                    checkin.setEndYear(s4);
                }
                break;
            case 3:
                if (TextUtils.isEmpty(s1)) {
                    Toast.makeText(BorrowActivity.this, "请填写完整信息", Toast.LENGTH_SHORT).show();
                    return;
                } else {
                    checkin.setProjectInfo(s1);

                }
                break;
            case 4:
                if (TextUtils.isEmpty(s1)) {
                    Toast.makeText(BorrowActivity.this, "请填写完整信息", Toast.LENGTH_SHORT).show();
                    return;
                } else {
                    checkin.setProjectInfo(s1);

                }
                break;
            case 5:
                if (TextUtils.isEmpty(s1) || TextUtils.isEmpty(s2) || TextUtils.isEmpty(s3) || TextUtils.isEmpty(s4)) {
                    Toast.makeText(BorrowActivity.this, "请填写完整信息", Toast.LENGTH_SHORT).show();
                    return;
                } else {
                    checkin.setSoundTime(s1);
                    checkin.setSoundPlace(s2);
                    checkin.setSoundPeople(s3);
                    checkin.setSoundThing(s4);
                }
                break;
            case 6:
                if (TextUtils.isEmpty(s1) || TextUtils.isEmpty(s2)) {
                    Toast.makeText(BorrowActivity.this, "请填写完整信息", Toast.LENGTH_SHORT).show();
                    return;
                } else {
                    checkin.setSpecialty(s1);
                    checkin.setEndYear(s2);

                }
                break;
            case 7:
                if (TextUtils.isEmpty(s1)) {
                    Toast.makeText(BorrowActivity.this, "请填写完整信息", Toast.LENGTH_SHORT).show();
                    return;
                } else {
                    checkin.setTheme(s1);

                }
                break;
            case 8:
                if (TextUtils.isEmpty(s1) || TextUtils.isEmpty(s2)) {
                    Toast.makeText(BorrowActivity.this, "请填写完整信息", Toast.LENGTH_SHORT).show();
                    return;
                } else {
                    checkin.setAccountNum(s1);
                    checkin.setAccountContent(s2);

                }
                break;
            default:
                break;
        }
        System.out.println(s1 + "  " + s2 + "   " + s3 + "   " + s4);
        System.out.println("借阅 " + checkin);
        checkin.setBmdw(keys.get(p));
        String checkinString = JSON.toJSONString(checkin);
        checkin.setUserid(Integer.valueOf(userId));
        map.put("userName", userName);
        map.put("userId", userId + "");
        map.put("checkinString", checkinString);
        OkHttpUtil.doPost(C.URL.addCheckin, map, new Callback() {
            @Override
            public void onFailure(Call call, IOException e) {
                runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        Toast.makeText(BorrowActivity.this, "连接服务器失败", Toast.LENGTH_SHORT).show();
                    }
                });
            }

            @Override
            public void onResponse(Call call, Response response) throws IOException {
                try {
                    JSONObject jsonObject=new JSONObject(response.body().string());
                    if ("0".equals(jsonObject.getString("requestcode"))){
                        Toast.makeText(BorrowActivity.this, "提交借阅成功", Toast.LENGTH_SHORT).show();
                    }else {
                        Toast.makeText(BorrowActivity.this, "服务器异常", Toast.LENGTH_SHORT).show();
                    }
                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }
        });

    }
}
