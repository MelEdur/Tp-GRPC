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
                'Authorization': `Bearer ${localStorage.getItem('jwt')}`
            },
            body: JSON.stringify({nombre,codigoTienda})
        });

        if(!response.ok){
            const errorData = await response.json();
            throw new Error(errorData.error.message || 'Ocurrió un error');
        }

        console.log(localStorage.getItem('jwt'));
        const data = await response.json();
        //Borrar resultados previos
        ul.innerHTML = '';

        data.usuarios.forEach(usuario =>{
            const li = document.createElement('li');
            li.innerHTML = `
            <span>Usuario: ${usuario.nombreUsuario}</span>
            <span>Nombre: ${usuario.nombre}</span>
            <span>Apellido: ${usuario.apellido}</span>
            <span>Tienda: ${usuario.codigoTienda}</span>
            <span>Habilitado: ${usuario.habilitado ? '✔️' : '❌'}</span>
            <button type="button" id="botonEliminarUsuario" class="btn btn-danger" data-id=${usuario.id}">Borrar</button>
            <button type="button" id="botonModificarUsuarioFormulario" class="btn btn-primary" data-usuario='${JSON.stringify(usuario)}'>Modificar</button>
            `;
            ul.appendChild(li);
        });
        searchFields.style.display = 'block';



    } catch (error) {
        document.getElementById('errores').innerText = `Error: ${error.message}`;
        document.getElementById('errores').style.display = 'block';
    }
});

document.getElementById('botonAgregarUsuario').addEventListener('click', async (event)=>{
    event.preventDefault();
    const nombreUsuario = document.getElementById('usuarioCrearUsuario').value;
    const contrasenia = document.getElementById('contraseniaCrearUsuario').value;
    const nombre = document.getElementById('nombreCrearUsuario').value;
    const apellido = document.getElementById('apellidoCrearUsuario').value;
    const codigoTienda = document.getElementById('tiendaCrearUsuario').value;
    const habilitado = document.getElementById('habilitadoCrearUsuario').checked;

    try {
        const response = await fetch('http://localhost:5000/usuarios',{
            method: 'POST',
            headers:{
                'Content-Type': 'application/json',
            },
            body: JSON.stringify({nombreUsuario,contrasenia,nombre,apellido,habilitado,codigoTienda})
        });

        if(!response.ok){
            const errorData = await response.json();
            throw new Error(errorData.error.message || 'Ocurrió un error');
        }

        const data = await response.json();
        document.getElementById('mensajeCrearUsuario').innerText = `Usuario Agregado correctamente`;
        document.getElementById('mensajeCrearUsuario').style.color = 'green';
        document.getElementById('mensajeCrearUsuario').style.display = 'block';
        ul.innerHTML = '';
    } catch (error) {
        document.getElementById('mensajeCrearUsuario').innerText = `Error: ${error.message}`;
        document.getElementById('mensajeCrearUsuario').style.color = 'crimson';
        document.getElementById('mensajeCrearUsuario').style.display = 'block';
    }
});

ul.addEventListener('click', function(event){
    if(event.target && event.target.id === 'botonModificarUsuarioFormulario'){
        const usuario = JSON.parse(event.target.getAttribute('data-usuario'));
        document.getElementById('idModificarUsuario').value = usuario.id;
        document.getElementById('usuarioModificarUsuario').value = usuario.nombreUsuario;
        document.getElementById('nombreModificarUsuario').value = usuario.nombre;
        document.getElementById('apellidoModificarUsuario').value = usuario.apellido;
        document.getElementById('tiendaModificarUsuario').value = usuario.codigoTienda;
        document.getElementById('habilitadoModificarUsuario').checked = usuario.habilitado;

        $('#modificarUsuarioModal').modal('show');
    }
});

document.querySelectorAll('[id="cerrarModificarFormulario"]').forEach(button =>{
    button.addEventListener('click', function(){
        $('#modificarUsuarioModal').modal('hide');
    });
});

document.getElementById('botonModificarUsuario').addEventListener('click',async(event)=>{
    event.preventDefault();
    const id = Number(document.getElementById('idModificarUsuario').value);
    const nombreUsuario = document.getElementById('usuarioModificarUsuario').value;
    const contrasenia = document.getElementById('contraseniaModificarUsuario').value;
    const nombre = document.getElementById('nombreModificarUsuario').value;
    const apellido = document.getElementById('apellidoModificarUsuario').value;
    const codigoTienda = document.getElementById('tiendaModificarUsuario').value;
    const habilitado = document.getElementById('habilitadoModificarUsuario').checked;
    try{
        console.log(JSON.stringify({id,nombreUsuario,contrasenia,nombre,apellido,habilitado,codigoTienda}));
        const response = await fetch('http://localhost:5000/usuarios',{
            method: 'PATCH',
            headers:{
                'Content-Type': 'application/json',
            },
            body: JSON.stringify({id,nombreUsuario,contrasenia,nombre,apellido,habilitado,codigoTienda})
        });
        if(!response.ok){
            const errorData = await response.json();
            throw new Error(errorData.error.message || 'Ocurrió un error');
        }

        const data = await response.json();
        document.getElementById('mensajeModificarUsuario').innerText = `Usuario modificado correctamente`;
        document.getElementById('mensajeModificarUsuario').style.color = 'green';
        document.getElementById('mensajeModificarUsuario').style.display = 'block';
        ul.innerHTML = '';

    }catch(error){
        document.getElementById('mensajeModificarUsuario').innerText = `Error: ${error.message}`;
        document.getElementById('mensajeModificarUsuario').style.color = 'crimson';
        document.getElementById('mensajeModificarUsuario').style.display = 'block';
    }
});