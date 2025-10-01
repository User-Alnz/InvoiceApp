import { CommonModule } from '@angular/common';
import { Component } from '@angular/core';
import { ReactiveFormsModule, FormBuilder, Validators, FormGroup } from '@angular/forms';

import { ErrorMessageOnInputs } from '../error-message-on-inputs/error-message-on-inputs';
import { CustomedValidators } from '@app/components/error-message-on-inputs/form.inputs.validator';

@Component({
  selector: 'app-login-form',
  imports: [CommonModule, ReactiveFormsModule, ErrorMessageOnInputs],
  templateUrl: './login-form.html',
  styleUrl: './login-form.css'
})
export class LoginForm 
{

  form : FormGroup;

  constructor( private formBuilder : FormBuilder)
  {
    this.form = this.formBuilder.group({
      email: ['', [Validators.required, CustomedValidators.email()]],
      password: ['', Validators.required],
    },
    {
      updateOn: 'blur'
    });
  } 

  get formEntries()
  {
    return this.form.controls;
  }

  onSubmit() 
  {
    if (this.form.invalid) 
    {
      this.form.markAllAsTouched();
      return;
    }

    console.log(this.form.value);
  }
}
