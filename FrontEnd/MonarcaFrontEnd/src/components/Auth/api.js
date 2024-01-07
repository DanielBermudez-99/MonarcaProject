// api.js
import axios from 'axios';

// Crear una instancia de axios
const api = axios.create({
    baseURL: 'http://localhost:8080', // reemplaza esto con la URL base de tu API
});

// Agregar un interceptor de solicitud a la instancia de axios
api.interceptors.request.use((config) => {
    // Obtener el token del almacenamiento local
    const token = localStorage.getItem('jwt');

    // Si el token existe, agregarlo al encabezado de la solicitud
    if (token) {
        config.headers.Authorization = `Bearer ${token}`;
    }

    return config;
}, (error) => {
    return Promise.reject(error);
});

export default api;