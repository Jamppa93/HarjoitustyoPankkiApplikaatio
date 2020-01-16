package com.example.harjoitustyopankkiapplikaatio;


import java.util.ArrayList;
import java.util.Date;

public class BankAccountTransactions {

    //***************************************************************//
    //Declarations
    private Integer count = 900000;
    private Integer transactionId;
    private Integer titleId;
    private String title;
    private Date timeStamp;
    private String usernameId;
    private String accountType;
    private String accountNumber;
    private String cardType="";
    private String cardNumber="";
    private String amount;



    // TransferMoneyViaAccount
    BankAccountTransactions( Integer titleId, String title,String usernameId,String accountType,String accountNumber,String cardType,String cardNumber, String amount) {

        this.count++;
        this.transactionId = count;
        this.titleId = titleId;
        this.title = title;
        this.timeStamp = new Date( );
        this.usernameId = usernameId;
        this.accountType = accountType;
        this.accountNumber = accountNumber;
        this.cardType = cardType;
        this.cardNumber = cardNumber;
        this.amount = amount;
    }

    BankAccountTransactions( Integer titleId, String title, String usernameId, String accountType, String accountNumber, String amount) {
        this.count++;
        this.transactionId = count;
        this.titleId = titleId;
        this.title = title;
        this.timeStamp = new Date( );
        this.usernameId =usernameId;
        this.accountType = accountType;
        this.accountNumber= accountNumber;
        this.amount = amount;

    }

    public int getTitleId() {
        return titleId;
    }

    public Date getTimeStamp() {
        return timeStamp;
    }

    public int getTransactionId() {
        return transactionId;
    }

    public String getAccountNumber() {
        return accountNumber;
    }

    public String getAccountType() {
        return accountType;
    }

    public String getCardNumber() {
        return cardNumber;
    }

    public String getCardType() {
        return cardType;
    }

    public String getTitle() {
        return title;
    }

    public String getUserId() {
        return usernameId;
    }

    public String getAmount() {
        return amount;
    }

    public ArrayList<String> valuesToList(){
        ArrayList<String> valueList =new ArrayList();
        valueList.add(usernameId);
        valueList.add(transactionId.toString());
        valueList.add(titleId.toString());
        valueList.add(title);
        valueList.add(timeStamp.toString());
        valueList.add(accountType);
        valueList.add(accountNumber);
        valueList.add(cardType);
        valueList.add(cardNumber);
        valueList.add(amount);
        return valueList;

    }
}