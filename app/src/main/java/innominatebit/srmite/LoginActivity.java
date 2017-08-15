package innominatebit.srmite;

import android.app.ProgressDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.AsyncTask;
import android.os.Handler;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.webkit.JavascriptInterface;
import android.webkit.WebSettings;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.Toast;
import com.google.android.gms.ads.MobileAds;
import java.util.ArrayList;

public class LoginActivity extends AppCompatActivity {
    boolean flag;
    ImageView submit_button;
    static boolean attendancenotfound=false,testperformancenotfound=false;
    static ArrayList<String> profiledetails, data, days, subjectList, legends, legendMeaning;
    static int numberOfHours;
    static Subject[] subjects;
    static Tests[] t;
    static String startdate="",enddate="",attendancedata[];
    WebView webview;
    int count=0;
    Context context;
    private SharedPreferences.Editor loginPrefsEditor;
    private Boolean loginsuccess=false;
    RadioGroup gpaRadioGroup;
    ProgressDialog progressDialog;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        MobileAds.initialize(getApplicationContext(), "ca-app-pub-1997672390726507~7755617672");
        context=this;
        SharedPreferences loginPreferences = getSharedPreferences("loginPrefs", MODE_PRIVATE);
        loginPrefsEditor = loginPreferences.edit();
        boolean dontshow = loginPreferences.getBoolean("Do Not Show Again", false);
        if(!dontshow)
        {
            LayoutInflater inflater = LayoutInflater.from(this);
            final View checkboxlayout = inflater.inflate(R.layout.checkbox, null);
            new AlertDialog.Builder(context)
                    .setView(checkboxlayout)
                    .setTitle("Attention")
                    .setMessage("This App shows your Profile Information, Test Performance and Attendance" +
                            " Details. For any other details please visit the website directly. If you" +
                            " find any issues in the content shown in the app please check the website before" +
                            " reporting it. For any issues please contact the developer team at" +
                            " info.srmite@gmail.com\n\nThank You\n~The Developer Team\n\n")
                    .setPositiveButton(android.R.string.ok, new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialogInterface, int i) {
                            CheckBox dontshowagain = (CheckBox)checkboxlayout.findViewById(R.id.skip);
                            if(dontshowagain.isChecked())
                                loginPrefsEditor.putBoolean("Do Not Show Again",true);
                        }
                    })
                    .setIcon(android.R.drawable.ic_dialog_alert)
                    .create().show();
        }
        final EditText registernum=(EditText)findViewById(R.id.registernum);
        final EditText password=(EditText)findViewById(R.id.password);
        final CheckBox rememberpasswordbox=(CheckBox)findViewById(R.id.rememberpasswordbox);
        rememberpasswordbox.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(rememberpasswordbox.isChecked())
                    new AlertDialog.Builder(context).setTitle("Attention").setMessage("Your ID and Password" +
                            " are stored locally on your device. We recommend you to enable this only if this" +
                            " is your own device.")
                            .setPositiveButton(android.R.string.ok, null)
                            .setIcon(android.R.drawable.ic_dialog_alert)
                            .setCancelable(false).create().show();
            }
        });
        Boolean saveLogin = loginPreferences.getBoolean("saveLogin", false);
        if (saveLogin) {
            registernum.setText(loginPreferences.getString("id", ""));
            password.setText(loginPreferences.getString("password", ""));
            rememberpasswordbox.setChecked(true);
        }
        ImageView contactus=(ImageView)findViewById(R.id.contactustext);
        contactus.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent contactpage=new Intent(context,ContactUs.class);
                startActivity(contactpage);
            }
        });
        ImageView madewith=(ImageView)findViewById(R.id.madewith);
        //madewith.setText("Made with \u2764 from Vadapalani!");
        madewith.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent aboutuspage=new Intent(context,MeetTheDevelopers.class);
                startActivity(aboutuspage);
            }
        });
        profiledetails=new ArrayList<>();
        data=new ArrayList<>();
        days = new ArrayList<>();
        subjectList = new ArrayList<>();
        legends = new ArrayList<>();
        legendMeaning = new ArrayList<>();
        webview=(WebView)findViewById(R.id.webview);
        //webview.setVisibility(View.GONE);
        WebSettings webSettings=webview.getSettings();
        webSettings.setJavaScriptEnabled(true);
        webview.addJavascriptInterface(new MyJavaScriptInterface(), "HtmlViewer");
        webview.setWebViewClient(new WebViewClient()
        {
            @Override
            public void onPageFinished(WebView view, String url)
            {
                super.onPageFinished(view, url);
                if(count==1)
                {
                    webview.loadUrl("javascript:window.HtmlViewer.showHTML" +
                            "(document.getElementsByClassName('spacer01')[0].innerHTML);");
                }
                if(count==2)
                {
                    webview.loadUrl("javascript:window.HtmlViewer.showHTML('<head>'+document.getElementsByTagName('html')[0].innerHTML+'</head>');");
                }
                if(count==3)
                {
                    webview.loadUrl("javascript:window.HtmlViewer.showHTML('<head>'+document.getElementsByTagName('html')[0].innerHTML+'</head>');");
                }
                if(count>=4&&count<7)
                {
                    webview.loadUrl("javascript:window.HtmlViewer.showHTML('<head>'+document.getElementsByTagName('html')[0].innerHTML+'</head>');");
                }
            }
        });
        count++;
        webview.loadUrl("http://evarsity.srmuniv.ac.in/srmswi/usermanager/ParentLogin.jsp");
        submit_button = (ImageView)findViewById(R.id.submit_button);
        submit_button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view)
            {
                //if((submit_button.getText().toString()).equals("SUBMIT")) {
                if (flag){
                    if (isOnline()) {
                        String regnum = registernum.getText().toString();
                        String pass = password.getText().toString();
                        if (rememberpasswordbox.isChecked()) {
                            loginPrefsEditor.putBoolean("saveLogin", true);
                            loginPrefsEditor.putString("id", regnum);
                            loginPrefsEditor.putString("password", pass);
                            loginPrefsEditor.apply();
                        } else {
                            loginPrefsEditor.clear();
                            loginPrefsEditor.apply();
                        }
                        if (regnum.trim().length() == 0)
                            Toast.makeText(context, "Enter Register Number / Student ID", Toast.LENGTH_LONG).show();
                        else if (pass.length() == 0)
                            Toast.makeText(context, "Enter Password", Toast.LENGTH_LONG).show();
                        else {
                            count = 2;
                            webview.loadUrl("javascript: {" + "var x=document.getElementById('txtRegNumber').value='" + regnum + "';"
                                    + "var y=document.getElementById('txtPwd').value = '" + pass + "';"
                                    + "submitData();" + "};");
                            final Wait wait = new Wait();
                            wait.execute();
                            Handler handler = new Handler();
                            handler.postDelayed(new Runnable() {
                                public void run() {
                                    if(!loginsuccess)
                                        Toast.makeText(context, "This seems to be taking too long", Toast.LENGTH_LONG).show();
                                }
                            }, 20000);
                            handler.postDelayed(new Runnable() {
                                public void run() {
                                    if(!loginsuccess)
                                        Toast.makeText(context, "Aww, Damn too long", Toast.LENGTH_LONG).show();
                                }
                            }, 30000);
                            handler.postDelayed(new Runnable() {
                                public void run() {
                                    if(!loginsuccess)
                                        Toast.makeText(context, "Your device seems to have a slower connection" +
                                            " or the website is not responding.", Toast.LENGTH_LONG).show();
                                    wait.cancel(true);
                                }
                            }, 60000);
                        }
                    }
                    else
                        Toast.makeText(context, "Your Device is Offline. Please connect to the internet and restart application.", Toast.LENGTH_LONG).show();
                }
                else if(!isOnline())
                    Toast.makeText(context, "Your Device is Offline. Please connect to the internet and restart application.", Toast.LENGTH_LONG).show();
            }
        });
        //submit_button.setText("Establishing Connection");
        Load load=new Load();
        load.execute();
        ImageView gpa=(ImageView)findViewById(R.id.gpa);
        gpa.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                LayoutInflater inflater = LayoutInflater.from(LoginActivity.this);
                final View radioLayout = inflater.inflate(R.layout.gpa, null);
                gpaRadioGroup = (RadioGroup)radioLayout.findViewById(R.id.gparadiogroup);
                new AlertDialog.Builder(LoginActivity.this)
                        .setView(radioLayout)
                        .setTitle("Select")
                        .setMessage("\nPlease select SGPA to find your SGPA or CGPA for your CGPA.\n")
                        .setPositiveButton(android.R.string.ok, new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialogInterface, int i) {
                                int selectedId=gpaRadioGroup.getCheckedRadioButtonId();
                                RadioButton gpaoption=(RadioButton)radioLayout.findViewById(selectedId);
                                if(gpaoption.getText().toString().equals("SGPA"))
                                {
                                    Intent sgpapage=new Intent(context,SGPA.class);
                                    startActivity(sgpapage);
                                }
                                else
                                {
                                    Intent cgpapage=new Intent(context,CGPA.class);
                                    startActivity(cgpapage);
                                }
                            }
                        })
                        .setIcon(android.R.drawable.radiobutton_on_background)
                        .create().show();
            }
        });
    }
    private class Load extends AsyncTask<Void,Void,Void> {
        @Override
        protected void onPreExecute()
        {
            super.onPreExecute();
            webview.loadUrl("javascript:window.HtmlViewer.showHTML" +
                    "(document.getElementsByClassName('spacer01')[0].innerHTML);");
        }
        @Override
        protected Void doInBackground(Void... voids)
        {
            return null;
        }
        @Override
        protected void onPostExecute(Void aVoid)
        {
            super.onPostExecute(aVoid);
            if(!flag)
            {
                try {
                    Thread.sleep(500);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
                new Load().execute();
            }
            else
            {
                submit_button.setImageResource(R.drawable.ic_arrow_forward_black_24dp);
                //submit_button.setText("SUBMIT");
            }
        }
    }
    private class Wait extends AsyncTask<Void,Void,Void> {
        boolean serverProblem;
        @Override
        protected void onPreExecute()
        {
            super.onPreExecute();
            Toast.makeText(context, "This can take upto 1 minute, depending on your connection", Toast.LENGTH_LONG).show();
            progressDialog = ProgressDialog.show(context, "Wait!","Logging In");
        }
        @Override
        protected Void doInBackground(Void... voids)
        {
            while((data.size()!=1))
            {
                if(isCancelled())
                {
                    serverProblem=true;
                    break;
                }
            }
            if(!serverProblem)
            {
                String rawdata = data.toString();
                if(rawdata.contains("Login failed"))
                {
                    cancel(true);
                }
                if(!isCancelled())
                {
                    loginsuccess=true;
                    int a = rawdata.indexOf("Semester: ") + 10;
                    rawdata = rawdata.substring(a);
                    profiledetails.add(rawdata.substring(0, rawdata.indexOf('<')));
                    a = rawdata.indexOf("Section : ") + 10;
                    rawdata = rawdata.substring(a);
                    profiledetails.add(rawdata.substring(0, rawdata.indexOf('<')));
                }
            }
            data.clear();
            return null;
        }
        @Override
        protected void onPostExecute(Void aVoid)
        {
            super.onPostExecute(aVoid);
            Home home = new Home();
            home.execute();
        }
        @Override
        protected void onCancelled(Void aVoid)
        {
            super.onCancelled(aVoid);
            progressDialog.dismiss();
            if (serverProblem)
            {
                Toast.makeText(context, "Please check back again later", Toast.LENGTH_LONG).show();
            }
            else
            {
                Toast.makeText(context,"Login Failed. ID and Password do not Match",Toast.LENGTH_LONG).show();
            }
        }
    }
    private class Home extends AsyncTask<Void,Void,Void> {
        @Override
        protected void onPreExecute()
        {
            super.onPreExecute();
            progressDialog.setMessage("Fetching Profile");
            count++;
            webview.loadUrl("http://evarsity.srmuniv.ac.in/srmswi/resource/StudentDetailsResources.jsp?resourceid=1");
        }
        @Override
        protected Void doInBackground(Void... voids)
        {
            while (data.size()!=1);
            processProfileDetails();
            return null;
        }
        @Override
        protected void onPostExecute(Void aVoid)
        {
            super.onPostExecute(aVoid);
            Test test=new Test();
            test.execute();
        }
        @Override
        protected void onCancelled(Void aVoid) {
            progressDialog.dismiss();
            super.onCancelled(aVoid);
        }
    }
    private class Test extends AsyncTask<Void,Void,Void> {
        @Override
        protected void onPreExecute()
        {
            super.onPreExecute();
            count++;
            webview.loadUrl("http://evarsity.srmuniv.ac.in/srmswi/resource/StudentDetailsResources.jsp?resourceid=16");
            progressDialog.setMessage("Fetching Test Performance");
        }
        @Override
        protected Void doInBackground(Void... voids)
        {
            while (data.size()!=1);
            processTestPerformance();
            return null;
        }
        @Override
        protected void onPostExecute(Void aVoid)
        {
            super.onPostExecute(aVoid);
            TimeTable timeTable = new TimeTable();
            timeTable.execute();
        }
        @Override
        protected void onCancelled(Void aVoid)
        {
            progressDialog.dismiss();
            super.onCancelled(aVoid);
        }
    }
    private class TimeTable extends AsyncTask<Void, Void, Void> {
        @Override
        protected void onPreExecute() {
            super.onPreExecute();
            count++;
            webview.loadUrl("http://evarsity.srmuniv.ac.in/srmswi/resource/StudentDetailsResources.jsp?resourceid=5");
            progressDialog.setMessage("Fetching Time Table");
        }
        @Override
        protected Void doInBackground(Void... voids) {
            while (data.size()!=1);
            processTimeTable();
            return null;
        }
        @Override
        protected void onPostExecute(Void aVoid) {
            super.onPostExecute(aVoid);
            Attendance attend=new Attendance();
            attend.execute();
        }
        @Override
        protected void onCancelled(Void aVoid) {
            progressDialog.dismiss();
            super.onCancelled(aVoid);
        }
    }
    private class Attendance extends AsyncTask<Void,Void,Void> {
        @Override
        protected void onPreExecute()
        {
            super.onPreExecute();
            count++;
            webview.loadUrl("http://evarsity.srmuniv.ac.in/srmswi/resource/StudentDetailsResources.jsp?resourceid=7");
            progressDialog.setMessage("Fetching Attendance");
        }
        @Override
        protected Void doInBackground(Void... voids)
        {
            while (data.size()!=1);
            processAttendance();
            return null;
        }
        @Override
        protected void onPostExecute(Void aVoid)
        {
            super.onPostExecute(aVoid);
            webview.loadUrl("http://evarsity.srmuniv.ac.in/srmswi/usermanager/Logout.jsp");
            webview.loadUrl("http://evarsity.srmuniv.ac.in/srmswi/usermanager/ParentLogin.jsp");
            Intent intent=new Intent(context,HomeActivity.class);
            startActivity(intent);
            progressDialog.dismiss();
        }
        @Override
        protected void onCancelled(Void aVoid)
        {
            super.onCancelled(aVoid);
            progressDialog.dismiss();
        }
    }
    void processTimeTable() {
        String rawdata = data.toString();
        rawdata=rawdata.substring(rawdata.indexOf("tabletitle05"));
        rawdata=rawdata.substring(rawdata.indexOf("tr")+2);
        String hours = rawdata.substring(0, rawdata.indexOf("/tr"));
        numberOfHours = getNumbers("tabletitle05", hours);
        rawdata = rawdata.substring(rawdata.indexOf("/tr")+3);
        while(rawdata.contains("tabletitle05"))
        {
            int j = rawdata.indexOf("tabletitle05");
            String day = rawdata.substring(j + 12);
            days.add(getData(day));
            for(int i=0;i<numberOfHours;i++)
            {
                int k = rawdata.indexOf("tablecontent02");
                String subject = rawdata.substring(k + 14);
                rawdata = rawdata.substring(k + 14);
                subjectList.add(getData(subject)+",");
            }
            rawdata = rawdata.substring(j + 12);
        }
        while(rawdata.contains("tablecontent01"))
        {
            int j = rawdata.indexOf("tablecontent01");
            String day = rawdata.substring(j + 12);
            legends.add(getData(day));
            rawdata = rawdata.substring(j + 12);
            j = rawdata.indexOf("tablecontent01");
            String legend = rawdata.substring(j + 12);
            legendMeaning.add(getData(legend));;
            rawdata = rawdata.substring(j + 12);
        }
        legends.add("-");
        legendMeaning.add("FREE HOUR");;
        data.clear();
    }
    void processAttendance() {
        String rawdata;
        rawdata=data.toString();
        if(rawdata.contains("No Record(s) Found"))
            attendancenotfound=true;
        else
        {
            rawdata=rawdata.substring(rawdata.indexOf("tabletitle05"));
            int a=rawdata.indexOf("During the Period  :");
            int b=rawdata.indexOf("&nbsp;&nbsp;TO");
            startdate=(rawdata.substring(a+"During the Period  :".length(),b)).trim();
            a=rawdata.indexOf("TO&nbsp;&nbsp;");
            b=rawdata.indexOf('<');
            enddate=(rawdata.substring(a+"TO&nbsp;&nbsp;".length(),b)).trim();
            rawdata=rawdata.substring(rawdata.indexOf("tablecontent01"),rawdata.indexOf("CUMULATIVE "));
            int n=getNumbers("tablecontent01",rawdata);
            int numberofsubjects=n/2;
            subjects = new Subject[numberofsubjects];
            for(int i=0;i<numberofsubjects;i++)
            {
                subjects[i]=new Subject();
                rawdata=rawdata.substring(rawdata.indexOf("tablecontent01")+14);
                subjects[i].setSubjectcode(getData(rawdata));
                rawdata=rawdata.substring(rawdata.indexOf("tablecontent01")+14);
                subjects[i].setSubjectname(getData(rawdata));
                rawdata=rawdata.substring(rawdata.indexOf("tablecontent02")+14);
                subjects[i].setMaxhours(getData(rawdata));
                rawdata=rawdata.substring(rawdata.indexOf("tablecontent02")+14);
                subjects[i].setAtthours(getData(rawdata));
                rawdata=rawdata.substring(rawdata.indexOf("tablecontent02")+14);
                subjects[i].setAbsenthours(getData(rawdata));
                rawdata=rawdata.substring(rawdata.indexOf("tablecontent02")+14);
                subjects[i].setAverage(getData(rawdata));
                rawdata=rawdata.substring(rawdata.indexOf("tablecontent02")+14);
                subjects[i].setOD_ML(getData(rawdata));
                rawdata=rawdata.substring(rawdata.indexOf("tablecontent02")+14);
                subjects[i].setTotal(getData(rawdata));
            }
            int i;
            attendancedata=new String[6];
            rawdata=rawdata.substring(rawdata.indexOf("tabletitle05")+13);
            for(i=0;i<5;i++)
            {
                rawdata=rawdata.substring(rawdata.indexOf("tabletitle05")+13);
                attendancedata[i]=getData(rawdata);
            }
            rawdata=rawdata.substring(rawdata.indexOf("b>"));
            attendancedata[i]=getData(rawdata);
        }
        data.clear();
    }
    void processTestPerformance() {
        String exams[],rawdata;
        rawdata=data.toString();
        if(rawdata.contains("No Record(s) Found"))
            testperformancenotfound=true;
        else
        {
            int numberoftests=getNumbers("tabletitle03",rawdata);
            t = new Tests[numberoftests];
            exams=new String[numberoftests];
            rawdata=rawdata+"tabletitle03";
            for(int i=0;i<numberoftests;i++)
            {
                t[i]=new Tests();
                int j=rawdata.indexOf("tabletitle03");
                int k=(rawdata.substring(j+1)).indexOf("tabletitle03");
                exams[i]=rawdata.substring(j,j+1+k);
                t[i].setTestname(getData(exams[i]));
                rawdata=rawdata.substring(j+12);
            }
            for(int i=0;i<numberoftests;i++)
            {
                int numberofsubjects=getNumbers("tablecontent01",exams[i]);
                t[i].setNumberofsubjects(numberofsubjects);
                Subject[] sub=new Subject[numberofsubjects];
                for(int j=0;j<numberofsubjects;j++)
                {
                    sub[j]=new Subject();
                    exams[i]=exams[i].substring(exams[i].indexOf("tablecontent02"));
                    sub[j].setSubjectcode(getData(exams[i]));
                    exams[i]=exams[i].substring(exams[i].indexOf("tablecontent01"));
                    sub[j].setSubjectname(getData(exams[i]));
                    exams[i]=exams[i].substring(exams[i].indexOf("tablecontent02"));
                    sub[j].setSubjectmarks(getData(exams[i]));
                    exams[i]=exams[i].substring(exams[i].indexOf("tablecontent02")+14);
                }
                t[i].setS(sub);
            }
        }
        data.clear();
    }
    void processProfileDetails() {
        String rawdata=data.toString();
        int n=getNumbers("tablecontent01",rawdata);
        String x;
        for(int i=1;i<=n-4;i++)
        {
            rawdata=rawdata.substring(rawdata.indexOf("tablecontent01")+14);
            x=getData(rawdata).trim();
            if(x.equals("")|| x.contains("WELCOME"))
                continue;
            if(x.equals("Parent Contact Mobile No. (10 digit)")
                    ||x.equals("Parent Contact Mobile No.")||x.equals("Place of Birth"))
                rawdata=rawdata.substring(rawdata.indexOf("tablecontent01")+14);
            else
                profiledetails.add(x);
            if(x.equals("Student Contact Mobile No. (10 digit)"))
            {
                rawdata=rawdata.substring(rawdata.indexOf("value='")+7);
                profiledetails.add(rawdata.substring(0,rawdata.indexOf("'")));
            }
        }
        data.clear();
    }
    String getData(String source) {
        int a=source.indexOf('>');
        int b=source.indexOf('<');
        String ans=source.substring(a+1,b);
        return ans.trim();
    }
    int getNumbers(String s,String currentdata) {
        String copy=currentdata;
        int n=copy.indexOf(s),c=0;
        while(n!=-1)
        {
            c++;
            copy=copy.substring(n+1);
            n=copy.indexOf(s);
        }
        return c;
    }
    private class MyJavaScriptInterface {
        @JavascriptInterface
        public void showHTML(String html) {
            html=html.trim();
            if(count==1)
                flag=html.contains("Welcome!");
            if(count==2)
                data.add(html);
            if(count==3)
                data.add(html);
            if(count>=4)
                data.add(html);
        }
    }
    private boolean isOnline() {
        ConnectivityManager connMgr = (ConnectivityManager) getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo networkInfo = connMgr.getActiveNetworkInfo();
        return  (networkInfo != null && networkInfo.isConnected());
    }
    @Override
    public void onDestroy() {
        super.onDestroy();
        if (progressDialog != null) {
            progressDialog.dismiss();
            progressDialog = null;
        }
    }
}