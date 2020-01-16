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
    protected UserInfo userInfo;
    protected ContactInfo contactInfo;

    // Declaring ListArrays for bank account and card classes
    protected ArrayList<CreditAccount> debitCreditAccounts;
    protected ArrayList<DebitAccount> debitAccounts;
    protected ArrayList<CreditAccount> creditAccounts;
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

    protected void createDebitCreditAccount(String creditDebitAccountName, Double DebitBalance, Integer creditLimit) {
        CreditAccount debitCreditTemp = new CreditAccount(creditDebitAccountName, DebitBalance, creditLimit);
        debitCreditAccounts.add(debitCreditTemp);
    }

    protected void createDebitAccount(String debitAccountName, Double depositAmount) {
        DebitAccount debitTemp = new DebitAccount(debitAccountName, depositAmount);
        debitAccounts.add(debitTemp);
    }

    protected void createCreditAccount(Boolean isDebitActive, String creditAccountName, int creditLimit) {
        CreditAccount creditTemp = new CreditAccount(isDebitActive, creditAccountName, creditLimit);
        creditAccounts.add(creditTemp);
    }

    //************************************************************************************
    // Card creation methods

    protected CreditAccount createDebitCreditCard(CreditAccount creditObjectTemp, String creditDebitCardNameTemp, Integer paymentLimitTemp) {


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

    protected DebitAccount createDebitCard(DebitAccount debitObjectTemp, String debitCardNameTemp, Integer paymentLimitTemp) {

        Integer debitBankNumber = debitObjectTemp.getBankAccountNumber( );
        Double debitBalance = debitObjectTemp.getBalance( );

        DebitCard debitCardTemp = new DebitCard(debitCardNameTemp, paymentLimitTemp, debitBankNumber, debitBalance);

        debitObjectTemp.SetDebitCardToDebitCardList(debitCardTemp);

        return debitObjectTemp;
    }

    protected CreditAccount createCreditCard(CreditAccount creditObjectTemp, String creditCardNameTemp, Integer paymentLimitTemp) {


        Integer creditAccountNumber = creditObjectTemp.getCreditNumber( );
        Double creditSaldo = creditObjectTemp.getCreditSaldo( );

        CreditCard creditCardTemp = new CreditCard(creditCardNameTemp, paymentLimitTemp, creditAccountNumber, creditSaldo);

        creditObjectTemp.SetCreditCardToCreditCardList(creditCardTemp);

        return creditObjectTemp;
    }

    //************************************************************************************
    //BankAccountList Getters

    public ArrayList<CreditAccount> getCreditAccounts() {
        return creditAccounts;
    }

    public ArrayList<CreditAccount> getDebitCreditAccounts() {
        return debitCreditAccounts;
    }

    public ArrayList<DebitAccount> getDebitAccounts() {
        return debitAccounts;
    }

    //************************************************************************************
    //BankAccount Getters

    public CreditAccount getDebitCreditAccount(Integer accountNumber) {
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

    public DebitAccount getDebitAccount(Integer accountNumber) {
        DebitAccount debitTemp = null;
        for (DebitAccount d : debitAccounts) {
            if (d.getBankAccountNumber( ).equals(accountNumber)) {
                debitTemp = d;
            }
        }
        return debitTemp;
    }

    public CreditAccount getCreditAccount(Integer accountNumber) {
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

    public SuperCard getDebitCreditCard(Integer cardNumber) {
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

    public DebitCard getDebitCard(Integer cardNumber) {
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

    public CreditCard getCreditCard(Integer cardNumber) {
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
    // Other getters
    public UserInfo getUserInfo() {
        return userInfo;
    }

    public ContactInfo getContactInfo() {
        return contactInfo;
    }



    //************************************************************************************
    //Make changes to accounts and cards

    public Boolean changeAccountCreditLimit(Integer accountNumber, Integer newCreditLimit) {

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


    public Boolean removeDebitAccountFromDebitCredit(Integer accountNumber) throws CloneNotSupportedException {

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

    public Boolean removeCreditAccountFromDebitCredit(Integer accountNumber) throws CloneNotSupportedException {

        for (CreditAccount i : getDebitCreditAccounts( )) {
            if (i.getCreditNumber( ).equals(accountNumber)) {
                DebitAccount tempDebitAccount = i.transformToDebitAccount();
                getDebitAccounts( ).add(tempDebitAccount);
                getDebitCreditAccounts( ).remove(i);
                return true;
            }
        }

    return false;}

    public Boolean removeAccount(Integer AccountNumber){

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

    public Boolean transformDebitToDebitCreditAccount(Integer accountNumber) {

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

    public Boolean removeDebitOrCreditCard(Integer cardNumber) {

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

    public Boolean removeSuperCard(int cardNumber) {

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

    public Boolean changePaymentLimit(Integer cardNumber, Integer newPaymentLimit) {


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
                if (s.getId( ).equals(cardNumber)) {
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


    public Boolean transformfromCreditToDebitCreditAccount(Integer cardNumber){

        for (CreditAccount i : getCreditAccounts( )) {
            if (i.getCreditNumber( ).equals(cardNumber)) {
                CreditAccount creditAccountTemp = new CreditAccount(i);
                getDebitCreditAccounts( ).add(creditAccountTemp);
                getCreditAccounts( ).remove(i);
                return true;
            }
        }

    return false;}
    //************************************************************************************
    //BankAccountTransactionRecords

    public ArrayList<BankAccountTransactions> getBankAccountTransactions() {
        return bankAccountTransactions;
    }

    public void createBankAccountTransactionRecord(Integer titleId, String title, String usernameId, String accountType, String accountNumber, String cardType, String cardNumber, String amount) {
        BankAccountTransactions tempRecord = new BankAccountTransactions(titleId, title, usernameId, accountType, accountNumber, cardType, cardNumber, amount);
        bankAccountTransactions.add(tempRecord);

    }

    public void createBankAccountTransactionRecord(Integer titleId, String title, String usernameId, String accountType, String accountNumber, String amount) {
        BankAccountTransactions tempRecord = new BankAccountTransactions(titleId, title, usernameId, accountType, accountNumber, amount);
        bankAccountTransactions.add(tempRecord);

    }


    public ArrayList<String> getAllAccountsNumbers() {

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

    public ArrayList<String> getAllCardNumbers() {

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



