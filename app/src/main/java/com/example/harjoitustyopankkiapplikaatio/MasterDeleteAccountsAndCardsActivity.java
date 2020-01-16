package com.example.harjoitustyopankkiapplikaatio;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.Spinner;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.Arrays;

public class MasterDeleteAccountsAndCardsActivity extends AppCompatActivity {

    Spinner selectAccountOrCard;
    Spinner selectObject;
    TextView outputText;
    String selectedTypeAccountOrCard;
    String selectedAccountOrCard;
    private Account currentUserAccount = Bank.getLoggedAccount( );
    Boolean UserAccountDeletion;
    Boolean bankAccountDeletion;
    Boolean bankCardDeletion;
    Button buttonDeleteAccountOrCard;
    Button buttonContactInfo;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_master_delete_accounts_and_cards);
        outputText = findViewById(R.id.textViewOutput);
        selectAccountOrCard = findViewById(R.id.spinnerSelectAccountOrCard);
        selectObject = findViewById(R.id.spinnerSelectObjectToDelete);
        buttonDeleteAccountOrCard = (Button) findViewById(R.id.deleteAccountOrCard);
        buttonContactInfo = (Button) findViewById(R.id.returnFromMasterDeleteAccountsAndCardsActivity);

        activeSelectAccountOrCardSpinner( );

        buttonContactInfo.setOnClickListener(new View.OnClickListener( ) {
            @Override
            public void onClick(View v) {
                openMasterLoginActivity( );
            }

            public void openMasterLoginActivity() {
                Intent intent = new Intent(MasterDeleteAccountsAndCardsActivity.this, MasterLoginActivity.class);
                startActivity(intent);
            }
        });
        buttonDeleteAccountOrCard.setOnClickListener(new View.OnClickListener( ) {
            @Override
            public void onClick(View v) {makeDeletionAction( );activeSelectAccountOrCardSpinner( );
            }
        });

    }

    //**********************************************************************************************************
    //DELETION ACTIONS
    public void makeDeletionAction() {

        if (UserAccountDeletion == true) {
            deleteUserAccount( );

        } else if (bankAccountDeletion == true &&selectedAccountOrCard!=null) {
            deleteBankAccount( );
        } else if (bankCardDeletion == true &&selectedAccountOrCard!=null){
            deleteBankCard( );

        }
    }

    private void deleteBankCard() {
        Integer cardNumber = parseToNumbers(selectedAccountOrCard);
        currentUserAccount.removeSuperCard(cardNumber);
        currentUserAccount.removeDebitOrCreditCard(cardNumber);
        outputText.setText("Bank card deleted!");
    }

    private void deleteBankAccount() {

        Integer accountNumber = parseToNumbers(selectedAccountOrCard);
        currentUserAccount.removeAccount(accountNumber);

        outputText.setText("Bank account deleted!");
    }

    private void deleteUserAccount() {
        Bank.deleteAccount(currentUserAccount.getUserInfo( ).getId( ));
        outputText.setText("Account deleted!");
        Intent intent = new Intent(MasterDeleteAccountsAndCardsActivity.this, MasterLoginActivity.class);
        startActivity(intent);
    }

    //**********************************************************************************************************
    //Initialize spinners
    private void activeSelectAccountOrCardSpinner() {
        ArrayList<String> selectionList = new ArrayList<>(Arrays.asList("SELECT", "DELETE USER ACCOUNT", "SELECT BANK ACCOUNT TO DELETE", "SELECT CARD TO DELETE"));
        ArrayAdapter<String> adapter = new ArrayAdapter<String>(this, android.R.layout.simple_spinner_item, selectionList);
        adapter.setDropDownViewResource(android.R.layout.simple_dropdown_item_1line);
        selectAccountOrCard.setAdapter(adapter);
        selectAccountOrCard.setOnItemSelectedListener(new SelectAccountOrCardSpinnerClass( ));
        SetSpinnerSelectNothing(selectObject);
        UserAccountDeletion = false;
        bankAccountDeletion = false;
        bankCardDeletion = false;
    }

    private void activeAccountCardObjectSpinnerCard(ArrayList<String> listForAccountOrCardsSpinner) {
        if (listForAccountOrCardsSpinner.size( ) > 0) {
            selectObject.setEnabled(true);
            initializeAccountCardObjectSpinnerAccount(listForAccountOrCardsSpinner);
        }
    }

    private void initializeAccountCardObjectSpinnerAccount(ArrayList<String> selectionList) {
        ArrayAdapter<String> adapter = new ArrayAdapter<String>(this, android.R.layout.simple_spinner_item, selectionList);
        adapter.setDropDownViewResource(android.R.layout.simple_dropdown_item_1line);
        selectObject.setAdapter(adapter);
        selectObject.setOnItemSelectedListener(new SelectShowedAccountCardObjectSpinnerClassAccount( ));
    }

    private void SetSpinnerSelectNothing(Spinner s) {
        ArrayList<String> nothingList = new ArrayList(Arrays.asList(""));
        ArrayAdapter<String> adapter = new ArrayAdapter<String>(this, android.R.layout.simple_spinner_item, nothingList);
        adapter.setDropDownViewResource(android.R.layout.simple_dropdown_item_1line);
        s.setAdapter(adapter);
        s.setEnabled(false);
    }

    class SelectAccountOrCardSpinnerClass implements AdapterView.OnItemSelectedListener {
        public void onItemSelected(AdapterView<?> parent, View v, int position, long id) {

            String oldSpinOutputType = "";
            selectedTypeAccountOrCard = parent.getItemAtPosition(position).toString( );

            if (oldSpinOutputType != selectedTypeAccountOrCard) {
                oldSpinOutputType = selectedTypeAccountOrCard;
                SetSpinnerSelectNothing(selectObject);
            }
            ArrayList<String> listForAccountOrCardsSpinner = new ArrayList<>( );

            if (selectedTypeAccountOrCard.matches("DELETE USER ACCOUNT")) {
                outputText.setText("");
                UserAccountDeletion = true;
                bankAccountDeletion = false;
                bankCardDeletion = false;

            } else if (selectedTypeAccountOrCard.matches("SELECT BANK ACCOUNT TO DELETE")) {

                listForAccountOrCardsSpinner = getAccountsForDisplay( );
                activeAccountCardObjectSpinnerCard(listForAccountOrCardsSpinner);
                outputText.setText("");
                UserAccountDeletion = false;
                bankAccountDeletion = true;
                bankCardDeletion = false;


            } else if (selectedTypeAccountOrCard.matches("SELECT CARD TO DELETE")) {

                listForAccountOrCardsSpinner = getCardsForDisplay( );
                activeAccountCardObjectSpinnerCard(listForAccountOrCardsSpinner);
                outputText.setText("");
                UserAccountDeletion = false;
                bankAccountDeletion = false;
                bankCardDeletion = true;

            } else {
                SetSpinnerSelectNothing(selectObject);
            }
        }

        @Override
        public void onNothingSelected(AdapterView<?> adapterView) {
        }
    }

    class SelectShowedAccountCardObjectSpinnerClassAccount implements AdapterView.OnItemSelectedListener {
        public void onItemSelected(AdapterView<?> parent, View v, int position, long id) {
            selectedAccountOrCard = parent.getItemAtPosition(position).toString( );
        }

        @Override
        public void onNothingSelected(AdapterView<?> adapterView) {
        }
    }
    //**********************************************************************************************************

    private ArrayList<String> getAccountsForDisplay() {
        return currentUserAccount.getAllAccountsNumbers( );
    }

    private ArrayList<String> getCardsForDisplay() {
        return currentUserAccount.getAllCardNumbers( );
    }

    private Integer parseToNumbers(String selectedAccountOrCard) {
        String[] tempStringList = selectedAccountOrCard.split(":");
        if(tempStringList[1].contains("/")){
            String[] tempStringList2 = tempStringList[1].split("/");
            System.out.println(Integer.parseInt(tempStringList2[0].trim()));
            return (Integer.parseInt(tempStringList2[0].trim()));}
        else{
            System.out.println(Integer.parseInt(tempStringList[1].trim()));
            return (Integer.parseInt(tempStringList[1].trim()));
        }
    }
}