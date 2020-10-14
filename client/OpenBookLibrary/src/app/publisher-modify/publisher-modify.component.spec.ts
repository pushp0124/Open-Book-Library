import { async, ComponentFixture, TestBed } from '@angular/core/testing';

import { PublisherModifyComponent } from './publisher-modify.component';

describe('PublisherModifyComponent', () => {
  let component: PublisherModifyComponent;
  let fixture: ComponentFixture<PublisherModifyComponent>;

  beforeEach(async(() => {
    TestBed.configureTestingModule({
      declarations: [ PublisherModifyComponent ]
    })
    .compileComponents();
  }));

  beforeEach(() => {
    fixture = TestBed.createComponent(PublisherModifyComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
