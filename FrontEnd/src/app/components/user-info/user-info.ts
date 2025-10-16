import { Component, OnInit } from '@angular/core';
import { CommonModule } from '@angular/common';
import { ReactiveFormsModule, FormBuilder, FormGroup  } from '@angular/forms';
import { CompanyService } from '@app/services/company/companyService';
import { Company } from '@app/services/company/company.models';

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
  needToCreateCompany: boolean = false;
  companyId?: number; //lives only in memory no caching. Only during component life cycle.
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
        this.companyId = res.data.id;
        this.form.patchValue(res.data);
        this.hasACompany = true; 
      }
      
      if(res.code === 404)
        this.hasACompany = false;
        
    });
  }
  
  //this is used on button
  createFirstCompany(): void
  {
    this.hasACompany = true;
    this.needToCreateCompany = true;
    this.option = "Cancel";
    this.isAllowed = true;
    this.form.enable();
  }

  //this is used on button
  updateAllowed(): void
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

  //this handle automatically logic PUT | POST depending user context  
  onSubmit(): void
  {
    const company: Company = this.form.value;

    //security convert input string safely to number
    company.shareCapital = Number(
      String(company.shareCapital).replace(/\s+/g, '').replace(',', '.')
    );

    //Create a company
    if(this.needToCreateCompany)
    {
      this.companyService.createCompany(company).subscribe(
        (res) =>
        {
          this.form.patchValue(res.data);
          this.needToCreateCompany = false;
          this.isAllowed = false;
          this.option = 'Modifier';
          this.form.disable();
        }
      );
    }

    //Update a company
    if(!this.needToCreateCompany && this.companyId)
    {
      this.companyService.updateCompany(this.companyId, company).subscribe(
        (res) =>
        {
          this.form.patchValue(res.data);
          this.isAllowed = false;
          this.option = 'Modifier';
          this.form.disable();
        }
      );
    }
  }

}
