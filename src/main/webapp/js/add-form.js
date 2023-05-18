const addIngredientButton = document.querySelector('#add-ingredient');
addIngredientButton.addEventListener('click', addIngredient)

function addIngredient($event) {
    debugger
    $event.stopPropagation();
    const ingredientSelect = (
        document.querySelector('#ingredient option[selected]') ||
        document.querySelector('#ingredient option:first-of-type')
    )
    const ingredientName = ingredientSelect.innerHTML.trim();
    const idIngredient = ingredientSelect.getAttribute('value');
    const unit = (
        document.querySelector('#unit option[selected]') ||
        document.querySelector('#unit option:first-of-type')
    ).getAttribute('value');
    const quantity = document.querySelector('#quantity').value;
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
        `
    const element = new DOMParser().parseFromString(html, "text/html")
    element.querySelector('button').addEventListener('click', removeIngredient)
    const parentContainer = document.querySelector('.ingredients-container');
    parentContainer.insertBefore(element.querySelector('body').firstChild, parentContainer.lastChild);
}

function removeIngredient($event) {
    $event.stopPropagation();
    $event.target.parentElement.remove();
}