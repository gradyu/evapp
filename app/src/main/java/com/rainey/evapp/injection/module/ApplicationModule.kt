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

package com.rainey.evapp.injection.module

import android.app.Application
import android.content.Context
import com.rainey.domain.executor.PostExecutionThread
import com.rainey.evapp.activity.common.UiThread
import com.rainey.evapp.activity.common.service.DefaultDispatch
import com.rainey.evapp.activity.common.service.Dispatch
import com.rainey.evapp.injection.scope.PerApplication
import dagger.Module
import dagger.Provides

@Module
class ApplicationModule {

    @PerApplication
    @Provides
    fun provideContext(application: Application): Context {
        return application
    }

    @PerApplication
    @Provides
    fun provideDispatch(dispatch: DefaultDispatch): Dispatch {
        return dispatch
    }

    @PerApplication
    @Provides
    fun providePostExecutionThread(uiThread: UiThread): PostExecutionThread {
        return uiThread
    }
}
