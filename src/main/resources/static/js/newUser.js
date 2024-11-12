document.addEventListener('DOMContentLoaded', function () {
    // Получаем список доступных ролей
    fetch('/api/admin/allRoles')
        .then(response => response.json())
        .then(roles => {
            const rolesContainer = document.getElementById('rolesContainer');
            roles.forEach(role => {
                const checkbox = document.createElement('input');
                checkbox.type = 'checkbox';
                checkbox.value = role.id;
                checkbox.classList.add('form-check-input');

                const label = document.createElement('label');
                label.textContent = role.name.replace('ROLE_', '');
                label.classList.add('form-check-label');

                const div = document.createElement('div');
                div.classList.add('form-check');
                div.appendChild(checkbox);
                div.appendChild(label);

                rolesContainer.appendChild(div);
            });
        })
        .catch(error => console.error('Ошибка при получении ролей:', error));

    // Обработчик отправки формы
    document.getElementById('newUserForm').addEventListener('submit', function (event) {
        event.preventDefault();

        const username = document.getElementById('username').value;
        const password = document.getElementById('password').value;
        const age = parseInt(document.getElementById('age').value, 10);

        // Получаем все выбранные роли
        const selectedRoles = Array.from(document.querySelectorAll('#rolesContainer .form-check-input'))
            .filter(checkbox => checkbox.checked)
            .map(checkbox => ({ id: checkbox.value }));

        const newUser = {
            username,
            password,
            age,
            roles: selectedRoles
        };

        console.log('Отправляем нового пользователя:', newUser);

        // Отправляем данные на сервер
        fetch('/api/admin/newUser', {
            method: 'POST',
            headers: {
                'Content-Type': 'application/json'
            },
            body: JSON.stringify(newUser)
        })
            .then(response => {
                const errorMessage = document.getElementById("errorMessage");

                if (response.ok) {
                    alert("Пользователь успешно создан!");
                    // Очистка формы
                    document.getElementById('newUserForm').reset();
                    errorMessage.style.display = "none"; // Скрываем сообщение об ошибке, если оно было
                } else if (response.status === 400) {
                    // Если ответ - 400, показываем сообщение об ошибке
                    errorMessage.textContent = "Это имя пользователя уже занято.";
                    errorMessage.style.display = "block";
                } else {
                    console.error('Ошибка при создании пользователя:', response.statusText);
                }
            })
            .catch(error => console.error('Ошибка при отправке данных на сервер:', error));
    });
});