package com.example.harjoitustyopankkiapplikaatio;

import java.util.ArrayList;

public class CreditAccount extends DebitAccount implements Cloneable {

    static private Integer count = 220000;
    private Integer creditNumber = 0;
    private String creditAccountName;
    private Integer creditLimit;
    private Double usedCredit = 0.00;
    private ArrayList<CreditCard> creditCardsInAccount;
    private ArrayList<SuperCard> superCardsInAccount;
    private Boolean isCreditActive;


    //**********************************************************//
    //Builders




    CreditAccount(String debitAccountName,String creditAccountName, Integer creditLimit){
        super(debitAccountName);
        this.creditAccountName = creditAccountName;
        this.isCreditActive = true;
        this.creditNumber +=1 ;
        this.creditLimit = creditLimit;
    }


    CreditAccount(CreditAccount another){
        super(another.creditAccountName);
        this.creditNumber =another.creditNumber;
        this.isCreditActive =another.isCreditActive;
        super.setDebitActive(true);
        this.creditLimit =another.creditLimit;
        this.usedCredit =another.usedCredit;
        this.creditCardsInAccount =another.creditCardsInAccount;
        this.superCardsInAccount =another.superCardsInAccount;

    }

    CreditAccount(DebitAccount debitAccountTemp){
        super(debitAccountTemp);

        this.count ++;
        creditNumber = count;
        this.creditAccountName = debitAccountTemp.getDebitAccountName();
        this.isCreditActive = true;
        super.setDebitActive(true);
        this.creditLimit = 10000;
        this.usedCredit = 0.00;
        this.creditCardsInAccount = new ArrayList<>();
        this.superCardsInAccount = new ArrayList<>();

    }


    //Create debit and credit with deposit money
    CreditAccount(String creditDebitAccountName, Double DebitBalance, Integer creditLimit){
        super(creditDebitAccountName,DebitBalance);

        this.count ++;
        creditNumber = count;
        this.creditAccountName = creditDebitAccountName;
        this.isCreditActive = true;
        super.setDebitActive(true);
        this.creditLimit = creditLimit;
        this.usedCredit = 0.00;
        this.creditCardsInAccount = new ArrayList<>();
        this.superCardsInAccount = new ArrayList<>();

    }

    // Create credit and delete debit object
    CreditAccount(Boolean isDebitActive,String creditAccountName,Integer creditLimit){
        super(isDebitActive);
        this.count ++;
        creditNumber = count;
        this.creditAccountName = creditAccountName;
        this.isCreditActive = true;
        super.setDebitActive(isDebitActive);
        this.creditLimit = creditLimit;
        this.usedCredit = 0.00;
        this.creditCardsInAccount = new ArrayList<>();
        this.superCardsInAccount = new ArrayList<>();
    }
    //**********************************************************//

    //**********************************************************//
    // Getters


     Double getCreditSaldo(){ return (creditLimit-usedCredit);}

     Integer getCreditNumber() {
        return creditNumber;
    }

     String getCreditAccountName(){
        return creditAccountName;
    }

     ArrayList<SuperCard> getSuperCardsInAccount() {
        return superCardsInAccount;
    }

     ArrayList<CreditCard> getCreditCardsInAccount() {
        return creditCardsInAccount;
    }

    // Setters

     void SetCreditCardToCreditCardList(CreditCard creditCardTemp){
        creditCardsInAccount.add(creditCardTemp);
    }

     void SetSuperCardToCreditCardList(SuperCard superCardTemp){
        superCardsInAccount.add(superCardTemp);
    }
    // activate the account credit account
     boolean isCreditActive() {
        return isCreditActive;
    }


    // clones the clone
     DebitAccount transformToDebitAccount() throws CloneNotSupportedException {
        return (DebitAccount) this.clone();
    }

    // clone the object and transforms it to CreditAccount object
     CreditAccount transformToCreditAccount() throws CloneNotSupportedException {
        CreditAccount tempCreditAccount = (CreditAccount) this.clone();
        tempCreditAccount.removeDebitAccount();
        return tempCreditAccount;
    }

    // updates the balance
     void addMoneyToCreditAccount(Double d){
        usedCredit =usedCredit-d;
    }

    // updates the saldo
     Boolean withdrawMoneyFromCreditAccount(Double amountToSendDouble) {
        if(getCreditSaldo()>=amountToSendDouble){
            usedCredit= usedCredit+amountToSendDouble;
                    return true;
        }else{return false;}
    }

     void setCreditLimit(Integer newCreditLimit) {

        creditLimit = newCreditLimit;
    }

    @Override
    public String getName() {
        return creditAccountName;
    }

    @Override
    public String getAccountNumber() {
        return (getBankAccountNumber().toString()+"/"+ creditNumber.toString());
    }
    @Override
    public String getMoney() {
        String s = this.getBalance().toString();
        s = s+"/"+getCreditSaldo().toString();
        return  s;
    }


}


