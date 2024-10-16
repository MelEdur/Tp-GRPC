// Obtener contenedor de productos
const productosContainer = document.getElementById('productosContainer');

// Modal y formulario
const modal = document.getElementById('productModal');
const form = document.getElementById('productForm');
const tallesContainer = document.getElementById('tallesContainer');
const coloresContainer = document.getElementById('coloresContainer');
const fotosContainer = document.getElementById('fotosContainer');
const codigoProductoInput = document.getElementById('codigoProducto');

//ESTE ES UN CONTROLLER EN EL SERVIDOR JAVA
    const response = fetch('http://localhost:8080/producto/proveedor',{
        method: 'GET',
        headers:{
            'Content-Type': 'application/json',
            //'Authorization': `Bearer ${localStorage.getItem('jwt')}`
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
        console.log(data);
        console.log(localStorage.getItem('jwt'));
        mostrarProductos(data);
    });
// Función para mostrar los productos en forma de cartas
function mostrarProductos(productos) {
    productos.forEach(producto => {
        const card = document.createElement('div');
        card.classList.add('card');
        
        card.innerHTML = `
            <img src="${producto.fotos[0]}" alt="Producto">
            <h3>Código: ${producto.codigo}</h3>
            <p>Talles: ${producto.talles.join(', ')}</p>
            <p>Colores: ${producto.colores.join(', ')}</p>
            <button onclick="abrirFormulario('${producto.codigo}', '${producto.talles}', '${producto.colores}', '${producto.fotos}')">Seleccionar</button>
        `;

        productosContainer.appendChild(card);
    });
}

// Función para abrir el formulario con la información del producto
function abrirFormulario(codigo, talles, colores, fotos) {
    // Asignar el código del producto al input hidden
    codigoProductoInput.value = codigo;

    // Limpiar el contenedor de talles y colores
    tallesContainer.innerHTML = '';
    coloresContainer.innerHTML = '';
    fotosContainer.innerHTML = '';

    // Crear checkboxes para talles
    talles.split(',').forEach(talle => {
        const label = document.createElement('label');
        label.innerHTML = `
            <input type="checkbox" name="talles" value="${talle.trim()}"> ${talle.trim()}
        `;
        tallesContainer.appendChild(label);
    });

    // Crear checkboxes para colores
    colores.split(',').forEach(color => {
        const label = document.createElement('label');
        label.innerHTML = `
            <input type="checkbox" name="colores" value="${color.trim()}"> ${color.trim()}
        `;
        coloresContainer.appendChild(label);
    });

    fotos.split(',').forEach(foto => {
        const label = document.createElement('label');
        label.innerHTML = `
            <input type="checkbox" name="fotos" value="${foto.trim()}"> ${foto.trim()}
        `;
        fotosContainer.appendChild(label);
    });

    // Mostrar el modal
    modal.style.display = 'block';
}

// Cerrar modal
document.querySelector('.close').onclick = function() {
    modal.style.display = 'none';
}

// Enviar los datos seleccionados al servidor
document.getElementById('botonNuevoProducto').addEventListener('click', async (event)=>{
    event.preventDefault();

    const nombreProducto = document.getElementById('nombre').value;
    const codigoProducto = codigoProductoInput.value;

    const tallesSeleccionados = Array.from( document.querySelectorAll('input[name="talles"]:checked'))
                                      .map(talle => ({ talle: talle.value }));

    const coloresSeleccionados = Array.from(document.querySelectorAll('input[name="colores"]:checked'))
                                       .map(color => ({ color: color.value }));

    const fotosSeleccionados = Array.from(document.querySelectorAll('input[name="fotos"]:checked'))
                                       .map(foto => ({ foto: foto.value }));

    const ids = document.getElementById('tiendas').value.split(',').map(tienda => ({ id: tienda.trim() }));

    const data = {
        codigoProducto,
        nombreProducto,
        talles: tallesSeleccionados,
        colores: coloresSeleccionados,
        fotos: fotosSeleccionados,
        ids
    };
    console.log(JSON.stringify(data));
    console.log(localStorage.getItem('jwt'));
    // Enviar datos al servidor
    try {
	//METODO IMPLEMENTADO EN EL CLIENTE PYTHON
        const response = await fetch('http://localhost:5000/productos/proveedor', {
        method: 'POST',
        headers: {
            'Content-Type': 'application/json',
            'Authorization': `Bearer ${localStorage.getItem('jwt')}`
        },
        body: JSON.stringify(data),
        //SIN LA SIGUIENTE LINEA TIRA ERROR DE CORS
        mode: 'no-cors'
    });
    
    if(!response.ok){
        const errorData = await response.json();
        console.log(errorData);
        throw new Error(errorData.error.message || 'Ocurrió un error');
    }
    } catch (error) {
    document.getElementById('errores').innerText = `Error: ${error.message}`;
    document.getElementById('errores').style.display = 'block';
    }
});

// Cerrar modal si se hace clic fuera del modal
window.onclick = function(event) {
    if (event.target == modal) {
        modal.style.display = 'none';
    }
}