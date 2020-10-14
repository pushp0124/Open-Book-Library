import { TestBed } from '@angular/core/testing';

import { FirestoreChatService } from './firestore-chat.service';

describe('FirestoreChatService', () => {
  beforeEach(() => TestBed.configureTestingModule({}));

  it('should be created', () => {
    const service: FirestoreChatService = TestBed.get(FirestoreChatService);
    expect(service).toBeTruthy();
  });
});
