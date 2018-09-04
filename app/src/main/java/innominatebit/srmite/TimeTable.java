package innominatebit.srmite;

import android.content.Context;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AlertDialog;
import android.support.v7.widget.CardView;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.google.android.gms.ads.AdRequest;
import com.google.android.gms.ads.AdView;

import java.util.ArrayList;

public class TimeTable extends Fragment
{
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.fragment_timetable, container, false);
        AdView mAdView = (AdView) rootView.findViewById(R.id.adView);
        AdRequest adRequest = new AdRequest.Builder().build();
        mAdView.loadAd(adRequest);
        final ArrayList<String> days = LoginActivity.days;
        final ArrayList<String> subjectList = LoginActivity.subjectList;
        final ArrayList<String> legends = LoginActivity.legends;
        final ArrayList<String> legendMeaning = LoginActivity.legendMeaning;
        int limit=LoginActivity.numberOfHours;
        LinearLayout totaldata=(LinearLayout)rootView.findViewById(R.id.timetabledata);
        RelativeLayout.LayoutParams params = new RelativeLayout.LayoutParams
                (RelativeLayout.LayoutParams.MATCH_PARENT, RelativeLayout.LayoutParams.MATCH_PARENT);
        params.addRule(RelativeLayout.CENTER_IN_PARENT);
        final LinearLayout.LayoutParams lparams = new LinearLayout.LayoutParams(LinearLayout.LayoutParams
                .MATCH_PARENT, 200,1);
        lparams.gravity = Gravity.CENTER;
        lparams.setMargins(10, 10, 10, 10);
        final Context context=this.getContext();
        LinearLayout previousLinearLayout = new LinearLayout(context);
        int j = 0;
        for(int i=0;i<days.size();i++)
        {
            final CardView cardView = new CardView(context);
            cardView.setLayoutParams(lparams);
            TextView thisDay = new TextView(context);
            thisDay.setLayoutParams(params);
            thisDay.setTextSize(19);
            thisDay.setGravity(Gravity.CENTER_HORIZONTAL|Gravity.CENTER_VERTICAL);
            thisDay.setText(days.get(i));
            cardView.addView(thisDay);
            final int finalI = i;
            final int jj = j;
            final int finalLimit = limit;
            cardView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    int startAt = jj;
                    LinearLayout.LayoutParams tv1Params = new LinearLayout.LayoutParams(LinearLayout.LayoutParams
                            .MATCH_PARENT, LinearLayout.LayoutParams.WRAP_CONTENT, 7);
                    tv1Params.gravity = Gravity.CENTER;
                    LinearLayout.LayoutParams tv2Params = new LinearLayout.LayoutParams(LinearLayout.LayoutParams
                            .MATCH_PARENT, LinearLayout.LayoutParams.WRAP_CONTENT, 3);
                    tv2Params.gravity = Gravity.CENTER;
                    LinearLayout.LayoutParams layoutParams = new LinearLayout.LayoutParams(LinearLayout.LayoutParams
                            .MATCH_PARENT, LinearLayout.LayoutParams.WRAP_CONTENT, 1);
                    LayoutInflater tableInflater = LayoutInflater.from(context);
                    final View timeTableDialog = tableInflater.inflate(R.layout.timetable_dialog, null);
                    LinearLayout table = (LinearLayout)timeTableDialog.findViewById(R.id.table);
                    for(int k = 1; startAt < finalLimit; k++, startAt++)
                    {
                        LinearLayout tempLL = new LinearLayout(context);
                        tempLL.setLayoutParams(layoutParams);
                        tempLL.setPadding(4,4,4,4);
                        tempLL.setBackground(ContextCompat.getDrawable(getContext(), R.drawable.border));
                        String codes, done="";
                        codes = subjectList.get(startAt);
                        TextView tempTV = new TextView(context);
                        tempTV.setLayoutParams(tv1Params);
                        tempTV.setGravity(Gravity.CENTER);
                        tempTV.setText("Hour "+k);
                        tempLL.addView(tempTV);
                        String tempSchedule ="";
                        while(codes.contains(","))
                        {
                            int commaIndex = codes.indexOf(',');
                            String s = codes.substring(0, commaIndex).trim();
                            if(!done.contains(s))
                            {
                                tempSchedule  = tempSchedule + legendMeaning.get(legends.indexOf(s)) + ", ";
                                done = done + s;
                            }
                            codes = codes.substring(commaIndex+1);
                        }
                        TextView tempTV2 = new TextView(context);
                        tempTV2.setLayoutParams(tv2Params);
                        tempTV2.setGravity(Gravity.CENTER);
                        tempTV2.setBackground(ContextCompat.getDrawable(getContext(), R.drawable.leftborder));
                        tempTV2.setText(tempSchedule.substring(0, tempSchedule.length()-2));
                        tempLL.addView(tempTV2);
                        table.addView(tempLL);
                    }
                    new AlertDialog.Builder(context)
                            .setTitle(days.get(finalI))
                            .setView(timeTableDialog)
                            .setPositiveButton(android.R.string.ok, null)
                            .create().show();
                }
            });
            j+=LoginActivity.numberOfHours;
            limit+=LoginActivity.numberOfHours;
            if(i%2 == 0)
            {
                LinearLayout myLinearLayout = new LinearLayout(context);
                myLinearLayout.setLayoutParams(lparams);
                myLinearLayout.setOrientation(LinearLayout.HORIZONTAL);
                myLinearLayout.addView(cardView);
                previousLinearLayout = myLinearLayout;
                if(i+1==days.size())
                    totaldata.addView(previousLinearLayout);
            }
            else
            {
                previousLinearLayout.addView(cardView);
                totaldata.addView(previousLinearLayout);
            }
        }
        return rootView;
    }
}