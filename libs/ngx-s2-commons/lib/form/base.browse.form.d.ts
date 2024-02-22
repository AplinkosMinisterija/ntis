import { Spr_id_values_ot } from '../model/spr_id_values_ot';
import { FormGroup } from '@angular/forms';
import { Table, TableLazyLoadEvent } from 'primeng/table';
import { Spr_paging_ot } from '../model/spr_paging_ot';
import { CommonFormServices } from './common.form.services';
import { Spr_key_values_ot } from '../model/spr_key_values_ot';
import { ExtendedSearchParam } from '../model/extended-search';
import * as i0 from "@angular/core";
export declare const BROWSE_SEARCH_DATA_KEY = "browseSearchData";
interface BrowseSearchData {
    url: string;
    paramsObj: Record<string, unknown>;
    extendedParams: ExtendedSearchParam[];
    searchFormValues: Record<string, unknown>;
    first: number;
    pageSize: number;
    sortField: string;
    order: number;
}
export declare abstract class BaseBrowseForm<T> {
    protected commonFormServices: CommonFormServices;
    static DEFAULT_ROWS_TO_SHOW: number;
    keepFilters: boolean;
    data: T[];
    totalRecords: number;
    rowsPerPageOptions: number[];
    showRows: number;
    searchForm: FormGroup;
    useExtendedSearchParams: boolean;
    dataTableComponent: Table;
    constructor(commonFormServices: CommonFormServices);
    static getSortOrder(sortField: string, order: number, defaultSort: string): string;
    static getPagingParams(first: number, pageSize: number, sortField: string, order: number, defaultSort: string): Spr_paging_ot;
    protected abstract load(first: number, pageSize: number, sortField: string, order: number, params: Map<string, unknown>): void;
    protected abstract load(first: number, pageSize: number, sortField: string, order: number, params: Map<string, unknown>, extendedParams?: ExtendedSearchParam[]): void;
    loadDataLazy(event: TableLazyLoadEvent): void;
    search(table: Table, resetPage?: boolean): void;
    protected doLoad(first: number, pageSize: number, sortField: string, order: number, params: Map<string, unknown>, extendedParams?: ExtendedSearchParam[]): void;
    reload(resetPage?: boolean): void;
    protected getFilterParamNameMapping(): Record<string, string>;
    protected restoreOrClearFilters(): void;
    private formatSearchParamName;
    private addItemToSearchParamMap;
    /**
     * Pridėtas ELSE, jei gauti duomenys yra ne ExtendedSearchParam tipo,
     * ELSE sąlygoje sukuriamas ExtendedSearchParam objektas ir pridedamas į
     * paramsArray masyvą.
     * Pridėtas key parametra gauti controls`o pavadinimą.
     */
    private addItemToExtendedSearchParams;
    /**
     * Buvo pridėtas tikrinimas ar this.searchForm.controls turi duomenis,
     * jei this.searchForm.controls turi duomenis, taip pat tikrina ar gauti duomenys
     * nėra ExtendedSearchParam tipo, jei sąlyga pasitvirtina duomenys pridedami į PARAMS masyvą.
     */
    protected getSearchParamMap(event: TableLazyLoadEvent | Table): Map<string, unknown>;
    protected getExtendedSearchParams(event: TableLazyLoadEvent | Table): ExtendedSearchParam[];
    instanceOfIdValuesOt(object: unknown): object is Spr_id_values_ot;
    instanceOfKeyValuesOt(object: unknown): object is Spr_key_values_ot;
    protected formatValue(value: unknown): unknown;
    protected getSortOrder(sortField: string, order: number, defaultSort: string): string;
    protected getPagingParams(first: number, pageSize: number, sortField: string, order: number, defaultSort: string): Spr_paging_ot;
    protected getDefaultRowsToShow(): number;
    protected abstract deleteRecord(recordId: string): void;
    protected deleteRecordWithConfirmation(recordId: string, message?: string, translateMessage?: boolean): void;
    protected saveSearchDataToSession(first: number, pageSize: number, sortField: string, order: number, params: Map<string, unknown>, extendedParams?: ExtendedSearchParam[]): void;
    protected getSearchDataFromSession(): BrowseSearchData | null;
    protected removeSearchDataFromSession(): void;
    protected loadSearchDataIntoSearchForm(): void;
    static ɵfac: i0.ɵɵFactoryDeclaration<BaseBrowseForm<any>, never>;
    static ɵcmp: i0.ɵɵComponentDeclaration<BaseBrowseForm<any>, "ng-component", never, {}, {}, never, never, false, never>;
}
export {};
