package com.rmathur.weather;

import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.LayoutInflater;
import android.view.View;
import android.view.Menu;
import android.view.MenuItem;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.EditText;
import android.widget.ListView;

import com.orm.SugarContext;

import java.util.List;

public class MainActivity extends AppCompatActivity {

    private Context mContext;
    private ListView lvZipCodes;

    private List<Location> locationList;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        mContext = this;

        SugarContext.init(mContext);

        lvZipCodes = (ListView) findViewById(R.id.zip_codes);
        lvZipCodes.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Location clickedLoc = locationList.get(position);
                Intent intent = new Intent(mContext, WeatherDetailActivity.class);
                intent.putExtra(getString(R.string.zip_key), clickedLoc);
                startActivity(intent);
            }
        });
        updateZipCodes();

        FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
            runOnUiThread(new Runnable() {
                @Override
                public void run() {
                    addLocation();
                }
            });
            }
        });
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    @Override
    public void onDestroy() {
        SugarContext.terminate();
    }

    public void updateZipCodes() {
        locationList = Location.listAll(Location.class);
        lvZipCodes.setAdapter(new ZipcodeAdapter(this, locationList));
    }

    public void addLocation() {
        AlertDialog.Builder builder = new AlertDialog.Builder(mContext);
        builder.setTitle(getString(R.string.add_location));

        View viewInflated = LayoutInflater.from(mContext).inflate(R.layout.dialog_add_loc, (ViewGroup) findViewById(android.R.id.content), false);

        // Set up the input
        final EditText input = (EditText) viewInflated.findViewById(R.id.input);

        // Specify the type of input expected; this, for example, sets the input as a password, and will mask the text
        builder.setView(viewInflated);

        // Set up the buttons
        builder.setPositiveButton(android.R.string.ok, new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                dialog.dismiss();
                String zipcode = input.getText().toString();
                Location newLocation = new Location(zipcode);
                newLocation.save();
                updateZipCodes();
            }
        });
        builder.setNegativeButton(android.R.string.cancel, new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                dialog.cancel();
            }
        });

        builder.show();
    }
}
