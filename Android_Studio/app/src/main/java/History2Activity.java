package com.example.capstonedesign;

import androidx.appcompat.app.AppCompatActivity;

import android.app.Dialog;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.media.MediaMetadataRetriever;
import android.net.Uri;
import android.os.Bundle;
import android.util.DisplayMetrics;
import android.view.Gravity;
import android.view.View;
import android.widget.Button;
import android.graphics.Bitmap;

import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;

import android.widget.GridLayout;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

public class History2Activity extends AppCompatActivity {

    private Uri fileUri;
    private GridLayout gridLayout;
    private int screenHeight;
    private int screenWidth;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.history2);

        fileUri = Uri.parse(getIntent().getStringExtra("selectedFileUri")); // 선택된 txt 파일 전달 받기

        Button buttonA = findViewById(R.id.button0);

        // BACK 버튼
        buttonA.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onBackPressed();
            }
        });

        // 디바이스 화면 크기 얻기
        DisplayMetrics displayMetrics = new DisplayMetrics();
        getWindowManager().getDefaultDisplay().getMetrics(displayMetrics);
        screenHeight = displayMetrics.heightPixels;
        screenWidth = displayMetrics.widthPixels;

        gridLayout = findViewById(R.id.grid_layout);
        gridLayout.setColumnCount(3); // 3열

        loadData(); // txt 파일에서 정보를 읽어 이미지와 클래스를 화면에 출력
    }

    // txt 파일에서 정보 읽기
    private void loadData() {
        int lineNumber = 0;

        try {
            // 파일 읽기 준비
            InputStream inputStream = getContentResolver().openInputStream(fileUri);
            BufferedReader reader = new BufferedReader(new InputStreamReader(inputStream));

            String line;
            while ((line = reader.readLine()) != null) { // txt 파일의 각 줄 읽기
                lineNumber++;

                String[] parts = line.split(","); // ','를 기준으로 정보 나누기
                if (parts.length != 3) {
                    throw new IOException("Invalid format at line " + lineNumber);
                }

                Uri videoUri = Uri.parse(parts[0].trim());
                String classLabel = parts[1].trim();

                String frameInfo = parts[2].split(":")[1].trim();  // "frame: 1000"에서 "1000"을 추출
                long frameTime;
                try {
                    frameTime = Long.parseLong(frameInfo);
                } catch (NumberFormatException e) {
                    throw new IOException("Invalid frame format at line " + lineNumber);
                }

                Bitmap frameBitmap = extractFrame(videoUri, frameTime); // 해당 프레임 시간의 이미지 추출
                if (frameBitmap != null) {
                    updateGridLayout(frameBitmap, classLabel); // 이미지와 클래스를 GridLayout에 출력
                }
            }
        } catch (FileNotFoundException e) {
            e.printStackTrace();
            Toast.makeText(this, "File not found: " + e.getMessage(), Toast.LENGTH_LONG).show();
        } catch (IOException e) {
            e.printStackTrace();
            Toast.makeText(this, "Error reading file at line " + lineNumber + ": " + e.getMessage(), Toast.LENGTH_LONG).show();
        }
    }

    // 이미지 추출
    private Bitmap extractFrame(Uri videoUri, long frameTime) {
        MediaMetadataRetriever retriever = new MediaMetadataRetriever();
        try {
            retriever.setDataSource(this, videoUri);
            return retriever.getFrameAtTime(frameTime * 1000, MediaMetadataRetriever.OPTION_CLOSEST_SYNC); // 특정 프레임에 해당하는 이미지 추출
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        } finally {
            retriever.release();
        }
    }

    // GridLayout 동적 업데이트
    private void updateGridLayout(Bitmap bitmap, String classLabel) {
        // LinearLayout 생성
        LinearLayout linearLayout = new LinearLayout(this);
        linearLayout.setOrientation(LinearLayout.VERTICAL);
        linearLayout.setLayoutParams(new LinearLayout.LayoutParams(LinearLayout.LayoutParams.WRAP_CONTENT, LinearLayout.LayoutParams.WRAP_CONTENT)); // LinearLayout 크기 설정

        // ImageView, TextView 생성
        ImageView imageView = new ImageView(this);
        TextView textView = new TextView(this);

        // ImageView 설정
        imageView.setImageBitmap(bitmap); // ImageView에 Bitmap 설정
        imageView.setAdjustViewBounds(true);
        int imageDimension = Math.min(screenWidth, screenHeight) / 3; // ImageView 크기를 디바이스 화면 너비의 1/3로 설정
        LinearLayout.LayoutParams imageLayoutParams = new LinearLayout.LayoutParams(imageDimension, LinearLayout.LayoutParams.WRAP_CONTENT);
        imageView.setLayoutParams(imageLayoutParams);
        imageView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                showImageDialog(bitmap);
            }
        });

        // TextView 설정
        LinearLayout.LayoutParams textLayoutParams = new LinearLayout.LayoutParams(LinearLayout.LayoutParams.WRAP_CONTENT, LinearLayout.LayoutParams.WRAP_CONTENT); // TextView 크기 설정
        textView.setLayoutParams(textLayoutParams);
        textView.setText(getKoreanLabel(classLabel)); // 클래스명 영한 변환
        textView.setGravity(Gravity.CENTER);
        textView.setTextColor(Color.BLACK);

        // ImageView와 TextView를 LinearLayout에 추가
        linearLayout.setGravity(Gravity.CENTER);
        linearLayout.addView(imageView);
        linearLayout.addView(textView);

        // LinearLayout에 포함된 내용의 크기에 따라 GridLayout의 너비와 높이 결정
        GridLayout.LayoutParams layoutParams = new GridLayout.LayoutParams();
        layoutParams.width = GridLayout.LayoutParams.WRAP_CONTENT;
        layoutParams.height = GridLayout.LayoutParams.WRAP_CONTENT;
        linearLayout.setLayoutParams(layoutParams); // LinearLayout에 의한 GridLayout 크기 설정

        // LinearLayout을 GridLayout에 추가
        gridLayout.addView(linearLayout);
    }

    // ImageView 클릭 시 Dialog 생성 및 이미지 확대
    private void showImageDialog(Bitmap bitmap) {
        Dialog dialog = new Dialog(this);
        dialog.setContentView(R.layout.dialog_image);

        ImageView dialogImage0 = dialog.findViewById(R.id.dialog_image);
        dialogImage0.setImageBitmap(bitmap);

        dialog.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT)); // 대화상자 배경 투명 설정
        dialog.show();
    }

    private String getKoreanLabel(String classLabel) {
        switch (classLabel) {
            case "back":
                return "엎드린 자세";
            case "back_hurray":
                return "만세한\n엎드린 자세";
            case "back_left":
                return "왼팔 올린\n엎드린 자세";
            case "back_right":
                return "오른팔 올린\n엎드린 자세";
            case "front":
                return "정자세";
            case "front_hurray":
                return "만세한\n정자세";
            case "front_left_raised":
                return "왼팔 올린\n정자세";
            case "front_right_raised":
                return "오른팔 올린\n정자세";
            case "left":
                return "왼쪽으로\n누운 자세";
            case "right":
                return "오른쪽으로\n누운 자세";
            default:
                return classLabel;
        }
    }
}
