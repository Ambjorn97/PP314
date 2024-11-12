document.getElementById("logoutButton").addEventListener("click", function (event) {
    event.preventDefault(); // Предотвращаем стандартное поведение кнопки

    // Отправляем запрос POST на /logout
    fetch('/logout', {
        method: 'POST',
        headers: {
            'Content-Type': 'application/x-www-form-urlencoded'
        }
    }).then(response => {
        if (response.ok) {
            // Перенаправляем на главную страницу или страницу входа после выхода
            window.location.href = '/auth/login';
        } else {
            console.error('Ошибка при выходе');
        }
    }).catch(error => console.error('Ошибка:', error));
});