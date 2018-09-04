package innominatebit.srmite;

import android.content.Context;
import android.content.DialogInterface;
import android.os.Bundle;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;

public class SGPA extends AppCompatActivity {
    ArrayList<Integer> creditIds;
    ArrayList<Integer> gradeIds;
    ArrayList<String> credits;
    ArrayList<String> grades;
    ArrayList<Integer> gradePoints;
    RadioGroup radioGroup;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sgpa);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        credits=new ArrayList<>();
        grades=new ArrayList<>();
        gradePoints=new ArrayList<>();
        creditIds=new ArrayList<>();
        gradeIds=new ArrayList<>();
        LayoutInflater inflater = LayoutInflater.from(this);
        final View radioLayout = inflater.inflate(R.layout.regulations, null);
        radioGroup = (RadioGroup)radioLayout.findViewById(R.id.radioGroup);
        new AlertDialog.Builder(this)
                .setView(radioLayout)
                .setTitle("Regulations?")
                .setMessage("\nPlease select the regulations you belong too. If you are admitted in" +
                        " or after 2015 select 2015 Regulations else if you were admitted in or after 2013" +
                        " select 2013 Regulations\n")
                .setPositiveButton(android.R.string.ok, new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                        int selectedId=radioGroup.getCheckedRadioButtonId();
                        RadioButton regulation=(RadioButton)radioLayout.findViewById(selectedId);
                        if(regulation.getText().toString().equals("2015 Regulations"))
                            fill2015();
                        else
                            fill2013();
                    }
                })
                .setIcon(android.R.drawable.radiobutton_on_background)
                .setCancelable(false).create().show();
        final LinearLayout subjectData=(LinearLayout)findViewById(R.id.subjectdata);
        final Button submit=(Button)findViewById(R.id.submit);
        final EditText numberofsubjects=(EditText)findViewById(R.id.numberofsubjects);
        final Button calculate=(Button)findViewById(R.id.calculate);
        submit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String input=numberofsubjects.getText().toString();
                if(input.length()>=1)
                {
                    int num=Integer.parseInt(input);
                    if(num>=1)
                    {
                        for(int i=0;i<Integer.parseInt(input);i++)
                            addViewForAnotherSubject(subjectData);
                        numberofsubjects.setVisibility(View.GONE);
                        submit.setVisibility(View.GONE);
                        InputMethodManager imm = (InputMethodManager)getSystemService(Context.INPUT_METHOD_SERVICE);
                        imm.hideSoftInputFromWindow(view.getWindowToken(), 0);
                        TextView creditinfo=(TextView)findViewById(R.id.creditinfo);
                        TextView gradeinfo=(TextView)findViewById(R.id.gradeinfo);
                        creditinfo.setVisibility(View.VISIBLE);
                        gradeinfo.setVisibility(View.VISIBLE);
                        calculate.setEnabled(true);
                    }
                    else
                        Toast.makeText(SGPA.this, "Invalid Input", Toast.LENGTH_LONG).show();
                }
                else
                    Toast.makeText(SGPA.this, "Invalid Input", Toast.LENGTH_LONG).show();
            }
        });
        calculate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                calculateSGPA();
            }
        });
    }
    void calculateSGPA()
    {
        double num=0.0,deno=0.0;
        for(int i=0;i<gradeIds.size();i++)
        {
            Spinner spin1=(Spinner)findViewById(gradeIds.get(i));
            Spinner spin2=(Spinner)findViewById(creditIds.get(i));
            String selectedgrade=spin1.getSelectedItem().toString();
            String selectedcredit=spin2.getSelectedItem().toString();
            int credit=Integer.parseInt(selectedcredit);
            int gradepoint=0;
            for(int j=0;j<grades.size();j++)
                if(grades.get(j).equals(selectedgrade))
                    gradepoint=gradePoints.get(j);
            num=num+gradepoint*credit;
            deno=deno+credit;
        }
        String ans=""+num/deno;
        if(ans.length()>6)
            ans=ans.substring(0,6);
        TextView result=(TextView)findViewById(R.id.result);
        result.setText("Your SGPA is\n"+ans);
    }
    void addViewForAnotherSubject(LinearLayout subjectData)
    {
        Context c =this;
        LinearLayout.LayoutParams lp = new LinearLayout.LayoutParams(LinearLayout
                .LayoutParams.MATCH_PARENT, LinearLayout.LayoutParams.MATCH_PARENT);
        LinearLayout l=new LinearLayout(c);
        l.setOrientation(LinearLayout.HORIZONTAL);
        l.setLayoutParams(lp);
        l.setGravity(Gravity.CENTER_HORIZONTAL);
        Spinner creditspinner=new Spinner(c);
        Spinner gradespinner=new Spinner(c);
        int id1=View.generateViewId();
        int id2=View.generateViewId();
        creditspinner.setId(id1);
        creditIds.add(id1);
        gradespinner.setId(id2);
        gradeIds.add(id2);
        ArrayAdapter<String> creditSpinnerArrayAdapter = new ArrayAdapter<>(this,
                android.R.layout.simple_spinner_dropdown_item, credits);
        creditspinner.setAdapter(creditSpinnerArrayAdapter);
        ArrayAdapter<String> gradeSpinnerArrayAdapter = new ArrayAdapter<>(this,
                android.R.layout.simple_spinner_dropdown_item, grades);
        gradespinner.setAdapter(gradeSpinnerArrayAdapter);
        LinearLayout.LayoutParams spinnerParams = new LinearLayout.LayoutParams(LinearLayout
                .LayoutParams.WRAP_CONTENT, LinearLayout.LayoutParams.WRAP_CONTENT);
        spinnerParams.setMargins(100,0,100,0);
        creditspinner.setLayoutParams(spinnerParams);
        gradespinner.setLayoutParams(spinnerParams);
        l.addView(gradespinner);
        l.addView(creditspinner);
        subjectData.addView(l);
    }
    void fill2015()
    {
        grades.add("O");    gradePoints.add(10);
        grades.add("A+");   gradePoints.add(9);
        grades.add("A");    gradePoints.add(8);
        grades.add("B+");   gradePoints.add(7);
        grades.add("B");    gradePoints.add(6);
        grades.add("C");    gradePoints.add(5);
        grades.add("P");    gradePoints.add(4);
        grades.add("F");    gradePoints.add(0);
        grades.add("Ab");   gradePoints.add(0);
        grades.add("I");    gradePoints.add(0);
        for(int i=1;i<=10;i++)
            credits.add(""+i);
    }
    void fill2013()
    {
        grades.add("S");    gradePoints.add(10);
        grades.add("A");   gradePoints.add(9);
        grades.add("B");    gradePoints.add(8);
        grades.add("C");   gradePoints.add(7);
        grades.add("D");    gradePoints.add(5);
        grades.add("U");    gradePoints.add(0);
        grades.add("W");    gradePoints.add(0);
        grades.add("I");    gradePoints.add(0);
        for(int i=1;i<=10;i++)
            credits.add(""+i);
    }
    /*void removeViewForLastSubject(LinearLayout subjectData)
    {
        int id1=creditIds.get(creditIds.size()-1);
        int id2=gradeIds.get(gradeIds.size()-1);
        View creditSpinner = findViewById(id1);
        View gradeSpinner = findViewById(id2);
        ViewGroup parentcredit = (ViewGroup) creditSpinner.getParent();
        ViewGroup parentgrade = (ViewGroup) gradeSpinner.getParent();
        if (parentcredit != null)
            parentcredit.removeView(creditSpinner);
        if (parentgrade!= null)
            parentgrade.removeView(gradeSpinner);
        creditIds.remove(creditIds.size()-1);
        gradeIds.remove(gradeIds.size()-1);
    }*/
}