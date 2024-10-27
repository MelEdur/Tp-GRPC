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
        <span data-Nombre="${productoItem.productoNombre}">Nombre: ${productoItem.productoNombre}</span>
        <span data-Talle="${productoItem.productoTalle}">Talle: ${productoItem.productoTalle}</span>
        <span data-Color="${productoItem.productoColor}">Color: ${productoItem.productoColor}</span>
        `;
        ul.appendChild(li);
    });
    resultados.style.display = 'block';

});