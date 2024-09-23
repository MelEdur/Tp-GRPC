const ul = document.getElementById('resultados');
document.getElementById('botonBuscarUsuarios').addEventListener('click', async (event)=>{
    event.preventDefault();
    const searchFields = document.querySelector('.results');


    const nombre =  document.getElementById('searchUsarioNombre').value;
    const codigoTienda = document.getElementById('searchUsarioTienda').value;

    try {
        const response = await fetch('http://localhost:5000/usuarios/filtro',{
            method: 'POST',
            headers:{
                'Content-Type': 'application/json',
            },
            body: JSON.stringify({nombre,codigoTienda})
        });

        if(!response.ok){
            const errorData = await response.json();
            throw new Error(errorData.error.message || 'Ocurrió un error');
        }

        const data = await response.json();
        ul.innerHTML = '';

        data.usuarios.forEach(usuario =>{
            const li = document.createElement('li');
            li.innerHTML = `
            <span>Usuario: ${usuario.nombreUsuario}</span>
            <span>Nombre: ${usuario.nombre}</span>
            <span>Apellido: ${usuario.apellido}</span>
            <span>Tienda: ${usuario.codigoTienda}</span>
            <button class="btn btn-danger data-id=${usuario.id}">Borrar</button>
            <button class="btn btn-primary" data-id=${usuario.id}>Modificar</button>
            `;
            ul.appendChild(li);
        });
        searchFields.style.display = 'block';



    } catch (error) {
        console.log(error.message);
    }
});