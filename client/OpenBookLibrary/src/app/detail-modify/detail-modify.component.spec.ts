import { async, ComponentFixture, TestBed } from '@angular/core/testing';

import { DetailModifyComponent } from './detail-modify.component';

describe('DetailModifyComponent', () => {
  let component: DetailModifyComponent;
  let fixture: ComponentFixture<DetailModifyComponent>;

  beforeEach(async(() => {
    TestBed.configureTestingModule({
      declarations: [ DetailModifyComponent ]
    })
    .compileComponents();
  }));

  beforeEach(() => {
    fixture = TestBed.createComponent(DetailModifyComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
