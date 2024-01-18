import React, { useEffect, useState} from "react";
import { Card, CardBody, CardFooter, CardHeader, Input, Image, Button } from "@nextui-org/react";
import { useNavigate } from 'react-router-dom';
import api from '../Auth/api.js';
import { jwtDecode } from 'jwt-decode';
import { MasterCard} from "./MasterCard.jsx";
import { Visa } from "./Visa.jsx";

export default function App() {
  const [cartItems, setCartItems] = useState([]);
  const [userId, setUserId] = useState(null);
  const [cardNumber, setCardNumber] = useState('');
  const [cardHolderName, setCardHolderName] = useState('');
  const [expiryDate, setExpiryDate] = useState('');
  const [cvv, setCvv] = useState('');
  const [orderId, setOrderId] = useState(null);
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

  // ... código anterior ...

useEffect(() => {
  // Obtén el orderId del almacenamiento local
  const orderId = localStorage.getItem('orderId');
  const orderItems = JSON.parse(localStorage.getItem('orderItems'));

  if (orderId) {
    setOrderId(orderId);
  } else {
    console.error('Error: orderId es null');
  }// asumiendo que el id del pago es 2
}, []);

const payOrder = () => {
  const orderId = localStorage.getItem('orderId');
  const paymentId = localStorage.getItem('paymentId') // asumiendo que el id del pago es 2

  if (paymentId && orderId) {
    api.post(`/payment/${paymentId}/order/${orderId}`, {
      cardNumber,
      cardHolderName,
      expiryDate,
      cvv
    })
      .then(response => {
        console.log('Pago asociado con la orden:', response.data);
        window.alert('Pago exitoso');
        localStorage.removeItem('orderId');
        localStorage.removeItem('userId');
        localStorage.removeItem('paymentId');
        localStorage.removeItem('price');
        localStorage.removeItem('orderItems');
        navigate('/buy');
      })
      .catch(error => {
        console.error('Error al asociar el pago con la orden:', error);
        window.alert('Error al realizar el pago, comprueba tus datos.');
      });
  } else {
    console.error('Error: paymentId u orderId es null');
  }
};

  return (
    <div className=" flex flex-col gap-4 justify-center items-center h-screen">
      <Card className="max-w-md w-full">
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
          <br/>
        </CardBody>
      </Card>
      <Card className="max-w-md w-full">
        <CardHeader className="flex flex-col max-w-sm gap-4">
          <Input size="sm" placeholder="Número De La Tarjeta" onChange={e => setCardNumber(e.target.value)}></Input>
          <Input size="sm" placeholder="Nombre Del Títular" onChange={e => setCardHolderName(e.target.value)}></Input>
        </CardHeader>
        <CardHeader className="flex  gap-4 w-30">
          <Input size="sm" type="Date" className="w-20" onChange={e => setExpiryDate(e.target.value)}></Input>
          <Input size="sm" className="w-20" placeholder="CVV" onChange={e => setCvv(e.target.value)}></Input>
          <MasterCard/>
          <Visa/>
        </CardHeader>
        <div className="flex justify-center items-center">
          <Button color="primary" onClick={payOrder}>
            <b>PAGAR</b>
          </Button>
        </div>
      </Card>
    </div>
  );
}