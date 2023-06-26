import { Button, Form, Input, message } from "antd";
import React from "react";
import { Link } from "react-router-dom";
import { generateSHA512Hash } from "../utils";

function Signup(props) {
    const onFinish = (values) => {
        console.log(values);
        fetch("http://localhost:8080/signup", {
            method: "POST",
            mode: "cors",
            body: JSON.stringify({ ...values, password: generateSHA512Hash(values.password) }),
            headers: {
                'Content-Type': 'application/json; charset=UTF-8',
            }
        })
            .then((data) => {
                if (data.status === 200) {
                    message.success("Đăng kí thành công!")
                } else {
                    message.error("Tên người dùng đã tồn tại!");
                }
            })
    };

    return (
        <Form
            name="basic"
            labelCol={{ span: 8 }}
            wrapperCol={{ span: 16 }}
            style={{ width: "50%", marginLeft: "23%" }}
            initialValues={{ remember: true }}
            onFinish={onFinish}
            // onFinishFailed={onFinishFailed}
            autoComplete="off"
        >
            <h1 style={{ textAlign: "center", marginTop: "50px" }}>Đăng kí</h1>
            <Form.Item
                label="Username"
                name="name"
                rules={[{ required: true, message: 'Vui lòng nhập username!' }]}
            >
                <Input />
            </Form.Item>
            <Form.Item
                label="Email"
                name="email"
                rules={[
                    {
                        type: 'email',
                        message: 'Email không hợp lệ!',
                    },
                    {
                        required: true,
                        message: 'Vui lòng nhập E-mail của bạn!',
                    },
                ]}
            >
                <Input />
            </Form.Item>
            <Form.Item
                label="Password"
                name="password"
                rules={[
                    { required: true, message: 'Vui lòng nhập mật khẩu!', min: 3 },
                    {
                        pattern: /^[a-zA-Z0-9!@#$%^&.]+$/,
                        message: 'Mật khẩu không hợp lệ!',
                    },
                ]}
                hasFeedback
            >
                <Input.Password />
            </Form.Item>
            <Form.Item
                name="confirm"
                label="Confirm Password"
                dependencies={['password']}
                hasFeedback
                rules={[
                    {
                        required: true,
                        message: 'Vui lòng xác nhận mật khẩu!',
                    },
                    ({ getFieldValue }) => ({
                        validator(_, value) {
                            if (!value || getFieldValue('password') === value) {
                                return Promise.resolve();
                            }
                            return Promise.reject(new Error('Mật khẩu không khớp!'));
                        },
                    }),
                ]}
            >
                <Input.Password />
            </Form.Item>
            <Form.Item wrapperCol={{ offset: 8, span: 16 }}>
                <Button type="primary" htmlType="submit" style={{ background: 'lightcoral' }}>
                    Submit
                </Button>
            </Form.Item>
            <Form.Item wrapperCol={{ offset: 8, span: 16 }}>
                Đã có tài khoản?
                <Link to={"/login"} style={{ textDecoration: "none", color: 'lightcoral' }}> Đăng nhập</Link>
            </Form.Item>
        </Form>
    );
}
export default Signup;