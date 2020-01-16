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

public class MasterCheckUserAccountsAndCards extends AppCompatActivity {

    private Account currentUserAccount = Bank.getLoggedAccount( );

    private TextView welcomeText;
    private TextView showAnything;
    private Spinner SelectShowedAccountTypeInfo;
    Button buttonReturnCheckAllUserInfo;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_master_check_user_accounts_and_cards);

        welcomeText = (TextView) findViewById(R.id.textViewTitleAccountsAndCardMaster);
        SelectShowedAccountTypeInfo = findViewById(R.id.spinnerSelectShowedAccountInfoMaster);
        buttonReturnCheckAllUserInfo = (Button) findViewById(R.id.buttonReturnToAllUserInfoMaster);
        showAnything = (TextView) findViewById(R.id.textViewShowAccountsAndCardsMaster);


        showAnything.setMovementMethod(new ScrollingMovementMethod( ));
        welcomeText.setText("USERS, " + Bank.getLoggedAccount( ).contactInfo.firstName + " ACCOUNTS AND CARDS");
        buttonReturnCheckAllUserInfo.setOnClickListener(new View.OnClickListener( ) {
            @Override
            public void onClick(View v) {
                openMasterCheckUserAccountsAndCardsActivity( );
            }

            public void openMasterCheckUserAccountsAndCardsActivity() {
                Intent intent = new Intent(MasterCheckUserAccountsAndCards.this, MasterCheckAllUserInfoActivity.class);
                startActivity(intent);
            }
        });

        activeShowAccountsAndCardsMasterSpinner();

    }


    public void activeShowAccountsAndCardsMasterSpinner(){
        ArrayList<String> selectionList = new ArrayList<>(Arrays.asList("SELECT WHAT TO SHOW", "ACCOUNT: DEBIT/CREDIT", "ACCOUNT: DEBIT", "ACCOUNT: CREDIT", "CARD:DEBIT/CREDIT", "CARD: DEBIT", "CARD: CREDIT"));
        ArrayAdapter<String> adapter = new ArrayAdapter<String>(this, android.R.layout.simple_spinner_item, selectionList);
        adapter.setDropDownViewResource(android.R.layout.simple_dropdown_item_1line);
        SelectShowedAccountTypeInfo.setAdapter(adapter);
        SelectShowedAccountTypeInfo.setOnItemSelectedListener(new SelectShowedAccountAndCardsMasterInfoSpinnerClass( ));

    }
    class SelectShowedAccountAndCardsMasterInfoSpinnerClass implements AdapterView.OnItemSelectedListener {
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

    public void displayDebitCreditAccount(){
        if (currentUserAccount.debitCreditAccounts.size( ) > 0) {
            String finalMessage = "";
            String Message ="";
            for (CreditAccount i : currentUserAccount.debitCreditAccounts) {

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
        if (currentUserAccount.debitAccounts.size( ) > 0) {
            String finalMessage = "";
            for (DebitAccount i : currentUserAccount.debitAccounts) {
                String Message = "Debit Account Number:" + i.getBankAccountNumber( ).toString( ) + "\nName:" +  i.getDebitAccountName( ) + "\nBalance:" + i.getBalance( ).toString( ) + "\n\n";
                finalMessage = finalMessage + Message;
            }
            showAnything.setText(finalMessage);
        } else {
            showAnything.setText("There is no debit accounts yet.");
        }
    }

    public void displayCreditAccount(){
        if (currentUserAccount.creditAccounts.size( ) > 0) {
            String finalMessage = "";
            for (CreditAccount i : currentUserAccount.creditAccounts) {
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
