# Sleep Position Classification App Using Deep Learning
Capstone design project for graduation

## Abstract
This is a repository for the capstone design project that allow users to identify their sleep positions by classifying the sleep position image data received by a deep learning model, recording them on the app screen, and generating statistics.

In this work, ResNet50 was trained using Google Colab. We purchased a home CCTV and recorded sleeping videos. Plus, we applied data transformation and augmentation and various improvement techniques improving overfitting to increase the test accuracy to over 90%. The ResNet50 was converted to 'tflite' format and saved in Android Studio. Then, we created an app using Android Studio so that users can easily identify their sleep positions. The main functions are divided into MAIN, INFO, RECORDS, and STAT screens. On the MAIN screen, the sleeping video is uploaded, goes through preprocessing, and ResNet50 classifies the sleep positions. On the INFO screen, users can check the pros, cons, and improvements for 10 different sleeping positions. On the RECORDS screen, classified images are recorded. On the STAT screen, a representative image and two charts are provided.

## Flow Chart
![Capstone Design Flow Chart](https://github.com/parkjjoe/capstone-design/assets/105961163/65fff919-495e-40cc-a756-18885438fcb8)

## Files Description
### Google Colab

### Android Studio

Coming soon..
