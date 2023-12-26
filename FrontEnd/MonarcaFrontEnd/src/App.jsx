import * as React from "react";
// import User from "./components/Users/User.jsx";
import NavBar from "./components/Navbar/NavBar.jsx";
import Product from "./components/Products/Product.jsx";
// import ResetPassword from "./components/Auth/ResetPassword.jsx";
import Login from "./components/Auth/Login.jsx";
import {NextUIProvider} from "@nextui-org/react";


function App() {

  return (
    
    <NextUIProvider>
      <NavBar/>
      {/* <ResetPassword/> */}
      {/* <Login/>   */}
      <Product/> 
    </NextUIProvider>
  );
}
export default App