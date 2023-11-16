import React from "react";
import {Card, CardBody, CardFooter, Image} from "@nextui-org/react";
import {Button} from "@nextui-org/react";
import {Textarea} from "@nextui-org/react";


export default function App() {
  const list = [
    {
      title: "Orange",
      img: "https://nextui.org/images/fruit-2.jpeg",
      price: "$5.50",
    },
    {
      title: "Raspberry",
      img: "https://nextui.org/images/fruit-1.jpeg",
      price: "$10.00",
    },
    {
      title: "Lemon",
      img: "https://nextui.org/images/fruit-3.jpeg",
      price: "$5.30",
    },
    {
      title: "Avocado",
      img: "https://nextui.org/images/fruit-4.jpeg",
      price: "$15.70",
    },
  ];

  return (
    <div className="gap-2 grid grid-cols-2 sm:grid-cols-4">
      {list.map((item, index) => (
        <Card shadow="sm" key={index} isPressable onPress={() => console.log("item pressed")}>
            
          <CardBody className="overflow-visible p-0 w-13">
            <Image
              shadow="sm"
              radius="lg"
              width="100%"
              alt={item.title}
              className="w-full object-cover h-[140px]"
              src={item.img}
              
            />
          </CardBody>
          <CardFooter className="text-small justify-between">
            <b>{item.title}</b>
            <p className="text-default-500">{item.price}</p>
            <Button color="primary" >
                COMPRAR
            </Button>
            <Textarea
            isDisabled
            label="Descripción"
            labelPlacement="outside"
            placeholder="Enter your description"
            defaultValue="Espacio para la descripción del producto."
            className="max-w-xs"
            />
          </CardFooter>
        </Card>
      ))}
    </div>
  );
}
