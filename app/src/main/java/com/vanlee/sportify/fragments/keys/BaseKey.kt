package com.vanlee.sportify.fragments.keys

import com.zhuinden.simplestack.ServiceBinder
import com.zhuinden.simplestackextensions.fragments.DefaultFragmentKey
import com.zhuinden.simplestackextensions.services.DefaultServiceProvider


abstract class BaseKey : DefaultFragmentKey(), DefaultServiceProvider.HasServices {
    override fun getFragmentTag(): String {
        return super.getFragmentTag()
    }

    override fun getScopeTag(): String {
        return fragmentTag
    }

    override fun bindServices(serviceBinder: ServiceBinder) {}
}