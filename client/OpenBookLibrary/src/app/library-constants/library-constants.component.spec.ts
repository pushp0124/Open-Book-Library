import { async, ComponentFixture, TestBed } from '@angular/core/testing';

import { LibraryConstantsComponent } from './library-constants.component';

describe('LibraryConstantsComponent', () => {
  let component: LibraryConstantsComponent;
  let fixture: ComponentFixture<LibraryConstantsComponent>;

  beforeEach(async(() => {
    TestBed.configureTestingModule({
      declarations: [ LibraryConstantsComponent ]
    })
    .compileComponents();
  }));

  beforeEach(() => {
    fixture = TestBed.createComponent(LibraryConstantsComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
