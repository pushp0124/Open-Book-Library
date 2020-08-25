import { async, ComponentFixture, TestBed } from '@angular/core/testing';

import { AdminHomeChildComponent } from './admin-home-child.component';

describe('AdminHomeChildComponent', () => {
  let component: AdminHomeChildComponent;
  let fixture: ComponentFixture<AdminHomeChildComponent>;

  beforeEach(async(() => {
    TestBed.configureTestingModule({
      declarations: [ AdminHomeChildComponent ]
    })
    .compileComponents();
  }));

  beforeEach(() => {
    fixture = TestBed.createComponent(AdminHomeChildComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
