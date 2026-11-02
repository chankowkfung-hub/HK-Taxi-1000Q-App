
package com.momomart.dse5star;
import android.app.Activity;
import android.os.Bundle;
import android.widget.*;
import android.view.View;
import android.view.Gravity;
import android.graphics.Color;
import org.json.*;
import java.io.*;
import java.util.*;

public class MainActivity extends Activity {
    JSONArray bank;
    List<JSONObject> filtered = new ArrayList<>();
    String curSubject = "";
    int curIndex = 0;
    int score = 0;
    // UI
    LinearLayout root;
    TextView tvTitle, tvProgress, tvQuestion, tvExplain, tvScore;
    Button[] optBtns = new Button[4];
    Button btnNext, btnPrev, btnHome;
    LinearLayout optContainer, navContainer, homeContainer;
    boolean showingHome = true;

    protected void onCreate(Bundle b){
        super.onCreate(b);
        root = new LinearLayout(this);
        root.setOrientation(LinearLayout.VERTICAL);
        root.setPadding(24,24,24,24);
        root.setBackgroundColor(0xFF0A0A0A);

        tvTitle = new TextView(this);
        tvTitle.setText("DSE 5**地獄版 V15 正常版面 700題");
        tvTitle.setTextColor(0xFFFFD700);
        tvTitle.setTextSize(20);
        tvTitle.setGravity(Gravity.CENTER);
        root.addView(tvTitle);

        tvScore = new TextView(this);
        tvScore.setTextColor(0xFFAAAAAA);
        tvScore.setTextSize(12);
        root.addView(tvScore);

        // Home container - subject list
        homeContainer = new LinearLayout(this);
        homeContainer.setOrientation(LinearLayout.VERTICAL);
        homeContainer.setPadding(0,16,0,16);
        root.addView(homeContainer);

        // Quiz UI
        tvProgress = new TextView(this);
        tvProgress.setTextColor(0xFFAAAAAA);
        tvProgress.setTextSize(13);
        root.addView(tvProgress);

        tvQuestion = new TextView(this);
        tvQuestion.setTextColor(0xFFFFFFFF);
        tvQuestion.setTextSize(16);
        tvQuestion.setPadding(0,16,0,16);
        root.addView(tvQuestion);

        optContainer = new LinearLayout(this);
        optContainer.setOrientation(LinearLayout.VERTICAL);
        for(int i=0;i<4;i++){
            optBtns[i] = new Button(this);
            optBtns[i].setTag(i);
            optBtns[i].setOnClickListener(this::onOpt);
            optBtns[i].setBackgroundColor(0xFF1A1A1A);
            optBtns[i].setTextColor(0xFFFFFFFF);
            LinearLayout.LayoutParams lp = new LinearLayout.LayoutParams(-1,-2);
            lp.setMargins(0,8,0,8);
            optBtns[i].setLayoutParams(lp);
            optContainer.addView(optBtns[i]);
        }
        root.addView(optContainer);

        tvExplain = new TextView(this);
        tvExplain.setTextColor(0xFFFFD700);
        tvExplain.setTextSize(13);
        tvExplain.setPadding(0,12,0,12);
        root.addView(tvExplain);

        navContainer = new LinearLayout(this);
        navContainer.setOrientation(LinearLayout.HORIZONTAL);
        navContainer.setGravity(Gravity.CENTER);
        btnPrev = new Button(this); btnPrev.setText("上一題"); btnPrev.setOnClickListener(v->{ if(curIndex>0){curIndex--; showQuiz();}});
        btnHome = new Button(this); btnHome.setText("主頁"); btnHome.setOnClickListener(v->showHome());
        btnNext = new Button(this); btnNext.setText("下一題"); btnNext.setOnClickListener(v->{ if(curIndex < filtered.size()-1){curIndex++; showQuiz();} else {showHome();}});
        navContainer.addView(btnPrev); navContainer.addView(btnHome); navContainer.addView(btnNext);
        root.addView(navContainer);

        TextView ad = new TextView(this);
        ad.setText("AdMob: 4473556802 / 7999749175 / 2932410270 / 1817484203 | V15 700Q 正常版");
        ad.setTextColor(0xFF555555); ad.setTextSize(9); ad.setPadding(0,16,0,0);
        root.addView(ad);

        ScrollView sv = new ScrollView(this); sv.addView(root);
        setContentView(sv);

        try{
            InputStream is = getAssets().open("hell_bank.json");
            bank = new JSONArray(new String(is.readAllBytes()));
            showHome();
        }catch(Exception e){
            tvQuestion.setText("讀題庫失敗: "+e.getMessage());
        }
    }

