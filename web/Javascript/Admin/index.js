let sideList = document.querySelectorAll("[data-sider]");

sideList.forEach(btn => {
    btn.addEventListener('click', (e) => {
//        let path = window.location.href;
//        console.log(path+"?sec=" + e.target.getAttribute('value'));
        let input = document.getElementById("siderInput");
        input.value = e.target.getAttribute('value');

        document.getElementById("sider").submit();
    });
});

