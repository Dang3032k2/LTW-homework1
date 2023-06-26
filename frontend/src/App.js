import logo from './logo.svg';
import './App.css';
import { Route, Routes, useLocation, useNavigate } from 'react-router-dom';
import Signup from './components/Signup';
import Login from './components/Login';
import BooksAdmin from './components/BooksAdmin';
import BookAdmin from './components/BookAdmin';
import BooksUser from './components/BooksUser';
import TopNavbar from './components/Narbar';
import BookUser from './components/BookUser';
import { useEffect, useState } from 'react';
import Orders from './components/Orders';
function App() {
  const navigate = useNavigate();
  const location = useLocation();
  useEffect(() => {
    const user = (JSON.parse(localStorage.getItem('user')));
    if (user != null) {
      if (user.admin == true) {
        if (!location.pathname.includes('books/'))
          navigate({ pathname: '/books' })
      }
      else {
        if (!location.pathname.includes('bookuser') && !location.pathname.includes('orders'))
          navigate({ pathname: '/booksuser' })

      }

    }
  }, []);
  return (
    <div className="App">
      <TopNavbar />
      <Routes>
        <Route path='signup' element={<Signup />} />
        <Route path='dangbook' element={<Login />} />
        <Route path='login' element={<Login />} />
        <Route path='books' element={<BooksAdmin />} />
        <Route path='books/:idbook' element={<BookAdmin />} />
        <Route path='books/-1' element={<BookAdmin />} />
        <Route path='booksuser' element={<BooksUser />} />
        <Route path='bookuser/:idbook' element={<BookUser />} />
        <Route path='orders' element={<Orders />} />
        <Route path='orders/:idbook' element={<BookUser isBookOrd={true} />} />
      </Routes>
    </div>
  );
}

export default App;
