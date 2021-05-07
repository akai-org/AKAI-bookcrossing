export function handleError() {
    alert("Problem z połączeniem z internetem!");
}

export function handleResponse(successMessage, errorMessage) {
    return response => {
        if (response.status === 200 || response.status === 201) {
            location.reload();
            alert(successMessage);
        } else {
            alert(errorMessage);
        }
    }
}
