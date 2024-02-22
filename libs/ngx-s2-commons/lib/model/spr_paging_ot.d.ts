import { Spr_key_values_ot } from './spr_key_values_ot';
export declare class Spr_paging_ot {
    cnt: number;
    skip_rows: number;
    page_size: number;
    order_clause: string;
    sum_values: Spr_key_values_ot[];
    constructor(cnt: number, skip_rows: number, page_size: number, order_clause: string);
}
