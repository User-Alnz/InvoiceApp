import { Component } from '@angular/core';
import { FormBuilder, FormGroup, ReactiveFormsModule, Validators } from '@angular/forms';

import { CustomedValidators } from '@app/components/error-message-on-inputs/form.inputs.validator';
import { ErrorMessageOnInputs } from "../error-message-on-inputs/error-message-on-inputs";

@Component({
  selector: 'app-signup-form',
  imports: [ReactiveFormsModule, ErrorMessageOnInputs],
  templateUrl: './signup-form.html',
  styleUrl: './signup-form.css'
})
export class SignupForm 
{
  form : FormGroup;

  constructor( private formBuilder : FormBuilder )
  {
    this.form = this.formBuilder.group({
      firstName : ['', [Validators.required, Validators.minLength(2), Validators.maxLength(50)]],
      lastName : ['', [Validators.required, Validators.minLength(2), Validators.maxLength(50)]],
      email : ['', [Validators.required, Validators.maxLength(50) ,CustomedValidators.email()]],
      password : ['', [Validators.required,CustomedValidators.password()]],
      confirmPassword : ['', [Validators.required]]
    }, 
    { 
      //This below is options?: AbstractControlOptions | null .
      //this accept super validators over controls of FormGroup above. ref doc: "FormGroup-level validators" | "cross-field validators".
      validators : CustomedValidators.passwordMismatch('password', 'confirmPassword'),
      //In html bind control to whole "form". easier access to group controls <app-error-message-on-inputs [control]="form"></app-error-message-on-inputs>.
      updateOn: 'blur'
    }
  );
  }

  get formEntites()
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
