import { Component } from '@angular/core';
import { RouterModule } from '@angular/router';

interface MenuButton {
  label: string;
  icon?: string;
  route: string;
}

@Component({
  selector: 'app-menu',
  imports: [RouterModule],
  templateUrl: './menu.html',
  styleUrl: './menu.css'
})
export class Menu {

  buttons : MenuButton[] = [
    { label: 'Créer une facture', icon: '/edit.png', route: 'invoice' },
    { label: 'Factures', icon: '/invoice.png', route: 'invoice' },
    { label: 'Informations', icon: '/user.png', route: 'info' },
    { label: 'Services et produits', icon: '/package.png', route: 'invoice' }
  ];
  
}
