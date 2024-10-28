const ul = document.getElementById('resultados');

document.getElementById('botonBuscarInformes').addEventListener('click', async (event)=>{
    event.preventDefault();
    const searchFields = document.querySelector('.results');

    const codigoProducto =  document.getElementById('searchInformeCodProducto').value;
    const codigoTienda = document.getElementById('searchInformeCodTienda').value;
    const estado =  document.getElementById('searchInformeEstado').value;
    const fechaDesde = document.getElementById('searchInformeFechaDesde').value;
    const fechaHasta = document.getElementById('searchInformeFechaHasta').value;

    try {
        const response = await fetch('http://localhost:5050/ordenDeCompras',{
            method: 'POST',
            headers:{
                'Content-Type': 'application/json'
            },
            body: JSON.stringify({codigoProducto,codigoTienda,estado,fechaDesde,fechaHasta})
        });

        if(!response.ok){
            const errorData = await response.json();
            throw new Error(errorData.error.message || 'Ocurrió un error');
        }

        const data = await response.json();
        //Borrar resultados previos
        ul.innerHTML = '';
        console.log(data);
        data.forEach(informe=>{
            informe.productos.forEach(producto=>{
                console.log("entrasd");

                const li = document.createElement('li');
                li.style.width = "100%";
                li.innerHTML = `
                <span>Informe De Compra: ${informe.idOrdenDeCompra}</span>
                <span>Codigo de Producto: ${producto.codigoProducto}</span>
                <span>Nombre de Producto: ${producto.nombre}</span>
                <span>Cantidad Pedida Total: ${producto.cantidadPedidaTotal}</span>
                <span>Codigo de Tienda: ${informe.codigoTienda}</span>
                <span>Estado: ${informe.estado}</span>
                <span>Fecha de Solicitud: ${informe.fechaDeSolicitud}</span>
                `;
                ul.appendChild(li);

            })
        });
        searchFields.style.display = 'block';
        resultados.style.display = 'block';

    } catch (error) {
        document.getElementById('errores').innerText = `Error: ${error.message}`;
        document.getElementById('errores').style.display = 'block';
    }
});

document.addEventListener("DOMContentLoaded", function() {
    if(rolActual == "USUARIO"){
        document.getElementById('searchInformeCodTienda').style.display = 'none'
    }
});