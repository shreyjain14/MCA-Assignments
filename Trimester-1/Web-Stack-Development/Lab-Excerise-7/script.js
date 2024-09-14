// Global variables
let books = [];
let currentPage = 1;
const booksPerPage = 6;

// DOM elements
const bookList = document.getElementById('bookList');
const pagination = document.getElementById('pagination');
const loading = document.getElementById('loading');
const error = document.getElementById('error');
const searchInput = document.getElementById('searchInput');
const sortSelect = document.getElementById('sortSelect');

// Fetch books from API
async function fetchBooks() {
    try {
        loading.classList.remove('hidden');
        error.classList.add('hidden');
        
        const response = await fetch('https://potterapi-fedeperin.vercel.app/en/books');
        if (!response.ok) {
            throw new Error('Failed to fetch books');
        }
        books = await response.json();
        
        loading.classList.add('hidden');
        renderBooks();
        renderPagination();
    } catch (err) {
        loading.classList.add('hidden');
        error.classList.remove('hidden');
        error.textContent = 'Error fetching books. Please try again later.';
        console.error('Error fetching books:', err);
    }
}

// Render books
function renderBooks() {
    const filteredBooks = filterBooks();
    const sortedBooks = sortBooks(filteredBooks);
    const paginatedBooks = paginateBooks(sortedBooks);
    
    bookList.innerHTML = '';
    paginatedBooks.forEach(book => {
        const bookElement = createBookElement(book);
        bookList.appendChild(bookElement);
    });
}

// Create book element
function createBookElement(book) {
    const bookDiv = document.createElement('div');
    bookDiv.className = 'bg-white p-4 rounded shadow flex flex-col h-full';
    bookDiv.innerHTML = `
        <img src="${book.cover}" alt="${book.title}" class="w-full h-64 object-cover mb-4 rounded">
        <h2 class="text-xl font-semibold mb-2">${book.title}</h2>
        <p class="text-gray-600 mb-2">Release Date: ${book.releaseDate}</p>
        <p class="text-gray-500 mb-2">Pages: ${book.pages}</p>
        <p class="text-sm text-gray-700 flex-grow">${book.description.substring(0, 150)}...</p>
    `;
    return bookDiv;
}

// Filter books based on search input
function filterBooks() {
    const searchTerm = searchInput.value.toLowerCase();
    return books.filter(book => 
        book.title.toLowerCase().includes(searchTerm) ||
        book.description.toLowerCase().includes(searchTerm)
    );
}

// Sort books based on selected option
function sortBooks(booksToSort) {
    const sortBy = sortSelect.value;
    return booksToSort.sort((a, b) => {
        if (sortBy === 'title') {
            return a.title.localeCompare(b.title);
        } else if (sortBy === 'releaseDate') {
            return new Date(a.releaseDate) - new Date(b.releaseDate);
        } else if (sortBy === 'pages') {
            return a.pages - b.pages;
        }
        return 0;
    });
}

// Paginate books
function paginateBooks(booksToPage) {
    const startIndex = (currentPage - 1) * booksPerPage;
    const endIndex = startIndex + booksPerPage;
    return booksToPage.slice(startIndex, endIndex);
}

// Render pagination
function renderPagination() {
    const pageCount = Math.ceil(filterBooks().length / booksPerPage);
    pagination.innerHTML = '';
    
    for (let i = 1; i <= pageCount; i++) {
        const button = document.createElement('button');
        button.textContent = i;
        button.className = `mx-1 px-3 py-1 rounded ${currentPage === i ? 'bg-blue-500 text-white' : 'bg-gray-200'}`;
        button.addEventListener('click', () => {
            currentPage = i;
            renderBooks();
            renderPagination();
        });
        pagination.appendChild(button);
    }
}

// Event listeners
searchInput.addEventListener('input', () => {
    currentPage = 1;
    renderBooks();
    renderPagination();
});

sortSelect.addEventListener('change', () => {
    currentPage = 1;
    renderBooks();
    renderPagination();
});

// Initialize the application
fetchBooks();