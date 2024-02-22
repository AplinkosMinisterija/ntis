import { FormGroup } from '@angular/forms';
import { Component, ViewChild } from '@angular/core';
import { FilterMetadata } from 'primeng/api';
import { Table, TableLazyLoadEvent } from 'primeng/table';
import { SprPaging } from '../model/spr-paging';
import { CommonFormServices } from './common.form.services';
import { ExtendedSearchParam } from '../model/extended-search';
import { ExtendedSearchCondition, ExtendedSearchUpperLower } from '../enums/extended-search';

export const BROWSE_SEARCH_DATA_KEY = 'browseSearchData';

interface BrowseSearchData {
  url: string;
  paramsObj: Record<string, string>;
  extendedParams: ExtendedSearchParam[];
  searchFormValues: Record<string, unknown>;
  first: number;
  pageSize: number;
  sortField: string;
  order: number;
}

@Component({ template: '' })
// eslint-disable-next-line @angular-eslint/component-class-suffix
export abstract class BaseBrowseForm<T> {
  public static DEFAULT_ROWS_TO_SHOW = 20;

  keepFilters: boolean;
  data: T[];
  totalRecords: number;
  rowsPerPageOptions = [20, 50, 100];
  showRows = this.getDefaultRowsToShow();
  searchForm: FormGroup = new FormGroup({});
  useExtendedSearchParams = false;

  @ViewChild(Table, { static: true }) dataTableComponent: Table;

  constructor(protected commonFormServices: CommonFormServices) {
    this.keepFilters = !!this.commonFormServices?.router?.getCurrentNavigation()?.extras?.state?.keepFilters;
    this.restoreOrClearFilters();
  }

  static getSortOrder(sortField: string, order: number, defaultSort: string): string {
    let orderClause: string = defaultSort;
    if (sortField) {
      orderClause = sortField + (order === 1 ? ' ASC' : ' DESC');
    }
    return orderClause;
  }

  static getPagingParams(
    first: number,
    pageSize: number,
    sortField: string,
    order: number,
    defaultSort: string
  ): SprPaging {
    return new SprPaging(null, first, pageSize, BaseBrowseForm.getSortOrder(sortField, order, defaultSort));
  }

  protected abstract load(
    first: number,
    pageSize: number,
    sortField: string,
    order: number,
    params: Map<string, string>
  ): void;

  protected abstract load(
    first: number,
    pageSize: number,
    sortField: string,
    order: number,
    params: Map<string, string>,
    extendedParams?: ExtendedSearchParam[]
  ): void;

  loadDataLazy(event: TableLazyLoadEvent): void {
    this.doLoad(
      event.first,
      event.rows,
      typeof event.sortField === 'string' ? event.sortField : undefined,
      event.sortOrder,
      this.getSearchParamMap(event),
      this.useExtendedSearchParams ? this.getExtendedSearchParams(event) : undefined
    );
  }

  search(table: Table, resetPage = true): void {
    table.first = resetPage ? 0 : table.first;
    this.doLoad(
      table.first,
      table.rows,
      typeof table.sortField === 'string' ? table.sortField : undefined,
      table.sortOrder,
      this.getSearchParamMap(table),
      this.useExtendedSearchParams ? this.getExtendedSearchParams(table) : undefined
    );
  }

  protected doLoad(
    first: number,
    pageSize: number,
    sortField: string,
    order: number,
    params: Map<string, string>,
    extendedParams?: ExtendedSearchParam[]
  ): void {
    if (this.keepFilters) {
      const searchData = this.getSearchDataFromSession();
      if (searchData) {
        if (typeof searchData.first === 'number') {
          first = searchData.first;
          if (this.dataTableComponent) {
            this.dataTableComponent.first = searchData.first;
          }
        }
        if (typeof searchData.pageSize === 'number') {
          this.showRows = searchData.pageSize;
        }
        if (typeof searchData.sortField === 'string') {
          sortField = searchData.sortField;
        }
        if (typeof searchData.order === 'number') {
          order = searchData.order;
        }
        if (searchData.paramsObj) {
          params = new Map(Object.entries(searchData.paramsObj));
        }
        if (searchData.extendedParams) {
          extendedParams = searchData.extendedParams;
        }
        this.loadSearchDataIntoSearchForm();
      }
      this.keepFilters = false;
    }
    this.saveSearchDataToSession(first, pageSize, sortField, order, params, extendedParams);
    if (this.useExtendedSearchParams) {
      this.load(first, pageSize, sortField, order, params, extendedParams);
    } else {
      this.load(first, pageSize, sortField, order, params);
    }
  }

