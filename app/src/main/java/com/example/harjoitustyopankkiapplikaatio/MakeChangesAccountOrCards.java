package com.example.harjoitustyopankkiapplikaatio;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.Arrays;

public class MakeChangesAccountOrCards extends AppCompatActivity {

    private Account currentUserAccount = Bank.getLoggedAccount( );

    //Declarations
    //*********************************************************//
    private String SelectedOptionTypeAccountOrCard = "";
    private String SelectedOptionWhichObject = "";
    private String SelectedOptionChangeableFeatures = "";
    private String SelectedOptionTargetCardOrAccount = "";

    private Spinner SelectTypeAccountOrCard;
    private Spinner SelectWhichObject;
    private Spinner SelectChangeableFeatures;
    private Spinner SelectedTargetCard;
    private Button buttonMakeChanges;
    Button buttonReturnMakeAccountOrCardMainActivity;
    protected EditText editTextInput;

    private Boolean isChangeOK;


    //*********************************************************//
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);



        setContentView(R.layout.activity_make_changes_account_or_cards);
        SelectTypeAccountOrCard = findViewById(R.id.SpinnerSelectType);
        SelectChangeableFeatures = findViewById(R.id.spinnerSelectFeature);
        SelectWhichObject = findViewById(R.id.SpinnerSelectObject);
        SelectedTargetCard = findViewById(R.id.SpinnerSelectTargetCard);
        editTextInput = findViewById(R.id.editTextInputtext);
        buttonMakeChanges = findViewById(R.id.buttonMakeChanges);
        buttonReturnMakeAccountOrCardMainActivity = findViewById(R.id.buttonReturnFromMakeChangesAccountOrCardsActivity);

        SetSpinnerSelectTypeAccountOrCard( );

        buttonReturnMakeAccountOrCardMainActivity.setOnClickListener(new View.OnClickListener( ) {
            @Override
            public void onClick(View v) {
                openMakeAccountsCardsMainActivity( );
            }

            public void openMakeAccountsCardsMainActivity() {
                Intent intent = new Intent(MakeChangesAccountOrCards.this, MakeAccountsCardsMainActivity.class);
                startActivity(intent);
            }
        });

        buttonMakeChanges.setOnClickListener(new View.OnClickListener( ) {
            @Override
            public void onClick(View v) {
                try {

                    isChangeOK = false;
                    makeChanges( );
                    if (isChangeOK) {
                        Bank.loggedIn(currentUserAccount);
                        editTextInput.setHint("The change was made!");
                        editTextInput.getText().clear();
                    } else {
                        editTextInput.setHint("The change failed!");
                        editTextInput.getText().clear();
                    }
                    SetSpinnerSelectTypeAccountOrCard( );

                } catch (CloneNotSupportedException e) {
                    e.printStackTrace( );
                }
            }

            public void makeChanges() throws CloneNotSupportedException {

                Integer accountOrCard = Integer.parseInt(SelectedOptionWhichObject);

                //ACCOUNT FEATURE CHANGES
                if (SelectedOptionTypeAccountOrCard.matches("ACCOUNT: DEBIT/CREDIT")) {
                    if (SelectedOptionChangeableFeatures.matches("CHANGE CREDIT LIMIT")) {
                        isChangeOK = currentUserAccount.changeAccountCreditLimit(accountOrCard, checkifEmpty(editTextInput.getText( ).toString( )));

                    } else if (SelectedOptionChangeableFeatures.matches("REMOVE DEBIT ACCOUNT AND CARDS")) {

                        isChangeOK = currentUserAccount.removeDebitAccountFromDebitCredit(accountOrCard);

                    } else if (SelectedOptionChangeableFeatures.matches("REMOVE CREDIT ACCOUNT AND CARDS")) {

                        isChangeOK = currentUserAccount.removeCreditAccountFromDebitCredit(accountOrCard);

                    } else if (SelectedOptionChangeableFeatures.matches("REMOVE DEBIT CARD")) {

                        isChangeOK = currentUserAccount.removeDebitOrCreditCard(accountOrCard);

                    } else if (SelectedOptionChangeableFeatures.matches("REMOVE CREDIT CARD")) {

                        isChangeOK = currentUserAccount.removeDebitOrCreditCard(accountOrCard);//

                    } else if (SelectedOptionChangeableFeatures.matches("DELETE ACCOUNT AND ALL THE CARDS")) {

                        isChangeOK = currentUserAccount.removeAccount(accountOrCard);

                    }
                }

                if (SelectedOptionTypeAccountOrCard.matches("ACCOUNT: DEBIT")) {

                    if (SelectedOptionChangeableFeatures.matches("CHANGE THIS ACCOUNT TO DEBIT/CREDIT ACCOUNT")) {

                        isChangeOK =currentUserAccount.transformDebitToDebitCreditAccount(Integer.parseInt(SelectedOptionWhichObject));

                    } else if (SelectedOptionChangeableFeatures.matches("REMOVE DEBIT CARD")) {

                        isChangeOK = currentUserAccount.removeDebitOrCreditCard(Integer.parseInt(SelectedOptionWhichObject));

                    } else if (SelectedOptionChangeableFeatures.matches("DELETE ACCOUNT AND ALL THE CARDS")) {

                        isChangeOK = currentUserAccount.removeAccount(Integer.parseInt(SelectedOptionWhichObject));
                    }
                }

                if (SelectedOptionTypeAccountOrCard.matches("ACCOUNT: CREDIT")) {

                    if (SelectedOptionChangeableFeatures.matches(" CHANGE CREDIT LIMIT")) {

                        isChangeOK =currentUserAccount.changeAccountCreditLimit(Integer.parseInt(SelectedOptionWhichObject),checkifEmpty(editTextInput.getText( ).toString( )));

                    } else if (SelectedOptionChangeableFeatures.matches("CHANGE THIS ACCOUNT TO DEBIT/CREDIT ACCOUNT")) {

                        isChangeOK = currentUserAccount.transformfromCreditToDebitCreditAccount(Integer.parseInt(SelectedOptionWhichObject));

                    } else if (SelectedOptionChangeableFeatures.matches("REMOVE CREDIT CARD")) {

                        isChangeOK = currentUserAccount.removeDebitOrCreditCard(Integer.parseInt(SelectedOptionWhichObject));

                    } else if (SelectedOptionChangeableFeatures.matches("DELETE ACCOUNT AND ALL THE CARDS")) {

                        isChangeOK = currentUserAccount.removeAccount(Integer.parseInt(SelectedOptionWhichObject));
                    }
                }
                //CARD FEATURE CHANGES
                        System.out.println(SelectedOptionWhichObject);
                    System.out.println(editTextInput.getText().toString());
                if (SelectedOptionTypeAccountOrCard.matches("CARD: DEBIT/CREDIT")) {

                    if (SelectedOptionChangeableFeatures.matches("CHANGE PAYMENT LIMIT")) {

                        isChangeOK = currentUserAccount.changePaymentLimit(Integer.parseInt(SelectedOptionWhichObject),checkifEmpty(editTextInput.getText( ).toString( )));

                    } else if (SelectedOptionChangeableFeatures.matches("REMOVE DEBIT CARD")) {

                        isChangeOK = currentUserAccount.removeDebitOrCreditCard(Integer.parseInt(SelectedOptionWhichObject));

                    } else if (SelectedOptionChangeableFeatures.matches("REMOVE CREDIT CARD")) {

                        isChangeOK = currentUserAccount.removeDebitOrCreditCard(Integer.parseInt(SelectedOptionWhichObject));

                    } else if (SelectedOptionChangeableFeatures.matches("DELETE CARD")) {

                        isChangeOK = currentUserAccount.removeSuperCard(Integer.parseInt(SelectedOptionWhichObject));

                    }
                }
                if (SelectedOptionTypeAccountOrCard.matches("CARD: DEBIT")) {

                    if (SelectedOptionChangeableFeatures.matches("CHANGE PAYMENT LIMIT")) {

                        isChangeOK = currentUserAccount.changePaymentLimit(Integer.parseInt(SelectedOptionWhichObject),checkifEmpty(editTextInput.getText( ).toString( )));

                    } else if (SelectedOptionChangeableFeatures.matches("DELETE CARD")) {

                        isChangeOK = currentUserAccount.removeDebitOrCreditCard(Integer.parseInt(SelectedOptionWhichObject));
                    }
                }
                if (SelectedOptionTypeAccountOrCard.matches("CARD: CREDIT")) {

                    if (SelectedOptionChangeableFeatures.matches("CHANGE PAYMENT LIMIT")) {

                        isChangeOK = currentUserAccount.changePaymentLimit(Integer.parseInt(SelectedOptionWhichObject),checkifEmpty(editTextInput.getText( ).toString( )));

                    } else if (SelectedOptionChangeableFeatures.matches("DELETE CARD")) {

                        isChangeOK = currentUserAccount.removeDebitOrCreditCard(Integer.parseInt(SelectedOptionWhichObject));

                    }
                }
            }
        });
    }
    public Integer checkifEmpty(String s){
        if(s!=""){
            return Integer.parseInt(s);}
        else return 0;}






    class SelectTypeAccountOrCardSpinnerClass implements AdapterView.OnItemSelectedListener {
        public void onItemSelected(AdapterView<?> parent, View v, int position, long id) {
            SelectedOptionTypeAccountOrCard = parent.getItemAtPosition(position).toString( );

            if (!SelectedOptionTypeAccountOrCard.matches("SELECT")) {
                SelectWhichObject.setEnabled(true);
                SetSpinnerSelectNothing(SelectWhichObject);
                SetSpinnerSelectWhichObject( );
            } else {
                buttonMakeChanges.setEnabled(false);
                SetSpinnerSelectNothing(SelectWhichObject);
                SetSpinnerSelectNothing(SelectChangeableFeatures);
                SetSpinnerSelectNothing(SelectedTargetCard);
                SelectWhichObject.setEnabled(false);
                SelectChangeableFeatures.setEnabled(false);
                SelectedTargetCard.setEnabled(false);
            }
        }
        @Override
        public void onNothingSelected(AdapterView<?> adapterView) {
        }
    }

    class SelectWhichObjectSpinnerClass implements AdapterView.OnItemSelectedListener {
        public void onItemSelected(AdapterView<?> parent, View v, int position, long id) {
            SelectedOptionWhichObject = parent.getItemAtPosition(position).toString( );

            if (!SelectedOptionTypeAccountOrCard.isEmpty( ) && !SelectedOptionTypeAccountOrCard.matches("SELECT ACCOUNT") && !SelectedOptionWhichObject.matches("SELECT ACCOUNT") && !SelectedOptionWhichObject.matches("SELECT CARD")) {
                SetSpinnerSelectChangeableFeatures( );
                SelectChangeableFeatures.setEnabled(true);
            } else {
                buttonMakeChanges.setEnabled(false);
                SetSpinnerSelectNothing(SelectChangeableFeatures);
                SetSpinnerSelectNothing(SelectedTargetCard);
                SelectedTargetCard.setEnabled(false);
                SelectChangeableFeatures.setEnabled(false);
            }
        }
        @Override
        public void onNothingSelected(AdapterView<?> adapterView) {
        }
    }

    class SelectChangeableFeaturesSpinnerClass implements AdapterView.OnItemSelectedListener {
        public void onItemSelected(AdapterView<?> parent, View v, int position, long id) {
            SelectedOptionChangeableFeatures = parent.getItemAtPosition(position).toString( );

            if (!SelectedOptionChangeableFeatures.matches("SELECT ACTION") && !SelectedOptionChangeableFeatures.isEmpty( )) {
                buttonMakeChanges.setEnabled(true);
                if (SelectedOptionChangeableFeatures.matches("REMOVE DEBIT ACCOUNT AND CARDS") || SelectedOptionChangeableFeatures.matches("REMOVE CREDIT ACCOUNT AND CARDS")) {
                    SelectedTargetCard.setEnabled(true);
                    SetSpinnerSelectTargetAccount( );
                } else if (SelectedOptionChangeableFeatures.matches("REMOVE DEBIT CARD") || SelectedOptionChangeableFeatures.matches("REMOVE CREDIT CARD")) {
                    SelectedTargetCard.setEnabled(true);
                    SetSpinnerSelectTargetCard( );
                }
            } else {
                SetSpinnerSelectNothing(SelectedTargetCard);
                SelectedTargetCard.setEnabled(false);
            }
        }
        @Override
        public void onNothingSelected(AdapterView<?> adapterView) {
        }
    }

    class SelectTargetCardSpinnerClass implements AdapterView.OnItemSelectedListener {

        public void onItemSelected(AdapterView<?> parent, View v, int position, long id) {
            SelectedOptionTargetCardOrAccount = parent.getItemAtPosition(position).toString( );
        }

        @Override
        public void onNothingSelected(AdapterView<?> adapterView) {
        }
    }

    //*********************************************************//
    // SPINNERS
    private void SetSpinnerSelectTypeAccountOrCard() {

        ArrayList<String> typesAccountCard = new ArrayList(Arrays.asList("SELECT", "ACCOUNT: DEBIT/CREDIT", "ACCOUNT: DEBIT", "ACCOUNT: CREDIT", "CARD: DEBIT/CREDIT", "CARD: DEBIT", "CARD: CREDIT"));
        ArrayAdapter<String> adapter = new ArrayAdapter<String>(this, android.R.layout.simple_spinner_item, typesAccountCard);
        adapter.setDropDownViewResource(android.R.layout.simple_dropdown_item_1line);
        SelectTypeAccountOrCard.setAdapter(adapter);
        SelectTypeAccountOrCard.setOnItemSelectedListener(new SelectTypeAccountOrCardSpinnerClass( ));
    }

    private void SetSpinnerSelectWhichObject() {

        String selectedType = SelectedOptionTypeAccountOrCard; // Since the output texts depend on what option is in SelectedOptionTypeAccountOrCard
        ArrayList<String> typeOptions = getSelectedAccountCardTypeOptions(selectedType);
        ArrayAdapter<String> adapter = new ArrayAdapter<String>(this, android.R.layout.simple_spinner_item, typeOptions);
        adapter.setDropDownViewResource(android.R.layout.simple_dropdown_item_1line);
        SelectWhichObject.setAdapter(adapter);
        SelectWhichObject.setOnItemSelectedListener(new SelectWhichObjectSpinnerClass( ));
    }

    private void SetSpinnerSelectChangeableFeatures() {

        String selectedType = SelectedOptionTypeAccountOrCard;
        ArrayList<String> typeOptions = getSelectedAccountCardTypeFeatures(selectedType);
        ArrayAdapter<String> adapter = new ArrayAdapter<String>(this, android.R.layout.simple_spinner_item, typeOptions);
        adapter.setDropDownViewResource(android.R.layout.simple_dropdown_item_1line);
        SelectChangeableFeatures.setAdapter(adapter);
        SelectChangeableFeatures.setOnItemSelectedListener(new SelectChangeableFeaturesSpinnerClass( ));
    }

    private void SetSpinnerSelectTargetCard() {

        ArrayList<String> typeOptions = getSelectedCards(SelectedOptionTypeAccountOrCard, SelectedOptionChangeableFeatures, SelectedOptionWhichObject);
        ArrayAdapter<String> adapter = new ArrayAdapter<String>(this, android.R.layout.simple_spinner_item, typeOptions);
        adapter.setDropDownViewResource(android.R.layout.simple_dropdown_item_1line);
        SelectedTargetCard.setAdapter(adapter);
        SelectedTargetCard.setOnItemSelectedListener(new SelectTargetCardSpinnerClass( ));
    }

    private void SetSpinnerSelectTargetAccount() {

        ArrayList<String> typeOptions = getSelectedAccounts(SelectedOptionTypeAccountOrCard, SelectedOptionWhichObject, SelectedOptionChangeableFeatures);

        ArrayAdapter<String> adapter = new ArrayAdapter<String>(this, android.R.layout.simple_spinner_item, typeOptions);
        adapter.setDropDownViewResource(android.R.layout.simple_dropdown_item_1line);
        SelectedTargetCard.setAdapter(adapter);
        SelectedTargetCard.setOnItemSelectedListener(new SelectTargetCardSpinnerClass( ));
    }

    private void SetSpinnerSelectNothing(Spinner s) {
        ArrayList<String> nothingList = new ArrayList(Arrays.asList(""));
        ArrayAdapter<String> adapter = new ArrayAdapter<String>(this, android.R.layout.simple_spinner_item, nothingList);
        adapter.setDropDownViewResource(android.R.layout.simple_dropdown_item_1line);
        s.setAdapter(adapter);
    }

    //*********************************************************//
    // GET SPINNER TEXTS

    protected ArrayList<String> getSelectedAccountCardTypeOptions(String selectedType) {
        ArrayList<String> showThese = new ArrayList<>( );


        if (selectedType.matches("ACCOUNT: DEBIT/CREDIT")) {
            showThese.add("SELECT ACCOUNT");

            for (CreditAccount i : currentUserAccount.getDebitCreditAccounts( )) {
                showThese.add(i.getCreditNumber( ).toString( ));
            }
        } else if (selectedType.matches("ACCOUNT: DEBIT")) {
            showThese.add("SELECT ACCOUNT");
            for (DebitAccount i : currentUserAccount.getDebitAccounts( )) {
                showThese.add(i.getBankAccountNumber( ).toString( ));

            }
        } else if (selectedType.matches("ACCOUNT: CREDIT")) {
            showThese.add("SELECT ACCOUNT");
            for (CreditAccount i : currentUserAccount.getCreditAccounts( )) {
                showThese.add(i.getCreditNumber( ).toString( ));
            }
        } else if (selectedType.matches("CARD: DEBIT/CREDIT")) {
            showThese.add("SELECT CARD");
            for (CreditAccount i : currentUserAccount.getDebitCreditAccounts( )) {
                for (SuperCard s : i.getSuperCardsInAccount( )) {
                    showThese.add(s.getId( ).toString( ));
                }
            }
        } else if (selectedType.matches("CARD: DEBIT")) {
            showThese.add("SELECT CARD");
            for (DebitAccount i : currentUserAccount.getDebitAccounts( )) {
                for (DebitCard s : i.getDebitCardsInAccount( )) {
                    showThese.add(s.getDebitCardNumber( ).toString( ));
                }
            }

        } else if (selectedType.matches("CARD: CREDIT")) {
            showThese.add("SELECT CARD");
            for (CreditAccount i : currentUserAccount.getCreditAccounts( )) {
                for (CreditCard s : i.getCreditCardsInAccount( )) {
                    showThese.add(s.getcreditCardNumber( ).toString( ));
                }
            }

        }
        return showThese;
    }

    protected ArrayList<String> getSelectedAccountCardTypeFeatures(String selectedType) {

        ArrayList<String> showThese;

        if (selectedType.matches("ACCOUNT: DEBIT/CREDIT")) {
            showThese = new ArrayList<>(Arrays.asList("SELECT ACTION","CHANGE CREDIT LIMIT","REMOVE DEBIT ACCOUNT AND CARDS","REMOVE CREDIT ACCOUNT AND CARDS","REMOVE DEBIT CARD","REMOVE CREDIT CARD","DELETE ACCOUNT AND ALL THE CARDS" ) );

        } else if (selectedType.matches("ACCOUNT: DEBIT")) {
            showThese = new ArrayList<>(Arrays.asList("SELECT ACTION","CHANGE THIS ACCOUNT TO DEBIT/CREDIT ACCOUNT","REMOVE DEBIT CARD","DELETE ACCOUNT AND ALL THE CARDS"));

        } else if (selectedType.matches("ACCOUNT: CREDIT")) {
            showThese = new ArrayList<>(Arrays.asList("SELECT ACTION","CHANGE CREDIT LIMIT","CHANGE THIS ACCOUNT TO DEBIT/CREDIT ACCOUNT","REMOVE CREDIT CARD","DELETE ACCOUNT AND ALL THE CARDS"));

        } else if (selectedType.matches("CARD: DEBIT/CREDIT")) {
            showThese = new ArrayList<>(Arrays.asList("SELECT ACTION","CHANGE PAYMENT LIMIT","REMOVE DEBIT CARD", "REMOVE CREDIT CARD","DELETE CARD"));

        } else if (selectedType.matches("CARD: DEBIT")) {
            showThese = new ArrayList<>(Arrays.asList("SELECT ACTION","CHANGE PAYMENT LIMIT","DELETE CARD"));

        } else if (selectedType.matches("CARD: CREDIT")) {
            showThese = new ArrayList<>(Arrays.asList("SELECT ACTION","CHANGE PAYMENT LIMIT","DELETE CARD"));
        }
        else{
            showThese = new ArrayList<>();
        }
        return showThese;
    }

    protected ArrayList<String> getSelectedCards(String selectedType, String selectedAccount, String selectedFeature) {

        ArrayList<String> showThese = new ArrayList<>( );
        String title = "SELECT CARD";
        showThese.add(0, title);

        if (selectedType.matches("ACCOUNT: DEBIT/CREDIT")) {

            if (selectedFeature.matches("REMOVE DEBIT CARD")) {

                for (CreditAccount i : currentUserAccount.getDebitCreditAccounts( )) {
                    if (selectedAccount.matches(i.creditNumber.toString( ))) {
                        for (SuperCard s : i.getSuperCardsInAccount( )) {
                            showThese.add(s.getDebitCardHolder( ).getDebitBankNumber( ).toString( ));
                        }
                    }
                }
            }

            if (selectedFeature.matches("REMOVE CREDIT CARD")) {

                for (CreditAccount i : currentUserAccount.getDebitCreditAccounts( )) {
                    if (selectedAccount.matches(i.creditNumber.toString( ))) {
                        for (SuperCard s : i.getSuperCardsInAccount( )) {
                            showThese.add(s.getCreditCardHolder( ).getcreditCardNumber( ).toString( ));
                        }
                    }
                }
            }
        } else if (selectedType.matches("ACCOUNT: DEBIT")) {

            if (selectedFeature.matches("REMOVE DEBIT CARD")) {

                for (DebitAccount i : currentUserAccount.getDebitAccounts( )) {
                    if (selectedAccount.matches(i.getBankAccountNumber( ).toString( ))) {
                        for (DebitCard s : i.getDebitCardsInAccount( )) {
                            showThese.add(s.getDebitBankNumber( ).toString( ));
                        }
                    }
                }
            }
        } else if (selectedType.matches("ACCOUNT: CREDIT")) {

            if (selectedFeature.matches("REMOVE CREDIT CARD")) {

                for (CreditAccount i : currentUserAccount.getCreditAccounts( )) {
                    if (selectedAccount.matches(i.getCreditNumber( ).toString( ))) {
                        for (CreditCard s : i.getCreditCardsInAccount( )) {
                            showThese.add(s.getcreditCardNumber( ).toString( ));
                        }
                    }
                }
            }

        }
        if (showThese.size( ) == 1) {
            showThese.add("THERE'S NO CARDS");
        }
        return showThese;
    }

    protected ArrayList<String> getSelectedAccounts(String selectedType, String selectedAccount, String selectedFeature) {

        ArrayList<String> showThese = new ArrayList<>( );
        String title = "SELECT  ACCOUNT";
        showThese.add(0, title);

        if (selectedType.matches("ACCOUNT: DEBIT/CREDIT")) {

            if (selectedFeature.matches("REMOVE DEBIT ACCOUNT AND CARDS")) {

                for (CreditAccount i : currentUserAccount.getDebitCreditAccounts( )) {
                    if (selectedAccount.matches(i.getCreditNumber( ).toString( )) && (i.isDebitActive( ) == true)) {
                        showThese.add(i.getBankAccountNumber( ).toString( ));
                    }
                }
            }
            if (selectedFeature.matches("REMOVE CREDIT ACCOUNT AND CARDS")) {

                for (CreditAccount i : currentUserAccount.getDebitCreditAccounts( )) {
                    if (selectedAccount.matches(i.getCreditNumber( ).toString( )) && (i.isCreditActive( ) == true)) {
                        showThese.add(i.getCreditNumber( ).toString( ));
                    }
                }
            }

            if (showThese.size( ) == 1) {
                showThese.add("THERE'S NO ACCOUNT'S LEFT");
            }

        }
        return showThese;
    }



}


/*

        public void onItemSelected(AdapterView<?> parent, View v, int position, long id) {
            SelectedOptionTargetCardOrAccount = parent.getItemAtPosition(position).toString( );
            if(SelectedOptionTargetCardOrAccount.matches("SELECT DEBIT ACCOUNT") ||SelectedOptionTargetCardOrAccount.matches("SELECT CREDIT ACCOUNT")
                    ||SelectedOptionTargetCardOrAccount.matches("SELECT DEBIT CARD")||SelectedOptionTargetCardOrAccount.matches("SELECT CREDIT CARD")
            ){
                SelectedOptionTargetCardOrAccount="";
            }
        }

 */
