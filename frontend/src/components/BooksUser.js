import React, { useEffect, useState } from "react";
import { AppstoreOutlined } from '@ant-design/icons';
import { Link } from "react-router-dom";
import { Button, Carousel, Col, Input, Row, Select, Spin } from 'antd'
import './Book.css';
function BooksUser(props) {
    const [books, setBooks] = useState([]);
    const [keyword, setKeyword] = useState('')
    const [loading, setLoading] = useState(true)
    const [isShow, setIsShow] = useState(true)
    useEffect(() => {
        fetch("http://localhost:8080/books")
            .then(response => response.json())
            .then(data => {
                setBooks(data)
                setLoading(false)
            })
            .catch(err => console.log(err))
    }, []);
    const handleClick = () => {
        if (keyword == "") setIsShow(true)
        else setIsShow(false)
        fetch(`http://localhost:8080/books/search?keyword=${keyword}`)
            .then(response => response.json())
            .then(data => {
                setBooks(data)
            })
            .catch(err => console.log(err))
    }
    const onChange = (value) => {
        if (value == 0) {
            setIsShow(true)
            fetch("http://localhost:8080/books")
                .then(response => response.json())
                .then(data => {
                    setBooks(data)
                    setLoading(false)
                })
                .catch(err => console.log(err))
        }
        else {
            setIsShow(false)
            fetch(`http://localhost:8080/books/${value}`)
                .then(response => response.json())
                .then(data => {
                    setBooks(data)
                    setLoading(false)
                })
                .catch(err => console.log(err))
        }
    };
    return (
        <div style={{ padding: "3% 8%" }}>
            <Row style={{ display: "flex", margin: "0% 0% 2% 0%" }}>
                <Col xs={24} sm={12} md={8} lg={6} style={{ display: "flex" }}>
                    <AppstoreOutlined style={{ fontSize: "180%", padding: "0 10px", color: "lightcoral" }} />
                    <Select
                        showSearch
                        placeholder="Chọn thể loại"
                        optionFilterProp="children"
                        onChange={onChange}
                        options={[
                            { value: 0, label: "Tất cả thể loại" },
                            { value: 1, label: "Văn học" },
                            { value: 2, label: "Kinh tế" },
                            { value: 3, label: "Thiếu nhi" },
                            { value: 4, label: "Giáo khoa-Tham khảo" },
                            { value: 5, label: "Kỹ năng sống" },
                        ]}
                    />
                </Col>
                <Col xs={0} sm={0} md={8} lg={6}></Col>
                <Col xs={0} sm={0} md={0} lg={0}></Col>
                <Col xs={24} sm={12} md={8} lg={12} style={{ display: "flex" }}>
                    <Input value={keyword} onChange={event => setKeyword(event.target.value)} />
                    <Button type="primary" onClick={handleClick} style={{ height: "40px", background: "lightcoral", marginLeft: "10px" }}>Tìm kiếm</Button>
                </Col>

            </Row>
            {loading && <Spin tip="Loading" size="large">
                <div className="content" />
            </Spin>}
            {isShow && <Carousel autoplay>
                <div >
                    <img
                        src="https://firebasestorage.googleapis.com/v0/b/bookstore-5336d.appspot.com/o/promo-banner-bookstore-bookshop-library-260nw-1976145590.jpg?alt=media&token=e0c2e3c9-d81b-46ae-94f5-b673ef4aeaa5"
                        style={{ objectFit: "cover", height: "100%", width: "100%" }}
                    />
                </div>
                <div >
                    <img
                        src="https://firebasestorage.googleapis.com/v0/b/bookstore-5336d.appspot.com/o/love-what-you-read-banner.jpg?alt=media&token=70d513e9-8c8b-47fc-a7e3-8d89a5af8ace"
                        style={{ objectFit: "cover", height: "100%", width: "100%" }}
                    />
                </div>
                <div >
                    <img
                        src="https://firebasestorage.googleapis.com/v0/b/bookstore-5336d.appspot.com/o/banner.jpg?alt=media&token=d8aedebe-394a-4fe8-8a02-f12bc69e81ca"
                        style={{ objectFit: "cover", height: "100%", width: "100%" }}
                    />
                </div>
                <div >
                    <img
                        src="https://firebasestorage.googleapis.com/v0/b/bookstore-5336d.appspot.com/o/b2.jpg?alt=media&token=6fd5d124-ca2e-49b8-a96e-7f20acd9dc11"
                        style={{ objectFit: "cover", height: "100%", width: "100%" }}
                    />
                </div>
            </Carousel>}
            <Row gutter={[16, 16]} align="stretch" className="ant-row-responsive">
                {books.map((book) => (
                    <Col key={book.idbook} className="book-wrapper" xs={8} sm={8} md={6} lg={4}>
                        <Link to={`/bookuser/${book.idbook}`} style={{ textDecoration: "none", display: "flex", flexDirection: "column", justifyContent: "space-between" }} className="image-container" >
                            <img src={book.imageurl} className="image, image-wrapper" />
                            <div style={{ fontSize: "1.2vw", height: "7vw", marginTop: "10px", color: "black" }} className="image-description">
                                <b>{book.title}</b> <br />
                                {book.author}
                            </div>
                        </Link>


                    </Col>
                ))}

            </Row>
        </div>
    );
}
export default BooksUser;