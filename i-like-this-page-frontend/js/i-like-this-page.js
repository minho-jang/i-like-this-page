import { getLike, deleteLike, addLike, checkToken } from "./api.js";

const likeBackgroundColor = "rgb(255, 202, 202)";
const errorBackgroundColor = "rgb(214, 214, 214)";

const renderButton = (likeOrError) => {
  const iltpBox = document.createElement("span");
  iltpBox.setAttribute("id", "iltp-box");

  const iltpBoxStyle = `
    border-radius: 15%;
    background-color: ${
      likeOrError ? likeBackgroundColor : errorBackgroundColor
    };
    font-family: 'Fira Sans', sans-serif;
    padding: 0.5rem 1rem;`;
  iltpBox.setAttribute("style", iltpBoxStyle);

  const iltpIcon = document.createElement("span");
  iltpIcon.setAttribute("id", "iltp-content-icon");
  iltpIcon.innerText = likeOrError ? "💕" : "🖤";
  iltpBox.appendChild(iltpIcon);

  const iltpContent = document.createElement("span");
  iltpContent.setAttribute("id", "iltp-content-number");
  iltpContent.setAttribute("style", "margin-left: .4rem;");
  iltpContent.innerText = likeOrError ? "0" : "ERROR";
  iltpBox.appendChild(iltpContent);

  removeLikeButtonIfExisted();
  iltpContainer.appendChild(iltpBox);

  const cssLinkElement = document.createElement("link");
  cssLinkElement.setAttribute("href", "css/i-like-this-page.css");
  cssLinkElement.setAttribute("type", "text/css");
  cssLinkElement.setAttribute("rel", "stylesheet");
  document.head.appendChild(cssLinkElement);
};

const removeLikeButtonIfExisted = () => {
  if (iltpContainer.childNodes.length > 0) {
    iltpContainer.removeChild(iltpContainer.childNodes[0]);
  }
};

let userLikeStatus = false;
const iltpContainer = document.getElementById("i-like-this-page");
const iltpContainerStyle = `
  display: inline-block;
  cursor: pointer;
`;
iltpContainer.setAttribute("style", iltpContainerStyle);

iltpContainer.addEventListener("click", function () {
  const urlWithoutProtocol = window.location.host + window.location.pathname;

  if (userLikeStatus) {
    cancelLikeAndRender(urlWithoutProtocol);
  } else {
    addLikeAndRender(urlWithoutProtocol);
  }
});

const getLikeAndRender = (currentLocation) => {
  getLike(currentLocation)
    .then((data) => {
      if (!data.success) {
        console.error(data.error.message);
        renderErrorButtonTo(iltpContainer, data.error.status);
        return;
      }

      console.log(data);
      userLikeStatus = data.response.likeStatus;
      document.getElementById("iltp-content-number").innerHTML =
        data.response.likeCount;
    })
    .catch((err) => handleError(err));
}

const addLikeAndRender = (currentLocation) => {
  addLike(currentLocation)
    .then((data) => {
      console.log(data);
      if (!data.success) {
        handleError(data.error);
        return;
      }

      userLikeStatus = data.response.likeStatus;
      document.getElementById("iltp-content-number").innerText =
        data.response.likeCount;
    })
    .catch((err) => handleError(err));
}

const cancelLikeAndRender = (currentLocation) => {
  deleteLike(currentLocation)
    .then((data) => {
      console.log(data);
      if (!data.success) {
        handleError(data.error);
        return;
      }

      userLikeStatus = data.response.likeStatus;
      document.getElementById("iltp-content-number").innerText = data.response.likeCount;
    })
    .catch((err) => handleError(err));
}

const handleError = (err) => {
  console.error(err);
  renderButton(false);
};

renderButton(true);

window.addEventListener("load", () => {
  const currentLocation = window.location.host + window.location.pathname;
  getLikeAndRender(currentLocation);
});
