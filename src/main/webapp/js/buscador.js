const MAX_OPTIONS = 6;

let optionCount = 0;
const optionInput = document.getElementById('add-ingredient');
const addOptionButton = document.getElementById('add-ingredient-button');
const optionsContainer = document.getElementById('ingredients-container');
const searchButton = document.getElementById('search-button');
const form = document.getElementById('form');
const addedOptions = [];

addOptionButton.addEventListener('click', addOption);

optionInput.addEventListener('keydown', (event) => {
    if (event.keyCode === 13) {
        addOption(event);
    }
});

searchButton.addEventListener('click', search)


function addOption(event) {
    event.preventDefault();

    if (optionCount >= MAX_OPTIONS) {
        alert(`Se ha alcanzado el límite máximo de ${MAX_OPTIONS} opciones.`);
        return;
    }

    const optionValue = optionInput.value.trim();
    if (optionValue) {

        // Comprobar si el ingrediente ya está agregado
        if (addedOptions.includes(optionValue)) {
            optionInput.value = '';
            return;
        }

        // Crear un elemento de opción y agregarlo al contenedor de opciones
        const option = document.createElement('div');
        option.classList.add('option');
        const optionText = document.createTextNode(optionValue);
        option.appendChild(optionText);
        optionsContainer.appendChild(option);

        // Agregar un botón para eliminar la opción
        const deleteButton = document.createElement('button');
        deleteButton.classList.add('delete-button');
        const buttonText = document.createTextNode('X');
        deleteButton.appendChild(buttonText);
        option.appendChild(deleteButton);

        // Agregar un controlador de eventos para el botón de eliminar
        deleteButton.addEventListener('click', () => {
            option.remove();
            optionCount--;
            addedOptions.splice(addedOptions.indexOf(optionValue), 1); // Eliminar la opción del array
        });

        optionCount++;
        addedOptions.push(optionValue); // Agregar la opción al array
        optionInput.value = '';
    }

}

function search() {
    const options = optionsContainer.querySelectorAll('.option');
    if (options.length < 3) {
        alert('Se necesitan al menos 3 opciones para realizar la búsqueda.');
        return;
    }

    for (let i = 0; i < addedOptions.length; i++) {
        const input = document.createElement('input');
        input.setAttribute('type', 'hidden');
        input.setAttribute('name', 'ingredientes[]'); // nombre del parámetro que se enviará
        input.setAttribute('value', addedOptions[i]);

        form.appendChild(input);
    }
    // Enviar el formulario
    form.submit();


}

