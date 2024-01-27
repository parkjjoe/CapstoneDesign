package com.example.capstonedesign;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Typeface;
import android.os.Bundle;
import android.text.Spannable;
import android.text.SpannableStringBuilder;
import android.text.Spanned;
import android.text.style.StyleSpan;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

public class InfoActivity extends AppCompatActivity {

    private TextView textView0, textView1, textView2, textView3, textView4, textView5,textView6, textView7, textView8, textView9, textView10;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.info);

        Button buttonA = findViewById(R.id.button0);
        Button buttonB = findViewById(R.id.button1);
        textView0 = findViewById(R.id.text_view0);
        textView1 = findViewById(R.id.text_view1);
        textView2 = findViewById(R.id.text_view2);
        textView3 = findViewById(R.id.text_view3);
        textView4 = findViewById(R.id.text_view4);
        textView5 = findViewById(R.id.text_view5);
        textView6 = findViewById(R.id.text_view6);
        textView7 = findViewById(R.id.text_view7);
        textView8 = findViewById(R.id.text_view8);
        textView9 = findViewById(R.id.text_view9);
        textView10 = findViewById(R.id.text_view10);
        ImageView imageView1 = findViewById(R.id.image_view1);
        ImageView imageView2 = findViewById(R.id.image_view2);
        ImageView imageView3 = findViewById(R.id.image_view3);
        ImageView imageView4 = findViewById(R.id.image_view4);
        ImageView imageView5 = findViewById(R.id.image_view5);
        ImageView imageView6 = findViewById(R.id.image_view6);
        ImageView imageView7 = findViewById(R.id.image_view7);
        ImageView imageView8 = findViewById(R.id.image_view8);
        ImageView imageView9 = findViewById(R.id.image_view9);
        ImageView imageView10 = findViewById(R.id.image_view10);

        // BACK 버튼
        buttonA.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onBackPressed();
            }
        });

        // SOURCE 버튼
        buttonB.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(InfoActivity.this, SourceActivity.class);
                startActivity(intent);
            }
        });

        imageView1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String content = "장점\n위산 역류 예방, 허리/목 통증 예방, 주름 예방\n\n단점\n임신 말기에 사산 위험 증가, 기도폐색, 코골이, 수면무호흡증, 하대정맥 압력\n\n개선\n낮은 베개로 바꿔 경추/근육 스트레스 완화, 무릎 아래에 베개를 넣어 허리 통증 완화";
                SpannableStringBuilder bold = getBold(content); // content에서 '장점', '단점', '개선' 단어를 찾아 볼드체 적용
                showDialog("정자세", bold);
            }
        });

        imageView2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String content = "장점\n위산 역류 예방, 주름 예방\n\n단점\n코골이, 수면무호흡증, 척추 후만증, 흉곽출구증후군, 어깨 통증, 양 팔의 혈액 순환 어려움\n\n개선\n무릎 아래에 베개를 넣어 허리 통증 완화";
                SpannableStringBuilder bold = getBold(content);
                showDialog("만세한 정자세", bold);
            }
        });

        imageView3.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String content = "장점\n위산 역류 예방, 주름 예방\n\n단점\n어깨 통증, 전신 불균형\n\n개선\n통증이 없는 방향으로 돌아누워 베개를 안고 자기, 따뜻한 샤워나 온찜질로 어깨 풀어주기";
                SpannableStringBuilder bold = getBold(content);
                showDialog("왼팔 올린 정자세", bold);
            }
        });

        imageView4.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String content = "장점\n위산 역류 예방, 주름 예방\n\n단점\n어깨 통증, 전신 불균형\n\n개선\n통증이 없는 방향으로 돌아누워 베개를 안고 자기, 따뜻한 샤워나 온찜질로 어깨 풀어주기";
                SpannableStringBuilder bold = getBold(content);
                showDialog("오른팔 올린 정자세", bold);
            }
        });

        imageView5.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String content = "장점\n역류성 식도염 환자 및 임산부 추천 자세, 속쓰림/요통/허리 통증 예방, 혈액 순환 도움\n\n단점\n악몽, 주름, 안면 비대칭, 어깨 통증\n\n개선\n등 펴기, 목과 척추가 일직선이 되도록 베개 높이 조정, 상체를 하체보다 높게 해 역류성 식도염 예방";
                SpannableStringBuilder bold = getBold(content);
                showDialog("왼쪽으로 누운 자세", bold);
            }
        });

        imageView6.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String content = "장점\n성(性)적 꿈, 코골이/수면무호흡증 완화\n\n단점\n임산부 및 통증 환자 비추천 자세, 요통/근육통/두통, 녹내장, 얼굴 변형\n\n개선\n-";
                SpannableStringBuilder bold = getBold(content);
                showDialog("엎드린 자세", bold);
            }
        });

        imageView7.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String content = "장점\n성(性)적 꿈, 통풍 완화\n\n단점\n코골이, 척추 후만증, 흉곽출구증후군, 어깨 통증, 얼굴 변형, 뒤척임, 양 팔의 혈액 순환 어려움\n\n개선\n부드러운 베개 사용, 침대를 바라봐 기도 확보";
                SpannableStringBuilder bold = getBold(content);
                showDialog("만세한 엎드린 자세", bold);
            }
        });

        imageView8.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String content = "장점\n-\n\n단점\n전신 불균형, 얼굴 변형, 어깨 통증\n\n개선\n통증이 없는 방향으로 돌아누워 베개를 안고 자기, 따뜻한 샤워나 온찜질로 어깨 풀어주기";
                SpannableStringBuilder bold = getBold(content);
                showDialog("왼팔 올린 엎드린 자세", bold);
            }
        });

        imageView9.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String content = "장점\n-\n\n단점\n전신 불균형, 얼굴 변형, 어깨 통증\n\n개선\n통증이 없는 방향으로 돌아누워 베개를 안고 자기, 따뜻한 샤워나 온찜질로 어깨 풀어주기";
                SpannableStringBuilder bold = getBold(content);
                showDialog("오른팔 올린 엎드린 자세", bold);
            }
        });

        imageView10.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String content = "장점\n고혈압환자 추천 자세, 알츠하이머병 위험 감소, 뇌 속 노폐물 제거, 신경질환 발달 늦춤\n\n단점\n임산부 비추천 자세, 역류성 식도염, 주름, 안면 비대칭, 어깨 통증\n\n개선\n등 펴기, 목과 척추가 일직선이 되도록 베개 높이 조정, 상체를 하체보다 높게 해 역류성 식도염 예방";
                SpannableStringBuilder bold = getBold(content);
                showDialog("오른쪽으로 누운 자세", bold);
            }
        });

        updateTextView(); // textView0 업데이트
    }

    // '장점', '단점', '개선'에 볼드체 적용
    private SpannableStringBuilder getBold(String content) {
        SpannableStringBuilder spannable = new SpannableStringBuilder(content);
        String[] keywords = {"장점", "단점", "개선"};

        for (String keyword : keywords) {
            int start = content.indexOf(keyword);
            if (start != -1) {
                spannable.setSpan(new StyleSpan(Typeface.BOLD), start, start + keyword.length(), Spannable.SPAN_INCLUSIVE_INCLUSIVE);
            }
        }

        return spannable;
    }

    // classifications.txt에서 분류된 클래스 정보를 읽어와 textView0에 표시
    private void updateTextView() {
        // SharedPreferences에서 CLASSIFY 버튼 클릭 여부 확인
        SharedPreferences sharedPreferences = getSharedPreferences("MyPrefs", Context.MODE_PRIVATE);
        boolean isButtonEClicked = sharedPreferences.getBoolean("isClassifiedClicked", false);

        // CLASSIFY 버튼이 클릭되지 않았으면 아래 텍스트 출력
        if (!isButtonEClicked) {
            textView0.setText("(아직 오늘 수면 영상이 분류되지 않았습니다.)\n아래 이미지를 누르면 각 수면 자세 정보를 확인할 수 있습니다.");
            return;
        }

        // CLASSIFY 버튼이 클릭됐으면 아래 코드 실행
        try {
            File file = new File(getFilesDir(), "classifications.txt");
            FileInputStream fis = new FileInputStream(file);

            // 파일을 문자열로 변환
            BufferedReader reader = new BufferedReader(new InputStreamReader(fis));
            String line;
            Set<String> classificationSet = new HashSet<>();
            while ((line = reader.readLine()) != null) {
                String[] parts = line.split(", ");
                if (parts.length >= 2) {
                    classificationSet.add(parts[1].trim()); // 분류된 클래스 이름 추출
                }
            }
            reader.close();

            // textView0에 표시
            List<String> classifications = new ArrayList<>(classificationSet);
            SpannableStringBuilder message = boldClassNames(classifications);
            textView0.setText(message);

            setTextViewBold(classifications);

        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private String getKoreanLabel(String className) {
        switch (className) {
            case "back":
                return "엎드린 자세";
            case "back_hurray":
                return "만세한 엎드린 자세";
            case "back_left":
                return "왼팔 올린 엎드린 자세";
            case "back_right":
                return "오른팔 올린 엎드린 자세";
            case "front":
                return "정자세";
            case "front_hurray":
                return "만세한 정자세";
            case "front_left_raised":
                return "왼팔 올린 정자세";
            case "front_right_raised":
                return "오른팔 올린 정자세";
            case "left":
                return "왼쪽으로 누운 자세";
            case "right":
                return "오른쪽으로 누운 자세";
            default:
                return className;
        }
    }

    // textView0에 표시되는 클래스 이름 볼드체로 출력
    private SpannableStringBuilder boldClassNames(List<String> classifications) {
        SpannableStringBuilder spannable = new SpannableStringBuilder("오늘 분류된 자세는\n");
        int startIndex, endIndex;

        for (int i = 0; i < classifications.size(); i++) {
            String className = classifications.get(i);
            String koreanName = getKoreanLabel(className); // 클래스명 영한 변환

            startIndex = spannable.length();
            spannable.append(koreanName);
            endIndex = startIndex + koreanName.length();

            spannable.setSpan(new StyleSpan(Typeface.BOLD), startIndex, endIndex, Spanned.SPAN_EXCLUSIVE_EXCLUSIVE);

            if (i < classifications.size() - 1) {
                spannable.append(", ");
            } else {
                spannable.append("입니다!\n아래 이미지를 누르면 각 수면 자세 정보를 확인할 수 있습니다.");
            }
        }
        return spannable;
    }

    // classifications.txt에서 추출된 클래스 이름에 해당하는 textView 볼드체 처리
    private void setTextViewBold(List<String> classifications) {

        for (String classification : classifications) {
            switch (classification) {
                case "front":
                    textView1.setTypeface(null, Typeface.BOLD);
                    break;
                case "front_hurray":
                    textView2.setTypeface(null, Typeface.BOLD);
                    break;
                case "front_left_raised":
                    textView3.setTypeface(null, Typeface.BOLD);
                    break;
                case "front_right_raised":
                    textView4.setTypeface(null, Typeface.BOLD);
                    break;
                case "left":
                    textView5.setTypeface(null, Typeface.BOLD);
                    break;
                case "back":
                    textView6.setTypeface(null, Typeface.BOLD);
                    break;
                case "back_hurray":
                    textView7.setTypeface(null, Typeface.BOLD);
                    break;
                case "back_left":
                    textView8.setTypeface(null, Typeface.BOLD);
                    break;
                case "back_right":
                    textView9.setTypeface(null, Typeface.BOLD);
                    break;
                case "right":
                    textView10.setTypeface(null, Typeface.BOLD);
                    break;
            }
        }
    }

    // Dialog 창 구성
    private void showDialog(String title, CharSequence message) {
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setTitle(title);
        builder.setMessage(message);
        builder.setPositiveButton("확인", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                dialog.dismiss();
            }
        });
        builder.show();
    }
}
