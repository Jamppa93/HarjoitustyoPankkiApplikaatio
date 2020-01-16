package com.example.harjoitustyopankkiapplikaatio;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.TextView;

import java.text.ParseException;
import java.util.ArrayList;
import java.util.Arrays;

public class ContactInfoActivity extends AppCompatActivity {

    //Declarations
    //*********************************************************//
    private String featureTemp;
    private String featureTextTemp;
    private Boolean isFormatOk;
    private Spinner contactFeatures;
    private EditText alterContactFeatures;
    private TextView firstNameContact;
    private TextView SurameContact;
    private TextView birthDateContact;
    private TextView emailAddressContact;
    private TextView phoneNumberContact;
    private TextView streetNameContact;
    private TextView cityContact;
    private TextView postalCodeContact;
    private TextView userNameContact;
    private TextView passwordContact;
    //*********************************************************//

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_contact_info);

        //*********************************************************//
        //Initializing TextViews
        firstNameContact = (TextView) findViewById(R.id.textViewDataFirstName);
        firstNameContact.setText(Bank.getLoggedAccount( ).getContactInfo().getFirstName());

        SurameContact = (TextView) findViewById(R.id.textViewDatasurname);
        SurameContact.setText(Bank.getLoggedAccount( ).getContactInfo().getSurName());

        birthDateContact = (TextView) findViewById(R.id.textViewDataBirthdate);
        birthDateContact.setText(Bank.getLoggedAccount( ).getContactInfo().getBirthDate());

        phoneNumberContact = (TextView) findViewById(R.id.textViewDataPhoneNumber);
        phoneNumberContact.setText(Bank.getLoggedAccount( ).getContactInfo().getPhone());

        emailAddressContact = (TextView) findViewById(R.id.textViewDataEmaiLAddress);
        emailAddressContact.setText(Bank.getLoggedAccount( ).getContactInfo().getEmail());

        streetNameContact = (TextView) findViewById(R.id.textViewDataStreetName);
        streetNameContact.setText(Bank.getLoggedAccount( ).getContactInfo().getStreetName());

        cityContact = (TextView) findViewById(R.id.textViewDataCity);
        cityContact.setText(Bank.getLoggedAccount( ).getContactInfo().getCity());

        postalCodeContact = (TextView) findViewById(R.id.textViewDataPostalCode);
        postalCodeContact.setText(Bank.getLoggedAccount( ).getContactInfo().getPostalCode());

        userNameContact = (TextView) findViewById(R.id.textViewDataUserName);
        userNameContact.setText(Bank.getLoggedAccount( ).getUserInfo().getUserName( ));

        passwordContact = (TextView) findViewById(R.id.textViewDataPassword);
        passwordContact.setText(Bank.getLoggedAccount( ).getUserInfo().getPassWord( ));

        //*********************************************************//

        //Initializing buttons
        Button changeContactInfo = (Button) findViewById(R.id.buttonChangeInfo);
        changeContactInfo.setOnClickListener(new View.OnClickListener( ) {
            @Override
            public void onClick(View v) {
                changeInfo( );
            }
        });
        //ContactInfoActivity: Return to LoginActivity
        Button buttonContactInfo = (Button) findViewById(R.id.buttonReturnFromContact);
        buttonContactInfo.setOnClickListener(new View.OnClickListener( ) {
            @Override
            public void onClick(View v) {
                openLoginActivity( );
            }

            public void openLoginActivity() {
                Intent intent = new Intent(ContactInfoActivity.this, LoginActivity.class);
                startActivity(intent);
            }
        });
        //*********************************************************//
        //Initializing EditText
        alterContactFeatures = findViewById(R.id.editTextContactFeatures);


        //*********************************************************//
        //Initializing Spinner
        ArrayList<String> featureList = new ArrayList<>(Arrays.asList("SELECT", "First Name", "Surname", "Date of Birth", "Email", "Phone", "Street name", "City", "Postal Code", "Username", "Password"));
        contactFeatures = findViewById(R.id.spinnerContactFeatures);
        ArrayAdapter<String> adapter = new ArrayAdapter<String>(this, android.R.layout.simple_spinner_item, featureList);
        adapter.setDropDownViewResource(android.R.layout.simple_dropdown_item_1line);
        contactFeatures.setAdapter(adapter);
        contactFeatures.setOnItemSelectedListener(new ContactInfoSpinnerClass( ));
    }

    class ContactInfoSpinnerClass implements AdapterView.OnItemSelectedListener {
            public void onItemSelected(AdapterView<?> parent, View v, int position, long id) {
                featureTemp = parent.getItemAtPosition(position).toString();
            }
            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {
            }
    }

    protected void changeInfo() {
        featureTextTemp = alterContactFeatures.getText().toString();
        alterContactFeatures.getText().clear();

        if (featureTemp == "First Name"){
            // Use SupporMethod class to check if the variable is Alphabetical.
            isFormatOk = SupportMethods.isStringOnlyAlphabet(featureTextTemp);
            if(isFormatOk == true){

                Bank.getLoggedAccount().getContactInfo().setFirstName(featureTextTemp);
                firstNameContact.setText(Bank.getLoggedAccount().getContactInfo().getFirstName());
                alterContactFeatures.setHintTextColor(Color.GREEN);
                alterContactFeatures.setHint("First name changed successfully!");

            }else{
                alterContactFeatures.setHintTextColor(Color.RED);
                alterContactFeatures.setHint("Wrong format!");
            }
        }
        else if (featureTemp == "Surname") {
            // Use SupporMethod class to check if the variable is Alphabetical.
            isFormatOk = SupportMethods.isStringOnlyAlphabet(featureTextTemp);
            if(isFormatOk == true){

                Bank.getLoggedAccount().getContactInfo().setStreetName(featureTextTemp);
                SurameContact.setText(Bank.getLoggedAccount().getContactInfo().getSurName());
                alterContactFeatures.setHintTextColor(Color.GREEN);
                alterContactFeatures.setHint("Surname changed successfully!");

            }else{
                alterContactFeatures.setHintTextColor(Color.RED);
                alterContactFeatures.setHint("Wrong format!");
            }
        }
        else if  (featureTemp =="City") {
            // Use SupporMethod class to check if the variable is Alphabetical.
            isFormatOk = SupportMethods.isStringOnlyAlphabet(featureTextTemp);
            if(isFormatOk == true){

                Bank.getLoggedAccount().getContactInfo().setStreetName( featureTextTemp);
                cityContact.setText(Bank.getLoggedAccount().getContactInfo().getStreetName());
                alterContactFeatures.setHintTextColor(Color.GREEN);
                alterContactFeatures.setHint("First name changed successfully!");

            }else{
                alterContactFeatures.setHintTextColor(Color.RED);
                alterContactFeatures.setHint("Wrong format!");
            }
        }
        else if (featureTemp == "Date of Birth"){
            try {
                isFormatOk = SupportMethods.isStringDateFormat(featureTextTemp);
                if(isFormatOk == true){

                    Bank.getLoggedAccount().getContactInfo().setBirthDate( featureTextTemp);
                    birthDateContact.setText(Bank.getLoggedAccount().getContactInfo().getBirthDate());
                    alterContactFeatures.setHintTextColor(Color.GREEN);
                    alterContactFeatures.setHint("First name changed successfully!");

                }else{
                    alterContactFeatures.setHintTextColor(Color.RED);
                    alterContactFeatures.setHint("Wrong format!");
                }
            }catch (ParseException e){
                alterContactFeatures.setHintTextColor(Color.RED);
                alterContactFeatures.setHint("Wrong format!");
            }
        }
        else if (featureTemp == "Phone"){
            // Use SupporMethod class to check if the variable is numerical.
            isFormatOk = SupportMethods.isStringOnlyNumeric(featureTextTemp);
            if(isFormatOk == true){

                Bank.getLoggedAccount().getContactInfo().setPhone(featureTextTemp);
                phoneNumberContact.setText(Bank.getLoggedAccount().getContactInfo().getPhone());
                alterContactFeatures.setHintTextColor(Color.GREEN);
                alterContactFeatures.setHint("First name changed successfully!");

            }else{
                alterContactFeatures.setHintTextColor(Color.RED);
                alterContactFeatures.setHint("Wrong format!");
            }
        }
        else if(featureTemp == "Postal Code") {
            // Use SupporMethod class to check if the variable is numerical.
            isFormatOk = SupportMethods.isStringOnlyNumeric(featureTextTemp);
            if(isFormatOk == true){

                Bank.getLoggedAccount().getContactInfo().setPostalCode(featureTextTemp);
                postalCodeContact.setText(Bank.getLoggedAccount().getContactInfo().getPostalCode());
                alterContactFeatures.setHintTextColor(Color.GREEN);
                alterContactFeatures.setHint("First name changed successfully!");

            }else{
                alterContactFeatures.setHintTextColor(Color.RED);
                alterContactFeatures.setHint("Wrong format!");
            }
        }
        else if (featureTemp == ("Email")) {
            // Use SupporMethod class to check if the variable is in Email Format.
            isFormatOk = SupportMethods.isStringEmailAddress(featureTextTemp);
            if(isFormatOk == true){

                Bank.getLoggedAccount().getContactInfo().setEmail(featureTextTemp);
                emailAddressContact.setText(Bank.getLoggedAccount().getContactInfo().getEmail());
                alterContactFeatures.setHintTextColor(Color.GREEN);
                alterContactFeatures.setHint("First name changed successfully!");

            }else{
                alterContactFeatures.setHintTextColor(Color.RED);
                alterContactFeatures.setHint("Wrong format!");
            }
        }
        else if (featureTemp == "Street name"){
            // Check that EditText is not empty.
            if ((!featureTextTemp.isEmpty( ))){

                Bank.getLoggedAccount().getContactInfo().setStreetName( featureTextTemp);
                streetNameContact.setText(Bank.getLoggedAccount().getContactInfo().getStreetName());
                alterContactFeatures.setHintTextColor(Color.GREEN);
                alterContactFeatures.setHint("First name changed successfully!");

            }else{
                alterContactFeatures.setHintTextColor(Color.RED);
                alterContactFeatures.setHint("Wrong format!");
            }
        }
        else if(featureTemp == "Username") {
            // Check that EditText is not empty.
            if ((!featureTextTemp.isEmpty( ))) {

                Bank.getLoggedAccount().getUserInfo().setUsername(featureTextTemp);
                userNameContact.setText(Bank.getLoggedAccount().getUserInfo().getUserName());
                alterContactFeatures.setHintTextColor(Color.GREEN);
                alterContactFeatures.setHint("First name changed successfully!");

            }else{
                alterContactFeatures.setHintTextColor(Color.RED);
                alterContactFeatures.setHint("Wrong format!");
            }
        }
        else if(featureTemp == "Password"){
            // Check that EditText is not empty.
            if ((!featureTextTemp.isEmpty( ))){

                Bank.getLoggedAccount().getUserInfo().setPassword(featureTextTemp);
                passwordContact.setText(Bank.getLoggedAccount().getUserInfo().getPassWord());
                alterContactFeatures.setHintTextColor(Color.GREEN);
                alterContactFeatures.setHint("First name changed successfully!");

            }else{
                alterContactFeatures.setHintTextColor(Color.RED);
                alterContactFeatures.setHint("Wrong format!");
            }
        }
    }


}
