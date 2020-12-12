function search(allItemList, elementId) {
    $('#search').on('keyup', function () {
        const value = $(this).val();
        console.log('Value = ' + value);
        const data = filterFunction(value, allItemList);
        rebuildTable(data, elementId)
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

function rebuildTable(data, elementId) {
    const table = document.getElementById(elementId);
    let rows = ``

    if (elementId === 'book-list') {
        rows = `<tr>
                <th>Tytuł</th>
                <th>Autor</th>
                <th>Właściciel</th>
                <th>Obecny Właściciel</th>
                <th>Szczegóły</th>
                <th>Status książki</th>
            </tr>`
        for (let i = 0; i < data.length; i++) {
            let row = `<tr>
                <td class="break">${data[i].title}</td>
                <td class="break">${data[i].author}</td>
                <td class="break">${data[i].owner?.fullName}</td>
                <td class="break">${data[i].reader?.fullName}</td>
                <td class="break"><a href="/book/${data[i].id}">Szczegóły</a></td>`
            console.log(data[i]);
            if (data[i].available === true) {
                row += `<td>
                    <a class="btn btn-info small"  onclick="sendRentRequest(${data[i].id})">Wypożycz</a>
                    </td></tr>`
            } else {
                row += `<td>
                        <span>Niedostępna</span>
                    </td></tr>`
            }
            rows += row
        }

    } else if (elementId === "books-reader") {
        rows = `<tr>
                <th>Tytuł</th>
                <th>Autor</th>
                <th>Właściciel</th>
                <th>Szczegóły</th>
                <th>Udostępnij</th>
            </tr>`
        for (let i = 0; i < data.length; i++) {
            let availability;
            if (data[i].available) {
                availability = "Zablokuj";
            } else {
                availability = "Udostępnij";
            }
            let row = `<tr>
                    <td class="break title">${data[i].title}</td>
                    <td class="break">${data[i].author}</td>
                    <td class="break">${data[i].owner?.fullName}</td>
                    <td class="break"><a href="/book/${data[i].id}">Szczegóły</a></td>
                    <td class="break">
                        <a class="btn btn-info"
                           onclick="changeAvailability(${data[i].id}, ${data[i].available})">${availability}</a>
                    </td>
                </tr>`

            rows += row
        }
    } else if (elementId === "books-owner") {
        rows = `<tr>
                <th>Tytuł</th>
                <th>Autor</th>
                <th>Obecny Właściciel</th>
                <th>Szczegóły</th>
            </tr>`
        for (let i = 0; i < data.length; i++) {
            let row = `<tr>
                <td class="break title">${data[i].title}</td>
                <td class="break">${data[i].author}</td>
                <td class="break">${data[i].reader?.fullName}</td>
                <td class="break"><a href="/book/${data[i].id}">Szczegóły</a></td>`
            rows += row
        }
    }
    table.innerHTML = rows;
}
