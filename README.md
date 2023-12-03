# Sleep Position Classification App Using Deep Learning
Capstone design project for graduation

## Abstract
This is a repository for works that allow users to identify their sleep positions by classifying the sleep position image data received by a deep learning model, recording them on the app screen, and generating statistics.

Most systems that provide sleep-related information are linked to smart devices such as smart watches to analyze biosignals and evaluate sleep quality. However, they have some disadvantage of having to wear a smart device even while sleeping, which is inconvenient, and they do not provide information about sleep positions. So, to avoid any inconvenience while sleeping, our team wanted to develop a system that would capture actual sleeping images using a home CCTV and allow users to identify what position they slept in.

In this work, ResNet50 was trained using Google Colab. We purchased a home CCTV and recorded sleeping videos. Plus, we applied data transformation and augmentation and various improvement techniques improving overfitting to increase the test accuracy to over 90%. The ResNet50 was converted to 'tflite' format and saved in Android Studio. Then, we created an app using Android Studio so that users can easily identify their sleep positions. The main functions are divided into MAIN, INFO, RECORDS, and STAT screens. On the MAIN screen, the sleeping video is uploaded, goes through preprocessing, and ResNet50 classifies the sleep positions. On the INFO screen, users can check the pros, cons, and improvements for 10 different sleeping positions. On the RECORDS screen, classified images are recorded. On the STAT screen, a representative image and two charts are provided.

This allows users to identify their sleep positions and correct them themselves. It is expected to be highly useful for patients, newborns, the elderly, and other medical and health fields.

## Flow Chart
![Capstone Design Flow Chart](https://github.com/parkjjoe/capstone-design/assets/105961163/65fff919-495e-40cc-a756-18885438fcb8)

## ResNet
In deep learning models, errors may increase as the neural network becomes deeper. This is called the gradient vanishing or exploding. But ResNet improved this problem by introducing the concept of residual learning. Other neural network models predict the label of an input image when received. However, ResNet trains the residual, the difference between input and output. This allows us to build a deeper network.

The residual operation in a residual block consists of two things:
- x, the input passed directly to the output through a shortcut connection
- F(x), the output when the input 'x' passes through Conv layers and is passed to

The output of residual block learns these two residuals. Additionally, a shortcut connection that directly connects input and output makes gradient backpropagation more effective. When the filter dimension in a layer within the residual block is doubled, the feature map size is reduced by half to maintain the complexity per layer. Versions after ResNet50 use the bottleneck block structure, which consists of Conv layers in the order of 1x1, 3x3, and 1x1.

ResNet's residual block includes a Convolutional Block (Conv Block) and an Identity Block (Id Block). Both alleviate the vanishing gradient problem that can occur in deep neural networks, and are commonly composed of a Conv layer - Batch Norm layer - ReLU layer and also have a shortcut connection. However, since the input and output dimensions of the Conv Block may be different, the Conv operation is also applied to shortcut connections in Conv Block. Therefore, Conv Block is also used when changing dimensions or downsampling. Because of this difference, the Conv Block is used first, followed by the ID Block.

Coming soon..
