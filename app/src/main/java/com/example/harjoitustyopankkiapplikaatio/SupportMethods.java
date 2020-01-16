package com.example.harjoitustyopankkiapplikaatio;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;

//SINGLETON
public class SupportMethods {

    private static SupportMethods supportMethods = new SupportMethods( );

    private SupportMethods() {
    }

    public static SupportMethods getInstance() {
        return supportMethods;
    }

    public static Boolean isStringOnlyAlphabet(String str) {
        return ((!str.equals("")) && (str != null) && (str.matches("^[a-zA-Z]*$")));
    }

    public static Boolean isStringOnlyNumeric(String str) {
        return ((!str.equals("")) && str.matches("^[+-]?\\d+$") && (str != null));
    }

    public static Boolean isStringDateFormat(String str) throws ParseException {

        Date date=new SimpleDateFormat("dd/MM/yyyy").parse(str);

        if (str.length()==10)
            return true;
        else{
           return false;
        }
    }

    public static Boolean isStringEmailAddress(String str) {
        return ((!str.equals("")) && str.contains("@") && (str != null));
    }

    public static Boolean checkThereIsNoNulls(ArrayList<String> lista){
        return !lista.contains(null);
    }






}




// Reference https://www.geeksforgeeks.org/check-if-a-string-contains-only-alphabets-in-java-using-regex/


