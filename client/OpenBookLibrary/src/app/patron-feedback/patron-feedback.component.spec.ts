import { async, ComponentFixture, TestBed } from '@angular/core/testing';

import { PatronFeedbackComponent } from './patron-feedback.component';

describe('PatronFeedbackComponent', () => {
  let component: PatronFeedbackComponent;
  let fixture: ComponentFixture<PatronFeedbackComponent>;

  beforeEach(async(() => {
    TestBed.configureTestingModule({
      declarations: [ PatronFeedbackComponent ]
    })
    .compileComponents();
  }));

  beforeEach(() => {
    fixture = TestBed.createComponent(PatronFeedbackComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
