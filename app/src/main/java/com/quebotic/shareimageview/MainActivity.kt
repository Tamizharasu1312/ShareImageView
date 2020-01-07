package com.quebotic.shareimageview


import android.content.Intent
import android.graphics.Bitmap
import android.graphics.drawable.BitmapDrawable
import android.net.Uri
import android.os.Bundle
import android.os.StrictMode
import android.view.View
import android.widget.Button
import android.widget.ImageView
import androidx.appcompat.app.AppCompatActivity
import java.io.File
import java.io.FileOutputStream


class MainActivity : AppCompatActivity() {

    var mbt_Click: Button? = null
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main_2)

        val builder = StrictMode.VmPolicy.Builder()
        StrictMode.setVmPolicy(builder.build())


        mbt_Click = findViewById(R.id.bt_click)


        mbt_Click!!.setOnClickListener {
            shareImage()
        }

    }
    private fun shareImage(){
        try {
            val post_image = findViewById<View>(R.id.imageView) as ImageView

            val imageView = ImageView(this)

            val imgUri = Uri.parse("android.resource://com.quebotic.shareimageview/" + R.drawable.android1)

            //imageView.setImageResource(R.drawable.android1)

            imageView.setImageURI(null);
            imageView.setImageURI(imgUri);
            val myDrawable = imageView.drawable
            val bitmap = (myDrawable as BitmapDrawable).bitmap


            val file = File(externalCacheDir, "devofandroid.png")
            val fOut = FileOutputStream(file)
            bitmap.compress(Bitmap.CompressFormat.PNG, 100, fOut)
            fOut.flush()
            fOut.close()
            val intent = Intent(Intent.ACTION_SEND)
            intent.flags = Intent.FLAG_ACTIVITY_NEW_TASK
            intent.putExtra(Intent.EXTRA_STREAM, Uri.fromFile(file))
            intent.type = "image/png"
            startActivity(Intent.createChooser(intent, "Share image via"))
        } catch (e: Exception) {
            e.printStackTrace()
        }
    }
}
