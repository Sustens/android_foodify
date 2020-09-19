package com.sustens.foodify

import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.scandit.datacapture.barcode.capture.BarcodeCapture
import com.scandit.datacapture.barcode.capture.BarcodeCaptureListener
import com.scandit.datacapture.barcode.capture.BarcodeCaptureSession
import com.scandit.datacapture.barcode.capture.BarcodeCaptureSettings
import com.scandit.datacapture.barcode.data.Symbology
import com.scandit.datacapture.barcode.ui.overlay.BarcodeCaptureOverlay
import com.scandit.datacapture.core.capture.DataCaptureContext
import com.scandit.datacapture.core.data.FrameData
import com.scandit.datacapture.core.source.Camera
import com.scandit.datacapture.core.source.FrameSourceState
import com.scandit.datacapture.core.ui.DataCaptureView


class MainActivity : AppCompatActivity(), BarcodeCaptureListener {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

//        initializeBarCode()

//        var api :ApiHandler = ApiHandler();
//        api.call()

        val intent = Intent(this, DetailActivity::class.java)
        // start your next activity
        intent.putExtra("barcode", "123")
        startActivity(intent)
    }

    private fun initializeBarCode() {
        val dataCaptureContext =
            DataCaptureContext.forLicenseKey(resources.getString(R.string.scan_license))
        val settings = BarcodeCaptureSettings()
        settings.enableSymbology(Symbology.CODE128, true)
        settings.enableSymbology(Symbology.CODE39, true)
        settings.enableSymbology(Symbology.QR, true)
        settings.enableSymbology(Symbology.EAN8, true)
        settings.enableSymbology(Symbology.UPCE, true)
        settings.enableSymbology(Symbology.EAN13_UPCA, true)

        val barcodeCapture = BarcodeCapture.forDataCaptureContext(dataCaptureContext, settings);
        barcodeCapture.addListener(this)

        val cameraSettings = BarcodeCapture.createRecommendedCameraSettings()

// Depending on the use case further camera settings adjustments can be made here.


// Depending on the use case further camera settings adjustments can be made here.
        val camera: Camera = Camera.getDefaultCamera()!!

        if (camera != null) {
            camera.applySettings(cameraSettings)
            camera.switchToDesiredState(FrameSourceState.ON)
        }

        dataCaptureContext.setFrameSource(camera)

        val dataCaptureView = DataCaptureView.newInstance(this, dataCaptureContext)
        setContentView(dataCaptureView)

        val overlay =
            BarcodeCaptureOverlay.newInstance(barcodeCapture, dataCaptureView)
    }


    override fun onBarcodeScanned(
        barcodeCapture: BarcodeCapture,
        session: BarcodeCaptureSession,
        data: FrameData
    ) {
        super.onBarcodeScanned(barcodeCapture, session, data)

        val recognizedBarcodes = session.newlyRecognizedBarcodes
        val list_barcodes = recognizedBarcodes.toList()
        for (barcode in list_barcodes)
            Log.d("MainActivity", barcode.data)
    }
}