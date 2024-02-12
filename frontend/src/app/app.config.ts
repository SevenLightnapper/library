// urls config
// URLs бэкенда Spring Boot

// base url
export const baseUrl = 'http://localhost:8080'

// books
export const booksUrl = baseUrl + '/books';

// genres
export const genresUrl = baseUrl + '/genres';

// authors
export const authorsUrl = baseUrl + '/authors';

// comments
export const commentsUrl = booksUrl;

// auth
export const authUrl = baseUrl + '/auth/';

// users
export const usersUrl = baseUrl + '/users';
