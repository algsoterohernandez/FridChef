const allergenFilterSelect = document.getElementById('allergen-filter-options');
const allergenFilterButton = document.getElementById('allergen-filter-button');

allergenFilterButton.addEventListener('click', filterByAllergen);

function filterByAllergen() {
    const selectedAllergen = allergenFilterSelect.value;

    if (selectedAllergen != 'none') {
        //Obtener sugerencias por la clase recipe-suggestions
        const suggestions = document.getElementsByClassName('recipe-suggestions');

        //Recorre las sugerencias
        for (let i = 0; i < suggestions.length; i++) {
            const suggestedRecipe = suggestions[i];
            let suggestedRecipeHidden = false;

            //Obtener alérgenos de la sugerencia
            const recipeAllergens = suggestedRecipe.getElementsByClassName('recipe-allergen');

            //Recorre los alérgenos de la sugerencia
            for (let j = 0; j < recipeAllergens.length; j++) {

                //Compara si hay coincidencia con el valor del filtro
                if (recipeAllergens[j].innerText === selectedAllergen) {
                    suggestedRecipe.style.display = 'none';
                    suggestedRecipeHidden = true;

                    break;
                }
            }

            if (!suggestedRecipeHidden) {
                suggestedRecipe.style.display = '';
            }
        }
    }
}