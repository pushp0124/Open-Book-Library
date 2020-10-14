import { async, ComponentFixture, TestBed } from '@angular/core/testing';

import { LibraryInventoryReportComponent } from './library-inventory-report.component';

describe('LibraryInventoryReportComponent', () => {
  let component: LibraryInventoryReportComponent;
  let fixture: ComponentFixture<LibraryInventoryReportComponent>;

  beforeEach(async(() => {
    TestBed.configureTestingModule({
      declarations: [ LibraryInventoryReportComponent ]
    })
    .compileComponents();
  }));

  beforeEach(() => {
    fixture = TestBed.createComponent(LibraryInventoryReportComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
