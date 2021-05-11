function initializeSearch(elementId, allItemList) {
    const searchField = document.getElementById(`${elementId}-search`);
    searchField.addEventListener('keyup', function () {
        const filteredData = filterFunction(this.value, allItemList);
        reloadPagination(elementId, filteredData, 1);
    });
}

function filterFunction(value, data) {
    const filteredData = [];
    for (const item of data) {

        value = value.toLowerCase();
        const title = item.title ? item.title.toLowerCase() : item.book.title.toLowerCase();
        const authorName = item.author ? item.author.toLowerCase() : item.book.author ? item.book.author.toLowerCase() : '';

        if (title.includes(value) || authorName.includes(value)) {
            filteredData.push(item);
        } else {
            if (item.tagList) {
                for (const tag of item.tagList) {
                    const tagName = tag.name.toLowerCase();
                    if (tagName.includes(value)) {
                        filteredData.push(item);
                        break;
                    }
                }
            } else if (item.book.tagList) {
                for (const tag of item.book.tagList) {
                    const tagName = tag.name.toLowerCase();
                    if (tagName.includes(value)) {
                        filteredData.push(item);
                        break;
                    }
                }
            }
        }
    }
    return filteredData;
}
