import React, { useEffect, useState} from "react";
import { Card, CardBody, CardFooter, CardHeader, Image, Button } from "@nextui-org/react";
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

  const createOrder = (userId) => {
  const total = cartItems.reduce((total, item) => total + item.productInfo.price * item.quantity, 0);

  api.post(`/orders/create/${userId}`, { total_price: total })
    .then(response => {
      const orderId = response.data.id;
      const price = response.data.total_price;
      const orderItems = cartItems.map(item => ({
        product_id: item.productInfo.id,
        quantity: item.quantity,
        price: item.productInfo.price
      }));
      window.alert('Orden creada exitosamente');
      console.log('Orden creada:', response.data);
      // Guarda el orderId en el almacenamiento local
      localStorage.setItem('orderId', orderId);
      localStorage.setItem('price', price);
      localStorage.setItem('orderItems', JSON.stringify(orderItems));
      console.log('orderId:', orderId);
      // navigate('/payment'); // Navega al componente de pago
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
          <br/>
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