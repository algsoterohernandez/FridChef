const addIngredientButton = document.querySelector('#add-ingredient');
addIngredientButton.addEventListener('click', addIngredient)

//array para almacenar los ingredientes
let addedIngredients = [];
function addIngredient($event) {
    $event.stopPropagation();
    $event.preventDefault();
    const ingredientSelect = (
        document.querySelector('#ingredient option:checked') ||
        document.querySelector('#ingredient option:first-of-type')
    );
    const ingredientName = ingredientSelect.innerHTML.trim();
    const idIngredient = ingredientSelect.getAttribute('value');
    const unit = (
        document.querySelector('#unit option:checked') ||
        document.querySelector('#unit option:first-of-type')
    ).getAttribute('value');
    const quantity = document.querySelector('#quantity').value;

    if(!Number.isInteger(parseFloat(quantity))){
        alert('La cantidad debe ser un número entero.');
        return;
    }

    //se comprueba si el ingrediente ya ha sido agregado
    const isDuplicate = addedIngredients.includes(parseInt(idIngredient));
    if(isDuplicate){
        alert('El ingrediente ya ha sido agregado. Puedes agregar otro ingrediente o eliminarlo si la cantidad es errónea');
        return;
    }

    //se limita el número de ingredientes a un máximo de 10
    if(addedIngredients.length>=10){
        alert('Has alcanzado el número máximo de ingredientes que puedes registrar para esta receta. Si necesitas añadir algún ingrediente, puedes indicarlo en el apartado CONTACTOS!');
        return;
    }

    //Se comprueba si la cantidad es un número entero
    const quantityValue = Number(quantity);

    if(!Number.isInteger(quantityValue) || quantityValue.toString().includes('.') || quantityValue.toString().includes(',')){
        alert('La cantidad debe ser un número entero');
        document.querySelector('#quantity').value = '';
        return;
    }

    const html = `
        <div class="ingredient-wrapper">
            <span class="ingredient-name">${ingredientName}</span>
            <span class="ingredient-unit">${quantity}</span>
            <span class="ingredient-quantity">${unit}</span>
            <button>X</button>
            <input type="hidden" class="ingredient" name="ingredient[]" value="${idIngredient}">
            <input type="hidden" name="unit[]" value="${unit}">
            <input type="hidden" name="quantity[]" value="${quantity}">
        </div>
        `;
    const element = new DOMParser().parseFromString(html, "text/html")
    element.querySelector('button').addEventListener('click', removeIngredient)
    const parentContainer = document.querySelector('.ingredients-container');
    parentContainer.insertBefore(element.querySelector('body').firstChild, parentContainer.lastChild);

    //Agregar el ingrediente al array de ingredientes agregados
    addedIngredients.push(parseInt(idIngredient));
}

function removeIngredient($event) {
    $event.stopPropagation();
    const idIngredient = $event.target.parentElement.querySelector('.ingredient').getAttribute('value');
    $event.target.parentElement.remove();
    addedIngredients = addedIngredients.filter(id => id !== parseInt(idIngredient));
}