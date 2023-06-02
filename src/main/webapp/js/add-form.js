const addIngredientButton = document.querySelector('#add-ingredient');
addIngredientButton.addEventListener('click', addIngredient)

function addIngredient($event) {
    $event.stopPropagation();
    $event.preventDefault();
    const ingredientSelect = (
        document.querySelector('#ingredient option:checked') ||
        document.querySelector('#ingredient option:first-of-type')
    )
    const ingredientName = ingredientSelect.innerHTML.trim();
    const idIngredient = ingredientSelect.getAttribute('value');
    const unit = (
        document.querySelector('#unit option:checked') ||
        document.querySelector('#unit option:first-of-type')
    ).getAttribute('value');
    const quantity = document.querySelector('#quantity').value;
<<<<<<< Updated upstream
=======

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
        alert('Has alcanzado el número máximo de ingredientes. Si necesitas añadir algún ingrediente, puedes indicarlo en el apartado CONTACTOS!');
        return;
    }

    //Se comprueba si la cantidad es un número entero
    const quantityValue = Number(quantity);

    if(!Number.isInteger(quantityValue) || quantityValue.toString().includes('.') || quantityValue.toString().includes(',')){
        alert('La cantidad debe ser un número entero');
        document.querySelector('#quantity').value = '';
        return;
    }

>>>>>>> Stashed changes
    const html = `
        <div class="ingredient-wrapper">
            <span class="ingredient-name">${ingredientName}</span>
            <span class="ingredient-unit">${quantity}</span>
            <span class="ingredient-quantity">${unit}</span>
            <button>X</button>
            <input type="hidden" name="ingredient[]" value="${idIngredient}">
            <input type="hidden" name="unit[]" value="${unit}">
            <input type="hidden" name="quantity[]" value="${quantity}">
        </div>
        `;
    const element = new DOMParser().parseFromString(html, "text/html")
    element.querySelector('button').addEventListener('click', removeIngredient)
    const parentContainer = document.querySelector('.ingredients-container');
    parentContainer.insertBefore(element.querySelector('body').firstChild, parentContainer.lastChild);
}

function removeIngredient($event) {
    $event.stopPropagation();
    $event.target.parentElement.remove();
<<<<<<< Updated upstream
}
=======
    addedIngredients = addedIngredients.filter(id => id !== parseInt(idIngredient));
}
>>>>>>> Stashed changes
