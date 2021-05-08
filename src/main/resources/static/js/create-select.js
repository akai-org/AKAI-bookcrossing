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
