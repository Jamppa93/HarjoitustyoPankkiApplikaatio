package com.example.harjoitustyopankkiapplikaatio;

class CreditCard implements Cloneable{

    static protected Integer count = 200000;
    private Integer creditCardNumber= 0;
    private String creditCardName;
    private Integer paymentLimit;
    private Integer creditAccountNumber;
    private Double creditSaldo;
    private Boolean isActive;


    public CreditCard(String creditCardName, Integer paymentLimit, Integer creditAccountNumber, Double creditSaldo) {

        this.count ++;
        creditCardNumber = count;
        this.creditCardName = creditCardName;
        this.paymentLimit = paymentLimit;
        this.creditAccountNumber = creditAccountNumber;
        this.creditSaldo = creditSaldo;
        this.isActive = true;
    }

    public Integer getcreditCardNumber(){
        return creditCardNumber;


    }

    public Double getCreditSaldo() {
        return creditSaldo;
    }

    public String getCreditCardName() {
        return creditCardName;
    }

    public Integer getPaymentLimitCredit() {
        return paymentLimit;
    }

    public Integer getCreditAccountNumber() {
        return creditAccountNumber;
    }

    public void setCreditCardname(String newNameTemp) {
        this.creditCardName = newNameTemp;
    }

    public void setPaymentLimitCredit(Integer newLimitTemp) {
        this.paymentLimit = newLimitTemp;
    }

    public void removeCrediCard() {
        creditCardNumber = null;
        this.creditCardName = null;
        this.paymentLimit = null;
        this.creditAccountNumber = null;
        this.creditSaldo = null;
        this.isActive = false;

    }

    public boolean getIsAcvice() {
        return this.isActive;
    }

    public CreditCard getCreditCardClone() throws CloneNotSupportedException {
        return (CreditCard) this.clone();
    }


    public void setCreditSaldo(Double newCreditSaldo) {
        creditSaldo = newCreditSaldo;

    }

}


