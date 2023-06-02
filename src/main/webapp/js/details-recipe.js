var favoriteButton = document.getElementById("favoriteButton");
var isFavorite = false;

favoriteButton.addEventListener("click", function() {
    if (isFavorite) {
        removeFromFavorites();
    } else {
        addToFavorites();
    }
});

function addToFavorites() {
    favoriteButton.classList.add("is-favorite");
    isFavorite = true;
    var recipe = getRecipe(); // Obtener los datos de la receta actual, puedes adaptar esta función a tu caso
    saveToLocalStorage(recipe); // Guardar la receta en el localStorage
}

function removeFromFavorites() {
    favoriteButton.classList.remove("is-favorite");
    isFavorite = false;
    var recipe = getRecipe(); // Obtener los datos de la receta actual
    removeFromLocalStorage(recipe); // Eliminar la receta del localStorage
}

function getRecipe() {
    // Aquí puedes obtener los datos de la receta actual, por ejemplo, el ID o cualquier otro identificador único
    // Puedes adaptar esta función según cómo estés almacenando los datos de las recetas en tu aplicación
    // Retorna los datos de la receta actual en un objeto
    return {
        id: 123, // Ejemplo de ID de receta
        // Otros datos de la receta
    };
}

function saveToLocalStorage(recipe) {
    var favorites = JSON.parse(localStorage.getItem("favorites")) || [];
    favorites.push(recipe);
    localStorage.setItem("favorites", JSON.stringify(favorites));
}

function removeFromLocalStorage(recipe) {
    var favorites = JSON.parse(localStorage.getItem("favorites")) || [];
    var index = favorites.findIndex(function(fav) {
        return fav.id === recipe.id;
    });
    if (index > -1) {
        favorites.splice(index, 1);
        localStorage.setItem("favorites", JSON.stringify(favorites));
    }
}