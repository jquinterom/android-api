package co.com.ceiba.mobile.pruebadeingreso.utilities

import android.content.Context
import android.support.v7.app.AlertDialog
import co.com.ceiba.mobile.pruebadeingreso.R


class Utilities {

    // Dialogo para mostrar en la carga de datos
    fun progressDialog(context: Context): AlertDialog {
        val builder: AlertDialog.Builder = AlertDialog.Builder(context)
        builder.setCancelable(false) // if you want user to wait for some process to finish,
        builder.setView(R.layout.loading_dialog)

        return builder.create();
    }
}