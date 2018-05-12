package com.mg.axechen.wanandroid.base

import android.content.BroadcastReceiver
import android.content.Context
import android.content.Intent
import android.content.IntentFilter
import android.os.Bundle
import android.support.v4.app.Fragment
import com.mg.axechen.wanandroid.utils.Contacts
import java.lang.ref.WeakReference

/**
 * Created by AxeChen on 2018/4/9.
 */
open class BaseFragment : Fragment() {


    private var receiver: MyLoginStatusReceiver? = null

    override fun onResume() {
        super.onResume()
        changeThemeRefresh()
    }

    protected fun registerLoginStatusReceiver() {
        receiver = MyLoginStatusReceiver(this)//广播接受者实例
        val intentFilter = IntentFilter()
        intentFilter.addAction(Contacts.LOGIN_SUCCESS)
        activity.registerReceiver(receiver, intentFilter)
    }

    /**
     * 修改主题之后，onResume刷新特定的控件
     */
    open fun changeThemeRefresh() {
    }

    /**
     * 登录状态广播接收
     */
    class MyLoginStatusReceiver(fragment: BaseFragment) : BroadcastReceiver() {

        var weakFragment: WeakReference<BaseFragment>? = null

        init {
            weakFragment = WeakReference(fragment)
        }

        override fun onReceive(context: Context?, intent: Intent?) {
            var fragment = weakFragment?.get()
            fragment?.loginSuccess()
        }

    }

    open fun loginSuccess() {

    }

    override fun onDestroy() {
        super.onDestroy()
        if (receiver != null) {
            activity?.unregisterReceiver(receiver)
        }
    }

}