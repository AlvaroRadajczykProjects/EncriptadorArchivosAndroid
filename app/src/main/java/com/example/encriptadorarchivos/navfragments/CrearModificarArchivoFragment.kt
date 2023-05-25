package com.example.encriptadorarchivos.navfragments

import android.net.Uri
import android.os.Bundle
import android.text.Editable
import android.text.method.HideReturnsTransformationMethod
import android.text.method.PasswordTransformationMethod
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import com.example.encriptadorarchivos.GestorArchivosAndroid
import com.example.encriptadorarchivos.GestorDialogos
import com.example.encriptadorarchivos.GestorEncriptacionDecriptacion
import com.example.encriptadorarchivos.databinding.FragmentCrearModificarArchivoBinding
import java.io.File

class CrearModificarArchivoFragment : Fragment() {

    private lateinit var binding: FragmentCrearModificarArchivoBinding

    private var param1: String? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        //arguments?.let { param1 = it.getString(ARG_PARAM1) }
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {

        binding = FragmentCrearModificarArchivoBinding.inflate(layoutInflater)

        requireArguments().getString("nombre")?.let { cambiarNombre(it) }
        requireArguments().getString("contra")?.let { cambiarContra(it) }
        requireArguments().getString("contenido")?.let { cambiarContenido(it) }

        binding.bcancelar.setOnClickListener {
            requireActivity().onBackPressed()
        }

        binding.switch1.setOnClickListener {
            if(binding.switch1.isChecked){
                binding.editTextTextPassword.transformationMethod = HideReturnsTransformationMethod.getInstance()
            } else {
                binding.editTextTextPassword.transformationMethod = PasswordTransformationMethod.getInstance()
            }
        }

        binding.bguardar.setOnClickListener {
            val contenido: String = binding.textarea.text.toString()
            val nombre: String = binding.nombrearchivo.text.toString()
            val contra: String = binding.editTextTextPassword.text.toString()

            fun gestionarCrearArchivo(): Unit {
                val contenidoEncriptado = GestorEncriptacionDecriptacion.encriptar(contenido,contra)
                GestorArchivosAndroid.modificarCrearArchivo("/storage/emulated/0/Download/",nombre, "txt",contenidoEncriptado,this)
                GestorDialogos.dialogoInformativo("Exito", "Se ha creado el archivo $nombre.txt correctamente en /storage/emulated/0/Download/", this)!!.show()
                requireActivity().onBackPressed()
            }

            try {
                if(nombre.isEmpty()){
                    GestorDialogos.dialogoInformativo("Error", "El nombre debe tener al menos un caracter", this)!!.show()
                } else if (contra.length < 12){
                    GestorDialogos.dialogoInformativo("Error", "La contraseña debe tener al menos 12 caracteres", this)!!.show()
                } else if (contenido.isEmpty()){
                    GestorDialogos.dialogoInformativo("Error", "El contenido debe tener al menos un caracter", this)!!.show()
                } else {
                    val file: File = File( Uri.fromFile(File("/storage/emulated/0/Download/$nombre.txt")).path)
                    if(file.exists()){
                        GestorDialogos.dialogoSiNo(
                            "Ya existe",
                            "Este archivo ya existe, ¿quieres reemplazarlo?",
                            { gestionarCrearArchivo() },
                            {},
                            this
                        )!!.show()
                    } else {
                        gestionarCrearArchivo()
                    }
                }
            } catch (e: Exception) {
                GestorDialogos.dialogoInformativo("Error", "Error al crear el archivo encriptado", this)!!.show()
                Log.e("CrearModificarArchivoFragment", "Error al crear o modificar el archivo")
            }
        }

        return binding.root
    }

    fun cambiarNombre(nuevonombre: String){
        binding.nombrearchivo.setText(nuevonombre)
    }

    fun cambiarContra(nuevacontra: String){
        binding.editTextTextPassword.setText(nuevacontra)
    }

    fun cambiarContenido(nuevocont: String){
        binding.textarea.setText(nuevocont)
    }

    companion object {
        /*@JvmStatic
        fun newInstance(param1: String, param2: String) =
            CrearModificarArchivoFragment().apply {
                arguments = Bundle().apply {
                    putString(ARG_PARAM1, param1)
                }
            }
        */
    }

}