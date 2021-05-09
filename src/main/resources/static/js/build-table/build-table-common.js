function rebuildTable(data, elementId, buildTable) {
    const table = document.getElementById(elementId);
    table.innerHTML = '';
    const headerRow = table.insertRow();
    headerRow.classList.add('header-text-size');
    buildTable(table, data)
}

function createTableHeader(headerRow, text) {
    const th = document.createElement('th');
    th.innerHTML = text;
    headerRow.appendChild(th);
}

function createTableCell(row, value) {
    const newCell = row.insertCell();
    newCell.innerHTML = value;
}

function createTableCellWithButton(row, dataRow, extra) {
    const newCell = row.insertCell()
    const button = document.createElement('a');
    button.classList.add('btn', 'btn-primary', 'table-button-size');
    for (const property in extra) {
        button[property] = extra[property]
    }
    newCell.appendChild(button);
}
