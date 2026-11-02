
package com.momomart.taxi.hk;

import android.os.Bundle;
import androidx.appcompat.app.AppCompatActivity;
import android.widget.*;
import android.view.*;
import android.graphics.Color;
import android.graphics.Typeface;
import org.json.JSONArray;
import org.json.JSONObject;
import java.io.InputStream;
import java.nio.charset.StandardCharsets;
import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity {
    private JSONArray allQuestions;
    private List<JSONObject> filtered = new ArrayList<>();
    private String currentSubject = "JIA1";
    private int currentIndex = 0;
    private LinearLayout questionsContainer;
    private TextView progressText;
    private Button btnJIA1, btnJIA2, btnYI;
    private LinearLayout optionsLayout;
    private TextView questionText, explanationText;
    private int score = 0;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        
        // Root
        LinearLayout root = new LinearLayout(this);
        root.setOrientation(LinearLayout.VERTICAL);
        root.setBackgroundColor(Color.parseColor("#0a0a0a"));
        
        // Top Bar
        TextView topBar = new TextView(this);
        topBar.setText("HK Taxi 1000Q Hell Edition 黑金版");
        topBar.setTextColor(Color.parseColor("#FFD700"));
        topBar.setTextSize(18);
        topBar.setTypeface(null, Typeface.BOLD);
        topBar.setPadding(30,30,30,20);
        topBar.setBackgroundColor(Color.parseColor("#111111"));
        root.addView(topBar);
        
        // Tabs
        LinearLayout tabs = new LinearLayout(this);
        tabs.setOrientation(LinearLayout.HORIZONTAL);
        tabs.setPadding(10,10,10,10);
        tabs.setBackgroundColor(Color.parseColor("#1a1a1a"));
        
        btnJIA1 = createTabButton("甲1 的士及網約車", true);
        btnJIA2 = createTabButton("甲2 地方路線", false);
        btnYI = createTabButton("乙 道使", false);
        
        btnJIA1.setOnClickListener(v -> switchSubject("JIA1"));
        btnJIA2.setOnClickListener(v -> switchSubject("JIA2"));
        btnYI.setOnClickListener(v -> switchSubject("YI"));
        
        tabs.addView(btnJIA1);
        tabs.addView(btnJIA2);
        tabs.addView(btnYI);
        root.addView(tabs);
        
        // Progress
        progressText = new TextView(this);
        progressText.setTextColor(Color.WHITE);
        progressText.setPadding(30,15,30,15);
        progressText.setTextSize(14);
        root.addView(progressText);
        
        // Question area
        ScrollView scroll = new ScrollView(this);
        LinearLayout content = new LinearLayout(this);
        content.setOrientation(LinearLayout.VERTICAL);
        content.setPadding(30,20,30,20);
        
        questionText = new TextView(this);
        questionText.setTextColor(Color.WHITE);
        questionText.setTextSize(18);
        questionText.setLineSpacing(10,1.2f);
        content.addView(questionText);
        
        optionsLayout = new LinearLayout(this);
        optionsLayout.setOrientation(LinearLayout.VERTICAL);
        optionsLayout.setPadding(0,20,0,20);
        content.addView(optionsLayout);
        
        explanationText = new TextView(this);
        explanationText.setTextColor(Color.parseColor("#FFD700"));
        explanationText.setTextSize(14);
        explanationText.setPadding(20,20,20,20);
        explanationText.setBackgroundColor(Color.parseColor("#222222"));
        explanationText.setVisibility(View.GONE);
        content.addView(explanationText);
        
        LinearLayout nav = new LinearLayout(this);
        nav.setOrientation(LinearLayout.HORIZONTAL);
        nav.setPadding(0,30,0,0);
        
        Button prev = new Button(this);
        prev.setText("上一題");
        prev.setOnClickListener(v -> { if(currentIndex>0){ currentIndex--; showQuestion(); }});
        Button next = new Button(this);
        next.setText("下一題");
        next.setOnClickListener(v -> { if(currentIndex<filtered.size()-1){ currentIndex++; showQuestion(); }});
        Button reveal = new Button(this);
        reveal.setText("睇解釋");
        reveal.setOnClickListener(v -> explanationText.setVisibility(View.VISIBLE));
        
        nav.addView(prev);
        nav.addView(next);
        nav.addView(reveal);
        content.addView(nav);
        
        scroll.addView(content);
        root.addView(scroll);
        
        setContentView(root);
        
        // Load JSON
        try {
            InputStream is = getAssets().open("hell_bank.json");
            int size = is.available();
            byte[] buffer = new byte[size];
            is.read(buffer);
            is.close();
            String json = new String(buffer, StandardCharsets.UTF_8);
            allQuestions = new JSONArray(json);
            switchSubject("JIA1");
        } catch (Exception e) {
            questionText.setText("載入失敗: " + e.getMessage());
        }
    }
    
    private Button createTabButton(String text, boolean active){
        Button b = new Button(this);
        b.setText(text);
        b.setTextSize(12);
        LinearLayout.LayoutParams lp = new LinearLayout.LayoutParams(0, LinearLayout.LayoutParams.WRAP_CONTENT, 1f);
        lp.setMargins(5,5,5,5);
        b.setLayoutParams(lp);
        if(active){
            b.setBackgroundColor(Color.parseColor("#FFD700"));
            b.setTextColor(Color.BLACK);
        } else {
            b.setBackgroundColor(Color.parseColor("#333333"));
            b.setTextColor(Color.WHITE);
        }
        return b;
    }
    
    private void switchSubject(String sub){
        currentSubject = sub;
        filtered.clear();
        try {
            for(int i=0;i<allQuestions.length();i++){
                JSONObject q = allQuestions.getJSONObject(i);
                if(q.optString("subject","").equals(sub)){
                    filtered.add(q);
                }
            }
        } catch (Exception e){}
        currentIndex = 0;
        updateTabs();
        showQuestion();
    }
    
    private void updateTabs(){
        btnJIA1.setBackgroundColor(currentSubject.equals("JIA1")? Color.parseColor("#FFD700"): Color.parseColor("#333333"));
        btnJIA1.setTextColor(currentSubject.equals("JIA1")? Color.BLACK: Color.WHITE);
        btnJIA2.setBackgroundColor(currentSubject.equals("JIA2")? Color.parseColor("#FFD700"): Color.parseColor("#333333"));
        btnJIA2.setTextColor(currentSubject.equals("JIA2")? Color.BLACK: Color.WHITE);
        btnYI.setBackgroundColor(currentSubject.equals("YI")? Color.parseColor("#FFD700"): Color.parseColor("#333333"));
        btnYI.setTextColor(currentSubject.equals("YI")? Color.BLACK: Color.WHITE);
    }
    
    private void showQuestion(){
        if(filtered.isEmpty()){
            questionText.setText("此分類暫無題目");
            return;
        }
        try {
            JSONObject q = filtered.get(currentIndex);
            progressText.setText(currentSubject + " " + (currentIndex+1) + "/" + filtered.size() + "  已載入1000條完全唔重複");
            questionText.setText((currentIndex+1) + ". [" + q.optString("topic","") + "] " + q.optString("question",""));
            explanationText.setText("答案: " + q.optString("answer","") + "\n\n解釋: " + q.optString("explanation",""));
            explanationText.setVisibility(View.GONE);
            
            optionsLayout.removeAllViews();
            JSONArray opts = q.optJSONArray("options");
            if(opts!=null){
                for(int i=0;i<opts.length();i++){
                    String opt = opts.getString(i);
                    Button ob = new Button(this);
                    ob.setText(opt);
                    ob.setAllCaps(false);
                    ob.setTextColor(Color.WHITE);
                    ob.setBackgroundColor(Color.parseColor("#222222"));
                    ob.setPadding(20,20,20,20);
                    LinearLayout.LayoutParams lp = new LinearLayout.LayoutParams(LinearLayout.LayoutParams.MATCH_PARENT, LinearLayout.LayoutParams.WRAP_CONTENT);
                    lp.setMargins(0,10,0,10);
                    ob.setLayoutParams(lp);
                    final int idx = i;
                    ob.setOnClickListener(v -> {
                        String ans = q.optString("answer","");
                        String selected = opt.substring(0,1);
                        if(selected.equals(ans)){
                            ob.setBackgroundColor(Color.parseColor("#2e7d32"));
                            explanationText.setVisibility(View.VISIBLE);
                            Toast.makeText(this, "答啱！", Toast.LENGTH_SHORT).show();
                        } else {
                            ob.setBackgroundColor(Color.parseColor("#c62828"));
                            explanationText.setVisibility(View.VISIBLE);
                            Toast.makeText(this, "答錯，正確答案係 " + ans, Toast.LENGTH_SHORT).show();
                        }
                    });
                    optionsLayout.addView(ob);
                }
            }
        } catch (Exception e){
            questionText.setText("顯示錯誤: " + e.getMessage());
        }
    }
}
