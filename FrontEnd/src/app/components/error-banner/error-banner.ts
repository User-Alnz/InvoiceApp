import { Component, Input } from '@angular/core';
import { CommonModule } from '@angular/common';

@Component({
  selector: 'app-error-banner',
  imports: [CommonModule],
  template: ` 
  @if(message)
  {
    <div class="error-banner">
      <img src="/error.png" alt="error-icon">
      {{ message }}
    </div>
  }
  `,
  styles:[`
    .error-banner
    {
      display : flex;
      align-items : center;
      gap : 10px;
      padding: 5px 3px 5px 10px;
      color : #ffa2a2;
      background: #fb2c364d;
      border : 1px solid #fb2c364d;
      border-radius: 8px;
    }  
  `] 
})
export class ErrorBanner 
{
  @Input() message : string | null = null;
}
