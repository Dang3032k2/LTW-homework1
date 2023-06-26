import { Avatar, Button, List, Modal, Rate, message } from "antd";
import TextArea from "antd/es/input/TextArea";
import { useEffect, useState } from "react";
import { useNavigate } from "react-router-dom";

function Feedbacks({ idbook }) {
    const [data, setData] = useState([])
    const user = JSON.parse(localStorage.getItem("user"))
    const isLoggedIn = user != null ? true : false
    const { confirm } = Modal
    const navigate = useNavigate();
    const fb = {
        idbook: idbook,
        iduser: isLoggedIn ? user.iduser : 0,
        comment: '',
        star: 5
    }
    const onClick = () => {
        confirm({
            title: "Gửi đánh giá?", onOk: () => {
                fetch(`http://localhost:8080/feedback`, {
                    method: "POST",
                    mode: "cors",
                    body: JSON.stringify(fb),
                    headers: {
                        'Content-Type': 'application/json; charset=UTF-8',
                    }
                })
                    .then((data) => {
                        if (data.status === 200) {
                            message.success("Gửi đánh giá thành công!")
                            fetch(`http://localhost:8080/feedbacks/${idbook}`)
                                .then(response => response.json())
                                .then(data => {
                                    setData(data)
                                })
                                .catch(err => console.log(err))
                        }
                        else
                            message.warning("Bạn đã đánh giá cuốn sách này rồi!")
                        window.location.reload();
                    })
                    .catch((err) => console.log(err));
            }
        });
    }
    useEffect(() => {
        fetch(`http://localhost:8080/feedbacks/${idbook}`)
            .then(response => response.json())
            .then(data => {
                setData(data)
            })
            .catch(err => console.log(err))
    }, [])
    return (
        <div>
            {isLoggedIn && <div>
                <div style={{ display: "flex" }}>
                    <Avatar src={`https://xsgames.co/randomusers/avatar.php?g=pixel&key=${user.iduser}`} />
                    <h4 style={{ fontSize: "14px", marginLeft: "10px" }}>{user.name}</h4>
                </div>

                <Rate
                    style={{ marginLeft: "38%" }}
                    onChange={(e) => fb.star = e}
                    defaultValue={5}
                /> <br />
                <TextArea
                    placeholder="Viết nhận xét tối đa 200 kí tự..."
                    style={{ width: "50%", margin: "5px 5px 20px 0px" }}
                    onChange={(e) => fb.comment = e.target.value}
                    showCount
                    maxLength={200}
                /><br />
                <Button
                    style={{ marginLeft: "45%", background: "lightcoral", color: "white" }}
                    onClick={onClick}
                >
                    Gửi
                </Button>
            </div>}
            <h4 style={{ fontSize: "17px" }}>Tất cả đánh giá</h4>
            <List
                itemLayout="horizontal"
                dataSource={data}
                rowKey={data.idfb}
                renderItem={(item, index) => (
                    <List.Item style={{}}>
                        <List.Item.Meta
                            avatar={<Avatar src={`https://xsgames.co/randomusers/avatar.php?g=pixel&key=${item.iduser}`} />}
                            title={item.name}
                            description={item.comment}
                        /><br />
                        <Rate disabled defaultValue={item.star} style={{ display: "inline" }} /><br />
                    </List.Item>

                )}
            />

        </div>


    )
}
export default Feedbacks;