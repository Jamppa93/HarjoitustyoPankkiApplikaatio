package com.example.harjoitustyopankkiapplikaatio;

// TO DO

// find the earlier recorder! Make a getter for that!
//Create UI buttons same way how they are made in Accounts?  + select account
// Start adding things up

// klo 13 mennessä nuo on tehty ja tarkistettu

// klo 16-21 teen tilimuokkaussetit valmiiksi


// USER ACCOUNTS
import java.util.ArrayList;

public class Account {

    static protected Integer id = 0;
    //************************************************************************************
    // Declaring classes for user information
    private UserInfo userInfo;
    private ContactInfo contactInfo;

    // Declaring ListArrays for bank account and card classes
    private ArrayList<CreditAccount> debitCreditAccounts;
    private ArrayList<DebitAccount> debitAccounts;
    private ArrayList<CreditAccount> creditAccounts;
    private ArrayList<BankAccountTransactions> bankAccountTransactions;

    Account(String firstName, String surName, String birthDate, String email, String phone, String streetName, String city, String postalCode, String username, String password) {

        this.id += 1;
        this.userInfo = new UserInfo(id, username, password);
        this.contactInfo = new ContactInfo(id, firstName, surName, birthDate, email, phone, streetName, city, postalCode);

        this.debitCreditAccounts = new ArrayList<>( );
        this.debitAccounts = new ArrayList<>( );
        this.creditAccounts = new ArrayList<>( );
        this.bankAccountTransactions = new ArrayList<>( );
    }


    //************************************************************************************
    //  Account creation methods

    // Inputs: account name, the initial balance and credit limit
    // adds the new Accounts to an account list

     void createDebitCreditAccount(String creditDebitAccountName, Double DebitBalance, Integer creditLimit) {
        CreditAccount debitCreditTemp = new CreditAccount(creditDebitAccountName, DebitBalance, creditLimit);
        debitCreditAccounts.add(debitCreditTemp);
    }

     void createDebitAccount(String debitAccountName, Double depositAmount) {
        DebitAccount debitTemp = new DebitAccount(debitAccountName, depositAmount);
        debitAccounts.add(debitTemp);
    }

     void createCreditAccount(Boolean isDebitActive, String creditAccountName, Integer creditLimit) {
        CreditAccount creditTemp = new CreditAccount(isDebitActive, creditAccountName, creditLimit);
        creditAccounts.add(creditTemp);
    }

    //************************************************************************************
    // Card creation methods

    // Inputs: account object, account name, the initial balance and credit limit
    // creates a bank card which is added to the object
     CreditAccount createDebitCreditCard(CreditAccount creditObjectTemp, String creditDebitCardNameTemp, Integer paymentLimitTemp) {


        Integer debitBankNumber = creditObjectTemp.getBankAccountNumber( );
        Integer creditBankNumber = creditObjectTemp.getCreditNumber( );
        Double debitBalance = creditObjectTemp.getBalance( );
        Double creditSaldo = creditObjectTemp.getCreditSaldo( );

        DebitCard debitCardTemp = new DebitCard(creditDebitCardNameTemp, paymentLimitTemp, debitBankNumber, debitBalance);
        CreditCard creditCardTemp = new CreditCard(creditDebitCardNameTemp, paymentLimitTemp, creditBankNumber, creditSaldo);

        SuperCard creditDebitCardTemp = new SuperCard(debitCardTemp, creditCardTemp);

        creditObjectTemp.SetSuperCardToCreditCardList(creditDebitCardTemp);

        return creditObjectTemp;
    }

     DebitAccount createDebitCard(DebitAccount debitObjectTemp, String debitCardNameTemp, Integer paymentLimitTemp) {

        Integer debitBankNumber = debitObjectTemp.getBankAccountNumber( );
        Double debitBalance = debitObjectTemp.getBalance( );

        DebitCard debitCardTemp = new DebitCard(debitCardNameTemp, paymentLimitTemp, debitBankNumber, debitBalance);

        debitObjectTemp.SetDebitCardToDebitCardList(debitCardTemp);

        return debitObjectTemp;
    }

     CreditAccount createCreditCard(CreditAccount creditObjectTemp, String creditCardNameTemp, Integer paymentLimitTemp) {


        Integer creditAccountNumber = creditObjectTemp.getCreditNumber( );
        Double creditSaldo = creditObjectTemp.getCreditSaldo( );

        CreditCard creditCardTemp = new CreditCard(creditCardNameTemp, paymentLimitTemp, creditAccountNumber, creditSaldo);

        creditObjectTemp.SetCreditCardToCreditCardList(creditCardTemp);

        return creditObjectTemp;
    }

