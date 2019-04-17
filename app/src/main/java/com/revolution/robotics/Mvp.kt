package com.revolution.robotics

import androidx.annotation.CallSuper
import androidx.lifecycle.ViewModel

interface Mvp {

    interface View

    interface Presenter<V : View, M : ViewModel> {
        var view: V?
        var model: M?

        @CallSuper
        fun register(view: V, model: M?) {
            this.view = view
            this.model = model
        }

        @CallSuper
        fun unregister() {
            this.view = null
            this.model = null
        }
    }
}
