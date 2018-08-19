package com.cput.chauma;

import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.design.widget.NavigationView;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.Toast;

import com.example.shaun.chauma.R;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.SetOptions;

import java.util.Calendar;
import java.util.HashMap;
import java.util.Map;
import java.util.Objects;

/**
 * Application form for individuals to apply in order to become
 * volunteer peer educators.
 *
 * @author  Kelvin Van Sittert
 * @version 1.0
 * @since   2018-01-31
 */
public class GetInvolveActivity extends AppCompatActivity {

    private DrawerLayout drawerLayout;  //This is the  layout for the navigation bar
    private ActionBarDrawerToggle actionBarDrawerToggle; //This is the button that will be used to show and hide Navigation bar
    private Toolbar toolbar;    //This instance is for the navigation toolbar
    FirebaseFirestore db = FirebaseFirestore.getInstance();


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.get_involve_activity);

        toolbar = findViewById(R.id.navigation_action_bar);
        setSupportActionBar(toolbar);

        drawerLayout = findViewById(R.id.getInvolveActivityDrawerLayout);
        actionBarDrawerToggle = new ActionBarDrawerToggle(this, drawerLayout, R.string.open, R.string.close);
        actionBarDrawerToggle.setDrawerIndicatorEnabled(true);

        drawerLayout.addDrawerListener(actionBarDrawerToggle);
        actionBarDrawerToggle.syncState();

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.KITKAT) {
            Objects.requireNonNull(getSupportActionBar()).setDisplayHomeAsUpEnabled(true);
        }
        getSupportActionBar().setDisplayShowTitleEnabled(false);    //removing the title name(in this case is was the app name)

        final NavigationView navigationView = findViewById(R.id.navigationViewLayout);
        navigationView.setNavigationItemSelectedListener(new NavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem item) {
                switch (item.getItemId()) {
                    case R.id.home:
                        Toast.makeText(getApplicationContext(), "Home", Toast.LENGTH_SHORT).show();
                        openActivity("HomeActivity");break;
                    case R.id.clinic:
                        Toast.makeText(getApplicationContext(), "Clinic", Toast.LENGTH_SHORT).show();
                        openActivity("ClinicActivity");break;
                    case R.id.brochure:
                        Toast.makeText(getApplicationContext(), "Brochure", Toast.LENGTH_SHORT).show();
                        openActivity("BrochureActivity");break;
                    case R.id.events:
                        Toast.makeText(getApplicationContext(), "Events", Toast.LENGTH_SHORT).show();
                        openActivity("EventActivity");break;
                    case R.id.faq:
                        Toast.makeText(getApplicationContext(), "FAQ", Toast.LENGTH_SHORT).show();
                        openActivity("FrequentlyAskedQuestionActivity");break;
                    case R.id.getInvolve:
                        Toast.makeText(getApplicationContext(), "GetInvolveActivity", Toast.LENGTH_SHORT).show();
                        openActivity("GetInvolveActivity");break;
                    case R.id.contacts:
                        Toast.makeText(getApplicationContext(), "Contact", Toast.LENGTH_SHORT).show();
                        openActivity("ContactActivity");break;
                }
                return true;
            }
        });

        Button apply = findViewById(R.id.btnPeerApply); //to home page
        apply.setOnClickListener(new View.OnClickListener() {
           @Override
           public void onClick(View v) {
               try {
                   EditText txtPeerEmailAddress = findViewById(R.id.txtPeerEmailAddress);
                   EditText txtPeerName =  findViewById(R.id.txtPeerName);
                   EditText txtPeerSurname =  findViewById(R.id.txtPeerSurname);
                   EditText txtPeerIdNumber =  findViewById(R.id.txtPeerIdNumber);
                   EditText txtPeerContactNumber =  findViewById(R.id.txtPeerContactNumber);
                   EditText txtPeerCourse =  findViewById(R.id.txtPeerCourse);
                   EditText txtPeerYearOfStudy =  findViewById(R.id.txtPeerYearOfStudy);
                   EditText txtPeerStudentNumber =  findViewById(R.id.txtPeerStudentNumber);
                   RadioButton rdbMale =  findViewById(R.id.rdbMale);
                   RadioButton rdbFemale =  findViewById(R.id.rdbFemale);

                   PeerCounselor peerCounselor = new PeerCounselor();
                   peerCounselor.ContactNumber = txtPeerContactNumber.getText().toString();
                   peerCounselor.Course = txtPeerCourse.getText().toString();
                   peerCounselor.EmailAddress = txtPeerEmailAddress.getText().toString();
                   peerCounselor.Gender = rdbFemale.isChecked() ? rdbFemale.getText().toString() : rdbMale.isChecked() ? rdbMale.getText().toString() : "Private";
                   peerCounselor.IdNumber = txtPeerIdNumber.getText().toString();
                   peerCounselor.Name = txtPeerName.getText().toString();
                   peerCounselor.Surname = txtPeerSurname.getText().toString();
                   peerCounselor.YearOfStudy = txtPeerYearOfStudy.getText().toString();
                   peerCounselor.StudentNumber = txtPeerStudentNumber.getText().toString();

                   db
                           .collection("PeerCounselor")
                           .document(peerCounselor.EmailAddress)
                           .set(peerCounselor, SetOptions.merge());
                   openActivity("HomeActivity");
                   Toast.makeText(getApplicationContext(), "Thank you!  Our Coordinator will contact you.", Toast.LENGTH_SHORT).show();

               }
               catch (Exception e){
                   Log.w("Failed Peer Counselor", "Error adding document", e);
               }
           }
       });



    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.toolbar_options, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        if(item.getItemId() == R.id.homeButton){
            Toast.makeText(getApplicationContext(), "Go back Home", Toast.LENGTH_SHORT).show();
            openActivity("HomeActivity");
            finish();
        }else if(item.getItemId() == R.id.loginButton){
            Toast.makeText(getApplicationContext(), "Admin Login", Toast.LENGTH_SHORT).show();
            openActivity("LoginActivity");
            finish();
        }
        return actionBarDrawerToggle.onOptionsItemSelected(item) || super.onOptionsItemSelected(item);
    }

    public void openActivity(String value){
        switch (value){
            case "HomeActivity":
                Intent homeActivity = new Intent(this, HomeActivity.class);
                startActivity(homeActivity);
                finish();break;
            case "ClinicActivity":
                Intent clinicActivity = new Intent(this, ClinicActivity.class);
                startActivity(clinicActivity);
                finish();break;
            case "BrochureActivity":
                Intent brochureActivity = new Intent(this, BrochureActivity.class);
                startActivity(brochureActivity);
                finish();break;
            case "EventActivity":
                Intent eventActivity = new Intent(this, EventActivity.class);
                startActivity(eventActivity);
                finish();break;
            case "FrequentlyAskedQuestionActivity":
                Intent faqActivity = new Intent(this, FrequentlyAskedQuestionActivity.class);
                startActivity(faqActivity);
                finish();break;
            case "GetInvolveActivity":
                Intent getInvolveActivity = new Intent(this, GetInvolveActivity.class);
                startActivity(getInvolveActivity);
                finish();break;
            case "ContactActivity":
                Intent contactActivity = new Intent(this, ContactActivity.class);
                startActivity(contactActivity);
                finish();break;
            case "LoginActivity":
                Intent loginActivity = new Intent(this, LoginActivity.class);
                startActivity(loginActivity);
                finish();break;
        }
    }
}