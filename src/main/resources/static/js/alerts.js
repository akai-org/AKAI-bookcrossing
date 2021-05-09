function showAlert() {
    const successMessage = localStorage.getItem('success');
    const alert = document.getElementById('alert');
    if (successMessage) {
        showSuccess(alert, successMessage);
        setTimeout( () => hide(alert),3000);
        localStorage.removeItem('success')
    }

    const errorMessage = localStorage.getItem('error');
    if (errorMessage) {
        showError(alert, errorMessage);
        setTimeout( () => hide(alert),3000);
        localStorage.removeItem('error')
    }

}
function showSuccess(alert, message){
    alert.classList.add('alert', 'alert-success');
    alert.innerText = message;
}

function showError(alert, message){
    alert.classList.add('alert', 'alert-danger');
    alert.innerText = message;
}

function hide(alert) {
    alert.innerText = '';
    alert.classList = []
}
