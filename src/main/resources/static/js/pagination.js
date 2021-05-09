const PREV = '«';
const NEXT = '»';
const PAGE_SIZE = 10;

function initializePagination(elementId, buildTable) {
    const pagination = document.getElementById(`${elementId}-pagination`);
    pagination.addEventListener('page', function (e) {
        const newData = getDataOnPage(e.detail.data, e.detail.itemsToSkip);
        rebuildTable(newData, elementId, buildTable);
    });
}

function getDataOnPage(data, value){
    return data.filter((x, i)=> i > (value - 1) && i < (value + 10))
}

function reloadPagination(elementId, data, value) {
    const paginationNode = document.getElementById(`${elementId}-pagination`);
    const pageCount = Math.max(Math.ceil(data.length / PAGE_SIZE), 1);
    const currentPage = getCurrentPage(paginationNode);
    const nextPage = getNextPage(value, currentPage);
    const pageNumber = getPageNumber(pageCount, nextPage);
    generatePagination(paginationNode, pageNumber, elementId, data, nextPage);

    if (nextPage === 1) {
        setDisablePrev(paginationNode);
    }
    if (nextPage === pageCount) {

        setDisableNext(paginationNode);
    }

    const event = new CustomEvent('page', {
        detail:
            {
                itemsToSkip: PAGE_SIZE * (nextPage - 1),
                data: data
            }
    });
    paginationNode.dispatchEvent(event);
}

function getCurrentPage(pagination) {
    const currentActive = pagination.querySelector('.active');
    return Number(currentActive.children[0].innerText);
}

function getNextPage(value, currentPage) {
    if (value === PREV) {
        return currentPage - 1;
    } else if (value === NEXT) {
        return currentPage + 1;
    } else {
        return Number(value);
    }
}

function getPageNumber(pageCount, nextPage) {
    if (pageCount <= 5 || nextPage <= 3) {
        return [...Array(Math.min(pageCount, 5)).keys()].map(x => x + 1);
    } else if (pageCount - nextPage < 3) {
        return [...Array(5).keys()].map(x => x + pageCount - 4);
    } else {
        return [...Array(5).keys()].map(x => x + nextPage - 2);
    }
}

function generatePagination(pagination, pageNumbers, id, data, nextPage) {
    pagination.innerHTML = '';
    const pages = [PREV, ...pageNumbers, NEXT]
    for (const page of pages) {
        const li = createPageLink(id, data, page);
        if (page === nextPage) {
            li.classList.add('active');
        }
        pagination.appendChild(li);
    }
}

function createPageLink(id, data, value) {
    const li = document.createElement('li');
    li.classList.add('page-item');
    const a = document.createElement('a');
    a.classList.add('page-link');
    a.classList.add('pointer');
    a.innerText = value;
    a.onclick = () => reloadPagination(id, data, value);
    li.appendChild(a);
    return li;
}


function setDisablePrev(pagination) {
    const node = pagination.children[0]
    node.classList.add('disabled');
}

function setDisableNext(pagination) {
    const node = pagination.children[pagination.children.length - 1];
    node.classList.add('disabled');
}
