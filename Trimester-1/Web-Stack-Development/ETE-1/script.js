const API_URL = 'https://studious-space-zebra-69gp5q7g46wrcw6j-5000.app.github.dev';
const coursesPerPage = 3;
let currentPage = 1;
let coursesData = [];

// Fetch courses from the API
async function fetchCourses() {
    try {
        console.log('Fetching courses from:', API_URL);
        const response = await fetch(API_URL);
        console.log('Response status:', response.status);
        console.log('Response headers:', response.headers);

        if (!response.ok) {
            throw new Error(`HTTP error! status: ${response.status}`);
        }

        const data = await response.json();
        console.log('Received data:', data);

        coursesData = data;
        updateDisplay();
    } catch (error) {
        console.error("Error fetching courses:", error);
        document.getElementById('courses').innerHTML = `
            <p class="text-red-500">Error loading courses. Please check the console for more details.</p>
            <p class="text-red-500">Error message: ${error.message}</p>
        `;
    }
}

// Update the courses
function updateDisplay() {
    const searchTerm = document.getElementById('search').value.toLowerCase();
    const sortBy = document.getElementById('sort').value;
    
    let filteredCourses = coursesData.filter(course => 
        course.title.toLowerCase().includes(searchTerm) ||
        course.instructor.toLowerCase().includes(searchTerm) ||
        course.category.toLowerCase().includes(searchTerm)
    );
    
    // Sort courses
    filteredCourses.sort((a, b) => {
        if (sortBy === 'price') {
            return parseFloat(a[sortBy].replace('$', '')) - parseFloat(b[sortBy].replace('$', ''));
        } else if (sortBy === 'rating') {
            return b[sortBy] - a[sortBy];
        } else {
            return a[sortBy].localeCompare(b[sortBy]);
        }
    });
    
    const startIndex = (currentPage - 1) * coursesPerPage;
    const endIndex = startIndex + coursesPerPage;
    const coursesToDisplay = filteredCourses.slice(startIndex, endIndex);
    
    const coursesContainer = document.getElementById('courses');

    // Display courses
    coursesContainer.innerHTML = coursesToDisplay.map(course => `
        <div class="bg-white p-4 rounded shadow">
            <h2 class="text-xl font-semibold">${course.title}</h2>
            <p class="text-gray-600">Instructor: ${course.instructor}</p>
            <p class="text-gray-600">Category: ${course.category}</p>
            <p class="text-gray-600">Duration: ${course.duration}</p>
            <p class="text-gray-600">Enrolled: ${course.enrolled}</p>
            <p class="text-gray-600">Price: ${course.price}</p>
            <p class="text-gray-600">Rating: ${course.rating}</p>
        </div>
    `).join('');
    
    document.getElementById('page-info').textContent = `Page ${currentPage} of ${Math.ceil(filteredCourses.length / coursesPerPage)}`;
    document.getElementById('prev').disabled = currentPage === 1;
    document.getElementById('next').disabled = endIndex >= filteredCourses.length;
}

// Search input event listener
document.getElementById('search').addEventListener('input', () => {
    currentPage = 1;
    updateDisplay();
});

// Sort dropdown event listener
document.getElementById('sort').addEventListener('change', updateDisplay);

// Previous button event listener
document.getElementById('prev').addEventListener('click', () => {
    if (currentPage > 1) {
        currentPage--;
        updateDisplay();
    }
});

// Next button event listener
document.getElementById('next').addEventListener('click', () => {
    if (currentPage < Math.ceil(coursesData.length / coursesPerPage)) {
        currentPage++;
        updateDisplay();
    }
});

// Initial fetch of courses
fetchCourses();