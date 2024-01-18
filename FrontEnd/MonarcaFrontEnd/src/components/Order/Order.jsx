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
      api.get(`/payment/user/${userId}`)
        .then(response => {
          if (response.data && response.data.length > 0) {
            const paymentId = response.data[0].id;
            console.log('Id traído con éxito', paymentId);
            localStorage.setItem('paymentId', paymentId);
          } else {
            console.log('No se encontraron métodos de pago para este usuario');
          }
        })

      api.get(`/orders/user/${userId}/pending`)
  .then(response => {
    if (response.data && response.data.length > 0) {
      setPendingOrders(response.data);
      console.log(response.data[0].order.status);
      localStorage.setItem('orderId', response.data[0].order.id);
      console.log('OrderId traído con éxito', response.data[0].order.id);
    } else {
      console.log('No se encontraron órdenes pendientes para este usuario');
    }
  })
  .catch(error => {
    console.error('Error al obtener las órdenes pendientes:', error);
  });
    }
  }, [userId]);

  const redirection = (userId) => {
    navigate('/payment');
  };

  const handleRemoveOrder = async (orderId) => {
    try {
      const response = await api.delete(`/orders/delete/${orderId}`);
      console.log('Orden eliminada con éxito:', response.data);
      window.location.reload();
    } catch (error) {
      console.error('Error al eliminar la orden:', error);
    }
  }
  return (
    <div className="flex gap-4 h-screen flex-col w-full justify-start items-center">
      {pendingOrders.map((order, index) => (
        <Card key={index} className="max-w-xl w-full mb-4">
          <CardBody>
            <div className="mt-4">
              <div className="flex justify-between items-center">
                <b className="text-lg font-bold">Orden #{order.order.id}</b>
              <Button className="flex justify-end" isIconOnly radius="none" color="foreground" aria-label="Remove" onClick={() => handleRemoveOrder(order.order.id)}>
                  <b>X</b>
                </Button>
              </div>
              {order.cartItems.map((item, idx) => (
                <div key={idx} className="flex justify-between">
                  <span>{item.productName}</span>
                  <b>{item.quantity}</b>
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
              <Button color="primary" onClick={() => redirection(userId)}>
                <b>PAGAR</b>
              </Button>
            </div>
          </CardBody>
        </Card>
      ))}
    </div>
  );
}
