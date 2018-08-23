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

import android.app.Activity
import android.support.v4.app.Fragment
import kotlin.reflect.KProperty

class ExtrasDelegate<out T>(private val name: String, private val defValue: T) {

    private var extra: T? = null

    operator fun getValue(thisRef: Activity, property: KProperty<*>): T {
        extra = getExtra(thisRef, name, extra)
        return extra ?: defValue
    }

    operator fun getValue(thisRef: Fragment, property: KProperty<*>): T {
        extra = getExtra(thisRef, name, extra)
        return extra ?: defValue
    }

}

fun <T> extra(name: String, defValue: T) = ExtrasDelegate(name, defValue)

fun extra(name: String) = extra(name, null)


@Suppress("UNCHECKED_CAST")
private fun <T> getExtra(thisRef: Activity, name: String, oldExtra: T?): T? {
    return oldExtra ?: thisRef.intent?.extras?.get(name) as T?
}

@Suppress("UNCHECKED_CAST")
private fun <T> getExtra(thisRef: Fragment, name: String, oldExtra: T?): T? {
    return oldExtra ?: thisRef.arguments?.get(name) as T?
}
