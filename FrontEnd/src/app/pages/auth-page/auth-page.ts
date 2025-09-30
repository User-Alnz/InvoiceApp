import { Component } from '@angular/core';
import { CommonModule } from '@angular/common';
import { LoginForm } from '@app/components/login-form/login-form';
import { SignupForm } from '@app/components/signup-form/signup-form';


@Component({
  selector: 'app-auth-page',
  imports: [LoginForm, SignupForm, CommonModule],
  templateUrl: './auth-page.html',
  styleUrl: './auth-page.css'
})
export class AuthPage 
{

  activeSelection: 'login' | 'signup' = 'login';

}
