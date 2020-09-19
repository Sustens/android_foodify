package com.sustens.foodify

import android.os.Bundle
import android.view.ViewGroup
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
import kotlinx.android.synthetic.main.activity_main.*


class MainActivity : AppCompatActivity(), BarcodeCaptureListener {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        initializeBarCode()

//        var api :ApiHandler = ApiHandler();
//        api.call()

//        val intent = Intent(this, DetailActivity::class.java)
//        // start your next activity
//        intent.putExtra("barcode", "123")
//        startActivity(intent)
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
        settings.enableSymbology(Symbology.CODABAR, true)

        val barcodeCapture = BarcodeCapture.forDataCaptureContext(dataCaptureContext, settings)
        barcodeCapture.addListener(this)


        val cameraSettings = BarcodeCapture.createRecommendedCameraSettings()
// Depending on the use case further camera settings adjustments can be made here.


// Depending on the use case further camera settings adjustments can be made here.
       camera = Camera.getDefaultCamera()!!

        if (camera != null) {

            camera!!.applySettings(cameraSettings)
//            camera!!.switchToDesiredState(FrameSourceState.ON)
        }

        dataCaptureContext.setFrameSource(camera)

        val dataCaptureView = DataCaptureView.newInstance(this, dataCaptureContext)
//        val margins = MarginsWithUnit(
//            FloatWithUnit(0f, MeasureUnit.DIP),
//            FloatWithUnit(0f, MeasureUnit.DIP),
//            FloatWithUnit(0f, MeasureUnit.DIP),
//            FloatWithUnit(110f, MeasureUnit.DIP)
//        )
//        dataCaptureView.scanAreaMargins = margins
//        setContentView(dataCaptureView)

        (findViewById<ViewGroup>(R.id.scanner_container)).addView(dataCaptureView)


        val overlay =
            BarcodeCaptureOverlay.newInstance(barcodeCapture, dataCaptureView)
        overlay.shouldShowScanAreaGuides = true
//        dataCaptureView.addOverlay(overlay)


        // We have to add the laser line viewfinder to the overlay.
//        LaserlineViewfinder viewFinder = new LaserlineViewfinder();
//        viewFinder.setWidth(new FloatWithUnit(0.9f, MeasureUnit.FRACTION));
//        overlay.setViewfinder(viewFinder);

        // We put the dataCaptureView in its container.
    }

    private var camera: Camera? = null
    override fun onPause() {
        super.onPause()
        if (camera != null) {
            camera!!.switchToDesiredState(FrameSourceState.OFF);
        }
    }

    override fun onResume() {
        super.onResume()
        if (camera != null) {
            camera!!.switchToDesiredState(FrameSourceState.ON);
        }
    }

    private var lastBarcode = ""
    override fun onBarcodeScanned(
        barcodeCapture: BarcodeCapture,
        session: BarcodeCaptureSession,
        data: FrameData
    ) {
        super.onBarcodeScanned(barcodeCapture, session, data)



        runOnUiThread {
            val recognizedBarcodes = session.newlyRecognizedBarcodes
            if (recognizedBarcodes.isNotEmpty() && lastBarcode != recognizedBarcodes[0].data.toString()) {
                lastBarcode = recognizedBarcodes[0].data.toString()
                barcode_data.text = "${barcode_data.text} \n $lastBarcode \n"
            }        }
        val list_barcodes = ArrayList<String>()

//        for (barcode in recognizedBarcodes) {
//            list_barcodes.add(barcode.data!!)
//            Log.d("barcodeData", barcode.data)
//            barcode_data.text = "${barcode_data.text} \n ${barcode.data}"
//        }

//        val intent = Intent(this, DetailActivity::class.java)
//        intent.putExtra("barcode", list_barcodes)
//        startActivity(intent)

    }
}