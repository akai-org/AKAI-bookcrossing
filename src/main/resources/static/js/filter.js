function search(allItemList, elementId, buildTable) {
    const searchField = document.getElementById(`${elementId}-search`);
    searchField.addEventListener('keyup', function () {
        const data = filterFunction(this.value, allItemList);
        rebuildTable(data, elementId, buildTable)
    });
}

function filterFunction(value, data) {
    const filteredData = [];
    for (const item of data) {

        value = value.toLowerCase();
        const title = item.title.toLowerCase();
        const authorName = item.author ? item.author.toLowerCase() : '';

        if (title.includes(value) || authorName.includes(value)) {
            filteredData.push(item);
        } else {
            for (const tag of item.tagList) {
                const tagName = tag.name.toLowerCase();
                if (tagName.includes(value)) {
                    filteredData.push(item);
                    break;
                }
            }
        }
    }
    return filteredData;
}

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
