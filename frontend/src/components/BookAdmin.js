import { Button, Col, DatePicker, Form, Input, Modal, Row, Select, Spin, message } from 'antd';
import dayjs from 'dayjs';
import { useEffect, useState } from 'react';
import { useNavigate, useParams } from 'react-router-dom';
import ImageUploader from './UploadImg';


function BookAdmin() {
    const { confirm } = Modal;
    const params = useParams();
    const idbook = params.idbook;
    const isEdit = idbook > 0
    const [editing, setEditing] = useState(!isEdit)
    const [imageUrl, setImageUrl] = useState()
    const navigate = useNavigate()
    const [form] = Form.useForm();
    const [loading, setLoading] = useState(true)
    const handleImageUploadComplete = (imageUrl) => {
        form.setFieldsValue({ imageUrl });
    };
    const onFinish = (values) => {
        console.log("image", values)

        console.log("imageurl state", form.getFieldValue("imageUrl"))
        const payload = {
            author: values.author,
            category: values.category,
            description: values.description,
            title: values.title,
            numberpage: values.numberpage,
            issuedate: values.issuedate.format("YYYY-MM-DD"),
            imageurl: form.getFieldValue("imageUrl") || imageUrl
        }
        // console.log("payload", payload)
        confirm({
            title: "Bạn có chắc muốn thay đổi không?", onOk: () => {
                const url = isEdit ? `http://localhost:8080/book/${idbook}` : "http://localhost:8080/book"
                fetch(url, {
                    method: isEdit ? "PUT" : "POST",
                    mode: "cors",
                    body: JSON.stringify(payload),
                    headers: {
                        'Content-Type': 'application/json; charset=UTF-8',
                    }
                })
                    // .then((response) => response.json())
                    .then((data) => {
                        if (data.status === 200) {
                            message.success("Đã lưu thay đổi!")
                            navigate({ pathname: "/books" })
                        } else {
                            message.warning("Sách này đã tồn tại");
                        }

                    })
                    .catch((err) => {
                        console.log(err)
                    });
            }, onCancel: () => { }
        });
    };
    useEffect(() => {
        if (isEdit)
            fetch(`http://localhost:8080/book/${idbook}`)
                .then((response) => response.json())
                .then(async (data) => {
                    setImageUrl(data.imageurl)
                    form.setFieldsValue({
                        ...data, issuedate: dayjs(data.issuedate, "YYYY-MM-DD"), category: +data.idcategory, imageurl: []
                    })
                    setLoading(false)
                })
                .catch((err) => console.log(err));
    }, []);

    return (
        <Form
            form={form}
            name="basic"
            labelCol={{ span: 24 }}
            wrapperCol={{ span: 24 }}
            style={{ width: "100%" }}
            initialValues={true}
            onFinish={onFinish}
            autoComplete="off"
        >
            <h1 style={{ paddingTop: "10px", textAlign: "center" }}>Sách</h1> <br />
            {loading && isEdit && <Spin tip="Loading" size="large">
                <div className="content" />
            </Spin>}
            <Row style={{ paddingLeft: "12%", paddingRight: "12%" }}>
                <Col xs={24} sm={24} md={12} lg={12}>
                    <Row>
                        <Col span={12}>
                            <Form.Item
                                label="Tiêu đề"
                                name="title"
                                rules={[{ required: true, message: 'Không được bỏ trống tiêu đề!' }]}
                                style={{ paddingRight: "5px" }}
                            >
                                <Input disabled={!editing} />
                            </Form.Item>
                        </Col>
                        <Col span={12}>
                            <Form.Item
                                label="Tác giả"
                                name="author"
                                rules={[{ required: true, message: 'Không được bỏ trống tác giả!' }]}
                            >
                                <Input disabled={!editing} />
                            </Form.Item>
                        </Col>
                    </Row>
                    <Row>
                        <Col span={24}>
                            <Form.Item
                                label="Mô tả sách"
                                name="description"
                                rules={[{ required: false }]}
                            >
                                <Input.TextArea
                                    style={{ width: "100%" }}
                                    disabled={!editing}
                                    showCount
                                    maxLength={1000} />
                            </Form.Item>
                        </Col>

                    </Row>
                    <Row>
                        <Col span={12}>
                            <Form.Item
                                label="Ngày phát hành"
                                name="issuedate"
                                rules={[{ required: true, message: 'Không được bỏ trống ngày phát hành!' }]}
                            >
                                <DatePicker disabled={!editing} />
                            </Form.Item>
                        </Col>
                        <Col span={12}>
                            <Form.Item
                                label="Số trang"
                                name="numberpage"
                                rules={[{ required: false }]}
                            >
                                <Input type='number' disabled={!editing} />
                            </Form.Item>
                        </Col>
                    </Row>
                    <Row style={{ marginTop: "25px" }}>
                        <Col span={12}>
                            <Form.Item
                                label="Thể loại"
                                name="category"
                                rules={[{ required: false }]}
                            >
                                <Select
                                    style={{ width: "100%" }}
                                    disabled={!editing}
                                    options={[
                                        { value: 1, label: "Văn học" },
                                        { value: 2, label: "Kinh tế" },
                                        { value: 3, label: "Thiếu nhi" },
                                        { value: 4, label: "Giáo khoa-Tham khảo" },
                                        { value: 5, label: "Kỹ năng sống" },
                                        { value: 6, label: "Không xác định" }
                                    ]}
                                />

                            </Form.Item>
                        </Col>
                    </Row>
                </Col>
                <Col xs={24} sm={24} md={12} lg={12} style={{ textAlign: "center", paddingLeft: "20px" }}>
                    <ImageUploader imageUrl={imageUrl} onUploadComplete={handleImageUploadComplete} disabled={!editing} />
                </Col>
            </Row>
            <hr style={{ border: "2px solid lightcoral" }} />
            <Form.Item wrapperCol={{ offset: 8, span: 16 }}>
                <Button
                    style={{ marginLeft: "65%", background: "lightcoral", color: "white" }}
                    htmlType={(editing && isEdit) || (editing && !isEdit) ? "submit" : "button"} // khi dang o man edit 1 sach, va editing = false, tuc la chua bam nut Edit thi type = "button"
                    onClick={(e) => {
                        if (isEdit && !editing) {
                            e.preventDefault()
                            setEditing(true)
                        } // khi dang o man edit 1 sach va chua bam nut edit, thi khi click nut nay => chuyen sang trang thai editing
                    }}
                >
                    {isEdit ? editing ? "Save" : "Edit" : "Add"}
                    {/* khi o man edit 1 sach va da bam Edit thi hien Save, neu chua bam thi hien Edit, con lai hien Add */}
                </Button>
            </Form.Item>
        </Form>
    )

}
export default BookAdmin;