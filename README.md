# Sleep Position Classification App Using Deep Learning
Capstone design project for graduation

## Abstract
This is a repository for works that allow users to identify their sleep positions by classifying the sleep position image data received by a deep learning model, recording them on the app screen, and generating statistics.

Most systems that provide sleep-related information are linked to smart devices such as smart watches to analyze biosignals and evaluate sleep quality. However, they have some disadvantage of having to wear a smart device even while sleeping, which is inconvenient, and they do not provide information about sleep positions. So, to avoid any inconvenience while sleeping, our team wanted to develop a system that would capture actual sleeping images using a home CCTV and allow users to identify what position they slept in.

In this work, ResNet50 was trained using Google Colab. We purchased a home CCTV and recorded sleeping videos. Plus, we applied data transformation and augmentation and various improvement techniques improving overfitting to increase the test accuracy to over 90%. The ResNet50 was converted to 'tflite' format and saved in Android Studio. Then, we created an app using Android Studio so that users can easily identify their sleep positions. The main functions are divided into MAIN, INFO, RECORDS, and STAT screens. On the MAIN screen, the sleeping video is uploaded, goes through preprocessing, and ResNet50 classifies the sleep positions. On the INFO screen, users can check the pros, cons, and improvements for 10 different sleeping positions. On the RECORDS screen, classified images are recorded. On the STAT screen, a representative image and two charts are provided.

This allows users to identify their sleep positions and correct them themselves. It is expected to be highly useful for patients, newborns, the elderly, and other medical and health fields.
