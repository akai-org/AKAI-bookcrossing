const opinionULR = `/opinion`;

function addOpinion(resourceId) {
    const description = document.getElementById('description').value;
    const rating = document.getElementById('rating').value;
    fetch(
        opinionULR,
            {
                method: 'POST',
                headers: headers,
                xhrFields: {
                    withCredentials: true
                },
                body: JSON.stringify({
                    description: description,
                    rating: Number(rating),
                    resourceId: Number(resourceId)
                })
            })
        .then(handleResponse("Dodanie opinii zakończone sukcesem",
        "Dodanie opinii zakończone niepowodzeniem"))
        .catch(handleError);
}

function deleteOpinion(opinionId) {
    const isConfirmed = confirm("Czy na pewno chcesz usunąć swoją opinię?");
    if (isConfirmed) {
        fetch(
            `${opinionULR}/${opinionId}`,
            {
                method: 'DELETE',
                headers: headers,
                xhrFields: {
                    withCredentials: true
                },
            })
            .then(handleResponse("Usunięcie opinii zakończone sukcesem",
                "Usunięcie opinii zakończone niepowodzeniem"))
            .catch(handleError);
    }
}

function updateOpinion(opinionId) {
    const opinion = document.getElementById(`opinion-${opinionId}`);
    const rating = opinion.querySelector("select").value;
    const description = opinion.querySelector("textarea").value;
    fetch(
        `${opinionULR}/${opinionId}`,
        {
            method: 'PATCH',
            headers: headers,
            xhrFields: {
                withCredentials: true
            },
            body: JSON.stringify({
                description: description,
                rating: Number(rating),
            })
        })
        .then(handleResponse("Aktualizowanie opinii zakończone sukcesem",
            "Aktualizowanie opinii zakończone niepowodzeniem"))
        .catch(handleError);
}