    //************************************************************************************
    //BankAccountList Getters

     ArrayList<CreditAccount> getCreditAccounts() {
        return creditAccounts;
    }

     ArrayList<CreditAccount> getDebitCreditAccounts() {
        return debitCreditAccounts;
    }

     ArrayList<DebitAccount> getDebitAccounts() {
        return debitAccounts;
    }

    //************************************************************************************
    //BankAccount Getters

     CreditAccount getDebitCreditAccount(Integer accountNumber) {
        CreditAccount debitCreditTemp = null;
        for (CreditAccount c : debitCreditAccounts) {
            if (c.getCreditNumber( ).equals(accountNumber)) {
                debitCreditTemp = c;
            }
            if (c.getBankAccountNumber( ).equals(accountNumber)) {
               return c;
            }

        }
        return debitCreditTemp;
    }

     DebitAccount getDebitAccount(Integer accountNumber) {
        DebitAccount debitTemp = null;
        for (DebitAccount d : debitAccounts) {
            if (d.getBankAccountNumber( ).equals(accountNumber)) {
                debitTemp = d;
            }
        }
        return debitTemp;
    }

     CreditAccount getCreditAccount(Integer accountNumber) {
        CreditAccount creditTemp = null;
        for (CreditAccount c : creditAccounts) {
            if (c.getCreditNumber( ).equals(accountNumber)) {
                creditTemp = c;
            }

        }
        return creditTemp;
    }


    //************************************************************************************
    //Card Getters

     SuperCard getDebitCreditCard(Integer cardNumber) {
        SuperCard superCardTemp = null;

        if (getDebitCreditAccounts( ).size( ) > 0) {
            for (CreditAccount i : getDebitCreditAccounts( )) {
                if (i.getSuperCardsInAccount( ).size( ) > 0) {
                    for (SuperCard s : i.getSuperCardsInAccount( )) {
                        if (s.getId( ).equals(cardNumber)) {
                            superCardTemp = s;
                            break;
                        }
                    }
                }
            }
        }
        return superCardTemp;
    }

     DebitCard getDebitCard(Integer cardNumber) {
        DebitCard debitTemp = null;
        if (getDebitAccounts( ).size( ) > 0) {
            for (DebitAccount i : getDebitAccounts( )) {
                if (i.getDebitCardsInAccount( ).size( ) > 0) {
                    for (DebitCard d : i.getDebitCardsInAccount( )) {
                        if (d.getDebitCardNumber( ).equals(cardNumber)) {
                            debitTemp = d;
                            break;
                        }
                    }
                }
            }
        }
        return debitTemp;
    }

     CreditCard getCreditCard(Integer cardNumber) {
        CreditCard creditTemp = null;
        if (getCreditAccounts( ).size( ) > 0) {
            for (CreditAccount i : getCreditAccounts( )) {
                if (i.getCreditCardsInAccount( ).size( ) > 0) {
                    for (CreditCard c : i.getCreditCardsInAccount( )) {
                        if (c.getcreditCardNumber( ).equals(cardNumber)) {
                            creditTemp = c;
                            break;
                        }
                    }
                }
            }
        }
        return creditTemp;
    }
    //************************************************************************************
    // User and contact getters
     UserInfo getUserInfo() {
        return userInfo;
    }

     ContactInfo getContactInfo() {
        return contactInfo;
    }

    //************************************************************************************
    //Make changes to accounts and cards


    //Loops accounts to find the account and changes the credit limit, boolean value if the change was a success.

     Boolean changeAccountCreditLimit(Integer accountNumber, Integer newCreditLimit) {

        for (CreditAccount i : getDebitCreditAccounts( )) {

            if (i.getCreditNumber( ).equals(accountNumber)) {
                i.setCreditLimit(newCreditLimit);
                return true;
            }
        }
        for (CreditAccount i : getCreditAccounts( )) {
            if (i.getCreditNumber( ).equals(accountNumber)) {
                i.setCreditLimit(newCreditLimit);
                return true;
            }
        }


        return false;
    }

    //Loops accounts to find the account, removes the debit account and creates debitcredit account, boolean value if the change was a success.

     Boolean removeDebitAccountFromDebitCredit(Integer accountNumber) throws CloneNotSupportedException {

        for (CreditAccount i : getDebitCreditAccounts( )) {
            if (i.getCreditNumber( ).equals(accountNumber)) {
                CreditAccount tempCreditAccount = i.transformToCreditAccount( );
                getCreditAccounts( ).add(tempCreditAccount);
                getDebitCreditAccounts( ).remove(i);
                return true;
            }
        }

        return false;
    }

