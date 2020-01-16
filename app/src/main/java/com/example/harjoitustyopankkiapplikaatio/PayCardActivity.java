package com.example.harjoitustyopankkiapplikaatio;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.CompoundButton;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.Switch;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.Arrays;

public class PayCardActivity extends AppCompatActivity {

    //Getting the user
    private Account currentUserAccount = Bank.getLoggedAccount();

    //Declarations
    //********************************************************************************************//
    private String SpinOutputType;
    private String spinOutputObject;

    private Spinner SelectShowedCardType;
    private Spinner SelectShowedCardTarget;

    private TextView ShowCardInfoName;
    private TextView ShowCardInfoCardNumber;
    private TextView ShowCardInfoBalanceSaldo;
    private TextView ShowCardInfoLimit;

    private Boolean isDebitCreditTransactionOK=false;
    private Boolean isDebitTransactionOK=false;
    private Boolean iCreditTransactionOK=false;
    private Boolean isCreditAvailable = false;

    private Switch creditSwitch;

    protected EditText receiverCardNumber;
    protected EditText amountToSend;

    //********************************************************************************************//
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_pay_card);

        //********************************************************************************************//
        //initializing switch
        creditSwitch = (Switch) findViewById(R.id.switchCreditOnOffCard);
        creditSwitch.setClickable(false);
        creditSwitch.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener( ) {
            @Override
            public void onCheckedChanged(CompoundButton compoundButton, boolean b) {
                if(b == true){
                    isCreditAvailable= true;

                }else{
                    isCreditAvailable= false;
                }
            }
        });
        //********************************************************************************************//
        //initializing EditTexts
        receiverCardNumber = (EditText) findViewById(R.id.editTextReceiverCard) ;
        amountToSend = (EditText) findViewById(R.id.editTextAmountCard) ;


        //initializing textViews
        ShowCardInfoName =  (TextView) findViewById(R.id.textViewCardName);
        ShowCardInfoCardNumber =  (TextView) findViewById(R.id.textViewCardNumber);
        ShowCardInfoBalanceSaldo =  (TextView) findViewById(R.id.textViewDisposableCard);
        ShowCardInfoLimit = (TextView) findViewById(R.id. textViewPaymentLimit);

        //initializing spinners
        SelectShowedCardType = findViewById(R.id.spinnerSelectCardTypePayCard);
        SelectShowedCardTarget = findViewById(R.id.spinnerSelectShowedCardObjectPayCard);

        //********************************************************************************************//
        initializeAccountTypeSpinner();

        //********************************************************************************************//
        // //initializing buttons
        //Make PayCard action
        Button buttonMakePayCardAction  = (Button) findViewById(R.id.buttonMakePayCardAction);
        buttonMakePayCardAction .setOnClickListener(new View.OnClickListener( ) {

            @Override
            public void onClick(View v) {
                if(canWeStart()) {
                    makePayCardAction( );
                    IOXML.getInstance().saveToXML(currentUserAccount.getBankAccountTransactions());
                    initializeAccountTypeSpinner( );
                }
            }

            public void makePayCardAction() {
                String receiverAccountNumberText =receiverCardNumber.getText().toString() ;
                String amountToSendDoubleText =amountToSend.getText().toString();
                creditSwitch.isChecked();

                if(isDebitCreditTransactionOK==true &&inputsNotEmpty(receiverAccountNumberText,amountToSendDoubleText)==true){

                    payDebitCreditCard(spinOutputObject,Integer.parseInt(receiverAccountNumberText),Double.parseDouble(amountToSendDoubleText));

                }
                else if(isDebitTransactionOK==true&&inputsNotEmpty(receiverAccountNumberText,amountToSendDoubleText)==true){

                    payDebitCard(spinOutputObject,Integer.parseInt(receiverAccountNumberText),Double.parseDouble(amountToSendDoubleText));

                }
                else if(iCreditTransactionOK==true&&inputsNotEmpty(receiverAccountNumberText,amountToSendDoubleText)==true &&isCreditAvailable==true){
                    payCreditCard(spinOutputObject,Integer.parseInt(receiverAccountNumberText),Double.parseDouble(amountToSendDoubleText));

                }
                else{
                    receiverCardNumber.setHint("No input or action chosen");
                    amountToSend.setHint("No input or action chosen");
                }
                resetValues();
            }


        });

        ////PayCardActivity: : Return to TransferMoneyActivity
        Button buttonReturnTransMoneyFromPayCard  = (Button) findViewById(R.id.buttonReturnFromPayAccountToTransferMoney);
        buttonReturnTransMoneyFromPayCard .setOnClickListener(new View.OnClickListener( ) {
            @Override
            public void onClick(View v) {
                openTransferMoneyActivity( );
            }

            public void openTransferMoneyActivity() {
                Intent intent = new Intent(PayCardActivity.this, TransferMoneyActivity.class);
                startActivity(intent);
            }
        });
    }

    //********************************************************************************************//
    // methods and spinner classes for initializing spinners
    private void initializeAccountTypeSpinner() {
        ArrayList<String> selectionList = new ArrayList<>(Arrays.asList("SELECT CARD TYPE","CARD: DEBIT/CREDIT","CARD: DEBIT","CARD: CREDIT"));
        ArrayAdapter<String> adapter = new ArrayAdapter<String>(this, android.R.layout.simple_spinner_item, selectionList);
        adapter.setDropDownViewResource(android.R.layout.simple_dropdown_item_1line);
        SelectShowedCardType.setAdapter(adapter);
        SelectShowedCardType.setOnItemSelectedListener(new SelectShowedAccountInfoSpinnerClass( ));
    }

    class SelectShowedAccountInfoSpinnerClass implements AdapterView.OnItemSelectedListener {
        public void onItemSelected(AdapterView<?> parent, View v, int position, long id) {

            String oldSpinOutputType ="";
            SpinOutputType = parent.getItemAtPosition(position).toString( );
            if (oldSpinOutputType!=SpinOutputType){
                oldSpinOutputType=SpinOutputType;
                resetValues();
            }

            ArrayList<String> listForCardObjectSpinner = new ArrayList<>( );

            if (SpinOutputType.matches("CARD: DEBIT/CREDIT")) {

                creditSwitch.setClickable(true);
                listForCardObjectSpinner = getDebitCreditCardForDisplay( );


            }else if (SpinOutputType.matches("CARD: DEBIT")) {

                creditSwitch.setClickable(false);creditSwitch.setChecked(false);
                listForCardObjectSpinner = getDebitCardForDisplay( );


            }else if (SpinOutputType.matches("CARD: CREDIT")) {

                creditSwitch.setClickable(false);creditSwitch.setChecked(true);
                listForCardObjectSpinner = getCreditCardForDisplay( );

            }else{creditSwitch.setClickable(false);resetValues();}

            activeAccountCardObjectSpinner(listForCardObjectSpinner);
        }
        @Override
        public void onNothingSelected(AdapterView<?> adapterView) {
        }
    }

    //******************************//
    private void initializeAccountCardObjectSpinner(ArrayList<String> selectionList) {
        ArrayAdapter<String> adapter = new ArrayAdapter<String>(this, android.R.layout.simple_spinner_item, selectionList);
        adapter.setDropDownViewResource(android.R.layout.simple_dropdown_item_1line);
        SelectShowedCardTarget.setAdapter(adapter);
        SelectShowedCardTarget.setOnItemSelectedListener(new SelectShowedAccountCardObjectSpinnerClass( ));
    }

    class SelectShowedAccountCardObjectSpinnerClass implements AdapterView.OnItemSelectedListener {
        public void onItemSelected(AdapterView<?> parent, View v, int position, long id) {

            spinOutputObject = parent.getItemAtPosition(position).toString( );

            if (!spinOutputObject.isEmpty( ) && !spinOutputObject.matches("SELECT CARD") && !SpinOutputType.isEmpty( ) && !SpinOutputType.matches("SELECT CARD TYPE")) {

                if (SpinOutputType.matches("CARD: DEBIT/CREDIT")) {

                    showDebitCreditCards(Integer.parseInt(spinOutputObject));
                    whichActionIsGo("debit/credit");
                } else if (SpinOutputType.matches("CARD: DEBIT")) {

                    showDebitCards(Integer.parseInt(spinOutputObject));
                    whichActionIsGo("debit");

                } else if (SpinOutputType.matches("CARD: CREDIT")) {

                    showCreditCards(Integer.parseInt(spinOutputObject));
                    whichActionIsGo("credit");
                }
            } else {
                whichActionIsGo(null);
            }
        }
        @Override
        public void onNothingSelected(AdapterView<?> adapterView) {
        }

    }

    //********************************************************************************************//
    //Account data output getters

    public void resetValues(){


        ShowCardInfoName.setText("CARD NAME: ");
        ShowCardInfoCardNumber.setText("CARD NUMBER: ");
        ShowCardInfoBalanceSaldo.setText("DEBIT/CREDIT:");
        ShowCardInfoLimit.setText("PAYMENT LIMIT:");
        receiverCardNumber.getText().clear();
        amountToSend.getText().clear();
        whichActionIsGo(null);
    }

    public void showDebitCreditCards(Integer cardNum){

        ShowCardInfoName.setText("CARD NAME:    "+currentUserAccount.getDebitCreditCard(cardNum).getCreditCardHolder().getcreditCardNumber());
        ShowCardInfoCardNumber.setText("DEBIT/CREDIT NUMBER:    "+currentUserAccount.getDebitCreditCard(cardNum).getDebitCardHolder().getDebitCardNumber()+"/"+currentUserAccount.getDebitCreditCard(cardNum).getCreditCardHolder().getcreditCardNumber());
        ShowCardInfoBalanceSaldo.setText("DEBIT/CREDIT:   "+currentUserAccount.getDebitCreditCard(cardNum).getDebitCardHolder().getDebitBalance()+"/"+currentUserAccount.getDebitCreditCard(cardNum).getCreditCardHolder().getCreditSaldo());
        ShowCardInfoLimit.setText("PAYMENT LIMIT:    "+currentUserAccount.getDebitCreditCard(cardNum).getDebitCardHolder().getPaymentLimitDebit()+"/"+currentUserAccount.getDebitCreditCard(cardNum).getCreditCardHolder().getPaymentLimitCredit());
    }

    public void showDebitCards(Integer cardNum){

        ShowCardInfoName.setText("CARD NAME:    "+currentUserAccount.getDebitCard(cardNum).getdebitCardName());
        ShowCardInfoCardNumber.setText("CARD NUMBER:    "+currentUserAccount.getDebitCard(cardNum).getDebitCardNumber());
        ShowCardInfoBalanceSaldo.setText("DISPOSABLE:    "+currentUserAccount.getDebitCard(cardNum).getDebitBalance());
        ShowCardInfoLimit.setText("PAYMENT LIMIT:    "+currentUserAccount.getDebitCard(cardNum).getPaymentLimitDebit());
    }

    public void showCreditCards(Integer cardNum){

        ShowCardInfoName.setText("CARD NAME:    "+currentUserAccount.getCreditCard(cardNum).getCreditCardName());
        ShowCardInfoCardNumber.setText("CARD NUMBER:    "+currentUserAccount.getCreditCard(cardNum).getcreditCardNumber());
        ShowCardInfoBalanceSaldo.setText("DISPOSABLE:    "+currentUserAccount.getCreditCard(cardNum).getCreditSaldo());
        ShowCardInfoLimit.setText("PAYMENT LIMIT:    "+currentUserAccount.getCreditCard(cardNum).getPaymentLimitCredit());
    }


    private void payDebitCreditCard(String spinOutputObject, Integer receiverAccountNumberInt, Double amountToSendDouble) {

        Boolean isActionCompleted;
        Integer cardNumber = Integer.parseInt(spinOutputObject);




        creditSwitch.isChecked();
        if(isCreditAvailable==true){
           isActionCompleted = Bank.payDebitCreditCard((currentUserAccount.getUserInfo().getId()),cardNumber,receiverAccountNumberInt,amountToSendDouble,true);
            if(isActionCompleted != null){
                Integer cardsAccountNumber = Bank.getCreditDebitCardCreditAccountNumber(cardNumber);

                if(isActionCompleted){currentUserAccount.createBankAccountTransactionRecord
                        (5,"CREDIT PAY CARD",currentUserAccount.getUserInfo().getId().toString(),"DEBIT/CREDIT",cardsAccountNumber.toString(),"SUPER CARD",spinOutputObject,amountToSendDouble.toString());
                }
            }
        }
        else{
           isActionCompleted = Bank.payDebitCreditCard((currentUserAccount.getUserInfo().getId()),cardNumber,receiverAccountNumberInt,amountToSendDouble,false);
            if(isActionCompleted != null){
                Integer cardsAccountNumber = Bank.getCreditDebitCardDebitAccountNumber(cardNumber);

                if(isActionCompleted){currentUserAccount.createBankAccountTransactionRecord
                        (6,"DEBIT PAY CARD",currentUserAccount.getUserInfo().getId().toString(),"DEBIT/CREDIT",cardsAccountNumber.toString(),"SUPER CARD",spinOutputObject,amountToSendDouble.toString());
                }
            }
        }
        messageForUserTransactionEnd(isActionCompleted);
    }

    private void payDebitCard(String spinOutputObject, Integer receiverAccountNumberInt, Double amountToSendDouble) {



        Integer cardNumber = Integer.parseInt(spinOutputObject);
        Boolean isActionCompleted = Bank.payDebitCard((currentUserAccount.getUserInfo().getId()),cardNumber,receiverAccountNumberInt,amountToSendDouble);
        if(isActionCompleted != null) {
            Integer cardsAccountNumber = Bank.getDebitCardAccountNumber(cardNumber);

            if (isActionCompleted) {
                currentUserAccount.createBankAccountTransactionRecord
                        (7, "DEBIT PAY CARD", currentUserAccount.getUserInfo( ).getId( ).toString( ), "DEBIT", cardsAccountNumber.toString( ), "DEBIT CARD", spinOutputObject,amountToSendDouble.toString());
            }
        }
        messageForUserTransactionEnd(isActionCompleted);
    }

    private void payCreditCard(String spinOutputObject, Integer receiverAccountNumberInt, Double amountToSendDouble) {

        Integer cardNumber = Integer.parseInt(spinOutputObject);
        Boolean isActionCompleted = Bank.payCreditCard((currentUserAccount.getUserInfo().getId()),cardNumber,receiverAccountNumberInt,amountToSendDouble);
        if(isActionCompleted != null) {
            Integer cardsAccountNumber = Bank.getCreditCardAccountNumber(cardNumber);

            if (isActionCompleted) {
                currentUserAccount.createBankAccountTransactionRecord
                        (8, "CREDIT PAY CARD", currentUserAccount.getUserInfo( ).getId( ).toString( ), "CREDIT", cardsAccountNumber.toString( ), "CREDIT CARD", spinOutputObject,amountToSendDouble.toString());
            }
        }
        messageForUserTransactionEnd(isActionCompleted);

    }


    //********************************************************************************************//
    //else

    public void whichActionIsGo(String access){

        if (access =="debit/credit"){
            isDebitCreditTransactionOK=true;
            isDebitTransactionOK=false;
            iCreditTransactionOK=false;
        }
        else if(access=="debit"){
            isDebitCreditTransactionOK=false;
            isDebitTransactionOK=true;
            iCreditTransactionOK=false;
        }
        else if(access=="credit"){
            isDebitCreditTransactionOK=false;
            isDebitTransactionOK=false;
            iCreditTransactionOK=true;
        }
        else{
            isDebitCreditTransactionOK=false;
            isDebitTransactionOK=false;
            iCreditTransactionOK=false;
        }
    }

    private boolean inputsNotEmpty(String s1, String s2) {

        if(!s1.isEmpty() && !s2.isEmpty()){
            return true;
        }
        return false;}

    private void messageForUserTransactionEnd(Boolean isActionCompleted) {

        if(isActionCompleted==null) {
            receiverCardNumber.setHint("Card limit excess!");
            amountToSend.setHint("Card limit excess!");

        }else{
            if(isActionCompleted==true){
                receiverCardNumber.setHint("Transaction completed!");
                amountToSend.setHint("Transaction completed!");
            }
            else if (isActionCompleted == false){
                receiverCardNumber.setHint("Not enough money!");
                amountToSend.setHint("Not enough money!");
            }
        }

    }

    private ArrayList<String> getDebitCreditCardForDisplay() {
        ArrayList <String> tempList = new ArrayList<>(Arrays.asList("SELECT CARD"));
        if (currentUserAccount.getDebitCreditAccounts().size( ) > 0) {
            for( CreditAccount i: currentUserAccount.getDebitCreditAccounts()){
                if(i.getSuperCardsInAccount().size()>0){
                    for(SuperCard s:i.getSuperCardsInAccount()){
                        tempList.add(s.getId().toString());
                    }
                }
            }
        }
        return tempList;
    }

    private ArrayList<String> getDebitCardForDisplay() {
        ArrayList <String> tempList = new ArrayList<>(Arrays.asList("SELECT CARD"));
        if (currentUserAccount.getDebitAccounts().size( ) > 0) {
            for( DebitAccount i: currentUserAccount.getDebitAccounts()){
                if(i.getDebitCardsInAccount().size()>0){
                    for(DebitCard s:i.getDebitCardsInAccount()){
                        tempList.add(s.getDebitCardNumber().toString());
                    }
                }
            }
        }
        return tempList;
    }

    private ArrayList<String> getCreditCardForDisplay() {
        ArrayList <String> tempList = new ArrayList<>(Arrays.asList("SELECT CARD"));
        if (currentUserAccount.getCreditAccounts().size( ) > 0) {
            for( CreditAccount i: currentUserAccount.getCreditAccounts()){
                if(i.getCreditCardsInAccount().size()>0){
                    for(CreditCard s:i.getCreditCardsInAccount()){
                        tempList.add(s.getcreditCardNumber().toString());
                    }
                }
            }
        }
        return tempList;
    }

    private void SetSpinnerSelectNothing(Spinner s) {
        ArrayList<String> nothingList = new ArrayList(Arrays.asList(""));
        ArrayAdapter<String> adapter = new ArrayAdapter<String>(this, android.R.layout.simple_spinner_item, nothingList);
        adapter.setDropDownViewResource(android.R.layout.simple_dropdown_item_1line);
        s.setAdapter(adapter);
    }

    private void activeAccountCardObjectSpinner( ArrayList<String> arrayList){

        if (arrayList.isEmpty( ) ) {
            SetSpinnerSelectNothing(SelectShowedCardTarget);
            SelectShowedCardTarget.setEnabled(false);
        }
        else{
            initializeAccountCardObjectSpinner(arrayList);
            SelectShowedCardTarget.setEnabled(true);
        }

    }

    private Boolean canWeStart() {
        if (isDebitCreditTransactionOK || isDebitTransactionOK || iCreditTransactionOK) {
            return true;
        } else {
            return false;
        }
    }
}

