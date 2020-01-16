package com.example.harjoitustyopankkiapplikaatio;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.graphics.Color;
import android.graphics.PorterDuff;
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

public class MakeCardActivity extends AppCompatActivity {


    //Getting the user
    private Account currentUserAccount = Bank.getLoggedAccount();

    //Declarations
    //*********************************************************//
    private String SelectedOptionInCardCreation;
    private String SelectedOptionInAccountConnection;

    private Spinner SelectCardCreation;
    private Spinner SelectToWhatAccount;

    private TextView textViewTemp1;
    private TextView textViewTemp2;
    private TextView textViewTemp3;

    private EditText editTextTemp1;
    private EditText editTextTemp2;
    private EditText editTextTemp3;

    private Boolean debitCreditCardGo=false;
    private Boolean debitCardGo=false;
    private Boolean creditCardGo= false;

    //*********************************************************//



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_make_card);


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
        //Initializing Spinners
        ArrayList<String> selectionList = new ArrayList<>(Arrays.asList("SELECT","MAKE DEBIT/CREDIT", "DEBIT", "CREDIT"));
        SelectCardCreation = findViewById(R.id.spinnerSelecCardCreation);
        ArrayAdapter<String> adapter = new ArrayAdapter<String>(this, android.R.layout.simple_spinner_item, selectionList);
        adapter.setDropDownViewResource(android.R.layout.simple_dropdown_item_1line);
        SelectCardCreation.setAdapter(adapter);
        SelectCardCreation.setOnItemSelectedListener(new SelectCreationActionCardSpinnerClass( ));





        //*********************************************************//
        //Initializing buttons



        Button buttonMakeCard = (Button) findViewById(R.id.buttonMakeCard);
        buttonMakeCard.setOnClickListener(new View.OnClickListener( ) {
            @Override
            public void onClick(View v) {
                if (debitCreditCardGo == true && !SelectedOptionInAccountConnection.matches("SELECT")){

                    String[] tempOption = SelectedOptionInAccountConnection.split(":");
                    makeDebitCreditCard(Integer.parseInt(tempOption[1]));

                }
                else if(debitCardGo == true&& !SelectedOptionInAccountConnection.matches("SELECT")){
                    String[] tempOption = SelectedOptionInAccountConnection.split(":");
                   makeDebitCard(Integer.parseInt(tempOption[1]));

                }
                else if(creditCardGo == true&& !SelectedOptionInAccountConnection.matches("SELECT")){
                    String[] tempOption = SelectedOptionInAccountConnection.split(":");
                    makeCreditCard(Integer.parseInt(tempOption[1]));
                }
            }
        });

        Button buttonReturnMakeAccountOrCardMainActivity = (Button) findViewById(R.id.buttonReturnFromMakeCardActivity);
        buttonReturnMakeAccountOrCardMainActivity.setOnClickListener(new View.OnClickListener( ) {
            @Override
            public void onClick(View v) {

                openMakeAccountsCardsMainActivity();


            }
            public void openMakeAccountsCardsMainActivity() {
                Intent intent = new Intent(MakeCardActivity.this, MakeAccountsCardsMainActivity.class);
                startActivity(intent);
            }


        });







    }

    class SelectCreationActionCardSpinnerClass implements AdapterView.OnItemSelectedListener {
        public void onItemSelected(AdapterView<?> parent, View v, int position, long id) {
            SelectedOptionInCardCreation = parent.getItemAtPosition(position).toString( );

            if (SelectedOptionInCardCreation.matches("MAKE DEBIT/CREDIT")){
                setTextDisplayOnOff1();
                SetSpinnerListDebitCreditCard();


                debitCreditCardGo = true;
                debitCardGo = false;
                creditCardGo = false;


            }
            else if(SelectedOptionInCardCreation.matches("DEBIT")){
                setTextDisplayOnOff2();
                SetSpinnerListDebitCard();


                debitCardGo = true;
                debitCreditCardGo = false;
                creditCardGo = false;

            }

            else if(SelectedOptionInCardCreation.matches("CREDIT")){
                setTextDisplayOnOff3();
                SetSpinnerListCreditCard();

                creditCardGo = true;
                debitCardGo = false;
                debitCreditCardGo = false;

            }

            else{
                setTextDisplayOnOff4();
                SetSpinnerShowNothing();

            }

        }
        @Override
        public void onNothingSelected(AdapterView<?> adapterView) {
        }

    }

    class SelectWhatAccountTheCardSpinnerClass implements AdapterView.OnItemSelectedListener {
        public void onItemSelected(AdapterView<?> parent, View v, int position, long id) {


            SelectedOptionInAccountConnection = parent.getItemAtPosition(position).toString( );

        }

        @Override
        public void onNothingSelected(AdapterView<?> adapterView) {
        }
    }


    //DISPLAYING EDITTEXT OPTIONS
    public void setTextDisplayOnOff1(){
        textViewTemp1.setText("Card name");
        textViewTemp2.setText("Payment Limit");

        editTextTemp1.setInputType(InputType.TYPE_CLASS_TEXT);
        editTextTemp2.setInputType(InputType.TYPE_CLASS_NUMBER);
        editTextTemp3.setInputType(InputType.TYPE_NULL);

        editTextTemp1.setHint("Give a card name");
        editTextTemp2.setHint("Give a payment limit");

    }
    public void setTextDisplayOnOff2(){
        textViewTemp1.setText("Card name");
        textViewTemp2.setText("Payment Limit");
        textViewTemp3.setText("");

        editTextTemp1.setInputType(InputType.TYPE_CLASS_TEXT);
        editTextTemp2.setInputType(InputType.TYPE_CLASS_NUMBER);
        editTextTemp3.setInputType(InputType.TYPE_NULL);

        editTextTemp1.setHint("Give a card name");
        editTextTemp2.setHint("Payment Limit");
        editTextTemp3.setHint("");

    }
    public void setTextDisplayOnOff3(){
        textViewTemp1.setText("Card name");
        textViewTemp2.setText("Payment Limit");
        textViewTemp3.setText("");

        editTextTemp1.setInputType(InputType.TYPE_CLASS_TEXT);
        editTextTemp2.setInputType(InputType.TYPE_CLASS_NUMBER);
        editTextTemp3.setInputType(InputType.TYPE_NULL);

        editTextTemp1.setHint("Give a card name");
        editTextTemp2.setHint("Payment Limit");
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

    // TEXT OPTIONS FOT THE 2TH SPINNER
    private void SetSpinnerListDebitCreditCard(){


        ArrayList<String> selectionDebetCreditAccountLists= new ArrayList<>();
        selectionDebetCreditAccountLists.add("SELECT");

        for(CreditAccount i: currentUserAccount.getDebitCreditAccounts()){
            selectionDebetCreditAccountLists.add("ACCOUNT NUMBER:"+i.getCreditNumber().toString());
        }

        SelectToWhatAccount = findViewById(R.id.spinnerSelectAccount);
        ArrayAdapter<String> adapter2 = new ArrayAdapter<String>(this, android.R.layout.simple_spinner_item, selectionDebetCreditAccountLists);
        adapter2.setDropDownViewResource(android.R.layout.simple_dropdown_item_1line);
        SelectToWhatAccount.setAdapter(adapter2);
        SelectToWhatAccount.setOnItemSelectedListener(new SelectWhatAccountTheCardSpinnerClass( ));
    }
    private void SetSpinnerListDebitCard(){

        ArrayList<String> selectionDebetAccountLists= new ArrayList<>();
        selectionDebetAccountLists.add("SELECT");

        for(DebitAccount i: currentUserAccount.getDebitAccounts()){
            selectionDebetAccountLists.add("ACCOUNT NUMBER:"+i.getBankAccountNumber().toString());
        }


        SelectCardCreation = findViewById(R.id.spinnerSelectAccount);
        ArrayAdapter<String> adapter2 = new ArrayAdapter<String>(this, android.R.layout.simple_spinner_item, selectionDebetAccountLists);
        adapter2.setDropDownViewResource(android.R.layout.simple_dropdown_item_1line);
        SelectCardCreation.setAdapter(adapter2);
        SelectCardCreation.setOnItemSelectedListener(new SelectWhatAccountTheCardSpinnerClass( ));
    }
    private void SetSpinnerListCreditCard(){

        ArrayList<String> selectionCreditAccountLists= new ArrayList<>();
        selectionCreditAccountLists.add("SELECT");

        for(CreditAccount i: currentUserAccount.getCreditAccounts()){
            selectionCreditAccountLists.add("ACCOUNT NUMBER:"+i.getCreditNumber().toString());
        }

        SelectCardCreation = findViewById(R.id.spinnerSelectAccount);
        ArrayAdapter<String> adapter2 = new ArrayAdapter<String>(this, android.R.layout.simple_spinner_item, selectionCreditAccountLists);
        adapter2.setDropDownViewResource(android.R.layout.simple_dropdown_item_1line);
        SelectCardCreation.setAdapter(adapter2);
        SelectCardCreation.setOnItemSelectedListener(new SelectWhatAccountTheCardSpinnerClass( ));

    }
    private void SetSpinnerShowNothing(){
        ArrayList<String> emptyList= new ArrayList<>(); emptyList.add("");
        SelectCardCreation = findViewById(R.id.spinnerSelectAccount);
        ArrayAdapter<String> adapter2 = new ArrayAdapter<String>(this, android.R.layout.simple_spinner_item,emptyList);
        adapter2.setDropDownViewResource(android.R.layout.simple_dropdown_item_1line);
        SelectCardCreation.setAdapter(adapter2);
        SelectCardCreation.setOnItemSelectedListener(new SelectWhatAccountTheCardSpinnerClass( ));

    }

    // CARD CREATION METHODS
    private void makeDebitCreditCard(int askedAccount) {
        String debitAndCreditCardName = editTextTemp1.getText( ).toString( ).trim( );
        String paymentLimit = editTextTemp2.getText( ).toString( ).trim( );
        Integer paymentLimitInt;

        if (paymentLimit.isEmpty( )) {
            //there is no limit
            paymentLimitInt  = null;
        }else{
            //Payment Limit
            paymentLimitInt = Integer.parseInt(paymentLimit);
        }

        if (debitAndCreditCardName.isEmpty( )) {
            editTextTemp1.setHintTextColor(Color.RED);
            editTextTemp1.getText( ).clear( );
            editTextTemp1.setHint("Give a card name!");

        }else if (!debitAndCreditCardName.isEmpty( )) {

            Boolean IsDebitAccountNameOK = SupportMethods.isStringOnlyAlphabet(debitAndCreditCardName);

            if (IsDebitAccountNameOK == true) {

               // CreditAccount tempCreditAccountOld=null;
               // CreditAccount tempCreditAccountNew=null;





                for (CreditAccount i :currentUserAccount.getDebitCreditAccounts() ){

                    if( i.getCreditNumber() == askedAccount ) {
                        currentUserAccount.createDebitCreditCard(i, debitAndCreditCardName, paymentLimitInt);
                        //tempCreditAccountOld = i;
                        break;
                    }
                }
                //Integer IndexofOldtempCreditAccount = currentUserAccount.getDebitCreditAccounts().indexOf(tempCreditAccountOld);
                //currentUserAccount.getDebitCreditAccounts().set(IndexofOldtempCreditAccount,tempCreditAccountNew);
                Bank.loggedIn(currentUserAccount);
                editTextTemp1.getText( ).clear( );
                editTextTemp2.getText( ).clear( );

                editTextTemp1.setHint("Account created successfully!");
                editTextTemp2.setHint("Account created successfully!");

                editTextTemp1.setHintTextColor(Color.GRAY);
                    }

                }
             else {
                editTextTemp1.setHintTextColor(Color.RED);
                editTextTemp1.setHint("Account name should be alphabetical.");
            }
    }
    private void makeDebitCard(int askedAccount){
        String debitCardName = editTextTemp1.getText( ).toString( ).trim( );
        String paymentLimit = editTextTemp2.getText( ).toString( ).trim( );
        Integer paymentLimitInt;

        if (paymentLimit.isEmpty( )) {
            //there is no limit
            paymentLimitInt  = null;
        }else{
            //Payment Limit
            paymentLimitInt = Integer.parseInt(paymentLimit);
        }

        if (debitCardName.isEmpty( )) {
            editTextTemp1.setHintTextColor(Color.RED);
            editTextTemp1.getText( ).clear( );
            editTextTemp1.setHint("Give a card name!");

        }else if (!debitCardName.isEmpty( )) {

            Boolean IsDebitAccountNameOK = SupportMethods.isStringOnlyAlphabet(debitCardName);

            if (IsDebitAccountNameOK == true) {

               // DebitAccount tempDebitAccountOld=null;
               // DebitAccount tempDebitAccountNew=null;

                for (DebitAccount i :currentUserAccount.getDebitAccounts() ){

                    if( i.getBankAccountNumber() == askedAccount ) {
                        currentUserAccount.createDebitCard(i, debitCardName, paymentLimitInt);

                        break;
                    }
                }

                //Integer IndexofOldtempDebitAccount = currentUserAccount.getDebitAccounts().indexOf(tempDebitAccountOld);
                //currentUserAccount.getDebitAccounts().set(IndexofOldtempDebitAccount,tempDebitAccountNew);
                Bank.loggedIn(currentUserAccount);
                editTextTemp1.getText( ).clear( );
                editTextTemp2.getText( ).clear( );

                editTextTemp1.setHint("Card created successfully!");
                editTextTemp2.setHint("Card created successfully!");

                editTextTemp1.setHintTextColor(Color.GRAY);
            }
        }
        else {
            editTextTemp1.setHintTextColor(Color.RED);
            editTextTemp1.setHint("Account name should be alphabetical.");
        }
    }
    private void makeCreditCard(int askedAccount){

        String creditCardName = editTextTemp1.getText( ).toString( ).trim( );
        String paymentLimit = editTextTemp2.getText( ).toString( ).trim( );
        Integer paymentLimitInt;

        if (paymentLimit.isEmpty( )) {
            //there is no limit
            paymentLimitInt  = null;
        }else{
            //Payment Limit
            paymentLimitInt = Integer.parseInt(paymentLimit);
        }

        if (creditCardName.isEmpty( )) {
            editTextTemp1.setHintTextColor(Color.RED);
            editTextTemp1.getText( ).clear( );
            editTextTemp1.setHint("Give a card name!");

        }else if (!creditCardName.isEmpty( )) {

            Boolean IsCreditAccountNameOK = SupportMethods.isStringOnlyAlphabet(creditCardName);

            if (IsCreditAccountNameOK == true) {

              //  CreditAccount tempCreditAccountNew=null;
              //  CreditAccount tempCreditAccountOld=null;

                for (CreditAccount i :currentUserAccount.getCreditAccounts() ){

                    if( i.getCreditNumber() == askedAccount ) {
                         currentUserAccount.createCreditCard(i, creditCardName, paymentLimitInt);
                        //tempCreditAccountOld = i;
                        break;
                    }
                }

                //Integer IndexofOldtempCreditAccount = currentUserAccount.getCreditAccounts().indexOf(tempCreditAccountOld);
                //currentUserAccount.getDebitAccounts().set(IndexofOldtempCreditAccount,tempCreditAccountNew);
                Bank.loggedIn(currentUserAccount);
                editTextTemp1.getText( ).clear( );
                editTextTemp2.getText( ).clear( );

                editTextTemp1.setHint("Card created successfully!");
                editTextTemp2.setHint("Card created successfully!");

                editTextTemp1.setHintTextColor(Color.GRAY);
            }
        }
        else {
            editTextTemp1.setHintTextColor(Color.RED);
            editTextTemp1.setHint("Account name should be alphabetical.");
        }
    }

}
