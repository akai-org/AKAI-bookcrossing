function buildBooksReaderTable(table, data) {
    const header = ['Tytuł', 'Autor', 'Właściciel', 'Szczegóły', 'Udostępnij'];
    const headerRow = table.children[0].children[0];
    for (const headerKey of header) {
        createTableHeader(headerRow, headerKey);
    }

    for (const item of data) {
        createBooksReaderTableRow(table, item);
    }
}

function createBooksReaderTableRow(table, dataRow) {
    const row = table.insertRow();
    row.classList.add('table-text-size');
    createTableCell(row, dataRow.title);
    createTableCell(row, dataRow.author);
    createTableCell(row, dataRow.owner.fullName);
    createTableCellWithButton(row, dataRow, {
        href: `/books/${dataRow.id}`,
        innerHTML: 'Szczegóły'
    });

    let availability = dataRow.available ? 'Zablokuj' : 'Udostępnij';
    createTableCellWithButton(row, dataRow, {
        onclick: () => changeAvailability(dataRow.id, dataRow.available),
        innerHTML: availability
    });
}
