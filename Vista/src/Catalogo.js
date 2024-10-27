document.addEventListener("DOMContentLoaded", function() {
    document.getElementById('errores').style.display = 'hidden';

    // Llamada GET cuando la página carga
    const resultados = document.querySelector('.results');

    //Traigo productos seleccionados
    const selectedProductos = JSON.parse(localStorage.getItem("selectedProductos"));

    const ul = document.getElementById('resultados'); // Selecciona el ul con id "resultados"
    ul.innerHTML = '';
    selectedProductos.forEach(productoItem =>{
        const li = document.createElement('li');
        li.innerHTML = `
        <span >Producto</span>
        <span data-id="${productoItem.idProducto}">Codigo de Item: ${productoItem.idProducto}</span>
        <span data-Nombre="${productoItem.productoNombre}">Nombre: ${productoItem.productoNombre}</span>
        <span data-Talle="${productoItem.productoTalle}">Talle: ${productoItem.productoTalle}</span>
        <span data-Color="${productoItem.productoColor}">Color: ${productoItem.productoColor}</span>
        `;
        ul.appendChild(li);
    });
    resultados.style.display = 'block';
});

document.getElementById('enviarCatalogo').addEventListener('click',async (event)=>{

    const nombre = document.getElementById('nombreCatalogo').value;
    const codigoTienda = localStorage.getItem('codigoTienda');
    const ids = [];

    JSON.parse(localStorage.getItem("selectedProductos")).forEach(producto =>{
        ids.push(parseInt(producto.idProducto,10));
    });

    console.log();

    if(nombre.trim() === ''){
        document.getElementById('errores').innerText = `Debe ingresar un nombre para el catálogo`;
        document.getElementById('errores').style.display = 'block';
        return;
    }


    try{
        const response = await fetch('http://localhost:5050/catalogos',{
            method: 'POST',
            headers:{
                'Content-Type': 'application/json'
            },
            body: JSON.stringify({nombre,codigoTienda,ids})
        });
        if(response.ok){
            alert('Catalogo agregado correctamente');
        }
    }catch{

    }
});