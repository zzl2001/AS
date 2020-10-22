package com.example.myapplication;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;

public class B_Activity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_b_);

        Intent intent = this.getIntent();
        Bundle bundle = intent.getExtras();
        String a = bundle.getString("a");
        Log.d("msg",a);
        String b = bundle.getString("b");

        String fuhao = bundle.getString("fuhao");
        Log.d("msg",fuhao);
        Double sum = null;
        if(fuhao.equals("+") ) {
            sum =  Double.parseDouble(a)+Double.parseDouble(b);
        }if(fuhao.equals("-"))
            sum =  Double.parseDouble(a)-Double.parseDouble(b);
        if(fuhao.equals("*"))
            sum = Double.parseDouble(a)*Double.parseDouble(b);
        if(fuhao.equals("/"))
            sum = Double.parseDouble(a)/Double.parseDouble(b);
        bundle.putDouble("sum", sum);
        intent.putExtras(bundle);
        setResult(RESULT_OK, intent);
        finish();
    }
}
