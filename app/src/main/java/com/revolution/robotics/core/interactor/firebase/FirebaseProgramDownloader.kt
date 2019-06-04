package com.revolution.robotics.core.interactor.firebase

import android.net.Uri
import com.revolution.robotics.core.domain.local.ProgramLocalFiles
import com.revolution.robotics.core.domain.remote.Program
import com.revolution.robotics.core.utils.UserProgramFileNameGenerator
import com.revolution.robotics.features.shared.ErrorHandler

class FirebaseProgramDownloader(
    private val firebaseFileDownloader: FirebaseFileDownloader,
    private val userProgramFileNameGenerator: UserProgramFileNameGenerator,
    private val errorHandler: ErrorHandler
) {

    private var onResponse: ((localFiles: List<ProgramLocalFiles>) -> Unit)? = null
    private var onError: ((throwable: Throwable) -> Unit)? = null

    private var python: Uri? = null
    private var xml: Uri? = null

    private var currentIndex = 0
    private lateinit var programs: List<Program>
    private val downloadedFiles = mutableListOf<ProgramLocalFiles>()

    fun downloadProgramFiles(
        programs: List<Program>,
        onResponse: ((localFiles: List<ProgramLocalFiles>) -> Unit),
        onError: ((throwable: Throwable) -> Unit)?
    ) {
        this.programs = programs
        this.onResponse = onResponse
        this.onError = onError
        this.xml = null
        this.python = null
        currentIndex = 0
        downloadFiles(currentIndex)
    }

    fun downloadProgramFiles(
        programs: List<Program>,
        onResponse: ((localFiles: List<ProgramLocalFiles>) -> Unit)
    ) {
        downloadProgramFiles(programs, onResponse, null)
    }

    private fun downloadFiles(index: Int) {
        programs[index].let { currentProgram ->
            firebaseFileDownloader.downloadFirestoreFile(
                userProgramFileNameGenerator.generatePythonFileName(),
                currentProgram.python ?: "",
                { storedFile ->
                    python = storedFile
                    downloadNextFile()
                }, {
                    sendOnError(it)
                })
            firebaseFileDownloader.downloadFirestoreFile(userProgramFileNameGenerator.generateXmlFileName(),
                currentProgram.xml ?: "", { storedFile ->
                    xml = storedFile
                    downloadNextFile()
                }, {
                    sendOnError(it)
                })
        }
    }

    private fun sendOnError(throwable: Throwable) {
        onError?.invoke(throwable) ?: errorHandler.onError()
        xml = null
        python = null
        onError = null
        onResponse = null
    }

    private fun downloadNextFile() {
        python?.let { python ->
            xml?.let { xml ->
                downloadedFiles.add(ProgramLocalFiles(xml, python, programs[currentIndex].id ?: ""))
                if (currentIndex < programs.size - 1) {
                    currentIndex++
                    this.xml = null
                    this.python = null
                    downloadFiles(currentIndex)
                } else {
                    onResponse?.invoke(downloadedFiles)
                    onResponse = null
                    onError = null
                    this.xml = null
                    this.python = null
                }
            }
        }
    }
}