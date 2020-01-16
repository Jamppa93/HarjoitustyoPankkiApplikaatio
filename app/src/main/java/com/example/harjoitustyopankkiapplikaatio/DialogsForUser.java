package com.example.harjoitustyopankkiapplikaatio;

import android.app.AlertDialog;
import android.app.Dialog;

import android.os.Bundle;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatDialogFragment;

public class DialogsForUser extends AppCompatDialogFragment {
    @Override
    public Dialog onCreateDialog(@Nullable Bundle savedInstanceState) {
        AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
        builder.setTitle("GREAT SUCCESS!!!")
            .setMessage("The account has been made! Returning to main menu.");
        return builder.create();
    }
}
//Reference https://www.youtube.com/watch?v=Bsm-BlXo2SI