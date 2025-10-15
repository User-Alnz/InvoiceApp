import { Component } from '@angular/core';
import { CommonModule } from '@angular/common';
import { ReactiveFormsModule, FormBuilder, FormGroup  } from '@angular/forms';

@Component({
  selector: 'app-user-info',
  imports: [ReactiveFormsModule,CommonModule],
  templateUrl: './user-info.html',
  styleUrl: './user-info.css'
})
export class UserInfo 
{
  option : "Modifier" | "Cancel" = 'Modifier';
  isAllowed : boolean = false; 


  form : FormGroup;

  constructor(private formBuilder : FormBuilder)
  {
    this.form = this.formBuilder.group({

      name: [''],
      address: [''],
      postalCode: [''],
      country: [''],
      tel: [''],
      email: [''],
      legalStatus: [''],
      shareCapital: [''],
      siren: [''],
      siret: [''],
      rcs: [''],
      tvaNumber: [''],
      websiteUrl: ['']

    })

    this.form.disable();
  }
  
  onSubmit()
  {
    console.log(this.form.value);
  }

  updateAllowed() : void
  {
    if(!this.isAllowed)
    {
      this.option = "Cancel";
      this.form.enable();
    }
    else
    {
      this.option = "Modifier";
      this.form.disable();
    }
      
    this.isAllowed = !this.isAllowed;
  }

}
