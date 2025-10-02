import { Component } from '@angular/core';
import { RouterOutlet } from '@angular/router';
import { Menu } from '@app/components/menu/menu';

@Component({
  selector: 'app-main-layout',
  imports: [RouterOutlet, Menu],
  templateUrl: './main-layout.html',
  styleUrl: './main-layout.css'
})
export class MainLayout {

}
