import { Component } from '@angular/core';
import { ReactiveFormsModule, FormGroup, FormBuilder, Validators } from '@angular/forms';

@Component({
  selector: 'app-create-client',
  imports: [ReactiveFormsModule],
  templateUrl: './create-client.html',
  styleUrl: './create-client.css'
})
export class CreateClient 
{
  clientForm : FormGroup;

  constructor(private formBuilder : FormBuilder)
  {
    this.clientForm = this.formBuilder.group({
      name: ['', [Validators.required, Validators.minLength(2)]],
      email: ['', [Validators.required, Validators.email]],
      tel: ['', [Validators.required]],
      address: [''],
      postalCode: [''],
      country: ['']
    })
  }

}
