import { async, ComponentFixture, TestBed } from '@angular/core/testing';

import { DepositBookComponent } from './deposit-book.component';

describe('DepositBookComponent', () => {
  let component: DepositBookComponent;
  let fixture: ComponentFixture<DepositBookComponent>;

  beforeEach(async(() => {
    TestBed.configureTestingModule({
      declarations: [ DepositBookComponent ]
    })
    .compileComponents();
  }));

  beforeEach(() => {
    fixture = TestBed.createComponent(DepositBookComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
