import firebase from 'firebase/app';
import 'firebase/storage';

// Initialize Firebase
const firebaseConfig = {
    apiKey: "AIzaSyB_41R44_-HPpcw6I8rew9cmWTp360LYhc",
    authDomain: "bookstore-5336d.firebaseapp.com",
    projectId: "bookstore-5336d",
    storageBucket: "bookstore-5336d.appspot.com",
    messagingSenderId: "267633563396",
    appId: "1:267633563396:web:136deb1a1a3b836d956251",
};

firebase.initializeApp(firebaseConfig);
