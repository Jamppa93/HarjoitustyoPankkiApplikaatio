package com.example.harjoitustyopankkiapplikaatio;

public class UserInfo {

    protected Integer id;
    private String userName;
    private String password;


    UserInfo(Integer id, String userNameTemp, String passWordTemp){
        this.id = id;
        this.userName = userNameTemp;
        this.password = passWordTemp;
    }

    public Integer getId(){ return id;}
     String getUserName(){ return userName;}
     String getPassWord(){ System.out.println( userName +password);return password;}

     void setUsername(String usernameTemp ) {
        userName = usernameTemp;
    }

     void setPassword(String passwordTemp ) {
        password = passwordTemp;
    }


}
