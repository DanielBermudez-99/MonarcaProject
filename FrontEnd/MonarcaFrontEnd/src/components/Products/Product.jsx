// Importa useState y useEffect de React
import React, { useState, useEffect } from 'react';
// import * as jwt_decode from 'jwt-decode';
import { jwtDecode } from 'jwt-decode';
// Importa los componentes necesarios de @nextui-org/react
import { Card, CardBody, CardFooter, Image, Chip, Button, Textarea, user } from '@nextui-org/react';
// Importa la instancia de axios con el interceptor
import api from '../Auth/api.js';
// Importa el logo del carrito
import { CartLogo } from '../Navbar/CartLogo.jsx';

export default function App() {
  // Crea un estado para almacenar los productos
  const [products, setProducts] = useState([]);
  const [userId, setUserId] = useState(null); // Agrega estado para el ID del usuario

  // Hace una solicitud GET a la API de productos
  useEffect(() => {
    api.get('/product/list')
      .then(response => {
        // Actualiza el estado con los datos de la respuesta
        setProducts(response.data);
      })
      .catch(error => {
        console.error('Error al obtener los productos:', error);
      });
  }, []);

  useEffect(() => {
  const token = localStorage.getItem('jwt');
  if (token) {
    const decodedJwt = jwtDecode(token);
    const userId = parseInt(decodedJwt.userId, 10); // Convierte a número entero

    // También puedes usar el operador +
    // const userId = +decodedJwt.userId;

    setUserId(userId);
  }
}, []);


  // Función para manejar el clic en el botón del carrito
 const handleAddToCart = (productId) => {
  const token = localStorage.getItem('jwt');
  if (!token) {
    console.error('No token found');
    return;
  }

  api.post(`/cart/${userId}/add/${productId}`, { quantity: 1 })  // Envía la cantidad directamente como parámetro en la URL
    .then(response => {
      console.log('Producto agregado al carrito:', response.data);
    })
    .catch(error => {
      console.error('Error al agregar el producto al carrito:', error);
    });
};


  return (
    <div className="flex flex-wrap gap-4 justify-center items-start">
      {products.map((product, index) => (
        <Card className="max-w-xs flex justify-center items-center" shadow="sm" key={index} isPressable onPress={() => console.log("item pressed")}>
          <CardBody className="overflow-visible p-0 w-13">
            <Image
              shadow="sm"
              radius="lg"
              width="100%"
              alt={product.name}
              className="w-full object-cover h-[140px]"
              src={product.image_url}
              isZoomed
              isBlurred
            />
          </CardBody>
          <CardFooter className="text-small justify-between flex flex-col gap-4">
            <div className="flex justify-center items-center gap-4">
              <b>{product.name}</b>
              <Chip color="primary">{product.size}</Chip>
              <Button
                isIconOnly
                color="foreground"
                aria-label="Add"
                onClick={() => {
                  console.log('ID del producto:', product.id);
                  console.log('ID del usuario:', userId);

                  if (userId !== null) {
                    handleAddToCart(product.id);
                  } else {
                    console.error('El ID del usuario es null. Asegúrate de que el token JWT esté presente y sea válido.');
                  }
                }}
              >
                <CartLogo />
              </Button>
            </div>
            <Textarea
              isDisabled
              label="Descripción"
              labelPlacement="outside"
              placeholder="Enter your description"
              defaultValue={product.description}
            />
          </CardFooter>
          <div className="flex justify-center items-center gap-4">
            <p className="text-default-foreground">${product.price}</p>
            <Button color="primary">
              COMPRAR
            </Button>
          </div>
        </Card>
      ))}
    </div>
  );
}
