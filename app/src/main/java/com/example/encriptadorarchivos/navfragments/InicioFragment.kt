package com.example.encriptadorarchivos.navfragments

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.navigation.findNavController
import com.example.encriptadorarchivos.R
import com.example.encriptadorarchivos.databinding.FragmentInicioBinding

class InicioFragment : Fragment() {

    private lateinit var binding: FragmentInicioBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentInicioBinding.inflate(layoutInflater)

        binding.bcrear.setOnClickListener{ v ->
            val bundle = Bundle()
            bundle.putString("nombre", "")
            bundle.putString("contra", "")
            bundle.putString("contenido", "")
            v!!.findNavController().navigate(R.id.action_inicioFragment_to_crearModificarArchivoFragment, bundle)
        }

        binding.babrir.setOnClickListener{ v ->
            v!!.findNavController().navigate(R.id.action_inicioFragment_to_abrirFragment)
        }

        return binding.root
    }

    /*var todo: String = ""
        val textoAEncriptar = "Holaloco!"
        val contra = "contra21503"

        val textoEncriptado: String = GestorEncriptacionDecriptacion.encriptar(textoAEncriptar,contra)
        val textoDesencriptado: String = GestorEncriptacionDecriptacion.desencriptar(textoEncriptado,contra)

        todo += textoAEncriptar
        todo += "\n\n"
        todo += textoEncriptado
        todo += "\n\n"
        todo += textoDesencriptado

        binding.texto.text = todo*/
}