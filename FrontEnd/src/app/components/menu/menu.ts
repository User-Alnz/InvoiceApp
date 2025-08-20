import { Component } from '@angular/core';

interface MenuButton {
  label: string;
  icon?: string;
  route: string;
}

@Component({
  selector: 'app-menu',
  imports: [],
  templateUrl: './menu.html',
  styleUrl: './menu.css'
})
export class Menu {

  buttons : MenuButton[] = [
    { label: 'Créer une facture', icon: '/edit.png', route: '/home' },
    { label: 'Factures', icon: '/invoice.png', route: '/invoices/create' },
    { label: 'Informations', icon: '/user.png', route: '/invoices' },
    { label: 'Services et produits', icon: '/package.png', route: '/user' }
  ];
  
}
