
package com.momomart.taxi.hk;

import android.os.Bundle;
import androidx.appcompat.app.AppCompatActivity;
import android.widget.*;
import android.view.*;
import android.graphics.*;
import android.graphics.drawable.GradientDrawable;
import org.json.JSONArray;
import org.json.JSONObject;
import java.io.InputStream;
import java.nio.charset.StandardCharsets;
import java.util.*;

public class MainActivity extends AppCompatActivity {
    JSONArray all;
    List<JSONObject> filtered = new ArrayList<>();
    String curSub = "JIA1";
    int curIdx = 0;
    TextView qText, expText, progText, progCount, databaseText;
    LinearLayout optsLayout;
    View progressBar;
    Button tab1, tab2, tab3;
    TextView numBadge, tagText, pageInfo;
    
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getWindow().setStatusBarColor(Color.parseColor("#050505"));
        
        LinearLayout root = new LinearLayout(this);
        root.setOrientation(LinearLayout.VERTICAL);
        root.setBackgroundColor(Color.parseColor("#050505"));
        
        // HEADER
        LinearLayout header = new LinearLayout(this);
        header.setOrientation(LinearLayout.HORIZONTAL);
        header.setPadding(28,50,28,18);
        header.setGravity(Gravity.CENTER_VERTICAL);
        header.setBackgroundColor(Color.parseColor("#050505"));
        
        LinearLayout titleBox = new LinearLayout(this);
        titleBox.setOrientation(LinearLayout.VERTICAL);
        titleBox.setLayoutParams(new LinearLayout.LayoutParams(0, ViewGroup.LayoutParams.WRAP_CONTENT, 1));
        
        TextView t1 = new TextView(this);
        t1.setText("香港的士10000 黑金版");
        t1.setTextColor(Color.parseColor("#FFD700"));
        t1.setTextSize(20);
        t1.setTypeface(null, Typeface.BOLD);
        titleBox.addView(t1);
        
        TextView t2 = new TextView(this);
        t2.setText("BLACK GOLD • HELL EDITION v3.2.1");
        t2.setTextColor(Color.parseColor("#666666"));
        t2.setTextSize(9);
        GradientDrawable pill = new GradientDrawable();
        pill.setColor(Color.parseColor("#1a1a1a"));
        pill.setCornerRadius(20);
        pill.setStroke(1, Color.parseColor("#2a2a2a"));
        t2.setBackground(pill);
        t2.setPadding(18,6,18,6);
        LinearLayout.LayoutParams tp = new LinearLayout.LayoutParams(ViewGroup.LayoutParams.WRAP_CONTENT, ViewGroup.LayoutParams.WRAP_CONTENT);
        tp.setMargins(0,8,0,0);
        t2.setLayoutParams(tp);
        titleBox.addView(t2);
        
        header.addView(titleBox);
        
        // Search and more icons
        for(int i=0;i<2;i++){
            TextView ic = new TextView(this);
            ic.setText(i==0?"⌕":"⋯");
            ic.setTextColor(Color.parseColor("#999999"));
            ic.setTextSize(18);
            ic.setGravity(Gravity.CENTER);
            GradientDrawable c = new GradientDrawable();
            c.setColor(Color.parseColor("#161616"));
            c.setCornerRadius(100);
            c.setStroke(1, Color.parseColor("#222222"));
            ic.setBackground(c);
            LinearLayout.LayoutParams lp = new LinearLayout.LayoutParams(76,76);
            lp.setMargins(12,0,0,0);
            ic.setLayoutParams(lp);
            header.addView(ic);
        }
        root.addView(header);
        
        // TABS - Gold active style like screenshot
        HorizontalScrollView hScroll = new HorizontalScrollView(this);
        hScroll.setHorizontalScrollBarEnabled(false);
        LinearLayout tabs = new LinearLayout(this);
        tabs.setOrientation(LinearLayout.HORIZONTAL);
        tabs.setPadding(20,8,20,8);
        
