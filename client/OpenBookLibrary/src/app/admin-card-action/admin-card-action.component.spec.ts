import { async, ComponentFixture, TestBed } from '@angular/core/testing';

import { AdminCardActionComponent } from './admin-card-action.component';

describe('AdminCardActionComponent', () => {
  let component: AdminCardActionComponent;
  let fixture: ComponentFixture<AdminCardActionComponent>;

  beforeEach(async(() => {
    TestBed.configureTestingModule({
      declarations: [ AdminCardActionComponent ]
    })
    .compileComponents();
  }));

  beforeEach(() => {
    fixture = TestBed.createComponent(AdminCardActionComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
