document.addEventListener("DOMContentLoaded", function () {
    // Получаем текущий URL
    const currentURL = window.location.pathname;

    // Проверяем, равен ли URL /user
    if (currentURL === '/user') {
        // Выполняем запрос к серверу для получения данных о текущем пользователе
        fetch('/api/user')
            .then(response => response.json())
            .then(user => {
                // Заполняем таблицу данными пользователя
                document.getElementById("userId").textContent = user.id;
                document.getElementById("userName").textContent = user.username;
                document.getElementById("userAge").textContent = user.age;

                // Отображаем блок с информацией о пользователе
                document.getElementById("main-content-my-page").style.display = "block";
            })
            .catch(error => console.error('Ошибка при получении данных пользователя:', error));
    }
});