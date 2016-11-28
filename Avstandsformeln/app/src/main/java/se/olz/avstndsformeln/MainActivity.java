package se.olz.avstndsformeln;

import android.content.Intent;
import android.content.SharedPreferences;
import android.preference.PreferenceManager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;


public class MainActivity extends AppCompatActivity
{
    SharedPreferences sharedPref;
    final EditText[] mEdit = new EditText[4];

    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        //Preferences
        PreferenceManager.setDefaultValues(this, R.xml.preferences, false);
        sharedPref = PreferenceManager.getDefaultSharedPreferences(this);

        //Toolbar
        Toolbar myToolbar = (Toolbar) findViewById(R.id.main_toolbar);
        myToolbar.showOverflowMenu();
        setSupportActionBar(myToolbar);

        //Result textView
        TextView resultView = (TextView)findViewById(R.id.result);

        new InputValues();
        CalcThread calcThread = new CalcThread(sharedPref, resultView, this);

        //EditTexts
        mEdit[0] = (EditText)findViewById(R.id.x1);
        mEdit[1] = (EditText)findViewById(R.id.y1);
        mEdit[2] = (EditText)findViewById(R.id.x2);
        mEdit[3] = (EditText)findViewById(R.id.y2);

        for(int i = 0; i < 4; i++)
        {
            mEdit[i].addTextChangedListener(new InputListener(mEdit[i], i, calcThread));
        }
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

    public void clearFields(View view)
    {
        for(int i = 0; i < 4; i++)
        {
            mEdit[i].setText("");
        }
    }
}