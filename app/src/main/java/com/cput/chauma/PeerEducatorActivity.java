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
import android.widget.Toast;

import com.example.shaun.chauma.R;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QuerySnapshot;
import com.google.firebase.firestore.SetOptions;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Arrays;

/**
 * Accept or decline a request to join an event set by the co-ordinator.
 * If a decline, the peer educator must send a message as to why they are declining.
 *
 * @author  Jacob de Beer
 * @version 1.0
 * @since   2018-01-31
 */
public class PeerEducatorActivity extends AppCompatActivity {

    private DrawerLayout drawerLayout;  //This is the  layout for the navigation bar
    private ActionBarDrawerToggle actionBarDrawerToggle; //This is the button that will be used to show and hide Navigation bar
    private Toolbar toolbar;    //This instance is for the navigation toolbar
    private PeerEducator peerEducator;
    FirebaseFirestore db = FirebaseFirestore.getInstance();
    //ArrayList<EventInvitation> invitations;
    EventInvitation invitation;
    EditText invitationDeets;
    EditText invitationResponse;
    Boolean hasMessages;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.peer_educator_activity);
        peerEducator = (PeerEducator) getIntent().getSerializableExtra("peerEducator");
        toolbar = findViewById(R.id.navigation_action_bar);
        setSupportActionBar(toolbar);

        drawerLayout = findViewById(R.id.peerEducatorActivityDrawerLayout);
        actionBarDrawerToggle = new ActionBarDrawerToggle(this, drawerLayout, R.string.open, R.string.close);
        actionBarDrawerToggle.setDrawerIndicatorEnabled(true);

        drawerLayout.addDrawerListener(actionBarDrawerToggle);
        actionBarDrawerToggle.syncState();

        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
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
                        Toast.makeText(getApplicationContext(), "Clinics", Toast.LENGTH_SHORT).show();
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

        invitationDeets = (EditText) findViewById(R.id.txtEventInvitationDetails);
        invitationResponse = findViewById(R.id.txtInvitationResponse);
        //invitations = new ArrayList<EventInvitation>();
        CheckInvitations();

        Button accept = findViewById(R.id.btnAccept); //to home page
        Button decline = findViewById(R.id.btnDecline);
        accept.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                try {


                    invitation.InvitationStatus = "Accepted";
                    invitation.ResponseMessage = invitationResponse.getText().toString();

                    db
                            .collection("EventInvitation")
                            .document(invitation.InvitationId)
                            .set(invitation, SetOptions.merge());

                    SendEmail(invitation, true);
                    Toast.makeText(getApplicationContext(), "Thank you for accepting!", Toast.LENGTH_SHORT).show();
                    CheckInvitations();
                } catch (Exception e) {
                    Log.w("Failed Peer Counselor", "Error adding document", e);
                }
            }
        });

        decline.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                try {


                    invitation.InvitationStatus = "Declined";
                    invitation.ResponseMessage = invitationResponse.getText().toString();

                    db
                            .collection("EventInvitation")
                            .document(invitation.InvitationId)
                            .set(invitation, SetOptions.merge());

                    SendEmail(invitation, true);
                    Toast.makeText(getApplicationContext(), "No worries, you can join us next time", Toast.LENGTH_SHORT).show();
                    CheckInvitations();
                } catch (Exception e) {
                    Log.w("Failed Peer Counselor", "Error adding document", e);
                }
            }
        });

    }

    private void CheckInvitations() {
        invitationDeets.setText("");
        invitationResponse.setText("");
        hasMessages = false;
        db.collection("EventInvitation")
                .whereEqualTo("InviteeEmail", peerEducator.EmailAddress)
                .whereEqualTo("InvitationStatus", "Pending")
                .get()
                .addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
                    @Override
                    public void onComplete(@NonNull Task<QuerySnapshot> task) {
                        if (task.isSuccessful()) {
                            if (task.getResult() == null)
                                Toast.makeText(getApplicationContext(), "Unable to retrieve messages.", Toast.LENGTH_SHORT).show();

                            if (task.getResult() != null) {
                                for (DocumentSnapshot document : task.getResult()) {
                                    ObjectMapper mapper = new ObjectMapper();
                                    invitation = mapper.convertValue(document.getData(), EventInvitation.class);
                                    invitation.InvitationId = document.getId().toString();
                                    invitationDeets.setText("Name: " + invitation.EventDescription + ".  Date: " + invitation.EventDate);
                                    hasMessages = true;
                                    break;
                                }


                            }
                            if(!hasMessages) {
                                Toast.makeText(getApplicationContext(), "You currently have no invitations.", Toast.LENGTH_LONG).show();
                                //openActivity("HomeActivity");
                            }
                        }

                        else {
                            Log.w("Search", "Error getting documents.", task.getException());
                            if(!hasMessages) {
                                Toast.makeText(getApplicationContext(), "You currently have no invitations.", Toast.LENGTH_LONG).show();
                                //openActivity("HomeActivity");
                            }
                        }
                    }
                });
    }

    private void SendEmail(EventInvitation event, Boolean attending) {
        Intent emailIntent = new Intent(Intent.ACTION_SEND);
        Log.i("Send email", "");

        String[] TO = {event.EventCoordinatorEmail};
        emailIntent.setData(Uri.parse("mailto:"));
        emailIntent.setType("text/html");
        emailIntent.putExtra(Intent.EXTRA_EMAIL, TO);
        emailIntent.putExtra(Intent.EXTRA_SUBJECT, "RE: Upcoming Event: " + event.EventName);
        String attendance = attending? "I WILL be attending:" : "I am unfortunately unable to attend:";
        emailIntent.putExtra(Intent.EXTRA_TEXT, Html.fromHtml(
                "<h1>Hi " + event.EventCoordinator + "</h1>" +
                        "<br/>"+attendance +
                        "<br/>"+event.EventName+
                        "<br/><br/>At: "+event.EventDate+
                        "<br/><br/>Message: "+event.ResponseMessage+
                        "<br/><br/><br/>Kind regards,<br/>" + peerEducator.Name

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
            //finish();
        }else if(item.getItemId() == R.id.loginButton){
            Toast.makeText(getApplicationContext(), "Admin Login", Toast.LENGTH_SHORT).show();
            openActivity("LoginActivity");
            //finish();
        }
        return actionBarDrawerToggle.onOptionsItemSelected(item) || super.onOptionsItemSelected(item);
    }

    public void openActivity(String value){
        switch (value){
            case "HomeActivity":
                Intent homeActivity = new Intent(this, HomeActivity.class);
                startActivity(homeActivity);
                //finish();
                break;
            case "ClinicActivity":
                Intent clinicsActivity = new Intent(this, ClinicActivity.class);
                startActivity(clinicsActivity);
                //finish();
                break;
            case "BrochureActivity":
                Intent brochureActivity = new Intent(this, BrochureActivity.class);
                startActivity(brochureActivity);
                //finish();
                break;
            case "EventActivity":
                Intent eventActivity = new Intent(this, EventActivity.class);
                startActivity(eventActivity);
                //finish();
                break;
            case "FrequentlyAskedQuestionActivity":
                Intent faqActivity = new Intent(this, FrequentlyAskedQuestionActivity.class);
                startActivity(faqActivity);
                //finish();
                break;
            case "GetInvolveActivity":
                Intent getInvolveActivity = new Intent(this, GetInvolveActivity.class);
                startActivity(getInvolveActivity);
                //finish();
                break;
            case "ContactActivity":
                Intent contactActivity = new Intent(this, ContactActivity.class);
                startActivity(contactActivity);
                //finish();
                break;
            case "LoginActivity":
                Intent loginActivity = new Intent(this, LoginActivity.class);
                startActivity(loginActivity);
                //finish();
                break;
        }
    }
}