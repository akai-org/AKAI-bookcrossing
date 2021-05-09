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
    if (description) {
        description.replaceWith(textarea);
    } else {
        const descriptionContainer = opinion.querySelector('div.description');
        descriptionContainer.appendChild(createCommentLabel());
        descriptionContainer.appendChild(textarea);
    }
}

function createRatingSelect(oldValue) {
    const ratingValues = [...Array(5).keys()].map(value => value + 1);
    const select = createSelect(ratingValues);
    select.value = oldValue;
    select.classList.add('form-select');
    select.classList.add('d-inline-block');
    select.classList.add('ms-2');
    select.classList.add('fit-content');
    return select;
}

function createSelect(range) {
    const select = document.createElement('select');
    for (const value of range) {
        const option = document.createElement('option');
        option.value = String(value);
        option.text = value;
        select.appendChild(option);
    }
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
    textarea.innerText = description ? description.innerText : '';
    textarea.classList.add('form-control');
    return textarea;
}

function createCommentLabel() {
    const div = document.createElement('div');
    div.classList.add('lead');
    div.classList.add('property');
    div.innerText = 'Komentarz: '
    return div;
}
