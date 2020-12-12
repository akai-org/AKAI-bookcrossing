const availableULR = `http://${window.location.host}/my-books/change-available`
const declineRentRequestULR = `http://${window.location.host}/my-books/decline`
const acceptRentRequestULR = `http://${window.location.host}/my-books/accept`
const sendRentRequestULR = `http://${window.location.host}/book/rent`
const addOpinionULR = `http://${window.location.host}/book`



const headers = {};
const csrfHeader = $("meta[name='_csrf_header']").attr("content");
headers[csrfHeader] = $("meta[name='_csrf']").attr("content");

async function acceptRentRequest(requestId) {
    try {
        const result = await $.ajax({
            url: `${acceptRentRequestULR}/${requestId}/`,
            type: 'DELETE',
            headers: headers,
            xhrFields: {
                withCredentials: true
            },
        });
        location.reload();
        alert("Odrzucenie requesta zakończone sukcesem");
    } catch (err) {
        console.log(err);
        alert(err);
    }
    return false;
}

async function declineRentRequest(requestId) {
    try {
        const result = await $.ajax({
            url: `${declineRentRequestULR}/${requestId}/`,
            type: 'DELETE',
            headers: headers,
            xhrFields: {
                withCredentials: true
            },
        });
        location.reload();
        alert("Odrzucenie requesta zakończone sukcesem");
    } catch (err) {
        console.log(err);
        alert("Odrzucenie requesta zakończone niepowodzeniem");
    }
    return false;
}

async function changeAvailability(bookId, isAvailable) {
    try {
        const result = await $.ajax({
            url: availableULR,
            type: 'PATCH',
            contentType: 'application/json',
            headers: headers,
            xhrFields: {
                withCredentials: true
            },
            data: JSON.stringify({
                id: Number(bookId),
                isAvailable: !isAvailable
            })
        });
        location.reload();
        alert("Zmiana statusu zakończona sukcesem");
    } catch (err) {
        alert("Zmiana statusu zakończona niepowodzeniem");
    }
    return false;
}

async function sendRentRequest(bookId) {
    try {
        const result = await $.ajax({
            url: sendRentRequestULR,
            type: 'POST',
            contentType: 'application/json',
            headers: headers,
            xhrFields: {
                withCredentials: true
            },
            data: JSON.stringify({
                id: Number(bookId)
            })
        });
        alert("Wysłanie requestu zakończone sukcesem");
    } catch (err) {
        console.log(err);
        alert("Wysłanie requestu zakończone niepowodzeniem");
    }
    return false;
}

async function addOpinion(bookId) {
    console.log(bookId);
    const description = document.getElementById('description').value;
    const rating = document.getElementById('rating').value;
    try {
        const result = await $.ajax({
            url: `${addOpinionULR}/${bookId}/add-opinion`,
            type: 'POST',
            contentType: 'application/json',
            headers: headers,
            xhrFields: {
                withCredentials: true
            },
            data: JSON.stringify({
                description: description,
                rating: Number(rating)
            })
        });
        alert("Dodanie opinii zakończone sukcesem");
        location.reload();
    } catch (err) {
        alert("Dodanie opinii zakończone niepowodzeniem");
    }
    return false;
}





