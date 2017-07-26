package innominatebit.srmite;

import android.content.DialogInterface;
import android.content.Intent;
import android.net.Uri;
import android.support.design.widget.TabLayout;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.view.ViewPager;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.Toast;

public class HomeActivity extends AppCompatActivity {
    private SectionsPagerAdapter mSectionsPagerAdapter;
    private ViewPager mViewPager;
    RadioGroup gpaRadioGroup;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        mSectionsPagerAdapter = new SectionsPagerAdapter(getSupportFragmentManager());
        mViewPager = (ViewPager) findViewById(R.id.container);
        mViewPager.setAdapter(mSectionsPagerAdapter);
        TabLayout tabLayout = (TabLayout) findViewById(R.id.tabs);
        tabLayout.setupWithViewPager(mViewPager);
    }
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_home, menu);
        return true;
    }
    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();
        if (id == R.id.gpacalc)
        {
            LayoutInflater inflater = LayoutInflater.from(HomeActivity.this);
            final View radioLayout = inflater.inflate(R.layout.gpa, null);
            gpaRadioGroup = (RadioGroup)radioLayout.findViewById(R.id.gparadiogroup);
            new AlertDialog.Builder(HomeActivity.this)
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
                                Intent sgpapage=new Intent(HomeActivity.this,SGPA.class);
                                startActivity(sgpapage);
                            }
                            else
                            {
                                Intent cgpapage=new Intent(HomeActivity.this,CGPA.class);
                                startActivity(cgpapage);
                            }
                        }
                    })
                    .setIcon(android.R.drawable.radiobutton_on_background)
                    .create().show();
            return true;
        }
        if (id == R.id.action_logout)
        {
            logout();
            return true;
        }
        if(id==R.id.action_contactus)
        {
            Intent intent=new Intent(this,ContactUs.class);
            startActivity(intent);
        }
        if(id==R.id.donateus)
        {
            Intent intent=new Intent(this,DonateUs.class);
            startActivity(intent);
        }
        if(id==R.id.aboutus)
        {
            Intent intent=new Intent(this,AboutUs.class);
            startActivity(intent);
        }
        if(id==R.id.action_attendancegenie)
        {
            if(!LoginActivity.attendancenotfound)
            {
                Intent intent=new Intent(this,AttendanceGenie.class);
                startActivity(intent);
            }
            else
                Toast.makeText(this, "No Attendance Record Found", Toast.LENGTH_LONG).show();
        }
        if(id==R.id.action_ad)
        {
            Intent intent=new Intent(this,AdvertismentActivity.class);
            startActivity(intent);
        }
        if(id==R.id.action_about)
        {
            new AlertDialog.Builder(this)
                    .setTitle("Attention")
                    .setMessage("This App shows your Profile Information, Test Performance and Attendance" +
                            " Details. For any other details please visit the website directly. If you" +
                            " find any issues in the content shown in the app please check the website before" +
                            " reporting it. For any issues please contact the developer team at" +
                            " info.srmite@gmail.com\n\nThank You\n~The Developer Team\n\n")
                    .setPositiveButton(android.R.string.ok, null)
                    .setIcon(android.R.drawable.ic_dialog_alert)
                    .create().show();
        }
        if(id==R.id.action_rate)
        {
            try
            {
                startActivity(new Intent(Intent.ACTION_VIEW, Uri.parse("market://details?id="+this.getPackageName())));
            }
            catch (Exception e){
                Toast.makeText(this, "Error while connecting to Play Store", Toast.LENGTH_LONG).show();
            }
        }
        return super.onOptionsItemSelected(item);
    }
    public class SectionsPagerAdapter extends FragmentPagerAdapter {
        public SectionsPagerAdapter(FragmentManager fm) {
            super(fm);
        }
        @Override
        public Fragment getItem(int position) {
            switch (position) {
                case 0:
                    Profile profile =new Profile();
                    return profile;
                case 1:
                    TestPerformance testPerformance=new TestPerformance();
                    return testPerformance;
                case 2:
                    Attendance attendance=new Attendance();
                    return attendance;
            }
            return null;
        }
        @Override
        public int getCount() {
            return 3;
        }
        @Override
        public CharSequence getPageTitle(int position) {
            switch (position) {
                case 0:
                    return "Profile";
                case 1:
                    return "Test Performance";
                case 2:
                    return "Attendance";
            }
            return null;
        }
    }
    @Override
    public void onBackPressed() {
        logout();
    }
    void logout(){
        new AlertDialog.Builder(this)
                .setTitle("Really Logout?")
                .setMessage("Are you sure you want to Logout?")
                .setNegativeButton(android.R.string.no, null)
                .setPositiveButton(android.R.string.yes, new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface arg0, int arg1) {
                        Intent i = getBaseContext().getPackageManager()
                                .getLaunchIntentForPackage( getBaseContext().getPackageName() );
                        i.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                        startActivity(i);
                    }
                }).create().show();
    }
}