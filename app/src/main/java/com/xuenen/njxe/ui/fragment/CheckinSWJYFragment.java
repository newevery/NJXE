package com.xuenen.njxe.ui.fragment;

import android.content.Context;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ListView;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.Toast;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import com.xuenen.njxe.R;
import com.xuenen.njxe.bean.Check;
import com.xuenen.njxe.bean.Checkin;
import com.xuenen.njxe.constant.C;
import com.xuenen.njxe.ui.adapter.CheckinItemAdapter;
import com.xuenen.njxe.utils.GsonUtils;
import com.xuenen.njxe.utils.LogUtils;
import com.xuenen.njxe.utils.OkHttpUtil;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.Response;


public class CheckinSWJYFragment extends Fragment {

    private RadioGroup rgCheckin;
    private RadioButton rbCheckin1;
    private RadioButton rbCheckin2;
    private RadioButton rbCheckin3;
    private ListView lvCheckin;

    private String flag, dqf;
    private Map<String, String> map = new HashMap<>();

    @NonNull
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {


        View view = inflater.inflate(R.layout.fragment_checkin_swjy, container, false);
        init(view);

        return view;
    }

    private void init(View view) {
        SharedPreferences userInfo = getActivity().getSharedPreferences("userInfo", Context.MODE_WORLD_READABLE);
        int userid = userInfo.getInt("USERID", 0);

        map.put("userid", "" + userid);
        map.put("jyfs", 2 + "");
        rgCheckin = (RadioGroup) view.findViewById(R.id.rg_checkin);
        rbCheckin1 = (RadioButton) view.findViewById(R.id.rb_checkin1);
        rbCheckin2 = (RadioButton) view.findViewById(R.id.rb_checkin2);
        rbCheckin3 = (RadioButton) view.findViewById(R.id.rb_checkin3);
        lvCheckin = (ListView) view.findViewById(R.id.lv_checkin);
        checkList = new ArrayList<>();


        checkinItemAdapter = new CheckinItemAdapter(getActivity(), checkList);
        lvCheckin.setAdapter(checkinItemAdapter);
        rbCheckin1.setChecked(true);
        checkList.clear();
        checkinItemAdapter.notifyDataSetChanged();
        map.put("flag", 2 + "");
        getData(map);
        rgCheckin.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(RadioGroup group, int checkedId) {
                switch (checkedId) {
                    case R.id.rb_checkin1:
                        checkList.clear();
                        checkinItemAdapter.notifyDataSetChanged();
                        map.put("flag", 2 + "");
                        map.put("dqf",  "");
                        getData(map);
                        break;
                    case R.id.rb_checkin2:
                        checkList.clear();
                        checkinItemAdapter.notifyDataSetChanged();
                        map.put("flag", 1 + "");
                        map.put("dqf",  "");
                        getData(map);
                        break;
                    case R.id.rb_checkin3:
                        checkList.clear();
                        checkinItemAdapter.notifyDataSetChanged();
                        map.put("flag", 2 + "");
                        map.put("dqf", 1 + "");
                        getData(map);
                        break;
                }
            }
        });
    }

    private List<Check> checkList = new ArrayList<>();
    private CheckinItemAdapter checkinItemAdapter;

    private void getData(Map<String, String> map) {
        OkHttpUtil.doPost(C.URL.queryReborrowURL, map, new Callback() {
            @Override
            public void onFailure(Call call, IOException e) {
                getActivity().runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        Toast.makeText(getActivity(), "连接服务器失败", Toast.LENGTH_LONG).show();
                    }
                });
            }

            @Override
            public void onResponse(Call call, Response response) throws IOException {

                try {
                    JSONObject jsonObject = new JSONObject(response.body().string());
                    LogUtils.w(jsonObject.toString());
                    System.out.println(jsonObject.toString());
                    JSONArray js = new JSONArray(jsonObject.getString("data"));
                    for (int i = 0; i < js.length(); i++) {
                        String s = js.getString(i);
                        System.out.println(s);
                        Check check = new Check();

                        JSONObject jsonObject1 = new JSONObject(s);
                        check.setCyrxm(jsonObject1.getString("cyrxm"));
                        check.setDalymd(jsonObject1.getString("dalymd"));
                        check.setDjsj(jsonObject1.getString("djsj"));
                        check.setId(jsonObject1.getInt("id"));
                        check.setJyfs(jsonObject1.getInt("jyfs"));
                        checkList.add(check);
                    }
                    getActivity().runOnUiThread(new Runnable() {
                        @Override
                        public void run() {
                            checkinItemAdapter.notifyDataSetChanged();
                        }
                    });
                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }
        });
    }

}
