package com.project.surveyapps

import android.app.Notification
import android.app.NotificationChannel
import android.app.NotificationManager
import android.content.Context
import android.content.Intent
import android.os.Build
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Toast
import androidx.core.app.NotificationCompat
import androidx.core.app.NotificationManagerCompat
import androidx.core.content.res.ResourcesCompat
import androidx.recyclerview.widget.LinearLayoutManager
import com.project.surveyapps.adapter.SurveyRecyclerAdapter
import com.project.surveyapps.callbacks.ConnectionCallback
import com.project.surveyapps.model.MSurvey
import com.project.surveyapps.response.ResponseSurvey
import com.project.surveyapps.retrofit.DataService
import com.project.surveyapps.retrofit.RetrofitClient
import com.project.surveyapps.dialogs.signal.NoInternetDialogSignal
import kotlinx.android.synthetic.main.activity_main.*
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import www.sanju.motiontoast.MotionToast
import java.util.ArrayList

class MainActivity : AppCompatActivity() {

    companion object {
        const val CHANNEL_ID = "channelID"
        const val CHANNEL_NAME = "channelName"
        const val NOTIFICATION_ID = 0
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        NoInternetDialogSignal.Builder(
            this,
            lifecycle
        ).apply {
            dialogProperties.apply {
                connectionCallback = object : ConnectionCallback { // Optional
                    override fun hasActiveConnection(hasActiveConnection: Boolean) {
                        // ...
                    }
                }

                cancelable = false // Optional
                noInternetConnectionTitle = "No Internet" // Optional
                noInternetConnectionMessage =
                    "Check your Internet connection and try again." // Optional
                showInternetOnButtons = true // Optional
                pleaseTurnOnText = "Please turn on" // Optional
                wifiOnButtonText = "Wifi" // Optional
                mobileDataOnButtonText = "Mobile data" // Optional

                onAirplaneModeTitle = "No Internet" // Optional
                onAirplaneModeMessage = "You have turned on the airplane mode." // Optional
                pleaseTurnOffText = "Please turn off" // Optional
                airplaneModeOffButtonText = "Airplane mode" // Optional
                showAirplaneModeOffButtons = true // Optional
            }
        }.build()

        createNotificationChannel()
        setRecyclerView()
    }

    private fun createNotificationChannel() {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            val channel =
                NotificationChannel(CHANNEL_ID, CHANNEL_NAME, NotificationManager.IMPORTANCE_HIGH)
            val manager = getSystemService(Context.NOTIFICATION_SERVICE) as NotificationManager
            manager.createNotificationChannel(channel)
        }
    }

    private fun setRecyclerView() {
        val service = RetrofitClient().apiRequest().create(DataService::class.java)
        service.getQuestion().enqueue(object : Callback<ResponseSurvey> {
            override fun onFailure(call: Call<ResponseSurvey>, t: Throwable) {
                Toast.makeText(this@MainActivity, "${t.message}", Toast.LENGTH_LONG).show()
            }

            override fun onResponse(call: Call<ResponseSurvey>, response: Response<ResponseSurvey>) {
                if (response.isSuccessful) {
                    if (response.body()!!.status == "success") {
                        val listData = response.body()!!.data

                        rv_survey.layoutManager = LinearLayoutManager(this@MainActivity)
                        val surveyAdapter = SurveyRecyclerAdapter(this@MainActivity, listData)
                        rv_survey.adapter = surveyAdapter

                        surveyAdapter.setOnItemClickCallback(object : SurveyRecyclerAdapter.OnItemClickCallback {
                            override fun onItemClicked(data: ArrayList<MSurvey>, position: Int) {
                                val notificationManager = NotificationManagerCompat.from(this@MainActivity)
                                btn_submit.setOnClickListener {
                                    for (i in 0 until data.size){
                                        postSurvey(data[i].idpertanyaan.toString(), data[i].idjawaban, "")
                                    }
                                    val notification = NotificationCompat.Builder(this@MainActivity, CHANNEL_ID)
                                        .setContentTitle("Pengisian Sukses")
                                        .setContentText("Terimakasih telah mengisi survey layanan kami, senang bisa melayani anda dengan sepenuh hati")
                                        .setStyle(NotificationCompat.BigTextStyle().bigText("Terimakasih telah mengisi survey layanan kami, senang bisa melayani anda dengan sepenuh hati"))
                                        .setSmallIcon(R.drawable.notif_survey)
                                        .setAutoCancel(true)
                                        .setPriority(NotificationCompat.PRIORITY_HIGH)
                                        .build()
                                    notificationManager.notify(NOTIFICATION_ID, notification)
                                    MotionToast.darkToast(this@MainActivity,
                                        response.body()!!.status,
                                        "Berhasil mengisi",
                                        MotionToast.TOAST_SUCCESS,
                                        MotionToast.GRAVITY_BOTTOM,
                                        MotionToast.LONG_DURATION,
                                        ResourcesCompat.getFont(this@MainActivity, R.font.helvetica_regular))
                                    startActivity(Intent(this@MainActivity, MainActivity::class.java))
                                    finish()
                                }
////
                            }

                        })

//
                    } else {
                        Toast.makeText(this@MainActivity, response.body()!!.status, Toast.LENGTH_LONG).show()
                    }

                } else {
                    Toast.makeText(this@MainActivity, response.message(), Toast.LENGTH_LONG).show()
                }
            }

        })
    }

    private fun postSurvey(idpertanyaan: String, idjawaban: String, komen: String) {
        val service = RetrofitClient().apiRequest().create(DataService::class.java)
        service.postSurvey(idpertanyaan, idjawaban, komen).enqueue(object :
            Callback<ResponseSurvey> {
            override fun onFailure(call: Call<ResponseSurvey>, t: Throwable) {

            }

            override fun onResponse(call: Call<ResponseSurvey>, response: Response<ResponseSurvey>) {

            }
        })
    }
}