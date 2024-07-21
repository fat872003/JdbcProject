console.log("Hello form NAVBAR")

document.addEventListener("DOMContentLoaded", function () {
    // open
    const burger = document.querySelectorAll(".navbar-burger");
    const menu = document.querySelectorAll(".navbar-menu");
  
    if (burger.length && menu.length) {
      for (var i = 0; i < burger.length; i++) {
        burger[i].addEventListener("click", function () {
          for (var j = 0; j < menu.length; j++) {
            menu[j].classList.toggle("hidden");
          }
        });
      }
    }
  
    // close
    const close = document.querySelectorAll(".navbar-close");
    const backdrop = document.querySelectorAll(".navbar-backdrop");
  
    if (close.length) {
      for (var i = 0; i < close.length; i++) {
        close[i].addEventListener("click", function () {
          for (var j = 0; j < menu.length; j++) {
            menu[j].classList.toggle("hidden");
          }
        });
      }
    }
  
    if (backdrop.length) {
      for (var i = 0; i < backdrop.length; i++) {
        backdrop[i].addEventListener("click", function () {
          for (var j = 0; j < menu.length; j++) {
            menu[j].classList.toggle("hidden");
          }
        });
      }
    }
  });
  
  //Fixed Navbar:
  
  
  //servicePop:
  //isSerNav: true / => ra ngoai header => false
  const navbar = document.getElementById("navbar");
  
  const serNav = document.getElementById("serviceNav");
  const serPop = document.getElementById("servicePop");
  const memNav = document.getElementById("memNav");
  const memPop = document.getElementById("memPop");
  const supportNav = document.getElementById("supportNav");
  const supportPop = document.getElementById("supportPop");
  
  let isSerNav = false;
  let isMemNav = false;
  let isSupportNav = false;
  const disableNav = () => {
    isSerNav = false;
    isMemNav = false;
    isSupportNav = false;
  };
  const toggleClass = () => {
    serPop.classList.toggle("translate-y-[-110%]", !isSerNav);
    memPop.classList.toggle("translate-y-[-110%]", !isMemNav);
    supportPop.classList.toggle("translate-y-[-110%]", !isSupportNav);
  };
  
  
  //Service action===========
  serNav.addEventListener("mouseenter", () => {
    disableNav();
    isSerNav = true;
    toggleClass();
  });
  
  serPop.addEventListener("mouseenter", () => {
    disableNav();
    isSerNav = true;
    toggleClass();
  });
  
  serPop.addEventListener("mouseleave", () => {
    disableNav();
    toggleClass();
  });
  
  //Member action
  memNav.addEventListener("mouseenter", () => {
    disableNav();
    isMemNav = true;
    toggleClass();
  });
  memPop.addEventListener("mouseenter", () => {
    disableNav();
    isMemNav = true;
    toggleClass();
  });
  memPop.addEventListener("mouseleave", () => {
    disableNav();
    toggleClass();
  });
  
  //Support action
  supportNav.addEventListener("mouseenter", () => {
    disableNav();
    isSupportNav = true;
    toggleClass();
  });
  supportPop.addEventListener("mouseenter", () => {
    disableNav();
    isSupportNav = true;
    toggleClass();
  });
  supportPop.addEventListener("mouseleave", () => {
    disableNav();
    toggleClass();
  });
  
  //Khi ra khoi Nav:
  navbar.addEventListener("mouseleave", () => {
    disableNav();
    toggleClass();
  });
  
  