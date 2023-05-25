package com.example.encriptadorarchivos

import org.springframework.security.crypto.encrypt.Encryptors
import com.google.common.base.Charsets
import com.google.common.hash.HashCode
import com.google.common.hash.HashFunction
import com.google.common.hash.Hashing

class GestorEncriptacionDecriptacion {

    companion object {
        fun encriptar(contenido: String, contra: String): String {
            val hashFunction: HashFunction = Hashing.sha256()
            val hashCode: HashCode = hashFunction.newHasher()
                .putString(contra, Charsets.UTF_8)
                .hash()
            val sha256hex: String = hashCode.toString()
            val parte_a_tomar: Int= Math.abs(sha256hex.substring(0,6).toLong(radix = 16).toInt())%4
            val salt: String = sha256hex.substring(parte_a_tomar*16, (parte_a_tomar!!+1)*16)

            val encryptor = Encryptors.text(contra, salt)
            return encryptor.encrypt(contenido)
        }

        fun desencriptar(contenido: String, contra: String): String {
            val hashFunction: HashFunction = Hashing.sha256()
            val hashCode: HashCode = hashFunction.newHasher()
                .putString(contra, Charsets.UTF_8)
                .hash()
            val sha256hex: String = hashCode.toString()
            val parte_a_tomar: Int= Math.abs(sha256hex.substring(0,6).toLong(radix = 16).toInt())%4
            val salt: String = sha256hex.substring(parte_a_tomar*16, (parte_a_tomar!!+1)*16)

            val decryptor = Encryptors.text(contra, salt)
            return decryptor.decrypt(contenido)
        }
    }

}