import { API_BASE_URL, ACCESS_TOKEN } from '../constants/constants';

const request = (options) => {
    const headers = new Headers({
        'Content-Type': 'application/json',
    });

    if(localStorage.getItem(ACCESS_TOKEN)) {
        headers.append('Authorization', 'Bearer ' + localStorage.getItem(ACCESS_TOKEN))
    }

    const defaults = {headers: headers};
    options = Object.assign({}, defaults, options);

    return fetch(options.url, options)
    .then(response =>
        response.json().then(json => {
            if(!response.ok) {
                return Promise.reject(json);
            }
            return json;
        })
    );
};

export function login(loginRequest) {
    return request({
        url: API_BASE_URL + "/auth/login",
        method: 'POST',
        body: JSON.stringify(loginRequest)
    });
}

export function register(registerRequest) {
    return request({
        url: API_BASE_URL + "/auth/register",
        method: 'PUT',
        body: JSON.stringify(registerRequest)
    });
}

export function uploadImage(uploadImageRequest) {
    return request({
        url: API_BASE_URL + "/image",
        method: 'PUT',
        body: JSON.stringify(uploadImageRequest)
    });
}

export function getImage(imageId, needSource) {
    return request({
        url: API_BASE_URL + "/image?id=" + imageId +"&src=" + needSource,
        method: 'GET',
    });
}

export function getImages() {
    return request({
        url: API_BASE_URL + "/image/gallery",
        method: 'GET',
    });
}

export function deleteImage(imageId) {
    return request({
        url: API_BASE_URL + "/image?id=" + imageId,
        method: 'DELETE',
    });
}

export function deleteMod(modId) {
    return request({
        url: API_BASE_URL + "/image/mod?id=" + modId,
        method: 'DELETE',
    });
}

export function addMod(imageId,mod) {
    return request({
        url: API_BASE_URL + "/image/mod?id=" + imageId,
        method: 'PUT',
        body: JSON.stringify(mod)
    });
}

export function checkUsernameAvailability(username) {
    return request({
        url: API_BASE_URL + "/user/checkUsernameAvailability?username=" + username,
        method: 'GET'
    });
}

export function getCurrentUser() {
    if(!localStorage.getItem(ACCESS_TOKEN)) {
        return Promise.reject("No access token set.");
    }
    return request({
        url: API_BASE_URL + "/user/me",
        method: 'GET'
    });
}

export function getUserProfile(username) {
    return request({
        url: API_BASE_URL + "/users/" + username,
        method: 'GET'
    });
}