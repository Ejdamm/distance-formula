package se.olz.avstndsformeln;

class InputValues
{
    private static String inputs[];
    private static int filledFields;

    InputValues()
    {
        inputs = new String[4];
        for(int i = 0 ; i < 4; i++)
        {
            inputs[i] = "";
        }
        filledFields = 0;
    }

    static String getInputs(int i) {
        return inputs[i];
    }

    static void setInputs(String input, int i) {
        InputValues.inputs[i] = input;
    }

    static int getFilledFields() {
        return filledFields;
    }

    static void incrFilledFields() {
        InputValues.filledFields++;
    }

    static void decrFilledFields() {
        InputValues.filledFields--;
    }
}