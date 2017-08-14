package innominatebit.srmite;

import android.content.Context;
import android.graphics.Color;
import android.os.Build;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.app.AlertDialog;
import android.support.v7.widget.CardView;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.SeekBar;
import android.widget.TextView;

import com.google.android.gms.ads.AdRequest;
import com.google.android.gms.ads.AdView;

import pl.pawelkleczkowski.customgauge.CustomGauge;

public class Attendance extends Fragment {
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.fragment_attendance, container, false);
        AdView mAdView = (AdView) rootView.findViewById(R.id.adView);
        AdRequest adRequest = new AdRequest.Builder().build();
        mAdView.loadAd(adRequest);
        LinearLayout totaldata=(LinearLayout)rootView.findViewById(R.id.attendancedata);
        LinearLayout.LayoutParams lparams = new LinearLayout.LayoutParams(LinearLayout.LayoutParams
                .MATCH_PARENT, LinearLayout.LayoutParams.MATCH_PARENT,1);
        lparams.setMargins(10, 10, 10, 10);
        LinearLayout.LayoutParams lparam = new LinearLayout.LayoutParams(LinearLayout.LayoutParams
                .MATCH_PARENT, LinearLayout.LayoutParams.MATCH_PARENT,1);
        LinearLayout.LayoutParams textParams = new LinearLayout.LayoutParams(LinearLayout.LayoutParams
                .WRAP_CONTENT, LinearLayout.LayoutParams.WRAP_CONTENT);
        textParams.gravity=Gravity.CENTER;
        final Context context=this.getContext();
        if(!LoginActivity.attendancenotfound)
        {
            final String datatotal[]=LoginActivity.attendancedata;
            String temporary;
            final String startdate=LoginActivity.startdate;
            final String enddate=LoginActivity.enddate;
            double temp=Double.parseDouble(datatotal[5]);
            CustomGauge myGauge = (CustomGauge) rootView.findViewById(R.id.gauge1);
            myGauge.setValue((int)(temp));
            TextView totalpercentage=(TextView)rootView.findViewById(R.id.totalpercentage);
            totalpercentage.setText(temp+"%");
            if(temp<=75)
                totalpercentage.setTextColor(Color.RED);
            CardView cardView = (CardView) rootView.findViewById(R.id.myCard);
            cardView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    new AlertDialog.Builder(context)
                            .setTitle("Overall Attendance")
                            .setMessage("Maximun Hours : "+datatotal[0]
                                    +"\nAttended Hours : "+datatotal[1]
                                    +"\nAbsent Hours : "+datatotal[2]
                                    +"\nAverage Hours : "+datatotal[3]
                                    +"\nOD/ML Percentage : "+datatotal[4]
                                    +"\nTotal Percentage: "+datatotal[5]
                                    +"\n\n(From "+startdate+" till "+enddate+")")
                            .setPositiveButton(android.R.string.ok, null)
                            .setIcon(android.R.drawable.ic_dialog_info)
                            .create().show();
                }
            });
            final Subject sub[]=LoginActivity.subjects;
            LinearLayout previousLinearLayout = new LinearLayout(context);
            for(int i=0;i<sub.length;i++)
            {
                CardView c = new CardView(context);
                c.setLayoutParams(lparams);
                c.setMaxCardElevation(10);
                LinearLayout xyz = new LinearLayout(context);
                xyz.setLayoutParams(lparam);
                xyz.setOrientation(LinearLayout.VERTICAL);
                TextView t1=new TextView(context);
                t1.setLayoutParams(textParams);
                t1.setText(sub[i].subjectname+"\n");
                t1.setPadding(0,8,0,8);
                t1.setTextSize(19);
                t1.setGravity(Gravity.CENTER);
                TextView t8=new TextView(context);
                t8.setLayoutParams(textParams);
                temporary=sub[i].total;
                temp=Double.parseDouble(temporary);
                if(temp==75)
                {
                    t8.setText(temporary+"%\n");
                    t8.setTextColor(Color.WHITE);
                    t1.setTextColor(Color.WHITE);
                    c.setCardBackgroundColor(Color.RED);
                }
                else if(temp<75)
                {
                    t8.setText(temporary+"%\n"+calculate(sub[i].maxhours,sub[i].absenthours));
                    t8.setTextColor(Color.WHITE);
                    t1.setTextColor(Color.WHITE);
                    c.setCardBackgroundColor(Color.RED);
                }
                else
                    t8.setText(temporary+"%\n");
                t8.setPadding(0,8,0,8);
                t8.setTextSize(16);
                t8.setGravity(Gravity.CENTER);
                LayoutInflater progressInflater = LayoutInflater.from(context);
                SeekBar seekBar = (SeekBar) progressInflater.inflate(R.layout.progress_bar, null);
                seekBar.setOnTouchListener(new View.OnTouchListener() {
                    @Override
                    public boolean onTouch(View view, MotionEvent motionEvent) {
                        return true;
                    }
                });
                if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.N) {
                    seekBar.setProgress((int)temp, true);
                }
                else
                    seekBar.setProgress((int)temp);
                xyz.addView(t1);
                xyz.addView(t8);
                xyz.addView(seekBar);
                c.addView(xyz);
                final int finalI = i;
                c.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        Subject tempSubject = sub[finalI];
                        new AlertDialog.Builder(context)
                                .setTitle(tempSubject.subjectname)
                                .setMessage("Subject Code : "+tempSubject.subjectcode
                                        +"\nMax. Hours : "+tempSubject.maxhours
                                        +"\nAtt. Hours : "+tempSubject.atthours
                                        +"\nAbsent Hours : "+tempSubject.absenthours
                                        +"\nAverage Percentage : "+tempSubject.average
                                        +"\nOD/ML Percentage : "+tempSubject.OD_ML
                                        +"\nTotal Percentage: "+tempSubject.total)
                                .setPositiveButton(android.R.string.ok, null)
                                .setIcon(android.R.drawable.ic_dialog_info)
                                .create().show();
                    }
                });
                if(i%2 == 0)
                {
                    LinearLayout myLinearLayout = new LinearLayout(context);
                    myLinearLayout.setLayoutParams(lparam);
                    myLinearLayout.setOrientation(LinearLayout.HORIZONTAL);
                    myLinearLayout.addView(c);
                    previousLinearLayout = myLinearLayout;
                    if(i+1==sub.length)
                        totaldata.addView(previousLinearLayout);
                }
                else
                {
                    previousLinearLayout.addView(c);
                    totaldata.addView(previousLinearLayout);
                }
            }
        }
        else
        {
            TextView tv=(TextView)rootView.findViewById(R.id.attendance);
            tv.setText("No Record(s) Found");
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