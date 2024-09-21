// this script will run on every page
console.log("script loaded");
let currentTheme=getTheme();


// document.addEventListener("DOMContentLoaded", ()=>{
   changeTheme();
// });

//function to  change theme
function changeTheme(){
   //set to web page //adds class to html
 //  document.querySelector("html").classList.add(currentTheme);

 changePageTheme(currentTheme, currentTheme);
   //set the listener to change theme button
   const changeThemeButton = document.querySelector("#theme_change_button");

//change text of button
// changeThemeButton.querySelector("span").textContent=currentTheme=="light" ? "Dark" : "Light";
   
   
   changeThemeButton.addEventListener("click", (event) => {
      let oldTheme=currentTheme;
      console.log("button clicked")
     // const oldTheme=currentTheme;
      if(currentTheme==="dark")
      {
        //chnage theme to light
        currentTheme="light"

      }else{
         currentTheme="dark"
      }

      changePageTheme(currentTheme, oldTheme);
   
   });
}

//set theme to localstorage
function setTheme(theme){
    localStorage.setItem("theme", theme);

}

//get theme for localstorage
function getTheme(){
    let theme=localStorage.getItem("theme");
    if(theme)
    return theme;
    else 
    return "light";
 }

 //change current Page Theme
 function changePageTheme(theme, oldTheme){
       //upadting to local storage
       setTheme(currentTheme);

       
       //remove the current theme
       document.querySelector("html").classList.remove(oldTheme);

    //set the current theme
    document.querySelector("html").classList.add(theme);

    //change text of button
document.querySelector('#theme_change_button').querySelector("span").textContent=theme=="light" ? "Dark" : "Light";
 }