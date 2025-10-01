import { Component, Input } from '@angular/core';
import { CommonModule } from '@angular/common';
import { AbstractControl } from '@angular/forms';

import { ERROR_MESSAGES } from '@app/components/error-message-on-inputs/form-error-messages';

@Component({
  selector: 'app-error-message-on-inputs',
  imports: [CommonModule],
  template: `

    @if(control.invalid && (control.touched || control.dirty))
    {
      <div class="error">
        @for(error of errorMessages; track $index)
        {
          <small>{{error}}</small>
        }
      </div>
    }
  `, 
})
export class ErrorMessageOnInputs 
{

  @Input () control!: AbstractControl;

  get errorMessages() : string[]
  {
    if (!this.control?.errors) 
    return [];

    return Object.keys(this.control.errors).map(
      (key) => ERROR_MESSAGES[key] || "Erreur inconnue."
    );
  }
}
