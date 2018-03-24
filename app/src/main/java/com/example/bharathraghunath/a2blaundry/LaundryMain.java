package com.example.bharathraghunath.a2blaundry;

import android.content.Intent;
import android.support.design.widget.TabLayout;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.view.ViewPager;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;

import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;


public class LaundryMain extends AppCompatActivity {


    private SectionsPagerAdapter mSectionsPagerAdapter;
    private ViewPager mViewPager;

    static EditText e1,e2,e3,e12;
    static TextView t1,t2,t3,t4,t5,t20;
    static private DatabaseReference dref;
    static private FirebaseAuth mAuth;
    static private FirebaseDatabase Fbase;
    static private String userId;

   //get the data by storing it in SharedPreferences.


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_laundry_main);

        Toolbar tbar = (Toolbar)findViewById(R.id.toolbar);
        setSupportActionBar(tbar);

        // Create the adapter that will return a fragment for each of the three
        // primary sections of the activity.
        mSectionsPagerAdapter = new SectionsPagerAdapter(getSupportFragmentManager());

        // Set up the ViewPager with the sections adapter.
        mViewPager = (ViewPager) findViewById(R.id.container);
        mViewPager.setAdapter(mSectionsPagerAdapter);

        TabLayout tabLayout = (TabLayout) findViewById(R.id.tabs);
        tabLayout.setupWithViewPager(mViewPager);




    }



    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.menu,menu);

        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {

        switch (item.getItemId()){
            case R.id.menulogout:
                FirebaseAuth.getInstance().signOut();
                finish();
                startActivity(new Intent(this,MainActivity.class));

        }
        return true;
    }
    /**
     * A placeholder fragment containing a simple view.
     */
    public static class PlaceholderFragment extends Fragment {
        /**
         * The fragment argument representing the section number for this
         * fragment.
         */
        private static final String ARG_SECTION_NUMBER = "section_number";

        public PlaceholderFragment() {
        }

        /**
         * Returns a new instance of this fragment for the given section
         * number.
         */
        public static PlaceholderFragment newInstance(int sectionNumber) {
            PlaceholderFragment fragment = new PlaceholderFragment();
            Bundle args = new Bundle();
            args.putInt(ARG_SECTION_NUMBER, sectionNumber);
            fragment.setArguments(args);
            return fragment;
        }

        @Override
        public View onCreateView(LayoutInflater inflater, ViewGroup container,
                                 Bundle savedInstanceState) {
            if(getArguments().getInt(ARG_SECTION_NUMBER)==1){
                View rootView = inflater.inflate(R.layout.fragment_section_1, container, false);
                return rootView;
            }
            else if(getArguments().getInt(ARG_SECTION_NUMBER)==2){
                View rootView = inflater.inflate(R.layout.fragment_section_2, container, false);

                e1 = (EditText)rootView.findViewById(R.id.editText11);
                e12 = (EditText)rootView.findViewById(R.id.editText12);
                e2 = (EditText)rootView.findViewById(R.id.editText9);
                e3 = (EditText)rootView.findViewById(R.id.editText10);
                dref = FirebaseDatabase.getInstance().getReference().child("customers");
                FirebaseUser user = FirebaseAuth.getInstance().getCurrentUser();
                userId = user.getUid().toString();
                DatabaseReference dref_c = dref.child(userId);
                t1=(TextView)rootView.findViewById(R.id.textView12);
                t2=(TextView)rootView.findViewById(R.id.textView14);
                t3=(TextView)rootView.findViewById(R.id.textView18);
                t20=(TextView)rootView.findViewById(R.id.textView20);
                t4=(TextView)rootView.findViewById(R.id.textView21);
                t5=(TextView)rootView.findViewById(R.id.textView22);
                t1.setText("No Order placed");
                String s = getActivity().getIntent().getStringExtra("key");
                if((s!=null && s.equals("Order") || userId !=null)){
                    dref_c.addValueEventListener(new ValueEventListener() {
                        @Override
                        public void onDataChange(DataSnapshot dataSnapshot) {
                            t1.setText("Your Order accepted");
                            t2.setVisibility(View.VISIBLE);
                            t2.setText("Order summary :");
                            t20.setVisibility(View.VISIBLE);
                            t20.setText("Estimated Price :");
                            t3.setVisibility(View.VISIBLE);
                            t3.setText("Address :");
                            t4.setVisibility(View.VISIBLE);
                            t4.setText("Date :");
                            t5.setVisibility(View.VISIBLE);
                            t5.setText("Time :");
                            e1.setVisibility(View.VISIBLE);
                            e2.setVisibility(View.VISIBLE);
                            e3.setVisibility(View.VISIBLE);
                            e12.setVisibility(View.VISIBLE);
                            showdata(dataSnapshot);
                            e1.setEnabled(false);
                            e2.setEnabled(false);
                            e3.setEnabled(false);
                            e12.setEnabled(false);

                        }

                        @Override
                        public void onCancelled(DatabaseError databaseError) {

                        }
                    });
                }
                return rootView;
            }
            else {
                View rootView = inflater.inflate(R.layout.fragment_laundry_main, container, false);
                return rootView;
            }



        }
        private void showdata(DataSnapshot dataSnapshot) {

            e1.setText(dataSnapshot.child("Address").getValue(String.class));
            e2.setText(dataSnapshot.child("Delivery_Date").getValue(String.class));
            e3.setText(dataSnapshot.child("Time").getValue(String.class));
            e12.setText(dataSnapshot.child("Total Price").getValue(String.class));



        }


    }

    /**
     * A {@link FragmentPagerAdapter} that returns a fragment corresponding to
     * one of the sections/tabs/pages.
     */
    public class SectionsPagerAdapter extends FragmentPagerAdapter {

        public SectionsPagerAdapter(FragmentManager fm) {
            super(fm);
        }

        @Override
        public Fragment getItem(int position) {
            // getItem is called to instantiate the fragment for the given page.
            // Return a PlaceholderFragment (defined as a static inner class below).
            return PlaceholderFragment.newInstance(position + 1);
        }

        @Override
        public int getCount() {
            // Show 3 total pages.
            return 2;
        }

        @Override
        public CharSequence getPageTitle(int position) {
            switch (position) {
                case 0:
                    return "Place Order";
                case 1:
                    return "View Orders";

            }
            return null;
        }
    }
    public void buttonClick(View v) {
        switch(v.getId()) {
            case R.id.button5:
                finish();
                Intent myIntent = new Intent(LaundryMain.this, Order_1.class);
                myIntent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                startActivity(myIntent);
                break;
            case R.id.button6:
                //
        }
    }


}
