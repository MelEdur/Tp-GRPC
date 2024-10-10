const ul = document.getElementById('stockLista');

fetch('http://localhost:8081/stocks')
.then(response => response.json())
.then(data =>{
    ul.innerHTML = '';

    data.forEach(stock =>{
        const li = document.createElement('li');

        li.innerHTML = `
        <span>Codigo: ${stock.codigo}</span><br>
        <span>Colores: ${stock.colores.join(', ')}</span><br>
        <span>Talles: ${stock.talles.join(', ')}</span><br>
        <span>Fotos: ${stock.fotos.map(foto => `<img src="${foto}" alt="foto" style="width:50px;height:50px;">`).join(' ')}</span><br>
        <label for="cantidad-${stock.codigo}">Cantidad:</label>
        <input type="number" id="cantidad-${stock.codigo}" name="cantidad" value="${stock.cantidad}" min="1"><br>
        <button class="boton-actualizar" data-codigo="${stock.codigo}">Actualizar</button>
        `;
        ul.appendChild(li);

        // Add event listener to the button
        const updateButton = li.querySelector('.boton-actualizar');
        updateButton.addEventListener('click', () => {
            const codigo = updateButton.getAttribute('data-codigo');
            const cantidadInput = document.getElementById(`cantidad-${codigo}`);
            let cantidad = document.getElementById(`cantidad-${codigo}`).value;
            if(cantidad< 0){
                cantidad = 0;
            }

            const StockData = {
                codigo: codigo,
                colores: [],
                talles: [],
                fotos: [],
                cantidad: parseInt(cantidad)
            };

            // Make a request to the API
            fetch('http://localhost:8081/stocks', {
                method: 'PUT',
                headers: {
                    'Content-Type': 'application/json'
                },
                body: JSON.stringify(StockData)
            })
            .then(response => response.json())
            .then(result => {
                console.log('Success:', result);
                cantidadInput.value = result;
            })
        });
    });
});