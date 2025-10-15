import { CommonModule } from '@angular/common';
import { Component } from '@angular/core';
import { ReactiveFormsModule, FormBuilder, Validators, FormGroup } from '@angular/forms';
import { Router } from '@angular/router';
import { AuthService } from '@app/services/Auth/authService';

import { ErrorBanner } from '../error-banner/error-banner';
import { ErrorMessageOnInputs } from '../error-message-on-inputs/error-message-on-inputs';
import { CustomedValidators } from '@app/components/error-message-on-inputs/form.inputs.validator';
import { loginRequest } from '@app/services/Auth/auth.models';


@Component({
  selector: 'app-login-form',
  imports: [CommonModule, ReactiveFormsModule, ErrorMessageOnInputs, ErrorBanner],
  templateUrl: './login-form.html',
  styleUrl: './login-form.css'
})
export class LoginForm 
{

  form : FormGroup;
  apiErrorMessage : string | null = null;
  
  constructor( 
    private formBuilder : FormBuilder, 
    private authService : AuthService,
    private router: Router )
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

    this.apiErrorMessage = null;
    const request : loginRequest = this.form.value;

    this.authService.login(request).subscribe(
    (res) =>
    {
      if(res.status ==="success")
        this.router.navigate(['/app']); 
      else if(res.code === 401 && res.data.includes("Invalid credentials"))
        this.apiErrorMessage = "vos identifiants sont invalides";
      else
        this.apiErrorMessage = "une error est survenue";
    });
  }
}
