import {handleError} from "./response-handlers.js";

const sendRentRequestULR = `/book/rent`

const headers = {'Content-Type': 'application/json'};
const csrfHeader = document.querySelector("meta[name='_csrf_header']").getAttribute("content")
headers[csrfHeader] = document.querySelector("meta[name='_csrf']").getAttribute("content")

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
        alert("Wysłanie prośby o wypożyczenie zakończone sukcesem");
    } else if (response.status === 409) {
        alert("Już wysłałeś prośbę o tę książkę");
    } else {
        alert("Wysłanie prośby o wypożyczenie zakończone niepowodzeniem");
    }
}

window.sendRentRequest = sendRentRequest;
