package com.example.harjoitustyopankkiapplikaatio;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.text.method.ScrollingMovementMethod;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.Spinner;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.Arrays;

public class LoginActivity extends AppCompatActivity {

    //KEY
    private Account currentUserAccount = Bank.getLoggedAccount( );


    //*****************************************************************//
    //Declarations

    private TextView welcomeText;
    private TextView showAnything;
    private Spinner SelectShowedAccountTypeInfo;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        // Salute your master!
        welcomeText = (TextView) findViewById(R.id.textViewTitleWelcomeUser);
        welcomeText.setText("Welcome, " + Bank.getLoggedAccount( ).getContactInfo().getFirstName());
        //*****************************************************************//

        //Declaring and initializing buttons//

        // Login activity: Return to main menu
        Button buttonReturnMain = (Button) findViewById(R.id.buttonLogOut);
        buttonReturnMain.setOnClickListener(new View.OnClickListener( ) {
            @Override
            public void onClick(View v) {
                //Return to main menu
                Bank.loggedIn(currentUserAccount);
                Bank.loggedOut( );
                openMainActivity( );
            }

            public void openMainActivity() {
                Intent intent = new Intent(LoginActivity.this, MainActivity.class);
                startActivity(intent);
            }
        });

        // Login activity: Go to Contact Info
        Button buttonContactInfo = (Button) findViewById(R.id.buttonContactInfo);
        buttonContactInfo.setOnClickListener(new View.OnClickListener( ) {
            @Override
            public void onClick(View v) {
                openContactInfoActivity( );
            }

            public void openContactInfoActivity() {
                Intent intent = new Intent(LoginActivity.this, ContactInfoActivity.class);
                startActivity(intent);
            }
        });

        // Login activity: Go to make bank accounts and cards
        Button buttonMakeAccountsCards = (Button) findViewById(R.id.buttonMakeAccountsCards);
        buttonMakeAccountsCards.setOnClickListener(new View.OnClickListener( ) {
            @Override
            public void onClick(View v) {
                //Return to main menu
                openMakeAccountsCardsActivity( );
            }

            public void openMakeAccountsCardsActivity() {
                Intent intent = new Intent(LoginActivity.this, MakeAccountsCardsMainActivity.class);
                startActivity(intent);
            }
        });

        // Login activity: Go to transfer money
        Button buttonTransferMoney = (Button) findViewById(R.id.buttonTransferMoney);
        buttonTransferMoney.setOnClickListener(new View.OnClickListener( ) {
            @Override
            public void onClick(View v) {
                //Return to main menu
                openTransferMoneyActivity( );
            }

            public void openTransferMoneyActivity() {
                Intent intent = new Intent(LoginActivity.this, TransferMoneyActivity.class);
                startActivity(intent);
            }
        });
        //*****************************************************************//

        // Declaring and initializing Spinner
        ArrayList<String> selectionList = new ArrayList<>(Arrays.asList("SELECT WHAT TO SHOW","ACCOUNT: DEBIT/CREDIT","ACCOUNT: DEBIT","ACCOUNT: CREDIT","CARD:DEBIT/CREDIT","CARD: DEBIT","CARD: CREDIT"));
        SelectShowedAccountTypeInfo = findViewById(R.id.spinnerSelectShowedAccountInfo);
        ArrayAdapter<String> adapter = new ArrayAdapter<String>(this, android.R.layout.simple_spinner_item, selectionList);
        adapter.setDropDownViewResource(android.R.layout.simple_dropdown_item_1line);
        SelectShowedAccountTypeInfo.setAdapter(adapter);
        SelectShowedAccountTypeInfo.setOnItemSelectedListener(new SelectShowedAccountInfoSpinnerClass( ));

        //*****************************************************************//


        //Initialize TextView
        showAnything = (TextView) findViewById(R.id.textViewShowAnything);
        showAnything.setMovementMethod(new ScrollingMovementMethod( ));

