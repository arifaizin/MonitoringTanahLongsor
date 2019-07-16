package com.iavariav.monitoringiot;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.os.CountDownTimer;
import android.widget.Toast;

import com.iavariav.monitoringiot.model.ResponseModel;
import com.iavariav.monitoringiot.rest.ApiConfig;
import com.iavariav.monitoringiot.rest.ApiService;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class MainActivity extends AppCompatActivity {

    private ResponseModel responseModel;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);


        getTime();
    }

    private void getTime() {

        new CountDownTimer(120000, 1000) {

            public void onTick(long millisUntilFinished) {
//                mTextField.setText("seconds remaining: " + millisUntilFinished / 1000);
                //here you can have your logic to set text to edittext
                Toast.makeText(MainActivity.this, "" + millisUntilFinished / 1000, Toast.LENGTH_SHORT).show();
            }

            public void onFinish() {
//                mTextField.setText("done!");
                getData();
                getTime();
            }

        }.start();
    }

    private void getData() {
        ApiService apiService = ApiConfig.getApiService();
        apiService.ambilData().enqueue(new Callback<ResponseModel>() {
            @Override
            public void onResponse(Call<ResponseModel> call, Response<ResponseModel> response) {
                if (response.isSuccessful()){
                    responseModel = response.body();


                }
            }

            @Override
            public void onFailure(Call<ResponseModel> call, Throwable t) {
                Toast.makeText(MainActivity.this, "" + t.getMessage(), Toast.LENGTH_SHORT).show();

            }
        });
    }
}
