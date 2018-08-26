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

package com.rainey.evapp.common.utils

import android.content.SharedPreferences
import com.rainey.evapp.common.delegate.*

class SharedPreferencesHelper(sharedPreferences: SharedPreferences) {

    init {
        sharedPreferences.initKey("1234567890abcdef")
    }

    var spName by sharedPreferences.string(isEncrypt = true)
    var spPassword by sharedPreferences.string(isEncrypt = true)
    var spAge by sharedPreferences.int(isEncrypt = true)
    var spMarried by sharedPreferences.boolean(isEncrypt = true)
    var spWeight by sharedPreferences.float(isEncrypt = true)

}