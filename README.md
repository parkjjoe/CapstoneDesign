# Sleeping Position Classification App Using Deep Learning
Capstone design project for graduation

## Abstract
This repository is a repository for works that allow users to understand their sleeping posture by classifying the sleeping posture image data received by a deep learning model, recording it on the app screen, and generating statistics.

Most systems that provide sleep-related information are linked to smart devices such as smart watches to analyze biosignals to evaluate sleep quality. However, it has the disadvantage of having to wear the smart device even while sleeping, which is inconvenient, and it does not provide information about sleeping posture. So, to avoid any inconvenience while sleeping, we wanted to develop a system that would capture actual sleeping images using home CCTV and allow users to check what position they slept in.

In this work, ResNet50 was trained using Google Colab. We purchased a home CCTV and recorded sleeping videos, and applied data transformation and augmentation and various overfitting improvement techniques to increase the test accuracy to over 90%. The completed ResNet50 model was converted to tflite format and saved in Android Studio. We created an app using Android Studio so that users can easily check their sleeping posture. The main functions are divided into main, information, records, and statistics screens. On the main screen, the sleeping video is uploaded, goes through preprocessing, and a deep learning model classifies the posture. On the information screen, you can check the pros, cons, and improvements for 10 different sleeping positions. In the record screen, classified images are recorded, and in the statistics screen, a representative image and two charts are provided according to the classified results.

This allows users to check their sleeping posture and correct it themselves. It is expected to be highly useful for patients, newborns, the elderly, and other medical and health fields where sleeping positions are important.
