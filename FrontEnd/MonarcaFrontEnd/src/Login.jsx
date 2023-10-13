import React, { useState } from "react";

const LoginForm = () => {
  const [name, setName] = useState("");
  const [password, setPassword] = useState("");
  const [errors, setErrors] = useState({});

  const handleSubmit = (event) => {
    event.preventDefault();

    // Validar el formulario

    if (name === "") {
      setErrors({ name: "El campo de nombre es obligatorio" });
      return;
    }

    if (password.length < 8) {
      setErrors({ password: "La contraseña debe tener al menos 8 caracteres" });
      return;
    }

    // Enviar el formulario
  };

  return (
    <section>
  <div className="px-4 py-12 mx-auto max-w-7xl sm:px-6 md:px-12 lg:px-24 lg:py-24">
    <div className="justify-center mx-auto text-left align-bottom transition-all transform bg-white rounded-lg sm:align-middle sm:max-w-2xl sm:w-full">
      <div className="grid flex-wrap items-center justify-center grid-cols-1 mx-auto shadow-xl lg:grid-cols-2 rounded-xl">
        <div className="w-full px-6 py-3">
          <div>
            <div className="mt-3 text-left sm:mt-5">
              <div className="inline-flex items-center w-full">
                <h3 className="text-lg font-bold text-neutral-600 l eading-6 lg:text-5xl">
                  Inicio De Sesión
                </h3>
              </div>
              <div className="mt-4 text-base text-gray-500">
                <p>Navega por el maravilloso mundo de las compras</p>
              </div>
            </div>
          </div>
          <div className="mt-6 space-y-2">
            <div>
              <label htmlFor="email" className="sr-only">
                Email
              </label>
              <input
                type="text"
                name="email"
                id="email"
                className="block w-full px-5 py-3 text-base text-neutral-600 placeholder-gray-300 transition duration-500 ease-in-out transform border border-transparent rounded-lg bg-gray-50 focus:outline-none focus:border-transparent focus:ring-2 focus:ring-white focus:ring-offset-2 focus:ring-offset-gray-300"
                placeholder="Ingresa Tu email"
                style={{
                  backgroundImage: 'url("data:image/png',
                  backgroundRepeat: "no-repeat",
                  backgroundSize: 20,
                  backgroundPosition: "97% center",
                  cursor: "auto"
                }}
                data-temp-mail-org={6}
              />
            </div>
            <div>
              <label htmlFor="password" className="sr-only">
                Password
              </label>
              <input
                type="password"
                name="password"
                id="password"
                className="block w-full px-5 py-3 text-base text-neutral-600 placeholder-gray-300 transition duration-500 ease-in-out transform border border-transparent rounded-lg bg-gray-50 focus:outline-none focus:border-transparent focus:ring-2 focus:ring-white focus:ring-offset-2 focus:ring-offset-gray-300"
                placeholder="Ingresa Tu Contrseña"
              />
            </div>
            <div className="flex flex-col mt-4 lg:space-y-2">
              <button
                type="button"
                className="flex items-center justify-center w-full px-10 py-4 text-base font-medium text-center text-white transition duration-500 ease-in-out transform bg-blue-600 rounded-xl hover:bg-blue-700 focus:outline-none focus:ring-2 focus:ring-offset-2 focus:ring-blue-500"
              >
                Iniciar Sesión
              </button>
              <a
                href="#"
                type="button"
                className="inline-flex justify-center py-4 text-base font-medium text-gray-500 focus:outline-none hover:text-neutral-600 focus:text-blue-600 sm:text-sm"
              >
                {" "}
                Recuperar Contraseña?{" "}
              </a>
            </div>
          </div>
        </div>
        <div className="order-first hidden w-full lg:block">
          <img
            className="object-cover h-full bg-cover rounded-l-lg"
            src="https://static.vecteezy.com/system/resources/previews/026/715/703/non_2x/eco-friendly-craft-paper-bags-fall-in-a-metal-shopping-cart-on-a-yellow-background-black-friday-gift-sales-top-and-vertical-view-photo.jpg"
            alt=""
          />
        </div>
      </div>
    </div>
  </div>
</section>

  );
};

export default LoginForm;