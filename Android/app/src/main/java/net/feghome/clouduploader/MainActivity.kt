package net.feghome.clouduploader

import android.Manifest
import android.content.pm.PackageManager
import android.support.v7.app.AppCompatActivity
import android.os.Bundle


class MainActivity : AppCompatActivity() {
    private lateinit var managePermissions: ManagePermissions
    private val PermissionsRequestCode = 123



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
            var fUtil: FileUtil = FileUtil(this)
            var fileList:ArrayList<String> = fUtil!!.allImagesList
            fileList.forEach {

            }
        }
    }ç
}
