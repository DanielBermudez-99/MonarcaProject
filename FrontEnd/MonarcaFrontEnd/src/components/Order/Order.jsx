import React, { useEffect, useState} from "react";
import { Card, CardBody, CardFooter, CardHeader, Image, Button } from "@nextui-org/react";
import { Textarea, Chip } from "@nextui-org/react";
import { useNavigate } from 'react-router-dom';
import api from '../Auth/api.js';
import { jwtDecode } from 'jwt-decode';

export default function Cart() {
  const [cartItems, setCartItems] = useState([]);
  const [userId, setUserId] = useState(null);
  const navigate = useNavigate();

  useEffect(() => {
    const token = localStorage.getItem('jwt');
    if (token) {
      const decodedJwt = jwtDecode(token);
      const userId = parseInt(decodedJwt.userId, 10);
      setUserId(userId);
    }
  }, []);

  const handleRemoveFromCart = (productId) => {
    api.delete(`/cart/${userId}/remove/${productId}`)
      .then(response => {
        setCartItems(cartItems.filter(item => item.productInfo.id !== productId));
      })
      .catch(error => {
        console.error('Error al eliminar el producto del carrito:', error);
      });
  };

  useEffect(() => {
    if (userId) {
      api.get(`/cart/${userId}`)
        .then(response => {
          setCartItems(response.data);
        })
        .catch(error => {
          console.error('Error al obtener los productos del carrito:', error);
        });
    }
  }, [userId]);

  const handleUpdateQuantity = (productId, quantity) => {
    const product = cartItems.find(item => item.productInfo.id === productId);
    if (product && quantity > product.productInfo.stock) {
      quantity = product.productInfo.stock;
      window.alert('No hay suficiente stock de este producto.');
    }

    api.put(`/cart/${userId}/update/${productId}`, { quantity })
      .then(response => {
        setCartItems(cartItems.map(item => item.productInfo.id === productId ? {...item, quantity} : item));
      })
      .catch(error => {
        console.error('Error al actualizar la cantidad del producto en el carrito:', error);
      });
  };

  const createOrder = (userId) => {
    const total = cartItems.reduce((total, item) => total + item.productInfo.price * item.quantity, 0);

    api.post(`/orders/create/${userId}`, { total_price: total })
      .then(response => {
        window.alert('Orden creada exitosamente');
        console.log('Orden creada:', response.data);
        navigate('/order');
      })
      .catch(error => {
        console.error('Error al crear la orden:', error);
      });
  };
  return (
      <div className="flex h-screen flex-col w-full justify-start items-center">
      <Card className="max-w-xl w-full fixed">
        <CardBody>
          <div className="mt-4">
            <b className="text-lg font-bold">Resumen de la orden</b>
            {cartItems.map((item, index) => (
              <div key={index} className="flex justify-between">
                <span>{item.productInfo.name}</span>
                <b>x{item.quantity}</b>
              </div>
            ))}
          </div>
          <br />
          <div className="flex justify-between items-center">
            <b>Total</b>
            <b className="text-foreground-900">${cartItems.reduce((total, item) => total + item.productInfo.price * item.quantity, 0,)}</b>
          </div>
          <br />
          <div className="flex justify-center items-center gap-4">
            <Button color="primary" onClick={() => createOrder(userId)}>
              <b>PAGAR</b>
            </Button>
          </div>
        </CardBody>
      </Card>
    </div>
  );
}