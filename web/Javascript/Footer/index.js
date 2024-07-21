// Biến trạng thái
let isOpen1 = false;
let isOpen2 = false;
let isOpen3 = false;
// Lấy phần tử accordion 1
const btn1 = document.getElementById('hidden_top_1').querySelector('.accordion-button');
const content1 = document.getElementById('hidden_top_1').querySelector('.accordion-content');

// Lấy phần tử accordion 2
const btn2 = document.getElementById('hidden_top_2').querySelector('.accordion-button');  
const content2 = document.getElementById('hidden_top_2').querySelector('.accordion-content');

// Accordion 3
const btn3 = document.getElementById('hidden_top_3').querySelector('.accordion-button');
const content3 = document.getElementById('hidden_top_3').querySelector('.accordion-content');

// Xử lý sự kiện click btn1  
btn1.addEventListener('click', () => {
  toggleAccordion1();
});

// Hàm toggle accordion 1
function toggleAccordion1() {
  // code toggle
  if(!isOpen1) {
    content1.classList.remove('hidden');
  } else {
    content1.classList.add('hidden');
  }
  isOpen1 = !isOpen1;
} 

// Xử lý sự kiện click btn2
btn2.addEventListener('click', () => {
  toggleAccordion2();
});

// Hàm toggle accordion 2 
function toggleAccordion2() {
  // code toggle
    if(!isOpen2) {
    content2.classList.remove('hidden');
  } else {  
    content2.classList.add('hidden');
  }
  isOpen2 = !isOpen2;
}

// Xử lý sự kiện click btn3
btn3.addEventListener('click', () => {
    toggleAccordion3();
  });

  function toggleAccordion3() {
    // code toggle
      if(!isOpen3) {
      content3.classList.remove('hidden');
    } else {  
      content3.classList.add('hidden');
    }
    isOpen3 = !isOpen3;
  }
  

  let currentOpenTab;
  function toggleAccordion1() {

    // Đóng tất cả tab
    closeAllTabs();
  
    if(isOpen1) {
      // Tab đang mở thì đóng
      content1.classList.add('hidden');
    } else {
      // Tab chưa mở thì mở    
      content1.classList.remove('hidden');
    }
    
  
    currentOpenTab = 1;
    isOpen1 = !isOpen1;
  
  }
  function toggleAccordion2() {

    // Đóng tất cả tab
    closeAllTabs();
  
    if(isOpen2) {
     // Tab 2 đang mở thì đóng 
      content2.classList.add('hidden');
    } else {
     // Tab 2 chưa mở thì mở
      content2.classList.remove('hidden'); 
    }
  
    currentOpenTab = 2;
    isOpen2 = !isOpen2;
  
  }
  function toggleAccordion3() {

    // Đóng tất cả tab
    closeAllTabs();
  
    if(isOpen3) {
     // Tab 3 đang mở thì đóng
      content3.classList.add('hidden');
    } else {
     // Tab 3 chưa mở thì mở
      content3.classList.remove('hidden');
    }
  
    currentOpenTab = 3;  
    isOpen3 = !isOpen3;
  
  }
  
  function closeAllTabs() {
    content1.classList.add('hidden');
    content2.classList.add('hidden');
    content3.classList.add('hidden');
  }