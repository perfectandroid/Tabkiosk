package com.maniparambil.jijo.tabkiosk.Activity;

import android.app.ProgressDialog;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.maniparambil.jijo.tabkiosk.Api.ApiInterface;
import com.maniparambil.jijo.tabkiosk.Helper.Common;
import com.maniparambil.jijo.tabkiosk.Utils.InternetUtil;
import com.maniparambil.tabkiosk2.R;

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

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import okhttp3.OkHttpClient;
import okhttp3.RequestBody;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;
import retrofit2.converter.scalars.ScalarsConverterFactory;

public class Activity_Main extends AppCompatActivity implements View.OnClickListener {
    Button btnDisappointed,btnJustOk,btnSoHappy,btnJustHappy;
    ProgressDialog progressDialog;
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_customerfeedback);
        initiateViews();
        setRegViews();

        //getCustomerfeedback();
    }

    private void getCustomerfeedback() {


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

                        requestObject1.put("FK_Reason", "1");
                        requestObject1.put("Phonenumber", "8606658217");
                        requestObject1.put("Status", "1");
                        requestObject1.put("BranchCode", "1");
                        requestObject1.put("Device_ID", "100");
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
                               /* JSONObject jmember = jObject.getJSONObject("UserNameAndPasswordChk");
                                String ResponseCode = jmember.getString("ResponseCode");
                                if(ResponseCode.equals("0")){
                                    Toast.makeText(getApplicationContext(), jmember.getString("ResponseMessage"),Toast.LENGTH_LONG).show();
                                }
                                if (ResponseCode.equals("-1")){
                                    Toast.makeText(getApplicationContext(), jmember.getString("ResponseMessage"),Toast.LENGTH_LONG).show();
                                }*/
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


    private void setRegViews() {

        btnDisappointed.setOnClickListener(this);
        btnJustOk.setOnClickListener(this);
        btnSoHappy.setOnClickListener(this);
        btnJustHappy.setOnClickListener(this);
    }

    private void initiateViews() {
        btnDisappointed  =  (Button) findViewById(R.id.btnDisappointed);
        btnJustOk  =  (Button) findViewById(R.id.btnJustOk);
        btnSoHappy  =  (Button) findViewById(R.id.btnSoHappy);
        btnJustHappy  =  (Button) findViewById(R.id.btnJustHappy);
    }

    @Override
    public void onClick(View v) {
        switch(v.getId()) {
            case R.id.btnDisappointed:

                Intent i = new Intent (Activity_Main.this,Activity_Thankyou.class);
                startActivity(i);
                break;
            case R.id.btnJustOk:

                Intent in = new Intent (Activity_Main.this,Activity_Reason.class);
                startActivity(in);
                break;
            case R.id.btnSoHappy:
                Intent inn = new Intent (Activity_Main.this,Activity_Reason.class);
                startActivity(inn);
                break;
            case R.id.btnJustHappy:

                Intent intent = new Intent (Activity_Main.this,Activity_Reason.class);
                startActivity(intent);
                break;
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
