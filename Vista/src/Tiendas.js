document.getElementById('botonBuscarTiendas').addEventListener('click', async (event)=>{
    event.preventDefault();
    const searchFields = document.querySelector('.results');
    const inputSearch = document.getElementById("searchInputTiendas").value;
    const checkbox = document.getElementById("boolHabilitada");
    const isChecked = checkbox.checked;

    if(!inputSearch){
        try {
            const response = await fetch('http://localhost:5000/tiendas',{
                method: 'GET',
                headers:{
                    'Content-Type': 'application/json',
                    'Authorization': `Bearer ${localStorage.getItem('jwt')}`
                }
            });
    
            if(!response.ok){
                const errorData = await response.json();
                throw new Error(errorData.error.message || 'Ocurrió un error');
            }
    
            console.log(localStorage.getItem('jwt'));
            const data = await response.json();
            //Borrar resultados previos
            ul.innerHTML = '';
    
            data.tiendas.forEach(tienda =>{
                const li = document.createElement('li');
                li.innerHTML = `
                <span>Codigo Tienda: ${tienda.codigoTienda}</span>
                <span>Direccion: ${tienda.direccion}</span>
                <span>Ciudad: ${tienda.ciudad}</span>
                <span>Provincia: ${tienda.provincia}</span>
                <span>Habilitado: ${tienda.habilitada ? '✔️' : '❌'}</span>
                <button type="button" id="botonModificarTiendaFormulario" class="btn btn-primary" data-tienda='${JSON.stringify(tienda)}'>Modificar</button>
                `;
                ul.appendChild(li);
            });
            searchFields.style.display = 'block';
    
    
    
        } catch (error) {
            document.getElementById('errores').innerText = `Error: ${error.message}`;
            document.getElementById('errores').style.display = 'block';
        }
    }else{
        try {
            const filtro = `codigoTienda:${inputSearch},provincia:${inputSearch},habilitada:${isChecked}`;
            
            const response = await fetch('http://localhost:5000/tiendasPorFiltro',{
                method: 'POST',
                headers:{
                    'Content-Type': 'application/json',
                    'Authorization': `Bearer ${localStorage.getItem('jwt')}`
                },
                body: JSON.stringify({filtro: filtro})
            });
    
            if(!response.ok){
                const errorData = await response.json();
                throw new Error(errorData.error.message || 'Ocurrió un error');
            }
    
            console.log(localStorage.getItem('jwt'));
            const data = await response.json();
            //Borrar resultados previos
            ul.innerHTML = '';
    
            data.tiendas.forEach(tienda =>{
                const li = document.createElement('li');
                li.innerHTML = `
                <span>Codigo Tienda: ${tienda.codigoTienda}</span>
                <span>Direccion: ${tienda.direccion}</span>
                <span>Ciudad: ${tienda.ciudad}</span>
                <span>Provincia: ${tienda.provincia}</span>
                <span>Habilitado: ${tienda.habilitada ? '✔️' : '❌'}</span>
                <button type="button" id="botonModificarTiendaFormulario" class="btn btn-primary" data-tienda='${JSON.stringify(tienda)}'>Modificar</button>
                `;
                ul.appendChild(li);
            });
            searchFields.style.display = 'block';
    
    
    
        } catch (error) {
            document.getElementById('errores').innerText = `Error: ${error.message}`;
            document.getElementById('errores').style.display = 'block';
        }
    }
    
});

document.getElementById('botonAgregarTienda').addEventListener('click', async (event)=>{
    event.preventDefault();
    const ciudad = document.getElementById('ciudadCrearTienda').value;
    const codigoTienda = document.getElementById('codigoTiendaCrearTienda').value;
    const direccion = document.getElementById('direccionCrearTienda').value;
    const provincia = document.getElementById('provinciaCrearTienda').value;
    const habilitada = document.getElementById('habilitadaCrearTienda').checked;

    try {
        const response = await fetch('http://localhost:5000/tienda',{
            method: 'POST',
            headers:{
                'Content-Type': 'application/json',
                'Authorization': `Bearer ${localStorage.getItem('jwt')}`
            },
            body: JSON.stringify({ciudad,codigoTienda,direccion,habilitada,provincia})
        });

        if(!response.ok){
            const errorData = await response.json();
            throw new Error(errorData.error.message || 'Ocurrió un error');
        }

        const data = await response.json();
        document.getElementById('mensajeCrearTienda').innerText = `Tienda Agregado correctamente`;
        document.getElementById('mensajeCrearTienda').style.color = 'green';
        document.getElementById('mensajeCrearTienda').style.display = 'block';
        ul.innerHTML = '';
    } catch (error) {
        document.getElementById('mensajeCrearTienda').innerText = `Error: ${error.message}`;
        document.getElementById('mensajeCrearTienda').style.color = 'crimson';
        document.getElementById('mensajeCrearTienda').style.display = 'block';
    }
});

ul.addEventListener('click', function(event){
    if(event.target && event.target.id === 'botonModificarTiendaFormulario'){
        const tienda = JSON.parse(event.target.getAttribute('data-tienda'));
        document.getElementById('codigoTiendaModificarTienda').value = tienda.codigoTienda;
        document.getElementById('provinciaModificarTienda').value = tienda.provincia;
        document.getElementById('ciudadModificarTienda').value = tienda.ciudad;
        document.getElementById('direccionModificarTienda').value = tienda.direccion;
        document.getElementById('habilitadaModificarTienda').checked = tienda.habilitada;

        $('#modificarTiendaModal').modal('show');
    }
});

document.querySelectorAll('[id="cerrarModificarFormulario"]').forEach(button =>{
    button.addEventListener('click', function(){
        $('#modificarTiendaModal').modal('hide');
    });
});

document.getElementById('botonModificarTienda').addEventListener('click',async(event)=>{
    event.preventDefault();
    const codigoTienda = document.getElementById('codigoTiendaModificarTienda').value;
    const provincia = document.getElementById('provinciaModificarTienda').value;
    const ciudad = document.getElementById('ciudadModificarTienda').value;
    const direccion = document.getElementById('direccionModificarTienda').value;
    const habilitada = document.getElementById('habilitadaModificarTienda').checked;
    try{
        console.log(JSON.stringify({ciudad,codigoTienda,direccion,habilitada,provincia}));
        const response = await fetch('http://localhost:5000/tienda',{
            method: 'PATCH',
            headers:{
                'Content-Type': 'application/json',
                'Authorization': `Bearer ${localStorage.getItem('jwt')}`
            },
            body: JSON.stringify({ciudad,codigoTienda,direccion,habilitada,provincia})
        });
        if(!response.ok){
            const errorData = await response.json();
            throw new Error(errorData.error.message || 'Ocurrió un error');
        }

        const data = await response.json();
        document.getElementById('mensajeModificarTienda').innerText = `Tienda modificado correctamente`;
        document.getElementById('mensajeModificarTienda').style.color = 'green';
        document.getElementById('mensajeModificarTienda').style.display = 'block';
        ul.innerHTML = '';

    }catch(error){
        document.getElementById('mensajeModificarTienda').innerText = `Error: ${error.message}`;
        document.getElementById('mensajeModificarTienda').style.color = 'crimson';
        document.getElementById('mensajeModificarTienda').style.display = 'block';
    }
});