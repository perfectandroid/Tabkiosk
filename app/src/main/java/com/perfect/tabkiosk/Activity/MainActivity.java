package com.perfect.tabkiosk.Activity;

import android.Manifest;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.os.Build;
import android.os.Bundle;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.perfect.tabkiosk.Api.ApiInterface;
import com.perfect.tabkiosk.Helper.Common;
import com.perfect.tabkiosk.R;
import com.perfect.tabkiosk.Utils.InternetUtil;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import okhttp3.OkHttpClient;
import okhttp3.RequestBody;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;
import retrofit2.converter.scalars.ScalarsConverterFactory;

import android.telephony.TelephonyManager;
import android.util.Log;
import android.view.View;

import android.widget.Button;
import android.widget.ImageView;
import android.widget.Toast;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.io.InputStream;
import java.security.KeyManagementException;
import java.security.KeyStore;
import java.security.KeyStoreException;
import java.security.NoSuchAlgorithmException;
import java.security.cert.Certificate;
import java.security.cert.CertificateException;
import java.security.cert.CertificateFactory;
import java.security.cert.X509Certificate;

import javax.net.ssl.HostnameVerifier;
import javax.net.ssl.SSLContext;
import javax.net.ssl.SSLSession;
import javax.net.ssl.SSLSocketFactory;
import javax.net.ssl.TrustManager;
import javax.net.ssl.TrustManagerFactory;
import javax.net.ssl.X509TrustManager;
import android.provider.Settings;
import android.provider.Settings.System;
public class MainActivity extends AppCompatActivity implements View.OnClickListener{
   // Button im_excellent,im_happy,im_jstok,im_complaint;
    ImageView im_excellent,im_happy,im_jstok,im_complaint;
    public static String status;
    String imei;
    TelephonyManager telephonyManager;
    private static final int MY_PERMISSIONS_REQUEST_READ_PHONE_STATE = 100;
    ProgressDialog progressDialog;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        initiateViews();
        setRegViews();
        telephonyManager = (TelephonyManager) getSystemService(Context.TELEPHONY_SERVICE);

        imei = Settings.Secure.getString(this.getContentResolver(),
                Settings.Secure.ANDROID_ID);

