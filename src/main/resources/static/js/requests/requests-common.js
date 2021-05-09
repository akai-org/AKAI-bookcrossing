const headers = {'Content-Type': 'application/json'};
const csrfHeader = document.querySelector("meta[name='_csrf_header']").getAttribute("content")
headers[csrfHeader] = document.querySelector("meta[name='_csrf']").getAttribute("content")

function handleError() {
    alert("Problem z połączeniem z internetem!");
}

function handleResponse(successMessage, errorMessage) {
    return response => {
        if (response.status === 200 || response.status === 201) {
            localStorage.setItem('success', successMessage);
            location.reload();
        } else {
            localStorage.setItem('error', errorMessage);
            location.reload();
        }
    }
}
