package com.example.harjoitustyopankkiapplikaatio;

import java.util.ArrayList;

public class Bank {
    private static Bank bank = new Bank();
    private static Account logged;
    static String name;
    static ArrayList<Account>accounts;



//*************************************************************************************************************************************************
//Constructor


    private Bank(){
        this.name = "Mega Bank";
        this.accounts = new ArrayList<Account>();
    }

    public static Bank getInstance(){return bank;}
//*************************************************************************************************************************************************
//UserAccount Creation,

    //Return boolean value to tell if the account name is not used
     static  Boolean checkUsernameAvailability(String userNameCandidate) {

        for(Account a: accounts){
            if(a.getUserInfo().getUserName().equals(userNameCandidate)){
                return false;
            }
        }
        return true;
    }

    // Add the account to the Account list
     static Boolean addAccountToList(ArrayList<String> lista){
        try {
            if (SupportMethods.checkThereIsNoNulls(lista) && lista.size()==10){
                Account accountTemp = new Account(lista.get(0),lista.get(1),lista.get(2),lista.get(3),lista.get(4),lista.get(5),lista.get(6),lista.get(7),lista.get(8),lista.get(9));
                for(String s: lista){
                    System.out.println(s);
                }
                accounts.add(accountTemp);
                return true;
            }

        }catch (Exception e){
            return false;
        }
        return false;
    }
//*************************************************************************************************************************************************
//Login

    //Return boolean value to tell if the account name is in the accountlists userfinfo object
     static Boolean checkLoginUsername(String usernameTemp){
        for(Account a: accounts){
            if(a.getUserInfo().getUserName().equals(usernameTemp)){
                return true;
            }
        }
    return false;}

    //Return boolean value to tell if the account password matches with the account username
     static Boolean checkLoginPassword(String checkUsername, String passwordTemp){

        for(Account a: accounts){
            if(a.getUserInfo().getUserName().equals(checkUsername)){
                if((a.getUserInfo().getPassWord()).equals(passwordTemp)){
                    return true;
            }}
        }

        return false;}

     // makes account active and accesible tto other classes
     static void loggedIn(Account userAccountData) {
        Integer indexOfAccount=null;
        logged = userAccountData;
        for(Account a: accounts){
            if(a.getUserInfo().getId()==userAccountData.getUserInfo().getId())
            {
                indexOfAccount = accounts.indexOf(a);
            }
        }
        System.out.println(indexOfAccount);
        accounts.set(indexOfAccount,logged);
    }

     static void loggedOut() {
        logged = null;
    }

    public static Account getLoggedAccount( ) {
        return logged;
    }

     static Account IdentifyAccount(String checkUsername){
        Account tempAccount = null;
        for( Account a:accounts){
            if(a.getUserInfo().getUserName().equals(checkUsername)){
                tempAccount = a;
            }
        }


        return tempAccount;
    }

//*************************************************************************************************************************************************
    //Pay with account (takes user Id, account number, the account number to who the money will be sent and the amount of moneey)
    // these methods will seach for the account, update the balance and search the receivers account and updates that too.

     static Boolean payDebitCreditAccountDebit(int UserId, int UserDebitCreditAccountNumber, Integer receiverAccountNumberInt, Double amountToSendDouble) {

        Boolean isSubTransactionOk =false;
        Boolean isOverallTransactionOk =false;

        Integer debitAccountNumber= UserDebitCreditAccountNumber;


        // Making the transaction
        for(Account a: accounts) {
            if(a.getUserInfo().getId().equals(UserId)) {
                for(CreditAccount cd: a.getDebitCreditAccounts()) {
                    if(cd.getBankAccountNumber().equals(debitAccountNumber)){
                        isSubTransactionOk = cd.withdrawMoneyFromBankAccount(amountToSendDouble);

                        //Seaching for card balance's to be updated.

                        if(cd.getSuperCardsInAccount().size()>0){
                            for(SuperCard s: cd.getSuperCardsInAccount()){
                                if(s.getDebitCardHolder().getDebitBankNumber().equals(debitAccountNumber)){
                                    s.getDebitCardHolder().setDebitBalance(cd.getBalance());
                                }
                            }
                        }
                        if(cd.getDebitCardsInAccount().size()>0){
                            for(DebitCard dc: cd.getDebitCardsInAccount()){
                                if(dc.getDebitBankNumber().equals(debitAccountNumber)){
                                    dc.setDebitBalance(cd.getBalance());
                                }
                            }
                        }
                        break;
                    }
                }
            }
        }
            if(isSubTransactionOk==true) {

                // updates the balance of the receivers if account in found.
                sendMoneyToAnotherAccount(receiverAccountNumberInt,amountToSendDouble);
                isOverallTransactionOk = true;
            }

        return isOverallTransactionOk;}

