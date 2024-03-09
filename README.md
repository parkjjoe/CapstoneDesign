# Sleep Position Classification App Using Deep Learning
Capstone design project for graduation

## Abstract
This is a repository for the capstone design project that allow users to identify their sleep positions by classifying the sleep position image data received by a deep learning model, recording them on the app screen, and generating statistics.

In this work, ResNet50 was trained by Google Colab. We purchased a home CCTV and recorded sleeping videos. Plus, we applied data transformation, data augmentation and various improvement techniques improving overfitting to increase the test accuracy to over 90%. The ResNet50 was converted to 'tflite' format and saved in Android Studio. Then, we created an application so that users can easily identify their sleep positions. The application is divided into MAIN, INFO, RECORDS, and STAT screens. The functions of each screen are as follows:
- **MAIN**: The sleeping video is uploaded, goes through preprocessing, and then ResNet50 classifies the sleep positions.
- **INFO**: Users can check the pros, cons, and improvements for 10 different sleeping positions.
- **RECORDS**: Classified images are recorded.
- **STAT**: A representative image and two charts are provided.

## Flow Chart
![Capstone Design Flow Chart](https://github.com/parkjjoe/capstone-design/assets/105961163/65fff919-495e-40cc-a756-18885438fcb8)

## Usage
### 🔴 MAIN
![MAIN1](https://github.com/parkjjoe/sleep-position-classification/assets/105961163/67d876e8-0436-4958-b968-68690e37b568)

When you first run the app, this screen appears. Only the '**INFO**' and '**SELECT**' buttons are activated.

![MAIN2_Select_Video1](https://github.com/parkjjoe/sleep-position-classification/assets/105961163/c7ad2895-12b5-4be9-920c-602601c76fcf)
![MAIN3_Select_Video2](https://github.com/parkjjoe/sleep-position-classification/assets/105961163/66e73211-db04-46ce-9578-bac4db5c4483)
![MAIN4_After_Selecting_Video](https://github.com/parkjjoe/sleep-position-classification/assets/105961163/2789606c-58e2-437d-a89d-3ef79508df26)

When you click the '**SELECT**' button, you can select a video. After selecting the video in your gallery, the video name is displayed in a black box on the MAIN screen, and the toast message '영상이 업로드되었습니다.' appears. And then, the '**CLASSIFY**' button is activated.

![MAIN5_CLASSIFY](https://github.com/parkjjoe/sleep-position-classification/assets/105961163/02ee86ab-976e-4f67-9392-554c93e8d986)
![MAIN6_After_Classifying](https://github.com/parkjjoe/sleep-position-classification/assets/105961163/1dfdfcab-0627-41aa-8447-c1a8a2d4ee21)

When you click the '**CLASSIFY**' button, the images are extracted from the uploaded video in designated frame units. The video path, classification results, and extracted frame units are written and stored in two txt files. After classifying, the '**RECORDS**' and '**STAT**' buttons are activated.

![classification.txt](https://github.com/parkjjoe/sleep-position-classification/assets/105961163/4c1b239c-b8e4-41e0-a848-406433ec6832)

The txt files are written as shown in the picture above. One is used in the INFO, RECORD, and STAT screens, and the other is used in the HISTORY and HISTORY2 screens. Up to 30 txt files used in HISTORY and HISTORY2 are stored, and more are stored in place of the oldest file.

### 🟠 INFO
![INFO1](https://github.com/parkjjoe/sleep-position-classification/assets/105961163/83f00dad-0082-4fc4-99e9-c90e598cbf01)

When you click the '**INFO**' button on the MAIN screen before classifying, this screen appears.

![INFO2](https://github.com/parkjjoe/sleep-position-classification/assets/105961163/9cd1dc61-9625-4dfd-8c54-1cae8653612d)
![SOURCE1](https://github.com/parkjjoe/sleep-position-classification/assets/105961163/37c2ce6f-b407-483e-a4ed-c4a4bfb609fd)

When you click the image in the table below, the pros, cons and improvement information of that sleep position are displayed in a dialog window. When you click the '**SOURCE**' button, you can see the sources for the images and information.

![INFO3](https://github.com/parkjjoe/sleep-position-classification/assets/105961163/05f8b358-ebaa-4023-87a0-7dba67c9dc9e)

When you click the '**INFO**' button on the MAIN screen after classifying, the classified classes are displayed at the top, and the classe names written in the table are changed to bold.

### 🟡 RECORDS
![RECORDS1](https://github.com/parkjjoe/sleep-position-classification/assets/105961163/f7b580eb-27ab-4561-8537-0302db08a5b4)
![RECORDS2](https://github.com/parkjjoe/sleep-position-classification/assets/105961163/07562bc6-9dc7-4783-9640-dc36fabba559)

When you click '**RECORDS**' button on the MAIN screen after classifying, you can see the image extracted for each frame and the classification class of the image. If you click any image, the image will be enlarged in the center of the screen.

### 🟢 HISTORY2 (RECORDS)
![HISTORY2_RECORDS](https://github.com/parkjjoe/sleep-position-classification/assets/105961163/a1658b5e-d243-4770-ad0f-2243a7f9ee94)

When you click '**HISTORY2**' button on the RECORDS screen, you should choose one txt file. After that, the classification information in the selected txt file is output as if displayed on the RECORDS screen.

### 🔵 STAT
![STAT1](https://github.com/parkjjoe/sleep-position-classification/assets/105961163/a5be08b2-48bd-44b2-86f5-273c5706d3fc)
![STAT2](https://github.com/parkjjoe/sleep-position-classification/assets/105961163/dfb9a8e8-9d18-481e-8303-86baa05a2fa2)
![STAT3](https://github.com/parkjjoe/sleep-position-classification/assets/105961163/aaf07677-27d7-4898-b977-1e2db5c18119)

When you click '**STAT**' button on the MAIN screen after classifying, you can see the most classified class and representative image, a chart of changes in sleep position over time, and a chart of sleep position percentage indicators.

### 🟣 HISTORY (STAT)
![HISTORY_STAT](https://github.com/parkjjoe/sleep-position-classification/assets/105961163/bb138211-ca0e-4751-baef-936363f04f53)

Like the HISTORY2 screen, when you click '**HISTORY**' button on the STAT screen, you should choose one txt file. After that, the classification information in the selected txt file is output as if displayed on the STAT screen.

## Android Studio Files Matching
|Screen Name|.java|.xml|Additional files|
|:---:|---|---|---|
|**MAIN**|FirstActivity.java|first.xml|FileHelper.java <br> FileHelper2.java <br> loading.xml <br> border.xml <br> border_red.xml|
|**INFO**|InfoActivity.java|info.xml|dialog_image.xml|
|**SOURCE**|SourceActivity.java|source.xml|
|**RECORDS**|RecordActivity.java|records.xml|dialog_info.xml|
|**HISTORY2 (RECORDS)**|Histor2yActivity.java|history2.xml|dialog_info.xml|
|**STAT**|MainActivity.java|capstonedesign.xml|border.xml <br> border_chart.xml|
|**HISTORY (STAT)**|HistoryActivity.java|history.xml|border.xml <br> border_chart.xml|
