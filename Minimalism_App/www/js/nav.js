document.onload = function() {
    $("##header__icon").on("touchend", headericoClick(Event));
    $("#site-cache").on("touchend", sitecacheClick());
};

    

//    document.getElementById("header__icon").addEventListener("click", headericoClick, false);
//    document.getElementById("site-cache").addEventListener("click", sitecachClick, false);



function headericoClick(e) {
    e.preventDefault();
    $('body').toggleClass('with--sidebar');
}

function sitecacheClick() {
    $('body').removeClass('with--sidebar');
    
}