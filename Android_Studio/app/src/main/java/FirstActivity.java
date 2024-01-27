package com.example.capstonedesign;

import androidx.activity.result.ActivityResultLauncher;
import androidx.activity.result.contract.ActivityResultContracts;
import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;

import android.Manifest;
import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.pm.PackageManager;
import android.content.res.AssetFileDescriptor;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.graphics.Matrix;
import android.media.MediaMetadataRetriever;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import org.tensorflow.lite.Interpreter;

import java.io.File;
import java.io.FileInputStream;
import java.io.FilenameFilter;
import java.io.IOException;
import java.nio.MappedByteBuffer;
import java.nio.channels.FileChannel;
import java.text.SimpleDateFormat;
import java.util.Arrays;
import java.util.Comparator;
import java.util.Date;
import java.util.Locale;

public class FirstActivity extends AppCompatActivity {

    public static View loadingLayout;
    public static boolean isActive = false;
    Interpreter tfliteInterpreter; // tflite 모델 Interpreter
    private static final int NUM_CLASSES = 10;
    private static final int VIDEO_PICK_REQUEST = 1000; // 권한 요청 코드
    private static final int REQUEST_PERMISSION = 2000; // 권한 요청 코드
    private Uri selectedVideoUri = null;
    private TextView textView1;
    private Button buttonB;
    private Button buttonC;
    private Button buttonE;
    private static final String PREFS_NAME = "ButtonPrefs"; // 버튼 활성화 상태를 저장하기 위한 SharedPreferences 변수명
    private static final String PREFS_NAME2 = "MyPrefs"; // CLASSIFY 버튼 클릭 여부를 저장하기 위한 SharedPreferences 변수명
    private static final String IS_CLASSIFIED_CLICKED = "isClassifiedClicked";
    private static final String BUTTON_B_ENABLED = "ButtonBEnabled";
    private static final String BUTTON_C_ENABLED = "ButtonCEnabled";
    private SharedPreferences sharedPreferences; // 버튼 활성화 상태를 저장, 불러오기 위한 참조 변수
    private SharedPreferences sharedPreferences2; // CLASSIFY 버튼 클릭 여부를 저장, 불러오기 위한 참조 변수
    private SharedPreferences.Editor editor; // sharedPreferences 관련 에디터 변수
    private SharedPreferences.Editor editor2; // sharedPreferences2 관련 에디터 변수

    // 영상 업로드 처리
    private final ActivityResultLauncher<Intent> videoPickerResultLauncher = registerForActivityResult(
            new ActivityResultContracts.StartActivityForResult(),
            result -> {
                if (result.getResultCode() == Activity.RESULT_OK) {
                    Intent data = result.getData();
                    if (data != null) {
                        selectedVideoUri = data.getData();
                        Toast.makeText(FirstActivity.this, "영상이 업로드되었습니다.", Toast.LENGTH_SHORT).show();

                        // 영상이 선택되면 CLASSIFY 버튼 활성화
                        buttonE.setEnabled(true);

                        // 영상 경로를 textView1에 표시
                        String videoName = getFileNameFromUri(selectedVideoUri);
                        if (videoName != null) {
                            textView1.setText(videoName);
                        }
                    }
                }
            }
    );

    // 메인 화면이 활성 상태일 때 로딩 창 비활성화
    @Override
    protected void onResume() {
        super.onResume();
        isActive = true;
        loadingLayout.setVisibility(View.GONE);
    }

    // 메인 화면이 비활성 상태일 때 로딩 창 초기화
    @Override
    protected void onPause() {
        super.onPause();
        isActive = false;
    }

    // tflite 모델을 MappedByteBuffer 형태로 불러오기
    private MappedByteBuffer loadModelFile() throws IOException {
        AssetFileDescriptor fileDescriptor = this.getAssets().openFd("ResNet50_DEFG_ImageDataGenerator.tflite"); // 모델명
        FileInputStream inputStream = new FileInputStream(fileDescriptor.getFileDescriptor());
        FileChannel fileChannel = inputStream.getChannel();
        long startOffset = fileDescriptor.getStartOffset();
        long declaredLength = fileDescriptor.getDeclaredLength();
        return fileChannel.map(FileChannel.MapMode.READ_ONLY, startOffset, declaredLength);
    }

