# Weather App 🌤️

A simple weather application built for Android that provides real-time weather information based on the user's location.

## Features ✨
- Get the current weather based on your location. 🌍
- Displays city name, temperature, and weather condition. 🌡️
- Shows weather icons for a better visual experience. 🌈
- Splash screen before the main app screen. 🚀

## Technologies Used 💻
- Android Studio 🖥️
- Java ☕
- OpenWeather API (for weather data) 🌦️
- Volley (for making API requests) 📡

## Setup and Installation 🛠️

Follow these steps to get the project running on your local machine.

### Prerequisites 📜
- Android Studio (latest version) 📲
- Android SDK 📦
- OpenWeather API Key (You can get it [here](https://openweathermap.org/)) 🔑

### Steps to Run the App 🚀
1. Clone the repository:
    ```bash
    git clone https://github.com/Muqaddas-lab/Weather_App.git
    ```
2. Open the project in Android Studio. 🖥️
3. Add your OpenWeather API Key:
   - Go to `MainActivity.java`.
   - Replace the `apiKey` variable with your API key:
     ```java
     String apiKey = "your-api-key-here";
     ```
4. Run the app on an emulator or a real device. 📱

## App Flow 🔄
1. The app opens with a splash screen. 💥
2. After 3 seconds, it transitions to the main screen. 🎬
3. The app fetches the user's current location. 📍
4. It then fetches the weather data from OpenWeather API and displays it on the screen. 🌞

## Permissions Required 🔐
- Location Permission: To fetch the user's current location. 📍

### How to Add Permissions 🔧:
- Go to your `AndroidManifest.xml` and add the following permissions:
  ```xml
  <uses-permission android:name="android.permission.ACCESS_FINE_LOCATION" />
  <uses-permission android:name="android.permission.ACCESS_COARSE_LOCATION" />
