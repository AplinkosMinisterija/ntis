import { HttpEvent, HttpHeaders, HttpRequest, HttpResponse } from '@angular/common/http';
import { Observable } from 'rxjs';

export function mockApi(url: string, method: string, request: HttpRequest<unknown>): Observable<HttpEvent<unknown>> {
  console.log('MOCK: ' + method + ':' + url);

  // LOGIN:
  if (url.endsWith('/nemcert-api/rest/auth/login') && method === 'POST') {
    // get parameters from post request
    const params = JSON.parse(request.body as string) as Record<string, unknown>;

    if (params.username === 'demo' && params.password === 'demo') {
      // if login details are valid return 200 OK with user details and fake jwt token
      return new Observable((resp) => {
        resp.next(
          new HttpResponse({
            status: 200,
            body: {
              user: {
                id: 1236,
                username: 'demo',
                firstName: 'Demo',
                lastName: 'Demo',
              },
              token: 'JWT',
            },
          })
        );
        resp.complete();
      });
    } else {
      return new Observable((resp) => {
        const respHeaders = new HttpHeaders({ 'X-S2-status': 'ERR' });
        const response = new HttpResponse({
          status: 401,
          body: [{ default_text: 'Username or password is incorrect', severity: 'E' }],
          headers: respHeaders,
        });
        resp.next(response);
        resp.complete();
      });
    }
  }

  if (url.endsWith('/nemcert-api/rest/auth/logout') && method === 'POST') {
    return new Observable((resp) => {
      resp.next(
        new HttpResponse({
          status: 200,
        })
      );
      resp.complete();
    });
  }
}
