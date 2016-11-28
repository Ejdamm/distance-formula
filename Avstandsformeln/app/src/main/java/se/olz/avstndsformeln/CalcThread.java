package se.olz.avstndsformeln;

import android.app.Activity;
import android.content.SharedPreferences;
import android.widget.TextView;


import static java.lang.Double.parseDouble;
import static java.lang.Math.pow;
import static java.lang.Math.sqrt;
import static java.lang.String.valueOf;
import static se.olz.avstndsformeln.SettingsFragment.KEY_PREF_DECIMALS;
import static se.olz.avstndsformeln.SettingsFragment.KEY_PREF_UNIT;

class CalcThread implements Runnable
{
    private SharedPreferences sharedPref;
    private TextView resultView;
    private Activity main;
    CalcThread(SharedPreferences sharedPref, TextView resultView, Activity main)
    {
        this.sharedPref = sharedPref;
        this.resultView = resultView;
        this.main = main;
    }

    public void run()
    {
        final String outputText;

        if (InputValues.getFilledFields() == 4)
        {
            String[] inputStr = new String[4];
            for(int i = 0; i < 4; i++)
            {
                inputStr[i] = InputValues.getInputs(i);
            }

            double[] inputD = new double[4];
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
            outputText = "";
        }

        main.runOnUiThread(new Runnable() {
            @Override
            public void run() {
                resultView.setText(outputText);
            }
        });
    }

    private double distanceFormula(double[] array)
    {
        double deltaX = array[0] - array[2];
        double deltaY = array[1] - array[3];
        return sqrt(pow(deltaX, 2) + pow(deltaY, 2));
    }

    private double roundToDecimal(double d)
    {
        String decimals = sharedPref.getString(KEY_PREF_DECIMALS, "3");
        double tens = pow(10.0, Integer.valueOf(decimals));
        return Math.round(d * tens) / tens;
    }
}