    void showHome(){
        showingHome = true;
        homeContainer.removeAllViews();
        optContainer.setVisibility(View.GONE);
        navContainer.setVisibility(View.GONE);
        tvQuestion.setVisibility(View.GONE);
        tvExplain.setVisibility(View.GONE);
        tvProgress.setVisibility(View.GONE);
        homeContainer.setVisibility(View.VISIBLE);

        TextView homeTitle = new TextView(this);
        homeTitle.setText("揀科目開始地獄特訓 (共"+bank.length()+"題)");
        homeTitle.setTextColor(0xFFFFFFFF);
        homeTitle.setTextSize(16);
        homeTitle.setPadding(0,0,0,16);
        homeContainer.addView(homeTitle);

        Map<String,Integer> counts = new HashMap<>();
        for(int i=0;i<bank.length();i++){
            try{ String s = bank.getJSONObject(i).getString("subject"); counts.put(s, counts.getOrDefault(s,0)+1); }catch(Exception e){}
        }
        for(String subj : counts.keySet()){
            Button b = new Button(this);
            b.setText(subj+"  ("+counts.get(subj)+"題)");
            b.setBackgroundColor(0xFF222222);
            b.setTextColor(0xFFFFD700);
            b.setTag(subj);
            b.setOnClickListener(v->{
                curSubject = (String)v.getTag();
                filtered.clear();
                for(int i=0;i<bank.length();i++){
                    try{ JSONObject o = bank.getJSONObject(i); if(o.getString("subject").equals(curSubject)) filtered.add(o); }catch(Exception e){}
                }
                curIndex = 0; score = 0;
                showQuiz();
            });
            LinearLayout.LayoutParams lp = new LinearLayout.LayoutParams(-1,-2);
            lp.setMargins(0,8,0,8);
            b.setLayoutParams(lp);
            homeContainer.addView(b);
        }

        Button allBtn = new Button(this);
        allBtn.setText("全部科目隨機 (700題地獄)");
        allBtn.setBackgroundColor(0xFFFFD700);
        allBtn.setTextColor(0xFF000000);
        allBtn.setOnClickListener(v->{
            curSubject = "全部";
            filtered.clear();
            for(int i=0;i<bank.length();i++){ try{ filtered.add(bank.getJSONObject(i)); }catch(Exception e){} }
            Collections.shuffle(filtered);
            curIndex = 0; score = 0;
            showQuiz();
        });
        homeContainer.addView(allBtn);
        tvScore.setText("已選: 主頁 | 總分: "+score);
    }

    void showQuiz(){
        showingHome = false;
        homeContainer.setVisibility(View.GONE);
        optContainer.setVisibility(View.VISIBLE);
        navContainer.setVisibility(View.VISIBLE);
        tvQuestion.setVisibility(View.VISIBLE);
        tvExplain.setVisibility(View.VISIBLE);
        tvProgress.setVisibility(View.VISIBLE);
        try{
            JSONObject q = filtered.get(curIndex);
            tvProgress.setText(curSubject+" "+(curIndex+1)+"/"+filtered.size());
            tvQuestion.setText(q.getString("question"));
            JSONArray opts = q.getJSONArray("options");
            for(int i=0;i<4;i++){ optBtns[i].setText((char)('A'+i)+". "+opts.getString(i)); optBtns[i].setEnabled(true); optBtns[i].setBackgroundColor(0xFF1A1A1A); }
            tvExplain.setText("");
            tvScore.setText("科目: "+curSubject+" | 進度: "+(curIndex+1)+"/"+filtered.size()+" | 得分: "+score);
            btnPrev.setEnabled(curIndex>0);
            btnNext.setEnabled(true);
        }catch(Exception e){ tvQuestion.setText("Error: "+e.getMessage()); }
    }

    void onOpt(View v){
        int sel = (int)v.getTag();
        try{
            JSONObject q = filtered.get(curIndex);
            int ans = q.getInt("answer");
            for(int i=0;i<4;i++){ optBtns[i].setEnabled(false); }
            if(sel==ans){
                score++;
                tvExplain.setText("✅ 5** 正確!\n"+q.getString("explanation"));
                optBtns[sel].setBackgroundColor(0xFF00AA00);
            }else{
                tvExplain.setText("❌ 正確: "+q.getJSONArray("options").getString(ans)+"\n"+q.getString("explanation")+"\n地獄指數: "+q.optString("hell","4★"));
                optBtns[sel].setBackgroundColor(0xFFAA0000);
                optBtns[ans].setBackgroundColor(0xFF00AA00);
            }
            tvScore.setText("科目: "+curSubject+" | 進度: "+(curIndex+1)+"/"+filtered.size()+" | 得分: "+score);
        }catch(Exception e){}
    }
}
