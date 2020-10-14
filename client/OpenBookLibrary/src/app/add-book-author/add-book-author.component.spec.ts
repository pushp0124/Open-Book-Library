import { async, ComponentFixture, TestBed } from '@angular/core/testing';

import { AddBookAuthorComponent } from './add-book-author.component';

describe('AddBookAuthorComponent', () => {
  let component: AddBookAuthorComponent;
  let fixture: ComponentFixture<AddBookAuthorComponent>;

  beforeEach(async(() => {
    TestBed.configureTestingModule({
      declarations: [ AddBookAuthorComponent ]
    })
    .compileComponents();
  }));

  beforeEach(() => {
    fixture = TestBed.createComponent(AddBookAuthorComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
