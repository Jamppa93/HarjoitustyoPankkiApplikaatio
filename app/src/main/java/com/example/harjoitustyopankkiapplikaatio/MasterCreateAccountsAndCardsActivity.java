package com.example.harjoitustyopankkiapplikaatio;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

public class MasterCreateAccountsAndCardsActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_master_create_account_and_cards);

        Button buttonContactInfo = (Button) findViewById(R.id.returnFromMasterCreateAccountsAndCardsActivity);
        buttonContactInfo.setOnClickListener(new View.OnClickListener( ) {
            @Override
            public void onClick(View v) {
                openMasterLoginActivity( );
            }

            public void openMasterLoginActivity() {
                Intent intent = new Intent(MasterCreateAccountsAndCardsActivity.this, MasterLoginActivity.class);
                startActivity(intent);
            }
        });


    }
}




