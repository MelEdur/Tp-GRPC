const ul = document.getElementById('resultados');
document.getElementById('botonBuscarProductos').addEventListener('click', async (event)=>{
    event.preventDefault();
    const searchFields = document.querySelector('.results');

    const nombreProducto = document.getElementById('searchProductosNombre').value;
    const codigoProducto = document.getElementById('searchProductosCodigo').value;
    const talle = document.getElementById('searchProductosTalle').value;
    const color = document.getElementById('searchProductosColor').value;
    const codigoTienda = localStorage.getItem('codigoTienda');

    try {
        const response = await fetch('http://localhost:5000/productos/filtrado',{
            method: 'POST',
            headers:{
                'Content-Type': 'application/json',
                'Authorization': `Bearer ${localStorage.getItem('jwt')}`
            },
            body: JSON.stringify({nombreProducto,codigoProducto,talle,color,codigoTienda})
        });
        
        if(!response.ok){
            const errorData = await response.json();
            console.log(errorData);
            throw new Error(errorData.error.message || 'Ocurrió un error');
        }

        const data = await response.json();
        console.log(data.stocksCompleto);
        //Borrar resultados previos
        ul.innerHTML = '';
        data.stocksCompleto.forEach(stockCompleto =>{
            const li = document.createElement('li');
            console.log(stockCompleto.idStockCompleto);
            li.innerHTML = `
            <span><img src="${stockCompleto.foto}" class="card-img-top" alt="Product Image" style="height:150px;width:150px;"/></span>
            <span>CodigoProducto: ${stockCompleto.codigoProducto}</span><br>
            <span>NombreProducto: ${stockCompleto.nombreProducto}</span><br>
            <span>Talle: ${stockCompleto.talle}</span><br>
            <span>Color: ${stockCompleto.color}</span><br>
            <span>Habilitado: ${stockCompleto.habilitado ? '✔️' : '❌'}</span>
            <span>Cantidad: ${stockCompleto.cantidad}</span><br>
            <button type="button" id="botonModificarProductoFormulario" class="btn btn-primary" data-producto='${JSON.stringify(stockCompleto)}'>Modificar</button>
            `;
            ul.appendChild(li);
        });
        searchFields.style.display = 'block';

    } catch (error) {
        document.getElementById('errores').innerText = `Error: ${error.message}`;
        document.getElementById('errores').style.display = 'block';
    }
});

ul.addEventListener('click', function(event){
    if(event.target && event.target.id === 'botonModificarProductoFormulario'){
        const stockCompleto = JSON.parse(event.target.getAttribute('data-producto'));
        console.log(stockCompleto.idProducto);
        document.getElementById('idModificarStock').value = stockCompleto.idStockCompleto;
        document.getElementById('cantidadStockModificarStock').value = stockCompleto.cantidad;
        document.getElementById('mensajeModificarProducto').style.display = 'none';
        $('#modificarProductoModal').modal('show');
    }
});

document.querySelectorAll('[id="cerrarModificarFormulario"]').forEach(button =>{
    button.addEventListener('click', function(){
        $('#modificarProductoModal').modal('hide');
    });
});

document.getElementById('botonModificarProducto').addEventListener('click',async(event)=>{
    event.preventDefault();
    const idStockCompleto = Number(document.getElementById('idModificarStock').value);
    const cantidad = Number(document.getElementById('cantidadStockModificarStock').value);
    

    try{
        console.log(JSON.stringify({idStockCompleto,cantidad}));
        const response = await fetch('http://localhost:5000/productos/stock',{
            method: 'PATCH',
            headers:{
                'Content-Type': 'application/json',
                'Authorization': `Bearer ${localStorage.getItem('jwt')}`
            },
            body: JSON.stringify({idStockCompleto,cantidad})
        });
        if(!response.ok){
            const errorData = await response.json();
            throw new Error(errorData.error.message || 'Ocurrió un error');
        }

        const data = await response.json();
        document.getElementById('mensajeModificarProducto').innerText = `Stock modificado correctamente`;
        document.getElementById('mensajeModificarProducto').style.color = 'green';
        document.getElementById('mensajeModificarProducto').style.display = 'block';
        ul.innerHTML = '';

    }catch(error){
        document.getElementById('mensajeModificarProducto').innerText = `Error: ${error.message}`;
        document.getElementById('mensajeModificarProducto').style.color = 'crimson';
        document.getElementById('mensajeModificarProducto').style.display = 'block';
    }
});