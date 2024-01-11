import React, { useEffect, useState } from "react";
import { Card, CardBody, CardFooter, CardHeader, Image, Button } from "@nextui-org/react";
import { Textarea, Chip } from "@nextui-org/react";
import api from '../Auth/api.js';
import { jwtDecode } from 'jwt-decode';

export default function Cart() {
  const [cartItems, setCartItems] = useState([]);
  const [userId, setUserId] = useState(null);

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

  return (
    <div className="h-screen flex flex-col gap-4">
      <div className="flex flex-wrap gap-4 justify-center items-start">
        {cartItems.map((cartItem, index) => (
          <Card key={index} className="max-w-xs flex justify-center items-center" shadow="sm" isPressable onPress={() => console.log("item pressed")}>
            <CardBody className="overflow-visible p-0 w-13">
              <div className="flex justify-between items-center">
                <b>{cartItem.productInfo.name}</b>
                <Button isIconOnly radius="none" color="foreground" aria-label="Remove" onClick={() => handleRemoveFromCart(cartItem.productInfo.id)}>
                  X
                </Button>
              </div>
              <Image
                shadow="sm"
                radius="lg"
                width="100%"
                alt={cartItem.productInfo.name}
                className="w-full object-cover h-[140px]"
                src={cartItem.productInfo.image_url}
                isZoomed
                isBlurred
              />
            </CardBody>
            <CardFooter className="text-small justify-between flex flex-col gap-4">
              <div className="flex justify-center items-center gap-1">
                <Chip color="primary">{cartItem.productInfo.size}</Chip>
              </div>
              <Textarea
                isDisabled
                label="Descripción"
                labelPlacement="outside"
                placeholder="Enter your description"
                defaultValue={cartItem.productInfo.description}
              />
            </CardFooter>
            <div className="flex flex-col justify-center items-center gap-4">
              <div className="flex gap-2">
                <Button isIconOnly color="primary" aria-label="Remove" onClick={() => {
                  if (cartItem.quantity > 1) {
                      handleUpdateQuantity(cartItem.productInfo.id, cartItem.quantity - 1);
                      }
                  }}>
                  -
                </Button>
                <Button isIconOnly color="primary" aria-label="Quantity">
                  {cartItem.quantity}
                </Button>
                <Button isIconOnly color="primary" aria-label="Add" onClick={() => handleUpdateQuantity(cartItem.productInfo.id, cartItem.quantity + 1)}>
                  +
                </Button>
              </div>
              <div>
                <p className="text-foreground-900">${cartItem.productInfo.price}</p>
              </div>
            </div>
          </Card>
        ))}
      </div>
      <div className="flex flex-col items-center">
      <Card className="max-w-xl w-full">
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
            <Button color="primary">
              PAGAR
            </Button>
          </div>
        </CardBody>
      </Card>
    </div>
      </div>
  );
}