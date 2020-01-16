package com.example.harjoitustyopankkiapplikaatio;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.os.Environment;
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

public class DepositAndWithdrawActivity extends AppCompatActivity {

    //Getting the user
    private Account currentUserAccount = Bank.getLoggedAccount();

    //Declarations
    //********************************************************************************************//
    private String SpinOutputAccountOrCard;
    private String SpinOutputType;
    private String spinOutputObject;

    private Spinner SelectAccountOrCard;
    private Spinner SelectShowedType;
    private Spinner SelectShowedTarget;

    private TextView ShowInfoName;
    private TextView ShowInfoAccountOrCardNumber;
    private TextView ShowInfoBalanceSaldo;
    private TextView ShowInfoLimit;

    private Boolean isDebitCreditTransactionOK=false;
    private Boolean isDebitTransactionOK=false;
    private Boolean iCreditTransactionOK=false;
    private Boolean isCreditAvailable = false;
    private Boolean isAccountOperationsOk = null;


    private Switch creditSwitch;

    protected EditText amountOfMoney;

    //********************************************************************************************//

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_deposit_and_withdraw);
        //********************************************************************************************//
        //initializing switch
        creditSwitch = (Switch) findViewById(R.id.switchCreditOnOffDepositWithdraw);
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
        amountOfMoney = (EditText) findViewById(R.id.editTextAmountDepositWithdraw) ;


        //initializing textViews
        ShowInfoName =  (TextView) findViewById(R.id.textViewAccountOrCardNamDepositWithdraw);
        ShowInfoAccountOrCardNumber =  (TextView) findViewById(R.id.textViewAccountOrCardNumberDepositWithdraw);
        ShowInfoBalanceSaldo =  (TextView) findViewById(R.id.textViewDisposableDepositWithdraw);
        ShowInfoLimit = (TextView) findViewById(R.id.textViewPaymentLimitDepositWithdraw);

        //initializing spinners
        SelectAccountOrCard = findViewById(R.id.spinnerUseAccountOrCardPayDepositWithdraw);
        SelectShowedType = findViewById(R.id.spinnerSelectAccountCardTypePayDepositWithdraw);
        SelectShowedTarget = findViewById(R.id.spinnerSelectShowedCardObjectDepositWithdraw);

        //********************************************************************************************//
        initializeAccountOrCreditSpinner();


        //Make deposit action
        Button buttonMakeDepositAction = (Button) findViewById(R.id.buttonWithdraw);
        buttonMakeDepositAction.setOnClickListener(new View.OnClickListener( ) {
            @Override
            public void onClick(View v) {
                makeWithdrawAction( );
            }

            public void makeWithdrawAction() {
                //Checks that spinners gave selected to account
                if (!isAccountOperationsOk.equals(null)){
                    if(isAccountOperationsOk.equals(true)){
                        makeAccountWithdrawAction();
                    }
                    else{
                        makeCardWithdrawAction();
                    }
                }
                //make xml file
                IOXML.getInstance().saveToXML(currentUserAccount.getBankAccountTransactions());
                initializeAccountOrCreditSpinner();



            }
        });

        //Make deposit action
        Button buttonMakeWithdrawAction = (Button) findViewById(R.id.buttonDeposit);
        buttonMakeWithdrawAction.setOnClickListener(new View.OnClickListener( ) {
            @Override
            public void onClick(View v) {
                makeDepositAction( );
            }

            public void makeDepositAction() {
                //Checks that spinners is selected account or card
                if (!isAccountOperationsOk.equals(null)){
                    if(isAccountOperationsOk.equals(true)){
                        makeAccountDepositAction();
                    }
                    else{
                       makeCardDepositAction();
                    }
                }
                //make xml file
                IOXML.getInstance().saveToXML(currentUserAccount.getBankAccountTransactions());
                initializeAccountOrCreditSpinner();

            }
        });

        //DepositAndWithdrawActivity: Return to DepositAndWithdrawActivity
        Button buttonReturnTransMoneyFromDepositWithdraw = (Button) findViewById(R.id.buttonReturnFromDepositWithdrawToTransferMoney);
        buttonReturnTransMoneyFromDepositWithdraw.setOnClickListener(new View.OnClickListener( ) {
            @Override
            public void onClick(View v) {
                openTransferMoneyActivity( );
            }

            public void openTransferMoneyActivity() {
                Intent intent = new Intent(DepositAndWithdrawActivity.this, TransferMoneyActivity.class);
                startActivity(intent);
            }
        });
    }



    private void initializeAccountOrCreditSpinner() {
        ArrayList<String> selectionList = new ArrayList<>(Arrays.asList("SELECT ACCOUNT OR CARD","ACCOUNT","CARD"));
        ArrayAdapter<String> adapter = new ArrayAdapter<String>(this, android.R.layout.simple_spinner_item, selectionList);
        adapter.setDropDownViewResource(android.R.layout.simple_dropdown_item_1line);
        SelectAccountOrCard.setAdapter(adapter);
        SelectShowedType.setEnabled(true);
        SelectAccountOrCard.setOnItemSelectedListener(new SelectSelectAccountOrCardSpinnerClass( ));
    }

    class SelectSelectAccountOrCardSpinnerClass implements AdapterView.OnItemSelectedListener {
        public void onItemSelected(AdapterView<?> parent, View v, int position, long id) {

            String oldSpinOutputAccountOrCard ="";
            SpinOutputAccountOrCard = parent.getItemAtPosition(position).toString( );
            if (oldSpinOutputAccountOrCard!=SpinOutputType){
                oldSpinOutputAccountOrCard=SpinOutputType;
                resetAllValues();
            }

            if (SpinOutputAccountOrCard.matches("ACCOUNT")) {
                activeAccountTypeSpinner();
                isAccountOperationsOk = true;

            }else if (SpinOutputAccountOrCard.matches("CARD")) {
                activeCardTypeSpinner();
                isAccountOperationsOk = false;


            }else{creditSwitch.setClickable(false); isAccountOperationsOk = null;}//resetAllValues();
        }
        @Override
        public void onNothingSelected(AdapterView<?> adapterView) {
        }
    }

    //********************************************************************************************//

    // Spinner 2 method when 1 spinner points to cards,, activates spinner 2 and give data for spinner 3  as a list
    private void activeCardTypeSpinner() {
        ArrayList<String> selectionList = new ArrayList<>(Arrays.asList("SELECT CARD TYPE","CARD: DEBIT/CREDIT","CARD: DEBIT","CARD: CREDIT"));
        ArrayAdapter<String> adapter = new ArrayAdapter<String>(this, android.R.layout.simple_spinner_item, selectionList);
        adapter.setDropDownViewResource(android.R.layout.simple_dropdown_item_1line);
        SelectShowedType.setAdapter(adapter);
        SelectShowedType.setEnabled(true);
        SelectShowedType.setOnItemSelectedListener(new SelectShowedCardInfoSpinnerClass( ));}

    // Spinner 2  method when 1 spinner points to accounts, , activates spinner 2 and give data for spinner 3  as a list
    private void activeAccountTypeSpinner() {
        ArrayList<String> selectionList = new ArrayList<>(Arrays.asList("SELECT ACCOUNT TYPE","ACCOUNT: DEBIT/CREDIT","ACCOUNT: DEBIT","ACCOUNT: CREDIT"));
        ArrayAdapter<String> adapter = new ArrayAdapter<String>(this, android.R.layout.simple_spinner_item, selectionList);
        adapter.setDropDownViewResource(android.R.layout.simple_dropdown_item_1line);
        SelectShowedType.setAdapter(adapter);
        SelectShowedType.setEnabled(true);
        SelectShowedType.setOnItemSelectedListener(new SelectShowedAccountInfoSpinnerClass( ));
    }

    //********************************************************************************************//

    // Spinner class to show spinner 2 when  spinner 1 points to cards, activates spinner 3 and give data for spinner 3  as a list
    class SelectShowedCardInfoSpinnerClass implements AdapterView.OnItemSelectedListener {
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

            activeAccountCardObjectSpinnerCard(listForCardObjectSpinner);
        }
        @Override
        public void onNothingSelected(AdapterView<?> adapterView) {
        }
    }

    // Spinner class to show spinner 2 when  spinner 1 points to accounts, activates spinner 3 and give data for spinner 3  as a list
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

            activeAccountCardObjectSpinnerAccount(listForAccountCardObjectSpinner);
        }
        @Override
        public void onNothingSelected(AdapterView<?> adapterView) {
        }
    }

    //********************************************************************************************//

    // Spinner 3  method when 1 spinner points to accounts and spinner 2 has selected an account type
    private void initializeAccountCardObjectSpinnerAccount(ArrayList<String> selectionList) {
        ArrayAdapter<String> adapter = new ArrayAdapter<String>(this, android.R.layout.simple_spinner_item, selectionList);
        adapter.setDropDownViewResource(android.R.layout.simple_dropdown_item_1line);
        SelectShowedTarget.setAdapter(adapter);
        SelectShowedTarget.setOnItemSelectedListener(new SelectShowedAccountCardObjectSpinnerClassAccount( ));
    }

    // Spinner 3  method when 1 spinner points to cards and spinner 2 has selected an card type
    private void initializeAccountCardObjectSpinnerCard(ArrayList<String> selectionList) {
        ArrayAdapter<String> adapter = new ArrayAdapter<String>(this, android.R.layout.simple_spinner_item, selectionList);
        adapter.setDropDownViewResource(android.R.layout.simple_dropdown_item_1line);
        SelectShowedTarget.setAdapter(adapter);
        SelectShowedTarget.setOnItemSelectedListener(new SelectShowedAccountCardObjectSpinnerClassCard( ));
    }

    //********************************************************************************************//

    // these classes will tell which actions is ok to take between different card and account type by inputting the proper action to  whichActionIsGo() method

    class SelectShowedAccountCardObjectSpinnerClassCard implements AdapterView.OnItemSelectedListener {
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
        }@Override
        public void onNothingSelected(AdapterView<?> adapterView) {
        }

    }

    class SelectShowedAccountCardObjectSpinnerClassAccount implements AdapterView.OnItemSelectedListener {
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

    // Switch for deciding to  show  either accounts or or cards in 4th spinner
    private void activeAccountCardObjectSpinnerAccount( ArrayList<String> arrayList){

        if (arrayList.isEmpty( ) ) {
            SetSpinnerSelectNothing(SelectShowedTarget);
            SelectShowedTarget.setEnabled(false);
        }
        else{
            initializeAccountCardObjectSpinnerAccount(arrayList);
            SelectShowedTarget.setEnabled(true);
        }
    }

    private void activeAccountCardObjectSpinnerCard( ArrayList<String> arrayList){

        if (arrayList.isEmpty( ) ) {
            SetSpinnerSelectNothing(SelectShowedTarget);
            SelectShowedTarget.setEnabled(false);
        }
        else{
            initializeAccountCardObjectSpinnerCard(arrayList);
            SelectShowedTarget.setEnabled(true);
        }
    }
    //********************************************************************************************//
    //HANDLE WHAT TO DO (LOGIC)

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

    public void makeCardWithdrawAction() {

        String amountToSendDoubleText =amountOfMoney.getText().toString();
        creditSwitch.isChecked();

        if(isDebitCreditTransactionOK==true &&inputsNotEmpty(amountToSendDoubleText)==true){

            withdrawDebitCreditCard(spinOutputObject,Double.parseDouble(amountToSendDoubleText));

        }
        else if(isDebitTransactionOK==true&&inputsNotEmpty(amountToSendDoubleText)==true){

            withdrawDebitCard(spinOutputObject,Double.parseDouble(amountToSendDoubleText));

        }
        else if(iCreditTransactionOK==true&&inputsNotEmpty(amountToSendDoubleText)==true &&isCreditAvailable==true){

            withdrawCreditCard(spinOutputObject,Double.parseDouble(amountToSendDoubleText));

        }
        resetValues();
    }

    public void makeAccountWithdrawAction() {

        String amountToSendDoubleText =amountOfMoney.getText().toString();
        creditSwitch.isChecked();

        if(isDebitCreditTransactionOK==true &&inputsNotEmpty(amountToSendDoubleText)==true){

            withdrawDebitCreditAccount(spinOutputObject,Double.parseDouble(amountToSendDoubleText));

        }
        else if(isDebitTransactionOK==true&&inputsNotEmpty(amountToSendDoubleText)==true){

            withdrawDebitAccount(spinOutputObject,Double.parseDouble(amountToSendDoubleText));

        }
        else if(iCreditTransactionOK==true&&inputsNotEmpty(amountToSendDoubleText)==true &&isCreditAvailable==true){

            withdrawCreditAccount(spinOutputObject,Double.parseDouble(amountToSendDoubleText));

        }
        resetValues();
    }

    private void makeCardDepositAction() {

        String amountToSendDoubleText =amountOfMoney.getText().toString();
        creditSwitch.isChecked();

        if(isDebitCreditTransactionOK==true &&inputsNotEmpty(amountToSendDoubleText)==true){

            depositDebitCreditCard(spinOutputObject,Double.parseDouble(amountToSendDoubleText));

        }
        else if(isDebitTransactionOK==true&&inputsNotEmpty(amountToSendDoubleText)==true){

            depositDebitCard(spinOutputObject,Double.parseDouble(amountToSendDoubleText));

        }
        else if(iCreditTransactionOK==true&&inputsNotEmpty(amountToSendDoubleText)==true &&isCreditAvailable==true){

            depositCreditCard(spinOutputObject,Double.parseDouble(amountToSendDoubleText));

        }
        resetValues();

    }

    private void makeAccountDepositAction() {
        String amountToSendDoubleText =amountOfMoney.getText().toString();
        creditSwitch.isChecked();

        if(isDebitCreditTransactionOK==true &&inputsNotEmpty(amountToSendDoubleText)==true){

            depositDebitCreditAccount(spinOutputObject,Double.parseDouble(amountToSendDoubleText));
        }
        else if(isDebitTransactionOK==true&&inputsNotEmpty(amountToSendDoubleText)==true){

            depositDebitAccount(spinOutputObject,Double.parseDouble(amountToSendDoubleText));
        }
        else if(iCreditTransactionOK==true&&inputsNotEmpty(amountToSendDoubleText)==true &&isCreditAvailable==true){

            depositCreditAccount(spinOutputObject,Double.parseDouble(amountToSendDoubleText));
        }
        resetValues();

    }


    ////////////////////////////////////////////////////////////////////////////////////////
    //If the previous spinner is not pointing to any action  or band account or card.
    // it is used to make sure that action methods cannot get any wrong input.
    private void resetAllValues() {

        SetSpinnerSelectNothing(SelectShowedType);
        SetSpinnerSelectNothing(SelectShowedTarget);
        SelectShowedType.setEnabled(false);
        SelectShowedTarget.setEnabled(false);

        ShowInfoName.setText("ACCOUNT/CARD NAME: ");
        ShowInfoAccountOrCardNumber.setText("ACCOUNT/CARD NUMBER: ");
        ShowInfoBalanceSaldo.setText("DISPOSABLE: ");
        ShowInfoLimit.setText("PAYMENT LIMIT:");
        amountOfMoney.getText().clear();

    }

    public void resetValues(){


        ShowInfoName.setText("ACCOUNT/CARD NAME: ");
        ShowInfoAccountOrCardNumber.setText("ACCOUNT/CARD NUMBER: ");
        ShowInfoBalanceSaldo.setText("DISPOSABLE: ");
        ShowInfoLimit.setText("PAYMENT LIMIT:");
        amountOfMoney.getText().clear();
        whichActionIsGo(null);
    }

    private void SetSpinnerSelectNothing(Spinner s) {
        ArrayList<String> nothingList = new ArrayList(Arrays.asList(""));
        ArrayAdapter<String> adapter = new ArrayAdapter<String>(this, android.R.layout.simple_spinner_item, nothingList);
        adapter.setDropDownViewResource(android.R.layout.simple_dropdown_item_1line);
        s.setAdapter(adapter);
    }

    private boolean inputsNotEmpty(String s1) {

        if(!s1.isEmpty()){
            return true;
        }
        return false;}
    ////////////////////////////////////////////////////////////////////////////////////////

    // Edittext hints after the actions. The text is showed based on what is the boolean value parameter value
    private void messageForUserTransactionEndAccountWithdraw(Boolean isActionCompleted) {
        if(isActionCompleted==true){
            amountOfMoney.setHint("Money withdrawn!");
        }
        else{
            amountOfMoney.setHint("Not enough money!");
        }

    }

    private void messageForUserTransactionEndCardWithdraw(Boolean isActionCompleted) {

        if(isActionCompleted==null) {
            amountOfMoney.setHint("Card limit excess!");

        }else{
            if(isActionCompleted==true){
                amountOfMoney.setHint("Money withdrawn!");
            }
            else if (isActionCompleted == false){
                amountOfMoney.setHint("Not enough money!");
            }
        }

    }

    private void messageForUserTransactionEndAccountDeposit(Boolean isActionCompleted) {
        if(isActionCompleted==true){
            amountOfMoney.setHint("Money added!");
        }
        else{
            amountOfMoney.setHint("Not enough money!");
        }

    }

    private void messageForUserTransactionEndCardDeposit(Boolean isActionCompleted) {

        if(isActionCompleted==null) {
            amountOfMoney.setHint("Card limit excess!");

        }else{
            if(isActionCompleted==true){
                amountOfMoney.setHint("Money added!");
            }
            else if (isActionCompleted == false){
                amountOfMoney.setHint("Not enough money!");
            }
        }

    }



    //GET ACCOUNTS AND CARDS: return a string list of cards of accounts
    // These methods query the data which is showed on show-methods .They are accessed in spinner classes.

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



    //SHOW ACCOUNTS AND CARDS
    // These are accessed in spinner classes

    public void showDebitCreditAccounts(Integer AccountNum){

        ShowInfoName.setText("ACCOUNT NAME:    "+currentUserAccount.getDebitCreditAccount(AccountNum).getName());
        ShowInfoAccountOrCardNumber.setText("DEBIT/CREDIT NUMBER:    "+currentUserAccount.getDebitCreditAccount(AccountNum).getAccountNumber());
        ShowInfoBalanceSaldo.setText("DEBIT/CREDIT:   "+currentUserAccount.getDebitCreditAccount(AccountNum).getMoney());
        ShowInfoLimit.setText("");
    }

    public void showDebitAccounts(Integer AccountNum){

        ShowInfoName.setText("ACCOUNT NAME:    "+currentUserAccount.getDebitAccount(AccountNum).getDebitAccountName());
        ShowInfoAccountOrCardNumber.setText("ACCOUNT NUMBER:    "+currentUserAccount.getDebitAccount(AccountNum).getBankAccountNumber());
        ShowInfoBalanceSaldo.setText("DISPOSABLE:    "+currentUserAccount.getDebitAccount(AccountNum).getBalance());
        ShowInfoLimit.setText("");

    }

    public void showCreditAccounts(Integer AccountNum){

        ShowInfoName.setText("ACCOUNT NAME:    "+currentUserAccount.getCreditAccount(AccountNum).getCreditAccountName());
        ShowInfoAccountOrCardNumber.setText("ACCOUNT NUMBER:    "+currentUserAccount.getCreditAccount(AccountNum).getCreditNumber());
        ShowInfoBalanceSaldo.setText("DISPOSABLE:    "+currentUserAccount.getCreditAccount(AccountNum).getCreditSaldo());
        ShowInfoLimit.setText("");

    }

    public void showDebitCreditCards(Integer cardNum){

        ShowInfoName.setText("CARD NAME:    "+currentUserAccount.getDebitCreditCard(cardNum).getCreditCardHolder().getcreditCardNumber());
        ShowInfoAccountOrCardNumber.setText("DEBIT/CREDIT NUMBER:    "+currentUserAccount.getDebitCreditCard(cardNum).getDebitCardHolder().getDebitCardNumber()+"/"+currentUserAccount.getDebitCreditCard(cardNum).getCreditCardHolder().getcreditCardNumber());
        ShowInfoBalanceSaldo.setText("DEBIT/CREDIT:   "+currentUserAccount.getDebitCreditCard(cardNum).getDebitCardHolder().getDebitBalance()+"/"+currentUserAccount.getDebitCreditCard(cardNum).getCreditCardHolder().getCreditSaldo());
        ShowInfoLimit.setText("PAYMENT LIMIT:    "+currentUserAccount.getDebitCreditCard(cardNum).getDebitCardHolder().getPaymentLimitDebit()+"/"+currentUserAccount.getDebitCreditCard(cardNum).getCreditCardHolder().getPaymentLimitCredit());
    }

    public void showDebitCards(Integer cardNum){

        ShowInfoName.setText("CARD NAME:    "+currentUserAccount.getDebitCard(cardNum).getdebitCardName());
        ShowInfoAccountOrCardNumber.setText("CARD NUMBER:    "+currentUserAccount.getDebitCard(cardNum).getDebitCardNumber());
        ShowInfoBalanceSaldo.setText("DISPOSABLE:    "+currentUserAccount.getDebitCard(cardNum).getDebitBalance());
        ShowInfoLimit.setText("PAYMENT LIMIT:    "+currentUserAccount.getDebitCard(cardNum).getPaymentLimitDebit());
    }

    public void showCreditCards(Integer cardNum){

        ShowInfoName.setText("CARD NAME:    "+currentUserAccount.getCreditCard(cardNum).getCreditCardName());
        ShowInfoAccountOrCardNumber.setText("CARD NUMBER:    "+currentUserAccount.getCreditCard(cardNum).getcreditCardNumber());
        ShowInfoBalanceSaldo.setText("DISPOSABLE:    "+currentUserAccount.getCreditCard(cardNum).getCreditSaldo());
        ShowInfoLimit.setText("PAYMENT LIMIT:    "+currentUserAccount.getCreditCard(cardNum).getPaymentLimitCredit());
    }




    //TRANSACTIONS

    //WITHDRAW
    // these are accessed in make account withdraw action. Inputs are account number and amount of money
    private void withdrawDebitCreditAccount(String accountNumber, Double amountToSendDouble) {

        Boolean isActionCompleted;

        creditSwitch.isChecked();
        if(isCreditAvailable==true){
            isActionCompleted = Bank.payDebitCreditAccountCredit((currentUserAccount.getUserInfo().getId()),Integer.parseInt(accountNumber),0,amountToSendDouble);
            if(isActionCompleted){currentUserAccount.createBankAccountTransactionRecord(13,"WITHDRAW CREDIT ACCOUNT ",currentUserAccount.getUserInfo().getId().toString(),"DEBIT/CREDIT",accountNumber,amountToSendDouble.toString());}
        }else{
            isActionCompleted = Bank.payDebitCreditAccountDebit((currentUserAccount.getUserInfo().getId()),Integer.parseInt(accountNumber),0,amountToSendDouble);
            if(isActionCompleted){currentUserAccount.createBankAccountTransactionRecord(9,"WITHDRAW DEBIT ACCOUNT ",currentUserAccount.getUserInfo().getId().toString(),"DEBIT/CREDIT",accountNumber,amountToSendDouble.toString());}
        }
        messageForUserTransactionEndAccountWithdraw(isActionCompleted);
    }

    private void withdrawDebitAccount(String accountNumber, Double amountToSendDouble) {

        Boolean isActionCompleted;
        isActionCompleted = Bank.payDebitAccount((currentUserAccount.getUserInfo().getId()),Integer.parseInt(accountNumber),0,amountToSendDouble);
        if(isActionCompleted){currentUserAccount.createBankAccountTransactionRecord(10,"WITHDRAW DEBIT ACCOUNT ",currentUserAccount.getUserInfo().getId().toString(),"DEBIT",accountNumber,amountToSendDouble.toString());}
        messageForUserTransactionEndAccountWithdraw(isActionCompleted);
    }

    private void withdrawCreditAccount(String accountNumber,  Double amountToSendDouble) {


        Boolean isActionCompleted = Bank.payCreditAccount((currentUserAccount.getUserInfo().getId()),Integer.parseInt(accountNumber),0,amountToSendDouble);
        if(isActionCompleted){currentUserAccount.createBankAccountTransactionRecord(11,"WITHDRAW CREDIT ACCOUNT ",currentUserAccount.getUserInfo().getId().toString(),"CREDIT",accountNumber,amountToSendDouble.toString());}
        messageForUserTransactionEndAccountWithdraw(isActionCompleted);
    }

// these are accessed in make card withdraw action. Inputs are card number and amount of money

    private void withdrawDebitCreditCard(String spinOutputObject, Double amountToSendDouble) {

        Boolean isActionCompleted;
        Integer cardNumber = Integer.parseInt(spinOutputObject);


        creditSwitch.isChecked();
        if(isCreditAvailable==true) {
            isActionCompleted = Bank.payDebitCreditCard((currentUserAccount.getUserInfo( ).getId( )), cardNumber, 0, amountToSendDouble, true);
            if (isActionCompleted != null) {
                Integer cardsAccountNumber = Bank.getCreditDebitCardCreditAccountNumber(cardNumber);
                if (isActionCompleted) {
                    currentUserAccount.createBankAccountTransactionRecord
                            (12, " WITHDRAW CREDIT CARD", currentUserAccount.getUserInfo( ).getId( ).toString( ), "DEBIT/CREDIT", cardsAccountNumber.toString( ), "SUPER CARD", spinOutputObject,amountToSendDouble.toString());
                }
            }
        }else{
            isActionCompleted = Bank.payDebitCreditCard((currentUserAccount.getUserInfo().getId()),cardNumber,0,amountToSendDouble,false);
            if(isActionCompleted != null) {
                Integer cardsAccountNumber = Bank.getCreditDebitCardDebitAccountNumber(cardNumber);

                if (isActionCompleted && !isActionCompleted.equals(null)) {
                    currentUserAccount.createBankAccountTransactionRecord
                            (13, " WITHDRAW CREDIT CARD", currentUserAccount.getUserInfo( ).getId( ).toString( ), "DEBIT/CREDIT", cardsAccountNumber.toString( ), "SUPER CARD", spinOutputObject,amountToSendDouble.toString());
                }
            }
        }
        messageForUserTransactionEndCardWithdraw(isActionCompleted);
    }

    private void withdrawDebitCard(String spinOutputObject,  Double amountToSendDouble) {

        Integer cardNumber = Integer.parseInt(spinOutputObject);
        Boolean isActionCompleted = Bank.payDebitCard((currentUserAccount.getUserInfo().getId()),Integer.parseInt(spinOutputObject),0,amountToSendDouble);
        if(isActionCompleted != null) {
            Integer cardsAccountNumber = Bank.getDebitCardAccountNumber(cardNumber);
            if (isActionCompleted) {
                currentUserAccount.createBankAccountTransactionRecord
                        (14, "WITHDRAW DEBIT CARD", currentUserAccount.getUserInfo( ).getId( ).toString( ), "DEBIT", cardsAccountNumber.toString( ), "DEBIT CARD", spinOutputObject,amountToSendDouble.toString());
            }
        }
        messageForUserTransactionEndCardWithdraw(isActionCompleted);
    }

    private void withdrawCreditCard(String spinOutputObject, Double amountToSendDouble) {

        Integer cardNumber = Integer.parseInt(spinOutputObject);
        Boolean isActionCompleted = Bank.payCreditCard((currentUserAccount.getUserInfo( ).getId( )), Integer.parseInt(spinOutputObject), 0, amountToSendDouble);
        if(isActionCompleted != null) {
            Integer cardsAccountNumber = Bank.getCreditCardAccountNumber(cardNumber);

            if (isActionCompleted) {
                currentUserAccount.createBankAccountTransactionRecord
                        (15, "WITHDRAW CREDIT CARD", currentUserAccount.getUserInfo( ).getId( ).toString( ), "CREDIT", cardsAccountNumber.toString( ), "CREDIT CARD", spinOutputObject,amountToSendDouble.toString());
            }
        }
        messageForUserTransactionEndCardWithdraw(isActionCompleted);

    }

    //DEPOSIT

    // these are accessed in  make account deposit action.Inputs are account number and amount of money

    private void depositDebitCreditAccount(String accountNumber, Double amountToSendDouble) {

        Boolean isActionCompleted;
        Double amountToSendDoubleInverse = -amountToSendDouble;

        creditSwitch.isChecked();
        if(isCreditAvailable==true){
            isActionCompleted = Bank.payDebitCreditAccountCredit((currentUserAccount.getUserInfo().getId()),Integer.parseInt(accountNumber),0,amountToSendDoubleInverse);
            if(isActionCompleted){currentUserAccount.createBankAccountTransactionRecord(16,"DEPOSIT CREDIT ACCOUNT ",currentUserAccount.getUserInfo().getId().toString(),"DEBIT/CREDIT",accountNumber,amountToSendDouble.toString());}

        }else{
            isActionCompleted = Bank.payDebitCreditAccountDebit((currentUserAccount.getUserInfo().getId()),Integer.parseInt(accountNumber),0,amountToSendDoubleInverse);
            if(isActionCompleted){currentUserAccount.createBankAccountTransactionRecord(17,"DEPOSIT DEBIT ACCOUNT ",currentUserAccount.getUserInfo().getId().toString(),"DEBIT/CREDIT",accountNumber,amountToSendDouble.toString());}
        }
        messageForUserTransactionEndAccountDeposit(isActionCompleted);
    }

    private void depositDebitAccount(String accountNumber, Double amountToSendDouble) {

        Boolean isActionCompleted;
        Double amountToSendDoubleInverse = -amountToSendDouble;

        isActionCompleted = Bank.payDebitAccount((currentUserAccount.getUserInfo().getId()),Integer.parseInt(accountNumber),0,amountToSendDoubleInverse);
        if(isActionCompleted){currentUserAccount.createBankAccountTransactionRecord(18,"DEPOSIT DEBIT ACCOUNT ",currentUserAccount.getUserInfo().getId().toString(),"DEBIT",accountNumber,amountToSendDouble.toString());}
        messageForUserTransactionEndAccountDeposit(isActionCompleted);
    }

    private void depositCreditAccount(String accountNumber, Double amountToSendDouble) {

        Boolean isActionCompleted;
        Double amountToSendDoubleInverse = -amountToSendDouble;

        isActionCompleted = Bank.payCreditAccount((currentUserAccount.getUserInfo().getId()),Integer.parseInt(accountNumber),0,amountToSendDoubleInverse);
        if(isActionCompleted){currentUserAccount.createBankAccountTransactionRecord(19,"DEPOSIT CREDIT ACCOUNT ",currentUserAccount.getUserInfo().getId().toString(),"CREDIT",accountNumber,amountToSendDouble.toString());}
        messageForUserTransactionEndAccountDeposit(isActionCompleted);
    }

// these are accessed in make card deposit action. Inputs are card number and amount of money

    private void depositDebitCreditCard(String spinOutputObject, Double amountToSendDouble) {

        Boolean isActionCompleted;
        Integer cardNumber = Integer.parseInt(spinOutputObject);
        Double amountToSendDoubleInverse = -amountToSendDouble;


        creditSwitch.isChecked();
        if(isCreditAvailable==true){
            isActionCompleted = Bank.payDebitCreditCard((currentUserAccount.getUserInfo().getId()),cardNumber,0,amountToSendDoubleInverse,true);
            if(isActionCompleted != null) {
                Integer cardsAccountNumber = Bank.getCreditDebitCardCreditAccountNumber(cardNumber);
                if (isActionCompleted) {
                    currentUserAccount.createBankAccountTransactionRecord
                            (20, "DEPOSIT CREDIT CARD", currentUserAccount.getUserInfo( ).getId( ).toString( ), "DEBIT/CREDIT", cardsAccountNumber.toString( ), "SUPER CARD", cardNumber.toString(),amountToSendDouble.toString());
                    IOXML.getInstance().saveToXML(currentUserAccount.getBankAccountTransactions());
                }
            }
        }else{
            isActionCompleted = Bank.payDebitCreditCard((currentUserAccount.getUserInfo().getId()),cardNumber,0,amountToSendDoubleInverse,false);
            if(isActionCompleted != null){
                Integer cardsAccountNumber = Bank.getCreditDebitCardDebitAccountNumber(cardNumber);
                if(isActionCompleted){currentUserAccount.createBankAccountTransactionRecord
                        (21,"DEPOSIT DEBIT CARD",currentUserAccount.getUserInfo().getId().toString(),"DEBIT/CREDIT",cardsAccountNumber.toString(),"SUPER CARD",cardNumber.toString(),amountToSendDouble.toString());
                }
            }
        }
        messageForUserTransactionEndCardDeposit(isActionCompleted);
    }

    private void depositDebitCard(String spinOutputObject, Double amountToSendDouble) {

        Integer cardNumber = Integer.parseInt(spinOutputObject);
        Double amountToSendDoubleInverse = -amountToSendDouble;

        Boolean isActionCompleted = Bank.payDebitCard((currentUserAccount.getUserInfo().getId()),Integer.parseInt(spinOutputObject),0,amountToSendDoubleInverse);
        if(isActionCompleted != null){
            Integer cardsAccountNumber = Bank.getDebitCardAccountNumber(cardNumber);
            if(isActionCompleted){currentUserAccount.createBankAccountTransactionRecord
                    (22,"DEBOSIT DEBIT CARD",currentUserAccount.getUserInfo().getId().toString(),"DEBIT",cardsAccountNumber.toString(),"DEBIT CARD",spinOutputObject,amountToSendDouble.toString());
            }
        }
        messageForUserTransactionEndCardDeposit(isActionCompleted);
    }

    private void depositCreditCard(String spinOutputObject, Double amountToSendDouble) {

        Integer cardNumber = Integer.parseInt(spinOutputObject);
        Double amountToSendDoubleInverse = -amountToSendDouble;

        Boolean isActionCompleted = Bank.payCreditCard((currentUserAccount.getUserInfo( ).getId( )), Integer.parseInt(spinOutputObject), 0, amountToSendDoubleInverse);
        if(isActionCompleted != null){
            Integer cardsAccountNumber = Bank.getCreditCardAccountNumber(cardNumber);
            if(isActionCompleted){currentUserAccount.createBankAccountTransactionRecord
                    (23,"DEPOSIT CREDIT CARD",currentUserAccount.getUserInfo().getId().toString(),"CREDIT",cardsAccountNumber.toString(),"CREDIT CARD",spinOutputObject,amountToSendDouble.toString());
            }
        }
        messageForUserTransactionEndCardDeposit(isActionCompleted);


    }

}