        tab1 = makeTab("甲1 的士及網約車 334", true);
        tab2 = makeTab("甲2 地方及路線 333", false);
        tab3 = makeTab("乙 道路使用者守則", false);
        
        tab1.setOnClickListener(v->switchSub("JIA1"));
        tab2.setOnClickListener(v->switchSub("JIA2"));
        tab3.setOnClickListener(v->switchSub("YI"));
        
        tabs.addView(tab1);
        tabs.addView(tab2);
        tabs.addView(tab3);
        hScroll.addView(tabs);
        root.addView(hScroll);
        
        // PROGRESS MATRIX
        LinearLayout progBox = new LinearLayout(this);
        progBox.setOrientation(LinearLayout.VERTICAL);
        progBox.setPadding(28,14,28,12);
        
        LinearLayout progTop = new LinearLayout(this);
        progTop.setOrientation(LinearLayout.HORIZONTAL);
        progTop.setGravity(Gravity.CENTER_VERTICAL);
        
        progText = new TextView(this);
        progText.setText("甲1 的士及網約車  •  334個  完成度");
        progText.setTextColor(Color.parseColor("#888888"));
        progText.setTextSize(11);
        progText.setLayoutParams(new LinearLayout.LayoutParams(0, ViewGroup.LayoutParams.WRAP_CONTENT, 1));
        
        TextView badge = new TextView(this);
        badge.setText("87%");
        badge.setTextColor(Color.BLACK);
        badge.setTextSize(11);
        badge.setTypeface(null, Typeface.BOLD);
        GradientDrawable bd = new GradientDrawable();
        bd.setColor(Color.parseColor("#FFD700"));
        bd.setCornerRadius(8);
        badge.setBackground(bd);
        badge.setPadding(16,5,16,5);
        
        progCount = new TextView(this);
        progCount.setText("  87/334 已做");
        progCount.setTextColor(Color.parseColor("#444444"));
        progCount.setTextSize(11);
        
        progTop.addView(progText);
        progTop.addView(badge);
        progTop.addView(progCount);
        progBox.addView(progTop);
        
