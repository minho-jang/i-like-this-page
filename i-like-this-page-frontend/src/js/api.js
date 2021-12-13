const DOMAIN = 'https://iltp.link';
const RESTAPI_BASEADDRESS = `${DOMAIN}/api/v1`;

const AUTHORIZATION_PREFIX = 'Bearer ';
const TOKEN_KEY_LOCALSTORAGE = 'iltp_tk';

export const getLike = async (currentLocation) => {
  const token = await checkToken();
  const getLikeUrl = createRequestUrlWithParams(`${RESTAPI_BASEADDRESS}/like`, {
    url: currentLocation,
  });

  const options = {
    method: 'GET',
    headers: {
      Authorization: AUTHORIZATION_PREFIX + token,
    },
  };

  const apiResult = await fetchSync(getLikeUrl, options);
  return apiResult;
};

export const addLike = async (currentLocation) => {
  const token = await checkToken();
  const addLikeUrl = createRequestUrlWithParams(`${RESTAPI_BASEADDRESS}/like`, {
    url: currentLocation,
  });

  const options = {
    method: 'POST',
    headers: {
      Authorization: AUTHORIZATION_PREFIX + token,
    },
  };

  const apiResult = await fetchSync(addLikeUrl, options);
  return apiResult;
};

export const deleteLike = async (currentLocation) => {
  const token = await checkToken();
  const deleteLikeUrl = createRequestUrlWithParams(
    `${RESTAPI_BASEADDRESS}/like`,
    { url: currentLocation }
  );

  const options = {
    method: 'DELETE',
    headers: {
      Authorization: AUTHORIZATION_PREFIX + token,
    },
  };

  const apiResult = await fetchSync(deleteLikeUrl, options);
  return apiResult;
};

const getToken = async () => {
  const getTokenUrl = `${RESTAPI_BASEADDRESS}/token`;
  const apiResult = await fetchSync(getTokenUrl);
  return apiResult;
};

const checkToken = async () => {
  const token = localStorage.getItem(TOKEN_KEY_LOCALSTORAGE);
  if (token) {
    return token;
  } else {
    const apiResult = await getToken();
    if (apiResult.error) {
      handleError(apiResult.error);
    }

    const newToken = apiResult.response;
    localStorage.setItem(TOKEN_KEY_LOCALSTORAGE, newToken);
    return newToken;
  }
};

const fetchSync = async (url, options) => {
  const response = await fetch(url, options);
  const responseJson = await response.json();
  return responseJson;
};

const createRequestUrlWithParams = (url, params) => {
  const tmpUrl = new URL(url);
  tmpUrl.search = new URLSearchParams(params).toString();
  return tmpUrl.toString();
};

const handleError = (message) => {
  throw message;
};
