package com.example.harjoitustyopankkiapplikaatio;

import java.util.ArrayList;

public class CreditAccount extends DebitAccount implements Cloneable {

    static protected Integer count = 220000;
    protected Integer creditNumber = 0;
    private String creditAccountName;
    private Integer creditLimit;
    private Double usedCredit = 0.00;
    protected ArrayList<CreditCard> creditCardsInAccount;
    protected ArrayList<SuperCard> superCardsInAccount;
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




    public Boolean useCredit(double withdrawFromCredit){
        if (withdrawFromCredit <= (creditLimit-usedCredit)){
            usedCredit+=withdrawFromCredit;
            return true;
        }
        else{return false;
        }

    }

    public void paymentToCredit(double paymentToCredit){
        usedCredit -= paymentToCredit;
    }

    private void changeCreditLimit(Integer newLimit){
        creditLimit = newLimit; }

    protected void deActivateDebitAccountFromCreditAccount() {
        if (this.isMutable == true) {
            this.usedCredit = -balance;
            super.setDebitActive(false);
            this.balance = null;
            isMutable = false;
        }
    }

    protected void activateDebitAccountFromCreditAccount(){
        this.isMutable = true;
        super.setDebitActive(true);
        this.balance = 0.00;

        }

    protected void activeCreditAccountCredit(int creditLimitTemp){
        creditLimit = creditLimitTemp;

    }

    protected void deActiveCreditAccountCredit(){
        isCreditActive = false;
        creditLimit = 0;

    }
    //**********************************************************//
    // Getters

    public Integer getCreditLimit(){return creditLimit;}

    public Double getCreditSaldo(){ return (creditLimit-usedCredit);}

    public Integer getCreditNumber() {
        return creditNumber;
    }

    public String getCreditAccountName(){
        return creditAccountName;
    }

    public ArrayList<SuperCard> getSuperCardsInAccount() {
        return superCardsInAccount;
    }

    public ArrayList<CreditCard> getCreditCardsInAccount() {
        return creditCardsInAccount;
    }

    // Setters

    public void SetCreditCardToCreditCardList(CreditCard creditCardTemp){
        creditCardsInAccount.add(creditCardTemp);
    }

    public void SetSuperCardToCreditCardList(SuperCard superCardTemp){
        superCardsInAccount.add(superCardTemp);
    }


    public boolean isCreditActive() {
        return isCreditActive;
    }
    public DebitAccount transformToDebitAccount() throws CloneNotSupportedException {
        return (DebitAccount) this.clone();
    }

    public CreditAccount transformToCreditAccount() throws CloneNotSupportedException {
        CreditAccount tempCreditAccount = (CreditAccount) this.clone();
        tempCreditAccount.removeDebitAccount();
        return tempCreditAccount;
    }

    protected void addMoneyToCreditAccount(Double d){
        usedCredit =usedCredit-d;
    }

    public Boolean withdrawMoneyFromCreditAccount(Double amountToSendDouble) {
        if(getCreditSaldo()>=amountToSendDouble){
            usedCredit= usedCredit+amountToSendDouble;
                    return true;
        }else{return false;}
    }

    public void setCreditLimit(Integer newCreditLimit) {

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


