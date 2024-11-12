// Получаем текущий URL
const currentURL = window.location.pathname;

// Проверяем, равен ли URL /admin
if (currentURL === '/admin') {
    // Отображаем содержимое, если условие выполняется
    document.getElementById("adminPanel").style.display = "block";
}