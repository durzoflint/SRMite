package innominatebit.srmite;

import android.content.Context;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.google.android.gms.ads.AdRequest;
import com.google.android.gms.ads.AdView;

import java.util.ArrayList;

/**
 * Created by Abhinav on 02-01-2017.
 */

public class Profile extends Fragment {
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.fragment_profile, container, false);

        AdView mAdView = (AdView) rootView.findViewById(R.id.adView);
        AdRequest adRequest = new AdRequest.Builder().build();
        mAdView.loadAd(adRequest);
        Context c=this.getContext();
        ArrayList<String> mvalue=LoginActivity.profiledetails;
        String z="Name Register No. Office Name Course Name Father Name" +
                " Date of Birth Sex Blood Group Address Student Contact Mobile No. (10 digit)" +
                " Pincode Place of Birth Validity ";
        ArrayList<String> value=new ArrayList<>();
        for (int i=2;i<mvalue.size()-1;i++)
        {
            String x=mvalue.get(i+1);
            if(z.contains(mvalue.get(i)))
                value.add(x);
        }
        TextView name=(TextView)rootView.findViewById(R.id.name);
        name.setText(value.get(0));
        TextView regnum=(TextView)rootView.findViewById(R.id.regnum);
        regnum.setText("Register Number : "+value.get(1));
        TextView section=(TextView)rootView.findViewById(R.id.section);
        section.setText("Section : "+mvalue.get(1));
        TextView semester=(TextView)rootView.findViewById(R.id.semester);
        semester.setText("Semester : "+mvalue.get(0));
        TextView officename=(TextView)rootView.findViewById(R.id.officename);
        officename.setText("Office Name : "+value.get(2));
        TextView coursename=(TextView)rootView.findViewById(R.id.coursename);
        coursename.setText("Course Name : "+value.get(3));
        TextView fathersname=(TextView)rootView.findViewById(R.id.fathersname);
        fathersname.setText("Father's Name : "+value.get(4));
        TextView dob=(TextView)rootView.findViewById(R.id.dob);
        dob.setText("Date of Birth : "+value.get(5));
        TextView sex=(TextView)rootView.findViewById(R.id.sex);
        sex.setText("Sex : "+value.get(6));
        TextView bloodgroup=(TextView)rootView.findViewById(R.id.bloodgroup);
        bloodgroup.setText("Blood Group : "+value.get(7));
        TextView address=(TextView)rootView.findViewById(R.id.address);
        address.setText("Address : "+value.get(8));
        TextView mobilenumber=(TextView)rootView.findViewById(R.id.mobilenumbner);
        mobilenumber.setText("Mobile Number : "+value.get(9));
        TextView pincode=(TextView)rootView.findViewById(R.id.pincode);
        pincode.setText("Pincode : "+value.get(10));
        TextView validity=(TextView)rootView.findViewById(R.id.validity);
        validity.setText("Validity : "+value.get(11));
        return rootView;
    }
}