    //Loops accounts to find the account, removes the creditdebet account and creates debit account, boolean value if the change was a success.

     Boolean removeCreditAccountFromDebitCredit(Integer accountNumber) throws CloneNotSupportedException {

        for (CreditAccount i : getDebitCreditAccounts( )) {
            if (i.getCreditNumber( ).equals(accountNumber)) {
                DebitAccount tempDebitAccount = i.transformToDebitAccount();
                getDebitAccounts( ).add(tempDebitAccount);
                getDebitCreditAccounts( ).remove(i);
                return true;
            }
        }

    return false;}

    //Loops accounts to find the account and deletes the account, boolean value if the change was a success.

     Boolean removeAccount(Integer AccountNumber){

        for (CreditAccount i : getDebitCreditAccounts( )) {
            if (i.getCreditNumber( ).equals(AccountNumber)) {
                getDebitCreditAccounts( ).remove(i);
                return true;

            }
        }

        for (DebitAccount i : getDebitAccounts( )) {
            if (i.getBankAccountNumber( ).equals(AccountNumber)) {
                getDebitAccounts( ).remove(i);
                return true;

            }
        }

        for (CreditAccount i : getCreditAccounts( )) {
            if (i.getCreditNumber( ).equals(AccountNumber)) {
                getCreditAccounts( ).remove(i);
                return true;

            }
        }




    return false;}

    //Loops accounts to find the account and transform it to debitcredit, boolean value if the change was a success.

     Boolean transformDebitToDebitCreditAccount(Integer accountNumber) {

        for (DebitAccount i : getDebitAccounts( )) {
            if (i.getBankAccountNumber( ).equals(accountNumber)) {
                CreditAccount creditAccountTemp = new CreditAccount(i);
                getDebitCreditAccounts( ).add(creditAccountTemp);
                getDebitAccounts( ).remove(i);
                return true;
            }
        }


        return false;
    }

    //Loops accounts to find the account and transform it to debitcredit, boolean value if the change was a success.

    Boolean transformfromCreditToDebitCreditAccount(Integer cardNumber){

        for (CreditAccount i : getCreditAccounts( )) {
            if (i.getCreditNumber( ).equals(cardNumber)) {
                CreditAccount creditAccountTemp = new CreditAccount(i);
                getDebitCreditAccounts( ).add(creditAccountTemp);
                getCreditAccounts( ).remove(i);
                return true;
            }
        }

        return false;}


    //Loops cards to find the card and deletes the card, boolean value if the change was a success.
    Boolean removeDebitOrCreditCard(Integer cardNumber) {

        for (DebitAccount i : getDebitAccounts( )) {
            for (DebitCard s : i.getDebitCardsInAccount( )) {
                if (s.getDebitBankNumber( ).equals(cardNumber)) {
                    i.getDebitCardsInAccount( ).remove(s);
                    return true;
                }
            }
        }
        for (CreditAccount i : getCreditAccounts( )) {
            for (CreditCard s : i.getCreditCardsInAccount( )) {
                if (s.getcreditCardNumber( ).equals(cardNumber)) {
                    i.getCreditCardsInAccount( ).remove(s);
                    return true;
                }
            }
        }

        for (CreditAccount i : getDebitCreditAccounts( )) {
            for (SuperCard s : i.getSuperCardsInAccount( )) {
                if (s.getCreditCardHolder( ).getcreditCardNumber( ).equals(cardNumber)) {
                    s.getCreditCardHolder( ).removeCrediCard( );
                    return true;
                }
                if (s.getDebitCardHolder( ).getDebitCardNumber( ).equals(cardNumber)) {
                    s.getDebitCardHolder( ).removeDebitCard( );
                    return true;
                }
            }
        }
        return false;
    }

    //Loops cards to find the card and deletes the card, boolean value if the change was a success.

     Boolean removeSuperCard(int cardNumber) {

        for (CreditAccount i : getDebitCreditAccounts( )) {
            for (SuperCard s : i.getSuperCardsInAccount( )) {
                if (s.getId( ).equals(cardNumber)) {
                    i.getSuperCardsInAccount( ).remove(s);
                    return true;
                }
            }
        }
        return false;
    }

    //Loops cards to find the card and changes the payment limit of the card (if supercard, does it to both of the card), boolean value if the change was a success.

