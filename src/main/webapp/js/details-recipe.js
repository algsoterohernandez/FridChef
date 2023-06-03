const favoriteButton = document.getElementById("favoriteButton");

favoriteButton.addEventListener("click", function () {
    debugger;
    const isFavorite = favoriteButton.classList.contains('is-favorite');
    const idRecipe = favoriteButton.getAttribute("recipe");
    if (isFavorite) {
        removeFromFavorites(idRecipe);
    } else {
        addToFavorites(idRecipe);
    }
});

function addToFavorites(idRecipe) {
    favoriteButton.classList.add("is-favorite");
    request("add", idRecipe)
}

function removeFromFavorites(idRecipe) {
    favoriteButton.classList.remove("is-favorite");
    request("remove", idRecipe)

}

function request(action = '', idRecipe = '') {
    try {
        fetch(
            "/FridChef/favorite?id_recipe=" + idRecipe + "&recipe_favorite=" + action,
            {
                method: 'POST',
            }).then(() => {
            debugger
        });
    } catch (err) {

    }
}

