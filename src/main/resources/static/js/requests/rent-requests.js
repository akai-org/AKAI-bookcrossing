const sendRentRequestULR = `/books/rent`

function sendRentRequest(bookId) {
    fetch(
        sendRentRequestULR,
        {
            method: 'POST',
            headers: headers,
            xhrFields: {
                withCredentials: true
            },
            body: JSON.stringify({
                id: Number(bookId)
            })
        })
        .then(handleResponse)
        .catch(handleError);
}

function handleResponse(response) {
    if (response.status === 201) {
        localStorage.setItem('success', "Wysłanie prośby o wypożyczenie zakończone sukcesem");
        location.reload();
    } else if (response.status === 409) {
        localStorage.setItem('error', "Już wysłałeś prośbę o tę książkę");
        location.reload();
    } else {
        alert("Wysłanie prośby o wypożyczenie zakończone niepowodzeniem");
    }
}
