import React from "react";
import {Tabs, Tab, Input, Link, Button, Card, CardBody, CardHeader} from "@nextui-org/react";
import { EyeFilledIcon } from "./EyeFilledIcon";
import { EyeSlashFilledIcon } from "./EyeSlashFilledIcon";
import axios from "axios";

export default function App() {
  const [selected, setSelected] = React.useState("login");

  //Ver y ocultar contraseña
  const [isVisible, setIsVisible] = React.useState(false);
  const toggleVisibility = () => setIsVisible(!isVisible);

  //Validación email
  const [value, setValue] = React.useState("");
  const validateEmail = (value) => value.match(/^[A-Z0-9._%+-]+@[A-Z0-9.-]+.[A-Z]{2,4}$/i);
  const isInvalid = React.useMemo(() => {
    if (value === "") return false;
    return validateEmail(value) ? false : true;
  }, [value]);

  //Validación nombre
  const [value1, setValue1] = React.useState("");
  
  //Validación Autenticación
  const handleLogin = async (event) => {
    event.preventDefault();

    const userCredentials = {
      username: value, // asumiendo que 'value' contiene el nombre de usuario
      password: value1 // asumiendo que 'value1' contiene la contraseña
    };

    try {
      const response = await axios.post('http://localhost:8080/auth/login', userCredentials);
      if (response.status === 200) {
        window.location.href = 'http://localhost:5173/product/list';
      } else {
        // manejar error de inicio de sesión
      }
    } catch (error) {
      window.alert('Error al iniciar sesión, comprueba tus datos.');
      console.error('Error al iniciar sesión:', error);
      // manejar error de red
    }
  };

  return (
    <div className=" flex justify-center items-center h-screen">
      <Card className="max-w-lg w-full ">
        <CardHeader className="pb-0 pt-2 px-4 flex-col items-center">
          <h6 className="font-Roboto Condensed">MONARCA INC</h6>
        </CardHeader>
        <CardBody className="overflow-hidden">
          <Tabs
            fullWidth
            size="md"
            aria-label="Tabs form"
            selectedKey={selected}
            onSelectionChange={setSelected}
          >
            {/* Formulario login */}
            <Tab key="login" title="Iniciar Sesión">
              <form className="flex flex-col gap-4" onSubmit={handleLogin}>

                {/* Input email */}
                <Input
                  value={value}
                  isClearable
                  isRequired
                  type="email"
                  label="Email"
                  variant="bordered"
                  placeholder="Ingresa tu email"
                  isInvalid={isInvalid}
                  color={isInvalid ? "danger" : "primary"}
                  errorMessage={isInvalid && "Por favor, ingrese un email válido."}
                  onValueChange={setValue}
                  onClear={() => console.log("input cleared")}
                  className="max-w-md mx-auto"
                />

                {/* Input contraseña */}
                <Input
                  value={value1}
                  onValueChange={setValue1}
                  isRequired
                  label="Contraseña"
                  variant="bordered"
                  placeholder="Ingresa tu contraseña"
                  color="primary"
                  endContent={
                    <button
                      className="focus:outline-none"
                      type="button"
                      onClick={toggleVisibility}
                    >
                      {isVisible ? (
                        <EyeSlashFilledIcon className="text-2xl text-default-400 pointer-events-none" />
                      ) : (
                        <EyeFilledIcon className="text-2xl text-default-400 pointer-events-none" />
                      )}
                    </button>
                  }
                  type={isVisible ? "text" : "password"}
                  className="max-w-md mx-auto mt-2"
                />
                <p className="text-center text-small">
                  Necesita crear una cuenta?{" "}
                  <Link size="sm" onPress={() => setSelected("sign-up")}>
                    Registrate
                  </Link>
                </p>
                <div className="flex gap-2 justify-center ">
                  <Button type="submit" color="primary">
                    Iniciar Sesión
                  </Button>
                </div>
              </form>
            </Tab>

            {/* Formulario registro */}
            <Tab key="sign-up" title="Registrarse">
              <form className="flex flex-col gap-4 h-[300px]">
                <Input
                  isClearable
                  isRequired
                  type="Text"
                  label="Nombre"
                  variant="bordered"
                  placeholder="Ingresa tu nombre"
                  color="primary"
                  onClear={() => console.log("input cleared")}
                  className="max-w-md mx-auto"
                />
                <Input
                  value={value}
                  isClearable
                  isRequired
                  type="email"
                  label="Email"
                  variant="bordered"
                  placeholder="Ingresa tu email"
                  isInvalid={isInvalid}
                  color={isInvalid ? "danger" : "primary"}
                  errorMessage={isInvalid && "Por favor, ingrese un email válido."}
                  onValueChange={setValue}
                  onClear={() => console.log("input cleared")}
                  className="max-w-md mx-auto"
                />
                <Input
                  isRequired
                  label="Contraseña"
                  variant="bordered"
                  placeholder="Ingresa tu contraseña"
                  color="primary"
                  endContent={
                    <button
                      className="focus:outline-none"
                      type="button"
                      onClick={toggleVisibility}
                    >
                      {isVisible ? (
                        <EyeSlashFilledIcon className="text-2xl text-default-400 pointer-events-none" />
                      ) : (
                        <EyeFilledIcon className="text-2xl text-default-400 pointer-events-none" />
                      )}
                    </button>
                  }
                  type={isVisible ? "text" : "password"}
                  className="max-w-md mx-auto mt-2"
                />
                <p className="text-center text-small">
                  Ya tienes una cuenta?{" "}
                  <Link size="sm" onPress={() => setSelected("login")}>
                    Iniciar Sesión
                  </Link>
                </p>
                <div className="flex gap-2 justify-center">
                  <Button color="primary">
                    Registrarme
                  </Button>
                </div>
              </form>
            </Tab>
          </Tabs>
        </CardBody>
      </Card>
    </div>
  );
}