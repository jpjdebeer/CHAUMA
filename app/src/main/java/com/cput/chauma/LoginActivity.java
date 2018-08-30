package com.cput.chauma;

import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.design.widget.NavigationView;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.example.shaun.chauma.R;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QuerySnapshot;
import com.fasterxml.jackson.databind.ObjectMapper;

import static com.cput.chauma.AESCrypt.encrypt;

/**
 * Login screen
 *
 * @author  Jacob de Beer
 * @version 1.0
 * @since   2018-01-31
 */
public class LoginActivity extends AppCompatActivity {

    private DrawerLayout drawerLayout;  //This is the  layout for the navigation bar
    private ActionBarDrawerToggle actionBarDrawerToggle; //This is the button that will be used to show and hide Navigation bar
    private Toolbar toolbar;    //This instance is for the navigation toolbar
    FirebaseFirestore db = FirebaseFirestore.getInstance();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.login_activity);

        toolbar = findViewById(R.id.navigation_action_bar);
        setSupportActionBar(toolbar);

        drawerLayout = findViewById(R.id.loginActivityDrawerLayout);
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

        Button apply = findViewById(R.id.btnLogin); //to home page
        apply.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                try {
                    EditText txtUsername = findViewById(R.id.txtUsername);
                    EditText txtPassword =  findViewById(R.id.txtPassword);

                    final String password = encrypt(txtPassword.getText().toString());
                    final String username = txtUsername.getText().toString();

                    db.collection("PeerEducator")
                            .whereEqualTo("EmailAddress".trim(), username)
                            .get()
                            .addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
                                @Override
                                public void onComplete(@NonNull Task<QuerySnapshot> task) {
                                    final Task<QuerySnapshot> peerEducatorTask = task;
                                    if (task.isSuccessful()) {
                                        db.collection("Coordinator")
                                                .whereEqualTo("EmailAddress".trim(), username)
                                                .get()
                                                .addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
                                                    @Override
                                                    public void onComplete(@NonNull Task<QuerySnapshot> coordinatorTask) {

                                                        if(peerEducatorTask.getResult() == null && coordinatorTask.getResult() == null)
                                                            Toast.makeText(getApplicationContext(), "Username or password was incorrect", Toast.LENGTH_SHORT).show();

                                                        if(peerEducatorTask.getResult() != null) {
                                                            for (DocumentSnapshot document : peerEducatorTask.getResult()) {
                                                                ObjectMapper mapper = new ObjectMapper();
                                                                PeerEducator peerEducator = mapper.convertValue(document.getData(), PeerEducator.class);
                                                                if (!peerEducator.Password.equals(password)) {
                                                                    Toast.makeText(getApplicationContext(), "Username or password was incorrect", Toast.LENGTH_SHORT).show();
                                                                } else {
                                                                    Toast.makeText(getApplicationContext(), "Hello " + peerEducator.Name, Toast.LENGTH_SHORT).show();
                                                                    Intent nextPage = new Intent(getApplicationContext(), PeerEducatorActivity.class);
                                                                    nextPage.putExtra("peerEducator", peerEducator);
                                                                    startActivity(nextPage);
                                                                }
                                                            }
                                                        }
                                                        if(coordinatorTask.getResult() != null) {
                                                            for (DocumentSnapshot document : coordinatorTask.getResult()) {
                                                                ObjectMapper mapper = new ObjectMapper();
                                                                Coordinator coordinator = mapper.convertValue(document.getData(), Coordinator.class);
                                                                if (!coordinator.Password.equals(password)) {
                                                                    Toast.makeText(getApplicationContext(), "Username or password was incorrect", Toast.LENGTH_SHORT).show();
                                                                } else {
                                                                    Toast.makeText(getApplicationContext(), "Hello " + coordinator.Name, Toast.LENGTH_SHORT).show();
                                                                    Intent nextPage = new Intent(getApplicationContext(), AdminActivity.class);
                                                                    nextPage.putExtra("coordinator", coordinator);
                                                                    startActivity(nextPage);
                                                                }
                                                            }
                                                        }
                                                    }
                                                });

                                    } else {
                                        Log.w("Login", "Error Logging in.", task.getException());
                                    }
                                }
                            });
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
                finish();break;
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