     static Boolean payDebitCreditAccountCredit(int UserId, int UserCreditAccountNumber, Integer receiverAccountNumberInt, Double amountToSendDouble) {

        Boolean isSubTransactionOk =false;
        Boolean isOverallTransactionOk =false;

        for(Account a: accounts) {
            if(a.getUserInfo().getId().equals(UserId)) {
                for(CreditAccount cd: a.getDebitCreditAccounts()) {
                    if(cd.getCreditNumber().equals(UserCreditAccountNumber)){
                        isSubTransactionOk = cd.withdrawMoneyFromCreditAccount(amountToSendDouble);

                        //Seaching for card saldo's to be updated.

                        if(cd.getSuperCardsInAccount().size()>0){
                            for(SuperCard s: cd.getSuperCardsInAccount()){
                                if(s.getCreditCardHolder().getCreditAccountNumber().equals(UserCreditAccountNumber)){
                                    s.getCreditCardHolder().setCreditSaldo(cd.getCreditSaldo());
                                }
                            }
                        }

                        if(cd.getCreditCardsInAccount().size()>0){
                            for(CreditCard cc: cd.getCreditCardsInAccount()){
                                if(cc.getCreditAccountNumber().equals(UserCreditAccountNumber)){
                                    cc.setCreditSaldo(cd.getCreditSaldo());
                                }
                            }
                        }
                    }
                }
            }
        }

        if(isSubTransactionOk==true) {
            sendMoneyToAnotherAccount(receiverAccountNumberInt,amountToSendDouble);
            isOverallTransactionOk = true;
        }

        return isOverallTransactionOk;}

     static Boolean payDebitAccount(int UserId, int UserDebitAccountNumber, Integer receiverAccountNumberInt, Double amountToSendDouble) {

        Boolean isSubTransactionOk =false;
        Boolean isOverallTransactionOk =false;

        for(Account a: accounts) {
            if(a.getUserInfo().getId().equals(UserId)) {
                for(DebitAccount cd: a.getDebitAccounts()) {
                    if(cd.getBankAccountNumber().equals(UserDebitAccountNumber)){
                        isSubTransactionOk = cd.withdrawMoneyFromBankAccount(amountToSendDouble);
                    }
                    if(cd.getDebitCardsInAccount().size()>0){
                        for(DebitCard dc: cd.getDebitCardsInAccount()){
                            if(dc.getDebitBankNumber().equals(UserDebitAccountNumber)){
                                dc.setDebitBalance(cd.getBalance());
                            }
                        }
                    }
                }
            }
        }
        if(isSubTransactionOk==true) {
            sendMoneyToAnotherAccount(receiverAccountNumberInt,amountToSendDouble);
            isOverallTransactionOk = true;
        }

        return isOverallTransactionOk;}

     static Boolean payCreditAccount(int UserId, int UserCreditAccountNumber, Integer receiverAccountNumberInt, Double amountToSendDouble) {
        Boolean isSubTransactionOk =false;
        Boolean isOverallTransactionOk =false;

        for(Account a: accounts) {
            if(a.getUserInfo().getId().equals(UserId)) {
                for(CreditAccount cd: a.getCreditAccounts()) {
                    if(cd.getCreditNumber().equals(UserCreditAccountNumber)){
                        isSubTransactionOk = cd.withdrawMoneyFromCreditAccount(amountToSendDouble);
                    }

                    //Seaching for card saldo's to be updated.

                    if(cd.getCreditCardsInAccount().size()>0){
                        for(CreditCard cc: cd.getCreditCardsInAccount()){
                            if(cc.getCreditAccountNumber().equals(UserCreditAccountNumber)){
                                cc.setCreditSaldo(cd.getCreditSaldo());
                            }
                        }
                    }
                }
            }
        }

        if(isSubTransactionOk==true) {
            sendMoneyToAnotherAccount(receiverAccountNumberInt,amountToSendDouble);
            isOverallTransactionOk = true;
        }

        return isOverallTransactionOk;}

    //Pay with card t (takes user Id, card number, the account number to who the money will be sent and the amount of moneey)
    // these methods will seach for the card and identify it with the account, update the balance and search the receivers account and updates that too.