        //*****************************************************************//
        // DISPLAY ACCOUNT OR CARDS IN TEXTVIEW

    }
    // is activated when activity is opened
    class SelectShowedAccountInfoSpinnerClass implements AdapterView.OnItemSelectedListener {
        public void onItemSelected(AdapterView<?> parent, View v, int position, long id) {
            String SelectedAccountCardOptionToShow = parent.getItemAtPosition(position).toString( );

            if (SelectedAccountCardOptionToShow.matches("ACCOUNT: DEBIT/CREDIT")){
                //Display debit/credit accounts
                displayDebitCreditAccount();


            } else if (SelectedAccountCardOptionToShow.matches("ACCOUNT: DEBIT")) {
                //Display debit accounts
                displayDebitAccount();


            } else if (SelectedAccountCardOptionToShow.matches("ACCOUNT: CREDIT")) {
                //Display credit accounts
                displayCreditAccount();

            }
            else if (SelectedAccountCardOptionToShow.matches("CARD:DEBIT/CREDIT")) {
                //Display debit/credit cards
                displayDebitCreditCard();
            }
            else if (SelectedAccountCardOptionToShow.matches("CARD: DEBIT")) {
                //Display debit cards
                displayDebitCard();
            }
            else if (SelectedAccountCardOptionToShow.matches("CARD: CREDIT")) {
                //Display credit cards
                displayCreditCard();
            }
        }

        @Override
        public void onNothingSelected(AdapterView<?> adapterView) {
        }
    }


    ////////////////////////////////////////////////////////////////////////////////////////
    // these methods are used in spinner class based on what in the 1. spinner has been selected
    //Account class display

    public void displayDebitCreditAccount(){
        if (currentUserAccount.getDebitCreditAccounts().size( ) > 0) {
            String finalMessage = "";
            String Message ="";
            for (CreditAccount i : currentUserAccount.getDebitCreditAccounts()) {

                if (i.isDebitActive()==false){
                    Message = "Credit Account Number: "+ i.getCreditNumber( ).toString( ) + "\nName: " +  i.getCreditAccountName( )+"\n Credit saldo:" + i.getCreditSaldo( ).toString( ) + "\n\n";
                }
                else if(i.isCreditActive()==false){
                    Message = "Debit Account Number: "+i.getBankAccountNumber( ).toString( )+ "\nName: " +  i.getDebitAccountName()+ "\n Balance: "+i.getBalance( ).toString() + "\n\n";
                }
                else {
                    Message = "Debit/Credit Account Number: "+i.getBankAccountNumber( ).toString( )+"/" + i.getCreditNumber( ).toString( ) + "\nName: " +  i.getCreditAccountName( )+ "\nBalance: "+i.getBalance( ).toString()+"\nCredit saldo:" + i.getCreditSaldo( ).toString( ) + "\n\n";

                }

                finalMessage = finalMessage + Message;
            }
            showAnything.setText(finalMessage);
        } else {
            showAnything.setText("There is no debit/credit accounts yet");
        }
    }

    public void displayDebitAccount(){
        if (currentUserAccount.getDebitAccounts().size( ) > 0) {
            String finalMessage = "";
            for (DebitAccount i : currentUserAccount.getDebitAccounts()) {
                String Message = "Debit Account Number:" + i.getBankAccountNumber( ).toString( ) + "\nName:" +  i.getDebitAccountName( ) + "\nBalance:" + i.getBalance( ).toString( ) + "\n\n";
                finalMessage = finalMessage + Message;
            }
            showAnything.setText(finalMessage);
        } else {
            showAnything.setText("There is no debit accounts yet.");
        }
    }

    public void displayCreditAccount(){
        if (currentUserAccount.getCreditAccounts().size( ) > 0) {
        String finalMessage = "";
        for (CreditAccount i : currentUserAccount.getCreditAccounts()) {
            String Message = "Credit Account Number::" + i.getCreditNumber( ).toString( ) + "\nName:" + i.getCreditAccountName( ) + "\nCredit saldo:" + i.getCreditSaldo( ).toString( ) + "\n\n";
            finalMessage = finalMessage + Message;
        }
        showAnything.setText(finalMessage);
    } else {
        showAnything.setText("There is no credit accounts yet.");
    }}

    //Card class display

    public void displayDebitCreditCard(){

        Boolean isEmpty = true;

        for (CreditAccount i : currentUserAccount.getDebitCreditAccounts()) {
            if(i.getSuperCardsInAccount().size()>0){
                isEmpty = false;
                break;
            }
            }

        if (isEmpty==false) {
            String finalMessage = "";
            String  Message;
            for (CreditAccount i : currentUserAccount.getDebitCreditAccounts()) {
                for ( SuperCard s : i.getSuperCardsInAccount()){

                    if(s.getCreditCardHolder().getIsAcvice()==false){
                        Message = "Debit Account Number:"+s.getDebitCardHolder().getDebitBankNumber()+"\nDebit Card number"+s.getId()+"\n  Name:"+s.getDebitCardHolder().getdebitCardName()+"\nBalance:"+s.getDebitCardHolder().getDebitBalance()+"\nDebit payment  limit:"+s.getDebitCardHolder().getPaymentLimitDebit()+ "\n\n";

                    }
                    else if(s.getDebitCardHolder().getIsAcvice()==false){
                        Message = "Credit Account Number:"+s.getDebitCardHolder().getDebitBankNumber()+ "\nCredit Card number:"+s.getId()+"\nName:"+s.getCreditCardHolder().getCreditCardName()+",\nCredit Saldo:"+s.getCreditCardHolder().getCreditSaldo()+"\nCredit payment  limit:"+s.getCreditCardHolder().getPaymentLimitCredit()+"\n\n";
                    }
                    else{
                        Message = "Debit/Credit Account Number:"+s.getDebitCardHolder().getDebitBankNumber()+"/"+s.getCreditCardHolder().getCreditAccountNumber()+"debit/credit Card ID:"+s.getId()+"\nName:"+s.getCreditCardHolder().getCreditCardName()+"\nBalance:"+s.getDebitCardHolder().getDebitBalance()+"\nCredit Saldo:"+s.getCreditCardHolder().getCreditSaldo()+"\nCredit payment  limit:"+s.getCreditCardHolder().getPaymentLimitCredit()+"\nDebit payment  limit:"+s.getDebitCardHolder().getPaymentLimitDebit()+ "\n\n";

                    }
                    finalMessage = finalMessage + Message;
                }

            }
            showAnything.setText(finalMessage);

        } else {
            showAnything.setText("There is no debit/credit cards yet.");
        }

    }

    public void displayDebitCard(){


        Boolean isEmpty = true;

        for (DebitAccount i : currentUserAccount.getDebitAccounts()) {
            if(i.getDebitCardsInAccount().size()>0){
                isEmpty = false;
                break;

            }
        }
        if (isEmpty==false) {
            String finalMessage = "";
            for (DebitAccount i : currentUserAccount.getDebitAccounts()) {
                for ( DebitCard s : i.getDebitCardsInAccount()){

                    String Message = "Debit Card:"+s.getDebitBankNumber()+"\nName:"+s.getdebitCardName()+"\nBalance:"+s.getDebitBalance()+"\nPayment Limit:"+s.getPaymentLimit()+ "\n\n";

                    finalMessage = finalMessage + Message;
                }

            }
            showAnything.setText(finalMessage);

        } else {
            showAnything.setText("There is no debit/credit cards yet.");
        }

    }

    public void displayCreditCard(){

        Boolean isEmpty = true;

        for (CreditAccount i : currentUserAccount.getCreditAccounts()) {
            if(i.getCreditCardsInAccount().size()>0){
                isEmpty = false;
                break;
            }
        }
        if (isEmpty==false) {
            String finalMessage = "";
            for (CreditAccount i : currentUserAccount.getCreditAccounts()) {
                for ( CreditCard s : i.getCreditCardsInAccount()){


                    String Message = "Credit Card Number:"+s.getcreditCardNumber()+"\nName:"+s.getCreditCardName()+"\nCredit Saldo:"+s.getCreditSaldo()+"\nPayment Limit:"+s.getPaymentLimitCredit()+ "\n\n";

                    finalMessage = finalMessage + Message;
                }

            }
            showAnything.setText(finalMessage);

        } else {
            showAnything.setText("There is no debit/credit cards yet.");
        }
    }
}



