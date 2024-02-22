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
export { MessageResolver };
//# sourceMappingURL=data:application/json;base64,eyJ2ZXJzaW9uIjozLCJmaWxlIjoibWVzc2FnZS5yZXNvbHZlci5qcyIsInNvdXJjZVJvb3QiOiIiLCJzb3VyY2VzIjpbIi4uLy4uLy4uLy4uLy4uLy4uLy4uL3Byb2plY3RzL25neC1zMi1jb21tb25zL3NyYy9saWIvdWkvZmllbGQvdmFsaWRhdGlvbi9tZXNzYWdlLnJlc29sdmVyLnRzIl0sIm5hbWVzIjpbXSwibWFwcGluZ3MiOiJBQUVBLE1BQWEsZUFBZTtJQUNsQixNQUFNLENBQVUsZ0JBQWdCLEdBQUcsY0FBYyxDQUFDO0lBRTFEOztPQUVHO0lBQ0ssTUFBTSxDQUFDLGtCQUFrQixDQUFDLEdBQVcsRUFBRSxZQUFvQztRQUNqRixPQUFPLFlBQVksRUFBRSxDQUFDLEdBQUcsQ0FBQyxJQUFJLGVBQWUsQ0FBQyxnQkFBZ0IsQ0FBQztJQUNqRSxDQUFDO0lBRUQsTUFBTSxDQUFDLFlBQVksQ0FDakIsU0FBMkIsRUFDM0IsTUFBK0IsRUFDL0IsWUFBb0MsRUFDcEMsYUFBYSxHQUFHLEtBQUs7UUFFckIsTUFBTSxHQUFHLEdBQWEsRUFBRSxDQUFDO1FBQ3pCLHVDQUF1QztRQUN2QyxJQUFJLE1BQU0sRUFBRTtZQUNWLDZEQUE2RDtZQUU3RCxNQUFNLFNBQVMsR0FBRyxNQUFNLENBQUMsSUFBSSxDQUFDLE1BQU0sQ0FBQyxDQUFDO1lBQ3RDLFNBQVMsQ0FBQyxPQUFPLENBQUMsQ0FBQyxHQUFHLEVBQUUsRUFBRTtnQkFDeEIsSUFBSSxNQUFNLEdBQUcsRUFBRSxDQUFDO2dCQUNoQixJQUFJLENBQUMsYUFBYSxFQUFFO29CQUNsQixNQUFNLEdBQUcsZUFBZSxDQUFDLGtCQUFrQixDQUFDLEdBQUcsRUFBRSxZQUFZLENBQUMsR0FBRyxHQUFHLENBQUM7aUJBQ3RFO2dCQUNELHNDQUFzQztnQkFFdEMsR0FBRyxDQUFDLElBQUksQ0FBQyxTQUFTLENBQUMsT0FBTyxDQUFDLE1BQU0sR0FBRyxHQUFHLEVBQUUsTUFBTSxDQUFDLEdBQUcsQ0FBQyxDQUFXLENBQUMsQ0FBQztZQUNuRSxDQUFDLENBQUMsQ0FBQztTQUNKO1FBQ0QsT0FBTyxHQUFHLENBQUM7SUFDYixDQUFDO0lBRUQsTUFBTSxDQUFDLG9CQUFvQixDQUN6QixTQUEyQixFQUMzQixNQUErQixFQUMvQixZQUFvQyxFQUNwQyxhQUFhLEdBQUcsS0FBSztRQUVyQixJQUFJLFlBQVksR0FBRyxFQUFFLENBQUM7UUFDdEIsTUFBTSxRQUFRLEdBQUcsZUFBZSxDQUFDLFlBQVksQ0FBQyxTQUFTLEVBQUUsTUFBTSxFQUFFLFlBQVksRUFBRSxhQUFhLENBQUMsQ0FBQztRQUM5RixRQUFRLENBQUMsT0FBTyxDQUFDLENBQUMsR0FBRyxFQUFFLEdBQUcsRUFBRSxFQUFFO1lBQzVCLFlBQVksSUFBSSxHQUFHLENBQUM7WUFDcEIsSUFBSSxHQUFHLEdBQUcsUUFBUSxDQUFDLE1BQU0sR0FBRyxDQUFDLEVBQUU7Z0JBQzdCLFlBQVksSUFBSSxJQUFJLENBQUM7YUFDdEI7UUFDSCxDQUFDLENBQUMsQ0FBQztRQUNILE9BQU8sWUFBWSxDQUFDO0lBQ3RCLENBQUM7O1NBbERVLGVBQWUiLCJzb3VyY2VzQ29udGVudCI6WyJpbXBvcnQgeyBUcmFuc2xhdGVTZXJ2aWNlIH0gZnJvbSAnQG5neC10cmFuc2xhdGUvY29yZSc7XHJcblxyXG5leHBvcnQgY2xhc3MgTWVzc2FnZVJlc29sdmVyIHtcclxuICBwcml2YXRlIHN0YXRpYyByZWFkb25seSBFUlJPUl9LRVlfUFJFRklYID0gJ2NvbW1vbi5lcnJvcic7XHJcblxyXG4gIC8qKlxyXG4gICAqIEplaWd1IGZpZWxkZSBwZXIgW2Vycm9yRGVmc10gbnVyb2R5dGkgbmVzdGFuZGFydGluaWFpIHZhbGlkYWNpam9zIHByYW5lxaFpbWFpLCB0YWkgcGlybWlhdXNpYSBuYXVkb2phbWUganVvczpcclxuICAgKi9cclxuICBwcml2YXRlIHN0YXRpYyBnZXRFcnJvckNvZGVQcmVmaXgoa2V5OiBzdHJpbmcsIGN1c3RvbUVycm9yczogUmVjb3JkPHN0cmluZywgc3RyaW5nPik6IHN0cmluZyB7XHJcbiAgICByZXR1cm4gY3VzdG9tRXJyb3JzPy5ba2V5XSB8fCBNZXNzYWdlUmVzb2x2ZXIuRVJST1JfS0VZX1BSRUZJWDtcclxuICB9XHJcblxyXG4gIHN0YXRpYyByZXNvbHZlRXJyb3IoXHJcbiAgICB0cmFuc2xhdGU6IFRyYW5zbGF0ZVNlcnZpY2UsXHJcbiAgICBlcnJvcnM6IFJlY29yZDxzdHJpbmcsIHVua25vd24+LFxyXG4gICAgY3VzdG9tRXJyb3JzOiBSZWNvcmQ8c3RyaW5nLCBzdHJpbmc+LFxyXG4gICAgZnVsbEVycm9yQ29kZSA9IGZhbHNlXHJcbiAgKTogc3RyaW5nW10ge1xyXG4gICAgY29uc3QgcmV6OiBzdHJpbmdbXSA9IFtdO1xyXG4gICAgLy8gamVpZ3UgZmllbGQgdHVyaSB2YWxpZGFjaWpvcyBrbGFpZMWzOlxyXG4gICAgaWYgKGVycm9ycykge1xyXG4gICAgICAvLyBpxaF0cmF1a2lhbWUgacWhIHZhbGlkYWNpam9zIHJlenVsdGF0xbMgb2JqZWt0byBrbGFpZMWzIGtvZHVzOlxyXG5cclxuICAgICAgY29uc3QgZXJyb3JLZXlzID0gT2JqZWN0LmtleXMoZXJyb3JzKTtcclxuICAgICAgZXJyb3JLZXlzLmZvckVhY2goKGtleSkgPT4ge1xyXG4gICAgICAgIGxldCBwcmVmaXggPSAnJztcclxuICAgICAgICBpZiAoIWZ1bGxFcnJvckNvZGUpIHtcclxuICAgICAgICAgIHByZWZpeCA9IE1lc3NhZ2VSZXNvbHZlci5nZXRFcnJvckNvZGVQcmVmaXgoa2V5LCBjdXN0b21FcnJvcnMpICsgJy4nO1xyXG4gICAgICAgIH1cclxuICAgICAgICAvLyB0YWRhIGF0bGlla2FtZSB2ZXJ0aW3EhSBwZXIgc2VydmlzxIU6XHJcblxyXG4gICAgICAgIHJlei5wdXNoKHRyYW5zbGF0ZS5pbnN0YW50KHByZWZpeCArIGtleSwgZXJyb3JzW2tleV0pIGFzIHN0cmluZyk7XHJcbiAgICAgIH0pO1xyXG4gICAgfVxyXG4gICAgcmV0dXJuIHJlejtcclxuICB9XHJcblxyXG4gIHN0YXRpYyByZXNvbHZlRXJyb3JUb1N0cmluZyhcclxuICAgIHRyYW5zbGF0ZTogVHJhbnNsYXRlU2VydmljZSxcclxuICAgIGVycm9yczogUmVjb3JkPHN0cmluZywgdW5rbm93bj4sXHJcbiAgICBjdXN0b21FcnJvcnM6IFJlY29yZDxzdHJpbmcsIHN0cmluZz4sXHJcbiAgICBmdWxsRXJyb3JDb2RlID0gZmFsc2VcclxuICApOiBzdHJpbmcge1xyXG4gICAgbGV0IGVycm9yTWVzc2FnZSA9ICcnO1xyXG4gICAgY29uc3QgbWVzc2FnZXMgPSBNZXNzYWdlUmVzb2x2ZXIucmVzb2x2ZUVycm9yKHRyYW5zbGF0ZSwgZXJyb3JzLCBjdXN0b21FcnJvcnMsIGZ1bGxFcnJvckNvZGUpO1xyXG4gICAgbWVzc2FnZXMuZm9yRWFjaCgobXNnLCBpZHgpID0+IHtcclxuICAgICAgZXJyb3JNZXNzYWdlICs9IG1zZztcclxuICAgICAgaWYgKGlkeCA8IG1lc3NhZ2VzLmxlbmd0aCAtIDEpIHtcclxuICAgICAgICBlcnJvck1lc3NhZ2UgKz0gJy4gJztcclxuICAgICAgfVxyXG4gICAgfSk7XHJcbiAgICByZXR1cm4gZXJyb3JNZXNzYWdlO1xyXG4gIH1cclxufVxyXG4iXX0=