import { Component, OnInit } from '@angular/core';
import { Pagination } from '../pagination/pagination';
import { CompanyService } from '@app/services/company/companyService';
import { ClientService } from '@app/services/client/clientService';
import { filter, switchMap } from 'rxjs';
import { clientList } from '@app/services/client/client.model';

@Component({
  selector: 'app-client',
  imports: [Pagination],
  templateUrl: './client.html',
  styleUrl: './client.css'
})
export class Client implements OnInit
{

  companyId?: number;
  currentPage = 0;
  clients:clientList[]=[];

  constructor(
    private companyService: CompanyService,
    private clientService : ClientService)
  {}

  ngOnInit(): void 
  {
    this.companyService.getCompany().pipe(

    filter(res => res.status ==='success'),
    switchMap(
      (res) =>
      {
        this.companyId = res.data.id;
        return this.clientService.getClient(this.companyId, this.currentPage);
      }
    )
    ).subscribe(
    {
      next: (res)=>
      {
        this.clients = res.data.content;
      },
      error: (err)=>
      {
        console.error("error caught =>",err);
      }
    });
  }
}
