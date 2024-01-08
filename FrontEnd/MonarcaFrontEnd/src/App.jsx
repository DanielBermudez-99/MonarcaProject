import * as React from "react";
import { BrowserRouter as Router, Route, Routes } from 'react-router-dom';
import NavBar from "./components/Navbar/NavBar.jsx";
import Product from "./components/Products/Product.jsx";
import Category from "./components/Category/Category.jsx"; // Importa el componente Category
import Login from "./components/Auth/Login.jsx";
import {NextUIProvider} from "@nextui-org/react";

function App() {
  return (
    <NextUIProvider>
      <Router>
        <NavBar/>
        <Routes>
          <Route path="/auth/login" element={<Login/>} />
          <Route path="/product/list" element={<Product/>} />
          <Route path="/category/:categoryId" element={<Category/>} /> 
          
        </Routes>
      </Router>
    </NextUIProvider>
  );
}

export default App;