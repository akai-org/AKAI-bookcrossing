function buildBookRequestsTable(table, data) {
    const header = ['Tytuł', 'Autor', 'Właściciel', 'Szczegóły', 'Udostępnij'];
    const headerRow = table.children[0].children[0];
    for (const headerKey of header) {
        createTableHeader(headerRow, headerKey);
    }

    for (const item of data) {
        createBookRequestsTableRow(table, item);
    }
}

function createBookRequestsTableRow(table, dataRow) {
    const row = table.insertRow();
    row.classList.add('table-text-size');
    createTableCell(row, dataRow.book.title);
    createTableCell(row, dataRow.book.author);
    createTableCell(row, dataRow.requester.fullName);
    createTableCellWithButton(row, dataRow, {
        href: `/books/${dataRow.id}`,
        innerHTML: 'Szczegóły'
    });
    const cellWithButtons = row.insertCell();
    row.classList.add('w-25');

    const acceptButton = document.createElement('a');
    acceptButton.classList.add('btn', 'btn-success', 'table-button-size', 'mr-2');
    acceptButton.innerHTML = 'Zaakceptuj';
    acceptButton.onclick = () => acceptRentRequest(dataRow.id);

    const declineButton = document.createElement('a');
    declineButton.classList.add('btn', 'btn-danger', 'table-button-size');
    declineButton.innerHTML = 'Odrzuć';
    declineButton.onclick = () => declineRentRequest(dataRow.id);

    cellWithButtons.appendChild(acceptButton);
    cellWithButtons.appendChild(declineButton);
    row.appendChild(cellWithButtons);
}
