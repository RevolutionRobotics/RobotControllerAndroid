package com.revolution.robotics.features.configure.robotPicture

import androidx.databinding.BaseObservable
import androidx.databinding.Observable
import androidx.databinding.ObservableBoolean
import androidx.databinding.ObservableField
import java.io.File

class RobotPictureViewModel {
    var image = ObservableField<File?>()
    var defaultCoverImage = ObservableField<String?>()
    var showPlaceholder = ObservableBoolean(true)

    init {
        defaultCoverImage.changesPlaceholderVisibility()
        image.changesPlaceholderVisibility()
    }

    private fun setShowPlaceholder() {
        val imageExists = image.get()?.exists() == true
        showPlaceholder.set(!imageExists && defaultCoverImage.get().isNullOrEmpty())
    }

    private fun BaseObservable.changesPlaceholderVisibility() {
        addOnPropertyChangedCallback(object : Observable.OnPropertyChangedCallback() {
            override fun onPropertyChanged(sender: Observable?, propertyId: Int) = setShowPlaceholder()
        })
    }
}