    // tfliteInterpreter 초기화
    private void initModel() {
        try {
            tfliteInterpreter = new Interpreter(loadModelFile());
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.first);

        // 권한 상태 확인 및 필요 권한 요청
        if (ContextCompat.checkSelfPermission(this, Manifest.permission.READ_EXTERNAL_STORAGE) != PackageManager.PERMISSION_GRANTED ||
                ContextCompat.checkSelfPermission(this, Manifest.permission.WRITE_EXTERNAL_STORAGE) != PackageManager.PERMISSION_GRANTED) {
            ActivityCompat.requestPermissions(this, new String[] {
                    Manifest.permission.READ_EXTERNAL_STORAGE,
                    Manifest.permission.WRITE_EXTERNAL_STORAGE
            }, REQUEST_PERMISSION);
        }

        loadingLayout = findViewById(R.id.loading);
        loadingLayout.setVisibility(View.GONE);

        Button buttonA = findViewById(R.id.button1);
        buttonB = findViewById(R.id.button2);
        buttonC = findViewById(R.id.button3);
        Button buttonD = findViewById(R.id.button4);
        buttonE = findViewById(R.id.button5);
        textView1 = findViewById(R.id.text_view1);
        TextView textView2 = findViewById(R.id.text_view2);

        // 앱 실행 시 기존 SharedPreferences에 저장된 정보(버튼 활성화 상태 및 CLASSIFY 버튼 클릭 여부) 초기화
        sharedPreferences = getSharedPreferences(PREFS_NAME, MODE_PRIVATE);
        sharedPreferences2 = getSharedPreferences(PREFS_NAME2, MODE_PRIVATE);
        editor = sharedPreferences.edit();
        editor2 = sharedPreferences2.edit();
        editor.putBoolean(BUTTON_B_ENABLED, false); // RECORDS 버튼 활성화 초기화
        editor.putBoolean(BUTTON_C_ENABLED, false); // STAT 버튼 활성화 초기화
        editor2.putBoolean(IS_CLASSIFIED_CLICKED, false); // CLASSIFY 버튼 클릭 여부 초기화
        editor.apply();
        editor2.apply();
        buttonB.setEnabled(sharedPreferences.getBoolean(BUTTON_B_ENABLED, false));
        buttonC.setEnabled(sharedPreferences.getBoolean(BUTTON_C_ENABLED, false));

        // CLASSIFY 버튼 비활성화
        buttonE.setEnabled(false);

        // INFO 버튼
        buttonA.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(FirstActivity.this, InfoActivity.class);
                startActivity(intent);
            }
        });

        // RECORDS 버튼
        buttonB.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (FirstActivity.isActive) {  // 메인 화면에서 기록 화면으로 이동 시 로딩 창 표시
                    loadingLayout.setVisibility(View.VISIBLE);
                }

                Intent intent = new Intent(FirstActivity.this, RecordActivity.class);
                startActivity(intent);
                editor.putBoolean(BUTTON_B_ENABLED, false);
                editor.apply();
            }
        });

        // STAT 버튼
        buttonC.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(FirstActivity.this, MainActivity.class);
                startActivity(intent);
                editor.putBoolean(BUTTON_C_ENABLED, false);
                editor.apply();
            }
        });

        // SELECT 버튼
        buttonD.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(Intent.ACTION_PICK, MediaStore.Video.Media.EXTERNAL_CONTENT_URI);
                intent.setType("video/*");
                videoPickerResultLauncher.launch(intent);
            }
        });

        // CLASSIFY 버튼
        buttonE.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // CLASSIFY 버튼 클릭 여부를 SharedPreferences에 저장
                SharedPreferences sharedPreferences = getSharedPreferences("MyPrefs", Context.MODE_PRIVATE);
                SharedPreferences.Editor editor = sharedPreferences.edit();
                editor.putBoolean("isClassifiedClicked", true);
                editor.apply();

                performClassification(); // 분류 작업 실행
            }
        });

        textView2.setText("[앱 사용 방법]\n\n1. SELECT 버튼을 눌러 수면 영상을 업로드하세요.\n\n2. CLASSIFY 버튼을 눌러 분류를 시작하세요.\n\n3. 분류가 끝나면 RECORDS와 STAT 버튼이 활성화됩니다.\n\n4. 본인의 수면 자세를 확인하고 교정해보세요.\n\n5. HISTORY 화면에서 이전에 분류된 수면 자세들을 다시 확인할 수 있습니다!");

        initModel(); // 모델 초기화

        FirstActivity.loadingLayout.setVisibility(View.GONE);
    }

    // 추출된 이미지 전처리 및 정규화
    private int predictImage(Bitmap bitmap) {
        // 이미지를 90도 반시계방향으로 회전
        Matrix matrix = new Matrix();
        matrix.postRotate(-90);
        bitmap = Bitmap.createBitmap(bitmap, 0, 0, bitmap.getWidth(), bitmap.getHeight(), matrix, true);

        // 이미지 크기가 최소 1080x1080이 되도록 조절
        if (bitmap.getWidth() < 1080 || bitmap.getHeight() < 1080) {
            bitmap = Bitmap.createScaledBitmap(bitmap, 1080, 1080, true);
        }

        // 1080x1080 크기로 자르기
        bitmap = Bitmap.createBitmap(bitmap, 0, 0, 1080, 1080);

        // 224x224로 크기 조절 (ResNet의 입력 이미지 크기는 224x224x3)
        bitmap = Bitmap.createScaledBitmap(bitmap, 224, 224, true);

        // 정규화
        int[] intValues = new int[224 * 224];
        float[][][][] input = new float[1][224][224][3];
        bitmap.getPixels(intValues, 0, bitmap.getWidth(), 0, 0, bitmap.getWidth(), bitmap.getHeight());

        for (int i = 0; i < intValues.length; ++i) {
            final int val = intValues[i];
            input[0][i / 224][i % 224][0] = ((val >> 16) & 0xFF) / 255.0f;
            input[0][i / 224][i % 224][1] = ((val >> 8) & 0xFF) / 255.0f;
            input[0][i / 224][i % 224][2] = (val & 0xFF) / 255.0f;
        }

        // 출력 tensor 생성
        float[][] output = new float[1][NUM_CLASSES];

        // 이미지 예측 실행
        try {
            tfliteInterpreter.run(input, output);
        } catch (Exception e) {
            e.printStackTrace();
            // add log or print statement
            Log.e("RecordActivity", "Error predicting image", e);
            return -1;
        }

        // 예측 결과 return
        int prediction = getPrediction(output[0]);
        return prediction;
    }

    // 가장 높은 확률의 클래스 찾기
    private int getPrediction(float[] probabilities) {
        int prediction = -1;
        float maxProb = -1.0f;
        for (int i = 0; i < NUM_CLASSES; i++) {
            if (probabilities[i] > maxProb) {
                prediction = i;
                maxProb = probabilities[i];
            }
        }
        return prediction;
    }

    // 분류 작업 실행
    private void performClassification() {
        runOnUiThread(new Runnable() {
            @Override
            public void run() {
                loadingLayout.setVisibility(View.VISIBLE);
            } // 분류가 시작되면 로딩 창 표시
        });

        // 기존 classifications.txt 파일 삭제
        String filePath = getFilesDir() + "/classifications.txt";
        File classificationsFile = new File(filePath);
        if (classificationsFile.exists()) {
            classificationsFile.delete();
        }

        // Classified_on_~.txt의 파일명으로 표시될 타임스탬프 생성
        final String timestamp = new SimpleDateFormat("yyyyMMdd_HHmmss", Locale.getDefault()).format(new Date());
        String classifiedOnFileName = "Classified_on_" + timestamp + ".txt";

        new Thread(new Runnable() {
            @Override
            public void run() {
                // 클래스 정의
                String[] classLabels = {"back", "back_hurray", "back_left", "back_right", "front", "front_hurray", "front_left_raised", "front_right_raised", "left", "right"};

                if (selectedVideoUri != null) {
                    MediaMetadataRetriever retriever = new MediaMetadataRetriever();
                    try {
                        retriever.setDataSource(FirstActivity.this, selectedVideoUri);

                        // 영상 전체 길이 가져오기
                        String durationStr = retriever.extractMetadata(MediaMetadataRetriever.METADATA_KEY_DURATION);
                        long durationMs = Long.parseLong(durationStr);

                        // 영상 길이에 따른 프레임 추출 간격 설정
                        long frameValues;
                        if (durationMs <= 60000) { // 1분 이하 영상이면,
                            frameValues = 1000; // 1초 단위로 추출
                        } else if (durationMs <= 600000) { // 10분 이하,
                            frameValues = 10000; // 10초 단위
                        } else if (durationMs <= 1800000) { // 30분 이하,
                            frameValues = 30000; // 30초 단위
                        } else if (durationMs <= 3600000) { // 1시간 이하,
                            frameValues = 60000; // 1분 단위
                        } else if (durationMs <= 10800000) { // 3시간 이하,
                            frameValues = 180000; // 3분 단위
                        } else if (durationMs <= 21600000) { // 6시간 이하,
                            frameValues = 360000; // 6분 단위
                        } else if (durationMs <= 43200000) { // 12시간 이하,
                            frameValues = 600000; // 10분 단위
                        } else { // 12시간 초과,
                            frameValues = 1200000; // 20분 단위
                        }

                        // 설정한 간격으로 프레임 추출
                        for (long i = 0; i < durationMs; i += frameValues) {
                            final Bitmap bitmap = retriever.getFrameAtTime(i * 1000, MediaMetadataRetriever.OPTION_CLOSEST_SYNC);

                            if (bitmap != null) {
                                final int prediction = predictImage(bitmap); // 예측 실행
                                final String predictionLabel = (prediction == -1) ? "Error" : classLabels[prediction];

                                // 예측 결과를 새 classifications.txt로 저장
                                FileHelper.writeToFile(FirstActivity.this, selectedVideoUri.toString() + ", " + predictionLabel + ", frame: " + i + "\n");

                                // 동일한 타임스탬프를 가진 classified_on_~.txt 파일에 예측 결과 저장
                                FileHelper2.writeToFile(FirstActivity.this, selectedVideoUri.toString() + ", " + predictionLabel + ", frame: " + i + "\n", classifiedOnFileName);
                            }
                        }
                    } catch (Exception e) {
                        e.printStackTrace();
                    } finally {
                        retriever.release();
                    }

                    // 분류 작업이 끝나면 RECORDS, STAT 버튼 활성화 및 로딩 창 숨김
                    runOnUiThread(new Runnable() {
                        @Override
                        public void run() {
                            buttonB.setEnabled(true);
                            buttonC.setEnabled(true);
                            editor.putBoolean(BUTTON_B_ENABLED, true);
                            editor.putBoolean(BUTTON_C_ENABLED, true);
                            editor.apply();

                            loadingLayout.setVisibility(View.GONE);

                            cleanupClassifiedOnFiles();
                        }
                    });
                } else {
                    runOnUiThread(new Runnable() {
                        @Override
                        public void run() {
                            Toast.makeText(FirstActivity.this, "영상을 선택하세요.", Toast.LENGTH_SHORT).show();
                            loadingLayout.setVisibility(View.GONE);

                            cleanupClassifiedOnFiles();
                        }
                    });
                }
            }
        }).start();
    }

    // 영상 제목 추출
    private String getFileNameFromUri(Uri uri) {
        String result = null;
        if (uri.getScheme().equals("content")) {
            // cursor를 사용해 URI 정보 조회
            Cursor cursor = getContentResolver().query(uri, null, null, null, null);
            try {
                if (cursor != null && cursor.moveToFirst()) { // 조회된 정보가 있으면 cursor를 첫 행으로 이동
                    result = cursor.getString(cursor.getColumnIndexOrThrow(MediaStore.Video.Media.DISPLAY_NAME)); // cursor로부터 파일명을 가져와 result에 저장
                }
            } finally {
                cursor.close();
            }
        }

        // 위 작업에 실패했다면 URI 경로에서 직접 파일명 추출
        if (result == null) {
            result = uri.getPath();
            int cut = result.lastIndexOf('/');
            if (cut != -1) {
                result = result.substring(cut + 1);
            }
        }
        return result;
    }

    // Classified_on_~.txt 파일 정리를 위한 함수
    private void cleanupClassifiedOnFiles() {
        File dir = getFilesDir();
        File[] classifiedOnFiles = dir.listFiles(new FilenameFilter() {
            public boolean accept(File dir, String name) {
                return name.startsWith("Classified_on_") && name.endsWith(".txt");
            }
        });

        // Classified_on_~.txt가 31개 이상일 경우 가장 오래된 파일 삭제
        if (classifiedOnFiles != null && classifiedOnFiles.length > 30) {
            // 파일을 마지막 수정된 날짜에 따라 정렬
            Arrays.sort(classifiedOnFiles, new Comparator<File>() {
                public int compare(File f1, File f2) {
                    return Long.valueOf(f1.lastModified()).compareTo(f2.lastModified());
                }
            });
            classifiedOnFiles[0].delete(); // 가장 오래된 파일 삭제
        }
    }

    /* 메인 화면 결과 처리
    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        // 영상 선택 요청 코드가 주어지면 결과 처리
        if (requestCode == VIDEO_PICK_REQUEST && resultCode == RESULT_OK && data != null) {
            Uri selectedVideoUri = data.getData();
            // TODO; 여기서 selectedVideoUri를 사용해 추가 작업 수행 가능
        }
    }
    */

    // 권한 요청 처리
    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        if (requestCode == REQUEST_PERMISSION) {
            if (grantResults.length > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                // 권한이 허용됨
                Toast.makeText(this, "권한 허용됨", Toast.LENGTH_SHORT).show();
            } else {
                // 권한이 거부됨
                Toast.makeText(this, "권한이 필요합니다!", Toast.LENGTH_LONG).show();
            }
        }
    }
}
