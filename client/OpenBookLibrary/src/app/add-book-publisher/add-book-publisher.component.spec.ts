import { async, ComponentFixture, TestBed } from '@angular/core/testing';

import { AddBookPublisherComponent } from './add-book-publisher.component';

describe('AddBookPublisherComponent', () => {
  let component: AddBookPublisherComponent;
  let fixture: ComponentFixture<AddBookPublisherComponent>;

  beforeEach(async(() => {
    TestBed.configureTestingModule({
      declarations: [ AddBookPublisherComponent ]
    })
    .compileComponents();
  }));

  beforeEach(() => {
    fixture = TestBed.createComponent(AddBookPublisherComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
