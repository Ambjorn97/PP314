document.addEventListener("DOMContentLoaded", function () {
    // Получаем текущий URL
    console.log("baklan")
    const currentURL = window.location.pathname;

    // Получаем элемент ссылки
    const userLink = document.getElementById("sidebar-my-page-button");

    // Проверяем URL и добавляем класс "active", если совпадает
    if (currentURL === "/user") {
        userLink.classList.add("active");
    }
});