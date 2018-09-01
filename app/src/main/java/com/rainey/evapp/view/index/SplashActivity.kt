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

package com.rainey.evapp.view.index

import android.os.Build
import android.os.Bundle
import android.view.View
import com.alibaba.android.arouter.launcher.ARouter
import com.rainey.evapp.R
import com.rainey.evapp.common.Const
import com.rainey.evapp.common.extension.plusAssign
import com.rainey.evapp.view.BaseActivity
import com.rainey.evapp.view.main.HomeActivity
import io.reactivex.Observable
import java.util.concurrent.TimeUnit

class SplashActivity : BaseActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_splash)
        hideSystemUiNavigationBar()
        setupDelayHomeActivity()
    }

    private fun hideSystemUiNavigationBar() {
        if (Build.VERSION.SDK_INT < 19) {
            window.decorView.systemUiVisibility = View.GONE
        } else {
            window.decorView.systemUiVisibility = View.SYSTEM_UI_FLAG_HIDE_NAVIGATION or
                    View.SYSTEM_UI_FLAG_IMMERSIVE_STICKY or
                    View.SYSTEM_UI_FLAG_FULLSCREEN
        }
    }

    private fun setupDelayHomeActivity() {
        autoDisposables += Observable.timer(Const.SPLASH_DELAY, TimeUnit.SECONDS).subscribe {
            goHomeActivity()
        }
    }

    private fun arouterNavigate() {
        ARouter.getInstance().build(Const.APP_HOME_PATH)
                .withString(HomeActivity.EXTRA_NAME, "Grady")
                .withString(HomeActivity.EXTRA_PWD, "123456")
                .withInt(HomeActivity.EXTRA_AGE, 21)
                .withBoolean(HomeActivity.EXTRA_MARRIED, true)
                .withFloat(HomeActivity.EXTRA_WEIGHT, 55.6f)
                .navigation()
    }

    private fun goHomeActivity() {
        arouterNavigate()
        finish()
    }

}
