import React, { useEffect, useState } from "react";
import {Card, CardBody, CardFooter, Image, Chip} from "@nextui-org/react";
import {Button} from "@nextui-org/react";
import {Textarea} from "@nextui-org/react";
import api from '../Auth/api.js'; // Importa la instancia de axios con el interceptor
import { useParams } from 'react-router-dom'; // Importa el hook useParams

export default function Category() {
  const [products, setProducts] = useState([]); // Crea un estado para almacenar los productos
  const { categoryId } = useParams(); // Obtiene el parámetro de ruta categoryId

  useEffect(() => {
    api.get(`/product/list/category/${categoryId}`) // Hace una solicitud GET a la API de productos por categoría
        .then(response => {
          setProducts(response.data); // Actualiza el estado con los datos de la respuesta
        })
        .catch(error => {
          console.error('Error al obtener los productos:', error);
        });
  }, [categoryId]); // El array vacío significa que useEffect se ejecutará solo una vez, cuando el componente se monte

  // Resto del código...

  return (
      <div className=" flex flex-wrap gap-4  justify-center items-start">

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
                  <Chip color="primary" >{product.size}</Chip>
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
                <p className="text-default-500">${product.price}</p>
                <Button color="primary" >
                  COMPRAR
                </Button>
              </div>
            </Card>
        ))}
      </div>
  );
}