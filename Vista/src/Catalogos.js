document.getElementById('botonBuscarCatalogos').addEventListener('click',async (event)=>{
    event.preventDefault();

    try{
        const response = await fetch('http://localhost:5050/catalogos',{
            method: 'GET',
            headers:{
                'Content-Type': 'application/json',
            },
        });

        const data = await response.json();
        ul.innerHTML = '';

        data.catalogos.forEach(catalogo =>{
            const li = document.createElement('li');
            li.innerHTML = ``;
        });

    }catch(error){
    }
});

document.getElementById('botonGenerarCatalogo').addEventListener('click',async(event)=>{
    if(event.target && event.target.id === 'botonGenerarCatalogo'){

        const selectedProducts = [];
        document.querySelectorAll('.producto-select:checked').forEach(function(checkbox){

            const productoCodigo = checkbox.getAttribute('data-id');
            const productoColor = checkbox.getAttribute('data-color');
            const productoTalle = checkbox.getAttribute('data-talle');
            const productoNombre = checkbox.getAttribute('data-nombre');

            const productoData = {
                idProducto: productoCodigo,
                productoColor: productoColor,
                productoTalle: productoTalle,
                productoNombre: productoNombre
            };

            selectedProducts.push(productoData);
        });

        localStorage.setItem("selectedProductos", JSON.stringify(selectedProducts));

        window.location.href = "vistaCatalogo.html";
    }
});