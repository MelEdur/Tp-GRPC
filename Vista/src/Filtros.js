const rolActual = localStorage.getItem('rol');

document.addEventListener("DOMContentLoaded", function() {
    const idUsuario = localStorage.getItem('idUsuario');

    fetch('http://localhost:5050/filtrosTraer', {
        method: 'POST',
        headers: {
            'Content-Type': 'application/json',
        },
        body: JSON.stringify({ idUsuario })
    })
    .then(response => {
        if (!response.ok) {
            throw new Error('Network response was not ok');
        }
        return response.json();
    })
    .then(data => {

        const sel = document.getElementById('floatingSelectGrid');

        data.forEach(filtro => {
            const op = document.createElement('option');

            op.className = 'filtrosOpciones';
            op.value = `${filtro.idFiltro}+${filtro.codigoProducto} + ${filtro.codigoTienda} + ${filtro.estado} + ${filtro.fechaDesde} + ${filtro.fechaHasta}`; // Use a unique value if needed
            op.textContent = `${filtro.codigoProducto} + ${filtro.codigoTienda} + ${filtro.estado} + ${filtro.fechaDesde} + ${filtro.fechaHasta}`;
            sel.appendChild(op);
        });

        sel.addEventListener('change', (event) => {
            const valorSeleccionado = event.target.value; // Obtiene el valor de la opción seleccionada
            const valores = obtenerCamposDeFiltro(valorSeleccionado);

            document.getElementById('searchInformeCodProducto').value= valores.codigoProducto == 'null ' ? '' : valores.codigoProducto.replace(/\s+/g, '');
            document.getElementById('searchInformeFechaDesde').value= valores.fechaDesde.replace(/\s+/g, '');
            document.getElementById('searchInformeFechaHasta').value= valores.fechaHasta.replace(/\s+/g, '');
            document.getElementById('searchInformeEstado').value= valores.estado.replace(/\s+/g, '');
            if(rolActual != "USUARIO"){
                document.getElementById('searchInformeCodTienda').value= valores.codigoTienda == ' null ' ? '' : valores.codigoTienda.replace(/\s+/g, '');
            }
        });
    })
    .catch(error => {
        console.error('Error:', error);
    });
});

document.getElementById('botonAgregarFiltro').addEventListener('click', async(event)=>{
    const codigoProducto = document.getElementById('searchInformeCodProducto').value;
    const fechaDesde = document.getElementById('searchInformeFechaDesde').value;
    const fechaHasta = document.getElementById('searchInformeFechaHasta').value;
    const estado = document.getElementById('searchInformeEstado').value;

    let codigoTienda = "";
    if(rolActual == "USUARIO"){
        const tiendaActual = localStorage.getItem('codigoTienda');
        codigoTienda = tiendaActual;
    }else{
        codigoTienda = document.getElementById('searchInformeCodTienda').value;
    }

    const idUsuario = localStorage.getItem('idUsuario');

    console.log(fechaDesde);

    const response = await fetch('http://localhost:5050/filtros',{
        method: 'POST',
        headers:{
            'Content-Type': 'application/json',
        },
        body: JSON.stringify({codigoProducto,fechaDesde,fechaHasta,estado,codigoTienda,idUsuario})
    }).then(alert("Filtro agregado"));
});


function obtenerCamposDeFiltro(value) {
    const [idFiltro, codigoProducto, codigoTienda, estado, fechaDesde, fechaHasta] = value.split('+');

    return {
        idFiltro,
        codigoProducto,
        codigoTienda,
        estado,
        fechaDesde,
        fechaHasta
    };
}

document.getElementById('botonEditarFiltro').addEventListener('click', function(){
    const selectElement = document.getElementById('floatingSelectGrid');
    const selectedOption = selectElement.options[selectElement.selectedIndex];

    const codigoProducto = document.getElementById('searchInformeCodProducto').value;
    const fechaDesde = document.getElementById('searchInformeFechaDesde').value;
    const fechaHasta = document.getElementById('searchInformeFechaHasta').value;
    const estado = document.getElementById('searchInformeEstado').value;

    let codigoTienda = "";
    if(rolActual == "USUARIO"){
        const tiendaActual = localStorage.getItem('codigoTienda');
        codigoTienda = tiendaActual;
    }else{
        codigoTienda = document.getElementById('searchInformeCodTienda').value;
    }

    const idFiltro = obtenerCamposDeFiltro(selectedOption.value).idFiltro;

    fetch('http://localhost:5050/filtros',{
        method: 'PATCH',
        headers:{
            'Content-Type': 'application/json',
        },
        body: JSON.stringify({codigoProducto,fechaDesde,fechaHasta,estado,codigoTienda,idFiltro})
    }).then(alert("Filtro Editado"));
});

document.getElementById('botonBorrarFiltro').addEventListener('click',function(){
    const selectElement = document.getElementById('floatingSelectGrid');
    const selectedOption = selectElement.options[selectElement.selectedIndex];
    const idFiltro = obtenerCamposDeFiltro(selectedOption.value).idFiltro;

    fetch(`http://localhost:5050/filtros/${idFiltro}`,{
        method: 'DELETE',
        headers:{
            'Content-Type': 'application/json',
        },
    }).then(alert("Filtro eliminado"));

    selectElement.remove(selectElement.selectedIndex);
});