     Boolean changePaymentLimit(Integer cardNumber, Integer newPaymentLimit) {


        for (DebitAccount i : getDebitAccounts( )) {
            for (DebitCard s : i.getDebitCardsInAccount( )) {
                if((cardNumber-s.getDebitCardNumber())==0){
                    s.setPaymentLimitDebit(newPaymentLimit);
                    return true;
                }
            }
        }
        for (CreditAccount i : getCreditAccounts( )) {
            for (CreditCard s : i.getCreditCardsInAccount( )) {
                if((cardNumber-s.getcreditCardNumber())==0){
                    s.setPaymentLimitCredit(newPaymentLimit);
                    return true;
                }
            }
        }

        for (CreditAccount i : getDebitCreditAccounts( )) {
            for (SuperCard s : i.getSuperCardsInAccount( )) {
                if (cardNumber.equals(s.getId( ))) {
                    s.getCreditCardHolder().setPaymentLimitCredit(newPaymentLimit);
                    return true;
                }
                if (s.getId( ).equals(cardNumber)) {
                    s.getDebitCardHolder().setPaymentLimitDebit(newPaymentLimit);
                    return true;
                }
            }
        }

        return false;
    }


    //************************************************************************************
    //BankAccountTransactionRecords

     ArrayList<BankAccountTransactions> getBankAccountTransactions() {
        return bankAccountTransactions;
    }

    // Creates card action record and adds it to transaction lists
     void createBankAccountTransactionRecord(Integer titleId, String title, String usernameId, String accountType, String accountNumber, String cardType, String cardNumber, String amount) {
        BankAccountTransactions tempRecord = new BankAccountTransactions(titleId, title, usernameId, accountType, accountNumber, cardType, cardNumber, amount);
        bankAccountTransactions.add(tempRecord);

    }

    // Creates account action record and adds it to transaction lists
     void createBankAccountTransactionRecord(Integer titleId, String title, String usernameId, String accountType, String accountNumber, String amount) {
        BankAccountTransactions tempRecord = new BankAccountTransactions(titleId, title, usernameId, accountType, accountNumber, amount);
        bankAccountTransactions.add(tempRecord);

    }

    // loops all the Account list and return cards numbers, returns String list
     ArrayList<String> getAllAccountsNumbers() {

        ArrayList<String> accountNumbers = new ArrayList<>();

        if(getDebitCreditAccounts().size()>0) {
            String tempAccountNumber = "DEBIT/CREDIT: ";

            for (CreditAccount cd : getDebitCreditAccounts( )) {
                tempAccountNumber = tempAccountNumber + cd.getCreditNumber( ).toString( ) + "/";
                tempAccountNumber =tempAccountNumber+ cd.getBankAccountNumber( ).toString( );
                accountNumbers.add(tempAccountNumber);
            }
        }

        if(getCreditAccounts().size()>0){
            String tempAccountNumber = "CREDIT: ";
            for(CreditAccount c : getCreditAccounts()){
                accountNumbers.add(tempAccountNumber+c.getCreditNumber().toString());
            }
        }
        if(getDebitAccounts().size()>0){
            String tempAccountNumber = "DEBIT: ";
            for(DebitAccount d : getDebitAccounts()){
                accountNumbers.add(tempAccountNumber+d.getBankAccountNumber().toString());
            }
        }

        return accountNumbers;}

     // loops all the card list and return cards numbers, returns String list
     ArrayList<String> getAllCardNumbers() {

        ArrayList<String> accountNumbers = new ArrayList<>();

        if(getDebitCreditAccounts().size()>0) {
            String tempAccountNumber = "DEBIT/CREDIT: ";
            for (CreditAccount cd : getDebitCreditAccounts( )) {
                if(cd.getSuperCardsInAccount().size()>0){
                    for(SuperCard s : cd.getSuperCardsInAccount()){
                        accountNumbers.add(tempAccountNumber+ s.getId().toString());
                    }
                }
            }
        }

        if(getDebitAccounts().size()>0){
            String tempAccountNumber = "DEBIT: ";
            for (DebitAccount da : getDebitAccounts( )) {
                if(da.getDebitCardsInAccount().size()>0){
                    for(DebitCard dc : da.getDebitCardsInAccount()){
                        accountNumbers.add(tempAccountNumber+ dc.getDebitCardNumber().toString());
                    }
                }
            }
        }


        if(getCreditAccounts().size()>0){
            String tempAccountNumber = "CREDIT: ";
            for (CreditAccount ca : getCreditAccounts( )) {
                if(ca.getCreditCardsInAccount().size()>0){
                    for(CreditCard cc : ca.getCreditCardsInAccount()){
                        accountNumbers.add(tempAccountNumber+ cc.getcreditCardNumber().toString());
                    }
                }
            }
        }

    return  accountNumbers;
    }
}



