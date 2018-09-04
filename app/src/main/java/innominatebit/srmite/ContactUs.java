package innominatebit.srmite;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.widget.TextView;

public class ContactUs extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_contact_us);
        setTitle("Contact Us");
        TextView message=(TextView)findViewById(R.id.message);
        message.setText("We value your feedback. Please tell us about any issues that you face while" +
                " using this App or any other features that you'd like us to add.\nAll your " +
                "suggestions are welcome. And if you don't know what to say, just drop a message to " +
                "say 'Hi'\n\n~The Developer Team");
    }
}
