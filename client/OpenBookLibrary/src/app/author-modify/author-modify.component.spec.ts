import { async, ComponentFixture, TestBed } from '@angular/core/testing';

import { AuthorModifyComponent } from './author-modify.component';

describe('AuthorModifyComponent', () => {
  let component: AuthorModifyComponent;
  let fixture: ComponentFixture<AuthorModifyComponent>;

  beforeEach(async(() => {
    TestBed.configureTestingModule({
      declarations: [ AuthorModifyComponent ]
    })
    .compileComponents();
  }));

  beforeEach(() => {
    fixture = TestBed.createComponent(AuthorModifyComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
