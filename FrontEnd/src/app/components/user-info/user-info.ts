import { Component, OnInit } from '@angular/core';
import { CommonModule } from '@angular/common';
import { ReactiveFormsModule, FormBuilder, FormGroup  } from '@angular/forms';
import { CompanyService } from '@app/services/company/companyService';

@Component({
  selector: 'app-user-info',
  imports: [ReactiveFormsModule,CommonModule],
  templateUrl: './user-info.html',
  styleUrl: './user-info.css'
})
export class UserInfo implements OnInit
{
  option : "Modifier" | "Cancel" = 'Modifier';
  isAllowed : boolean = false; 
  hasACompany: boolean = false;
  form : FormGroup;


  constructor(
    private formBuilder : FormBuilder,
    private companyService : CompanyService)
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


  //fetch backend on first page load
  ngOnInit(): void 
  {
    this.companyService.getCompany().subscribe(

    (res) =>
    {
      if (res.status === 'success')
      {
        this.form.patchValue(res.data);
        this.hasACompany = true; 
      }
      
      if(res.code === 404)
        this.hasACompany = false;
        
    });
  }
  
  onSubmit()
  {
    console.log(this.form.value);
  }

  createFirstCompany() :void
  {
    this.hasACompany = true;
    this.option = "Cancel";
    this.isAllowed = true;
    this.form.enable();
  }

  updateAllowed() : void
  {
    if(!this.isAllowed && this.hasACompany)
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
