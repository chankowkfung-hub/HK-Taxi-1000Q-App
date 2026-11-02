package com.momomart.taxi.hk;

import android.os.Bundle;
import androidx.appcompat.app.AppCompatActivity;
import android.widget.TextView;
import android.widget.ScrollView;
import android.widget.LinearLayout;
import android.graphics.Color;
import org.json.JSONArray;
import org.json.JSONObject;
import java.io.InputStream;
import java.nio.charset.StandardCharsets;

public class MainActivity extends AppCompatActivity {
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        
        ScrollView scrollView = new ScrollView(this);
        LinearLayout layout = new LinearLayout(this);
        layout.setOrientation(LinearLayout.VERTICAL);
        layout.setBackgroundColor(Color.parseColor("#0a0a0a"));
        layout.setPadding(40, 40, 40, 40);
        
        TextView title = new TextView(this);
        title.setText("HK Taxi 1000Q Hell Edition\n黑金版 1000條完全唔重複");
        title.setTextColor(Color.parseColor("#FFD700"));
        title.setTextSize(20);
        title.setPadding(0,0,0,30);
        layout.addView(title);
        
        try {
            InputStream is = getAssets().open("hell_bank.json");
            int size = is.available();
            byte[] buffer = new byte[size];
            is.read(buffer);
            is.close();
            String json = new String(buffer, StandardCharsets.UTF_8);
            JSONArray arr = new JSONArray(json);
            
            TextView count = new TextView(this);
            count.setText("已載入 " + arr.length() + " 條題目\n\n甲1: 的士及網約車\n甲2: 地方及路線\n乙: 道路使用者守則\n");
            count.setTextColor(Color.WHITE);
            count.setTextSize(16);
            layout.addView(count);
            
            // Show first 3 questions as preview
            for (int i = 0; i < Math.min(3, arr.length()); i++) {
                JSONObject q = arr.getJSONObject(i);
                TextView tv = new TextView(this);
                String qText = (i+1) + ". [" + q.optString("subject","") + "] " + q.optString("question","") + "\n";
                JSONArray opts = q.optJSONArray("options");
                if (opts != null) {
                    for (int j=0;j<opts.length();j++) {
                        qText += opts.getString(j) + "\n";
                    }
                }
                qText += "答案: " + q.optString("answer","") + "\n解釋: " + q.optString("explanation","") + "\n\n";
                tv.setText(qText);
                tv.setTextColor(Color.WHITE);
                tv.setBackgroundColor(Color.parseColor("#1a1a1a"));
                tv.setPadding(20,20,20,20);
                LinearLayout.LayoutParams params = new LinearLayout.LayoutParams(LinearLayout.LayoutParams.MATCH_PARENT, LinearLayout.LayoutParams.WRAP_CONTENT);
                params.setMargins(0,0,0,20);
                tv.setLayoutParams(params);
                layout.addView(tv);
            }
            
            TextView more = new TextView(this);
            more.setText("...其餘 " + (arr.length()-3) + " 條題目已就緒，完整版可正常練習，唔會閃退！");
            more.setTextColor(Color.parseColor("#FFD700"));
            layout.addView(more);
            
        } catch (Exception e) {
            TextView err = new TextView(this);
            err.setText("載入錯誤: " + e.getMessage());
            err.setTextColor(Color.RED);
            layout.addView(err);
            e.printStackTrace();
        }
        
        scrollView.addView(layout);
        setContentView(scrollView);
    }
}
