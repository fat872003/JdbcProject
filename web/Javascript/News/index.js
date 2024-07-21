//news:
const btnTinTuc = document.getElementById("btnTinTuc")
const tinTuc= document.getElementById("tinTuc")
const khuyenMai= document.getElementById("khuyenMai")
const btnKhuyenMai = document.getElementById("btnKhuyenMai")
let isTinTuc = true;
let isKhuyenMai = false;
btnTinTuc.addEventListener('click', ()=> {
  isTinTuc = true;
  isKhuyenMai=!isTinTuc
})
btnKhuyenMai.addEventListener('click', ()=> {
  isKhuyenMai = true;
  isTinTuc=!isKhuyenMai
})

document.addEventListener('click' , ()=>{
  btnTinTuc.classList.toggle("newsActive", isTinTuc)
  tinTuc.classList.toggle("opacity-0", !isTinTuc)
  btnKhuyenMai.classList.toggle("newsActive", isKhuyenMai)
  khuyenMai.classList.toggle("opacity-0", !isKhuyenMai)
})
