package innominatebit.srmite;

import android.content.Context;
import android.graphics.Color;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.google.android.gms.ads.AdRequest;
import com.google.android.gms.ads.AdView;

public class TestPerformance extends Fragment {
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.fragment_testperformance, container, false);
        AdView mAdView = (AdView) rootView.findViewById(R.id.adView);
        AdRequest adRequest = new AdRequest.Builder().build();
        mAdView.loadAd(adRequest);
        Context c=this.getContext();
        if(!LoginActivity.testperformancenotfound)
        {
            LinearLayout data=(LinearLayout)rootView.findViewById(R.id.data);
            Tests tests[]=LoginActivity.t;
            LinearLayout.LayoutParams lparams = new LinearLayout.LayoutParams
                    (LinearLayout.LayoutParams.WRAP_CONTENT, LinearLayout.LayoutParams.WRAP_CONTENT);
            for(int i=0;i<tests.length;i++)
            {
                TextView tv=new TextView(c);
                tv.setLayoutParams(lparams);
                tv.setText(tests[i].testname);
                tv.setPadding(30,30,30,10);
                tv.setTextSize(19);
                tv.setGravity(Gravity.CENTER);
                data.addView(tv);
                for(int j=0;j<tests[i].numberofsubjects;j++)
                {
                    TextView t=new TextView(c);
                    t.setLayoutParams(lparams);
                    String name=tests[i].s[j].subjectname;
                    String temp=name+"\n"+tests[i].s[j].subjectcode+" : "+tests[i].s[j].subjectmarks;
                    String marks=tests[i].s[j].subjectmarks;
                    double a=Double.parseDouble(marks.substring(0,marks.indexOf('/'))),
                            b=Double.parseDouble(marks.substring(marks.indexOf('/')+1));
                    t.setText(temp);
                    if(a<=b/2)
                        t.setTextColor(Color.RED);
                    if(j+1==tests[i].numberofsubjects)
                        t.setPadding(6,6,0,25);
                    else
                        t.setPadding(6,6,0,6);
                    t.setTextSize(16);
                    t.setGravity(Gravity.CENTER);
                    data.addView(t);
                }
                View v=new View(c);
                LinearLayout.LayoutParams l = new LinearLayout.LayoutParams(LinearLayout.LayoutParams.MATCH_PARENT, 1);
                v.setLayoutParams(l);
                v.setBackgroundColor(Color.BLACK);
                data.addView(v);
            }
        }
        else
        {
            TextView tv=(TextView)rootView.findViewById(R.id.testperformance);
            tv.setText("No Record(s) Found");
        }
        return rootView;
    }
}
