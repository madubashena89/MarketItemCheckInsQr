package top.stores.marketitemcheckinsqr

import android.app.Activity
import android.content.Intent
import android.media.MediaPlayer
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import com.google.zxing.Result
import me.dm7.barcodescanner.zxing.ZXingScannerView

class ScannerViewActivity : Activity(), ZXingScannerView.ResultHandler {
    private var mScannerView: ZXingScannerView? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        mScannerView = ZXingScannerView(this)   // Programmatically initialize the scanner view
        setContentView(mScannerView)

        mScannerView!!.setResultHandler(this) // Register ourselves as a handler for scan results.
        mScannerView!!.startCamera()         // Start camera


    }
    ////////////////////////////////////////////////////////////////////////////////////////////////////////////////////

    override fun onResume() {
        super.onResume()
    }

    ////////////////////////////////////////////////////////////////////////////////////////////////////////////////////

    override fun onPause() {
        super.onPause()
        this.cleanResult()
    }

    ////////////////////////////////////////////////////////////////////////////////////////////////////////////////////

    override fun onBackPressed() { this.cleanResult() }

    private fun cleanResult() {
        try {
            mScannerView!!.stopCamera() // Stop camera on pause
        } catch (e: Exception) {
            e.message?.let { Log.e(Constants.ERROR, it) }
        }

        val resultIntent = Intent()
        resultIntent.putExtra(Constants.BAR_CODE, "")
        setResult(2, resultIntent)
        finish()
    }

    override fun handleResult(rawResult: Result) {
        // Do something with the result here

        Log.e(Constants.HANDLER, rawResult.text) // Prints scan results
        Log.e(Constants.HANDLER, rawResult.barcodeFormat.toString()) // Prints the scan format (qrcode)

        try {
            mScannerView!!.stopCamera()

            // Add Sound to comfirm correct take sound
            MediaPlayer.create(this, Constants.SCAN_OK_SOUND).start()
            val resultIntent = Intent()

            resultIntent.putExtra(Constants.BAR_CODE, rawResult.text)
            setResult(2, resultIntent)
            finish()

            println(Constants.STOP_CAMERA)
            // Stop camera on pause
        } catch (e: Exception) {
            e.message?.let { Log.e(Constants.ERROR, it) }
            MediaPlayer.create(this, Constants.SCAN_ERROR_SOUND).start()
        }

    }
}
