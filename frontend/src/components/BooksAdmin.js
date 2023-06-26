import React, { useEffect, useState } from "react";
import { Link } from "react-router-dom";
import { Button, Input, Modal, Spin, message } from 'antd'
const { confirm } = Modal;
function BooksAdmin() {
    const [books, setBooks] = useState([]);
    const [keyword, setKeyword] = useState('')
    const user = JSON.parse(localStorage.getItem("user"))
    const isLoggedIn = (user != null && user.admin === true)
    const [loading, setLoading] = useState(true)
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
        fetch(`http://localhost:8080/books/search?keyword=${keyword}`)
            .then(response => response.json())
            .then(data => {
                setBooks(data)
            })
            .catch(err => console.log(err))
    }
    const handleDelete = (idbook) => {
        confirm({
            title: "Bạn có chắc muốn xóa không?", onOk: () => {
                fetch(`http://localhost:8080/book/${idbook}`, {
                    method: "DELETE",
                    mode: "cors",
                    headers: {
                        'Content-Type': 'application/json; charset=ISO-8859-1',
                    }
                })
                    // .then((response) => response.json())
                    .then((data) => {
                        console.log(data)
                        if (data.status === 200) {
                            message.success('Xóa thành công!')
                        }
                        else
                            message.error('Không thể xóa cuốn sách có trong đơn hàng của người dùng!')
                        fetch("http://localhost:8080/books")
                            .then(response => response.json())
                            .then(data => {
                                setBooks(data)

                            })
                            .catch(err => console.log(err))
                    })
                    .catch((err) => console.log(err));
            }
        });
    }

    return (
        <div style={{ padding: "2% 5%" }}>
            <h2 className="text-center">Kho Sách</h2>
            <div style={{ display: "flex", justifyContent: "space-between" }}>
                <div style={{ display: "flex" }}>
                    <Input value={keyword} onChange={event => setKeyword(event.target.value)} />
                    <Button type="primary" onClick={handleClick} style={{ marginLeft: "10px", height: "40px", background: "lightcoral" }}>Tìm kiếm</Button>
                </div>
                {isLoggedIn && <Link to="/books/-1">
                    <Button type="primary" style={{ height: "40px", background: "lightcoral" }}>
                        Thêm sách
                    </Button>
                </Link>}
            </div>
            {loading && <Spin tip="Loading" size="large">
                <div className="content" />
            </Spin>}
            <div className="row" style={{ marginTop: "10px" }}>
                <table className="table table-striped table-bordered">
                    <thead>
                        <tr>
                            <th>Tiêu đề</th>
                            <th>Tác giả</th>
                            <th>Thể loại</th>
                            <th>Ngày phát hành</th>
                            <th>Số trang</th>
                            <th>Số lượng đã bán</th>
                            <th>Tùy chọn</th>
                        </tr>
                    </thead>
                    <tbody>
                        {books.map((book) => (
                            <tr key={book.idbook}>
                                <td>{book.title}</td>
                                <td>{book.author}</td>
                                <td>{book.category}</td>
                                <td>{book.issuedate}</td>
                                <td>{book.numberpage}</td>
                                <td>{book.numbersold}</td>
                                <td>
                                    {isLoggedIn && <Link to={`/books/${book.idbook}`} className="btn btn-success">Xem</Link>}
                                    {isLoggedIn && <button className="btn btn-danger" onClick={() => handleDelete(book.idbook)}>Xóa</button>}
                                </td>
                            </tr>
                        ))}
                    </tbody>
                </table>
            </div>
        </div>
    );
}
export default BooksAdmin;