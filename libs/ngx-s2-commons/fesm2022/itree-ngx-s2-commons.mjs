import * as i4$1 from '@angular/common';
import { CommonModule } from '@angular/common';
import * as i1$1 from '@angular/common/http';
import { HttpClientModule, HttpErrorResponse, HTTP_INTERCEPTORS, HttpResponse } from '@angular/common/http';
import * as i0 from '@angular/core';
import { NgModule, Injectable, Component, ViewChild, isDevMode, ErrorHandler } from '@angular/core';
import * as i3 from '@ngx-translate/core';
import { TranslateModule } from '@ngx-translate/core';
import { Subject, BehaviorSubject, takeUntil, auditTime, Observable, of } from 'rxjs';
import * as i1$2 from '@angular/forms';
import { FormGroup, AbstractControl } from '@angular/forms';
import { Table } from 'primeng/table';
import * as i1 from '@angular/router';
import { ActivationEnd, NavigationEnd } from '@angular/router';
import * as i4 from 'primeng/api';
import { v4 } from 'uuid';
import { tap } from 'rxjs/operators';

class NgxS2CommonsModule {
    static ɵfac = i0.ɵɵngDeclareFactory({ minVersion: "12.0.0", version: "16.0.2", ngImport: i0, type: NgxS2CommonsModule, deps: [], target: i0.ɵɵFactoryTarget.NgModule });
    static ɵmod = i0.ɵɵngDeclareNgModule({ minVersion: "14.0.0", version: "16.0.2", ngImport: i0, type: NgxS2CommonsModule, imports: [CommonModule, HttpClientModule, TranslateModule] });
    static ɵinj = i0.ɵɵngDeclareInjector({ minVersion: "12.0.0", version: "16.0.2", ngImport: i0, type: NgxS2CommonsModule, imports: [CommonModule, HttpClientModule, TranslateModule] });
}
i0.ɵɵngDeclareClassMetadata({ minVersion: "12.0.0", version: "16.0.2", ngImport: i0, type: NgxS2CommonsModule, decorators: [{
            type: NgModule,
            args: [{
                    declarations: [],
                    imports: [CommonModule, HttpClientModule, TranslateModule],
                    exports: [],
                }]
        }] });

class AppMessages {
    ngZone;
    static SEVERITY_ERROR = 'error';
    static SEVERITY_WARN = 'warn';
    static SEVERITY_SUCCESS = 'success';
    static SEVERITY_INFO = 'info';
    add$ = new Subject();
    clear$ = new Subject();
    messages = [];
    constructor(ngZone) {
        this.ngZone = ngZone;
    }
    showMessage(message) {
        if (message.id === 'VALIDATION' ||
            message.severity === AppMessages.SEVERITY_ERROR ||
            message.severity === AppMessages.SEVERITY_WARN) {
            message.sticky = true;
        }
        this.ngZone.run(() => {
            if (this.messages.some((messageFromArray) => messageFromArray.severity === message.severity && messageFromArray.detail === message.detail)) {
                this.clearMessages();
                this.messages.push(message);
                this.add$.next(message);
            }
            else if (message.data?.allowMultiple ||
                !this.messages.some((messageFromArray) => messageFromArray.severity === message.severity && messageFromArray.detail === message.detail)) {
                this.messages.push(message);
                this.add$.next(message);
            }
        });
    }
    clearMessages() {
        this.clear$.next();
        this.messages = [];
    }
    clearMessagesExceptSuccess() {
        if (this.messages.some((message) => message.severity !== AppMessages.SEVERITY_SUCCESS)) {
            this.clear$.next();
            this.messages = [];
        }
    }
    removeMessage(message) {
        const messageIndex = this.messages.findIndex((messageFromArray) => messageFromArray.severity === message.severity && messageFromArray.detail === message.detail);
        if (messageIndex !== -1) {
            this.messages.splice(messageIndex, 1);
        }
    }
    showError(summary, detail, id) {
        this.showMessage({ severity: AppMessages.SEVERITY_ERROR, detail, id });
    }
    showWarning(summary, detail, id) {
        this.showMessage({ severity: AppMessages.SEVERITY_WARN, detail, id });
    }
    showSuccess(summary, detail, id) {
        this.showMessage({ severity: AppMessages.SEVERITY_SUCCESS, detail, id });
    }
    showInfo(summary, detail, id) {
        this.showMessage({ severity: AppMessages.SEVERITY_INFO, detail, id });
    }
    static ɵfac = i0.ɵɵngDeclareFactory({ minVersion: "12.0.0", version: "16.0.2", ngImport: i0, type: AppMessages, deps: [{ token: i0.NgZone }], target: i0.ɵɵFactoryTarget.Injectable });
    static ɵprov = i0.ɵɵngDeclareInjectable({ minVersion: "12.0.0", version: "16.0.2", ngImport: i0, type: AppMessages, providedIn: 'root' });
}
i0.ɵɵngDeclareClassMetadata({ minVersion: "12.0.0", version: "16.0.2", ngImport: i0, type: AppMessages, decorators: [{
            type: Injectable,
            args: [{
                    providedIn: 'root',
                }]
        }], ctorParameters: function () { return [{ type: i0.NgZone }]; } });

class LoaderService {
    appMessages;
    showCount = 0;
    timeOutInterval;
    showLoader = new BehaviorSubject({ show: false });
    constructor(appMessages) {
        this.appMessages = appMessages;
    }
    show(message, dimmed) {
        if (this.showCount === 0) {
            this.showLoader.next({ show: true, message, dimmed });
        }
        this.showCount++;
    }
    hide() {
        if (this.showCount > 0) {
            this.showCount--;
        }
        if (this.showCount === 0) {
            this.showLoader.next({ show: false });
            if (this.timeOutInterval !== undefined) {
                clearTimeout(this.timeOutInterval);
                this.timeOutInterval = undefined;
            }
        }
    }
    showWithOptions({ message, dimmed, timeout, timeoutMessage, }) {
        this.show(message, dimmed);
        if (timeout) {
            this.timeOutInterval = setTimeout(() => {
                this.onTimeout(timeoutMessage);
            }, timeout);
        }
    }
    onTimeout(timeoutMessage) {
        this.hide();
        if (timeoutMessage) {
            this.appMessages.showError('', timeoutMessage);
        }
    }
    static ɵfac = i0.ɵɵngDeclareFactory({ minVersion: "12.0.0", version: "16.0.2", ngImport: i0, type: LoaderService, deps: [{ token: AppMessages }], target: i0.ɵɵFactoryTarget.Injectable });
    static ɵprov = i0.ɵɵngDeclareInjectable({ minVersion: "12.0.0", version: "16.0.2", ngImport: i0, type: LoaderService, providedIn: 'root' });
}
i0.ɵɵngDeclareClassMetadata({ minVersion: "12.0.0", version: "16.0.2", ngImport: i0, type: LoaderService, decorators: [{
            type: Injectable,
            args: [{ providedIn: 'root' }]
        }], ctorParameters: function () { return [{ type: AppMessages }]; } });

const DEFAULT_LANGUAGE = 'lt';
const LOCAL_STORAGE_LANG_KEY = 'lang';
/**
 * Gets language from localstorage
 */
function getLang() {
    return localStorage.getItem(LOCAL_STORAGE_LANG_KEY);
}
/**
 * Sets language, updates localstorage ant ngx-translate language
 */
function setLang(translate, lang) {
    localStorage.setItem(LOCAL_STORAGE_LANG_KEY, lang);
    translate.use(lang);
}
/**
 * Used to init translations on app startup
 */
function initLang(translate) {
    const userLang = localStorage.getItem(LOCAL_STORAGE_LANG_KEY);
    // ------------ Isjungta nes nekorektiskai veikia kalbos setinimas is url parametru --------------
    // translate.setDefaultLang(DEFAULT_LANGUAGE);
    if (userLang != null) {
        translate.use(userLang);
    }
    else {
        setLang(translate, DEFAULT_LANGUAGE);
    }
}

class AppMissingTranslationHandler {
    handle(params) {
        return `#!!!${params.key}!!!`;
    }
}

/* eslint-disable @typescript-eslint/naming-convention, no-underscore-dangle, id-blacklist, id-match */
// eslint-disable-next-line @typescript-eslint/naming-convention
class Spr_paging_ot {
    cnt;
    skip_rows;
    page_size;
    order_clause;
    sum_values;
    constructor(cnt, skip_rows, page_size, order_clause) {
        this.cnt = cnt;
        this.skip_rows = skip_rows;
        this.page_size = page_size;
        this.order_clause = order_clause;
    }
}

