// Выполняем запрос к серверу, чтобы получить информацию о текущем пользователе
fetch('http://localhost:8080/api/user')
    .then(response => response.json())
    .then(user => {
        // Получаем текущий URL
        const currentURL = window.location.pathname;

        // Проверяем, есть ли у пользователя роль "ROLE_ADMIN"
        const isAdmin = user.roles.some(role => role.name === 'ROLE_ADMIN');

        // Если пользователь имеет роль "ROLE_ADMIN", отображаем ссылку и добавляем класс "active" при необходимости
        const adminLink = document.getElementById("sidebar-admin-panel-button");
        if (isAdmin) {
            adminLink.style.display = "block";  // Отображаем ссылку для администратора
            if (currentURL === '/admin') {
                adminLink.classList.add("active");  // Добавляем класс "active" для текущей страницы
            }
        } else {
            adminLink.style.display = "none";  // Скрываем ссылку, если у пользователя нет роли "ROLE_ADMIN"
        }
    })
    .catch(error => console.error('Ошибка при получении данных пользователя:', error));