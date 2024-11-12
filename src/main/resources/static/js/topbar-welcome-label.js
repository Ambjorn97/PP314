fetch('/api/user')
    .then(response => response.json())
    .then(user => {
        // Получаем элемент, где нужно отобразить имя пользователя
        const welcomeMessage = document.getElementById("welcomeMessage");

        // Обновляем текст элемента, используя имя пользователя
        welcomeMessage.textContent = `Добро пожаловать, ${user.username}`;
    })
    .catch(error => console.error('Ошибка при получении данных пользователя:', error));