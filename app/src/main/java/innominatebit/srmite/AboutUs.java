package innominatebit.srmite;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.widget.TextView;

public class AboutUs extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_about_us);
        setTitle("Meet the Developers");
        TextView abhinavtext=(TextView)findViewById(R.id.abhinavtext);
        TextView dhanyatext=(TextView)findViewById(R.id.dhanyatext);
        TextView gurkeerattext=(TextView)findViewById(R.id.gurkeerattext);
        gurkeerattext.setText("Gurkeerat Singh Sondhi\nBasically a Tech enthusiast who is a Punjabi" +
                " who grew up in Kolkata and is pursuing B.Tech in CS in Chennai \uD83D\uDE00");
        abhinavtext.setText("Abhinav Upadhyay\nI simply love coding;");
        dhanyatext.setText("Dhanya Baid\nAn outspoken techy travelling engineer who wants to speak to the world with his photographs and paragraphs.");
    }
}