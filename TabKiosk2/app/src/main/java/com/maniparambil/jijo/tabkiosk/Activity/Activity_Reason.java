package com.maniparambil.jijo.tabkiosk.Activity;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EdgeEffect;
import android.widget.EditText;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.maniparambil.tabkiosk2.R;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

public class Activity_Reason extends AppCompatActivity implements View.OnClickListener {
   Button btnsubmitreason;
   EditText txtMobileNumber;
   TextView t1;
   RelativeLayout reasonspage;
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_reason);
        initiateViews();
        setRegViews();
    }

    private void initiateViews() {
        btnsubmitreason  =(Button) findViewById(R.id.btnsubmitreason);
        txtMobileNumber  =(EditText) findViewById(R.id.txtMobileNumber);
      //  reasonspage = (RelativeLayout)findViewById(R.id.reasonspage);
      /*  reasonspage.setOnClickListener(new View.OnClickListener()
        {

            @Override
            public void onClick(View v)
            {

              //  Toast.makeText(getApplicationContext(),"clicked",Toast.LENGTH_LONG).show();
            }
        });*/
       /* btn_submitreason  =  (Button) findViewById(R.id.btn_submitreason);
        txtMobileNumber  =  (EditText) findViewById(R.id.txtMobileNumber);*/

    }

    private void setRegViews() {
        btnsubmitreason.setOnClickListener(this);
      /*
        txtMobileNumber.setOnClickListener(this);*/
       // reasonspage.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId())
        {
          /*  case R.id.reasonspage:
              //  Toast.makeText(getApplicationContext(),"clicked",Toast.LENGTH_LONG).show();
                break;*/
            case R.id.btnsubmitreason:
                Toast.makeText(getApplicationContext(),"clicked",Toast.LENGTH_LONG).show();
                break;
           /* case R.id.txtMobileNumber:
                Toast.makeText(getApplicationContext(),"clicked",Toast.LENGTH_LONG).show();
                break;*/

        }
    }
}
