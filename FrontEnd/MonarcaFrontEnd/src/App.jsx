import * as React from "react";
import { BrowserRouter as Router, Route, Routes } from 'react-router-dom';
import NavBar from "./components/Navbar/NavBar.jsx";
import Product from "./components/Products/Product.jsx";
import Category from "./components/Category/Category.jsx"; // Importa el componente Category
import Login from "./components/Auth/Login.jsx";
import Cart from "./components/Cart/Cart.jsx";
import Order from "./components/Order/Order.jsx";
import Payment from "./components/Payment/Payment.jsx";
import Buy from "./components/Order/Buy.jsx";
import {NextUIProvider} from "@nextui-org/react";

function App() {
  return (
    <NextUIProvider>
      <Router>
        <NavBar/>
        <Routes>
          <Route path="localhost:3000" element={<Login/>} />
          <Route path="/auth/login" element={<Login/>} />
          <Route path="/product/list" element={<Product/>} />
          <Route path="/category/:categoryId" element={<Category/>} /> 
          <Route path="/cart" element={<Cart/>}/>
          <Route path="/order" element={<Order/>}/>
          <Route path="/payment" element={<Payment/>}/>
          <Route path="/buy" element={<Buy/>} />
        </Routes>
      </Router>
    </NextUIProvider>
  );
}

export default App;