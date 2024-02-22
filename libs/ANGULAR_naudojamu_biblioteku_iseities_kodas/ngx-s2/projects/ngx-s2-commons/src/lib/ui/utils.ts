import { BaseBrowseForm } from '../form/base.browse.form';
import { SprPaging } from '../model/spr-paging';

export const SPEC_CHARS = '@./$!%*?&#_-';
export const PWD_PATTERN = '(?=^.{12,}$)(?=.*\\d)(?=.*[a-z])(?=.*[A-Z])(?=.*[-@.$/!%*?&#_.])(?!.*\\s).*$';

export function escapeRegExp(str: string): string {
  return str.replace(/[-[\]/{}()*+?.\\^$|]/g, '\\$&');
}

const Y = 'Y';
const N = 'N';

export function toBoolean(value: string): boolean {
  return value === Y;
}

export function fromBoolean(value: boolean): string {
  return value ? Y : N;
}

export function appendPagingParams(url: string, pagingParams: SprPaging): string {
  if (!pagingParams) {
    return url;
  }
  if (!pagingParams.pageSize) {
    pagingParams.pageSize = BaseBrowseForm.DEFAULT_ROWS_TO_SHOW;
  }
  if (url.substring(url.length - 1) !== '?') {
    url = url + '&';
  }
  url = `${url}pageSize=${pagingParams.pageSize}&skipRows=${pagingParams.skipRows}`;
  if (pagingParams.orderClause) {
    url = url + '&orderClause=' + pagingParams.orderClause;
  }
  return url;
}

export function appendSearchParams(url: string, params: Map<string, unknown>): string {
  if (params) {
    params.forEach((value: unknown, key: string) => {
      if (value && value !== '') {
        url = `${url}&${key}=${value as string}`;
      }
    });
  }
  return url;
}

export const deepEquals = (x: unknown, y: unknown): boolean => {
  if (x === y) {
    return true; // if both x and y are null or undefined and exactly the same
  } else if (!(x instanceof Object) || !(y instanceof Object)) {
    return false; // if they are not strictly equal, they both need to be Objects
  } else if (x.constructor !== y.constructor) {
    // they must have the exact same prototype chain, the closest we can do is
    // test their constructor.
    return false;
  } else {
    for (const p in x) {
      if (!Object.prototype.hasOwnProperty.call(x, p)) {
        continue; // other properties were tested using x.constructor === y.constructor
      }
      if (!Object.prototype.hasOwnProperty.call(y, p)) {
        return false; // allows to compare x[ p ] and y[ p ] when set to undefined
      }
      if ((x as Record<string, unknown>)[p] === (y as Record<string, unknown>)[p]) {
        continue; // if they have the same strict value or identity then they are equal
      }
      if (typeof (x as Record<string, unknown>)[p] !== 'object') {
        return false; // Numbers, Strings, Functions, Booleans must be strictly equal
      }
      if (!deepEquals((x as Record<string, unknown>)[p], (y as Record<string, unknown>)[p])) {
        return false;
      }
    }
    for (const p in y) {
      if (Object.prototype.hasOwnProperty.call(y, p) && !Object.prototype.hasOwnProperty.call(x, p)) {
        return false;
      }
    }
    return true;
  }
};
