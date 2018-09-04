package innominatebit.srmite;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.widget.TextView;

public class MeetTheDevelopers extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_meet_the_developers);
        setTitle("Meet the Developers");
        TextView dhanyatext=(TextView)findViewById(R.id.dhanyatext);
        TextView gurkeerattext=(TextView)findViewById(R.id.gurkeerattext);
        gurkeerattext.setText("Gurkeerat Singh Sondhi\nBasically a Tech enthusiast who is a Punjabi" +
                " who grew up in Kolkata and is pursuing B.Tech in CS in Chennai \uD83D\uDE00");
        dhanyatext.setText("Dhanya Baid\nAn outspoken techy travelling engineer who wants to speak to the world with his photographs and paragraphs.");
    }
}