  reload(resetPage = true): void {
    this.search(this.dataTableComponent, resetPage);
  }

  protected getFilterParamNameMapping(): Record<string, string> {
    return {};
  }

  protected restoreOrClearFilters(): void {
    if (this.keepFilters) {
      const searchData = this.getSearchDataFromSession();
      if (searchData) {
        if (typeof searchData.pageSize === 'number') {
          this.showRows = searchData.pageSize;
        }
      }
    } else {
      this.removeSearchDataFromSession();
    }
  }

  protected formatSearchParamName(fieldName: string): string {
    return this.getFilterParamNameMapping()[fieldName] || 'p_' + fieldName;
  }

  protected addItemToSearchParamMap(map: Map<string, string>, filterName: string, value: unknown): Map<string, string> {
    return map.set(this.formatSearchParamName(filterName), this.formatSearchParamValue(value));
  }

  /**
   * Pridėtas ELSE, jei gauti duomenys yra ne ExtendedSearchParam tipo,
   * ELSE sąlygoje sukuriamas ExtendedSearchParam objektas ir pridedamas į
   * paramsArray masyvą.
   * Pridėtas key parametra gauti controls`o pavadinimą.
   */
  protected addItemToExtendedSearchParams(
    paramsArray: ExtendedSearchParam[],
    value: ExtendedSearchParam,
    key?: string
  ): void {
    if (value instanceof Object && value.paramName && value.paramValue) {
      paramsArray.push(value);
    } else {
      paramsArray.push({
        paramName: key,
        paramValue: {
          condition: ExtendedSearchCondition.Equals,
          value: this.searchForm.controls[key]?.value as string,
          upperLower: ExtendedSearchUpperLower.Regular,
        },
      });
    }
  }

  /**
   * Buvo pridėtas tikrinimas ar this.searchForm.controls turi duomenis,
   * jei this.searchForm.controls turi duomenis, taip pat tikrina ar gauti duomenys
   * nėra ExtendedSearchParam tipo, jei sąlyga pasitvirtina duomenys pridedami į PARAMS masyvą.
   */
  // eslint-disable-next-line @typescript-eslint/no-unused-vars
  protected getSearchParamMap(event: TableLazyLoadEvent | Table): Map<string, string> {
    const result = new Map<string, string>();
    if (this.searchForm) {
      Object.keys(this.searchForm.controls).forEach((key) => {
        if (
          !this.searchForm.controls[key].value === false &&
          !!(this.searchForm.controls[key].value as ExtendedSearchParam).paramValue === false
        ) {
          this.addItemToSearchParamMap(result, key, this.searchForm.controls[key].value);
        }
      });
    }
    return result;
  }

  protected getExtendedSearchParams(event: TableLazyLoadEvent | Table): ExtendedSearchParam[] {
    const result: ExtendedSearchParam[] = [];
    if (event && event.filters) {
      const filterFields = Object.getOwnPropertyNames(event.filters);

      filterFields.forEach((filterName) => {
        if (Array.isArray(event.filters[filterName])) {
          const filters = event.filters[filterName] as FilterMetadata[];
          filters.forEach((filter) => {
            if (filter.value) {
              this.addItemToExtendedSearchParams(result, filter.value as ExtendedSearchParam);
            }
          });
        } else {
          const filter = event.filters[filterName] as FilterMetadata;

          if (filter.value) {
            this.addItemToExtendedSearchParams(result, filter.value as ExtendedSearchParam);
          }
        }
      });
    }
    if (this.searchForm) {
      Object.keys(this.searchForm.controls).forEach((key) => {
        if (this.searchForm.controls[key]?.value) {
          this.addItemToExtendedSearchParams(result, this.searchForm.controls[key]?.value as ExtendedSearchParam, key);
        }
      });
    }
    return result;
  }

