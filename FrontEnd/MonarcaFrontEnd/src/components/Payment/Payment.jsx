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

  const payOrder = (payId, orderId) => {
  api.post(`/pay/${payId}/order/${orderId}`)
    .then(response => {
      window.alert('Pago exitoso');
      console.log('Pago exitoso:', response.data);
      // Aquí puedes redirigir al usuario a una página de éxito, o actualizar el estado del componente para reflejar que el pago fue exitoso
    })
    .catch(error => {
      console.error('Error al realizar el pago:', error);
      // Aquí puedes manejar el error, por ejemplo mostrando un mensaje al usuario
    });
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
                <Input size="sm"></Input>
                <Input size="sm"></Input>
            </CardHeader>
            <CardHeader className="flex  gap-4 w-30">
                <Input size="sm" className="w-10"></Input> 
                <Input size="sm" className="w-10"></Input> 
                <MasterCard/>
                <Visa/>
            </CardHeader>
            <div className="flex justify-center items-center">
              <Button color="primary" className="" onClick={() => payOrder(payId, orderId)}>
                <b>PAGAR</b>
              </Button>
            </div>
            </Card>
            </div>
            
    );
}