var ExtendedSearchCondition;
(function (ExtendedSearchCondition) {
    ExtendedSearchCondition["More"] = ">";
    ExtendedSearchCondition["Less"] = "<";
    ExtendedSearchCondition["Equals"] = "=";
    ExtendedSearchCondition["NotEqual"] = "!=";
    ExtendedSearchCondition["StartsWith"] = "%-";
    ExtendedSearchCondition["EndsWith"] = "-%";
    ExtendedSearchCondition["Contains"] = "%-%";
    ExtendedSearchCondition["Empty"] = "null";
    ExtendedSearchCondition["NotEmpty"] = "notnull";
    ExtendedSearchCondition["Period"] = "[]";
})(ExtendedSearchCondition || (ExtendedSearchCondition = {}));
var ExtendedSearchUpperLower;
(function (ExtendedSearchUpperLower) {
    ExtendedSearchUpperLower["Regular"] = "regular";
    ExtendedSearchUpperLower["Lowercase"] = "lowercase";
    ExtendedSearchUpperLower["Uppercase"] = "uppercase";
    ExtendedSearchUpperLower["CaseInsensitiveLatin"] = "caseInsensitiveLatin";
    ExtendedSearchUpperLower["RegularLatin"] = "regularLatin";
})(ExtendedSearchUpperLower || (ExtendedSearchUpperLower = {}));

class CommonFormServices {
    route;
    router;
    appMessages;
    translate;
    confirmationService;
    calendarLt = {
        closeText: 'Done',
        prevText: 'Prev',
        nextText: 'Next',
        currentText: 'Today',
        today: 'Šiandien',
        clear: 'Išvalyti',
        monthNames: [
            'sausis',
            'vasaris',
            'kovas',
            'balandis',
            'gegužė',
            'birželis',
            'liepa',
            'rugpjūtis',
            'rugsėjis',
            'spalis',
            'lapkritis',
            'gruodis',
        ],
        monthNamesShort: ['Jan', 'Feb', 'Mar', 'Apr', 'May', 'Jun', 'Jul', 'Aug', 'Sep', 'Oct', 'Nov', 'Dec'],
        dayNames: ['Sunday', 'Monday', 'Tuesday', 'Wednesday', 'Thursday', 'Friday', 'Saturday'],
        dayNamesShort: ['Sun', 'Mon', 'Tue', 'Wed', 'Thu', 'Fri', 'Sat'],
        dayNamesMin: ['Su', 'Mo', 'Tu', 'We', 'Th', 'Fr', 'Sa'],
        weekHeader: 'Wk',
        dateFormat: 'dd/mm/yy',
        firstDay: 1,
        isRTL: false,
        showMonthAfterYear: false,
        yearSuffix: '',
    };
    constructor(route, router, appMessages, translate, confirmationService) {
        this.route = route;
        this.router = router;
        this.appMessages = appMessages;
        this.translate = translate;
        this.confirmationService = confirmationService;
    }
    static ɵfac = i0.ɵɵngDeclareFactory({ minVersion: "12.0.0", version: "16.0.2", ngImport: i0, type: CommonFormServices, deps: [{ token: i1.ActivatedRoute }, { token: i1.Router }, { token: AppMessages }, { token: i3.TranslateService }, { token: i4.ConfirmationService }], target: i0.ɵɵFactoryTarget.Injectable });
    static ɵprov = i0.ɵɵngDeclareInjectable({ minVersion: "12.0.0", version: "16.0.2", ngImport: i0, type: CommonFormServices, providedIn: 'root' });
}
i0.ɵɵngDeclareClassMetadata({ minVersion: "12.0.0", version: "16.0.2", ngImport: i0, type: CommonFormServices, decorators: [{
            type: Injectable,
            args: [{
                    providedIn: 'root',
                }]
        }], ctorParameters: function () { return [{ type: i1.ActivatedRoute }, { type: i1.Router }, { type: AppMessages }, { type: i3.TranslateService }, { type: i4.ConfirmationService }]; } });

const BROWSE_SEARCH_DATA_KEY = 'browseSearchData';
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
    static ɵfac = i0.ɵɵngDeclareFactory({ minVersion: "12.0.0", version: "16.0.2", ngImport: i0, type: BaseBrowseForm, deps: [{ token: CommonFormServices }], target: i0.ɵɵFactoryTarget.Component });
    static ɵcmp = i0.ɵɵngDeclareComponent({ minVersion: "14.0.0", version: "16.0.2", type: BaseBrowseForm, selector: "ng-component", viewQueries: [{ propertyName: "dataTableComponent", first: true, predicate: Table, descendants: true, static: true }], ngImport: i0, template: '', isInline: true });
}
i0.ɵɵngDeclareClassMetadata({ minVersion: "12.0.0", version: "16.0.2", ngImport: i0, type: BaseBrowseForm, decorators: [{
            type: Component,
            args: [{ template: '' }]
        }], ctorParameters: function () { return [{ type: CommonFormServices }]; }, propDecorators: { dataTableComponent: [{
                type: ViewChild,
                args: [Table, { static: true }]
            }] } });

