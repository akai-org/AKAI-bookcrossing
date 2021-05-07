const availableULR = '/my-books/change-available'
const declineRentRequestULR = '/my-books/decline'
const acceptRentRequestULR = '/my-books/accept'

function acceptRentRequest(requestId) {
    fetch(
        `${acceptRentRequestULR}/${requestId}/`,
        {
            method: 'DELETE',
            headers: headers,
            xhrFields: {
                withCredentials: true
            },
        })
        .then(handleResponse("Zaakceptowanie requesta zakończone sukcesem",
            "Zaakceptowanie requesta zakończone niepowodzeniem"))
        .catch(handleError);
}

function declineRentRequest(requestId) {
    fetch(
        `${declineRentRequestULR}/${requestId}/`,
        {
            method: 'DELETE',
            headers: headers,
            xhrFields: {
                withCredentials: true
            },
        })
        .then(handleResponse("Odrzucenie requesta zakończone sukcesem",
            "Odrzucenie requesta zakończone niepowodzeniem"))
        .catch(handleError);
}

function changeAvailability(bookId, isAvailable) {
    if (typeof isAvailable === "string") {
        isAvailable = (isAvailable === 'true');
    }
    fetch(
        availableULR,
        {
            method: 'PATCH',
            headers: headers,
            xhrFields: {
                withCredentials: true
            },
            body: JSON.stringify({
                id: Number(bookId),
                available: !isAvailable
            })
        })
        .then(handleResponse("Zmiana statusu zakończona sukcesem",
            "Zmiana statusu zakończona niepowodzeniem"))
        .catch(handleError);
}
