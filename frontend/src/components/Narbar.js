import { Layout } from 'antd';
import { Link, useLocation } from 'react-router-dom';
import { ShoppingOutlined, HomeOutlined, LoginOutlined, LogoutOutlined } from '@ant-design/icons'
const { Header } = Layout;

const TopNavbar = () => {
    const location = useLocation()
    const handleLogout = () => {
        localStorage.clear()
    }
    const user = JSON.parse(localStorage.getItem('user'))
    const isLoggedIn = (user != null)
    const isHidden = location.pathname.includes("login") || location.pathname.includes("signup")
    if (isHidden) return null
    return (
        <Header style={{ background: 'lightcoral', display: "flex" }}>
            <Link
                to={isLoggedIn && user.admin == true ? 'books' : 'booksuser'}
                style={{ marginLeft: "5%", textDecoration: "none", color: "white" }}
            >
                <HomeOutlined style={{ fontSize: "200%", padding: "10px" }} />
                Trang chủ
            </Link>
            {isLoggedIn && !user.admin &&
                <Link
                    to={'orders'}
                    style={{ margin: "0% 5% 0% 60%", textDecoration: "none", color: "white" }}
                >
                    <ShoppingOutlined style={{ fontSize: "200%", padding: "10px" }} />
                    Đơn hàng
                </Link>}
            <Link
                to={'/login'}
                onClick={handleLogout}
                style={{ marginRight: "5%", textDecoration: "none", color: "white" }}
            >
                {isLoggedIn ?
                    <><LogoutOutlined style={{ fontSize: "200%", padding: "10px" }} />Đăng xuất</>
                    : <><LoginOutlined style={{ fontSize: "200%", padding: "10px" }} />Đăng nhập</>}
            </Link>
        </Header>
    );
};

export default TopNavbar;
