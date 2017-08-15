package innominatebit.srmite;

import android.content.DialogInterface;
import android.content.Intent;
import android.net.Uri;
import android.support.design.widget.NavigationView;
import android.support.design.widget.TabLayout;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.view.ViewPager;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.Toast;

public class HomeActivity extends AppCompatActivity
{
    private DrawerLayout drawerLayout;
    private Toolbar toolbar;
    RadioGroup gpaRadioGroup;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home);
        SectionsPagerAdapter mSectionsPagerAdapter = new SectionsPagerAdapter(getSupportFragmentManager());
        ViewPager mViewPager = (ViewPager) findViewById(R.id.container);
        mViewPager.setOffscreenPageLimit(2);
        mViewPager.setAdapter(mSectionsPagerAdapter);
        TabLayout tabLayout = (TabLayout) findViewById(R.id.tabs);
        tabLayout.setupWithViewPager(mViewPager);
        toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        drawerLayout = (DrawerLayout) findViewById(R.id.drawer);
        navigationDrawer();
    }
    private class SectionsPagerAdapter extends FragmentPagerAdapter {
        public SectionsPagerAdapter(FragmentManager fm) {
            super(fm);
        }
        @Override
        public Fragment getItem(int position) {
            switch (position) {
                case 0:
                    Attendance attendance=new Attendance();
                    return attendance;
                    /*Profile profile =new Profile();
                    return profile;*/
                case 1:
                    TestPerformance testPerformance=new TestPerformance();
                    return testPerformance;
                case 2:
                    TimeTable timeTable = new TimeTable();
                    return timeTable;
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
                    return "Attendance";
                case 1:
                    return "Test Performance";
                case 2:
                    return "Time Table";
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
    public void navigationDrawer() {
        NavigationView nview = (NavigationView) findViewById(R.id.navigation_view);
        nview.setNavigationItemSelectedListener(new NavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(MenuItem menuItem) {
                int id = menuItem.getItemId();
                if (id == R.id.gpacalc)
                {
                    drawerLayout.closeDrawers();
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

                }
                if (id == R.id.action_logout)
                {
                    drawerLayout.closeDrawers();
                    logout();
                }
                if(id==R.id.action_contactus)
                {
                    drawerLayout.closeDrawers();
                    Intent intent=new Intent(HomeActivity.this,ContactUs.class);
                    startActivity(intent);
                }
                if(id==R.id.donateus)
                {
                    drawerLayout.closeDrawers();
                    Intent intent=new Intent(HomeActivity.this,DonateUs.class);
                    startActivity(intent);
                }
                if(id==R.id.aboutus)
                {
                    drawerLayout.closeDrawers();
                    Intent intent=new Intent(HomeActivity.this,MeetTheDevelopers.class);
                    startActivity(intent);
                }
                if(id==R.id.action_attendancegenie)
                {
                    drawerLayout.closeDrawers();
                    if(!LoginActivity.attendancenotfound)
                    {
                        Intent intent=new Intent(HomeActivity.this,AttendanceGenie.class);
                        startActivity(intent);
                    }
                    else
                        Toast.makeText(HomeActivity.this, "No Attendance Record Found", Toast.LENGTH_LONG).show();
                }
                if(id==R.id.action_ad)
                {
                    drawerLayout.closeDrawers();
                    Intent intent=new Intent(HomeActivity.this,AdvertismentActivity.class);
                    startActivity(intent);
                }
                if(id==R.id.action_about)
                {
                    drawerLayout.closeDrawers();
                    new AlertDialog.Builder(HomeActivity.this)
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
                    drawerLayout.closeDrawers();
                    try
                    {
                        startActivity(new Intent(Intent.ACTION_VIEW, Uri.parse("market://details?id="+HomeActivity.this.getPackageName())));
                    }
                    catch (Exception e){
                        Toast.makeText(HomeActivity.this, "Error while connecting to Play Store", Toast.LENGTH_LONG).show();
                    }
                }
                return true;
            }
        });
        ActionBarDrawerToggle dtoogle = new ActionBarDrawerToggle(this, drawerLayout, toolbar, R.string.draweropen, R.string.drawerclose) {
            @Override
            public void onDrawerClosed(View v) {
                super.onDrawerClosed(v);
            }

            @Override
            public void onDrawerOpened(View v) {
                super.onDrawerOpened(v);
            }
        };
        drawerLayout.addDrawerListener(dtoogle);
        dtoogle.setDrawerIndicatorEnabled(true);
        dtoogle.syncState();
    }
}