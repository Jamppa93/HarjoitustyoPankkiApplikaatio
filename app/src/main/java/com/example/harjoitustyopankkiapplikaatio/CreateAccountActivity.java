package com.example.harjoitustyopankkiapplikaatio;

import androidx.appcompat.app.AppCompatActivity;
import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import java.text.ParseException;
import java.util.ArrayList;

public class CreateAccountActivity extends AppCompatActivity {

    private EditText firstName;
    private EditText surName;
    private EditText birthDate;
    private EditText email;
    private EditText phone;
    private EditText streetName;
    private EditText city;
    private EditText postalCode;
    private EditText username;
    private EditText password;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_create_account);

        firstName = (EditText) findViewById(R.id.editTextFirstName);
        surName = (EditText) findViewById(R.id.editTextSurname);
        birthDate = (EditText) findViewById(R.id.editTextDateOfBirth);
        email = (EditText) findViewById(R.id.editTextEmailAddress);
        phone = (EditText) findViewById(R.id.editTextPhone);
        streetName = (EditText) findViewById(R.id.editTextStreetName);
        city = (EditText) findViewById(R.id.editTextCityName);
        postalCode = (EditText) findViewById(R.id.editTextPostalCode);
        username = (EditText) findViewById(R.id.editTextUserName);
        password = (EditText) findViewById(R.id.editTextPassword);

            // CreateAccount activity: Return to main menu
        Button buttonReturnMain = (Button) findViewById(R.id.buttonReturnMain);
        buttonReturnMain.setOnClickListener(new View.OnClickListener( ) {
            @Override
            public void onClick(View v) {
                //Return to main menu
                openMainActivity( );
            }

            public void openMainActivity() {
                Intent intent = new Intent(CreateAccountActivity.this, MainActivity.class);
                startActivity(intent);
            }

        });

            // CreateAccount activity: Create account and return to main menu
        Button buttonCreateAccount = (Button) findViewById(R.id.buttonCreateAccount);
        buttonCreateAccount.setOnClickListener(new View.OnClickListener( ) {
            @Override
            public void onClick(View v) {

                //Create an account and if it was succesfull move forward
                Boolean isAccountCreated =  createAccount(v);
                if(isAccountCreated==true){
                    //Show pop up to inform the user
                    openDialog();
                    //Return to main menu
                    openMainActivity( );
                }

            }


            public void openMainActivity() {
                Intent intent = new Intent(CreateAccountActivity.this, MainActivity.class);
                startActivity(intent);


            }


        });
    }

    //**********************************************************//
    // CreateAccount activity: EditText Getters for contact information.
    public String getFirstName(View v) {
        String firstNameTemp = firstName.getText( ).toString( );

        // use SupporMethod class to check if the variable is Alphabetical.
        Boolean booleanValue = SupportMethods.isStringOnlyAlphabet(firstNameTemp);


        if ((!firstNameTemp.isEmpty( )) && (booleanValue))
            //Assign the value to the final variable.
            return firstNameTemp;
        else {
            firstName.getText( ).clear( );
            firstName.setHintTextColor(Color.RED);
            firstName.setHint("Give your first name");
            return null;

        }

    }

    public String getSurName(View v) {
        String surNameTemp = surName.getText( ).toString( );

        // use SupporMethod class to check if the variable is Alphabetical.
        Boolean booleanValue = SupportMethods.isStringOnlyAlphabet(surNameTemp);

        if ((!surNameTemp.isEmpty( )) && (booleanValue))
            //Assign the value to the final variable.
            return surNameTemp;
        else {
            surName.getText( ).clear( );
            surName.setHintTextColor(Color.RED);
            surName.setHint("Give your surname");
            return null;

        }

    }

    public String getBirthDate(View v) {
        Boolean booleanValue = false;
        String birthDateTemp = birthDate.getText( ).toString( );
        try {
            //Assign the value to the final variable.
            booleanValue = SupportMethods.isStringDateFormat(birthDateTemp);
        } catch (ParseException e) {
            birthDate.getText( ).clear( );
            birthDate.setHintTextColor(Color.RED);
            birthDate.setHint("Give your birthdate");
            return null;
        }
        if (booleanValue == true)
            return birthDateTemp;
        else{
            birthDate.getText( ).clear( );
            birthDate.setHintTextColor(Color.RED);
            birthDate.setHint("Give your birthdate");
            return null;
        }

    }

    public String getEmail(View v) {
        String emailTemp = email.getText( ).toString( );

        // use SupporMethod class to check if the variable is Alphabetical.
        Boolean booleanValue = SupportMethods.isStringEmailAddress(emailTemp);

        if ((!emailTemp.isEmpty( )) && (booleanValue))
            //Assign the value to the final variable.
            return emailTemp;
        else {
            email.getText( ).clear( );
            email.setHintTextColor(Color.RED);
            email.setHint("Give a proper email address");
            return null;

        }

    }

    public String getPhoneNumber(View v) {
        String phoneTemp = phone.getText( ).toString( );

        // use SupporMethod class to check if the variable is Alphabetical.
        Boolean booleanValue = SupportMethods.isStringOnlyNumeric(phoneTemp);
        if ((!phoneTemp.isEmpty( )&& booleanValue))
            //Assign the value to the final variable.
            return phoneTemp;
        else {
            phone.getText( ).clear( );
            phone.setHintTextColor(Color.RED);
            phone.setHint("Give your phone number");
            return null;
        }
    }

    public String getStreetName(View v){
        String streetNameTemp = streetName.getText( ).toString( );
        if ((!streetNameTemp.isEmpty( )))
            //Assign the value to the final variable.
            return streetNameTemp;
        else {
            streetName.getText( ).clear( );
            streetName.setHintTextColor(Color.RED);
            streetName.setHint("Give a street address.");
            return null;
        }
    }

    public String getCity(View v){
        String cityTemp = city.getText( ).toString( );
        // use SupporMethod class to check if the variable is Alphabetical.
        Boolean booleanValue = SupportMethods.isStringOnlyAlphabet(cityTemp);
        if ((!cityTemp.isEmpty( )&& booleanValue))
            //Assign the value to the final variable.
            return cityTemp;
        else {
            city.getText( ).clear( );
            city.setHintTextColor(Color.RED);
            city.setHint("Give your city");
            return null;
        }
    }

    public String getPostalCode(View v){
        String postalTemp = postalCode.getText( ).toString( );
        Boolean booleanValue = SupportMethods.isStringOnlyNumeric(postalTemp);
        if ((!postalTemp.isEmpty( )&& booleanValue))
            //Assign the value to the final variable.
            return postalTemp;
        else {
            postalCode.getText( ).clear( );
            postalCode.setHintTextColor(Color.RED);
            postalCode.setHint("Give the postal code correctly");
            return null;
        }
    }

    public String getUsername(View v) {
        String UsernameTemp = username.getText( ).toString( );
        if ((!UsernameTemp.isEmpty( )))
            //Assign the value to the final variable.
            return UsernameTemp;
        else {
            username.getText( ).clear( );
            username.setHintTextColor(Color.RED);
            username.setHint("Give a user name");
            return null;
        }

    }

    public String getPassword(View v) {
        String PasswordTemp = password.getText( ).toString( );
        if ((!PasswordTemp.isEmpty( )))
            return  PasswordTemp;
        else {
            password.getText( ).clear( );
            password.setHintTextColor(Color.RED);
            password.setHint("Give a password");
            return null;
        }
    }

    //**********************************************************//
    // Extra layer to handle the contact information collection
    public ArrayList<String> collectInfo(View v){

        ArrayList<String> ContactInfo = new ArrayList<>();

        String firstNameString = getFirstName(v);
        ContactInfo.add(firstNameString);

        String surNameString = getSurName(v);
        ContactInfo.add(surNameString);

        String birthDateString = getBirthDate(v);
        ContactInfo.add(birthDateString);

        String emailString = getEmail(v);
        ContactInfo.add(emailString);

        String phoneString = getPhoneNumber(v);
        ContactInfo.add(phoneString);

        String streetNameString = getStreetName(v);
        ContactInfo.add(streetNameString);

        String cityString = getCity(v);
        ContactInfo.add(cityString);

        String postalCodeString = getPostalCode(v);
        ContactInfo.add(postalCodeString);

        String usernameString = getUsername(v);
        ContactInfo.add(usernameString);

        String passwordString = getPassword(v);
        ContactInfo.add(passwordString);

    return ContactInfo; }

    // Method which actually handles all the operations to create an account
    public Boolean createAccount(View v){

        ArrayList<String> ContactInfoCheck = collectInfo(v);

        Boolean isContactInfoOK =  SupportMethods.checkThereIsNoNulls(ContactInfoCheck);

        if (isContactInfoOK == true){

           Boolean IsUserNameOk =  Bank.checkUsernameAvailability(ContactInfoCheck.get(9));
           if (IsUserNameOk == true){

            Bank.addAccountToList(ContactInfoCheck);
            return true;
            }
            else {
               username.getText( ).clear( );
               username.setHintTextColor(Color.RED);
               username.setHint("USERNAME IS ALREADY TAKEN");
             }
         }
        return false;
    }

    //Pop up when account has been created
    public void openDialog(){
        // How to delay?
        DialogsForUser dia = new DialogsForUser();
        dia.show(getSupportFragmentManager(),"example dialog");
    }
}








