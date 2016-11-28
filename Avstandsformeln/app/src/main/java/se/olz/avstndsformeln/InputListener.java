package se.olz.avstndsformeln;


import android.text.Editable;
import android.text.TextWatcher;
import android.widget.EditText;

class InputListener implements TextWatcher
{
    private EditText mEdit;
    private int i;
    private CalcThread calcThread;

    InputListener(EditText mEdit, int i, CalcThread calcThread)
    {
        this.mEdit = mEdit;
        this.i = i;
        this.calcThread = calcThread;
    }

    public void afterTextChanged(Editable s)
    {
        String inputText = mEdit.getText().toString();
        if (InputValues.getInputs(i).equals("") && !inputText.equals(""))
        {
            InputValues.incrFilledFields();
        }
        else if (!InputValues.getInputs(i).equals("") && inputText.equals(""))
        {
            InputValues.decrFilledFields();
        }
        InputValues.setInputs(inputText, i);
        new Thread(calcThread).start();
    }
    public void beforeTextChanged(CharSequence s, int start, int count, int after) {}
    public void onTextChanged(CharSequence s, int start, int before, int count) {}


}