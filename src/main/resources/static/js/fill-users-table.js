document.addEventListener('DOMContentLoaded', function () {
    // Загружаем список пользователей при загрузке страницы
    fetch('/api/allUsers')
        .then(response => response.json())
        .then(users => {
            const tableBody = document.getElementById("userTableBody");

            users.forEach(user => {
                const row = document.createElement("tr");
                row.innerHTML = `
                    <td>${user.id}</td>
                    <td>${user.username}</td>
                    <td>${user.age}</td>
                    <td>${user.roles.map(role => role.name.replace('ROLE_', '')).join(', ')}</td>
                    <td><button class="btn btn-primary btn-sm" data-user-id="${user.id}">Редактировать</button></td>
                    <td><button class="btn btn-danger btn-sm" data-user-id="${user.id}">Удалить</button></td>
                `;
                tableBody.appendChild(row);
            });

            // Обработчики кнопок "Редактировать" и "Удалить"
            tableBody.addEventListener('click', function (event) {
                const userId = event.target.getAttribute('data-user-id');

                if (event.target.classList.contains('btn-primary') && userId) {
                    editUser(userId);
                }
                if (event.target.classList.contains('btn-danger') && userId) {
                    deleteUser(userId);
                }
            });
        })
        .catch(error => console.error('Ошибка при получении данных пользователей:', error));

    function editUser(userId) {
        selectedUserId = userId;

        // Получаем список всех доступных ролей
        fetch('/api/admin/allRoles')
            .then(response => response.json())
            .then(allRoles => {
                // Получаем данные пользователя для заполнения формы
                return fetch(`/api/user/${userId}`)
                    .then(response => response.json())
                    .then(user => {
                        // Отображаем имя пользователя и возраст
                        document.getElementById("editUsername").value = user.username || '';
                        document.getElementById("editAge").value = user.age || '';

                        // Генерируем чекбоксы для ролей
                        const rolesContainer = document.getElementById("editRolesContainer");
                        rolesContainer.innerHTML = ''; // Очищаем контейнер ролей перед добавлением

                        allRoles.forEach(role => {
                            const isChecked = user.roles.some(userRole => userRole.name === role.name);

                            const checkbox = document.createElement('input');
                            checkbox.type = 'checkbox';
                            checkbox.value = role.name;
                            checkbox.checked = isChecked;
                            checkbox.classList.add('role-checkbox');

                            const label = document.createElement('label');
                            label.textContent = role.name.replace('ROLE_', '');
                            label.appendChild(checkbox);

                            const div = document.createElement('div');
                            div.classList.add('form-check');
                            div.appendChild(label);

                            rolesContainer.appendChild(div);
                        });

                        $('#editUserModal').modal('show');
                    });
            })
            .catch(error => console.error('Ошибка при получении данных пользователя или ролей:', error));
    }

    // Функция для сохранения изменений пользователя
    document.getElementById("saveEditUser").addEventListener("click", function () {
        const roles = [];
        const rolesSelected = document.getElementById("editRolesContainer");

        // Если роли были выбраны
        if (rolesSelected) {
            const checkboxes = rolesSelected.querySelectorAll(".role-checkbox");

            checkboxes.forEach(checkbox => {
                if (checkbox.checked) {
                    // Проверяем, какие роли выбраны
                    if (checkbox.value === "ROLE_USER") {
                        roles.push({ name: "ROLE_USER" });
                    } else if (checkbox.value === "ROLE_ADMIN") {
                        roles.push({ name: "ROLE_ADMIN" });
                    }
                }
            });
        }

        const updatedUser = {
            id: selectedUserId,
            username: document.getElementById("editUsername").value,
            age: parseInt(document.getElementById("editAge").value, 10),
            roles: roles
        };

        console.log("Отправляем обновленные данные пользователя:", JSON.stringify(updatedUser));

        fetch(`/api/user/${selectedUserId}`, {
            method: 'PUT', // используем PUT, так как вы заменяете все данные
            headers: {
                'Content-Type': 'application/json'
            },
            body: JSON.stringify(updatedUser)
        })
            .then(response => {
                if (response.ok) {
                    alert("Данные пользователя обновлены.");
                    $('#editUserModal').modal('hide');
                    location.reload();  // Перезагружаем страницу, чтобы отобразить обновленные данные
                } else if (response.status === 400) {
                    // Если ответ сервера - 400, показываем сообщение об ошибке
                    const errorMessage = document.getElementById("editErrorMessage");
                    errorMessage.textContent = "Это имя пользователя уже занято.";
                    errorMessage.style.display = "block";
                } else {
                    console.error('Ошибка при обновлении данных пользователя:', response.statusText);
                }
            })
            .catch(error => console.error('Ошибка при обновлении данных пользователя:', error));
    });

    function deleteUser(userId) {
        selectedUserId = userId;
        $('#deleteUserModal').modal('show');
    }

    document.getElementById("confirmDeleteUser").addEventListener("click", function () {
        fetch(`/api/user/${selectedUserId}`, {
            method: 'DELETE',
            headers: {
                'Content-Type': 'application/json'
            }
        })
            .then(response => {
                if (response.ok) {
                    alert("Пользователь удален.");
                    $('#deleteUserModal').modal('hide');
                    location.reload();  // Перезагружаем страницу, чтобы отобразить изменения
                } else {
                    console.error('Ошибка при удалении пользователя');
                }
            })
            .catch(error => console.error('Ошибка при удалении пользователя:', error));
    });
});