const SPEC_CHARS = '@./$!%*?&#_-';
const PWD_PATTERN = '(?=^.{12,}$)(?=.*\\d)(?=.*[a-z])(?=.*[A-Z])(?=.*[-@.$/!%*?&#_.])(?!.*\\s).*$';
function escapeRegExp(str) {
    return str.replace(/[-[\]/{}()*+?.\\^$|]/g, '\\$&');
}
function toKeyValues(codes) {
    if (Array.isArray(codes)) {
        return codes.map((code) => ({ code }));
    }
    return [];
}
function fromKeyValues(keyValues) {
    if (Array.isArray(keyValues)) {
        return keyValues.map((keyValue) => keyValue.code);
    }
    return [];
}
function convertKeyValues(keyValues) {
    if (Array.isArray(keyValues)) {
        return keyValues.map((keyValue) => ({ value: keyValue.code, label: keyValue.key_value }));
    }
    return [];
}
function keyValuesToMap(keyValues) {
    const result = new Map();
    keyValues.forEach((keyValue) => {
        result.set(keyValue.code, keyValue.key_value);
    });
    return result;
}
const Y = 'Y';
const N = 'N';
function toBoolean(value) {
    return value === Y;
}
function fromBoolean(value) {
    return value ? Y : N;
}
function toRadioBtnIdVal(values) {
    let result = null;
    if (values) {
        const value = values.find((p) => p.key_value === Y);
        if (value) {
            result = value.id;
        }
    }
    return result;
}
function fromRadioBtnIdVal(array, id) {
    if (array) {
        array.forEach((p) => {
            p.key_value = p.id === id ? Y : N;
        });
    }
    return array;
}
function toRadioBtnKeyVal(values) {
    let result = '';
    if (values) {
        const value = values.find((p) => p.key_value === Y);
        if (value) {
            result = value.code;
        }
    }
    return result;
}
function fromRadioBtnKeyVal(array, code) {
    if (array) {
        array.forEach((p) => {
            p.key_value = p.code === code ? Y : N;
        });
    }
    return array;
}
function appendPagingParams(url, pagingParams) {
    if (!pagingParams) {
        return url;
    }
    if (!pagingParams.page_size) {
        pagingParams.page_size = BaseBrowseForm.DEFAULT_ROWS_TO_SHOW;
    }
    if (url.substring(url.length - 1) !== '?') {
        url = url + '&';
    }
    url = `${url}page_size=${pagingParams.page_size}&skip_rows=${pagingParams.skip_rows}`;
    if (pagingParams.order_clause) {
        url = url + '&order_clause=' + pagingParams.order_clause;
    }
    return url;
}
function appendSearchParams(url, params) {
    if (params) {
        params.forEach((value, key) => {
            if (value && value !== '') {
                url = `${url}&${key}=${value}`;
            }
        });
    }
    return url;
}
const deepEquals = (x, y) => {
    if (x === y) {
        return true; // if both x and y are null or undefined and exactly the same
    }
    else if (!(x instanceof Object) || !(y instanceof Object)) {
        return false; // if they are not strictly equal, they both need to be Objects
    }
    else if (x.constructor !== y.constructor) {
        // they must have the exact same prototype chain, the closest we can do is
        // test their constructor.
        return false;
    }
    else {
        for (const p in x) {
            if (!Object.prototype.hasOwnProperty.call(x, p)) {
                continue; // other properties were tested using x.constructor === y.constructor
            }
            if (!Object.prototype.hasOwnProperty.call(y, p)) {
                return false; // allows to compare x[ p ] and y[ p ] when set to undefined
            }
            if (x[p] === y[p]) {
                continue; // if they have the same strict value or identity then they are equal
            }
            if (typeof x[p] !== 'object') {
                return false; // Numbers, Strings, Functions, Booleans must be strictly equal
            }
            if (!deepEquals(x[p], y[p])) {
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

const MULTI_TAB_KEY = 'multitab';
const SESSION_KEY = 'session';
const TERMS_KEY = 'terms';
const SES_VALUE_TRUE = 'T';
const SES_VALUE_FALSE = 'F';
class AuthUtil {
    static copyFromSessionStorageToLocalStorage(keys) {
        keys.forEach((key) => {
            const value = sessionStorage.getItem(key);
            if (value !== null) {
                localStorage.setItem(key, value);
            }
        });
    }
    static setMultiTabSession(multiTabSession) {
        if (multiTabSession) {
            if (localStorage.getItem(MULTI_TAB_KEY) !== SES_VALUE_TRUE) {
                localStorage.setItem(MULTI_TAB_KEY, SES_VALUE_TRUE);
                this.copyFromSessionStorageToLocalStorage([SESSION_KEY, TERMS_KEY]);
            }
        }
        else {
            localStorage.setItem(MULTI_TAB_KEY, SES_VALUE_FALSE);
            localStorage.removeItem(SESSION_KEY);
            localStorage.removeItem(TERMS_KEY);
        }
    }
    static isMultiTabSession() {
        return localStorage.getItem(MULTI_TAB_KEY) === SES_VALUE_TRUE;
    }
    static clearSessionStorage() {
        if (AuthUtil.isMultiTabSession()) {
            localStorage.removeItem(SESSION_KEY);
            localStorage.removeItem(TERMS_KEY);
        }
        sessionStorage.clear();
    }
    static getJWTFromSession() {
        const loginResult = AuthUtil.getLoginResult();
        return loginResult ? loginResult.token : null;
    }
    static getSessionInfo() {
        const loginResult = AuthUtil.getLoginResult();
        return loginResult ? loginResult.session : null;
    }
    static updateSessionInfo(session) {
        const loginResult = AuthUtil.getLoginResult();
        loginResult.session = session;
        if (AuthUtil.isMultiTabSession()) {
            localStorage.setItem(SESSION_KEY, JSON.stringify(loginResult));
        }
        else {
            sessionStorage.setItem(SESSION_KEY, JSON.stringify(loginResult));
        }
    }
    static getLoginResult() {
        const sessionData = AuthUtil.isMultiTabSession()
            ? localStorage.getItem(SESSION_KEY)
            : sessionStorage.getItem(SESSION_KEY);
        return sessionData ? JSON.parse(sessionData) : null;
    }
    static setLoginResult(loginResult) {
        if (AuthUtil.isMultiTabSession()) {
            localStorage.setItem(SESSION_KEY, JSON.stringify(loginResult));
        }
        else {
            sessionStorage.setItem(SESSION_KEY, JSON.stringify(loginResult));
        }
    }
    static isLoggedIn() {
        return this.getLoginResult() != null;
    }
    static isTermsAccepted() {
        const termsOfUse = JSON.parse(AuthUtil.isMultiTabSession() ? localStorage.getItem(TERMS_KEY) : sessionStorage.getItem(TERMS_KEY));
        return termsOfUse && (termsOfUse.code === 'Y' || termsOfUse.code === SES_VALUE_TRUE);
    }
    static setTermsAccepted(accept) {
        const terms = {
            code: accept ? SES_VALUE_TRUE : SES_VALUE_FALSE,
            key_value: null,
            display_text: null,
        };
        const termsString = JSON.stringify(terms);
        if (AuthUtil.isMultiTabSession()) {
            localStorage.setItem(TERMS_KEY, termsString);
        }
        else {
            sessionStorage.setItem(TERMS_KEY, termsString);
        }
    }
    static getRoleCode() {
        const loginResult = AuthUtil.getLoginResult();
        return loginResult ? loginResult.session.roleCode : null;
    }
    static getRoleId() {
        const loginResult = AuthUtil.getLoginResult();
        return loginResult ? loginResult.session.roleId : null;
    }
    static getRoleName() {
        const loginResult = AuthUtil.getLoginResult();
        return loginResult ? loginResult.session.roleName : null;
    }
    static getOrgId() {
        const loginResult = AuthUtil.getLoginResult();
        return loginResult ? loginResult.session.orgId : null;
    }
    static getOrgName() {
        const loginResult = AuthUtil.getLoginResult();
        return loginResult ? loginResult.session.orgName : null;
    }
    static getLanguage() {
        const loginResult = AuthUtil.getLoginResult();
        return loginResult ? loginResult.session.language : null;
    }
}

class AuthEvent {
    userLoggedIn;
    session;
    type;
    regStatus;
    returnUrl;
    static AUTH_EVENT_LOGIN = 'LOGIN';
    static AUTH_EVENT_LOGOUT = 'LOGOUT';
    static AUTH_EVENT_NO_AUTH = 'NO_AUTH';
    static AUTH_EVENT_401 = '401';
    static AUTH_EVENT_OTHER_USER = 'OTHER_USER';
    static AUTH_EVENT_UPDATE = 'UPDATE';
    static isUnauthError = false;
    static userLoggedIn = new Subject();
    constructor(userLoggedIn, session, type, regStatus, returnUrl) {
        this.userLoggedIn = userLoggedIn;
        this.session = session;
        this.type = type;
        this.regStatus = regStatus;
        this.returnUrl = returnUrl;
    }
}

const noop = () => undefined;
class ConsoleLoggerService {
    get debug() {
        if (isDevMode()) {
            return console.debug.bind(console);
        }
        else {
            return noop;
        }
    }
    get info() {
        return console.info.bind(console);
    }
    get warn() {
        return console.warn.bind(console);
    }
    get error() {
        return console.error.bind(console);
    }
    static ɵfac = i0.ɵɵngDeclareFactory({ minVersion: "12.0.0", version: "16.0.2", ngImport: i0, type: ConsoleLoggerService, deps: [], target: i0.ɵɵFactoryTarget.Injectable });
    static ɵprov = i0.ɵɵngDeclareInjectable({ minVersion: "12.0.0", version: "16.0.2", ngImport: i0, type: ConsoleLoggerService });
}
i0.ɵɵngDeclareClassMetadata({ minVersion: "12.0.0", version: "16.0.2", ngImport: i0, type: ConsoleLoggerService, decorators: [{
            type: Injectable
        }] });

class LoggerFactory {
    static getLogger() {
        return new ConsoleLoggerService();
    }
}

class S2Error extends Error {
    messages;
    constructor(messages) {
        super();
        this.messages = messages;
        this.name = S2Error.name;
        Object.setPrototypeOf(this, S2Error.prototype);
    }
}

class ServerError extends Error {
    uuid;
    constructor(uuid) {
        super();
        this.uuid = uuid;
        this.name = ServerError.name;
        Object.setPrototypeOf(this, ServerError.prototype);
    }
}

class UnauthorizedError extends Error {
    constructor() {
        super();
        this.name = UnauthorizedError.name;
        Object.setPrototypeOf(this, UnauthorizedError.prototype);
    }
}

class ServerUnavailableError extends Error {
    constructor() {
        super();
        this.name = ServerUnavailableError.name;
        Object.setPrototypeOf(this, ServerUnavailableError.prototype);
    }
}

class ClientLogService {
    http;
    constructor(http) {
        this.http = http;
    }
    logClientError(clientSideError) {
        return this.http.post(this.getLogServiceApiUrl(), clientSideError);
    }
    static ɵfac = i0.ɵɵngDeclareFactory({ minVersion: "12.0.0", version: "16.0.2", ngImport: i0, type: ClientLogService, deps: [{ token: i1$1.HttpClient }], target: i0.ɵɵFactoryTarget.Injectable });
    static ɵprov = i0.ɵɵngDeclareInjectable({ minVersion: "12.0.0", version: "16.0.2", ngImport: i0, type: ClientLogService });
}
i0.ɵɵngDeclareClassMetadata({ minVersion: "12.0.0", version: "16.0.2", ngImport: i0, type: ClientLogService, decorators: [{
            type: Injectable
        }], ctorParameters: function () { return [{ type: i1$1.HttpClient }]; } });

class AppErrorHandler {
    appMessages;
    translate;
    clientLogService;
    datePipe;
    log = LoggerFactory.getLogger();
    logFrequencyPerMinute = 10;
    logTimeArray = [];
    constructor(appMessages, translate, clientLogService, datePipe) {
        this.appMessages = appMessages;
        this.translate = translate;
        this.clientLogService = clientLogService;
        this.datePipe = datePipe;
    }
    handleError(error) {
        const currentDateString = this.datePipe.transform(new Date(), 'yyyy-MM-dd HH:mm:ss');
        if (error instanceof S2Error) {
            error.messages.forEach((msg) => {
                if (msg.code) {
                    this.translate
                        .get(msg.code, {
                        param1: msg.param1,
                        param2: msg.param2,
                        param3: msg.param3,
                        param4: msg.param4,
                        param5: msg.param5,
                    })
                        .subscribe((translation) => {
                        this.showMessage(translation, msg.severity);
                    });
                }
                else {
                    this.showMessage(msg.default_text, msg.severity);
                }
            });
            //Resolver do not throw UnauthorizedError instance, why we have to chack message text
        }
        else if (error instanceof UnauthorizedError || error.message.includes('UnauthorizedError')) {
            AuthEvent.userLoggedIn.next({
                userLoggedIn: false,
                session: null,
                type: AuthEvent.AUTH_EVENT_401,
            });
        }
        else if (error instanceof ServerError) {
            this.translate
                .get('common.error.unexpectedWithId', { errorID: `${error.uuid} API ${currentDateString} ` })
                .subscribe((translation) => this.appMessages.showError('', translation));
            this.log.error('Server error:' + error.uuid);
        }
        else if (error instanceof ServerUnavailableError) {
            this.translate
                .get('common.error.serverUnavailableError', { dateTimeTxt: currentDateString })
                .subscribe((translation) => this.appMessages.showError('', translation));
        }
        else {
            if (AuthUtil.isLoggedIn()) {
                const errorId = v4();
                // check if error is sending not more than 10 time per minute
                if (!this.checkLogFrequencyLimit()) {
                    this.clientLogService.logClientError(this.formatClientError(error, errorId)).subscribe(() => {
                        this.translate
                            .get('common.error.unexpectedWithId', {
                            errorID: `${errorId} ${currentDateString} `,
                        })
                            .subscribe((translation) => this.appMessages.showError('', translation));
                    });
                }
            }
            else {
                this.translate
                    .get('common.error.unexpected')
                    .subscribe((translation) => this.appMessages.showError('', `${translation}`));
            }
            this.log.error('Unhandled error has occured:\n', error);
        }
    }
    showMessage(text, severity) {
        if (severity === 'E') {
            this.appMessages.showError('', text);
        }
        if (severity === 'W') {
            this.appMessages.showWarning('', text);
        }
    }
    formatClientError(error, errorId) {
        const clientSideError = {};
        clientSideError.errorCode = errorId;
        clientSideError.clientErrorTime = this.datePipe.transform(new Date(), 'yyyy-MM-dd HH:mm:ss');
        if (error instanceof HttpErrorResponse) {
            clientSideError.errorMessage = error.message;
        }
        else {
            clientSideError.errorMessage = error.stack;
        }
        return clientSideError;
    }
    checkLogFrequencyLimit() {
        let isLogToOften = false;
        let isFrequencyCheckRequired = false;
        if (this.logTimeArray.length > this.logFrequencyPerMinute - 1) {
            this.logTimeArray.shift();
            isFrequencyCheckRequired = true;
        }
        this.logTimeArray.push(Date.now());
        if (isFrequencyCheckRequired) {
            if ((this.logTimeArray[this.logTimeArray.length - 1] - this.logTimeArray[0]) / 60000 < 1) {
                isLogToOften = true;
            }
        }
        return isLogToOften;
    }
    static ɵfac = i0.ɵɵngDeclareFactory({ minVersion: "12.0.0", version: "16.0.2", ngImport: i0, type: AppErrorHandler, deps: [{ token: AppMessages }, { token: i3.TranslateService }, { token: ClientLogService }, { token: i4$1.DatePipe }], target: i0.ɵɵFactoryTarget.Injectable });
    static ɵprov = i0.ɵɵngDeclareInjectable({ minVersion: "12.0.0", version: "16.0.2", ngImport: i0, type: AppErrorHandler });
}
i0.ɵɵngDeclareClassMetadata({ minVersion: "12.0.0", version: "16.0.2", ngImport: i0, type: AppErrorHandler, decorators: [{
            type: Injectable
        }], ctorParameters: function () { return [{ type: AppMessages }, { type: i3.TranslateService }, { type: ClientLogService }, { type: i4$1.DatePipe }]; } });
const appErrorHandler = {
    provide: ErrorHandler,
    useClass: AppErrorHandler,
};

class MessageResolver {
    static ERROR_KEY_PREFIX = 'common.error';
    /**
     * Jeigu fielde per [errorDefs] nurodyti nestandartiniai validacijos pranešimai, tai pirmiausia naudojame juos:
     */
    static getErrorCodePrefix(key, customErrors) {
        return customErrors?.[key] || MessageResolver.ERROR_KEY_PREFIX;
    }
    static resolveError(translate, errors, customErrors, fullErrorCode = false) {
        const rez = [];
        // jeigu field turi validacijos klaidų:
        if (errors) {
            // ištraukiame iš validacijos rezultatų objekto klaidų kodus:
            const errorKeys = Object.keys(errors);
            errorKeys.forEach((key) => {
                let prefix = '';
                if (!fullErrorCode) {
                    prefix = MessageResolver.getErrorCodePrefix(key, customErrors) + '.';
                }
                // tada atliekame vertimą per servisą:
                rez.push(translate.instant(prefix + key, errors[key]));
            });
        }
        return rez;
    }
    static resolveErrorToString(translate, errors, customErrors, fullErrorCode = false) {
        let errorMessage = '';
        const messages = MessageResolver.resolveError(translate, errors, customErrors, fullErrorCode);
        messages.forEach((msg, idx) => {
            errorMessage += msg;
            if (idx < messages.length - 1) {
                errorMessage += '. ';
            }
        });
        return errorMessage;
    }
}

const EDIT_FORM_DATA_KEY = 'editFormValues';
/**
 * @deprecated since 16.0.0
 */
class DeprecatedBaseEditForm {
    formBuilder;
    commonFormServices;
    activatedRoute;
    static EDIT_FORM_DATA_KEY = 'editFormValues';
    destroy$ = new Subject();
    static formSavedSuccessSubject = new Subject();
    log = LoggerFactory.getLogger();
    formDataLoaded = false;
    initialData;
    data;
    form;
    id;
    actionType;
    dirty = false;
    saved = false;
    newRecordMode = true;
    constructor(formBuilder, commonFormServices, activatedRoute) {
        this.formBuilder = formBuilder;
        this.commonFormServices = commonFormServices;
        this.activatedRoute = activatedRoute;
    }
    ngOnInit() {
        this.buildForm(this.formBuilder);
        if (this.isRestoreUnsavedDataEnabled) {
            this.form.valueChanges.pipe(takeUntil(this.destroy$), auditTime(200)).subscribe(() => {
                this.saveFormDataToLS();
            });
        }
        if (this.activatedRoute) {
            this.activatedRoute.queryParams.subscribe((queryParam) => {
                if (queryParam.actionType !== undefined) {
                    this.actionType = queryParam.actionType;
                }
                else {
                    this.actionType = null;
                }
            });
            this.activatedRoute.params.subscribe((params) => {
                this.id = params.id;
                this.formDataLoaded = false;
                if (this.id && this.id !== 'new') {
                    if (this.isRestoreUnsavedDataEnabled && this.getFormDataFromLS()) {
                        this.loadFormDataFromLSWithConfirmation(true);
                    }
                    else {
                        this.executeDoLoad(this.id, this.actionType);
                    }
                }
                else if (this.id === 'new') {
                    this.initialData = this.copyObject(this.getData());
                    this.setFormState(null);
                    this.loadFormDataFromLSWithConfirmation();
                }
                else if (this.forceDoLoad()) {
                    this.executeDoLoad(null, this.actionType);
                }
            });
        }
    }
    ngOnDestroy() {
        this.destroy$.next();
        this.destroy$.unsubscribe();
        this.removeFormDataFromLS();
    }
    // eslint-disable-next-line @typescript-eslint/no-unused-vars
    onSubmit(event) {
        this.form.markAllAsTouched();
        this.commonFormServices.appMessages.clearMessages();
        if (this.form.valid) {
            this.executeDoSave(this.getData());
        }
        else {
            this.log.debug(this.form.errors);
            if (this.form.errors) {
                this.commonFormServices.appMessages.showError('', MessageResolver.resolveErrorToString(this.commonFormServices.translate, this.form.errors, null, true));
            }
            else {
                this.commonFormServices.translate
                    .get('common.message.correctValidationErrors')
                    .subscribe((translation) => {
                    this.commonFormServices.appMessages.showError('', translation, 'VALIDATION');
                });
            }
        }
    }
    canDeactivate() {
        if (this.hasDataChanged()) {
            return new Observable((observer) => {
                this.commonFormServices.confirmationService.confirm({
                    message: this.commonFormServices.translate.instant('common.message.discardChanges'),
                    icon: 'fa fa-question-circle',
                    accept: () => {
                        this.removeFormDataFromLS();
                        observer.next(true);
                        observer.complete();
                    },
                    reject: () => {
                        observer.next(false);
                        observer.complete();
                    },
                });
            });
        }
        else {
            this.removeFormDataFromLS();
            return true;
        }
    }
    onChange() {
        this.dirty = true;
        this.saved = false;
    }
    doCancel() {
        if (this.isWarningEnabled &&
            window.confirm(this.commonFormServices.translate.instant('common.message.discardChanges'))) {
            this.executeDoLoad(this.id);
        }
    }
    downloadFile(data, fileName) {
        const link = document.createElement('a');
        link.style.display = 'none';
        document.body.appendChild(link);
        if (link.download !== undefined) {
            link.setAttribute('href', URL.createObjectURL(data));
            link.setAttribute('download', fileName);
            link.click();
        }
        document.body.removeChild(link);
    }
    onSaveSuccess(data) {
        this.log.debug('onSaveSuccess');
        this.commonFormServices.appMessages.showSuccess('', this.getSuccessMessage());
        this.form.reset();
        this.removeFormDataFromLS();
        this.onLoadSuccess(data);
        this.dirty = false;
        this.saved = true;
        if (this.isEventEmitEnabled) {
            DeprecatedBaseEditForm.formSavedSuccessSubject.next(true);
        }
    }
    getSuccessMessage() {
        return this.commonFormServices.translate.instant('common.message.saveSuccess');
    }
    setFormControlDisabled(controlName, disabled) {
        const control = this.form.controls[controlName];
        if (disabled === true) {
            control.disable();
        }
        else {
            control.enable();
        }
    }
    // eslint-disable-next-line @typescript-eslint/no-empty-function, @typescript-eslint/no-unused-vars
    setFormState(data) { }
    onLoadSuccess(data) {
        this.log.debug('onLoadSuccess');
        this.newRecordMode = false;
        this.setFormState(data);
        this.setData(data);
        this.initialData = this.copyObject(this.getData());
        this.dirty = false;
        this.formDataLoaded = true;
    }
    markFormFieldsPristine() {
        Object.keys(this.form.controls).forEach((key) => {
            this.form.controls[key].markAsPristine();
            if (this.form.controls[key] instanceof FormGroup) {
                Object.keys(this.form.controls[key].controls).forEach((key2) => {
                    this.form.controls[key].controls[key2].markAsPristine();
                });
            }
        });
    }
    get isWarningEnabled() {
        return true;
    }
    get isEventEmitEnabled() {
        return true;
    }
    get isRestoreUnsavedDataEnabled() {
        return false;
    }
    copyObject(data) {
        return JSON.parse(this.stringifyJsonEmptyAsNulls(data));
    }
    stringifyJsonEmptyAsNulls(data) {
        return JSON.stringify(data, (key, value) => {
            return value === '' ? null : value;
        });
    }
    hasDataChanged() {
        const initialData = JSON.stringify(this.initialData);
        const newData = this.stringifyJsonEmptyAsNulls(this.getData());
        return !this.saved && initialData !== newData;
    }
    forceDoLoad() {
        return false;
    }
    backToBrowseForm(formUrl, startAtRoot = true) {
        const navigationExtras = { state: { keepFilters: true } };
        if (startAtRoot) {
            if (typeof formUrl === 'string') {
                formUrl = [`/${formUrl}`];
            }
            else if (Array.isArray(formUrl)) {
                formUrl = ['/', ...formUrl];
            }
        }
        void this.commonFormServices.router.navigate(typeof formUrl === 'string' ? [formUrl] : formUrl, navigationExtras);
    }
    saveFormDataToLS() {
        if (this.formDataLoaded) {
            const formData = {
                url: this.commonFormServices.router.url,
                actionType: this.actionType,
                data: this.getData(),
            };
            localStorage.setItem(EDIT_FORM_DATA_KEY, JSON.stringify(formData));
        }
    }
    getFormDataFromLS() {
        const formData = JSON.parse(localStorage.getItem(EDIT_FORM_DATA_KEY));
        if (formData) {
            if (formData.url === this.commonFormServices.router.url &&
                formData.actionType === this.actionType &&
                formData.data) {
                return formData;
            }
            this.removeFormDataFromLS();
        }
        return null;
    }
    removeFormDataFromLS() {
        localStorage.removeItem(EDIT_FORM_DATA_KEY);
    }
    loadFormDataFromLSWithConfirmation(doLoadIfRejected = false, dialogText = 'common.message.restoreUnsavedData', translate = true) {
        if (this.isRestoreUnsavedDataEnabled) {
            const formData = this.getFormDataFromLS();
            if (formData?.data) {
                if (translate) {
                    this.commonFormServices.translate.get(dialogText).subscribe((translation) => {
                        this.commonFormServices.confirmationService.confirm({
                            message: translation,
                            accept: () => {
                                this.setData(formData.data);
                                this.formDataLoaded = true;
                            },
                            reject: () => {
                                this.removeFormDataFromLS();
                                if (doLoadIfRejected) {
                                    this.executeDoLoad(this.id, this.actionType);
                                }
                                else {
                                    this.formDataLoaded = true;
                                }
                            },
                        });
                    });
                }
                else {
                    this.commonFormServices.confirmationService.confirm({
                        message: dialogText,
                        accept: () => {
                            this.setData(formData.data);
                        },
                        reject: this.removeFormDataFromLS.bind(this),
                    });
                }
            }
        }
    }
    executeDoSave(data) {
        this.doSave(data)?.subscribe({
            next: (result) => {
                this.onSaveSuccess(result);
            },
            error: (error) => {
                let constraintMessages;
                if (error instanceof S2Error) {
                    constraintMessages = error.messages?.filter((message) => message.key === 'db.table.uk.constraint');
                    if (constraintMessages?.length) {
                        const violatedConstraints = constraintMessages.map((message) => ({
                            name: message.item,
                            fields: message.params,
                        }));
                        this.handleConstraintsResult(violatedConstraints);
                    }
                }
                if (!constraintMessages?.length || !(error instanceof S2Error)) {
                    throw error;
                }
            },
        });
    }
    executeDoLoad(id, actionType) {
        this.doLoad(id, actionType)?.subscribe((result) => {
            this.onLoadSuccess(result);
        });
    }
    checkConstraints(apiFunction, fields, fieldControls) {
        apiFunction().subscribe((response) => {
            this.handleConstraintsResult(response, fields, fieldControls);
        });
    }
    handleConstraintsResult(result, fields, fieldControls) {
        result.forEach((constraint) => {
            if (constraint.fields?.length) {
                constraint.fields
                    .filter((field) => !fields || fields.includes(field))
                    .forEach((field) => {
                    const control = fieldControls?.[field] && fieldControls?.[field] instanceof AbstractControl
                        ? fieldControls?.[field]
                        : this.form.controls[field];
                    if (control) {
                        const errors = control.errors || {};
                        errors[constraint.name] = true;
                        control.setErrors(errors);
                    }
                });
            }
        });
    }
    static ɵfac = i0.ɵɵngDeclareFactory({ minVersion: "12.0.0", version: "16.0.2", ngImport: i0, type: DeprecatedBaseEditForm, deps: [{ token: i1$2.FormBuilder }, { token: CommonFormServices }, { token: i1.ActivatedRoute }], target: i0.ɵɵFactoryTarget.Component });
    static ɵcmp = i0.ɵɵngDeclareComponent({ minVersion: "14.0.0", version: "16.0.2", type: DeprecatedBaseEditForm, selector: "ng-component", ngImport: i0, template: '<p></p>', isInline: true });
}
i0.ɵɵngDeclareClassMetadata({ minVersion: "12.0.0", version: "16.0.2", ngImport: i0, type: DeprecatedBaseEditForm, decorators: [{
            type: Component,
            args: [{ template: '<p></p>' }]
        }], ctorParameters: function () { return [{ type: i1$2.FormBuilder }, { type: CommonFormServices }, { type: i1.ActivatedRoute }]; } });

class BaseEditForm {
    commonFormServices;
    activatedRoute;
    static LS_KEY_EDIT_FORM_DATA = 'editFormValues';
    destroy$ = new Subject();
    static formSavedSuccessSubject = new Subject();
    log = LoggerFactory.getLogger();
    formDataLoaded = false;
    initialData;
    data;
    id;
    actionType;
    dirty = false;
    saved = false;
    newRecordMode = true;
    constructor(commonFormServices, activatedRoute) {
        this.commonFormServices = commonFormServices;
        this.activatedRoute = activatedRoute;
    }
    ngOnInit() {
        if (this.isRestoreUnsavedDataEnabled) {
            this.form.valueChanges.pipe(takeUntil(this.destroy$), auditTime(200)).subscribe(() => {
                this.saveFormDataToLS();
            });
        }
        if (this.activatedRoute) {
            this.activatedRoute.queryParams.subscribe((queryParam) => {
                if (queryParam.actionType !== undefined) {
                    this.actionType = queryParam.actionType;
                }
                else {
                    this.actionType = null;
                }
            });
            this.activatedRoute.params.subscribe((params) => {
                this.id = params.id;
                this.formDataLoaded = false;
                if (this.id && this.id !== 'new') {
                    if (this.isRestoreUnsavedDataEnabled && this.getFormDataFromLS()) {
                        this.loadFormDataFromLSWithConfirmation(true);
                    }
                    else {
                        this.executeDoLoad(this.id, this.actionType);
                    }
                }
                else if (this.id === 'new') {
                    this.initialData = this.copyObject(this.getData());
                    this.setFormState(null);
                    this.loadFormDataFromLSWithConfirmation();
                }
                else if (this.forceDoLoad()) {
                    this.executeDoLoad(null, this.actionType);
                }
            });
        }
    }
    ngOnDestroy() {
        this.destroy$.next();
        this.destroy$.unsubscribe();
        this.removeFormDataFromLS();
    }
    // eslint-disable-next-line @typescript-eslint/no-unused-vars
    onSubmit(event) {
        this.form.markAllAsTouched();
        this.commonFormServices.appMessages.clearMessages();
        if (this.form.valid) {
            this.executeDoSave(this.getData());
        }
        else {
            this.log.debug(this.form.errors);
            if (this.form.errors) {
                this.commonFormServices.appMessages.showError('', MessageResolver.resolveErrorToString(this.commonFormServices.translate, this.form.errors, null, true));
            }
            else {
                this.commonFormServices.translate
                    .get('common.message.correctValidationErrors')
                    .subscribe((translation) => {
                    this.commonFormServices.appMessages.showError('', translation, 'VALIDATION');
                });
            }
        }
    }
    canDeactivate() {
        if (this.hasDataChanged()) {
            return new Observable((observer) => {
                this.commonFormServices.confirmationService.confirm({
                    message: this.commonFormServices.translate.instant('common.message.discardChanges'),
                    icon: 'fa fa-question-circle',
                    accept: () => {
                        this.removeFormDataFromLS();
                        observer.next(true);
                        observer.complete();
                    },
                    reject: () => {
                        observer.next(false);
                        observer.complete();
                    },
                });
            });
        }
        else {
            this.removeFormDataFromLS();
            return true;
        }
    }
    onChange() {
        this.dirty = true;
        this.saved = false;
    }
    doCancel() {
        if (this.isWarningEnabled &&
            window.confirm(this.commonFormServices.translate.instant('common.message.discardChanges'))) {
            this.executeDoLoad(this.id);
        }
    }
    downloadFile(data, fileName) {
        const link = document.createElement('a');
        link.style.display = 'none';
        document.body.appendChild(link);
        if (link.download !== undefined) {
            link.setAttribute('href', URL.createObjectURL(data));
            link.setAttribute('download', fileName);
            link.click();
        }
        document.body.removeChild(link);
    }
    onSaveSuccess(data) {
        this.log.debug('onSaveSuccess');
        this.commonFormServices.appMessages.showSuccess('', this.getSuccessMessage());
        this.form.reset();
        this.removeFormDataFromLS();
        this.onLoadSuccess(data);
        this.dirty = false;
        this.saved = true;
        if (this.isEventEmitEnabled) {
            BaseEditForm.formSavedSuccessSubject.next(true);
        }
    }
    getSuccessMessage() {
        return this.commonFormServices.translate.instant('common.message.saveSuccess');
    }
    setFormControlDisabled(controlName, disabled) {
        const control = this.form.controls[controlName];
        if (disabled === true) {
            control.disable();
        }
        else {
            control.enable();
        }
    }
    // eslint-disable-next-line @typescript-eslint/no-empty-function, @typescript-eslint/no-unused-vars
    setFormState(data) { }
    onLoadSuccess(data) {
        this.log.debug('onLoadSuccess');
        this.newRecordMode = false;
        this.setFormState(data);
        this.setData(data);
        this.initialData = this.copyObject(this.getData());
        this.dirty = false;
        this.formDataLoaded = true;
    }
    markFormFieldsPristine() {
        Object.keys(this.form.controls).forEach((key) => {
            this.form.controls[key].markAsPristine();
            if (this.form.controls[key] instanceof FormGroup) {
                Object.keys(this.form.controls[key].controls).forEach((key2) => {
                    this.form.controls[key].controls[key2].markAsPristine();
                });
            }
        });
    }
    get isWarningEnabled() {
        return true;
    }
    get isEventEmitEnabled() {
        return true;
    }
    get isRestoreUnsavedDataEnabled() {
        return false;
    }
    copyObject(data) {
        return JSON.parse(this.stringifyJsonEmptyAsNulls(data));
    }
    stringifyJsonEmptyAsNulls(data) {
        return JSON.stringify(data, (key, value) => {
            return value === '' ? null : value;
        });
    }
    hasDataChanged() {
        const initialData = JSON.stringify(this.initialData);
        const newData = this.stringifyJsonEmptyAsNulls(this.getData());
        return !this.saved && initialData !== newData;
    }
    forceDoLoad() {
        return false;
    }
    backToBrowseForm(formUrl, startAtRoot = true) {
        const navigationExtras = { state: { keepFilters: true } };
        if (startAtRoot) {
            if (typeof formUrl === 'string') {
                formUrl = [`/${formUrl}`];
            }
            else if (Array.isArray(formUrl)) {
                formUrl = ['/', ...formUrl];
            }
        }
        void this.commonFormServices.router.navigate(typeof formUrl === 'string' ? [formUrl] : formUrl, navigationExtras);
    }
    saveFormDataToLS() {
        if (this.formDataLoaded) {
            const formData = {
                url: this.commonFormServices.router.url,
                actionType: this.actionType,
                data: this.getData(),
            };
            localStorage.setItem(BaseEditForm.LS_KEY_EDIT_FORM_DATA, JSON.stringify(formData));
        }
    }
    getFormDataFromLS() {
        const formData = JSON.parse(localStorage.getItem(BaseEditForm.LS_KEY_EDIT_FORM_DATA));
        if (formData) {
            if (formData.url === this.commonFormServices.router.url &&
                formData.actionType === this.actionType &&
                formData.data) {
                return formData;
            }
            this.removeFormDataFromLS();
        }
        return null;
    }
    removeFormDataFromLS() {
        localStorage.removeItem(BaseEditForm.LS_KEY_EDIT_FORM_DATA);
    }
    loadFormDataFromLSWithConfirmation(doLoadIfRejected = false, dialogText = 'common.message.restoreUnsavedData', translate = true) {
        if (this.isRestoreUnsavedDataEnabled) {
            const formData = this.getFormDataFromLS();
            if (formData?.data) {
                if (translate) {
                    this.commonFormServices.translate.get(dialogText).subscribe((translation) => {
                        this.commonFormServices.confirmationService.confirm({
                            message: translation,
                            accept: () => {
                                this.setData(formData.data);
                                this.formDataLoaded = true;
                            },
                            reject: () => {
                                this.removeFormDataFromLS();
                                if (doLoadIfRejected) {
                                    this.executeDoLoad(this.id, this.actionType);
                                }
                                else {
                                    this.formDataLoaded = true;
                                }
                            },
                        });
                    });
                }
                else {
                    this.commonFormServices.confirmationService.confirm({
                        message: dialogText,
                        accept: () => {
                            this.setData(formData.data);
                        },
                        reject: this.removeFormDataFromLS.bind(this),
                    });
                }
            }
        }
    }
    executeDoSave(data) {
        this.doSave(data)?.subscribe({
            next: (result) => {
                this.onSaveSuccess(result);
            },
            error: (error) => {
                let constraintMessages;
                if (error instanceof S2Error) {
                    constraintMessages = error.messages?.filter((message) => message.key === 'db.table.uk.constraint');
                    if (constraintMessages?.length) {
                        const violatedConstraints = constraintMessages.map((message) => ({
                            name: message.item,
                            fields: message.params,
                        }));
                        this.handleConstraintsResult(violatedConstraints);
                    }
                }
                if (!constraintMessages?.length || !(error instanceof S2Error)) {
                    throw error;
                }
            },
        });
    }
    executeDoLoad(id, actionType) {
        this.doLoad(id, actionType)?.subscribe((result) => {
            this.onLoadSuccess(result);
        });
    }
    checkConstraints(apiFunction, fields, fieldControls) {
        apiFunction().subscribe((response) => {
            this.handleConstraintsResult(response, fields, fieldControls);
        });
    }
    handleConstraintsResult(result, fields, fieldControls) {
        result.forEach((constraint) => {
            if (constraint.fields?.length) {
                constraint.fields
                    .filter((field) => !fields || fields.includes(field))
                    .forEach((field) => {
                    const control = fieldControls?.[field] && fieldControls?.[field] instanceof AbstractControl
                        ? fieldControls?.[field]
                        : this.form.controls[field];
                    if (control) {
                        const errors = control.errors || {};
                        errors[constraint.name] = true;
                        control.setErrors(errors);
                    }
                });
            }
        });
    }
    static ɵfac = i0.ɵɵngDeclareFactory({ minVersion: "12.0.0", version: "16.0.2", ngImport: i0, type: BaseEditForm, deps: [{ token: CommonFormServices }, { token: i1.ActivatedRoute }], target: i0.ɵɵFactoryTarget.Component });
    static ɵcmp = i0.ɵɵngDeclareComponent({ minVersion: "14.0.0", version: "16.0.2", type: BaseEditForm, selector: "ng-component", ngImport: i0, template: '<p></p>', isInline: true });
}
i0.ɵɵngDeclareClassMetadata({ minVersion: "12.0.0", version: "16.0.2", ngImport: i0, type: BaseEditForm, decorators: [{
            type: Component,
            args: [{ template: '<p></p>' }]
        }], ctorParameters: function () { return [{ type: CommonFormServices }, { type: i1.ActivatedRoute }]; } });

class BaseAppComponent {
    router;
    appMessages;
    translate;
    route;
    ngZone;
    currentUser;
    paramsLang;
    constructor(router, appMessages, translate, route, ngZone) {
        this.router = router;
        this.appMessages = appMessages;
        this.translate = translate;
        this.route = route;
        this.ngZone = ngZone;
        this.router.events.subscribe((event) => {
            if (event instanceof ActivationEnd) {
                this.paramsLang = event.snapshot.queryParams['lang'];
                if (this.paramsLang) {
                    setLang(this.translate, this.paramsLang);
                }
            }
            initLang(this.translate);
        });
        AuthEvent.userLoggedIn.subscribe((res) => {
            if (res.type === AuthEvent.AUTH_EVENT_UPDATE) {
                this.onUserInfoUpdate(res.session);
            }
            else {
                if (res.userLoggedIn) {
                    this.setUser();
                    this.onLogin();
                    this.handleRegStatus(res);
                }
                else if (res.type !== AuthEvent.AUTH_EVENT_LOGOUT) {
                    this.clearUser();
                    this.onLogout();
                    this.ngZone.run(() => {
                        void this.router
                            .navigate([this.getLoginUrl()], { queryParams: { returnUrl: this.getReturnUrl(res) } })
                            .then(() => {
                            if (res.type === AuthEvent.AUTH_EVENT_401) {
                                this.showNoAuthWarning();
                            }
                        });
                    });
                }
                if (res.type === AuthEvent.AUTH_EVENT_LOGOUT) {
                    this.clearUser();
                    this.onLogout();
                }
            }
        });
    }
    // eslint-disable-next-line @typescript-eslint/no-empty-function
    onLogin() { }
    // eslint-disable-next-line @typescript-eslint/no-empty-function
    onLogout() { }
    // eslint-disable-next-line @typescript-eslint/no-empty-function, @typescript-eslint/no-unused-vars
    handleRegStatus(regStatus) { }
    onUserInfoUpdate(session) {
        AuthUtil.updateSessionInfo(session);
        this.currentUser = session;
    }
    ngOnInit() {
        this.setUser();
        this.router.events.subscribe((val) => {
            if (val instanceof NavigationEnd) {
                this.appMessages.clearMessagesExceptSuccess();
            }
        });
    }
    clearUser() {
        this.currentUser = null;
        AuthUtil.clearSessionStorage();
    }
    setUser() {
        this.currentUser = AuthUtil.getSessionInfo();
        if (this.currentUser != null && this.currentUser.language != null) {
            setLang(this.translate, this.currentUser.language.toLowerCase());
        }
    }
    showNoAuthWarning() {
        this.translate.get('common.message.noSession').subscribe((translation) => {
            this.appMessages.showWarning('', translation, AuthEvent.AUTH_EVENT_NO_AUTH);
        });
    }
    getReturnUrl(authEvent) {
        return authEvent.returnUrl || '';
    }
    isLoggedIn() {
        return AuthUtil.isLoggedIn();
    }
    static ɵfac = i0.ɵɵngDeclareFactory({ minVersion: "12.0.0", version: "16.0.2", ngImport: i0, type: BaseAppComponent, deps: [{ token: i1.Router }, { token: AppMessages }, { token: i3.TranslateService }, { token: i1.ActivatedRoute }, { token: i0.NgZone }], target: i0.ɵɵFactoryTarget.Component });
    static ɵcmp = i0.ɵɵngDeclareComponent({ minVersion: "14.0.0", version: "16.0.2", type: BaseAppComponent, selector: "ng-component", ngImport: i0, template: '', isInline: true });
}
i0.ɵɵngDeclareClassMetadata({ minVersion: "12.0.0", version: "16.0.2", ngImport: i0, type: BaseAppComponent, decorators: [{
            type: Component,
            args: [{ template: '' }]
        }], ctorParameters: function () { return [{ type: i1.Router }, { type: AppMessages }, { type: i3.TranslateService }, { type: i1.ActivatedRoute }, { type: i0.NgZone }]; } });

class ContentTypeInterceptor {
    intercept(request, next) {
        if (request.method === 'POST' &&
            !request.headers.get('Content-Type') &&
            (request.body === undefined || request.body === null)) {
            const clonedRequest = request.clone({
                headers: request.headers.set('Content-Type', 'application/json'),
            });
            return next.handle(clonedRequest);
        }
        return next.handle(request);
    }
    static ɵfac = i0.ɵɵngDeclareFactory({ minVersion: "12.0.0", version: "16.0.2", ngImport: i0, type: ContentTypeInterceptor, deps: [], target: i0.ɵɵFactoryTarget.Injectable });
    static ɵprov = i0.ɵɵngDeclareInjectable({ minVersion: "12.0.0", version: "16.0.2", ngImport: i0, type: ContentTypeInterceptor });
}
i0.ɵɵngDeclareClassMetadata({ minVersion: "12.0.0", version: "16.0.2", ngImport: i0, type: ContentTypeInterceptor, decorators: [{
            type: Injectable
        }] });
const contentTypeInterceptor = {
    provide: HTTP_INTERCEPTORS,
    useClass: ContentTypeInterceptor,
    multi: true,
};

class JwtInterceptor {
    intercept(req, next) {
        // console.log('JwtInterceptor');
        const clonedRequest = req.clone({
            headers: req.headers.set('Authorization', 'Bearer ' + AuthUtil.getJWTFromSession()),
        });
        return next.handle(clonedRequest);
    }
    static ɵfac = i0.ɵɵngDeclareFactory({ minVersion: "12.0.0", version: "16.0.2", ngImport: i0, type: JwtInterceptor, deps: [], target: i0.ɵɵFactoryTarget.Injectable });
    static ɵprov = i0.ɵɵngDeclareInjectable({ minVersion: "12.0.0", version: "16.0.2", ngImport: i0, type: JwtInterceptor });
}
i0.ɵɵngDeclareClassMetadata({ minVersion: "12.0.0", version: "16.0.2", ngImport: i0, type: JwtInterceptor, decorators: [{
            type: Injectable
        }] });
const jwtProvider = {
    provide: HTTP_INTERCEPTORS,
    useClass: JwtInterceptor,
    multi: true,
};

class S2Interceptor {
    loaderService;
    log = LoggerFactory.getLogger();
    constructor(loaderService) {
        this.loaderService = loaderService;
    }
    intercept(req, next) {
        if (!req.headers.has('x-s2-noloader')) {
            this.loaderService.show(req.url);
        }
        return next.handle(req).pipe(tap({
            next: (event) => {
                if (event instanceof HttpResponse) {
                    if (!req.headers.has('x-s2-noloader')) {
                        this.loaderService.hide();
                    }
                    const s2Status = event.headers.get('X-S2-status');
                    this.log.debug(`X-S2-status=${s2Status}`);
                    if (s2Status === 'ERR') {
                        const messages = event.body;
                        this.log.debug(messages);
                        throw new S2Error(messages);
                    }
                }
            },
            error: (err) => {
                if (!req.headers.has('x-s2-noloader')) {
                    this.loaderService.hide();
                }
                this.log.error('HTTP error', err);
                if (err instanceof HttpErrorResponse) {
                    this.log.debug(`${err.status} ' - system error:'}`);
                    switch (err.status) {
                        case 403:
                        case 401:
                            AuthEvent.isUnauthError = true;
                            throw new UnauthorizedError();
                        case 500:
                            throw new ServerError(err.headers.get('X-S2-Err-UUID'));
                        case 503:
                        case 504:
                            throw new ServerUnavailableError();
                    }
                }
            },
        }));
    }
    static ɵfac = i0.ɵɵngDeclareFactory({ minVersion: "12.0.0", version: "16.0.2", ngImport: i0, type: S2Interceptor, deps: [{ token: LoaderService }], target: i0.ɵɵFactoryTarget.Injectable });
    static ɵprov = i0.ɵɵngDeclareInjectable({ minVersion: "12.0.0", version: "16.0.2", ngImport: i0, type: S2Interceptor });
}
i0.ɵɵngDeclareClassMetadata({ minVersion: "12.0.0", version: "16.0.2", ngImport: i0, type: S2Interceptor, decorators: [{
            type: Injectable
        }], ctorParameters: function () { return [{ type: LoaderService }]; } });
const s2Provider = {
    provide: HTTP_INTERCEPTORS,
    useClass: S2Interceptor,
    multi: true,
};

class CommonAuthService {
    http;
    constructor(http) {
        this.http = http;
    }
    processLogin(response, returnUrl, checkForPasswordChangeToken = true) {
        if (response && response.session) {
            AuthUtil.setLoginResult(response);
            if (response.token && (!checkForPasswordChangeToken || !response.session.usrPasswordChangeToken)) {
                AuthUtil.setTermsAccepted(response.session?.usrTermsAccepted === 'Y');
                this.getRegistrationStatus().subscribe((regStatus) => {
                    AuthEvent.userLoggedIn.next({
                        userLoggedIn: true,
                        session: response.session,
                        type: AuthEvent.AUTH_EVENT_LOGIN,
                        regStatus,
                        returnUrl,
                    });
                });
            }
        }
    }
    getRegistrationStatus() {
        return of('ok');
    }
    processLogout() {
        AuthUtil.clearSessionStorage();
        AuthEvent.userLoggedIn.next({ userLoggedIn: false, session: null, type: AuthEvent.AUTH_EVENT_LOGOUT });
    }
    static ɵfac = i0.ɵɵngDeclareFactory({ minVersion: "12.0.0", version: "16.0.2", ngImport: i0, type: CommonAuthService, deps: [{ token: i1$1.HttpClient }], target: i0.ɵɵFactoryTarget.Injectable });
    static ɵprov = i0.ɵɵngDeclareInjectable({ minVersion: "12.0.0", version: "16.0.2", ngImport: i0, type: CommonAuthService, providedIn: 'root' });
}
i0.ɵɵngDeclareClassMetadata({ minVersion: "12.0.0", version: "16.0.2", ngImport: i0, type: CommonAuthService, decorators: [{
            type: Injectable,
            args: [{
                    providedIn: 'root',
                }]
        }], ctorParameters: function () { return [{ type: i1$1.HttpClient }]; } });

class AuthGuard {
    canActivate(route, state) {
        return this.checkSession(state) && this.checkStatus(state);
    }
    canActivateChild(childRoute, state) {
        return this.canActivate(childRoute, state);
    }
    checkSession(state) {
        if (AuthUtil.isLoggedIn()) {
            return true;
        }
        AuthEvent.userLoggedIn.next({
            userLoggedIn: false,
            session: null,
            type: AuthEvent.AUTH_EVENT_NO_AUTH,
            returnUrl: state.url,
        });
        return false;
    }
    checkStatus(state) {
        return true;
    }
    static ɵfac = i0.ɵɵngDeclareFactory({ minVersion: "12.0.0", version: "16.0.2", ngImport: i0, type: AuthGuard, deps: [], target: i0.ɵɵFactoryTarget.Injectable });
    static ɵprov = i0.ɵɵngDeclareInjectable({ minVersion: "12.0.0", version: "16.0.2", ngImport: i0, type: AuthGuard });
}
i0.ɵɵngDeclareClassMetadata({ minVersion: "12.0.0", version: "16.0.2", ngImport: i0, type: AuthGuard, decorators: [{
            type: Injectable
        }] });

class CanDeactivateGuard {
    canDeactivate(component, currentRoute, currentState, nextState) {
        const skip = AuthEvent.isUnauthError || (nextState && nextState.url.includes(this.getLoginPath()));
        AuthEvent.isUnauthError = false;
        return skip || component.canDeactivate();
    }
}

class Spr_id_values_ot {
    id;
    key_value;
    display_text;
    constructor(id, key_value, display_text) {
        this.id = id;
        this.key_value = key_value;
        this.display_text = display_text;
    }
}

/* eslint-disable @typescript-eslint/naming-convention, no-underscore-dangle, id-blacklist, id-match */
// eslint-disable-next-line @typescript-eslint/naming-convention
class Spr_key_values_ot {
    code;
    key_value;
    display_text;
}

/*
 * Public API Surface of ngx-s2-commons
 */

/**
 * Generated bundle index. Do not edit.
 */

export { AppErrorHandler, AppMessages, AppMissingTranslationHandler, AuthEvent, AuthGuard, AuthUtil, BROWSE_SEARCH_DATA_KEY, BaseAppComponent, BaseBrowseForm, BaseEditForm, CanDeactivateGuard, ClientLogService, CommonAuthService, CommonFormServices, ContentTypeInterceptor, DEFAULT_LANGUAGE, DeprecatedBaseEditForm, EDIT_FORM_DATA_KEY, ExtendedSearchCondition, ExtendedSearchUpperLower, JwtInterceptor, LOCAL_STORAGE_LANG_KEY, LoaderService, MULTI_TAB_KEY, MessageResolver, NgxS2CommonsModule, PWD_PATTERN, S2Error, S2Interceptor, SESSION_KEY, SES_VALUE_FALSE, SES_VALUE_TRUE, SPEC_CHARS, ServerError, ServerUnavailableError, Spr_id_values_ot, Spr_key_values_ot, Spr_paging_ot, TERMS_KEY, UnauthorizedError, appErrorHandler, appendPagingParams, appendSearchParams, contentTypeInterceptor, convertKeyValues, deepEquals, escapeRegExp, fromBoolean, fromKeyValues, fromRadioBtnIdVal, fromRadioBtnKeyVal, getLang, initLang, jwtProvider, keyValuesToMap, s2Provider, setLang, toBoolean, toKeyValues, toRadioBtnIdVal, toRadioBtnKeyVal };
//# sourceMappingURL=itree-ngx-s2-commons.mjs.map
