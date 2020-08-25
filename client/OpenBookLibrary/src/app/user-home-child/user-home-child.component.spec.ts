import { async, ComponentFixture, TestBed } from '@angular/core/testing';

import { UserHomeChildComponent } from './user-home-child.component';

describe('UserHomeChildComponent', () => {
  let component: UserHomeChildComponent;
  let fixture: ComponentFixture<UserHomeChildComponent>;

  beforeEach(async(() => {
    TestBed.configureTestingModule({
      declarations: [ UserHomeChildComponent ]
    })
    .compileComponents();
  }));

  beforeEach(() => {
    fixture = TestBed.createComponent(UserHomeChildComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
