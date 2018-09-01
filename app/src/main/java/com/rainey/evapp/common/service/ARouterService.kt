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

package com.rainey.evapp.common.service

import android.content.Context
import android.net.Uri
import com.alibaba.android.arouter.facade.Postcard
import com.alibaba.android.arouter.facade.annotation.Route
import com.alibaba.android.arouter.facade.service.DegradeService
import com.alibaba.android.arouter.facade.service.PathReplaceService
import com.alibaba.android.arouter.facade.service.SerializationService
import com.google.gson.FieldNamingPolicy
import com.google.gson.Gson
import com.google.gson.GsonBuilder
import com.rainey.evapp.common.Const
import com.rainey.evapp.common.extension.toast
import java.lang.reflect.Type

@Route(path = Const.SRV_PATH_REPLACE)
class PathReplaceServiceImpl : PathReplaceService {
    private lateinit var mContext: Context

    override fun init(context: Context?) {
        assert(context != null) { "ARouter: Application Context cannot be null! " }
        mContext = context!!
    }

    override fun forString(path: String?): String {
        assert(path != null) {"ARouter: Path cannot be null!"}
        return path!!
    }

    override fun forUri(uri: Uri?): Uri {
        assert(uri != null) {"ARouter: Uri cannot be null!"}
        return uri!!
    }
}

@Route(path = Const.SRV_DEGRADE)
class DegradleServiceImpl: DegradeService {
    private lateinit var mContext: Context

    override fun init(context: Context?) {
        assert(context != null) { "ARouter: Application Context cannot be null! " }
        mContext = context!!
    }

    override fun onLost(context: Context?, postcard: Postcard?) {
        mContext.toast("router not found!!!")
    }
}

@Route(path = Const.SRV_JSON)
class JsonServiceImpl: SerializationService {
    private lateinit var gson: Gson

    override fun init(context: Context?) {
        gson = gson()
    }
    @Suppress("OverridingDeprecatedMember")
    override fun <T : Any?> json2Object(input: String?, clazz: Class<T>?): T {
        return gson.fromJson(input, clazz)
    }


    override fun object2Json(instance: Any?): String {
        return gson.toJson(instance)
    }

    override fun <T : Any?> parseObject(input: String?, clazz: Type?): T {
        return gson.fromJson(input, clazz)
    }

    private fun gson(): Gson = GsonBuilder()
            .setLenient()
            .setDateFormat("yyyy-MM-dd HH:mm:ss")
            .setFieldNamingPolicy(FieldNamingPolicy.LOWER_CASE_WITH_UNDERSCORES)
            .create()

}