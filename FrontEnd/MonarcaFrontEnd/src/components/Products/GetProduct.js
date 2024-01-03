
export async function obtenerProductos() {
  const response = await fetch("http://localhost:8080/product/list");
  if (!response.ok) {
    throw new Error(`HTTP error! status: ${response.status}`);
  }
  const productos = await response.json();
  return productos;
}
