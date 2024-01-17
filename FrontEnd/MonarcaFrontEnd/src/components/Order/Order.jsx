import React, { useEffect, useState } from "react";
import { Card, CardBody, Button } from "@nextui-org/react";
import { useNavigate } from 'react-router-dom';
import api from '../Auth/api.js';
import { jwtDecode } from 'jwt-decode';

export default function Cart() {
  const [cartItems, setCartItems] = useState([]);
  const [pendingOrders, setPendingOrders] = useState([]);
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

  useEffect(() => {
    if (userId) {
      api.get(`/cart/${userId}`)
        .then(response => {
          setCartItems(response.data);
        })
        .catch(error => {
          console.error('Error al obtener los productos del carrito:', error);
        });

      api.get(`/orders/user/${userId}/pending`)
        .then(response => {
          setPendingOrders(response.data);
        })
        .catch(error => {
          console.error('Error al obtener las órdenes pendientes:', error);
        });
    }
  }, [userId]);

  const createOrder = (userId) => {
    // Implementa la lógica para crear la orden y realizar el pago
    console.log("Orden creada y pagada con éxito");
  };

  return (
    <div className="flex gap-4 h-screen flex-col w-full justify-start items-center">
      {pendingOrders.map((order, index) => (
        <Card key={index} className="max-w-xl w-full mb-4">
          <CardBody>
            <div className="mt-4">
              <div className="flex justify-between items-center">
                <b className="text-lg font-bold">Orden #{order.order.id}</b>
              <Button isIconOnly radius="none" color="foreground" aria-label="Remove" onClick={() => handleRemoveFromCart(cartItem.productInfo.id)}>
                  X
                </Button>
              </div>
              {order.cartItems.map((item, idx) => (
                <div key={idx} className="flex justify-between">
                  <span>{item.productName}</span>
                  <b>x{item.quantity}</b>
                </div>
              ))}
            </div>
            <br />
            <div className="flex justify-between items-center">
              <b>Total</b>
              <b className="text-foreground-900">${order.order.total}</b>
            </div>
            <br />
            <div className="flex justify-center items-center gap-4">
              <Button color="primary" onClick={() => createOrder(userId)}>
                <b>PAGAR</b>
              </Button>
            </div>
          </CardBody>
        </Card>
      ))}
    </div>
  );
}
