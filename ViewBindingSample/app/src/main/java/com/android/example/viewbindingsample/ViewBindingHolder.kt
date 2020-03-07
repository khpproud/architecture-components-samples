package com.android.example.viewbindingsample

import android.view.View
import androidx.fragment.app.Fragment
import androidx.viewbinding.ViewBinding

/**
 * Holds and manages ViewBinding inside a Fragment.
 */
interface ViewBindingHolder<VB : ViewBinding> {

    /**
     * This binding variable is managed by holder instance.
     */
    var binding: VB?

    /**
     * Assigns the binding object and cleanups on onDestroyView, `onBound` means that all inner view
     * actions complete with `this` receiver and returns bounded fragment's view root.
     */
    fun assignBinding(binding: VB, fragment: Fragment, onBound: (VB.() -> Unit)?): View

    /**
     * Just like requireActivity, requireContext, this requires specified binding instance with [block]
     * action.
     *
     * @throws IllegalStateException when called outside of an active fragment's lifecycle, it throws
     */
    fun requireBinding(block: (VB.() -> Unit)? = null): VB
}
