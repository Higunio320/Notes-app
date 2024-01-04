export enum ApiUrl {
  API = '/api',

  AUTH = `${API}/auth`,
  OAUTH = '/oauth2',

  LOGIN = `${AUTH}/login`,
  REGISTER = `${AUTH}/register`,

  OAUTH_AUTHORIZE = `${OAUTH}/authorize`,

  OAUTH_GITHUB = `${OAUTH_AUTHORIZE}/github`,
  OAUTH_GOOGLE = `${OAUTH_AUTHORIZE}/google`,
}
