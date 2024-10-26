document.addEventListener("DOMContentLoaded", function() {
    // Llamada GET cuando la página carga
    const resultados = document.querySelector('.results');

    //Traigo productos seleccionados
    const selectedProductos = JSON.parse(localStorage.getItem("selectedProductos"));

    const ul = document.getElementById('resultados'); // Selecciona el ul con id "resultados"
    ul.innerHTML = '';
    selectedProductos.forEach(productoItem =>{
        const li = document.createElement('li');
        li.innerHTML = `
        <span >Items:</span>  
        <span data-id="${productoItem.idProducto}">Codigo de Item: ${productoItem.idProducto}</span>
        <span data-Talle="${productoItem.productoTalle}">Talle: ${productoItem.productoTalle}</span> 
        <span data-Color="${productoItem.productoColor}">Color: ${productoItem.productoColor}</span>
        <div class="form-group">
            <label>Cantidad a pedir:</label>
            <input type="number" class="productoCantidad">
        </div>
        `;
        ul.appendChild(li);
    });
    resultados.style.display = 'block';

});

document.getElementById('enviarOrden').addEventListener('click', async (event)=>{

    listaItems =[];

    const liItems = document.querySelectorAll("#resultados li");

    liItems.forEach(function(item) {
        // Seleccionar los elementos específicos que contienen los atributos data-id, data-Talle, y data-Color
        const codigoItem = item.querySelector("span[data-id]");
        const talleItem = item.querySelector("span[data-Talle]");
        const colorItem = item.querySelector("span[data-Color]");
        
        // Obtener los valores de los atributos data-* usando getAttribute
        const id = codigoItem.getAttribute("data-id");
        const talle = talleItem.getAttribute("data-Talle");
        const color = colorItem.getAttribute("data-Color");
        const cantidadInput = item.querySelector(".productoCantidad");
        const cantidad = cantidadInput.value; // valor ingresado en el input

        listaItems.push({
            codigo: id,
            color: talle,
            talle: color,
            cantidad: parseInt(cantidad)
        });
    });
    console.log(listaItems);
    console.log(localStorage.getItem('codigoTienda'));

    const request = {
        codigoTienda: localStorage.getItem('codigoTienda'),
        items: listaItems
    };
    console.log(request);
    try {
        const response = await fetch('http://localhost:8080/generarOrdenDeCompra',{
            method: 'POST',
            headers:{
                'Content-Type': 'application/json',
                'Authorization': `Bearer ${localStorage.getItem('jwt')}`
            },
            body: JSON.stringify(request)
        });
        
        if(!response.ok){
            const errorData = await response.json();
            console.log("dassads");
            throw new Error(errorData.error.message || 'Ocurrió un error');
        }
    } catch (error) {
        document.getElementById('errores').innerText = `Error: ${error.message}`;
        document.getElementById('errores').style.display = 'block';
    }
});
