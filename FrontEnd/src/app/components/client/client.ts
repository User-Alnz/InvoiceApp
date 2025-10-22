import { Component, OnInit } from '@angular/core';
import { Pagination } from '../pagination/pagination';
import { CreateClient } from '../modals/create-client/create-client';
import { CompanyService } from '@app/services/company/companyService';
import { ClientService } from '@app/services/client/clientService';
import { filter, switchMap } from 'rxjs';
import { clientList, CreateClientRequest } from '@app/services/client/client.model';

@Component({
  selector: 'app-client',
  imports: [Pagination, CreateClient],
  templateUrl: './client.html',
  styleUrl: './client.css'
})
export class Client implements OnInit
{

  companyId?: number;
  currentPage = 1;
  totalPages = 0;
  clients: clientList[]=[];
  displayModal: boolean = false;

  constructor(
    private companyService: CompanyService,
    private clientService : ClientService)
  {}
  

  /*
    API call needed on fisrt page load. 
  */

  ngOnInit(): void 
  {
    this.companyService.getCompany().pipe(

    filter(res => res.status ==='success' && res.code === 200),
    switchMap(
      (res) =>
      {
        //because account  start from 1 parameter endpoint '?page=0;'
        const page = this.currentPage - 1;

        this.companyId = res.data.id;
        return this.clientService.getClient(this.companyId, page);
      }
    )
    ).subscribe(
    {
      next: (res)=>
      {
        this.clients = res.data.content;
        this.totalPages = res.data.totalPages;
      },
      error: (err)=>
      {
        console.error("error caught =>",err);
      }
    });
  }


  /*
    Methods below handle API call logic
  */

  private updateListOfClients(): void
  {
    if(!this.companyId)
      return;

    //because account  start from 1 parameter endpoint '?page=0;'
    const page = this.currentPage - 1;
    
    this.clientService.getClient(this.companyId, page)
    .subscribe
    ({

      next:(res)=>
      {
        if(res.status ==='success' && res.code === 200)
        {
          this.clients = res.data.content;
          this.totalPages = res.data.totalPages;
        }
      },
      error: (err) => console.error('Error fetching clients:', err)

    });

  }

  private createNewClient( payload : CreateClientRequest ): void
  {
    if(!this.companyId)
      return;

    this.clientService.createClient(this.companyId, payload)
    .subscribe
    ({

      next: (res)=> 
      {
        if(res.status ==='success' && res.code === 200)
        {
          this.displayModal = false;
          this.updateListOfClients();
        }
      },
      error: (err) => console.error('Error fetching clients:', err)

    });
  }

  /*
    Catch EmitterEvent children components
  */

  onPageChange(page: number): void 
  {
    this.currentPage = page;
    this.updateListOfClients();
  }

  onClientPayload(client: CreateClientRequest): void
  {
    this.createNewClient(client);
  }
}
