package innominatebit.srmite;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

public class AdvertismentActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,
                WindowManager.LayoutParams.FLAG_FULLSCREEN);
        setContentView(R.layout.activity_advertisment);
        setTitle("Fests / Events");
        getSupportActionBar().hide();
        //TODO
        //This is a temporary setup to display the add template. Need a more permanent solution
        RelativeLayout rl=(RelativeLayout)findViewById(R.id.rl);
        rl.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                onBackPressed();
            }
        });
        TextView message=(TextView) findViewById(R.id.message);
        message.setText("To place your advertisment here please contact us at\ninfo.srmite@gmail.com");
        Toast.makeText(this, "Tap once anywhere to dismiss", Toast.LENGTH_LONG).show();
        /*Date curDate = new Date();
        SimpleDateFormat format = new SimpleDateFormat("dd/MM/yyyy HH:mm:ss");
        try {
            Date date = format.parse("27/03/2017 15:00:00");
            long diffInMillisec = date.getTime() - curDate.getTime();
            if(diffInMillisec<=0)
            {
                ImageView ad=(ImageView)findViewById(R.id.ad);
                ad.setVisibility(View.INVISIBLE);
            }
            else
                setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_LANDSCAPE);
        }catch (ParseException e) {
            e.printStackTrace();
        }*/
    }
}