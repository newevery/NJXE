package com.xuenen.njxe.ui.adapter;

import android.app.Activity;
import android.app.Fragment;
import android.content.Intent;
import android.graphics.Color;
import android.text.Layout;
import android.widget.BaseAdapter;

/**
 * Created by Administrator on 2016/12/14 0014.
 */


import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.xuenen.njxe.R;
import com.xuenen.njxe.bean.Check;
import com.xuenen.njxe.bean.Checkin;
import com.xuenen.njxe.ui.activity.DetialActivity;
import com.xuenen.njxe.utils.PrettyDate;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.List;


public class CheckinItemAdapter extends BaseAdapter {

    private List<Check> objects;
    private Context context;


    public CheckinItemAdapter(Context context, List<Check> objects) {
        this.context = context;
        this.objects = objects;
    }

    @Override
    public int getCount() {
        return objects.size();
    }

    @Override
    public Check getItem(int position) {
        return objects.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(final int position, View convertView, ViewGroup parent) {
        if (convertView == null) {
            convertView = LayoutInflater.from(context).inflate(R.layout.item_checkin, null);
            convertView.setMinimumHeight(30);
            convertView.setTag(new ViewHolder(convertView));

        }
        convertView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent=new Intent((Activity)context, DetialActivity.class);
                JSONObject jsonObject=new JSONObject();
                Check item = (Check) getItem(position);
                try {
                    jsonObject.put("id",item.getId()+"");
                    jsonObject.put("jyfs",item.getJyfs());
                    jsonObject.put("cyrxm",item.getCyrxm());
                    jsonObject.put("djsj",item.getDjsj());
                    jsonObject.put("dalymd",item.getDalymd());
                } catch (JSONException e) {
                    e.printStackTrace();
                }
                intent.putExtra("check", jsonObject.toString());
                context.startActivity(intent);
            }
        });
        initializeViews((Check) getItem(position), (ViewHolder) convertView.getTag(),position);
        return convertView;
    }

    private void initializeViews(Check object, ViewHolder holder,int pos) {
        holder.tvId.setText(""+(pos+1));
        holder.tvDJSJ.setText(object.getDjsj());
        if (object.getJyfs() == 1) {
            holder.tvJYFS.setText("实物借阅");
        } else if (object.getJyfs() == 2) {
            holder.tvJYFS.setText("电子借阅");
        } else {
            holder.tvJYFS.setText("馆内借阅");
        }
        holder.tvDALYMD.setText(object.getDalymd());
        holder.tvCYRXM.setText(object.getCyrxm());
    }

    class ViewHolder {
        private TextView tvId,tvCYRXM, tvDALYMD, tvJYFS, tvDJSJ;

        public ViewHolder(View view) {
            tvId= (TextView) view.findViewById(R.id.tv_id);
            tvId.setTextColor(Color.BLACK);
            tvCYRXM = (TextView) view.findViewById(R.id.tv_cyrxm);
            tvCYRXM.setTextColor(Color.BLACK);
            tvDALYMD = (TextView) view.findViewById(R.id.tv_dalymd);
            tvDALYMD.setTextColor(Color.BLACK);
            tvJYFS = (TextView) view.findViewById(R.id.tv_jyfs);
            tvJYFS.setTextColor(Color.BLACK);
            tvDJSJ = (TextView) view.findViewById(R.id.tv_djsj);
            tvDJSJ.setTextColor(Color.BLACK);
        }
    }
}
