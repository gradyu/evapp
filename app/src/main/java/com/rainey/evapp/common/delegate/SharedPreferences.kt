/*
 * Copyright (C) 2018 grady
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *       http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

@file:Suppress("unused")

package com.rainey.evapp.common.delegate

import android.annotation.SuppressLint
import android.content.SharedPreferences
import android.content.SharedPreferences.Editor
import android.util.Base64
import javax.crypto.Cipher
import javax.crypto.spec.SecretKeySpec
import kotlin.properties.ReadWriteProperty
import kotlin.reflect.KProperty

private inline fun <T> SharedPreferences.delegate(
        key: String? = null,
        defValue: T,
        crossinline getter: SharedPreferences.(String, T) -> T,
        crossinline setter: Editor.(String, T) -> Editor
): ReadWriteProperty<Any, T> = object : ReadWriteProperty<Any, T> {
    override fun getValue(thisRef: Any, property: KProperty<*>): T =
            getter(key ?: property.name, defValue)

    override fun setValue(thisRef: Any, property: KProperty<*>, value: T) =
            edit().setter(key ?: property.name, value).apply()
}

fun SharedPreferences.int(key: String? = null, defValue: Int = 0, isEncrypt: Boolean = false): ReadWriteProperty<Any, Int> {
    return if (isEncrypt) {
        delegate(key, defValue, SharedPreferences::getEncryptInt, Editor::putEncryptInt)
    } else {
        delegate(key, defValue, SharedPreferences::getInt, Editor::putInt)
    }
}

fun SharedPreferences.long(key: String? = null, defValue: Long = 0, isEncrypt: Boolean = false): ReadWriteProperty<Any, Long> {
    return if (isEncrypt) {
        delegate(key, defValue, SharedPreferences::getEncryptLong, Editor::putEncryptLong)
    } else {
        delegate(key, defValue, SharedPreferences::getLong, Editor::putLong)
    }
}

fun SharedPreferences.float(key: String? = null, defValue: Float = 0f, isEncrypt: Boolean = false): ReadWriteProperty<Any, Float> {
    return if (isEncrypt) {
        delegate(key, defValue, SharedPreferences::getEncryptFloat, Editor::putEncryptFloat)
    } else {
        delegate(key, defValue, SharedPreferences::getFloat, Editor::putFloat)
    }
}

fun SharedPreferences.boolean(key: String? = null, defValue: Boolean = false, isEncrypt: Boolean = false): ReadWriteProperty<Any, Boolean> {
    return if (isEncrypt) {
        delegate(key, defValue, SharedPreferences::getEncryptBoolean, Editor::putEncryptBoolean)
    } else {
        delegate(key, defValue, SharedPreferences::getBoolean, Editor::putBoolean)
    }
}

fun SharedPreferences.string(key: String? = null, defValue: String = "", isEncrypt: Boolean = false): ReadWriteProperty<Any, String> {
    return if (isEncrypt) {
        delegate(key, defValue, SharedPreferences::getEncryptString, Editor::putEncryptString)
    } else {
        delegate(key, defValue, SharedPreferences::getString, Editor::putString)
    }
}

fun SharedPreferences.stringSet(key: String? = null, defValue: Set<String> = emptySet(), isEncrypt: Boolean = false): ReadWriteProperty<Any, Set<String>> {
    return if (isEncrypt) {
        delegate(key, defValue, SharedPreferences::getEncryptStringSet, Editor::putEncryptStringSet)
    } else {
        delegate(key, defValue, SharedPreferences::getStringSet, Editor::putStringSet)
    }
}

fun SharedPreferences.initKey(key: String) = Encrypt.key(key)

fun SharedPreferences.getEncryptInt(key: String, defValue: Int): Int {
    val value = getString(encrypt(key), null) ?: return defValue
    return decrypt(value).toInt()
}

fun Editor.putEncryptInt(key: String, value: Int): Editor =
        putString(encrypt(key), encrypt(value.toString()))

fun SharedPreferences.getEncryptLong(key: String, defValue: Long): Long {
    val value = getString(encrypt(key), null) ?: return defValue
    return decrypt(value).toLong()
}

fun Editor.putEncryptLong(key: String, value: Long): Editor =
        putString(encrypt(key), encrypt(value.toString()))

fun SharedPreferences.getEncryptFloat(key: String, defValue: Float): Float {
    val value = getString(encrypt(key), null) ?: return defValue
    return decrypt(value).toFloat()
}

fun Editor.putEncryptFloat(key: String, value: Float): Editor =
        putString(encrypt(key), encrypt(value.toString()))

fun SharedPreferences.getEncryptBoolean(key: String, defValue: Boolean): Boolean {
    val value = getString(encrypt(key), null) ?: return defValue
    return decrypt(value).toBoolean()
}

fun Editor.putEncryptBoolean(key: String, value: Boolean): Editor =
        putString(encrypt(key), encrypt(value.toString()))

fun SharedPreferences.getEncryptString(key: String, defValue: String?): String {
    val value = getString(encrypt(key), null)
    return if (value == null) defValue ?: "" else decrypt(value)
}

fun Editor.putEncryptString(key: String, value: String): Editor =
        putString(encrypt(key), encrypt(value))

fun SharedPreferences.getEncryptStringSet(key: String, defValue: Set<String>): Set<String> {
    val values = getStringSet(encrypt(key), null) ?: return defValue
    val rstSet = HashSet<String>()
    for (value in values) {
        rstSet.add(decrypt(value))
    }
    return rstSet
}

fun Editor.putEncryptStringSet(key: String, values: Set<String>): Editor {
    val rstSet = HashSet<String>()
    for (value in values) {
        rstSet.add(encrypt(value))
    }
    return putStringSet(encrypt(key), rstSet)
}

private fun encrypt(plains: String): String = Encrypt.encrypt(plains)

private fun decrypt(ciphers: String): String = Encrypt.decrypt(ciphers)

private object Encrypt {

    private lateinit var key: String

    /**
     * set encrypt key
     * @param key: encrypt key (length: 16)
     */
    fun key(key: String) {
        this.key = key
    }

    /**
     * AES 128 encrypt
     * @param plains: plain text
     * @return encrypted string by Base64
     */
    @SuppressLint("GetInstance")
    fun encrypt(plains: String): String {
        try {
            val cipher = Cipher.getInstance("AES/ECB/PKCS5Padding")
            val keySpec = SecretKeySpec(key.toByteArray(), "AES")
            cipher.init(Cipher.ENCRYPT_MODE, keySpec)
            val encode = cipher.doFinal(plains.toByteArray())
            return Base64.encodeToString(encode, Base64.NO_WRAP)
        } catch (e: Exception) {
            e.printStackTrace()
        }
        return ""
    }

    /**
     * AES 128 decrypt
     * @param ciphers: encrypted text
     * @return decrypted string
     */
    @SuppressLint("GetInstance")
    fun decrypt(ciphers: String): String {
        try {
            val encode = Base64.decode(ciphers, Base64.NO_WRAP)
            val cipher = Cipher.getInstance("AES/ECB/PKCS5Padding")
            val keySpec = SecretKeySpec(key.toByteArray(), "AES")
            cipher.init(Cipher.DECRYPT_MODE, keySpec)
            val decode = cipher.doFinal(encode)
            return String(decode)
        } catch (e: Exception) {
            e.printStackTrace()
        }
        return ""
    }
}