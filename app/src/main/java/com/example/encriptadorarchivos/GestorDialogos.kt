package com.example.encriptadorarchivos

import android.app.AlertDialog
import android.content.DialogInterface
import androidx.fragment.app.Fragment

class GestorDialogos {

    companion object {

        fun dialogoInformativo(titulo:String, mensaje:String, fragmento: Fragment): AlertDialog? {
            val builder: AlertDialog.Builder = AlertDialog.Builder(fragmento.requireActivity())
            builder.setTitle(titulo)
                .setMessage(mensaje)
                .setPositiveButton("OK") { dialog, which -> }
            return builder.create()
        }

        fun dialogoSiNo(titulo:String, mensaje:String, funsi: () -> Unit, funno: () -> Unit, fragmento: Fragment): AlertDialog? {
            val builder: AlertDialog.Builder = AlertDialog.Builder(fragmento.requireActivity())
            builder.setTitle(titulo)
                .setMessage(mensaje)
                .setPositiveButton("SI") { dialog, which ->
                    funsi()
                }
                .setNegativeButton("NO") { dialog, which ->
                    funno()
                }
            return builder.create()
        }

    }

}