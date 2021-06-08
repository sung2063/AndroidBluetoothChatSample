# Android Bluetooth Chat Sample App

Bluetooth chat application is a sample Android project which can chat with other user in anywhere, anytime without internet required.</br>
This is also a supporting tool for helping people social distancing from COVID-19 pandamic.

## 💖 Sponsor

Android Bluetooth Chat updates regularly. Your valuable sponsorship helps me contributing more features and maintaining the library. Support me for building more interesting projects! 💜

<div align="center">
<a href="https://github.com/sponsors/sung2063"><img src="https://img.shields.io/badge/sponsor-30363D?style=for-the-badge&logo=GitHub-Sponsors&logoColor=#white" style="float: left" /></a>
<a href="https://www.paypal.com/donate?business=sunghyunb1991%40gmail.com&item_name=GitHub+Open+Source+Project+Sponsorship&currency_code=USD"><img src="https://img.shields.io/badge/PayPal-00457C?style=for-the-badge&logo=paypal&logoColor=white" /></a>
</div>
 
## 🎬 Sample GIF Image

<center>
  <table>
    <tr style="border-collapse: collapse;">
      <td><img src="gifs/sample_gif.gif" width="575" /></td>
    </tr>
    <tr>
      <td>Android Bluetooth Chat App - No internet required 👍</td>
    </tr>
   </table>
 </center>


## 🔢 Instruction

1) Clone the AndroidBluetoothChatSample repository to your local computer
2) Open the project on Android Studio
3) Prepare two Android devices either phone(s) or tablet(s)
4) Turn on the Bluetooth on both devices and pair them each other
5) Run the app on both devices
6) Create a room on one device and join the room with other device
7) Enjoy chatting! 💬

## 📖 How to Use Bluetooth Chat Library

<b>1. Setup your Android project setting</b>

Minimum SDK Version: 21 or greater (Update in your <i>app level</i> `build.gradle`)<br/>
Supported Programming Language: Java
<br/><br/>
<b>2. Add required library</b>

First, include following jitpack url inside maven block in your <i>project level</i> `build.gradle`.
```gradle
allprojects {
    repositories {
        ...
        maven { url 'https://jitpack.io' }
    }
}
```

Next, add the Bluetooth chat library in <i>app level</i> `build.gradle` and sync the gradle file. 
```gradle
implementation 'com.github.sung2063:AndroidBluetoothChatLibrary:1.02'
```

Now you are ready to use BluetoothChat library and it has two different roles: 
<ul>
 <li><b>Server</b> - Creates the room and communicate with client user</li>
 <li><b>Client</b> - Joins the existing room and communicate with server user</li>
</ul>

Start with implementing each methods of EventListener interface on Activity.
```java
EventListener eventListener = new EventListener() {

 @Override
 public void onConnected() {
    // TODO: Implement the method
 }

  @Override
  public void onNotified(final int notificationType) {
    // TODO: Implement the method
  }

  @Override
  public void onReceived(final String message) {
    // TODO: Implement the method
  }

  @Override
  public void onErrorOccurred(ErrorDataModel errorData) {
    // TODO: Implement the method
  }
};
```

Once interface is implemented, create an object either server or client whichever you require and use its features to develop Bluetooth chat system.
```java
ServerConnectivity serverConnectivity = new ServerConnectivity(this, eventListener);      // Create a server object
ClientConnectivity clientConnectivity = new ClientConnectivity(this, eventListener);      // Create a client object
```

## 📚 Library APIs

Here are the available library APIs which you can use to develop a Bluetooth chat system.

<b>ServerConnectivity</b>

<center>
  <table>
    <tr>
      <th><b>Method</b></th>
      <th><b>Description</b></th>
    </tr>
    <tr>
      <td><b>sendMessage(message)</b></td>
      <td>Sends message to client.</td>
    </tr>
    <tr>
      <td><b>onDestroy()</b></td>
      <td>Close the chat room.</td>
    </tr>
   </table>
 </center>
 
<b>ClientConnectivity</b>

<center>
  <table>
    <tr>
      <th><b>Method</b></th>
      <th><b>Description</b></th>
    </tr>
    <tr>
      <td><b>sendMessage(message)</b></td>
      <td>Sends message to server.</td>
    </tr>
    <tr>
      <td><b>onDestroy()</b></td>
      <td>Leave the chat room.</td>
    </tr>
   </table>
 </center>
 
<b>IncomingType</b>
 
 <center>
  <table>
    <tr>
      <th><b>Field</b></th>
      <th><b>Value</b></th>
      <th><b>Description</b></th>
    </tr>
    <tr>
      <td><b>NOTIFICATION</b></td>
      <td>0</td>
      <td>The message is alert or notification.</td>
    </tr>
    <tr>
      <td><b>GENERAL_MESSAGE</b></td>
      <td>1</td>
      <td>The message received from server or client.</td>
    </tr>
   </table>
 </center>
 
<b>Notification</b>
 
 <center>
  <table>
    <tr>
     <th><b>Field</b></th>
      <th><b>Value</b></th>
      <th><b>Description</b></th>
    </tr>
    <tr>
      <td><b>NOTIFICATION_ROOM_ESTABLISH</b></td>
      <td>0</td>
      <td>The notification when room is established.</td>
    </tr>
    <tr>
      <td><b>NOTIFICATION_SELF_ENTER</b></td>
      <td>1</td>
      <td>The notification when client enters the room on client side.</td>
    </tr>
    <tr>
      <td><b>NOTIFICATION_ENTER</b></td>
      <td>2</td>
      <td>The notification when client enters the room on server side.</td>
    </tr>
    <tr>
      <td><b>NOTIFICATION_LEAVE</b></td>
      <td>3</td>
      <td>The notification when client leave the room.</td>
    </tr>
   </table>
 </center>
 
 ## 🌟 Contributor
 
 Sung Hyun Back (@sung2063)
