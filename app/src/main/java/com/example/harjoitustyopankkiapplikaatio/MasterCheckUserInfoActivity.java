package com.example.harjoitustyopankkiapplikaatio;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.TextView;



public class MasterCheckUserInfoActivity extends AppCompatActivity {
    private Account currentUserAccount = Bank.getLoggedAccount( );

    private TextView title;
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




    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_master_check_user_info);
        //*********************************************************//
        //Initializing TextViews

        title = (TextView) findViewById(R.id.textViewTitlePresentInfoMaster);
        title.setText("USER: "+currentUserAccount.getUserInfo().getUserName()+" INFORMATION");


        firstNameContact = (TextView) findViewById(R.id.textViewDataFirstNameMaster);
        firstNameContact.setText(currentUserAccount.getContactInfo().getFirstName());

        SurameContact = (TextView) findViewById(R.id.textViewDatasurnameMaster);
        SurameContact.setText(currentUserAccount.getContactInfo().getSurName());

        birthDateContact = (TextView) findViewById(R.id.textViewDataBirthdateMaster);
        birthDateContact.setText(currentUserAccount.getContactInfo().getBirthDate());

        phoneNumberContact = (TextView) findViewById(R.id.textViewDataPhoneNumberMaster);
        phoneNumberContact.setText(currentUserAccount.getContactInfo().getPhone());

        emailAddressContact = (TextView) findViewById(R.id.textViewDataEmaiLAddressMaster);
        emailAddressContact.setText(currentUserAccount.getContactInfo().getEmail());

        streetNameContact = (TextView) findViewById(R.id.textViewDataStreetNameMaster);
        streetNameContact.setText(currentUserAccount.getContactInfo().getFirstName());

        cityContact = (TextView) findViewById(R.id.textViewDataCityMaster);
        cityContact.setText(currentUserAccount.getContactInfo().getCity());

        postalCodeContact = (TextView) findViewById(R.id.textViewDataPostalCodeMaster);
        postalCodeContact.setText(currentUserAccount.getContactInfo().getPostalCode());

        userNameContact = (TextView) findViewById(R.id.textViewDataUserNameMaster);
        userNameContact.setText(currentUserAccount.getUserInfo().getUserName());

        passwordContact = (TextView) findViewById(R.id.textViewDataPasswordMaster);
        passwordContact.setText(currentUserAccount.getUserInfo().getPassWord());

        //*********************************************************//
        //Initializing EditText

        //*********************************************************//
        //Initializing Buttons

        //MasterCheckUserInfoActivity:: return to MasterCheckAllUserInfoActivity
        Button buttonContactInfo = (Button) findViewById(R.id.returnFromCheckUserInfoActivity);
        buttonContactInfo.setOnClickListener(new View.OnClickListener( ) {
            @Override
            public void onClick(View v) {
                openMasterLoginActivity( );
            }

            public void openMasterLoginActivity() {
                Intent intent = new Intent(MasterCheckUserInfoActivity.this, MasterCheckAllUserInfoActivity.class);
                startActivity(intent);
            }
        });

        //*********************************************************//



    }
}
