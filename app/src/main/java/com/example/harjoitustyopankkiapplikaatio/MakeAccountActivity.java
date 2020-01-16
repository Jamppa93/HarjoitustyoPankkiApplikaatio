package com.example.harjoitustyopankkiapplikaatio;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.text.InputType;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.Arrays;

public class MakeAccountActivity extends AppCompatActivity {

    private Account currentUserAccount = Bank.getLoggedAccount();

    //Declarations
    //*********************************************************//
    private String SelectedOptionInAccountCreation;

    private Boolean debitCreditAccountGo;
    private Boolean debitAccountGo;
    private Boolean creditAccountGo;

    private Spinner SelectAccountCreation;

    private TextView textViewTemp1;
    private TextView textViewTemp2;
    private TextView textViewTemp3;

    private EditText editTextTemp1;
    private EditText editTextTemp2;
    private EditText editTextTemp3;



    //*********************************************************//


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_make_account);

        //*********************************************************//
        //Initializing TextViews
        textViewTemp1 = findViewById(R.id.textView1);
        textViewTemp2 = findViewById(R.id.textView2);
        textViewTemp3 = findViewById(R.id.textView3);


        //*********************************************************//
        //Initializing EditTexts

        editTextTemp1 = findViewById(R.id.editText1);
        editTextTemp2 = findViewById(R.id.editText2);
        editTextTemp3 = findViewById(R.id.editText3);


        //*********************************************************//
        //Initializing Spinner
        ArrayList<String> selectionList = new ArrayList<>(Arrays.asList("SELECT","MAKE DEBIT/CREDIT", "DEBIT", "CREDIT"));
        SelectAccountCreation = findViewById(R.id.spinnerSelectAccountCreation);
        ArrayAdapter<String> adapter = new ArrayAdapter<String>(this, android.R.layout.simple_spinner_item, selectionList);
        adapter.setDropDownViewResource(android.R.layout.simple_dropdown_item_1line);
        SelectAccountCreation.setAdapter(adapter);
        SelectAccountCreation.setOnItemSelectedListener(new SelectCreationActionAccountSpinnerClass( ));

        //*********************************************************//
        //Initializing buttons

        Button buttonMakeDebitAccount = (Button) findViewById(R.id.buttonMakeChanges);
        buttonMakeDebitAccount.setOnClickListener(new View.OnClickListener( ) {
            @Override
            public void onClick(View v) {

                if (debitCreditAccountGo == true){
                    makeDebitCreditAccount();

                }
                else if(debitAccountGo == true){
                    makeDebitAccount();

                }
                else if(creditAccountGo == true){
                    makeCreditAccount();

                }

            }


        });

        Button buttonReturnMakeAccountOrCardMainActivity = (Button) findViewById(R.id.buttonReturnFromMakeChangesAccountOrCardsActivity);
        buttonReturnMakeAccountOrCardMainActivity.setOnClickListener(new View.OnClickListener( ) {
            @Override
            public void onClick(View v) {

                openMakeAccountsCardsMainActivity();


            }
            public void openMakeAccountsCardsMainActivity() {
                Intent intent = new Intent(MakeAccountActivity.this, MakeAccountsCardsMainActivity.class);
                startActivity(intent);
            }
        });

    }

        // Class makes an account based on the spinners selection.
        class SelectCreationActionAccountSpinnerClass implements AdapterView.OnItemSelectedListener {


            public void onItemSelected(AdapterView<?> parent, View v, int position, long id) {
                SelectedOptionInAccountCreation = parent.getItemAtPosition(position).toString( );

                if (SelectedOptionInAccountCreation.matches("MAKE DEBIT/CREDIT")){
                    setTextDisplayOnOff1();
                    debitCreditAccountGo = true;
                    debitAccountGo = false;
                    creditAccountGo = false;

                }
                else if(SelectedOptionInAccountCreation.matches("DEBIT")){
                    setTextDisplayOnOff2();
                    debitAccountGo = true;
                    debitCreditAccountGo = false;
                    creditAccountGo = false;


                }

                else if(SelectedOptionInAccountCreation.matches("CREDIT")){
                    setTextDisplayOnOff3();
                    creditAccountGo = true;
                    debitAccountGo = false;
                    debitCreditAccountGo = false;

                }

                else{
                    setTextDisplayOnOff4();
                }

            }
            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {
            }

        }

        //DISPLAYING EDITTEXT OPTIONS
        public void setTextDisplayOnOff1(){
            textViewTemp1.setText("Account name");
            textViewTemp2.setText("Amount of deposit");
            textViewTemp3.setText("Credit Limit");

            editTextTemp1.setInputType(InputType.TYPE_CLASS_TEXT);
            editTextTemp2.setInputType(InputType.TYPE_CLASS_NUMBER);
            editTextTemp3.setInputType(InputType.TYPE_CLASS_NUMBER);

            editTextTemp1.setHint("Give a name");
            editTextTemp2.setHint("Give a deposit");
            editTextTemp3.setHint("Give a credit limit");

        }
        public void setTextDisplayOnOff2(){
            textViewTemp1.setText("Account name");
            textViewTemp2.setText("Amount of deposit");
            textViewTemp3.setText("");

            editTextTemp1.setInputType(InputType.TYPE_CLASS_TEXT);
            editTextTemp2.setInputType(InputType.TYPE_CLASS_NUMBER);
            editTextTemp3.setInputType(InputType.TYPE_NULL);

            editTextTemp1.setHint("Give a name");
            editTextTemp2.setHint("Give a deposit");
            editTextTemp3.setHint("");

        }
        public void setTextDisplayOnOff3(){
            textViewTemp1.setText("Account name");
            textViewTemp2.setText("Credit Limit");
            textViewTemp3.setText("");

            editTextTemp1.setInputType(InputType.TYPE_CLASS_TEXT);
            editTextTemp2.setInputType(InputType.TYPE_CLASS_NUMBER);
            editTextTemp3.setInputType(InputType.TYPE_NULL);

            editTextTemp1.setHint("Give a name");
            editTextTemp2.setHint("Give a credit limit");
            editTextTemp3.setHint("");

     }
        public void setTextDisplayOnOff4(){
            textViewTemp1.setText("");
            textViewTemp2.setText("");
            textViewTemp3.setText("");

            editTextTemp1.setInputType(InputType.TYPE_NULL);
            editTextTemp2.setInputType(InputType.TYPE_NULL);
            editTextTemp3.setInputType(InputType.TYPE_NULL);

            editTextTemp1.setText("");
            editTextTemp2.setText("");
            editTextTemp3.setText("");

            editTextTemp1.setHint("");
            editTextTemp2.setHint("");
            editTextTemp3.setHint("");

        }


        // ACCOUNT CREATION METHODS

    // takes in the edittext parameters and makes the account if the parameters are ok

        public void makeDebitCreditAccount(){
            String getDebitAndCreditAccountName = editTextTemp1.getText( ).toString( ).trim( );
            String getDebitAccountDeposit = editTextTemp2.getText( ).toString( ).trim( );
            String getCreditAccountCredit = editTextTemp3.getText( ).toString( ).trim( );

            if (getDebitAccountDeposit.isEmpty( )) {
                getDebitAccountDeposit = "0";
            }

            if (getCreditAccountCredit.isEmpty( )) {
                getCreditAccountCredit = "10000";
            }

            if (getDebitAndCreditAccountName.isEmpty( )) {
                editTextTemp1.setHintTextColor(Color.RED);
                editTextTemp1.getText( ).clear( );
                editTextTemp1.setHint("Give a account name!");

            } else if (!getDebitAndCreditAccountName.isEmpty( )) {

                Boolean IsDebitAccountNameOK = true ; //SupportMethods.isStringOnlyAlphabet(getDebitAndCreditAccountName);

                if (IsDebitAccountNameOK == true) {


                    //deposit amount
                    Integer getDebitAccountDepositInt = Integer.parseInt(getDebitAccountDeposit);
                    Double getDebitAccountDepositDouble = (double) getDebitAccountDepositInt;

                    //credit limit
                    Integer getCreditAccountCreditInt = Integer.parseInt(getCreditAccountCredit);

                    //Call the Account to create a credit debit account
                    currentUserAccount.createDebitCreditAccount(getDebitAndCreditAccountName,getDebitAccountDepositDouble,getCreditAccountCreditInt);
                    Bank.loggedIn(currentUserAccount);

                    editTextTemp1.getText( ).clear( );
                    editTextTemp2.getText( ).clear( );
                    editTextTemp3.getText( ).clear( );

                    editTextTemp1.setHint("Account created successfully!");
                    editTextTemp2.setHint("Account created successfully!");
                    editTextTemp3.setHint("Account created successfully!");

                    editTextTemp1.setHintTextColor(Color.GRAY);


                } else {
                    editTextTemp1.setHintTextColor(Color.RED);
                    editTextTemp1.setHint("Account name should be alphabetical.");
                }
            }



    }

        public void makeDebitAccount() {
        String getDebitAccountName = editTextTemp1.getText( ).toString( ).trim( );
        String getDebitAccountDeposit = editTextTemp2.getText( ).toString( ).trim( );

        if (getDebitAccountDeposit.isEmpty( )) {
            getDebitAccountDeposit = "0";
        }



        if (getDebitAccountName.isEmpty( )) {
            editTextTemp1.setHintTextColor(Color.RED);
            editTextTemp1.getText( ).clear( );
            editTextTemp1.setHint("Give a account name!");

        } else if (!getDebitAccountName.isEmpty( )) {
            Boolean IsDebitAccountNameOK = true; //SupportMethods.isStringOnlyAlphabet(getDebitAccountName);
            Integer getDebitAccountDepositInt = Integer.parseInt(getDebitAccountDeposit);
            Double getDebitAccountDepositDouble = (double) getDebitAccountDepositInt;
            if (IsDebitAccountNameOK == true) {
                //Call the Account to create a debit account

                currentUserAccount.createDebitAccount(getDebitAccountName, getDebitAccountDepositDouble);
                Bank.loggedIn(currentUserAccount);

                editTextTemp1.getText( ).clear( );
                editTextTemp2.getText( ).clear( );

                editTextTemp1.setHint("Account created successfully!");
                editTextTemp2.setHint("Account created successfully!");

                editTextTemp1.setHintTextColor(Color.GRAY);


            } else {
                editTextTemp1.setHintTextColor(Color.RED);
                editTextTemp1.setHint("Account name should be alphabetical.");
            }
        }
    }

        public void makeCreditAccount( ) {

            String getCreditAccountName = editTextTemp1.getText( ).toString( ).trim( );
            String getCreditAccountCredit = editTextTemp2.getText( ).toString( ).trim( );


            if (getCreditAccountCredit.isEmpty( )) {
                getCreditAccountCredit = "10000";
            }

            if (getCreditAccountName.isEmpty( )) {
                editTextTemp1.setHintTextColor(Color.RED);
                editTextTemp1.getText( ).clear( );
                editTextTemp1.setHint("Give a account name!");

            } else if (!getCreditAccountName.isEmpty( )) {
                Boolean isCreditAccountNameOK = true ;//SupportMethods.isStringOnlyAlphabet(getCreditAccountName);


                if (isCreditAccountNameOK == true) {


                    //Call the Account to create a credit  account

                    Integer getCreditAccountCreditInt = Integer.parseInt(getCreditAccountCredit);

                    currentUserAccount.createCreditAccount(false, getCreditAccountName, getCreditAccountCreditInt);
                    Bank.loggedIn(currentUserAccount);

                    editTextTemp1.getText( ).clear( );
                    editTextTemp2.getText( ).clear( );
                    editTextTemp3.getText( ).clear( );

                    editTextTemp1.setHint("Account created successfully!");
                    editTextTemp2.setHint("Account created successfully!");

                    editTextTemp1.setHintTextColor(Color.GRAY);


                } else {
                    editTextTemp1.setHintTextColor(Color.RED);
                    editTextTemp1.setHint("Account name should be alphabetical.");
                }
            }

        }



}