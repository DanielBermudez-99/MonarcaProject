import React, { useEffect, useState } from "react";
import {Card, CardBody, CardFooter, Image, Chip} from "@nextui-org/react";
import {Button} from "@nextui-org/react";
import {Textarea} from "@nextui-org/react";
import api from '../Auth/api.js'; // Importa la instancia de axios con el interceptor
import { CartLogo } from "../Navbar/CartLogo.jsx";

export default function App() {const [products, setProducts] = useState([]); // Crea un estado para almacenar los productos

useEffect(() => {
    api.get('/product/list') // Hace una solicitud GET a la API de productos
        .then(response => {
          setProducts(response.data); // Actualiza el estado con los datos de la respuesta
        })
        .catch(error => {
        console.error('Error al obtener los productos:', error);
        });
  }, []); // El array vacío significa que useEffect se ejecutará solo una vez, cuando el componente se monte

return (
    <div className=" flex flex-wrap gap-4  justify-center items-start">
        {products.map((product, index) => (
            <Card className="max-w-xs flex justify-center items-center" shadow="sm"  isPressable>

            <CardBody className="overflow-visible p-0 w-13">
                <div className="flex justify-between items-center ">
                    <b>Nombre</b>
                    
                    <Button isIconOnly radius="none" color="foreground" aria-label="Add">
                        X
                    </Button>
                </div>
                <Image
                    shadow="sm"
                    radius="lg"
                    width="100%"
                    alt="Product"
                    className="w-full object-cover h-[140px]"
                    src="https://monarcaweb.blob.core.windows.net/imagenesmonarca/2-dunk-low-retro-zapatillas-VMwkPQ (1).png"
                    isZoomed
                    isBlurred
                />
            </CardBody>
            <CardFooter className="text-small justify-between flex flex-col gap-4">
                <div className="flex justify-center items-center gap-1">
                
                <Chip color="primary" >{product.size}</Chip>
                </div>
                <Textarea
                    isDisabled
                    label="Descripción"
                    labelPlacement="outside"
                    placeholder="Enter your description"
                    defaultValue="Descripcion"
                />
            </CardFooter>
            <div className="flex flex-col justify-center items-center gap-4">
                <div className="flex gap-2">
                <Button isIconOnly  color="primary"  aria-label="Add">
                    -
                </Button>
                <Button isIconOnly  color="primary"  aria-label="Add">
                    12
                </Button>
                <Button isIconOnly  color="primary"  aria-label="Add">
                    +
                </Button>
                </div>
                <div>
                    <p className="text-foreground-9">$500</p>
                </div>
            </div>
            
            </Card>
        ))}
    </div>
);
}
