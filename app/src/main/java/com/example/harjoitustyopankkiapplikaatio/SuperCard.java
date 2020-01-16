package com.example.harjoitustyopankkiapplikaatio;

class SuperCard {

    static private Integer count = 300000;
    protected Integer id= 0;
    private DebitCard debitCardHolder;
    private CreditCard creditCardHolder;

    public SuperCard(DebitCard debitCardTemp, CreditCard creditCardTemp) {
        this.count ++;
        id = count;
        this.debitCardHolder = debitCardTemp;
        this.creditCardHolder = creditCardTemp;
    }

    public SuperCard(SuperCard another) {
        this.creditCardHolder = another.creditCardHolder;
    }

    //Getters

    public Integer getId(){
        return id;
    }

    public CreditCard getCreditCardHolder() {
        return creditCardHolder;
    }

    public DebitCard getDebitCardHolder() {
        return debitCardHolder;
    }




}
