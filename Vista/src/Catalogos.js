document.getElementById('botonBuscarCatalogos').addEventListener('click',async (event)=>{
    event.preventDefault();

    const codigoTienda = localStorage.getItem('codigoTienda');
    console.log(JSON.stringify({codigoTienda}));
    try{
        const response = await fetch('http://localhost:5050/catalogoslista',{
            method: 'POST',
            headers:{
                'Content-Type': 'application/json',
            },
            body: JSON.stringify({codigoTienda})
        });

        const data = await response.json();
        const ul = document.getElementById('catalogoList');
        ul.innerHTML = '';
        // Loop through each catalog
        data.forEach(catalogo => {
            
            // Create a list item for each catalog
            const catalogoItem = document.createElement('li');
            catalogoItem.textContent = catalogo.nombre;
            // Create Delete button
            const deleteButton = document.createElement('button');
            deleteButton.textContent = 'Eliminar Catalogo';
            deleteButton.addEventListener('click', async () => {
                const idCatalogo = catalogo.idCatalogo;
                try {
                    await fetch(`http://localhost:5050/catalogos/${catalogo.idCatalogo}`, {
                        method: 'DELETE',
                        headers:{
                            'Content-Type': 'application/json',
                        },
                        body: JSON.stringify({idCatalogo})
                    });
                    alert(`Catalogo "${catalogo.nombre}" eliminado.`);
                    catalogoItem.remove();  // Remove from the UI
                } catch (error) {
                    console.error(`Error deleting catalogo ${catalogo.idCatalogo}:`, error);
                }
            });
            catalogoItem.appendChild(deleteButton);
            
            const pdfButton = document.createElement('button');
            pdfButton.textContent = 'PDF';
            pdfButton.addEventListener('click', async () => {
                const idCatalogo = catalogo.idCatalogo;
                const link = document.createElement('a');
                    link.href = `http://localhost:5050/catalogos/pdf/${catalogo.idCatalogo}`;
                    link.target = '_blank'; // opcional: abre el archivo en una nueva pestaña
                    link.download = `catalogo_${catalogo.idCatalogo}.pdf`; // opcional: nombre sugerido para la descarga
                    document.body.appendChild(link);
                    link.click();
                    document.body.removeChild(link);
            });
            catalogoItem.appendChild(pdfButton);

            // Create Edit button
            const editButton = document.createElement('button');
            editButton.textContent = 'Editar Catalogo';
            editButton.addEventListener('click', () => {
                // Show the edit form
                const editForm = document.createElement('div');
                editForm.innerHTML = `
                    <label>Nuevo Nombre: <input type="text" id="newCatalogName${catalogo.idCatalogo}" value="${catalogo.nombre}"></label>
                    <button id="addProduct${catalogo.idCatalogo}">Modificar Productos del catalogo</button>
                    <button id="saveChanges${catalogo.idCatalogo}">Guardar Cambios</button>
                    <h1 class="text-center mb-4">Seleccione los Productos que desea que contenga el Catalogo</h1>
                `;
                //Modificar para poder seleccionar productos del stock 
                // Handle adding products (extend as needed)
                const addProductButton = editForm.querySelector(`#addProduct${catalogo.idCatalogo}`);
                addProductButton.addEventListener('click', async() => {

                    const nombreProducto = null;
                    const codigoProducto = null;
                    const talle = null;
                    const color = null;
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
                            throw new Error(errorData.error.message || 'Ocurrió un error');
                        }

                        const data = await response.json();
        
                        data.stocksCompleto.forEach(stockCompleto =>{

                            const li = document.createElement('li');
                            li.innerHTML = `
                            <input type="checkbox" class="producto-select" data-id="${stockCompleto.codigoProducto}"
                            data-talle="${stockCompleto.talle}" data-color="${stockCompleto.color}" data-nombre="${stockCompleto.nombreProducto}"
                            data-idproducto="${stockCompleto.idProducto}">
                            <span><img src="${stockCompleto.foto}" class="card-img-top" alt="Product Image" style="height:150px;width:150px;"/></span>
                            <span>CodigoProducto: ${stockCompleto.codigoProducto}</span><br>
                            <span>NombreProducto: ${stockCompleto.nombreProducto}</span><br>
                            <span>Talle: ${stockCompleto.talle}</span><br>
                            <span>Color: ${stockCompleto.color}</span><br>
                            <span>Habilitado: ${stockCompleto.habilitado ? '✔️' : '❌'}</span>
                            <span>Cantidad: ${stockCompleto.cantidad}</span><br>
                            `;
                            editForm.appendChild(li);
            
                        });

                    } catch (error) {
                        document.getElementById('errores').innerText = `Error: ${error.message}`;
                        document.getElementById('errores').style.display = 'block';
                    }


                });

                // Handle saving changes
                const saveChangesButton = editForm.querySelector(`#saveChanges${catalogo.idCatalogo}`);
                saveChangesButton.addEventListener('click', async () => {
                    const nombre = document.getElementById(`newCatalogName${catalogo.idCatalogo}`).value;
                    const idCatalogo = catalogo.idCatalogo;
                    const codigoTienda = localStorage.getItem('codigoTienda');
                    const ids = [];
                    const selectedProductos = [];
                document.querySelectorAll('.producto-select:checked').forEach(function(checkbox) {
                    const idProducto = checkbox.getAttribute('data-idproducto');
                    const productoColor = checkbox.getAttribute('data-color');
                    const productoTalle = checkbox.getAttribute('data-talle');

                    const productoData = {
                        idProducto: idProducto,
                        productoColor: productoColor,
                        productoTalle: productoTalle
                    };

                selectedProductos.push(productoData);
                });

            localStorage.setItem("selectedProductos", JSON.stringify(selectedProductos));
            JSON.parse(localStorage.getItem("selectedProductos")).forEach(producto =>{
                ids.push(Number(producto.idProducto));
            });
                    //guardar la lista de ids de los productos en ids
                    console.log(JSON.stringify({idCatalogo, nombre, codigoTienda, ids}));
                    try {
                        await fetch('http://localhost:5050/catalogos', {
                            method: 'PATCH',
                            headers: {
                                'Content-Type': 'application/json',
                            },
                            body: JSON.stringify({idCatalogo, nombre, codigoTienda, ids})
                        });
                        alert(`Catalogo "${catalogo.nombre}" actualizado a "${nombre}".`);
                    } catch (error) {
                        console.error(`Error updating catalogo ${catalogo.idCatalogo}:`, error);
                    }
                });

                catalogoItem.appendChild(editForm);
            });
            catalogoItem.appendChild(editButton);

            ul.appendChild(catalogoItem);
            console.log(catalogo.nombre)
            // Create a sub-list for products within the catalog
            const productList = document.createElement('ul');
            catalogoItem.appendChild(productList);

            catalogo.productos.forEach(producto => {
                // Create a list item for each product
                const productItem = document.createElement('li');
                
                // Create a div for product details
                productItem.innerHTML = `
                    <strong>${producto.nombre}</strong> - Talle: ${producto.talle} - Color: ${producto.color}
                    <br><img src="${producto.foto}" alt="${producto.nombre}" width="100">
                `;
                
                productList.appendChild(productItem);
            });
        });

    }catch(error){
        console.error('Error fetching catalog data:', error);
    }
});

document.getElementById('botonGenerarCatalogo').addEventListener('click',async(event)=>{
    document.getElementById('errores').style.display = 'hidden';
    if(event.target && event.target.id === 'botonGenerarCatalogo'){

        const selectedProducts = [];
        document.querySelectorAll('.producto-select:checked').forEach(function(checkbox){

            const productoCodigo = checkbox.getAttribute('data-idproducto');
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
        if(document.querySelectorAll('.producto-select:checked').length >0){
            window.location.href = "vistaCatalogo.html";
        }else{
            document.getElementById('errores').innerText = `Debe seleccionar al menos 1 producto para generar un catálogo`;
            document.getElementById('errores').style.display = 'block';
        }
    }
});