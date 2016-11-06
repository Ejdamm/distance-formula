package se.olz.avstndsformeln;

import android.content.Context;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.EditText;
import android.widget.TextView;
import static java.lang.Double.parseDouble;
import static java.lang.Math.pow;
import static java.lang.Math.sqrt;
import static java.lang.String.valueOf;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
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
            double rounded = Math.round(distance * 1000.0 ) / 1000.0;
            outputText = valueOf(rounded) + getString(R.string.unit);
        }
        else
            outputText = getString(R.string.fill_all_fields);

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
}