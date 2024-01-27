package com.example.capstonedesign;

import android.content.Context;
import android.util.Log;

import java.io.IOException;
import java.io.OutputStreamWriter;

public class FileHelper2 {
    public static void writeToFile(Context context, String data, String fileName) {
        try {
            OutputStreamWriter outputStreamWriter = new OutputStreamWriter(context.openFileOutput(fileName, Context.MODE_APPEND));
            outputStreamWriter.write(data);
            outputStreamWriter.close();
        } catch (IOException e) {
            Log.e("Exception", "File write failed: " + e.toString());
        }
    }
}
