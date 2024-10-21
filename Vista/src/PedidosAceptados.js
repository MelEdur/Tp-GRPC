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
        let numeroData = 0;
        ul.innerHTML = '';
        console.log(data);
        if (data === null || data === undefined || (Array.isArray(data) && data.length === 0)) {
            const label = this.createElement('label');
            label.innerHTML = `<h5>No existen Pedidos Aceptados</h5>`;
            ul.appendChild(label);
            resultados.style.display = 'block';
        } else {
            data.forEach(ordenDeCompra =>{
                const li = document.createElement('li');
                numeroData = numeroData+1;
                li.innerHTML = `
                <input type="checkbox" class="order-select" data-id="${ordenDeCompra.idOrdenDeCompra}" data-tienda="${ordenDeCompra.codigoTienda}" data-fecha-solicitud="${ordenDeCompra.fechaDeSolicitud}" 
                data-estado="${ordenDeCompra.estado}" data-fecha-estimada-recepcion="${ordenDeCompra.ordenDeDespacho.fechaEstimada}" data-observaciones="${ordenDeCompra.observaciones}"
                data-ordenDeDespacho-id="${ordenDeCompra.ordenDeDespacho.idOrdenDeDespacho}" data-ordenDeDespacho-idOrdenDeCompra="${ordenDeCompra.ordenDeDespacho.idOrdenDeCompra}"
                >
                <span >Pedido: Orden ${ordenDeCompra.idOrdenDeCompra}</span>  
                <span id="${ordenDeCompra.codigoTienda}">Codigo de Tienda: ${ordenDeCompra.codigoTienda}</span>
                <span>Estado: ${ordenDeCompra.estado}</span> 
                <span>Fecha De Solicitud: ${ordenDeCompra.fechaDeSolicitud}</span>
                <span>Observaciones: ${ordenDeCompra.observaciones}</span>
                <span>Fecha Estimada Recepcion: ${ordenDeCompra.ordenDeDespacho.fechaEstimada}</span>
                <div>
                <strong>Items:</strong>
                    <ul class="itemsUl">
                        ${ordenDeCompra.items.map(item => `
                            <li>
                                <span style="display:none">IdItem: ${item.idItem}</span> 
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
        }
        
    })
    .catch(error => {
        console.error('Error al cargar los pedidos:', error); // Manejo de errores
    });

});

function getCurrentDate() {
    const today = new Date();
    
    const year = today.getFullYear(); // Get the full year (e.g., 2024)
    const month = String(today.getMonth() + 1).padStart(2, '0'); // Get the month (0-indexed, so add 1). Use padStart to ensure two digits.
    const day = String(today.getDate()).padStart(2, '0'); // Get the day and pad it to 2 digits.

    return `${year}-${month}-${day}`; // Format the date as YYYY-MM-DD
}

document.getElementById('procesarOrdenes').addEventListener('click', function() {

    const selectedOrders = [];
    document.querySelectorAll('.order-select:checked').forEach(function(checkbox) {
        const orderId = checkbox.getAttribute('data-id');
        const codigoTienda = checkbox.getAttribute('data-tienda');
        const fechaDeSolicitud = checkbox.getAttribute('data-fecha-solicitud');
        const estado = checkbox.getAttribute('data-estado');
        const fechaEstimadaRecepcion = checkbox.getAttribute('data-fecha-estimada-recepcion');
        const observaciones = checkbox.getAttribute('data-observaciones');
        const ordenDeDespachoId = checkbox.getAttribute('data-ordenDeDespacho-id');
        const ordenDeDespachoOrdenCompraId = checkbox.getAttribute('data-ordenDeDespacho-idOrdenDeCompra');

        const items = [];
        document.querySelectorAll('.itemsUl').forEach(function(itemElement) {

            const idItem = itemElement.querySelector('span:nth-child(1)').textContent.split(': ')[1]; 
            const codigo = itemElement.querySelector('span:nth-child(2)').textContent.split(': ')[1]; 
            const color = itemElement.querySelector('span:nth-child(3)').textContent.split(': ')[1]; 
            const talle = itemElement.querySelector('span:nth-child(4)').textContent.split(': ')[1]; 
            const cantidad = itemElement.querySelector('span:nth-child(5)').textContent.split(': ')[1];

            items.push({
                idItem: parseInt(idItem), 
                codigo: codigo,
                color: color,
                talle: talle,
                cantidad: parseInt(cantidad) 
            });
        });
    
        const orderData = {
            idOrdenDeCompra: parseInt(orderId),
            codigoTienda: codigoTienda,
            items: items,
            fechaDeSolicitud: fechaDeSolicitud,
            estado: estado,
            observaciones: observaciones,
            ordenDeDespacho: {
                idOrdenDeDespacho: parseInt(ordenDeDespachoId),  
                idOrdenDeCompra: parseInt(ordenDeDespachoOrdenCompraId),
                fechaEstimada: fechaEstimadaRecepcion
            },
            fechaDeRecepcion: getCurrentDate()
        };

        selectedOrders.push(orderData);
    });
    console.log('aca:',selectedOrders);
    if (selectedOrders.length > 0) {
        fetch('http://127.0.0.1:8080/procesarOrdenesRecibidas', {
            method: 'POST',
            headers: {
                'Content-Type': 'application/json'
            },
            body: JSON.stringify(selectedOrders)
        })
        .then(response => response.json())
        .then(data => console.log('Success:', data))
        .catch((error) => console.error('Error:', error));
    } else {
        alert('No orders selected');
    } 
});