var fontawsomeScript = document.createElement('script');
fontawsomeScript.setAttribute('src', 'https://kit.fontawesome.com/67f1a84681.js');
document.head.appendChild(fontawsomeScript);

window.addEventListener('load', function () {
  var iltpElement = document.getElementById('i-love-this-page');

  var iltpBox = document.createElement('span');
  var iltpBoxStyle = `
    border-radius: 15%;
    background-color: rgb(255, 202, 202);
    font-family: 'Fira Sans', sans-serif;
    padding: 0.5rem 1rem;`;
  iltpBox.setAttribute('style', iltpBoxStyle);

  var iltpIcon = document.createElement('i');
  iltpIcon.classList.add('far');
  iltpIcon.classList.add('fa-heart');
  iltpBox.appendChild(iltpIcon);

  var iltpContent = document.createElement('span');
  iltpContent.setAttribute('style', 'margin-left: .4rem;');
  iltpContent.textContent = 34;
  iltpBox.appendChild(iltpContent);

  iltpElement.appendChild(iltpBox);
});
