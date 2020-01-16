package com.example.harjoitustyopankkiapplikaatio;


// General (E.g. father class) bank account asw well as debit bank account.


// credit -tilien ID-laskija  on korjattava.
import java.util.ArrayList;

public class DebitAccount {


    static protected Integer count = 110000;
    protected Integer bankNumber ;
    protected Double balance = 0.00;
    private String debitAccountName;
    protected Boolean isAccountActive= true;
    private Boolean isDebitActive= true;
    protected Boolean isMutable = true;
    protected  ArrayList<DebitCard> debitCardsInAccount;



    //**********************************************************//
    //Builders


    //Activated Debit
    DebitAccount(String debitAccountNameTemp){
        this.count ++;
        this.bankNumber = count;
        this.debitAccountName = debitAccountNameTemp;
        this.debitCardsInAccount = new ArrayList<>();
    }

    //Activated Debit with deposit money
    DebitAccount(String debitAccountNameTemp, Double balanceTemp){
        this.count ++;
        this.bankNumber = count;
        this.debitAccountName= debitAccountNameTemp;
        this.balance = balanceTemp;
        this.debitCardsInAccount = new ArrayList<>();
    }

    //Deactivated debit card for the "only credit card method"
    DebitAccount(Boolean valueForDeactivation){
        this.bankNumber =null;
        this.balance = null;
        this.isDebitActive = valueForDeactivation;
        this.debitCardsInAccount = new ArrayList<>();
    }

    // From "DebitAccount to DebitCreditAccount
    DebitAccount( DebitAccount another){
        this.bankNumber  = another.bankNumber;
        this.debitAccountName= another.debitAccountName;
        this.balance = another.balance;
        this.isDebitActive = true;
        this.debitCardsInAccount = another.getDebitCardsInAccount();
    }

    //**********************************************************//



    public Integer getBankAccountNumber(){ return bankNumber;}

    public Double getBalance(){return balance;}

    protected Boolean  withdrawMoneyFromBankAccount(double d){
        if (balance>=d) {
            balance =balance- d;
        return true;}
        else{ return false;}
    }

    protected void addMoneyToDebitAccount(Double d){
        balance = balance +d;
    }


    // Getters

    public String getDebitAccountName(){
        return debitAccountName;
}

    public ArrayList<DebitCard> getDebitCardsInAccount() {
        return debitCardsInAccount;
    }

    //Setterrs

    public void SetDebitCardToDebitCardList( DebitCard debitcardTemp){
        debitCardsInAccount.add(debitcardTemp);
    }

    public void setDebitAccountName(String newNameTemp) {
        this.debitAccountName = newNameTemp;
    }

    public void removeDebitAccount() {
        this.count = 0;
        this.bankNumber =  null;
        this.balance = 0.00;
        this.debitAccountName = null;
        this.isAccountActive = false;
        this.isDebitActive= false;
        this.isMutable = false;


    }
    public Boolean isDebitActive(){
            return isDebitActive;}

    protected void setDebitActive(boolean b) {
        this.isDebitActive = b;
    }

    public String getName() {
        return debitAccountName;
    }

    public String getAccountNumber() {
        return bankNumber.toString();
    }

    public String getMoney() {
        return balance.toString();
    }
}