  protected formatSearchParamValue(value: unknown): string {
    if (typeof value === 'string') {
      return value;
    } else if (value instanceof Date) {
      return value.toISOString();
    } else if (value instanceof Array) {
      return JSON.stringify(value.map((val) => this.formatSearchParamValue(val)));
    } else if (typeof value === 'object') {
      return Object.keys(value)?.length === 0 ? undefined : JSON.stringify(value);
    } else if (typeof value === 'boolean') {
      return JSON.stringify(value);
    }
    return undefined;
  }

  protected getSortOrder(sortField: string, order: number, defaultSort: string): string {
    return BaseBrowseForm.getSortOrder(sortField, order, defaultSort);
  }

  protected getPagingParams(
    first: number,
    pageSize: number,
    sortField: string,
    order: number,
    defaultSort: string
  ): SprPaging {
    return BaseBrowseForm.getPagingParams(first, pageSize, sortField, order, defaultSort);
  }

  protected getDefaultRowsToShow(): number {
    return BaseBrowseForm.DEFAULT_ROWS_TO_SHOW;
  }

  protected abstract deleteRecord(recordId: string): void;

  protected deleteRecordWithConfirmation(
    recordId: string,
    message: string = 'common.message.deleteConfirmationMsg',
    translateMessage: boolean = true
  ): void {
    const showConfirmationMessage = (confirmationMessage: string): void => {
      this.commonFormServices.confirmationService.confirm({
        message: confirmationMessage,
        accept: () => {
          this.deleteRecord(recordId);
        },
      });
    };
    if (translateMessage) {
      this.commonFormServices.translate.get(message).subscribe((translation: string) => {
        showConfirmationMessage(translation);
      });
    } else {
      showConfirmationMessage(message);
    }
  }

  protected saveSearchDataToSession(
    first: number,
    pageSize: number,
    sortField: string,
    order: number,
    params: Map<string, string>,
    extendedParams?: ExtendedSearchParam[]
  ): void {
    const browseSearchData: BrowseSearchData = {
      url: this.commonFormServices.router.url,
      paramsObj: Object.fromEntries(params),
      extendedParams,
      searchFormValues: Object.keys(this.searchForm.controls)
        .filter((controlName) => this.searchForm.controls[controlName]?.value)
        .reduce(
          (valuesObj, controlName) => ({
            ...valuesObj,
            [controlName]: this.searchForm.controls[controlName].value as unknown,
          }),
          {} as Record<string, unknown>
        ),
      first,
      pageSize,
      sortField,
      order,
    };
    sessionStorage.setItem(BROWSE_SEARCH_DATA_KEY, JSON.stringify(browseSearchData));
  }

  protected getSearchDataFromSession(): BrowseSearchData | null {
    const searchData = JSON.parse(sessionStorage.getItem(BROWSE_SEARCH_DATA_KEY)) as BrowseSearchData;
    if (searchData) {
      if (searchData?.url === this.commonFormServices.router.url) {
        return searchData;
      }
    }
    return null;
  }

  protected removeSearchDataFromSession(): void {
    sessionStorage.removeItem(BROWSE_SEARCH_DATA_KEY);
  }

  protected loadSearchDataIntoSearchForm(): void {
    const searchData = this.getSearchDataFromSession();
    if (searchData?.searchFormValues) {
      Object.keys(searchData.searchFormValues).forEach((controlName) => {
        if (this.searchForm.controls[controlName]) {
          this.searchForm.controls[controlName].setValue(searchData.searchFormValues[controlName]);
        }
      });
    }
  }
}
