import { Subject } from 'rxjs';
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
export { AuthEvent };
//# sourceMappingURL=data:application/json;base64,eyJ2ZXJzaW9uIjozLCJmaWxlIjoiYXV0aC5ldmVudC5qcyIsInNvdXJjZVJvb3QiOiIiLCJzb3VyY2VzIjpbIi4uLy4uLy4uLy4uLy4uL3Byb2plY3RzL25neC1zMi1jb21tb25zL3NyYy9saWIvYXV0aC9hdXRoLmV2ZW50LnRzIl0sIm5hbWVzIjpbXSwibWFwcGluZ3MiOiJBQUFBLE9BQU8sRUFBRSxPQUFPLEVBQUUsTUFBTSxNQUFNLENBQUM7QUFHL0IsTUFBYSxTQUFTO0lBYVg7SUFDQTtJQUNBO0lBQ0E7SUFDQTtJQWhCRixNQUFNLENBQVUsZ0JBQWdCLEdBQUcsT0FBTyxDQUFDO0lBQzNDLE1BQU0sQ0FBVSxpQkFBaUIsR0FBRyxRQUFRLENBQUM7SUFDN0MsTUFBTSxDQUFVLGtCQUFrQixHQUFHLFNBQVMsQ0FBQztJQUMvQyxNQUFNLENBQVUsY0FBYyxHQUFHLEtBQUssQ0FBQztJQUN2QyxNQUFNLENBQVUscUJBQXFCLEdBQUcsWUFBWSxDQUFDO0lBQ3JELE1BQU0sQ0FBVSxpQkFBaUIsR0FBRyxRQUFRLENBQUM7SUFFN0MsTUFBTSxDQUFDLGFBQWEsR0FBRyxLQUFLLENBQUM7SUFFN0IsTUFBTSxDQUFDLFlBQVksR0FBdUIsSUFBSSxPQUFPLEVBQUUsQ0FBQztJQUUvRCxZQUNTLFlBQXFCLEVBQ3JCLE9BQXVCLEVBQ3ZCLElBQVksRUFDWixTQUFrQixFQUNsQixTQUFrQjtRQUpsQixpQkFBWSxHQUFaLFlBQVksQ0FBUztRQUNyQixZQUFPLEdBQVAsT0FBTyxDQUFnQjtRQUN2QixTQUFJLEdBQUosSUFBSSxDQUFRO1FBQ1osY0FBUyxHQUFULFNBQVMsQ0FBUztRQUNsQixjQUFTLEdBQVQsU0FBUyxDQUFTO0lBQ3hCLENBQUM7O1NBbEJPLFNBQVMiLCJzb3VyY2VzQ29udGVudCI6WyJpbXBvcnQgeyBTdWJqZWN0IH0gZnJvbSAncnhqcyc7XHJcbmltcG9ydCB7IFdlYlNlc3Npb25JbmZvIH0gZnJvbSAnLi4vbW9kZWwvY29tbW9uLmFwaSc7XHJcblxyXG5leHBvcnQgY2xhc3MgQXV0aEV2ZW50IHtcclxuICBwdWJsaWMgc3RhdGljIHJlYWRvbmx5IEFVVEhfRVZFTlRfTE9HSU4gPSAnTE9HSU4nO1xyXG4gIHB1YmxpYyBzdGF0aWMgcmVhZG9ubHkgQVVUSF9FVkVOVF9MT0dPVVQgPSAnTE9HT1VUJztcclxuICBwdWJsaWMgc3RhdGljIHJlYWRvbmx5IEFVVEhfRVZFTlRfTk9fQVVUSCA9ICdOT19BVVRIJztcclxuICBwdWJsaWMgc3RhdGljIHJlYWRvbmx5IEFVVEhfRVZFTlRfNDAxID0gJzQwMSc7XHJcbiAgcHVibGljIHN0YXRpYyByZWFkb25seSBBVVRIX0VWRU5UX09USEVSX1VTRVIgPSAnT1RIRVJfVVNFUic7XHJcbiAgcHVibGljIHN0YXRpYyByZWFkb25seSBBVVRIX0VWRU5UX1VQREFURSA9ICdVUERBVEUnO1xyXG5cclxuICBwdWJsaWMgc3RhdGljIGlzVW5hdXRoRXJyb3IgPSBmYWxzZTtcclxuXHJcbiAgcHVibGljIHN0YXRpYyB1c2VyTG9nZ2VkSW46IFN1YmplY3Q8QXV0aEV2ZW50PiA9IG5ldyBTdWJqZWN0KCk7XHJcblxyXG4gIGNvbnN0cnVjdG9yKFxyXG4gICAgcHVibGljIHVzZXJMb2dnZWRJbjogYm9vbGVhbixcclxuICAgIHB1YmxpYyBzZXNzaW9uOiBXZWJTZXNzaW9uSW5mbyxcclxuICAgIHB1YmxpYyB0eXBlOiBzdHJpbmcsXHJcbiAgICBwdWJsaWMgcmVnU3RhdHVzPzogc3RyaW5nLFxyXG4gICAgcHVibGljIHJldHVyblVybD86IHN0cmluZ1xyXG4gICkge31cclxufVxyXG4iXX0=