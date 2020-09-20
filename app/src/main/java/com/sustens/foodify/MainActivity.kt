package com.sustens.foodify

import android.content.Intent
import android.content.pm.PackageManager
import android.os.Bundle
import android.util.Log
import android.view.ViewGroup
import androidx.appcompat.app.AppCompatActivity
import com.google.android.material.floatingactionbutton.FloatingActionButton
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
import com.sustens.foodify.model.ItemsResponse
import com.sustens.foodify.model.ItemsResponseItem
import kotlinx.android.synthetic.main.activity_main.*
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response


class MainActivity : AppCompatActivity(), BarcodeCaptureListener {

    private lateinit var apiInterface: APIInterface
    private var itemsData = ArrayList<ItemsResponseItem>()
    private var selectedItems = ArrayList<ItemsResponseItem>()
    private lateinit var itemsAdapter: ItemsAdapter



    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        getItems()
        if (!requestPermissions(arrayOf(CAMERA_PERMISSION))){
            initializeBarCode()
        }

        itemsAdapter = ItemsAdapter(selectedItems)
        recycler_items.adapter = itemsAdapter

        DetailActivity.sel_objs = selectedItems
    }

    private fun getItems() {
        apiInterface = APIClient.getClient()!!.create(APIInterface::class.java)

        val call: Call<ItemsResponse> = apiInterface.getItems()
        call.enqueue(object : Callback<ItemsResponse?> {
            override fun onResponse(
                call: Call<ItemsResponse?>?,
                response: Response<ItemsResponse?>
            ) {
                Log.d("TAG", response.code().toString() + "")
                itemsData = response.body()!!

            }

            override fun onFailure(call: Call<ItemsResponse?>, t: Throwable?) {
                call.cancel()
            }
        })

        findViewById<FloatingActionButton>(R.id.history).setOnClickListener { view ->
           val intent = Intent(this, DetailActivity::class.java)

            startActivity(intent)
        }

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

        (findViewById<ViewGroup>(R.id.scanner_container)).addView(dataCaptureView)


        val overlay =
            BarcodeCaptureOverlay.newInstance(barcodeCapture, dataCaptureView)
        overlay.shouldShowScanAreaGuides = true

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

    private var itemID = ""
    override fun onBarcodeScanned(
        barcodeCapture: BarcodeCapture,
        session: BarcodeCaptureSession,
        data: FrameData
    ) {
        super.onBarcodeScanned(barcodeCapture, session, data)



        runOnUiThread {
            val recognizedBarcodes = session.newlyRecognizedBarcodes
            if (recognizedBarcodes.isNotEmpty()) {
                itemID = recognizedBarcodes[0].data.toString()
                if (selectedItems.indexOfFirst { it.ID.toString() == itemID } == -1) {
                    val index = itemsData.indexOfFirst { it.ID.toString() == itemID }

                    if (index > 0) {
                        selectedItems.add(itemsData[index])
                        itemsAdapter.notifyDataSetChanged()


                    }
                }
//                barcode_data.text = "${barcode_data.text} \n $lastBarcode \n"
            }
        }
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


    override fun onRequestPermissionsResult(requestCode: Int,
                                            permissions: Array<String>, grantResults: IntArray) {
        when (requestCode) {

            PERMISSION_ALL -> {
                // If request is cancelled, the result arrays are empty.
                if ((grantResults.isNotEmpty() && grantResults[0] == PackageManager.PERMISSION_GRANTED)) {
                    initializeBarCode()
                    // permission was granted, yay! Do the
                    // contacts-related task you need to do.
                } else {
                    showSnackBar("you should accept this permission to pick Barcode")
                    // permission denied, boo! Disable the
                    // functionality that depends on this permission.
                }
                return
            }

            // Add other 'when' lines to check for other
            // permissions this app might request.
            else -> {
                Log.v("a7a", "error")
            }
        }


    }

}