     static Boolean payDebitCreditCard(Integer UserId, Integer superCardId, Integer receiverAccountNumberInt, Double amountToSendDouble, boolean payCredit) {
        Boolean isSubTransactionOk =false;
        Boolean isOverallTransactionOk =false;

        if(payCredit==true) {
            for (Account a : accounts) {
                if (a.getUserInfo( ).getId( ).equals(UserId)) {
                    for (CreditAccount cd : a.getDebitCreditAccounts( )) {
                        for(SuperCard s:cd.getSuperCardsInAccount( )) {
                                if(s.getId().equals(superCardId)){
                                    if(s.getCreditCardHolder().getPaymentLimitCredit()>=amountToSendDouble){
                                    isSubTransactionOk = Bank.payDebitCreditAccountCredit(UserId,cd.getCreditNumber(),receiverAccountNumberInt,amountToSendDouble);
                                    s.getCreditCardHolder().setCreditSaldo(cd.getCreditSaldo());
                                    break;
                                    }else{
                                        return null;
                                    }

                                }
                        }
                    }
                }
            }
        }
        else{
            for (Account a : accounts) {
                if (a.getUserInfo( ).getId( ).equals(UserId)) {
                    for (CreditAccount cd : a.getDebitCreditAccounts( )) {
                        for(SuperCard s:cd.getSuperCardsInAccount( )) {
                            if(s.getId().equals(superCardId)){
                                if(s.getDebitCardHolder().getPaymentLimitDebit()>=amountToSendDouble){
                                isSubTransactionOk = Bank.payDebitCreditAccountDebit(UserId,cd.getBankAccountNumber(),receiverAccountNumberInt,amountToSendDouble);
                                s.getDebitCardHolder().setDebitBalance(cd.getBalance());
                                break;
                                }else{
                                    return null;
                                }
                            }
                        }
                    }
                }
            }
        }

        if(isSubTransactionOk==true) {
            isOverallTransactionOk = true;
        }

        return isOverallTransactionOk;
    }

     static Boolean payDebitCard(Integer UserId, Integer debitCardNumber, Integer receiverAccountNumberInt, Double amountToSendDouble) {
        Boolean isSubTransactionOk =false;
        Boolean isOverallTransactionOk =false;

        for (Account a : accounts) {
            if (a.getUserInfo( ).getId( ).equals(UserId)) {
                for (DebitAccount d : a.getDebitAccounts( )) {
                    for(DebitCard dc:d.getDebitCardsInAccount( )) {
                        if(dc.getDebitCardNumber().equals(debitCardNumber)){
                            if(dc.getPaymentLimitDebit()>=amountToSendDouble){
                            isSubTransactionOk = Bank.payDebitAccount(UserId,dc.getDebitBankNumber(),receiverAccountNumberInt,amountToSendDouble);
                            dc.setDebitBalance(d.getBalance());
                            break;
                        }else{
                            return null;
                        }
                        }
                    }
                }
            }
        }

        if(isSubTransactionOk==true) {
            isOverallTransactionOk = true;
        }

        return isOverallTransactionOk;
    }

     static Boolean payCreditCard(Integer UserId, Integer creditCardNumber, Integer receiverAccountNumberInt, Double amountToSendDouble){

        Boolean isSubTransactionOk =false;
        Boolean isOverallTransactionOk =false;

        for (Account a : accounts) {
            if (a.getUserInfo( ).getId( ).equals(UserId)) {
                for (CreditAccount ca : a.getCreditAccounts( )) {
                    for(CreditCard cc:ca.getCreditCardsInAccount( )) {
                        if(cc.getcreditCardNumber().equals(creditCardNumber)){
                            if(cc.getPaymentLimitCredit()>=amountToSendDouble){
                            isSubTransactionOk = Bank.payCreditAccount(UserId,cc.getCreditAccountNumber(),receiverAccountNumberInt,amountToSendDouble);
                            cc.setCreditSaldo(ca.getCreditSaldo());
                            break;
                        }else{
                            return null;
                        }
                        }
                    }
                }
            }
        }

        if(isSubTransactionOk==true) {
            isOverallTransactionOk = true;
        }

        return isOverallTransactionOk;
    }
//*************************************************************************************************************************************************
//Get cards with bank numbers

