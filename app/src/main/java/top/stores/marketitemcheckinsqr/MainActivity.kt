package top.stores.marketitemcheckinsqr

import android.Manifest
import android.content.Intent
import android.content.pm.PackageManager
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button
import android.widget.TextView
import android.widget.Toast
import androidx.core.app.ActivityCompat
import androidx.core.content.ContextCompat
import com.mugan86.qrscanner.utils.DateTime

class MainActivity : AppCompatActivity() {

    private val ZXING_CAMERA_PERMISSION = 1
    private var mClss: Class<*>? = null
    private lateinit var openScanner : Button
    private lateinit var bar_code_id_txt : TextView
    private lateinit var scan_data_txt : TextView



    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        openScanner = findViewById(R.id.openScanner)
        bar_code_id_txt = findViewById(R.id.bar_code_id_txt)
        scan_data_txt = findViewById(R.id.scan_data_txt)
        openScanner.setOnClickListener {
            launchActivity(ScannerViewActivity::class.java)
        }
    }


    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        val barcode = data?.extras?.getString(Constants.BAR_CODE)

        if (barcode == "") {
            Toast.makeText(this@MainActivity,Constants.BAR_CODE_NOT_FOUND, Toast.LENGTH_LONG).show()
        } else {
            bar_code_id_txt?.text = barcode
            scan_data_txt.text = DateTime.currentDataTime
        }
       }



    private fun launchActivity(clss: Class<*>) {
        if (ContextCompat.checkSelfPermission(this, Manifest.permission.CAMERA) != PackageManager.PERMISSION_GRANTED) {
            mClss = clss
            ActivityCompat.requestPermissions(this,
                    arrayOf(Manifest.permission.CAMERA), ZXING_CAMERA_PERMISSION)
        } else {
            val intent = Intent(this, clss)
            startActivityForResult(intent, 2)
        }
    }

    override fun onRequestPermissionsResult(requestCode: Int,
                                            permissions: Array<String>, grantResults: IntArray) {
        when (requestCode) {
            ZXING_CAMERA_PERMISSION -> {
                if (grantResults[0] == PackageManager.PERMISSION_GRANTED) {

                    // permission was granted, yay! do the
                    // calendar task you need to do.
                    Toast.makeText(this, Constants.PRESS_AGAIN_TO_SCAN, Toast.LENGTH_LONG).show()


                } else {

                    // permission denied, boo! Disable the
                    // functionality that depends on this permission.
                }
                return
            }
        }// other 'switch' lines to check for other
        // permissions this app might request
    }




}