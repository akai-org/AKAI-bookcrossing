function buildEbooksTable(table, data) {
    const header = ['Tytuł', 'Autor', 'Szczegóły', 'Link'];
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
    row.children[0].classList.add('text-break');
    createTableCell(row, dataRow.author);
    createTableCellWithButton(row, dataRow, {
        href: `/ebooks/${dataRow.id}`,
        innerHTML: 'Szczegóły'
    });
    createTableCellWithButton(row, dataRow, {
        href: dataRow.url,
        innerHTML: 'Czytaj'
    });
}