    // Since creditDebit cards have debit account and credit account, these  features return the other account number.
     static Integer getCreditDebitCardCreditAccountNumber(Integer accountNumber) {

        Integer creditAccountTemp = null;

        for (Account a : accounts) {
            if(a.getDebitCreditAccounts().size()>0){
                for (CreditAccount cd : a.getDebitCreditAccounts( )) {
                    if(cd.getSuperCardsInAccount().size()>0){
                        for (SuperCard s : cd.getSuperCardsInAccount( )) {
                            if (s.getId( ).equals(accountNumber)) {

                                creditAccountTemp = s.getCreditCardHolder( ).getCreditAccountNumber( );


                                break;
                            }
                        }
                    }
                }
            }
        }

        return creditAccountTemp;
    }

     static Integer getCreditDebitCardDebitAccountNumber(Integer accountNumber){

        Integer debitAccountTemp = null;

        for (Account a : accounts) {
            if(a.getDebitCreditAccounts().size()>0){
                for (CreditAccount cd : a.getDebitCreditAccounts( )) {
                    if(cd.getSuperCardsInAccount().size()>0) {
                        for (SuperCard s : cd.getSuperCardsInAccount( )) {
                            if (s.getId( ).equals(accountNumber)) {
                                debitAccountTemp = s.getDebitCardHolder( ).getDebitCardNumber( );
                                break;
                            }
                        }
                    }
                }
            }
        }
        return debitAccountTemp;
    }

    // These features will return the account number of the card
     static Integer getDebitCardAccountNumber(Integer accountNumber){

        Integer debitAccountTemp = null;

        for (Account a : accounts) {
            if(a.getDebitAccounts().size()>0){

                for (DebitAccount da : a.getDebitAccounts( )) {

                    if(da.getDebitCardsInAccount().size()>0) {

                        for (DebitCard dc : da.getDebitCardsInAccount( )) {
                            if (dc.getDebitCardNumber().equals(accountNumber)) {
                                debitAccountTemp = dc.getDebitBankNumber( );
                                break;
                            }
                        }
                    }
                }
            }
        }

        return debitAccountTemp;

    }

     static Integer getCreditCardAccountNumber(Integer accountNumber) {

        Integer creditCardTemp = null;

        for (Account a : accounts) {
            if (a.getDebitAccounts( ).size( ) > 0) {
                for (CreditAccount ca : a.getCreditAccounts( )) {
                    if (ca.getCreditCardsInAccount( ).size( ) > 0) {
                        for (CreditCard dc : ca.getCreditCardsInAccount( )) {
                            if (dc.getcreditCardNumber( ).equals(accountNumber)) {
                                creditCardTemp = dc.getcreditCardNumber();
                                break;
                            }
                        }
                    }
                }
            }
        }
        return creditCardTemp;
    }


//*************************************************************************************************************************************************
//Assistance methods
    private static void sendMoneyToAnotherAccount(Integer receiverAccountNumberInt, Double amountToSendDouble) {

        for (Account a : accounts) {

            //Checking for Account from DebitCreditAccounts
            for (CreditAccount cd : a.getDebitCreditAccounts( )) {
                if (cd.getBankAccountNumber( ).equals(receiverAccountNumberInt)) {
                    cd.addMoneyToDebitAccount(amountToSendDouble);
                    break;
                }

                if (cd.getCreditNumber( ).equals(receiverAccountNumberInt)) {
                    cd.addMoneyToCreditAccount(amountToSendDouble);
                    break;
                }
            }
            //Checking for Account from DebitAccounts
            for (DebitAccount d : a.getDebitAccounts( )) {
                if (d.getBankAccountNumber( ).equals(receiverAccountNumberInt)) {
                    d.addMoneyToDebitAccount(amountToSendDouble);
                    break;
                }
            }

            //Checking for Account from CreditAccounts
            for (CreditAccount c : a.getCreditAccounts( )) {
                if (c.getCreditNumber( ).equals(receiverAccountNumberInt)) {
                    c.addMoneyToCreditAccount(amountToSendDouble);
                    break;

                }
            }
        }
    }

     static Boolean checkIfMasterUser(String checkUsername, String checkPassword) {
        if (checkUsername.equals("master") && checkPassword.equals("account")) {
            return true;
        }
    return false;}

     static ArrayList<Account> getAccounts() {
        return accounts;
    }

     static void deleteAccount(Integer id) {
        for (Account a : accounts){
            if(a.getUserInfo().getId() == id){
                accounts.remove(a);
            }


    }
    }
}


