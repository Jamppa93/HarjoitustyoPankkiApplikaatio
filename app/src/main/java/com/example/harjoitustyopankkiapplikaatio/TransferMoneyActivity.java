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

public class TransferMoneyActivity extends AppCompatActivity {

    //Getting the user
    private Account currentUserAccount = Bank.getLoggedAccount( );

    //Declarations
    //*********************************************************//
    private TextView ShowAnythingOutput;
    ;


    //*********************************************************//

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_transfer_money);

        //  initializing TextView
        ShowAnythingOutput = (TextView) findViewById(R.id.textViewShowAnythingTransactions);
        ShowAnythingOutput.setMovementMethod(new ScrollingMovementMethod( ));

        // Declaring and initializing Spinner
        displayTransactions( );

        //TransferMoneyActivity:  to PayAccount
        Button buttonGoToPayAccount = (Button) findViewById(R.id.buttonPayAccount);
        buttonGoToPayAccount.setOnClickListener(new View.OnClickListener( ) {
            @Override
            public void onClick(View v) {
                openLoginActivity( );
            }

            private void openLoginActivity() {
                Intent intent = new Intent(TransferMoneyActivity.this, PayAccountActivity.class);
                startActivity(intent);
            }
        });

        //TransferMoneyActivity: to PayCard
        Button buttonGoToPayCard = (Button) findViewById(R.id.buttonPayCard);
        buttonGoToPayCard.setOnClickListener(new View.OnClickListener( ) {
            @Override
            public void onClick(View v) {
                openLoginActivity( );
            }

            private void openLoginActivity() {
                Intent intent = new Intent(TransferMoneyActivity.this, PayCardActivity.class);
                startActivity(intent);
            }
        });

        //TransferMoneyActivity: to DepositWithdraw
        Button buttonGoToDepositWithdraw = (Button) findViewById(R.id.buttonDepositWithdraw);
        buttonGoToDepositWithdraw.setOnClickListener(new View.OnClickListener( ) {
            @Override
            public void onClick(View v) {
                openLoginActivity( );
            }

            private void openLoginActivity() {
                Intent intent = new Intent(TransferMoneyActivity.this, DepositAndWithdrawActivity.class);
                startActivity(intent);
            }
        });

        //TransferMoneyActivity: to LoginActivity
        Button buttonReturnLoginFromTranserMoney = (Button) findViewById(R.id.buttonReturnFromTransferMoneyToLogin);
        buttonReturnLoginFromTranserMoney.setOnClickListener(new View.OnClickListener( ) {
            @Override
            public void onClick(View v) {
                openLoginActivity( );
            }

            private void openLoginActivity() {
                Intent intent = new Intent(TransferMoneyActivity.this, LoginActivity.class);
                startActivity(intent);
            }
        });
    }


    // prints the bank transactions which are in the bankTransactionsList;
    public void displayTransactions() {

        String finalMessage = "";
        Boolean isEmpty = true;

        if (currentUserAccount.getBankAccountTransactions( ).size( ) > 0) {

            for (BankAccountTransactions record : currentUserAccount.getBankAccountTransactions( )) {
                {
                    isEmpty = false;
                    String message = "ACTION: " + record.getTitle( ) + "\nTIME: " + record.getTimeStamp( ) + "\nAMOUNT: " + record.getAmount( ) + "\n\n";
                    finalMessage = finalMessage + message;

                }
                if (isEmpty == false) {
                    ShowAnythingOutput.setText(finalMessage);

                } else {
                    ShowAnythingOutput.setText("There's no any transactions to show.");
                }

            }
        }

    }
}
