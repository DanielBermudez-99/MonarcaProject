import React from "react";
import {Card, CardBody, CardFooter, Image} from "@nextui-org/react";
import {Button} from "@nextui-org/react";
import {Textarea} from "@nextui-org/react";
import {ScrollShadow} from "@nextui-org/react";


export default function App() {
  const list = [
    {
      title: "Sudadera",
      img: "https://monarcaweb.blob.core.windows.net/imagenesmonarca/flared-tp.jpg",
      price: "$5.50",
    },
    {
      title: "Inter Miami",
      img: "https://monarcaweb.blob.core.windows.net/imagenesmonarca/Camiseta%20Inter%20Miami.jpg",
      price: "$10.00",
    },
    {
      title: "Xforce White",
      img: "https://monarcaweb.blob.core.windows.net/imagenesmonarca/tenis-nmd_r1.jpg",
      price: "$5.30",
    },
    {
      title: "Avocado Running",
      img: "https://monarcaweb.blob.core.windows.net/imagenesmonarca/tenis-adizero-sl.jpg",
      price: "$15.70",
    },
    {
      title: "Sudadera",
      img: "https://monarcaweb.blob.core.windows.net/imagenesmonarca/flared-tp.jpg",
      price: "$5.50",
    },
    {
      title: "Inter Miami",
      img: "https://monarcaweb.blob.core.windows.net/imagenesmonarca/Camiseta%20Inter%20Miami.jpg",
      price: "$10.00",
    },
    {
      title: "Xforce White",
      img: "https://monarcaweb.blob.core.windows.net/imagenesmonarca/tenis-nmd_r1.jpg",
      price: "$5.30",
    },
    {
      title: "Avocado Running",
      img: "https://monarcaweb.blob.core.windows.net/imagenesmonarca/tenis-adizero-sl.jpg",
      price: "$15.70",
    },
    {
      title: "Sudadera",
      img: "https://monarcaweb.blob.core.windows.net/imagenesmonarca/flared-tp.jpg",
      price: "$5.50",
    },
    {
      title: "Inter Miami",
      img: "https://monarcaweb.blob.core.windows.net/imagenesmonarca/Camiseta%20Inter%20Miami.jpg",
      price: "$10.00",
    },
    {
      title: "Sudadera",
      img: "https://monarcaweb.blob.core.windows.net/imagenesmonarca/flared-tp.jpg",
      price: "$5.50",
    },
    {
      title: "Avocado Running",
      img: "https://monarcaweb.blob.core.windows.net/imagenesmonarca/tenis-adizero-sl.jpg",
      price: "$15.70",
    },
    

  ];

  return (
    <div className=" flex flex-wrap gap-4  justify-center items-start">
      
      {list.map((item, index) => (
        <Card className="max-w-xs flex justify-center items-center" shadow="sm" key={index} isPressable onPress={() => console.log("item pressed")}>
            
          <CardBody className="overflow-visible p-0 w-13">
            <Image
              shadow="sm"
              radius="lg"
              width="100%"
              alt={item.title}
              className="w-full object-cover h-[140px]"
              src={item.img}
              isZoomed
              isBlurred
            />
          </CardBody>
          <CardFooter className="text-small justify-between">
            <b>{item.title}</b>
            <p className="text-default-500">{item.price}</p>
            <Button color="primary" >
                COMPRAR
            </Button>
          </CardFooter>
          <div className="">
            <Textarea
            isDisabled
            label="Descripción"
            labelPlacement="outside"
            placeholder="Enter your description"
            defaultValue="Espacio para la descripción del producto."
            className=""
            />
            </div>
        </Card>
      ))}
    </div>
  );
}
