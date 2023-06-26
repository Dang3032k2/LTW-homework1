import { Button, Form, Input, message } from "antd";
import React from "react";
import { Link, useNavigate } from "react-router-dom";
import { generateSHA512Hash } from "../utils";

function Login() {
    const navigate = useNavigate()
    const onFinish = (values) => {
        fetch("http://localhost:8080/login", {
            method: "POST",
            mode: "cors",
            body: JSON.stringify({ ...values, password: generateSHA512Hash(values.password) }),
            headers: {
                'Content-Type': 'application/json; charset=UTF-8',
            }
        })
            .then((response) => {
                if (response.status === 200) {
                    response.json()
                        .then((data) => {
                            localStorage.setItem('user', JSON.stringify(data));
                            console.log("data", data)

                            if (data.admin == true) {
                                navigate({ pathname: '/books' })
                            }
                            else
                                navigate({ pathname: '/booksuser' })
                        });
                }
                else message.error('Sai tài khoản  hoặc mật khẩu!')
            })
            .catch((err) => {
                console.log(err)
            });
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
            <h1 style={{ textAlign: "center", marginTop: "50px" }}>Đăng nhập</h1>
            <Form.Item
                label="Username"
                name="name"
                rules={[{ required: true, message: 'Vui lòng nhập username!' }]}
            >
                <Input />
            </Form.Item>

            <Form.Item
                label="Password"
                name="password"
                rules={[{ required: true, message: 'Vui lòng nhập mật khẩu!' }]}
            >
                <Input.Password />
            </Form.Item>

            <Form.Item wrapperCol={{ offset: 8, span: 16 }}>
                <Button type="primary" htmlType="submit" style={{ background: "lightcoral" }}>
                    Submit
                </Button>
            </Form.Item>
            <Form.Item wrapperCol={{ offset: 8, span: 16 }}>
                Chưa có tài khoản?
                <Link to={"/signup"} style={{ textDecoration: "none", color: "lightcoral" }}> Đăng kí</Link>
            </Form.Item>
        </Form>
    );
}
export default Login;