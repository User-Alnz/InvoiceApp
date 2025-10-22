import { Component, EventEmitter, Output } from '@angular/core';
import { ReactiveFormsModule, FormGroup, FormBuilder, Validators } from '@angular/forms';
import { CreateClientRequest } from '@app/services/client/client.model';

import { CustomedValidators } from '@app/components/error-message-on-inputs/form.inputs.validator';
import { ErrorMessageOnInputs } from '@app/components/error-message-on-inputs/error-message-on-inputs';

@Component({
  selector: 'app-create-client',
  imports: [ReactiveFormsModule, ErrorMessageOnInputs],
  templateUrl: './create-client.html',
  styleUrl: './create-client.css'
})
export class CreateClient 
{
  clientForm : FormGroup;

  @Output() clientPayload = new EventEmitter<CreateClientRequest>()
  @Output() close = new EventEmitter<void>();

  constructor(
    private formBuilder : FormBuilder)
  {
    this.clientForm = this.formBuilder.group({
      name: ['', [Validators.required, Validators.minLength(2)]],
      email: ['', [CustomedValidators.email()]],
      tel: ['', [CustomedValidators.phone()]],
      address: ['', [Validators.required]],
      postalCode: ['', [Validators.required, CustomedValidators.postalCode()]],
      country: ['', [Validators.required]]
    })
  }

  get clientFormEntries()
  {
    return this.clientForm.controls;
  }

  onSubmit() : void
  {

    if (this.clientForm.invalid) 
    {
      this.clientForm.markAllAsTouched();
      return;
    }

    this.clientPayload.emit(this.clientForm.value as CreateClientRequest)
  }

  onClose(): void
  {
    this.close.emit();
  }
}
