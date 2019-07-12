package net.feghome.clouduploader

import android.content.Context
import android.os.Build
import android.os.Environment
import android.os.storage.StorageManager
import android.os.storage.StorageVolume
import java.io.File
import android.webkit.MimeTypeMap



class FileUtil(context: Context) {
    var allFilesList: ArrayList<String> = ArrayList()
    var allImagesList: ArrayList<String> = ArrayList()
    var allVideosList: ArrayList<String> = ArrayList()

    init {
        getMultimediaList(context)
    }

    fun getMultimediaList(context: Context) {

        var volumeList: ArrayList<File> = ArrayList()
        val storageManager: StorageManager = context.getSystemService(Context.STORAGE_SERVICE) as StorageManager
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.N){
            var storageVolumeList:List<StorageVolume> =  storageManager.storageVolumes

            val volumes = storageManager.javaClass
                .getMethod("getVolumePaths", *arrayOfNulls(0))
                .invoke(storageManager, *arrayOfNulls(0)) as Array<String>
            for (i in volumes.indices){
                println(volumes[i])
                volumeList.add(File(volumes[i]))
            }
        } else {
            //var storage:File=Environment.getExternalStorageDirectory()
            volumeList.add(Environment.getExternalStorageDirectory())
        }

        for (i in volumeList.indices) {
            if (Environment.getExternalStorageState(volumeList[i]) == Environment.MEDIA_MOUNTED || Environment.getExternalStorageState(volumeList[i]) == Environment.MEDIA_MOUNTED_READ_ONLY) {
                listFiles(volumeList[i])
            }
        }
    }

    private fun listFiles(directory: File) {
        val files = directory.listFiles()
        if (files != null) {
            for (file in files) {
                if (file != null) {
                    if (file.isDirectory) {
                        listFiles(file)
                    } else {
                        allFilesList.add(file.absolutePath)
                        var fileMime: String? = getMimeType(file.absolutePath)
                        if ( fileMime != null) {
                            if (fileMime!!.startsWith("image")){
                                allImagesList.add(file.absolutePath)
                            }
                            if (fileMime!!.startsWith("video")){
                                allVideosList.add(file.absolutePath)
                            }
                        }

                    }
                }
            }
        }
    }

    // url = file path or whatever suitable URL you want.
    fun getMimeType(url: String): String? {
        var type: String? = null
        val extension = MimeTypeMap.getFileExtensionFromUrl(url)
        if (extension != null) {
            type = MimeTypeMap.getSingleton().getMimeTypeFromExtension(extension)
        }
        return type
    }
}