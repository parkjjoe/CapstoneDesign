package com.example.capstonedesign;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.content.FileProvider;

import android.content.DialogInterface;
import android.graphics.Color;
import android.net.Uri;
import android.os.Bundle;
import android.text.SpannableStringBuilder;
import android.text.Spanned;
import android.text.style.ForegroundColorSpan;
import android.text.style.RelativeSizeSpan;
import android.view.View;
import android.widget.Button;
import android.content.Intent;
import android.media.MediaMetadataRetriever;
import android.graphics.Bitmap;
import android.widget.ImageView;
import android.widget.TextView;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.Comparator;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Random;

import com.github.mikephil.charting.charts.HorizontalBarChart;
import com.github.mikephil.charting.charts.LineChart;
import com.github.mikephil.charting.components.AxisBase;
import com.github.mikephil.charting.components.Description;
import com.github.mikephil.charting.components.XAxis;
import com.github.mikephil.charting.components.YAxis;
import com.github.mikephil.charting.data.BarData;
import com.github.mikephil.charting.data.BarDataSet;
import com.github.mikephil.charting.data.BarEntry;
import com.github.mikephil.charting.data.Entry;
import com.github.mikephil.charting.data.LineData;
import com.github.mikephil.charting.data.LineDataSet;
import com.github.mikephil.charting.formatter.ValueFormatter;

public class MainActivity extends AppCompatActivity {

    // 프레임 정보 저장
    private static class FrameData {
        String videoPath;
        long time;

        FrameData(String videoPath, long time) {
            this.videoPath = videoPath;
            this.time = time;
        }
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.capstonedesign);

        Button buttonA = findViewById(R.id.button0);
        Button buttonB = findViewById(R.id.button1);
        ImageView imageView1 = findViewById(R.id.image_view);
        TextView textView1 = findViewById(R.id.text_view);

