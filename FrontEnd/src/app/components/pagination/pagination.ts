import {Component, Input, Output, EventEmitter, OnChanges} from '@angular/core';

@Component({
  selector: 'app-pagination',
  imports: [],
  templateUrl: './pagination.html',
  styleUrl: './pagination.css'
})
export class Pagination implements OnChanges
{
  /**
   *  NOTICE :
   * 
   *  Before going futher please read this. How to re-use pagination component ?
   *  
   *  logic is defined as followed : 
   *  ! Pagination simply handles flow through pages and take as arguments parent components properties defined there:
   *  
   *  @Input() totalPages!:number;
   *  @Input() currentPage!:number;
   * 
   *  this only emitChanges on currentPage ! and let parent handles fetching data from :
   *  @Output() pageChange = new EventEmitter<number>();
   * 
   *  to update curentPage on endpoint for pagination system like `${this.url}/user?page={currentPage}` 
   *  
   *  1- set Pagination in parent component html : 
   *  
   *   <app-pagination
   *    [currentPage]="currentPage"
   *    [totalPages]="totalPages"
   *    (pageChange)="onPageChange($event)"
   *    ></app-pagination>  
   * 
   *  2- in parent component set following function to update "currentPage" propertie: 
   * 
   *  onPageChange(page: number): void 
   *  {
   *    this.currentPage = page;
   *    this.fetch();
   *  }
   * 
   *  !Good - Call method responsible of fetching in function onPageChange() which is called by EventEmitter.
   *  
   */

  @Input() totalPages!:number;
  @Input() currentPage!:number;

  //this emit event to Parent Component to update currentPage
  @Output() pageChange = new EventEmitter<number>();


  pages:number[]=[];

  //On Each component changes. This recompute pages[] result. 
  ngOnChanges(): void 
  {
    //Reset array on each change
    this.pages = [];

    //This defines max and min limits of pagination
    const maxPages = this.totalPages;
    const minPages = 1;
    
    //This ensures selection to always remain two indexes up and below currentPage selection of user in view
    let loadArrayFrom = this.currentPage -3;
    let loadArrayStop = this.currentPage + 2;

    //This control pagination suggestion to never overflow max and min. 
    if(loadArrayFrom < minPages)
      loadArrayFrom = minPages;

    if(loadArrayStop > maxPages)
      loadArrayStop = maxPages;

    //This populates pages[] to map through -> to render page indexes.
    while(loadArrayFrom <= loadArrayStop)
    {
      this.pages.push(loadArrayFrom);
      loadArrayFrom++;
    }
  }

  /**
   * This below control pagination flow :
   * Through click on page selection, click to previous and next button
   */

  selectPage(page:number):void
  {
    if(page !== this.currentPage)
      this.pageChange.emit(page);
  }

  nextPage(): void 
  {
    if (this.currentPage < this.totalPages) 
      this.pageChange.emit(this.currentPage + 1);
  }

  prevPage(): void
  {
    if(this.currentPage > 1)
      this.pageChange.emit(this.currentPage-1);
  }
}
