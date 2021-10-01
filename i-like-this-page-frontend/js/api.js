const DOMAIN = "http://localhost:8080";
const RESTAPI_BASEADDRESS = `${DOMAIN}/api/v1`;

const AUTHORIZATION_PREFIX = "Bearer ";
const TOKEN_KEY_LOCALSTORAGE = "iltp_token";

export const getLike = (currentLocation) => {
  return new Promise((resolve, reject) => {
    checkToken()
      .then((token) => {
        const getLikeUrl = new URL(`${RESTAPI_BASEADDRESS}/like`);
        const params = { url: currentLocation };
        getLikeUrl.search = new URLSearchParams(params).toString();

        const options = {
          method: "GET",
          headers: {
            Authorization: AUTHORIZATION_PREFIX + token,
          },
        };
        resolve(fetch(getLikeUrl, options).then((response) => response.json()));
      })
      .catch(err => {
        console.err(err);
        reject();
      });
  });
}

export const addLike = (currentLocation) => {
  return new Promise((resolve, reject) => {
    checkToken()
      .then((token) => {
        const addLikeUrl = `${RESTAPI_BASEADDRESS}/like`;
        const options = {
          method: "POST",
          headers: {
            Authorization: AUTHORIZATION_PREFIX + token,
            "Content-Type": "application/json",
          },
          body: JSON.stringify({ url: currentLocation }),
        };

        resolve(fetch(addLikeUrl, options).then((response) => response.json()));
      })
      .catch((err) => {
        console.err(err);
        reject();
      });
  });
};

export const deleteLike = (currentLocation) => {
  return new Promise((resolve, reject) => {
    checkToken()
      .then((token) => {
        const deleteLikeUrl = `${RESTAPI_BASEADDRESS}/like`;
        const options = {
          method: "DELETE",
          headers: {
            Authorization: AUTHORIZATION_PREFIX + token,
            "Content-Type": "application/json",
          },
          body: JSON.stringify({ url: currentLocation }),
        };

        resolve(fetch(deleteLikeUrl, options).then((response) => response.json()));
      })
      .catch((err) => {
        console.err(err);
        reject();
      });
  });
};

const getToken = () => {
  const getTokenUrl = `${RESTAPI_BASEADDRESS}/token`;
  return fetch(getTokenUrl).then((response) => response.json());
}

const checkToken = () => {
  return new Promise((resolve, reject) => {
    const token = localStorage.getItem(TOKEN_KEY_LOCALSTORAGE);
    if (token) {
      resolve(token);

    } else {
      getToken()
        .then((data) => {
          console.log(data);
          if (!data.success) {
            handleError(data.error);
            return;
          }

          const newToken = data.response;
          localStorage.setItem(TOKEN_KEY_LOCALSTORAGE, newToken);
          resolve(newToken);
        })
        .catch(err => {
          console.err(err);
          reject();
        });
    }
  });
}