package innominatebit.srmite;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.widget.TextView;

public class DonateUs extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_donate_us);
        setTitle("Donate Us");
        TextView donateustext=(TextView)findViewById(R.id.aboutustext);
        donateustext.setText("We are a group of programmers trying to make a difference" +
                " by trying to solve anything. Simply anything! Isn't it cool? We take simple projects" +
                " which can make life simpler even if it's a little bit. Wanna help us with this initiative?" +
                " Its simple, you can join us and be a part of us in this initiative. Alternatively, you can" +
                " donate us using the QR code below or the mobile number '7358675690'" +
                " using your paytm app. We thank you for your support.\n\n~The Developer Team \u263A");
    }
}