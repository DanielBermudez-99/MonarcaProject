import React, { useState, useEffect } from "react";
import {
  Navbar,
  NavbarBrand,
  NavbarMenuToggle,
  NavbarMenuItem,
  NavbarMenu,
  NavbarContent,
  NavbarItem,
  Link,
  Button
} from "@nextui-org/react";
import { CartLogo } from "./CartLogo.jsx";
import { useNavigate } from 'react-router-dom';
import api from '../Auth/api.js'; // Importa la instancia de axios con el interceptor
import { MonarcaLogo } from "./MonarcaLogo.jsx";

export default function App() {
  const [isMenuOpen, setIsMenuOpen] = useState(false);
  const [isAuthenticated, setIsAuthenticated] = useState(false);
  const [categories, setCategories] = useState([]); // Crea un estado para almacenar las categorías
  const navigate = useNavigate();
  

  useEffect(() => {
    const token = localStorage.getItem('jwt');
    if (token) {
      setIsAuthenticated(true);
    }

    api.get('/category/list') // Hace una solicitud GET a la API de categorías
        .then(response => {
          setCategories(response.data); // Actualiza el estado con los datos de la respuesta
        })
        .catch(error => {
          console.error('Error al obtener las categorías:', error);
        });
  }, []);

  const handleLogout = () => {
    localStorage.removeItem('jwt');
    setIsAuthenticated(false);
    window.location.href = '/auth/login';
  };

  const handleOrderClick = () => {
    navigate('/order');
  };

  const handleOrderClickBuy = () => {
    navigate('/buy');
  };

  const menuItems = [
    "Mis Compras",
    "Mis Ordenes", 
    "Pqr",
    "Log Out",
  ];

  return (
    <Navbar shouldHideOnScroll onMenuOpenChange={setIsMenuOpen}>
      <NavbarContent>
        <NavbarMenuToggle
          aria-label={isMenuOpen ? "Close menu" : "Open menu"}
          className="md:hidden"
        />
        <NavbarBrand>
          <Link isActive color="foreground" size="lg" href="http://localhost:5173/product/list"><MonarcaLogo/></Link>
        </NavbarBrand>
      </NavbarContent>

      <NavbarContent className="md:flex  md:gap-4" justify="center">
        {categories.map((category, index) => (
          <NavbarItem isActive key={index}>
            <Link color="foreground"  href="#" onClick={() => navigate(`/category/${category.id}`)}>
              {category.name}
            </Link>
          </NavbarItem>
        ))}
      </NavbarContent>

      <NavbarContent  justify="end" className="md:justify-end">
        {!isAuthenticated && (
          <>
            <NavbarItem  className="hidden lg:flex">
              <Link href="">Login</Link>
            </NavbarItem>
            <NavbarItem>
              <Button as={Link} color="primary" href="http://localhost:5173/auth/login" variant="flat">
                Registrarse
              </Button>
            </NavbarItem>
          </>
        )}
      {isAuthenticated && (
      <div className="flex gap-4 items-center">
        <Button isIconOnly  color="foreground" as={Link} href="http://localhost:5173/cart" aria-label="Add">
          <CartLogo />
        </Button>
        </div>
      )}
      </NavbarContent>

      <NavbarMenu>
        {menuItems.map((item, index) => (
          <NavbarMenuItem key={`${item}-${index}`}>
            <Link
              color={
                index === 2
                  ? "primary"
                  : index === menuItems.length - 1
                  ? "danger"
                  : "foreground"
              }
              className="w-full"
              href="#"
              size="lg"
              onClick={() => {
                if (item === "Log Out") {
                  handleLogout();
                } else if (item === "Mis Ordenes") {
                  handleOrderClick();
                  setIsMenuOpen (false);
                }else if (item === "Mis Compras") {
                  handleOrderClickBuy();
                  setIsMenuOpen (false);
                }
              }}
            >
              {item}
            </Link>
          </NavbarMenuItem>
        ))}
      </NavbarMenu>
    </Navbar>
  );
}