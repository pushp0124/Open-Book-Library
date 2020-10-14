import { TestBed } from '@angular/core/testing';

import { TokenAuthInterceptorService } from './token-auth-interceptor.service';

describe('TokenAuthInterceptorService', () => {
  beforeEach(() => TestBed.configureTestingModule({}));

  it('should be created', () => {
    const service: TokenAuthInterceptorService = TestBed.get(TokenAuthInterceptorService);
    expect(service).toBeTruthy();
  });
});
