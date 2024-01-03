import * as React from "react";
import { BrowserRouter as Router, Route, Routes } from 'react-router-dom';
import NavBar from "./components/Navbar/NavBar.jsx";
import Product from "./components/Products/Product.jsx";
import Login from "./components/Auth/Login.jsx";
import {NextUIProvider} from "@nextui-org/react";

function App() {
  return (
    <NextUIProvider>
      <Router>
        <NavBar/>
        <Routes>
          <Route path="/login/auth" element={<Login/>} />
          <Route path="/product/list" element={<Product/>} />
          {/* Define las demás rutas aquí... */}
        </Routes>
      </Router>
    </NextUIProvider>
  );
}

export default App;