const guestBtn = document.querySelectorAll("#guestBtn");
const qtvBtn = document.querySelectorAll("#qtvBtn");
const coverLayer = document.querySelectorAll("#coverLayer");

//flag
let isGuest = true;
let isQtv = false;
qtvBtn.forEach((btn) => {
    btn.addEventListener("click", () => {
        isQtv = true;
        isGuest = !isQtv;
    });
});

// qtvBtn.addEventListener("click", () => {
//   console.log("lo");
//   isQtv = true;
//   isGuest = !isQtv;
// });

// guestBtn.addEventListener("click", () => {
//   isGuest = true;
//   isQtv = !isGuest;
// });

guestBtn.forEach((btn) => {
    btn.addEventListener("click", () => {
        isGuest = true;
        isQtv = !isGuest;
    });
});



//Login-Signup
// const goToLog = document.getElementById("goToLog")
const goToRegBtn = document.querySelectorAll("#swapRegBtn");
const goToLoginBtn = document.querySelectorAll("#swapLoginBtn");

//getParam
const params = new URLSearchParams(location.href);

//Vi sao lai queryAll? => Do responsive co 2 table
var regPage = document.querySelectorAll("#regPage");

var isReg;
if (params.get('sec') === "2") {
    isReg = true;
    regPage.forEach(btn => {
        btn.classList.toggle("swapPage", isReg);
    });
} else {
    isReg = false;
}


goToRegBtn.forEach((btn) => {
    btn.addEventListener("click", () => {
        isReg = !isReg;
    });
});

goToLoginBtn.forEach((btn) => {
    btn.addEventListener("click", () => {
        isReg = !isReg;
    });
});



document.addEventListener("click", (e) => {
    coverLayer[0].classList.toggle("toTheRight", isQtv);
    coverLayer[1].classList.toggle("toTheBot", isQtv);
    //   coverLayer.classList.toggle("toTheRight", isQtv);
    regPage.forEach(btn => {
        btn.classList.toggle("swapPage", isReg);
    })
    // regPage.classList.toggle("swapPage", isLogin);
});