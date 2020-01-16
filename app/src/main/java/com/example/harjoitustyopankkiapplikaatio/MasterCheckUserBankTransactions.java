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

public class MasterCheckUserBankTransactions extends AppCompatActivity {

    private Account currentUserAccount = Bank.getLoggedAccount( );

    //Declarations
    //*********************************************************//
    private String SpinOutputTypeMaster;
    private String spinOutputObjectMaster;
    private Spinner SelectShowedAccountTypeMaster;
    private Spinner SelectShowedAccountTargetMaster;
    private TextView ShowAnythingOutputMaster;
    ;


    //*********************************************************//

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_master_check_user_bank_transactions);

        //  initializing TextView
        ShowAnythingOutputMaster = (TextView) findViewById(R.id.textViewShowAnythingTransactionsMaster);
        ShowAnythingOutputMaster.setMovementMethod(new ScrollingMovementMethod( ));

        // Declaring and initializing Spinner
        displayTransactions( );


        //TransferMoneyActivity: to LoginActivity
        Button buttonReturnUserAllInfoMaster = (Button) findViewById(R.id.buttonReturnToAllUserInfoMaster);
        buttonReturnUserAllInfoMaster.setOnClickListener(new View.OnClickListener( ) {
            @Override
            public void onClick(View v) {
                openLoginActivity( );
            }

            public void openLoginActivity() {
                Intent intent = new Intent(MasterCheckUserBankTransactions.this, MasterCheckAllUserInfoActivity.class);
                startActivity(intent);
            }
        });
    }



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
                    ShowAnythingOutputMaster.setText(finalMessage);

                } else {
                    ShowAnythingOutputMaster.setText("There's no any transactions to show.");
                }

            }
        }

    }
}






































