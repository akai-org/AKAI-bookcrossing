const addOpinionULR = `/opinion`;

function addOpinion(resourceId) {
    const description = document.getElementById('description').value;
    const rating = document.getElementById('rating').value;
    fetch(
            addOpinionULR,
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
