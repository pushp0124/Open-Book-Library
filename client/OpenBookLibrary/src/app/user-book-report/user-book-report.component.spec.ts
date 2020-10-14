import { async, ComponentFixture, TestBed } from '@angular/core/testing';

import { UserBookReportComponent } from './user-book-report.component';

describe('UserBookReportComponent', () => {
  let component: UserBookReportComponent;
  let fixture: ComponentFixture<UserBookReportComponent>;

  beforeEach(async(() => {
    TestBed.configureTestingModule({
      declarations: [ UserBookReportComponent ]
    })
    .compileComponents();
  }));

  beforeEach(() => {
    fixture = TestBed.createComponent(UserBookReportComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
