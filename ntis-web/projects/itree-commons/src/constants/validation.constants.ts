export const EMAIL_PATTERN: RegExp =
  /^(?=.{1,254}$)(?=.{1,64}@)[-!#$%&'*+/0-9=?A-Z^_`a-z{|}~]+(\.[-!#$%&'*+/0-9=?A-Z^_`a-z{|}~]+)*@[A-Za-z0-9]([A-Za-z0-9-]{0,61}[A-Za-z0-9])?(\.[A-Za-z0-9]([A-Za-z0-9-]{0,61}[A-Za-z0-9])?)*$/;
export const STRONG_PASSWORD = '^(?=.*[a-z])(?=.*[A-Z])(?=.*\\d)(?=.*[~!@#$%^&*+=_-])[A-Za-z\\d~!@#$%^&*+=_-]{10,}$';
export const NAME_SURNAME_PATTERN = '^[a-zA-Z0-9\u00c0-\u017e/\\_-]+$';
export const PHONE_PATTERN = '^((\\+370)\\d{8})|^(\\d{4,5})$';
export const YES = 'Y';
export const NO = 'N';
export const LKS_94_COORDINATES_PATTERN = '^(\\d{6}(.\\d{1,6})?),\\s(\\d{7}(.\\d{1,6})?)$';
export const AN_INTEGER = '^[0-9]*$';
export const WEBSITE_PATTERN = '^(www\\.)([\\da-ž\\.-]+)\\.([a-ž\\.]{2,6})([\\/\\w \\.-]*)$';
export const PERSON_CODE = '^[0-9]{11}$';
