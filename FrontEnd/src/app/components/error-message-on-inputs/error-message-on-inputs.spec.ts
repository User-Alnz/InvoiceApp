import { ComponentFixture, TestBed } from '@angular/core/testing';

import { ErrorMessageOnInputs } from './error-message-on-inputs';

describe('ErrorMessageOnInputs', () => {
  let component: ErrorMessageOnInputs;
  let fixture: ComponentFixture<ErrorMessageOnInputs>;

  beforeEach(async () => {
    await TestBed.configureTestingModule({
      imports: [ErrorMessageOnInputs]
    })
    .compileComponents();

    fixture = TestBed.createComponent(ErrorMessageOnInputs);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
