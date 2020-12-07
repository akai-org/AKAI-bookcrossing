function search(allItemList) {
    $('#search').on('keyup', function () {
        const value = $(this).val();
        console.log('Value = ' + value);
        const data = filterFunction(value, allItemList);
        rebuildTable(data)
    });
}

function filterFunction(value, data) {
    const filteredData = [];
    for (let i = 0; i < data.length; i++) {
        value = value.toLowerCase();
        const title = data[i].title.toLowerCase();
        const authorName = data[i].author.toLowerCase();

        if (title.includes(value) || authorName.includes(value)) {
            filteredData.push(data[i]);
        } else {
            for (let j = 0; j < data[i].tagList.length; j++) {
                const tagName = data[i].tagList[j].name.toLowerCase();
                if (tagName.includes(value)) {
                    filteredData.push(data[i]);
                    break;
                }
            }
        }
    }
    return filteredData;
}

function rebuildTable(data) {
    const table = document.getElementById('bookList');
    let rows = `<tr>
                <th>Tytuł</th>
                <th>Autor</th>
                <th>Opis</th>
                <th>Właściciel</th>
                <th>Obecny Właściciel</th>
                <th>Szczegóły</th>
            </tr>`
    for (let i = 0; i < data.length; i++) {
        const row = `<tr>
                <td class="break">${data[i].title}</td>
                <td class="break">${data[i].author}</td>
                <td class="break mv" >${data[i].description}</td>
                <td class="break">${data[i].owner?.fullName}</td>
                <td class="break">${data[i].reader?.fullName}</td>
                <td class="break"><a href="/book/${data[i].id}">Szczegóły</a></td>
                </tr>`
        rows += row
    }
    table.innerHTML = rows;
}
