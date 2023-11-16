import React from "react";
import {Input} from "@nextui-org/react";
//Importación de iconos contraseña
import {EyeFilledIcon} from "./EyeFilledIcon";
import {EyeSlashFilledIcon} from "./EyeSlashFilledIcon";
//Checkbox
import {Checkbox} from "@nextui-org/react";
//Botón
import {Button} from "@nextui-org/react";
//Card
import {Card, CardHeader, CardBody, Image} from "@nextui-org/react";



export default function App() {

    //Validación de email
    const [value, setValue] = React.useState("");
    const validateEmail = (value) => value.match(/^[A-Z0-9._%+-]+@[A-Z0-9.-]+.[A-Z]{2,4}$/i);
    const isInvalid = React.useMemo(() => {
    if (value === "") return false;
    return validateEmail(value) ? false : true;
  }, [value]);
   
    //Constante para tipos de input
    const variants = ["flat", "bordered", "underlined", "faded"];

  //Constante visibilidad de contraseña
    const [isVisible, setIsVisible] = React.useState(false);
    const toggleVisibility = () => setIsVisible(!isVisible);

  return (
    <Card className="py-4 max-w-sm" >
      <CardHeader className="pb-0 pt-2 px-4 flex-col items-start">
        <h6 className="">MONARCA INC</h6>
      </CardHeader>
      <CardBody className="overflow-visible py-2">

    <Input 
      value={value}
      isClearable
      type="email"
      label="Email"
      variant="bordered"
      placeholder="Ingresa tu email"
      isInvalid={isInvalid}
      color={isInvalid ? "danger" : "primary"}
      errorMessage={isInvalid && "Por favor, ingrese un email válido."}
      onValueChange={setValue}
      onClear={() => console.log("input cleared")}
      className="max-w-xs"
    />

    <Input
      label="Contraseña"
      variant="bordered"
      placeholder="Ingresa tu contraseña"
      color="primary"
      endContent={
        <button className="focus:outline-none" type="button" onClick={toggleVisibility}>
          {isVisible ? (
            <EyeSlashFilledIcon className="text-2xl text-default-400 pointer-events-none" />
          ) : (
            <EyeFilledIcon className="text-2xl text-default-400 pointer-events-none" />
          )}
        </button>
      }
      type={isVisible ? "text" : "password"}
      className="max-w-xs"
    />

    <Checkbox defaultSelected>Recordarme</Checkbox>

    <Button variant="shadow" color="primary" className="w-3.5">
      Iniciar Sesión
    </Button>
      </CardBody>
    </Card>
  );
}
