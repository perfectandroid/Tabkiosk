package com.perfect.tabkiosk.Activity;

import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.telephony.TelephonyManager;
import android.util.Log;
import android.view.View;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.perfect.tabkiosk.Api.ApiInterface;
import com.perfect.tabkiosk.Helper.Common;
import com.perfect.tabkiosk.R;
import com.perfect.tabkiosk.Utils.InternetUtil;

import org.json.JSONException;
import org.json.JSONObject;

import java.security.KeyManagementException;
import java.security.KeyStoreException;
import java.security.cert.CertificateFactory;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.content.ContextCompat;
import okhttp3.OkHttpClient;
import okhttp3.RequestBody;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;
import retrofit2.converter.scalars.ScalarsConverterFactory;
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
import java.util.regex.Matcher;
import java.util.regex.Pattern;

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

public class ReasonActivity extends AppCompatActivity implements View.OnClickListener{
    Button btnSubmit;
    TextView txtvnotsatisfied,txtvSpeed,txtvImprove,txtvOthers;
    EditText txtvMobno;
    String session,imei;
    ProgressDialog progressDialog;
    String pattern = "^\\s*(?:\\+?(\\d{1,3}))?[-. (]*(\\d{3})[-. )]*(\\d{3})[-. ]*(\\d{4})(?: *x(\\d+))?\\s*$";
    CheckBox chekcbox,chekcbox1,chekcbox2,chekcbox3;
    TelephonyManager tm;
    boolean iscolor = true;
    Bundle extras;
    LinearLayout l1,l2,l3,l4;
    String reason;
    String chk1,chk2,chk3,chk4;
    Pattern r = Pattern.compile(pattern);
    String device_id;
    String validNumber = "^[6-9][0-9]{9}$";
    Matcher m;
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_reason);

        getSupportActionBar().hide();

        initiateViews();
        setRegViews();

       extras = getIntent().getExtras();
        if (extras != null) {

            session = extras.getString("Status");
            imei = extras.getString("imei");
            Log.i("Status",session);
            //The key argument here must match that used in the other activity
        }

     /*   tm = (TelephonyManager)getSystemService(Context.TELEPHONY_SERVICE);
        device_id = tm.getDeviceId();
        if(device_id!=null)
        {
            Log.i("Deviceid",device_id);
            Toast.makeText(getApplicationContext(),device_id,Toast.LENGTH_LONG).show();
        }*/

       /* chekcbox.setChecked(false);
        chekcbox1.setChecked(false);
        chekcbox2.setChecked(false);
        chekcbox3.setChecked(false);*/
    }

    private void initiateViews() {
        btnSubmit  =  (Button)findViewById(R.id.btnSubmit);

        txtvnotsatisfied  =  (TextView) findViewById(R.id.txtvnotsatisfied);
        txtvSpeed  =  (TextView)findViewById(R.id.txtvSpeed);
        txtvImprove  =  (TextView)findViewById(R.id.txtvImprove);
        txtvOthers  =  (TextView)findViewById(R.id.txtvOthers);

        txtvMobno  =  (EditText) findViewById(R.id.txtvMobno);
        txtvMobno.requestFocus();

        l1  =  (LinearLayout) findViewById(R.id.l1);
        l2  =  (LinearLayout) findViewById(R.id.l2);
        l3  =  (LinearLayout) findViewById(R.id.l3);
        l4  =  (LinearLayout) findViewById(R.id.l4);

      /*  chekcbox  =  (CheckBox) findViewById(R.id.chekcbox);
        chekcbox1  =  (CheckBox) findViewById(R.id.chekcbox1);
        chekcbox2  =  (CheckBox) findViewById(R.id.chekcbox2);
        chekcbox3  =  (CheckBox) findViewById(R.id.chekcbox3);*/
    }

    private void setRegViews() {

        txtvnotsatisfied.setOnClickListener(this);
        txtvSpeed.setOnClickListener(this);
        txtvImprove.setOnClickListener(this);
        txtvOthers.setOnClickListener(this);
        btnSubmit.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
       switch (v.getId())
       {
           case R.id.txtvnotsatisfied:
               if(iscolor)
               {

                   l1.setBackground(ContextCompat.getDrawable(this, R.drawable.wthchkbx));
                   reason = "2";
                   chk1   = "1";
                  /* l2.setBackground(ContextCompat.getDrawable(this, R.drawable.withoutchkbx));
                   l3.setBackground(ContextCompat.getDrawable(this, R.drawable.withoutchkbx));
                   l4.setBackground(ContextCompat.getDrawable(this, R.drawable.withoutchkbx));*/
                 //  l.setBackgroundColor(Color.BLUE);
                   iscolor = false;

               }

               else
               {
                   l1.setBackground(ContextCompat.getDrawable(this, R.drawable.withoutchkbx));

                   iscolor = true;
                   chk1="0";
               }

           break;
           case R.id.txtvSpeed:
               if(iscolor)
               {
                   l2.setBackground(ContextCompat.getDrawable(this, R.drawable.wthchkbx));
                   reason = "4";
                   chk2="1";

                  /* l1.setBackground(ContextCompat.getDrawable(this, R.drawable.withoutchkbx));
                   l3.setBackground(ContextCompat.getDrawable(this, R.drawable.withoutchkbx));
                   l4.setBackground(ContextCompat.getDrawable(this, R.drawable.withoutchkbx));*/
                   //  l.setBackgroundColor(Color.BLUE);
                   iscolor = false;

               }
               else
               {
                   l2.setBackground(ContextCompat.getDrawable(this, R.drawable.withoutchkbx));

                   iscolor = true;
                   chk2="0";
               }
               break;
           case R.id.txtvImprove:
               if(iscolor)
               {
                   l3.setBackground(ContextCompat.getDrawable(this, R.drawable.wthchkbx));
                   reason = "8";
                   chk3="1";
                  /* l1.setBackground(ContextCompat.getDrawable(this, R.drawable.withoutchkbx));
                   l2.setBackground(ContextCompat.getDrawable(this, R.drawable.withoutchkbx));
                   l4.setBackground(ContextCompat.getDrawable(this, R.drawable.withoutchkbx));*/
                   //  l.setBackgroundColor(Color.BLUE);
                   iscolor = false;

               }
               else
               {
                   l3.setBackground(ContextCompat.getDrawable(this, R.drawable.withoutchkbx));
                   iscolor = true;
                   chk3="0";
               }
               break;
           case R.id.txtvOthers:
               if(iscolor)
               {
                   l4.setBackground(ContextCompat.getDrawable(this, R.drawable.wthchkbx));
                   reason = "16";
                   chk4="1";
                 /*  l1.setBackground(ContextCompat.getDrawable(this, R.drawable.withoutchkbx));
                   l2.setBackground(ContextCompat.getDrawable(this, R.drawable.withoutchkbx));
                   l3.setBackground(ContextCompat.getDrawable(this, R.drawable.withoutchkbx));*/
                   //  l.setBackgroundColor(Color.BLUE);
                   iscolor = false;

               }
               else
               {
                   l4.setBackground(ContextCompat.getDrawable(this, R.drawable.withoutchkbx));
                   iscolor = true;
                   chk4="0";
               }
               break;
           case R.id.btnSubmit:
            /*   if(chekcbox.isChecked())
               {
                   Intent intent = new Intent (ReasonActivity.this,ThankComplaintActivity.class);
                   startActivity(intent);
                   //Toast.makeText(getApplicationContext(),"checkbox clickked",Toast.LENGTH_LONG).show();

               }
               else if(chekcbox1.isChecked())
               {
                   Intent intent = new Intent (ReasonActivity.this,ThankComplaintActivity.class);
                   startActivity(intent);
                 //  Toast.makeText(getApplicationContext(),"checkbox1 clickked",Toast.LENGTH_LONG).show();
                  *//* chekcbox.setChecked(false);
                   chekcbox2.setChecked(false);
                   chekcbox3.setChecked(false);*//*
               }
               else if(chekcbox2.isChecked())
               {
                   Intent intent = new Intent (ReasonActivity.this,ThankComplaintActivity.class);
                   startActivity(intent);
               //    Toast.makeText(getApplicationContext(),"checkbox2 clickked",Toast.LENGTH_LONG).show();
                 *//*  chekcbox.setChecked(false);
                   chekcbox1.setChecked(false);
                   chekcbox3.setChecked(false);*//*
               }
               else if(chekcbox3.isChecked())
               {
                   Intent intent = new Intent (ReasonActivity.this,ThankComplaintActivity.class);
                   startActivity(intent);
                  // Toast.makeText(getApplicationContext(),"checkbox3 clickked",Toast.LENGTH_LONG).show();
                 *//*  chekcbox1.setChecked(false);
                   chekcbox2.setChecked(false);
                   chekcbox.setChecked(false);*//*
               }
            *//*   else if(chekcbox.isChecked()||chekcbox1.isChecked()||chekcbox2.isChecked()||chekcbox3.isChecked())
               {

               }*//*
               else
               {
                   Toast.makeText(getApplicationContext(),R.string.option,Toast.LENGTH_LONG).show();
               }*/
               if(chk1==null&& chk2==null && chk3==null && chk4==null)
               {
                   Toast.makeText(getApplicationContext(),R.string.option,Toast.LENGTH_LONG).show();
               }
             else if(txtvMobno.getText().toString().isEmpty())
               {
                   Toast.makeText(getApplicationContext(),R.string.mobno,Toast.LENGTH_LONG).show();
               }


              /* else if((value == null || value.length() == 0))
               {
                   Toast.makeText(getApplicationContext(),R.string.option,Toast.LENGTH_LONG).show();
               }*/
               else
               {
                 if(!txtvMobno.getText().toString().isEmpty())
               {
                  // m = r.matcher(txtvMobno.getText().toString().trim());

                //   if (m.find()) {
                       if (txtvMobno.getText().toString().matches(validNumber)) {
                           getCustomerfeedback();
                       }
                       else {
                           Toast.makeText(getApplicationContext(), R.string.invalid, Toast.LENGTH_LONG).show();
                       }


                 //  } else {
                      // Toast.makeText(getApplicationContext(), R.string.invalid, Toast.LENGTH_LONG).show();
                  // }

               }
                //   validateMobile();

               }





               break;
       }
    }



    private void getCustomerfeedback() {

        if(chk1!=null)
        {

            if(chk1.equals("1"))
            {
                reason = "2";
            }
        }
        if(chk2!=null)
        {


            if(chk2.equals("1"))
            {
                reason = "4";
            }
        }
          //  Log.i("VALUES",chk1+"\n"+chk2+"\n"+chk3+"\n"+chk4);
        if(chk3!=null)
        {
            if(chk3.equals("1"))
            {
                reason = "8";
            }

        }
        if(chk4!=null)
        {
            if(chk4.equals("1"))
            {
                reason = "16";
            }
        }
        if(chk1!=null&&chk2!=null) {
            if(chk1.equals("1")&&chk2.equals("1"))
            {
                reason = "6";
            }
        }
        if(chk1!=null&&chk3!=null) {
                if(chk1.equals("1")&&chk3.equals("1"))
            {
                reason = "10";
            }
        }
        if(chk1!=null&&chk2!=null&&chk3!=null) {
            if(chk1.equals("1")&&chk2.equals("1")&&chk3.equals("1"))
            {
                reason = "14";
            }
        }
        if(chk1!=null&&chk4!=null){
            if(chk1.equals("1")&&chk4.equals("1"))
            {
                reason = "18";
            }
        }
        if(chk2!=null&&chk3!=null){
            if(chk2.equals("1")&&chk3.equals("1"))
            {
                reason = "12";
            }
        }
        if(chk2!=null&&chk4!=null){
            if(chk2.equals("1")&&chk4.equals("1"))
            {
                reason = "20";
            }
        }

        if(chk3!=null&&chk4!=null){
            if(chk3.equals("1")&&chk4.equals("1"))
            {
                reason = "24";
            }
        }
        if(chk2!=null&&chk3!=null&&chk4!=null){
            if(chk2.equals("1")&&chk3.equals("1")&&chk4.equals("1"))
            {
                reason = "28";
            }
        }
        if(chk1!=null&&chk2!=null&&chk3!=null&&chk4!=null){
            if(chk1.equals("1")&&chk2.equals("1")&&chk3.equals("1")&&chk4.equals("1"))
            {
                reason = "30";
            }
        }



       /* try {
            telephonyManager = (TelephonyManager)getSystemService(Context.TELEPHONY_SERVICE);

            if (android.os.Build.VERSION.SDK_INT >= 26) {
                imei=telephonyManager.getImei();
                Log.i("IMEI",imei);
            }
            else
            {
                imei=telephonyManager.getDeviceId();
                Log.i("IMEI",imei);
            }


        }catch(SecurityException e)
        {

        }*/


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

                        requestObject1.put("FK_Reason", Common.encryptStart(reason));
                       // 8606658217
                        requestObject1.put("Phonenumber", Common.encryptStart(txtvMobno.getText().toString()));
                        requestObject1.put("Status", Common.encryptStart(session));
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
                                        Intent i = new Intent (ReasonActivity.this,ThankComplaintActivity.class);
                                        startActivity(i);
                                        finish();
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
