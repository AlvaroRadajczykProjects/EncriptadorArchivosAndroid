package com.example.encriptadorarchivos

import android.os.Bundle
import android.text.method.HideReturnsTransformationMethod
import android.text.method.PasswordTransformationMethod
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.navigation.findNavController
import com.example.encriptadorarchivos.databinding.FragmentAbrirBinding
import com.example.encriptadorarchivos.databinding.FragmentCrearModificarArchivoBinding

class AbrirFragment : Fragment() {

    private lateinit var binding: FragmentAbrirBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentAbrirBinding.inflate(layoutInflater)

        binding.switch1.setOnClickListener {
            if(binding.switch1.isChecked){
                binding.editTextTextPassword.transformationMethod = HideReturnsTransformationMethod.getInstance()
            } else {
                binding.editTextTextPassword.transformationMethod = PasswordTransformationMethod.getInstance()
            }
        }

        binding.babrir.setOnClickListener { v ->
            try {
                val contenidoEncriptado: String = GestorArchivosAndroid.obtenerContenidoArchivo("/storage/emulated/0/Download/", binding.nombrearchivo.text.toString(), "txt", this)
                val contenidoDesencriptado: String = GestorEncriptacionDecriptacion.desencriptar(contenidoEncriptado, binding.editTextTextPassword.text.toString())
                val bundle = Bundle()
                bundle.putString("nombre", binding.nombrearchivo.text.toString())
                bundle.putString("contra", binding.editTextTextPassword.text.toString())
                bundle.putString("contenido", contenidoDesencriptado)
                v!!.findNavController().navigate(R.id.action_abrirFragment_to_crearModificarArchivoFragment, bundle)
                GestorDialogos.dialogoInformativo("Exito", "Se ha abierto el archivo sin errores", this)!!.show()
            } catch (e: Exception){
                GestorDialogos.dialogoInformativo("Error", "Error al abrir el archivo: ${e.message}", this)!!.show()
            }
        }

        return binding.root
    }

    companion object {}

}