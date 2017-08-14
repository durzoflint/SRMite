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
        ArrayList<String> subjectList = LoginActivity.subjectList;
        ArrayList<String> legends = LoginActivity.legends;
        ArrayList<String> legendMeaning = LoginActivity.legendMeaning;
        int limit=LoginActivity.numberOfHours;
        LinearLayout totaldata=(LinearLayout)rootView.findViewById(R.id.timetabledata);
        RelativeLayout.LayoutParams params = new RelativeLayout.LayoutParams
                (RelativeLayout.LayoutParams.MATCH_PARENT, RelativeLayout.LayoutParams.MATCH_PARENT);
        params.addRule(RelativeLayout.CENTER_IN_PARENT);
        LinearLayout.LayoutParams lparams = new LinearLayout.LayoutParams(LinearLayout.LayoutParams
                .MATCH_PARENT, 200,1);
        lparams.gravity = Gravity.CENTER;
        lparams.setMargins(10, 10, 10, 10);
        final Context context=this.getContext();
        LinearLayout previousLinearLayout = new LinearLayout(context);
        int j=0;
        for(int i=0;i<days.size();i++)
        {
            CardView cardView = new CardView(context);
            cardView.setLayoutParams(lparams);
            TextView thisDay = new TextView(context);
            thisDay.setLayoutParams(params);
            thisDay.setTextSize(19);
            thisDay.setGravity(Gravity.CENTER_HORIZONTAL|Gravity.CENTER_VERTICAL);
            thisDay.setText(days.get(i));
            cardView.addView(thisDay);
            String schedule = "\n";
            for(int k=1;j<limit;k++,j++)
            {
                String codes, done="";
                codes = subjectList.get(j);
                schedule = schedule + k+ ") ";
                while(codes.contains(","))
                {
                    int commaIndex = codes.indexOf(',');
                    String s = codes.substring(0, commaIndex).trim();
                    if(!done.contains(s))
                    {
                        schedule = schedule + legendMeaning.get(legends.indexOf(s)) + ", ";
                        done = done + s;
                    }
                    codes = codes.substring(commaIndex+1);
                }
                schedule = schedule.substring(0,schedule.length()-2) + "\n\n";
            }
            final int finalI = i;
            final String finalSchedule = schedule;
            cardView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    new AlertDialog.Builder(context)
                            .setTitle(days.get(finalI))
                            .setMessage(finalSchedule)
                            .setPositiveButton(android.R.string.ok, null)
                            .setIcon(android.R.drawable.ic_dialog_info)
                            .create().show();

                }
            });
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