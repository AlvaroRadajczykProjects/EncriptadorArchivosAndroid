package com.example.encriptadorarchivos

import android.content.Intent
import android.net.Uri
import android.provider.DocumentsContract
import android.util.Log
import androidx.fragment.app.Fragment
import java.io.File
import java.io.FileInputStream
import java.io.FileNotFoundException
import java.io.FileOutputStream
import java.io.IOException

class GestorArchivosAndroid {

    companion object {
        const val READ_FILE = 0
        const val CREATE_FILE = 1

        /*
        @Throws(Exception::class)
        fun crearArchivoVacioAbriendoGestorCarpetas(directorio: String, nombre: String, tipo: String, fragment: Fragment): Unit {
            //"/storage/emulated/0/Documents/"
            val pickerInitialUri = Uri.Builder().path(directorio).build()
            val intent = Intent(Intent.ACTION_CREATE_DOCUMENT).apply {
                addCategory(Intent.CATEGORY_OPENABLE)
                type = "application/$tipo"
                putExtra(Intent.EXTRA_TITLE, "$nombre.$tipo")

                // Optionally, specify a URI for the directory that should be opened in
                // the system file picker before your app creates the document.
                putExtra(DocumentsContract.EXTRA_INITIAL_URI, pickerInitialUri)
            }
            fragment.startActivityForResult(intent, CREATE_FILE)
        }*/

        @Throws(Exception::class)
        fun modificarCrearArchivo(directorio: String, nombre: String, tipo: String, contenido: String, fragment: Fragment): Unit {
            val contentResolver = fragment.requireContext().contentResolver
            val root: File = android.os.Environment.getExternalStorageDirectory()
            val uri = Uri.fromFile(File("$directorio$nombre.$tipo"))
            contentResolver.openFileDescriptor(uri, "w")?.use {
                FileOutputStream(it.fileDescriptor).use {
                    it.write(
                        contenido.toByteArray()
                    )
                }
            }
        }

        fun obtenerContenidoArchivo(directorio: String, nombre: String, tipo: String, fragment: Fragment): String {
            val contentResolver = fragment.requireContext().contentResolver
            val root: File = android.os.Environment.getExternalStorageDirectory()
            val uri = Uri.fromFile(File("$directorio$nombre.$tipo"))
            try {
                contentResolver.openFileDescriptor(uri, "r")?.use {
                    FileInputStream(it.fileDescriptor).use {
                        var array: ByteArray = ByteArray(it.available())
                        it.read(array,0,it.available())
                        return String(array)
                    }
                }
            } catch (e: FileNotFoundException) {
                Log.e("GestorArchivosAndroid", "No se encuentra el archivo: ${e.message}")
            } catch (e: IOException) {
                Log.e("GestorArchivosAndroid", "Error de entrada/salida: ${e.message}")
            }
            return ""
        }

    }
}