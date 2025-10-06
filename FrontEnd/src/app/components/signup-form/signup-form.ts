import { Component } from '@angular/core';
import { FormBuilder, FormGroup, ReactiveFormsModule, Validators } from '@angular/forms';
import { AuthService } from '@app/services/authService';

import { ErrorBanner } from '../error-banner/error-banner';
import { CustomedValidators } from '@app/components/error-message-on-inputs/form.inputs.validator';
import { ErrorMessageOnInputs } from "../error-message-on-inputs/error-message-on-inputs";
import { registerRequest } from '@app/services/auth.models';

@Component({
  selector: 'app-signup-form',
  imports: [ReactiveFormsModule, ErrorMessageOnInputs, ErrorBanner],
  templateUrl: './signup-form.html',
  styleUrl: './signup-form.css'
})
export class SignupForm 
{

  form : FormGroup;
  apiErrorMessage : string | null = null;

  constructor( private formBuilder : FormBuilder, private http : AuthService )
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

    this.apiErrorMessage = null;
    const request : registerRequest = this.form.value;

    this.http.register(request).subscribe(
    (res) => 
    {
      if(res.status ==="success")
        console.log("account created");
      else if(res.code === 400 && res.data.includes("Email already in used"))
        this.apiErrorMessage = "votre email est invalide.";
      else
        this.apiErrorMessage = "une erreur est survenue";
    });
  }
}
