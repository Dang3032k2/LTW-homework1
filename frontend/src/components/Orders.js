import { Empty, Modal, Result, Spin, message } from "antd";
import { useEffect, useState } from "react";
import { Link } from "react-router-dom";

function Orders() {
    const [orders, setOrders] = useState([]);
    const { confirm } = Modal;
    const user = JSON.parse(localStorage.getItem("user"))
    const [loading, setLoading] = useState(true)
    const [isNull, setisNull] = useState(false)
    useEffect(() => {
        fetch(`http://localhost:8080/orders/${user.iduser}`)
            .then(response => response.json())
            .then(data => {
                setOrders(data)
                setLoading(false)
                if (orders.length === 0) setisNull(true)
                else setisNull(false)
            })
            .catch(err => console.log(err))
    }, [isNull]);
    const handleCancel = (idorder) => {
        confirm({
            title: "Bạn có chắc muốn hủy không?", onOk: () => {
                fetch(`http://localhost:8080/order/${idorder}`, {
                    method: "PUT",
                    mode: "cors",
                    headers: {
                        'Content-Type': 'application/json; charset=UTF-8',
                    }
                })
                    // .then((response) => response.json())
                    .then((data) => {
                        message.success("Hủy thành công!")
                        fetch(`http://localhost:8080/orders/${user.iduser}`)
                            .then(response => response.json())
                            .then(data => {
                                setOrders(data)
                            })
                            .catch(err => console.log(err))
                    })
                    .catch((err) => console.log(err));
            }
        });
    }
    return (
        <div style={{ padding: "2% 10%" }}>
            <h2 className="text-center">Đơn hàng</h2>
            {loading && <Spin tip="Loading" size="large">
                <div className="content" />
            </Spin>}
            {/* {isNull && <Empty image={Empty.PRESENTED_IMAGE_SIMPLE} />} */}
            {isNull && <Result
                status="500"
                subTitle="Oh... bạn chưa có đơn hàng nào cả! Hãy đặt hàng nhé!"
            />}
            {!isNull && <div className="row" style={{ marginTop: "10px" }}>
                <table className="table table-striped table-bordered">
                    <thead>
                        <tr>
                            <th >Bìa sách</th>
                            <th>Tiêu đề</th>
                            <th>Số lượng</th>
                            <th>Trạng thái</th>
                        </tr>
                    </thead>
                    <tbody>
                        {orders.map((order) => (
                            <tr key={order.idorder}>
                                <td style={{ width: "20%" }}><img src={order.imageurl} style={{ width: "80%" }} /></td>
                                <td>{order.title}</td>
                                <td>{order.number}</td>
                                {/* <td>{order.cancel ? "Đã hủy" : "Đã mua"}</td> */}
                                <td>
                                    {order.cancel ? <p style={{ color: "red" }}>Đã hủy</p>
                                        : <p style={{ color: "green" }}>Đã mua</p>}
                                </td>
                                <td>
                                    <Link to={`${order.idbook}`} className="btn btn-success">Xem</Link>
                                    {!order.cancel && <button className="btn btn-danger" onClick={() => handleCancel(order.idorder)}>Hủy</button>}
                                </td>
                            </tr>
                        ))}
                    </tbody>
                </table>
            </div>}
        </div>
    );
}
export default Orders