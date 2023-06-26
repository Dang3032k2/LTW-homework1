import { Button, Col, message, Image, Input, Modal, Row, Rate, Spin } from 'antd';
import { useEffect, useState } from 'react';
import { LoadingOutlined } from '@ant-design/icons';
import { useNavigate, useParams } from 'react-router-dom';
import Feedbacks from './Feedbacks';

function BookUser({ isBookOrd = false }) {
    const { confirm } = Modal;
    const params = useParams();
    const idbook = params.idbook;
    console.log(idbook)
    const [number, setNumber] = useState(1)
    const [book, setBook] = useState({})
    const navigate = useNavigate()
    const user = JSON.parse(localStorage.getItem('user'))
    const [loading, setLoading] = useState(true)
    const onClick = () => {
        if (user == null) {
            message.warning("Vui lòng đăng nhập để mua hàng!")
        }
        else {
            const payload = {
                idbook: book.idbook,
                iduser: user.iduser,
                number: number,
                cancel: 0
            }
            confirm({
                title: "Bạn đồng ý đặt hàng chứ?", onOk: () => {
                    fetch("http://localhost:8080/order", {
                        method: "POST",
                        mode: "cors",
                        body: JSON.stringify(payload),
                        headers: {
                            'Content-Type': 'application/json; charset=UTF-8',
                        }
                    })
                        // .then((response) => response.json())
                        .then((data) => {
                            if (data.status === 200) {
                                message.success("Đặt hàng thành công!")
                                navigate({ pathname: "/booksuser" })
                            }
                        })
                        .catch((err) => {
                            console.log(err)
                        });
                }, onCancel: () => { }
            });
        }

    };
    useEffect(() => {
        fetch(`http://localhost:8080/book/${idbook}`)
            .then(response => response.json())
            .then(data => {
                setBook(data)
                setLoading(false)
            })
            .catch(err => console.log(err))
    }, []);

    return (
        <div style={{ marginTop: "5%", paddingLeft: "7%", paddingRight: "10%" }}>
            {loading &&
                <Spin tip="Loading" size="large">
                    <div className="content" />
                </Spin>}
            <Row className="ant-row-responsive">
                <Col xs={24} sm={24} md={12} lg={8} style={{ marginLeft: "2%" }}>
                    <Image src={book.imageurl} width={250} />
                </Col>
                <Col xs={24} sm={24} md={12} lg={8}>
                    <Row >
                        <h1>{book.title}</h1>
                    </Row>
                    <Row style={{ fontSize: '120%' }}>
                        <b>Tác giả </b>: {book.author}
                    </Row>
                    <Row style={{ fontSize: '120%' }}>
                        <b>Thể loại </b>: {book.category}
                    </Row>
                    <Row style={{ fontSize: '120%' }}>
                        <b>Số trang </b>: {book.numberpage}
                    </Row>
                    <Row style={{ fontSize: '120%' }}>
                        <b>Mô tả </b>: {book.description}
                    </Row>
                    {!isBookOrd && <Row style={{ fontSize: '120%', marginTop: "15px" }}>
                        <b>Số lượng:</b>
                        <Input
                            type='number' defaultValue={1}
                            style={{ width: "15%", marginLeft: "15px" }}
                            min={1}
                            onChange={event => setNumber(event.target.value)}
                        />
                    </Row>}
                </Col>
            </Row>
            {!isBookOrd &&
                <Button
                    type='primary'
                    style={{ background: "lightcoral", marginLeft: "80%" }}
                    onClick={onClick}
                >
                    Đặt hàng
                </Button>}
            {!isBookOrd && <div style={{ margin: "40px 20px" }}>
                <Row >
                    <h4>Đánh giá sản phẩm</h4>
                </Row>
                <Row style={{ marginTop: "20px" }}>
                    <Col span={24}>
                        <Feedbacks idbook={idbook} />
                    </Col>

                </Row>
            </div>}
        </div>
    )

}
export default BookUser;