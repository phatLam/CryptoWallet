package com.example.superdemo.condition.KeyStoreUtils

import android.os.Build
import androidx.annotation.RequiresApi
import java.io.IOException
import java.nio.charset.Charset
import java.security.KeyStore
import java.security.KeyStoreException
import java.security.NoSuchAlgorithmException
import java.security.UnrecoverableEntryException
import java.security.cert.CertificateException
import javax.crypto.Cipher
import javax.crypto.SecretKey
import javax.crypto.spec.GCMParameterSpec

class DeCryptor() {
    companion object{
        val TRANSFORMATION = "AES/GCM/NoPadding";
        val ANDROID_KEY_STORE = "AndroidKeyStore";
    }
    private lateinit var keyStore: KeyStore

    init {
        initKeyStore()
    }
    @Throws(
        KeyStoreException::class,
        IOException::class,
        CertificateException::class,
        NoSuchAlgorithmException::class
    )
    private fun initKeyStore() {
        keyStore = KeyStore.getInstance(ANDROID_KEY_STORE)
        keyStore.load(null)
    }

    @RequiresApi(Build.VERSION_CODES.KITKAT)
    @Throws(
        UnrecoverableEntryException::class,
        NoSuchAlgorithmException::class,
        KeyStoreException::class
    )
    public fun decryptData(alias: String, encryptedData: ByteArray, encryptionIv: ByteArray) : String {
        val cipher: Cipher = Cipher.getInstance(TRANSFORMATION)
        val spec = GCMParameterSpec(128, encryptionIv)
        cipher.init(Cipher.DECRYPT_MODE, getSecretKey(alias), spec)

        return String(cipher.doFinal(encryptedData), Charset.forName("UTF-8"))
    }

    @Throws(
        NoSuchAlgorithmException::class,
        UnrecoverableEntryException::class,
        KeyStoreException::class
    )
    private fun getSecretKey(alias: String): SecretKey? {
        return (keyStore.getEntry(alias, null) as KeyStore.SecretKeyEntry).secretKey
    }
}