package com.qr.stt;

import android.app.Activity;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.speech.RecognizerIntent;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.ViewDebug;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.List;
import java.util.Locale;

public class MainActivity extends AppCompatActivity {

    String Fcode="";
    TextView txt;
    Spinner lang;
    ImageView speak;

    ArrayList<String> Langauges;
    ArrayList<String> codes;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        speak=findViewById(R.id.btn);
        txt=findViewById(R.id.txt);
        lang=findViewById(R.id.spin);

        Langauges=new ArrayList<String>();
        Langauges.add("English");Langauges.add("Arabic");

        codes=new ArrayList<String>();
        codes.add("en");codes.add("ar");

        ArrayAdapter adapt=new ArrayAdapter(MainActivity.this,R.layout.spin,R.id.txt,Langauges);
        lang.setAdapter(adapt);

        lang.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                Fcode=codes.get(position);
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });

        speak.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                txt.setText("");
                Intent recognizerIntent = new Intent(RecognizerIntent.ACTION_RECOGNIZE_SPEECH);
//                recognizerIntent.putExtra(RecognizerIntent.EXTRA_LANGUAGE,new );
                recognizerIntent.putExtra(RecognizerIntent.EXTRA_LANGUAGE,Fcode);
                recognizerIntent.putExtra(RecognizerIntent.EXTRA_LANGUAGE_MODEL, RecognizerIntent.LANGUAGE_MODEL_FREE_FORM);
                recognizerIntent.putExtra(RecognizerIntent.EXTRA_PROMPT, "Speak now");
                try {
                    startActivityForResult(recognizerIntent, 1);
                } catch (Exception ex) {
                    Toast.makeText(MainActivity.this, ex.getMessage(), Toast.LENGTH_SHORT).show();
                }
            }
        });
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == 1) {
            if (resultCode == RESULT_OK && data != null) {
                ArrayList<String> list = data.getStringArrayListExtra(RecognizerIntent.EXTRA_RESULTS);
                if(list.size()>0)
                {
                    txt.setText(list.get(0));
                }
                else
                {
                    Toast.makeText(this, "No Speech Found", Toast.LENGTH_SHORT).show();
                }
            }
        }
    }
}
