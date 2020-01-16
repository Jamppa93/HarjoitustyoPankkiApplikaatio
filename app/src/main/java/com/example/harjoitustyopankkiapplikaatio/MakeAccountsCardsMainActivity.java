package com.example.harjoitustyopankkiapplikaatio;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

public class MakeAccountsCardsMainActivity extends AppCompatActivity {



    private Account currentUserAccount = Bank.getLoggedAccount();

    @Override

    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_open_make_accounts_cards);

        Button  makeAccounts= (Button) findViewById(R.id.buttonMakeChanges);
        makeAccounts.setOnClickListener(new View.OnClickListener( ) {
            @Override
            public void onClick(View v) {
               openMakeAccountsActivity( );
            }

            public void openMakeAccountsActivity() {
                Intent intent = new Intent(MakeAccountsCardsMainActivity.this, MakeAccountActivity.class);
                startActivity(intent);
            }

        });

        Button  makeCards = (Button) findViewById(R.id.buttonMakeCards);
        makeCards.setOnClickListener(new View.OnClickListener( ) {
            @Override
            public void onClick(View v) {
                openMakeCardsActivity( );
            }

            public void openMakeCardsActivity () {
                Intent intent = new Intent(MakeAccountsCardsMainActivity.this, MakeCardActivity.class);
                startActivity(intent);
            }

        });

        Button makeChangesAccountOrCards = (Button) findViewById(R.id.buttonChangeAccountsOrCards);
        makeChangesAccountOrCards.setOnClickListener(new View.OnClickListener( ) {
            @Override
            public void onClick(View v) {
                openChangeAccountsOrCardsActivity( );
            }

            public void openChangeAccountsOrCardsActivity() {
                Intent intent = new Intent(MakeAccountsCardsMainActivity.this, MakeChangesAccountOrCards.class);
                startActivity(intent);
            }

        });

        //MakeAccountsCardsActivity: Return to LoginActivity
        Button buttonReturnLoginFromMakeAccounts = (Button) findViewById(R.id.buttonReturnFromMakeAccounts);
        buttonReturnLoginFromMakeAccounts.setOnClickListener(new View.OnClickListener( ) {
            @Override
            public void onClick(View v) {
                openLoginActivity( );
            }

            public void openLoginActivity() {
                Intent intent = new Intent(MakeAccountsCardsMainActivity.this, LoginActivity.class);
                startActivity(intent);
            }
        });
    }
}












