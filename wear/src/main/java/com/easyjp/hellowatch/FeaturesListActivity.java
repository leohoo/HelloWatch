package com.easyjp.hellowatch;

import android.app.Activity;
import android.content.pm.FeatureInfo;
import android.content.pm.PackageManager;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.ArrayAdapter;
import android.widget.ListView;

import java.util.ArrayList;
import java.util.List;

public class FeaturesListActivity extends Activity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_features_list);


        PackageManager pm = getPackageManager();

        FeatureInfo[] features = pm.getSystemAvailableFeatures();

        final ListView listview = (ListView) findViewById(R.id.listview);

        List<String> items = new ArrayList<String>();
        for(FeatureInfo f : features) {
            if(null != f.name)
                items.add(f.name);
            else
                items.add(f.toString());
        }

        final ArrayAdapter adapter = new ArrayAdapter(this,
                R.layout.feature_item_layout, items);

        listview.setAdapter(adapter);
    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.features_list, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();
        if (id == R.id.action_settings) {
            return true;
        }
        return super.onOptionsItemSelected(item);
    }
}
