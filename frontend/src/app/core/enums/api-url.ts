export enum ApiUrl {
  AUTH = '/auth',
  OAUTH = '/oauth',

  LOGIN = `${ApiUrl.AUTH}/login`,
  REGISTER = `${ApiUrl.AUTH}/register`,

  OAUTH_AUTHORIZE = `${ApiUrl.OAUTH}/authorize`,

  OAUTH_GITHUB = `${ApiUrl.OAUTH_AUTHORIZE}/github`,
  OAUTH_GOOGLE = `${ApiUrl.OAUTH_AUTHORIZE}/google`,
}
