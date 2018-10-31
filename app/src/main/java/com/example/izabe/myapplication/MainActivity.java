package com.example.izabe.myapplication;


import android.app.Activity;
import android.app.ProgressDialog;
import android.content.Context;
import android.os.AsyncTask;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.example.izabe.myapplication.R;

import org.json.JSONObject;

import java.io.BufferedInputStream;
import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.net.HttpURLConnection;
import java.net.URL;
import java.net.URLConnection;


public class MainActivity extends Activity {
public static Context context;
public static View view;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        context = this;
        view = findViewById(R.id.main_view);

        ((Button) findViewById(R.id.start_button))
                .setOnClickListener(new OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        new WebServiceHandler()
                                .execute("https://jsonplaceholder.typicode.com/posts/45");
                    }
                });
    }







}
