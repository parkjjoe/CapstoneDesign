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
### MAIN
![MAIN1](https://github.com/parkjjoe/sleep-position-classification/assets/105961163/67d876e8-0436-4958-b968-68690e37b568)

When you first run the app, this screen appears. Only the '**INFO**' and '**SELECT**' buttons are activated.

![MAIN2_Select_Video1](https://github.com/parkjjoe/sleep-position-classification/assets/105961163/c7ad2895-12b5-4be9-920c-602601c76fcf)
![MAIN3_Select_Video2](https://github.com/parkjjoe/sleep-position-classification/assets/105961163/66e73211-db04-46ce-9578-bac4db5c4483)
![MAIN4_After_Selecting_Video](https://github.com/parkjjoe/sleep-position-classification/assets/105961163/2789606c-58e2-437d-a89d-3ef79508df26)

When you click the '**SELECT**' button, you can select a video. After selecting the video in your gallery, the video name is displayed in a black box on the MAIN screen, and the toast message '영상이 업로드되었습니다.' appears. And then, you can click the '**CLASSIFY**' button.

![MAIN5_CLASSIFY](https://github.com/parkjjoe/sleep-position-classification/assets/105961163/02ee86ab-976e-4f67-9392-554c93e8d986)
![MAIN6_After_Classifying](https://github.com/parkjjoe/sleep-position-classification/assets/105961163/1dfdfcab-0627-41aa-8447-c1a8a2d4ee21)

When you click the '*CLASSIFY*' button, the images are extracted from the uploaded video in designated frame units. The video path, classification results, and extracted frame units are written and stored in a total of two txt files. After classifying, you can click the '**RECORDS**' and '**STAT**' buttons.

![classification.txt](https://github.com/parkjjoe/sleep-position-classification/assets/105961163/4c1b239c-b8e4-41e0-a848-406433ec6832)

The txt files are written as shown in the picture above. One is used in the INFO, RECORD, and STAT screens, and the other is used in the HISTORY and HISTORY2 screens. Up to 30 txt files used in HISTORY and HISTORY2 are stored, and more are stored in place of one of the oldest files.

### INFO
