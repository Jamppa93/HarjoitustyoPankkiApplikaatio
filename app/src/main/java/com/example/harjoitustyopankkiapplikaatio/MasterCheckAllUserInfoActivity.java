package com.example.harjoitustyopankkiapplikaatio;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

public class MasterCheckAllUserInfoActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_master_check_all_user_info);
        Button buttonReturnLoginMaster = (Button) findViewById(R.id.buttonReturnMasterLogin);
        Button buttonGoUserContactInfo = (Button) findViewById(R.id.buttonUserInfoMaster);
        Button buttonGoUserAccountsAndCards = (Button) findViewById(R.id.buttonAccountAndCardsMaster);
        Button buttonGoUserBankTransactions = (Button) findViewById(R.id.buttonBankTransactionsMaster);


        buttonReturnLoginMaster.setOnClickListener(new View.OnClickListener( ) {
            @Override
            public void onClick(View v) {
                returnUserAllInfo( );
            }

            public void returnUserAllInfo() {
                Intent intent = new Intent(MasterCheckAllUserInfoActivity.this, MasterLoginActivity.class);
                startActivity(intent);

            }
        });

        buttonGoUserContactInfo.setOnClickListener(new View.OnClickListener( ) {
            @Override
            public void onClick(View v) {
                gooUserContactInfo( );
            }

            public void gooUserContactInfo() {
                Intent intent = new Intent(MasterCheckAllUserInfoActivity.this, MasterCheckUserInfoActivity.class);
                startActivity(intent);

            }
        });

        buttonGoUserAccountsAndCards.setOnClickListener(new View.OnClickListener( ) {
            @Override
            public void onClick(View v) {
                goUserAccountsAndCards( );
            }

            public void goUserAccountsAndCards() {
                Intent intent = new Intent(MasterCheckAllUserInfoActivity.this, MasterCheckUserAccountsAndCards.class);
                startActivity(intent);

            }
        });

        buttonGoUserBankTransactions.setOnClickListener(new View.OnClickListener( ) {
            @Override
            public void onClick(View v) {
                goUserBankTransactions( );
            }

            public void goUserBankTransactions() {
                Intent intent = new Intent(MasterCheckAllUserInfoActivity.this, MasterCheckUserBankTransactions.class);
                startActivity(intent);

            }
        });



    }
}


