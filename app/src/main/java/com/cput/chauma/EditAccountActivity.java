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
import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.Toast;

import com.example.shaun.chauma.R;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.SetOptions;

import static com.cput.chauma.AESCrypt.encrypt;

/**
 * Edit accounts for the registered peer educators
 *
 * @author  Nelson Mpyana
 * @version 1.0
 * @since   2018-01-31
 */
public class EditAccountActivity extends AppCompatActivity {

    private DrawerLayout drawerLayout;  //This is the  layout for the navigation bar
    private ActionBarDrawerToggle actionBarDrawerToggle; //This is the button that will be used to show and hide Navigation bar
    private Toolbar toolbar;    //This instance is for the navigation toolbar
    private Coordinator coordinator;
    FirebaseFirestore db = FirebaseFirestore.getInstance();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.edit_account_activity);
        coordinator = (Coordinator) getIntent().getSerializableExtra("coordinator");

        toolbar = findViewById(R.id.navigation_action_bar);
        setSupportActionBar(toolbar);

        drawerLayout = findViewById(R.id.editAccountActivityDrawerLayout);
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

        Button addUser = findViewById(R.id.btnAddUser); //to home page
        addUser.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                try {
                    EditText txtNewUserName = findViewById(R.id.txtNewUserName);
                    EditText txtNewUserSurnamme =  findViewById(R.id.txtNewUserSurnamme);
                    EditText txtEmail =  findViewById(R.id.txtNewUsername);
                    EditText txtNewUserPassword =  findViewById(R.id.txtNewUserPassword);
                    EditText txtConfirmPassword =  findViewById(R.id.txtConfirmPassword);

                    if(txtNewUserPassword.getText().toString().equals(txtConfirmPassword.getText().toString())){

                        boolean validated = true;
                        PeerEducatorAdd peerEducatorAdd = new PeerEducatorAdd();
                        String password = encrypt(txtNewUserPassword.getText().toString());

                        String email = txtEmail.getText().toString().trim();
                        String emailPattern = "[a-zA-Z0-9._-]+@[a-z]+\\.+[a-z]+";

                        if(email.matches(emailPattern)){
                            peerEducatorAdd.EmailAddress = txtEmail.getText().toString().trim();
                        }else{
                            validated = false;
                            Toast.makeText(getApplicationContext(), "Your email is incorrect", Toast.LENGTH_SHORT).show();
                        }

                        peerEducatorAdd.Name = txtNewUserName.getText().toString();
                        peerEducatorAdd.Surname = txtNewUserSurnamme.getText().toString();
                        peerEducatorAdd.Password = password;
                        peerEducatorAdd.IsAuthorised = true;

                        if(validated) {
                            db
                                    .collection("PeerEducator")
                                    .document(peerEducatorAdd.EmailAddress)
                                    .set(peerEducatorAdd, SetOptions.merge());
                            Toast.makeText(getApplicationContext(), "Peer Educator successfully added", Toast.LENGTH_SHORT).show();


                            SendEmail(txtNewUserName.getText().toString(), txtNewUserSurnamme.getText().toString(), txtEmail.getText().toString(), txtNewUserPassword.getText().toString(), "NEW");
                        }
                    }
                    else
                        Toast.makeText(getApplicationContext(), "Passwords don't match", Toast.LENGTH_SHORT).show();

                }
                catch (Exception e){
                    Log.w("Failed Peer Counselor", "Error adding document", e);
                }
            }
        });

        Button saveChange = findViewById(R.id.btnSaveChange); //to home page
        saveChange.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                try {
                    boolean validated = true;

                    EditText txtChangeEmail = findViewById(R.id.txtChangeUsername);
                    EditText txtChangeUserPassword =  findViewById(R.id.txtChangeUserPassword);
                    EditText txtConfirmChangePassword =  findViewById(R.id.txtConfirmChangePassword);

                    if(txtChangeUserPassword.getText().toString().equals(txtConfirmChangePassword.getText().toString())) {
                        PeerEducatorEdit peerEducatorEdit = new PeerEducatorEdit();
                        String password = encrypt(txtChangeUserPassword.getText().toString());

                        String email = txtChangeEmail.getText().toString().trim();
                        String emailPattern = "[a-zA-Z0-9._-]+@[a-z]+\\.+[a-z]+";

                        if (email.matches(emailPattern)) {
                            peerEducatorEdit.EmailAddress = txtChangeEmail.getText().toString().trim();
                        } else {
                            validated = false;
                            Toast.makeText(getApplicationContext(), "Your email is incorrect", Toast.LENGTH_SHORT).show();
                        }


                        peerEducatorEdit.Password = password;


                        if (validated) {
                            db
                                    .collection("PeerEducator")
                                    .document(peerEducatorEdit.EmailAddress)
                                    .set(peerEducatorEdit, SetOptions.merge());
                            Toast.makeText(getApplicationContext(), "Peer Educator successfully added", Toast.LENGTH_SHORT).show();


                            SendEmail("", "", txtChangeEmail.getText().toString(), txtChangeUserPassword.getText().toString(), "UPDATE");
                        }
                    }
                    else
                        Toast.makeText(getApplicationContext(), "Passwords don't match", Toast.LENGTH_SHORT).show();

                }
                catch (Exception e){
                    Log.w("Failed Peer Counselor", "Error adding document", e);
                }
            }
        });

        Button btnDeleteUser = findViewById(R.id.btnDeleteUser); //to home page
        btnDeleteUser.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                try {

                    boolean validated = true;
                    EditText txtDeleteEmail = findViewById(R.id.txtDeleteUser);

                    PeerEducatorDelete peerEducatorDelete = new PeerEducatorDelete();


                    String email = txtDeleteEmail.getText().toString().trim();
                    String emailPattern = "[a-zA-Z0-9._-]+@[a-z]+\\.+[a-z]+";

                    if (email.matches(emailPattern)) {
                        peerEducatorDelete.EmailAddress = txtDeleteEmail.getText().toString().trim();
                    } else {
                        validated = false;
                        Toast.makeText(getApplicationContext(), "Your email is incorrect", Toast.LENGTH_SHORT).show();
                    }

                    if (validated) {
                        db
                                .collection("PeerEducator")
                                .document(peerEducatorDelete.EmailAddress)
                                .delete();
                        Toast.makeText(getApplicationContext(), "Peer Educator successfully added", Toast.LENGTH_SHORT).show();


                        SendEmail("", "", txtDeleteEmail.getText().toString(), "", "DELETE");

                    }
                }
                catch (Exception e){
                    Log.w("Failed Peer Counselor", "Error deleting document", e);
                }
            }
        });

    }

    private void SendEmail(String name, String surname, String email, String password, String action) {
        Intent emailIntent = new Intent(Intent.ACTION_SEND);
        Log.i("Send email", "");
        String[] TO = {email};
        emailIntent.setData(Uri.parse("mailto:"));
        emailIntent.setType("text/html");
        String subject = "", body = "";

        switch (action) {
            case "NEW":
                subject = "Welcome Peer Educator!";
                body = "<h1>You are now a Peer Educator</h1>" +
                        "<h2>Here are your details you can use to login:</h2>" +
                        "<br/>Username: "+email+
                        "<br/><strong>Password: "+password+"</strong>"+
                        "<h2>Here are the rest of your details:</h2>" +
                        "<br/>Name: "+name+
                        "<br/>Surname: "+surname+
                        "<br/><br/>"+
                        "Kind regards,<br/>"+
                        coordinator.Name;
                break;
            case "UPDATE":
                subject = "Peer Educator your account has been updated";
                body = "<h1>Here are your new details</h1>" +
                        "<br/>Username: "+email+
                        "<br/><strong>Password: "+password+"</strong>"+
                        "<br/><br/>"+
                        "Kind regards,<br/>"+
                        coordinator.Name;
                break;
                default:
                    subject = "Peer Educator your account has been deleted";
                    body = "<h1>We are sad to see you go</h1>" +
                            "<h2>Feel free to come back whenever you want :)</h2>"+
                            "<br/><br/>"+
                            "Kind regards,<br/>"+
                            coordinator.Name;
                    break;

        }

        emailIntent.putExtra(Intent.EXTRA_EMAIL, TO);
        emailIntent.putExtra(Intent.EXTRA_SUBJECT, subject);
        emailIntent.putExtra(Intent.EXTRA_TEXT, Html.fromHtml(body));

        try {
            startActivity(Intent.createChooser(emailIntent, "Send mail..."));
            //finish();
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