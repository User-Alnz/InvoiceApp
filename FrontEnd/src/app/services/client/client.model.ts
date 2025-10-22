export interface CreateClientRequest
{
    name: string,
    address: string,
    postalCode: string,
    country: string,
    tel: string,
    email: string
}

export interface ApiResponse<T>
{
  status: string; // "error"
  code: number;   // 200, 400, 409, etc
  data: T;   // ex => Company
}

export interface clientList
{
    id: number,
    name: string,
    address: string,
    postalCode: string,
    country: string,
    tel: string,
    email: string           
}

export interface Pagination
{
    content: [clientList],
    
        pageable: {
            pageNumber: number, // by default start from 0
            pageSize: number, //by default 20. enforces by backend not mutable
            sort: {
                sorted: boolean,
                empty: boolean,
                unsorted: boolean
            },
            offset: number, 
            paged: boolean,
            unpaged: boolean
        },
        totalPages: number, //computed by default from backend
        totalElements: number,
        last: boolean,
        size: number,
        number: number,
        sort: {
            sorted: boolean,
            empty: boolean,
            unsorted: boolean
        },
        numberOfElements: number,
        first: boolean,
        empty: boolean
}
    