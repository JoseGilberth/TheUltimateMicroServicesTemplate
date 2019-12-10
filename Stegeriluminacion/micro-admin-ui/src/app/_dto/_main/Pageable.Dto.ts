
export class Sort {
    sorted: boolean;
    unsorted: boolean;
    empty: boolean;
}

export class Pageable {
    sort: Sort;
    offset: number;
    pageNumber: number;
    pageSize: number;
    unpaged: boolean;
    paged: boolean;
}

export class Sort2 {
    sorted: boolean;
    unsorted: boolean;
    empty: boolean;
}

export class PageableDTO {
    content: any[];
    pageable: Pageable;
    last: boolean;
    totalPages: number;
    totalElements: number;
    size: number;
    number: number;
    sort: Sort2;
    numberOfElements: number;
    first: boolean;
    empty: boolean;
}