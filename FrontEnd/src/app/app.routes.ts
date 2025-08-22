import { Routes } from '@angular/router';
import { CreateInvoice } from './components/create-invoice/create-invoice';

/* 
    improve routing later => https://medium.com/@schaman762/angular-routing-best-practices-and-advanced-techniques-2da3f47226f1 
*/

export const routes: Routes = [
    {
        path :"",
        component : CreateInvoice,
        title : "create invoice"
    }
];
