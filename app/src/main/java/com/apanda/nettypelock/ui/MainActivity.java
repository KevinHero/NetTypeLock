package com.apanda.nettypelock.ui;

import android.content.Context;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.Spinner;
import android.widget.Toast;

import com.apanda.nettypelock.R;
import com.apanda.nettypelock.mod.ModNetLock;

public class MainActivity extends AppCompatActivity implements View.OnClickListener {
    private Spinner mNetworks;
    private ArrayAdapter<CharSequence> mAdapter;
    private SharedPreferences mPref;
    private Button mLock;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        mPref = getSharedPreferences(ModNetLock.PREF_XML, Context.MODE_WORLD_READABLE);

        mNetworks = (Spinner) findViewById(R.id.main_spinner);
        mAdapter = ArrayAdapter.createFromResource(this, R.array.networks, android.R.layout.simple_spinner_item);
        mAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        mNetworks.setAdapter(mAdapter);

        mLock = (Button) findViewById(R.id.main_lock);

        mNetworks.post(new Runnable() {
            @Override
            public void run() {
                mNetworks.setSelection(mPref.getInt(ModNetLock.PREF_NAME, 0));
                mLock.setOnClickListener(MainActivity.this);
            }
        });
    }

    @Override
    public void onClick(View v) {
        if (v != mLock) throw new RuntimeException("WHAT???");

        mPref.edit().putInt(ModNetLock.PREF_NAME, mNetworks.getSelectedItemPosition()).commit();
        Toast.makeText(this, R.string.tip, Toast.LENGTH_SHORT).show();
    }
}
