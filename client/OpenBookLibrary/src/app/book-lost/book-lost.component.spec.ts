import { async, ComponentFixture, TestBed } from '@angular/core/testing';

import { BookLostComponent } from './book-lost.component';

describe('BookLostComponent', () => {
  let component: BookLostComponent;
  let fixture: ComponentFixture<BookLostComponent>;

  beforeEach(async(() => {
    TestBed.configureTestingModule({
      declarations: [ BookLostComponent ]
    })
    .compileComponents();
  }));

  beforeEach(() => {
    fixture = TestBed.createComponent(BookLostComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
