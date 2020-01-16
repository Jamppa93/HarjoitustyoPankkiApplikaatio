package com.example.harjoitustyopankkiapplikaatio;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.Spinner;

import java.util.ArrayList;
import java.util.Arrays;

public class MasterLoginActivity extends AppCompatActivity {

    String SelectedAccounts;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        String selectedAccount;
        setContentView(R.layout.activity_master_login);
        initializeSpinner();
        initializeButtons();

    }

    private void initializeButtons() {



        Button buttonCreateAccount = (Button) findViewById(R.id.buttonLogOut);
        buttonCreateAccount.setOnClickListener(new View.OnClickListener( ) {
            @Override
            public void onClick(View v) {
                openMainActivity( );
            }

        });

        Button ButtonCheckUserInfo = (Button) findViewById(R.id.buttonCheckUserInfo);
        ButtonCheckUserInfo.setOnClickListener(new View.OnClickListener( ) {
            @Override
            public void onClick(View v) {
                if(!SelectedAccounts.equals("SELECT ACCOUNT")&&!SelectedAccounts.equals("THERE'S NO ACCOUNTS")){openCheckUserInfo( SelectedAccounts);}
            }




        });


        Button ButtonDeleteAccountsAndCards = (Button) findViewById(R.id.buttonDelete);
        ButtonDeleteAccountsAndCards.setOnClickListener(new View.OnClickListener( ) {
            @Override
            public void onClick(View v) {
                if(!SelectedAccounts.equals("SELECT ACCOUNT")&&!SelectedAccounts.equals("THERE'S NO ACCOUNTS")){openDeleteAccountsAndCards( SelectedAccounts);}
            }




        });

        Button buttonCreateAccountsAndCards = (Button) findViewById(R.id.buttonCreate);
        buttonCreateAccountsAndCards.setOnClickListener(new View.OnClickListener( ) {
            @Override
            public void onClick(View v) {
                if(!SelectedAccounts.equals("SELECT ACCOUNT")&&!SelectedAccounts.equals("THERE'S NO ACCOUNTS")){ openCreateAccountsAndCards(SelectedAccounts );}
            }


        });
    }

    public void initializeSpinner() {

        ArrayList<String> selectionList = new ArrayList<>(Arrays.asList("SELECT ACCOUNT"));
        if (Bank.getAccounts( ).size( ) > 0) {
            for (Account a : Bank.getAccounts( )) {
                selectionList.add(a.getUserInfo( ).getUserName( ));
            }
        } else {
            selectionList.set(0, "THERE'S NO ACCOUNTS");
        }

        Spinner selectAccount = findViewById(R.id.spinnerSelectAccount);
        ArrayAdapter<String> adapter = new ArrayAdapter<String>(this, android.R.layout.simple_spinner_item, selectionList);
        adapter.setDropDownViewResource(android.R.layout.simple_dropdown_item_1line);
        selectAccount.setAdapter(adapter);
        selectAccount.setOnItemSelectedListener(new SelectAccountSpinnerClass( ));
    }


    class SelectAccountSpinnerClass implements AdapterView.OnItemSelectedListener {
        public void onItemSelected(AdapterView<?> parent, View v, int position, long id) {
            SelectedAccounts= parent.getItemAtPosition(position).toString( );

        }
        @Override
        public void onNothingSelected(AdapterView<?> adapterView) {
        }
    }


    public void openMainActivity() {
        Bank.loggedOut( );
        Intent intent = new Intent(MasterLoginActivity.this, MainActivity.class);
        startActivity(intent);
    }

    public void openCreateAccountsAndCards(String s) {


        for (Account a : Bank.getAccounts( )) {
            if (a.getUserInfo().getUserName() == s){
                Bank.loggedIn(a);
            }
            }


        Intent intent = new Intent(MasterLoginActivity.this, MasterCreateAccountsAndCardsActivity.class);
        startActivity(intent);
    }
    public void openDeleteAccountsAndCards(String s) {

        for (Account a : Bank.getAccounts( )) {
            if (a.getUserInfo().getUserName() == s){
                Bank.loggedIn(a);
            }
        }
        Intent intent = new Intent(MasterLoginActivity.this, MasterDeleteAccountsAndCardsActivity.class);
        startActivity(intent);
    }

    public void openCheckUserInfo(String s) {


        for (Account a : Bank.getAccounts( )) {
            if (a.getUserInfo().getUserName() == s){
                Bank.loggedIn(a);
            }
        }


        Intent intent = new Intent(MasterLoginActivity.this, MasterCheckAllUserInfoActivity.class);
        startActivity(intent);
    }

}


