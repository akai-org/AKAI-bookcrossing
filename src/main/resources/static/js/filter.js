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
