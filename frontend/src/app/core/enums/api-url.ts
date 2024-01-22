export enum ApiUrl {
  API = '/api',
  OAUTH = '/oauth2',

  AUTH = `${API}/auth`,
  TOKEN = `${API}/tokens`,

  LOGIN = `${AUTH}/login`,
  REGISTER = `${AUTH}/register`,
  LOGOUT = `${AUTH}/logout`,

  CHECK_TOKEN = `${TOKEN}/check-token`,

  OAUTH_AUTHORIZE = `${OAUTH}/authorize`,

  OAUTH_GITHUB = `${OAUTH_AUTHORIZE}/github`,
  OAUTH_GOOGLE = `${OAUTH_AUTHORIZE}/google`,

  NOTES = `${API}/notes`,

  FIND_ALL_NOTES = `${NOTES}/find-all`,
  FIND_BY_TEXT = `${NOTES}/get-by-text`,
  CREATE_NOTE = `${NOTES}/create`,
  UPDATE_NOTE = `${NOTES}/update`,
  DELETE_NOTE = `${NOTES}/delete-by-id`
}
