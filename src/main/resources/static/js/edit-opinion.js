function editOpinion(opinionId) {
    const opinion = document.getElementById(`opinion-${opinionId}`);

    const icon = opinion.querySelector('i.fa-pencil-alt');
    icon.classList.replace('fa-pencil-alt', 'fa-save');
    icon.onclick = () => updateOpinion(opinionId);

    const rating = opinion.querySelector('p.rating');
    const ratingIndex = rating.innerText.search('\\d');
    const oldValue = rating.innerText[ratingIndex];
    rating.innerHTML = '';
    rating.appendChild(createRatingSelectLabel());
    rating.appendChild(createRatingSelect(oldValue));

    const description = opinion.querySelector('div.description div.text-break');
    const textarea = createTextarea(description);
    description.replaceWith(textarea);

}

function createRatingSelect(oldValue) {
    const ratingValues = [...Array(5).keys()].map(value => value + 1);
    const select = createSelect(ratingValues);
    select.value = oldValue;
    select.classList.add('form-control');
    select.classList.add('d-inline-block');
    select.classList.add('ms-2');
    select.classList.add('fit-content');
    return select;
}

function createRatingSelectLabel() {
    const label = document.createElement('label')
    label.classList.add('property')
    label.innerHTML = 'Ocena:'
    return label
}

function createTextarea(description) {
    const textarea = document.createElement('textarea');
    textarea.innerText = description.innerText;
    textarea.classList.add('form-control');
    return textarea;
}
