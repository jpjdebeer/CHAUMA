package com.cput.chauma;

import android.content.Intent;
import android.net.Uri;
import android.support.annotation.NonNull;
import android.support.design.widget.NavigationView;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.text.Html;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.CalendarView;
import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.Toast;

import com.example.shaun.chauma.R;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QuerySnapshot;
import com.google.firebase.firestore.SetOptions;
import com.fasterxml.jackson.databind.ObjectMapper;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Date;
import java.util.List;

/**
 * Create all the events for the CPUT HIV/AIDS Unit.
 *
 * @author Banele Mlamleli
 * @version 1.0
 * @since 2018-01-31
 */
public class SetEventActivity extends AppCompatActivity {

    private DrawerLayout drawerLayout;  //This is the  layout for the navigation bar
    private ActionBarDrawerToggle actionBarDrawerToggle; //This is the button that will be used to show and hide Navigation bar
    private Toolbar toolbar;    //This instance is for the navigation toolbar
    private Coordinator coordinator;
    private ArrayList<PeerEducator> peerEducators;
    FirebaseFirestore db = FirebaseFirestore.getInstance();
    private ArrayList<PeerEducator> peerEducators1;
    CalendarView simpleCalendarView;
    int dayOfMonth, monthOfYear, yearOfLife;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.set_event_activity);
        coordinator = (Coordinator) getIntent().getSerializableExtra("coordinator");

        toolbar = findViewById(R.id.navigation_action_bar);
        setSupportActionBar(toolbar);

        drawerLayout = findViewById(R.id.setEventActivityDrawerLayout);
        actionBarDrawerToggle = new ActionBarDrawerToggle(this, drawerLayout, R.string.open, R.string.close);
        actionBarDrawerToggle.setDrawerIndicatorEnabled(true);

        drawerLayout.addDrawerListener(actionBarDrawerToggle);
        actionBarDrawerToggle.syncState();

        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setDisplayShowTitleEnabled(false);    //removing the title name(in this case is was the app name)


        peerEducators = new ArrayList<PeerEducator>();
        db.collection("PeerEducator")
                .whereEqualTo("IsAuthorised", true)
                .get()
                .addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
                    @Override
                    public void onComplete(@NonNull Task<QuerySnapshot> task) {
                        if (task.isSuccessful()) {
                            if (task.getResult() == null)
                                Toast.makeText(getApplicationContext(), "Unable to retrieve Coordinator Email.", Toast.LENGTH_SHORT).show();

                            if (task.getResult() != null) {
                                for (DocumentSnapshot document : task.getResult()) {
                                    ObjectMapper mapper = new ObjectMapper();
                                    PeerEducator peerEducator = mapper.convertValue(document.getData(), PeerEducator.class);
                                    peerEducators.add(peerEducator);
                                }
                            }
                        }

                        else {
                            Log.w("Search", "Error getting documents.", task.getException());
                        }
                    }
                });
        simpleCalendarView = findViewById(R.id.simpleCalendarView); // get the reference of CalendarView
        simpleCalendarView.setOnDateChangeListener(new CalendarView.OnDateChangeListener() {
            @Override
            public void onSelectedDayChange(CalendarView view, int year, int month, int day) {
                dayOfMonth = day;
                yearOfLife = year;
                monthOfYear = month + 1;
                // display the selected date by using a toast
                Toast.makeText(getApplicationContext(), dayOfMonth + "/" + month + "/" + year, Toast.LENGTH_LONG).show();
            }
        });

        Button apply = findViewById(R.id.btnSetEvent); //to home page
        apply.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                try {
                    long selectedDate = simpleCalendarView.getDate(); // get selected date in milliseconds
                    Event event = new Event();
                    event.EventCoordinator = coordinator.Name;
                    event.EventDescription = "The description you provided";
                    event.EventName = "The name of the event";
                    SimpleDateFormat outputFormatter = new SimpleDateFormat("yyyy-mm-dd");
                    event.EventDate = yearOfLife + "-" + monthOfYear + "-" + dayOfMonth;

                    db
                            .collection("Events")
                            .document(event.EventDate)
                            .set(event, SetOptions.merge());

                    SendEmail(event);
                    Toast.makeText(getApplicationContext(), "Event has been created!", Toast.LENGTH_SHORT).show();
                } catch (Exception e) {
                    Log.w("Failed Peer Counselor", "Error adding document", e);
                }
            }
        });
    }

    private void SendEmail(Event event) {
        Intent emailIntent = new Intent(Intent.ACTION_SEND);
        Log.i("Send email", "");
        ArrayList<String> emails = new ArrayList<String>();
        for (PeerEducator peerEducator: peerEducators) {
                emails.add(peerEducator.EmailAddress);
        }
        Object[] objEmails = emails.toArray();
        String[] TO = Arrays.copyOf(objEmails, objEmails.length, String[].class);
        emailIntent.setData(Uri.parse("mailto:"));
        emailIntent.setType("text/html");
        emailIntent.putExtra(Intent.EXTRA_EMAIL, TO);
        emailIntent.putExtra(Intent.EXTRA_SUBJECT, "Upcoming Event: " + event.EventName);
        emailIntent.putExtra(Intent.EXTRA_TEXT, Html.fromHtml(
                "<h1>I've setup a new event and would like you to join</h1>" +
                        "<br/>"+event.EventName+
                        "<br/><br/>"+event.EventDescription+
                        "<br/><br/>When: "+event.EventDate+
                        "<br/><br/><br/>Please log into the app and accept my invitation if you would like to join :)"+
                        "<br/><br/><br/>Kind regards,<br/>" + coordinator.Name

        ));

        try {
            startActivity(Intent.createChooser(emailIntent, "Send mail..."));
            finish();
            Log.i("Finished with email...", "");
        } catch (android.content.ActivityNotFoundException ex) {
            //Toast.makeText(MainActivity.this, "There is no email client installed.", Toast.LENGTH_SHORT).show();
        }
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
            case "ClinicsActivity":
                Intent clinicsActivity = new Intent(this, ClinicsActivity.class);
                startActivity(clinicsActivity);
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