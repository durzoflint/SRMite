package innominatebit.srmite;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.view.Gravity;
import android.view.View;
import android.view.ViewGroup;
import android.view.inputmethod.InputMethodManager;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

public class AttendanceGenie extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_attendance_genie);
        setTitle("Attendance Genie");
        Intent ad=new Intent(this,AdvertismentActivity.class);
        startActivity(ad);
        TextView message=(TextView)findViewById(R.id.message);
        message.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                message();
            }
        });
        final Spinner subjectlist=(Spinner)findViewById(R.id.subjectlist);
        final Subject subs[]=LoginActivity.subjects;
        String s[]=new String[subs.length];
        for(int i=0;i<subs.length;i++)
            s[i]=subs[i].subjectname;
        ArrayAdapter<String> adapter=new ArrayAdapter<String>(this,android.R.layout.simple_spinner_item,s) {
            @Override
            public View getView(int position, View convertView, ViewGroup parent)
            {
                return setCentered(super.getView(position, convertView, parent));
            }
            @Override
            public View getDropDownView(int position, View convertView, ViewGroup parent)
            {
                return setCentered(super.getDropDownView(position, convertView, parent));
            }
            private View setCentered(View view)
            {
                TextView textView = (TextView)view.findViewById(android.R.id.text1);
                textView.setGravity(Gravity.CENTER);
                textView.setTextSize(19);
                textView.setPaddingRelative(0,6,0,6);
                return view;
            }
        };
        subjectlist.setAdapter(adapter);
        Button check=(Button)findViewById(R.id.check);
        check.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                InputMethodManager imm = (InputMethodManager)getSystemService(Context.INPUT_METHOD_SERVICE);
                imm.hideSoftInputFromWindow(view.getWindowToken(), 0);
                String selectedsub=subjectlist.getSelectedItem().toString();
                TextView tv=(TextView)findViewById(R.id.hoursinput);
                TextView bi=(TextView)findViewById(R.id.bunkinput);
                String hours=tv.getText().toString();
                String bunkhours=bi.getText().toString();
                if(hours.length()==0)
                    hours="0";
                if(bunkhours.length()==0)
                    bunkhours="0";
                hours=removeZero(hours);
                bunkhours=removeZero(bunkhours);
                if(hours.length()<=3&&bunkhours.length()<=3)
                {
                    int a = Integer.parseInt(hours);
                    int b = Integer.parseInt(bunkhours);
                    for (int i = 0; i < subs.length; i++)
                        if (selectedsub.equals(subs[i].subjectname))
                        {
                            TextView answertext = (TextView) findViewById(R.id.answertext);
                            String answer;
                            double totalhours, absenthours, ans;
                            totalhours = Double.parseDouble(subs[i].maxhours);
                            absenthours = Double.parseDouble(subs[i].absenthours);
                            totalhours = totalhours + a+b;
                            ans = (totalhours - absenthours-b) / totalhours * 100;
                            answer = ans + "";
                            if (answer.length() > 6)
                                answer = answer.substring(0, 6);
                            answertext.setText("Your Attendance in " + subs[i].subjectname + " will now be " + answer+"%");
                        }
                }
                else
                    Toast.makeText(AttendanceGenie.this, "Input too large!", Toast.LENGTH_LONG).show();
            }
        });
    }
    String removeZero(String s)
    {
        int i=0;
        for(i=0;i<s.length()-1;i++)
        {
            char ch=s.charAt(i);
            if(ch!='0')
                break;
        }
        return s.substring(i);
    }
    void message()
    {
        new AlertDialog.Builder(AttendanceGenie.this).setTitle("Learn How to Use")
                .setMessage("This is a tool to calculate your attendance in a particular subject after" +
                        " missing/attending a few more classes. The working is very simple. Just select" +
                        " a subject, enter the number of classes you wish to attend and the number" +
                        " of classes periods you don't wish to attend and then simply hit 'Check'." +
                        " The final attendance will be displayed below.")
                .setPositiveButton(android.R.string.ok, null).setIcon(android.R.drawable.ic_dialog_info)
                .create().show();
    }
}