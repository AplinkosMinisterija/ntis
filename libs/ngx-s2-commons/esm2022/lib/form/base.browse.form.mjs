import { FormGroup } from '@angular/forms';
import { Component, ViewChild } from '@angular/core';
import { Table } from 'primeng/table';
import { Spr_paging_ot } from '../model/spr_paging_ot';
import { ExtendedSearchCondition, ExtendedSearchUpperLower } from '../enums/extended-search';
import * as i0 from "@angular/core";
import * as i1 from "./common.form.services";
export const BROWSE_SEARCH_DATA_KEY = 'browseSearchData';
class BaseBrowseForm {
    commonFormServices;
    static DEFAULT_ROWS_TO_SHOW = 20;
    keepFilters;
    data;
    totalRecords;
    rowsPerPageOptions = [20, 50, 100];
    showRows = this.getDefaultRowsToShow();
    searchForm = new FormGroup({});
    useExtendedSearchParams = false;
    dataTableComponent;
    constructor(commonFormServices) {
        this.commonFormServices = commonFormServices;
        this.keepFilters = !!this.commonFormServices?.router?.getCurrentNavigation()?.extras?.state?.keepFilters;
        this.restoreOrClearFilters();
    }
    static getSortOrder(sortField, order, defaultSort) {
        let orderClause = defaultSort;
        if (sortField) {
            orderClause = sortField + (order === 1 ? ' ASC' : ' DESC');
        }
        return orderClause;
    }
    static getPagingParams(first, pageSize, sortField, order, defaultSort) {
        return new Spr_paging_ot(null, first, pageSize, BaseBrowseForm.getSortOrder(sortField, order, defaultSort));
    }
    loadDataLazy(event) {
        this.doLoad(event.first, event.rows, typeof event.sortField === 'string' ? event.sortField : undefined, event.sortOrder, this.getSearchParamMap(event), this.useExtendedSearchParams ? this.getExtendedSearchParams(event) : undefined);
    }
    search(table, resetPage = true) {
        table.first = resetPage ? 0 : table.first;
        this.doLoad(table.first, table.rows, typeof table.sortField === 'string' ? table.sortField : undefined, table.sortOrder, this.getSearchParamMap(table), this.useExtendedSearchParams ? this.getExtendedSearchParams(table) : undefined);
    }
    doLoad(first, pageSize, sortField, order, params, extendedParams) {
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
        }
        else {
            this.load(first, pageSize, sortField, order, params);
        }
    }
    reload(resetPage = true) {
        this.search(this.dataTableComponent, resetPage);
    }
    getFilterParamNameMapping() {
        return {};
    }
    restoreOrClearFilters() {
        if (this.keepFilters) {
            const searchData = this.getSearchDataFromSession();
            if (searchData) {
                if (typeof searchData.pageSize === 'number') {
                    this.showRows = searchData.pageSize;
                }
            }
        }
        else {
            this.removeSearchDataFromSession();
        }
    }
    formatSearchParamName(fieldName) {
        return this.getFilterParamNameMapping()[fieldName] || 'p_' + fieldName;
    }
    addItemToSearchParamMap(map, filterName, value) {
        return map.set(this.formatSearchParamName(filterName), this.formatValue(value));
    }
    /**
     * Pridėtas ELSE, jei gauti duomenys yra ne ExtendedSearchParam tipo,
     * ELSE sąlygoje sukuriamas ExtendedSearchParam objektas ir pridedamas į
     * paramsArray masyvą.
     * Pridėtas key parametra gauti controls`o pavadinimą.
     */
    addItemToExtendedSearchParams(paramsArray, value, key) {
        if (value instanceof Object && value.paramName && value.paramValue) {
            paramsArray.push(value);
        }
        else {
            paramsArray.push({
                paramName: key,
                paramValue: {
                    condition: ExtendedSearchCondition.Equals,
                    value: this.searchForm.controls[key]?.value,
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
    getSearchParamMap(event) {
        const result = new Map();
        if (event && event.filters) {
            const filterFields = Object.getOwnPropertyNames(event.filters);
        }
        if (this.searchForm) {
            Object.keys(this.searchForm.controls).forEach((key) => {
                if (!this.searchForm.controls[key].value === false &&
                    !!this.searchForm.controls[key].value.paramValue === false) {
                    this.addItemToSearchParamMap(result, key, `${this.searchForm.controls[key].value}`);
                }
            });
        }
        return result;
    }
    getExtendedSearchParams(event) {
        const result = [];
        if (event && event.filters) {
            const filterFields = Object.getOwnPropertyNames(event.filters);
            filterFields.forEach((filterName) => {
                if (Array.isArray(event.filters[filterName])) {
                    const filters = event.filters[filterName];
                    filters.forEach((filter) => {
                        if (filter.value) {
                            this.addItemToExtendedSearchParams(result, filter.value);
                        }
                    });
                }
                else {
                    const filter = event.filters[filterName];
                    if (filter.value) {
                        this.addItemToExtendedSearchParams(result, filter.value);
                    }
                }
            });
        }
        if (this.searchForm) {
            Object.keys(this.searchForm.controls).forEach((key) => {
                if (this.searchForm.controls[key]?.value) {
                    this.addItemToExtendedSearchParams(result, this.searchForm.controls[key]?.value, key);
                }
            });
        }
        return result;
    }
    instanceOfIdValuesOt(object) {
        return (object instanceof Object &&
            Object.prototype.hasOwnProperty.call(object, 'id') &&
            Object.prototype.hasOwnProperty.call(object, 'key_value'));
    }
    instanceOfKeyValuesOt(object) {
        return (object instanceof Object &&
            Object.prototype.hasOwnProperty.call(object, 'code') &&
            Object.prototype.hasOwnProperty.call(object, 'key_value'));
    }
    formatValue(value) {
        if (value instanceof Date) {
            return value.toISOString();
        }
        else if (this.instanceOfIdValuesOt(value)) {
            return value.id;
        }
        else if (this.instanceOfKeyValuesOt(value)) {
            return value.code;
        }
        else if (value instanceof Array) {
            return value.map((val) => this.formatValue(val));
        }
        else if (value === null || Object.keys(value).length === 0) {
            return undefined;
        }
        return value;
    }
    getSortOrder(sortField, order, defaultSort) {
        return BaseBrowseForm.getSortOrder(sortField, order, defaultSort);
    }
    getPagingParams(first, pageSize, sortField, order, defaultSort) {
        return BaseBrowseForm.getPagingParams(first, pageSize, sortField, order, defaultSort);
    }
    getDefaultRowsToShow() {
        return BaseBrowseForm.DEFAULT_ROWS_TO_SHOW;
    }
    deleteRecordWithConfirmation(recordId, message = 'common.message.deleteConfirmationMsg', translateMessage = true) {
        const showConfirmationMessage = (confirmationMessage) => {
            this.commonFormServices.confirmationService.confirm({
                message: confirmationMessage,
                accept: () => {
                    this.deleteRecord(recordId);
                },
            });
        };
        if (translateMessage) {
            this.commonFormServices.translate.get(message).subscribe((translation) => {
                showConfirmationMessage(translation);
            });
        }
        else {
            showConfirmationMessage(message);
        }
    }
    saveSearchDataToSession(first, pageSize, sortField, order, params, extendedParams) {
        const browseSearchData = {
            url: this.commonFormServices.router.url,
            paramsObj: Object.fromEntries(params),
            extendedParams,
            searchFormValues: Object.keys(this.searchForm.controls)
                .filter((controlName) => this.searchForm.controls[controlName]?.value)
                .reduce((valuesObj, controlName) => ({
                ...valuesObj,
                [controlName]: this.searchForm.controls[controlName].value,
            }), {}),
            first,
            pageSize,
            sortField,
            order,
        };
        sessionStorage.setItem(BROWSE_SEARCH_DATA_KEY, JSON.stringify(browseSearchData));
    }
    getSearchDataFromSession() {
        const searchData = JSON.parse(sessionStorage.getItem(BROWSE_SEARCH_DATA_KEY));
        if (searchData) {
            if (searchData?.url === this.commonFormServices.router.url) {
                return searchData;
            }
        }
        return null;
    }
    removeSearchDataFromSession() {
        sessionStorage.removeItem(BROWSE_SEARCH_DATA_KEY);
    }
    loadSearchDataIntoSearchForm() {
        const searchData = this.getSearchDataFromSession();
        if (searchData?.searchFormValues) {
            Object.keys(searchData.searchFormValues).forEach((controlName) => {
                if (this.searchForm.controls[controlName]) {
                    this.searchForm.controls[controlName].setValue(searchData.searchFormValues[controlName]);
                }
            });
        }
    }
    static ɵfac = i0.ɵɵngDeclareFactory({ minVersion: "12.0.0", version: "16.0.2", ngImport: i0, type: BaseBrowseForm, deps: [{ token: i1.CommonFormServices }], target: i0.ɵɵFactoryTarget.Component });
    static ɵcmp = i0.ɵɵngDeclareComponent({ minVersion: "14.0.0", version: "16.0.2", type: BaseBrowseForm, selector: "ng-component", viewQueries: [{ propertyName: "dataTableComponent", first: true, predicate: Table, descendants: true, static: true }], ngImport: i0, template: '', isInline: true });
}
export { BaseBrowseForm };
i0.ɵɵngDeclareClassMetadata({ minVersion: "12.0.0", version: "16.0.2", ngImport: i0, type: BaseBrowseForm, decorators: [{
            type: Component,
            args: [{ template: '' }]
        }], ctorParameters: function () { return [{ type: i1.CommonFormServices }]; }, propDecorators: { dataTableComponent: [{
                type: ViewChild,
                args: [Table, { static: true }]
            }] } });
//# sourceMappingURL=data:application/json;base64,eyJ2ZXJzaW9uIjozLCJmaWxlIjoiYmFzZS5icm93c2UuZm9ybS5qcyIsInNvdXJjZVJvb3QiOiIiLCJzb3VyY2VzIjpbIi4uLy4uLy4uLy4uLy4uL3Byb2plY3RzL25neC1zMi1jb21tb25zL3NyYy9saWIvZm9ybS9iYXNlLmJyb3dzZS5mb3JtLnRzIl0sIm5hbWVzIjpbXSwibWFwcGluZ3MiOiJBQUNBLE9BQU8sRUFBRSxTQUFTLEVBQUUsTUFBTSxnQkFBZ0IsQ0FBQztBQUMzQyxPQUFPLEVBQUUsU0FBUyxFQUFFLFNBQVMsRUFBRSxNQUFNLGVBQWUsQ0FBQztBQUVyRCxPQUFPLEVBQUUsS0FBSyxFQUFzQixNQUFNLGVBQWUsQ0FBQztBQUMxRCxPQUFPLEVBQUUsYUFBYSxFQUFFLE1BQU0sd0JBQXdCLENBQUM7QUFJdkQsT0FBTyxFQUFFLHVCQUF1QixFQUFFLHdCQUF3QixFQUFFLE1BQU0sMEJBQTBCLENBQUM7OztBQUU3RixNQUFNLENBQUMsTUFBTSxzQkFBc0IsR0FBRyxrQkFBa0IsQ0FBQztBQWF6RCxNQUVzQixjQUFjO0lBYVo7SUFaZixNQUFNLENBQUMsb0JBQW9CLEdBQUcsRUFBRSxDQUFDO0lBRXhDLFdBQVcsQ0FBVTtJQUNyQixJQUFJLENBQU07SUFDVixZQUFZLENBQVM7SUFDckIsa0JBQWtCLEdBQUcsQ0FBQyxFQUFFLEVBQUUsRUFBRSxFQUFFLEdBQUcsQ0FBQyxDQUFDO0lBQ25DLFFBQVEsR0FBRyxJQUFJLENBQUMsb0JBQW9CLEVBQUUsQ0FBQztJQUN2QyxVQUFVLEdBQWMsSUFBSSxTQUFTLENBQUMsRUFBRSxDQUFDLENBQUM7SUFDMUMsdUJBQXVCLEdBQUcsS0FBSyxDQUFDO0lBRUksa0JBQWtCLENBQVE7SUFFOUQsWUFBc0Isa0JBQXNDO1FBQXRDLHVCQUFrQixHQUFsQixrQkFBa0IsQ0FBb0I7UUFDMUQsSUFBSSxDQUFDLFdBQVcsR0FBRyxDQUFDLENBQUMsSUFBSSxDQUFDLGtCQUFrQixFQUFFLE1BQU0sRUFBRSxvQkFBb0IsRUFBRSxFQUFFLE1BQU0sRUFBRSxLQUFLLEVB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TyxJQUFJLENBQUM7SUFDZCxDQUFDO0lBRVMsMkJBQTJCO1FBQ25DLGNBQWMsQ0FBQyxVQUFVLENBQUMsc0JBQXNCLENBQUMsQ0FBQztJQUNwRCxDQUFDO0lBRVMsNEJBQTRCO1FBQ3BDLE1BQU0sVUFBVSxHQUFHLElBQUksQ0FBQyx3QkFBd0IsRUFBRSxDQUFDO1FBQ25ELElBQUksVUFBVSxFQUFFLGdCQUFnQixFQUFFO1lBQ2hDLE1BQU0sQ0FBQyxJQUFJLENBQUMsVUFBVSxDQUFDLGdCQUFnQixDQUFDLENBQUMsT0FBTyxDQUFDLENBQUMsV0FBVyxFQUFFLEVBQUU7Z0JBQy9ELElBQUksSUFBSSxDQUFDLFVBQVUsQ0FBQyxRQUFRLENBQUMsV0FBVyxDQUFDLEVBQUU7b0JBQ3pDLElBQUksQ0FBQyxVQUFVLENBQUMsUUFBUSxDQUFDLFdBQVcsQ0FBQyxDQUFDLFFBQVEsQ0FBQyxVQUFVLENBQUMsZ0JBQWdCLENBQUMsV0FBVyxDQUFDLENBQUMsQ0FBQztpQkFDMUY7WUFDSCxDQUFDLENBQUMsQ0FBQztTQUNKO0lBQ0gsQ0FBQzt1R0FwV21CLGNBQWM7MkZBQWQsY0FBYyx3R0FXdkIsS0FBSyw4REFiSyxFQUFFOztTQUVILGNBQWM7MkZBQWQsY0FBYztrQkFGbkMsU0FBUzttQkFBQyxFQUFFLFFBQVEsRUFBRSxFQUFFLEVBQUU7eUdBYVcsa0JBQWtCO3NCQUFyRCxTQUFTO3VCQUFDLEtBQUssRUFBRSxFQUFFLE1BQU0sRUFBRSxJQUFJLEVBQUUiLCJzb3VyY2VzQ29udGVudCI6WyJpbXBvcnQgeyBTcHJfaWRfdmFsdWVzX290IH0gZnJvbSAnLi4vbW9kZWwvc3ByX2lkX3ZhbHVlc19vdCc7XG5pbXBvcnQgeyBGb3JtR3JvdXAgfSBmcm9tICdAYW5ndWxhci9mb3Jtcyc7XG5pbXBvcnQgeyBDb21wb25lbnQsIFZpZXdDaGlsZCB9IGZyb20gJ0Bhbmd1bGFyL2NvcmUnO1xuaW1wb3J0IHsgRmlsdGVyTWV0YWRhdGEgfSBmcm9tICdwcmltZW5nL2FwaSc7XG5pbXBvcnQgeyBUYWJsZSwgVGFibGVMYXp5TG9hZEV2ZW50IH0gZnJvbSAncHJpbWVuZy90YWJsZSc7XG5pbXBvcnQgeyBTcHJfcGFnaW5nX290IH0gZnJvbSAnLi4vbW9kZWwvc3ByX3BhZ2luZ19vdCc7XG5pbXBvcnQgeyBDb21tb25Gb3JtU2VydmljZXMgfSBmcm9tICcuL2NvbW1vbi5mb3JtLnNlcnZpY2VzJztcbmltcG9ydCB7IFNwcl9rZXlfdmFsdWVzX290IH0gZnJvbSAnLi4vbW9kZWwvc3ByX2tleV92YWx1ZXNfb3QnO1xuaW1wb3J0IHsgRXh0ZW5kZWRTZWFyY2hQYXJhbSB9IGZyb20gJy4uL21vZGVsL2V4dGVuZGVkLXNlYXJjaCc7XG5pbXBvcnQgeyBFeHRlbmRlZFNlYXJjaENvbmRpdGlvbiwgRXh0ZW5kZWRTZWFyY2hVcHBlckxvd2VyIH0gZnJvbSAnLi4vZW51bXMvZXh0ZW5kZWQtc2VhcmNoJztcblxuZXhwb3J0IGNvbnN0IEJST1dTRV9TRUFSQ0hfREFUQV9LRVkgPSAnYnJvd3NlU2VhcmNoRGF0YSc7XG5cbmludGVyZmFjZSBCcm93c2VTZWFyY2hEYXRhIHtcbiAgdXJsOiBzdHJpbmc7XG4gIHBhcmFtc09iajogUmVjb3JkPHN0cmluZywgdW5rbm93bj47XG4gIGV4dGVuZGVkUGFyYW1zOiBFeHRlbmRlZFNlYXJjaFBhcmFtW107XG4gIHNlYXJjaEZvcm1WYWx1ZXM6IFJlY29yZDxzdHJpbmcsIHVua25vd24+O1xuICBmaXJzdDogbnVtYmVyO1xuICBwYWdlU2l6ZTogbnVtYmVyO1xuICBzb3J0RmllbGQ6IHN0cmluZztcbiAgb3JkZXI6IG51bWJlcjtcbn1cblxuQENvbXBvbmVudCh7IHRlbXBsYXRlOiAnJyB9KVxuLy8gZXNsaW50LWRpc2FibGUtbmV4dC1saW5lIEBhbmd1bGFyLWVzbGludC9jb21wb25lbnQtY2xhc3Mtc3VmZml4XG5leHBvcnQgYWJzdHJhY3QgY2xhc3MgQmFzZUJyb3dzZUZvcm08VD4ge1xuICBwdWJsaWMgc3RhdGljIERFRkFVTFRfUk9XU19UT19TSE9XID0gMjA7XG5cbiAga2VlcEZpbHRlcnM6IGJvb2xlYW47XG4gIGRhdGE6IFRbXTtcbiAgdG90YWxSZWNvcmRzOiBudW1iZXI7XG4gIHJvd3NQZXJQYWdlT3B0aW9ucyA9IFsyMCwgNTAsIDEwMF07XG4gIHNob3dSb3dzID0gdGhpcy5nZXREZWZhdWx0Um93c1RvU2hvdygpO1xuICBzZWFyY2hGb3JtOiBGb3JtR3JvdXAgPSBuZXcgRm9ybUdyb3VwKHt9KTtcbiAgdXNlRXh0ZW5kZWRTZWFyY2hQYXJhbXMgPSBmYWxzZTtcblxuICBAVmlld0NoaWxkKFRhYmxlLCB7IHN0YXRpYzogdHJ1ZSB9KSBkYXRhVGFibGVDb21wb25lbnQ6IFRhYmxlO1xuXG4gIGNvbnN0cnVjdG9yKHByb3RlY3RlZCBjb21tb25Gb3JtU2VydmljZXM6IENvbW1vbkZvcm1TZXJ2aWNlcykge1xuICAgIHRoaXMua2VlcEZpbHRlcnMgPSAhIXRoaXMuY29tbW9uRm9ybVNlcnZpY2VzPy5yb3V0ZXI/LmdldEN1cnJlbnROYXZpZ2F0aW9uKCk/LmV4dHJhcz8uc3RhdGU/LmtlZXBGaWx0ZXJzO1xuICAgIHRoaXMucmVzdG9yZU9yQ2xlYXJGaWx0ZXJzKCk7XG4gIH1cblxuICBzdGF0aWMgZ2V0U29ydE9yZGVyKHNvcnRGaWVsZDogc3RyaW5nLCBvcmRlcjogbnVtYmVyLCBkZWZhdWx0U29ydDogc3RyaW5nKTogc3RyaW5nIHtcbiAgICBsZXQgb3JkZXJDbGF1c2U6IHN0cmluZyA9IGRlZmF1bHRTb3J0O1xuICAgIGlmIChzb3J0RmllbGQpIHtcbiAgICAgIG9yZGVyQ2xhdXNlID0gc29ydEZpZWxkICsgKG9yZGVyID09PSAxID8gJyBBU0MnIDogJyBERVNDJyk7XG4gICAgfVxuICAgIHJldHVybiBvcmRlckNsYXVzZTtcbiAgfVxuXG4gIHN0YXRpYyBnZXRQYWdpbmdQYXJhbXMoXG4gICAgZmlyc3Q6IG51bWJlcixcbiAgICBwYWdlU2l6ZTogbnVtYmVyLFxuICAgIHNvcnRGaWVsZDogc3RyaW5nLFxuICAgIG9yZGVyOiBudW1iZXIsXG4gICAgZGVmYXVsdFNvcnQ6IHN0cmluZ1xuICApOiBTcHJfcGFnaW5nX290IHtcbiAgICByZXR1cm4gbmV3IFNwcl9wYWdpbmdfb3QobnVsbCwgZmlyc3QsIHBhZ2VTaXplLCBCYXNlQnJvd3NlRm9ybS5nZXRTb3J0T3JkZXIoc29ydEZpZWxkLCBvcmRlciwgZGVmYXVsdFNvcnQpKTtcbiAgfVxuXG4gIHByb3RlY3RlZCBhYnN0cmFjdCBsb2FkKFxuICAgIGZpcnN0OiBudW1iZXIsXG4gICAgcGFnZVNpemU6IG51bWJlcixcbiAgICBzb3J0RmllbGQ6IHN0cmluZyxcbiAgICBvcmRlcjogbnVtYmVyLFxuICAgIHBhcmFtczogTWFwPHN0cmluZywgdW5rbm93bj5cbiAgKTogdm9pZDtcblxuICBwcm90ZWN0ZWQgYWJzdHJhY3QgbG9hZChcbiAgICBmaXJzdDogbnVtYmVyLFxuICAgIHBhZ2VTaXplOiBudW1iZXIsXG4gICAgc29ydEZpZWxkOiBzdHJpbmcsXG4gICAgb3JkZXI6IG51bWJlcixcbiAgICBwYXJhbXM6IE1hcDxzdHJpbmcsIHVua25vd24+LFxuICAgIGV4dGVuZGVkUGFyYW1zPzogRXh0ZW5kZWRTZWFyY2hQYXJhbVtdXG4gICk6IHZvaWQ7XG5cbiAgbG9hZERhdGFMYXp5KGV2ZW50OiBUYWJsZUxhenlMb2FkRXZlbnQpOiB2b2lkIHtcbiAgICB0aGlzLmRvTG9hZChcbiAgICAgIGV2ZW50LmZpcnN0LFxuICAgICAgZXZlbnQucm93cyxcbiAgICAgIHR5cGVvZiBldmVudC5zb3J0RmllbGQgPT09ICdzdHJpbmcnID8gZXZlbnQuc29ydEZpZWxkIDogdW5kZWZpbmVkLFxuICAgICAgZXZlbnQuc29ydE9yZGVyLFxuICAgICAgdGhpcy5nZXRTZWFyY2hQYXJhbU1hcChldmVudCksXG4gICAgICB0aGlzLnVzZUV4dGVuZGVkU2VhcmNoUGFyYW1zID8gdGhpcy5nZXRFeHRlbmRlZFNlYXJjaFBhcmFtcyhldmVudCkgOiB1bmRlZmluZWRcbiAgICApO1xuICB9XG5cbiAgc2VhcmNoKHRhYmxlOiBUYWJsZSwgcmVzZXRQYWdlID0gdHJ1ZSk6IHZvaWQge1xuICAgIHRhYmxlLmZpcnN0ID0gcmVzZXRQYWdlID8gMCA6IHRhYmxlLmZpcnN0O1xuICAgIHRoaXMuZG9Mb2FkKFxuICAgICAgdGFibGUuZmlyc3QsXG4gICAgICB0YWJsZS5yb3dzLFxuICAgICAgdHlwZW9mIHRhYmxlLnNvcnRGaWVsZCA9PT0gJ3N0cmluZycgPyB0YWJsZS5zb3J0RmllbGQgOiB1bmRlZmluZWQsXG4gICAgICB0YWJsZS5zb3J0T3JkZXIsXG4gICAgICB0aGlzLmdldFNlYXJjaFBhcmFtTWFwKHRhYmxlKSxcbiAgICAgIHRoaXMudXNlRXh0ZW5kZWRTZWFyY2hQYXJhbXMgPyB0aGlzLmdldEV4dGVuZGVkU2VhcmNoUGFyYW1zKHRhYmxlKSA6IHVuZGVmaW5lZFxuICAgICk7XG4gIH1cblxuICBwcm90ZWN0ZWQgZG9Mb2FkKFxuICAgIGZpcnN0OiBudW1iZXIsXG4gICAgcGFnZVNpemU6IG51bWJlcixcbiAgICBzb3J0RmllbGQ6IHN0cmluZyxcbiAgICBvcmRlcjogbnVtYmVyLFxuICAgIHBhcmFtczogTWFwPHN0cmluZywgdW5rbm93bj4sXG4gICAgZXh0ZW5kZWRQYXJhbXM/OiBFeHRlbmRlZFNlYXJjaFBhcmFtW11cbiAgKTogdm9pZCB7XG4gICAgaWYgKHRoaXMua2VlcEZpbHRlcnMpIHtcbiAgICAgIGNvbnN0IHNlYXJjaERhdGEgPSB0aGlzLmdldFNlYXJjaERhdGFGcm9tU2Vzc2lvbigpO1xuICAgICAgaWYgKHNlYXJjaERhdGEpIHtcbiAgICAgICAgaWYgKHR5cGVvZiBzZWFyY2hEYXRhLmZpcnN0ID09PSAnbnVtYmVyJykge1xuICAgICAgICAgIGZpcnN0ID0gc2VhcmNoRGF0YS5maXJzdDtcbiAgICAgICAgICBpZiAodGhpcy5kYXRhVGFibGVDb21wb25lbnQpIHtcbiAgICAgICAgICAgIHRoaXMuZGF0YVRhYmxlQ29tcG9uZW50LmZpcnN0ID0gc2VhcmNoRGF0YS5maXJzdDtcbiAgICAgICAgICB9XG4gICAgICAgIH1cbiAgICAgICAgaWYgKHR5cGVvZiBzZWFyY2hEYXRhLnBhZ2VTaXplID09PSAnbnVtYmVyJykge1xuICAgICAgICAgIHRoaXMuc2hvd1Jvd3MgPSBzZWFyY2hEYXRhLnBhZ2VTaXplO1xuICAgICAgICB9XG4gICAgICAgIGlmICh0eXBlb2Ygc2VhcmNoRGF0YS5zb3J0RmllbGQgPT09ICdzdHJpbmcnKSB7XG4gICAgICAgICAgc29ydEZpZWxkID0gc2VhcmNoRGF0YS5zb3J0RmllbGQ7XG4gICAgICAgIH1cbiAgICAgICAgaWYgKHR5cGVvZiBzZWFyY2hEYXRhLm9yZGVyID09PSAnbnVtYmVyJykge1xuICAgICAgICAgIG9yZGVyID0gc2VhcmNoRGF0YS5vcmRlcjtcbiAgICAgICAgfVxuICAgICAgICBpZiAoc2VhcmNoRGF0YS5wYXJhbXNPYmopIHtcbiAgICAgICAgICBwYXJhbXMgPSBuZXcgTWFwKE9iamVjdC5lbnRyaWVzKHNlYXJjaERhdGEucGFyYW1zT2JqKSk7XG4gICAgICAgIH1cbiAgICAgICAgaWYgKHNlYXJjaERhdGEuZXh0ZW5kZWRQYXJhbXMpIHtcbiAgICAgICAgICBleHRlbmRlZFBhcmFtcyA9IHNlYXJjaERhdGEuZXh0ZW5kZWRQYXJhbXM7XG4gICAgICAgIH1cbiAgICAgICAgdGhpcy5sb2FkU2VhcmNoRGF0YUludG9TZWFyY2hGb3JtKCk7XG4gICAgICB9XG4gICAgICB0aGlzLmtlZXBGaWx0ZXJzID0gZmFsc2U7XG4gICAgfVxuICAgIHRoaXMuc2F2ZVNlYXJjaERhdGFUb1Nlc3Npb24oZmlyc3QsIHBhZ2VTaXplLCBzb3J0RmllbGQsIG9yZGVyLCBwYXJhbXMsIGV4dGVuZGVkUGFyYW1zKTtcbiAgICBpZiAodGhpcy51c2VFeHRlbmRlZFNlYXJjaFBhcmFtcykge1xuICAgICAgdGhpcy5sb2FkKGZpcnN0LCBwYWdlU2l6ZSwgc29ydEZpZWxkLCBvcmRlciwgcGFyYW1zLCBleHRlbmRlZFBhcmFtcyk7XG4gICAgfSBlbHNlIHtcbiAgICAgIHRoaXMubG9hZChmaXJzdCwgcGFnZVNpemUsIHNvcnRGaWVsZCwgb3JkZXIsIHBhcmFtcyk7XG4gICAgfVxuICB9XG5cbiAgcmVsb2FkKHJlc2V0UGFnZSA9IHRydWUpOiB2b2lkIHtcbiAgICB0aGlzLnNlYXJjaCh0aGlzLmRhdGFUYWJsZUNvbXBvbmVudCwgcmVzZXRQYWdlKTtcbiAgfVxuXG4gIHByb3RlY3RlZCBnZXRGaWx0ZXJQYXJhbU5hbWVNYXBwaW5nKCk6IFJlY29yZDxzdHJpbmcsIHN0cmluZz4ge1xuICAgIHJldHVybiB7fTtcbiAgfVxuXG4gIHByb3RlY3RlZCByZXN0b3JlT3JDbGVhckZpbHRlcnMoKTogdm9pZCB7XG4gICAgaWYgKHRoaXMua2VlcEZpbHRlcnMpIHtcbiAgICAgIGNvbnN0IHNlYXJjaERhdGEgPSB0aGlzLmdldFNlYXJjaERhdGFGcm9tU2Vzc2lvbigpO1xuICAgICAgaWYgKHNlYXJjaERhdGEpIHtcbiAgICAgICAgaWYgKHR5cGVvZiBzZWFyY2hEYXRhLnBhZ2VTaXplID09PSAnbnVtYmVyJykge1xuICAgICAgICAgIHRoaXMuc2hvd1Jvd3MgPSBzZWFyY2hEYXRhLnBhZ2VTaXplO1xuICAgICAgICB9XG4gICAgICB9XG4gICAgfSBlbHNlIHtcbiAgICAgIHRoaXMucmVtb3ZlU2VhcmNoRGF0YUZyb21TZXNzaW9uKCk7XG4gICAgfVxuICB9XG5cbiAgcHJpdmF0ZSBmb3JtYXRTZWFyY2hQYXJhbU5hbWUoZmllbGROYW1lOiBzdHJpbmcpOiBzdHJpbmcge1xuICAgIHJldHVybiB0aGlzLmdldEZpbHRlclBhcmFtTmFtZU1hcHBpbmcoKVtmaWVsZE5hbWVdIHx8ICdwXycgKyBmaWVsZE5hbWU7XG4gIH1cblxuICBwcml2YXRlIGFkZEl0ZW1Ub1NlYXJjaFBhcmFtTWFwKG1hcDogTWFwPHN0cmluZywgdW5rbm93bj4sIGZpbHRlck5hbWU6IHN0cmluZywgdmFsdWU6IHVua25vd24pOiBNYXA8c3RyaW5nLCB1bmtub3duPiB7XG4gICAgcmV0dXJuIG1hcC5zZXQodGhpcy5mb3JtYXRTZWFyY2hQYXJhbU5hbWUoZmlsdGVyTmFtZSksIHRoaXMuZm9ybWF0VmFsdWUodmFsdWUpKTtcbiAgfVxuXG4gIC8qKlxuICAgKiBQcmlkxJd0YXMgRUxTRSwgamVpIGdhdXRpIGR1b21lbnlzIHlyYSBuZSBFeHRlbmRlZFNlYXJjaFBhcmFtIHRpcG8sXG4gICAqIEVMU0Ugc8SFbHlnb2plIHN1a3VyaWFtYXMgRXh0ZW5kZWRTZWFyY2hQYXJhbSBvYmpla3RhcyBpciBwcmlkZWRhbWFzIMSvXG4gICAqIHBhcmFtc0FycmF5IG1hc3l2xIUuXG4gICAqIFByaWTEl3RhcyBrZXkgcGFyYW1ldHJhIGdhdXRpIGNvbnRyb2xzYG8gcGF2YWRpbmltxIUuXG4gICAqL1xuICBwcml2YXRlIGFkZEl0ZW1Ub0V4dGVuZGVkU2VhcmNoUGFyYW1zKFxuICAgIHBhcmFtc0FycmF5OiBFeHRlbmRlZFNlYXJjaFBhcmFtW10sXG4gICAgdmFsdWU6IEV4dGVuZGVkU2VhcmNoUGFyYW0sXG4gICAga2V5Pzogc3RyaW5nXG4gICk6IHZvaWQge1xuICAgIGlmICh2YWx1ZSBpbnN0YW5jZW9mIE9iamVjdCAmJiB2YWx1ZS5wYXJhbU5hbWUgJiYgdmFsdWUucGFyYW1WYWx1ZSkge1xuICAgICAgcGFyYW1zQXJyYXkucHVzaCh2YWx1ZSk7XG4gICAgfSBlbHNlIHtcbiAgICAgIHBhcmFtc0FycmF5LnB1c2goe1xuICAgICAgICBwYXJhbU5hbWU6IGtleSxcbiAgICAgICAgcGFyYW1WYWx1ZToge1xuICAgICAgICAgIGNvbmRpdGlvbjogRXh0ZW5kZWRTZWFyY2hDb25kaXRpb24uRXF1YWxzLFxuICAgICAgICAgIHZhbHVlOiB0aGlzLnNlYXJjaEZvcm0uY29udHJvbHNba2V5XT8udmFsdWUgYXMgc3RyaW5nLFxuICAgICAgICAgIHVwcGVyTG93ZXI6IEV4dGVuZGVkU2VhcmNoVXBwZXJMb3dlci5SZWd1bGFyLFxuICAgICAgICB9LFxuICAgICAgfSk7XG4gICAgfVxuICB9XG5cbiAgLyoqXG4gICAqIEJ1dm8gcHJpZMSXdGFzIHRpa3JpbmltYXMgYXIgdGhpcy5zZWFyY2hGb3JtLmNvbnRyb2xzIHR1cmkgZHVvbWVuaXMsXG4gICAqIGplaSB0aGlzLnNlYXJjaEZvcm0uY29udHJvbHMgdHVyaSBkdW9tZW5pcywgdGFpcCBwYXQgdGlrcmluYSBhciBnYXV0aSBkdW9tZW55c1xuICAgKiBuxJdyYSBFeHRlbmRlZFNlYXJjaFBhcmFtIHRpcG8sIGplaSBzxIVseWdhIHBhc2l0dmlydGluYSBkdW9tZW55cyBwcmlkZWRhbWkgxK8gUEFSQU1TIG1hc3l2xIUuXG4gICAqL1xuICBwcm90ZWN0ZWQgZ2V0U2VhcmNoUGFyYW1NYXAoZXZlbnQ6IFRhYmxlTGF6eUxvYWRFdmVudCB8IFRhYmxlKTogTWFwPHN0cmluZywgdW5rbm93bj4ge1xuICAgIGNvbnN0IHJlc3VsdCA9IG5ldyBNYXA8c3RyaW5nLCB1bmtub3duPigpO1xuXG4gICAgaWYgKGV2ZW50ICYmIGV2ZW50LmZpbHRlcnMpIHtcbiAgICAgIGNvbnN0IGZpbHRlckZpZWxkcyA9IE9iamVjdC5nZXRPd25Qcm9wZXJ0eU5hbWVzKGV2ZW50LmZpbHRlcnMpO1xuICAgIH1cblxuICAgIGlmICh0aGlzLnNlYXJjaEZvcm0pIHtcbiAgICAgIE9iamVjdC5rZXlzKHRoaXMuc2VhcmNoRm9ybS5jb250cm9scykuZm9yRWFjaCgoa2V5KSA9PiB7XG4gICAgICAgIGlmIChcbiAgICAgICAgICAhdGhpcy5zZWFyY2hGb3JtLmNvbnRyb2xzW2tleV0udmFsdWUgPT09IGZhbHNlICYmXG4gICAgICAgICAgISEodGhpcy5zZWFyY2hGb3JtLmNvbnRyb2xzW2tleV0udmFsdWUgYXMgRXh0ZW5kZWRTZWFyY2hQYXJhbSkucGFyYW1WYWx1ZSA9PT0gZmFsc2VcbiAgICAgICAgKSB7XG4gICAgICAgICAgdGhpcy5hZGRJdGVtVG9TZWFyY2hQYXJhbU1hcChyZXN1bHQsIGtleSwgYCR7dGhpcy5zZWFyY2hGb3JtLmNvbnRyb2xzW2tleV0udmFsdWUgYXMgc3RyaW5nfWApO1xuICAgICAgICB9XG4gICAgICB9KTtcbiAgICB9XG4gICAgcmV0dXJuIHJlc3VsdDtcbiAgfVxuXG4gIHByb3RlY3RlZCBnZXRFeHRlbmRlZFNlYXJjaFBhcmFtcyhldmVudDogVGFibGVMYXp5TG9hZEV2ZW50IHwgVGFibGUpOiBFeHRlbmRlZFNlYXJjaFBhcmFtW10ge1xuICAgIGNvbnN0IHJlc3VsdDogRXh0ZW5kZWRTZWFyY2hQYXJhbVtdID0gW107XG4gICAgaWYgKGV2ZW50ICYmIGV2ZW50LmZpbHRlcnMpIHtcbiAgICAgIGNvbnN0IGZpbHRlckZpZWxkcyA9IE9iamVjdC5nZXRPd25Qcm9wZXJ0eU5hbWVzKGV2ZW50LmZpbHRlcnMpO1xuXG4gICAgICBmaWx0ZXJGaWVsZHMuZm9yRWFjaCgoZmlsdGVyTmFtZSkgPT4ge1xuICAgICAgICBpZiAoQXJyYXkuaXNBcnJheShldmVudC5maWx0ZXJzW2ZpbHRlck5hbWVdKSkge1xuICAgICAgICAgIGNvbnN0IGZpbHRlcnMgPSBldmVudC5maWx0ZXJzW2ZpbHRlck5hbWVdIGFzIEZpbHRlck1ldGFkYXRhW107XG4gICAgICAgICAgZmlsdGVycy5mb3JFYWNoKChmaWx0ZXIpID0+IHtcbiAgICAgICAgICAgIGlmIChmaWx0ZXIudmFsdWUpIHtcbiAgICAgICAgICAgICAgdGhpcy5hZGRJdGVtVG9FeHRlbmRlZFNlYXJjaFBhcmFtcyhyZXN1bHQsIGZpbHRlci52YWx1ZSBhcyBFeHRlbmRlZFNlYXJjaFBhcmFtKTtcbiAgICAgICAgICAgIH1cbiAgICAgICAgICB9KTtcbiAgICAgICAgfSBlbHNlIHtcbiAgICAgICAgICBjb25zdCBmaWx0ZXIgPSBldmVudC5maWx0ZXJzW2ZpbHRlck5hbWVdIGFzIEZpbHRlck1ldGFkYXRhO1xuXG4gICAgICAgICAgaWYgKGZpbHRlci52YWx1ZSkge1xuICAgICAgICAgICAgdGhpcy5hZGRJdGVtVG9FeHRlbmRlZFNlYXJjaFBhcmFtcyhyZXN1bHQsIGZpbHRlci52YWx1ZSBhcyBFeHRlbmRlZFNlYXJjaFBhcmFtKTtcbiAgICAgICAgICB9XG4gICAgICAgIH1cbiAgICAgIH0pO1xuICAgIH1cbiAgICBpZiAodGhpcy5zZWFyY2hGb3JtKSB7XG4gICAgICBPYmplY3Qua2V5cyh0aGlzLnNlYXJjaEZvcm0uY29udHJvbHMpLmZvckVhY2goKGtleSkgPT4ge1xuICAgICAgICBpZiAodGhpcy5zZWFyY2hGb3JtLmNvbnRyb2xzW2tleV0/LnZhbHVlKSB7XG4gICAgICAgICAgdGhpcy5hZGRJdGVtVG9FeHRlbmRlZFNlYXJjaFBhcmFtcyhyZXN1bHQsIHRoaXMuc2VhcmNoRm9ybS5jb250cm9sc1trZXldPy52YWx1ZSBhcyBFeHRlbmRlZFNlYXJjaFBhcmFtLCBrZXkpO1xuICAgICAgICB9XG4gICAgICB9KTtcbiAgICB9XG4gICAgcmV0dXJuIHJlc3VsdDtcbiAgfVxuXG4gIGluc3RhbmNlT2ZJZFZhbHVlc090KG9iamVjdDogdW5rbm93bik6IG9iamVjdCBpcyBTcHJfaWRfdmFsdWVzX290IHtcbiAgICByZXR1cm4gKFxuICAgICAgb2JqZWN0IGluc3RhbmNlb2YgT2JqZWN0ICYmXG4gICAgICBPYmplY3QucHJvdG90eXBlLmhhc093blByb3BlcnR5LmNhbGwob2JqZWN0LCAnaWQnKSAmJlxuICAgICAgT2JqZWN0LnByb3RvdHlwZS5oYXNPd25Qcm9wZXJ0eS5jYWxsKG9iamVjdCwgJ2tleV92YWx1ZScpXG4gICAgKTtcbiAgfVxuXG4gIGluc3RhbmNlT2ZLZXlWYWx1ZXNPdChvYmplY3Q6IHVua25vd24pOiBvYmplY3QgaXMgU3ByX2tleV92YWx1ZXNfb3Qge1xuICAgIHJldHVybiAoXG4gICAgICBvYmplY3QgaW5zdGFuY2VvZiBPYmplY3QgJiZcbiAgICAgIE9iamVjdC5wcm90b3R5cGUuaGFzT3duUHJvcGVydHkuY2FsbChvYmplY3QsICdjb2RlJykgJiZcbiAgICAgIE9iamVjdC5wcm90b3R5cGUuaGFzT3duUHJvcGVydHkuY2FsbChvYmplY3QsICdrZXlfdmFsdWUnKVxuICAgICk7XG4gIH1cblxuICBwcm90ZWN0ZWQgZm9ybWF0VmFsdWUodmFsdWU6IHVua25vd24pOiB1bmtub3duIHtcbiAgICBpZiAodmFsdWUgaW5zdGFuY2VvZiBEYXRlKSB7XG4gICAgICByZXR1cm4gdmFsdWUudG9JU09TdHJpbmcoKTtcbiAgICB9IGVsc2UgaWYgKHRoaXMuaW5zdGFuY2VPZklkVmFsdWVzT3QodmFsdWUpKSB7XG4gICAgICByZXR1cm4gdmFsdWUuaWQ7XG4gICAgfSBlbHNlIGlmICh0aGlzLmluc3RhbmNlT2ZLZXlWYWx1ZXNPdCh2YWx1ZSkpIHtcbiAgICAgIHJldHVybiB2YWx1ZS5jb2RlO1xuICAgIH0gZWxzZSBpZiAodmFsdWUgaW5zdGFuY2VvZiBBcnJheSkge1xuICAgICAgcmV0dXJuIHZhbHVlLm1hcCgodmFsKSA9PiB0aGlzLmZvcm1hdFZhbHVlKHZhbCkpO1xuICAgIH0gZWxzZSBpZiAodmFsdWUgPT09IG51bGwgfHwgT2JqZWN0LmtleXModmFsdWUpLmxlbmd0aCA9PT0gMCkge1xuICAgICAgcmV0dXJuIHVuZGVmaW5lZDtcbiAgICB9XG4gICAgcmV0dXJuIHZhbHVlO1xuICB9XG5cbiAgcHJvdGVjdGVkIGdldFNvcnRPcmRlcihzb3J0RmllbGQ6IHN0cmluZywgb3JkZXI6IG51bWJlciwgZGVmYXVsdFNvcnQ6IHN0cmluZyk6IHN0cmluZyB7XG4gICAgcmV0dXJuIEJhc2VCcm93c2VGb3JtLmdldFNvcnRPcmRlcihzb3J0RmllbGQsIG9yZGVyLCBkZWZhdWx0U29ydCk7XG4gIH1cblxuICBwcm90ZWN0ZWQgZ2V0UGFnaW5nUGFyYW1zKFxuICAgIGZpcnN0OiBudW1iZXIsXG4gICAgcGFnZVNpemU6IG51bWJlcixcbiAgICBzb3J0RmllbGQ6IHN0cmluZyxcbiAgICBvcmRlcjogbnVtYmVyLFxuICAgIGRlZmF1bHRTb3J0OiBzdHJpbmdcbiAgKTogU3ByX3BhZ2luZ19vdCB7XG4gICAgcmV0dXJuIEJhc2VCcm93c2VGb3JtLmdldFBhZ2luZ1BhcmFtcyhmaXJzdCwgcGFnZVNpemUsIHNvcnRGaWVsZCwgb3JkZXIsIGRlZmF1bHRTb3J0KTtcbiAgfVxuXG4gIHByb3RlY3RlZCBnZXREZWZhdWx0Um93c1RvU2hvdygpOiBudW1iZXIge1xuICAgIHJldHVybiBCYXNlQnJvd3NlRm9ybS5ERUZBVUxUX1JPV1NfVE9fU0hPVztcbiAgfVxuXG4gIHByb3RlY3RlZCBhYnN0cmFjdCBkZWxldGVSZWNvcmQocmVjb3JkSWQ6IHN0cmluZyk6IHZvaWQ7XG5cbiAgcHJvdGVjdGVkIGRlbGV0ZVJlY29yZFdpdGhDb25maXJtYXRpb24oXG4gICAgcmVjb3JkSWQ6IHN0cmluZyxcbiAgICBtZXNzYWdlOiBzdHJpbmcgPSAnY29tbW9uLm1lc3NhZ2UuZGVsZXRlQ29uZmlybWF0aW9uTXNnJyxcbiAgICB0cmFuc2xhdGVNZXNzYWdlOiBib29sZWFuID0gdHJ1ZVxuICApOiB2b2lkIHtcbiAgICBjb25zdCBzaG93Q29uZmlybWF0aW9uTWVzc2FnZSA9IChjb25maXJtYXRpb25NZXNzYWdlOiBzdHJpbmcpOiB2b2lkID0+IHtcbiAgICAgIHRoaXMuY29tbW9uRm9ybVNlcnZpY2VzLmNvbmZpcm1hdGlvblNlcnZpY2UuY29uZmlybSh7XG4gICAgICAgIG1lc3NhZ2U6IGNvbmZpcm1hdGlvbk1lc3NhZ2UsXG4gICAgICAgIGFjY2VwdDogKCkgPT4ge1xuICAgICAgICAgIHRoaXMuZGVsZXRlUmVjb3JkKHJlY29yZElkKTtcbiAgICAgICAgfSxcbiAgICAgIH0pO1xuICAgIH07XG4gICAgaWYgKHRyYW5zbGF0ZU1lc3NhZ2UpIHtcbiAgICAgIHRoaXMuY29tbW9uRm9ybVNlcnZpY2VzLnRyYW5zbGF0ZS5nZXQobWVzc2FnZSkuc3Vic2NyaWJlKCh0cmFuc2xhdGlvbjogc3RyaW5nKSA9PiB7XG4gICAgICAgIHNob3dDb25maXJtYXRpb25NZXNzYWdlKHRyYW5zbGF0aW9uKTtcbiAgICAgIH0pO1xuICAgIH0gZWxzZSB7XG4gICAgICBzaG93Q29uZmlybWF0aW9uTWVzc2FnZShtZXNzYWdlKTtcbiAgICB9XG4gIH1cblxuICBwcm90ZWN0ZWQgc2F2ZVNlYXJjaERhdGFUb1Nlc3Npb24oXG4gICAgZmlyc3Q6IG51bWJlcixcbiAgICBwYWdlU2l6ZTogbnVtYmVyLFxuICAgIHNvcnRGaWVsZDogc3RyaW5nLFxuICAgIG9yZGVyOiBudW1iZXIsXG4gICAgcGFyYW1zOiBNYXA8c3RyaW5nLCB1bmtub3duPixcbiAgICBleHRlbmRlZFBhcmFtcz86IEV4dGVuZGVkU2VhcmNoUGFyYW1bXVxuICApOiB2b2lkIHtcbiAgICBjb25zdCBicm93c2VTZWFyY2hEYXRhOiBCcm93c2VTZWFyY2hEYXRhID0ge1xuICAgICAgdXJsOiB0aGlzLmNvbW1vbkZvcm1TZXJ2aWNlcy5yb3V0ZXIudXJsLFxuICAgICAgcGFyYW1zT2JqOiBPYmplY3QuZnJvbUVudHJpZXMocGFyYW1zKSxcbiAgICAgIGV4dGVuZGVkUGFyYW1zLFxuICAgICAgc2VhcmNoRm9ybVZhbHVlczogT2JqZWN0LmtleXModGhpcy5zZWFyY2hGb3JtLmNvbnRyb2xzKVxuICAgICAgICAuZmlsdGVyKChjb250cm9sTmFtZSkgPT4gdGhpcy5zZWFyY2hGb3JtLmNvbnRyb2xzW2NvbnRyb2xOYW1lXT8udmFsdWUpXG4gICAgICAgIC5yZWR1Y2UoXG4gICAgICAgICAgKHZhbHVlc09iaiwgY29udHJvbE5hbWUpID0+ICh7XG4gICAgICAgICAgICAuLi52YWx1ZXNPYmosXG4gICAgICAgICAgICBbY29udHJvbE5hbWVdOiB0aGlzLnNlYXJjaEZvcm0uY29udHJvbHNbY29udHJvbE5hbWVdLnZhbHVlIGFzIHVua25vd24sXG4gICAgICAgICAgfSksXG4gICAgICAgICAge30gYXMgUmVjb3JkPHN0cmluZywgdW5rbm93bj5cbiAgICAgICAgKSxcbiAgICAgIGZpcnN0LFxuICAgICAgcGFnZVNpemUsXG4gICAgICBzb3J0RmllbGQsXG4gICAgICBvcmRlcixcbiAgICB9O1xuICAgIHNlc3Npb25TdG9yYWdlLnNldEl0ZW0oQlJPV1NFX1NFQVJDSF9EQVRBX0tFWSwgSlNPTi5zdHJpbmdpZnkoYnJvd3NlU2VhcmNoRGF0YSkpO1xuICB9XG5cbiAgcHJvdGVjdGVkIGdldFNlYXJjaERhdGFGcm9tU2Vzc2lvbigpOiBCcm93c2VTZWFyY2hEYXRhIHwgbnVsbCB7XG4gICAgY29uc3Qgc2VhcmNoRGF0YSA9IEpTT04ucGFyc2Uoc2Vzc2lvblN0b3JhZ2UuZ2V0SXRlbShCUk9XU0VfU0VBUkNIX0RBVEFfS0VZKSkgYXMgQnJvd3NlU2VhcmNoRGF0YTtcbiAgICBpZiAoc2VhcmNoRGF0YSkge1xuICAgICAgaWYgKHNlYXJjaERhdGE/LnVybCA9PT0gdGhpcy5jb21tb25Gb3JtU2VydmljZXMucm91dGVyLnVybCkge1xuICAgICAgICByZXR1cm4gc2VhcmNoRGF0YTtcbiAgICAgIH1cbiAgICB9XG4gICAgcmV0dXJuIG51bGw7XG4gIH1cblxuICBwcm90ZWN0ZWQgcmVtb3ZlU2VhcmNoRGF0YUZyb21TZXNzaW9uKCk6IHZvaWQge1xuICAgIHNlc3Npb25TdG9yYWdlLnJlbW92ZUl0ZW0oQlJPV1NFX1NFQVJDSF9EQVRBX0tFWSk7XG4gIH1cblxuICBwcm90ZWN0ZWQgbG9hZFNlYXJjaERhdGFJbnRvU2VhcmNoRm9ybSgpOiB2b2lkIHtcbiAgICBjb25zdCBzZWFyY2hEYXRhID0gdGhpcy5nZXRTZWFyY2hEYXRhRnJvbVNlc3Npb24oKTtcbiAgICBpZiAoc2VhcmNoRGF0YT8uc2VhcmNoRm9ybVZhbHVlcykge1xuICAgICAgT2JqZWN0LmtleXMoc2VhcmNoRGF0YS5zZWFyY2hGb3JtVmFsdWVzKS5mb3JFYWNoKChjb250cm9sTmFtZSkgPT4ge1xuICAgICAgICBpZiAodGhpcy5zZWFyY2hGb3JtLmNvbnRyb2xzW2NvbnRyb2xOYW1lXSkge1xuICAgICAgICAgIHRoaXMuc2VhcmNoRm9ybS5jb250cm9sc1tjb250cm9sTmFtZV0uc2V0VmFsdWUoc2VhcmNoRGF0YS5zZWFyY2hGb3JtVmFsdWVzW2NvbnRyb2xOYW1lXSk7XG4gICAgICAgIH1cbiAgICAgIH0pO1xuICAgIH1cbiAgfVxufVxuIl19