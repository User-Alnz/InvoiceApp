import { Component } from '@angular/core';
import { CommonModule } from '@angular/common';
import { FormBuilder, FormGroup, FormArray, Validators, ReactiveFormsModule } from '@angular/forms';

@Component({
  selector: 'app-create-invoice',
  imports: [ReactiveFormsModule, CommonModule],
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
      invoiceNumber: ['', [Validators.required, Validators.pattern(/^F\d{4,}-\d+$/)]],// pattern F0000-00000
      invoiceDate : ['', Validators.required],
      invoiceDueDate : ['', Validators.required],

      clientName : ['', Validators.required],
      clientAdress : ['', Validators.required],
      clientPostalCode : ['', [Validators.required, Validators.pattern(/^\d{5}$/)]], //pattern 5 digits
      clientEmail : ['', [Validators.required, Validators.email]],
      clientPhone : ['', [Validators.required, Validators.pattern(/^(\+33|0)[1-9](\d{2}){4}$/)]], //pattern french format

      companyName : ['', Validators.required],
      companyAdress : ['', Validators.required],
      companyPostalCode : ['', [Validators.required, Validators.pattern(/^\d{5}$/)]],
      companyEmail : ['', [Validators.required, Validators.email]],
      companyPhone : ['', [Validators.required, Validators.pattern(/^(\+33|0)[1-9](\d{2}){4}$/)]],

      items : this.formConrolsOfInvoiceForm.array([this.createNewItemEntry()])
    });

  }

  createNewItemEntry(): FormGroup
  {
    return this.formConrolsOfInvoiceForm.group(
      {
        itemDescription : ['', Validators.required],
        itemPrice : [0, [Validators.required, Validators.min(0)]],
        itemQuantity : [1, [Validators.required, Validators.min(1)]],
        itemHT : [0, [Validators.required, Validators.min(0)]],
        itemTVA : [0, [Validators.required, Validators.min(0)]],
      }
    )
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
