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

/**
 * Created by Abhinav on 02-01-2017.
 */

public class Attendance extends Fragment {
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.fragment_attendance, container, false);

        AdView mAdView = (AdView) rootView.findViewById(R.id.adView);
        AdRequest adRequest = new AdRequest.Builder().build();
        mAdView.loadAd(adRequest);
        LinearLayout totaldata=(LinearLayout)rootView.findViewById(R.id.attendancedata);
        LinearLayout.LayoutParams lparams = new LinearLayout.LayoutParams(LinearLayout.LayoutParams.WRAP_CONTENT, LinearLayout.LayoutParams.WRAP_CONTENT);
        Context context=this.getContext();
        if(!LoginActivity.attendancenotfound)
        {
            String datatotal[]=LoginActivity.attendancedata,temporary,startdate=LoginActivity.startdate,enddate=LoginActivity.enddate;
            int i=0;
            double temp;
            TextView date=(TextView)rootView.findViewById(R.id.date);
            date.setText("From "+startdate+" To "+enddate);
            TextView tv1=(TextView)rootView.findViewById(R.id.maxhourstext);
            tv1.setText(tv1.getText()+"\n"+datatotal[i++]);
            TextView tv2=(TextView)rootView.findViewById(R.id.atthourstext);
            tv2.setText(tv2.getText()+"\n"+datatotal[i++]);
            TextView tv3=(TextView)rootView.findViewById(R.id.absenthourstext);
            tv3.setText(tv3.getText()+"\n"+datatotal[i++]);
            TextView tv4=(TextView)rootView.findViewById(R.id.averagetext);
            tv4.setText(tv4.getText()+"\n"+datatotal[i++]);
            TextView tv5=(TextView)rootView.findViewById(R.id.od_mltext);
            tv5.setText(tv5.getText()+"\n"+datatotal[i++]);
            TextView tv6=(TextView)rootView.findViewById(R.id.totalpercentage);
            temporary=datatotal[i++];
            temp=Double.parseDouble(temporary);
            tv6.setText(tv6.getText()+"\n"+temp);
            if(temp<=75)
                tv6.setTextColor(Color.RED);
            Subject sub[]=LoginActivity.subjects;
            for(i=0;i<sub.length;i++)
            {
                TextView t1=new TextView(context);
                t1.setLayoutParams(lparams);
                t1.setText("Subject Name : "+sub[i].subjectname);
                t1.setPadding(30,30,30,10);
                t1.setTextSize(19);
                t1.setGravity(Gravity.CENTER);
                totaldata.addView(t1);
                TextView t2=new TextView(context);
                t2.setLayoutParams(lparams);
                t2.setText("Subject Code : "+sub[i].subjectcode);
                t2.setPadding(30,10,30,10);
                t2.setTextSize(19);
                t2.setGravity(Gravity.CENTER);
                totaldata.addView(t2);
                TextView t3=new TextView(context);
                t3.setLayoutParams(lparams);
                t3.setText("Max Hours : "+sub[i].maxhours);
                t3.setPadding(30,6,0,6);
                t3.setTextSize(16);
                t3.setGravity(Gravity.CENTER);
                totaldata.addView(t3);
                TextView t4=new TextView(context);
                t4.setLayoutParams(lparams);
                t4.setText("Att. Hours : "+sub[i].atthours);
                t4.setPadding(30,6,0,6);
                t4.setTextSize(16);
                t4.setGravity(Gravity.CENTER);
                totaldata.addView(t4);
                TextView t5=new TextView(context);
                t5.setLayoutParams(lparams);
                t5.setText("Absent Hours : "+sub[i].absenthours);
                t5.setPadding(30,6,0,6);
                t5.setTextSize(16);
                t5.setGravity(Gravity.CENTER);
                totaldata.addView(t5);
                TextView t6=new TextView(context);
                t6.setLayoutParams(lparams);
                t6.setText("Average Percentage : "+sub[i].average);
                t6.setPadding(30,6,0,6);
                t6.setTextSize(16);
                t6.setGravity(Gravity.CENTER);
                totaldata.addView(t6);
                TextView t7=new TextView(context);
                t7.setLayoutParams(lparams);
                t7.setText("OD / ML Percentage : "+sub[i].OD_ML);
                t7.setPadding(30,6,0,6);
                t7.setTextSize(16);
                t7.setGravity(Gravity.CENTER);
                totaldata.addView(t7);
                TextView t8=new TextView(context);
                t8.setLayoutParams(lparams);
                temporary=sub[i].total;
                temp=Double.parseDouble(temporary);
                if(temp==75)
                {
                    t8.setText("Total Percentage : " + temporary);
                    t8.setTextColor(Color.RED);
                }
                else if(temp<75)
                {
                    t8.setText("Total Percentage : "+temporary+calculate(sub[i].maxhours,sub[i].absenthours));
                    t8.setTextColor(Color.RED);
                }
                else
                    t8.setText("Total Percentage : "+temporary);
                t8.setPadding(30,6,0,30);
                t8.setTextSize(16);
                t8.setGravity(Gravity.CENTER);
                totaldata.addView(t8);
                View v=new View(context);
                LinearLayout.LayoutParams l = new LinearLayout.LayoutParams(LinearLayout.LayoutParams.MATCH_PARENT, 1);
                v.setLayoutParams(l);
                v.setBackgroundColor(Color.BLACK);
                totaldata.addView(v);
            }
        }
        else
        {
            TextView tv=(TextView)rootView.findViewById(R.id.attendance);
            tv.setText("No Record(s) Found");
            LinearLayout linearLayout=(LinearLayout)rootView.findViewById(R.id.totaldata);
            linearLayout.setVisibility(View.GONE);
        }
        return rootView;
    }
    String calculate(String a,String b) {
        int max=Integer.parseInt(a);
        int ab=Integer.parseInt(b);
        int i;
        for(i=0;((max-ab+i)*100)/(max+i)<75;i++);
        return "\n[Need "+i+" more hour(s) to get at least 75%]";
    }
}