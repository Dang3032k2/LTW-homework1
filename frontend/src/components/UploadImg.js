import { Button, Form, Upload } from "antd";
import firebase from 'firebase/compat/app';
import 'firebase/compat/storage';
import { useEffect, useState } from "react";
function ImageUploader({ imageUrl, onUploadComplete, disabled }) {
    const [avatar, setAvatar] = useState();
    const firebaseConfig = {
        apiKey: "AIzaSyB_41R44_-HPpcw6I8rew9cmWTp360LYhc",
        authDomain: "bookstore-5336d.firebaseapp.com",
        projectId: "bookstore-5336d",
        storageBucket: "bookstore-5336d.appspot.com",
        messagingSenderId: "267633563396",
        appId: "1:267633563396:web:136deb1a1a3b836d956251",
    };
    firebase.initializeApp(firebaseConfig);
    useEffect(() => {
        return () => avatar && URL.revokeObjectURL(avatar.preview);
    }, [avatar]);

    useEffect(() => {
        if (imageUrl) setAvatar({ preview: imageUrl })
    }, [imageUrl])

    const handlePreviewAvatar = async (e) => {
        const file = e.file;
        file.preview = URL.createObjectURL(file);
        setAvatar(file);
        const storageRef = firebase.storage().ref();
        const fileRef = storageRef.child(file.name);
        await fileRef.put(file);
        const url = await fileRef.getDownloadURL();
        onUploadComplete(url);
    };

    return (
        <>
            <Form.Item
                name="imageurl"
                rules={[{ required: false }]}
                valuePropName="fileList"
                getValueFromEvent={(e) => e.fileList}
            >
                <Upload
                    name="imageurl"
                    maxCount={1}
                    beforeUpload={() => false}
                    onChange={handlePreviewAvatar}
                    showUploadList={false}
                    disabled={disabled}
                >
                    <Button >Upload</Button>
                </Upload>
            </Form.Item>
            {avatar && (
                <img
                    style={{ marginTop: 8 }}
                    src={avatar.preview}
                    alt=""
                    width="55%"
                />
            )}
        </>
    );
}

export default ImageUploader;