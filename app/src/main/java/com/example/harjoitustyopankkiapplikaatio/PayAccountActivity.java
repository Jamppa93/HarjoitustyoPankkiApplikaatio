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

public class PayAccountActivity extends AppCompatActivity {

    //Getting the user
    private Account currentUserAccount = Bank.getLoggedAccount();

    //Declarations
    //********************************************************************************************//
    private String SpinOutputType;
    private String spinOutputObject;

    private Spinner SelectShowedAccountType;
    private Spinner SelectShowedAccountTarget;

    private TextView ShowAccountInfoName;
    private TextView ShowAccountInfoAccountNumber;
    private TextView ShowAccountInfoBalanceSaldo;

    private Boolean isDebitCreditTransactionOK=false;
    private Boolean isDebitTransactionOK=false;
    private Boolean iCreditTransactionOK=false;
    private Boolean isCreditAvailable = false;

    private Switch creditSwitch;

    private EditText receiverAccountNumber;
    private EditText amountToSend;

    //********************************************************************************************//
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_pay_account);

        //********************************************************************************************//
        //initializing switch
        creditSwitch = (Switch) findViewById(R.id.switchCreditOnOff);
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
        receiverAccountNumber = (EditText) findViewById(R.id.editTextReceiverAccount) ;
        amountToSend = (EditText) findViewById(R.id.editTextAmount) ;

        //initializing textViews
        ShowAccountInfoName=  (TextView) findViewById(R.id.textViewAccountName);
        ShowAccountInfoAccountNumber=  (TextView) findViewById(R.id.textViewAccountNumber);
        ShowAccountInfoBalanceSaldo=  (TextView) findViewById(R.id.textViewDisposable);

        //initializing spinners
        SelectShowedAccountType = findViewById(R.id.spinnerSelectAccountTypePayAccount);
        SelectShowedAccountTarget = findViewById(R.id.spinnerSelectShowedCardObjectPayAccount);

        //********************************************************************************************//
        initializeAccountTypeSpinner();

        //********************************************************************************************//
        // //initializing buttons
        //Make PayAccount action
        Button buttonMakePayAccountAction = (Button) findViewById(R.id.buttonMakePayAccountAction);
        buttonMakePayAccountAction.setOnClickListener(new View.OnClickListener( ) {

            @Override
            public void onClick(View v) {
                if(canWeStart()) {
                    makePayAccountAction( );
                    IOXML.getInstance().saveToXML(currentUserAccount.getBankAccountTransactions());
                    initializeAccountTypeSpinner( );
                }
            }

            public void makePayAccountAction() {
                String receiverAccountNumberText =receiverAccountNumber.getText().toString() ;
                String amountToSendDoubleText =amountToSend.getText().toString();
                creditSwitch.isChecked();

                if(isDebitCreditTransactionOK==true &&inputsNotEmpty(receiverAccountNumberText,amountToSendDoubleText)==true){
                    System.out.println(receiverAccountNumberText+amountToSendDoubleText);
                    payDebitCreditAccount(spinOutputObject,Integer.parseInt(receiverAccountNumberText),Double.parseDouble(amountToSendDoubleText));

                }
                else if(isDebitTransactionOK==true&&inputsNotEmpty(receiverAccountNumberText,amountToSendDoubleText)==true){
                    System.out.println(receiverAccountNumberText+amountToSendDoubleText);
                    payDebitAccount(spinOutputObject,Integer.parseInt(receiverAccountNumberText),Double.parseDouble(amountToSendDoubleText));

                }
                else if(iCreditTransactionOK==true&&inputsNotEmpty(receiverAccountNumberText,amountToSendDoubleText)==true &&isCreditAvailable==true){
                    System.out.println(receiverAccountNumberText+amountToSendDoubleText);
                    payCreditAccount(spinOutputObject,Integer.parseInt(receiverAccountNumberText),Double.parseDouble(amountToSendDoubleText));
                }
                resetValues();
            }


        });

        //PayAccountActivity: Return to TransferMoneyActivity
        Button buttonReturnTransMoneyFromPayAccount = (Button) findViewById(R.id.buttonReturnFromPayAccountToTransferMoney);
        buttonReturnTransMoneyFromPayAccount.setOnClickListener(new View.OnClickListener( ) {
            @Override
            public void onClick(View v) {
                openTransferMoneyActivity( );
            }

            public void openTransferMoneyActivity() {
                Intent intent = new Intent(PayAccountActivity.this, TransferMoneyActivity.class);
                startActivity(intent);
            }
        });
    }

    //********************************************************************************************//
    // methods and spinner classes for initializing spinners
    private void initializeAccountTypeSpinner() {
        ArrayList<String> selectionList = new ArrayList<>(Arrays.asList("SELECT ACCOUNT TYPE","ACCOUNT: DEBIT/CREDIT","ACCOUNT: DEBIT","ACCOUNT: CREDIT"));
        ArrayAdapter<String> adapter = new ArrayAdapter<String>(this, android.R.layout.simple_spinner_item, selectionList);
        adapter.setDropDownViewResource(android.R.layout.simple_dropdown_item_1line);
        SelectShowedAccountType.setAdapter(adapter);
        SelectShowedAccountType.setOnItemSelectedListener(new SelectShowedAccountInfoSpinnerClass( ));
    }

    class SelectShowedAccountInfoSpinnerClass implements AdapterView.OnItemSelectedListener {
        public void onItemSelected(AdapterView<?> parent, View v, int position, long id) {

            String oldSpinOutputType ="";
            SpinOutputType = parent.getItemAtPosition(position).toString( );
            if (oldSpinOutputType!=SpinOutputType){
                oldSpinOutputType=SpinOutputType;
                resetValues();
            }

            ArrayList<String> listForAccountCardObjectSpinner = new ArrayList<>( );

            if (SpinOutputType.matches("ACCOUNT: DEBIT/CREDIT")) {

                creditSwitch.setClickable(true);
                listForAccountCardObjectSpinner = getDebitCreditAccountForDisplay( );


            } else if (SpinOutputType.matches("ACCOUNT: DEBIT")) {

                creditSwitch.setClickable(false);creditSwitch.setChecked(false);
                listForAccountCardObjectSpinner = getDebitAccountForDisplay( );


            } else if (SpinOutputType.matches("ACCOUNT: CREDIT")) {

                creditSwitch.setClickable(false);creditSwitch.setChecked(true);
                listForAccountCardObjectSpinner = getCreditAccountForDisplay( );

            }else{creditSwitch.setClickable(false);resetValues();}

            activeAccountCardObjectSpinner(listForAccountCardObjectSpinner);
        }
        @Override
        public void onNothingSelected(AdapterView<?> adapterView) {
        }
    }

    //******************************//
    private void initializeAccountCardObjectSpinner(ArrayList<String> selectionList) {
        ArrayAdapter<String> adapter = new ArrayAdapter<String>(this, android.R.layout.simple_spinner_item, selectionList);
        adapter.setDropDownViewResource(android.R.layout.simple_dropdown_item_1line);
        SelectShowedAccountTarget.setAdapter(adapter);
        SelectShowedAccountTarget.setOnItemSelectedListener(new SelectShowedAccountCardObjectSpinnerClass( ));
    }

    class SelectShowedAccountCardObjectSpinnerClass implements AdapterView.OnItemSelectedListener {
        public void onItemSelected(AdapterView<?> parent, View v, int position, long id) {

            spinOutputObject = parent.getItemAtPosition(position).toString( );

            if (!spinOutputObject.isEmpty()&& !spinOutputObject.matches("SELECT ACCOUNT") && !SpinOutputType.isEmpty() && !SpinOutputType.matches("SELECT ACCOUNT TYPE")) {

                if (SpinOutputType.matches("ACCOUNT: DEBIT/CREDIT")) {
                    showDebitCreditAccounts(Integer.parseInt(spinOutputObject));
                    whichActionIsGo("debit/credit");

                } else if (SpinOutputType.matches("ACCOUNT: DEBIT")) {
                    showDebitAccounts(Integer.parseInt(spinOutputObject));
                    whichActionIsGo("debit");

                } else if (SpinOutputType.matches("ACCOUNT: CREDIT")) {
                    System.out.println(spinOutputObject);
                    showCreditAccounts(Integer.parseInt(spinOutputObject));
                    whichActionIsGo("credit");
                }
            }
            else{
                whichActionIsGo(null);}
        }
        @Override
        public void onNothingSelected(AdapterView<?> adapterView) {

        }
    }
    //********************************************************************************************//
    //Account data output getters

    public void resetValues(){


        ShowAccountInfoName.setText("ACCOUNT NAME: ");
        ShowAccountInfoAccountNumber.setText("ACCOUNT NUMBER: ");
        ShowAccountInfoBalanceSaldo.setText("DEBIT/CREDIT:");
        receiverAccountNumber.getText().clear();
        amountToSend.getText().clear();
        whichActionIsGo(null);
    }

    public void showDebitCreditAccounts(Integer AccountNum){

        ShowAccountInfoName.setText("ACCOUNT NAME:    "+currentUserAccount.getDebitCreditAccount(AccountNum).getName());
        ShowAccountInfoAccountNumber.setText("DEBIT/CREDIT NUMBER:    "+currentUserAccount.getDebitCreditAccount(AccountNum).getAccountNumber());
        ShowAccountInfoBalanceSaldo.setText("DEBIT/CREDIT:   "+currentUserAccount.getDebitCreditAccount(AccountNum).getMoney());

    }
    public void showDebitAccounts(Integer AccountNum){

        ShowAccountInfoName.setText("ACCOUNT NAME:    "+currentUserAccount.getDebitAccount(AccountNum).getDebitAccountName());
        ShowAccountInfoAccountNumber.setText("ACCOUNT NUMBER:    "+currentUserAccount.getDebitAccount(AccountNum).getBankAccountNumber());
        ShowAccountInfoBalanceSaldo.setText("DISPOSABLE:    "+currentUserAccount.getDebitAccount(AccountNum).getBalance());

    }

    public void showCreditAccounts(Integer AccountNum){

        ShowAccountInfoName.setText("ACCOUNT NAME:    "+currentUserAccount.getCreditAccount(AccountNum).getCreditAccountName());
        ShowAccountInfoAccountNumber.setText("ACCOUNT NUMBER:    "+currentUserAccount.getCreditAccount(AccountNum).getCreditNumber());
        ShowAccountInfoBalanceSaldo.setText("DISPOSABLE:    "+currentUserAccount.getCreditAccount(AccountNum).getCreditSaldo());

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
        if(isActionCompleted==true){
            receiverAccountNumber.setHint("Transaction completed!");
            amountToSend.setHint("Transaction completed!");
        }
        else{
            receiverAccountNumber.setHint("Not enough money!");
            amountToSend.setHint("Not enough money!");
        }

    }

    private ArrayList<String> getDebitCreditAccountForDisplay() {
        ArrayList <String> tempList = new ArrayList<>(Arrays.asList("SELECT ACCOUNT"));
        if (currentUserAccount.getDebitCreditAccounts().size( ) > 0) {
            for( CreditAccount i: currentUserAccount.getDebitCreditAccounts()){
                tempList.add(i.getCreditNumber().toString());
                tempList.add(i.getBankAccountNumber().toString());
            }
        }
        return tempList;
    }

    private ArrayList<String> getDebitAccountForDisplay() {
        ArrayList <String> tempList = new ArrayList<>(Arrays.asList("SELECT ACCOUNT"));
        if (currentUserAccount.getDebitAccounts().size( ) > 0) {
            for( DebitAccount i: currentUserAccount.getDebitAccounts()){
                tempList.add(i.getBankAccountNumber().toString());
            }
        }
        return tempList;
    }

    private ArrayList<String> getCreditAccountForDisplay() {
        ArrayList <String> tempList = new ArrayList<>(Arrays.asList("SELECT ACCOUNT"));
        if (currentUserAccount.getCreditAccounts().size( ) > 0) {
            for( CreditAccount i: currentUserAccount.getCreditAccounts()){
                tempList.add(i.getCreditNumber().toString());
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
            SetSpinnerSelectNothing(SelectShowedAccountTarget);
            SelectShowedAccountTarget.setEnabled(false);
        }
        else{
            initializeAccountCardObjectSpinner(arrayList);
            SelectShowedAccountTarget.setEnabled(true);
        }

    }

    private Boolean canWeStart() {
        if (isDebitCreditTransactionOK || isDebitTransactionOK || iCreditTransactionOK) {
            return true;
        } else {
            return false;
        }
    }

    private void payDebitCreditAccount(String accountNumber, Integer receiverAccountNumberInt, Double amountToSendDouble) {

        Boolean isActionCompleted;

        creditSwitch.isChecked();
        if(isCreditAvailable==true){
            isActionCompleted = Bank.payDebitCreditAccountCredit((currentUserAccount.getUserInfo().getId()),Integer.parseInt(accountNumber),receiverAccountNumberInt,amountToSendDouble);
            if(isActionCompleted){currentUserAccount.createBankAccountTransactionRecord(1,"CREDIT PAY ACCOUNT ",currentUserAccount.getUserInfo().getId().toString(),"DEBIT/CREDIT",accountNumber,amountToSendDouble.toString());}
        }else{
            isActionCompleted = Bank.payDebitCreditAccountDebit((currentUserAccount.getUserInfo().getId()),Integer.parseInt(accountNumber),receiverAccountNumberInt,amountToSendDouble);
            if(isActionCompleted){currentUserAccount.createBankAccountTransactionRecord(2,"DEBIT PAY ACCOUNT ",currentUserAccount.getUserInfo().getId().toString(),"DEBIT/CREDIT",accountNumber,amountToSendDouble.toString());}
        }
        messageForUserTransactionEnd(isActionCompleted);
    }

    private void payDebitAccount(String accountNumber, Integer receiverAccountNumberInt, Double amountToSendDouble) {

        Boolean isActionCompleted;
        isActionCompleted = Bank.payDebitAccount((currentUserAccount.getUserInfo().getId()),Integer.parseInt(accountNumber),receiverAccountNumberInt,amountToSendDouble);
        if(isActionCompleted){currentUserAccount.createBankAccountTransactionRecord(3,"DEBIT PAY ACCOUNT ",currentUserAccount.getUserInfo().getId().toString(),"DEBIT",accountNumber,amountToSendDouble.toString());}
        messageForUserTransactionEnd(isActionCompleted);
    }

    private void payCreditAccount(String accountNumber, Integer receiverAccountNumberInt, Double amountToSendDouble) {

        Boolean isActionCompleted;
        isActionCompleted = Bank.payCreditAccount((currentUserAccount.getUserInfo().getId()),Integer.parseInt(accountNumber),receiverAccountNumberInt,amountToSendDouble);
        if(isActionCompleted){currentUserAccount.createBankAccountTransactionRecord(4,"CREDIT PAY ACCOUNT ",currentUserAccount.getUserInfo().getId().toString(),"CREDIT",accountNumber,amountToSendDouble.toString());}
        messageForUserTransactionEnd(isActionCompleted);
    }
}

