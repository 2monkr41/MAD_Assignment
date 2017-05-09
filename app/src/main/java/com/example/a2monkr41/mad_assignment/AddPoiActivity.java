package com.example.a2monkr41.mad_assignment;

import android.app.Activity;
import android.os.Bundle;
import android.content.Intent;
import android.widget.Button;
import android.view.View;
import android.widget.ArrayAdapter;
import android.app.ListActivity;
import android.widget.TextView;
import android.widget.EditText;
import android.view.View.OnClickListener;

public class AddPoiActivity extends Activity implements View.OnClickListener{



    public void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.add_poi_layout);
        Button b = (Button)findViewById(R.id.btn1);
        b.setOnClickListener(this);
    }

    public void onClick(View view)
    {
        Bundle bundle=new Bundle();

        EditText poiName = (EditText)findViewById(R.id.poiname);
        bundle.putString("com.example.poiname",poiName.getText().toString());

        EditText poiType = (EditText)findViewById(R.id.poitype);
        bundle.putString("com.example.poitype",poiType.getText().toString());

        EditText poiDesc = (EditText)findViewById(R.id.poidescription);
        bundle.putString("com.example.poidescription",poiDesc.getText().toString());

        Intent intent = new Intent();
        intent.putExtras(bundle);
        setResult(RESULT_OK,intent);
        finish();

    }

}
