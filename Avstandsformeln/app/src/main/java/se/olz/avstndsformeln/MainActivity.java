package se.olz.avstndsformeln;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.preference.PreferenceManager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.EditText;
import android.widget.TextView;
import static java.lang.Double.parseDouble;
import static java.lang.Math.pow;
import static java.lang.Math.sqrt;
import static java.lang.String.valueOf;
import static se.olz.avstndsformeln.SettingsFragment.KEY_PREF_DECIMALS;
import static se.olz.avstndsformeln.SettingsFragment.KEY_PREF_UNIT;

public class MainActivity extends AppCompatActivity {
    SharedPreferences sharedPref;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        PreferenceManager.setDefaultValues(this, R.xml.preferences, false);
        sharedPref = PreferenceManager.getDefaultSharedPreferences(this);

        Toolbar myToolbar = (Toolbar) findViewById(R.id.main_toolbar);
        myToolbar.showOverflowMenu();
        setSupportActionBar(myToolbar);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();
        if (id == R.id.action_settings) {
            Intent intent = new Intent(this, SettingsActivity.class);
            startActivity(intent);
        }
        return super.onOptionsItemSelected(item);
    }

    public void calc(View view) {
        String outputText;

        EditText[] mEdit = new EditText[4];
        mEdit[0] = (EditText)findViewById(R.id.x1);
        mEdit[1] = (EditText)findViewById(R.id.y1);
        mEdit[2] = (EditText)findViewById(R.id.x2);
        mEdit[3] = (EditText)findViewById(R.id.y2);

        String[] inputStr = new String[4];
        for(int i = 0; i < 4; i++)
        {
            inputStr[i] = mEdit[i].getText().toString();
        }

        double[] inputD = new double[4];
        if (areAllEmpty(inputStr))
        {
            for(int i = 0; i < 4; i++)
            {
                inputD[i] = parseDouble(inputStr[i]);
            }

            double distance = distanceFormula(inputD);
            double rounded = roundToDecimal(distance);
            String unit = sharedPref.getString(KEY_PREF_UNIT, "");
            outputText = valueOf(rounded) + " " + unit;
        }
        else
        {
            outputText = getString(R.string.fill_all_fields);
        }

        TextView textView = (TextView)findViewById(R.id.result);
        textView.setText(outputText);

        //Hide keyboard when done
        if (view != null) {
            InputMethodManager imm = (InputMethodManager)getSystemService(Context.INPUT_METHOD_SERVICE);
            imm.hideSoftInputFromWindow(view.getWindowToken(), 0);
        }
    }

    public double distanceFormula(double[] array)
    {
        double deltaX = array[0] - array[2];
        double deltaY = array[1] - array[3];
        return sqrt(pow(deltaX, 2) + pow(deltaY, 2));
    }

    public boolean areAllEmpty(String[] array)
    {
        for(String str : array) if(str.equals("")) return false;
        return true;
    }

    public double roundToDecimal(double d)
    {
        String decimals = sharedPref.getString(KEY_PREF_DECIMALS, "3");
        double tens = pow(10.0, Integer.valueOf(decimals));
        return Math.round(d * tens) / tens;
    }
}