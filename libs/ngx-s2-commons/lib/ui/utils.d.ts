import { Spr_id_values_ot } from '../model/spr_id_values_ot';
import { Spr_key_values_ot } from '../model/spr_key_values_ot';
import { Spr_paging_ot } from '../model/spr_paging_ot';
export declare const SPEC_CHARS = "@./$!%*?&#_-";
export declare const PWD_PATTERN = "(?=^.{12,}$)(?=.*\\d)(?=.*[a-z])(?=.*[A-Z])(?=.*[-@.$/!%*?&#_.])(?!.*\\s).*$";
export declare function escapeRegExp(str: string): string;
export declare function toKeyValues(codes: string[]): Spr_key_values_ot[];
export declare function fromKeyValues(keyValues: Spr_key_values_ot[]): string[];
export declare function convertKeyValues(keyValues: Spr_key_values_ot[]): {
    value: string;
    label: string;
}[];
export declare function keyValuesToMap(keyValues: Spr_key_values_ot[]): Map<string, string>;
export declare function toBoolean(value: string): boolean;
export declare function fromBoolean(value: boolean): string;
export declare function toRadioBtnIdVal(values: Spr_id_values_ot[]): number;
export declare function fromRadioBtnIdVal(array: Spr_id_values_ot[], id: number): Spr_id_values_ot[];
export declare function toRadioBtnKeyVal(values: Spr_key_values_ot[]): string;
export declare function fromRadioBtnKeyVal(array: Spr_key_values_ot[], code: string): Spr_key_values_ot[];
export declare function appendPagingParams(url: string, pagingParams: Spr_paging_ot): string;
export declare function appendSearchParams(url: string, params: Map<string, unknown>): string;
export declare const deepEquals: (x: unknown, y: unknown) => boolean;
