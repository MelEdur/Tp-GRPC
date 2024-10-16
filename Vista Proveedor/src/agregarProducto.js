document.getElementById('productForm').addEventListener('submit', async (event) =>{
    event.preventDefault();

    // Obtener los datos del formulario
    const codigo = document.getElementById('codigo').value;
    const talles = document.getElementById('talles').value.split(',').map(talle => talle.trim());
    const colores = document.getElementById('colores').value.split(',').map(color => color.trim());
    const fotos = document.getElementById('fotos').value.split(',').map(url => url.trim());

    // Crear objeto a enviar
    const productoData = {
        codigo,
        talles,
        colores,
        fotos
    };
    console.log(productoData);
    console.log(JSON.stringify(productoData));
    // Enviar datos al servidor mediante una petición POST
    fetch('http://localhost:8081/crearProducto', {
        method: 'POST',
        headers: {
            'Content-Type': 'application/json'
        },
        body: JSON.stringify(productoData)
    })
    .then(data => {
        document.getElementById('response').textContent = 'Producto registrado con éxito';
    });
});