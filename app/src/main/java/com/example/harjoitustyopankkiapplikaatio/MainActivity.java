package com.example.harjoitustyopankkiapplikaatio;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import java.util.ArrayList;
import java.util.Arrays;


public class MainActivity extends AppCompatActivity {

    private EditText loginUsername;
    private EditText loginPassword;
    private Boolean isUsernameOk;
    private Boolean isPasswordOk;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        //*******************************************************************************
        // Creating TestUser for login


        Button buttonTestAccount = (Button) findViewById(R.id.buttonTestUser);
        buttonTestAccount.setOnClickListener(new View.OnClickListener( ){
            @Override
            public void onClick (View v){
                makeTestAccount();
            }
        });

        // create the xmlfile
        IOXML.getInstance().createRecordFile();

        //*******************************************************************************

        //
        loginUsername = (EditText) findViewById(R.id.editTextUsernameLogin);
        loginPassword = (EditText) findViewById(R.id.editTextPasswordLogin);

        Button buttonCreateAccount = (Button) findViewById(R.id.buttonCreateAccount);
        buttonCreateAccount.setOnClickListener(new View.OnClickListener( ){
            @Override
            public void onClick (View v){
                openCreateAccount();
            }
        });

        Button buttonLoginAccount = (Button) findViewById(R.id.buttonLoginAccount);
        buttonLoginAccount.setOnClickListener(new View.OnClickListener( ){
            @Override
            public void onClick (View v){
                openLoginAccount();
            }
        });
    }
    public void makeTestAccount() {
        ArrayList<String> lista = new ArrayList<String>(Arrays.asList("firstName", "surName", "01/01/2000", "hei@vaan.com", "050666", "streetName", "city", "00000", "user", "pass"));
        Bank.addAccountToList(lista);

        Bank.accounts.get(0).createDebitCreditAccount("Test1", 20000.0, 50000);
        Bank.accounts.get(0).createDebitAccount("test1", 10000.0);
        Bank.accounts.get(0).createCreditAccount(false, "test3", 10000);


        CreditAccount testing1 = Bank.accounts.get(0).createDebitCreditCard(Bank.accounts.get(0).getDebitCreditAccounts( ).get(0), "testCard1", 1000);
        Bank.accounts.get(0).getDebitCreditAccounts( ).clear( );
        Bank.accounts.get(0).getDebitCreditAccounts( ).add(testing1);

        DebitAccount testing2 = Bank.accounts.get(0).createDebitCard(Bank.accounts.get(0).getDebitAccounts( ).get(0), "testCard2", 1000);
        Bank.accounts.get(0).getDebitAccounts( ).clear( );
        Bank.accounts.get(0).getDebitAccounts( ).add(testing2);

        CreditAccount testing3 = Bank.accounts.get(0).createCreditCard(Bank.accounts.get(0).getCreditAccounts( ).get(0), "testCard3", 1000);
        Bank.accounts.get(0).getCreditAccounts( ).clear( );
        Bank.accounts.get(0).getCreditAccounts( ).add(testing3);


    }

    public void openCreateAccount() {
        Intent intent = new Intent(this, CreateAccountActivity.class);
        startActivity(intent);

    }
    public void openLoginAccount(){

        String checkUsername = loginUsername.getText().toString();
        String checkPassword = loginPassword.getText().toString();

        // Checking the username and password are ok
        if(Bank.checkIfMasterUser(checkUsername,checkPassword)){
                Intent intent = new Intent(this, MasterLoginActivity.class);
                startActivity(intent);
        }else{

            isUsernameOk = Bank.checkLoginUsername(checkUsername);
            isPasswordOk = Bank.checkLoginPassword(checkUsername,checkPassword);

            if (isUsernameOk == true&&isPasswordOk==true){
                Account activeUser = Bank.IdentifyAccount(checkUsername);
                // Active that account to be the right user.
                Bank.loggedIn(activeUser);
                Intent intent = new Intent(this, LoginActivity.class);
                startActivity(intent);
                }else{
                    loginUsername.getText( ).clear( );
                    loginUsername.setHintTextColor(Color.RED);
                    loginUsername.setHint("Username or password is wrong");
                    loginPassword.getText( ).clear( );
                    loginPassword.setHintTextColor(Color.RED);
                    loginPassword.setHint("Username or password is wrong");
                }

            }
        }
    }

