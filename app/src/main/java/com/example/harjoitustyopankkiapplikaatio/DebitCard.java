package com.example.harjoitustyopankkiapplikaatio;

class DebitCard implements Cloneable {
    static protected Integer count = 100000;
    private Integer DebitCardNumber= 0;
    private String debitCardName;
    private Integer paymentLimit;
    private Integer debitBankNumber;
    private Double debitBalance;
    private Boolean isActive;

    public DebitCard(String debitCardName, Integer paymentLimit, Integer debitBankNumber, Double debitBalance) {

        this.count ++;
        DebitCardNumber = count;
        this.debitCardName = debitCardName;
        this.paymentLimit = paymentLimit;
        this.debitBankNumber = debitBankNumber;
        this.debitBalance = debitBalance;
        this.isActive = true;

    }


    public Integer getDebitCardNumber(){
        return DebitCardNumber;
    }

    public Double getDebitBalance() {
        return debitBalance;
    }

    public Integer getDebitBankNumber() {
        return debitBankNumber;
    }

    public Integer getPaymentLimit() {
        return paymentLimit;
    }

    public String getdebitCardName() {
        return debitCardName;
    }

    public void setDebitCardName(String newNameTemp) {
        this.debitCardName = newNameTemp;
    }

    public void setPaymentLimitDebit(Integer newLimitTempInt) {
        this.paymentLimit = newLimitTempInt;
    }

    public Integer getPaymentLimitDebit() {return paymentLimit;
    }

    public void removeDebitCard() {
        DebitCardNumber = null;
        this.debitCardName = null;
        this.paymentLimit = null;
        this.debitBankNumber = null;
        this.debitBalance = null;
        this.isActive = false;

    }

    public boolean getIsAcvice() { return isActive;
    }

    public DebitCard getDebitCardClone() throws CloneNotSupportedException {
        return (DebitCard) this.clone();
    }



    public void setDebitBalance(Double DebitBalance) {
        debitBalance = DebitBalance;
    }
}
