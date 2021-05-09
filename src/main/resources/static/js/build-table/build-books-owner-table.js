function buildBooksOwnerTable(table, data) {
    const header = ['Tytuł', 'Autor', 'Obecny Właściciel', 'Szczegóły'];
    const headerRow = table.children[0].children[0];
    for (const headerKey of header) {
        createTableHeader(headerRow, headerKey);
    }

    for (const item of data) {
        createBooksOwnerTableRow(table, item);
    }
}

function createBooksOwnerTableRow(table, dataRow) {
    const row = table.insertRow();
    row.classList.add('table-text-size');
    createTableCell(row, dataRow.title);
    createTableCell(row, dataRow.author);
    createTableCell(row, dataRow.reader.fullName);
    createTableCellWithButton(row, dataRow, {
        href: `/books/${dataRow.id}`,
        innerHTML: 'Szczegóły'
    });
}
