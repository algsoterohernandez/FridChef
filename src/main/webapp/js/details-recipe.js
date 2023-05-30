 function toggleFavorite() {
        var button = document.querySelector('.favorite-button');
        button.classList.toggle('active');

        if (button.classList.contains('active')) {
            console.log('Agregado a favoritos');
            AgregarFavorito();
        } else {
            console.log('Eliminado de favoritos');
            EliminarFavorito();
        }
     function AgregarFavorito() {
            console.log('Agregar a favoritos');
     }

     function EliminarFavorito() {
         console.log('Eliminar de favoritos');
     }

 }