        // Gold progress line
        FrameLayout barBg = new FrameLayout(this);
        barBg.setBackgroundColor(Color.parseColor("#1a1a1a"));
        LinearLayout.LayoutParams barLp = new LinearLayout.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, 6);
        barLp.setMargins(0,12,0,6);
        barBg.setLayoutParams(barLp);
        GradientDrawable barBgD = new GradientDrawable();
        barBgD.setColor(Color.parseColor("#1a1a1a"));
        barBgD.setCornerRadius(10);
        barBg.setBackground(barBgD);
        
        progressBar = new View(this);
        FrameLayout.LayoutParams pblp = new FrameLayout.LayoutParams(0, ViewGroup.LayoutParams.MATCH_PARENT);
        progressBar.setLayoutParams(pblp);
        GradientDrawable pbd = new GradientDrawable();
        pbd.setColor(Color.parseColor("#FFD700"));
        pbd.setCornerRadius(10);
        progressBar.setBackground(pbd);
        barBg.addView(progressBar);
        progBox.addView(barBg);
        
        TextView matrix = new TextView(this);
        matrix.setText("PROGRESS MATRIX");
        matrix.setTextColor(Color.parseColor("#333333"));
        matrix.setTextSize(8);
        matrix.setLetterSpacing(0.15f);
        progBox.addView(matrix);
        
        root.addView(progBox);
        
        // QUESTION CARD SCROLL
        ScrollView scroll = new ScrollView(this);
        scroll.setBackgroundColor(Color.parseColor("#050505"));
        LinearLayout content = new LinearLayout(this);
        content.setOrientation(LinearLayout.VERTICAL);
        content.setPadding(20,10,20,20);
        
        // Card
        LinearLayout card = new LinearLayout(this);
        card.setOrientation(LinearLayout.VERTICAL);
        card.setPadding(24,20,24,20);
        GradientDrawable cardBg = new GradientDrawable();
        cardBg.setColor(Color.parseColor("#121212"));
        cardBg.setCornerRadius(24);
        cardBg.setStroke(1, Color.parseColor("#1e1e1e"));
        card.setBackground(cardBg);
        
        // Card header row
        LinearLayout qHeader = new LinearLayout(this);
        qHeader.setOrientation(LinearLayout.HORIZONTAL);
        qHeader.setGravity(Gravity.CENTER_VERTICAL);
        qHeader.setPadding(0,0,0,14);
        
        numBadge = new TextView(this);
        numBadge.setText("1");
        numBadge.setTextColor(Color.BLACK);
        numBadge.setTextSize(14);
        numBadge.setTypeface(null, Typeface.BOLD);
        numBadge.setGravity(Gravity.CENTER);
        GradientDrawable nb = new GradientDrawable();
        nb.setColor(Color.parseColor("#FFD700"));
        nb.setCornerRadius(20);
        numBadge.setBackground(nb);
        numBadge.setLayoutParams(new LinearLayout.LayoutParams(48,48));
        qHeader.addView(numBadge);
        
        tagText = new TextView(this);
        tagText.setText("拒載及揀客");
        tagText.setTextColor(Color.parseColor("#CCCCCC"));
        tagText.setTextSize(11);
        GradientDrawable tg = new GradientDrawable();
        tg.setColor(Color.parseColor("#1e1e1e"));
        tg.setCornerRadius(20);
        tg.setStroke(1, Color.parseColor("#2a2a2a"));
        tagText.setBackground(tg);
        tagText.setPadding(14,6,14,6);
        LinearLayout.LayoutParams tlp = new LinearLayout.LayoutParams(ViewGroup.LayoutParams.WRAP_CONTENT, ViewGroup.LayoutParams.WRAP_CONTENT);
        tlp.setMargins(12,0,8,0);
        tagText.setLayoutParams(tlp);
        qHeader.addView(tagText);
        
        TextView diff = new TextView(this);
        diff.setText("中");
        diff.setTextColor(Color.parseColor("#FFD700"));
        diff.setTextSize(10);
        GradientDrawable dg = new GradientDrawable();
        dg.setColor(Color.parseColor("#1e1e1e"));
        dg.setCornerRadius(20);
        dg.setStroke(1, Color.parseColor("#3a2e00"));
        diff.setBackground(dg);
        diff.setPadding(10,6,10,6);
        qHeader.addView(diff);
        
        // Spacer
        View spacer = new View(this);
        spacer.setLayoutParams(new LinearLayout.LayoutParams(0, ViewGroup.LayoutParams.WRAP_CONTENT, 1));
        qHeader.addView(spacer);
        
        // Star and share
        for(int i=0;i<2;i++){
            TextView ic = new TextView(this);
            ic.setText(i==0?"★":"↗");
            ic.setTextColor(i==0?Color.parseColor("#FFD700"):Color.parseColor("#555555"));
            ic.setTextSize(14);
            ic.setGravity(Gravity.CENTER);
            GradientDrawable cd = new GradientDrawable();
            cd.setColor(Color.parseColor("#1a1a1a"));
            cd.setCornerRadius(30);
            cd.setStroke(1, Color.parseColor("#2a2a2a"));
            ic.setBackground(cd);
            LinearLayout.LayoutParams ilp = new LinearLayout.LayoutParams(58,58);
            ilp.setMargins(8,0,0,0);
            ic.setLayoutParams(ilp);
            qHeader.addView(ic);
        }
        
        card.addView(qHeader);
        
        qText = new TextView(this);
        qText.setTextColor(Color.WHITE);
        qText.setTextSize(16);
        qText.setLineSpacing(6,1.35f);
        qText.setTypeface(null, Typeface.BOLD);
        card.addView(qText);
        
        optsLayout = new LinearLayout(this);
        optsLayout.setOrientation(LinearLayout.VERTICAL);
        optsLayout.setPadding(0,18,0,0);
        card.addView(optsLayout);
        
        expText = new TextView(this);
        expText.setTextColor(Color.parseColor("#FFD700"));
        expText.setTextSize(12);
        expText.setPadding(20,18,20,18);
        expText.setLineSpacing(4,1.3f);
        GradientDrawable exbg = new GradientDrawable();
        exbg.setColor(Color.parseColor("#1a1605"));
        exbg.setCornerRadius(12);
        exbg.setStroke(1, Color.parseColor("#3a2e00"));
        expText.setBackground(exbg);
        LinearLayout.LayoutParams exlp = new LinearLayout.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT);
        exlp.setMargins(0,16,0,0);
        expText.setLayoutParams(exlp);
        expText.setVisibility(View.GONE);
        card.addView(expText);
        
        content.addView(card);
        
        // Nav row
        LinearLayout nav = new LinearLayout(this);
        nav.setOrientation(LinearLayout.HORIZONTAL);
        nav.setGravity(Gravity.CENTER_VERTICAL);
        nav.setPadding(0,18,0,10);
        
        Button prev = new Button(this);
        prev.setText("＜ 上一題");
        styleNavBtn(prev, false);
        prev.setOnClickListener(v->{ if(curIdx>0){ curIdx--; showQ(); }});
        
        LinearLayout pages = new LinearLayout(this);
        pages.setOrientation(LinearLayout.HORIZONTAL);
        pages.setGravity(Gravity.CENTER);
        pages.setLayoutParams(new LinearLayout.LayoutParams(0, ViewGroup.LayoutParams.WRAP_CONTENT, 1));
        pages.setPadding(10,0,10,0);
        
        for(int i=0;i<3;i++){
            TextView pg = new TextView(this);
            pg.setText(String.valueOf(i+1));
            pg.setTextColor(i==0?Color.BLACK:Color.parseColor("#666666"));
            pg.setGravity(Gravity.CENTER);
            pg.setTextSize(12);
            pg.setTypeface(null, Typeface.BOLD);
            GradientDrawable pgd = new GradientDrawable();
            pgd.setColor(i==0?Color.parseColor("#FFD700"):Color.parseColor("#1a1a1a"));
            pgd.setCornerRadius(30);
            if(i!=0) pgd.setStroke(1, Color.parseColor("#2a2a2a"));
            pg.setBackground(pgd);
            LinearLayout.LayoutParams pglp = new LinearLayout.LayoutParams(52,52);
            pglp.setMargins(6,0,6,0);
            pg.setLayoutParams(pglp);
            pages.addView(pg);
        }
        
        pageInfo = new TextView(this);
        pageInfo.setText("/ 329");
        pageInfo.setTextColor(Color.parseColor("#444444"));
        pageInfo.setTextSize(11);
        pageInfo.setPadding(8,0,0,0);
        pages.addView(pageInfo);
        
        Button next = new Button(this);
        next.setText("下一題 ＞");
        styleNavBtn(next, true);
        next.setOnClickListener(v->{ if(curIdx<filtered.size()-1){ curIdx++; showQ(); }});
        
        nav.addView(prev);
        nav.addView(pages);
        nav.addView(next);
        content.addView(nav);
        
        // Footer
        databaseText = new TextView(this);
        databaseText.setText("BLACK GOLD ENGINE • 10000 Qs DATABASE LOADED    0001 / 1000");
        databaseText.setTextColor(Color.parseColor("#2a2a2a"));
        databaseText.setTextSize(8);
        databaseText.setLetterSpacing(0.1f);
        databaseText.setPadding(0,10,0,0);
        content.addView(databaseText);
        
        scroll.addView(content);
        root.addView(scroll);
        
        // BOTTOM NAV
        LinearLayout bottom = new LinearLayout(this);
        bottom.setOrientation(LinearLayout.HORIZONTAL);
        bottom.setBackgroundColor(Color.parseColor("#0a0a0a"));
        bottom.setPadding(0,12,0,28);
        GradientDrawable bottomBg = new GradientDrawable();
        bottomBg.setColor(Color.parseColor("#0a0a0a"));
        bottom.setBackground(bottomBg);
        
        String[] btm = {"首頁","題庫","模擬考試","我的"};
        String[] icons = {"⌂","▭","◫","◍"};
        for(int i=0;i<4;i++){
            LinearLayout item = new LinearLayout(this);
            item.setOrientation(LinearLayout.VERTICAL);
            item.setGravity(Gravity.CENTER);
            item.setLayoutParams(new LinearLayout.LayoutParams(0, ViewGroup.LayoutParams.WRAP_CONTENT, 1));
            
            TextView ic = new TextView(this);
            ic.setText(icons[i]);
            ic.setTextSize(18);
            ic.setGravity(Gravity.CENTER);
            ic.setTextColor(i==1?Color.parseColor("#FFD700"):Color.parseColor("#444444"));
            if(i==1){
                GradientDrawable aibg = new GradientDrawable();
                aibg.setColor(Color.parseColor("#1a1605"));
                aibg.setCornerRadius(12);
                ic.setBackground(aibg);
                ic.setPadding(18,6,18,2);
            }
            
            TextView lb = new TextView(this);
            lb.setText(btm[i]);
            lb.setTextSize(10);
            lb.setGravity(Gravity.CENTER);
            lb.setTextColor(i==1?Color.parseColor("#FFD700"):Color.parseColor("#444444"));
            if(i==1) lb.setTypeface(null, Typeface.BOLD);
            lb.setPadding(0,4,0,0);
            
            item.addView(ic);
            item.addView(lb);
            bottom.addView(item);
        }
        root.addView(bottom);
        
        setContentView(root);
        
        try {
            InputStream is = getAssets().open("hell_bank.json");
            byte[] buf = new byte[is.available()];
            is.read(buf);
            is.close();
            String json = new String(buf, StandardCharsets.UTF_8);
            all = new JSONArray(json);
            switchSub("JIA1");
        } catch (Exception e){
            qText.setText("載入失敗: "+e.getMessage()+" 請確保 hell_bank.json 在 app/src/main/assets/");
        }
    }
    
    Button makeTab(String name, boolean active){
        Button b = new Button(this);
        b.setText(name);
        b.setTextSize(11);
        b.setAllCaps(false);
        LinearLayout.LayoutParams lp = new LinearLayout.LayoutParams(ViewGroup.LayoutParams.WRAP_CONTENT, ViewGroup.LayoutParams.WRAP_CONTENT);
        lp.setMargins(6,0,6,0);
        b.setLayoutParams(lp);
        GradientDrawable d = new GradientDrawable();
        d.setCornerRadius(22);
        if(active){
            d.setColor(Color.parseColor("#FFD700"));
            b.setTextColor(Color.BLACK);
            b.setTypeface(null, Typeface.BOLD);
        } else {
            d.setColor(Color.parseColor("#161616"));
            d.setStroke(1, Color.parseColor("#222222"));
            b.setTextColor(Color.parseColor("#888888"));
        }
        b.setBackground(d);
        b.setPadding(26,14,26,14);
        return b;
    }
    
    void styleNavBtn(Button b, boolean gold){
        b.setTextSize(11);
        b.setAllCaps(false);
        b.setTypeface(null, Typeface.BOLD);
        GradientDrawable d = new GradientDrawable();
        d.setCornerRadius(20);
        if(gold){
            d.setColor(Color.parseColor("#1a1a1a"));
            d.setStroke(1, Color.parseColor("#333333"));
            b.setTextColor(Color.WHITE);
        } else {
            d.setColor(Color.parseColor("#161616"));
            d.setStroke(1, Color.parseColor("#222222"));
            b.setTextColor(Color.parseColor("#666666"));
        }
        b.setBackground(d);
        b.setPadding(22,12,22,12);
    }
    
    void switchSub(String s){
        curSub = s;
        filtered.clear();
        try {
            for(int i=0;i<all.length();i++){
                JSONObject o = all.getJSONObject(i);
                if(o.optString("subject","").equals(s)) filtered.add(o);
            }
        } catch (Exception e){}
        curIdx=0;
        tab1.setBackground(createTabBg(curSub.equals("JIA1")));
        tab1.setTextColor(curSub.equals("JIA1")?Color.BLACK:Color.parseColor("#888888"));
        tab2.setBackground(createTabBg(curSub.equals("JIA2")));
        tab2.setTextColor(curSub.equals("JIA2")?Color.BLACK:Color.parseColor("#888888"));
        tab3.setBackground(createTabBg(curSub.equals("YI")));
        tab3.setTextColor(curSub.equals("YI")?Color.BLACK:Color.parseColor("#888888"));
        showQ();
    }
    
    GradientDrawable createTabBg(boolean active){
        GradientDrawable d = new GradientDrawable();
        d.setCornerRadius(22);
        if(active) d.setColor(Color.parseColor("#FFD700"));
        else {
            d.setColor(Color.parseColor("#161616"));
            d.setStroke(1, Color.parseColor("#222222"));
        }
        return d;
    }
    
    void showQ(){
        if(filtered.isEmpty()){
            qText.setText("此分類暫無題目");
            return;
        }
        try {
            JSONObject q = filtered.get(curIdx);
            String topic = q.optString("topic","拒載及揀客");
            String question = q.optString("question","根據的士則例，的士司機喺咩情況下唔可以拒載？");
            // Clean question
            if(question.contains(" (情景")) question = question.substring(0, question.indexOf(" (情景"));
            
            qText.setText(question+"？(題目"+(curIdx+1)+" - "+topic+")");
            numBadge.setText(String.valueOf(curIdx+1));
            tagText.setText(topic);
            pageInfo.setText("/ "+filtered.size());
            
            float pct = (float)(curIdx+1)/filtered.size();
            if(progressBar!=null){
                progressBar.post(()->{
                    View parent = (View)progressBar.getParent();
                    int w = parent.getWidth();
                    ViewGroup.LayoutParams lp = progressBar.getLayoutParams();
                    lp.width = (int)(w * pct);
                    progressBar.setLayoutParams(lp);
                });
            }
            progCount.setText("  "+(curIdx+1)+"/"+filtered.size()+" 已做");
            databaseText.setText("BLACK GOLD ENGINE • 10000 Qs DATABASE LOADED    "+String.format("%04d", curIdx+1)+" / "+String.format("%04d", all.length()));
            
            optsLayout.removeAllViews();
            JSONArray opts = q.optJSONArray("options");
            String ans = q.optString("answer","B");
            String exp = q.optString("explanation","的士站及路邊揚手都唔可以拒載揀客。");
            expText.setText("答案: "+ans+"\n\nAI詳解: "+exp);
            expText.setVisibility(View.GONE);
            
            if(opts!=null){
                for(int i=0;i<opts.length();i++){
                    String opt = opts.getString(i);
                    String letter = opt.substring(0,1);
                    String txt = opt.length()>3?opt.substring(3):opt;
                    
                    LinearLayout row = new LinearLayout(this);
                    row.setOrientation(LinearLayout.HORIZONTAL);
                    row.setGravity(Gravity.CENTER_VERTICAL);
                    row.setPadding(18,16,18,16);
                    GradientDrawable rd = new GradientDrawable();
                    rd.setCornerRadius(14);
                    if(letter.equals(ans)){
                        rd.setColor(Color.parseColor("#1e1a05"));
                        rd.setStroke(2, Color.parseColor("#FFD700"));
                    } else {
                        rd.setColor(Color.parseColor("#181818"));
                        rd.setStroke(1, Color.parseColor("#222222"));
                    }
                    row.setBackground(rd);
                    LinearLayout.LayoutParams rlp = new LinearLayout.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT);
                    rlp.setMargins(0,8,0,8);
                    row.setLayoutParams(rlp);
                    
                    TextView letterTv = new TextView(this);
                    letterTv.setText(letter);
                    letterTv.setTextColor(letter.equals(ans)?Color.BLACK:Color.parseColor("#888888"));
                    letterTv.setGravity(Gravity.CENTER);
                    letterTv.setTextSize(12);
                    letterTv.setTypeface(null, Typeface.BOLD);
                    GradientDrawable ld = new GradientDrawable();
                    ld.setCornerRadius(20);
                    if(letter.equals(ans)) ld.setColor(Color.parseColor("#FFD700"));
                    else ld.setColor(Color.parseColor("#252525"));
                    letterTv.setBackground(ld);
                    letterTv.setLayoutParams(new LinearLayout.LayoutParams(44,44));
                    row.addView(letterTv);
                    
                    TextView optT = new TextView(this);
                    optT.setText(txt);
                    optT.setTextColor(Color.parseColor("#DDDDDD"));
                    optT.setTextSize(13);
                    optT.setLayoutParams(new LinearLayout.LayoutParams(0, ViewGroup.LayoutParams.WRAP_CONTENT, 1));
                    optT.setPadding(14,0,0,0);
                    optT.setLineSpacing(2,1.2f);
                    row.addView(optT);
                    
                    if(letter.equals(ans)){
                        TextView check = new TextView(this);
                        check.setText("✓");
                        check.setTextColor(Color.BLACK);
                        check.setGravity(Gravity.CENTER);
                        check.setTextSize(12);
                        check.setTypeface(null, Typeface.BOLD);
                        GradientDrawable cd = new GradientDrawable();
                        cd.setColor(Color.parseColor("#FFD700"));
                        cd.setCornerRadius(20);
                        check.setBackground(cd);
                        check.setLayoutParams(new LinearLayout.LayoutParams(38,38));
                        row.addView(check);
                    }
                    
                    final String sel = letter;
                    row.setOnClickListener(v->{
                        expText.setVisibility(View.VISIBLE);
                        // Add AI button effect
                        Toast.makeText(this, sel.equals(ans)?"答啱！":"答錯，正確答案係 "+ans, Toast.LENGTH_SHORT).show();
                        showQ(); // refresh to show gold border
                    });
                    
                    optsLayout.addView(row);
                }
                
                // AI 詳解 button like screenshot
                LinearLayout aiRow = new LinearLayout(this);
                aiRow.setOrientation(LinearLayout.HORIZONTAL);
                aiRow.setGravity(Gravity.END);
                aiRow.setPadding(0,12,0,0);
                
                Button aiBtn = new Button(this);
                aiBtn.setText("AI 詳解  ✦");
                aiBtn.setTextColor(Color.BLACK);
                aiBtn.setTextSize(11);
                aiBtn.setTypeface(null, Typeface.BOLD);
                aiBtn.setAllCaps(false);
                GradientDrawable aibg = new GradientDrawable();
                aibg.setColor(Color.parseColor("#FFD700"));
                aibg.setCornerRadius(20);
                aiBtn.setBackground(aibg);
                aiBtn.setPadding(24,12,24,12);
                aiBtn.setOnClickListener(v->{
                    expText.setVisibility(expText.getVisibility()==View.VISIBLE?View.GONE:View.VISIBLE);
                });
                aiRow.addView(aiBtn);
                optsLayout.addView(aiRow);
            }
        } catch (Exception e){
            qText.setText("顯示錯誤: "+e.getMessage());
        }
    }
}
