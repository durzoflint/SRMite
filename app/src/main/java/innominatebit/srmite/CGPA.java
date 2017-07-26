package innominatebit.srmite;

import android.content.Context;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.InputType;
import android.view.Gravity;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;

public class CGPA extends AppCompatActivity {
    ArrayList<Integer> semSGPAIds;
    ArrayList<Integer> semCreditIds;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_cgpa);
        setTitle("Calculate Your CGPA");
        semCreditIds=new ArrayList<>();
        semSGPAIds=new ArrayList<>();
        final LinearLayout gpaData = (LinearLayout)findViewById(R.id.gpadata);
        final Button submit=(Button)findViewById(R.id.submit);
        final EditText numberofsems=(EditText)findViewById(R.id.numberodsems);
        final Button calculate=(Button)findViewById(R.id.calculate);
        submit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String input = numberofsems.getText().toString();
                if (input.length() >= 1) {
                    int num = Integer.parseInt(input);
                    if (num >= 1) {
                        for (int i = 0; i < Integer.parseInt(input); i++)
                            addViewForAnotherSem(gpaData);
                        numberofsems.setVisibility(View.GONE);
                        submit.setVisibility(View.GONE);
                        InputMethodManager imm = (InputMethodManager) getSystemService(Context.INPUT_METHOD_SERVICE);
                        imm.hideSoftInputFromWindow(view.getWindowToken(), 0);
                        TextView totalCreditInfo = (TextView) findViewById(R.id.totalcreditinfo);
                        TextView sgpaInfo = (TextView) findViewById(R.id.sgpainfo);
                        totalCreditInfo.setVisibility(View.VISIBLE);
                        sgpaInfo.setVisibility(View.VISIBLE);
                        calculate.setEnabled(true);
                    } else
                        Toast.makeText(CGPA.this, "Invalid Input", Toast.LENGTH_LONG).show();
                } else
                    Toast.makeText(CGPA.this, "Invalid Input", Toast.LENGTH_LONG).show();
            }
        });
        calculate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                calculateCGPA();
            }
        });
    }
    void calculateCGPA()
    {
        double num=0.0,deno=0.0;
        for(int i=0;i<semSGPAIds.size();i++)
        {
            EditText sgpaData=(EditText)findViewById(semSGPAIds.get(i));
            EditText semCreditData=(EditText)findViewById(semCreditIds.get(i));
            if(sgpaData.length()>0&&semCreditData.length()>0)
            {
                double sgpa=Double.parseDouble(sgpaData.getText().toString());
                double semCredit=Double.parseDouble(semCreditData.getText().toString());
                if(sgpa>0&&semCredit>0)
                {
                    num=num+semCredit*sgpa;
                    deno=deno+semCredit;
                }
                else
                    Toast.makeText(this, "Invalid Input", Toast.LENGTH_LONG).show();
            }
            else
                Toast.makeText(this, "Invalid Input", Toast.LENGTH_LONG).show();
        }
        String ans=""+num/deno;
        if(ans.length()>6)
            ans=ans.substring(0,6);
        TextView result=(TextView)findViewById(R.id.result);
        result.setText("Your CGPA is\n"+ans);
    }
    void addViewForAnotherSem(LinearLayout gpaData)
    {
        Context c =this;
        LinearLayout.LayoutParams lp = new LinearLayout.LayoutParams(LinearLayout
                .LayoutParams.MATCH_PARENT, LinearLayout.LayoutParams.MATCH_PARENT);
        LinearLayout l=new LinearLayout(c);
        l.setOrientation(LinearLayout.HORIZONTAL);
        l.setLayoutParams(lp);
        l.setGravity(Gravity.CENTER_HORIZONTAL);
        int id1= View.generateViewId();
        int id2=View.generateViewId();
        EditText semSGPA=new EditText(c);
        semSGPA.setEms(6);
        semSGPA.setGravity(Gravity.CENTER);
        semSGPA.setId(id1);
        semSGPA.setInputType(InputType.TYPE_CLASS_NUMBER | InputType.TYPE_NUMBER_FLAG_DECIMAL);
        semSGPAIds.add(id1);
        EditText totalCredit=new EditText(c);
        totalCredit.setEms(2);
        totalCredit.setGravity(Gravity.CENTER);
        totalCredit.setId(id2);
        totalCredit.setInputType(InputType.TYPE_CLASS_NUMBER);
        semCreditIds.add(id2);
        LinearLayout.LayoutParams inputParams = new LinearLayout.LayoutParams(LinearLayout
                .LayoutParams.WRAP_CONTENT, LinearLayout.LayoutParams.WRAP_CONTENT);
        inputParams.setMargins(20,0,180,0);
        semSGPA.setLayoutParams(inputParams);
        totalCredit.setLayoutParams(inputParams);
        l.addView(semSGPA);
        l.addView(totalCredit);
        gpaData.addView(l);
    }

}