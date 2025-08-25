import { Component } from '@angular/core';
import { CommonModule } from '@angular/common';
import { FormBuilder, FormGroup, FormArray, Validators, ReactiveFormsModule } from '@angular/forms';

import { ErrorMessageOnInputs } from '../error-message-on-inputs/error-message-on-inputs';
import { CustomedValidators } from '@utils/validators/form.inputs.validator';


@Component({
  selector: 'app-create-invoice',
  imports: [ReactiveFormsModule, CommonModule, ErrorMessageOnInputs],
  templateUrl: './create-invoice.html',
  styleUrl: './create-invoice.css'
})
export class CreateInvoice {

  invoiceForm : FormGroup;

  //avoid new FormControl(''); wrap all in FormGroup
  constructor(private formConrolsOfInvoiceForm: FormBuilder) 
  {
    this.invoiceForm = this.formConrolsOfInvoiceForm.group(
    {
      invoiceNumber: ['', [Validators.required, CustomedValidators.invoicePattern()]],// pattern F0000-00000
      invoiceDate : ['', Validators.required],
      invoiceDueDate : ['', Validators.required],

      clientName : ['', Validators.required],
      clientAdress : ['', Validators.required],
      clientPostalCode : ['', [Validators.required, CustomedValidators.postalCode()]], //pattern 5 digits
      clientEmail : ['', [Validators.required, CustomedValidators.email()]],//email pattern
      clientPhone : ['', [Validators.required, CustomedValidators.phone()]],

      companyName : ['', Validators.required],
      companyAdress : ['', Validators.required],
      companyPostalCode : ['', [Validators.required, CustomedValidators.postalCode()]],
      companyEmail : ['', [Validators.required, CustomedValidators.email()]],
      companyPhone : ['', [Validators.required, CustomedValidators.phone()]],

      items : this.formConrolsOfInvoiceForm.array([this.createNewItemEntry()])
    });

  }

  createNewItemEntry(): FormGroup
  {
    const newItemEntry = this.formConrolsOfInvoiceForm.group(
      {
        itemDescription : ['', Validators.required],
        itemPrice : [0, [Validators.required, Validators.min(0)]],
        itemQuantity : [1, [Validators.required, Validators.min(1)]],
        itemHT : [0],
        itemTVA : [0, [Validators.required, Validators.min(0)]],
      }
    );

    // attach obsrvables to values changes & compute itemHT over user input
    newItemEntry.valueChanges.subscribe( item =>
      {
        const price = item.itemPrice || 0;
        const quantity = item.itemQuantity || 0;
        const HTresult =  price * quantity;

        newItemEntry.get('itemHT')?.setValue(HTresult, { emitEvent: false });
      }
    );

    return newItemEntry;
  }

  get invoiceFormEntries()
  {
    return this.invoiceForm.controls;
  }

  //This is used for looping through invoiceForm.items[] in html.
  get items(): FormArray
  {
    return this.invoiceForm.get('items') as FormArray;
  }

  addItem() 
  {
    this.items.push(this.createNewItemEntry());
  }

  removeItem(index : number)
  {
    this.items.removeAt(index);
  }

  workoutTotal(option :  "HT" | "TVA" | "Total") : number
  {
    let result : number = 0;

      this.items.controls.forEach(control =>
      {
        const price = control.get('itemPrice')?.value || 0;
        const quantity = control.get('itemQuantity')?.value || 0;
        const TVA = control.get('itemTVA')?.value || 0;

        if(option === "HT")
        result += price * quantity;
        else if(option === "TVA")
        result += price * quantity * (TVA/100);
        else
        result += (price * quantity) * (1+(TVA/100)); 
      });
    
    return result;
  }
  
  onSubmit()
  {
    console.log(this.invoiceForm.value);
  }

}