        // BACK 버튼
        buttonA.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onBackPressed();
            }
        });

        // HISTORY 버튼
        buttonB.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                showtxtFilePicker(); // HISTORY 화면으로 전달될 txt 파일 선택기 함수
            }
        });

        // classifications.txt에서 정보 읽기
        Map<String, Integer> classifications = new HashMap<>();
        Map<String, List<FrameData>> frameData = new HashMap<>();
        String maxClass = "";
        String videoPath = "";
        long frameTime = 0;

        try (BufferedReader br = new BufferedReader(new InputStreamReader(openFileInput("classifications.txt")))) {
            String line;
            while ((line = br.readLine()) != null) { // txt 파일의 각 줄 읽기
                String[] parts = line.split(", "); // ','를 기준으로 정보 나누기
                if (parts.length < 3)
                    continue;

                String video = parts[0].trim();
                String classLabel = parts[1].trim();
                String frameInfo = parts[2].trim();
                long frame = Long.parseLong(frameInfo.split(": ")[1]);

                int count = classifications.getOrDefault(classLabel, 0) + 1; // 분류된 클래스 카운트
                classifications.put(classLabel, count);
                // frameData에서 해당 클래스의 프레임 정보 가져오기
                if (!frameData.containsKey(classLabel)) {
                    frameData.put(classLabel, new ArrayList<>());
                }
                frameData.get(classLabel).add(new FrameData(video, frame));

                // 가장 많이 분류된 클래스 업데이트
                if (classifications.getOrDefault(maxClass, 0) < count) {
                    maxClass = classLabel;
                    videoPath = video;
                    frameTime = frame;
                }
            }
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }

        // 가장 많이 분류된 클래스에 해당하는 이미지 추출
        Random random = new Random();
        List<FrameData> maxClassFrames = frameData.get(maxClass);
        FrameData selectedFrame = maxClassFrames.get(random.nextInt(maxClassFrames.size()));

        // 영상에서 이미지 추출
        MediaMetadataRetriever retriever = new MediaMetadataRetriever();
        try {
            Uri videoUri = Uri.parse(selectedFrame.videoPath); // FrameData에서 가져온 영상 경로를 Uri 객체로 변환
            retriever.setDataSource(this, videoUri); // 읽은 경로를 사용하여 데이터 소스 설정
        } catch (Exception e) {
            e.printStackTrace();
        }
        Bitmap frameImage = retriever.getFrameAtTime(selectedFrame.time * 1000, MediaMetadataRetriever.OPTION_CLOSEST);

        Map<String, String> classDescriptions = new HashMap<String, String>() {{
            put("back", "엎드린 자세");
            put("back_hurray", "만세한 엎드린 자세");
            put("back_left", "왼팔 올린 엎드린 자세");
            put("back_right", "오른팔 올린 엎드린 자세");
            put("front", "정자세");
            put("front_hurray", "만세한 정자세");
            put("front_left_raised", "왼팔 올린 정자세");
            put("front_right_raised", "오른팔 올린 정자세");
            put("left", "왼쪽으로 누운 자세");
            put("right", "오른쪽으로 누운 자세");
        }};

        Map<String, String> classAdditionalTexts = new HashMap<String, String>() {{
            put("back", "입니다!\n위 수면 자세에 대한 정보는 INFO 화면을 참고해주세요!");
            put("back_hurray", "입니다!\n위 수면 자세에 대한 정보는 INFO 화면을 참고해주세요!");
            put("back_left", "입니다!\n위 수면 자세에 대한 정보는 INFO 화면을 참고해주세요!");
            put("back_right", "입니다!\n위 수면 자세에 대한 정보는 INFO 화면을 참고해주세요!");
            put("front", "입니다!\n위 수면 자세에 대한 정보는 INFO 화면을 참고해주세요!");
            put("front_hurray", "입니다!\n위 수면 자세에 대한 정보는 INFO 화면을 참고해주세요!");
            put("front_left_raised", "입니다!\n위 수면 자세에 대한 정보는 INFO 화면을 참고해주세요!");
            put("front_right_raised", "입니다!\n위 수면 자세에 대한 정보는 INFO 화면을 참고해주세요!");
            put("left", "입니다!\n위 수면 자세에 대한 정보는 INFO 화면을 참고해주세요!");
            put("right", "입니다!\n위 수면 자세에 대한 정보는 INFO 화면을 참고해주세요!");
        }};

        // 대표 이미지 및 클래스 텍스트 출력
        imageView1.setImageBitmap(frameImage);
        String maxClassDescription = classDescriptions.getOrDefault(maxClass, "No description available"); // maxClass에 대한 영한 변환
        String maxClassAdditionalText = classAdditionalTexts.getOrDefault(maxClass, ""); // maxClass에 대한 추가 설명
        String baseText = "가장 오래 측정된 수면 자세는 \n";
        SpannableStringBuilder spannableString = new SpannableStringBuilder(baseText + maxClassDescription + maxClassAdditionalText); // 3개의 텍스트 변수 결합
        // maxClassDescription 텍스트 설정
        int start = baseText.length();
        int end = start + maxClassDescription.length(); // maxClassDescription 길이 계산
        spannableString.setSpan(new RelativeSizeSpan(2.0f), start, end, Spanned.SPAN_EXCLUSIVE_EXCLUSIVE); // 글자 크기 2배
        spannableString.setSpan(new ForegroundColorSpan(Color.RED), start, end, Spanned.SPAN_EXCLUSIVE_EXCLUSIVE); // 빨간색
        textView1.setText(spannableString);
        retriever.release();

        // LineChart: 시간(프레임)의 흐름 별 클래스 분포 그래프
        // 각 클래스에 대한 index 매핑 맵
        Map<String, Integer> classIndices = new HashMap<String, Integer>() {{
            put("back", 0);
            put("back_hurray", 1);
            put("back_left", 2);
            put("back_right", 3);
            put("front", 4);
            put("front_hurray", 5);
            put("front_left_raised", 6);
            put("front_right_raised", 7);
            put("left", 8);
            put("right", 9);
        }};

        LineChart lineChart = findViewById(R.id.line_chart);
        List<Entry> lineEntries = new ArrayList<>();

        // 클래스와 index 정보를 가져오기
        for (Map.Entry<String, List<FrameData>> entry : frameData.entrySet()) {
            String classLabel = entry.getKey();
            int classIndex = classIndices.get(classLabel);
            for (FrameData frame : entry.getValue()) {
                lineEntries.add(new Entry(frame.time, classIndex)); // x축: 프레임, y축: 각 클래스의 index
            }
        }

        // x축(프레임) 기준으로 오름차순 정렬
        Collections.sort(lineEntries, new Comparator<Entry>() {
            @Override
            public int compare(Entry e1, Entry e2) {
                return Float.compare(e1.getX(), e2.getX());
            }
        });

        // LineChart 시각적 표현 설정
        LineDataSet set1_line = new LineDataSet(lineEntries, "");
        set1_line.setLineWidth(2f); // 선 두께
        set1_line.setDrawValues(false); // 데이터 포인트 위 값 표시 여부
        set1_line.setDrawCircles(false); // 데이터 포인트에 원 표시 여부
        set1_line.setColors(Color.BLUE); // 선 색상

        // x축 설정
        XAxis xAxis_line = lineChart.getXAxis();
        xAxis_line.setDrawLabels(true); // 레이블 표시 여부
        xAxis_line.setPosition(XAxis.XAxisPosition.BOTTOM); // 레이블 위치
        xAxis_line.setLabelCount(5, true); // 레이블 개수
        xAxis_line.setValueFormatter(new ValueFormatter() {
            @Override
            public String getAxisLabel(float value, AxisBase axis) {
                float timeInHours = value / 3600000f; // 프레임을 시간 단위로 나오도록 계산
                return String.format("%.2f", timeInHours);
            }
        });

        // y축 설정
        YAxis rightAxis_line = lineChart.getAxisRight();
        rightAxis_line.setEnabled(false); // 우측 y축 비활성화
        // 좌측 y축 설정
        YAxis leftAxis_line = lineChart.getAxisLeft();
        leftAxis_line.setAxisMinimum(0f); // 최솟값
        leftAxis_line.setAxisMaximum(classIndices.size() - 1); // 최댓값
        leftAxis_line.setLabelCount(classIndices.size(), true); // 레이블 개수
        leftAxis_line.setValueFormatter(new ValueFormatter() {
            private final String[] classes = new String[]{"back", "back_hurray", "back_left", "back_right", "front", "front_hurray", "front_left_raised", "front_right_raised", "left", "right"};

            @Override
            public String getAxisLabel(float value, AxisBase axis) {
                int index = (int) value;
                if (index >= 0 && index < classes.length) {
                    return classDescriptions.get(classes[index]); // 레이블 표시
                }
                return "";
            }
        });

        // LineChart 추가 설정
        LineData lineData = new LineData(set1_line);
        lineChart.setData(lineData);
        lineChart.setTouchEnabled(false); // 터치 활성화 여부
        Description desc_line = new Description();
        desc_line.setText("시간"); // 설명
        desc_line.setTextSize(10f); // 설명 글자 크기
        lineChart.setDescription(desc_line);
        lineChart.getDescription().setEnabled(true); // 설명 표시 여부
        lineChart.getLegend().setEnabled(false); // 범례 표시 여부
        lineChart.invalidate(); // 차트 갱신

        // HorizontalBarChart: 백분율 지표 수평 막대 차트
        HorizontalBarChart barChart = findViewById(R.id.bar_chart);

        ArrayList<BarEntry> barEntries = new ArrayList<>();
        String[] classLabels = new String[classifications.size()];
        int totalClassificationCount = 0;

        // 전체 분류된 클래스 개수 계산
        for (Map.Entry<String, Integer> classification : classifications.entrySet()) {
            totalClassificationCount += classification.getValue();
        }

        // 각 클래스의 백분율 계산
        int index = 0;
        for (Map.Entry<String, Integer> classification : classifications.entrySet()) {
            float percentage = (float) classification.getValue() / totalClassificationCount * 100;
            barEntries.add(new BarEntry(index++, percentage));
            classLabels[index - 1] = classification.getKey();
        }

        // BarChart의 시각적 표현 설정
        BarDataSet set1_bar = new BarDataSet(barEntries, "");
        set1_bar.setColor(Color.CYAN); // 막대 색상
        set1_bar.setValueTextSize(10f); // 값의 글자 크기
        set1_bar.setDrawValues(true); // 값 표시 여부
        set1_bar.setValueFormatter(new ValueFormatter() {
            @Override
            public String getFormattedValue(float value) {
                return String.format("%.2f%%", value); // 백분율 형식으로 출력
            }
        });

        // x축 설정
        XAxis xAxis_bar = barChart.getXAxis();
        xAxis_bar.setPosition(XAxis.XAxisPosition.BOTTOM); // 레이블 위치
        xAxis_bar.setGranularity(1f); // 레이블 간격
        xAxis_bar.setLabelCount(classifications.size()); // 레이블 개수
        xAxis_bar.setDrawGridLines(false); // 그리드 선 표시 여부
        xAxis_bar.setValueFormatter(new ValueFormatter() {
            @Override
            public String getFormattedValue(float value) {
                return classDescriptions.get(classLabels[(int) value]); // 레이블 영한 변환해 출력
            }
        });

        // y축 설정
        YAxis rightAxis_bar = barChart.getAxisRight();
        rightAxis_bar.setEnabled(false); // 우측 y축 비활성화
        // 좌측 y축 설정
        YAxis leftAxis_bar = barChart.getAxisLeft();
        leftAxis_bar.setAxisMinimum(0f); // 최솟값
        leftAxis_bar.setAxisMaximum(100f); // 최댓값
        leftAxis_bar.setLabelCount(6, true); // 백분율 값 개수 및 표시 여부
        leftAxis_bar.setDrawLabels(false);
        leftAxis_bar.setValueFormatter(new ValueFormatter() {
            @Override
            public String getAxisLabel(float value, AxisBase axis) {
                return value == axis.mAxisMaximum ? "percent" : "";
            }
        });

        // BarChart 추가 설정
        BarData data = new BarData(set1_bar);
        barChart.setData(data);
        data.setBarWidth(0.4f); // 막대 폭 크기
        barChart.setFitBars(true); // 막대 폭이 차트에 맞게 조정되도록 설정 여부
        barChart.setTouchEnabled(false); // 터치 활성화 여부
        Description desc_bar = new Description();
        desc_bar.setText("백분율"); // 설명
        desc_bar.setTextSize(10f); // 설명 글자 크기
        barChart.setDescription(desc_bar);
        barChart.getDescription().setEnabled(true); // 설명 표시 여부
        barChart.getLegend().setEnabled(false); // 범례 표시 여부
        barChart.invalidate(); // 차트 갱신
    }

    // HISTORY 화면으로 전달될 txt 파일 선택기
    private void showtxtFilePicker() {
        File filesDir = new File(getFilesDir(), ""); // 내부 저장소의 files 디렉토리
        File[] txtFiles = filesDir.listFiles((dir, name) -> name.startsWith("Classified_on_") && name.endsWith(".txt"));

        // 파일 리스트를 보여주는 AlterDialog
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setTitle("Select a TXT file");

        String[] fileNames = Arrays.stream(txtFiles) // 파일 이름을 String 배열로 변환
                .map(File::getName)
                .toArray(String[]::new);

        builder.setItems(fileNames, new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                // 선택된 파일의 URI를 HISTORY 화면으로 전달
                File selectedFile = txtFiles[which];
                Uri fileUri = FileProvider.getUriForFile(
                        MainActivity.this,
                        "com.example.capstonedesign.fileprovider",
                        selectedFile);

                Intent intent = new Intent(MainActivity.this, HistoryActivity.class);
                intent.putExtra("selectedFileUri", fileUri.toString());
                startActivity(intent);
            }
        });

        builder.show();
    }
}
