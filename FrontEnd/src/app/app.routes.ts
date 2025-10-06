import { Routes } from '@angular/router';
import { CreateInvoice } from './components/create-invoice/create-invoice';
import { AuthPage } from "./pages/auth-page/auth-page";
import { MainLayout } from './layout/main-layout';
import { authGuard } from './guards/auth-guard';

/* 
    improve routing later => https://medium.com/@schaman762/angular-routing-best-practices-and-advanced-techniques-2da3f47226f1 
*/

export const routes: Routes = [
    {
        path :"",
        component : AuthPage,
        title : "connection page"
    },
    {
        path: 'app',
        component: MainLayout,
        canActivate: [authGuard],
        children :
        [
            {
                path: 'invoice',
                component: CreateInvoice,
                title: 'Create Invoice'
            },
            { //this is default page when app load
                path: "",               
                redirectTo: "invoice",  
                pathMatch: "full" //means when entire path after /app is empty
            },
            {
                path: '**',
                redirectTo: 'invoice',
            }
        ]
    },
    { path: '**', redirectTo: '', pathMatch: 'full' }
];
