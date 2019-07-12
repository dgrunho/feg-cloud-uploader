package net.feghome.clouduploader

import android.Manifest
import android.content.Context
import android.content.pm.PackageManager
import android.database.Cursor
import android.net.Uri
import android.os.Build
import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.os.Environment
import android.os.storage.StorageManager
import android.os.storage.StorageVolume
import android.provider.MediaStore
import android.util.Log
import android.widget.Toast
import java.io.File

class MainActivity : AppCompatActivity() {
    private lateinit var managePermissions: ManagePermissions
    private val PermissionsRequestCode = 123
    var allFilesList: ArrayList<String> = ArrayList()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)


        var list = listOf<String>(
            Manifest.permission.WRITE_EXTERNAL_STORAGE,
            Manifest.permission.READ_EXTERNAL_STORAGE,
            Manifest.permission.FOREGROUND_SERVICE
        )

        managePermissions = ManagePermissions(this, list, PermissionsRequestCode)

        if (managePermissions.checkPermissions() == PackageManager.PERMISSION_GRANTED) {
            listExternalStorage()
        }
    }


    private fun listExternalStorage() {

        var volumeList: ArrayList<File> = ArrayList()
        val storageManager:StorageManager = this.getSystemService(Context.STORAGE_SERVICE) as StorageManager
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
                //listFiles(Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_DCIM))
                listFiles(volumeList[i])
                Toast.makeText(this, "Successfully listed all the files!", Toast.LENGTH_SHORT)
                    .show()
            }
        }


    }

    /**
     * Recursively list files from a given directory.
     */
    private fun listFiles(directory: File) {
        val files = directory.listFiles()
        if (files != null) {
            for (file in files) {
                if (file != null) {
                    if (file.isDirectory) {
                        listFiles(file)
                    } else {
                        allFilesList.add(file.absolutePath)
                        //println(file.absolutePath)
                    }
                }
            }
        }
    }
}