        Log.d("Android","Android ID : "+imei);
       /* if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            if (checkSelfPermission(Manifest.permission.READ_PHONE_STATE) == PackageManager.PERMISSION_GRANTED) {
                if (telephonyManager != null) {
                    try {
                        imei = telephonyManager.getImei();
                    } catch (Exception e) {
                        e.printStackTrace();
                        imei = Settings.Secure.getString(this.getContentResolver(), Settings.Secure.ANDROID_ID);
                    }
                }
            } else {
                ActivityCompat.requestPermissions(MainActivity.this, new String[]{Manifest.permission.READ_PHONE_STATE}, 1010);
            }
        } else {
            if (ActivityCompat.checkSelfPermission(MainActivity.this, Manifest.permission.READ_PHONE_STATE) == PackageManager.PERMISSION_GRANTED) {
                if (telephonyManager != null) {
                    imei = telephonyManager.getDeviceId();
                }
            } else {
                ActivityCompat.requestPermissions(MainActivity.this, new String[]{Manifest.permission.READ_PHONE_STATE}, 1010);
            }
        }
*/
    }

    private void initiateViews() {
       /* im_excellent  =  (Button) findViewById(R.id.im_excellent);
        im_happy  =  (Button) findViewById(R.id.im_happy);
        im_jstok  =  (Button) findViewById(R.id.im_jstok);
        im_complaint  =  (Button) findViewById(R.id.im_complaint);*/
        im_excellent  =  (ImageView) findViewById(R.id.im_excellent);
        im_happy  =  (ImageView) findViewById(R.id.im_happy);
        im_jstok  =  (ImageView) findViewById(R.id.im_jstok);
        im_complaint  =  (ImageView) findViewById(R.id.im_complaint);
    }

    private void setRegViews() {
        im_excellent.setOnClickListener(this);
        im_happy.setOnClickListener(this);
        im_jstok.setOnClickListener(this);
        im_complaint.setOnClickListener(this);
    }


    @Override
    public void onClick(View v ) {

        switch (v.getId())
        {
            case R.id.im_excellent:
                status="1";
                getCustomerfeedback(status);

                break;
            case R.id.im_happy:
                status="2";
                getCustomerfeedback(status);
                break;
            case R.id.im_jstok:
                status="3";
                getCustomerfeedback(status);
                break;
            case R.id.im_complaint:
                status="4";
                Intent intent = new Intent (MainActivity.this,ReasonActivity.class);
                intent.putExtra("Status", status);
                intent.putExtra("imei",imei);
                startActivity(intent);
                break;
        }
    }

    private void getCustomerfeedback(String status) {

        if (new InternetUtil(this).isInternetOn()) {
            progressDialog = new ProgressDialog(this, R.style.Progress);
            progressDialog.setProgressStyle(android.R.style.Widget_ProgressBar);
            progressDialog.setCancelable(false);
            progressDialog.setIndeterminate(true);
            progressDialog.setIndeterminateDrawable(this.getResources()
                    .getDrawable(R.drawable.progress));
            progressDialog.show();
            try{
                OkHttpClient client = new OkHttpClient.Builder()
                        .sslSocketFactory(getSSLSocketFactory())
                        .hostnameVerifier(getHostnameVerifier())
                        .build();
                Gson gson = new GsonBuilder()
                        .setLenient()
                        .create();
                Retrofit retrofit = new Retrofit.Builder()
                        .baseUrl(Common.BASE_URL)
                        .addConverterFactory(ScalarsConverterFactory.create())
                        .addConverterFactory(GsonConverterFactory.create(gson))
                        .client(client)
                        .build();
                ApiInterface apiService = retrofit.create(ApiInterface.class);
                final JSONObject requestObject1 = new JSONObject();
                try {

                    requestObject1.put("FK_Reason", Common.encryptStart("0"));
                    // 8606658217
                    requestObject1.put("Phonenumber", Common.encryptStart("0"));
                    requestObject1.put("Status", Common.encryptStart(status));
                    requestObject1.put("BranchCode", Common.encryptStart("1"));
                    requestObject1.put("Device_ID", Common.encryptStart(imei));
                  //  requestObject1.put("Device_ID", Common.encryptStart("100"));
                } catch (JSONException e) {
                    e.printStackTrace();
                }
                RequestBody body = RequestBody.create(okhttp3.MediaType.parse("application/json; charset=utf-8"), requestObject1.toString());
                Call<String> call = apiService.getCustomerfeed(body);
                call.enqueue(new Callback<String>() {
                    @Override public void onResponse(Call<String> call, retrofit2.Response<String> response) {
                        try {
                            progressDialog.dismiss();
                            JSONObject jObject = new JSONObject(response.body());
                            Log.i("Response",response.body());
                            //   Toast.makeText(getApplicationContext(),response.body(),Toast.LENGTH_LONG).show();
                            JSONObject jmember = jObject.getJSONObject("FeedBackEntryInfo");
                            String StatusCode = jObject.getString("StatusCode");
                            if(StatusCode.equals("0")){
                                //  Toast.makeText(getApplicationContext(), jmember.getString("Status"),Toast.LENGTH_LONG).show();
                                String resp = jmember.getString("Status");
                                if(resp.equals("true"))
                                {
                                    Intent i = new Intent (MainActivity.this,ThankyouActivity.class);
                                    startActivity(i);
                                  //  finish();
                                }
                            }
                            else{
                                Toast.makeText(getApplicationContext(), jmember.getString("ResponseMessage"),Toast.LENGTH_LONG).show();
                            }
                        } catch (JSONException e) {
                            e.printStackTrace();
                            progressDialog.dismiss();
                        }
                    }
                    @Override
                    public void onFailure(Call<String> call, Throwable t) {
                        progressDialog.dismiss();
                    }
                });
            } catch (Exception e) {
                e.printStackTrace();
            }  }else {
            // Intent in = new Intent(this,NoInternetActivity.class);
            //  startActivity(in);
        }

    }

    private HostnameVerifier getHostnameVerifier() {
        return new HostnameVerifier() {
            @Override
            public boolean verify(String hostname, SSLSession session) {
                return true;
            }
        };
    }
    private SSLSocketFactory getSSLSocketFactory()
            throws CertificateException, KeyStoreException, IOException,
            NoSuchAlgorithmException, KeyManagementException {
        CertificateFactory cf = CertificateFactory.getInstance("X.509");
        InputStream caInput = getAssets().open("client-demo.pem"); // File path: app\src\main\res\raw\your_cert.cer
        Certificate ca = cf.generateCertificate(caInput);
        caInput.close();
        KeyStore keyStore = KeyStore.getInstance("BKS");
        keyStore.load(null, null);
        keyStore.setCertificateEntry("ca", ca);
        String tmfAlgorithm = TrustManagerFactory.getDefaultAlgorithm();
        TrustManagerFactory tmf = TrustManagerFactory.getInstance(tmfAlgorithm);
        tmf.init(keyStore);
        TrustManager[] wrappedTrustManagers = getWrappedTrustManagers(tmf.getTrustManagers());
        SSLContext sslContext = SSLContext.getInstance("TLS");
        sslContext.init(null, wrappedTrustManagers, null);
        return sslContext.getSocketFactory();
    }
    private TrustManager[] getWrappedTrustManagers(TrustManager[] trustManagers) {
        final X509TrustManager originalTrustManager = (X509TrustManager) trustManagers[0];
        return new TrustManager[]{
                new X509TrustManager() {
                    public X509Certificate[] getAcceptedIssuers() {
                        return originalTrustManager.getAcceptedIssuers();
                    }
                    public void checkClientTrusted(X509Certificate[] certs, String authType) {
                        try {
                            if (certs != null && certs.length > 0){
                                certs[0].checkValidity();
                            } else {
                                originalTrustManager.checkClientTrusted(certs, authType);
                            }
                        } catch (CertificateException e) {
                            Log.w("checkClientTrusted", e.toString());
                        }
                    }
                    public void checkServerTrusted(X509Certificate[] certs, String authType) {
                        try {
                            if (certs != null && certs.length > 0){
                                certs[0].checkValidity();
                            } else {
                                originalTrustManager.checkServerTrusted(certs, authType);
                            }
                        } catch (CertificateException e) {
                            Log.w("checkServerTrusted", e.toString());
                        }
                    }
                }
        };
    }

}