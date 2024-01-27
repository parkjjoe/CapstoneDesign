package com.example.capstonedesign;

import android.content.Context;
import android.util.Log;

import java.io.FileOutputStream;
import java.io.IOException;

public class FileHelper {
    private static final String FILE_NAME = "classifications.txt";

    // 주어진 정보를 파일에 추가
    public static void writeToFile(Context context, String data) {
        FileOutputStream fos = null; // 파일 출력 변수
        try {
            fos = context.openFileOutput(FILE_NAME, Context.MODE_APPEND); // context로 classifications.txt 파일 출력 스트림 열기
            fos.write(data.getBytes()); // 정보를 byte 배열로 변환하여 파일에 작성
        } catch (IOException e) {
            Log.e("FileHelper", "Error writing to file", e);
        } finally {
            if (fos != null) {
                try {
                    fos.close();
                } catch (IOException e) {
                    Log.e("FileHelper", "Error closing file", e);
                }
            }
        }
    }
}
