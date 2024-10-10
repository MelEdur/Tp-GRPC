document.addEventListener("DOMContentLoaded", function() {
    // Llamada GET cuando la página carga
    const resultados = document.querySelector('.results');

    const response = fetch('http://127.0.0.1:8080/ordenesAceptadas',{
        method: 'GET',
        headers:{
            'Content-Type': 'application/json',
            // 'Authorization': `Bearer ${localStorage.getItem('jwt')}`
        }
    }).then(response => {
        // Verifica si la respuesta es exitosa (status 200)
        if (!response.ok) {
            throw new Error(errorData.error.message || 'Ocurrió un error');
        }
        // Convertir la respuesta a JSON
        return response.json();
    })
    .then(data => {
        //Borrar resultados previos
        const ul = document.getElementById('resultados'); // Selecciona el ul con id "resultados"
        ul.innerHTML = '';
        console.log(data);
        data.forEach(ordenDeCompra =>{
            const li = document.createElement('li');
            li.innerHTML = `
            <span>Pedido: Orden ${ordenDeCompra.idOrdenDeCompra}</span>  
            <span>Codigo de Tienda: ${ordenDeCompra.codigoTienda}</span>
            <span>Estado: ${ordenDeCompra.estado}</span> 
            <span>Fecha De Solicitud: ${ordenDeCompra.fechaDeSolicitud}</span>
            <span>Observaciones: ${ordenDeCompra.observaciones}</span>
            <span>Fecha Estimada Recepcion: ${ordenDeCompra.ordenDeDespacho.fechaEstimada}</span>
            <div>
            <strong>Items:</strong>
                <ul>
                    ${ordenDeCompra.items.map(item => `
                        <li>
                            <span>Código: ${item.codigo}</span> 
                            <span>Color: ${item.color}</span>
                            <span>Talle: ${item.talle}</span> 
                            <span>Cantidad: ${item.cantidad}</span>
                        </li>
                    `).join('')}
                </ul>
            </div>
            `;
            ul.appendChild(li);
        });
        resultados.style.display = 'block';
    })
    .catch(error => {
        console.error('Error al cargar los pedidos:', error); // Manejo de errores
    });

});