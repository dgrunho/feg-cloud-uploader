package net.feghome.clouduploader.Utils

import android.content.ContentValues.TAG
import android.content.Context
import android.os.Build
import android.os.Environment
import android.os.storage.StorageManager
import android.os.storage.StorageVolume
import android.webkit.MimeTypeMap
import android.util.Log
import java.io.*
import java.security.MessageDigest


class ManageFiles(context: Context) {
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
                                //allImgsList.add(FileChecksum(file.absolutePath, calculateChecksum(file)))
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

    fun calculateChecksum(updateFile: File): String? {
        val iStrm: InputStream
        try {
            iStrm = FileInputStream(updateFile)
        } catch (e: Exception) {
            Log.e(TAG, "Exception while getting FileInputStream", e)
            return null
        }

        try {
            val mGraphicBuffer = ByteArrayOutputStream()
            val buf = ByteArray(1024)
            while (true) {
                val readNum = iStrm.read(buf)
                if (readNum == -1) break
                mGraphicBuffer.write(buf, 0, readNum)
            }

            return generateChecksum(mGraphicBuffer, "SHA-512")
        } catch (e: IOException) {
            throw RuntimeException("Unable to process file for SHA-512", e)
        } finally {
            try {
                iStrm.close()
            } catch (e: IOException) {
                Log.e(TAG, "Exception on closing the input stream", e)
            }

        }
    }

    private fun generateChecksum(data: ByteArrayOutputStream, algorithm: String): String {
        try {
            val digest: MessageDigest = MessageDigest.getInstance(algorithm)
            val hash: ByteArray = digest.digest(data.toByteArray())
            return printableHexString(hash)
        } catch (e: Exception) {
            e.printStackTrace()
        }

        return ""
    }

    fun printableHexString(data: ByteArray): String {
        // Create Hex String
        val hexString: StringBuilder = StringBuilder()
        for (aMessageDigest:Byte in data) {
            var h: String = Integer.toHexString(0xFF and aMessageDigest.toInt())
            while (h.length < 2)
                h = "0$h"
            hexString.append(h)
        }
        return hexString.toString()
    }
}