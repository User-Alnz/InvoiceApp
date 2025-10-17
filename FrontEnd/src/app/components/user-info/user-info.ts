import { Component, OnInit } from '@angular/core';
import { CommonModule } from '@angular/common';
import { ReactiveFormsModule, FormBuilder, FormGroup, Validators } from '@angular/forms';
import { CompanyService } from '@app/services/company/companyService';
import { Company } from '@app/services/company/company.models';

import { ErrorMessageOnInputs } from '../error-message-on-inputs/error-message-on-inputs';
import { CustomedValidators } from '@app/components/error-message-on-inputs/form.inputs.validator';
import { formatCompanyRequest, formatCompanyResponse} from './cleanUserInputs';

@Component({
  selector: 'app-user-info',
  imports: [ReactiveFormsModule, CommonModule, ErrorMessageOnInputs],
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

      name: ['', [Validators.required, Validators.minLength(2), Validators.maxLength(128)]],
      address: ['', [Validators.required, Validators.minLength(5), Validators.maxLength(255)]],
      postalCode: ['', [Validators.required, CustomedValidators.postalCode()]],
      country: ['', [Validators.required, Validators.maxLength(64)]],
      tel: ['', [Validators.maxLength(20), CustomedValidators.phone()]],
      email: ['', [Validators.email, Validators.maxLength(254)]],
      legalStatus: ['', [Validators.required, Validators.maxLength(64)]],
      shareCapital: ['', [Validators.required, CustomedValidators.shareCapital()]],
      siren: ['',[Validators.required, CustomedValidators.siren()]],
      siret: ['',[Validators.required, CustomedValidators.siret()]],
      rcs: ['',[Validators.required, Validators.maxLength(64), CustomedValidators.rcs()]],
      tvaNumber: ['',[Validators.required ,CustomedValidators.tva()]],
      websiteUrl: ['', [Validators.maxLength(255)]]

    })

    this.form.disable();
  }

  get formFromEntries()
  {
    return this.form.controls;
  }

  //fetch backend on first page load
  ngOnInit(): void 
  {
    this.companyService.getCompany().subscribe(

    (res) =>
    {
      if (res.status === 'success')
      {
        formatCompanyResponse(res.data);
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
    let company: Company = this.form.value;

    //Clean extra space and correct format on -> shareCapital, siren, siret.
    formatCompanyRequest(company);

    //Create a company
    if(this.needToCreateCompany)
    {
      this.companyService.createCompany(company).subscribe(
        (res) =>
        {
          formatCompanyResponse(res.data);
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
          formatCompanyResponse(res.data);
          this.form.patchValue(res.data);
          this.isAllowed = false;
          this.option = 'Modifier';
          this.form.disable();
        }
      );
    }
  }

}
