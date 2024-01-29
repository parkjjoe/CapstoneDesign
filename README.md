# Sleep Position Classification App Using Deep Learning
Capstone design project for graduation

## Abstract
This is a repository for the capstone design project that allow users to identify their sleep positions by classifying the sleep position image data received by a deep learning model, recording them on the app screen, and generating statistics.

In this work, ResNet50 was trained by Google Colab. We purchased a home CCTV and recorded sleeping videos. Plus, we applied data transformation, data augmentation and various improvement techniques improving overfitting to increase the test accuracy to over 90%. The ResNet50 was converted to 'tflite' format and saved in Android Studio. Then, we created an application so that users can easily identify their sleep positions. The application is divided into MAIN, INFO, RECORDS, and STAT screens. The functions of each screen are as follows:
- MAIN: The sleeping video is uploaded, goes through preprocessing, and then ResNet50 classifies the sleep positions.
- INFO: Users can check the pros, cons, and improvements for 10 different sleeping positions.
- RECORDS: Classified images are recorded.
- STAT: A representative image and two charts are provided.

## Flow Chart
![Capstone Design Flow Chart](https://github.com/parkjjoe/capstone-design/assets/105961163/65fff919-495e-40cc-a756-18885438fcb8)

## How the app works
Coming soon...
