package com.example.harjoitustyopankkiapplikaatio;

public class UserInfo {

    protected Integer id;
    protected String userName;
    protected String password;


    UserInfo(Integer id, String userNameTemp, String passWordTemp){
        this.id = id;
        this.userName = userNameTemp;
        this.password = passWordTemp;
    }

    public Integer getId(){ return id;}
    public String getUserName(){ return userName;}
    public String getPassWord(){ System.out.println( userName +password);return password;}

    public void setUsername(String usernameTemp ) {
        userName = usernameTemp;
    }

    public void setPassword(String passwordTemp ) {
        password = passwordTemp;
    }


}
