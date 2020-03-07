package com.android.example.viewbindingsample

import android.view.View
import androidx.annotation.MainThread
import androidx.fragment.app.Fragment
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.LifecycleObserver
import androidx.lifecycle.OnLifecycleEvent
import androidx.viewbinding.ViewBinding

open class ViewBindingHolderImpl<VB: ViewBinding> : ViewBindingHolder<VB>, LifecycleObserver {

    override var binding: VB? = null
    var lifecycle: Lifecycle? = null

    /**
     * Prevent leaking with the binding reference on destroyed fragment,
     * so that binding variable must be null.
     */
    @OnLifecycleEvent(Lifecycle.Event.ON_DESTROY)
    fun onDestroyView() {
        lifecycle?.removeObserver(this)
        lifecycle = null
        binding = null
    }

    @MainThread
    override fun assignBinding(binding: VB, fragment: Fragment, onBound: (VB.() -> Unit)?): View {
        this.binding = binding
        lifecycle = fragment.viewLifecycleOwner.lifecycle
        lifecycle?.addObserver(this)
        onBound?.invoke(binding)
        return binding.root
    }

    @MainThread
    override fun requireBinding(block: (VB.() -> Unit)?): VB {
        return binding?.apply { block?.invoke(this) }
                ?: throw IllegalStateException("Accessing binding outside of Fragment lifecycle")
    }
}
