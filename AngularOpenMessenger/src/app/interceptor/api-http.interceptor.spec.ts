import { TestBed } from '@angular/core/testing';

import { ApiHttpInterceptor } from './api-http.interceptor';

describe('ApiHttpInterceptor', () => {
  beforeEach(() => TestBed.configureTestingModule({
    providers: [
      ApiHttpInterceptor
      ]
  }));

  it('should be created', () => {
    const interceptor: ApiHttpInterceptor = TestBed.inject(ApiHttpInterceptor);
    expect(interceptor).toBeTruthy();
  });
});
