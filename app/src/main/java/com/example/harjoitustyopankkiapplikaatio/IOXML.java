package com.example.harjoitustyopankkiapplikaatio;

import android.content.Context;

import android.util.Xml;


import org.xmlpull.v1.XmlSerializer;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStreamWriter;
import java.io.StringWriter;
import java.util.ArrayList;
import java.util.Arrays;



public class IOXML {

    Context context = ContextClass.getAppContext( );
    private static IOXML ioXML = new IOXML( );
    String fileName = "AccountTransactions.xml";
    private ArrayList<String> subElementNames = new ArrayList<>(Arrays.asList("userID", "transactionId", "titleId", "title", "timeStamp", "accountType", "accountNumber", "cardType", "cardNumber", "amount"));

    private IOXML() {
    }

    public static IOXML getInstance() {

        return ioXML;
    }

    public void createRecordFile() {

        File file = context.getFileStreamPath(fileName);

        if (file == null || !file.exists( )) {

            try {
                OutputStreamWriter owsCreate = new OutputStreamWriter(context.openFileOutput(fileName, Context.MODE_PRIVATE));
                owsCreate.close( );
            } catch (FileNotFoundException e) {
                e.printStackTrace( );
            } catch (IOException e) {
                e.printStackTrace( );

            }

            try {
                FileOutputStream ows = context.openFileOutput(fileName, Context.MODE_APPEND);
                XmlSerializer xmlSerializer = Xml.newSerializer( );
                StringWriter writer = new StringWriter( );
                xmlSerializer.setOutput(writer);
                xmlSerializer.startDocument("UTF-8", false);
                xmlSerializer.endDocument( );
                xmlSerializer.flush( );
                String dataWrite = writer.toString( );
                ows.write(dataWrite.getBytes( ));
                ows.close( );
            } catch (IOException e) {
                e.printStackTrace( );
            }

        }
    }

    public void saveToXML(ArrayList<BankAccountTransactions> bankAccountTransactions)  {


        try {
            FileOutputStream ows = context.openFileOutput(fileName, Context.MODE_PRIVATE);
            XmlSerializer xmlSerializer = Xml.newSerializer( );
            StringWriter writer = new StringWriter( );
            xmlSerializer.setOutput(writer);
            xmlSerializer.startDocument("UTF-8", false);
            xmlSerializer.startTag(null, "UserAccountTransactions");

            for (BankAccountTransactions record : bankAccountTransactions) {

                xmlSerializer.startTag(null, "TRANSACTION");
                for (int i = 0; i < (subElementNames.size( )); i++) {
                    ArrayList<String>  subElementsValues = record.valuesToList( );
                    xmlSerializer.startTag(null, subElementNames.get(i));
                    xmlSerializer.text(subElementsValues.get(i));
                    xmlSerializer.endTag(null, subElementNames.get(i));
                }
                xmlSerializer.endTag(null, "TRANSACTION");
            }
            xmlSerializer.endTag(null, "UserAccountTransactions");
            xmlSerializer.endDocument( );
            xmlSerializer.flush( );
            String dataWrite = writer.toString( );
            ows.write(dataWrite.getBytes( ));

            ows.close( );
        } catch (IOException e) {
            e.printStackTrace( );
        }
    }
}
