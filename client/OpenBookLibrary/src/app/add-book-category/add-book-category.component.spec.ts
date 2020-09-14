import { async, ComponentFixture, TestBed } from '@angular/core/testing';

import { AddBookCategoryComponent } from './add-book-category.component';

describe('AddBookCategoryComponent', () => {
  let component: AddBookCategoryComponent;
  let fixture: ComponentFixture<AddBookCategoryComponent>;

  beforeEach(async(() => {
    TestBed.configureTestingModule({
      declarations: [ AddBookCategoryComponent ]
    })
    .compileComponents();
  }));

  beforeEach(() => {
    fixture = TestBed.createComponent(AddBookCategoryComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
