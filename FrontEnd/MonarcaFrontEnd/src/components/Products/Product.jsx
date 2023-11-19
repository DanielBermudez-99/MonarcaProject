import React from "react";
import {Card, CardBody, CardFooter, Image} from "@nextui-org/react";
import {Button} from "@nextui-org/react";
import {Textarea} from "@nextui-org/react";
import {ScrollShadow} from "@nextui-org/react";


export default function App() {
  const list = [
    {
      title: "Sudadera",
      img: "https://assets.adidas.com/images/w_280,h_280,f_auto,q_auto:sensitive/e2ae95b94cb442edab573139e9c87c59_9366/flared-tp.jpg",
      price: "$5.50",
    },
    {
      title: "Inter Miami",
      img: "https://assets.adidas.com/images/w_280,h_280,f_auto,q_auto:sensitive/6b1faba76baf46e997acaf63000d83a9_9366/camiseta-local-inter-miami-cf-22-23.jpg",
      price: "$10.00",
    },
    {
      title: "Xforce White",
      img: "https://assets.adidas.com/images/w_280,h_280,f_auto,q_auto:sensitive/7a4837fc5c5549368b13c751bdae3ee6_9366/tenis-nmd_r1.jpg",
      price: "$5.30",
    },
    {
      title: "Avocado Running",
      img: "https://assets.adidas.com/images/w_280,h_280,f_auto,q_auto:sensitive/75c3e70cdace4495a74ab4840783963a_9366/tenis-adizero-sl.jpg",
      price: "$15.70",
    },
    {
      title: "Sudadera",
      img: "https://assets.adidas.com/images/w_280,h_280,f_auto,q_auto:sensitive/e2ae95b94cb442edab573139e9c87c59_9366/flared-tp.jpg",
      price: "$5.50",
    },
    {
      title: "Inter Miami",
      img: "https://assets.adidas.com/images/w_280,h_280,f_auto,q_auto:sensitive/6b1faba76baf46e997acaf63000d83a9_9366/camiseta-local-inter-miami-cf-22-23.jpg",
      price: "$10.00",
    },
    {
      title: "Xforce White",
      img: "https://assets.adidas.com/images/w_280,h_280,f_auto,q_auto:sensitive/7a4837fc5c5549368b13c751bdae3ee6_9366/tenis-nmd_r1.jpg",
      price: "$5.30",
    },
    {
      title: "Avocado Running",
      img: "https://assets.adidas.com/images/w_280,h_280,f_auto,q_auto:sensitive/75c3e70cdace4495a74ab4840783963a_9366/tenis-adizero-sl.jpg",
      price: "$15.70",
    },
    {
      title: "Sudadera",
      img: "https://assets.adidas.com/images/w_280,h_280,f_auto,q_auto:sensitive/e2ae95b94cb442edab573139e9c87c59_9366/flared-tp.jpg",
      price: "$5.50",
    },
    {
      title: "Inter Miami",
      img: "https://assets.adidas.com/images/w_280,h_280,f_auto,q_auto:sensitive/6b1faba76baf46e997acaf63000d83a9_9366/camiseta-local-inter-miami-cf-22-23.jpg",
      price: "$10.00",
    },
    {
      title: "Sudadera",
      img: "https://assets.adidas.com/images/w_280,h_280,f_auto,q_auto:sensitive/e2ae95b94cb442edab573139e9c87c59_9366/flared-tp.jpg",
      price: "$5.50",
    },
    {
      title: "Avocado Running",
      img: "https://assets.adidas.com/images/w_280,h_280,f_auto,q_auto:sensitive/75c3e70cdace4495a74ab4840783963a_9366/tenis-adizero-sl.jpg",
      price: "$15.70",
    },

  ];

  return (
    <div className=" flex flex-wrap gap-4  justify-center items-start h-screen">
      
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
