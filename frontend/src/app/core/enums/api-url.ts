export enum ApiUrl {
  API = '/api',

  AUTH = `${API}/auth`,
  OAUTH = '/oauth2',

  LOGIN = `${AUTH}/login`,
  REGISTER = `${AUTH}/register`,
  CHECK_TOKEN = `${AUTH}/check-token`,

  OAUTH_AUTHORIZE = `${OAUTH}/authorize`,

  OAUTH_GITHUB = `${OAUTH_AUTHORIZE}/github`,
  OAUTH_GOOGLE = `${OAUTH_AUTHORIZE}/google`,

  NOTES = `${API}/notes`,

  FIND_ALL_NOTES = `${NOTES}/find-all`,

  FIND_BY_TEXT = `${NOTES}/get-by-text`,
}
