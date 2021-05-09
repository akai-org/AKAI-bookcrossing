function buildBooksListTable(table, data) {
    const header = ['Tytuł', 'Autor', 'Właściciel', 'Obecny Właściciel', 'Szczegóły', 'Status książki'];
    const headerRow = table.children[0].children[0];
    for (const headerKey of header) {
        createTableHeader(headerRow, headerKey);
    }

    for (const item of data) {
        createRow(table, item);
    }
}

function createRow(table, dataRow) {
    const row = table.insertRow();
    row.classList.add('table-text-size');
    createTableCell(row, dataRow.title);
    createTableCell(row, dataRow.author);
    createTableCell(row, dataRow.owner.fullName);
    createTableCell(row, dataRow.reader.fullName);
    createTableCellWithButton(row, dataRow, {
        href: `/books/${dataRow.id}`,
        innerHTML: 'Szczegóły'
    });
    if (dataRow.available) {
        createTableCellWithButton(row, dataRow, {
            onclick: () => sendRentRequest(dataRow.id),
            innerHTML: 'Wypożycz'
        })
    } else {
        createTableCellUnavailable(row);
    }
}

function createTableCellUnavailable(row) {
    const newCell = row.insertCell()
    const unavailable = document.createElement('span');
    unavailable.innerHTML = 'Niedostępna';
    newCell.appendChild(unavailable);
}

