document.getElementById('loginForm').addEventListener('submit', async (event) => {
    event.preventDefault();

    const usuario = document.getElementById('usuario').value;
    const contrasenia = document.getElementById('contrasenia').value;

    try{
        //Hacer Petición
        const response = await fetch('http://localhost:5000/login',{
            method: 'POST',
            headers:{
                'Content-Type': 'application/json',
            },
            body: JSON.stringify({usuario,contrasenia})
        });

        //Detectar si hay error
        if(!response.ok){
            const errorData = await response.json();
            throw new Error(errorData.error.message || 'Ocurrió un error');
        }

        //En caso de que todo salga bien
        const data = await response.json();
        localStorage.setItem('jwt',data.jwt);
        localStorage.setItem('usuario',data.usuario);
        localStorage.setItem('rol',data.rol);
        console.log(data);

        if (data.rol === 'ADMIN'){
            window.location.href = 'vistaUCC.html';
        }else if (data.rol ===  'USUARIO'){
            window.location.href = 'user-dashboard.html'
        }else{
            window.location.href = '/'
        }



    //Manejo del error
    } catch(error){
        document.getElementById('message').innerText = `${error.message}`;
    }
});

