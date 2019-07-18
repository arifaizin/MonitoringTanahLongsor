package com.iavariav.monitoringiot;

import android.app.NotificationManager;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.content.SharedPreferences;
import android.os.Build;
import android.os.Bundle;
import android.os.CountDownTimer;
import android.text.TextUtils;
import android.util.Log;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AppCompatActivity;
import androidx.localbroadcastmanager.content.LocalBroadcastManager;

import com.google.firebase.messaging.FirebaseMessaging;
import com.iavariav.monitoringiot.helper.Config;
import com.iavariav.monitoringiot.model.ResponseModel;
import com.iavariav.monitoringiot.rest.ApiConfig;
import com.iavariav.monitoringiot.rest.ApiConfigServer;
import com.iavariav.monitoringiot.rest.ApiService;
import com.iavariav.monitoringiot.util.NotificationUtils;

import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class MainActivity extends AppCompatActivity {

    private String regId;

    private ResponseModel responseModel;

    private TextView tvDate;
    private TextView tvClock;
    private TextView tvCurahHujan;
    private TextView tvKadarAir;
    private TextView tvPergeseran;
    private TextView tvKemiringan;
    private TextView tvStatus;
    private EditText edtRegID;
    private TextView tvWaktu;

    private static final String TAG = MainActivity.class.getSimpleName();
    private BroadcastReceiver mRegistrationBroadcastReceiver;
    private NotificationManager mManager;

    @RequiresApi(api = Build.VERSION_CODES.O)
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        initView();
//        getTime();
        getData();
        displayFirebaseRegId();
        mRegistrationBroadcastReceiver = new BroadcastReceiver() {
            @Override
            public void onReceive(Context context, Intent intent) {

                // checking for type intent filter
                if (intent.getAction().equals(Config.REGISTRATION_COMPLETE)) {
                    // gcm successfully registered
                    // now subscribe to `global` topic to receive app wide notifications
                    FirebaseMessaging.getInstance().subscribeToTopic(Config.TOPIC_GLOBAL);

                    displayFirebaseRegId();

                } else if (intent.getAction().equals(Config.PUSH_NOTIFICATION)) {
                    // new push notification is received

                    String message = intent.getStringExtra("message");

                    Toast.makeText(getApplicationContext(), "Peringatan : " + message, Toast.LENGTH_LONG).show();

                    edtRegID.setText(message);
                }
            }
        };


    }

    private void getTime() {

        new CountDownTimer(120000, 1000) {

            public void onTick(long millisUntilFinished) {
                tvWaktu.setText("" + millisUntilFinished / 1000 + " detik");
                //here you can have your logic to set text to edittext
//                Toast.makeText(MainActivity.this, "" + millisUntilFinished / 1000, Toast.LENGTH_SHORT).show();
            }

            public void onFinish() {
//                mTextField.setText("done!");
                getData();

            }

        }.start();
    }

    private void getData() {
        ApiService apiService = ApiConfig.getApiService();
        apiService.ambilData().enqueue(new Callback<ResponseModel>() {
            @Override
            public void onResponse(Call<ResponseModel> call, Response<ResponseModel> response) {
                if (response.isSuccessful()) {
                    responseModel = response.body();
                    tvDate.setText(responseModel.getTanggal());
                    tvClock.setText(responseModel.getWaktu());
                    tvCurahHujan.setText(responseModel.getCurahHujan() + "°C");
                    tvKadarAir.setText(responseModel.getKadarAir() + "°C");
                    tvPergeseran.setText(responseModel.getPergeseran() + "°");
                    tvKemiringan.setText(responseModel.getKemiringan() + "°");
                    tvStatus.setText(responseModel.getStatus());
                    getTime();

                    if (responseModel.getStatus().equalsIgnoreCase("Bahaya")){
                        ApiService service = ApiConfigServer.getApiService();
                        service.postData(responseModel.getStatus(), "Bahaya Segera di Tindak Lanjuti", "individual", regId)
                                .enqueue(new Callback<ResponseBody>() {
                                    @Override
                                    public void onResponse(Call<ResponseBody> call, Response<ResponseBody> response) {
                                        if (response.isSuccessful()){
                                            Log.e(TAG, "onResponse: " + "Notif sukses");
                                        }
                                    }

                                    @Override
                                    public void onFailure(Call<ResponseBody> call, Throwable t) {
                                        Toast.makeText(MainActivity.this, "" + t.getMessage(), Toast.LENGTH_SHORT).show();

                                    }
                                });
                    }
                     else if (responseModel.getStatus().equalsIgnoreCase("Siaga")){
                        ApiService service = ApiConfigServer.getApiService();
                        service.postData(responseModel.getStatus(), "Siaga Segera di Tindak Lanjuti", "individual", regId)
                                .enqueue(new Callback<ResponseBody>() {
                                    @Override
                                    public void onResponse(Call<ResponseBody> call, Response<ResponseBody> response) {
                                        if (response.isSuccessful()){
                                            Log.e(TAG, "onResponse: " + "Notif sukses");
                                        }
                                    }

                                    @Override
                                    public void onFailure(Call<ResponseBody> call, Throwable t) {
                                        Toast.makeText(MainActivity.this, "" + t.getMessage(), Toast.LENGTH_SHORT).show();

                                    }
                                });
                    }

                }
            }

            @Override
            public void onFailure(Call<ResponseModel> call, Throwable t) {
                Toast.makeText(MainActivity.this, "" + t.getMessage(), Toast.LENGTH_SHORT).show();

            }
        });
    }


    // Fetches reg id from shared preferences
    // and displays on the screen
    private void displayFirebaseRegId() {
        SharedPreferences pref = getApplicationContext().getSharedPreferences(Config.SHARED_PREF, 0);
        regId = pref.getString("regId", null);

        Log.e(TAG, "Firebase reg id: " + regId);

        if (!TextUtils.isEmpty(regId))
            edtRegID.setText("Firebase Reg Id: " + regId);
        else
            edtRegID.setText("Firebase Reg Id is not received yet!");
    }

    @Override
    protected void onResume() {
        super.onResume();

        // register GCM registration complete receiver
        LocalBroadcastManager.getInstance(this).registerReceiver(mRegistrationBroadcastReceiver,
                new IntentFilter(Config.REGISTRATION_COMPLETE));

        // register new push message receiver
        // by doing this, the activity will be notified each time a new message arrives
        LocalBroadcastManager.getInstance(this).registerReceiver(mRegistrationBroadcastReceiver,
                new IntentFilter(Config.PUSH_NOTIFICATION));

        // clear the notification area when the app is opened
        NotificationUtils.clearNotifications(getApplicationContext());
    }

    @Override
    protected void onPause() {
        LocalBroadcastManager.getInstance(this).unregisterReceiver(mRegistrationBroadcastReceiver);
        super.onPause();
    }

    private void initView() {
        tvDate = findViewById(R.id.tvDate);
        tvClock = findViewById(R.id.tvClock);
        tvCurahHujan = findViewById(R.id.tvCurahHujan);
        tvKadarAir = findViewById(R.id.tvKadarAir);
        tvPergeseran = findViewById(R.id.tvPergeseran);
        tvKemiringan = findViewById(R.id.tvKemiringan);
        tvStatus = findViewById(R.id.tvStatus);
        edtRegID = findViewById(R.id.edtRegID);
        tvWaktu = findViewById(R.id.tvWaktu);
    }
}
